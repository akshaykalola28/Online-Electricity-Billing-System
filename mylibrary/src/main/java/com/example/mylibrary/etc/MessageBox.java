/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package com.example.mylibrary.etc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.mylibrary.R;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class  MessageBox {
    private AlertDialog poDialogx;
    private MaterialButton btnPositive;
    private MaterialButton btnNegative;
    private TextView lblTitle;
    private TextView lblMsgxx;
    private View midBorder;

    private final Context context;

    public MessageBox(Context context){
        // Must be, at all times, pass Activity Context.
        this.context = Objects.requireNonNull(context);
    }
    public void initDialog(){
        AlertDialog.Builder poBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_message_box, null);
        poBuilder.setCancelable(false)
                .setView(view);
        poDialogx = poBuilder.create();
        poDialogx.setCancelable(false);

        lblTitle = view.findViewById(R.id.lbl_dialogTitle);
        lblMsgxx = view.findViewById(R.id.lbl_dialogMessage);
        midBorder = view.findViewById(R.id.view_midBorder);
        btnPositive = view.findViewById(R.id.btn_dialogPositive);
        btnPositive.setVisibility(View.GONE);
        btnNegative = view.findViewById(R.id.btn_dialogNegative);
        midBorder.setVisibility(View.GONE);
        btnNegative.setVisibility(View.GONE);
    }

    public void setMessage(String psMessage) {
        try {
            lblMsgxx.setText(Objects.requireNonNull(psMessage));
        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    public void setTitle(String psTitlexx) {
        try {
            lblTitle.setText(Objects.requireNonNull(psTitlexx));
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void setPositiveButton(String psBtnPost, final DialogButton listener) {
        btnPositive.setVisibility(View.VISIBLE);
        btnPositive.setText(psBtnPost);
        btnPositive.setOnClickListener(view -> {
            listener.OnButtonClick(view, poDialogx);
//            isDialogShown = false;
        });
    }

    public void setNegativeButton(String psBtnNegt, final DialogButton listener) {
        midBorder.setVisibility(View.VISIBLE);
        btnNegative.setVisibility(View.VISIBLE);
        btnNegative.setText(psBtnNegt);
        btnNegative.setOnClickListener(view -> {
            listener.OnButtonClick(view, poDialogx);
//            isDialogShown = false;
        });
    }

    public void show() {
        if(!poDialogx.isShowing()) {
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.getWindow().getAttributes().windowAnimations = R.style.PopupAnimation;
            try {
                if (!((Activity) context).isFinishing()) {
                    poDialogx.show();
//                    isDialogShown = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface DialogButton{
        void OnButtonClick(View view, AlertDialog dialog);
    }
}

