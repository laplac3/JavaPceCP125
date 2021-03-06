package app;

import java.util.ArrayList;
import java.util.List;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.server.InvoiceServer;
import com.scg.util.ListFactory;
 
public class Assignment08Server {
	
	private final static int port = 10888;
	private final static String SERVER_DIRECTORY_PROPERTY = "target/server";
	public static void main(String[] args) {
        final List<ClientAccount> accounts = new ArrayList<ClientAccount>();
        final List<Consultant> consultants = new ArrayList<Consultant>();
        final List<TimeCard> timeCards = new ArrayList<TimeCard>();
        ListFactory.populateLists(accounts, consultants, timeCards);

        InvoiceServer serv = new InvoiceServer( port, accounts, consultants, SERVER_DIRECTORY_PROPERTY );
        serv.run();
       
        
	}
}
