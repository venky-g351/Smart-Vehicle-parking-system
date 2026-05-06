package controller;

import java.io.IOException;

import dao.BookingDAO;
import dao.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.BookingModel;
import model.UserModel;

@WebServlet("/AdminEditController")
public class AdminEditController extends HttpServlet {

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
            if ("user".equals(type)) {
                UserDAO udao = new UserDAO();
                UserModel user = udao.getUserById(Integer.parseInt(id));
                if (user != null && user.getUserid() > 0) {
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("editUser.jsp").forward(request, response);
                } else {
                    response.sendRedirect("viewUsers.jsp?error=User not found");
                }
            } else if ("booking".equals(type)) {
                BookingDAO bdao = new BookingDAO();
                BookingModel booking = bdao.getBookingById(Integer.parseInt(id));
                if (booking != null && booking.getBookingId() > 0) {
                    request.setAttribute("booking", booking);
                    request.getRequestDispatcher("editBooking.jsp").forward(request, response);
                } else {
                    response.sendRedirect("viewAllBookings.jsp?error=Booking not found");
                }
            } else {
                response.sendRedirect("AdminDashboard.jsp?error=Invalid type");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("AdminDashboard.jsp?error=Invalid ID format");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminUsername") == null) {
            response.sendRedirect("adminLogin.html");
            return;
        }

        String type = request.getParameter("type");

        try {
            if ("user".equals(type)) {
                UserModel user = new UserModel();
                user.setUserid(Integer.parseInt(request.getParameter("userId")));
                user.setUserName(request.getParameter("userName"));
                user.setEmail(request.getParameter("email"));
                user.setVehicleNumber(request.getParameter("vehicleNumber"));
                user.setVehicleType(request.getParameter("vehicleType"));

                UserDAO udao = new UserDAO();
                int status = udao.updateUser(user);

                if (status > 0) {
                    response.sendRedirect("viewUsers.jsp?msg=User updated successfully");
                } else {
                    response.sendRedirect("editUser.jsp?id=" + user.getUserid() + "&error=Update failed");
                }
            } else if ("booking".equals(type)) {
                BookingModel booking = new BookingModel();
                booking.setBookingId(Integer.parseInt(request.getParameter("bookingId")));
                booking.setBookingStatus(request.getParameter("bookingStatus"));

                BookingDAO bdao = new BookingDAO();
                int status = bdao.updateBookingStatus(booking);

                if (status > 0) {
                    response.sendRedirect("viewAllBookings.jsp?msg=Booking updated successfully");
                } else {
                    response.sendRedirect("editBooking.jsp?id=" + booking.getBookingId() + "&error=Update failed");
                }
            } else {
                response.sendRedirect("AdminDashboard.jsp?error=Invalid type");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("viewUsers.jsp?error=Invalid number format");
        }
    }
}