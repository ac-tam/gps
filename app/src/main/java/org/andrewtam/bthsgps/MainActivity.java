package org.andrewtam.bthsgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {
    private String[] classes = new String[10];
    public EditText a1, a2, a3, a4, a5, a6, a7, a8, a9, a10;
    public SharedPreferences data;

    public String getData(int i) {
        return data.getString(i + "", "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data =  PreferenceManager.getDefaultSharedPreferences(this);

        getSupportActionBar().hide();

        LocalTime[] starts = new LocalTime[]{
                LocalTime.of(7, 30), // 0 to 1
                LocalTime.of(8, 20), // 1 to 2
                LocalTime.of(9, 10), // 2 to 3
                LocalTime.of(10, 10), //3 to 4
                LocalTime.of(10, 50), // 4 to 5
                LocalTime.of(11, 30), // 5 to 6
                LocalTime.of(12, 10), //6 to 7
                LocalTime.of(13, 0), // 7 to 8
                LocalTime.of(13, 50), //8 to 9
                LocalTime.of(14, 30), //9 to 10
        };
        int i = 9;
        for (; i > 0; i--)
            if (LocalTime.now().isAfter(starts[i])) {
                i++;
                break;
            }

        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        a4 = findViewById(R.id.a4);
        a5 = findViewById(R.id.a5);
        a6 = findViewById(R.id.a6);
        a7 = findViewById(R.id.a7);
        a8 = findViewById(R.id.a8);
        a9 = findViewById(R.id.a9);
        a10 = findViewById(R.id.a10);


        a1.setText(data.getString("1", ""));
        a2.setText(data.getString("2", ""));
        a3.setText(data.getString("3", ""));
        a4.setText(data.getString("4", ""));
        a5.setText(data.getString("5", ""));
        a6.setText(data.getString("6", ""));
        a7.setText(data.getString("7", ""));
        a8.setText(data.getString("8", ""));
        a9.setText(data.getString("9", ""));
        a10.setText(data.getString("10", ""));


        a1.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        a2.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        a3.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        a4.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        a5.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        a6.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        a7.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        a8.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        a9.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        a10.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

        switch (i) {
            case 1:
                a1.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                break;
            case 2:
                a1.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                a2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                break;
            case 3:
                a2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                a3.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                break;
            case 4:
                a3.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                a4.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                break;
            case 5:
                a4.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                a5.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                break;
            case 6:
                a5.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                a6.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                break;
            case 7:
                a6.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                a7.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                break;
            case 8:
                a7.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                a8.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                break;
            case 9:
                a8.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                a9.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                break;
            case 10:
                a9.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                a10.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                break;
        }



        Button clickButton = findViewById(R.id.button);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isValid(a1.getText().toString()))
                    Toast.makeText(MainActivity.this, "Period 1 Room Invalid", Toast.LENGTH_LONG).show();
                else if (!isValid(a2.getText().toString()))
                    Toast.makeText(MainActivity.this, "Period 2 Room Invalid", Toast.LENGTH_LONG).show();
                else if (!isValid(a3.getText().toString()))
                    Toast.makeText(MainActivity.this, "Period 3 Room Invalid", Toast.LENGTH_LONG).show();
                else if (!isValid(a4.getText().toString()))
                    Toast.makeText(MainActivity.this, "Period 4 Room Invalid", Toast.LENGTH_LONG).show();
                else if (!isValid(a5.getText().toString()))
                    Toast.makeText(MainActivity.this, "Period 5 Room Invalid", Toast.LENGTH_LONG).show();
                else if (!isValid(a6.getText().toString()))
                    Toast.makeText(MainActivity.this, "Period 6 Room Invalid", Toast.LENGTH_LONG).show();
                else if (!isValid(a7.getText().toString()))
                    Toast.makeText(MainActivity.this, "Period 7 Room Invalid", Toast.LENGTH_LONG).show();
                else if (!isValid(a8.getText().toString()))
                    Toast.makeText(MainActivity.this, "Period 8 Room Invalid", Toast.LENGTH_LONG).show();
                else if (!isValid(a9.getText().toString()))
                    Toast.makeText(MainActivity.this, "Period 9 Room Invalid", Toast.LENGTH_LONG).show();
                else if (!isValid(a10.getText().toString()))
                    Toast.makeText(MainActivity.this, "Period 10 Room Invalid", Toast.LENGTH_LONG).show();
                else {
                    data.edit().putString("1", a1.getText().toString()).apply();
                    data.edit().putString("2", a2.getText().toString()).apply();
                    data.edit().putString("3", a3.getText().toString()).apply();
                    data.edit().putString("4", a4.getText().toString()).apply();
                    data.edit().putString("5", a5.getText().toString()).apply();
                    data.edit().putString("6", a6.getText().toString()).apply();
                    data.edit().putString("7", a7.getText().toString()).apply();
                    data.edit().putString("8", a8.getText().toString()).apply();
                    data.edit().putString("9", a9.getText().toString()).apply();
                    data.edit().putString("10", a10.getText().toString()).apply();

                    data.edit().putString("0", "1W13").apply();
                    data.edit().putString("11", "1W13").apply();

                    changeActivity();
                }
            }
        });

    }

    public void changeActivity() {
        Intent myIntent = new Intent(this, Gps.class);

        myIntent.putExtra("1", new String[] {a1.getText().toString(), a2.getText().toString(), a3.getText().toString(), a4.getText().toString(), a5.getText().toString(), a6.getText().toString(), a7.getText().toString(), a8.getText().toString(), a9.getText().toString(), a10.getText().toString()});
        startActivity(myIntent);
    }

    public boolean isValid(String str) {
        switch (str) {
            case "":
            case "LUNCH":
            case "GYM3":
            case "GYM8":
            case "LOCK3":
            case "LOCK8":
            case "LIB":
                return true;
        }

        return isNum(str.substring(0, 1)) && !isNum(str.substring(1, 2)) && isNum(str.substring(2));
    }
    public boolean isNum(String num) {
        for (int i = 0; i < num.length(); i++)
            if (    num.substring(i, i + 1).equals("1") ||
                    num.substring(i, i + 1).equals("2") ||
                    num.substring(i, i + 1).equals("3") ||
                    num.substring(i, i + 1).equals("4") ||
                    num.substring(i, i + 1).equals("5") ||
                    num.substring(i, i + 1).equals("6") ||
                    num.substring(i, i + 1).equals("7") ||
                    num.substring(i, i + 1).equals("8") ||
                    num.substring(i, i + 1).equals("9") ||
                    num.substring(i, i + 1).equals("0")  )
                ;
            else
                return false;

        return true;

    }




}
