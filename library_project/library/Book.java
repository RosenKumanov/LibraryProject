package library_project.library;

import library_project.users.User;
import library_project.utils.ConsoleColors;
import library_project.utils.IUseFiles;
import library_project.utils.Menu;
import library_project.utils.Utils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Set;

public class Book implements IUseFiles { //TODO encapsulate fields
    public static final String filepath = "library_project/files/books.csv";
    private String name;
    private String author;
    private String resume;
    double averageRating;
    public ISBNnum bookISBN;

    public Book() {
    }

    DecimalFormat df = new DecimalFormat("x.x");
    User bookOwner;
    //TODO bookOwnerUser, method edit book to be here, to add resume
    Set<Review> bookReviews;

    public Book(String name, User bookOwner) {
        this.name = name;
        this.bookOwner = bookOwner;
    }

    public Book(String name, String author, ISBNnum bookISBN, String resume) {
        this.name = name;
        this.author = author;
        this.bookISBN = bookISBN;
        this.resume = resume;
    }

    public Book(String name, String author, ISBNnum bookISBN, String resume, double averageRating, Set<Review> bookReviews) {
        this.name = name;
        this.author = author;
        this.bookISBN = bookISBN;
        this.resume = resume;
        this.averageRating = averageRating;
        //TODO to add a method for set BookReviews
    }

    @Override
    public String toString() {
        return "Book:" + ConsoleColors.CYAN + name + '\n' + ConsoleColors.RESET +
                "Author: " + ConsoleColors.CYAN + author + '\n' + ConsoleColors.RESET +
                "ISBN: " + ConsoleColors.CYAN + bookISBN + '\n' + ConsoleColors.RESET +
                "Rating: " + ConsoleColors.CYAN + averageRating + '\n'+ ConsoleColors.RESET +
                "Resume: " + ConsoleColors.CYAN + resume + '\n' ;
    }

    private void updateRating (int ratingByCurrentUser) {
        averageRating = (averageRating+ratingByCurrentUser)/bookReviews.size();
        System.out.println(ConsoleColors.YELLOW + "Thank you for your rating!" + ConsoleColors.RESET);
    }

    public static void editBook (User user) { //TODO
        //if user is bookOwner?
    }

    public static void showAllReviews () { // TODO


    }

    public void addReviewToSet (Review review) {
        bookReviews.add(review);
        updateRating(review.ratingByCurrentUser); //TODO encapsulate in review
    }

    public ISBNnum getBookISBN() {
        return bookISBN;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public String getBookName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getResume() {
        return resume;
    }

    public static Book addNewBook () {
        Book bookToAdd = new Book();

        try {
        System.out.println(ConsoleColors.YELLOW + "Type book name: " + ConsoleColors.RESET);
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(in);

            bookToAdd.name = br.readLine();
            System.out.println(ConsoleColors.YELLOW + "Type book author: " + ConsoleColors.RESET);
            bookToAdd.author = br.readLine();

            bookToAdd.bookISBN = ISBNnum.setISBNnum();

            System.out.println(ConsoleColors.YELLOW + "Type book resume: " + ConsoleColors.RESET);
            bookToAdd.resume = br.readLine();

        } catch (Exception e) {
            InputStreamReader in = new InputStreamReader(System.in);  //create BufferedReader class object to get input from user
            BufferedReader br = new BufferedReader(in);
            try {
                System.out.println("Something went wrong :(");
                System.out.println("---------");
                System.out.println("Options:" + "\n" + "1. Try again           " + "2. Exit");
                int command = Integer.parseInt(br.readLine());
                if (command == 1) {
                    Book.addNewBook();
                } else if (command == 2) {
                    //to exit to the main menu
                    System.out.println("Exiting to the main menu...");
                    Menu.start();
                } else {
                    System.out.println("Wrong input...");
                }
            } catch (IOException e2){
                System.out.println(ConsoleColors.RED + "Wrong input!" + ConsoleColors.RESET);
                //to exit to the main menu
                System.out.println("Exiting to the main menu...");
                Menu.start();
            }

        }

        bookToAdd.writeToFile();
        return bookToAdd;
    }

    @Override
    public void writeToFile() {
        try {

            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println( name + "," + author + "," + bookISBN  + "," + resume.replaceAll(",", "/") + "," + bookOwner);
            pw.flush();
            pw.close();

            System.out.println(ConsoleColors.GREEN + "Successfully uploaded: "+ '\n' +
                     "Book: " + ConsoleColors.CYAN + name + '\n' +
                            ConsoleColors.GREEN + "Author: " + ConsoleColors.CYAN + author + '\n' +
                            ConsoleColors.GREEN + "ISBN: " + ConsoleColors.CYAN + bookISBN.getISBN() + '\n' +
                            ConsoleColors.GREEN + "Resume: " + ConsoleColors.CYAN + resume
                    );
        }
        catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Book was not uploaded!" + ConsoleColors.RESET);

        }
    }

    @Override
    public void editFile() {

    }

}
