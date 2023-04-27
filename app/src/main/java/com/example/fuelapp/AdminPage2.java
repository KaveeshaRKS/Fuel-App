package com.example.fuelapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminPage2 extends AppCompatActivity {
    Spinner stationName, fuleTypes;
    EditText receivedAmount, price;
    Button update;
    long recordValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_page2);

        stationName=(Spinner)findViewById(R.id.spinner_station_name);
        fuleTypes=(Spinner)findViewById(R.id.spinner_fuel_type);
        receivedAmount=(EditText)findViewById(R.id.etReceivedFuelAmount);
        price=(EditText)findViewById(R.id.etPriceOf1L);
        update=(Button)findViewById(R.id.btn_update);

        FuelDB db = new FuelDB( getBaseContext());//context of the main activity is passing
        db.open();
        String[] stations=db.loadRegisterdFillingStations();

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stations);

        stationName.setAdapter(adapter);

        String[] typeOfFuels=getResources().getStringArray(R.array.fuelTypes);

        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typeOfFuels);

        fuleTypes.setAdapter(adapter2);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fillingStationName=stationName.getSelectedItem().toString();
                String fuelType=fuleTypes.getSelectedItem().toString();
                int amount=Integer.parseInt(receivedAmount.getText().toString());
                int priceOfFuel=Integer.parseInt(price.getText().toString());

                FuelDB db = new FuelDB( getBaseContext());//context of the main activity is passing
                db.open();
                recordValue=db.createEntry6(fillingStationName, fuelType, amount, priceOfFuel);
                if (recordValue==0){
                    Toast.makeText(AdminPage2.this, "Updated the record!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(), "Saved Successfully: Record Number" + recordValue , Toast.LENGTH_LONG).show();
                }
                clearField();
                db.close();
            }
        });
    }
    public void clearField(){
        receivedAmount.setText("");
        price.setText("");
    }
}
