package com.scg.beans;

import java.io.Serializable;
import java.util.EventObject;

import com.scg.domain.Consultant;

/**
 * @author Neil Nevitt
 * Event used to notify listeners of a consultants termination.
 *
 */
@SuppressWarnings("serial")
public final class TerminationEvent extends EventObject implements Serializable {

	
	/**
	 * Consultant to be terminated.
	 */
	private final Consultant consultant;
	/**
	 * Whether or not consultant was terminated voluntarily.  
	 */
	private final boolean voluntary;
	/**
	 * Constructor.
	 * @param source - the event source.
	 * @param consultant - the consultant being terminated.
	 * @param voluntary - was the termination voluntary. 
	 */
	public TerminationEvent(Object source, Consultant consultant, boolean voluntary) {
		super(source);
		this.consultant = consultant;
		this.voluntary = voluntary;
	}
	
	/**
	 * Gets the voluntary status.
	 * @return true if it was voluntary.
	 */
	public boolean isVoluntary() {
		return this.voluntary;
	}
	
	/**
	 * Gets the consultant being terminated.
	 * @return - the consultant. 
	 */
	public Consultant getConsultant() {
		return this.consultant;
	}
		
}
