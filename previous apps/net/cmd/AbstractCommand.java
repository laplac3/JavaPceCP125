package com.scg.net.cmd;

import com.scg.net.server.CommandProcessor;

/**
 * @author Neil Nevitt.
 * The superclass of all Command objects,implements the command role in the Command design patter.
 * @param <T> 
 */
@SuppressWarnings("serial")
public abstract class AbstractCommand<T> implements Command<T> {

	/**
	 * The target for the command.
	 */
	private T target;
	/** 
	 * CommandProcessor will execute this command.
	 */
	protected transient CommandProcessor receiver;
	/**
	 * Constructor. 
	 */
	public AbstractCommand() {
		
	}

	/**
	 * Constructor. 
	 * @param target the target for this command.
	 */
	public AbstractCommand( T target ) {
		this.target = target;
	}
	
	public final CommandProcessor getReceiver() {
		return this.receiver;
		
	}
	
	public final void setReceiver( CommandProcessor receiver ) { 
		this.receiver = receiver;
	}
	
	public final T getTarget() {
		return this.target;
	}
	
	public String toString() {
		return this.getClass().getSimpleName() + " + target=" + target;
	}
	
}
