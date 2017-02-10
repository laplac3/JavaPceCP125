package com.scg.domain;

import org.junit.Test;
import com.scg.util.Name;
import org.junit.Before;
import static org.junit.Assert.assertEquals;


public class TestConsultant {

	private Consultant consultant1;
	private Consultant consultant2;
	private Name name = new Name("LastName","FirstName");
	@Before
	public void init() {
		consultant1 = new Consultant(name);
		consultant2 = new Consultant();
	}
	@Test
	public void test() {
		assertEquals(name.toString(),consultant1.toString());
		consultant2.setName(name);
		assertEquals(name,consultant2.getName());
		
	}

}
