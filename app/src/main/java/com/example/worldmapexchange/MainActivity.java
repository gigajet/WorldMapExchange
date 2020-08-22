package com.example.worldmapexchange;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private Resources resources = Resources.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNumPad();
    }

    public void numpadClick(View view) {
        //handle onclick
        //int id = view.getId();
        //String identity = getResources().getResourceEntryName(id);
    }

    //thanh test
    public void addNewCurrency(View view) {
        Intent intent = new Intent(this,thanhActivity.class);
        startActivityForResult(intent,1);
    }

    //thanh test
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                TextView textView = findViewById(R.id.test);
                textView.setText(resources.chosenCurrency.name + " base:" + resources.baseCurrencyAPI.name);
            }
        }
    }

    public void initNumPad()
    {
        TextView textView = findViewById(R.id.btn_0);
        textView.setText("0");
        textView = findViewById(R.id.btn_1);
        textView.setText("1");
        textView = findViewById(R.id.btn_2);
        textView.setText("2");
        textView = findViewById(R.id.btn_3);
        textView.setText("3");
        textView = findViewById(R.id.btn_4);
        textView.setText("4");
        textView = findViewById(R.id.btn_5);
        textView.setText("5");
        textView = findViewById(R.id.btn_6);
        textView.setText("6");
        textView = findViewById(R.id.btn_7);
        textView.setText("7");
        textView = findViewById(R.id.btn_8);
        textView.setText("8");
        textView = findViewById(R.id.btn_9);
        textView.setText("9");
        textView = findViewById(R.id.btn_dot);
        textView.setText(".");
    }
}