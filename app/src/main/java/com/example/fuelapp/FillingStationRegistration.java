package com.example.fuelapp;

import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FillingStationRegistration extends AppCompatActivity {
    EditText username, district, id, password, confirmPassword;
    Button save;
    long recordValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filling_station_registration);

        username=(EditText) findViewById(R.id.etUsername);
        district=(EditText) findViewById(R.id.etDistrict);
        id=(EditText) findViewById(R.id.etRegisteredID);
        password=(EditText) findViewById(R.id.pwfPassword);
        confirmPassword=(EditText) findViewById(R.id.pwfConfirmPassword);
        save=(Button) findViewById(R.id.btnSave);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=username.getText().toString();
                String dist=district.getText().toString();
                String registeredID=id.getText().toString();
                String pass=password.getText().toString();

                FuelDB db = new FuelDB(getBaseContext());//context of the main activity is passing
                db.open();
                String results=db.getRightFillingStation(name, registeredID);

                Log.i("NAME", results);


                if(results.equals("OK")){
                    recordValue = db.createEntry3(name, dist, registeredID, pass);
                    Toast.makeText(getBaseContext(), "Saved Successfully: Record Number" + recordValue, Toast.LENGTH_LONG).show();
                    clearField();
                }else{
                    Toast.makeText(FillingStationRegistration.this, "Check with the Admin.", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        });
    }
    public void clearField(){
        username.setText("");
        district.setText("");
        id.setText("");
        password.setText("");
        confirmPassword.setText("");
    }
}
