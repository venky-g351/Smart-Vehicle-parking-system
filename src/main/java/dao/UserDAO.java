package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.UserModel;
import utility.DBConnection;

public class UserDAO implements UserInterface {

    @Override
    public String Login(UserModel um) {
        String status = "fail";
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM users WHERE email=? AND password=?")) {

            ps.setString(1, um.getEmail());
            ps.setString(2, um.getPassword());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    status = "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public int registerUser(UserModel user) {
        int status = 0;
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO users (user_name, email, vehicle_number, vehicle_type, password) VALUES (?, ?, ?, ?, ?)")) {

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getVehicleNumber());
            ps.setString(4, user.getVehicleType());
            ps.setString(5, user.getPassword());

            status = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public List<UserModel> getAllUsers() {
        List<UserModel> list = new ArrayList<>();
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users ORDER BY user_id");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UserModel u = new UserModel();
                u.setUserid(rs.getInt("user_id"));
                u.setUserName(rs.getString("user_name"));
                u.setEmail(rs.getString("email"));
                u.setVehicleNumber(rs.getString("vehicle_number"));
                u.setVehicleType(rs.getString("vehicle_type"));
                u.setPassword(rs.getString("password"));
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public UserModel getUserByEmail(String email) {
        UserModel user = new UserModel();
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM users WHERE email=?")) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user.setUserid(rs.getInt("user_id"));
                    user.setUserName(rs.getString("user_name"));
                    user.setEmail(rs.getString("email"));
                    user.setVehicleNumber(rs.getString("vehicle_number"));
                    user.setVehicleType(rs.getString("vehicle_type"));
                    user.setPassword(rs.getString("password"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public UserModel getUserById(int userId) {
        UserModel user = new UserModel();
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM users WHERE user_id=?")) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user.setUserid(rs.getInt("user_id"));
                    user.setUserName(rs.getString("user_name"));
                    user.setEmail(rs.getString("email"));
                    user.setVehicleNumber(rs.getString("vehicle_number"));
                    user.setVehicleType(rs.getString("vehicle_type"));
                    user.setPassword(rs.getString("password"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int updateUser(UserModel user) {
        int status = 0;
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE users SET user_name=?, vehicle_number=?, vehicle_type=? WHERE user_id=?")) {

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getVehicleNumber());
            ps.setString(3, user.getVehicleType());
            ps.setInt(4, user.getUserid());

            status = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public int deleteUser(int userId) {
        int status = 0;
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection()) {
            // Guard: do not delete user with active bookings
            try (PreparedStatement psCheck = conn.prepareStatement(
                    "SELECT COUNT(*) FROM bookings WHERE user_id = ? AND booking_status = 'active'")) {

                psCheck.setInt(1, userId);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return -1;
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM users WHERE user_id=?")) {
                ps.setInt(1, userId);
                status = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}