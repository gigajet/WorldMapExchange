package com.example.worldmapexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 1);
            if (checkSelfPermission(Manifest.permission.INTERNET)== PackageManager.PERMISSION_GRANTED) {
                Intent it=new Intent(this,GoogleMapActivity.class);
                startActivityForResult(it,1);
            }
            else {
                Toast.makeText(this, "No permission no app", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Intent it=new Intent(this,GoogleMapActivity.class);
            startActivityForResult(it,1);
        }
    }
}