package com.example.worldmapexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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