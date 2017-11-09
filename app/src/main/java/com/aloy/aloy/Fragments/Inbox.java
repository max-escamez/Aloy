package com.aloy.aloy.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.aloy.aloy.Adapters.InboxAdapter;
import com.aloy.aloy.R;
import com.github.clans.fab.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class Inbox extends Fragment {

    private InboxAdapter inboxAdapter;

    public Inbox() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);


        ViewPager viewPager = (ViewPager) view.findViewById(R.id.container);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);



        return view;
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        inboxAdapter = new InboxAdapter(getChildFragmentManager());
        inboxAdapter.addFragments(new Following(),"Following");
        inboxAdapter.addFragments(new Requests(),"Requests");
        inboxAdapter.addFragments(new Chat(),"Chat");
        viewPager.setAdapter(inboxAdapter);
    }


}
