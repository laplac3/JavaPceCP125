package com.scg.util;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class NameTest {
	private int prime = 31;

	private Name harrisonFord;
	private String ford = "Ford";
	private String harrison = "Harrison";

	private Name janeWoodJessica;
	private String wood = "Wood";
	private String jessica = "Jessica";
	private String jane = "Jane";
	
	private Name nullFirstName;	
	private Name nullLastName;
	private Name nullMiddleName;
	
	@Before
	public void init() {	
		harrisonFord = new Name(ford, harrison);
		janeWoodJessica = new Name(wood,jessica,jane);
		nullFirstName = new Name("name", null);
		nullLastName = new Name(null, "name");
		nullMiddleName = new Name("last", "first",null);
	}
	

	
	@Test
	public void test() {
		//test hashcode
		int expectedHashCode1 = 1;
		expectedHashCode1 = prime * expectedHashCode1 + harrison.hashCode();
		expectedHashCode1 = expectedHashCode1*prime + ford.hashCode();
		expectedHashCode1 = expectedHashCode1*31;
		assertEquals(expectedHashCode1, harrisonFord.hashCode(),0);
		// test jane
		int expectedHashCode2 = 1;
		expectedHashCode2 = expectedHashCode2 *prime  + jessica.hashCode();
		expectedHashCode2 = expectedHashCode2 *prime +  wood.hashCode();
		expectedHashCode2 = expectedHashCode2 *prime +  jane.hashCode();
		assertEquals(expectedHashCode2, janeWoodJessica.hashCode(),0);
		
		//test equals
		Object otherNull = null;
		Object otherNotNull = "not null";
		assertFalse(harrisonFord.equals(otherNull));
		assertFalse(harrisonFord.equals(otherNotNull));
		assertTrue(harrisonFord.equals(harrisonFord));
		assertFalse(harrisonFord.equals(janeWoodJessica));

		assertFalse(nullFirstName.equals(harrisonFord));
		assertFalse(harrisonFord.equals(nullFirstName));
		assertTrue(nullFirstName.equals(nullFirstName));
		
		assertFalse(nullLastName.equals(harrisonFord));
		assertFalse(harrisonFord.equals(nullLastName));
		assertTrue(nullLastName.equals(nullLastName));
		
		assertFalse(nullMiddleName.equals(harrisonFord));
		assertFalse(harrisonFord.equals(nullMiddleName));
		assertTrue(nullMiddleName.equals(nullMiddleName));
		//test toString
		StringBuilder str = new StringBuilder(); 
		str = str.append(ford).
								append(", ").
								append(harrison).
								append(" ").
								append("");
		assertEquals(str.toString(),harrisonFord.toString());
		
		StringBuilder str1 = new StringBuilder(); 
		str1 = str1.append(wood).
								append(", ").
								append(jessica).
								append(" ").
								append(jane);
		assertEquals(str1.toString(),janeWoodJessica.toString());
		
		//test getters and setters
		String john = "John";
		String smith = "Smith";
		String middle = "Middle";
		Name johnSmith = new Name();
		johnSmith.setFirstName(john);
		johnSmith.setLastName(smith);
		johnSmith.setMidleName(middle);
		assertEquals(john,johnSmith.getFirstName());
		assertEquals(smith,johnSmith.getLastName());
		assertEquals(middle,johnSmith.getMidleName());
		
	}

}
