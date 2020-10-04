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

package com.example.authlibrary.UserInterface.ForgotPassword;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class VMForgotPassword extends AndroidViewModel {
    public static final String TAG = VMForgotPassword.class.getSimpleName();

    public VMForgotPassword(@NonNull Application application) {
        super(application);
    }

    public interface RequestPasswordCallback{
        void OnSendRequest(String title, String message);
        void OnSuccessRequest();
        void OnFailedRequest(String message);
    }
}