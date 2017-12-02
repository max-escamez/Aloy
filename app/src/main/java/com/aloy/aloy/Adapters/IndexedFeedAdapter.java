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
import com.aloy.aloy.R;
import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.SharedPreferenceHelper;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by tldonne on 09/11/2017.
 */

public class IndexedFeedAdapter {

    private static DatabaseReference keyRef;
    private Query q;
    private static DatabaseReference dataRef;
    private static FirebaseRecyclerOptions<Question> options;
    private Context context;
    private IndexedFeed indexedFeed;
    private DataHandler dataHandler;

    public IndexedFeedAdapter(String userId, String type, Context context, final IndexedFeed indexedFeed) {
        dataHandler = new DataHandler(context);
        dataRef = FirebaseDatabase.getInstance().getReference("questions");
        if(!(type.equals("requests")||type.equals("following")||type.equals("questions")||type.equals("answers"))){
            keyRef = FirebaseDatabase.getInstance().getReference().child("categories").child(type).child("questions");
        }else {
            keyRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child(type);
        }
        options = new FirebaseRecyclerOptions.Builder<Question>()
                .setIndexedQuery(keyRef, dataRef, Question.class)
                .setLifecycleOwner(indexedFeed.getActivity())
                .build();
        this.context = context;
        this.indexedFeed = indexedFeed;
    }




    public RecyclerView.Adapter getAdapter(){
        return new FirebaseRecyclerAdapter<Question,QuestionHolder>(options) {
            @Override
            protected void onBindViewHolder(final QuestionHolder holder, int position, final Question model) {
                //final Question question = getItem(position);
                holder.questionBody.setText(model.getBody());

                if((model.getName()).equals("")){
                    holder.questionUsername.setText(model.getUsername());
                }else{
                    System.out.println(model.getName());
                    holder.questionUsername.setText(model.getName());
                }

                MainActivity.getDataHandler().getUrl(model.getUsername(),holder.profilePic,context);
                //Picasso.with(context).load(user.getPhotoUrl().toString()).into(holder.profilePic);
                //Picasso.with(context).load(model.getPic()).into(holder.profilePic);
                Picasso.with(context).load(model.getCover1()).into(holder.cover1);
                Picasso.with(context).load(model.getCover2()).into(holder.cover2);

                MainActivity.getDataHandler().getFollow(model.getId(),holder.followButton);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        indexedFeed.onQuestionCLick(model,holder.itemView);
                    }
                });

                holder.answerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        indexedFeed.onAnswerClick(model.getId());
                    }
                });

                holder.followButton.setOnClickListener(new View.OnClickListener() {
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

    public static class QuestionHolder extends RecyclerView.ViewHolder {

        TextView questionBody;
        TextView questionUsername;
        CircleImageView profilePic;
        ImageView cover1;
        ImageView cover2;
        ImageButton answerButton;
        ImageButton followButton;

        public QuestionHolder(View view) {
            super(view);
            questionBody = (TextView) view.findViewById(R.id.questionBody);
            questionUsername = (TextView) view.findViewById(R.id.questionUsername);
            profilePic = (CircleImageView) view.findViewById(R.id.questionProfilePic);
            cover1 = (ImageView) view.findViewById(R.id.questionCover1);
            cover2 = (ImageView) view.findViewById(R.id.questionCover2);
            answerButton = (ImageButton) view.findViewById(R.id.answerButton);
            followButton = (ImageButton) view.findViewById(R.id.followButton);
        }
    }
}
