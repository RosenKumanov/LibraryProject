package library_project.library;

import library_project.users.User;
import library_project.utils.ConsoleColors;
import library_project.utils.IUseFiles;
import library_project.utils.Menu;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Book implements IUseFiles { //TODO encapsulate fields
    Scanner scan = new Scanner(System.in);
    public static final String filepath = "library_project/files/books.csv";
    private String name;
    private String author;
    private String resume;
    double averageRating;
    public library_project.library.ISBNnum bookISBN;

    public Book() {
    }

    DecimalFormat df = new DecimalFormat("x.x");
    User bookOwner;

    Set<library_project.library.Review> bookReviews;

    public Book(String name, String author, library_project.library.ISBNnum bookISBN, String resume) {
        this.name = name;
        this.author = author;
        this.bookISBN = bookISBN;
        this.resume = resume;
    }

    public Book(String name, String author, library_project.library.ISBNnum bookISBN, String resume, double averageRating, Set<library_project.library.Review> bookReviews) {
        this.name = name;
        this.author = author;
        this.bookISBN = bookISBN;
        this.resume = resume;
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        return "Book:" + ConsoleColors.CYAN + name + '\n' + ConsoleColors.RESET +
                "Author: " + ConsoleColors.CYAN + author + '\n' + ConsoleColors.RESET +
                "ISBN: " + ConsoleColors.CYAN + bookISBN + '\n' + ConsoleColors.RESET +
                "Rating: " + ConsoleColors.CYAN + averageRating + '\n'+ ConsoleColors.RESET +
                "Resume: " + ConsoleColors.CYAN + resume + '\n' ;
    }

    public void updateRating () {
        library_project.library.Review review = new library_project.library.Review();
        int ratingByCurrentUser = review.getRatingByCurrentUser();
        averageRating = (averageRating+ratingByCurrentUser)/bookReviews.size();
        System.out.println(ConsoleColors.YELLOW + "Thank you for your rating!" + ConsoleColors.RESET);
        System.out.println("The Rating of this book is: " + averageRating);
    }

    public void editBook() throws IOException { //TODO
        // if user is bookowner
        editBookOptions();
        //else
        System.out.println("Only the Book Owner can Edit a book!");


    }
    private void editBookOptions () throws IOException {
        System.out.println("---------");
        System.out.println(ConsoleColors.BLACK_BOLD + ConsoleColors.WHITE_BACKGROUND + "Options:" + "\n" + ConsoleColors.RESET +
                "1. Edit Book name"+ '\n' +
                "2. Edit Book Author " + "\n" +
                "3. Edit Book ISBN" + '\n' +
                "4. Edit Book resume" + '\n' +
                "5. Exit to " + ConsoleColors.PURPLE_BOLD + "MAIN MENU" + ConsoleColors.RESET);
        int command = Integer.parseInt(scan.nextLine());
        String editedItem = scan.nextLine();
        switch (command) {
            case 1:
                updateFile(filepath,name,editedItem);
                break;
            case 2:
                updateFile(filepath,author,editedItem);
                break;
            case 3:
                updateFile(filepath, library_project.library.ISBNnum.setISBNnum().getISBN(),editedItem);
                break;
            case 4:
                updateFile(filepath,resume,editedItem.replaceAll(",", "/"));
                break;
            case 5:
                Menu.start();
            default:
                System.out.println(ConsoleColors.RED_BOLD + "Wrong Input");
                editBookOptions();
        }
    }


    public Set<library_project.library.Review> showAllReviews() {
        bookReviews = new HashSet<>();
        File booksFile = new File(library_project.library.Review.filepath);

        try {
            Scanner sc = new Scanner(booksFile);
            while (sc.hasNextLine()) {
                String[] reviewFields = sc.nextLine().split(",");
                library_project.library.Review review = new library_project.library.Review( Integer.parseInt(reviewFields[0]), reviewFields[1].replaceAll("/", ","));
                bookReviews.add(review);
                sc.close();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "Could not open file!");
        }

        return bookReviews;
    }

    private void addReviewToSet (library_project.library.Review review) {
        bookReviews.add(review);
        updateRating();
    }

    private library_project.library.ISBNnum getBookISBN() {
        return bookISBN;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public String getBookName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getResume() {
        return showResume();
    }

    public static Book addNewBook () {
        Book bookToAdd = new Book();
        try {
            System.out.println(ConsoleColors.YELLOW + "Type book name: " + ConsoleColors.RESET);
            InputStreamReader in = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(in);

            bookToAdd.name = br.readLine();
            System.out.println(ConsoleColors.YELLOW + "Type book author: " + ConsoleColors.RESET);
            bookToAdd.author = br.readLine();

            bookToAdd.bookISBN = library_project.library.ISBNnum.setISBNnum();

            System.out.println(ConsoleColors.YELLOW + "Type book resume: " + ConsoleColors.RESET);

            bookToAdd.resume = br.readLine();

            //bookToAdd.bookOwner.getUsername();

        } catch (Exception e) {
            InputStreamReader in = new InputStreamReader(System.in);  //create BufferedReader class object to get input from user
            BufferedReader br = new BufferedReader(in);
            try {
                System.out.println('\n' + ConsoleColors.BLACK_BOLD + ConsoleColors.RED_BACKGROUND + "Something went wrong :(");
                System.out.println("---------");
                System.out.println(ConsoleColors.BLACK_BOLD + ConsoleColors.YELLOW_BACKGROUND + "Options:" + "\n" + ConsoleColors.RESET + "1. Try again"+ '\n' + "2. Exit to " + ConsoleColors.PURPLE_BOLD + "MAIN MENU" + ConsoleColors.RESET);
                int command = Integer.parseInt(br.readLine());
                if (command == 1) {
                    Book.addNewBook();
                } else if (command == 2) {
                    //to exit to the main menu
                    System.out.println('\n' + "Exiting to the " + ConsoleColors.PURPLE_BOLD + "MAIN MENU" + ConsoleColors.RESET);
                    Menu.start();
                } else {
                    System.out.println('\n' + ConsoleColors.RED + "Wrong input!" + ConsoleColors.RESET);
                }
            } catch (IOException e2){
                System.out.println('\n' + ConsoleColors.RED + "Wrong input!" + ConsoleColors.RESET);
                //to exit to the main menu
                System.out.println('\n' + "Exiting to the " + ConsoleColors.PURPLE_BOLD + "MAIN MENU" + ConsoleColors.RESET);
                Menu.start();
            }

        }


        bookToAdd.writeToFile();
        return bookToAdd;
    }

    @Override
    public void writeToFile() {
        try {

            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println( name + "," + author + "," + bookISBN.getISBN() + "," + resume.replaceAll(",","/") );
            pw.flush();
            pw.close();

            System.out.println( '\n' + ConsoleColors.BLACK_BOLD + ConsoleColors.WHITE_BACKGROUND + "SUCCESSFULLY UPLOADED : "+ ConsoleColors.RESET + '\n' +
                    "Book: " + ConsoleColors.CYAN + name + '\n' + ConsoleColors.RESET +
                    "Author: " + ConsoleColors.CYAN + author + '\n' +  ConsoleColors.RESET +
                    "ISBN: " + ConsoleColors.CYAN + bookISBN.getISBN() + '\n' + ConsoleColors.RESET +
                    "Resume: " + ConsoleColors.BLACK + getResume() + '\n' + ConsoleColors.RESET +
                    //"Book owned by: " + ConsoleColors.BLUE + bookOwner.getUsername() + ConsoleColors.RESET +
                    '\n'
            );
        }
        catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Book was not uploaded!" + ConsoleColors.RESET);
            Menu.start();

        }
    }

    public User setBookOwner () {

        return bookOwner;
    }

    public String showResume () {
        String [] text = resume.split(" ");
        int counter = 0;
        for (String word : text) {
            System.out.print(word + " ");
            counter++;
            if (counter >= 12) {
                System.out.println();
                counter = 0;
            }
        }
        return Arrays.toString(text);
    }

}
