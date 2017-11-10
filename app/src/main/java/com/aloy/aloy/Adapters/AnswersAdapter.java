package com.aloy.aloy.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.Fragments.Details;
import com.aloy.aloy.Models.Answer;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Presenters.QuestionDetailsPresenter;
import com.aloy.aloy.R;
import com.aloy.aloy.Util.DataHandler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by tldonne on 06/11/2017.
 */

public class AnswersAdapter extends FirebaseRecyclerAdapter<AnswersAdapter.ViewHolder, Answer> {

    private Context context;
    private QuestionDetailsPresenter questionDetailsPresenter;
    private DatabaseReference questionId;



    public AnswersAdapter(Query query, @Nullable ArrayList<Answer> answers, @Nullable ArrayList<String> keys, Context context, QuestionDetailsPresenter presenter) {
        super(query, answers, keys);
        this.context = context;
        this.questionDetailsPresenter=presenter;
        this.questionId=query.getRef();
    }


    @Override
    public AnswersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View answerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer, parent, false);
        return new ViewHolder(answerView);
    }


    @Override
    public void onBindViewHolder(final AnswersAdapter.ViewHolder holder, int position) {
        //Question question = getItem(getItemCount()-position-1);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final Answer answer = getItem(position);
        holder.body.setText(answer.getBody());
        if ((answer.getName()).equals("")) {
            holder.username.setText(answer.getUsername());
        }else{
            holder.username.setText(answer.getName());
        }
        FirebaseDatabase.getInstance().getReference("users").child(answer.getUsername()).child("pic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot userURL) {
                Picasso.with(context).load(userURL.getValue().toString()).into(holder.profilePic);
                holder.profilePic.setVisibility(View.VISIBLE);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG,"addListenerForSingleValueEvent:failure",databaseError.toException());
            }

        });
        //Picasso.with(context).load(answer.getPic()).into(holder.profilePic);
        questionDetailsPresenter.getUserUpvote(questionId,answer.getId(),holder);
        //Picasso.with(context).load(question.getCover1()).into(holder.cover1);
        //Picasso.with(context).load(question.getCover2()).into(holder.cover2);

        holder.upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Upvote : " + answer.getId());
                questionDetailsPresenter.upvoteAnswer(questionId,answer.getId());
            }
        });


    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView body;
        TextView username;
        CircularImageView profilePic;
        public ImageButton upvote;
        ImageView cover1;
        ImageView cover2;
         ViewHolder(View view) {
             super(view);
             body = (TextView) view.findViewById(R.id.answerBody);
             username = (TextView) view.findViewById(R.id.answerUsername);
             profilePic = (CircularImageView) view.findViewById(R.id.answerProfilePic);
             upvote = (ImageButton) view.findViewById(R.id.upvote);

             //view.setOnClickListener(this);
          }
        }



}
