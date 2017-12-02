package com.aloy.aloy.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.Fragments.IndexedFeed;
import com.aloy.aloy.MainActivity;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Models.QuestionHolder;
import com.aloy.aloy.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by tldonne on 09/11/2017.
 */

public class IndexedFeedAdapter {

    private static DatabaseReference keyRef;
    private static DatabaseReference dataRef;
    private static FirebaseRecyclerOptions<Question> options;
    private Context context;
    private IndexedFeed indexedFeed;

    public IndexedFeedAdapter(String userId, String type, Context context, IndexedFeed indexedFeed) {
        keyRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child(type);
        dataRef = FirebaseDatabase.getInstance().getReference("questions");
        options = new FirebaseRecyclerOptions.Builder<Question>()
                .setIndexedQuery(keyRef, dataRef, Question.class)
                .setLifecycleOwner(indexedFeed.getActivity())
                .build();
        this.context=context;
        this.indexedFeed=indexedFeed;
    }


    public RecyclerView.Adapter getAdapter(){
        return new FirebaseRecyclerAdapter<Question,QuestionHolder>(options) {

            @Override
            public int getItemViewType(int position) {
                return position;
            }

            @Override
            protected void onBindViewHolder(final QuestionHolder holder, int position, final Question model) {
                //final Question question = getItem(position);
                holder.getQuestionBody().setText(model.getBody());

                if((model.getName()).equals("")){
                    holder.getQuestionUsername().setText(model.getUsername());
                }else{
                    System.out.println(model.getName());
                    holder.getQuestionUsername().setText(model.getName());
                }

                //Picasso.with(context).load(user.getPhotoUrl().toString()).into(holder.profilePic);
                //Picasso.with(context).load(model.getPic()).into(holder.profilePic);
                MainActivity.getDataHandler().getItems(model.getId(), holder,context);
                MainActivity.getDataHandler().getStyles(model.getId(),holder,context);
                MainActivity.getDataHandler().getUrl(model.getUsername(),holder.getProfilePic(),context);
                MainActivity.getDataHandler().getFollow(model.getId(),holder.getFollowButton());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        indexedFeed.onQuestionCLick(model,holder.itemView);
                    }
                });

                holder.getAnswerButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        indexedFeed.onAnswerClick(model.getId());
                    }
                });

                holder.getFollowButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.getDataHandler().follow(model.getId());
                    }
                });


            }


            @Override
            public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View questionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.question, parent, false);
                //questionView.findViewById(R.id.followButton).setVisibility(View.INVISIBLE);
                return new QuestionHolder(questionView);
            }
        };

    }

}
