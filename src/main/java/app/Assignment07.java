package app;

import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.Invoice;
import com.scg.persistent.DbServer;

/**
 * @author Neil Nevitt
 * Creates a invoice from the database.
 */
public final class Assignment07 {

	private static final String DATABASE_PROPERTY = "jdbc:derby://localhost:1527/memory:scgDb";
	private static final String USERNAME_PROPERTY = "student";
	private static final String PASSWORD_PROPERTY = "student";
	
	private static final Month month = Month.MARCH;
	private static final int year = 2006;
	/**
	 * Entry point.
	 * @param args - not used.
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		DbServer serv = new DbServer(DATABASE_PROPERTY,USERNAME_PROPERTY,PASSWORD_PROPERTY);
		List<ClientAccount> clients = serv.getClients();
		List<Consultant> consultants = serv.getConsultant();
		List<Invoice> invoices = new ArrayList<>();
		for ( ClientAccount client : clients)
			System.out.println(client.getName());
		for ( Consultant consultant : consultants )
			System.out.println(consultant.getName());
		for ( ClientAccount client : clients) {
			 invoices.add( serv.getInvoice(client, month, year));
		}
		for ( Invoice invoice : invoices )
			System.out.println(invoice);
	}

}
