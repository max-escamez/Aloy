package com.aloy.aloy.Contracts;

import com.aloy.aloy.Models.Question;
import com.aloy.aloy.Util.BaseView;
import com.google.firebase.database.Query;

/**
 * Created by tldonne on 06/11/2017.
 */

public interface QuestionDetailsContract {

    interface View  {

    }

    interface Presenter {

        Query getRef(String questionId);
    }
}
