package library_project.library;

public class Book { //TODO encapsulate fields
    String name;
    String author;
    int numberISBN; //TODO ID of a book, must be entered by the user and checked by the program
    String resume;

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

    public String getName() {
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
