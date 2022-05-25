package library_project.demo;

import library_project.users.User;
import library_project.users.Users;

import java.util.Set;

public class DemoClass {

    public static void main(String[] args) {
        Set<User> allUsers = Users.getExistingUsersFromFile();

        User firstUser = Users.registrationForm();



    }


}
