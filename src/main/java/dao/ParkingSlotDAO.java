package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.ParkingSlotModel;
import utility.DBConnection;

public class ParkingSlotDAO implements ParkingSlotInterface {

    @Override
    public int addParkingSlot(ParkingSlotModel pm) {
        int status = 0;
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO parking_slots(slot_id, slot_number, slot_type, floor_number, vehicle_type, available, hour_rate) " +
                     "VALUES(?,?,?,?,?,?,?)")) {

            ps.setInt(1, pm.getSlotId());
            ps.setInt(2, pm.getSlotNumber());
            ps.setString(3, pm.getSlotType());
            ps.setInt(4, pm.getFloorNumber());
            ps.setString(5, pm.getVehicleType());
            ps.setBoolean(6, pm.isAvailable());
            ps.setDouble(7, pm.getHourRate());
            status = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public List<ParkingSlotModel> getAllParkingSlots() {
        List<ParkingSlotModel> list = new ArrayList<>();
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM parking_slots ORDER BY floor_number, slot_number");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ParkingSlotModel> getAvailableSlots() {
        List<ParkingSlotModel> list = new ArrayList<>();
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM parking_slots WHERE available = TRUE ORDER BY floor_number, slot_number");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ParkingSlotModel getSlotById(int slotId) {
        ParkingSlotModel slot = new ParkingSlotModel();
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM parking_slots WHERE slot_id=?")) {

            ps.setInt(1, slotId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    slot = mapRow(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slot;
    }

    @Override
    public int updateParkingSlot(ParkingSlotModel slot) {
        int status = 0;
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE parking_slots SET slot_number=?, slot_type=?, floor_number=?, " +
                     "vehicle_type=?, available=?, hour_rate=? WHERE slot_id=?")) {

            ps.setInt(1, slot.getSlotNumber());
            ps.setString(2, slot.getSlotType());
            ps.setInt(3, slot.getFloorNumber());
            ps.setString(4, slot.getVehicleType());
            ps.setBoolean(5, slot.isAvailable());
            ps.setDouble(6, slot.getHourRate());
            ps.setInt(7, slot.getSlotId());
            status = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public int deleteParkingSlot(int slotId) {
        int status = 0;
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection()) {
            // Guard: do not delete slot with active bookings
            try (PreparedStatement psCheck = conn.prepareStatement(
                    "SELECT COUNT(*) FROM bookings WHERE slot_id = ? AND booking_status = 'active'")) {
                psCheck.setInt(1, slotId);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return -1;
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM parking_slots WHERE slot_id=?")) {
                ps.setInt(1, slotId);
                status = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    // Helper to avoid repeating ResultSet mapping
    private ParkingSlotModel mapRow(ResultSet rs) throws Exception {
        ParkingSlotModel slot = new ParkingSlotModel();
        slot.setSlotId(rs.getInt("slot_id"));
        slot.setSlotNumber(rs.getInt("slot_number"));
        slot.setSlotType(rs.getString("slot_type"));
        slot.setFloorNumber(rs.getInt("floor_number"));
        slot.setVehicleType(rs.getString("vehicle_type"));
        slot.setAvailable(rs.getBoolean("available"));
        slot.setHourRate(rs.getDouble("hour_rate"));
        return slot;
    }
}