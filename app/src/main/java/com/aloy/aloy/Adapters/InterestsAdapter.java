package com.aloy.aloy.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.Views.IndexedFeed;
import com.aloy.aloy.R;
import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.SharedPreferenceHelper;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * Created by madmax on 2017-11-30.
 * Adapter used to bind interests (Cover and title) into a recyclerView for the Interest Tab.
 */

public class InterestsAdapter extends RecyclerView.Adapter<InterestsAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private int itemNb;
    private DataHandler dataHandler;
    private View view;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private FragmentManager fragmentManager;

    public InterestsAdapter(Context context, int itemNb, View view, FragmentManager fragmentManager) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.itemNb = itemNb;
        this.view=view;
        this.fragmentManager=fragmentManager;
        sharedPreferenceHelper=new SharedPreferenceHelper(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.interests_holder, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        dataHandler = new DataHandler(context);
        dataHandler.bindInterests(holder,position,context);
        final String str_position = ""+position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) view.findViewById(R.id.chosenInterest);
                textView.setVisibility(View.VISIBLE);
                textView.setText(dataHandler.NumberToGenre(str_position));
                ViewPager interestsViewPager = (ViewPager) view.findViewById(R.id.interests_view_pager);
                setupViewPager(interestsViewPager, str_position);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager, String category) {
        Bundle interestsArgs = new Bundle();
        interestsArgs.putString("userId", sharedPreferenceHelper.getCurrentUserId());
        interestsArgs.putString("type",category);
        Fragment interests = new IndexedFeed();
        interests.setArguments(interestsArgs);

        SimpleTabAdapter interestsAdapter = new SimpleTabAdapter(fragmentManager);
        interestsAdapter.addFragments(interests,"Interests");
        viewPager.setAdapter(interestsAdapter);
    }

    @Override
    public int getItemCount() {
        return this.itemNb;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView primaryText;
        public TextView secondaryText;
        public ImageView cover;
        public CircularImageView check;

        ViewHolder(View itemView) {
            super(itemView);
            primaryText = (TextView) itemView.findViewById(R.id.interestText);
            secondaryText = (TextView) itemView.findViewById(R.id.secondarySearchText3);
            cover = (ImageView) itemView.findViewById(R.id.interestCover);
            check = (CircularImageView) itemView.findViewById(R.id.selectedTrack2);
        }


    }


    // Method that executes your code for the action received
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + position);
    }

}



