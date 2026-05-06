<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String error = request.getParameter("error");
    String success = request.getParameter("success");
%>

<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background: #f4f6f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .wrapper {
            display: flex;
            gap: 30px;
        }

        .container {
            width: 380px;
            background: #fff;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }

        input, select {
            width: 100%;
            padding: 10px;
            margin: 8px 0 15px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        input:focus, select:focus {
            border-color: #007bff;
            outline: none;
        }

        button {
            width: 100%;
            padding: 12px;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }

        .user-btn {
            background: #007bff;
        }

        .user-btn:hover {
            background: #0056b3;
        }

        .admin-btn {
            background: #28a745;
        }

        .admin-btn:hover {
            background: #218838;
        }

        .message {
            text-align: center;
            margin-bottom: 15px;
            font-weight: bold;
        }

        .error { color: red; }
        .success { color: green; }

        .links {
            text-align: center;
            margin-top: 15px;
        }

        .links a {
            text-decoration: none;
            color: #007bff;
            display: block;
            margin-top: 5px;
        }

        .links a:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>

<div class="wrapper">

    <!-- USER REGISTRATION -->
    <div class="container">
        <h2>User Registration</h2>

        <% if (error != null) { %>
            <div class="message error"><%= error %></div>
        <% } %>

        <% if (success != null) { %>
            <div class="message success"><%= success %></div>
        <% } %>

        <form action="UserRegisterController" method="post">
            <input type="text" name="userName" placeholder="Full Name" required>

            <input type="email" name="email" placeholder="Email" required>

            <input type="text" name="vehicleNumber" placeholder="Vehicle Number" required>

            <select name="vehicleType" required>
                <option value="">Select Vehicle Type</option>
                <option>Car</option>
                <option>Bike</option>
                <option>SUV</option>
            </select>

            <input type="password" name="password" placeholder="Password" required>

            <input type="password" name="confirmPassword" placeholder="Confirm Password" required>

            <button type="submit" class="user-btn">Register</button>
        </form>

        <div class="links">
            <a href="userLogin.jsp">User Login</a>
        </div>
    </div>

    <!-- ADMIN LOGIN -->
    <div class="container">
        <h2>Admin Login</h2>

        <form action="AdminLoginController" method="post">
            <input type="text" name="username" placeholder="Admin Username" required>

            <input type="password" name="password" placeholder="Password" required>

            <button type="submit" class="admin-btn">Login</button>
        </form>

        <div class="links">
            <a href="adminLogin.html">Go to Admin Page</a>
        </div>
    </div>

</div>

</body>
</html>