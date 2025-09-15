package Lesson.Enter;

import java.util.ArrayList;

public class LucasUtils {

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
