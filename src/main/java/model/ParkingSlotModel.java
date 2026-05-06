package model;

public class ParkingSlotModel {
    private int slotId;
    private int slotNumber;
    private String slotType;
    private int floorNumber;
    private String vehicleType;
    private boolean available;
    private double hourRate;
 
    public ParkingSlotModel() {}
 
    public ParkingSlotModel(int slotId, int slotNumber, String slotType, int floorNumber,
                            String vehicleType, boolean available, double hourRate) {
        this.slotId = slotId;
        this.slotNumber = slotNumber;
        this.slotType = slotType;
        this.floorNumber = floorNumber;
        this.vehicleType = vehicleType;
        this.available = available;
        this.hourRate = hourRate;
    }
 
    public int getSlotId() { return slotId; }
    public void setSlotId(int slotId) { this.slotId = slotId; }
    public int getSlotNumber() { return slotNumber; }
    public void setSlotNumber(int slotNumber) { this.slotNumber = slotNumber; }
    public String getSlotType() { return slotType; }
    public void setSlotType(String slotType) { this.slotType = slotType; }
    public int getFloorNumber() { return floorNumber; }
    public void setFloorNumber(int floorNumber) { this.floorNumber = floorNumber; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public double getHourRate() { return hourRate; }
    public void setHourRate(double hourRate) { this.hourRate = hourRate; }
}
