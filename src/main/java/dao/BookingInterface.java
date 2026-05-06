package dao;

 
import java.util.List;

import model.BookingModel;
 
public interface BookingInterface {
    String createBooking(BookingModel book);
    List<BookingModel> getAllBookings();
    BookingModel getBookingById(int bookingId);
    int updateBookingStatus(BookingModel booking);
    int deleteBooking(int bookingId);
    List<BookingModel> getBookingsByUserId(int userId);
    int completeBooking(int bookingId, java.sql.Time exitTime, int totalHours, double totalAmount);
}