package menu;

import api.AdminResource;
import api.HotelResource;
import model.room.FreeRoom;
import model.room.Room;
import model.room.RoomType;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AdminMenu extends Menu<AdminMenu.Action> {
    final AdminResource adminResource = AdminResource.getInstance();
    public AdminMenu() {
        super(List.of(Action.values()));
    }

    protected enum Action {
        SEE_ALL_CUSTOMERS(1),
        SEE_ALL_ROOMS(2),
        SEE_ALL_RESERVATIONS(3),
        ADD_A_ROOM(4),
        BACK_TO_MAIN_MENU(5);

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
        switch (action){
            case SEE_ALL_CUSTOMERS -> {
                adminResource.getAllCustomers().forEach(System.out::println);
            }
            case SEE_ALL_ROOMS ->{
                adminResource.getAllRooms().forEach(System.out::println);
            }
            case SEE_ALL_RESERVATIONS ->{
                adminResource.displayAllReservations();
            }
            case ADD_A_ROOM -> {
                boolean finishedAddingRooms = false;

                do{
                    Scanner scanner = new Scanner(System.in);
                    try{

                        String roomId = ScannerInputHandler.getValidStringInput(scanner, "Enter room number");
                        while(!adminResource.roomIdAvailable(roomId)){
                            roomId = ScannerInputHandler.getValidStringInput(scanner, "Room number is taken or invalid. Please enter room number.");
                        }

                        double pricePerNight = ScannerInputHandler.getValidDoubleInput(scanner,"Enter price per night",0.0);
                        int roomTypeNumber = ScannerInputHandler.getValidIntInput(scanner,"Enter room type: 1 for single bed, 2 for double bed",List.of(1,2));

                        if(pricePerNight == 0.0){
                            adminResource.addRoom(new FreeRoom(roomId,RoomType.fromInt(roomTypeNumber)));
                        }else{
                            adminResource.addRoom(new Room(roomId,pricePerNight, RoomType.fromInt(roomTypeNumber)));
                        }

                        String continueAddingRooms = ScannerInputHandler.getValidStringInput(scanner,"Would you like to add another room: y/n?",List.of("y","n"));

                        if(continueAddingRooms.equals("n")) {
                            finishedAddingRooms = true;
                        }
                } catch (InputMismatchException ex){
                    System.out.println("You are supposed to provide either a number of a double here.");
                }
                }while(!finishedAddingRooms);


            }
            case BACK_TO_MAIN_MENU -> {
            }
            default -> throw new IllegalStateException("Unexpected value: " + action);
        }
    }




}
