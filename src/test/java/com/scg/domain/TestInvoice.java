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

	private ClientAccount client1 = new ClientAccount("SomeName1", new Name("Last1","first1"));
	private java.time.Month invoiceMonth1 = Month.APRIL;
	private int invoiceYear1 = 3;
	private java.time.LocalDate startDate1 = LocalDate.of(2003, 5, 4);
	private java.time.LocalDate startDate2 = LocalDate.of(2003, 5, 5);
	private java.time.LocalDate startDate3 = LocalDate.of(2003, 5, 6);
	private java.time.LocalDate startDate4 = LocalDate.of(2003, 5, 7);
	private int totalHours1 = 40;
	private int totalCharges1;
	private Consultant consultant1 = new Consultant(new Name("Coder","Carl"));
	private Consultant consultant2 = new Consultant(new Name("Silverman","Dan"));
	private List<InvoiceLineItem> invoiceLineItems1 = new ArrayList<InvoiceLineItem>() {
		{
		add(new InvoiceLineItem(startDate1, consultant1,  Skill.PROJECT_MANAGER, 10));
		add(new InvoiceLineItem(startDate2, consultant2,  Skill.SOFTWARE_ENGINEER, 10));
		add(new InvoiceLineItem(startDate3, consultant1,  Skill.PROJECT_MANAGER, 10));
		add(new InvoiceLineItem(startDate4, consultant1,  Skill.PROJECT_MANAGER, 10));
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
	}
	@Test
	public void test() {
		
		//Test extractLineItem
			for ( ConsultantTime time : timeCard.getBillableHoursForClients( client.getName() ) ) 
				addLineItem(new InvoiceLineItem(time.getDate(), timeCard.getConsultant(), time.getSkillType(), time.getHours() ) );
			
		}
		
		
		//Test toReportString
		invoice1.extractLineItems( new TimeCard(consultant1,LocalDate.of(2003, 5, 1)) );
		invoice1.extractLineItems( new TimeCard(consultant2,LocalDate.of(2003, 5, 1)));
		
		InvoiceFooter footer = new InvoiceFooter(businessName);
		LocalDate invoiceForMonth = LocalDate.of(invoiceYear1,invoiceMonth1.getValue(),1);
		LocalDate invoiceDate = LocalDate.now();
		InvoiceHeader header = new InvoiceHeader(businessName,
				new Address(businessStreet,businessCity,businessState,businessZip), client1, invoiceDate , invoiceForMonth  );

		StringBuilder builder = new StringBuilder();
		String str1 = header.toSting();
		String str3 = footer.toString();

		int i=0;
		for ( InvoiceLineItem a : invoiceLineItems1 ){

			int mod = i % 5;
				if (i == 0 ) {
					builder = builder.append(str1)
							.append(System.getProperty("line.separator")).append(a.toString())
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
		System.out.println(expected1);
		System.out.println(actual1);
		//assertEquals(expected1,actual1);
	}

}
