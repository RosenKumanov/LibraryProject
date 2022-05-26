package library_project.library;

import java.util.List;

public class Review {
    int ratingByCurrentUser;
    double averageRating;
    String commentByCurrentUser;
    List<String> comments;

    public Review(double averageRating, List<String> comments) {
        this.averageRating = averageRating;
        this.comments = comments;
    }

    public Review(int ratingByCurrentUser, double averageRating, String commentByCurrentUser, List<String> comments) {
        this.ratingByCurrentUser = ratingByCurrentUser;
        this.commentByCurrentUser = commentByCurrentUser;
        this.comments = comments;
        this.averageRating = averageRating;
    }

    public static void addComments (String commentByCurrentUser, List<String> comments) {
        comments.add(commentByCurrentUser); // TODO to add the comment on top of all comments
        System.out.println("Your comment was successfully added!");
        System.out.println("Your comment for this book:" + commentByCurrentUser); //TODO to add a book object
    }
    private static void updateRating (int ratingByCurrentUser, double averageRating) { //TODO create a public method for AddRating that will call updateRating
        int countRatings = 0;
        countRatings++;
        averageRating = (averageRating+ratingByCurrentUser)/countRatings;
        System.out.println("Thank you for your rating!"); // TODO Rating 1 to 5
    }

}
