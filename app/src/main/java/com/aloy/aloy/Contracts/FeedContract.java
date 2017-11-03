package com.aloy.aloy.Contracts;

import android.support.annotation.Nullable;

import com.aloy.aloy.Models.SearchResult;
import com.aloy.aloy.Util.BaseView;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by tldonne on 28/10/2017.
 */

public interface FeedContract {

    interface View extends BaseView<Presenter> {
        void showAddQuestion();
        void setupRecyclerView(android.view.View feedView);

    }

    interface Presenter {
        void addQuestion();
        Query getQuery();
    }
}
