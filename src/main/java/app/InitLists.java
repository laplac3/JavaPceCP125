package app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.util.ListFactory;

public class InitLists {
	public static void main(String[] args) throws IOException {
        final List<ClientAccount> accounts = new ArrayList<ClientAccount>();
        final List<Consultant> consultants = new ArrayList<Consultant>();
        final List<TimeCard> timeCards = new ArrayList<TimeCard>();
        ListFactory.populateLists(accounts, consultants, timeCards);
        
        for (Consultant c : consultants )
        	System.out.print(c.getName()+ "\n");
        
        for (TimeCard card : timeCards )
        	System.out.println(card.toReportString());
        
        FileOutputStream cLF = new FileOutputStream("ClientList.ser");
        ObjectOutputStream cLFout = new ObjectOutputStream(cLF);

        // write the client objects to file
        cLFout.writeObject(accounts);
        cLFout.close();
        
        FileOutputStream tCF = new FileOutputStream("TimeCardList.ser");
        ObjectOutputStream tCFout = new ObjectOutputStream(tCF);

        // write the client objects to file
        tCFout.writeObject(timeCards);
        tCFout.close();
	}

}
