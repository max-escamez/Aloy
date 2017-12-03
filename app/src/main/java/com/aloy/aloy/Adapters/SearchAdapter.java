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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tldonne on 02/11/2017.
 * Adapter used to bind search results from the Spotify Api (Cover, name, artist, album)
 * into a recyclerView for the Search DialogFragment.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private SearchPresenter searchPresenter;
    private String searchQuery;
    private Context context;
    private String type;
    private int itemNb;
    private List<Integer> selectedItems;



    // data is passed into the constructor
    public SearchAdapter(Context context, SearchPresenter searchPresenter, String query, String type,int itemNb) {
        this.mInflater = LayoutInflater.from(context);
        this.searchPresenter = searchPresenter;
        this.searchQuery = query;
        this.context = context;
        this.type = type;
        this.itemNb=itemNb;
        this.searchQuery=query;
        selectedItems = new ArrayList<>();
    }


    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.search_result, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        searchPresenter.bind(searchQuery,holder,position,context,type,this);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v, getItemViewType(holder.getAdapterPosition()));
                if (!selectedItems.contains(getItemViewType(holder.getAdapterPosition()))) {
                    holder.check.setVisibility(View.VISIBLE);
                    searchPresenter.addItem(type,getItemViewType(holder.getAdapterPosition()), searchQuery);
                    selectedItems.add(getItemViewType(holder.getAdapterPosition()));
                }
                else {
                    holder.check.setVisibility(View.INVISIBLE);
                    searchPresenter.removeItem(type,getItemViewType(holder.getAdapterPosition()),searchQuery);
                    selectedItems.remove(Integer.valueOf(getItemViewType(holder.getAdapterPosition())));
                }
            }
        });
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return this.itemNb;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
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
        }


    }

    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " +  position);
    }




}
