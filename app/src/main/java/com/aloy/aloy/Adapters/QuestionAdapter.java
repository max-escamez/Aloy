package com.aloy.aloy.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.aloy.aloy.Fragments.Feed.TAG;


/**
 * Created by tldonne on 29/10/2017.
 */

public class QuestionAdapter extends FirebaseRecyclerAdapter<QuestionAdapter.ViewHolder, Question> {
    private Context context;
    private Feed feedView;
    private DataHandler dataHandler;



    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionBody;
        TextView questionUsername;
        public CircleImageView profilePic;
        ImageView cover1;
        ImageView cover2;
        Button answerButton;
        Button followButton;

        public ViewHolder(View view) {
            super(view);
            questionBody = (TextView) view.findViewById(R.id.questionBody);
            questionUsername = (TextView) view.findViewById(R.id.questionUsername);
            profilePic = (CircleImageView) view.findViewById(R.id.questionProfilePic);
            cover1 = (ImageView) view.findViewById(R.id.questionCover1);
            cover2 = (ImageView) view.findViewById(R.id.questionCover2);
            answerButton = (Button) view.findViewById(R.id.answerButton);
            followButton = (Button) view.findViewById(R.id.followButton);
        }
    }

    public QuestionAdapter(Query query, @Nullable ArrayList<Question> questions,
                           @Nullable ArrayList<String> keys, Context context, Feed view) {
        super(query, questions, keys);
        this.context = context;
        this.feedView=view;
        this.dataHandler = new DataHandler(context);

    }




    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View questionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.question, parent, false);
        return new ViewHolder(questionView);

    }

    @Override
    public void onBindViewHolder(final QuestionAdapter.ViewHolder holder, int position) {
        //Question question = getItem(getItemCount()-position-1);
        final Question question = getItem(position);
        holder.questionBody.setText(question.getBody());

        if((question.getName()).equals("")){
            holder.questionUsername.setText(question.getUsername());
        }else{
            holder.questionUsername.setText(question.getName());
        }

        FirebaseDatabase.getInstance().getReference("users").child(question.getUsername()).child("pic").addListenerForSingleValueEvent(new ValueEventListener() {
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


        //dataHandler.getUrl(question.getPic(),holder.profilePic,context);
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

        holder.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHandler.follow(question.getId());
            }
        });
    }


}
