package com.scg.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;

/**
 * @author neil.
 * Encapsulates the first middle and last name of a person.
 */
public final class Name implements Comparable<Name>, Serializable {
	
	private static final ObjectStreamField[] serialPersistentFields = {
			new ObjectStreamField("lastName",String.class),
			new ObjectStreamField("middleName", String.class),
			new ObjectStreamField("firstName", String.class),
			new ObjectStreamField("hashCode", int.class),
	};

	/**
	 * The last name. 
	 */
	private String lastName;
	/**
	 * The first name.
	 */
	private String firstName;
	/**
	 * The middle name.
	 */
	private String middleName;
	private static final String NA = "N/A";
	private int hashCode;
	
	/**
	 * Constructor with middle name.
	 * @param lastName - Last name.
	 * @param firstName - First name.
	 * @param midleName - Middle name.
	 */
	public Name(String lastName, String firstName, String midleName) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = midleName;
		this.hashCode = hashCode();
	}

	/**
	 * Constructor where middle name is set to Not Available. 
	 * @param lastName - Last name.
	 * @param firstName - First name.
	 */
	public Name(String lastName, String firstName) {
		this(lastName,firstName, NA );
	}
	
	/**
	 * Default constructor.
	 */
	public Name() {
		
	}
	
	public int getHashCode() {
		return this.hashCode;
	}
	
	/**
	 * Getter for last name.
	 * @return Last Name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for last name.
	 * @param lastName - Sets last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for first name.
	 * @return First name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for first name.
	 * @param firstName - Sets first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for middle name.
	 * @return Middle name.
	 */
	public String getMidleName() {
		return middleName;
	}

	/**
	 * Setter for middle name.
	 * @param midleName - Sets middle name.
	 */
	public void setMidleName(String midleName) {
		this.middleName = midleName;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder(); 
		str = str.append(lastName).
								append(", ").
								append(firstName).
								append(" ").
								append(middleName);
		return str.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Name other = (Name) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (middleName == null) {
			if (other.middleName != null)
				return false;
		} else if (!middleName.equals(other.middleName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Name other) {
		int diff = 0;
		if( this != other) {
			if (( diff = this.lastName.compareTo(other.lastName)) == 0 )
			if (( diff = this.firstName.compareTo(other.firstName)) == 0 )
			diff = this.middleName.compareTo(other.middleName);
				
		}
		return diff;
	}
	
	/**
	 * Reads the object fields from stream.
	 * @param ois the stream to read the object from.
	 * @throws ClassNotFoundException if the read object's class can't be loaded.
	 * @throws IOException if any I/O exceptions occur.
	 */
	private void readObject(final ObjectInputStream ois ) 
		throws ClassNotFoundException, IOException {
		ObjectInputStream.GetField fields = ois.readFields();
		String l = (String) fields.get("lastName", NA);
		String m = (String) fields.get("middleName", NA);
		String f = (String) fields.get("firstName", NA);
		int h = fields.get("hashCode", 0);	
	}
	
	private void writeObject(final ObjectOutputStream oos )
		throws IOException {
		
		ObjectOutputStream.PutField fields = oos.putFields();
		fields.put("lastName", lastName);
		fields.put("middleName", middleName);
		fields.put("firstName", firstName);
		fields.put("hashCode", hashCode);
		oos.writeFields();
	}

}
