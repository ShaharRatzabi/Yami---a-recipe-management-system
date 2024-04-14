package com.example.delicious.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.delicious.R;
import com.example.delicious.models.Recipe;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public static Recipe currentRecipe = new Recipe();;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

    }



}
