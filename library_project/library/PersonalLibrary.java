package library_project.library;

import library_project.utils.ConsoleColors;
import library_project.utils.IUseFiles;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class PersonalLibrary extends Library implements IUseFiles {

    private String libraryFilepath;

    public PersonalLibrary(Set<Book> books) {
        super(books);
    }


    public void writeToFile(Book bookToAdd) {
        try {
            FileWriter fw = new FileWriter(libraryFilepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(bookToAdd.bookISBN.getISBN());
            super.books.add(bookToAdd);
            pw.flush();
            pw.close();

            System.out.println(ConsoleColors.GREEN + "Successfully added " + ConsoleColors.CYAN + bookToAdd.getBookName() + " to My Library!\n" + ConsoleColors.RESET);
        }

        catch (
    IOException e) {
        System.out.println(ConsoleColors.RED + "Failed to add " + ConsoleColors.CYAN + bookToAdd.getBookName() + " to file!\n" + ConsoleColors.RESET);
    }

    }

    public void setLibraryFilepath(String filepath) {
        libraryFilepath = filepath;
    }

    @Override
    public void writeToFile() {

    }

    @Override
    public void editFile() {

    }

    public String getLibraryFilepath() {
        return libraryFilepath;
    }
}
