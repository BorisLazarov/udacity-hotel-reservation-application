package menu;

import java.util.List;

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

    public abstract void runMenu();
    protected abstract void executeAction(T action);


}

