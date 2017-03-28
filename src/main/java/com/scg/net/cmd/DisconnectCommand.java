package com.scg.net.cmd;

/**
 * @author Neil Nevitt
 * The command to disconnect, this command has no target.
 */
public final class DisconnectCommand extends AbstractCommand<Void> {

	/**
	 * Version Id.
	 */
	private static final long serialVersionUID = 9076692752230584371L;
	/**
	 * Constructor.
	 */
	public DisconnectCommand() {
		super();
	}
	@Override
	public void execute() {
		receiver.execute(this);
		
	}

}
