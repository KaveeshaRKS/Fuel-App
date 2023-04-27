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

public class AdminManageRequiredFuelPerMonth extends AppCompatActivity {
    EditText vehicle, amount;
    Spinner fuelTypes;
    Button addUpdate;
    long recordValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_required_fuel_per_month);

        vehicle=(EditText) findViewById(R.id.etVehicleType);
        amount=(EditText) findViewById(R.id.etFuelAmount);
        fuelTypes=(Spinner) findViewById(R.id.spinnerFuelType);
        addUpdate=(Button) findViewById(R.id.btn_add_update);

        String[] typeOfFuels=getResources().getStringArray(R.array.fuelTypes);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typeOfFuels);

        fuelTypes.setAdapter(adapter);

        addUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehicleNameAndType=vehicle.getText().toString();
                String fuelType=fuelTypes.getSelectedItem().toString();
                int fuelAmount=Integer.parseInt(amount.getText().toString());

                FuelDB db = new FuelDB( getBaseContext());//context of the main activity is passing
                db.open();
                recordValue=db.createEntry7(vehicleNameAndType, fuelType, fuelAmount);
                if (recordValue==0){
                    Toast.makeText(AdminManageRequiredFuelPerMonth.this, "Updated the record!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(), "Saved Successfully: Record Number" + recordValue , Toast.LENGTH_LONG).show();
                }
                clearField();
                db.close();
            }
        });
    }
    public void clearField(){
        vehicle.setText("");
        amount.setText("");
    }
}
