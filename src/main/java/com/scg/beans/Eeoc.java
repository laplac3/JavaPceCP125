package com.scg.beans;

import java.util.EventListener;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.util.Name;

public class Eeoc implements EventListener, TerminationListener {
	
	/**
	 * The number of forced termination events.
	 */
	private int forceTerminationCount=0;
	/**
	 * The number of voluntary Terminations.
	 */
	private int voluntaryTerminationCount=0;
	
	private static Logger log = LoggerFactory.getLogger(Eeoc.class);
	
	@Override
	public void voluntaryTermination(TerminationEvent evt) {
		final Name con = evt.getConsultant().getName();
		final String msg = String.format("The consultant %s has quit.", con);
		log.info(msg);
		voluntaryTerminationCount +=1;
	}

	@Override
	public void forcedTermination(TerminationEvent evt) {
		final Name con = evt.getConsultant().getName();
		final String msg = String.format("The consultant %s was fired.", con);
		log.info(msg);
		forceTerminationCount +=1;
		
	}
	
	/**
	 * Gets the number of forced terminations.
	 * @return forced termination count.
	 */
	public int forcedTerminationCount() {
		return forceTerminationCount;
	}
	
	/**
	 * Gets the number of voluntary terminations.
	 * @return voluntary termination count.
	 */
	public int voluntaryTerminationCount() {
		return voluntaryTerminationCount;
	}

}
