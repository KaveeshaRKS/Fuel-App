package com.example.fuelapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class WhoYouAreDialogBox extends DialogFragment {

    static WhoYouAreDialogBox newInstace(String q){
        WhoYouAreDialogBox fragment=new WhoYouAreDialogBox();
        Bundle args=new Bundle();
        args.putString("title", q);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        return new AlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(title)
                .setPositiveButton("Fuel Consumer",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MainActivity) getActivity()).clickedOnFuelConsumer();
                            }
                        })
                .setNegativeButton("Filling Station",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MainActivity) getActivity()).clickedOnFillingStation();
                            }
                        }).create();
    }
}