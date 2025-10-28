package core.commands;

import core.Playroom;

public class SaveToFileCommand implements Command {
    private final Playroom playroom;

    public SaveToFileCommand(Playroom playroom) {
        this.playroom = playroom;
    }

    @Override
    public void execute() {
        playroom.saveToFile();
    }
}