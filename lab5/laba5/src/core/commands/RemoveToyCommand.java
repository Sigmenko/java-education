package core.commands;

import core.Playroom;

public class RemoveToyCommand implements Command {
    private final Playroom playroom;

    public RemoveToyCommand(Playroom playroom) {
        this.playroom = playroom;
    }

    @Override
    public void execute() {
        playroom.removeToy();
    }
}