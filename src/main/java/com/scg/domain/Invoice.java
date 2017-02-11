package com.scg.domain;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;

import app.Assignment03;

public final class Invoice {
	
	/**
	 * Client Account
	 */
	private ClientAccount client;
	/**
	 * Month of invoice.
	 */
	private java.time.Month invoiceMonth;
	/**
	 * Year of invoice.
	 */
	private int invoiceYear;
	/**
	 * Date of start.
	 */
	private java.time.LocalDate startDate;
	/**
	 * Total hours.
	 */
	private int totalHours;
	/**
	 * Total charges.
	 */
	private int totalCharges;
	/**
	 * List of line items.
	 */
	private List<InvoiceLineItem> invoiceLineItems = new ArrayList<>();
	
	private String businessName;
	private String businessStreet;
	private String businessCity;
	private String businessZip;
	private StateCode businessState;

	/**
	 * Construct an invoice for a client. The period is set from the beginning of the month to the end.
	 * @param client - Client for this invoice.
	 * @param invoiceMonth - Month for this invoice.
	 * @param invoiceYear - Year for this invoice.
	 */
	public Invoice(ClientAccount client, Month invoiceMonth, int invoiceYear) {
		this.client = client;
		this.invoiceMonth = invoiceMonth;
		this.invoiceYear = invoiceYear;
	}

	/**
	 * Getter for the client.
	 * @return Returns the client for this invoice.
	 */
	public ClientAccount getClientAccount() {
		return client;
	}


	/**
	 * Getter for the month of this invoice.
	 * @return Returns the month for this invoice.
	 */
	public java.time.Month getInvoiceMonth() {
		return invoiceMonth;
	}

	/**
	 * Getter for the start date.
	 * @return Returns the start date for this invoice.
	 */
	public java.time.LocalDate getStartDate() {
		return this.startDate;
	}
	
	/**
	 * Getter for total hours of this invoice.
	 * @return Return the total hours.
	 */
	public int getTotalHours() {
		return totalHours;
	}
	
	/**
	 * Getter for the total charges;
	 * @return Return total charges.
	 */
	public int getTotalCharges() {
		return totalCharges;
	}
	
	/**
	 * Add an invoice line item to this invoice
	 * @param lineItem - the line item to add.
	 */
	public void addLineItem(InvoiceLineItem lineItem) {
		if ( invoiceMonth == lineItem.getDate().getMonth() ) {
			invoiceLineItems.add(lineItem);
			totalHours += lineItem.getHours();
			totalCharges += lineItem.getCharge();
		}
	}
	
	/**
	 * Extract the billable hours for this Invoice's client from the input TimeCard and add them to the line items.
	 * Only those hours for the client and month unique to this invoice will be added.
	 * @param timeCard - The TimeCard potentially containing line items for this Invoices client.
	 */
	public void extractLineItems(TimeCard timeCard) {

		for ( ConsultantTime time : timeCard.getBillableHoursForClients( client.getName() ) ) 
			addLineItem(new InvoiceLineItem(time.getDate(), timeCard.getConsultant(), time.getSkillType(), time.getHours() ) );
		
	}

	@Override
	public String toString() {
		return "Invoice [client=" + client + ", invoiceMonth=" + invoiceMonth + ", invoiceYear=" + invoiceYear
				+ ", startDate=" + startDate + ", totalHours=" + totalHours + ", totalCharges=" + totalCharges
				+ ", invoiceLineItems=" + invoiceLineItems + "]";
	}

	/**
	 * Created a formatted string representation of the invoice. Includes a header and footer on each page.
	 * @return Returns a string.
	 */
	public String toReportString() {
	
		try {
		Scanner scan = new Scanner( new File( "src/main/resources/invoice.properties"));
		
		
			while ( scan.hasNextLine() ) {
				String lineStr = scan.nextLine();
				int start = lineStr.indexOf("=")+1;
				if ( lineStr.contains("name") ) 
					businessName = lineStr.substring(start, lineStr.length());
				else if ( lineStr.contains("street"))
					businessStreet = lineStr.substring(start, lineStr.length());
				else if ( lineStr.contains("city")) 
					businessCity = lineStr.substring(start, lineStr.length());
				else if ( lineStr.contains("zip"))
					businessZip = lineStr.substring(start, lineStr.length());
				else
					businessState = StateCode.valueOf(lineStr.substring(15,lineStr.length()));
			}
			scan.close();
		} catch ( IOException ex ){
			ex.printStackTrace();
		}
		
		
		
		InvoiceFooter footer = new InvoiceFooter(businessName);
		LocalDate invoiceForMonth = LocalDate.of(invoiceYear,invoiceMonth.getValue(),1);
		LocalDate invoiceDate = LocalDate.now();
		InvoiceHeader header = new InvoiceHeader(businessName,
				new Address(businessStreet,businessCity,businessState,businessZip), client, invoiceDate , invoiceForMonth  );

		StringBuilder builder = new StringBuilder();
		String str1 = header.toSting();
		String str3 = footer.toString();

		int i=0;
		for ( InvoiceLineItem a : invoiceLineItems ){

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
		String totalChargesCurrency = numberFormat.format(totalCharges);
		totalChargesCurrency = totalChargesCurrency.substring(1);
		String totals = String.format("%n%s %2$64s %3$11s","total" ,totalHours,totalChargesCurrency);
		builder = builder.append(totals).append(System.getProperty("line.separator")).append(str3);

		return builder.toString();
	}
	
}
