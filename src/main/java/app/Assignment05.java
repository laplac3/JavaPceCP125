package app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.util.ListFactory;


public class Assignment05 {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileInputStream cLFis = new FileInputStream("clientList.ser");
        ObjectInputStream cLFin = new ObjectInputStream(cLFis);

        // read the clients
        @SuppressWarnings("unchecked")
		List<ClientAccount> accounts = (List<ClientAccount>) cLFin.readObject();
        cLFin.close();
        
        //print
        for ( ClientAccount a : accounts )
        	System.out.println(a.getName());
        
        FileInputStream tCFis = new FileInputStream("TimeCardList.ser");
        @SuppressWarnings("resource")
		ObjectInputStream tCFin = new ObjectInputStream(tCFis);

        // read the clients
        @SuppressWarnings("unchecked")
        List<TimeCard> timeCards = (List<TimeCard>) tCFin.readObject();
        tCFin.close();
        for ( TimeCard card : timeCards )
        	System.out.println(card);
        
//        ListFactory.printTimeCards(timeCards, System.out);
	}
}
