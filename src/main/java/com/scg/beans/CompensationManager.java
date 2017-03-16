package com.scg.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.EventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Neil Nevitt
 * Approves or rejects the compensation changes. 
 *
 */ 
public class CompensationManager implements PropertyChangeListener, VetoableChangeListener, EventListener {

	/**
	 * The max percent of pay rate raise.
	 */
	private static final int MAX_PRECENT = 105;
	/**
	 * The factor to make a number a percent.
	 */
	private int TO_PERCENT = 100; 
	

	private static final Logger log = LoggerFactory.getLogger(CompensationManager.class);
	
	/**
	 * Constructor.
	 */
	public CompensationManager() {
		
	}

	@Override
	public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
		if ( StaffConsultant.PAY_RATE_PROPERTY_NAME.equals(evt.getPropertyName())) {
			final int oldInt = (Integer)evt.getOldValue();  
			final int newInt = (Integer)evt.getNewValue();;
			if(  newInt * TO_PERCENT > oldInt * MAX_PRECENT ) {
				if ( log.isInfoEnabled() ) { 
					final String msg = String.format("Reject pay rate change, form %s to %2$s for %3$s", 
							evt.getOldValue(),
							evt.getNewValue(),
							((StaffConsultant)evt.getSource()).getName());
					log.info(msg);
				} throw new PropertyVetoException("Rejected", evt);
			} 
		} 

	}
		
		
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ( StaffConsultant.PAY_RATE_PROPERTY_NAME.equals(evt.getPropertyName())) {
			final String msg = String.format("Approved pay rate change, form %s to %2$s for %3$s", 
					evt.getOldValue(), 
					evt.getNewValue(), 
					((StaffConsultant) evt.getSource()).getName());
			log.info(msg);
		}
		
	}

}
