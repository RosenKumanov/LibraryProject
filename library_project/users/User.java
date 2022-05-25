package library_project.users;

import library_project.utils.ConsoleColors;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private static final AtomicInteger IDCount = new AtomicInteger(0);
    public static final String filepath = "library_project/files/users.csv";

    private final int ID;
    private String username;
    private char[] password;
    private String firstName;
    private String email;

    public User(String username, char[] password, String firstName, String email) {
        this.ID = IDCount.incrementAndGet();
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.email = email;

        writeToFile();
    }

    public User(int ID, String username, char[] password, String firstName, String email) {
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

            pw.println(ID + "," + username + "," + Arrays.toString(password) + "," + firstName + "," + email);
            pw.flush();
            pw.close();

            System.out.println(ConsoleColors.GREEN + "Successfully added user " + username + " to file!");
        }
        catch (Exception e) {
            System.out.println(ConsoleColors.RED + "Failed to add user " + username + " to file!");

        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
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
