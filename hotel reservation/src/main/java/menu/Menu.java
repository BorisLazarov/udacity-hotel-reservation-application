package menu;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public abstract class Menu<T extends Enum<T>> {

    List<T> actions;

    public Menu(List<T> actions){
        this.actions = actions;
    }

    public List<T> getActions(){
        return actions;
    }

    protected abstract T getActionFromInt(int i);

    protected abstract int getIntFromAction(T a);

    private String getActionString(T action){
        String a = action.toString().toLowerCase().replaceAll("_"," ");
        return a.substring(0,1).toUpperCase() + a.substring(1);
    }

    protected void printMenuActions(){
        for (T action : actions){
            System.out.println(getIntFromAction(action) + " " + getActionString(action));
        }
    }

    protected abstract void executeAction(T action);

    public void runMenu() {

        Scanner scanner = new Scanner(System.in);
        int userIntInput;
        T userInputAction = null;
        int valueOfLastMenuItem = getIntFromAction(actions.getLast()); //this gives us the int value of the last enum value
        do{
            System.out.println("Please select an option:");
            printMenuActions();
            try{
                userIntInput = scanner.nextInt();
                while(! (userIntInput > 0 && userIntInput <= valueOfLastMenuItem) ){
                    System.out.println("Please select a valid number that corresponds to one of the options below");
                    printMenuActions();
                    userIntInput = scanner.nextInt();
                }
                userInputAction = getActionFromInt(userIntInput);
                System.out.println("User Input: " + userIntInput);
                executeAction(userInputAction);
                System.out.println("--- ---- ---- ---");
            } catch (InputMismatchException e){
                System.out.println("You need to select the number corresponding to the option you want");
                scanner.nextLine(); //This will remove the invalid input from the scanner.
            }

        }while(userInputAction != actions.getLast()); //I'm not a huge fan of this, because it relies on the last item in the enum to be the "Exit/Return to main menu action"
                                                      //I've allowed myself to do this here, since the enums in questions are only and directly tied to menu items, not DB fields, so it "should be fine" to do this.
    }
}


