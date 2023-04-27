package com.example.fuelapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ConsumerRegistrationAddVehicle extends AppCompatActivity {
    Button addVehicle;
    EditText vehicleName, password;
    EditText vehicleID;
    long recordValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consumer_registration_add_vehicle);

        addVehicle=(Button) findViewById(R.id.add_Vehicle);
        vehicleName=(EditText)findViewById(R.id.vehicleNameAndType);
        vehicleID=(EditText)findViewById(R.id.vehicleOrMachineID);
        password=(EditText)findViewById(R.id.password);

        addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String consumerPassword = password.getText().toString();
                String nameOfVehicles = vehicleName.getText().toString();
                String idOfVehicle = vehicleID.getText().toString();

                FuelDB db = new FuelDB(getBaseContext());//context of the main activity is passing
                db.open();
                recordValue = db.createEntry2(consumerPassword, nameOfVehicles, idOfVehicle);
                db.close();
                Toast.makeText(getBaseContext(), "Saved Successfully: Record Number" + recordValue , Toast.LENGTH_LONG).show();
                clearField();
            }
        });
    }
    public void clearField(){
        vehicleName.setText("");
        vehicleID.setText("");
    }
}
