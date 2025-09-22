package utils;
import java.util.ArrayList;
/**
 * Утилітний клас для роботи з числами Люкаса.
 * Містить методи для генерації послідовності та пошуку спеціальних чисел.
 */
public class LucasUtils {

    /**
     * Генерує перші {@code n} чисел Люкаса.
     *
     * @param n кількість чисел, які потрібно згенерувати
     * @return список перших {@code n} чисел Люкаса
     */
    public static ArrayList<Integer> generateLucasNumbers(int n) {
        int a = 1, b = 3;
        ArrayList<Integer> lucasNumbers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            lucasNumbers.add(a);
            int c = a + b;
            a = b;
            b = c;
        }
        return lucasNumbers;
    }

    /**
     * Знаходить усі числа Люкаса, для яких {@code L+1} є кубом цілого числа.
     *
     * @param lucasNumbers список чисел Люкаса
     * @return список спеціальних чисел Люкаса
     */
    public static ArrayList<Integer> findSpecialLucas(ArrayList<Integer> lucasNumbers) {
        ArrayList<Integer> task = new ArrayList<>();
        for (int L : lucasNumbers) {
            int k = (int) Math.round(Math.cbrt(L + 1));
            if ((int) Math.pow(k, 3) == L + 1) {
                task.add(L);
            }
        }
        return task;
    }
}
