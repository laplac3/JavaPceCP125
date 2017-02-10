package com.scg.domain;

final class InvoiceFooter {
	
	/**
	 * The name of the business.
	 */
	private final String businessName;
	/**
	 * The page number.
	 */
	private int page=0;
	/**
	 * Page string.
	 */
	private static final String pageStr = "Page:";
	/**
	 * divider for invoices and invoice pages.
	 */
	private static final String divider = new String(new char[82]).replace("\0", "=");
	
	/**
	 * Invoice constructor.
	 * @param businessName - The name of the business.
	 */
	public InvoiceFooter(String businessName) {
		this.businessName = businessName;
	}
	
	/**
	 * Increments the page number by one.
	 */
	public void incrementPageNumber() {
		page += 1;
	}
	
	public String toString() { 
		incrementPageNumber();
		String businessNamePad = TimeCard.pad(businessName, " ", 68);
		String pageNumb = Integer.toString(page);
		String pageStrPad = TimeCard.pad(pageNumb, " ", 5);
		String result = String.format("%n%s %2$s %3$s %4$s %5$s %n%6$s%n",businessName, businessNamePad, pageStr, pageStrPad, pageNumb, divider);
		return result;
	}
}
