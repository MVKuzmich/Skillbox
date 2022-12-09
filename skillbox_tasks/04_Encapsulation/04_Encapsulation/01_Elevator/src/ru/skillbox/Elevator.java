package ru.skillbox;
public class Elevator {

    int currentFloor = 1;
    int minFloor;
    int maxFloor;

    public Elevator (int minFloor, int maxFloor) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }
    public int getCurrentFloor () { return currentFloor; }

    public void moveDown () {
        currentFloor = currentFloor - 1;
        getCurrentFloor();
    }
    public void moveUp () {
        currentFloor = currentFloor + 1;
        getCurrentFloor();
    }
    public void move (int floor) {
        if (floor > currentFloor && floor <= maxFloor && floor >= minFloor) {
            while (currentFloor != floor) {
                moveUp();
                System.out.println(currentFloor);
            }
        } else if (floor < currentFloor && floor <= maxFloor && floor >= minFloor) {
             while (currentFloor != floor) {
                 moveDown();
                 System.out.println(currentFloor);
             }
         } else {
             System.out.println("Error");
        }
    }

}
