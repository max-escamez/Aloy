package com.aloy.aloy.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.Presenters.SearchPresenter;
import com.aloy.aloy.R;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * Created by tldonne on 02/11/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private SearchPresenter searchPresenter;
    private String searchQuery;
    private Context context;
    private String type;
    private int itemNb;



    // data is passed into the constructor
    public SearchAdapter(Context context, SearchPresenter searchPresenter, String query, String type,int itemNb) {
        this.mInflater = LayoutInflater.from(context);
        this.searchPresenter = searchPresenter;
        this.searchQuery = query;
        this.context = context;
        this.type = type;
        this.itemNb=itemNb;
        this.searchQuery=query;
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
        searchPresenter.bind(searchQuery,holder,position,context,type,this);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return this.itemNb;
    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView primaryText;
        public TextView secondaryText;
        public ImageView cover;
        public CircularImageView check;

        ViewHolder(View itemView) {
            super(itemView);
            primaryText = (TextView) itemView.findViewById(R.id.primarySearchText);
            secondaryText = (TextView) itemView.findViewById(R.id.secondarySearchText);
            cover = (ImageView) itemView.findViewById(R.id.searchCover);
            check = (CircularImageView) itemView.findViewById(R.id.selectedTrack);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClick(view, getAdapterPosition());
            if (check.getVisibility() == View.INVISIBLE) {
                check.setVisibility(View.VISIBLE);
                searchPresenter.addItem(type,getAdapterPosition(), searchQuery);
            }
            else {
                check.setVisibility(View.INVISIBLE);
                searchPresenter.removeItem(type,getAdapterPosition(),searchQuery);
            }
        }
    }


    // Method that executes your code for the action received
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " +  position);
    }




}
