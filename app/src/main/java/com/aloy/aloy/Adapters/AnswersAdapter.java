package com.aloy.aloy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aloy.aloy.Views.Profile;
import com.aloy.aloy.Models.Answer;
import com.aloy.aloy.Presenters.QuestionDetailsPresenter;
import com.aloy.aloy.R;
import com.aloy.aloy.Util.AchievementsHandler;
import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.SharedPreferenceHelper;
import com.firebase.ui.database.*;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;


import de.hdodenhof.circleimageview.CircleImageView;

import static com.aloy.aloy.Views.Details.FB_NAME;
import static com.aloy.aloy.Views.Details.PROFILE_PIC_TRANSITION_NAME;
import static com.aloy.aloy.Views.Details.USERNAME_TRANSITION_NAME;

/**
 * Created by tldonne on 06/11/2017.
 */

public class AnswersAdapter {

    private Context context;
    private QuestionDetailsPresenter questionDetailsPresenter;
    private DatabaseReference answerRef;
    private String questionId;
    private static FirebaseRecyclerOptions<Answer> options;
    private AppCompatActivity activity;
    private DataHandler dataHandler;
    private SharedPreferenceHelper sharedPreferenceHelper;


    public AnswersAdapter(Context context, QuestionDetailsPresenter presenter, String questionId, AppCompatActivity activity) {
        dataHandler=new DataHandler(context);
        this.answerRef = dataHandler.getRefAnswers(questionId);
        options = new FirebaseRecyclerOptions.Builder<Answer>()
                .setQuery(answerRef.orderByChild("upvotes/number"), Answer.class)
                .setLifecycleOwner(activity)
                .build();
        this.context = context;
        this.questionDetailsPresenter=presenter;
        this.questionId=questionId;
        this.activity=activity;
        this.sharedPreferenceHelper = new SharedPreferenceHelper(context);
    }


    /**
     *This method is used to create a FirebaseRecyclerAdapter, loading answers corresponding
     * to a question, and binding every data stored in an Answer into an AnswerHolder.
     * @return the FirebaseRecyclerAdapter
     */
    public RecyclerView.Adapter getAdapter() {
        return new FirebaseRecyclerAdapter<Answer,AnswerHolder>(options) {
            @Override
            public AnswerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View answerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer, parent, false);
                return new AnswerHolder(answerView,context);
            }


            @Override
            protected void onBindViewHolder(final AnswerHolder holder, int position, final Answer answer) {
                System.out.println("bind");
                holder.body.setText(answer.getBody());
                if ((answer.getName()).equals("")) {
                    holder.username.setText(answer.getUsername());
                }else{
                    holder.username.setText(answer.getName());
                }
                //questionDetailsPresenter.getUpvotes(question,answer.getId(),holder.upvote);
                AchievementsHandler achievementsHandler = new AchievementsHandler(context,answer.getUsername());
                achievementsHandler.setProfilePicBorder(holder.profilePic);
                dataHandler.getUrl(answer.getUsername(),holder.profilePic,context);
                dataHandler.getUpvote(questionId,answer.getId(),holder.upvote);

                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                CoverFlowAdapter adapter = new CoverFlowAdapter(context,answerRef.child(answer.getId()),activity);
                holder.getItems().setLayoutManager(layoutManager);
                holder.getItems().setAdapter(adapter.getAdapter());
                holder.getItems().setOnFlingListener(null);
                SnapHelper snapHelper = new LinearSnapHelper();
                snapHelper.attachToRecyclerView(holder.getItems());

                holder.upvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //System.out.println("Upvote : " + answer.getId());
                        //questionDetailsPresenter.upvoteAnswer(questionId,answer.getId());
                        if (holder.upvote.getTag().equals(R.drawable.ic_playlist_add)){
                            holder.upvote.setImageResource(R.drawable.ic_playlist_add_check);
                            holder.upvote.setTag(R.drawable.ic_playlist_add_check);

                        }
                        else {
                            holder.upvote.setImageResource(R.drawable.ic_playlist_add);
                            holder.upvote.setTag(R.drawable.ic_playlist_add);


                        }
                        dataHandler.upvote(answerRef,answer.getId());

                    }
                });

                if (!(answer.getUsername().equals(sharedPreferenceHelper.getCurrentUserId()))) {
                    holder.username.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openProfile(answer,holder);
                        }
                    });
                    holder.profilePic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openProfile(answer,holder);

                        }
                    });
                }



            }


        };
    }

    private void openProfile(Answer answer,AnswerHolder holder) {
        Intent intent = new Intent(context, Profile.class);
        ViewCompat.setTransitionName(holder.username,answer.getUsername());
        ViewCompat.setTransitionName(holder.profilePic,answer.getPic());
        Pair usernamePair = Pair.create(holder.username,ViewCompat.getTransitionName(holder.username));
        Pair picturePair = Pair.create(holder.profilePic,ViewCompat.getTransitionName(holder.profilePic));
        intent.putExtra(USERNAME_TRANSITION_NAME, ViewCompat.getTransitionName(holder.username));
        intent.putExtra(PROFILE_PIC_TRANSITION_NAME, ViewCompat.getTransitionName(holder.profilePic));
        intent.putExtra(FB_NAME,answer.getName());
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, usernamePair, picturePair);
        context.startActivity(intent, options.toBundle());
    }


    public static class AnswerHolder extends RecyclerView.ViewHolder{
        TextView body;
        TextView username;
        CircleImageView profilePic;
        ImageButton upvote;
        private RecyclerView items;

        AnswerHolder(View view,Context context) {
            super(view);
            body = (TextView) view.findViewById(R.id.answerBody);
            username = (TextView) view.findViewById(R.id.answerUsername);
            profilePic = (CircleImageView) view.findViewById(R.id.answerProfilePic);
            upvote = (ImageButton) view.findViewById(R.id.upvote);
            this.items = (RecyclerView) view.findViewById(R.id.answer_recycler);
        }

        public RecyclerView getItems(){
             return this.items;
          }

    }
}
