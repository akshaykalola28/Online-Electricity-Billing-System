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

package com.example.authlibrary.UserInterface.CreateAccount;

import android.text.TextUtils;
import android.util.Patterns;

class AccountInfo {

    private String LastName;
    private String FrstName;
    private String MiddName;
    private String Suffix;
    private String Email;
    private String Password;
    private String cPasswrd;
    private String MobileNo;

    private String message;

    public AccountInfo() {
    }

    public String getMessage() {
        return message;
    }

    public String getFullName(){
        return LastName + ", " +
                "" + FrstName + " " +
                "" + MiddName + " " +
                "" + Suffix;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setFrstName(String frstName) {
        FrstName = frstName;
    }

    public void setMiddName(String middName) {
        MiddName = middName;
    }

    public void setSuffix(String suffix) {
        Suffix = suffix;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setcPasswrd(String cPasswrd) {
        this.cPasswrd = cPasswrd;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public boolean isAccountInfoValid(){
        if(!isLastNameValid()){
            return false;
        }
        if(!isFirstNameValid()){
            return false;
        }
        if(!isMiddNameValid()){
            return false;
        }
        if(!isEmailValid()){
            return false;
        }
        if(!isPasswordValid()){
            return false;
        }
        return isMobileValid();
    }

    private boolean isLastNameValid(){
        if(LastName.trim().isEmpty()){
            message = "Please enter your last name";
            return false;
        }
        return true;
    }

    private boolean isFirstNameValid(){
        if(FrstName.trim().isEmpty()){
            message = "Please enter your first name";
            return false;
        }
        return true;
    }

    private boolean isMiddNameValid(){
        if(MiddName.trim().isEmpty()){
            message = "Please enter your middle name";
            return false;
        }
        return true;
    }

    private boolean isEmailValid(){
        if(Email.trim().isEmpty()){
            message = "Please enter your email";
            return false;
        }
        if(TextUtils.isEmpty(Email) &&
                Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            message = "Please enter valid email";
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(){
        if(Password.trim().isEmpty()){
            message = "Please enter your password";
            return false;
        } else if(!Password.equalsIgnoreCase(cPasswrd)){
            message = "Password does not match";
            return false;
        }
        return true;
    }

    private boolean isMobileValid(){
        if(MobileNo.trim().isEmpty()){
            message = "Please enter mobile number";
            return false;
        } else if(MobileNo.length()!=11){
            message = "Mobile number must be 11 characters";
            return false;
        } else if(!MobileNo.substring(0, 2).equalsIgnoreCase("09")){
            message = "Mobile number must start with '09'";
            return false;
        }
        return true;
    }
}
