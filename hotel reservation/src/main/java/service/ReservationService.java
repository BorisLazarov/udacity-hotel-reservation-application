package service;

import model.customer.Customer;
import model.reservation.Reservation;
import model.room.IRoom;
import model.room.Room;
import model.room.RoomType;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ReservationService {
    private static final ReservationService reference = new ReservationService();
    final Set<Reservation> reservations;
    final Set<IRoom> rooms;

    private ReservationService(){
        this.reservations = new HashSet<>();
        this.rooms = new HashSet<>();
    }

    public static ReservationService getInstance(){
        return reference;
    }

    public void addRoom(IRoom room){
        if(reference.rooms.contains(room)){
          throw new IllegalArgumentException("Room already exists on the system " + room.toString());
        }
        reference.rooms.add(room);
    }

    public IRoom getARoom(String roomId){
        for (IRoom room : reference.rooms){
            if (room.getRoomNumber().equals(roomId)){
                return room;
            }
        }
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation newReservation = new Reservation(customer,room,checkInDate,checkOutDate);

        boolean occupiedOnce = false;
        for (Reservation reservation : reference.reservations){
            if (reservation.getRoom().equals(room)){
                if (newReservation.overlapsWith(reservation)){

                    if (room.getRoomType().equals(RoomType.SINGLE) || occupiedOnce)
                        throw new RuntimeException("Reservations overlap: " + reservation.toString() + " and " + newReservation.toString());

                    if(room.getRoomType().equals(RoomType.DOUBLE)){
                        occupiedOnce = true;
                    }
                }
            }
        }

        reference.reservations.add(newReservation);
        return newReservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Set<IRoom> availableRooms = new HashSet<>(reference.rooms);
        Set<IRoom> alternativeSuggestionsRooms = new HashSet<>(reference.rooms);

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(checkInDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date alternativeCheckInDate = calendar.getTime();

        calendar.setTime(checkOutDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date alternativeCheckOutDate = calendar.getTime();



        for (Reservation reservation : reference.reservations){
            if (reservation.overlapsWith(checkInDate,checkOutDate)){
                availableRooms.remove(reservation.getRoom());
            }
            if (reservation.overlapsWith(alternativeCheckInDate,alternativeCheckOutDate)){
                alternativeSuggestionsRooms.remove(reservation.getRoom());
            }

        }
        if(!availableRooms.isEmpty()){
            return availableRooms;
        }
        return alternativeSuggestionsRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer){
        Set<Reservation> customerReservations = new HashSet<>();
        for (Reservation reservation : reference.reservations){
            if (reservation.getCustomer().equals(customer)){
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }

    public void printAllReservations(){
        for (Reservation reservation : reference.reservations){
            System.out.println(reservation.toString());
        }
    }

    public Collection<IRoom> getAllRooms(){
        return reference.rooms;
    }

    public boolean roomNumberAvailable(String roomId){
        return !reference.rooms.contains(new Room(roomId,0.0,RoomType.SINGLE)); //A bit crude, but does the job
    }



}
