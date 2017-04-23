package com.benjamin.smartgarden;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Target;


public class MainActivity extends AppCompatActivity {
    int gardens = 0;
    int pots = 0;
    String x;
    String[] actions = {"Add Pot", "Add Garden"};
    ArrayAdapter<String> adapter;
    boolean didPress = false;
    public static final int PERMISSION_ASK = 1001;

    // ADD A TEXTFIELD ON A BUTTON SO THE USER CAN INPUT A NAME
    Button b1;
    Button b2;
    Button b5;
    Button b6;
    Button b3;
    Button b4;
    Button b8;
    Button b7;
    Button b9;
    EditText e2;
    EditText input;
    String m_Text;
    TextView t;
    Intent i;
    Intent i2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());

        //garden button
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);

        //pot buttons
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b8 = (Button) findViewById(R.id.button8);
        b7 = (Button) findViewById(R.id.button7);
        t = (TextView) findViewById(R.id.textView);

        //bluetooth button



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return (true);
    }

    public void createGardenDialog(String gp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name your " + gp);

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                if (gardens == 0) {
                    b1.setVisibility(View.VISIBLE);
                    b1.setText(m_Text);
                    gardens +=1;
                }

                else if (gardens == 1){
                    b2.setVisibility(View.VISIBLE);
                    b2.setText(m_Text);
                    gardens +=1;
                }

                else if (gardens == 2){
                    b5.setVisibility(View.VISIBLE);
                    b5.setText(m_Text);
                    gardens +=1;
                }
                else if (gardens == 3)
                {
                    b6.setVisibility(View.VISIBLE);
                    b6.setText(m_Text);
                    gardens +=1;
                }

                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "No more space!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void createPotDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name your pot: ");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                if (pots == 0) {
                    b3.setVisibility(View.VISIBLE);
                    b3.setText(m_Text);
                    pots +=1;
                }

                else if (pots == 1){
                    b8.setVisibility(View.VISIBLE);
                    b8.setText(m_Text);
                    pots +=1;
                }

                else if (pots == 2){
                    b4.setVisibility(View.VISIBLE);
                    b4.setText(m_Text);
                    pots +=1;
                }
                else if (pots == 3)
                {
                    b7.setVisibility(View.VISIBLE);
                    b7.setText(m_Text);
                    pots +=1;
                }

                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "No more space!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }


    public void createPot(MenuItem item) {
        createPotDialog();
    }

    public void createGarden(MenuItem item) {
            createGardenDialog("garden");
        }

    public void ViewData(View v){
        i=new Intent(MainActivity.this, Main2Activity.class);
        x = "1";
        i.putExtra("1", x);
        startActivity(i);


    }

    public void ViewData2(View v){
        i2=new Intent(MainActivity.this, Main2Activity.class);
        x = "2";
        i2.putExtra("1", x);
        startActivity(i2);


    }





    }