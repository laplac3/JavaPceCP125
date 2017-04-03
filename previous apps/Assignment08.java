package app;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.cmd.AddClientCommand;
import com.scg.net.cmd.AddConsultantCommand;
import com.scg.net.cmd.AddTimeCardCommand;
import com.scg.net.cmd.Command;
import com.scg.net.cmd.CreateInvoiceCommand;
import com.scg.net.cmd.DisconnectCommand;
import com.scg.net.cmd.ShutdownCommand;
import com.scg.util.ListFactory;
import com.scg.util.Name;
import com.scg.net.client.InvoiceClient;

public class Assignment08 { 

	
	private static final String localhost = "127.0.0.1";
	private static final int port = 10888;
	public static void main(String[] args) throws UnknownHostException, IOException {
        final List<ClientAccount> accounts = new ArrayList<ClientAccount>();
        final List<Consultant> consultants = new ArrayList<Consultant>();
        final List<TimeCard> timeCards = new ArrayList<TimeCard>();
        ListFactory.populateLists(accounts, consultants, timeCards);
        
        Socket socket = new Socket(localhost,port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        InvoiceClient client = new InvoiceClient(localhost,port,timeCards);


        Command<?> command = null;

        Name consultantName = new Name ("Nevitt","Neil");
        Name name = new Name ("Stooge","Larry");
        String account = "Stooges";
        Consultant consultant = new Consultant(consultantName);
        ClientAccount clientAcount = new ClientAccount(account,name);
        LocalDate date = LocalDate.of(2006, Month.MARCH, 6);
 
        //add consultants to stream
       // out.writeObject(consultant);
        for ( Consultant con : consultants ) { 
        	command = new AddConsultantCommand(con);
        	out.writeObject(command);
        	out.flush();
        }
        	

    	
        
        //add clients to stream
        //out.writeObject(clientAcount);
        for ( ClientAccount clientA : accounts ) {
        	command = new AddClientCommand(clientA);
        	out.writeObject(command);
        	out.flush();
        }

        
    	command = new AddConsultantCommand(consultant);
    	out.writeObject(command);
    	out.flush();
    	
    	command = new AddClientCommand(clientAcount);
    	out.writeObject(command);
    	out.flush();
        //add time cards to stream 
        for (TimeCard timecard : timeCards ) {
        	command = new AddTimeCardCommand(timecard);
        	out.writeObject(command);
        	out.flush();
        }
        

        //add date to stream
        command = new CreateInvoiceCommand(date);
        out.writeObject(command);
        out.flush();
        

        command = new DisconnectCommand();
        out.writeObject(command);
        out.flush();
        
        command = new ShutdownCommand();
        out.writeObject(command);
        out.flush();

	}

}
