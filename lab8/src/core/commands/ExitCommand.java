package core.commands;

import util.AppLogger;
import java.util.logging.Logger;

public class ExitCommand implements Command {
    private static final Logger logger = AppLogger.getLogger();

    @Override
    public void execute() {
        // Просто логуємо, бо MainMenu обробляє вихід
        // logger.info("Користувач обрав вихід з програми.");
    }
}