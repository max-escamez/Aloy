package com.aloy.aloy.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aloy.aloy.Contracts.QuestionDetailsContract;
import com.aloy.aloy.Models.SearchResult;
import com.aloy.aloy.R;

/**
 * Created by tldonne on 12/11/2017.
 */

public class CoverFlowAdapter extends RecyclerView.Adapter<SearchResult>{

    private Context context;

    public CoverFlowAdapter(Context context){
        this.context=context;

    }


    @Override
    public SearchResult onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new SearchResult(view);
    }

    @Override
    public void onBindViewHolder(SearchResult holder, int position) {
        holder.getPrimaryText().setText("Primary");
        holder.getSecondaryText().setText("Secondary");



    }

    @Override
    public int getItemCount() {

        return 5;
    }
}
