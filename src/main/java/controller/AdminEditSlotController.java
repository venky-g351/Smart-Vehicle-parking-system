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

@WebServlet("/AdminEditSlotController")
public class AdminEditSlotController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminUsername") == null) {
            response.sendRedirect("adminLogin.html");
            return;
        }

        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            try {
                ParkingSlotDAO pdao = new ParkingSlotDAO();
                ParkingSlotModel slot = pdao.getSlotById(Integer.parseInt(id));
                if (slot != null && slot.getSlotId() > 0) {
                    request.setAttribute("slot", slot);
                    request.getRequestDispatcher("editSlot.jsp").forward(request, response);
                } else {
                    response.sendRedirect("manageSlots.jsp?error=Slot not found");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("manageSlots.jsp?error=Invalid slot ID");
            }
        } else {
            response.sendRedirect("manageSlots.jsp?error=Slot ID not provided");
        }
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
            boolean available  = Boolean.parseBoolean(request.getParameter("available"));
            double hourRate    = Double.parseDouble(request.getParameter("hourRate"));

            ParkingSlotModel slot = new ParkingSlotModel();
            slot.setSlotId(slotId);
            slot.setSlotNumber(slotNumber);
            slot.setSlotType(slotType);
            slot.setFloorNumber(floorNumber);
            slot.setVehicleType(vehicleType);
            slot.setAvailable(available);
            slot.setHourRate(hourRate);

            ParkingSlotDAO pdao = new ParkingSlotDAO();
            int status = pdao.updateParkingSlot(slot);

            if (status > 0) {
                response.sendRedirect("manageSlots.jsp?msg=Slot updated successfully");
            } else {
                response.sendRedirect("editSlot.jsp?id=" + slotId + "&error=Update failed");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("editSlot.jsp?id=" + request.getParameter("slotId") + "&error=Invalid number format");
        } catch (Exception e) {
            response.sendRedirect("editSlot.jsp?id=" + request.getParameter("slotId") + "&error=Update failed");
        }
    }
}