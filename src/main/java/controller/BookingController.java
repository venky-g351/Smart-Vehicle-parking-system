package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import dao.BookingDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.BookingModel;
import model.UserModel;

@WebServlet("/BookingController")
public class BookingController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("userLogin.jsp");
            return;
        }

        String action = request.getParameter("action");

        if ("cancel".equals(action)) {
            try {
                int bookingId = Integer.parseInt(request.getParameter("bookingId"));
                BookingDAO bookingDAO = new BookingDAO();
                int result = bookingDAO.deleteBooking(bookingId);
                if (result > 0) {
                    response.sendRedirect("myBookings.jsp?msg=Booking cancelled successfully");
                } else {
                    response.sendRedirect("myBookings.jsp?error=Failed to cancel booking");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("myBookings.jsp?error=Invalid booking ID");
            }
            return;
        }

        String slotId = request.getParameter("slotId");
        if (slotId != null) {
            response.sendRedirect("bookSlot.jsp?slotId=" + slotId);
        } else {
            response.sendRedirect("userDashboard.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("userLogin.jsp");
            return;
        }

        try {
            String userEmail = (String) session.getAttribute("userEmail");
            UserDAO userDAO = new UserDAO();
            UserModel user = userDAO.getUserByEmail(userEmail);

            if (user == null || user.getUserid() == 0) {
                response.sendRedirect("userLogin.jsp?error=User not found");
                return;
            }

            int slotId               = Integer.parseInt(request.getParameter("slotId"));
            String vehicleNumber     = request.getParameter("vehicleNumber");
            String bookingDateStr    = request.getParameter("bookingDate");
            String entryTimeStr      = request.getParameter("entryTime");

            Date bookingDate = Date.valueOf(bookingDateStr);
            Time entryTime   = Time.valueOf(entryTimeStr + ":00");

            BookingModel booking = new BookingModel();
            booking.setUserId(user.getUserid());
            booking.setSlotId(slotId);
            booking.setVehicleNumber(vehicleNumber);
            booking.setBookingDate(bookingDate);
            booking.setEntryTime(entryTime);
            booking.setBookingStatus("active");

            BookingDAO bookingDAO = new BookingDAO();
            String status = bookingDAO.createBooking(booking);

            if ("success".equals(status)) {
                response.sendRedirect("myBookings.jsp?msg=Booking successful!");
            } else {
                response.sendRedirect("bookSlot.jsp?slotId=" + slotId + "&error=Booking failed!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("bookSlot.jsp?slotId=" + request.getParameter("slotId") + "&error=Invalid data!");
        }
    }
}