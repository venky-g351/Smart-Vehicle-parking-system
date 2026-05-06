package dao;

 
import java.util.List;

import model.ParkingSlotModel;
 
public interface ParkingSlotInterface {
    int addParkingSlot(ParkingSlotModel pm);
    List<ParkingSlotModel> getAllParkingSlots();
    List<ParkingSlotModel> getAvailableSlots();
    ParkingSlotModel getSlotById(int slotId);
    int updateParkingSlot(ParkingSlotModel slot);
    int deleteParkingSlot(int slotId);
}