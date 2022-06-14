package library_project.utils;

import library_project.library.Book;
import library_project.library.Library;
import library_project.users.User;
import library_project.users.UserRepo;

import java.util.Scanner;

public class Menu {

    public static void start() {
        firstScreen();
    }

    private static void firstScreen() {
        Utils.clearConsole();
        System.out.println();
        System.out.println("|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|");
        System.out.println("| " + ConsoleColors.BLUE + "   DIGITAL LIBRARY " + ConsoleColors.PURPLE + "\"A-TEAM\"" + ConsoleColors.BLUE + " HOMEPAGE   " + ConsoleColors.RESET + " |");
        System.out.println("| " + ConsoleColors.BLUE + "      created by" + ConsoleColors.PURPLE + " Rosen " + ConsoleColors.BLUE + " & " + ConsoleColors.PURPLE + " Stefi  " + ConsoleColors.RESET + "      |");
        System.out.println("|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|\n\n");
        System.out.println(ConsoleColors.YELLOW + "      1. Register         " + ConsoleColors.GREEN + "2. Log in\n\n" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED + "                  3. Exit\n\n\n" + ConsoleColors.RESET);

        User currentUser;
        Library mainLibrary = Library.generateMainLibrary();

        int command = getInput();
        while (command < 1 || command > 3) {
            System.out.println("Type a number between 1-3: ");
            command = getInput();
        }
        if (command == 1) {
            UserRepo.register();
            firstScreen();
        } else if (command == 2) {
            currentUser = UserRepo.logIn();
            options(mainLibrary, currentUser);
        } else {
            System.out.println(ConsoleColors.CYAN + "Come again!" + ConsoleColors.RESET);
            System.exit(0);
        }
    }

    public static void options(Library library, User user) {
        Utils.clearConsole();
        System.out.println();
        System.out.println(ConsoleColors.GREEN + "   currently logged in as: " + ConsoleColors.CYAN + user.getUsername());
        System.out.println(ConsoleColors.PURPLE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("|" + ConsoleColors.BLUE + "                MAIN MENU                " + ConsoleColors.PURPLE + "|");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ConsoleColors.CYAN);

        System.out.println(ConsoleColors.PURPLE + "|" + ConsoleColors.CYAN + " 1. Explore books   " + ConsoleColors.PURPLE + "|" + ConsoleColors.CYAN + " 2. My library      " + ConsoleColors.PURPLE + "|");
        System.out.println(ConsoleColors.PURPLE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ConsoleColors.CYAN);
        System.out.println(ConsoleColors.PURPLE + "|" + ConsoleColors.CYAN + " 3. Favourite books " + ConsoleColors.PURPLE + "|" + ConsoleColors.CYAN + " 4. Add new book    " + ConsoleColors.PURPLE + "|");
        System.out.println(ConsoleColors.PURPLE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("|" + ConsoleColors.BLUE + " 5. Account info    " + ConsoleColors.PURPLE + "|" + ConsoleColors.RED + " 6. Log out         " + ConsoleColors.PURPLE + "|");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ConsoleColors.RESET);

        int command = getInput();

        while (command < 1 || command > 6) {
            System.out.println("You need to type a number between 1-6:");
            command = getInput();
        }

        switch (command) {
            case 1:
                exploreBooks(user);
                break;
            case 2:
                myLibrary(user);
                break;
            case 3:
                favouriteBooks(user);
                break;
            case 4:
                addNewBook(user);
                break;
            case 5:
                accountInfo(library, user);
                break;
            case 6:
                firstScreen();
        }

    }

    private static void exploreBooks(User user) {
        Utils.clearConsole();
        Library library = Library.generateMainLibrary();

        System.out.println();
        System.out.println(ConsoleColors.PURPLE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("|" + ConsoleColors.BLUE + "                                        EXPLORE BOOKS                                        " + ConsoleColors.PURPLE + "|");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ConsoleColors.RESET);

        int bookCounter = 0;
        if (library.getBooks() == null) {
            System.out.println(ConsoleColors.YELLOW + "\nNo books in our library yet. Sorry!" + ConsoleColors.RESET);
            System.out.println("Returning to main menu...\n\n");
            Utils.pressKeyToContinue();
            options(library, user);
        } else {
            printAllBooks(library, user);

        }
    }

    private static void myLibrary(User user) {
        Utils.clearConsole();
        Library library = Library.generateMainLibrary();

        System.out.println();
        System.out.println(ConsoleColors.PURPLE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("|" + ConsoleColors.BLUE + "                                         MY LIBRARY                                          " + ConsoleColors.PURPLE + "|");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ConsoleColors.RESET);

        int bookCounter = 0;
        if (user.getPersonalLibrary().getBooks().size() == 0) {
            System.out.println("\nYou don't have any books added here yet.");
            System.out.println("Returning to main menu...\n\n");
            Utils.pressKeyToContinue();
            options(library, user);
        } else {
            printAllLibraryBooks(library, user);
        }
    }

    private static void favouriteBooks(User user) {
        Library library = Library.generateMainLibrary();

        System.out.println();
        System.out.println(ConsoleColors.PURPLE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("|" + ConsoleColors.BLUE + "                                  MY TOP 10 FAVOURITE BOOKS                                  " + ConsoleColors.PURPLE + "|");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ConsoleColors.RESET);

        Book[] favouriteBooks = user.getFavouriteBooks();

        if (favouriteBooks[0] == null) {
            System.out.println("\nNo books added to \"Favourites\" yet - returning to main menu...\n");
            Utils.pressKeyToContinue();
            options(library, user);
        } else {

            int bookCounter = 0;
            boolean color = true;

            for (Book book : favouriteBooks) {
                if (book != null) {
                    color = isColor(color);
                    System.out.println(++bookCounter + ". " + book.getBookName() + ", written by " + book.getAuthor() + ConsoleColors.RESET + "   Rating: " + ((book.getAverageRating() > 0) ? String.format("%.1f", book.getAverageRating()) : "Not yet rated"));
                    System.out.println(ConsoleColors.PURPLE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ConsoleColors.RESET);

                }
            }
            System.out.println("\n                                     " + ConsoleColors.RED + "B. Go back" + ConsoleColors.RESET);

            System.out.println("Choose a " + ConsoleColors.GREEN + "book " + ConsoleColors.RESET + "and type it's" + ConsoleColors.PURPLE + " number" + ConsoleColors.RESET);
            System.out.println("If you wish to " + ConsoleColors.YELLOW + "go back " + ConsoleColors.RESET + "- type " + ConsoleColors.PURPLE + "B");
            System.out.println(ConsoleColors.CYAN + "Type your input here: " + ConsoleColors.RESET);

            int command = getFavInput();

            while (command < 0 || command > bookCounter) {
                System.out.println("You need to type a number between " + ConsoleColors.PURPLE + "1-" + bookCounter
                        + ConsoleColors.RESET + " or the letter " + ConsoleColors.PURPLE + "B" + ConsoleColors.RESET + ":");
                command = getFavInput();
            }
            if (command == 0) {
                options(library, user);
            } else {
                favouriteBookInteract(user, favouriteBooks[command - 1]);
            }
        }
    }

    private static void accountInfo(Library library, User user) {
        Utils.clearConsole();
        System.out.println();
        System.out.println(ConsoleColors.PURPLE + "-=-=-=-=-=-=-=-=");
        System.out.println("| " + ConsoleColors.BLUE + "ACCOUNT INFO" + ConsoleColors.PURPLE + " |");
        System.out.println("-=-=-=-=-=-=-=-=\n\n" + ConsoleColors.RESET);
        System.out.println("YOUR NAME: " + ConsoleColors.CYAN + user.getFirstName() + ConsoleColors.RESET);
        System.out.println("USERNAME: " + ConsoleColors.CYAN + user.getUsername() + ConsoleColors.RESET);
        System.out.println("EMAIL ADDRESS: " + ConsoleColors.CYAN + user.getEmail() + ConsoleColors.RESET);
        System.out.println();
        System.out.println("Choose one of the options: ");
        System.out.println("1. Change password");
        System.out.println("2. Change email address");
        System.out.println("3. Go back");

        int input = getInput();
        while (input < 1 || input > 3) {
            System.out.println("Type a number between 1-3: ");
            input = getInput();
        }
        if (input == 1) {
            user.changePassword();
            accountInfo(library, user);
        } else if (input == 2) {
            user.changeEmail();
            accountInfo(library, user);
        } else {
            options(library, user);
        }

    }

    private static boolean isColor(boolean color) {
        if (color) {
            System.out.print(ConsoleColors.BLUE);
            color = false;
        } else {
            System.out.print(ConsoleColors.CYAN);
            color = true;
        }
        return color;
    }

    private static void addNewBook(User user) {
        Book.addNewBook(user, user.getUsername());
        Library library = Library.generateMainLibrary();
        options(library, user);
    }

    private static void printAllBooks(Library library, User user) {

        boolean color = true;

        Book[] allBooks = new Book[library.getBooks().size()];
        int bookIndex;
        int pages = (allBooks.length - 1) / 10;
        int currentPage;

        allBooks = Library.sortBooks(library.getBooks());

        for (currentPage = 0; currentPage <= pages; currentPage++) {
            for (bookIndex = 0; bookIndex < 10; bookIndex++) {
                color = isColor(color);

                if (bookIndex + (currentPage * 10) != allBooks.length) {
                    System.out.println((bookIndex + 1) + ". " + allBooks[bookIndex + (currentPage * 10)].getBookName()
                            + ", written by " + allBooks[bookIndex + (currentPage * 10)].getAuthor() + ConsoleColors.RESET
                            + "   Rating: " + ((allBooks[bookIndex + (currentPage * 10)].getAverageRating() > 0)
                            ? String.format("%.1f", allBooks[bookIndex + (currentPage * 10)].getAverageRating())
                            : "Not yet rated") + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.PURPLE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + ConsoleColors.RESET);
                } else {
                    break;
                }
            }

            System.out.println("\n" + ConsoleColors.PURPLE + (currentPage == 0 ? "                " : "P. Previous page")
                    + ConsoleColors.BLUE + (currentPage > pages - 1 ? "                    " : "   N. Next page    ")
                    + ConsoleColors.RED + "       B. Go back\n" + ConsoleColors.RESET);

            System.out.println("To select a " + ConsoleColors.GREEN + "book" + ConsoleColors.RESET + " - choose one and type it's " + ConsoleColors.PURPLE + "number\n" + ConsoleColors.RESET
                    + ConsoleColors.RESET + "To select an " + ConsoleColors.YELLOW + "option" + ConsoleColors.RESET + " - type it's corresponding " + ConsoleColors.PURPLE + "letter" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "Type your input here: " + ConsoleColors.RESET);

            int command = getPageInput();

            while (command < 1 || command > 13) {
                System.out.println("You need to type a number or one of the letters:");
                command = getPageInput();
            }

            if (command == 13) {
                options(library, user);
            } else if (command == 12 && currentPage != pages) {
                Utils.clearConsole();
            } else if (command == 11 && currentPage != 0) {
                currentPage -= 2;
            } else if ((command - 1 + (currentPage * 10)) < allBooks.length) {
                bookInteract(user, allBooks[command - 1 + (currentPage * 10)]);
            } else {
                System.out.println(ConsoleColors.YELLOW + "\nNo book available on the spot you selected.\n" + ConsoleColors.RESET);
                currentPage -= 1;
            }

        }
    }

    private static void printAllLibraryBooks(Library library, User user) {

        boolean color = true;

        Book[] allBooks = new Book[user.getPersonalLibrary().getBooks().size()];

        int bookIndex;
        int pages = (allBooks.length - 1) / 10;
        int currentPage;

        allBooks = Library.sortBooks(user.getPersonalLibrary().getBooks());

        for (currentPage = 0; currentPage <= pages; currentPage++) {
            for (bookIndex = 0; bookIndex < 10; bookIndex++) {
                color = isColor(color);

                if (bookIndex + (currentPage * 10) != allBooks.length) {
                    System.out.println((bookIndex + 1) + ". " + allBooks[bookIndex + (currentPage * 10)].getBookName()
                            + ", written by " + allBooks[bookIndex + (currentPage * 10)].getAuthor() + ConsoleColors.RESET
                            + "   Rating: " + ((allBooks[bookIndex + (currentPage * 10)].getAverageRating() > 0)
                            ? String.format("%.1f", allBooks[bookIndex + (currentPage * 10)].getAverageRating())
                            : "Not yet rated") + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.PURPLE
                            + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="
                            + ConsoleColors.RESET);
                } else {
                    break;
                }
            }

            System.out.println("\n" + ConsoleColors.PURPLE + (currentPage == 0 ? "                " : "P. Previous page ")
                    + ConsoleColors.BLUE + (currentPage != pages - 1 ? "                    " : "       N. Next page ")
                    + ConsoleColors.RED + "      B. Go back \n" + ConsoleColors.RESET);

            System.out.println("To select a " + ConsoleColors.GREEN + "book" + ConsoleColors.RESET + " - choose one and type it's " + ConsoleColors.PURPLE + "number\n" + ConsoleColors.RESET
                    + ConsoleColors.RESET + "To select an " + ConsoleColors.YELLOW + "option" + ConsoleColors.RESET + " - type it's corresponding " + ConsoleColors.PURPLE + "letter" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "Type your input here: " + ConsoleColors.RESET);

            int command = getPageInput();

            while (command < 1 || command > 13) {
                System.out.println("You need to type a number or one of the letters:");
                command = getPageInput();
            }

            if (command == 13) {
                options(library, user);
            } else if (command == 12 && currentPage != pages) {
                Utils.clearConsole();
            } else if (command == 11 && currentPage != 0) {
                currentPage -= 2;
            } else if ((command - 1 + (currentPage * 10)) < allBooks.length) {
                libraryBookInteract(library, user, allBooks[command - 1 + (currentPage * 10)]);
            } else {
                System.out.println(ConsoleColors.YELLOW + "\nNo book available on the spot you selected.\n" + ConsoleColors.RESET);
                currentPage -= 1;
            }

        }
    }

    private static void libraryBookInteract(Library library, User user, Book book) {
        System.out.println("\nSelected book: " + ConsoleColors.CYAN + book.getBookName().toUpperCase() + ConsoleColors.RESET);
        System.out.println();
        System.out.println("Options: \n " + ConsoleColors.CYAN);
        System.out.println("1. Rate");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("2. Leave a review");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("3. Add to \"Favourites\"");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("4. Remove from \"My Library\"");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("5. Show book resume");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("6. Edit book");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("7. Show book comments");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("8. Go back");
        System.out.print(ConsoleColors.RESET);

        int input = getInput();
        while (input < 1 || input > 8) {
            System.out.println("Type a number between 1-8: ");
            input = getInput();
        }
        switch (input) {
            case 1:
                book.rateBook(user.getUsername());
                libraryBookInteract(library, user, book);
            case 2:
                book.commentBook(user.getUsername());
                libraryBookInteract(library, user, book);
            case 3:
                user.addBookToFavourites(book);
                libraryBookInteract(library, user, book);
            case 4:
                user.removeFromMyLibrary(book);
                myLibrary(user);
            case 5:
                System.out.println(ConsoleColors.CYAN);
                book.showResume();
                System.out.println(ConsoleColors.RESET);
                Utils.pressKeyToContinue();
                libraryBookInteract(library, user, book);
            case 6:
                book.editBook(user);
                libraryBookInteract(library, user, book);
            case 7:
                book.showAllReviews();
                Utils.pressKeyToContinue();
                libraryBookInteract(library, user, book);
            case 8:
                options(library, user);
        }
    }

    private static void favouriteBookInteract(User user, Book book) {
        System.out.println("\nSelected book: " + ConsoleColors.CYAN + book.getBookName().toUpperCase() + ConsoleColors.RESET);
        System.out.println();
        System.out.println("Options: \n " + ConsoleColors.CYAN);
        System.out.println("1. Rate");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("2. Leave a review");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("3. Add to \"My Library\"");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("4. Remove from \"Favourites\"");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("5. Show book resume");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("6. Edit book");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("7. Show book comments");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("8. Go back");
        System.out.print(ConsoleColors.RESET);

        int input = getInput();
        while (input < 1 || input > 8) {
            System.out.println("Type a number between 1-8: ");
            input = getInput();
        }
        switch (input) {
            case 1:
                book.rateBook(user.getUsername());
                favouriteBookInteract(user, book);
            case 2:
                book.commentBook(user.getUsername());
                favouriteBookInteract(user, book);
            case 3:
                user.addBookToLibrary(book);
                favouriteBookInteract(user, book);
            case 4:
                user.removeFromFavourites(book);
                favouriteBooks(user);
            case 5:
                System.out.println(ConsoleColors.CYAN);
                book.showResume();
                System.out.println(ConsoleColors.RESET);
                Utils.pressKeyToContinue();
                favouriteBookInteract(user, book);
            case 6:
                book.editBook(user);
                favouriteBookInteract(user, book);
            case 7:
                book.showAllReviews();
                Utils.pressKeyToContinue();
                favouriteBookInteract(user, book);
            case 8:
                favouriteBooks(user);
        }
    }

    private static void bookInteract(User user, Book book) {
        System.out.println("\nSelected book: " + ConsoleColors.CYAN + book.getBookName().toUpperCase() + ConsoleColors.RESET);
        System.out.println();
        System.out.println("Options: \n" + ConsoleColors.CYAN);
        System.out.println("1. Rate");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("2. Leave a review");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("3. Add to \"My Library\"");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("4. Add to \"Favourites\"");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("5. Show book resume");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("6. Show book comments");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("7. Edit book");
        System.out.println(ConsoleColors.PURPLE + "------------------------------|" + ConsoleColors.CYAN);
        System.out.println("8. Go back" + ConsoleColors.RESET);

        int input = getInput();
        while (input < 1 || input > 8) {
            System.out.println("Type a number between 1-8: ");
            input = getInput();
        }
        switch (input) {
            case 1:
                book.rateBook(user.getUsername());
                bookInteract(user, book);
            case 2:
                book.commentBook(user.getUsername());
                bookInteract(user, book);
            case 3:
                user.addBookToLibrary(book);
                bookInteract(user, book);
            case 4:
                user.addBookToFavourites(book);
                bookInteract(user, book);
            case 5:
                System.out.println(ConsoleColors.CYAN);
                book.showResume();
                System.out.println(ConsoleColors.RESET);
                Utils.pressKeyToContinue();
                bookInteract(user, book);
            case 6:
                book.showAllReviews();
                Utils.pressKeyToContinue();
                bookInteract(user, book);
            case 7:
                book.editBook(user);
                bookInteract(user, book);
            case 8:
                exploreBooks(user);
        }
    }

    private static int getInput() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while (true) {
            if (input.isEmpty()) {
                System.out.println("You need to type something!");
                input = sc.nextLine();
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("You need to type a number!");
                input = sc.nextLine();
            }

        }

    }

    private static int getFavInput() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while (true) {
            if (input.isEmpty()) {
                System.out.println("You need to type something!");
                input = sc.nextLine();
            } else if (input.trim().equalsIgnoreCase("B")) {
                return 0;
            } else {
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.YELLOW + "Wrong input!" + ConsoleColors.RESET);
                }
            }
        }
    }

    private static int getPageInput() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while (true) {
            if (input.isEmpty()) {
                System.out.println("You need to type something!");
                input = sc.nextLine();
                continue;
            } else if (input.trim().equalsIgnoreCase("P")) {
                return 11;
            } else if (input.trim().equalsIgnoreCase("N")) {
                return 12;
            } else if (input.trim().equalsIgnoreCase("B")) {
                return 13;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.YELLOW + "You need to type a number or one of the letters:");
                input = sc.nextLine();
            }

        }
    }
}
