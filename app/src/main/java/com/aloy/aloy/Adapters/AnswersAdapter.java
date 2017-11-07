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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.Fragments.Feed;
import com.aloy.aloy.Fragments.QuestionDetails;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.R;
import com.google.firebase.database.Query;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tldonne on 06/11/2017.
 */

public class AnswersAdapter extends FirebaseRecyclerAdapter<AnswersAdapter.ViewHolder, Question> {

    private Context context;
    private QuestionDetails questionDetailsView;



    public AnswersAdapter(Query query, @Nullable ArrayList<Question> questions, @Nullable ArrayList<String> keys, Context context, QuestionDetails view) {
        super(query, questions, keys);
        this.context = context;
        this.questionDetailsView=view;
    }


    @Override
    public AnswersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View answerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer, parent, false);
        return new ViewHolder(answerView);
    }


    @Override
    public void onBindViewHolder(final AnswersAdapter.ViewHolder holder, int position) {
        //Question question = getItem(getItemCount()-position-1);
        final Question question = getItem(position);
        holder.body.setText(question.getBody());
        holder.username.setText(question.getUsername());
        Picasso.with(context).load(question.getPic()).into(holder.profilePic);
        //Picasso.with(context).load(question.getCover1()).into(holder.cover1);
        //Picasso.with(context).load(question.getCover2()).into(holder.cover2);

        holder.likeBorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.likeBorder.setVisibility(View.INVISIBLE);
                holder.like.setVisibility(View.VISIBLE);
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.like.setVisibility(View.INVISIBLE);
                holder.likeBorder.setVisibility(View.VISIBLE);

            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView body;
        TextView username;
        CircularImageView profilePic;
        ImageButton likeBorder;
        ImageButton like;
        ImageView cover1;
        ImageView cover2;
         ViewHolder(View view) {
             super(view);
             body = (TextView) view.findViewById(R.id.answerBody);
             username = (TextView) view.findViewById(R.id.answerUsername);
             profilePic = (CircularImageView) view.findViewById(R.id.answerProfilePic);
             likeBorder = (ImageButton) view.findViewById(R.id.answerLikeBorder);
             like = (ImageButton) view.findViewById(R.id.answerLike);

             //view.setOnClickListener(this);
          }
        }



}
