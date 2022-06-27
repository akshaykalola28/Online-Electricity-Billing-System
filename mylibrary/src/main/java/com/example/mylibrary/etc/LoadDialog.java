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

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mylibrary.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

public class LoadDialog {
    private AlertDialog poDialogx;
    private final Context context;
    private static boolean isShown;
    public LoadDialog(Context context) {
        this.context = context;
    }

    public void initDialog(String Title, String Message, boolean Cancellable){
        AlertDialog.Builder poBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
        poBuilder.setCancelable(false)
                .setView(view);
        poDialogx = poBuilder.create();
        poDialogx.setCancelable(Cancellable);
        ProgressBar progressBar = view.findViewById(R.id.progress_loading);
        TextView lblTitle = view.findViewById(R.id.lbl_dialogTitle);
        lblTitle.setText(Title);
        TextView lblMsgxx = view.findViewById(R.id.lbl_dialogMessage);
        lblMsgxx.setText(Message);
        Sprite loDrawable = new Circle();
        loDrawable.setColor(context.getResources().getColor(R.color.progressDialogTint));
        progressBar.setIndeterminateDrawable(loDrawable);
    }

    public void show() {
        if(!isShown) {
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.getWindow().getAttributes().windowAnimations = R.style.PopupAnimation;
            poDialogx.show();
            isShown = true;
        }
    }

    public void dismiss(){
        if(poDialogx != null && poDialogx.isShowing()){
            poDialogx.dismiss();
            isShown = false;
        }
    }
}

