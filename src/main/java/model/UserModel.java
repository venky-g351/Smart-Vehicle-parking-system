package model;

public class UserModel {
    private int userid;
    private String userName;
    private String email;
    private String vehicleNumber;
    private String vehicleType;
    private String password;
 
    public int getUserid() { return userid; }
    public void setUserid(int userid) { this.userid = userid; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}