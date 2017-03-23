package com.scg.persistent;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.ConsultantTime;
import com.scg.domain.Invoice;
import com.scg.domain.InvoiceLineItem;
import com.scg.domain.NonBillableAccount;
import com.scg.domain.Skill;
import com.scg.domain.TimeCard;
import com.scg.util.Address;
import com.scg.util.DateRange;
import com.scg.util.Name;
import com.scg.util.StateCode;

/**
 * @author Neil Nevitt 
 * Responsible for providing a programmatic interface to store and access objects in the database.
 *
 */
public final class DbServer {

	/**
	 * Database URL.
	 */
	private final String dbUrl;
	/**
	 * The username for database.
	 */
	private final String username;
	/**
	 * The password for the database.
	 */
	private final String password;
	
	private PreparedStatement ps;
	private ResultSet rs;
	private int timeCard_id =0;
	
	
	private final static String sqlAddClient = 
			"INSERT INTO clients (name, street, city, state, postal_code,"
					+ "contact_last_name, contact_first_name, contact_middle_name) " 
					+ "VALUES (?,?,?,?,?,?,?,?)";
	private static final String sqlAddConsultant = "INSERT INTO consultants " 
					+ "(last_name, first_name, middle_name)"
					+ "VALUES (?,?,?)";
	private static final String sqlAddTimeCard = "INSERT INTO timecards (consultant_id, start_date) "
			+ "VALUES (?,?)";
	private static final String clientSQL = "SELECT id, name FROM clients";
	/**
	 * Constructor.
	 * @param dbUrl - url for the database.
	 * @param username - user name for the database.
	 * @param password - password for the database.
	 */
	public DbServer(final String dbUrl, final String ussername, final String password) {
		super();
		this.dbUrl = dbUrl;
		this.username = ussername;
		this.password = password;
		
	}
	
	/**
	 * Add a client to the database.
	 * @param client - the client to add.
	 * @throws SQLException - if any database operation fails.
	 */
	public void addClient(ClientAccount client) throws SQLException {
		
		try (Connection conn = DriverManager.getConnection(this.dbUrl,this.username,this.password) ) {
		
		ps = conn.prepareStatement(sqlAddClient);
		ps.setString(1, client.getName());
		ps.setString(2,	client.getAddress().getStreetNumbers());
		ps.setString(3, client.getAddress().getCity());
		ps.setString(4, client.getAddress().getState().toString());
		ps.setString(5, client.getAddress().getPostalCode());
		ps.setString(6, client.getContact().getLastName());
		ps.setString(7, client.getContact().getFirstName());
		ps.setString(8, client.getContact().getMidleName());
		ps.executeUpdate();
		
		}
		
	}
	
	/**
	 * Add a consultant to the database.
	 * @param constulant - consultant to add.
	 * @throws SQLException - if any database operation fails.
	 */
	public void addConsultant(Consultant consultant ) throws SQLException {
		try ( Connection conn = DriverManager.getConnection(this.dbUrl,this.username,this.password) ) {
			ps = conn.prepareStatement(sqlAddConsultant);
			final Name name = consultant.getName();
			ps.setString(1, name.getLastName());
			ps.setString(2, name.getFirstName());
			ps.setString(3, name.getMidleName());
			ps.executeUpdate();

		}
		
	}
	
	/**
	 * Add a time card to the database.
	 * @param timeCard - the time card to add.
	 * @throws SQLException - if any database operation fails.
	 */
	public void addTimeCard(TimeCard timeCard ) throws SQLException {
		
		try ( Connection conn = DriverManager.getConnection(this.dbUrl,this.username,this.password) ) {
		Statement stat = conn.createStatement();
		
		Name name = timeCard.getConsultant().getName();
		String  id_consultant_command = "SELECT id FROM consultants WHERE" 
				+ " last_name = '" + name.getLastName() + "'" 
				+ " AND first_name = '" + name.getFirstName() + "'"  
				+ " AND middle_name = '" + name.getMidleName() + "'";
		rs =  stat.executeQuery(id_consultant_command);
		
		int consultant_id = 0;
		String startDate = timeCard.getWeekStartingDay().toString();
		while ( rs.next()) {
				consultant_id = rs.getInt(1);
		} 
		
		ps = conn.prepareStatement(sqlAddTimeCard, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, consultant_id);
		ps.setString(2, startDate);
		ps.executeUpdate();
		rs = ps.getGeneratedKeys();
		rs.next();
		timeCard_id = rs.getInt(1);
		
		billableHours( timeCard, timeCard_id, clientSQL , conn);
		
		
		String sqlDateCheck = "SELECT date FROM non_billable_hours";
		rs = stat.executeQuery(sqlDateCheck);

		List<LocalDate> list = new ArrayList<>();
		while ( rs.next() ) 
			list.add(LocalDate.parse(rs.getString(1)));	
		nonBillableHours( timeCard, timeCard_id, clientSQL, conn, list );	
		}
	}
	
	private void billableHours(TimeCard timeCard, int timecard_id , String sql, Connection conn)
			throws SQLException {

		Statement statQ = conn.createStatement();
		Statement statE = conn.createStatement();
		ResultSet rsQ;
		
		rsQ = statQ.executeQuery(sql);
		int client_id;
		String clientName;
		while ( rsQ.next() ) {
			client_id = rsQ.getInt(1);
			clientName = rsQ.getString(2);
			List<ConsultantTime> billableHours = timeCard.getBillableHoursForClients(clientName);

			for ( ConsultantTime billableHour : billableHours ) {
				String billableHoursSQL = "INSERT INTO billable_hours (client_id, timecard_id, date, skill, hours)"
					+ "VALUES (" + client_id + ", " 
						+ timecard_id + ", '" 
					+ billableHour.getDate() + "', '" 
					+ billableHour.getSkillType() + "', " 
					+ billableHour.getHours() + ")";
				statE.execute(billableHoursSQL);
			}
		}	
		
		
	}
	
	private void nonBillableHours( TimeCard timeCard, int timecard_id , String clientSQL, Connection conn, List<LocalDate> list)
			throws SQLException{

		Statement statQ = conn.createStatement();
		Statement statE = conn.createStatement();
		ResultSet rsQ;

		rsQ = statQ.executeQuery(clientSQL);
		while ( rsQ.next() ) {
			List <ConsultantTime> nonBillableHours = 
					timeCard.getConsultingHours().stream().filter(
							e-> e.account.isBillable()==false).filter(e->!list.contains(e.getDate())).collect(Collectors.toList());
			
			for ( ConsultantTime nonBillableHour : nonBillableHours ) {
				
				String nonBillableHoursSQL = "INSERT INTO non_billable_hours (account_name, timecard_id, date, hours)"
						+ "VALUES ('" + ((NonBillableAccount)nonBillableHour.getAccount()).name() + "', "
						+ timecard_id + ", '"
						+ nonBillableHour.getDate() + "', " 
						+ nonBillableHour.getHours() + ")";
				statE.execute(nonBillableHoursSQL);
			}

		
		}
	}
	
	/**
	 * Gets all of the clients in the database.
	 * @return a list of clients.
	 * @throws SQLException - if any database operation fails.
	 */
	public List<ClientAccount> getClients() throws SQLException {
		try ( Connection conn = DriverManager.getConnection(this.dbUrl,this.username,this.password) ) {
			Statement stat = conn.createStatement();
			ResultSet rs;
			
			List<ClientAccount> clients = new ArrayList<>();
			String sql = "SELECT name, street," 
					+ " city, state, postal_code,contact_last_name," 
					+ " contact_first_name, contact_middle_name " 
					+ "FROM clients";

			rs = stat.executeQuery(sql);
			
			while( rs.next() ) {
				String name = rs.getString(1);
				String street = rs.getString(2);
				String city = rs.getString(3);
				StateCode state = StateCode.valueOf(rs.getString(4));
				String zip = rs.getString(5);
				Address address = new Address( street, city, state, zip );
				Name consultantName = new Name (rs.getString(6), rs.getString(7), rs.getString(8) );
				clients.add(new ClientAccount(name, consultantName, address ) );
			}
			
			return clients;	
		}
	}
	
	/**
	 * Get all consultants from the database.
	 * @return - a list of consultant.
	 * @throws SQLException - if any database operation fails.
	 */
	public List<Consultant> getConsultant() throws SQLException {
		try ( Connection conn = DriverManager.getConnection(this.dbUrl,this.username,this.password) ) {
			Statement stat = conn.createStatement();
			ResultSet rs;
			
			List<Consultant> consultants = new ArrayList<>();
			String sql = "SELECT last_name, first_name, middle_name FROM consultants";
			
			rs = stat.executeQuery(sql);
			while (rs.next() ) {
				String lastName = rs.getString(1);
				String firstName = rs.getString(2);
				String middleName = rs.getString(3);
				consultants.add(new Consultant(new Name(lastName, firstName, middleName)) );
			}
			return consultants;
		}
	}
	
	/**
	 * Get clients monthly invoice.
	 * @param client - the client to get invoice for.
	 * @param month - the month of the invoice to get.
	 * @param year - the year of the invoice to get.
	 * @return the clients invoice for the month.
	 * @throws SQLException - if any database operation fails.
	 */
	public Invoice getInvoice(ClientAccount client, java.time.Month month, int year ) throws SQLException {
		try ( Connection conn = DriverManager.getConnection(this.dbUrl,this.username,this.password) ) {
			Statement statQ = conn.createStatement();
			ResultSet rsQ;
			String sql;
			int client_id = 0;
			int timecard_id = 0;
			int consultant_id = 0;
			Consultant consultant = null;
			Skill skill = null;
			int hours = 0;
			Invoice invoice = new Invoice(client, month, year);
			
			
			sql = "SELECT id FROM clients WHERE name= '" + client.getName() + "'";
			rsQ = statQ.executeQuery(sql);
			while ( rsQ.next() ) {
				client_id = rsQ.getInt(1);
			}
			
			String sqlBillable = "SELECT timecard_id, date, skill, hours FROM billable_hours WHERE client_id = " + client_id;
			Statement statBillable = conn.createStatement();
			ResultSet rsBillable = statBillable.executeQuery(sqlBillable);
			while ( rsBillable.next() ) {
				timecard_id = rsBillable.getInt(1);
				
				
				//get consultant id
				sql = "SELECT consultant_id FROM timecards WHERE id = " + timecard_id;
				rsQ =  statQ.executeQuery(sql);
				while ( rsQ.next() ) {
					consultant_id = rsQ.getInt(1);
				}
				
				//consultant
				sql = "SELECT last_name, first_name, middle_name FROM consultants WHERE id = " + consultant_id;
				rsQ = statQ.executeQuery(sql);
				while (rsQ.next() ) {
					String lastName = rsQ.getString(1);
					String firstName = rsQ.getString(2);
					String middleName = rsQ.getString(3);
					consultant = new Consultant(new Name(lastName, firstName, middleName));
				}

				String dateString = rsBillable.getString(2);
				LocalDate dateLine = LocalDate.parse(dateString);
				skill = Skill.valueOf((rsBillable.getString(3)));
				hours = rsBillable.getInt(4);
				InvoiceLineItem lineItem = new InvoiceLineItem(dateLine, consultant, skill, hours);
				DateRange range = new DateRange(month,year);	
				if ( range.isInRange(dateLine) )
					invoice.addLineItem(lineItem);
			}

			return invoice;
		}
	}
}
