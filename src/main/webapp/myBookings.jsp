<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.BookingDAO, dao.UserDAO, model.BookingModel, model.UserModel, java.util.List, java.text.SimpleDateFormat" %>

<%
    String userEmail = (String) session.getAttribute("userEmail");
    if (userEmail == null) {
        response.sendRedirect("userLogin.jsp");
        return;
    }

    UserDAO userDAO = new UserDAO();
    UserModel currentUser = userDAO.getUserByEmail(userEmail);
    if (currentUser == null) {
        response.sendRedirect("userLogin.jsp");
        return;
    }

    BookingDAO bookingDAO = new BookingDAO();
    List<BookingModel> bookings = bookingDAO.getBookingsByUserId(currentUser.getUserid());

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

    String msg   = request.getParameter("msg");
    String error = request.getParameter("error");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Bookings</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: Arial, sans-serif; }
        body { background: #f5f5f5; padding: 20px; }
        .container { max-width: 1100px; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        h2 { margin-bottom: 15px; color: #333; }
        .back-link { display: inline-block; margin-bottom: 15px; color: #007bff; text-decoration: none; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th { background: #007bff; color: white; padding: 10px; text-align: left; }
        td { padding: 10px; border-bottom: 1px solid #ddd; font-size: 14px; }
        tr:nth-child(even) { background: #f9f9f9; }
        .status-active    { color: green; font-weight: bold; }
        .status-completed { color: blue;  font-weight: bold; }
        .status-cancelled { color: red;   font-weight: bold; }
        .btn { padding: 5px 10px; text-decoration: none; color: white; border-radius: 3px; display: inline-block; font-size: 13px; }
        .btn-cancel { background: red; }
        .msg-success { background: #d4edda; color: #155724; padding: 10px; border-radius: 5px; margin-bottom: 15px; }
        .msg-error   { background: #f8d7da; color: #721c24; padding: 10px; border-radius: 5px; margin-bottom: 15px; }
        .no-bookings { text-align: center; padding: 40px; color: #666; }
    </style>
</head>
<body>
<div class="container">
    <h2>My Bookings</h2>
    <a href="userDashboard.jsp" class="back-link">← Back to Dashboard</a>

    <% if (msg   != null) { %><div class="msg-success"><%= msg   %></div><% } %>
    <% if (error != null) { %><div class="msg-error"><%= error %></div><% } %>

    <% if (bookings == null || bookings.isEmpty()) { %>
        <div class="no-bookings">
            <h3>No bookings found</h3>
            <p><a href="userDashboard.jsp">Book a slot now</a></p>
        </div>
    <% } else { %>
        <table>
            <tr>
                <th>ID</th>
                <th>Slot</th>
                <th>Vehicle</th>
                <th>Date</th>
                <th>Entry</th>
                <th>Exit</th>
                <th>Hours</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <% for (BookingModel b : bookings) {
                String status = b.getBookingStatus() != null ? b.getBookingStatus() : "";
                String cls = "";
                if      ("active".equalsIgnoreCase(status))    cls = "status-active";
                else if ("completed".equalsIgnoreCase(status)) cls = "status-completed";
                else if ("cancelled".equalsIgnoreCase(status)) cls = "status-cancelled";
            %>
            <tr>
                <td>#<%= b.getBookingId() %></td>
                <td><%= b.getSlotId() %></td>
                <td><%= b.getVehicleNumber() != null ? b.getVehicleNumber() : "" %></td>
                <td><%= b.getBookingDate() != null ? dateFormat.format(b.getBookingDate()) : "" %></td>
                <td><%= b.getEntryTime()   != null ? timeFormat.format(b.getEntryTime())   : "" %></td>
                <td><%= b.getExitTime()    != null ? timeFormat.format(b.getExitTime())    : "--" %></td>
                <td><%= b.getTotalHours()  > 0 ? b.getTotalHours() + " hrs" : "--" %></td>
                <td><%= b.getTotalAmount() > 0 ? "₹" + String.format("%.2f", b.getTotalAmount()) : "--" %></td>
                <td class="<%= cls %>"><%= status.toUpperCase() %></td>
                <td>
                    <% if ("active".equalsIgnoreCase(status)) { %>
                        <!-- FIX: removed dead href="#" View button -->
                        <a class="btn btn-cancel"
                           href="BookingController?action=cancel&bookingId=<%= b.getBookingId() %>"
                           onclick="return confirm('Cancel this booking?')">Cancel</a>
                    <% } %>
                </td>
            </tr>
            <% } %>
        </table>
    <% } %>
</div>
</body>
</html>
