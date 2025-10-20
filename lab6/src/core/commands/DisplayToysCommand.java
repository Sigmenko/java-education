package core.commands;

import core.Playroom;

public class DisplayToysCommand implements Command {
    private final Playroom playroom;

    public DisplayToysCommand(Playroom playroom) {
        this.playroom = playroom;
    }

    @Override
    public void execute() {
        playroom.displayToys();
    }
}