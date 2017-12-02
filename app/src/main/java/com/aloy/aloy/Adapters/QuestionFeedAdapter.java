package com.aloy.aloy.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.Fragments.Feed;
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
 * Created by tldonne on 29/10/2017.
 */

public class QuestionFeedAdapter {
    private Context context;
    private Feed feedView;
    private static FirebaseRecyclerOptions<Question> options;


    public QuestionFeedAdapter(Context context, Feed feedView){
        DatabaseReference feedRef = FirebaseDatabase.getInstance().getReference("questions");
        options = new FirebaseRecyclerOptions.Builder<Question>()
                .setQuery(feedRef, Question.class)
                .setLifecycleOwner(feedView.getActivity())
                .build();
        this.context=context;
        this.feedView=feedView;

    }

    public RecyclerView.Adapter getAdapter(){
        return new FirebaseRecyclerAdapter<Question,QuestionHolder>(options) {

            @Override
            public int getItemViewType(int position) {
                return position;
            }

            @Override
            protected void onBindViewHolder(final QuestionHolder holder, int position, final Question question) {
                holder.getQuestionBody().setText(question.getBody());
                if((question.getName()).equals("")){
                    holder.getQuestionUsername().setText(question.getUsername());
                }else{
                    holder.getQuestionUsername().setText(question.getName());

                }


                MainActivity.getDataHandler().getUrl(question.getUsername(),holder.getProfilePic(),context);
                MainActivity.getDataHandler().getItems(question.getId(), holder,context);
                //MainActivity.getDataHandler().getItems(question.getId(),holder.cover1,holder.cover2,holder.cover3,holder.textCover1,holder.textCover2,holder.textCover3,holder.items,context);
                MainActivity.getDataHandler().getStyles(question.getId(),holder,context);
                MainActivity.getDataHandler().getFollow(question.getId(),holder.getFollowButton());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        feedView.onQuestionCLick(question,holder.itemView);
                    }
                });

                holder.getAnswerButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        feedView.showAnswerQuestion(question.getId());
                        //indexedFeed.onAnswerClick(model.getId());
                    }
                });

                holder.getFollowButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.getDataHandler().follow(question.getId());
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
