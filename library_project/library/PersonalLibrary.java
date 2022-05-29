package library_project.library;

import library_project.utils.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class PersonalLibrary extends Library {

    public PersonalLibrary(Set<Book> books) {
        super(books);
    }

    public Set<String> allBooksISBN() {

        Set<String> booksISBN = new HashSet<>();
        for(Book book : super.getBooks()) {
            booksISBN.add(String.valueOf(book.getNumberISBN()));
        }
        return booksISBN;
    }


}
