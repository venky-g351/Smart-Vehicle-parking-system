<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
    if (session.getAttribute("adminUsername") == null) {
        response.sendRedirect("AdminLogin.html");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>

    <style>
        body {
            font-family: Arial;
            background: #f4f6f9;
            margin: 0;
            padding: 20px;
        }

        .container {
            max-width: 900px;
            margin: auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }

        h2 {
            border-bottom: 2px solid #007bff;
            padding-bottom: 10px;
        }

        .welcome {
            background: #d4edda;
            padding: 10px;
            margin: 15px 0;
            border-radius: 5px;
        }

        .grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 15px;
            margin-top: 20px;
        }

        .card {
            background: #007bff;
            color: white;
            padding: 20px;
            text-align: center;
            border-radius: 8px;
            text-decoration: none;
        }

        .card:hover {
            background: #0056b3;
        }

        .logout {
            display: inline-block;
            margin-top: 20px;
            background: red;
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 5px;
        }
    </style>
</head>

<body>

<div class="container">
    <h2>Admin Dashboard</h2>

    <div class="welcome">
        Welcome, <b><%= session.getAttribute("adminUsername") %></b>
    </div>

    <div class="grid">
        <a href="addSlot.jsp" class="card">Add Slot</a>
        <a href="manageSlots.jsp" class="card">Manage Slots</a>
        <a href="viewUsers.jsp" class="card">View Users</a>
        <a href="viewAllBookings.jsp" class="card">All Bookings</a>
    </div>

    <a href="AdminLogoutController" class="logout">Logout</a>
</div>

</body>
</html>