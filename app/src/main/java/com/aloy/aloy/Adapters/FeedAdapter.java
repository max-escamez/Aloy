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
import com.aloy.aloy.Util.AchievementsHandler;
import com.aloy.aloy.Util.DataHandler;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by tldonne on 29/10/2017.
 */

public class FeedAdapter {
    private Context context;
    private Feed feedView;
    private static FirebaseRecyclerOptions<Question> options;
    private DataHandler dataHandler;


    public FeedAdapter(Context context, Feed feedView){
        DatabaseReference feedRef = FirebaseDatabase.getInstance().getReference("questions");
        options = new FirebaseRecyclerOptions.Builder<Question>()
                .setQuery(feedRef, Question.class)
                .setLifecycleOwner(feedView.getActivity())
                .build();
        this.context=context;
        this.feedView=feedView;
        this.dataHandler=new DataHandler(context);

    }

    /**
     *This method is used to create a FirebaseRecyclerAdapter, loading the entire question feed
     *and binding every data stored in a Question into an QuestionHolder.
     * @return the FirebaseRecyclerAdapter
     */
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
                dataHandler.getUrl(question.getUsername(),holder.getProfilePic(),context);
                AchievementsHandler achievementsHandler = new AchievementsHandler(context,question.getUsername());
                achievementsHandler.setProfilePicBorder(holder.getProfilePic());
                dataHandler.getItems(question.getId(), holder,context);
                dataHandler.getStyles(question.getId(),holder,context);
                dataHandler.getFollow(question.getId(),holder.getFollowButton());


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
                        dataHandler.follow(question.getId());
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
