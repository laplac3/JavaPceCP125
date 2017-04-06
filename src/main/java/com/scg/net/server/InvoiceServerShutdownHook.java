package com.scg.net.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;

/**
 * @author Neil Nevitt.
 * ShutdownHook for the InvoiceServer.
 */
public class InvoiceServerShutdownHook extends Thread {

	private static final Logger log = LoggerFactory.getLogger(InvoiceServerShutdownHook.class);
	
	private static final int SHUTDOWN_DELAY_SECONDS = 5;
	
	private static final int ONE_SECOND = 1000;
	
	private List<ClientAccount> clientList;
	private List<Consultant> consultantList;
	private String outputDirectoryName;
	
	/**
	 * Construct an InvoiceServerShutDownHook.
	 * @param clientList - the ClientList to serialize.
	 * @param consultantList - the ConsultantList to serialize.
	 * @param outputDirectoryName - name of directory to write output to
	 */
	public InvoiceServerShutdownHook(List<ClientAccount> clientList,
			List<Consultant> consultantList,
			String outputDirectoryName) {
		this.clientList = clientList;
		this.consultantList = consultantList;
		this.outputDirectoryName = outputDirectoryName;
		
	}
	
	public void run() {
		File serverDir = new File(outputDirectoryName);
		if( serverDir.exists() || serverDir.mkdir() ) {
			File clientFile = new File(serverDir, "clientList.txt");
			File consultantFile = new File(serverDir, "consultantList.txt");
			
			try ( PrintStream clientOut = new PrintStream(new FileOutputStream( clientFile ));
					PrintStream consultantOut = new PrintStream(new FileOutputStream( consultantFile ));) {
				System.err.println("Saving list");
				synchronized(clientList ) {
					for ( final ClientAccount client : clientList ){
						clientOut.println(client);
					}
				}
				synchronized(consultantList) {
					for (Consultant consultant : consultantList ) {
						consultantOut.println(consultant);
					}
				}
				
			} catch (FileNotFoundException fnfe ) {
				log.error("Unable to make the output directory", fnfe);
			}
			System.err.println("STARTING TO SHUTDOWN");
			System.err.println(String.format("Shutting down after %d seconds", SHUTDOWN_DELAY_SECONDS ));
			for (int i = SHUTDOWN_DELAY_SECONDS; i>0; i--) {
				try {
					Thread.sleep(ONE_SECOND);
					System.err.println(String.format("Shutdown in %d seconds", i));
				} catch (final InterruptedException e ) {
					log.info("Shuttdown delay interrupted.");
				}
				System.err.println("SHUTDOWN!");
			}
		}
	}

}
