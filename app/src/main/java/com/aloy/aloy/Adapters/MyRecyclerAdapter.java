package com.aloy.aloy.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.Fragments.Feed;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.R;
import com.aloy.aloy.Util.DataHandler;
import com.google.firebase.database.Query;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tldonne on 29/10/2017.
 */

public class MyRecyclerAdapter extends FirebaseRecyclerAdapter<MyRecyclerAdapter.ViewHolder, Question> {
    private Context context;
    private Feed feedView;
    private DataHandler dataHandler;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionBody;
        TextView questionUsername;
        CircularImageView profilePic;
        ImageView cover1;
        ImageView cover2;
        Button answerButton;
        Button upvoteButton;

        public ViewHolder(View view) {
            super(view);
            questionBody = (TextView) view.findViewById(R.id.questionBody);
            questionUsername = (TextView) view.findViewById(R.id.questionUsername);
            profilePic = (CircularImageView) view.findViewById(R.id.questionProfilePic);
            cover1 = (ImageView) view.findViewById(R.id.questionCover1);
            cover2 = (ImageView) view.findViewById(R.id.questionCover2);
            answerButton = (Button) view.findViewById(R.id.answerButton);
            upvoteButton = (Button) view.findViewById(R.id.upvoteButton);
        }
    }

    public MyRecyclerAdapter(Query query, @Nullable ArrayList<Question> questions,
                             @Nullable ArrayList<String> keys, Context context, Feed view) {
        super(query, questions, keys);
        this.context = context;
        this.feedView=view;
        this.dataHandler = new DataHandler(context);
    }




    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View questionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.question, parent, false);
        return new ViewHolder(questionView);

    }

    @Override
    public void onBindViewHolder(final MyRecyclerAdapter.ViewHolder holder, int position) {
        //Question question = getItem(getItemCount()-position-1);
        final Question question = getItem(position);
        holder.questionBody.setText(question.getBody());
        holder.questionUsername.setText(question.getUsername());
        Picasso.with(context).load(question.getPic()).into(holder.profilePic);
        Picasso.with(context).load(question.getCover1()).into(holder.cover1);
        Picasso.with(context).load(question.getCover2()).into(holder.cover2);

        ViewCompat.setTransitionName(holder.itemView, question.getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "You clicked question " +  question.getId());
                feedView.onQuestionCLick(question,holder.itemView);

            }
        });

        holder.answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedView.showAnswerQuestion(question.getId());
            }
        });


    }


}
