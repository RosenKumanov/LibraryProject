package library_project.utils;

import java.util.Scanner;

public class Utils {
    private Utils () {} //с този прайвът конструктор забраняваме да се създава обект Ютилс в други класове


    public static void stopTheSystem(String message) {
        System.err.println(message); //color red
        System.exit(-1);
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pressKeyToContinue() {
        try {
            System.out.println("\nPress "+ ConsoleColors.GREEN + "Enter" + ConsoleColors.RESET + " to continue: ");
            System.in.read();
        } catch (Exception e) {
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean yesOrNo() {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        while(!input.trim().equalsIgnoreCase("Y") && !input.trim().equalsIgnoreCase("N")) {
            System.out.println(ConsoleColors.YELLOW + "Wrong input! Type either Y or N: " + ConsoleColors.RESET);
            input = sc.nextLine();
        }
        return input.equalsIgnoreCase("Y");
    }

}
