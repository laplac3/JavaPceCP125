package com.scg.net.cmd;

import java.io.Serializable;

import com.scg.net.server.CommandProcessor;

/**
 * @author Neil Nevitt
 * The superclass of all Command objects, the command role in the Command design pattern. 
 */
public interface Command<T> extends Serializable {
	
	/**
	 * Gets the CommandProcessor receiver for this Command.
	 * @return the receiver for this command.
	 */
	CommandProcessor getReceiver();
	
	/**
	 * The set CommandProcessor that will execute this command.
	 * @param receiver - the receiver for this command.
	 */
	void setReceiver(CommandProcessor receiver);
	
	/**
	 * Return the target for this command.
	 * @return the target.
	 */
	T getTarget();
	
	/**
	 * The method called by the receiver. This method must be implemented by subclass to send a reference ot themselves
	 * to receiver by calling receier.execute(this).
	 */
	void execute();
}
