package model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class BookingModel {
    private int bookingId;
    private int userId;
    private int slotId;
    private String vehicleNumber;
    private Date bookingDate;
    private Time entryTime;
    private Time exitTime;
    private int totalHours;
    private double totalAmount;
    private String bookingStatus;
    private Timestamp createdAt;
    // FIX: added to show user name in admin view instead of raw userId
    private String userName;

    public BookingModel() {}

    public BookingModel(int bookingId, int userId, int slotId, String vehicleNumber,
            Date bookingDate, Time entryTime, Time exitTime, int totalHours,
            double totalAmount, String bookingStatus, Timestamp createdAt) {
        this.bookingId     = bookingId;
        this.userId        = userId;
        this.slotId        = slotId;
        this.vehicleNumber = vehicleNumber;
        this.bookingDate   = bookingDate;
        this.entryTime     = entryTime;
        this.exitTime      = exitTime;
        this.totalHours    = totalHours;
        this.totalAmount   = totalAmount;
        this.bookingStatus = bookingStatus;
        this.createdAt     = createdAt;
    }

    public int getBookingId()                        { return bookingId; }
    public void setBookingId(int bookingId)          { this.bookingId = bookingId; }
    public int getUserId()                           { return userId; }
    public void setUserId(int userId)                { this.userId = userId; }
    public int getSlotId()                           { return slotId; }
    public void setSlotId(int slotId)                { this.slotId = slotId; }
    public String getVehicleNumber()                 { return vehicleNumber; }
    public void setVehicleNumber(String v)           { this.vehicleNumber = v; }
    public Date getBookingDate()                     { return bookingDate; }
    public void setBookingDate(Date bookingDate)     { this.bookingDate = bookingDate; }
    public Time getEntryTime()                       { return entryTime; }
    public void setEntryTime(Time entryTime)         { this.entryTime = entryTime; }
    public Time getExitTime()                        { return exitTime; }
    public void setExitTime(Time exitTime)           { this.exitTime = exitTime; }
    public int getTotalHours()                       { return totalHours; }
    public void setTotalHours(int totalHours)        { this.totalHours = totalHours; }
    public double getTotalAmount()                   { return totalAmount; }
    public void setTotalAmount(double totalAmount)   { this.totalAmount = totalAmount; }
    public String getBookingStatus()                 { return bookingStatus; }
    public void setBookingStatus(String s)           { this.bookingStatus = s; }
    public Timestamp getCreatedAt()                  { return createdAt; }
    public void setCreatedAt(Timestamp createdAt)    { this.createdAt = createdAt; }
    public String getUserName()                      { return userName; }
    public void setUserName(String userName)         { this.userName = userName; }
}