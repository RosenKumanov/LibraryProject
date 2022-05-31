package library_project.utils;

public class Utils {
    private Utils () {} //с този прайвът конструктор забраняваме да се създава обект Ютилс в други класове


    public static void stopTheSystem(String message) {
        System.err.println(message); //color red
        System.exit(-1);
    }

}
