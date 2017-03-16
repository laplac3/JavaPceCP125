package com.scg.beans;

import java.beans.PropertyVetoException;

import javax.swing.event.EventListenerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.domain.Consultant;

/**
 * @author Neil Nevitt
 * Responsible for modifiying the pay rate, sick leave and vacation hours of staff consultants.
 *
 */ 
public class HumanResourceManager {

	
	private Logger log = LoggerFactory.getLogger(HumanResourceManager.class);
	private EventListenerList listenerList = new EventListenerList();

	/**
	 * Sets the pay rate for a staff consultant. 
	 * @param c - the staff consultant
	 * @param newPayRate - the new pay rate.
	 */ 
	public void adjustPayRate(StaffConsultant c, int newPayRate ) {
		try { 
			if ( log.isInfoEnabled() ) {
					double percentChange = Math.abs(newPayRate - c.getPayRate())*10000.0/c.getPayRate()/100;
					final String msg = "Percent change is =" + percentChange ;
					log.info (msg);
			}
			c.setPayRate(newPayRate);
			log.info("approved raise for " + c.getName());
			} catch  ( final PropertyVetoException pve) {
								
		}

		
	}
	/**
	 * Sets the vacation hours for a staff consultant. 
	 * @param c - the staff consultant
	 * @param newVacationHours - the new vacation hours.
	 */
	public void adjustVacationHours(StaffConsultant c, int newVacationHours ) {
		c.setVacationHours(newVacationHours);
	}
	/**
	 * Sets the sick leave hours for a staff consultant. 
	 * @param c - the staff consultant
	 * @param newSickLeaveHours - the new sick leave hours.
	 */ 
	public void adjustSickLeaveHours(StaffConsultant c, int newSickLeaveHours ) {
		c.setSickLeaveHours(newSickLeaveHours);
	}
	
	/**
	 * Fires a voluntary termination event for the staff consultant.
	 * @param c
	 */
	public void acceptResignation( Consultant c) {
		fireTerminationEvent( new TerminationEvent(this,c,true));
	}
	
	/**
	 * Fires an involuntary termination event for a staff consultant.
	 * @param c - the consultant being fired.
	 */
	public void terminate(Consultant c ) {
		TerminationListener[] listeners = listenerList.getListeners(TerminationListener.class);
		for ( TerminationListener tl : listeners ) {
			tl.forcedTermination(new TerminationEvent(this,c,false));
		}
	}

	/**
	 * Adds a termination listener.
	 * @param l - the listener to be added.
	 */
	public void addTerminationListener(TerminationListener l) {
		listenerList.add(TerminationListener.class, l);
	}
	/**
	 * Removes a termination listener.
	 * @param l - the listener to be removed.
	 */
	public void removeTerminationListener(TerminationListener l) {
		listenerList.remove(TerminationListener.class, l);
	}
	
	private synchronized void fireTerminationEvent( final TerminationEvent evnt) {
		TerminationListener [] listeners;
		listeners = listenerList.getListeners( TerminationListener.class);
				
				for (TerminationListener listener : listeners) {
					if(evnt.isVoluntary())
						listener.voluntaryTermination(evnt);
				else
					listener.forcedTermination(evnt);
				}
	}
}
