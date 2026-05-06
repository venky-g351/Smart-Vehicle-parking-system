package controller;

import java.io.IOException;

import dao.AdminDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.AdminModel;

@WebServlet("/AdminLoginController")
public class AdminLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("adminLogin.html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        AdminModel admin = new AdminModel();
        admin.setUsername(username);
        admin.setPassword(password);

        AdminDAO dao = new AdminDAO();
        String status = dao.login(admin);

        if ("success".equals(status)) {
            HttpSession session = request.getSession();
            session.setAttribute("adminUsername", username);
            response.sendRedirect("AdminDashboard.jsp");
        } else {
            request.setAttribute("msg", "Invalid username or password!");
            RequestDispatcher rd = request.getRequestDispatcher("adminLogin.html");
            rd.forward(request, response);
        }
    }
}