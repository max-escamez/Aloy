package com.aloy.aloy.Fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.aloy.aloy.MainActivity;
import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Presenters.QuestionDetailsPresenter;
import com.aloy.aloy.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    //private ProfilePresenter profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportPostponeEnterTransition();

        setContentView(R.layout.fragment_profile);
        //profilePresenter = new ProfilePresenter(this, MainActivity.getDataHandler());
        Bundle extras = getIntent().getExtras();
        String usernameTransitionName = extras.getString(Details.USERNAME_TRANSITION_NAME);
        String picTranstionName = extras.getString(Details.PROFILE_PIC_TRANSITION_NAME);
        TextView username = (TextView) findViewById(R.id.username);
        CircleImageView profilePicture = (CircleImageView) findViewById(R.id.profilePicture);
        username.setText(usernameTransitionName);
        Picasso.with(this).load(picTranstionName).noFade().into(profilePicture);

        supportStartPostponedEnterTransition();

    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }
}
