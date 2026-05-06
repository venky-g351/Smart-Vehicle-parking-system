<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.ParkingSlotDAO, model.ParkingSlotModel" %>
<%
    if (session.getAttribute("adminUsername") == null) {
        response.sendRedirect("adminLogin.html");
        return;
    }
    ParkingSlotModel slot = (ParkingSlotModel) request.getAttribute("slot");
    if (slot == null) {
        String slotId = request.getParameter("id");
        if (slotId != null && !slotId.isEmpty()) {
            ParkingSlotDAO pdao = new ParkingSlotDAO();
            slot = pdao.getSlotById(Integer.parseInt(slotId));
        }
    }
    if (slot == null || slot.getSlotId() == 0) {
        response.sendRedirect("manageSlots.jsp?error=Slot not found");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Parking Slot</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: Arial, sans-serif; }
        body { background-color: #f5f5f5; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { color: #333; margin-bottom: 25px; padding-bottom: 10px; border-bottom: 2px solid #007bff; }
        .nav-links { display: flex; gap: 10px; margin-bottom: 20px; }
        .nav-links a { padding: 8px 16px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 5px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; font-weight: bold; color: #333; }
        input[type="number"], select { width: 100%; padding: 12px; border: 2px solid #ddd; border-radius: 5px; font-size: 16px; }
        input[readonly] { background-color: #e9ecef; cursor: not-allowed; }
        .submit-btn { width: 100%; padding: 14px; background-color: #ffc107; color: #333; border: none; border-radius: 5px; font-size: 18px; cursor: pointer; margin-top: 10px; }
        .back-link { display: inline-block; margin-top: 20px; padding: 10px 20px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 5px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Edit Parking Slot</h2>
        <div class="nav-links">
            <a href="adminDashboard.jsp">Dashboard</a>
            <a href="manageSlots.jsp">Manage Slots</a>
        </div>
        <form action="AdminEditSlotController" method="post">
            <div class="form-group">
                <label>Slot ID:</label>
                <input type="number" name="slotId" value="<%= slot.getSlotId() %>" readonly>
            </div>
            <div class="form-group">
                <label>Slot Number:</label>
                <input type="number" name="slotNumber" value="<%= slot.getSlotNumber() %>" required>
            </div>
            <div class="form-group">
                <label>Slot Type:</label>
                <select name="slotType" required>
                    <option value="Standard" <%= "Standard".equals(slot.getSlotType()) ? "selected" : "" %>>Standard</option>
                    <option value="Premium"  <%= "Premium".equals(slot.getSlotType())  ? "selected" : "" %>>Premium</option>
                    <option value="Handicap" <%= "Handicap".equals(slot.getSlotType()) ? "selected" : "" %>>Handicap</option>
                    <option value="VIP"      <%= "VIP".equals(slot.getSlotType())      ? "selected" : "" %>>VIP</option>
                </select>
            </div>
            <div class="form-group">
                <label>Floor Number:</label>
                <input type="number" name="floorNumber" value="<%= slot.getFloorNumber() %>" required>
            </div>
            <div class="form-group">
                <label>Vehicle Type:</label>
                <select name="vehicleType" required>
                    <option value="Car"   <%= "Car".equals(slot.getVehicleType())   ? "selected" : "" %>>Car</option>
                    <option value="Bike"  <%= "Bike".equals(slot.getVehicleType())  ? "selected" : "" %>>Bike</option>
                    <option value="SUV"   <%= "SUV".equals(slot.getVehicleType())   ? "selected" : "" %>>SUV</option>
                    <option value="Truck" <%= "Truck".equals(slot.getVehicleType()) ? "selected" : "" %>>Truck</option>
                    <option value="Bus"   <%= "Bus".equals(slot.getVehicleType())   ? "selected" : "" %>>Bus</option>
                </select>
            </div>
            <div class="form-group">
                <label>Status:</label>
                <select name="available" required>
                    <option value="true"  <%= slot.isAvailable()  ? "selected" : "" %>>Available</option>
                    <option value="false" <%= !slot.isAvailable() ? "selected" : "" %>>Booked</option>
                </select>
            </div>
            <div class="form-group">
                <label>Hourly Rate (₹):</label>
                <input type="number" name="hourRate" step="0.01" min="0" value="<%= slot.getHourRate() %>" required>
            </div>
            <button type="submit" class="submit-btn">Update Slot</button>
        </form>
        <a href="manageSlots.jsp" class="back-link">Back to Manage Slots</a>
    </div>
</body>
</html>