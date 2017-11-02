package com.aloy.aloy.Contracts;

import android.content.Context;
import android.view.View;

/**
 * Created by tldonne on 01/11/2017.
 */

public interface SearchContract {

    interface View {

        void hideKeyboardFrom(Context context, android.view.View view);
        void hideSearch();
        void setupRecyclerView(android.view.View searchView);


    }

    interface Presenter {

    }
}
