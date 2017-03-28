package com.scg.net.cmd;

import java.time.LocalDate;

import com.scg.domain.Invoice;

public final class CreateInvoiceCommand extends AbstractCommand<LocalDate> {

	/**
	 * Version Id.
	 */
	private static final long serialVersionUID = -58883860440228353L;

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
