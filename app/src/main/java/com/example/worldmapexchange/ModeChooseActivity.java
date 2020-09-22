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
                Resources.getInstance().chosenMode=Resources.getInstance().LENGTH_MODE;
                break;
            case R.id.btnModeArea:
                Resources.getInstance().chosenMode=Resources.getInstance().AREA_MODE;
                break;
            case R.id.btnModeMass:
                Resources.getInstance().chosenMode=Resources.getInstance().MASS_MODE;
                break;
            case R.id.btnModeTemperature:
                Resources.getInstance().chosenMode=Resources.getInstance().TEMPERATURE_MODE;
                break;
            case R.id.btnModeAngle:
                Resources.getInstance().chosenMode=Resources.getInstance().ANGLE_MODE;
                break;
            case R.id.btnModeEnergy:
                Resources.getInstance().chosenMode=Resources.getInstance().ENERGY_MODE;
                break;
            case R.id.btnModeSpeed:
                Resources.getInstance().chosenMode=Resources.getInstance().SPEED_MODE;
                break;
            case R.id.btnModeTime:
                Resources.getInstance().chosenMode=Resources.getInstance().TIME_MODE;
                break;
            //case R.id.btnModeBase:
                //Resources.getInstance().chosenMode=Resources.getInstance().BASE_MODE;
                //break;
            case R.id.btnModeCurrency:
                Resources.getInstance().chosenMode=Resources.getInstance().CURRENCY_MODE;
                break;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}