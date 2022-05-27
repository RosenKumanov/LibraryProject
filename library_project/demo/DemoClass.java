package library_project.demo;

import library_project.library.Library;
import library_project.users.Users;

public class DemoClass {


    public static void main(String[] args) {

        Library library = new Library(Library.getAllBooksFromFile());
        library.showAllBooks();


        Users.registrationForm();
        Users.loginForm();

    }
}
