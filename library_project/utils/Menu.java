package library_project.utils;

import library_project.library.Book;
import library_project.library.Library;
import library_project.users.User;
import library_project.users.Users;

import java.io.Console;
import java.util.Scanner;

public class Menu {

    public static void start() {
        firstScreen();
    }

    private static void firstScreen() {
        System.out.println();
        System.out.println("|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|");
        System.out.println("| " + ConsoleColors.BLUE + "DIGITAL LIBRARY " + ConsoleColors.PURPLE + "\"A-TEAM\"" + ConsoleColors.BLUE + " HOMEPAGE" + ConsoleColors.RESET + " |");
        System.out.println("|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|\n\n");
        System.out.println(ConsoleColors.YELLOW + "    1. Register        " + ConsoleColors.GREEN + "2. Log in\n\n\n\n" + ConsoleColors.RESET);

        User currentUser;
        Library mainLibrary = Library.generateMainLibrary();

        int command = getInput();
        while(command < 1 || command > 2) {
            System.out.println("Type either 1 or 2: ");
            command = getInput();
        }
        if(command == 1) {
            Users.register();
            firstScreen();
        }
        else {
            currentUser = Users.logIn();
            options(mainLibrary, currentUser);
        }
    }

    private static void options(Library library, User user) {
        System.out.println();
        System.out.println(ConsoleColors.PURPLE + "-=-=-=-=-=-=-");
        System.out.println("| " + ConsoleColors.BLUE + "MAIN MENU" + ConsoleColors.PURPLE + " |");
        System.out.println("-=-=-=-=-=-=-" + ConsoleColors.RESET);
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
            case 2:
                myLibrary(library, user);
                break;
            case 3:
                favouriteBooks(library, user);
                break;
            case 4:
                addNewBook(library, user);
                break;
            case 5:
                accountInfo(library, user);
                break;
            case 6:
                firstScreen();
        }

    }

    private static void exploreBooks(Library library, User user) {
        System.out.println();
        System.out.println(ConsoleColors.PURPLE + "-=-=-=-=-=-=-=-=-=-");
        System.out.println("| " + ConsoleColors.BLUE + "EXPLORE BOOKS" + ConsoleColors.PURPLE + " |");
        System.out.println("-=-=-=-=-=-=-=-=-=-" + ConsoleColors.RESET);

        int bookCounter = 0;
        if(library.getBooks() == null) {
            System.out.println("No books in our library yet. Sorry!");
            System.out.println("Returning to main menu...\n\n");
            options(library, user);
        }
        else {
            System.out.println("Choose a book to interact with: \n");
            boolean color = true;

            Book[] allBooks = new Book[library.getBooks().size()];
            int index = 0;
            for (Book book : library.getBooks()) {
                allBooks[index] = book;
                index++;
                if(color) {
                    System.out.print(ConsoleColors.BLUE);
                    color = false;
                }
                else {
                    System.out.print(ConsoleColors.CYAN);
                    color = true;
                }
                System.out.println(++bookCounter + ". " + book.getBookName() + ", written by " + book.getAuthor() + " , Rating: " + ((book.getAverageRating() > 0) ? book.getAverageRating() : "Not yet rated") + ConsoleColors.RESET);
            }
            System.out.println(++bookCounter + ". Go back");

            int command = getInput();

            while (command < 1 || command > bookCounter) {
                System.out.println("You need to type a number between 1-" + bookCounter + ":");
                command = getInput();
            }
            if(command == bookCounter) {
                options(library, user);
            }
            else {
                bookInteract(library, user, allBooks[command-1]);
            }
            //getBookByID(int ID) - a method which returns a book object by provided ID. It should find it in the file


        }
    }

    private static void myLibrary(Library library, User user) {
        System.out.println();
        System.out.println(ConsoleColors.PURPLE + "-=-=-=-=-=-=-=-=-=-");
        System.out.println("| " + ConsoleColors.BLUE + "MY LIBRARY" + ConsoleColors.PURPLE + " |");
        System.out.println("-=-=-=-=-=-=-=-=-=-" +  ConsoleColors.RESET);

        int bookCounter = 0;
        if(user.getPersonalLibrary().getBooks() == null) {
            System.out.println("You don't have any books added here yet.");
            System.out.println("Returning to main menu...\n\n");
            options(library, user);

        }
        else {
            System.out.println("Choose a book to interact with: \n");

            boolean color = true;
            for (Book book : user.getPersonalLibrary().getBooks()) {
                if(color) {
                    System.out.print(ConsoleColors.BLUE);
                    color = false;
                }
                else {
                    System.out.print(ConsoleColors.CYAN);
                    color = true;
                }
                System.out.println(++bookCounter + ". " + book.getBookName() + ", written by " + book.getAuthor() + " , Rating: " + ((book.getAverageRating() > 0) ? book.getAverageRating() : "Not yet rated") + ConsoleColors.RESET);
            }
            System.out.println((++bookCounter) + ". Go back");

            int command = getInput();

            while (command < 1 || command > bookCounter) {
                System.out.println("You need to type a number between 1-" + bookCounter + ":");
                command = getInput();
            }
            if(command == bookCounter) {
                options(library, user);
            }
        }
    }

    private static void favouriteBooks(Library library, User user) {
    }

    private static void addNewBook(Library library, User user) {
        Book newBook = Book.addNewBook();
        newBook.writeToFile();
        library = Library.generateMainLibrary();
        options(library, user);
    }

    private static void accountInfo(Library library, User user) {
        System.out.println();
        System.out.println(ConsoleColors.PURPLE + "-=-=-=-=-=-=-=-=");
        System.out.println("| " + ConsoleColors.BLUE + "ACCOUNT INFO" + ConsoleColors.PURPLE + " |");
        System.out.println("-=-=-=-=-=-=-=-=\n\n" +  ConsoleColors.RESET);
        System.out.println("YOUR NAME: " + ConsoleColors.CYAN + user.getFirstName() + ConsoleColors.RESET);
        System.out.println("USERNAME: " + ConsoleColors.CYAN + user.getUsername() + ConsoleColors.RESET);
        System.out.println("EMAIL ADDRESS: " + ConsoleColors.CYAN + user.getEmail() + ConsoleColors.RESET);
        System.out.println();
        System.out.println("Choose one of the options: ");
        System.out.println("1. Change password");
        System.out.println("2. Change email address");
        System.out.println("3. Go back");

        int input = getInput();
        while(input < 1 || input > 3) {
            System.out.println("Type a number between 1-3: ");
            input = getInput();
        }
        if(input == 3) {
            options(library, user);
        }
        else if (input == 2) {
            user.changeEmail();
            accountInfo(library, user);
        }
    }

    private static void bookInteract(Library library, User user, Book book) {
        System.out.println("Selected book: " + ConsoleColors.CYAN + book.getBookName() + ConsoleColors.RESET);
        System.out.println();
        System.out.println("Options: ");
        System.out.println("1. Rate");
        System.out.println("2. Leave a review");
        System.out.println("3. Add to \"My Library\"");
        System.out.println("4. Add to \"Favourites\"");
        System.out.println("5. Edit book");
        System.out.println("6. Go back");

        int input = getInput();
        while(input < 1 || input > 6) {
            System.out.println("Type a number between 1-6: ");
            input = getInput();
        }
        switch(input) {
            case 1:
            case 2:
                return;
            case 3:
                user.addBookToLibrary(book);
                options(library, user);
            case 4:
                user.addBookToFavourites(book);
                options(library, user);
            case 5:
                //editBook(user) method
                return;
            case 6:
                options(library, user);
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
