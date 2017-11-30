package com.aloy.aloy.Contracts;

import java.util.HashMap;

/**
 * Created by madmax on 2017-11-29.
 */

public interface InterestContract {


    interface View  {
        Presenter getInterestPresenter();

    }

    interface Presenter {
        void addGenre(String name,String url);
        void removeGenre(String name);
        HashMap getGenres();
        void update();
    }
}
