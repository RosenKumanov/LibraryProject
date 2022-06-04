package library_project.library;

import library_project.users.User;
import library_project.utils.ConsoleColors;
import library_project.utils.IUseFiles;

import java.io.*;
import java.util.Scanner;

public class Review implements IUseFiles {

    public static final String filepath = "library_project/files/bookReview.csv";

    Scanner scan = new Scanner(System.in);
    protected int ratingByCurrentUser;
    private static String commentByCurrentUser;

    User currentUserID;
    library_project.library.ISBNnum currentBookISBN;
    library_project.library.Book currentBook;

    public Review() {
    }

    public int getRatingByCurrentUser() {
        return ratingByCurrentUser;
    }

    public Review(int ratingByCurrentUser, String commentByCurrentUser) {
        //this.currentUserID = currentUserID;
        // this.currentBookISBN = currentBookISBN;
        this.ratingByCurrentUser = ratingByCurrentUser;
        this.commentByCurrentUser = commentByCurrentUser;
    }

    public static Comparable<String> addComments () {
        System.out.println(ConsoleColors.YELLOW + "You can add your comment for this book here: " + ConsoleColors.RESET);
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(in);
        try {
            commentByCurrentUser = br.readLine().replaceAll("," , "/");
        } catch (IOException e) {
            System.out.println("You failed to add a comment!");
        }
        System.out.println("Thank you for commenting!");
        return commentByCurrentUser;
    }

    public void setRatingByCurrentUser() {
        this.ratingByCurrentUser = ratingByCurrentUser;
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(in);
        try {
            int ratingByCurrentUser = Integer.parseInt(br.readLine());

            if (ratingByCurrentUser < 0 || ratingByCurrentUser > 5) {
                System.out.println("Please, enter a number from 0 to 5!");
                setRatingByCurrentUser();
            } else {
                System.out.println(ConsoleColors.GREEN + "You rated this book with " + ConsoleColors.BLUE + ratingByCurrentUser + ConsoleColors.RESET);
            }
        } catch (IOException e) {
            System.out.println("wrong");
            setRatingByCurrentUser();
        }
    }

    public static void addRating () {
        System.out.println("You can rate this book with a grade from 0 to 5. Please vote!");
        Review review = new Review();
        review.setRatingByCurrentUser();
    }

    @Override
    public void writeToFile() {
        try {

            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println( ratingByCurrentUser + "," + commentByCurrentUser);
            pw.flush();
            pw.close();

            System.out.println(ConsoleColors.GREEN + "Successfully added comment! Thank you, for your review!" + ConsoleColors.RESET);
            System.out.println(ratingByCurrentUser + '\n' + commentByCurrentUser);
        }
        catch (IOException e) {
            System.out.println( ConsoleColors.BLUE_BOLD + currentUserID.getUsername() + ConsoleColors.RED + ", you failed to add a comment!" + ConsoleColors.RESET);

        }
    }

}