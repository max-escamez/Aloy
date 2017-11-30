package com.aloy.aloy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.MainActivity;
import com.aloy.aloy.Models.Answer;
import com.aloy.aloy.Models.SpotifyItem;
import com.aloy.aloy.R;
import com.firebase.ui.database.*;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

/**
 * Created by tldonne on 12/11/2017.
 */

public class CoverFlowAdapter {

    private Context context;
    private String questionId;
    private String answerId;
    private int itemCount;
    private static FirebaseRecyclerOptions<SpotifyItem> options;
    private DatabaseReference entry;


    public CoverFlowAdapter(Context context, DatabaseReference reference, AppCompatActivity activity){
        options = new FirebaseRecyclerOptions.Builder<SpotifyItem>()
                .setQuery(reference.child("items"), SpotifyItem.class)
                .setLifecycleOwner(activity)
                .build();
        this.context=context;
        this.entry=reference;
    }

    public RecyclerView.Adapter getAdapter() {
        return new com.firebase.ui.database.FirebaseRecyclerAdapter<SpotifyItem, SpotifyItemHolder>(options) {

            @Override
            public SpotifyItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.spotify_item, parent, false);
                return new SpotifyItemHolder(view);
            }

            @Override
            protected void onBindViewHolder(SpotifyItemHolder holder, final int position, final SpotifyItem model) {
                MainActivity.getDataHandler().loadSpotifyItems(entry,holder,position);

                holder.cover.setVisibility(View.VISIBLE);
                Picasso.with(context).load(model.getCover()).into(holder.cover);
                switch (model.getType()) {
                    case "track" :
                        holder.primaryText.setText(model.getPrimaryText());
                        holder.secondaryText.setText(model.getSecondaryText());
                        break;
                    case "artist" :
                        holder.primaryText.setText(model.getPrimaryText());
                        holder.secondaryText.setVisibility(View.GONE);
                        break;
                    case "album" :
                        holder.primaryText.setText(model.getPrimaryText());
                        holder.secondaryText.setVisibility(View.GONE);
                        break;
                }

                holder.cover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getUri()));
                        context.startActivity(intent);
                        //MainActivity.getDataHandler().openUri(entry,position);
                    }
                });
            }
        };
    }


    public class SpotifyItemHolder extends RecyclerView.ViewHolder {

        private ImageView cover;
        private TextView primaryText;
        private TextView secondaryText;

        public SpotifyItemHolder(View itemView) {
            super(itemView);
            this.cover = (ImageView) itemView.findViewById(R.id.spotify_item_cover);
            this.primaryText = (TextView) itemView.findViewById(R.id.spotify_item_primary);
            this.secondaryText = (TextView) itemView.findViewById(R.id.spotify_item_secondary);
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
}
