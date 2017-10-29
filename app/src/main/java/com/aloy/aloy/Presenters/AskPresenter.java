package com.aloy.aloy.Presenters;

import com.aloy.aloy.Contracts.AskContract;
import com.aloy.aloy.Fragments.Ask;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by tldonne on 29/10/2017.
 */

public class AskPresenter implements AskContract.Presenter {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("questions");
    private AskContract.View askView;
    private String question;

    public AskPresenter(AskContract.View askView) {
        this.askView = askView;
    }




    @Override
    public void addQuestion(String question) {
        myRef.push().setValue(question);

    }
}
