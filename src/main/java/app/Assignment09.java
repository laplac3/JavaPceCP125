package app;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.client.InvoiceClient;
import com.scg.util.ListFactory;

/**
 * The client application for Assignment08.
 *
 * @author Neil Nevitt
 */
public final class Assignment09 {
    /** Localhost. */
    private static final String LOCALHOST = "127.0.0.1";
    /**
     * Prevent instantiation.
     */
    private Assignment09() {
    }

    /**
     * Instantiates an InvoiceClient, provides it with a set of timecards to
     * server the server and starts it running.
     *
     * @param args Command line parameters, not used
     * @throws Exception if anything goes awry
     */
    public static void main(final String[] args) throws Exception {
        final List<ClientAccount> accounts = new ArrayList<ClientAccount>();
        final List<Consultant> consultants = new ArrayList<Consultant>();
        final List<TimeCard> timeCards = new ArrayList<TimeCard>();
        ListFactory.populateLists(accounts, consultants, timeCards);

        final List<TimeCard> immutableTimeCard = Collections.unmodifiableList(timeCards);
        final InvoiceClient netClient1 = new InvoiceClient(LOCALHOST, Assignment09Server.DEFAULT_PORT, immutableTimeCard); 
        final InvoiceClient netClient2 = new InvoiceClient(LOCALHOST, Assignment09Server.DEFAULT_PORT, immutableTimeCard); 
        final InvoiceClient netClient3 = new InvoiceClient(LOCALHOST, Assignment09Server.DEFAULT_PORT, immutableTimeCard); 
        final InvoiceClient netClient4 = new InvoiceClient(LOCALHOST, Assignment09Server.DEFAULT_PORT, immutableTimeCard); //create four different invoice clients
        netClient1.start();
        netClient2.start();
        netClient3.start();
        netClient4.start();

        netClient1.join();
        netClient2.join();
        netClient3.join();
        netClient4.join();
        
        // Sent quit command
        InvoiceClient.sendShutdown(LOCALHOST, Assignment09Server.DEFAULT_PORT);
    }

}
