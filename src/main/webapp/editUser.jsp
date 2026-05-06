<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.UserDAO, model.UserModel" %>
<%
    if (session.getAttribute("adminUsername") == null) {
        response.sendRedirect("adminLogin.html");
        return;
    }
    String userId = request.getParameter("id");
    UserModel user = null;
    if (userId != null && !userId.isEmpty()) {
        UserDAO udao = new UserDAO();
        user = udao.getUserById(Integer.parseInt(userId));
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit User</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: Arial, sans-serif; }
        body { background-color: #f5f5f5; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { color: #333; margin-bottom: 25px; padding-bottom: 10px; border-bottom: 2px solid #007bff; }
        .nav-links { display: flex; gap: 10px; margin-bottom: 20px; }
        .nav-links a { padding: 8px 16px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 5px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; font-weight: bold; color: #333; }
        input[type="text"], input[type="email"], select { width: 100%; padding: 12px; border: 2px solid #ddd; border-radius: 5px; font-size: 16px; }
        input[readonly] { background-color: #e9ecef; cursor: not-allowed; }
        .submit-btn { width: 100%; padding: 14px; background-color: #28a745; color: white; border: none; border-radius: 5px; font-size: 18px; cursor: pointer; margin-top: 10px; }
        .back-link { display: inline-block; margin-top: 20px; padding: 10px 20px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 5px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Edit User</h2>
        <div class="nav-links">
            <a href="adminDashboard.jsp">Dashboard</a>
            <a href="viewUsers.jsp">View Users</a>
        </div>
        <% if (user != null && user.getUserid() > 0) { %>
        <form action="AdminEditController" method="post">
            <input type="hidden" name="type" value="user">
            <div class="form-group">
                <label>User ID:</label>
                <input type="text" name="userId" value="<%= user.getUserid() %>" readonly>
            </div>
            <div class="form-group">
                <label>Name:</label>
                <input type="text" name="userName" value="<%= user.getUserName() %>" required>
            </div>
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" value="<%= user.getEmail() %>" readonly>
            </div>
            <div class="form-group">
                <label>Vehicle Number:</label>
                <input type="text" name="vehicleNumber" value="<%= user.getVehicleNumber() %>" required>
            </div>
            <div class="form-group">
                <label>Vehicle Type:</label>
                <select name="vehicleType" required>
                    <option value="Car"   <%= "Car".equals(user.getVehicleType())   ? "selected" : "" %>>Car</option>
                    <option value="Bike"  <%= "Bike".equals(user.getVehicleType())  ? "selected" : "" %>>Bike</option>
                    <option value="SUV"   <%= "SUV".equals(user.getVehicleType())   ? "selected" : "" %>>SUV</option>
                    <option value="Truck" <%= "Truck".equals(user.getVehicleType()) ? "selected" : "" %>>Truck</option>
                </select>
            </div>
            <button type="submit" class="submit-btn">Update User</button>
        </form>
        <% } else { %>
            <p style="color:red; text-align:center; padding:20px;">User not found!</p>
        <% } %>
        <a href="viewUsers.jsp" class="back-link">Back to Users</a>
    </div>
</body>
</html>