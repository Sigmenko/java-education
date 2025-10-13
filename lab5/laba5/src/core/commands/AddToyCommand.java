package core.commands;

import core.Playroom;

public class AddToyCommand implements Command {
    private final Playroom playroom;

    public AddToyCommand(Playroom playroom) {
        this.playroom = playroom;
    }

    @Override
    public void execute() {
        playroom.addToy();
    }
}