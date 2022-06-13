package library_project.users;

import library_project.library.*;
import library_project.utils.ConsoleColors;
import library_project.utils.IUseFiles;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class User implements IUseFiles {
    public static final String FILEPATH = "library_project/files/users.csv";

    private final int ID;
    private final String username;
    private String password;
    private final String firstName;
    private String email;
    private PersonalLibrary personalLibrary;
    private Book[] favouriteBooks;
    private final String favouriteBooksFilepath;

    public User(String username, String password, String firstName, String email) {
        this.ID = getLastID() + 1;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.email = email;
        personalLibrary = new PersonalLibrary("library_project/files/" + username + "PersonalLibrary.csv");
        favouriteBooksFilepath = "library_project/files/" + username + "FavouriteBooks.csv";
        favouriteBooks = new Book[10];

    }

    public User(int ID, String username, String password, String firstName, String email) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.email = email;

        personalLibrary = new PersonalLibrary("library_project/files/" + username + "PersonalLibrary.csv");
        personalLibrary.setBooks(getAllBooksFromFile());
        favouriteBooksFilepath = "library_project/files/" + username + "FavouriteBooks.csv";
        favouriteBooks = getUserFavouriteBooks();
    }

    private int getLastID() {

        File usersFile = new File(FILEPATH);
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

    public void addBookToFavourites(Book book) {
        List<Book> books = Arrays.asList(favouriteBooks);

        for(Book eachBook : books) {
            if (eachBook != null) {
                if (eachBook.getBookName().equalsIgnoreCase(book.getBookName())) {
                    System.out.println(ConsoleColors.YELLOW + "Book is already in your Favourites." + ConsoleColors.RESET);
                    return;
                }
            }
        }

        if (favouriteBooks[9] != null) {
            System.out.println(ConsoleColors.YELLOW + "No more room for favourite books." + ConsoleColors.RESET);
            return;
        }

        for (Book favouriteBook : favouriteBooks) {
            if (favouriteBook == null) {
                writeFavouriteBookToFile(book);
                break;
            }
        }
        updateInfo();
    }

    public void removeFromFavourites(Book book) {

        try {
            deleteFavouriteFromFile(favouriteBooksFilepath, book.bookISBN.getISBN());
            System.out.println("Successfully removed " + ConsoleColors.CYAN + book.getBookName() + ConsoleColors.RESET + " from Favourites!");
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Issue with favourite books filepath!" + ConsoleColors.RESET);
        }
        updateInfo();
    }

    private void deleteFavouriteFromFile(String fileToUpdate, String isbn) throws IOException {

        File oldFile = new File(fileToUpdate);
        File tempFile = new File("library_project/files/temp.csv");

        FileWriter fw = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        Scanner scan = new Scanner(oldFile);

        while(scan.hasNextLine()) {
            String current = scan.nextLine();
            if (!isbn.equals(current)) {
                pw.println(current);
            }
        }
        pw.flush();
        pw.close();
        scan.close();


            if (!oldFile.delete()) {
                System.out.println("Unable to delete");
            }
            File dump = new File(fileToUpdate);
            tempFile.renameTo(dump);
        }

    public void addBookToLibrary(Book book) {
        Set<Book> books = personalLibrary.getBooks();
        if(!books.isEmpty()) {
            for (Book eachBook : books) {
                if(eachBook.getBookName().equalsIgnoreCase(book.getBookName())) {
                    System.out.println(ConsoleColors.YELLOW + "Book is already in your Library." + ConsoleColors.RESET);
                    return;
                }
            }
        }
        personalLibrary.writeToFile(book);
        updateInfo();
    }

    private Set<Book> getAllBooksFromFile() {
        Set<Book> allBooks = new HashSet<>();

        try {
            File booksFile = new File(Book.FILEPATH);
            File personalLibraryFile = new File(personalLibrary.getLibraryFilepath());

            Scanner scan = new Scanner(personalLibraryFile);

            Set<String> ISBNs = new HashSet<>();
            while(scan.hasNextLine()) {
                ISBNs.add(scan.nextLine().trim());
            }
            scan.close();

            for(String ISBN : ISBNs) {
                scan = new Scanner(booksFile);
                while (scan.hasNextLine()) {
                    String[] bookFields = scan.nextLine().split(",");
                    if (bookFields[2].equals(ISBN)) {

                        Book book;
                        if(bookFields.length < 6) {
                            book = new Book(bookFields[0], bookFields[1], new ISBNnum(bookFields[2]),bookFields[3]);
                        }
                        else {
                            //TODO add function for getting all reviews from file
                            Set<Review> reviews = new HashSet<>();

                            book = new Book(bookFields[0], bookFields[1], new ISBNnum(bookFields[2]), bookFields[3], Double.parseDouble(bookFields[4]), reviews);
                        }
                        book.updateAverageRating();
                        allBooks.add(book);
                        break;
                    }
                }
                scan.close();
            }
        }
        catch (NullPointerException e) {
            return null;
        }
        catch (FileNotFoundException e) {
            createFile(personalLibrary.getLibraryFilepath());
        }

        return allBooks;
    }

    private Book[] getUserFavouriteBooks() {
        Book[] favouriteBooks = new Book[10];
        Library library = Library.generateMainLibrary();

        try {
            File favouriteBooksFile = new File(favouriteBooksFilepath);

            Scanner scan = new Scanner(favouriteBooksFile);

            String[] favouriteISBNs = new String[10];
            int index = 0;

            while(scan.hasNextLine()) {
                favouriteISBNs[index] = scan.nextLine();
                index++;
            }
            scan.close();

            index = 0;

            if(library.getBooks().isEmpty()) {
                return null;
            }
            for(Book book : library.getBooks()) {

                for(String ISBN : favouriteISBNs) {

                    if (book.bookISBN.getISBN().equals(ISBN)) {
                        book.updateAverageRating();
                        favouriteBooks[index] = book;
                        index++;
                    }
                }
            }

        } catch(NullPointerException e) {
            return null;
        }

        catch (FileNotFoundException e) {
            createFile(favouriteBooksFilepath);
        }

        return favouriteBooks;
    }

    public void updateInfo() {
        personalLibrary.setBooks(getAllBooksFromFile());
        favouriteBooks = getUserFavouriteBooks();
    }

    public void changeEmail() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the new email address you'd like to use: ");

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
            updateFile(FILEPATH, email, newEmail);

        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Failed to update email - filepath missing" + ConsoleColors.RESET);
        }
        email = newEmail;
    }

    public void changePassword() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Type your old password: ");
        String oldPassword = sc.nextLine();

        while(!encryptPassword(oldPassword).equals(String.valueOf(password))) {
            System.out.println(ConsoleColors.YELLOW + "\nWrong password! Try again: " + ConsoleColors.RESET);
            oldPassword = sc.nextLine();
        }
        System.out.println("Type the new password you'd like to use: ");
        String newPassword = sc.nextLine();

        while(UserRepo.isPasswordWeak(newPassword)) {
            System.out.println(ConsoleColors.YELLOW + "You need to include at least 3 of the following: \n" + ConsoleColors.RESET);
            System.out.println("1. Lower case letters");
            System.out.println("2. Upper case letters");
            System.out.println("3. Numbers");
            System.out.println("4. Symbols");
            newPassword = sc.nextLine();
        }

        updatePassword(oldPassword,newPassword);
    }

    private void updatePassword(String oldPassword, String newPassword) {
        try {
            updateFile(FILEPATH, encryptPassword(oldPassword), encryptPassword(newPassword));

        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Failed to update password - filepath missing" + ConsoleColors.RESET);
        }
        System.out.println(ConsoleColors.GREEN + "\nSuccessfully changed password!\n" + ConsoleColors.RESET);
        password = newPassword;
    }

    public static String encryptPassword(String password) {

        String encryptedPassword = null;
        try
        {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for (byte aByte : bytes) {
                s.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            encryptedPassword = s.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.println(ConsoleColors.RED + "Issue encrypting password!" + ConsoleColors.RESET);
        }

        return encryptedPassword;
    }

    private void createFile(String filepath) {
        try {
            File newFile = new File(filepath);
            newFile.createNewFile();
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Issue creating file! " + ConsoleColors.RESET);
        }

    }

    private void writeFavouriteBookToFile(Book bookToAdd) {
        try {
            FileWriter fw = new FileWriter(favouriteBooksFilepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(bookToAdd.bookISBN.getISBN());

            pw.flush();
            pw.close();

            System.out.println(ConsoleColors.GREEN + "\nSuccessfully added " + ConsoleColors.CYAN + bookToAdd.getBookName() + ConsoleColors.GREEN + " to Favourites!\n" + ConsoleColors.RESET);
        }

        catch (NullPointerException e) {
            System.out.println("Something is wrong/Null pointer");
        }

        catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Failed to add " + ConsoleColors.CYAN + bookToAdd.getBookName() + " to file!\n" + ConsoleColors.RESET);
        }

    }

    public void removeFromMyLibrary(Book book) {
        try {
            deleteFavouriteFromFile(personalLibrary.getLibraryFilepath(), book.bookISBN.getISBN());
            System.out.println("Successfully removed " + ConsoleColors.CYAN + book.getBookName() + ConsoleColors.RESET + " from " + ConsoleColors.CYAN + "My library" + ConsoleColors.RESET + "!");
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Issue with favourite books filepath!" + ConsoleColors.RESET);
        }
        updateInfo();
    }

    public void writeToFile() {
        try {

            FileWriter fw = new FileWriter(FILEPATH, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(ID + "," + username + "," + encryptPassword(password) + "," + firstName + "," + email);
            pw.flush();
            pw.close();

            System.out.println(ConsoleColors.GREEN + "Successfully registered user " + ConsoleColors.CYAN + username + ConsoleColors.GREEN + "!\n" + ConsoleColors.RESET);
        }
        catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Failed to add user " + username + " to file!\n" + ConsoleColors.RESET);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public PersonalLibrary getPersonalLibrary() {
        return personalLibrary;
    }


    public Book[] getFavouriteBooks() {
        return favouriteBooks;
    }

    public String getEmail() {
        return email;
    }
}
