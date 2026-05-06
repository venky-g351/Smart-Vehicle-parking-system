package controller;

import java.io.IOException;

import dao.BookingDAO;
import dao.ParkingSlotDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AdminDeleteController")
public class AdminDeleteController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminUsername") == null) {
            response.sendRedirect("adminLogin.html");
            return;
        }

        String type = request.getParameter("type");
        String id   = request.getParameter("id");

        if (type == null || id == null) {
            response.sendRedirect("AdminDashboard.jsp?error=Invalid request");
            return;
        }

        try {
            if ("slot".equals(type)) {
                ParkingSlotDAO pdao = new ParkingSlotDAO();
                int status = pdao.deleteParkingSlot(Integer.parseInt(id));
                if (status > 0) {
                    response.sendRedirect("manageSlots.jsp?msg=Slot deleted successfully");
                } else if (status == -1) {
                    response.sendRedirect("manageSlots.jsp?error=Cannot delete slot with active bookings");
                } else {
                    response.sendRedirect("manageSlots.jsp?error=Failed to delete slot");
                }
            } else if ("user".equals(type)) {
                UserDAO udao = new UserDAO();
                int status = udao.deleteUser(Integer.parseInt(id));
                if (status > 0) {
                    response.sendRedirect("viewUsers.jsp?msg=User deleted successfully");
                } else if (status == -1) {
                    response.sendRedirect("viewUsers.jsp?error=Cannot delete user with active bookings");
                } else {
                    response.sendRedirect("viewUsers.jsp?error=Failed to delete user");
                }
            } else if ("booking".equals(type)) {
                BookingDAO bdao = new BookingDAO();
                int status = bdao.deleteBooking(Integer.parseInt(id));
                if (status > 0) {
                    response.sendRedirect("viewAllBookings.jsp?msg=Booking cancelled successfully");
                } else {
                    response.sendRedirect("viewAllBookings.jsp?error=Failed to cancel booking");
                }
            } else {
                response.sendRedirect("AdminDashboard.jsp?error=Invalid operation type");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("AdminDashboard.jsp?error=Invalid ID format");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}