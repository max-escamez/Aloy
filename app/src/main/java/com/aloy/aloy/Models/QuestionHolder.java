package com.aloy.aloy.Models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloy.aloy.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tldonne on 01/12/2017.
 */

public class QuestionHolder extends RecyclerView.ViewHolder {

    private TextView questionBody;
    private TextView questionUsername;
    private CircleImageView profilePic;
    private ImageView cover1;
    private ImageView cover2;
    private ImageView cover3;
    private TextView textCover1;
    private TextView textCover2;
    private TextView textCover3;
    private TextView style1;
    private TextView style2;
    private TextView style3;
    private ImageButton answerButton;
    private ImageButton followButton;
    private View items;
    private View styles;
    private ImageView moreItems;

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
        moreItems = (ImageView) itemView.findViewById(R.id.more_items);
    }


    public TextView getQuestionBody() {
        return questionBody;
    }

    public TextView getQuestionUsername() {
        return questionUsername;
    }

    public CircleImageView getProfilePic() {
        return profilePic;
    }

    public ImageButton getAnswerButton() {
        return answerButton;
    }

    public ImageButton getFollowButton() {
        return followButton;
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

    public ImageView getMoreItems() {
        return moreItems;
    }
}
