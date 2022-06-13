package library_project.library;

import library_project.users.User;
import library_project.utils.ConsoleColors;
import library_project.utils.Menu;
import library_project.utils.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ISBNnum {
   private String ISBN;

    public ISBNnum(String ISBN) {
        this.ISBN = ISBN;
    }
    // method to check number is ISBN

    public static boolean isISBN(String number) {

        // declare variable
        int length = 0;

        // remove all hyphens
        number = number.replace("-", "");
        // remove all spaces
        number = number.replace(" ", "");

        // check result string is a number or not
        try {
            // except for the case where
            // ISBN-10 ends with X or x
            char ch = number.charAt(9);
            ch = Character.toUpperCase(ch);
            if (ch != 'X') {
                // don't store, only check
                Long.parseLong(number);
            }
        } catch (NumberFormatException nfe) {
            // not a number
            return false;
        }

        // find length
        length = number.length();
        if (length == 13) {
            return isISBN13(number);
        }
        else if (length == 10) {
            return isISBN10(number);
        }

        return false;
    }

    // method to check ISBN-10
    private static boolean isISBN10(String number) {

        // declare variables
        int sum = 0;
        int digit = 0;
        char ch = '\0';

        // add up to 9th digit
        for (int i = 1; i <= 9; i++) {
            ch = number.charAt(i - 1);
            digit = Character.getNumericValue(ch);
            sum += (i * digit);
        }

        // last digit
        ch = number.charAt(9);
        ch = Character.toUpperCase(ch);
        if (ch == 'X')
            sum += (10 * 10);
        else {
            digit = Character.getNumericValue(ch);
            sum += (digit * 10);
        }

        // check sum
        if (sum % 11 == 0)
            return true;

        return false;
    }

    private static boolean isISBN13(String number) {
        int sum = 0;
        int multiple = 0;
        char ch = '\0';
        int digit = 0;

        for (int i = 1; i <= 13; i++) {

            if (i % 2 == 0) {
                multiple = 3;
            } else {
                multiple = 1;
            }

            // fetch digit
            ch = number.charAt(i - 1);
            // convert it to number
            digit = Character.getNumericValue(ch);

            sum += (multiple * digit);
        }

        if (sum % 10 == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static ISBNnum setISBNnum(User user) {

        String ISBN = null;
        System.out.println("Type book ISBN (10 or 13 digits): ");
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(in);

        try {
            ISBN = br.readLine();

            while (!isISBN(ISBN)) {

                System.out.println('\n' + ConsoleColors.YELLOW + ISBN + ConsoleColors.RED + " is not valid!\n" + ConsoleColors.RESET);
                System.out.println(ConsoleColors.BLACK_BOLD + ConsoleColors.WHITE_BACKGROUND + "Options:" + "\n" +
                        ConsoleColors.RESET + "1. Try again"+ '\n' + "2. Exit to " + ConsoleColors.PURPLE_BOLD + "MAIN MENU" + ConsoleColors.RESET);
                int command = Integer.parseInt(br.readLine());

                if (command == 1) {
                    setISBNnum(user);
                }
                else if (command == 2) {
                    Library library = Library.generateMainLibrary();
                    System.out.println("Exiting to the " + ConsoleColors.PURPLE_BOLD + "MAIN MENU" + ConsoleColors.RESET);
                    Menu.options(library,user);
                } else {
                    System.out.println(ConsoleColors.RED + "Wrong input!" + ConsoleColors.RESET);
                    System.out.println("\nWould you like to try again? Y/N:");
                    if(Utils.yesOrNo()) {
                        setISBNnum(user);
                    }
                    else {
                        Library library = Library.generateMainLibrary();
                        Menu.options(library, user);
                    }
                    setISBNnum(user);
                }
            }

            System.out.println("\n( Book ISBN " + ConsoleColors.YELLOW + ISBN + ConsoleColors.RESET + " is valid! )\n");
        } catch (Exception e) {
            System.out.println( '\n' + ConsoleColors.RED + "Wrong input!" + ConsoleColors.RESET);
            System.out.println("\nWould you like to try with another ISBN number? Y/N:");
            if (Utils.yesOrNo()) {
                setISBNnum(user);
            }
            else {
                Library library = Library.generateMainLibrary();
                Menu.options(library, user);
            }

        }
        return new ISBNnum(ISBN);
    }

    public String getISBN() {
        return ISBN;
    }

}