package core.commands;

import core.Playroom;

public class FindToysByRangeCommand implements Command {
    private final Playroom playroom;

    public FindToysByRangeCommand(Playroom playroom) {
        this.playroom = playroom;
    }

    @Override
    public void execute() {
        playroom.findToysByRange();
    }
}