package com.aloy.aloy.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.Models.SearchResult;
import com.aloy.aloy.Presenters.SearchPresenter;
import com.aloy.aloy.R;
import com.aloy.aloy.Util.SpotifyHandler;

import java.util.ArrayList;

/**
 * Created by tldonne on 02/11/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private String[] mData = new String[0];
    private ArrayList<SearchResult> searchResults;
    private LayoutInflater mInflater;
    private SearchPresenter searchPresenter;
    private String searchQuery;
    private Context context;

    // data is passed into the constructor
    public SearchAdapter(Context context, SearchPresenter searchPresenter, String query) {
        this.mInflater = LayoutInflater.from(context);
        this.searchPresenter=searchPresenter;
        this.searchQuery=query;
        this.context=context;
    }


    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.search_result, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        searchPresenter.bindTrack(searchQuery,holder,position,context);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return searchPresenter.getCount();
        //return searchResults.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView primaryText;
        public TextView secondaryText;
        public ImageView cover;

        ViewHolder(View itemView) {
            super(itemView);
            primaryText = (TextView) itemView.findViewById(R.id.primarySearchText);
            secondaryText = (TextView) itemView.findViewById(R.id.secondarySearchText);
            cover = (ImageView) itemView.findViewById(R.id.searchCover);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClick(view, getAdapterPosition());
        }
    }

    

    // Method that executes your code for the action received
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " +  position);
    }


}
