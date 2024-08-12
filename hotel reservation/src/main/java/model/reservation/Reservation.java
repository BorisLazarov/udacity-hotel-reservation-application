package model.reservation;

import model.customer.Customer;
import model.room.IRoom;

import java.util.Date;
import java.util.Objects;

public class Reservation {

    final Customer customer;
    final IRoom room;
    final Date checkInDate;
    final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public IRoom getRoom(){
        return room;
    }

    public Date getCheckInDate(){
        return checkInDate;
    }

    public Date getCheckOutDate(){
        return checkOutDate;
    }

    public boolean overlapsWith(Reservation otherReservation) {
        return checkInDate.before(otherReservation.getCheckOutDate()) && checkOutDate.after(otherReservation.getCheckInDate());
    }

    public boolean overlapsWith(Date otherCheckInDate, Date otherCheckOutDate){
        return checkInDate.before(otherCheckOutDate) && checkOutDate.after(otherCheckInDate);

    }

    public Customer getCustomer(){
        return customer;
    }

    @Override
    public String toString() {
        return "Customer: " + customer.toString() + " - Room: " + room.toString() + " - CheckInDate: " + checkInDate.toString() +  " - CheckOutDate: " + checkOutDate.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation otherReservation = (Reservation) o;
        return customer.equals(otherReservation.customer)
                &&
                room.equals(otherReservation.room)
                &&
                checkInDate.equals(otherReservation.checkInDate)
                &&
                checkOutDate.equals(otherReservation.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }
}
