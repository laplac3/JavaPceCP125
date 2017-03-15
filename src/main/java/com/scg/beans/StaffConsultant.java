package com.scg.beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

import com.scg.domain.Consultant; 
import com.scg.util.Name;

/**
 * @author Neil Nevitt
 * A consultant who is kept on staff(receives benefits).
 */
@SuppressWarnings("serial")
public class StaffConsultant extends Consultant implements Serializable, Comparable<Consultant>{
	
	/**
	 * Pay rate property name.
	 */
	public static final String PAY_RATE_PROPERTY_NAME ="payRate";
	/**
	 * Vacation hours property name.
	 */
	public static final String VACTION_HOURS_PROPERTY_NAME = "vacationHours";
	/**
	 * Sick leave hours property name.
	 */
	public static final String SICK_LEAVE_HOURS_PROPERTY_NAME = "sickLeaveHours";
	
	/**
	 * Pay rate in cents.
	 */
	private int payRate;
	/**
	 * Sick leave hours.
	 */
	private int sickLeaveHours;
	/**
	 * Vacation hours.
	 */
	private int vacationHours;
	
	private PropertyChangeSupport pcs;
	private VetoableChangeSupport vcs;
	/**
	 * Constructor for staff consultant.
	 * @param name - name of consultant.
	 * @param rate - pay rate in cents.
	 * @param sickLeave - hours of sick leave.
	 * @param vacationHours - hours of vacation time.
	 */
	public StaffConsultant(Name name,int payRate, int sickLeaveHours, int vacationHours) {
		super(name);
		this.payRate = payRate;
		this.sickLeaveHours = sickLeaveHours;
		this.vacationHours = vacationHours;
		pcs = new PropertyChangeSupport(this);
		vcs = new VetoableChangeSupport(this);
	}

	
	/**
	 * Getter for pay rate.
	 * @return rate of pay.
	 */
	public int getPayRate() {
		return this.payRate;
	}
	
	/**
	 * Gets the sick leave hours
	 * @return
	 */
	public int getSickLeaveHours() {
		return sickLeaveHours;
	}
	/**
	 * Get vacation hours.
	 * @return the hours to return.
	 */
	public int getVacationHours() {
		return vacationHours;
	}

	/**
	 * Setter for pay rate.
	 * @param rate - rate to set.
	 */ 
	public void setPayRate(int payRate) throws PropertyVetoException {
		vcs.fireVetoableChange(PAY_RATE_PROPERTY_NAME, this.payRate,payRate);
		final int oldRate = this.payRate;
		this.payRate = payRate;
		pcs.firePropertyChange(PAY_RATE_PROPERTY_NAME, oldRate, payRate );
	}
	
	/**
	 * Sets the vacation hours.
	 * @param vacationHours - the hours to set.
	 */
	public void setVacationHours(int vacationHours) {
		final int oldHours = this.vacationHours;
		this.vacationHours = vacationHours;
		pcs.firePropertyChange(VACTION_HOURS_PROPERTY_NAME,oldHours,vacationHours);
	}
	
	/**
	 * Sets the sick leave hours.
	 * @param sickLeaveHours - the hours to set.
	 */
	public void setSickLeaveHours(int sickLeaveHours) {
		final int oldHours = this.sickLeaveHours;
		this.sickLeaveHours = sickLeaveHours;
		pcs.firePropertyChange(SICK_LEAVE_HOURS_PROPERTY_NAME,oldHours,sickLeaveHours);
	}
	
	/**
	 * Adds general property change listener.
	 * @param l - the listener to add.
	 */
	public synchronized void addPropertyChangeListener(PropertyChangeListener l) {
		pcs.addPropertyChangeListener(l);
	}
	
	/**
	 * Removes general property change listener.
	 * @param l
	 */
	public synchronized void removePropertyChangeListener(PropertyChangeListener l ) {
		pcs.removePropertyChangeListener(l);
	}
	
	/**
	 * Add Pay rate listener.
	 * @param l - the listener to add.
	 */
	public synchronized void addPayRateListener(PropertyChangeListener l ) {
		pcs.addPropertyChangeListener(PAY_RATE_PROPERTY_NAME,l);
	}
	
	/**
	 * Removes pay rate listener.
	 * @param l - the listener to remove
	 */
	public synchronized void removePayRateListener(PropertyChangeListener l ) {

		pcs.removePropertyChangeListener(PAY_RATE_PROPERTY_NAME, l);
	}
	
	/**
	 * Add a vetoable change listener.
	 * @param l - the listener to add.
	 */
	public synchronized void addVetoableChangeListener(VetoableChangeListener l ) {
		vcs.addVetoableChangeListener( PAY_RATE_PROPERTY_NAME,  l);
	}
	
	/**
	 * Removes a vetoable change listener.
	 * @param l - the listener to remove
	 */
	public synchronized void removeVetoableChangeListener(VetoableChangeListener l ) {
		vcs.removeVetoableChangeListener(PAY_RATE_PROPERTY_NAME, l);
	}

	/**
	 * Adds sick leave listener.
	 * @param l - the listener to add.
	 */
	public void addSickLeaveHoursListener(PropertyChangeListener l ) {
		pcs.addPropertyChangeListener(SICK_LEAVE_HOURS_PROPERTY_NAME, l);
	}
	
	/**
	 * Removes sick leave listener.
	 * @param l - the listener to remove
	 */
	public void removeSickLeaveHoursListener(PropertyChangeListener l ) {
		pcs.removePropertyChangeListener(SICK_LEAVE_HOURS_PROPERTY_NAME, l);
	}

	/**
	 * Adds sick leave listener.
	 * @param l - the listener to add.
	 */
	public void addVacationHoursListener(PropertyChangeListener l ) {
		pcs.addPropertyChangeListener(VACTION_HOURS_PROPERTY_NAME, l);
	}
	
	/**
	 * Removes Vacation listener.
	 * @param l - the listener to remove
	 */
	public void removeVacationHoursListener(PropertyChangeListener l ) {
		pcs.removePropertyChangeListener(VACTION_HOURS_PROPERTY_NAME, l);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + payRate;
		result = prime * result + sickLeaveHours;
		result = prime * result + vacationHours;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StaffConsultant other = (StaffConsultant) obj;
		if (payRate != other.payRate)
			return false;
		if (sickLeaveHours != other.sickLeaveHours)
			return false;
		if (vacationHours != other.vacationHours)
			return false;
		return true;
	}
	
}
