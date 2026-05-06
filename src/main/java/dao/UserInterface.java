package dao;

 
import java.util.List;

import model.UserModel;
 
public interface UserInterface {
    String Login(UserModel um);
    List<UserModel> getAllUsers();
    UserModel getUserByEmail(String email);
    UserModel getUserById(int userId);
    int registerUser(UserModel user);
    int updateUser(UserModel user);
    int deleteUser(int userId);
}