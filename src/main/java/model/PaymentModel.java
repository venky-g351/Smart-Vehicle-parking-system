package model;

// FIX: was java.security.Timestamp (doesn't exist) → correct is java.sql.Timestamp
import java.sql.Timestamp;

public class PaymentModel {
    private int paymentId;
    private int bookingId;
    private double amount;
    private String paymentMethod;
    private String status;
    private Timestamp paymentDate;

    public PaymentModel() {}

    public PaymentModel(int paymentId, int bookingId, double amount, String paymentMethod,
            String status, Timestamp paymentDate) {
        this.paymentId     = paymentId;
        this.bookingId     = bookingId;
        this.amount        = amount;
        this.paymentMethod = paymentMethod;
        this.status        = status;
        this.paymentDate   = paymentDate;
    }

    public int getPaymentId()                        { return paymentId; }
    public void setPaymentId(int paymentId)          { this.paymentId = paymentId; }
    public int getBookingId()                        { return bookingId; }
    public void setBookingId(int bookingId)          { this.bookingId = bookingId; }
    public double getAmount()                        { return amount; }
    public void setAmount(double amount)             { this.amount = amount; }
    public String getPaymentMethod()                 { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod){ this.paymentMethod = paymentMethod; }
    public String getStatus()                        { return status; }
    public void setStatus(String status)             { this.status = status; }
    public Timestamp getPaymentDate()                { return paymentDate; }
    public void setPaymentDate(Timestamp paymentDate){ this.paymentDate = paymentDate; }
}