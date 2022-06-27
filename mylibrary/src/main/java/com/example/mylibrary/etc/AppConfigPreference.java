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

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AppConfigPreference {
    private static final String TAG = AppConfigPreference.class.getSimpleName();

    private final SharedPreferences pref;

    private final SharedPreferences.Editor editor;

    private static final String CONFIG_NAME = "OEBS_AndroidLocalConfig";
    private static final String AppServer = "ApplicationServer";
    private static final String temp_PIN = "ConfirmationPIN";
    private static final String APP_FIREBASE_TOKEN = "Firebase_Token";

    private static boolean isAgreedOnTerms = false;
    private static final String Application_Agreement = "OEBS_TermAndConditions";
    private static AppConfigPreference mAppConfigPreference;

    private AppConfigPreference(Context context){
        int priv_Mode = 0;
        pref = context.getSharedPreferences(CONFIG_NAME, priv_Mode);
        editor = pref.edit();
    }

    public static AppConfigPreference getInstance(Context context){
        if(mAppConfigPreference == null){
            mAppConfigPreference = new AppConfigPreference(context);
        }
        return mAppConfigPreference;
    }

    public void setAppServer(String LocalDataServer){
        editor.putString(AppServer, LocalDataServer);
        if(editor.commit()){
            Log.e(TAG, "Server has been set to localhost");
        }
    }

    /**
     *
     * @return Default return value https://restgk.guanzongroup.com.ph/
     * live data address
     */
    public String getAppServer(){
        return pref.getString(AppServer, "https://restgk.guanzongroup.com.ph/");
//        return pref.getString(AppServer, "http://192.168.10.141/");
    }



    public void setTemp_PIN(String PIN){
        editor.putString(temp_PIN, PIN);
        editor.commit();
    }

    public String getPIN(){
        return pref.getString(temp_PIN, "");
    }

    public void setAppToken(String AppToken){
        editor.putString(APP_FIREBASE_TOKEN, AppToken);
        editor.commit();
    }

    public String getAppToken(){
        return pref.getString(APP_FIREBASE_TOKEN, "");
    }


    public void setAgreement(boolean isAgree){
        editor.putBoolean(Application_Agreement, isAgree);
        editor.commit();
    }

    public boolean isAgreedOnTermsAndConditions(){
        return pref.getBoolean(Application_Agreement, false);
    }

    public boolean isAgreedOnTerms() {
        return isAgreedOnTerms;
    }

    public void setIsAgreedOnTerms(boolean isAgreed) {
        this.isAgreedOnTerms = isAgreed;
    }


}
