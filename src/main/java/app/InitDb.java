package app;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.persistent.DbServer;
import com.scg.util.ListFactory;
import org.slf4j.LoggerFactory;
/**
 * @author Neil Nevitt
 * Initialize and or populate the database.
 */
public class InitDb {
		
	private static final String DATABASE_PROPERTY = "jdbc:derby://localhost:1527/memory:scgDb";
	private static final String USERNAME_PROPERTY = "student";
	private static final String PASSWORD_PROPERTY = "student";
	
	/**
	 * Entry point.
	 * @param args - not used.
	 * @throws Exception - If any thing is out of whack. 
	 */
	public static void main(String[] args) throws Exception {
        final List<ClientAccount> accounts = new ArrayList<ClientAccount>();
        final List<Consultant> consultants = new ArrayList<Consultant>();
        final List<TimeCard> timeCards = new ArrayList<TimeCard>();
        ListFactory.populateLists(accounts, consultants, timeCards);
        
        DbServer serv = new DbServer(DATABASE_PROPERTY,USERNAME_PROPERTY,PASSWORD_PROPERTY);
        
        for ( final ClientAccount client : accounts )
        	serv.addClient(client);
        for ( final Consultant consultant : consultants )
        	serv.addConsultant(consultant);
        for ( final TimeCard timeCard : timeCards ) {
        	serv.addTimeCard(timeCard);
        }
	}
}
