package library_project.library;

import library_project.utils.ConsoleColors;
import library_project.utils.IUseFiles;

import java.io.*;
import java.util.Scanner;

public class Review implements IUseFiles {

    public static final String filepath = "library_project/files/bookReview.csv";

    private int ratingByCurrentUser;
    private String commentByCurrentUser;
    private String currentUser;
    private String currentBookISBN;

    public Review() {
    }
    public Review(String currentUser, String currentBookISBN, int ratingByCurrentUser, String commentByCurrentUser) {
        this.currentUser = currentUser;
        this.currentBookISBN = currentBookISBN;
        this.ratingByCurrentUser = ratingByCurrentUser;
        this.commentByCurrentUser = commentByCurrentUser;
    }

    public Review( String currentUser, String currentBookISBN, int ratingByCurrentUser) {
        this.currentUser = currentUser;
        this.currentBookISBN = currentBookISBN;
        this.ratingByCurrentUser = ratingByCurrentUser;
    }

    public static void addComment(String user, String ISBN) {
        String comment;
        Scanner sc = new Scanner(System.in);

        System.out.println("\nWrite your comment here: ");

        comment = sc.nextLine();
        System.out.println(ConsoleColors.GREEN + "\nThank you for commenting!\n" + ConsoleColors.RESET);

        try {
            updateCommentInFile(user, ISBN, comment);
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Issue updating file!" + ConsoleColors.RESET);
        }
    }

    public static void addNewComment(String user, String ISBN) {
        Review newComment = new Review();
        System.out.println("\nWrite your comment here: ");
        Scanner scan = new Scanner(System.in);

        newComment.commentByCurrentUser = scan.nextLine();
        System.out.println("\nThank you for commenting!\n");
        newComment.setCurrentUser(user);
        newComment.setCurrentBookISBN(ISBN);
        newComment.writeToFile();
    }

    private void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void setCurrentBookISBN(String currentBookISBN) {
        this.currentBookISBN = currentBookISBN;
    }

    public static void addRating (String user, String ISBN) {

        Review newRating = new Review();
        System.out.println("You can rate this book with a grade from 1 to 5. Type a number here: ");
        Scanner scan = new Scanner(System.in);

        try {
            newRating.ratingByCurrentUser = Integer.parseInt(scan.nextLine());
            if (newRating.ratingByCurrentUser < 1 || newRating.ratingByCurrentUser > 5) {
                System.out.println("Please, enter a number from 1 to 5!");
               addRating(user, ISBN);
            } else {
                System.out.println(ConsoleColors.GREEN + "You rated this book with " + ConsoleColors.BLUE + newRating.ratingByCurrentUser + ConsoleColors.RESET);
            }
        }
          catch (NumberFormatException e1) {
            System.out.println(ConsoleColors.RED_UNDERLINED + "Wrong input!" + ConsoleColors.RESET );
            addRating(user, ISBN);
        }
        newRating.setCurrentUser(user);
        newRating.setCurrentBookISBN(ISBN);
        newRating.writeToFile();
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getCurrentBookISBN() {
        return currentBookISBN;
    }

    public String getCommentByCurrentUser() {
        return commentByCurrentUser;
    }

    public static void updateRating (String user, String ISBN) {
        int newRating = 0;

        System.out.println("\nYou can rate this book with a grade from 1 to 5. Please vote!" + ConsoleColors.RESET);
        Scanner scan = new Scanner(System.in);

        try {
            newRating = Integer.parseInt(scan.nextLine());

            if (newRating < 1 || newRating > 5) {
                System.out.println(ConsoleColors.YELLOW + "Please, enter a number from 1 to 5!" + ConsoleColors.RESET);
                updateRating(user, ISBN);
            } else {
                System.out.println(ConsoleColors.GREEN + "\nYou rated this book with " + ConsoleColors.BLUE + newRating + ConsoleColors.RESET);
            }
        }
        catch (NumberFormatException e1) {
            System.out.println(ConsoleColors.RED_UNDERLINED + "Wrong input!" + ConsoleColors.RESET );
            updateRating(user, ISBN);
        }
        try {
            updateRatingInFile(user, ISBN, newRating);
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Issue opening reviews file!" + ConsoleColors.RESET);
        }


    }

    private static void updateRatingInFile(String username, String ISBN, int newRating) throws IOException {
        File oldFile = new File(filepath);
        File tempFile = new File("library_project/files/temp.csv");

        FileWriter fw = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        Scanner scan = new Scanner(oldFile);

        while (scan.hasNextLine()) {
            String[] fields = scan.nextLine().split(",");
            if(fields[0].equalsIgnoreCase(username) && fields[1].equalsIgnoreCase(ISBN)) {
                fields[2] = String.valueOf(newRating);
            }
            for(int i = 0; i < fields.length - 1; i++) {
                pw.print(fields[i] + ",");
            }
            pw.print(fields[fields.length - 1]);
            pw.println();
        }
        scan.close();
        bw.close();
        fw.close();
        pw.flush();
        pw.close();

        if (!oldFile.delete()) {
            System.out.println("Unable to delete");
        }
        File dump = new File(filepath);
        tempFile.renameTo(dump);
    }

    private static void updateCommentInFile(String username, String ISBN, String comment) throws IOException {
        File oldFile = new File(filepath);
        File tempFile = new File("library_project/files/temp.csv");

        FileWriter fw = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        Scanner scan = new Scanner(oldFile);

        while (scan.hasNextLine()) {
            String[] fields = scan.nextLine().split(",");
            if(fields[0].equalsIgnoreCase(username) && fields[1].equalsIgnoreCase(ISBN)) {
                fields[3] = comment;
            }
            for(int i = 0; i < fields.length - 1; i++) {
                pw.print(fields[i] + ",");
            }
            pw.print(fields[fields.length - 1]);
            pw.println();
        }
        scan.close();
        bw.close();
        fw.close();
        pw.flush();
        pw.close();

        if (!oldFile.delete()) {
            System.out.println("Unable to delete");
        }
        File dump = new File(filepath);
        tempFile.renameTo(dump);

    }

    @Override
    public void writeToFile() {
        try {

            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            if (commentByCurrentUser != null) {
                pw.println(getCurrentUser() + "," + getCurrentBookISBN() + "," + "no rating" + "," + commentByCurrentUser.replaceAll(",", "/"));
                pw.flush();
                pw.close();
                System.out.println( ConsoleColors.BLUE_UNDERLINED + currentUser + ConsoleColors.RESET +  ConsoleColors.YELLOW + ", your comment: " + ConsoleColors.PURPLE);
                showComment();
                System.out.println(ConsoleColors.RESET);

            } else if (ratingByCurrentUser != 0) {
                pw.println(currentUser + "," + currentBookISBN + "," + ratingByCurrentUser + "," + "no comment");
                pw.flush();
                pw.close();
            }
        }
        catch (IOException e) {
            System.out.println( ConsoleColors.BLUE_BOLD + currentUser + ConsoleColors.RED + ", you failed to add a comment!" + ConsoleColors.RESET);

        }
    }

    public void showComment () {
        String [] text = commentByCurrentUser.split(" ");
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

    public int getRatingByCurrentUser() {
        return ratingByCurrentUser;
    }
}