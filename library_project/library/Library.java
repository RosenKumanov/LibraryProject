package library_project.library;

import java.util.Set;

public class Library {
    Set<Book> books;

    Library(Set<Book> books) {
        this.books = books;
    }

    protected void removeBook(Book book) {
        System.out.println("Removing " + book.name + " from the library...");
        if(books.contains(book)) {
            System.out.println("Successfully removed book from the library.");
            books.remove(book);
        }
    }

    protected void addBook(Book book) {
        System.out.println("Adding " + book.name + " to the library...");
        if(books.contains(book)) {
            System.out.println("Book already exists.");
        }
        else {
            System.out.println("Successfully added book to library.");
            books.add(book);
        }
    }

}
