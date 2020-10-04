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

public class UserAuthInfo {
    private final String Email;
    private final String Password;
    private boolean needMobileNo;
    private String message;

    public UserAuthInfo(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }


    public String getMessage(){
        return message;
    }

    public boolean isAuthInfoValid(){
        if(!isEmailValid()){
            return false;
        }
        if(!isPasswordValid()){
            return false;
        }
        return true;
    }

    private boolean isEmailValid(){
        if(Email.isEmpty()){
            message = "Please enter your email";
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(){
        if(Password.isEmpty()) {
            message = "Please enter password";
            return false;
        }
        return true;
    }

}
