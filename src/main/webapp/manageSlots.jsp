<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, dao.ParkingSlotDAO, model.ParkingSlotModel" %>
<%
    if (session.getAttribute("adminUsername") == null) {
        response.sendRedirect("adminLogin.html");
        return;
    }
    ParkingSlotDAO slotDAO = new ParkingSlotDAO();
    List<ParkingSlotModel> slots = slotDAO.getAllParkingSlots();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Parking Slots</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: Arial, sans-serif; }
        body { background-color: #f5f5f5; padding: 20px; }
        .header { background-color: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        .header h2 { color: #333; margin-bottom: 15px; }
        .nav-links { display: flex; gap: 15px; flex-wrap: wrap; }
        .nav-links a { padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .nav-links a:hover { background-color: #0056b3; }
        .nav-links a.add-btn { background-color: #28a745; }
        .nav-links a.logout { background-color: #dc3545; }
        .table-container { background-color: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); overflow-x: auto; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #f2f2f2; font-weight: bold; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        .status-available { color: #28a745; font-weight: bold; }
        .status-booked { color: #dc3545; font-weight: bold; }
        .action-links a { padding: 5px 10px; margin: 0 3px; text-decoration: none; border-radius: 3px; display: inline-block; }
        .edit-btn { background-color: #ffc107; color: #333; }
        .delete-btn { background-color: #dc3545; color: white; }
        .message { padding: 12px; margin-bottom: 20px; border-radius: 5px; }
        .success { background-color: #d4edda; color: #155724; }
        .error { background-color: #f8d7da; color: #721c24; }
        .no-data { text-align: center; padding: 40px; color: #666; }
    </style>
</head>
<body>
    <div class="header">
        <h2>Manage Parking Slots</h2>
        <div class="nav-links">
            <a href="adminDashboard.jsp">Dashboard</a>
            <a href="addSlot.jsp" class="add-btn">+ Add New Slot</a>
            <a href="viewUsers.jsp">View Users</a>
            <a href="viewAllBookings.jsp">View Bookings</a>
            <a href="AdminLogoutController" class="logout">Logout</a>
        </div>
    </div>
    <% if (request.getParameter("msg") != null) { %><div class="message success"><%= request.getParameter("msg") %></div><% } %>
    <% if (request.getParameter("error") != null) { %><div class="message error"><%= request.getParameter("error") %></div><% } %>
    <div class="table-container">
        <% if (slots == null || slots.isEmpty()) { %>
            <div class="no-data"><h3>No parking slots found!</h3><p>Click "Add New Slot" to add parking slots.</p></div>
        <% } else { %>
        <table>
            <thead>
                <tr><th>Slot ID</th><th>Slot No.</th><th>Type</th><th>Floor</th><th>Vehicle</th><th>Status</th><th>Rate (₹/hr)</th><th>Actions</th></tr>
            </thead>
            <tbody>
                <% for (ParkingSlotModel slot : slots) { %>
                <tr>
                    <td><%= slot.getSlotId() %></td>
                    <td><%= slot.getSlotNumber() %></td>
                    <td><%= slot.getSlotType() %></td>
                    <td><%= slot.getFloorNumber() %></td>
                    <td><%= slot.getVehicleType() %></td>
                    <td class="<%= slot.isAvailable() ? "status-available" : "status-booked" %>">
                        <%= slot.isAvailable() ? "Available" : "Booked" %>
                    </td>
                    <td>₹<%= String.format("%.2f", slot.getHourRate()) %></td>
                    <td class="action-links">
                        <a href="AdminEditSlotController?id=<%= slot.getSlotId() %>" class="edit-btn">Edit</a>
                        <a href="AdminDeleteController?type=slot&id=<%= slot.getSlotId() %>"
                           class="delete-btn"
                           onclick="return confirm('Delete this slot?')">Delete</a>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <% } %>
    </div>
</body>
</html>