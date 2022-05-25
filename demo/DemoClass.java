package demo;

import testing_package.User;
import testing_package.Users;

import java.util.Set;

public class DemoClass {

    public static void main(String[] args) {

        Set<User> allUsers = Users.getExistingUsersFromFile();
        User firstUser = Users.registrationForm();

    }


}
