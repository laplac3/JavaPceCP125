package com.scg.net.cmd;

import com.scg.domain.TimeCard;

@SuppressWarnings("serial")
public final class AddTimeCardCommand extends AbstractCommand<TimeCard>{

	
	


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
