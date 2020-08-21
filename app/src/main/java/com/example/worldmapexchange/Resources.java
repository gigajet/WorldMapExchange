package com.example.worldmapexchange;


public class Resources {
    private static final Resources RESOURCES = new Resources();
    //private constructor to avoid client applications to use constructor
    private Resources(){}
    public static Resources getInstance(){
        return RESOURCES;
    }
    private String baseCurrency ="";
}
