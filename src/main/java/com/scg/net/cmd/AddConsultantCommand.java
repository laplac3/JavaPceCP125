package com.scg.net.cmd;

import com.scg.domain.Consultant;

/**
 * @author Neil Nevitt.
 * The command to add a Consultant to a list maintained by the server.
 */
public final class AddConsultantCommand extends AbstractCommand<Consultant> {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 8570132430635422490L;


	/**
	 * Construct an AddConsultantCommand with a target.
	 * @param target - The target of this command.
	 */
	public AddConsultantCommand(Consultant target) {
		super(target);
		
	}
	@Override
	public void execute() {
		receiver.execute(this);
		
	}

}
