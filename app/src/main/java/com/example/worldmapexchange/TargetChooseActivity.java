package com.example.worldmapexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class TargetChooseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String TAG="TargetChooseActivity";
    private TargetAdapter mTargetAdapter;
    private ListView listViewTarget;
    private ArrayList<AllObject> mListTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_choose);

        int mode=Resources.getInstance().chosenMode;
        String fileName="list"+mode+".json";
        String JSONString=null;
        //Parse JSON File
        try {
            InputStream inputStream=getAssets().open("json/" + fileName);
            Scanner sc=new Scanner(inputStream);
            StringBuilder stringBuilder=new StringBuilder();
            String line;
            while (sc.hasNextLine()) {
                line=sc.nextLine();
                stringBuilder.append(line);
            }
            JSONString=stringBuilder.toString();
        } catch (IOException e) {
            Log.e(TAG,"JSON file: json/"+fileName+" not found!");
            e.printStackTrace();
        }

        mListTarget = new ArrayList<>();

        try {
            JSONObject jsonObject=new JSONObject(JSONString);
            Iterator<String> keys=jsonObject.keys();
            while (keys.hasNext()) {
                String abbrev=keys.next();
                String fullname=jsonObject.getString(abbrev);
                AllObject Atarget=new AllObject(fullname,abbrev,null,0.0);
                mListTarget.add(Atarget);
            }
        } catch (JSONException e) {
            Log.e(TAG, "NULL JSON STRING OR CREATING JSONOBJECT FAILED");
            e.printStackTrace();
        }

        mTargetAdapter=new TargetAdapter(this,0, mListTarget);
        listViewTarget=findViewById(R.id.listViewTarget);
        listViewTarget.setAdapter(mTargetAdapter);
        listViewTarget.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AllObject target=mListTarget.get(i);
        ArrayList<AllObject> targetList=new ArrayList<>();
        targetList.add(target);
        Resources.getInstance().targetList=targetList;
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}