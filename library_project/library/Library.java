package library_project.library;

import library_project.utils.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
        Library library = new Library(getAllBooksFromFile());
        return library;
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

        Book[] arrayOfSortedBooks = new Book[books.size()];
        books.toArray(arrayOfSortedBooks);

        Book temp;

        for (int i = 0; i < books.size() - 1; i++) {

            for (int j = 1; j < books.size(); j++) {

                if(arrayOfSortedBooks[j] != null && arrayOfSortedBooks[j-1] != null) {
                    if (arrayOfSortedBooks[j-1].getBookName().compareToIgnoreCase(arrayOfSortedBooks[j].getBookName()) > 0) {

                        temp = arrayOfSortedBooks[j - 1];
                        arrayOfSortedBooks[j - 1] = arrayOfSortedBooks[j];
                        arrayOfSortedBooks[j] = temp;
                    }
                }
            }
        }

        return arrayOfSortedBooks;
    }

    public Set<Book> getBooks() {
        return books;
    }
}
