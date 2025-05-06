import controller.UserController;

public class Main {
    public static void main(String[] args) {
        UserController userController = new UserController();
        System.out.println(userController.registerUser());
    }
}
