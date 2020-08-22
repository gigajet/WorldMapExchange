package com.example.worldmapexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static boolean isValid;
    private static MainActivity instance;
    public static MainActivity getInstance()
    {
        return instance;
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
        ImageView backButton = (ImageView) findViewById(R.id.btn_back);
        backButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                TextView txt = (TextView)MainActivity.getInstance().findViewById(R.id.txt_Expression);
                txt.setText("0");
                //MainActivity.getInstance().convertCLick();
                return true;
            }
        });
    }

    public void onBackspaceClick(View view) {
        TextView curStr = (TextView)findViewById(R.id.txt_Expression);
        String cur = curStr.getText().toString();
        if (cur.length() <= 1)
            curStr.setText("0");
        else
            curStr.setText(cur.substring(0, cur.length() - 1));
    }

    public void onAddButtonClick(View view) {
    }

    public void numpadClick(View view) {
        String num = ((TextView)view).getText().toString();
        TextView curStr = (TextView)findViewById(R.id.txt_Expression);
        if (curStr.getText().toString().equals("0") && !num.equals("."))
            curStr.setText(num);
        else
            curStr.append(num);
    }

    public void onConvertClick(View view) {
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
        TextView curStr = (TextView)findViewById(R.id.txt_Expression);
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
}