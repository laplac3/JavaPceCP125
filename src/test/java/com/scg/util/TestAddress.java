package com.scg.util;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TestAddress {

	private String streetNumbers1 = "7508 ninth Ave NE";
	private String city1 = "Seattle";
	private StateCode state1 = StateCode.WA;
	private String postalCode1 = "98115";
	private Address adress1;
	
	
	@Before
	public void init() {
		adress1 = new Address( streetNumbers1, city1, state1, postalCode1 );
	}
	
	@Test
	public void test() {
		//test to String
		String format = "%s %n%2$s, %3$s %4$s";
		String expected1 = String.format(format, streetNumbers1, city1, state1, postalCode1);
		String actual1 = adress1.toString();
		assertEquals(expected1,actual1);
	}
}
