package com.example.worldmapexchange;

public class CurrencyInfo {
    public String code;
    public String name;
    public String src;
    public Double value;
    public CurrencyInfo(String name, String code, String src, double value) {
        this.code = code;
        this.name = name;
        this.src = src;
        this.value = value;
    }
}
