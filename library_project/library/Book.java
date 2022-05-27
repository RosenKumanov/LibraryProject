package library_project.library;

import library_project.users.User;

public class Book { //TODO encapsulate fields
    public static final String filepath = "library_project/files/books.csv";
    private final int  numberISBN; //TODO ID of a book, must be entered by the user and checked by the program
    private String name;
    private String author;
    private String resume;

    User bookOwner;
    //TODO bookOwnerUser, method edit book to be here
    //TODO do we need to add the current users rating and comment in class Book?
    Review bookReview;

    public Book(String name, String author, int numberISBN, String resume, Review bookReview) {
        this.name = name;
        this.author = author;
        this.numberISBN = numberISBN;
        this.resume = resume;
        this.bookReview = bookReview;
    }

    public String getBookName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getNumberISBN() {
        return numberISBN;
    }

    public String getResume() {
        return resume;
    }

    public Review getBookReview() {
        return bookReview;
    }

}
