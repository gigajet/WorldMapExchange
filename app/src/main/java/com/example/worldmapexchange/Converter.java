package com.example.worldmapexchange;

import android.util.Log;

import com.google.android.gms.maps.model.Polyline;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Converter {
    public static double Convert(int type, String base, String target, double amount)
    {
        String filename = "json/rate"+ type +".json";

        if (type == Resources.TEMPERATURE_MODE)
            return ConvertTemp(base, target, amount);

        String json = null;
        try
        {
            InputStream is = MainActivity.getInstance().getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        }
        catch (Exception e)
        {
            return -2.0;
        }

        try
        {
            Log.e("jsonException: ",base + "     " + target);
            JSONObject jsonObject = new JSONObject(json);
            String rates = jsonObject.getString("rate");
            JSONObject rateobj = new JSONObject(rates);
            double exRateBB = rateobj.getDouble(base);
            double exRateBT = rateobj.getDouble(target);

            return amount / exRateBB * exRateBT;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1.0;
        }

        //return 0.0;
    }

    private static double ConvertTemp(String base, String target, double amount)
    {
        if (base.equalsIgnoreCase("K") && target.equalsIgnoreCase("C"))
            return KtoC(amount);
        if (base.equalsIgnoreCase("C") && target.equalsIgnoreCase("K"))
            return CtoK(amount);
        if (base.equalsIgnoreCase("F") && target.equalsIgnoreCase("C"))
            return FtoC(amount);
        if (base.equalsIgnoreCase("C") && target.equalsIgnoreCase("F"))
            return CtoF(amount);
        if (base.equalsIgnoreCase("K") && target.equalsIgnoreCase("F"))
            return KtoF(amount);
        if (base.equalsIgnoreCase("F") && target.equalsIgnoreCase("K"))
            return FtoK(amount);
        if (base.equalsIgnoreCase("R") && target.equalsIgnoreCase("C"))
            return RatoC(amount);
        if (base.equalsIgnoreCase("C") && target.equalsIgnoreCase("R"))
            return CtoRa(amount);
        if (base.equalsIgnoreCase("F") && target.equalsIgnoreCase("R"))
            return FtoRa(amount);
        if (base.equalsIgnoreCase("R") && target.equalsIgnoreCase("F"))
            return RatoF(amount);
        if (base.equalsIgnoreCase("R") && target.equalsIgnoreCase("K"))
            return RatoK(amount);
        if (base.equalsIgnoreCase("K") && target.equalsIgnoreCase("R"))
            return KtoRa(amount);
        return amount;
    }

    private static double KtoC(double amount)
    {
        return amount - 273.15;
    }

    private static double CtoK(double amount)
    {
        return amount + 273.15;
    }

    private static double FtoC(double amount)
    {
        return (amount - 32.0) * 5.0 / 9.0;
    }

    private static double CtoF(double amount)
    {
        return amount / 5.0 * 9.0 + 32.0;
    }

    private static double FtoK(double amount)
    {
        return CtoK(FtoC(amount));
    }

    private static double KtoF(double amount)
    {
        return CtoF(KtoC(amount));
    }

    private static double RatoK(double amount)
    {
        return amount * 5.0 / 9.0;
    }

    private static double KtoRa(double amount)
    {
        return amount * 9.0 / 5.0;
    }

    private static double RatoC(double amount)
    {
        return (amount - 491.67) * 5.0 / 9.0;
    }

    private static double CtoRa(double amount)
    {
        return (amount + 273.15) * 9.0 / 5.0;
    }

    private static double RatoF(double amount)
    {
        return amount - 459.67;
    }

    private static double FtoRa(double amount)
    {
        return amount + 459.67;
    }
}
