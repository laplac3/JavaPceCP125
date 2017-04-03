package com.scg.net.cmd;

import com.scg.domain.Consultant;

/**
 * The command to add a Consultant to a list maintained by the server.
 *
 * @author Russ Moul and Neil Nevitt
 */
@SuppressWarnings("serial")
public final class AddConsultantCommand extends AbstractCommand<Consultant> {

    /**
     * Construct an AddConsultantCommand with a target.
     *
     * @param target The target of this Command.
     */
    public AddConsultantCommand(final Consultant target) {
        super(target);
    }

    /**
     * Execute this Command by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        getReceiver().execute(this);
    }
}
