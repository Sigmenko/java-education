package core.commands;

import core.Playroom;

public class LoadFromFileCommand implements Command {
    private final Playroom playroom;

    public LoadFromFileCommand(Playroom playroom) {
        this.playroom = playroom;
    }

    @Override
    public void execute() {
        playroom.loadFromFile();
    }
}