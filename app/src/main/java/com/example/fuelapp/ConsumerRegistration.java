package com.example.fuelapp;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ConsumerRegistration extends AppCompatActivity {
    Button addVehicles;
    EditText name, address, district, password, confirmPassword;
    long recordValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consumer_registration);

        addVehicles=(Button)findViewById(R.id.addVehiclesOrMachines);
        name=(EditText)findViewById(R.id.consumerName);
        address=(EditText)findViewById(R.id.address);
        district=(EditText)findViewById(R.id.consumerDistrict);
        password=(EditText)findViewById(R.id.consumerPassword);
        confirmPassword=(EditText)findViewById(R.id.consumerPasswordConfirmed);

        addVehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getBaseContext(), ConsumerRegistrationAddVehicle.class);
                startActivity(i);
            }
        });
    }
    public void btnSave(View v)
    {
        String consumerName = name.getText().toString();
        String consumerAddress = address.getText().toString();
        String consumerDistrict = district.getText().toString();
        String consumerPassword = password.getText().toString();

        FuelDB db = new FuelDB( this);//context of the main activity is passing
        db.open();
        String results=db.getUserAddress(consumerAddress);
        if (results.equals("")){
            recordValue = db.createEntry(consumerName, consumerAddress, consumerDistrict, consumerPassword);
            Toast.makeText(this, "Saved Successfully: Record Number" + recordValue , Toast.LENGTH_LONG).show();
            clearFields();
        }else{
            Toast.makeText(this, "This address already exists!", Toast.LENGTH_LONG).show();
        }
        db.close();
    }
    public void clearFields(){
        name.setText("");
        address.setText("");
        district.setText("");
        password.setText("");
        confirmPassword.setText("");
    }
}
