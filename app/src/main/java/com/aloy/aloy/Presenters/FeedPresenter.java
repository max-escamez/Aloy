package com.aloy.aloy.Presenters;

import com.aloy.aloy.Contracts.FeedContract;
import com.aloy.aloy.Util.DataHandler;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by tldonne on 29/10/2017.
 */

public class FeedPresenter implements FeedContract.Presenter {

    private Query query;
    private FeedContract.View feedView;
    private DataHandler dataHandler;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("questions");

    public FeedPresenter(FeedContract.View view, DataHandler dataHandler){
        this.feedView=view;
        this.dataHandler = dataHandler;
    }

}
