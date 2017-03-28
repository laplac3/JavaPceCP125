package com.scg.net.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.Invoice;
import com.scg.domain.TimeCard;
import com.scg.net.cmd.AddClientCommand;
import com.scg.net.cmd.AddConsultantCommand;
import com.scg.net.cmd.AddTimeCardCommand;
import com.scg.net.cmd.CreateInvoiceCommand;
import com.scg.net.cmd.DisconnectCommand;

import com.scg.net.cmd.ShutdownCommand;

/**
 * @author Neil Nevitt
 * The command processor for the invoice server. Implements the receiver role in the Command design pattern.
 */
public final class CommandProcessor {
	private static final Logger log = LoggerFactory.getLogger("CommandProcessor");
	/**
	 * The Socket connecting the server to the client.
	 */
	private Socket connection;
	/**
	 * The ClientList to add Clients to.
	 */
	private List<ClientAccount> clientList;
	/**
	 * The ConsultantList to add Consultants to.
	 */
	private List<Consultant> consultantList;
	
	private List<TimeCard> timeCardList = new ArrayList<>();
	/**
	 * The server that created this command processor.
	 */
	private InvoiceServer server;
	
	private String outputDirectoryName;
	
	// create local time card lis
	
	/**
	 * Construct a CommandProcessor.
	 * @param connection - the Socket connecting the server to the client.
	 * @param clientList - the ClientList to add Clients to.
	 * @param consultantList - the ConsultantList to add Consultants to.
	 * @param server - the server that created this command processor.
	 */
	public CommandProcessor(Socket connection, List<ClientAccount> clientList, List<Consultant> consultantList,
			InvoiceServer server) {
		super();
		this.connection = connection;
		this.clientList = clientList;
		this.consultantList = consultantList;
		this.server = server;
	}
	
	public void setOutputDirectoryName(String outputDirectoryName) {
		this.outputDirectoryName = outputDirectoryName;
	}

	/**
	 * Execute an AddClientCommand.
	 * @param command - the command to execute.
	 */
	public void execute(AddClientCommand command) {
		log.info( String.format("Added %s to the client list.", command.getTarget().getName()) );
		clientList.add(command.getTarget());
		
	}

	/**
	 * Execute and AddConsultantCommand.
	 * @param command - the command to execute.
	 */
	public void execute(AddConsultantCommand command) {
		log.info(String.format("Added %s to the consultant list." , command.getTarget().toString() ));
		consultantList.add(command.getTarget());
		
	}

	/**
	 * Execute and AddTimeCardCommand.
	 * @param command - the command to execute.
	 */
	public void execute(AddTimeCardCommand command) {
		log.info(String.format("Added a timecard to the timeCardlist for %s", command.getTarget().getConsultant()));
		timeCardList.add(command.getTarget());
	}

	/**
	 * Execute a CreateInvoicesCommand.
	 * @param command - the command to execute.
	 */
	public void execute(CreateInvoiceCommand command) {
		Invoice invoice = null;
		LocalDate date = command.getTarget();
		final DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMMyyyy");
		final String monthString = format.format(date);
		for ( ClientAccount client : clientList ) {
			invoice = new Invoice(client, date.getMonth(), date.getYear());
			for ( TimeCard timeCard : timeCardList ) {
				invoice.extractLineItems(timeCard);
			}
			if (invoice.getTotalHours() > 0 ) {
				final File servDir = new File(outputDirectoryName);
				if ( !servDir.exists() ) {
					if ( !servDir.mkdirs()) {
						log.error("Unable to create directory " + servDir.getName());
						return;
					}
				}
				final String outFileName = String.format("%s%sInvoice.txt",
						client.getName().replaceAll(" ", ""),
						monthString);
				final File outFile =  new File(outputDirectoryName, outFileName);
				try ( PrintStream printOut = new PrintStream(new FileOutputStream(outFile)); ) {
					printOut.println(invoice.toReportString());
				} catch (FileNotFoundException e) {
					log.error("Cannot write to file", e);
				}
			}
		}
		
	}

	/**
	 * Execute a DisconnectCommand.
	 * @param command - the input DisconnectCommand.
	 */
	public void execute(DisconnectCommand command) {
		command.execute();
	}

	/**
	 * Execute a ShutdownCommand. Closes any current connections,
	 * stops listening for connections and then terminates the server, without calling System.exit.
	 * @param command - the input ShutdownCommand.
	 */
	public void execute(ShutdownCommand command) {	
		command.execute();
		
	}



}
