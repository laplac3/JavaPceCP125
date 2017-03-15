package com.scg.beans;

import java.util.EventListener;

/**
 * @author Neil Nevitt
 * Interface for accepting notification of consultant termination. 
 */
public interface TerminationListener extends EventListener {
	/**
	 * Invoked when a consultant is voluntarily terminated. 
	 * @param evt - the termination event.
	 */
	void voluntaryTermination(TerminationEvent evt);	
	/**
	 * Invoked when a consultant is involuntarily terminated. 
	 * @param evt - the termination event.
	 */
	void forcedTermination(TerminationEvent evt);
}
