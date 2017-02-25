package com.scg.domain;

import com.scg.util.Address;
import com.scg.util.Name;

/**
 * @author neil
 *Encapsulates the information for a billable account.
 */
public final class ClientAccount implements Account, Comparable<ClientAccount> {

	/**
	 * Name of the client
	 */
	private String name;
	/**
	 * The account name
	 */
	private Name contact;
	/**
	 * The address of the account
	 */
	private Address address;
	
	/**
	 * Creates a new instance of client account.
	 * @param name - The name of the client.
	 * @param contact  - The account name.
	 */
	public ClientAccount(String name, Name contact) {
		this.name = name;
		this.contact = contact;
	}

	/**
	 *  Creates a new instance of client account.
	 */
	public ClientAccount() {

	}

	public String getName() {
		return name;
	}
	/**
	 * Creates a new instance of client account.
	 * @param name - The name of the client.
	 * @param contact  - The account name.
	 * @param adress - The address of the account.
	 */
	public ClientAccount(String name, Name contact, Address address) {
		this.name = name;
		this.contact = contact;
		this.address = address;
	}

	/**
	 * Getter for Address
	 * @return client Address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Setter for Address
	 * @param address - Set client address to address.
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Getter for the contacts Name.
	 * @return Returns the contacts Name.
	 */
	public Name getContact() {
		return contact;
	}
	
	/**
	 * Setter for clients name.
	 * @param name - The name of the client to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Setter for the contact Name.
	 * @param contact - The contact name to set.
	 */
	public void setContact(Name contact) {
		this.contact = contact;
	}

	public boolean isBillable() {
		return true;
	}

	@Override
	public int compareTo(ClientAccount other ) {
		int diff = 0;
		if ( this != other ) {
			if ((diff = this.contact.compareTo(other.contact)) == 0 )
			if (( diff = this.name.compareTo(other.name)) == 0 )
			diff = this.address.compareTo(other.address);
		}
		return diff;
	}

}
