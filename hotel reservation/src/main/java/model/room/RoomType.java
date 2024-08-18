package model.room;

import menu.AdminMenu;

public enum RoomType {
    SINGLE(1),
    DOUBLE(2);

    private final int value;

    RoomType(int value){
        this.value = value;
    }

    int getValue(){
        return value;
    }

    public static RoomType fromInt(int value) {
        for (RoomType roomType : RoomType.values()) {
            if (roomType.getValue() == value) {
                return roomType;
            }
        }
        return null;
    }
}
