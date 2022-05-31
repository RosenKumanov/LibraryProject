package library_project.library;

import library_project.users.User;
import library_project.utils.ConsoleColors;
import library_project.utils.IUseFiles;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Set;

public class Book implements IUseFiles { //TODO encapsulate fields
    public static final String filepath = "library_project/files/books.csv";
    private String name;
    private String author;
    private String resume;
    double averageRating;
    public library_project.library.ISBNnum bookISBN;

    public Book() {
    }

    DecimalFormat df = new DecimalFormat("x.x");
    User bookOwner;
    //TODO bookOwnerUser, method edit book to be here, to add resume
    Set<library_project.library.Review> bookReviews;

    public Book(String name, User bookOwner) {
        this.name = name;
        this.bookOwner = bookOwner;
    }

    public Book(String name, String author, library_project.library.ISBNnum bookISBN, String resume) {
        this.name = name;
        this.author = author;
        this.bookISBN = bookISBN;
        this.resume = resume;
    }

    public Book(String name, String author, library_project.library.ISBNnum bookISBN, String resume, double averageRating, Set<library_project.library.Review> bookReviews) {
        this.name = name;
        this.author = author;
        this.bookISBN = bookISBN;
        this.resume = resume;
        this.averageRating = averageRating;
        //TODO to add a method for set BookReviews
    }

    @Override
    public String toString() {
        return "Book:" + ConsoleColors.GREEN + name + '\n' + //TODO add colors
                "Author: " + author + '\n' +
                "ISBN: " + bookISBN + '\n' +
                "Rating: " + averageRating + '\n'+
                "Resume: " + resume + '\n' ;
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

    public void addReviewToSet (library_project.library.Review review) {
        bookReviews.add(review);
        updateRating(review.ratingByCurrentUser); //TODO encapsulate in review
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

    public static Book addNewBook() {
        Book bookToAdd = new Book();

        System.out.println(ConsoleColors.YELLOW + "Type book name: " + ConsoleColors.RESET);
        Scanner sc = new Scanner(System.in);

        bookToAdd.name = sc.nextLine();
        System.out.println(ConsoleColors.YELLOW + "Type book author: " + ConsoleColors.RESET);
        bookToAdd.author = sc.nextLine();
        System.out.println(ConsoleColors.YELLOW + "Type book ISBN: " + ConsoleColors.RESET);
        bookToAdd.bookISBN = ISBNnum.setISBNnum();
        System.out.println(ConsoleColors.YELLOW + "Type book resume: " + ConsoleColors.RESET);
        bookToAdd.resume = sc.nextLine();

        return bookToAdd;
    }

//    public static void addNewBook () {
//        Book bookToAdd = new Book();
//
//        System.out.println(ConsoleColors.YELLOW + "Type book name: " + ConsoleColors.RESET);
//        InputStreamReader in = new InputStreamReader(System.in);
//        BufferedReader br = new BufferedReader(in);
//        try {
//            bookToAdd.name = br.readLine();
//            System.out.println(ConsoleColors.YELLOW + "Type book author: " + ConsoleColors.RESET);
//            bookToAdd.name = br.readLine();
//            System.out.println(ConsoleColors.YELLOW + "Type book ISBN: " + ConsoleColors.RESET);
//            ISBNnum.setISBNnum();
//
//            System.out.println(ConsoleColors.YELLOW + "Type book resume: " + ConsoleColors.RESET);
//            bookToAdd.name = br.readLine();
//        } catch (Exception e) {
//            System.out.println(ConsoleColors.GREEN + "Book " + bookToAdd.name + " is added" + ConsoleColors.RESET);
//
//        }
//    }

    @Override
    public void writeToFile() {
        try {

            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(name + "," + author + "," + bookISBN  + "," + resume.replaceAll(",", "/"));
            pw.flush();
            pw.close();

            System.out.println(ConsoleColors.GREEN + "Successfully uploaded book! " + getBookName() + ConsoleColors.RESET);
        }
        catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Book was not uploaded!" + ConsoleColors.RESET);

        }
    }
//    public void writeToFile() {
//        try {
//
//            FileWriter fw = new FileWriter(filepath, true);
//            BufferedWriter bw = new BufferedWriter(fw);
//            PrintWriter pw = new PrintWriter(bw);
//
//            pw.println(name + "|" + author + "|" + bookISBN  + "|" + resume);
//            pw.flush();
//            pw.close();
//
//            System.out.println(ConsoleColors.GREEN + "Successfully uploaded book! " + getBookName() + ConsoleColors.RESET);
//        }
//        catch (IOException e) {
//            System.out.println(ConsoleColors.RED + "Book was not uploaded!" + ConsoleColors.RESET);
//
//        }
//    }

    @Override
    public void editFile() {

    }

}
