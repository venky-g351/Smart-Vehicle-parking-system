<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Login</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: Arial, sans-serif; }
        body { background-color: #f5f5f5; display: flex; justify-content: center; align-items: center; min-height: 100vh; }
        .container { background-color: white; padding: 40px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); width: 100%; max-width: 400px; }
        h2 { color: #333; margin-bottom: 25px; padding-bottom: 10px; border-bottom: 2px solid #007bff; text-align: center; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; font-weight: bold; color: #333; }
        input[type="text"], input[type="password"] { width: 100%; padding: 12px; border: 2px solid #ddd; border-radius: 5px; font-size: 16px; }
        input:focus { border-color: #007bff; outline: none; }
        .submit-btn { width: 100%; padding: 14px; background-color: #007bff; color: white; border: none; border-radius: 5px; font-size: 18px; cursor: pointer; }
        .submit-btn:hover { background-color: #0056b3; }
        .error-msg { background-color: #f8d7da; color: #721c24; padding: 10px; border-radius: 5px; margin-bottom: 15px; text-align: center; }
        .success-msg { background-color: #d4edda; color: #155724; padding: 10px; border-radius: 5px; margin-bottom: 15px; text-align: center; }
        .links { text-align: center; margin-top: 20px; }
        .links a { color: #007bff; text-decoration: none; margin: 5px; display: block; }
        .links a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <h2>User Login</h2>

        <% String error = request.getParameter("error");
           if (error != null) { %>
            <div class="error-msg"><%= error %></div>
        <% } %>

        <% String success = request.getParameter("success");
           if (success != null) { %>
            <div class="success-msg"><%= success %></div>
        <% } %>

        <form action="UserLoginController" method="post">
            <div class="form-group">
                <label>Email:</label>
                <!-- FIX: was name="username", controller reads "email" -->
                <input type="text" name="email" placeholder="Enter your email" required>
            </div>
            <div class="form-group">
                <label>Password:</label>
                <input type="password" name="password" placeholder="Enter your password" required>
            </div>
            <button type="submit" class="submit-btn">Login</button>
        </form>

        <div class="links">
            <a href="index.jsp">Don't have an account? Register here</a>
            <a href="adminLogin.html">Admin Login</a>
        </div>
    </div>
</body>
</html>
