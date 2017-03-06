package com.scg.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;

import com.scg.util.Address;
import com.scg.util.Name;

/**
 * @author neil
 *Encapsulates the information for a billable account.
 */
public final class ClientAccount implements Account, Comparable<ClientAccount> {

	/**
	 * Version Id
	 */
	private static final long serialVersionUID = 3546117381376328400L;
	
	/**
	 * The serialization fields.
	 */
//	private static final ObjectStreamField[] serialPersistenFields = {
//			new ObjectStreamField("name", String.class),
//			new ObjectStreamField("contact", Name.class),
//			new ObjectStreamField("address", Address.class)
//			
//	};
	
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
	
//	/**
//	 * Reads the object fields from stream.
//	 * @param ois the stream to read the object from.
//	 * @throws ClassNotFoundException if the read object's class can't be loaded.
//	 * @throws IOException if any I/O exception occurs.
//	 */
//	private void readObject(final ObjectInputStream ois ) 
//			throws ClassNotFoundException, IOException {
//		ObjectInputStream.GetField fields;
//		try {
//			fields = ois.readFields();
//		} catch (IOException ex) {
//			ex.printStackTrace();
//			throw ex;
//		}
//		name = (String) fields.get("name", "N/A");
//		contact = (Name) fields.get("contact", new Name() );
//		address = (Address) fields.get("address", new Address() );
//	}
//
//	private void writeObject(final ObjectOutputStream oos) throws IOException {
//		ObjectOutputStream.PutField fields = oos.putFields();
//		fields.put("name", name);
//		fields.put("contact", contact);
//		fields.put("address", address);
//		oos.writeFields();
//	}
}
