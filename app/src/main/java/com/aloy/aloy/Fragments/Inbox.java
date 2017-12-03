package com.aloy.aloy.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aloy.aloy.Adapters.SimpleTabAdapter;
import com.aloy.aloy.R;
import com.aloy.aloy.Util.SharedPreferenceHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class Inbox extends Fragment {

    private SimpleTabAdapter simpleTabAdapter;
    private SharedPreferenceHelper mSharedPreferenceHelper;

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
        View inboxView = inflater.inflate(R.layout.fragment_inbox, container, false);
        mSharedPreferenceHelper = new SharedPreferenceHelper(getContext());

        ViewPager viewPager = (ViewPager) inboxView.findViewById(R.id.inbox_container);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) inboxView.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        return inboxView;
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Bundle followsArgs = new Bundle();
        Bundle requestsArgs = new Bundle();
        followsArgs.putString("userId", mSharedPreferenceHelper.getCurrentUserId());
        followsArgs.putString("type","following");
        requestsArgs.putString("userId", mSharedPreferenceHelper.getCurrentUserId());
        requestsArgs.putString("type","requests");

        Fragment requests = new IndexedFeed();
        Fragment follows = new IndexedFeed();
        requests.setArguments(requestsArgs);
        follows.setArguments(followsArgs);

        simpleTabAdapter = new SimpleTabAdapter(getChildFragmentManager());
        simpleTabAdapter.addFragments(follows,"Following");
        simpleTabAdapter.addFragments(requests,"Requests");
        simpleTabAdapter.addFragments(new Chat(),"Chat");
        viewPager.setAdapter(simpleTabAdapter);

    }


}
