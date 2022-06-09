package library_project.library;

import library_project.utils.ConsoleColors;
import library_project.utils.IUseFiles;
import library_project.utils.Menu;
import library_project.utils.Utils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Book implements IUseFiles {
    Scanner scan = new Scanner(System.in);
    public static final String FILEPATH = "library_project/files/books.csv";

    private String name;
    private String author;
    private String resume;
    private double averageRating = 0;
    public ISBNnum bookISBN;
    private String bookOwner;
    private DecimalFormat df = new DecimalFormat("x.x");
    private Set<Review> bookReviews;

    public Book() {
    }

    public Book(String name, String author, ISBNnum bookISBN, String resume, String user) {
        this.name = name;
        this.author = author;
        this.bookISBN = bookISBN;
        this.resume = resume;
        this.bookOwner = user;
    }

    public Book(String name, String author, ISBNnum bookISBN, String resume) {
        this.name = name;
        this.author = author;
        this.bookISBN = bookISBN;
        this.resume = resume;
    }

    public Book(String name, String author, ISBNnum bookISBN, String resume, double averageRating, Set<Review> bookReviews) {
        this.name = name;
        this.author = author;
        this.bookISBN = bookISBN;
        this.resume = resume;

        updateAverageRating();
    }

    @Override
    public String toString() {
        return "Book:" + ConsoleColors.CYAN + name + '\n' + ConsoleColors.RESET +
                "Author: " + ConsoleColors.CYAN + author + '\n' + ConsoleColors.RESET +
                "ISBN: " + ConsoleColors.CYAN + getBookISBN().toString() + '\n' + ConsoleColors.RESET +
                "Rating: " + ConsoleColors.CYAN + averageRating + '\n'+ ConsoleColors.RESET +
                "Resume: ";
    }

    public void updateAverageRating () {
        bookReviews = getAllReviewsFromFile();
        double allRatings = 0;
        int rateCount = 0;
        for(Review review : bookReviews) {
            if(review.getRatingByCurrentUser() != 0) {
                allRatings += review.getRatingByCurrentUser();
                rateCount++;
            }
        }
        averageRating = allRatings / rateCount;
    }

    public void rateBook(String username) {
        bookReviews = getAllReviewsFromFile();

        if(bookReviews.size() != 0) {
            for (Review review : bookReviews) {
                if (review.getCurrentUser().equalsIgnoreCase(username) && review.getCurrentBookISBN().equalsIgnoreCase(bookISBN.getISBN()) && review.getRatingByCurrentUser() != 0) {
                    System.out.println("\nYou have already rated this book. Would you like to change your rating? Y/N:");
                    if (Utils.yesOrNo()) {
                        Review.updateRating(username, bookISBN.getISBN());
                    } else {
                        return;
                    }
                } else if (review.getCurrentUser().equalsIgnoreCase(username) && review.getCurrentBookISBN().equalsIgnoreCase(bookISBN.getISBN())) {
                    Review.updateRating(review.getCurrentUser(), review.getCurrentBookISBN());
                } else {
                    Review.addRating(username, bookISBN.getISBN());
                }
            }
        }
        else {
            Review.addRating(username, bookISBN.getISBN());
        }
    }

    public void commentBook(String username) {
        if(bookReviews.size() != 0) {
            for (Review review : bookReviews) {
                if (review.getCurrentUser().equalsIgnoreCase(username) && review.getCurrentBookISBN().equalsIgnoreCase(bookISBN.getISBN())) {
                    if(!review.getCommentByCurrentUser().equalsIgnoreCase("no comment")) {
                        System.out.println(ConsoleColors.YELLOW + "You have already commented on this book." + ConsoleColors.RESET);
                        return;
                    }
                    else {
                        Review.addComment(username, bookISBN.getISBN());
                    }
                }
            }
        }
        else {
            Review.addNewComment(username, bookISBN.getISBN());
        }

    }

    public Set<Review> getAllReviewsFromFile () {  //създава сет от ревюта за конкретната книга
        Set<Review> allReviews = new HashSet<>(); // TODO Stefi to learn the theory
        File reviewsFile = new File(Review.filepath);

        try {
            Scanner scan = new Scanner(reviewsFile);
            while (scan.hasNextLine()) {
                String[] reviewFields = scan.nextLine().split(",");
                if (reviewFields[1].equals(bookISBN.getISBN())) {
                    Review review;
//                    if (reviewFields[3].equalsIgnoreCase("no comment") && Utils.isNumeric(reviewFields[2])) {
//                        review = new Review(reviewFields[0], reviewFields[1], Integer.parseInt(reviewFields[2]));
                    if(Utils.isNumeric(reviewFields[2])){
                        review = new Review(reviewFields[0], reviewFields[1], Integer.parseInt(reviewFields[2]), reviewFields[3]);
                    }
                    else {
                        review = new Review(reviewFields[0], reviewFields[1], 0, reviewFields[3]);
                    }
                    allReviews.add(review);
                }
            }
            scan.close();

        } catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED_BOLD + "Missing Reviews file" + ConsoleColors.RESET);
        }
        return allReviews;
    }

    public void editBook(String username) {
        try {
            if (bookOwner.equalsIgnoreCase(username)) {
                editBookOptions();
            }
            else {
                System.out.println(ConsoleColors.YELLOW + "Only the Book Owner can Edit a book!" + ConsoleColors.RESET);
                System.out.println("Choose another option: " + ConsoleColors.RESET);
                return;
            }
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Something went wrong - IO exception");
            return;
        }

        System.out.println(ConsoleColors.GREEN + "\nSuccessfully updated book!\n" + ConsoleColors.RESET);

    }

    private static int getInput() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while(true) {
            if(input.isEmpty()) {
                System.out.println("You need to type something!");
                input = sc.nextLine();
                continue;
            }
            try {
                return Integer.parseInt(input);
            }
            catch (NumberFormatException e) {
                System.out.println("You need to type a number!");
                input = sc.nextLine();
            }

        }

    }

    private void editBookOptions () throws IOException {
        System.out.println("---------");
        System.out.println(ConsoleColors.BLACK_BOLD + ConsoleColors.WHITE_BACKGROUND + "Options:" + "\n" + ConsoleColors.RESET +
                "1. Edit Book name"+ '\n' +
                "2. Edit Book Author " + "\n" +
                "3. Edit Book ISBN" + '\n' +
                "4. Edit Book resume" + '\n' +
                "5. Exit to " + ConsoleColors.PURPLE_BOLD + "MAIN MENU" + ConsoleColors.RESET);
        int command = getInput();
        while(command < 1 || command > 5) {
            System.out.println(ConsoleColors.YELLOW + "You need to type a number between 1-5: ");
            command = getInput();
        }

        switch (command) {
            case 1:
                System.out.println("Type the new book name: ");
                String editedItem = scan.nextLine();
                updateFile(FILEPATH,name,editedItem);
                System.out.println("Book name was updated to: " + ConsoleColors.CYAN_BOLD + editedItem + ConsoleColors.RESET + '\n');
                break;
            case 2:
                System.out.println("Type the new book author: ");
                editedItem = scan.nextLine();
                updateFile(FILEPATH,author,editedItem);
                System.out.println("Book Author was updated to: " + ConsoleColors.CYAN_BOLD + editedItem + ConsoleColors.RESET + '\n');
                break;
            case 3:
                ISBNnum newISBN = ISBNnum.setISBNnum();
                updateFile(FILEPATH, bookISBN.getISBN(),newISBN.getISBN());
                System.out.println("Book ISBN was updated to: " + ConsoleColors.CYAN_BOLD + newISBN.getISBN() + ConsoleColors.RESET + '\n');
                break;
            case 4:
                System.out.println("Type the new book resume: ");
                editedItem = scan.nextLine();
                updateFile(FILEPATH,resume.replaceAll(",", "/"),editedItem.replaceAll(",", "/"));
                resume = editedItem;
                System.out.println("Book resume was updated to: ");
                System.out.print(ConsoleColors.CYAN_BOLD);
                showResume();
                System.out.println(ConsoleColors.RESET);
                break;
            case 5:
                Menu.start();
            default:
                System.out.println(ConsoleColors.RED + "Wrong Input");
                editBookOptions();
        }

    }

    public void showAllReviews() {
        bookReviews = getAllReviewsFromFile();
        if(bookReviews.size() == 0) {
            System.out.println("\nThere are no comments for this book yet.\n");
        }

        for ( Review review : bookReviews) {
            if (!review.getCommentByCurrentUser().equalsIgnoreCase("no comment")) {
                System.out.println('\n' + ConsoleColors.BLUE + review.getCurrentUser() + ':' + ConsoleColors.RESET);
                System.out.print(review.getRatingByCurrentUser() > 3 ? ConsoleColors.GREEN : ConsoleColors.YELLOW);
                review.showComment();
                System.out.println(ConsoleColors.PURPLE + "\nRating:" + ConsoleColors.RESET + (review.getRatingByCurrentUser() != 0 ? review.getRatingByCurrentUser() + "/5" : "No rating\n"));
            }
        }
    }

    private ISBNnum getBookISBN() {
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
        return resume;
    }

    public static void addNewBook (String user) {
        Library library = Library.generateMainLibrary();
        Set<Book> allBooks = library.getBooks();
        Book bookToAdd = new Book();
        try {
            System.out.println(ConsoleColors.YELLOW + "Type book name: " + ConsoleColors.RESET);
            InputStreamReader in = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(in);

            bookToAdd.name = br.readLine();
            for(Book book : allBooks) {
                if(bookToAdd.name.equalsIgnoreCase(book.getBookName())) {
                    System.out.println(ConsoleColors.YELLOW + "\nThis book is already available in our Library.\n" + ConsoleColors.RESET);
                    System.out.println("Would you like to try to add a different book? Y/N:");
                    if(Utils.yesOrNo()) {
                        addNewBook(user);
                    }
                    else {
                        return;
                    }
                }
            }
            System.out.println(ConsoleColors.YELLOW + "Type book author: " + ConsoleColors.RESET);
            bookToAdd.author = br.readLine();

            bookToAdd.bookISBN = ISBNnum.setISBNnum();

            System.out.println(ConsoleColors.YELLOW + "Type book resume: " + ConsoleColors.RESET);

            bookToAdd.resume = br.readLine();

            bookToAdd.setBookOwner(user);

        } catch (Exception e) {
            InputStreamReader in = new InputStreamReader(System.in);  //create BufferedReader class object to get input from user
            BufferedReader br = new BufferedReader(in);
            try {
                System.out.println('\n' + ConsoleColors.BLACK_BOLD + ConsoleColors.RED_BACKGROUND + "Something went wrong :(");
                System.out.println("---------");
                System.out.println(ConsoleColors.BLACK_BOLD + ConsoleColors.YELLOW_BACKGROUND + "Options:" + "\n" + ConsoleColors.RESET + "1. Try again"+ '\n' + "2. Exit to " + ConsoleColors.PURPLE_BOLD + "MAIN MENU" + ConsoleColors.RESET);
                int command = Integer.parseInt(br.readLine());
                if (command == 1) {
                    Book.addNewBook(user);
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
    }

    @Override
    public void writeToFile() {
        try {

            FileWriter fw = new FileWriter(FILEPATH, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println( name + "," + author + "," + bookISBN.getISBN() + "," + resume.replaceAll(",","/") + "," + bookOwner);
            pw.flush();
            pw.close();

            System.out.println( '\n' + ConsoleColors.BLACK_BOLD + ConsoleColors.WHITE_BACKGROUND + "SUCCESSFULLY UPLOADED : "+ ConsoleColors.RESET + '\n' +
                    "Book: " + ConsoleColors.CYAN + name + '\n' + ConsoleColors.RESET +
                    "Author: " + ConsoleColors.CYAN + author + '\n' +  ConsoleColors.RESET +
                    "ISBN: " + ConsoleColors.CYAN + bookISBN.getISBN() + '\n' + ConsoleColors.RESET +
                    "Resume: ");
            System.out.print(ConsoleColors.CYAN);
            showResume();
            System.out.println(ConsoleColors.RESET);
        }
        catch (IOException e) {
            System.out.println(ConsoleColors.YELLOW + "Failed to upload book!" + ConsoleColors.RESET);
        }
    }

    public void setBookOwner(String bookOwner) {
        this.bookOwner = bookOwner;
    }

    public void showResume () {
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

    }

}
