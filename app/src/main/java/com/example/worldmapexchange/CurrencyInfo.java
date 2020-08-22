package com.example.worldmapexchange;

public class CurrencyInfo {
    public CurrencyInfo(String name, String code, String src, double value) {
        this.code = code;
        this.name = name;
        this.src = src;
        this.value = value;
    }

    public String code,name,src;
    public double value=0.0;
}
