package library_project.users;

import library_project.utils.ConsoleColors;
import library_project.utils.Menu;
import library_project.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UserRepo {

    private UserRepo () {}

    public static User logIn() {

        User user = loginForm();

        while(user == null) {
            System.out.println(ConsoleColors.YELLOW + "\nUnsuccessful log in attempt - wrong "
                    + ConsoleColors.RED + "username "  + ConsoleColors.YELLOW + "or " + ConsoleColors.RED + "password\n" + ConsoleColors.RESET);
            System.out.println("Would you like to try again? Y/N:");
            if(Utils.yesOrNo()) {
                user = UserRepo.logIn();
            }
            else {
                Menu.start();
            }
        }
        return user;
    }

    private static User loginForm() {
        Set<User> allUsers = UserRepo.getExistingUsersFromFile();

        Scanner sc = new Scanner(System.in);

        System.out.println("username: ");
        String inputUsername = sc.nextLine();

        System.out.println("password: ");
        String inputPassword = sc.nextLine();

        assert allUsers != null;
        for(User user : allUsers) {
            if(user.getUsername().equals(inputUsername)) {
                if(User.encryptPassword(inputPassword).equals(String.valueOf(user.getPassword()))) {
                    System.out.println(ConsoleColors.GREEN + "\n      You have logged in successfully!\n" + ConsoleColors.RESET);
                    System.out.println("              Welcome, " + ConsoleColors.CYAN + user.getFirstName() + ConsoleColors.RESET + "!");
                    return user;
                }
                else {
                    break;
                }
            }
        }
        return null;
    }

    public static Set<User> getExistingUsersFromFile() {
        Set<User> users = new HashSet<>();

        File usersFile = new File(User.FILEPATH);

        try(Scanner sc = new Scanner(usersFile)) {

            if(!sc.hasNextLine()) {
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

    private static User registrationForm() {
        Set<User> allUsers = UserRepo.getExistingUsersFromFile();

        System.out.println(ConsoleColors.PURPLE + "-=-=-=-=-=-=-=-=-");
        System.out.println("| " + ConsoleColors.BLUE + "REGISTRATION" + ConsoleColors.PURPLE + " |");
        System.out.println("-=-=-=-=-=-=-=-=-" + ConsoleColors.RESET);

        Scanner sc = new Scanner(System.in);

        System.out.println("Type your First name: ");
        String name = sc.nextLine();

        System.out.println("Type your desired username: ");
        String username = sc.nextLine();

        if (allUsers != null) {
            for (User user : allUsers) {
                if (user.getUsername().equalsIgnoreCase(username)) {
                    System.out.println("Username " + ConsoleColors.CYAN + username + ConsoleColors.RESET + " already exists!\n" + ConsoleColors.RESET);
                    System.out.println("Would you like to try registering with another username? Y/N: ");
                    if(Utils.yesOrNo()) {
                        return null;
                    }
                    else {
                        Menu.start();
                    }
                }
            }
        }

        System.out.println("Type your desired password: ");
        String password = sc.nextLine();

        while (isPasswordWeak(password)) {
            System.out.println(ConsoleColors.YELLOW + "Your password must be at least 8 characters and it needs to include at least 3 of the following: \n" + ConsoleColors.RESET);
            System.out.println("1. Lower case letters");
            System.out.println("2. Upper case letters");
            System.out.println("3. Numbers");
            System.out.println("4. Symbols\n");
            password = sc.nextLine();
        }

        System.out.println("Type your email address: ");
        String email = getValidEmail();

        if (allUsers != null) {
            for (User user : allUsers) {
                if (user.getEmail().equalsIgnoreCase(email)) {
                    System.out.println(ConsoleColors.YELLOW + "Email address " + email + " already exists!" + ConsoleColors.RESET);
                    return null;
                }
            }
        }
            return new User(username, password, name, email);
    }

    private static String getValidEmail() {
        Scanner sc = new Scanner(System.in);
        String email = sc.nextLine();

        while(true) {
            if(email == null) {
                System.out.println("You need to type something: ");
                email = sc.nextLine();
            }
            else if(!email.contains("@") || !email.contains(".")) {
                System.out.println("Email is not valid! Try again: ");
                email = sc.nextLine();
            }
            else {
                break;
            }
        }
        return email;
    }

    public static boolean isPasswordWeak(String password) {
        char[] pass = password.toCharArray();
        if(pass.length < 8) {
            return true;
        }
        boolean lowerCase = false;
        boolean nums = false;
        boolean symbols = false;
        boolean upperCase = false;
        for (char c : pass) {
            if (c > 47 && c < 58) {
                nums = true;
            } else if (c > 96 && c < 123) {
                lowerCase = true;
            } else if (c > 64 && c < 91) {
                upperCase = true;
            } else {
                symbols = true;
            }
        }
        if(lowerCase && nums && symbols && upperCase) {
            return false;
        }
        else if(lowerCase && nums && upperCase) {
            return false;
        }
        else if(lowerCase && nums && symbols) {
            return false;
        }
        else return !lowerCase || !symbols || !upperCase;
    }

}
