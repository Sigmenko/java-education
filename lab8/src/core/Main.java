package core;

import util.AppLogger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = AppLogger.getLogger();

    public static void main(String[] args) {
        logger.info("========================================");
        logger.info(">>> Запуск програми Playroom <<<");
        logger.info("========================================");

        Playroom playroom = null;
        MainMenu menu = null;

        try {
            playroom = new Playroom(1000.0f); // Встановлюємо бюджет
            menu = new MainMenu(playroom);
            menu.display(); // Запускаємо меню

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Критична помилка під час виконання програми", e);
            System.err.println("!!! Сталася критична помилка. Дивіться деталі в логах та email. !!!");
        } finally {
            // Цей блок виконається навіть якщо була помилка, перед завершенням програми
            logger.info("========================================");
            logger.info(">>> Завершення роботи програми Playroom <<<");
            logger.info("========================================");

            throw new IllegalStateException();
        }
    }
}