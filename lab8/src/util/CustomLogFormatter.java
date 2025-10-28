package util; // Або назва твого пакету

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomLogFormatter extends Formatter {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                    .withZone(ZoneId.systemDefault());

    @Override
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();


        sb.append(DATE_FORMATTER.format(Instant.ofEpochMilli(record.getMillis())));
        sb.append(" [");


        sb.append(record.getLevel().getLocalizedName());
        sb.append("] ");


        if (record.getSourceClassName() != null) {

            String className = record.getSourceClassName();
            int lastDot = className.lastIndexOf('.');
            if (lastDot > 0) {
                className = className.substring(lastDot + 1);
            }
            sb.append(className);
            if (record.getSourceMethodName() != null) {
                sb.append(".").append(record.getSourceMethodName());
            }
            sb.append("(): ");
        }


        sb.append(formatMessage(record));
        sb.append(System.lineSeparator());


        if (record.getThrown() != null) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
                sb.append(sw.toString());
                sb.append(System.lineSeparator());
            } catch (Exception ex) {

            }
        }


        sb.append("--------------------------------------------------").append(System.lineSeparator());

        return sb.toString();
    }
}