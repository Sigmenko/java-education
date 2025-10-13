package core.commands;

import core.Playroom;

public class SortToysCommand implements Command {
    private final Playroom playroom;

    public SortToysCommand(Playroom playroom) {
        this.playroom = playroom;
    }

    @Override
    public void execute() {
        playroom.sortToys();
    }
}