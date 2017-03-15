package com.scg.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Neil Nevitt
 * Log changes to benefits.
 *
 */
public class BenefitManager implements EventListener, PropertyChangeListener {

	/**
	 * Constructor 
	 */
	public BenefitManager() {
		
	}
	private static final Logger log = LoggerFactory.getLogger(BenefitManager.class);
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		final String proName = evt.getPropertyName();
		final int oldValue = (Integer) evt.getOldValue();
		final int newValue = (Integer) evt.getNewValue();
		
		log.info( proName + " changed from "+ oldValue + " to " + newValue + " for " +((StaffConsultant) evt.getSource()).getName());
	}

}
