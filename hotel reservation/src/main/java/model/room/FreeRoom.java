package model.room;

import model.customer.Customer;

import java.util.Objects;

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, Double price, RoomType roomType){
        super(roomNumber,0.0,roomType);
    }

    @Override
    public boolean isFree(){
        return true;
    }
    @Override
    public String toString() {
        return "Free Room: Price " + price + " - Type " + roomType.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room otherRoom = (Room) o;
        return super.equals(otherRoom);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
