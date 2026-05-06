package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import model.BookingModel;
import utility.DBConnection;

public class BookingDAO implements BookingInterface {

    @Override
    public String createBooking(BookingModel book) {
        String status = "fail";
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO bookings(user_id, slot_id, vehicle_number, booking_date, entry_time, booking_status) " +
                    "VALUES(?,?,?,?,?,'active')")) {

                ps.setInt(1, book.getUserId());
                ps.setInt(2, book.getSlotId());
                ps.setString(3, book.getVehicleNumber());
                ps.setDate(4, book.getBookingDate());
                ps.setTime(5, book.getEntryTime());

                int n = ps.executeUpdate();
                if (n > 0) {
                    // Mark slot as unavailable
                    try (PreparedStatement ps2 = conn.prepareStatement(
                            "UPDATE parking_slots SET available = FALSE WHERE slot_id = ?")) {
                        ps2.setInt(1, book.getSlotId());
                        ps2.executeUpdate();
                    }
                    status = "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public List<BookingModel> getAllBookings() {
        List<BookingModel> list = new ArrayList<>();
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT b.*, u.user_name, p.slot_number " +
                     "FROM bookings b " +
                     "LEFT JOIN users u ON b.user_id = u.user_id " +
                     "LEFT JOIN parking_slots p ON b.slot_id = p.slot_id " +
                     "ORDER BY b.created_at DESC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BookingModel booking = new BookingModel();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setSlotId(rs.getInt("slot_id"));
                booking.setVehicleNumber(rs.getString("vehicle_number"));
                booking.setBookingDate(rs.getDate("booking_date"));
                booking.setEntryTime(rs.getTime("entry_time"));
                booking.setExitTime(rs.getTime("exit_time"));
                booking.setTotalHours(rs.getInt("total_hours"));
                booking.setTotalAmount(rs.getDouble("total_amount"));
                booking.setBookingStatus(rs.getString("booking_status"));
                booking.setCreatedAt(rs.getTimestamp("created_at"));
                // FIX: expose userName fetched by the JOIN
                booking.setUserName(rs.getString("user_name"));
                list.add(booking);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public BookingModel getBookingById(int bookingId) {
        BookingModel booking = new BookingModel();
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM bookings WHERE booking_id=?")) {

            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    booking.setBookingId(rs.getInt("booking_id"));
                    booking.setUserId(rs.getInt("user_id"));
                    booking.setSlotId(rs.getInt("slot_id"));
                    booking.setVehicleNumber(rs.getString("vehicle_number"));
                    booking.setBookingDate(rs.getDate("booking_date"));
                    booking.setEntryTime(rs.getTime("entry_time"));
                    booking.setExitTime(rs.getTime("exit_time"));
                    booking.setTotalHours(rs.getInt("total_hours"));
                    booking.setTotalAmount(rs.getDouble("total_amount"));
                    booking.setBookingStatus(rs.getString("booking_status"));
                    booking.setCreatedAt(rs.getTimestamp("created_at"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return booking;
    }

    @Override
    public List<BookingModel> getBookingsByUserId(int userId) {
        List<BookingModel> list = new ArrayList<>();
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM bookings WHERE user_id = ? ORDER BY created_at DESC")) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BookingModel booking = new BookingModel();
                    booking.setBookingId(rs.getInt("booking_id"));
                    booking.setUserId(rs.getInt("user_id"));
                    booking.setSlotId(rs.getInt("slot_id"));
                    booking.setVehicleNumber(rs.getString("vehicle_number"));
                    booking.setBookingDate(rs.getDate("booking_date"));
                    booking.setEntryTime(rs.getTime("entry_time"));
                    booking.setExitTime(rs.getTime("exit_time"));
                    booking.setTotalHours(rs.getInt("total_hours"));
                    booking.setTotalAmount(rs.getDouble("total_amount"));
                    booking.setBookingStatus(rs.getString("booking_status"));
                    booking.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(booking);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int updateBookingStatus(BookingModel booking) {
        int status = 0;
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE bookings SET booking_status=? WHERE booking_id=?")) {

                ps.setString(1, booking.getBookingStatus());
                ps.setInt(2, booking.getBookingId());
                status = ps.executeUpdate();
            }

            if (status > 0 && "cancelled".equals(booking.getBookingStatus())) {
                try (PreparedStatement ps2 = conn.prepareStatement(
                        "UPDATE parking_slots SET available = TRUE WHERE slot_id IN " +
                        "(SELECT slot_id FROM bookings WHERE booking_id = ?)")) {
                    ps2.setInt(1, booking.getBookingId());
                    ps2.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    // FIX: Renamed semantically — this cancels, it does NOT delete the row
    @Override
    public int deleteBooking(int bookingId) {
        int status = 0;
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection()) {
            int slotId = 0;

            try (PreparedStatement psGet = conn.prepareStatement(
                    "SELECT slot_id FROM bookings WHERE booking_id=?")) {
                psGet.setInt(1, bookingId);
                try (ResultSet rs = psGet.executeQuery()) {
                    if (rs.next()) {
                        slotId = rs.getInt("slot_id");
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE bookings SET booking_status='cancelled' WHERE booking_id=?")) {
                ps.setInt(1, bookingId);
                status = ps.executeUpdate();
            }

            if (status > 0 && slotId > 0) {
                try (PreparedStatement ps2 = conn.prepareStatement(
                        "UPDATE parking_slots SET available = TRUE WHERE slot_id=?")) {
                    ps2.setInt(1, slotId);
                    ps2.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public int completeBooking(int bookingId, Time exitTime, int totalHours, double totalAmount) {
        int status = 0;
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE bookings SET exit_time=?, total_hours=?, total_amount=?, " +
                    "booking_status='completed' WHERE booking_id=?")) {

                ps.setTime(1, exitTime);
                ps.setInt(2, totalHours);
                ps.setDouble(3, totalAmount);
                ps.setInt(4, bookingId);
                status = ps.executeUpdate();
            }

            if (status > 0) {
                try (PreparedStatement ps2 = conn.prepareStatement(
                        "UPDATE parking_slots SET available = TRUE WHERE slot_id IN " +
                        "(SELECT slot_id FROM bookings WHERE booking_id = ?)")) {
                    ps2.setInt(1, bookingId);
                    ps2.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}