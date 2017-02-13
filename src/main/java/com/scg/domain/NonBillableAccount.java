package com.scg.domain;

import com.scg.*;

/**
 * @author neil
 *Encapsulates the concept of a nonbillable account.
 */
public enum NonBillableAccount implements Account{
	/**
	 * Business development.
	 */
	BUSINESS_DEVELOPMENT("Business Development"),
	/**
	 * Sick leave.
	 */
	SICK_LEAVE("Sick Leave"),
	/**
	 * Vacation.
	 */
	VACATION("Vacation");
	private String title;
	NonBillableAccount(String title) {
		this.title = title;
	}
	
	@Override
	public boolean isBillable() {
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.title;
	}
	
	@Override
	public String toString() {
		return title;
	}


}
