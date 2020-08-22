package com.example.worldmapexchange;

import java.util.ArrayList;

public class Resources {
    private static final Resources RESOURCES = new Resources();
    //private constructor to avoid client applications to use constructor

    private Resources()
    {
        targetList = null;
    }
    public static Resources getInstance(){
        return RESOURCES;
    }
    public String baseCurrency = "USD";
    public static ArrayList<CurrencyInfo> targetList;

    public CurrencyInfo chosenCurrency = null; //chosen base currency
    public ArrayList<CurrencyInfo> allBaseCurrency = null; //all currency list
    public CurrencyInfo baseCurrencyAPI; //base rate from API
}
