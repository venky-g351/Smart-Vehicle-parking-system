<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, dao.UserDAO, model.UserModel" %>

<%
    if (session.getAttribute("adminUsername") == null) {
        response.sendRedirect("adminLogin.html");
        return;
    }
    UserDAO udao = new UserDAO();
    List<UserModel> users = udao.getAllUsers();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Users</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: Arial, sans-serif; }
        body { background: #f4f6f9; padding: 20px; }
        .container { max-width: 1100px; margin: auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 3px 10px rgba(0,0,0,0.1); }
        h2 { margin-bottom: 20px; color: #333; }
        .nav { margin-bottom: 20px; display: flex; gap: 10px; flex-wrap: wrap; }
        .nav a { text-decoration: none; padding: 8px 15px; background: #007bff; color: white; border-radius: 5px; }
        .nav a:hover { background: #0056b3; }
        .nav a.logout { background: #dc3545; }
        .nav a.logout:hover { background: #c82333; }
        table { width: 100%; border-collapse: collapse; }
        th { background: #007bff; color: white; padding: 12px; text-align: left; }
        td { padding: 12px; border-bottom: 1px solid #ddd; }
        tr:nth-child(even) { background: #f9f9f9; }
        tr:hover { background: #f1f1f1; }
        .no-data { text-align: center; padding: 30px; color: #666; }
        .btn { padding: 5px 10px; text-decoration: none; border-radius: 3px; display: inline-block; font-size: 13px; }
        .btn-edit   { background: #ffc107; color: #333; }
        .btn-delete { background: #dc3545; color: white; margin-left: 4px; }
        .msg-success { background: #d4edda; color: #155724; padding: 10px; border-radius: 5px; margin-bottom: 15px; }
        .msg-error   { background: #f8d7da; color: #721c24; padding: 10px; border-radius: 5px; margin-bottom: 15px; }
    </style>
</head>
<body>
<div class="container">
    <h2>All Users</h2>

    <div class="nav">
        <a href="AdminDashboard.jsp">Dashboard</a>
        <a href="manageSlots.jsp">Manage Slots</a>
        <!-- FIX: was viewBookings.jsp (404) → correct file is viewAllBookings.jsp -->
        <a href="viewAllBookings.jsp">View Bookings</a>
        <a href="AdminLogoutController" class="logout">Logout</a>
    </div>

    <% if (request.getParameter("msg") != null) { %>
        <div class="msg-success"><%= request.getParameter("msg") %></div>
    <% } %>
    <% if (request.getParameter("error") != null) { %>
        <div class="msg-error"><%= request.getParameter("error") %></div>
    <% } %>

    <% if (users == null || users.isEmpty()) { %>
        <div class="no-data">No users found</div>
    <% } else { %>
        <table>
            <tr>
                <th>User ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Vehicle Number</th>
                <th>Vehicle Type</th>
                <!-- FIX: added Actions column so admin can Edit / Delete -->
                <th>Actions</th>
            </tr>
            <% for (UserModel user : users) { %>
            <tr>
                <td><%= user.getUserid() %></td>
                <td><%= user.getUserName()      != null ? user.getUserName()      : "" %></td>
                <td><%= user.getEmail()         != null ? user.getEmail()         : "" %></td>
                <td><%= user.getVehicleNumber() != null ? user.getVehicleNumber() : "" %></td>
                <td><%= user.getVehicleType()   != null ? user.getVehicleType()   : "" %></td>
                <td>
                    <a class="btn btn-edit"
                       href="AdminEditController?type=user&id=<%= user.getUserid() %>">Edit</a>
                    <a class="btn btn-delete"
                       href="AdminDeleteController?type=user&id=<%= user.getUserid() %>"
                       onclick="return confirm('Delete user <%= user.getUserName() %>?')">Delete</a>
                </td>
            </tr>
            <% } %>
        </table>
    <% } %>
</div>
</body>
</html>
