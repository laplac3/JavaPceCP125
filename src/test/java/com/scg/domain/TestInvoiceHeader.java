package com.scg.domain;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;

public class TestInvoiceHeader {
	private String businessName1 = "businessName1";
	private Address businessAddress1 = new Address("7331 street Something", "NewYour",StateCode.NY,"9999");
	private ClientAccount client1 = new ClientAccount("clientName",new Name("Nevitt", "Neil", "A"), new Address("7508 9th ave N.E.", "Seattle", StateCode.WA,"98115"));
	private LocalDate invoiceDate1 = LocalDate.of(2021, 1, 4);
	private LocalDate invoiceForMonth1 = LocalDate.of(2021, 5, 5);
	private InvoiceHeader invoice1;
	
	private static final DateTimeFormatter invoice1Formatter = DateTimeFormatter.ofPattern("MMM yyyy");
	private static final DateTimeFormatter invoiceForMonth1Formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
	
	private static final String line5 = "Invoice For:";
	private static final String line7 = "Invoice For Month of:";
	private static final String line8 = "Invoice Date:";
	
	private final static String msg1 = "Date";
	private final static String msg2 = "Consultant";
	private final static String msg3 = "Skill";
	private final static String msg4 = "Hours";
	private final static String msg5 = "Charge";
	private final static String fmt4 = "%n%s %2$17s %3$24s %4$21s %5$8s";
	private final static String str4 = String.format(fmt4,msg1,msg2,msg3, msg4, msg5);
	
	private final static String divider2 = new String(new char[10]).replace("\0", "-");
	private final static String divider3 = new String(new char[28]).replace("\0", "-");
	private final static String divider4 = new String(new char[20]).replace("\0", "-");
	private final static String divider5 = new String(new char[6]).replace("\0", "-");
	private final static String divider6 = new String(new char[10]).replace("\0", "-");
	private final static String fmt5 = "%s %2$29s %3$21s %4$7s %5$11s%n";
	private final static String dashes = String.format(fmt5, divider2, divider3, divider4,divider5, divider6);
	
	@Before
	public void init() {
		invoice1 = new InvoiceHeader(businessName1, businessAddress1,client1, invoiceDate1, invoiceForMonth1);
	}
	
	@Test
	public void test() {

		String expected1 = String.format("%s %n%2$s %n%3$s, %4$s %5$s %n%n%6$s %n%7$s %n%8$s %n%9$s %n%n%10$s %11$s %n%12$s %13$s %n%14$s %n%15$s",
				businessName1, businessAddress1.getStreetNumbers(), businessAddress1.getCity(),
				businessAddress1.getState(),businessAddress1.getPostalCode(), line5, client1.getName(),
				client1.getAddress(),  client1.getContact(), line7, invoiceForMonth1.format(invoiceForMonth1Formatter),line8,
				invoiceDate1.format(invoice1Formatter), str4, dashes);
		String actual1 = invoice1.toSting();
		System.out.println(actual1);
		System.out.println(actual1);
		assertEquals(expected1,actual1);
	}
}
