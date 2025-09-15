package Lesson.Enter;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
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
