package api;

import model.customer.Customer;
import model.room.IRoom;
import model.room.Room;
import model.room.RoomType;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static final AdminResource reference = new AdminResource(CustomerService.getInstance(),ReservationService.getInstance());
    private final CustomerService customerService;
    private final ReservationService reservationService;
    private AdminResource(CustomerService customerService, ReservationService reservationService){
        this.customerService = customerService;
        this.reservationService = reservationService;
    }

    public static AdminResource getInstance(){
        return reference;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void addRooms(List<IRoom> rooms){
        rooms.forEach(reservationService::addRoom);
    }

    public void addRoom(IRoom room){
        reservationService.addRoom(room);
    }

    public boolean roomIdAvailable(String roomId){
        return reservationService.roomNumberAvailable(roomId);
    }

    public Collection<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public void displayAllReservations(){
        reservationService.printAllReservations();
    }

}
