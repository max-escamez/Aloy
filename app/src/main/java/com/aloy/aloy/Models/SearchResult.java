package com.aloy.aloy.Models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.R;

/**
 * Created by tldonne on 02/11/2017.
 */

public class SearchResult extends RecyclerView.ViewHolder {

    private ImageView cover;
    private TextView primaryText;
    private TextView secondaryText;

    public SearchResult(View itemView) {
        super(itemView);
        this.cover = (ImageView) itemView.findViewById(R.id.imageView2);
        this.primaryText = (TextView) itemView.findViewById(R.id.textView2);
        this.secondaryText = (TextView) itemView.findViewById(R.id.textView3);
    }

    public ImageView getCover() {
        return cover;
    }

    public TextView getPrimaryText() {
        return primaryText;
    }

    public TextView getSecondaryText() {
        return secondaryText;
    }
}
