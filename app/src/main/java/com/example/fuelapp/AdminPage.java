package com.example.fuelapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPage extends AppCompatActivity {
    Button add, remove, details, manage;
    EditText fname, fid, cAddress, receivedFuelAmount, price;

    long recordValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_page);

        add=(Button) findViewById(R.id.addBtn);
        manage=(Button)findViewById(R.id.btnManageFuel);
        fname=(EditText)findViewById(R.id.registeredFillingStationName);
        fid=(EditText)findViewById(R.id.registeredFillingStationID);
        remove=(Button)findViewById(R.id.removeBtn);
        cAddress=(EditText)findViewById(R.id.etConsumer_sAddress);
        details=(Button) findViewById(R.id.btn_satation_details);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName=fname.getText().toString();
                String fID=fid.getText().toString();

                FuelDB db = new FuelDB(getBaseContext());//context of the main activity is passing
                db.open();
                recordValue = db.createEntry5(fName, fID);
                db.close();
                Toast.makeText(getBaseContext(), "Saved Successfully: Record Number" + recordValue , Toast.LENGTH_LONG).show();
                clearField();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addr=cAddress.getText().toString();

                FuelDB db = new FuelDB( getBaseContext());//context of the main activity is passing
                db.open();
                db.removeUser(addr);
                Toast.makeText(AdminPage.this, "Successfully removed the suspicious user!", Toast.LENGTH_LONG).show();
                clearField();
                db.close();
            }
        });
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), AdminPage2.class);
                startActivity(i);
            }
        });
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), AdminManageRequiredFuelPerMonth.class);
                startActivity(i);
            }
        });
    }
    public void clearField(){
        fname.setText("");
        fid.setText("");
        cAddress.setText("");
    }
}