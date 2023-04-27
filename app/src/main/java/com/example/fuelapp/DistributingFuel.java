package com.example.fuelapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;

public class DistributingFuel extends AppCompatActivity {
    Spinner vehiclesAndMachines;
    TextView amount;
    String password, name;
    Button request;
    long millis=0, millis2=0;
    static final int READ_BLOCK_SIZE=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distributing_fuel);

        amount=(TextView) findViewById(R.id.tvAmount);
        vehiclesAndMachines=(Spinner) findViewById(R.id.spinnerVehicles);
        request=(Button)findViewById(R.id.btnRequest);

        password = getIntent().getStringExtra("pwf");
        name = getIntent().getStringExtra("name");

        FuelDB db = new FuelDB(getBaseContext());//context of the main activity is passing
        db.open();
        String[] result = db.loadVehicles(password);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, result);

        vehiclesAndMachines.setAdapter(adapter);

        vehiclesAndMachines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item=adapterView.getSelectedItem().toString();

                FuelDB db = new FuelDB(getBaseContext());//context of the main activity is passing
                db.open();
                int result = db.allocatedFuelAmount(item);

                FuelDB db1 = new FuelDB(getBaseContext());//context of the main activity is passing
                db1.open();
                String type = db1.allocatedFuelType(item);

                FuelDB db2 = new FuelDB(getBaseContext());//context of the main activity is passing
                db2.open();
                int result2 = db2.existingFuelAmount(name, type);

                if (result <= result2){
                    String fAmount=Integer.toString(result);
                    amount.setText(fAmount);
                }
                else {
                    amount.setText("No Enough Fuel");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        try {
            FileInputStream fIn=openFileInput("textfile.txt");
            InputStreamReader isr=new InputStreamReader(fIn);

            char[] inputBuffer=new char[READ_BLOCK_SIZE];
            String s="";
            int charRead;

            try {
                while((charRead = isr.read(inputBuffer))>0){
                    String readString = String.copyValueOf(inputBuffer, 0, charRead);
                    s+=readString;
                    inputBuffer=new char[READ_BLOCK_SIZE];
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            millis2=Long.parseLong(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (millis2!=0){
            BigInteger reallyBig = new BigInteger("2592000000");
            long time=reallyBig.longValue();
            long currentTime=System.currentTimeMillis();
            long time2=currentTime-millis2;
            long time3=time-time2;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    request.setEnabled(false);
                }
            }, time3);
        }

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (String w : result){
                    FuelDB db3 = new FuelDB(getBaseContext());//context of the main activity is passing
                    db3.open();
                    int result3 = db3.allocatedFuelAmount(w);

                    FuelDB db1 = new FuelDB(getBaseContext());//context of the main activity is passing
                    db1.open();
                    String type = db1.allocatedFuelType(w);

                    FuelDB db2 = new FuelDB(getBaseContext());//context of the main activity is passing
                    db2.open();
                    int result2 = db2.existingFuelAmount(name, type);

                    if (result3 <= result2){
                        int newAmount=result2-result3;

                        FuelDB db4 = new FuelDB(getBaseContext());//context of the main activity is passing
                        db4.open();
                        long record=db4.updateExistingFuelAmount(name, type, newAmount);

                        if (record==1){
                            Toast.makeText(getBaseContext(), "Updated the database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                request.setEnabled(false);
                millis = System.currentTimeMillis();
                String str=String.valueOf(millis);

                try {
                    FileOutputStream fout=openFileOutput("textfile.txt", MODE_PRIVATE);
                    OutputStreamWriter osw=new OutputStreamWriter(fout);
                    try {
                        osw.write(str);

                        osw.flush();
                        osw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
