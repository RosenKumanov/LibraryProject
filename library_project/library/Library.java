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

    public Library() {

   }
    public Library(Set<Book> books) {
        this.books = books;
    }

    public static Library generateMainLibrary() {
        return new Library(getAllBooksFromFile());
    }

    private static Set<Book> getAllBooksFromFile() {
        Set<Book> allBooks = new HashSet<>();
        File booksFile = new File(Book.FILEPATH);

        try {
            Scanner sc = new Scanner(booksFile);
            while(sc.hasNextLine()) {
                String[] bookFields = sc.nextLine().split(",");

                Set<Review> reviews = new HashSet<>();
                Book book;

                if(bookFields.length == 4) {
                    book = new Book(bookFields[0], bookFields[1], new ISBNnum(bookFields[2]), bookFields[3].replaceAll("/", ","));
                }
                else if(bookFields.length == 5) {
                    book = new Book(bookFields[0], bookFields[1], new ISBNnum(bookFields[2]), bookFields[3].replaceAll("/", ","), bookFields[4]);

                }
                else {
                    book = new Book(bookFields[0], bookFields[1], new ISBNnum(bookFields[2]), bookFields[3].replaceAll("/", ","), Double.parseDouble(bookFields[4]), reviews);
                }
                book.getAllReviewsFromFile();
                book.updateAverageRating();
                allBooks.add(book);
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "Could not open file!");
        }

        return allBooks;
    }

    public static Book[] sortBooks(Set<Book> books) {
        Book[] sortedBooks = new Book[books.size()];

        //TODO Stefi's wonderful code will be here

        return sortedBooks;
    }

    public Set<Book> getBooks() {
        return books;
    }
}
