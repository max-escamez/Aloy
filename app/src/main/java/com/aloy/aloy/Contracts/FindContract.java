package com.aloy.aloy.Contracts;

import android.content.Context;

/**
 * Created by tldonne on 01/11/2017.
 */

public interface FindContract {

    interface View {
        void hideKeyboardFrom(Context context, android.view.View view);
        void hideFind();


    }

    interface Presenter {

    }
}
