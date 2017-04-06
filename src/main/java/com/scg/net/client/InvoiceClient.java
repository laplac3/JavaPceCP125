package com.scg.net.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.cmd.AddClientCommand;
import com.scg.net.cmd.AddConsultantCommand;
import com.scg.net.cmd.AddTimeCardCommand;
import com.scg.net.cmd.Command;
import com.scg.net.cmd.CreateInvoicesCommand;
import com.scg.net.cmd.DisconnectCommand;
import com.scg.net.cmd.ShutdownCommand;
import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;

/**
 * The client of the InvoiceServer.
 *
 * @author Russ Moul and Neil Nevitt
 */
public final class InvoiceClient extends Thread implements Runnable {
    /** This class' logger. */
    private static final Logger logger =
                         LoggerFactory.getLogger(InvoiceClient.class);

    /** The invoice month. */
    private static final Month INVOICE_MONTH = Month.MARCH;

    /** The invoice year. */
    private static final int INVOICE_YEAR = 2006;

   /** The host of the server. */
    private final String host;

    /** The port of the server. */
    private final int port;

    /** The list of time cards. */
    private final List<TimeCard> timeCardList;

    /**
     * Construct an InvoiceClient with a host and port for the server.
     *
     * @param host the host for the server.
     * @param port the port for the server.
     * @param timeCardList the list of timeCards to send to the server
     */
    public InvoiceClient(final String host, final int port, final List<TimeCard> timeCardList) {
        this.host = host;
        this.port = port;
        this.timeCardList = timeCardList;
    }

    /**
     * Runs this InvoiceClient, sending clients, consultants, and time cards to
     * the server, then sending the command to create invoices for a specified
     * month.
     */
    public void run() {
        ObjectOutputStream out = null;
        try (Socket server = new Socket(host, port);) {
            System.out.println(String.format("Connected to server at: %s/%s:%d",
                    server.getInetAddress().getHostName(),
                    server.getInetAddress().getHostAddress(), server.getPort()));
            // We don't expect to get any input so shut it down.
            server.shutdownInput();
            out = new ObjectOutputStream(server.getOutputStream());
            sendClients(out);
            sendConsultants(out);
            // make sure we can handle unknown commands
            out.writeObject("NOT_A_COMMAND");
            sendTimeCards(out);
            createInvoices(out, INVOICE_MONTH, INVOICE_YEAR);
            sendDisconnect(out, server);
     //       server.shutdownOutput();
        } catch (final IOException ex) {
            logger.error("Unable to connect to server.", ex);
        }
    }

    /**
     * Send the clients to the server.
     *
     * @param out the output stream connecting this client to the server.
     */
    public void sendClients(final ObjectOutputStream out) {
        AddClientCommand command = null;

        // Send new accounts
        ClientAccount client = new ClientAccount("Gotbucks Technologies",
                               new Name("Gotbucks", "Horatio", "$"),
                               new Address("1040 Yellow Brick Road", "Golden", StateCode.NY, "91234"));
        command = new AddClientCommand(client);
        sendCommand(out, command);

        client = new ClientAccount("Nanosoft",
                 new Name("Bridges", "Betty", "S."),
                 new Address("1 Fiber Lane", "Brightville", StateCode.WA, "98234"));
        command = new AddClientCommand(client);
        sendCommand(out, command);
    }

    /**
     * Send the consultants to the server.
     *
     * @param out the output stream connecting this client to the server.
     */
    public void sendConsultants(final ObjectOutputStream out) {
        AddConsultantCommand command = null;

        // Send new consultants
        command = new AddConsultantCommand(new Consultant(
                  new Name("Jones", "FooBar", "Q.")));
        sendCommand(out, command);

        command = new AddConsultantCommand(new Consultant(
                new Name("Bug", "Don", "D.")));
        sendCommand(out, command);
    }

    /**
     * Send the time cards to the server.
     *
     * @param out the output stream connecting this client to the server.
     */
    public void sendTimeCards(final ObjectOutputStream out) {
        AddTimeCardCommand command = null;
        for (final TimeCard timeCard : timeCardList) {
            command = new AddTimeCardCommand(timeCard);
            sendCommand(out, command);
        }
    }

    /**
     * Send the disconnect command to the server.
     *
     * @param out the output stream connecting this client to the server.
     * @param server the connection to be closed after sending disconnect
     */
    public void sendDisconnect(final ObjectOutputStream out, final Socket server) {
        final DisconnectCommand command = new DisconnectCommand();
        sendCommand(out, command);
        try {
            server.close();
        } catch (IOException e) {
            logger.warn("Error closing socket.", e);
        }
    }

    /**
     * Send the command to the server to create invoices for the specified
     * month.
     *
     * @param out the output stream connecting this client to the server.
     * @param month the month to create invoice for
     * @param year the year to create invoice for
     */
    public void createInvoices(final ObjectOutputStream out, final Month month, final int year) {
        LocalDate date = LocalDate.of(year, month, 1);
        final CreateInvoicesCommand command = new CreateInvoicesCommand(date);
        sendCommand(out, command);
    }

    /**
     * Send the quit command to the server.
     * 
     * @param host the host for the server.
     * @param port the port for the server.
     *
     */
    public static void sendShutdown(final String host, final int port) {
        try (Socket server = new Socket(host, port);) {
            System.out.println(String.format("Quit connected to server at: %s/%s:%d",
                    server.getInetAddress().getHostName(),
                    server.getInetAddress().getHostAddress(), server.getPort()));
            ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
            server.shutdownInput();
            final ShutdownCommand command = new ShutdownCommand();
            sendCommand(out, command);
            server.close();
        } catch (final IOException ex) {
            logger.error("Connect and send quit command.", ex);
        }
    }

    /**
     * Send a command to the server.
     *
     * @param out the output stream connecting this client to the server
     * @param command the command to send
     */
    private static void sendCommand(final ObjectOutputStream out, final Command<?> command) {
        try {
            out.writeObject(command);
            out.flush();
        } catch (final IOException ex) {
            logger.error("Unable to write command.", ex);
        }
    }
}
