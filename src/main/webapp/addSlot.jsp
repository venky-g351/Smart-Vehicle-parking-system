<%
    if (session.getAttribute("adminUsername") == null) {
        response.sendRedirect("adminLogin.html");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Add Slot</title>
</head>
<body>

<h2>Add Parking Slot</h2>

<form action="AdminAddSlotController" method="post">
    Slot ID: <input type="number" name="slotId" required><br><br>
    Slot Number: <input type="number" name="slotNumber" required><br><br>

    Slot Type:
    <select name="slotType">
        <option>Standard</option>
        <option>Premium</option>
    </select><br><br>

    Floor: <input type="number" name="floorNumber" required><br><br>

    Vehicle Type:
    <select name="vehicleType">
        <option>Car</option>
        <option>Bike</option>
    </select><br><br>

    Rate: <input type="number" name="hourRate" required><br><br>

    <button type="submit">Add</button>
</form>

</body>
</html>