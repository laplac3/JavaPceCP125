package com.scg.domain;

import java.io.Serializable;

/**
 * @author neil
 *Interfaces all accounts must have.
 */
public interface Account extends Serializable {
	
	/**
	 * Getter for the name of the account.
	 * @return - Returns the name of the Account of the name.
	 */
	String getName();
	/**
	 * Determines if the account is billable.
	 * @return Returns whether or not the account is billable.
	 */
	boolean isBillable();
}
