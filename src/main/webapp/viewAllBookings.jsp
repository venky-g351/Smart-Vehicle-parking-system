<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, dao.BookingDAO, model.BookingModel, java.text.SimpleDateFormat" %>

<%
    if (session.getAttribute("adminUsername") == null) {
        response.sendRedirect("adminLogin.html");
        return;
    }

    BookingDAO bookingDAO = new BookingDAO();
    List<BookingModel> bookings = bookingDAO.getAllBookings();
    if (bookings == null) bookings = new ArrayList<>();

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

    int totalBookings = bookings.size();
    int activeBookings = 0, completedBookings = 0, cancelledBookings = 0;
    for (BookingModel b : bookings) {
        String s = b.getBookingStatus();
        if ("active".equalsIgnoreCase(s))    activeBookings++;
        else if ("completed".equalsIgnoreCase(s)) completedBookings++;
        else if ("cancelled".equalsIgnoreCase(s)) cancelledBookings++;
    }

    String msg   = request.getParameter("msg");
    String error = request.getParameter("error");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View All Bookings</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: Arial, sans-serif; }
        body { background: #f5f5f5; padding: 20px; }
        .container { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        h2 { margin-bottom: 15px; color: #333; }
        .nav { display: flex; gap: 10px; flex-wrap: wrap; margin-bottom: 15px; }
        .nav a { padding: 8px 15px; background: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .nav a:hover { background: #0056b3; }
        .nav a.logout { background: #dc3545; }
        .summary { background: #f8f9fa; padding: 10px 15px; border-radius: 5px; margin-bottom: 15px; font-size: 14px; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th { background: #007bff; color: white; padding: 10px; text-align: left; }
        td { padding: 10px; border-bottom: 1px solid #ddd; font-size: 14px; }
        tr:nth-child(even) { background: #f9f9f9; }
        .status-active    { color: green; font-weight: bold; }
        .status-completed { color: blue;  font-weight: bold; }
        .status-cancelled { color: red;   font-weight: bold; }
        .btn { padding: 5px 10px; color: white; text-decoration: none; border-radius: 3px; display: inline-block; font-size: 13px; }
        .btn-cancel { background: red; }
        .msg-success { background: #d4edda; color: #155724; padding: 10px; border-radius: 5px; margin-bottom: 15px; }
        .msg-error   { background: #f8d7da; color: #721c24; padding: 10px; border-radius: 5px; margin-bottom: 15px; }
    </style>
</head>
<body>
<div class="container">
    <h2>All Bookings</h2>

    <div class="nav">
        <a href="AdminDashboard.jsp">Dashboard</a>
        <a href="manageSlots.jsp">Slots</a>
        <a href="viewUsers.jsp">Users</a>
        <a href="addSlot.jsp">Add Slot</a>
        <a href="AdminLogoutController" class="logout">Logout</a>
    </div>

    <% if (msg != null)   { %><div class="msg-success"><%= msg %></div><%   } %>
    <% if (error != null) { %><div class="msg-error"><%= error %></div><% } %>

    <div class="summary">
        Total: <strong><%= totalBookings %></strong> &nbsp;|&nbsp;
        Active: <strong style="color:green"><%= activeBookings %></strong> &nbsp;|&nbsp;
        Completed: <strong style="color:blue"><%= completedBookings %></strong> &nbsp;|&nbsp;
        Cancelled: <strong style="color:red"><%= cancelledBookings %></strong>
    </div>

    <% if (bookings.isEmpty()) { %>
        <p style="text-align:center; padding:30px; color:#666;">No bookings found</p>
    <% } else { %>
        <table>
            <tr>
                <th>ID</th>
                <!-- FIX: was showing userId number, now shows user name from JOIN -->
                <th>User</th>
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
                <td><%= b.getUserName() != null ? b.getUserName() : String.valueOf(b.getUserId()) %></td>
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
                        <a class="btn btn-cancel"
                           href="AdminDeleteController?type=booking&id=<%= b.getBookingId() %>"
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
