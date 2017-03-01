package com.scg.domain;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
//import java.util.Scanner;
//import java.io.File;

import java.text.NumberFormat;

import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;

import app.Assignment04;

/**
 * @author Neil
 * Encapsulates a invoice. 
 */
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
	private List<InvoiceLineItem> lineItems;

    /** Name of property file containing invoicing business info. */
    private static final String PROP_FILE_NAME = "/invoice.properties";

    /** Property containing the invoicing business name. */
    private static final String BUSINESS_NAME_PROP = "business.name";

    /** Property containing the invoicing business street address. */
    private static final String BUSINESS_STREET_PROP = "business.street";

    /** Property containing the invoicing business city. */
    private static final String BUSINESS_CITY_PROP = "business.city";

    /** Property containing the invoicing business state. */
    private static final String BUSINESS_STATE_PROP = "business.state";

    /** Property containing the invoicing business zip or postal code. */
    private static final String BUSINESS_ZIP_PROP = "business.zip";
    
    /**
     * If consultant has no middle name then it is "Not Available"
     */
    private static final String NA = "N/A";
    
	/**
	 * Name of business.
	 */
	private static final String businessName;
	/**
	 * Street of business.
	 */
	private static final String businessStreet;
	/**
	 * City of business.
	 */
	private static final String businessCity;
	/**
	 * postal code of business.
	 */
	private static final String businessZip;
	/**
	 * State code of business.
	 */
	private static final String businessState;

	static {
		final Properties invoiceProps = new Properties();
		try ( InputStream in = Invoice.class.getResourceAsStream(PROP_FILE_NAME)) {
			invoiceProps.load(in);
		} catch ( final IOException e) {
			System.out.printf("Unable to read properties file", e);
		}
		businessName = invoiceProps.getProperty(BUSINESS_NAME_PROP, NA );
		businessStreet = invoiceProps.getProperty(BUSINESS_STREET_PROP, NA );
		businessCity = invoiceProps.getProperty(BUSINESS_CITY_PROP, NA );
		businessZip = invoiceProps.getProperty(BUSINESS_ZIP_PROP, NA );
		businessState = invoiceProps.getProperty(BUSINESS_STATE_PROP, NA );
	}
	
	/**
	 * Number of lines per page.
	 */
    private final int itemsPerPage = 5;
	/**
	 * Date of the invoice.
	 */
	private LocalDate invoiceDate;
	/**
	 * Construct an invoice for a client. The period is set from the beginning of the month to the end.
	 * @param client - Client for this invoice.
	 * @param invoiceMonth - Month for this invoice.
	 * @param invoiceYear - Year for this invoice.
	 */
	public Invoice(ClientAccount client, Month invoiceMonth, int invoiceYear) {
		this.client = client;
		this.lineItems = new ArrayList<InvoiceLineItem>();
		this.invoiceDate = LocalDate.now();
		this.startDate = LocalDate.of(invoiceYear, invoiceMonth, 1);
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
			lineItems.add(lineItem);
			totalHours += lineItem.getHours();
			totalCharges += lineItem.getCharge();
	}
	
	/**
	 * Extract the billable hours for this Invoice's client from the input TimeCard and add them to the line items.
	 * Only those hours for the client and month unique to this invoice will be added.
	 * @param timeCard - The TimeCard potentially containing line items for this Invoices client.
	 */
	public void extractLineItems(TimeCard timeCard) {
		final List<ConsultantTime> billableHoursList = timeCard.getBillableHoursForClients(client.getName());
		billableHoursList.stream().filter( e-> e.getDate().getMonth() == startDate.getMonth() )
		.forEach(e->addLineItem( 
				new InvoiceLineItem(e.getDate(), timeCard.getConsultant(), e.getSkillType(), e.getHours() )));
	}

	@Override
	public String toString() {
		return "Invoice [client=" + client + ", invoiceMonth=" + invoiceMonth + ", invoiceYear=" + invoiceYear
				+ ", startDate=" + startDate + ", totalHours=" + totalHours + ", totalCharges=" + totalCharges
				+ ", lineItems=" + lineItems + "]";
	}

	/**
	 * Created a formatted string representation of the invoice. Includes a header and footer on each page.
	 * @return Returns a string.
	 */
	public String toReportString() {
		
		InvoiceFooter footer = new InvoiceFooter(businessName);
		InvoiceHeader header = new InvoiceHeader(businessName,
				new Address(businessStreet,businessCity,StateCode.valueOf(businessState),businessZip),
				client, invoiceDate , startDate );

		StringBuilder builder = new StringBuilder();
		String str1 = header.toSting();
		String str3 = footer.toString();

		int i=0;
		for ( InvoiceLineItem a : lineItems ){

			int mod = i % itemsPerPage;
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
		String totalChargesCurrency = numberFormat.format(totalCharges);
		totalChargesCurrency = totalChargesCurrency.substring(1);
		String totals = String.format("%n%s %2$64s %3$11s","total" ,totalHours,totalChargesCurrency);
		builder = builder.append(totals).append(System.getProperty("line.separator")).append(str3);

		return builder.toString();
	}
	
}
