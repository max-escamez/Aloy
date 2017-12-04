package com.aloy.aloy.Views;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aloy.aloy.Adapters.InterestsAdapter;
import com.aloy.aloy.Contracts.InterestsContract;
import com.aloy.aloy.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Interests extends Fragment implements InterestsContract.View {


    public Interests() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View interestView = inflater.inflate(R.layout.fragment_interests, container, false);
        setupRecyclerView(interestView);
        return interestView;
    }


    @Override
    public void setupRecyclerView(View interestView) {
        RecyclerView recyclerView = (RecyclerView) interestView.findViewById(R.id.searchGridInterest);
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            //recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3,GridLayoutManager.HORIZONTAL,false));
        }
        else{
            //recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5,GridLayoutManager.HORIZONTAL,false));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        InterestsAdapter interestsAdapter = new InterestsAdapter(getContext(), 26,interestView,getChildFragmentManager());
        recyclerView.setAdapter(interestsAdapter);
    }

}
