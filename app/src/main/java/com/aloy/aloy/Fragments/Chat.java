package com.aloy.aloy.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aloy.aloy.R;

/**
 * Created by Max on 08/11/2017.
 */

public class Chat extends Fragment {
    private static final String TAG = "Chat";
    private Button test;

    public Chat() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        test =(Button) view.findViewById(R.id.chatButton);
        return view;
    }
}
