package com.aloy.aloy.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.MainActivity;
import com.aloy.aloy.Models.Answer;
import com.aloy.aloy.Presenters.QuestionDetailsPresenter;
import com.aloy.aloy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tldonne on 06/11/2017.
 */

public class AnswersAdapter extends FirebaseRecyclerAdapter<AnswersAdapter.ViewHolder, Answer> {

    private Context context;
    private QuestionDetailsPresenter questionDetailsPresenter;
    private DatabaseReference questionId;
    private String question;



    public AnswersAdapter(Query query, @Nullable ArrayList<Answer> answers, @Nullable ArrayList<String> keys, Context context, QuestionDetailsPresenter presenter, String question) {
        super(query, answers, keys);
        this.context = context;
        this.questionDetailsPresenter=presenter;
        this.questionId=query.getRef();
        this.question=question;
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
        System.out.println("Anser : "+answer);
        holder.body.setText(answer.getBody());
        if ((answer.getName()).equals("")) {
            holder.username.setText(answer.getUsername());
        }else{
            holder.username.setText(answer.getName());
        }
        /*FirebaseDatabase.getInstance().getReference("users").child(answer.getUsername()).child("pic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot userURL) {
                Picasso.with(context).load(userURL.getValue().toString()).into(holder.profilePic);
                holder.profilePic.setVisibility(View.VISIBLE);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG,"addListenerForSingleValueEvent:failure",databaseError.toException());
            }

        });*/

        MainActivity.getDataHandler().getUrl(answer.getUsername(),holder.profilePic,context);

        //Picasso.with(context).load(answer.getPic()).into(holder.profilePic);
        questionDetailsPresenter.getUpvotes(question,answer.getId(),holder);
        //Picasso.with(context).load(question.getCover1()).into(holder.cover1);
        //Picasso.with(context).load(question.getCover2()).into(holder.cover2);

        holder.upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("Upvote : " + answer.getId());
                questionDetailsPresenter.upvoteAnswer(questionId,answer.getId());
            }
        });


    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView body;
        TextView username;
        CircleImageView profilePic;
        public ImageButton upvote;
        ImageView cover1;
        ImageView cover2;
         ViewHolder(View view) {
             super(view);
             body = (TextView) view.findViewById(R.id.answerBody);
             username = (TextView) view.findViewById(R.id.answerUsername);
             profilePic = (CircleImageView) view.findViewById(R.id.answerProfilePic);
             upvote = (ImageButton) view.findViewById(R.id.upvote);

             //view.setOnClickListener(this);
          }
        }



}
