package library_project.users;

import library_project.library.Book;
import library_project.library.PersonalLibrary;
import library_project.library.Review;
import library_project.utils.ConsoleColors;
import library_project.utils.IUseFiles;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class User implements IUseFiles {
    private static final AtomicInteger IDCount = new AtomicInteger(0);
    public static final String filepath = "library_project/files/users.csv";

    private final int ID;
    private String username;
    private String password;
    private String firstName;
    private String email;
    private PersonalLibrary personalLibrary;
    private Book[] favouriteBooks;
    private String libraryFilepath;
    private String favouriteBooksFilepath;

    public User(String username, String password, String firstName, String email) {
        this.ID = getLastID() + 1;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.email = email;
        libraryFilepath = "library_project/files/" + username + "/personalLibrary.csv";
        favouriteBooksFilepath = "library_project/files/" + username + "/favouriteBooks.csv";
        favouriteBooks = new Book[10];

        writeToFile();
    }

    public User(int ID, String username, String password, String firstName, String email) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.email = email;
        personalLibrary = new PersonalLibrary(getAllBooksFromFile());
        favouriteBooks = getUserFavouriteBooks();

    }

    public void userInfo() {
        System.out.println();
        System.out.println("USERNAME: " + ConsoleColors.GREEN + username + ConsoleColors.RESET);
        System.out.println("YOUR NAME: " + ConsoleColors.GREEN + firstName + ConsoleColors.RESET);
        System.out.println("E-MAIL ADDRESS: " + ConsoleColors.GREEN + email + ConsoleColors.RESET);
    }


    private int getLastID() {

        File booksFile = new File(Book.filepath);
        int lastID = 0;
        try {
            Scanner sc = new Scanner(booksFile);
            while(sc.hasNextLine()) {
                lastID++;
            }
        } catch (FileNotFoundException e) {
            return lastID;
            }
        return lastID;
    }

    private Book[] getUserFavouriteBooks() {
        Book[] favouriteBooks = new Book[10];

        try {
            File favouriteBooksFile = new File(favouriteBooksFilepath);

            Scanner scan = new Scanner(favouriteBooksFile);
            String[] favouriteISBNs = scan.nextLine().split(",");

            int index = 0;
            for(Book book : personalLibrary.getBooks()) {
                for(String ISBN : favouriteISBNs) {
                    if (String.valueOf(book.getNumberISBN()).equals(ISBN)) {
                        favouriteBooks[index] = book;
                        index++;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "File not found!" + ConsoleColors.RESET);
        }

        return favouriteBooks;
    }

    public void addBookToFavourites(Book book) {
        List<Book> books = Arrays.asList(favouriteBooks);
        if (books.contains(book)) {
            System.out.println(ConsoleColors.YELLOW + "Book is already in your Favourites." + ConsoleColors.RESET);
            return;
        }
        if (favouriteBooks.length == 10) {
            System.out.println(ConsoleColors.YELLOW + "No more room for favourite books." + ConsoleColors.RESET);
        }

        for(int i = 0; i < favouriteBooks.length; i++) {
            if(favouriteBooks[i] == null) {
                favouriteBooks[i] = book;
                System.out.println(ConsoleColors.GREEN + "Successfully added book to Favourites!" );
                System.out.println(book);
                return;
            }
        }
    }

    private Set<Book> getAllBooksFromFile() {
        Set<Book> allBooks = new HashSet<>();

        try {
            File booksFile = new File(Book.filepath);
            File personalLibraryFile = new File(libraryFilepath);

            Scanner scan = new Scanner(personalLibraryFile);
            String[] ISBNs = scan.nextLine().split(",");
            scan = new Scanner(booksFile);

            for(String ISBN : ISBNs) {
                while (scan.hasNextLine()) {
                    String[] bookFields = scan.nextLine().split(",");
                    if (bookFields[2].equals(ISBN)) {

                        Review review = new Review(Double.parseDouble(bookFields[4]), null);
                        Book book = new Book(bookFields[0], bookFields[1], Integer.parseInt(bookFields[2]), bookFields[3], review);
                        allBooks.add(book);
                    }
                }
            }
        }
        catch (NullPointerException e) {
            System.out.println(ConsoleColors.YELLOW + "Filepath is empty!" + ConsoleColors.RESET);
        }
        catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "Could not open file!" + ConsoleColors.RESET);
        }

        return allBooks;
    }

    private void changeEmail(String newEmail) {

    }

    private void changePassword(String newPassword) {

    }

    private void changeName(String newName) {

    }



    //TODO set specific exception

    public void writeToFile() {
        try {

            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(ID + "," + username + "," + password + "," + firstName + "," + email);
            pw.flush();
            pw.close();

            System.out.println(ConsoleColors.GREEN + "Successfully added user " + username + " to file!\n" + ConsoleColors.RESET);
        }
        catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Failed to add user " + username + " to file!\n" + ConsoleColors.RESET);
        }
    }
    @Override
    public void editFile() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getID() {
        return ID;
    }

    public PersonalLibrary getPersonalLibrary() {
        return personalLibrary;
    }

    public void setPersonalLibrary(PersonalLibrary personalLibrary) {
        this.personalLibrary = personalLibrary;
    }

    public Book[] getFavouriteBooks() {
        return favouriteBooks;
    }

    public void setFavouriteBooks(Book[] favouriteBooks) {
        this.favouriteBooks = favouriteBooks;
    }

    public String getLibraryFilepath() {
        return libraryFilepath;
    }

    public void setLibraryFilepath(String libraryFilepath) {
        this.libraryFilepath = libraryFilepath;
    }

    public String getFavouriteBooksFilepath() {
        return favouriteBooksFilepath;
    }

    public void setFavouriteBooksFilepath(String favouriteBooksFilepath) {
        this.favouriteBooksFilepath = favouriteBooksFilepath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
