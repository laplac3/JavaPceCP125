package com.scg.net.cmd;

/**
 * @author Neil Nevitt
 * This Command will cause the CommandProcessor to shutdown the server.
 */
public final class ShutdownCommand extends AbstractCommand<Void> {

	/**
	 * Version Id.
	 */
	private static final long serialVersionUID = -5795739421376527482L;

	public ShutdownCommand() {
		super();
	}
	@Override
	public void execute() {
		receiver.execute(this); 
		
	}

}
