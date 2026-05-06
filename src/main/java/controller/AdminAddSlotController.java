package controller;

import java.io.IOException;

import dao.ParkingSlotDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ParkingSlotModel;

@WebServlet("/AdminAddSlotController")
public class AdminAddSlotController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminUsername") == null) {
            response.sendRedirect("adminLogin.html");
            return;
        }
        response.sendRedirect("addSlot.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminUsername") == null) {
            response.sendRedirect("adminLogin.html");
            return;
        }

        try {
            int slotId         = Integer.parseInt(request.getParameter("slotId"));
            int slotNumber     = Integer.parseInt(request.getParameter("slotNumber"));
            String slotType    = request.getParameter("slotType");
            int floorNumber    = Integer.parseInt(request.getParameter("floorNumber"));
            String vehicleType = request.getParameter("vehicleType");
            double hourRate    = Double.parseDouble(request.getParameter("hourRate"));

            ParkingSlotModel slot = new ParkingSlotModel();
            slot.setSlotId(slotId);
            slot.setSlotNumber(slotNumber);
            slot.setSlotType(slotType);
            slot.setFloorNumber(floorNumber);
            slot.setVehicleType(vehicleType);
            slot.setAvailable(true);
            slot.setHourRate(hourRate);

            ParkingSlotDAO dao = new ParkingSlotDAO();
            int result = dao.addParkingSlot(slot);

            if (result > 0) {
                response.sendRedirect("manageSlots.jsp?msg=Slot added successfully");
            } else {
                response.sendRedirect("addSlot.jsp?error=Failed to add slot");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("addSlot.jsp?error=Invalid number format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addSlot.jsp?error=Database error");
        }
    }
}