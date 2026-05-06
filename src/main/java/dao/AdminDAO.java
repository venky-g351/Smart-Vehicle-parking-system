package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.AdminModel;
import utility.DBConnection;

public class AdminDAO implements AdminInterface {

    @Override
    public String login(AdminModel ad) {
        String status = "fail";
        DBConnection db = new DBConnection();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM admin WHERE username=? AND password=?")) {

            ps.setString(1, ad.getUsername());
            ps.setString(2, ad.getPassword());

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
}