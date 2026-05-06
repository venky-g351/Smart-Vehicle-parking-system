package controller;

import java.io.IOException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UserModel;

@WebServlet("/UserRegisterController")
public class UserRegisterController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userName      = request.getParameter("userName");
        String email         = request.getParameter("email");
        String vehicleNumber = request.getParameter("vehicleNumber");
        String vehicleType   = request.getParameter("vehicleType");
        String password      = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate passwords match
        if (password == null || !password.equals(confirmPassword)) {
            response.sendRedirect("index.jsp?error=Passwords do not match");
            return;
        }

        try {
            UserDAO dao = new UserDAO();

            // Check if email already registered
            UserModel existing = dao.getUserByEmail(email);
            if (existing != null && existing.getUserid() > 0) {
                response.sendRedirect("index.jsp?error=Email Already Registered");
                return;
            }

            UserModel user = new UserModel();
            user.setUserName(userName);
            user.setEmail(email);
            user.setVehicleNumber(vehicleNumber);
            user.setVehicleType(vehicleType);
            user.setPassword(password);

            int result = dao.registerUser(user);

            if (result > 0) {
                response.sendRedirect("userLogin.jsp?success=Registered Successfully! Please login.");
            } else {
                response.sendRedirect("index.jsp?error=Registration Failed. Try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=An error occurred. Please try again.");
        }
    }
}