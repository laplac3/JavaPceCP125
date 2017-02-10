package com.scg.domain;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TestInvoiceFooter {

	private static final String pageStr = "Page:";
	
	private String businessName1 ="businessName1";
	private int page1=0;
	private InvoiceFooter invoiceFooter1;
	private static final String divider = new String(new char[82]).replace("\0", "=");
	@Before
	public void init() {
		invoiceFooter1 = new InvoiceFooter(businessName1);
	}
	@Test
	public void test() {
		//Test
		for (int i=0; i<101; i++) {
			page1 +=1;
			String businessNamePad = TimeCard.pad(businessName1, " ", 68);
			String pageNumb = Integer.toString(page1);
			String pageStrPad = TimeCard.pad(pageNumb, " ", 5);
			String expected1 = String.format("%n%s %2$s %3$s %4$s %5$s %n%6$s%n",businessName1, businessNamePad, pageStr, pageStrPad, pageNumb, divider);
			String actual1 = invoiceFooter1.toString();
			assertEquals(expected1,actual1);
		}
		
	}

}
