package com.scg.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.scg.util.Address;

final class InvoiceHeader {
	
	/**
	 * Name of the business.
	 */
	private String businessName;
	/**
	 * Address of the business.
	 */
	private Address businessAddress;
	/**
	 * The client billable.
	 */
	private ClientAccount client;
	/**
	 * The date the invoice was created.
	 */
	private LocalDate invoiceDate;
	/**
	 * The month being billed.
	 */
	private LocalDate invoiceForMonth;

	/**
	 * Format of invoice. 
	 */
	private static final DateTimeFormatter invoice1Formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
	/**
	 * Format of the invoice month
	 */
	private static final DateTimeFormatter invoiceForMonth1Formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
	
	private static final String line5 = "Invoice For:";
	private static final String line7 = "Invoice For Month of:";
	private static final String line8 = "Invoice Date:";
	

	private final String msg1 = "Date";
	private final String msg2 = "Consultant";
	private final String msg3 = "Skill";
	private final String msg4 = "Hours";
	private final String msg5 = "Charge";
	private final String fmt4 = "%n%s %2$17s %3$24s %4$21s %5$8s";
	private final String str4 = String.format(fmt4,msg1,msg2,msg3, msg4, msg5);
	
	private final String divider2 = new String(new char[10]).replace("\0", "-");
	private final String divider3 = new String(new char[28]).replace("\0", "-");
	private final String divider4 = new String(new char[20]).replace("\0", "-");
	private final String divider5 = new String(new char[6]).replace("\0", "-");
	private final String divider6 = new String(new char[10]).replace("\0", "-");
	private final String fmt5 = "%s %2$29s %3$21s %4$7s %5$11s%n";
	private final String dashes = String.format(fmt5, divider2, divider3, divider4,divider5, divider6);
	/**
	 * Constructor for a new instance of InvoiceHeader.
	 * @param businessName - name of business issuing invoice.
	 * @param businessAddress - the address of the business.
	 * @param client - name of the client.
	 * @param invoiceDate - Date of the invoice.
	 * @param invoiceForMonth - Month of billable charge for this invoice.
	 */
	public InvoiceHeader(String businessName, Address businessAddress, ClientAccount client, LocalDate invoiceDate,
			LocalDate invoiceForMonth) {
		this.businessName = businessName;
		this.businessAddress = businessAddress;
		this.client = client;
		this.invoiceDate = invoiceDate;
		this.invoiceForMonth = invoiceForMonth;
	}
	
	public String toSting() {
		return String.format("%s %n%2$s %n%n%3$s %n%4$s %n%5$s %n%6$s %n%n%7$s %8$s %n%9$s %10$s %n%11$s %n%12$s",
				businessName, businessAddress.toString(), line5, client.getName(),
				client.getAddress(),  client.getContact(), line7, invoiceForMonth.format(invoiceForMonth1Formatter),line8,
				invoiceDate.format(invoice1Formatter), str4, dashes);
	}
	
}
