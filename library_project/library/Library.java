package library_project.library;

import library_project.users.User;
import library_project.utils.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Library {
    private Set<Book> books;

    //TODO move getAllBooks() to a separate function, as default constructor should not fetch all books

    public Library() {

   }
    public Library(Set<Book> books) {
        this.books = books;
    }

    public void showAllBooks() {
        for(Book book : books) {
            System.out.println("Title: " + book.name + " | written by: " + book.author + " | Rating: " + book.bookReview.averageRating + "\n\n" + book.resume + "\n");
        }
    }

    public static Set<Book> getAllBooksFromFile() {
        Set<Book> allBooks = new HashSet<>();
        File booksFile = new File(User.filepath);

        try {
            Scanner sc = new Scanner(booksFile);
            while(sc.hasNextLine()) {
                String[] bookFields = sc.nextLine().split(",");
                Review review = new Review(Double.parseDouble(bookFields[4]), null );
                Book book = new Book(bookFields[0],bookFields[1], Integer.parseInt(bookFields[2]), bookFields[3], review);
                allBooks.add(book);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "Could not open file!");
        }

        return allBooks;
    }

    protected void removeBook(Book book) {
        System.out.println("Removing " + book.getBookName() + " from the library...");
        if(books.contains(book)) {
            System.out.println("Successfully removed book from the library.");
            books.remove(book);
        }
        else {
            System.out.println(ConsoleColors.YELLOW + "Book " + book.getBookName() + " is not present in the Library." + ConsoleColors.RESET);
        }
    }

    protected void addBook(Book book) {
        System.out.println("Adding " + book.getBookName() + " to the library...");
        if(books.contains(book)) {
            System.out.println("Book already exists.");
        }
        else {
            System.out.println("Successfully added book to library.");
            books.add(book);
        }
    }


    public Set<Book> getBooks() {
        return books;
    }
}
