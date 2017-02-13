package com.scg.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public final class InvoiceLineItem {

	/**
	 * Date of line item.
	 */
	private LocalDate date;
	/**
	 * Consultant of line item.
	 */
	private Consultant consultant;
	/**
	 * Skill type for line item.
	 */
	private Skill skill;
	/**
	 * Number of hours for line item.
	 */
	private int hours;

	/**
	 * Construct an InvoiceLineItem
	 * @param date of this line item
	 * @param consultant for this line item
	 * @param skill skill for this line item
	 * @param hours hours for this line item
	 */
	public InvoiceLineItem(LocalDate date, Consultant consultant, Skill skill, int hours) {
		this.date = date;
		this.consultant = consultant;
		this.skill = skill;
		this.hours = hours;
	}
	
	/**
	 * Getter for the date of this line item.
	 * @return The date.
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Getter for this line item consultant.
	 * @return The consultant.
	 */
	public Consultant getConsultant() {
		return consultant;
	}

	/**
	 * Getter for this line item skill.
	 * @return The skill.
	 */
	public Skill getSkill() {
		return skill;
	}

	/**
	 * Getter for the hours of this line item.
	 * @return The hours.
	 */
	public int getHours() {
		return hours;
	}
	/**
	 * Getter for the charge of this line item.
	 * @return The charge.
	 */
	public int getCharge() {
		return hours * skill.getRate();
	}

	public String toString() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, date.getMonth().getValue()-1);
		cal.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
		cal.set(Calendar.YEAR, date.getYear());
		String consultantStr = consultant.toString();
		String padSkil = TimeCard.pad(consultantStr, " ", 28);
		String hoursPad = "";
		String hoursStr = Integer.toString(hours);
		String skillStr = skill.getTitle();
		if ( hoursStr.length() == 1)
			hoursPad = TimeCard.pad(skillStr," ",25);
		else
			hoursPad = TimeCard.pad(skillStr," ",24);
		String chargeStr = Integer.toString(getCharge());
		String chargerPad = TimeCard.pad(chargeStr + ".00", " ", 10);
		String consultantPad = TimeCard.pad( "", " ", 2);
		return String.format("%1$tm/%1$td/%1$ty %2$s %3$s %4$s %5$s %6$s %7$s %8$s %9$s.00",
				cal,  consultantPad, consultantStr, padSkil, skillStr, hoursPad, hoursStr,  chargerPad, chargeStr );
		 
	}
	
}
