package Lesson.Enter;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Головний клас програми.
 * Зчитує кількість чисел, генерує послідовність Люкаса та виводить результат.
 */
public class Main {

    /**
     * Точка входу в програму.
     *
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter n = ");
        int n = sc.nextInt();

        ArrayList<Integer> lucasNumbers = LucasUtils.generateLucasNumbers(n);
        ArrayList<Integer> task = LucasUtils.findSpecialLucas(lucasNumbers);

        System.out.println("Lucas numbers: " + lucasNumbers);
        System.out.println("Special Lucas numbers: " + task);

        sc.close();
    }
}
