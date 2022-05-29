package library_project.library;

import library_project.utils.ConsoleColors;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ISBNnum {
        String ISBN;

        public ISBNnum(String ISBN) {
            this.ISBN = ISBN;
        }

        private static boolean isISBN13(String number) {
            int sum = 0;
            int multiple = 0;
            char ch = '\0';
            int digit = 0;

            for(int i=1; i<=13; i++) {

                if(i % 2 == 0) {
                    multiple = 3;
                } else { multiple = 1;}

                // fetch digit
                ch = number.charAt(i-1);
                // convert it to number
                digit = Character.getNumericValue(ch);

                sum += (multiple*digit);
            }

            if(sum%10 == 0) {
                return true; }
            else {
                return false; }
        }

        public String getISBN() {
            return ISBN;
        }

        public ISBNnum setISBNnum() {
            String ISBN = null;
            try {
                //create BufferedReader class object to get input from user
                InputStreamReader in = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(in);

                //show custom message
                System.out.println("Enter first 13 digit ISBN number");

                //store user entered value into variable num
                ISBN = new String(br.readLine());

                if (isISBN13(ISBN)) {
                    System.out.println("Book IBSN: " + ISBN); }
                else {
                    System.out.println(ConsoleColors.GREEN + ISBN + " is not valid!"+ ConsoleColors.RESET);
                }
            }catch(Exception e) {
                System.out.println("Please enter the ISBN for this book!");
            }
            return new library_project.library.ISBNnum(ISBN);

        }

    }


