package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button register;
    EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register=(Button)findViewById(R.id.register);
        username=(EditText)findViewById(R.id.etUsernameLogin);
        password=(EditText)findViewById(R.id.etPasswordLogin);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhoYouAreDialogBox dialogBox=WhoYouAreDialogBox.newInstace("You are a, ");
                dialogBox.show(getSupportFragmentManager(), "dialog");
            }
        });
    }
    public void clickedOnFuelConsumer(){
        Intent i=new Intent(this, ConsumerRegistration.class);
        startActivity(i);
    }
    public void clickedOnFillingStation(){
        Intent i=new Intent(this, FillingStationRegistration.class);
        startActivity(i);
    }

    public void adminLoginOnClick(View view) {
        String uName=username.getText().toString();
        String pwf=password.getText().toString();

        String result="";

        FuelDB db = new FuelDB( this);//context of the main activity is passing
        db.open();
        result=db.getAdminCredentials(uName, pwf);
        db.close();

        if (result.equals("OK")){
            clearFields();
            Intent i=new Intent(this, AdminPage.class);
            startActivity(i);
        }
        else{
            Toast.makeText(this, "Check Admin Credentials!", Toast.LENGTH_LONG).show();
            clearFields();
        }
    }
    public void clearFields(){
        username.setText("");
        password.setText("");
    }
    public void loginBtnClicked(View v) {
        String uName = username.getText().toString();
        String pwf = password.getText().toString();

        String result = "";

        FuelDB db = new FuelDB(this);//context of the main activity is passing
        db.open();
        result = db.getFillingStationCredentials(uName, pwf);

        if (result.equals("OK")) {
            clearFields();
            String[] details=new String[2];
            Intent i = new Intent("com.example.fuelapp.FillingStationLogin");
            i.putExtra("user", uName);
            startActivity(i);
        } else {
            result = db.getConsumerCredentials(uName, pwf);
            if (result.equals("OK")) {
                clearFields();
                String district=db.getConsumerDistrict(uName, pwf);
                Intent i = new Intent(this, MapsActivity.class);
                i.putExtra("dis", district);
                i.putExtra("password", pwf);
                startActivity(i);
            } else {
                clearFields();
                Toast.makeText(this, "Please Check Your Credentials", Toast.LENGTH_SHORT).show();
            }
        }
        db.close();
    }
}