package service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.User;

public class UserService {


    public static String registerUser(User u)  {
        try{
            FileOutputStream fs = new FileOutputStream("C:/Users/hp/Desktop/Intelli-Route/data/all_users.txt");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(u);
            os.close();
            fs.close();
        }catch (IOException ex){
            ex.printStackTrace();
            return "Io Exception Occured";
        }catch (SecurityException sx){
            sx.printStackTrace();
            return "Security Exception Arises";
        }
        return "Registration Successful";
    }
}
