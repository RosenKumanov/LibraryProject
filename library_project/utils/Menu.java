package library_project.utils;

import library_project.library.Book;
import library_project.library.Library;
import library_project.users.User;
import library_project.users.Users;

import java.util.Scanner;

public class Menu {

    public static void firstScreen() {
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println("| " + ConsoleColors.BLUE + "DIGITAL LIBRARY " + ConsoleColors.PURPLE + "\"A-TEAM\"" + ConsoleColors.BLUE + " HOMEPAGE" + ConsoleColors.RESET + " |");
        System.out.println("-------------------------------------\n\n");
        System.out.println(ConsoleColors.YELLOW + "1. Register        " + ConsoleColors.GREEN + "2. Log in" + ConsoleColors.RESET);

        User currentUser;
        Library mainLibrary = new Library();

        int command = getInput();
        while(command < 1 || command > 2) {
            System.out.println("Type either 1 or 2: ");
            command = getInput();
        }
        if(command == 1) {
            currentUser = Users.register();
            firstScreen();
        }
        else {
            currentUser = Users.logIn();
            options(mainLibrary, currentUser);
        }
    }

    private static void options(Library library, User user) {
        System.out.println();
        System.out.println("-=-=-=-=-=-=-");
        System.out.println("| " + ConsoleColors.BLUE + "MAIN MENU" + ConsoleColors.RESET + " |");
        System.out.println("-=-=-=-=-=-=-");
        System.out.println("1. Explore books");
        System.out.println("2. My library");
        System.out.println("3. Favourite books");
        System.out.println("4. Add a new book");
        System.out.println("5. Account info");
        System.out.println("6. Log out");

        int command = getInput();

        while(command < 1 || command > 6) {
            System.out.println("You need to type a number between 1-6:");
            command = getInput();
        }

        switch(command) {
            case 1:
                exploreBooks(library, user);
                break;
        }

    }

    private static void exploreBooks(Library library, User user) {
        System.out.println();
        System.out.println("-=-=-=-=-=-=-=-=-=-");
        System.out.println("| " + ConsoleColors.BLUE + "EXPLORE BOOKS" + ConsoleColors.RESET + " |");
        System.out.println("-=-=-=-=-=-=-=-=-=-");
        System.out.println("Choose a book to interact with: \n");

        int bookCounter = 0;
        if(library.getBooks() == null) {
            System.out.println("No books in our library yet. Sorry!");

        }
        else {
            for (Book book : library.getBooks()) {
                System.out.println(++bookCounter + ". " + book.getBookName() + ", written by " + book.getAuthor() + " , Rating: " + book.getBookReview());
            }

            int command = getInput();

            while (command < 1 || command > bookCounter) {
                System.out.println("You need to type a number between 1-" + bookCounter + ":");
                command = getInput();
            }
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
