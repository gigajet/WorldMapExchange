package com.example.worldmapexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ModeChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_choose);
    }

    public void onClickModeChooseButtons(View view) {
        switch (view.getId()) {
            case R.id.btnModeLength:
                Resources.getInstance().chosenMode=0;
                break;
            case R.id.btnModeArea:
                Resources.getInstance().chosenMode=1;
                break;
            case R.id.btnModeMass:
                Resources.getInstance().chosenMode=2;
                break;
            case R.id.btnModeTemperature:
                Resources.getInstance().chosenMode=3;
                break;
            case R.id.btnModeAngle:
                Resources.getInstance().chosenMode=4;
                break;
            case R.id.btnModeEnergy:
                Resources.getInstance().chosenMode=5;
                break;
            case R.id.btnModeSpeed:
                Resources.getInstance().chosenMode=6;
                break;
            case R.id.btnModeTime:
                Resources.getInstance().chosenMode=7;
                break;
            case R.id.btnModeBase:
                Resources.getInstance().chosenMode=8;
                break;
            case R.id.btnModeCurrency:
                Resources.getInstance().chosenMode=9;
                break;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}