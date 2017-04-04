package com.scg.net.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;

/**
 * @author Neil Nevitt.
 * ShutdownHook for the InvoiceServer.
 */
public class InvoiceServerShutdownHook extends Thread implements Runnable {

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
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
		}
	}

}
