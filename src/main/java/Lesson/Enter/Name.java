package Lesson.Enter;

import java.util.Scanner;
import java.util.ArrayList;

public class Name {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter n=");
        int n = sc.nextInt();

        int a = 1, b = 3;
        ArrayList<Integer> lucasNumbers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            lucasNumbers.add(a);
            int c = a + b;
            a = b;
            b = c;
        }


        ArrayList<Integer> task = new ArrayList<>();
        for (int L : lucasNumbers) {
            int k = (int) Math.round(Math.cbrt(L + 1));
            if ((int) Math.pow(k, 3) == L + 1) {
                task.add(L);
            }
        }


        System.out.println(lucasNumbers);
        System.out.println(task);
    }
}
