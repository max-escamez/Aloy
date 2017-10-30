package com.aloy.aloy.Adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aloy.aloy.Models.Question;
import com.aloy.aloy.R;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by tldonne on 29/10/2017.
 */

public class MyRecyclerAdapter extends FirebaseRecyclerAdapter<MyRecyclerAdapter.ViewHolder, Question> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionBody;
        TextView questionUsername;

        public ViewHolder(View view) {
            super(view);
            questionBody = (TextView) view.findViewById(R.id.questionBody);
            questionUsername = (TextView) view.findViewById(R.id.questionUsername);
        }
    }

    public MyRecyclerAdapter(Query query, @Nullable ArrayList<Question> questions,
                             @Nullable ArrayList<String> keys) {
        super(query, questions, keys);
    }

    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.ViewHolder holder, int position) {
        //Question question = getItem(getItemCount()-position-1);
        Question question = getItem(position);
        holder.questionBody.setText(question.getBody());
        holder.questionUsername.setText(question.getUsername());
    }


}
