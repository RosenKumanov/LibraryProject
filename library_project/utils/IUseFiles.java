package library_project.utils;


import java.io.*;
import java.util.Scanner;

public interface IUseFiles {

    void writeToFile();

    default void updateFile(String fileToUpdate, String old, String replace) throws IOException {
        File oldFile = new File(fileToUpdate);
        File tempFile = new File("library_project/files/temp.csv");

            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            Scanner scan = new Scanner(oldFile);

            while (scan.hasNextLine()) {
                String[] fields = scan.nextLine().split(",");

                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].equalsIgnoreCase(old)) {
                        fields[i] = replace;
                        break;
                    }
                }

                for (int i = 0; i < fields.length - 1; i++) {
                    pw.print(fields[i] + ",");
                }

                pw.print(fields[fields.length - 1]);
                pw.println();
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
    }

