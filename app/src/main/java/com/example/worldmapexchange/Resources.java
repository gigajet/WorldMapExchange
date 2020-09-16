package com.example.worldmapexchange;

import java.util.ArrayList;

public class Resources {
    public static final int LENGTH_MODE = 0;
    public static final int AREA_MODE = 1;
    public static final int MASS_MODE = 2;
    public static final int TEMPERATURE_MODE = 3;
    public static final int ANGLE_MODE = 4;
    public static final int ENERGY_MODE = 5;
    public static final int SPEED_MODE = 6;
    public static final int TIME_MODE = 7;
    public static final int BASE_MODE = 8;
    public static final int CURRENCY_MODE = 9;


    private static final Resources RESOURCES = new Resources();
    //private constructor to avoid client applications to use constructor

    public static int chosenMode = 9;

    public static String[] defaultBase = {"m", "m^2", "kg", "C", "rad", "J", "m/s", "sec", "base 2", "USD"};

    private Resources()
    {
        targetList = null;
    }
    public static Resources getInstance(){
        return RESOURCES;
    }
    public String baseCurrency = "USD";
    public static ArrayList<AllObject> targetList;

    public AllObject chosenBase = null; //chosen base currency
    public ArrayList<AllObject> allBase = null; //all currency list
    public AllObject baseCurrencyAPI; //base rate from API
}
