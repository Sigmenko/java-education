package util;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import java.util.logging.*;

public class AppLogger {

    private static final Logger logger = Logger.getLogger("PlayroomLogger");
    private static final String LOG_FILE = "playroom.log";

    static {
        try {

            CustomLogFormatter formatter = new CustomLogFormatter();


            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(formatter);
            fileHandler.setLevel(Level.INFO);
            logger.addHandler(fileHandler);


            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(formatter);
            consoleHandler.setLevel(Level.INFO);
            logger.addHandler(consoleHandler);

            logger.addHandler(new EmailHandler());


            logger.setUseParentHandlers(false);
            logger.setLevel(Level.ALL);

        } catch (IOException e) {
            System.err.println("Помилка ініціалізації логгера: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    private static class EmailHandler extends Handler {

        public EmailHandler() {
            setLevel(Level.SEVERE);
        }

        @Override
        public void publish(LogRecord record) {
            if (isLoggable(record)) {
                sendEmail(record);
            }
        }

        private void sendEmail(LogRecord record) {
            Dotenv dotenv = Dotenv.configure()
                    .directory("C:/Users/Morkvanius/IdeaProjects/java-education")
                    .ignoreIfMissing()
                    .load();
            if (dotenv == null) {
                System.err.println("Помилка: Не вдалося завантажити .env файл.");
                return;
            }

            final String host = dotenv.get("SMTP_HOST");
            final String port = dotenv.get("SMTP_PORT");
            final String user = dotenv.get("SMTP_USER");
            final String pass = dotenv.get("SMTP_PASS");
            final String from = dotenv.get("EMAIL_FROM");
            final String to = dotenv.get("EMAIL_TO");

            if (host == null || port == null || user == null || pass == null || to == null) {
                System.err.println("Помилка: Не всі SMTP налаштування знайдені у .env файлі.");
                return;
            }

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject("КРИТИЧНА ПОМИЛКА в Playroom App");

                StringBuilder messageText = new StringBuilder();
                messageText.append("Рівень: ").append(record.getLevel()).append("\n");
                messageText.append("Час: ").append(new java.util.Date(record.getMillis())).append("\n");
                messageText.append("Клас: ").append(record.getSourceClassName()).append("\n");
                messageText.append("Метод: ").append(record.getSourceMethodName()).append("\n");
                messageText.append("Повідомлення: ").append(record.getMessage()).append("\n");

                if (record.getThrown() != null) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    record.getThrown().printStackTrace(pw);
                    messageText.append("\nСтек викликів:\n").append(sw.toString());
                }
                message.setText(messageText.toString());

                Transport.send(message);
                System.out.println(">>> Повідомлення про критичну помилку надіслано на email.");

            } catch (MessagingException e) {
                System.err.println("Помилка надсилання email: " + e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public void flush() {}
        @Override
        public void close() throws SecurityException {}
    }
}