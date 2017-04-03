package com.scg.net.cmd;

import com.scg.domain.ClientAccount;

/**
 * @author Neil Nevitt
 * The commmand to add a client to a list maintained by the server.
 */
@SuppressWarnings("serial")
public final class AddClientCommand extends AbstractCommand<ClientAccount> {

	
	
	/**
	 * Construct an AddClientCommand with target.
	 * @param target - The target of this command.
	 */
	public AddClientCommand(ClientAccount target) {
		super(target);

	}


	@Override
	public void execute() {
		getReceiver().execute(this);
		
	}

}
