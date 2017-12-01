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
            public int getItemViewType(int position) {
                return position;
            }

            @Override
            protected void onBindViewHolder(final QuestionHolder holder, int position, final Question question) {
                holder.questionBody.setText(question.getBody());
                if((question.getName()).equals("")){
                    holder.questionUsername.setText(question.getUsername());
                }else{
                    holder.questionUsername.setText(question.getName());

                }

                System.out.println(getItemViewType(position));
                System.out.println(holder.getAdapterPosition());


                //holder.cover1.setVisibility(View.VISIBLE);

                MainActivity.getDataHandler().getUrl(question.getUsername(),holder.profilePic,context);
                //MainActivity.getDataHandler().getItems(question.getId(), holder,context);
                MainActivity.getDataHandler().getItems(question.getId(),holder.cover1,holder.cover2,holder.cover3,holder.textCover1,holder.textCover2,holder.textCover3,holder.items,context);
                MainActivity.getDataHandler().getStyles(question.getId(),holder,context);
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
        ImageView cover3;
        TextView textCover1;
        TextView textCover2;
        TextView textCover3;
        TextView style1;
        TextView style2;
        TextView style3;
        ImageButton answerButton;
        ImageButton followButton;
        View items;
        View styles;

        public QuestionHolder(View itemView) {
            super(itemView);
            questionBody = (TextView) itemView.findViewById(R.id.questionBody);
            questionUsername = (TextView) itemView.findViewById(R.id.questionUsername);
            profilePic = (CircleImageView) itemView.findViewById(R.id.questionProfilePic);
            cover1 = (ImageView) itemView.findViewById(R.id.item_1);
            cover2 = (ImageView) itemView.findViewById(R.id.item_2);
            cover3 = (ImageView) itemView.findViewById(R.id.item_3);
            textCover1 = (TextView) itemView.findViewById(R.id.item_1_text) ;
            textCover2 = (TextView) itemView.findViewById(R.id.item_2_text) ;
            textCover3 = (TextView) itemView.findViewById(R.id.item_3_text) ;
            style1 = (TextView) itemView.findViewById(R.id.style_1) ;
            style2 = (TextView) itemView.findViewById(R.id.style_2) ;
            style3 = (TextView) itemView.findViewById(R.id.style_3) ;
            answerButton = (ImageButton) itemView.findViewById(R.id.answerButton);
            followButton = (ImageButton) itemView.findViewById(R.id.followButton);
            items = itemView.findViewById(R.id.items);
            styles = itemView.findViewById(R.id.styles);
        }

        public ImageView getCover1() {
            return cover1;
        }

        public ImageView getCover2() {
            return cover2;
        }

        public ImageView getCover3() {
            return cover3;
        }

        public TextView getTextCover1() {
            return textCover1;
        }

        public TextView getTextCover2() {
            return textCover2;
        }

        public TextView getTextCover3() {
            return textCover3;
        }

        public TextView getStyle1() {
            return style1;
        }

        public TextView getStyle2() {
            return style2;
        }

        public TextView getStyle3() {
            return style3;
        }

        public View getItems() {
            return items;
        }

        public View getStyles(){
            return styles;
        }
    }






}
