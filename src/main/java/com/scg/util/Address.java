package com.scg.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.util.Formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author neil
 *A mailing address class.
 */
public final class Address implements Comparable<Address>, Serializable {
	/**
	 * Version Id.
	 */
	private static final long serialVersionUID = 7428228518988946916L;
	
    /**
     * The serialization fields.
     */
    private static final ObjectStreamField[] serialPersistentFields = {
            new ObjectStreamField("streetNumbers", String.class),
            new ObjectStreamField("city", String.class),
            new ObjectStreamField("state", StateCode.class),
            new ObjectStreamField("postalCode", String.class),
            new ObjectStreamField("hashCode", int.class)
        };
	/**
	 * The Street Numbers.
	 */
	private String streetNumbers;
	/**
	 * The city.
	 */
	private String city;
	/**
	 * The state code.
	 */
	private StateCode state;
	/**
	 * The zip.
	 */
	private String postalCode;
	private int hashCode;
	private static final String NA = "N/A";
    
	/**
	 * Constructor for a mailing address instance.
	 * @param streetNumbers - the numbers for the street
	 * @param city - the city.
	 * @param state - the state
	 * @param postalCode - the zip code
	 */
	public Address(String streetNumbers, String city, StateCode state, String postalCode) {
		this.streetNumbers = streetNumbers;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.hashCode = hashCode();
	}

	public Address() {

	}

	/**
	 * @return Returns the streetNumbers as a string
	 */
	public String getStreetNumbers() {
		return streetNumbers;
	}

	/**
	 * @return Returns the city as a string
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return Returns the sate code as a StateCode.
	 */
	public StateCode getState() {
		return state;
	}

	/**
	 * @return Returns the zip code as a String
	 */
	public String getPostalCode() {
		return postalCode;
	}
	public int getHashCode() {
		return this.hashCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((streetNumbers == null) ? 0 : streetNumbers.hashCode());
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
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		if (state != other.state)
			return false;
		if (streetNumbers == null) {
			if (other.streetNumbers != null)
				return false;
		} else if (!streetNumbers.equals(other.streetNumbers))
			return false;
		return true;
	}
	
	public String toString() {
		String format = "%s %n%2$s, %3$s %4$s";
		String toStr = String.format(format, streetNumbers, city, state, postalCode);
		return toStr;
	}

	@Override
	public int compareTo(Address other) {
		int diff = 0;
		if ( this != other ) {
			if (( this.streetNumbers.compareTo(other.streetNumbers)) == 0)
			if (( this.city.compareTo(other.city)) == 0)
			if (( this.state.compareTo(other.state)) == 0)
			diff = this.postalCode.compareTo(other.postalCode);
		}
		return diff;
	}
	
	/**
	 * Reads the object fields from stream.
	 * @param ois the stream to read the object from.
	 * @throws ClassNotFoundException if the read object's class can't be loaded.
	 * @throws IOException if any I/O exceptions occur.
	 */
	private void readObject( final ObjectInputStream ois ) 
		throws ClassNotFoundException, IOException {
		ObjectInputStream.GetField fields = ois.readFields();
		streetNumbers = (String) fields.get("streetNumbers", NA );
		city = (String) fields.get("city", NA);
		state = (StateCode) fields.get("state", StateCode.NY);
		postalCode = (String) fields.get("postalCode", NA);
		hashCode = fields.get("hashCode", 0);
	}
	
	/**
	 * Writes object to stream.
	 * @param oos the stream to write the object to.
	 * @throws IOException if any I/O exceptions occur.
	 */
	private void writeObject( final ObjectOutputStream oos ) 
		throws IOException {
		
		ObjectOutputStream.PutField fields = oos.putFields();
		fields.put("streetNumbers", streetNumbers);
		fields.put("city", city);
		fields.put("state", state);
		fields.put("postalCode", postalCode);
		fields.put("hashCode", hashCode);
		oos.writeFields();
	}
}
