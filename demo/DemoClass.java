package demo;

import java.util.Scanner;

public class DemoClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int command = 0;

        while(true) {
            printMenu();
            command = sc.nextInt();

            if(command == 3) {
                break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("Type a number: ");
        System.out.println("Menu: ");
        System.out.println("1. Option 1");
        System.out.println("2. Option 2");
        System.out.println("3. Exit");
    }
}
