public class House {
    private String houseAddress;
    private double houseSquare;
    private int countHouseRoom;
    private int countHouseFloor;

    public House(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public double getHouseSquare() {
        return houseSquare;
    }

    public void setHouseSquare(double houseSquare) {
        this.houseSquare = houseSquare;
    }

    public int getCountHouseRoom() {
        return countHouseRoom;
    }

    public void setCountHouseRoom(int countHouseRoom) {
        this.countHouseRoom = countHouseRoom;
    }

    public int getCountHouseFloor() {
        return countHouseFloor;
    }

    public void setCountHouseFloor(int countHouseFloor) {
        this.countHouseFloor = countHouseFloor;
    }
}
