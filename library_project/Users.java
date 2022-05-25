package library_project;

import library_project.utils.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Users {

    public static Set<User> getExistingUsersFromFile() {
        Set<User> users = new HashSet<>();

        File usersFile = new File(User.filepath);

        try {
            Scanner sc = new Scanner(usersFile);
            while(sc.hasNextLine()) {
                String[] userFields = sc.nextLine().split(",");
                users.add(new User(Integer.parseInt(userFields[0]),userFields[1], userFields[2].toCharArray(), userFields[3], userFields[4]));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "Could not open file!");
        }

        return users;
    }

    //TODO add data validation; check existing users for the same username and password
    public static User registrationForm() {
        Scanner sc = new Scanner(System.in);

        System.out.println("REGISTRATION\n");
        System.out.println("---------------------------");

        System.out.println("Type your First name: ");
        String name = sc.nextLine();

        System.out.println("Type your desired username: ");
        String username = sc.nextLine();

        System.out.println("Type your desired password: ");
        String password = sc.nextLine();

        System.out.println("Type your email address: ");
        String email = sc.nextLine();

        return new User(username, password.toCharArray(), name, email);
    }

}
