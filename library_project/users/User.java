package library_project.users;

import library_project.library.Book;
import library_project.library.ISBNnum;
import library_project.library.PersonalLibrary;
import library_project.library.Review;
import library_project.utils.ConsoleColors;
import library_project.utils.IUseFiles;

import java.io.*;
import java.util.*;

public class User implements IUseFiles {
    public static final String filepath = "library_project/files/users.csv";
    public static final String root = "library_project/files/";

    private final int ID;
    private String username;
    private String password;
    private String firstName;
    private String email;
    private PersonalLibrary personalLibrary;
    private Book[] favouriteBooks;
    private String favouriteBooksFilepath;

    public User(String username, String password, String firstName, String email) {
        this.ID = getLastID() + 1;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.email = email;
        personalLibrary.setLibraryFilepath("library_project/files/" + username + "PersonalLibrary.csv");
        favouriteBooksFilepath = "library_project/files/" + username + "FavouriteBooks.csv";
        favouriteBooks = new Book[10];

    }

    public User(int ID, String username, String password, String firstName, String email) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.email = email;
        personalLibrary = new PersonalLibrary(getAllBooksFromFile());
        personalLibrary.setLibraryFilepath("library_project/files/" + username + "/personalLibrary.csv");
        favouriteBooksFilepath = "library_project/files/" + username + "/favouriteBooks.csv";
        favouriteBooks = getUserFavouriteBooks();
    }

    public void userInfo() {
        System.out.println();
        System.out.println("USERNAME: " + ConsoleColors.GREEN + username + ConsoleColors.RESET);
        System.out.println("YOUR NAME: " + ConsoleColors.GREEN + firstName + ConsoleColors.RESET);
        System.out.println("E-MAIL ADDRESS: " + ConsoleColors.GREEN + email + ConsoleColors.RESET);
    }

    private int getLastID() {

        File usersFile = new File(filepath);
        int lastID = 0;
        try {
            Scanner sc = new Scanner(usersFile);
            while(sc.hasNextLine()) {
                lastID++;
                sc.nextLine();
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
            if(personalLibrary.getBooks().isEmpty()) {
                return null;
            }
            for(Book book : personalLibrary.getBooks()) {
                for(String ISBN : favouriteISBNs) {
                    if (book.bookISBN.getISBN().equals(ISBN)) {
                        favouriteBooks[index] = book;
                        index++;
                    }
                }
            }

        } catch(NullPointerException e) {
            return null;
        }

        catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "Folder not found! Creating it instead..." + ConsoleColors.RESET);
            File file = new File(favouriteBooksFilepath);
        }

        return favouriteBooks;
    }

    public void addBookToLibrary(Book book) {
        Set<Book> books = personalLibrary.getBooks();
        if(books != null) {
            if (books.contains(book)) {
                System.out.println(ConsoleColors.YELLOW + "Book is already in your Library." + ConsoleColors.RESET);
                return;
            }
        }
        personalLibrary.writeToFile(book);
        updateInfo();
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
                System.out.println("Successfully added " + ConsoleColors.CYAN + book.getBookName() + " to Favourites!" );
            }
        }
        updateInfo();
    }

    public void updateInfo() {
        personalLibrary = new PersonalLibrary(getAllBooksFromFile());
        favouriteBooks = getUserFavouriteBooks();
    }

    private Set<Book> getAllBooksFromFile() {
        Set<Book> allBooks = new HashSet<>();

        try {
            File booksFile = new File(Book.filepath);
            File personalLibraryFile = new File(personalLibrary.getLibraryFilepath());

            Scanner scan = new Scanner(personalLibraryFile);
            String[] ISBNs = scan.nextLine().split(",");
            scan = new Scanner(booksFile);


            for(String ISBN : ISBNs) {
                while (scan.hasNextLine()) {
                    String[] bookFields = scan.nextLine().split(",");
                    if (bookFields[2].equals(ISBN)) {

                        //TODO add function for getting all reviews from file
                        Set<Review> reviews = new HashSet<>();
                        Book book = new Book(bookFields[0], bookFields[1], new ISBNnum(bookFields[2]),bookFields[3], Double.parseDouble(bookFields[4]),  reviews);
                        allBooks.add(book);
                    }
                }
            }
        }
        catch (NullPointerException e) {
            return null;
        }
        catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "File not found! Creating it instead..." + ConsoleColors.RESET);
            File file = new File(personalLibrary.getLibraryFilepath());
            file.mkdirs();
        }

        return allBooks;
    }

    public void changeEmail() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type the new email address you'd like to use: ");

        String input = sc.nextLine();
        while(input.contains(" ") || !input.contains("@") || !input.contains(".")) {
            System.out.println("Invalid email address! Type a proper email: ");
            input = sc.nextLine();
        }
        updateEmailAddress(input);
        System.out.println("\nSuccessfully changed email address to \"" + ConsoleColors.CYAN + email + ConsoleColors.RESET + "\"");
    }

    private void updateEmailAddress(String newEmail) {



        try {
            updateFile(filepath, email, newEmail);

        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Failed to update email - filepath missing" + ConsoleColors.RESET);
        }
        email = newEmail;

    }

    public void changePassword() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type the new password you'd like to use: ");

        String input = sc.nextLine();
        updatePassword(input);
    }

    private void updatePassword(String newPassword) {
        try {
            updateFile(filepath, password, newPassword);

        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Failed to update email - filepath missing" + ConsoleColors.RESET);
        }
        password = newPassword;
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
