package com.scg.beans;

import java.util.EventListener;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Eeoc implements EventListener, TerminationListener {
	
	private int forceTerminationCount;
	private int voluntaryTerminationCount;
	
	private static Logger log = LoggerFactory.getLogger(Eeoc.class);
	
	@Override
	public void voluntaryTermination(TerminationEvent evt) {
		final String con = ((StaffConsultant)evt.getSource()).getName().toString(); 
		final String msg = String.format("The consultant %s has quit.", con);
		log.info(msg);
	}

	@Override
	public void forcedTermination(TerminationEvent evt) {
		final String con = ((StaffConsultant)evt.getSource()).getName().toString(); 
		final String msg = String.format("The consultant %s was fired.", con);
		log.info(msg);
		
	}
	
	public int forcedTerminationCount() {
		return forceTerminationCount;
	}
	
	public int voluntaryTerminationCount() {
		return voluntaryTerminationCount;
	}

}
