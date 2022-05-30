package library_project.users;

import library_project.utils.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Users {


    public static User logIn() {

        User user = loginForm();

        while(user == null) {
            System.out.println(ConsoleColors.YELLOW + "\nUnsuccessful log in attempt - Try again!\n" + ConsoleColors.RESET);
            user = Users.logIn();
        }
        return user;
    }

    private static User loginForm() {
        Scanner sc = new Scanner(System.in);

        Set<User> allUsers = Users.getExistingUsersFromFile();

        System.out.println("username: ");
        String inputUsername = sc.nextLine();

        System.out.println("password: ");
        String inputPassword = sc.nextLine();

        assert allUsers != null;
        for(User user : allUsers) {
            if(user.getUsername().equalsIgnoreCase(inputUsername)) {
                if(inputPassword.equals(String.valueOf(user.getPassword()))) {
                    System.out.println(ConsoleColors.GREEN + "\nYou have logged in successfully!\n" + ConsoleColors.RESET);
                    System.out.println("      Welcome, " + ConsoleColors.CYAN + user.getFirstName() + ConsoleColors.RESET + "!");
                    return user;
                }
                else {
                    break;
                }
            }
        }
        System.out.println(ConsoleColors.RED + "Wrong username or password!" + ConsoleColors.RESET);
        return null;
    }

    public static Set<User> getExistingUsersFromFile() {
        Set<User> users = new HashSet<>();

        File usersFile = new File(User.filepath);

        try {
            Scanner sc = new Scanner(usersFile);
            if(!sc.hasNextLine()) {
                System.out.println("No users are registered yet.");
                return null;
            }
            while(sc.hasNextLine()) {
                String[] userFields = sc.nextLine().split(",");
                User user = new User(Integer.parseInt(userFields[0]),userFields[1], userFields[2], userFields[3], userFields[4]);
                users.add(user);
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "File users.csv not found!" + ConsoleColors.RESET);
        }
        return users;
    }

    public static void register() {
        User newUser = registrationForm();
        while(newUser == null) {
            newUser = registrationForm();
        }
        newUser.writeToFile();
    }

    //TODO add data validation; check existing users for the same username and password
    private static User registrationForm() {
        Scanner sc = new Scanner(System.in);

        Set<User> allUsers = Users.getExistingUsersFromFile();

        System.out.println("REGISTRATION");
        System.out.println("---------------------------");

        System.out.println("Type your First name: ");
        String name = sc.nextLine();

        System.out.println("Type your desired username: ");
        String username = sc.nextLine();

        assert allUsers != null;
        for(User user : allUsers) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                System.out.println(ConsoleColors.YELLOW + "Username " + username + " already exists!" + ConsoleColors.RESET);
                return null;
            }
        }

        System.out.println("Type your desired password: ");
        String password = sc.nextLine();

        System.out.println("Type your email address: ");
        String email = sc.nextLine();

        for(User user : allUsers) {
            if(user.getEmail().equalsIgnoreCase(email)) {
                System.out.println(ConsoleColors.YELLOW + "Email address " + email + " already exists!" + ConsoleColors.RESET);
                return null;
            }
        }
        return new User(username, password, name, email);
    }

}
