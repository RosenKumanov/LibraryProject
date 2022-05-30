package library_project.library;

import java.util.HashSet;
import java.util.Set;

public class PersonalLibrary extends Library {

    public PersonalLibrary(Set<Book> books) {
        super(books);
    }

    public Set<String> allBooksISBN() {

        Set<String> booksISBN = new HashSet<>();
        for(Book book : super.getBooks()) {
            //TODO provide the option to access ISBN
            //booksISBN.add(String.valueOf(book.getNumberISBN()));
        }
        return booksISBN;
    }


}
