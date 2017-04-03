package com.scg.net.cmd;

/**
 * The command to disconnect, this command has no target.
 *
 * @author Russ Moul and Neil Nevitt
 */
@SuppressWarnings("serial")
public final class DisconnectCommand extends AbstractCommand<Void> {

    /**
     * Construct an DisconnectCommand.
     */
    public DisconnectCommand() {
        super();
    }

    /**
     * Execute this Command by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        getReceiver().execute(this);
    }
}
