package com.scg.domain;

import com.scg.util.Name;

/**
 * @author neil
 *A consultant.
 */
public class Consultant {
	
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
}
