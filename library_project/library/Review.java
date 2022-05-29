package library_project.library;

import library_project.users.User;
import library_project.utils.ConsoleColors;
import library_project.utils.IUseFiles;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import static library_project.users.User.filepath;

public class Review implements IUseFiles {
    public static final String filepath = "library_project/files/bookReview.csv";

    Scanner scan = new Scanner(System.in);
    int ratingByCurrentUser;
    double averageRating;
    String commentByCurrentUser;
    List<String> comments; // TODO is it the best collection to use?!

    User currentUser;
    Book currentBook;

    public Review(double averageRating, List<String> comments) {
        this.averageRating = averageRating;
        this.comments = comments;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public Review(int ratingByCurrentUser, double averageRating, String commentByCurrentUser, List<String> comments) {
        this.ratingByCurrentUser = ratingByCurrentUser;
        this.commentByCurrentUser = commentByCurrentUser;
        this.comments = comments;
        this.averageRating = averageRating;
    }

    public static void addComments (String commentByCurrentUser, List<String> comments) {
        comments.add(commentByCurrentUser); // TODO to add the comment on top of all comments
        System.out.println("Your comment for this book:" + commentByCurrentUser); //TODO to add a book object
    }
    protected static void updateRating (int ratingByCurrentUser, double averageRating) { //TODO create a public method for AddRating that will call updateRating
        int countRatings = 0;
        countRatings++;
        averageRating = (averageRating+ratingByCurrentUser)/countRatings;
        System.out.println("Thank you for your rating!"); // TODO Rating 1 to 5
    }
   public void addRating (int ratingByCurrentUser, User currentUser) {
       System.out.println("You can rate this book with a grade from 0 to 5. Please vote!");
         try {  ratingByCurrentUser = scan.nextInt();
           if (ratingByCurrentUser < 0 || ratingByCurrentUser > 5) {
               System.out.println("Please, enter a number from 0 to 5!");
           } else {
               System.out.println(ConsoleColors.GREEN + "You rated book: " + currentBook + " with " + ratingByCurrentUser);
           }
    } catch (Exception e) {
             System.out.println("Please, enter a number from 0 to 5!");
         }
   }

    @Override
    public void writeToFile() {
            try {

                FileWriter fw = new FileWriter(filepath, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);

                pw.println(commentByCurrentUser);
                pw.flush();
                pw.close();

                System.out.println(ConsoleColors.GREEN + "Successfully added comment! " + currentUser.getUsername() + ", thank you, for your review!" + ConsoleColors.RESET);
            }
            catch (IOException e) {
                System.out.println(ConsoleColors.RED + currentUser.getUsername() + ", you failed to add a comment!");

            }
    }

    @Override
    public void editFile() {
        // TODO A user can edit its own comments!
    }

}
