package com.scg.net.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.cmd.AddConsultantCommand;
import com.scg.net.cmd.AddTimeCardCommand;
import com.scg.net.cmd.Command;
import com.scg.net.cmd.CreateInvoiceCommand;
import com.scg.net.cmd.DisconnectCommand;
import com.scg.net.cmd.ShutdownCommand;

/**
 * @author Neil Nevitt
 * The client of the InvoiceServer.
 */
public final class InvoiceClient {

	private static final Logger log = LoggerFactory.getLogger("InvoiceClient");
	/**
	 * The host.
	 */
	private final String host;
	/**
	 * The port.
	 */
	private final int port;
	/**
	 * List of timeCards.
	 */
	private List<TimeCard> timeCardList;
	
	/**
	 * Constructor for the invoice client with host and port for the server.
	 * @param host - The host for the server.
	 * @param port - The port for the server.
	 * @param timeCardList - List of timecards to send to the server.
	 */
	public InvoiceClient(String host, int port, List<TimeCard> timeCardList) {
		super();
		this.host = host;
		this.port = port;
		this.timeCardList = timeCardList;
	}
	/**
	 * Runs this InvoiceClient, sending clients consultant and time cards to the server, 
	 * then sending the command to create invoice for the specific month.
	 */
	public void run() {
		ObjectOutputStream out =null;
		try ( Socket server = new Socket(host, port); ) {
			System.out.println(String.format("Connection to server at: %s%s:%d",
					server.getInetAddress().getHostName(),
					server.getInetAddress().getHostAddress(),
					server.getPort()));
			server.shutdownInput();
			out = new ObjectOutputStream(server.getOutputStream());
			sendClients(out);
			sendConsultant(out);
			out.writeObject("NOT_A_COMMAND");
			sendTimeCards(out);
			LocalDate date = LocalDate.now();
			createInvoice(out,date.getMonth(),date.getYear());
		} catch (IOException e) {
			log.warn("Could not run send on connection",e);
		}
	}
	
	/**
	 * Send the clients to the server.
	 * @param out - output stream connecting this client to the server.
	 */
	public void sendClients( ObjectOutputStream out ) {
		
		//sendCommand(out,command);
	}
	
	/**
	 * Send the consultant to the server.
	 * @param out - output stream connecting this client to the server.
	 */
	public void sendConsultant( ObjectOutputStream out ) {
		for ( TimeCard timeCard : timeCardList ) {
			Consultant consultant = timeCard.getConsultant();
			final AddConsultantCommand command = new AddConsultantCommand(consultant);
			sendCommand(out,command);
		}
	}
	
	/**
	 * Send the time cards to the server.
	 * @param out - output stream connecting this client to the server.
	 */
	public void sendTimeCards( ObjectOutputStream out ) {
		for (TimeCard timeCard : timeCardList ) {
			final AddTimeCardCommand command = new AddTimeCardCommand(timeCard);
			sendCommand(out,command);
		}
	}
	
	/**
	 * Send the Disconnect command to the server.
	 * @param out - the output stream connecting this client to the server.
	 * @param server - the connection to be closed after sending disconnect.
	 */
	public void sendDisconnect(ObjectOutputStream out, Socket server ) {
		final DisconnectCommand command = new DisconnectCommand();
		sendCommand(out,command);
		try {
			server.close();
		} catch (IOException e) {
			log.warn("Unable to close connection.",e);
		}
	}
	
	/**
	 * Send command to the server to create the invoice for a specific month.
	 * @param out - the output stream connecting this client to the server. 
	 * @param month - the month to create the invoice for.
	 * @param year - the year to create the invoice for.
	 */
	public void createInvoice(ObjectOutputStream out, java.time.Month month, int year) {
		final CreateInvoiceCommand command = new CreateInvoiceCommand(LocalDate.of(year, month, 1));
		sendCommand(out,command);
		
	}
	/**
	 * Send the quit command to the server.
	 * @param host - the host for the server.
	 * @param port - the port for the server.
	 */
	public void sendShutdown( String host, int port ) {
		try (Socket server =new Socket(host, port) ) {
			System.out.println(String.format("Quit connection to server at: %s%s:%d", 
					server.getInetAddress().getHostName(),
					server.getInetAddress().getHostAddress(),
					server.getPort()));
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			server.shutdownInput();
			final ShutdownCommand command = new ShutdownCommand();
			sendCommand(out,command);
		} catch (IOException e) {
			log.error("Connect and send quit command",e);
		}
	}
	
	private static void sendCommand(ObjectOutputStream out, Command<?> command) {
		try {
			out.writeObject(command);
			out.flush();
		} catch ( IOException ex ) {
			log.error("Bad command unable to execute.",ex);
		}
	}
}
