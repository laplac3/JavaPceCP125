package com.scg.net.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.net.cmd.Command;

/**
 * @author Neil Nevitt
 *The server for creation of account invoices based on time cards sent from the client.
 */
public final class InvoiceServer implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger("InvoiceServer");
	/**
	 * The port for this server to listen on
	 */
	private final int port;
	/**
	 * The list of clients.
	 */
	private List<ClientAccount> clientList;
	/**
	 * The list of consultants.
	 */
	private List<Consultant> consultantList;
	/**
	 * The directory to be used for files output by commands
	 */
	private final String outputDirectoryName;
	private ServerSocket serverSocket =null;
	/**
	 * @param port - The port for this server to listen on.
	 * @param clientList - the initial list of clients
	 * @param consultantList - the initial list of consultants
	 * @param outputDirectoryName - the directory to be used for files output by commands
	 */
	public InvoiceServer(int port, List<ClientAccount> clientList, List<Consultant> consultantList,
			String outputDirectoryName) {
		super();
		this.port = port;
		this.clientList = clientList;
		this.consultantList = consultantList;
		this.outputDirectoryName = outputDirectoryName;

	} 
	
	/**
	 * Run this server, establishing connections, receiving commands, and sending them to the CommandProcesser.
	 */
	public void run() {

		
		try (ServerSocket serverSocket = new ServerSocket(this.port)) {
			this.serverSocket = serverSocket;
			log.info("New server Socket " 
			+ serverSocket.getInetAddress().getHostName() 
			+ ":" + serverSocket.getLocalPort() );
			while (!serverSocket.isClosed() ) {
				log.info("InvoiceServer waiting for connection on port" + port);
				try ( Socket client= serverSocket.accept() ) {
					process(client);	
				} catch ( IOException ex ) {
					log.error("Server error " + ex);
				
			} 
			
		} 
			
		} catch (IOException e) {
			log.error("IOException" + e);
		} finally {
			if ( serverSocket != null ) {
				try {
					serverSocket.close();
					
				} catch ( IOException ioex ) {
					log.error("Error closing server socket. " + ioex);
				}
			}
		}
	}
	
	/**
	 * Shut down the server.
	 */
	void shutDown() {
		try {
			if ( this.serverSocket != null && !this.serverSocket.isClosed() ) {
				serverSocket.close();
				log.info("Serve connection has been closed");
			}
		} catch ( IOException e) {
			log.error("Could not shut down server",e);
		}
		
	}
	
	private void process(Socket clientSocket ) { 
		try {
			
			InputStream inStrm = clientSocket.getInputStream();
			ObjectInputStream inStrmObj = new ObjectInputStream(inStrm);
			final CommandProcessor cmdProc =new CommandProcessor(clientSocket, clientList, consultantList, this);
			while (true ) {
				if ( inStrmObj == null ) {
					clientSocket.close();			
				} else if ( inStrmObj instanceof Command<?>) { 
					Command<?> command = (Command<?>)inStrmObj;
					log.info(inStrmObj.toString());
					command.setReceiver(cmdProc);
					command.execute();
				} else {
					log.warn( String.format("recieved non command %s", inStrmObj.getClass().getSimpleName()));
				} 
			}
			
		} catch (IOException ex ) {
			log.error("Error " + ex);
		}
	}
}