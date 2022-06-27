/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.authLibrary
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */
package com.example.authlibrary.UserInterface.Login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class VMLogin extends AndroidViewModel {
    public static final String TAG =  VMLogin.class.getSimpleName();
    private final Application application;

    public VMLogin(@NonNull Application application) {
        super(application);
        this.application =application;
    }
}