package controller;

import java.io.IOException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;

@WebServlet("/UserLoginController")
public class UserLoginController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // FIX: was reading "username" but JSP sends "email"
        String email    = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            response.sendRedirect("userLogin.jsp?error=Please fill in all fields");
            return;
        }

        UserModel user = new UserModel();
        user.setEmail(email.trim());
        user.setPassword(password);

        UserDAO dao = new UserDAO();
        String status = dao.Login(user);

        if ("success".equals(status)) {
            HttpSession session = request.getSession();
            session.setAttribute("userEmail", email.trim());
            response.sendRedirect("userDashboard.jsp");
        } else {
            response.sendRedirect("userLogin.jsp?error=Invalid Email or Password");
        }
    }
}