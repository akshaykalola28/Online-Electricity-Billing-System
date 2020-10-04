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

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.authlibrary.R;
import com.example.mylibrary.etc.AppConfigPreference;
import com.example.mylibrary.etc.LoadDialog;
import com.example.mylibrary.etc.MessageBox;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class Fragment_ForgotPassword extends Fragment implements VMForgotPassword.RequestPasswordCallback {

    private VMForgotPassword mViewModel;

    private TextInputEditText tieEmail;
    private MaterialButton btnSendEmail;
    private LoadDialog poDialog;
    private MessageBox poMsgBox;

    private AppConfigPreference poConfigx;

    public static Fragment_ForgotPassword newInstance() {
        return new Fragment_ForgotPassword();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        poDialog = new LoadDialog(getActivity());
        poMsgBox = new MessageBox(getActivity());
        poConfigx = AppConfigPreference.getInstance(getActivity());
        tieEmail = v.findViewById(R.id.tie_fp_Email);
        btnSendEmail = v.findViewById(R.id.btn_sendEmail);

        return v;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            mViewModel = new ViewModelProvider(this).get(VMForgotPassword.class);
            btnSendEmail.setOnClickListener(view -> {
                String email = Objects.requireNonNull(tieEmail.getText()).toString().trim();

            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void OnSendRequest(String title, String message) {
        poDialog.initDialog(title, message, false);
        poDialog.show();
    }

    @Override
    public void OnSuccessRequest() {
        poDialog.dismiss();
        poMsgBox.initDialog();
        poMsgBox.setTitle("Forgot Password");
        poMsgBox.setMessage("You'll be receiving an email from MIS, Please check your email account");
        poMsgBox.setPositiveButton("Okay", (view, msgDialog) -> msgDialog.dismiss());
        poMsgBox.show();
    }

    @Override
    public void OnFailedRequest(String message) {
        poDialog.dismiss();
        poMsgBox.initDialog();
        poMsgBox.setTitle("Forgot Password");
        poMsgBox.setMessage(message);
        poMsgBox.setPositiveButton("Okay", (view, msgDialog) -> msgDialog.dismiss());
        poMsgBox.show();
    }
}