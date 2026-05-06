package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.PaymentModel;
import utility.DBConnection;

public class PaymentDAO implements PaymentInterface {

    @Override
    public String addPayment(PaymentModel payment) {
        String status = "fail";
        DBConnection db = new DBConnection();

        // FIX: was missing closing brace for the method, causing compile error
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO payments (booking_id, amount, payment_method, payment_status) VALUES (?,?,?,?)")) {

            ps.setInt(1, payment.getBookingId());
            ps.setDouble(2, payment.getAmount());
            ps.setString(3, payment.getPaymentMethod());
            ps.setString(4, payment.getStatus());

            int n = ps.executeUpdate();
            if (n > 0) {
                status = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}