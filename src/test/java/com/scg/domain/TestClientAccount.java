package com.scg.domain;

import org.junit.Test;
import com.scg.util.Name;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestClientAccount {
	
	private ClientAccount account1;
	private String account1Name = "account1Name";
	private Name accountContact = new Name("last","first");
	
	private ClientAccount account2;	
	private String account2Name = "account2Name";
	
	@Before
	public void init() {
		account1 = new ClientAccount(account1Name,accountContact);
		account2 = new ClientAccount();
	}
	
	@Test
	public void test() {
		//test getters and setters
		account2.setContact(accountContact);
		account2.setName(account2Name);
		assertEquals(accountContact,account2.getContact());
		assertEquals(account2Name,account2.getName());
		//test is billable
		assertTrue(account1.isBillable());
	}

}
