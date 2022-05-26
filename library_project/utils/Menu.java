package library_project.utils;

import library_project.library.Library;
import library_project.users.User;

public class Menu {

    public static void options(Library library, User user) {
        System.out.println(ConsoleColors.GREEN + "MAIN MENU");
        System.out.println("-----------------------" + ConsoleColors.RESET);
        System.out.println("1. Explore books");
        System.out.println("2. My library");
        System.out.println("3. Favourite books");
        System.out.println("4. Add a new book");
        System.out.println("5. Log out");
    }
}
