/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.authLibrary
 * Electronic Personnel Access Control Security System
 * project file created : 6/10/21 2:53 PM
 * project file last modified : 6/10/21 2:53 PM
 */

package com.example.authlibrary.UserInterface.Terms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.authlibrary.R;


public class Fragment_TermsAndConditions extends Fragment {


    public Fragment_TermsAndConditions() {
        // Required empty public constructor
    }


    public static Fragment_TermsAndConditions newInstance() {
        Fragment_TermsAndConditions fragment = new Fragment_TermsAndConditions();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);

        initViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initViews(View v) {

    }
}