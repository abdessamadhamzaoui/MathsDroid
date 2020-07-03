package com.example.mathsdroid;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class MathsDroid extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
