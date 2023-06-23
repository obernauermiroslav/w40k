package com.example.w40k.models;

public enum ShipClass {
    ESCORTS("Escorts"),
    CRUISERS("Cruisers"),
    BATTLESHIPS("Battleships");

    public final String label;

    ShipClass(String label) {
       this.label = label;
    }

    public static ShipClass fromLabel(String label){
        for( ShipClass type : ShipClass.values()){
            if(type.label.equals(label)){
                return type;
            }
        }
        return null;
    }
}
