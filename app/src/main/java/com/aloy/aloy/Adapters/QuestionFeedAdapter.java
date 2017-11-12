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
            protected void onBindViewHolder(final QuestionHolder holder, int position, final Question question) {
                //final Question question = getItem(position);
                holder.questionBody.setText(question.getBody());
                holder.questionUsername.setText(question.getUsername());


               /* if((question.getName()).equals("")){
                    holder.questionUsername.setText(question.getUsername());
                }else{
                    System.out.println(question.getName());
                    holder.questionUsername.setText(question.getName());
                }*/

                System.out.println("feed "+ question.getUsername());
                MainActivity.getDataHandler().getUrl(question.getUsername(),holder.profilePic,context);
                //Picasso.with(context).load(user.getPhotoUrl().toString()).into(holder.profilePic);
                //Picasso.with(context).load(model.getPic()).into(holder.profilePic);
                Picasso.with(context).load(question.getCover1()).into(holder.cover1);
                Picasso.with(context).load(question.getCover2()).into(holder.cover2);

                MainActivity.getDataHandler().getFollow(question.getId(),holder.followButton);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        feedView.onQuestionCLick(question,holder.itemView);
                    }
                });

                holder.answerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        feedView.showAnswerQuestion(question.getId());
                        //indexedFeed.onAnswerClick(model.getId());
                    }
                });

                holder.followButton.setOnClickListener(new View.OnClickListener() {
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
