package app;

import java.util.ArrayList;
import java.util.List;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.server.InvoiceServer;
import com.scg.util.ListFactory;

/**
 * The server application for assignment 08.
 *
 * @author Neil Nevitt
 */
public final class Assignment09Server {
    /** The port for the server to listen on. */
    public static final int DEFAULT_PORT = 10888;

    /**
     * Prevent instantiation.
     */
    private Assignment09Server() {
    }

    /**
     * Instantiates an InvoiceServer,initializes its account and consultant lists
     * and starts it.
     *
     * @param args Command line parameters.
     *
     * @throws Exception if the server raises any exceptions
     */
    public static void main(final String[] args) throws Exception {
        final List<ClientAccount> accounts = new ArrayList<ClientAccount>();
        final List<Consultant> consultants = new ArrayList<Consultant>();
        final List<TimeCard> timeCards = new ArrayList<TimeCard>();
        ListFactory.populateLists(accounts, consultants, timeCards);

        final InvoiceServer server = new InvoiceServer(DEFAULT_PORT,
                                                       accounts, consultants,
                                                       "target/server");
        server.run();
    }
}