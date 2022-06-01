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
                pw.println(fields[0] + "," + fields[1] + "," + fields[2] + "," + fields[3] + "," + fields[4]);
            }
            scan.close();
            pw.flush();
            pw.close();

            if (!oldFile.delete()) {
                System.out.println("Unable to delete");
            }
            File dump = new File(fileToUpdate);
            tempFile.renameTo(dump);
    }
}
