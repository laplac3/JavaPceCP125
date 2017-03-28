package com.scg.net.cmd;

import com.scg.domain.TimeCard;

public final class AddTimeCardCommand extends AbstractCommand<TimeCard>{

	
	
	/**
	 * Version Id.
	 */
	private static final long serialVersionUID = -2481920279800353353L;

	/**
	 * Construct an AddTimeCard command with target.
	 * @param target - The target of this command.
	 */
	public AddTimeCardCommand(final TimeCard target) {
		super(target);
	
	}
	@Override
	public void execute() {
		receiver.execute(this); 
		
	}

}
