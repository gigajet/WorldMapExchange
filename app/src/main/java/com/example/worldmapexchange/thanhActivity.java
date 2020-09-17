package com.example.worldmapexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import java.util.Currency;
import java.util.Iterator;

public class thanhActivity extends AppCompatActivity {
    private thanhCurrencyAdapter thanhCurrencyAdapter =null;
    private Resources resources = Resources.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh);
        if (resources.allBase == null)
            (new GetOnlineRate(this)).execute();
        else
            updateCurrency();
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void updateCurrency() {
        ListView listView = findViewById(R.id.thanhListView);
        thanhCurrencyAdapter = new thanhCurrencyAdapter(this,0,resources.allBase);
        listView.setAdapter(thanhCurrencyAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finishWork(position);
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(MainActivity.RESULT_CANCELED);
        finish();
    }

    private void finishWork(int position) {
        resources.chosenBase = resources.allBase.get(position);
        resources.baseChosen = resources.chosenBase.code;
        setResult(MainActivity.RESULT_OK);
        finish();
    }


    private class GetOnlineRate extends AsyncTask<Void,Void, ArrayList<AllObject>>
    {
        final String website = "http://data.fixer.io/api/latest?access_key=ab9075a61fa7f94a67dffe484a6247a2";
        String URLcontent = "";
        ArrayList<AllObject> res = new ArrayList<>();
        private ProgressDialog dialog;

        public GetOnlineRate(Activity activity) {
            this.dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Updating currency...");
            dialog.show();
        }

        @Override
        protected ArrayList<AllObject> doInBackground(Void... voids) {
            try {
                URL url = new URL(website);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String tmp = "";
                while (tmp != null) {
                    tmp = bufferedReader.readLine();
                    URLcontent += tmp;
                }

                JSONObject jsonObject = new JSONObject(URLcontent);
                String base_rate = jsonObject.getString("base");
                JSONObject rates = (JSONObject) jsonObject.get("rates");
                Iterator<String> keys = rates.keys();
                String key="";
                String name ="";
                String src = "";
                while(keys.hasNext()) {
                    key = keys.next();
                    src = "image/"+key+".svg";
                    name = Currency.getInstance(key).getDisplayName();
                    res.add(new AllObject(name,key,src,0.0));
                    if (base_rate.equals(key)){
                        AllObject tmpcur = new AllObject(name,key,src,0.0);
                        resources.baseCurrencyAPI = tmpcur;
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(ArrayList<AllObject> res) {
            if (resources.allBase != null) {
                resources.allBase.clear();
                resources.allBase = null;
            }
            resources.allBase = new ArrayList<>(res);
            if (dialog.isShowing())
                dialog.dismiss();
            updateCurrency();
        }
    }
}