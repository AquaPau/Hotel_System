package model;

import enums.*;

import java.math.BigDecimal;

public class Room {

    private int roomNumber;
    private ClassID classID;
    private Capacity capacity;
    private BigDecimal price;

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public ClassID getClassID() {
        return classID;
    }

    public void setClassID(ClassID classID) {
        this.classID = classID;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
