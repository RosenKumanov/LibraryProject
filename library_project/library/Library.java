package library_project.library;

import library_project.utils.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Library {
    protected Set<Book> books;

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
//TODO move getAllBooks() to a separate function, as default constructor should not fetch all books

    public Library() {

   }
    public Library(Set<Book> books) {
        this.books = books;
    }

    public static Library generateMainLibrary() {
        return new Library(getAllBooksFromFile());
    }

    public void showAllBooks() {
        for(Book book : books) {
            System.out.println("Title: " + book.getBookName() + " | written by: " + book.getAuthor() + " | Rating: " + book.getAverageRating() + "\n\n" + book.getResume() + "\n");
        }
    }

    private static Set<Book> getAllBooksFromFile() {
        Set<Book> allBooks = new HashSet<>();
        File booksFile = new File(Book.filepath);

        try {
            Scanner sc = new Scanner(booksFile);
            while(sc.hasNextLine()) {
                String[] bookFields = sc.nextLine().split(",");
                //TODO create a function to get all reviews from file
                Set<Review> reviews = new HashSet<>();
                if(bookFields.length < 6) {
                    Book book = new Book(bookFields[0], bookFields[1], new ISBNnum(bookFields[2]), bookFields[3].replaceAll("/", ","));
                    allBooks.add(book);
                }
                else {
                    Book book = new Book(bookFields[0], bookFields[1], new ISBNnum(bookFields[2]), bookFields[3].replaceAll("/", ","), Double.parseDouble(bookFields[4]), reviews);
                    allBooks.add(book);
                }
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "Could not open file!");
        }

        return allBooks;
    }

    public Set<Book> getBooks() {
        return books;
    }
}
