package com.example.worldmapexchange;

public class CurrencyInfo {
    public String code;
    public String name;
    public String src;
    public Double value;
    CurrencyInfo(String code, String name,String src,Double value)
    {
        this.code = code;
        this.name = name;
        this.src = src;
        this.value = value;
    }
}
