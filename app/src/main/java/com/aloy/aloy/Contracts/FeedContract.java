package com.aloy.aloy.Contracts;

import com.aloy.aloy.BaseView;

/**
 * Created by tldonne on 28/10/2017.
 */

public interface FeedContract {

    interface View extends BaseView<Presenter> {
        void showAddQuestion();

    }

    interface Presenter {
        void addQuestion();
    }
}
