package library_project.users;

import library_project.library.Book;
import library_project.library.PersonalLibrary;
import library_project.utils.ConsoleColors;
import library_project.utils.IUseFiles;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class User implements IUseFiles {
    private static final AtomicInteger IDCount = new AtomicInteger(0);
    public static final String filepath = "library_project/files/users.csv";

    private final int ID;
    private String username;
    private String password;
    private String firstName;
    private String email;
    PersonalLibrary library;
    Book[] favouriteBooks;

    //TODO set proper IDCount increment (value should be fetched from last user entry)
    public User(String username, String password, String firstName, String email) {
        this.ID = IDCount.incrementAndGet();
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.email = email;

        writeToFile();
    }

    public User(int ID, String username, String password, String firstName, String email) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.email = email;
    }

    //TODO set specific exception
    public void writeToFile() {
        try {

            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(ID + "," + username + "," + password + "," + firstName + "," + email);
            pw.flush();
            pw.close();

            System.out.println(ConsoleColors.GREEN + "Successfully added user " + username + " to file!" + ConsoleColors.RESET);
        }
        catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Failed to add user " + username + " to file!");

        }
    }

    @Override
    public void editFile() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
