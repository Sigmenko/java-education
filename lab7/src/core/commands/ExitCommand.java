package core.commands;

public class ExitCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Завершення роботи програми...");
    }
}