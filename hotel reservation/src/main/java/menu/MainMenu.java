package menu;

import api.AdminResource;
import api.HotelResource;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MainMenu extends Menu<MainMenu.Action> {


    final AdminResource adminResource = AdminResource.getInstance();
    final HotelResource hotelResource = HotelResource.getInstance();
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
        switch (action){
            case FIND_AND_RESERVE_A_ROOM -> {
                return;
            }case SEE_MY_RESERVATIONS -> {
                return;
            }case CREATE_AN_ACCOUNT -> {

            }case ADMIN -> {
                adminMenu.runMenu();
            }case EXIT -> {
                return;
            }
        }
    }

}
