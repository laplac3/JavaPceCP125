package com.scg.net.cmd;

import java.time.LocalDate;

/**
 * The command to create invoices for a specified month.
 *
 * @author Russ Moul and Neil Nevitt
 */
@SuppressWarnings("serial")
public final class CreateInvoicesCommand extends AbstractCommand<LocalDate> {
    /**
     * Construct a CreateInvoicesCommand with a target month, which should be
     * obtained by getting the desired month constant from LocalDate.
     *
     * @param target the target month.
     */
    public CreateInvoicesCommand(final LocalDate target) {
        super(target);
    }

    /**
     * Execute this command by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        getReceiver().execute(this);
    }
}
