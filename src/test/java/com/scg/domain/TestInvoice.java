package com.scg.domain;

import static org.junit.Assert.*;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Before;

import org.junit.Test;

import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;

public class TestInvoice {

	private ClientAccount client1 = new ClientAccount("SomeName1", new Name("Last1","first1"), new Address("3452 1st ave NW","Seattle", StateCode.WA,"98115"));
	private java.time.Month invoiceMonth1 = Month.APRIL;
	private int invoiceYear1 = 3;
	private java.time.LocalDate startDate1 = LocalDate.of(2003, 4, 4);
	private java.time.LocalDate startDate2 = LocalDate.of(2003, 4, 5);
	private java.time.LocalDate startDate3 = LocalDate.of(2003, 4, 6);
	private java.time.LocalDate startDate4 = LocalDate.of(2003, 4, 7);
	private java.time.LocalDate startDate5 = LocalDate.of(2003, 4, 8);
	private java.time.LocalDate startDate6 = LocalDate.of(2003, 4, 9);
	private java.time.LocalDate startDate7 = LocalDate.of(2003, 4, 10);
	private int totalHours1 = 60;
	private int totalCharges1 = 14000;
	private Consultant consultant1 = new Consultant(new Name("Coder","Carl"));
	private Consultant consultant2 = new Consultant(new Name("Silverman","Dan"));
	private List<InvoiceLineItem> listOfLineItems = new ArrayList<InvoiceLineItem>() {
		{
		add(new InvoiceLineItem(startDate1, consultant1,  Skill.PROJECT_MANAGER, 10));
		add(new InvoiceLineItem(startDate2, consultant2,  Skill.SOFTWARE_ENGINEER, 10));
		add(new InvoiceLineItem(startDate3, consultant1,  Skill.PROJECT_MANAGER, 10));
		add(new InvoiceLineItem(startDate4, consultant1,  Skill.PROJECT_MANAGER, 10));
		add(new InvoiceLineItem(startDate6, consultant1,  Skill.PROJECT_MANAGER, 10));
		add(new InvoiceLineItem(startDate7, consultant1,  Skill.PROJECT_MANAGER, 10));
		}
	};
	private String businessName="The Small Consulting Group";
	private String businessStreet="1616 Index Ct.";
	private String businessCity="Renton";
	private String businessZip="98058";
	private StateCode businessState = StateCode.WA;
	
	private Invoice invoice1;
	
	@Before
	public void init() {
		invoice1 = new Invoice(client1, invoiceMonth1, invoiceYear1 );

		TimeCard card1 = new TimeCard(consultant1, startDate1);
		card1.addConsultantTime(new ConsultantTime(startDate1, client1, Skill.PROJECT_MANAGER, 10) );
		invoice1.extractLineItems(card1);
		
		TimeCard card4 = new TimeCard(consultant2, startDate2);
		card4.addConsultantTime(new ConsultantTime(startDate2, client1, Skill.SOFTWARE_ENGINEER, 10) );
		invoice1.extractLineItems(card4);
		
		TimeCard card2 = new TimeCard(consultant1, startDate1);
		card2.addConsultantTime(new ConsultantTime(startDate3, client1, Skill.PROJECT_MANAGER, 10) );
		invoice1.extractLineItems(card2);
		
		TimeCard card3 = new TimeCard(consultant1, startDate1);
		card3.addConsultantTime(new ConsultantTime(startDate4, client1, Skill.PROJECT_MANAGER, 10) );
		invoice1.extractLineItems(card3);

		TimeCard card5 = new TimeCard(consultant1, startDate1);
		card5.addConsultantTime(new ConsultantTime(startDate5, NonBillableAccount.BUSINESS_DEVELOPMENT, Skill.PROJECT_MANAGER, 10) );
		invoice1.extractLineItems(card5);

		TimeCard card6 = new TimeCard(consultant1, startDate1);
		card6.addConsultantTime(new ConsultantTime(startDate6, client1, Skill.PROJECT_MANAGER, 10) );
		invoice1.extractLineItems(card6);
		
		TimeCard card7 = new TimeCard(consultant1, startDate1);
		card7.addConsultantTime(new ConsultantTime(startDate7, client1, Skill.PROJECT_MANAGER, 10) );
		invoice1.extractLineItems(card7);
	}
	@Test
	public void test() {
	
		
		
		//Test toReportString	
		InvoiceFooter footer = new InvoiceFooter(businessName);
		LocalDate invoiceForMonth = LocalDate.of(invoiceYear1,invoiceMonth1.getValue(),1);
		LocalDate invoiceDate = LocalDate.now();
		InvoiceHeader header = new InvoiceHeader(businessName,
				new Address(businessStreet,businessCity,businessState,businessZip), client1, invoiceDate , invoiceForMonth  );

		StringBuilder builder = new StringBuilder();
		String str1 = header.toSting();
		String str3 = footer.toString();

		int i=0;
		for ( InvoiceLineItem a : listOfLineItems ){

			int mod = i % 5;
				if (i == 0 ) {
					builder = builder.append(str1)
							.append(a.toString())
							.append(System.getProperty("line.separator"));
				} else if ( mod == 0 ) {
					builder = builder.append(str3)
							.append(System.getProperty("line.separator")).append(str1)
							.append(a.toString())
							.append(System.getProperty("line.separator"));
					str3 = footer.toString();
					
						
				} else {
					builder = builder.append(a.toString()).append(System.getProperty("line.separator"));
				}
				i++;
			}
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
		String totalChargesCurrency = numberFormat.format(totalCharges1);
		totalChargesCurrency = totalChargesCurrency.substring(1);
		String totals = String.format("%n%s %2$64s %3$11s","total" ,totalHours1,totalChargesCurrency);
		builder = builder.append(totals).append(System.getProperty("line.separator")).append(str3);
		
		String expected1 =  builder.toString();
		String actual1 = invoice1.toReportString();
		assertEquals(expected1,actual1);
	}

}
