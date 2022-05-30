package library_project.library;

import library_project.users.User;
import library_project.utils.ConsoleColors;
import library_project.utils.IUseFiles;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;

import static library_project.users.User.filepath;

public class Review implements IUseFiles {

    public static final String filepath = "library_project/files/bookReview.csv";

    Scanner scan = new Scanner(System.in);
    protected int ratingByCurrentUser;
    private String commentByCurrentUser;

    User currentUser;
    library_project.library.Book currentBook;

    public Review(int ratingByCurrentUser, String commentByCurrentUser) {
        this.ratingByCurrentUser = ratingByCurrentUser;
        this.commentByCurrentUser = commentByCurrentUser;
    }

    public int getRatingByCurrentUser() {
        return ratingByCurrentUser;
    }

    public String getCommentByCurrentUser() {
        return commentByCurrentUser;
    }

    public String addComments () {
        System.out.println(ConsoleColors.YELLOW + "You can add your comment for this book here: " + ConsoleColors.RESET);
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(in);
        try {
            commentByCurrentUser = new String(br.readLine());
        } catch (IOException e) {
            System.out.println("You failed to add a comment!");;
        }
        System.out.println("Thank you for commenting!");
        return commentByCurrentUser;
    }

    public static void addRating () {
        int ratingByCurrentUser = 0;

        try {
            InputStreamReader in = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(in);
            System.out.println("You can rate this book with a grade from 0 to 5. Please vote!");
            //store user entered value into variable num
            ratingByCurrentUser = Integer.parseInt(br.readLine());
            if (ratingByCurrentUser < 0 || ratingByCurrentUser > 5) {
                System.out.println("Please, enter a number from 0 to 5!");
            } else {
                System.out.println(ConsoleColors.GREEN + "You rated this book with " +ConsoleColors.BLUE +ratingByCurrentUser + ConsoleColors.RESET);
            }
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED + "Please, enter a number from 0 to 5!" + ConsoleColors.RESET);
        }
    }

    @Override
    public void writeToFile() {
        try {

            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(ratingByCurrentUser + "|" + commentByCurrentUser);
            pw.flush();
            pw.close();

            System.out.println(ConsoleColors.GREEN + "Successfully added comment! " + currentUser.getUsername() + ", thank you, for your review!" + ConsoleColors.RESET);
        }
        catch (IOException e) {
            System.out.println(ConsoleColors.RED + currentUser.getUsername() + ", you failed to add a comment!" + ConsoleColors.RESET);

        }
    }

    @Override
    public void editFile() {
        // TODO A user can edit its own comments!
    }

}
