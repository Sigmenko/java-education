package core;

public class ConsoleColors {
    // ANSI-коди для кольорів тексту
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    // ANSI-коди для жирного тексту
    public static final String BOLD = "\u001B[1m";

    /**
     * Повертає візуалізований рядок HP або Energy
     * @param current Поточне значення
     * @param max Максимальне значення
     * @param length Довжина смуги (кількість блоків)
     * @param color Колір для заповненої частини
     * @param symbol Символ для заповненої частини (наприклад, "█")
     * @return Візуалізований рядок
     */
    public static String getBar(int current, int max, int length, String color, String symbol) {
        if (max <= 0) return "";

        int filledBlocks = (int) Math.round((double) current * length / max);
        int emptyBlocks = length - filledBlocks;

        // Обмеження, щоб не виводити більше, ніж довжина
        if (filledBlocks < 0) filledBlocks = 0;
        if (filledBlocks > length) filledBlocks = length;
        emptyBlocks = length - filledBlocks;

        String filled = new String(new char[filledBlocks]).replace('\0', symbol.charAt(0));
        String empty = new String(new char[emptyBlocks]).replace('\0', '░');

        return BOLD + color + "[" + filled + RESET + empty + BOLD + color + "]" + RESET;
    }
}