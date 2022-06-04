package library_project.utils;

import library_project.library.Book;
import library_project.library.Library;
import library_project.users.User;
import library_project.users.Users;

import java.io.Console;
import java.security.cert.CertificateParsingException;
import java.util.Scanner;

public class Menu {

    public static void start() {
        firstScreen();
    }

    private static void firstScreen() {
        System.out.println();
        System.out.println("|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|");
        System.out.println("| " + ConsoleColors.BLUE + "   DIGITAL LIBRARY " + ConsoleColors.PURPLE + "\"A-TEAM\"" + ConsoleColors.BLUE + " HOMEPAGE   " + ConsoleColors.RESET + " |");
        System.out.println("| " + ConsoleColors.BLUE + "      created by" + ConsoleColors.PURPLE + " Rosen " + ConsoleColors.BLUE + " & " + ConsoleColors.PURPLE + " Stefi  " + ConsoleColors.RESET + "      |");
        System.out.println("|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|\n\n");
        System.out.println(ConsoleColors.YELLOW + "       1. Register        " + ConsoleColors.GREEN + "2. Log in\n" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED +    "                  3. Exit\n\n\n" + ConsoleColors.RESET);

        User currentUser;
        Library mainLibrary = Library.generateMainLibrary();

        int command = getInput();
        while(command < 1 || command > 3) {
            System.out.println("Type a number between 1-3: ");
            command = getInput();
        }
        if(command == 1) {
            Users.register();
            firstScreen();
        }
        else if (command == 2) {
            currentUser = Users.logIn();
            options(mainLibrary, currentUser);
        }
        else {
            System.exit(0);
        }
    }

    private static void options(Library library, User user) {
        System.out.println();
        System.out.println(ConsoleColors.GREEN + "   currently logged in as: " + ConsoleColors.CYAN + user.getUsername());
        System.out.println(ConsoleColors.PURPLE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("|" + ConsoleColors.BLUE + "                MAIN MENU                " + ConsoleColors.PURPLE + "|");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ConsoleColors.CYAN);

        System.out.println(ConsoleColors.PURPLE + "|" + ConsoleColors.CYAN + "  1. Explore books  " + ConsoleColors.PURPLE + "|" + ConsoleColors.CYAN + "   2. My library    " + ConsoleColors.PURPLE + "|");
        System.out.println(ConsoleColors.PURPLE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ConsoleColors.CYAN);
        System.out.println(ConsoleColors.PURPLE + "|" + ConsoleColors.CYAN + " 3. Favourite books " + ConsoleColors.PURPLE + "|" + ConsoleColors.CYAN + "  4. Add new book   " + ConsoleColors.PURPLE + "|");
        System.out.println(ConsoleColors.PURPLE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("|" + ConsoleColors.BLUE + "  5. Account info   " + ConsoleColors.PURPLE + "|" + ConsoleColors.RED + "     6. Log out     " + ConsoleColors.PURPLE + "|");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ConsoleColors.RESET);

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
        System.out.println(ConsoleColors.PURPLE +    "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("|" + ConsoleColors.BLUE + "                                        EXPLORE BOOKS                                        " + ConsoleColors.PURPLE + "|");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ConsoleColors.RESET);

        int bookCounter = 0;
        if(library.getBooks() == null) {
            System.out.println(ConsoleColors.YELLOW + "\nNo books in our library yet. Sorry!" + ConsoleColors.RESET);
            System.out.println("Returning to main menu...\n\n");
            options(library, user);
        }
        else {
            System.out.println("\n                                CHOOSE A BOOK TO INTERACT WITH: \n");
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
                System.out.println(ConsoleColors.PURPLE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ConsoleColors.RESET);
            }
            System.out.println(ConsoleColors.RED + ++bookCounter + ". Go back\n" + ConsoleColors.RESET);
            System.out.println("Type a number between 1-" + bookCounter + ":");

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
        System.out.println(ConsoleColors.PURPLE + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println("|" + ConsoleColors.BLUE + "                MY LIBRARY                " + ConsoleColors.PURPLE + "|");
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-" + ConsoleColors.RESET);

        int bookCounter = 0;
        if(user.getPersonalLibrary().getBooks().size() == 0) {
            System.out.println("\nYou don't have any books added here yet.");
            System.out.println("Returning to main menu...\n\n");
            options(library, user);

        }
        else {
            System.out.println("\nChoose a book to interact with: \n");
            Book[] allBooks = user.getPersonalLibrary().getBooks().toArray(new Book[0]);

            boolean color = true;
            for (Book book : allBooks) {
                if(color) {
                    System.out.print(ConsoleColors.BLUE);
                    color = false;
                }
                else {
                    System.out.print(ConsoleColors.CYAN);
                    color = true;
                }
                System.out.println(++bookCounter + ". " + book.getBookName() + ", written by " + book.getAuthor() + " , Rating: " + ((book.getAverageRating() > 0) ? book.getAverageRating() : "Not yet rated") + ConsoleColors.RESET);
                System.out.println(ConsoleColors.PURPLE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ConsoleColors.RESET);

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
            else {
                libraryBookInteract(library, user, allBooks[command-1]);
            }

        }
    }

    private static void favouriteBooks(Library library, User user) {
        System.out.println();
        System.out.println(ConsoleColors.PURPLE + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println("|" + ConsoleColors.BLUE + "              FAVOURITE BOOKS             " + ConsoleColors.PURPLE + "|");
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-" + ConsoleColors.RESET);
        System.out.println("\nChoose a book to interact with: \n");

        int bookCounter = 0;
        boolean color = true;

        Book[] favouriteBooks = user.getFavouriteBooks();
        for(Book book : favouriteBooks) {
            if (book != null) {
                if (color) {
                    System.out.print(ConsoleColors.BLUE);
                    color = false;
                } else {
                    System.out.print(ConsoleColors.CYAN);
                    color = true;
                }
                System.out.println(++bookCounter + ". " + book.getBookName() + ", written by " + book.getAuthor() + " , Rating: " + ((book.getAverageRating() > 0) ? book.getAverageRating() : "Not yet rated") + ConsoleColors.RESET);
                System.out.println(ConsoleColors.PURPLE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ConsoleColors.RESET);

            }
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
            else {
                favouriteBookInteract(library, user, favouriteBooks[command-1]);
            }
        }

    private static void addNewBook(Library library, User user) {
        Book newBook = Book.addNewBook(user.getUsername());
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
        if (input == 1) {
            user.changePassword();
            accountInfo(library, user);
        }
        else if (input == 2) {
            user.changeEmail();
            accountInfo(library, user);
        }
        else {
            options(library, user);
        }

    }

    private static void libraryBookInteract(Library library, User user, Book book) {
        System.out.println("\nSelected book: " + ConsoleColors.CYAN + book.getBookName().toUpperCase() + ConsoleColors.RESET);
        System.out.println();
        System.out.println("Options: \n " + ConsoleColors.CYAN);
        System.out.println("1. Rate");
        System.out.println(ConsoleColors.GREEN + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("2. Leave a review");
        System.out.println(ConsoleColors.GREEN + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("3. Add to \"Favourites\"");
        System.out.println(ConsoleColors.GREEN + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("4. Show book resume");
        System.out.println(ConsoleColors.GREEN + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("5. Edit book");
        System.out.println(ConsoleColors.GREEN + "------------------------------|" + ConsoleColors.RESET);
        System.out.println("6. Go back");

        int input = getInput();
        while(input < 1 || input > 7) {
            System.out.println("Type a number between 1-7: ");
            input = getInput();
        }
        switch(input) {
            case 1:
            case 2:
                return;
            case 3:
                user.addBookToFavourites(book);
                myLibrary(library, user);
            case 4:
                System.out.println(ConsoleColors.CYAN);
                System.out.println(book.getResume());
                System.out.println(ConsoleColors.RESET);
                myLibrary(library, user);
            case 5:
                //editBook(user) method
            case 6:
                options(library, user);
        }
    }

    private static void favouriteBookInteract(Library library, User user, Book book) {
        System.out.println("\nSelected book: " + ConsoleColors.CYAN + book.getBookName().toUpperCase() + ConsoleColors.RESET);
        System.out.println();
        System.out.println("Options: \n " + ConsoleColors.CYAN);
        System.out.println("1. Rate");
        System.out.println(ConsoleColors.GREEN + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("2. Leave a review");
        System.out.println(ConsoleColors.GREEN + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("3. Add to \"My Library\"");
        System.out.println(ConsoleColors.GREEN + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("4. Remove from \"Favourites\"");
        System.out.println(ConsoleColors.GREEN + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("5. Show book resume");
        System.out.println(ConsoleColors.GREEN + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("6. Edit book");
        System.out.println(ConsoleColors.GREEN + "------------------------------|" + ConsoleColors.RESET);
        System.out.println("7. Go back");

        int input = getInput();
        while(input < 1 || input > 7) {
            System.out.println("Type a number between 1-7: ");
            input = getInput();
        }
        switch(input) {
            case 1:
            case 2:
                return;
            case 3:
                user.addBookToLibrary(book);
                favouriteBooks(library, user);
            case 4:
                user.removeFromFavourites(book);
                favouriteBooks(library, user);
            case 5:
                System.out.println(ConsoleColors.CYAN);
                System.out.println(book.getResume());
                System.out.println(ConsoleColors.RESET);
                favouriteBooks(library, user);
            case 6:
                //editBook(user) method
            case 7:
                options(library, user);
        }
    }

    private static void bookInteract(Library library, User user, Book book) {
        System.out.println("\nSelected book: " + ConsoleColors.CYAN + book.getBookName().toUpperCase() + ConsoleColors.RESET);
        System.out.println();
        System.out.println("Options: ");
        System.out.println("1. Rate");
        System.out.println("2. Leave a review");
        System.out.println("3. Add to \"My Library\"");
        System.out.println("4. Add to \"Favourites\"");
        System.out.println("5. Show book resume");
        System.out.println("6. Edit book");
        System.out.println("7. Go back");

        int input = getInput();
        while(input < 1 || input > 7) {
            System.out.println("Type a number between 1-7: ");
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
                System.out.println(ConsoleColors.CYAN);
                book.showResume();
                System.out.println(ConsoleColors.RESET);
                options(library, user);
            case 6:
                book.editBook(user.getUsername());
            case 7:
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
