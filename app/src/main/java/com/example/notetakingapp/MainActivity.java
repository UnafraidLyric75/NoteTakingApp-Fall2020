package com.example.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonIsClicked(View v){
        // Get button id which should be note title
        // Then open up the note in note edit fragment to edit or read note
        // nothing more should happen here.
    }

    public void addOnClick(View v){
        // when add button is clicked create new object of note
        // open black note fragment to be edited
        // and add object to bottom of note scroll page save with UntiledNote[x]
    }

    public void saveOnClick(View v){
        // uopn backing out or clicking the save button
        // note shall be saved in full updating title and main body
    }
}