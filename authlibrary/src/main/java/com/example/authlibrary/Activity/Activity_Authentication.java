package com.example.authlibrary.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authlibrary.R;
import com.example.mylibrary.etc.TransparentToolbar;

public class Activity_Authentication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        new TransparentToolbar(Activity_Authentication.this).SetupActionbar();
    }
}