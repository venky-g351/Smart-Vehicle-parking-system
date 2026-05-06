<%@ page import="dao.UserDAO, model.UserModel" %>

<%
    String userEmail = (String) session.getAttribute("userEmail");

    if (userEmail == null) {
        response.sendRedirect(request.getContextPath() + "/userLogin.jsp");
        return;
    }

    UserDAO userDAO = new UserDAO();
    UserModel currentUser = userDAO.getUserByEmail(userEmail);

    if (currentUser == null) {
        response.sendRedirect(request.getContextPath() + "/userLogin.jsp");
        return;
    }

    String userName = currentUser.getUserName() != null ? currentUser.getUserName() : "";
    String vehicleNumber = currentUser.getVehicleNumber() != null ? currentUser.getVehicleNumber() : "";
    String vehicleType = currentUser.getVehicleType() != null ? currentUser.getVehicleType() : "";
    int userId = currentUser.getUserid();

    String slotId = request.getParameter("slotId");

    if (slotId == null || slotId.isEmpty()) {
        response.sendRedirect(request.getContextPath() + "/userDashboard.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book Slot</title>

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

    .container {
        width: 400px;
        background: #ffffff;
        padding: 25px;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.15);
    }

    h2 {
        text-align: center;
        margin-bottom: 20px;
        color: #333;
    }

    .user-info {
        background: #eef5ff;
        padding: 12px;
        border-left: 4px solid #007bff;
        border-radius: 5px;
        margin-bottom: 20px;
        font-size: 14px;
    }

    .user-info p {
        margin: 4px 0;
    }

    .form-group {
        margin-bottom: 15px;
    }

    label {
        font-weight: bold;
        display: block;
        margin-bottom: 5px;
        color: #333;
    }

    input {
        width: 100%;
        padding: 10px;
        border-radius: 5px;
        border: 1px solid #ccc;
        font-size: 14px;
    }

    input:focus {
        border-color: #007bff;
        outline: none;
    }

    .btn {
        width: 100%;
        padding: 12px;
        background: #28a745;
        border: none;
        color: white;
        font-size: 16px;
        border-radius: 5px;
        cursor: pointer;
    }

    .btn:hover {
        background: #218838;
    }

    .back {
        display: block;
        text-align: center;
        margin-top: 15px;
        text-decoration: none;
        color: #007bff;
        font-size: 14px;
    }

    .back:hover {
        text-decoration: underline;
    }
</style>

</head>

<body>

<div class="container">

    <h2>Book Parking Slot</h2>

    <div class="user-info">
        <p><strong>User:</strong> <%= userName %></p>
        <p><strong>Email:</strong> <%= userEmail %></p>
        <p><strong>Vehicle:</strong> <%= vehicleNumber %> (<%= vehicleType %>)</p>
    </div>

    <form action="<%= request.getContextPath() %>/BookingController" method="post">

        <input type="hidden" name="userId" value="<%= userId %>">
        <input type="hidden" name="slotId" value="<%= slotId %>">

        <div class="form-group">
            <label>Vehicle Number</label>
            <input type="text" name="vehicleNumber" value="<%= vehicleNumber %>" required>
        </div>

        <div class="form-group">
            <label>Booking Date</label>
            <input type="date" name="bookingDate" required>
        </div>

        <div class="form-group">
            <label>Entry Time</label>
            <input type="time" name="entryTime" required>
        </div>

        <button type="submit" class="btn">Confirm Booking</button>

    </form>

    <a href="<%= request.getContextPath() %>/userDashboard.jsp" class="back">← Back to Dashboard</a>

</div>

</body>
</html>