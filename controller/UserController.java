package controller;

import model.User;
import service.UserService;

public class UserController {
    UserService userService = new UserService();
    public  String registerUser(){
        User u1 = new User(1,"Chirag","test@gmail.com","admin");
        return UserService.registerUser(u1);
    }
}
