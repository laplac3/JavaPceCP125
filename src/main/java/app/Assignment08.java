package app;

import java.io.IOException;
import java.io.ObjectOutputStream;
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
import com.scg.net.cmd.CreateInvoiceCommand;
import com.scg.util.ListFactory;
import com.scg.net.client.InvoiceClient;

public class Assignment08 {

	
	private static final String localhost = "127.0.0.1";
	private static final int port = 10880;
	public static void main(String[] args) throws UnknownHostException, IOException {
        final List<ClientAccount> accounts = new ArrayList<ClientAccount>();
        final List<Consultant> consultants = new ArrayList<Consultant>();
        final List<TimeCard> timeCards = new ArrayList<TimeCard>();
        ListFactory.populateLists(accounts, consultants, timeCards);
        
        Socket socket = new Socket(localhost,port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        LocalDate date = LocalDate.now();
        InvoiceClient client = new InvoiceClient(localhost,port,timeCards);
        client.sendConsultant(out);
        client.sendTimeCards(out);
 //       client.sendClients(out);
 //       client.createInvoice(out, Month.MARCH, 2006);
 //       client.sendDisconnect(out,socket);
        client.sendShutdown(localhost, port);


	}

}
