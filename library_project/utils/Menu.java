package library_project.utils;

import library_project.library.Book;
import library_project.library.Library;
import library_project.users.User;

import java.util.Scanner;

public class Menu {

    public static void options(Library library, User user) {
        System.out.println(ConsoleColors.GREEN + "MAIN MENU");
        System.out.println("-----------------------" + ConsoleColors.RESET);
        System.out.println("1. Explore books");
        System.out.println("2. My library");
        System.out.println("3. Favourite books");
        System.out.println("4. Add a new book");
        System.out.println("5. Log out");

        int command = getInput();

        while(command < 1 || command > 5) {
            System.out.println("You need to type a number between 1-5:");
            command = getInput();
        }
    }

    private static void exploreBooks(Library library, User user) {
        System.out.println("EXPLORE BOOKS");
        System.out.println("--------------");
        System.out.println("Choose a book to interact with: \n");

        int bookCounter = 0;
        for(Book book : library.getBooks()) {
            System.out.println(++bookCounter + ". " + book.getName() + ", written by " + book.getAuthor() + " , Rating: " + book.getBookReview());
        }

        int command = getInput();

        while(command < 1 || command > bookCounter) {
            System.out.println("You need to type a number between 1-" + bookCounter + ":");
            command = getInput();
        }
    }

    private static int getInput() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while(true) {
            if(input.isEmpty()) {
                System.out.println("You need to type something!");
                input = sc.nextLine();
                continue;
            }
            try {
                return Integer.parseInt(input);
            }
            catch (NumberFormatException e) {
                System.out.println("You need to type a number!");
                input = sc.nextLine();
            }

        }

    }
}
