package com.aloy.aloy.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aloy.aloy.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Interests extends Fragment {


    public Interests() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View interestView = inflater.inflate(R.layout.fragment_interests, container, false);


        return interestView;
    }

}
