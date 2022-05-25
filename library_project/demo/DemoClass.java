package library_project.demo;

import library_project.User;
import library_project.Users;

import java.util.Set;

public class DemoClass {

    public static void main(String[] args) {

        Set<User> allUsers = Users.getExistingUsersFromFile();
        User firstUser = Users.registrationForm();

    }


}
