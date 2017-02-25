package com.scg.domain;

import com.scg.util.Name;

/**
 * @author neil
 *A consultant.
 */
public class Consultant implements Comparable<Consultant> {
	
	/**
	 * Name of the consultant.
	 */
	private Name name;

	/**
	 * Creates a new instance of consultant
	 * @param name - The name of the consultant to set.
	 */
	public Consultant(Name name) {
		this.name = name;
	}
	
	/**
	 * Creates a new instance of consultant.
	 */
	public Consultant() {
		
	}

	/**
	 * Getter for the name of consultant.
	 * @return Returns the name of the consultant.
	 */
	public Name getName() {
		return name;
	}

	/**
	 * Setter for the name of the consultant.
	 * @param name - The name of the consultant name.
	 */
	public void setName(Name name) {
		this.name = name;
	}

	public String toString() {
		return name.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Consultant other = (Consultant) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Consultant other) {
		int diff = 0;
		if ( this != other ) {
			diff = name.compareTo(other.getName());
		}
		return diff;
	}
	
}
