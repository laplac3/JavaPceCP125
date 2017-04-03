package com.scg.net.cmd;

/**
 * @author Neil Nevitt
 * The command to disconnect, this command has no target.
 */
@SuppressWarnings("serial")
public final class DisconnectCommand extends AbstractCommand<Void> {

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
