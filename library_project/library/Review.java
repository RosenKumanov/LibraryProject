package library_project.library;

import library_project.utils.ConsoleColors;
import library_project.utils.IUseFiles;

import java.io.*;
import java.util.Scanner;

public class Review implements IUseFiles {

    public static final String filepath = "library_project/files/bookReview.csv";

    private int ratingByCurrentUser;
    private String commentByCurrentUser;
    private String currentUser;
    private String currentBookISBN;

    public Review() {
    }
    public Review(String currentUser, String currentBookISBN, int ratingByCurrentUser, String commentByCurrentUser) {
        this.currentUser = currentUser;
        this.currentBookISBN = currentBookISBN;
        this.ratingByCurrentUser = ratingByCurrentUser;
        this.commentByCurrentUser = commentByCurrentUser;
    }

    public Review( String currentUser, String currentBookISBN, int ratingByCurrentUser) {
        this.currentUser = currentUser;
        this.currentBookISBN = currentBookISBN;
        this.ratingByCurrentUser = ratingByCurrentUser;
    }

    public int getRatingByCurrentUser() {
        return ratingByCurrentUser;
    }

    public static void addComments (String user, String ISBN) { //TODO to update - add rating if you want to leave a comment!
        Review newComment = new Review();
        System.out.println(ConsoleColors.YELLOW + "You can add your comment for this book here: " + ConsoleColors.RESET);
        Scanner scan = new Scanner(System.in);
        newComment.commentByCurrentUser = scan.nextLine();
        System.out.println("Thank you for commenting!");
        newComment.setCurrentUser(user);
        newComment.setCurrentBookISBN(ISBN);
        scan.close();
        newComment.writeToFile();
    }

    private void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void setCurrentBookISBN(String currentBookISBN) {
        this.currentBookISBN = currentBookISBN;
    }

    public static Review addRating (String user, String ISBN) {
        Review newRating = new Review();
        System.out.println(ConsoleColors.YELLOW_BOLD + "You can rate this book with a grade from 1 to 5. Please vote!" + ConsoleColors.RESET);
        Scanner scan = new Scanner(System.in);

        try {
            newRating.ratingByCurrentUser = Integer.parseInt(scan.nextLine());
            if (newRating.ratingByCurrentUser < 1 || newRating.ratingByCurrentUser > 5) {
                System.out.println("Please, enter a number from 1 to 5!");
               addRating(user, ISBN);
            } else {
                System.out.println(ConsoleColors.GREEN + "You rated this book with " + ConsoleColors.BLUE + newRating.ratingByCurrentUser + ConsoleColors.RESET);
            }
        }
          catch (NumberFormatException e1) {
            System.out.println(ConsoleColors.RED_UNDERLINED + "Wrong input!" + ConsoleColors.RESET );
            addRating(user, ISBN);
        }
        newRating.setCurrentUser(user);
        newRating.setCurrentBookISBN(ISBN);
        scan.close();
        newRating.writeToFile();
        return newRating;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getCurrentBookISBN() {
        return currentBookISBN;
    }

    public String getCommentByCurrentUser() {
        return commentByCurrentUser;
    }

    @Override
    public void updateFile(String fileToUpdate, String old, String replace) throws IOException { //TODO
        // to update the review file after a comment is added by the same user and for the same book.
    }

    @Override
    public void writeToFile() {
        try {

            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            if (commentByCurrentUser != null) {
            pw.println(getCurrentUser() + "," + getCurrentBookISBN() + "," + "no rating" + "," + commentByCurrentUser.replaceAll(",", "/"));
            pw.flush();
            pw.close();
                System.out.println( ConsoleColors.BLUE_UNDERLINED + currentUser + ConsoleColors.RESET +  ConsoleColors.YELLOW + ", your comment: " + ConsoleColors.PURPLE);
                showComment();
                System.out.println(ConsoleColors.RESET);

            } else if (ratingByCurrentUser != 0) {
                pw.println(currentUser + "," + currentBookISBN + "," + ratingByCurrentUser + "," + null);
                pw.flush();
                pw.close();
            }
        }
        catch (IOException e) {
            System.out.println( ConsoleColors.BLUE_BOLD + currentUser + ConsoleColors.RED + ", you failed to add a comment!" + ConsoleColors.RESET);

        }
    }

    public void showComment () {
        String [] text = commentByCurrentUser.split(" ");
        int counter = 0;
        for (String word : text) {
            System.out.print(word + " ");
            counter++;
            if (counter >= 12) {
                System.out.println();
                counter = 0;
            }
        }
    }
}