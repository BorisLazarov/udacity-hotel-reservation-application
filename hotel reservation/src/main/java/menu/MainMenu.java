package menu;

import Utilities.ScannerInputHandler;
import api.AdminResource;
import api.HotelResource;
import model.reservation.Reservation;
import model.room.IRoom;
import model.room.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu extends Menu<MainMenu.Action> {


    final AdminResource adminResource = AdminResource.getInstance();
    final HotelResource hotelResource = HotelResource.getInstance();

    /*
    I considered adding a subMenus field in the Menu abstract class and accessing it from there
    the main reason is the line of thinking "what if we need this in the future",
    but decided to follow the keep it simple for now.
    */
    final AdminMenu adminMenu = new AdminMenu();


    public MainMenu() {
        super(List.of(Action.values()));
    }

    protected enum Action {
        FIND_AND_RESERVE_A_ROOM(1),
        SEE_MY_RESERVATIONS(2),
        CREATE_AN_ACCOUNT(3),
        ADMIN(4),
        EXIT(5);

        private final int value;

        Action(int value){
            this.value = value;
        }

        int getValue(){
            return value;
        }

        static Action fromInt(int value) {
            for (Action action : Action.values()) {
                if (action.getValue() == value) {
                    return action;
                }
            }
            return null;
        }

    }

    @Override
    protected Action getActionFromInt(int i) {
        return Action.fromInt(i);
    }

    @Override
    protected int getIntFromAction(Action a) {
        return a.getValue();
    }


    @Override
    protected void executeAction(Action action) {
        Scanner scanner = new Scanner(System.in);
        switch (action){
            case FIND_AND_RESERVE_A_ROOM -> {
                Date checkInDate;
                Date checkOutDate;
                checkInDate = ScannerInputHandler.getValidDateInput(scanner,"Enter check in date mm/dd/yyyy example 02/01/2020");
                while(true){
                    checkOutDate = ScannerInputHandler.getValidDateInput(scanner,"Enter check out date mm/dd/yyyy example 02/01/2020");
                    if(checkOutDate.after(checkInDate)){
                        break;
                    }
                    System.out.println("Check in date cannot be after check out date. Please try again.");
                }

                Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate,checkOutDate);
                if(availableRooms.isEmpty()){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(checkInDate);
                    calendar.add(Calendar.DAY_OF_MONTH, 7);
                    checkInDate = calendar.getTime();

                    calendar.setTime(checkOutDate);
                    calendar.add(Calendar.DAY_OF_MONTH, 7);
                    checkOutDate = calendar.getTime();
                    System.out.printf("No rooms are available for the requested dates, will search for rooms on alternative dates %s - %s.%n", checkInDate,checkOutDate);
                    availableRooms = hotelResource.findARoom(checkInDate,checkOutDate);
                    if(availableRooms.isEmpty()){
                        System.out.println("No rooms are available for the alternative dates as well.");
                        return;
                    }else{
                        System.out.printf("Alternative rooms are have been found and the booking dates have been updated to %s and %s%n",  checkInDate, checkOutDate);
                    }
                }

                availableRooms.forEach(System.out::println);
                boolean bookARoomChoice = ScannerInputHandler.getValidBooleanYNInput(scanner,"Would you like to book a room? y/n");
                if(!bookARoomChoice) return;

                boolean alreadyHaveAnAccount = ScannerInputHandler.getValidBooleanYNInput(scanner,"Do you already have an account with us? y/n");
                if(!alreadyHaveAnAccount) executeAction(Action.CREATE_AN_ACCOUNT);

                String email = ScannerInputHandler.getValidStringInput(scanner, "Enter Email format: name@domain.com");
                if(hotelResource.getCustomer(email) == null){
                    //Here I could also do the email validity check and force the customer to supply a registered and valid email
                    //but decided it is more straightforward to just return them to the menu
                    System.out.println("Email is not registered or invalid. Please enter email format: name@domain.com");
                    return ;
                }

                String roomNumber = ScannerInputHandler.getValidStringInput(scanner,"What room would you like to reserve?");
                while(hotelResource.getRoom(roomNumber) == null){
                    //Тhe reason I am forcing a choice here instead of returning to menu as above is that the valid room numbers have been printed out,
                    //so it shouldn't be possible to get stuck in a case where you are not aware of the valid input choices
                    roomNumber = ScannerInputHandler.getValidStringInput(scanner,"Room number is not valid. Please enter a valid room number.");
                }

                Reservation reservation = hotelResource.bookARoom(email,hotelResource.getRoom(roomNumber),checkInDate,checkOutDate);
                System.out.println(reservation);

            }case SEE_MY_RESERVATIONS -> {
                String email = ScannerInputHandler.getValidStringInput(scanner, "Enter Email format: name@domain.com");
                //Here I could also do the email validity check and force the customer to supply a registered and valid email
                //but decided it is more straightforward to just return them to the menu
                if(hotelResource.getCustomer(email) == null){
                    System.out.println("Email is not registered or invalid.");
                    return ;
                }
                Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);
                reservations.forEach(System.out::println);
            }case CREATE_AN_ACCOUNT -> {
                String email = ScannerInputHandler.getValidStringInput(scanner, "Enter Email format: name@domain.com");
                while(!hotelResource.customerEmailAvailable(email)){
                    email = ScannerInputHandler.getValidStringInput(scanner, "Email is taken or invalid. Please enter email format: name@domain.com");
                }
                String firstName = ScannerInputHandler.getValidStringInput(scanner, "Enter First Name:");
                String lastName = ScannerInputHandler.getValidStringInput(scanner, "Enter Last Name:");
                hotelResource.createACustomer(email,firstName,lastName);
                System.out.println("Account created successfully! Welcome to the Hotel Reservation Application!");
            }case ADMIN -> {
                adminMenu.runMenu();
            }case EXIT -> {
            }
        }
    }

}
