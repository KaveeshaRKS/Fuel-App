package com.example.fuelapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FillingStationLogin extends AppCompatActivity {
    Spinner fuelType;
    EditText etName, etID, etReceivedAmount, etPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filling_station_login);

        etName=(EditText)findViewById(R.id.registeredFillingStationName4);
        etID=(EditText)findViewById(R.id.registeredFillingStationID4);
        etReceivedAmount=(EditText)findViewById(R.id.etReceivedFuelAmount4);
        etPrice=(EditText)findViewById(R.id.etPrice4);

        fuelType=(Spinner)findViewById(R.id.spinnerFuelType4);

        String[] typeOfFuels=getResources().getStringArray(R.array.fuelTypes);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typeOfFuels);

        fuelType.setAdapter(adapter);

        String user=getIntent().getStringExtra("user");

        FuelDB db = new FuelDB(this);//context of the main activity is passing
        db.open();
        String[] details = db.getFillingStation(user);

        etName.setText(details[0]);
        etID.setText(details[1]);

        fuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name=etName.getText().toString();
                String fType=fuelType.getSelectedItem().toString();

                FuelDB db=new FuelDB(getBaseContext());
                db.open();
                String[] fuelDetails=db.getFillingStationFuelDetails(name, fType);

                etReceivedAmount.setText(fuelDetails[0]);
                etPrice.setText(fuelDetails[1]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
