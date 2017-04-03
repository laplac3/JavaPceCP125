package com.scg.net.cmd;

import java.time.LocalDate;

import com.scg.domain.Invoice;

@SuppressWarnings("serial")
public final class CreateInvoiceCommand extends AbstractCommand<LocalDate> {


	/**
	 * Construct a addInvoiceCommand for target.
	 * @param target - the target for this commmand.
	 */
	public CreateInvoiceCommand(LocalDate target) {
		super(target);
		
	}
	@Override
	public void execute() {
		receiver.execute(this);
		
	}

}
