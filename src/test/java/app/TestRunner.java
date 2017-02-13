package app;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.scg.domain.TestClientAccount;
import com.scg.domain.TestConsultant;
import com.scg.domain.TestConsultantTime;
import com.scg.domain.TestInvoice;
import com.scg.domain.TestInvoiceFooter;
import com.scg.domain.TestInvoiceHeader;
import com.scg.domain.TestInvoiceLineItem;
import com.scg.domain.TestTimeCard;
import com.scg.util.NameTest;
import com.scg.util.TestAddress;




public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(TestClientAccount.class,
    		  TestConsultant.class,
    		  TestConsultantTime.class,
    		  TestInvoice.class,
    		  TestInvoiceFooter.class,
    		  TestInvoiceHeader.class,
    		  TestInvoiceLineItem.class,
    		  TestTimeCard.class,
    		  NameTest.class,
    		  TestAddress.class);

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println(result.wasSuccessful());
   }
} 