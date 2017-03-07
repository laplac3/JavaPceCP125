package app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.Invoice;
import com.scg.domain.TimeCard;
import com.scg.util.DateRange;
import com.scg.util.ListFactory;
import com.scg.util.TimeCardListUtil;


public class Assignment05 {
	
    /** The invoice month. */
    private static final Month INVOICE_MONTH = Month.MARCH;

    /** The test year. */
    private static final int INVOICE_YEAR = 2006;

    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger("Assignment05");

    /**
     * Prevent instantiation.
     */
    private Assignment05() {
    }
    
    /**
     * Print the invoice to a PrintStream.
     *
     * @param invoices the invoices to print
     * @param out The output stream; can be System.out or a text file.
     */
    private static void printInvoices(final List<Invoice> invoices, final PrintStream out) {
        for (final Invoice invoice : invoices) {
            out.println(invoice.toReportString());
        }
    }
    /**
     * Create invoices for the clients from the timecards.
     *
     * @param accounts the accounts to create the invoices for
     * @param timeCards the time cards to create the invoices from
     *
     * @return the created invoices
     */
    private static List<Invoice> createInvoices(final List<ClientAccount> accounts,
                                            final List<TimeCard> timeCards) {
        final List<Invoice> invoices = new ArrayList<Invoice>();

        final List<TimeCard> timeCardList = TimeCardListUtil
                .getTimeCardsForDateRange(timeCards, new DateRange(INVOICE_MONTH, INVOICE_YEAR));
        for (final ClientAccount account : accounts) {
            final Invoice invoice = new Invoice(account, INVOICE_MONTH, INVOICE_YEAR);
            invoices.add(invoice);
            for (final TimeCard currentTimeCard : timeCardList) {
                invoice.extractLineItems(currentTimeCard);
            }
        }

        return invoices;
    }
    /**
     * The application method.
     *
     * @param args Command line arguments.
     */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileInputStream cLFis = new FileInputStream("clientList.ser");
        ObjectInputStream cLFin = new ObjectInputStream(cLFis);

        // read the clients
        @SuppressWarnings("unchecked")
		List<ClientAccount> accounts = (List<ClientAccount>) cLFin.readObject();
        cLFin.close();
        
        //print
        for ( ClientAccount a : accounts )
        	System.out.println(a.getName());
        
        FileInputStream tCFis = new FileInputStream("TimeCardList.ser");
        @SuppressWarnings("resource")
		ObjectInputStream tCFin = new ObjectInputStream(tCFis);

         
        // read the clients
        @SuppressWarnings("unchecked")
        List<TimeCard> timeCards = (List<TimeCard>) tCFin.readObject();
        tCFin.close();
        
        for ( TimeCard card : timeCards )
        	System.out.println(card.toReportString());
        
        final List<Invoice> invoices = createInvoices(accounts, timeCards);
        // Print them
        System.out.println();
        System.out.println("==================================================================================");
        System.out.println("=============================== I N V O I C E S ==================================");
        System.out.println("==================================================================================");
        System.out.println();
        printInvoices(invoices, System.out);
        // Now print it to a file
        PrintStream writer;
        try {
            writer = new PrintStream(new FileOutputStream("invoice.txt"));
            printInvoices(invoices, writer);
        } catch (final IOException ex) {
             log.error("Unable to print invoice.", ex);
        }
	}
 
}
