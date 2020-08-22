package com.example.worldmapexchange;

public class CurrencyInfo {
    public String name;
    public String code;
    public String src;
    public double value = 0.0;
    //public boolean checked = false;

    public CurrencyInfo(String name, String code, String src, double value) {
        this.name = name;
        this.code = code;
        this.src = src;
        this.value = value;
        //this.checked = false;
    }
}
