package com.scg.util;

import java.util.Formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author neil
 *A mailing address class.
 */
public final class Address implements Comparable<Address>{
	private String streetNumbers;
	private String city;
	private StateCode state;
	private String postalCode;
	
    
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
	
}
