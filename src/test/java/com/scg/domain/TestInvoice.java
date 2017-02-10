package com.scg.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import org.junit.Test;

import com.scg.util.Name;

public class TestInvoice {

	private ClientAccount client1 = new ClientAccount("SomeName1", new Name("Last1","first1"));
	private java.time.Month invoiceMonth1;
	private int invoiceYear1;
	private java.time.LocalDate startDate1;
	private int totalHours1;
	private int totalCharges1;
	private List<InvoiceLineItem> invoiceLineItems1 = new ArrayList<>();
	private Invoice invoice1;
	
	@Before
	public void init() {
		//invoice1 = new Invoice();
	}
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
