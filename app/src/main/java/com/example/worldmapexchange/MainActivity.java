package com.example.worldmapexchange;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Iterator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    private Resources resources = Resources.getInstance();

    private static final int CHOOSE_BASE_REQUEST = 1;
    private static final int CHOOSE_TARGET_REQUEST = 2;

    private static boolean isValid;
    private static MainActivity instance;
    public static MainActivity getInstance()
    {
        return instance;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_BASE_REQUEST) {
            if (resultCode != RESULT_OK) return;
            TextView base1 = findViewById(R.id.txt_Base_1);
            TextView base2 = findViewById(R.id.txt_Base_2);

            base1.setText(Resources.getInstance().baseCurrency);
            base2.setText(Resources.getInstance().baseCurrency);
        }
        if (requestCode == CHOOSE_TARGET_REQUEST) {
            if (resultCode != RESULT_OK) return;
            ListView lv = findViewById(R.id.currencyList);
            if (Resources.targetList == null)
                return;
            else {
                AllObjectAdapter allObjectAdapter = new AllObjectAdapter(this.getApplicationContext(), Resources.targetList);
                lv.setAdapter(allObjectAdapter);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        initComponent();
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

    private void initComponent() {
        MainActivity.isValid = true;
        setUpBackButton();
        initNumPad();
    }

    private void setUpBackButton() {
        ImageView backButton = findViewById(R.id.btn_back);
        backButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                TextView txt = MainActivity.getInstance().findViewById(R.id.txt_Expression);
                txt.setText("0");
                return true;
            }
        });
    }

    public void onBackspaceClick(View view) {
        TextView curStr = findViewById(R.id.txt_Expression);
        String cur = curStr.getText().toString();
        if (cur.length() <= 1)
            curStr.setText("0");
        else
            curStr.setText(cur.substring(0, cur.length() - 1));
    }

    public void onAddButtonClick(View view) {
        //Intent intent = new Intent(view.getContext(), ...);
        //startActivityForResult(intent, CHOOSE_TARGET_REQUEST);
    }

    public void numpadClick(View view) {
        String num = ((TextView)view).getText().toString();
        TextView curStr = findViewById(R.id.txt_Expression);
        if (curStr.getText().toString().equals("0") && !num.equals("."))
            curStr.setText(num);
        else
            curStr.append(num);
    }

    public void onConvertClick(View view) {
        TextView expression = findViewById(R.id.txt_Expression);
        String base = ((TextView)findViewById(R.id.txt_Base_1)).getText().toString();
        double result = MainActivity.eval(expression.getText().toString());
        if (!MainActivity.isValid) result = 0.0;
        TextView txtResult = findViewById(R.id.txt_Exp_Result);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(4);
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.CEILING);
        nf.setGroupingUsed(true);
        nf.setMinimumIntegerDigits(1);
        nf.setMaximumIntegerDigits(56);
        txtResult.setText(nf.format(result));

        String baseurl = "https://currency.labstack.com/api/v1/rates";
        String apiKey = "bjoVn986JOKvV8BXyGmMeaRq0sTBnlGI203NF68b7mRXqB-0zpbLt";

        (new OkHttpHandler(result, base)).execute(baseurl, apiKey);
    }

    public void alertDialog(String msg)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg).setCancelable(false).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length())
                {
                    MainActivity.getInstance().alertDialog("Invalid Expression! Cannot convert");
                    return 0.0;
                }
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) //x /= parseFactor(); // division
                    {
                        x /= parseFactor();
                        if (x == Double.NEGATIVE_INFINITY || x == Double.POSITIVE_INFINITY)
                        {
                            MainActivity.isValid = false;
                            MainActivity.getInstance().alertDialog("Bad Expression! Cannot divide by 0");
                        }
                    }
                    else return x;
                }
            }

            private boolean isValidNum(String s)
            {
                try
                {
                    double x = Double.parseDouble(s);
                }
                catch (Exception e)
                {
                    MainActivity.getInstance().alertDialog("Invalid number! Cannot convert");
                    //Log.d("MainActivity", e.getMessage());
                    //MainActivity.isValid = false;
                    return false;
                }
                return true;
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    if (isValidNum(str.substring(startPos, this.pos)))
                        x = Double.parseDouble(str.substring(startPos, this.pos));
                    else
                    {
                        MainActivity.isValid = false;
                        return 0.0;
                    }
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else
                    {
                        MainActivity.getInstance().alertDialog("Invalid Expression! Cannot convert");
                        MainActivity.isValid = false;
                        return 0.0;
                    }
                } else {
                    MainActivity.getInstance().alertDialog("Invalid Expression! Cannot convert");
                    MainActivity.isValid = false;
                    return 0.0;
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    public void onExpressionClick(View view) {
        TextView curStr = findViewById(R.id.txt_Expression);
        switch (view.getId())
        {
            case R.id.btn_sub:
                if (curStr.getText().toString().equals("0"))
                    curStr.setText("-");
                else
                    curStr.append("-");
                break;
            case R.id.btn_add:
                curStr.append("+");
                break;
            case R.id.btn_mul:
                curStr.append("*");
                break;
            case R.id.btn_div:
                curStr.append("/");
                break;
            case R.id.btn_dot:
                curStr.append(".");
                break;
            default:
                return;
        }
    }

    public void onChangeBaseClick(View view) {
        //Intent intent = new Intent(view.getContext(), ...);
        //startActivityForResult(intent, CHOOSE_BASE_REQUEST);
    }

    private class OkHttpHandler extends AsyncTask<String, Void, String>
    {
        double amount;
        String base;
        OkHttpClient client = new OkHttpClient();

        public OkHttpHandler(double amount, String base)
        {
            this.amount = amount;
            this.base = base;
        }

        @Override
        protected String doInBackground(String... params) {
            Request request = new Request.Builder().header("Authorization", params[1]).url(params[0]).build();
            try {
                Response response = client.newCall(request).execute();
                Log.d("Response: ", "Success");
                return response.body().string();
            } catch (Exception e)
            {
                e.printStackTrace();
                return "fail";
            }
            //return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equalsIgnoreCase("fail"))
            {
                Toast.makeText(MainActivity.getInstance().getApplicationContext(), "no Internet connection", LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jobject = new JSONObject(s);
                String rates = jobject.getString("rates");
                JSONObject ratesObj = new JSONObject(rates);

                //convert tu usd qua base
                double baseToUSD = convert(amount, base, ratesObj, 0);
//                Log.d("value base =", String.valueOf(baseToUSD));
//                Toast.makeText(MainActivity.getInstance().getApplicationContext(), String.valueOf(baseToUSD), LENGTH_SHORT).show();
                ListView lv = MainActivity.getInstance().findViewById(R.id.currencyList);
                AllObjectAdapter allObjectAdapter = (AllObjectAdapter) lv.getAdapter();
                if (allObjectAdapter == null) return;
                for (int i = 0; i < allObjectAdapter.getCount(); ++i)
                {
                    allObjectAdapter.getItem(i).value = convert(baseToUSD, allObjectAdapter.getItem(i).code, ratesObj, 1);
                }
                allObjectAdapter.notifyDataSetChanged();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        //opt = 0 -> base -> usd
        //opt = 1 -> use -> base
        private double convert(double amount, String base, JSONObject ratesObj,int opt)
        {
            double exRate = 0.0;
            try {
                for (Iterator<String> i = ratesObj.keys(); i.hasNext();) {
                    String key = i.next();
                    if (key.equalsIgnoreCase(base)) {
                        exRate = convertStringtoDouble(ratesObj.getString(key));
                        return (opt == 1)?(exRate * amount):(amount / exRate);
                    }
                }
            } catch (Exception e)
            {
                return 0.0;
            }
            return 0.0;
        }
    }

    private double convertStringtoDouble(String s)
    {
        try
        {
            return Double.parseDouble(s);
        }
        catch (Exception e)
        {
            alertDialog("Invalid number");
            Log.d("MainActivity", e.getMessage());
            MainActivity.isValid = false;
            return -10.0;
        }
    }
}