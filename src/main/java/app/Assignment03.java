package app;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.scg.util.ListFactory;

/**
 * Assignment 03 application.
 */
public final class Assignment03 {
    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger(Assignment03.class);

    /** The invoice month. */
    private static final Month INVOICE_MONTH = Month.MARCH;

    /** The test year. */
    private static final int INVOICE_YEAR = 2006;

    /**
     * Prevent instantiation.
     */
    private Assignment03() {
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
        final List<Invoice> invoices = new ArrayList<Invoice>(accounts.size());
        for (final ClientAccount account : accounts) {
            final Invoice invoice = new Invoice(account, INVOICE_MONTH, INVOICE_YEAR);
            invoices.add(invoice);
            for (final TimeCard currentTimeCard : timeCards) {
                invoice.extractLineItems(currentTimeCard);
            }
        }

        return invoices;
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
     * Confirm the invoice totals.
     * 
     * @param exHours the expected hours
     * @param exCharges the expected charges
     * @param invoice the invoice
     */
    private static void confirmTotals(final int exHours, final int exCharges,
                                      final Invoice invoice) {
        final int invHours = invoice.getTotalHours();
        final int invCharges = invoice.getTotalCharges();
        if (invHours != exHours) {
            log.error(String.format("Invoice hours for %s are incorrect, expected %d but was %d",
                                    invoice.getClientAccount().getName(),
                                    exHours, invHours));
        }
        if (invCharges != exCharges) {
            log.error(String.format("Invoice charges for %s are incorrect, expected %d but was %d",
                                    invoice.getClientAccount().getName(),
                                    exCharges, invCharges));
        }
    }

    /**
     * The application method.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        // Create lists to be populated by factory
        final List<ClientAccount> accounts = new ArrayList<ClientAccount>();
        final List<Consultant> consultants = new ArrayList<Consultant>();
        final List<TimeCard> timeCards = new ArrayList<TimeCard>();
        ListFactory.populateLists(accounts, consultants, timeCards);
        // Print them
        ListFactory.printTimeCards(timeCards, System.out);

        // Create the Invoices
        final List<Invoice> invoices = createInvoices(accounts, timeCards);
 
        // Print them
        System.out.println();
        System.out.println("==================================================================================");
        System.out.println("=============================== I N V O I C E S ==================================");
        System.out.println("==================================================================================");
        System.out.println();
        Invoice invoice = invoices.get(0);
        confirmTotals(16, 2400, invoice);
        System.out.println(invoice.toReportString());
        invoice = invoices.get(1);
        confirmTotals(108, 19400, invoice);
        System.out.println(invoice.toReportString());

        // Now print it to a file
        PrintStream writer;
        try {
            writer = new PrintStream(new FileOutputStream("invoices.txt"));
            printInvoices(invoices, writer);
        } catch (final IOException ex) {
            log.error("Unable to print invoice.", ex);
        }
    }
}
