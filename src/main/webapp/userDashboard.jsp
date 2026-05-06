<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="dao.ParkingSlotDAO, dao.UserDAO, model.ParkingSlotModel, model.UserModel, java.util.*" %>

<%
    // Session check
    String userEmail = (String) session.getAttribute("userEmail");

    if (userEmail == null) {
        response.sendRedirect(request.getContextPath() + "/userLogin.jsp");
        return;
    }

    // Get user
    UserDAO userDAO = new UserDAO();
    UserModel currentUser = userDAO.getUserByEmail(userEmail);

    if (currentUser == null) {
        response.sendRedirect(request.getContextPath() + "/userLogin.jsp");
        return;
    }

    // Get slots
    ParkingSlotDAO slotDAO = new ParkingSlotDAO();
    List<ParkingSlotModel> availableSlots = slotDAO.getAvailableSlots();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Dashboard</title>

<style>
    * { margin:0; padding:0; box-sizing:border-box; font-family:Arial; }
    body { background:#f5f5f5; padding:20px; }

    .header {
        background:#fff;
        padding:20px;
        border-radius:8px;
        margin-bottom:20px;
        box-shadow:0 2px 5px rgba(0,0,0,0.1);
    }

    .user-info {
        margin:10px 0;
        padding:10px;
        background:#f8f9fa;
        border-left:4px solid #007bff;
    }

    .nav-links a {
        margin-right:10px;
        padding:8px 15px;
        background:#007bff;
        color:#fff;
        text-decoration:none;
        border-radius:5px;
    }

    .logout { background:#dc3545; }

    .slots {
        background:#fff;
        padding:20px;
        border-radius:8px;
        box-shadow:0 2px 5px rgba(0,0,0,0.1);
    }

    table {
        width:100%;
        border-collapse:collapse;
        margin-top:10px;
    }

    th, td {
        padding:10px;
        border:1px solid #ddd;
        text-align:center;
    }

    th { background:#f2f2f2; }

    .book-btn {
        background:#28a745;
        color:#fff;
        padding:5px 10px;
        text-decoration:none;
        border-radius:4px;
    }

    .no-data {
        text-align:center;
        padding:20px;
        color:#666;
    }
</style>

</head>

<body>

<div class="header">
    <h2>User Dashboard</h2>

    <div class="user-info">
        <p>
            Welcome:
            <strong>
                <%= currentUser.getUserName() != null ? currentUser.getUserName() : userEmail %>
            </strong>
        </p>

        <p>
            Vehicle:
            <strong>
                <%= currentUser.getVehicleNumber() != null ? currentUser.getVehicleNumber() : "Not Set" %>
                (
                <%= currentUser.getVehicleType() != null ? currentUser.getVehicleType() : "Not Set" %>
                )
            </strong>
        </p>
    </div>

    <div class="nav-links">
        <a href="<%= request.getContextPath() %>/myBookings.jsp">My Bookings</a>
        <a href="<%= request.getContextPath() %>/UserLogoutController" class="logout">Logout</a>
    </div>
</div>

<div class="slots">
    <h3>Available Slots</h3>

    <% if (availableSlots == null || availableSlots.isEmpty()) { %>

        <div class="no-data">
            No parking slots available
        </div>

    <% } else { %>

        <table>
            <tr>
                <th>Slot No</th>
                <th>Type</th>
                <th>Floor</th>
                <th>Vehicle</th>
                <th>Rate</th>
                <th>Action</th>
            </tr>

            <% for (ParkingSlotModel slot : availableSlots) { %>
            <tr>
                <td><%= slot.getSlotNumber() %></td>
                <td><%= slot.getSlotType() %></td>
                <td><%= slot.getFloorNumber() %></td>
                <td><%= slot.getVehicleType() %></td>
                <td>₹<%= String.format("%.2f", slot.getHourRate()) %></td>
                <td>
                    <a href="<%= request.getContextPath() %>/bookSlot.jsp?slotId=<%= slot.getSlotId() %>" class="book-btn">
                        Book
                    </a>
                </td>
            </tr>
            <% } %>

        </table>

    <% } %>

</div>

</body>
</html>