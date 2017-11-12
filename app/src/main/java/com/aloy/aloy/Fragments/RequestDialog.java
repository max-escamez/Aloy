package com.aloy.aloy.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aloy.aloy.R;
import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.SharedPreferenceHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.aloy.aloy.Fragments.Feed.TAG;

/**
 * Created by madmax on 11/11/2017.
 */

public class RequestDialog extends DialogFragment {

    private AutoCompleteTextView findUserField;
    private Button submitRequest;
    private ImageButton close;
    private String requestBody;
    private DataHandler dataHandler;
    private TextView errorText;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private ArrayList<String> usersList;
    private Map<String, String> fbMap;


    public RequestDialog() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View requestView = inflater.inflate(R.layout.fragment_request_dialog, container, false);
        usersList= new ArrayList<>();
        fbMap=new HashMap<>();
        sharedPreferenceHelper = new SharedPreferenceHelper(getContext());
        Bundle args = getArguments();
        final String questionId = args.getString("questionId");
        dataHandler = new DataHandler(getContext());
        errorText = (TextView) requestView.findViewById(R.id.errorText);
        submitRequest = (Button) requestView.findViewById(R.id.submitRequest);
        close = (ImageButton) requestView.findViewById(R.id.closeRequestButton);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        FirebaseDatabase.getInstance().getReference("questions").child(questionId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot question) {
                FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot users) {
                        final String currentUser = sharedPreferenceHelper.getCurrentUserId();
                        final String author = question.child("username").getValue().toString();
                        for (DataSnapshot user : users.getChildren()) {
                            if(!(user.getKey().equals(currentUser)||user.getKey().equals(author))) {
                                if(user.child("name").getValue().toString().equals("")){
                                    usersList.add(user.getKey());
                                    fbMap.put(user.getKey(),"");
                                }else {
                                    usersList.add(user.child("name").getValue().toString());
                                    fbMap.put(user.child("name").getValue().toString(),user.getKey());
                                }
                            }
                        }
                        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item, usersList);
                        findUserField = (AutoCompleteTextView) requestView.findViewById(R.id.findUser);
                        findUserField.setThreshold(1);
                        findUserField.setAdapter(adapter);

                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hideRequest();
                            }
                        });


                        findUserField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                if (actionId == EditorInfo.IME_ACTION_DONE) {
                                    requestBody = findUserField.getText().toString();
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        hideKeyboardFrom(getContext(),requestView);
                                    }
                                    return true;
                                }
                                return false;
                            }
                        });

                        submitRequest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //requestBody = findUserField.getText().toString();
                                if(!requestBody.isEmpty()) {
                                    if (requestBody.equals(currentUser)) {
                                        findUserField.setText("");
                                        errorText.setText("Can't request yourself");
                                    } else if (requestBody.equals(author)) {
                                        findUserField.setText("");
                                        errorText.setText("Can't request the author");
                                    } else if (!usersList.contains(requestBody)) {
                                        findUserField.setText("");
                                        errorText.setText("User is unknown");
                                    } else {
                                        //will not work on homonyms..
                                        Toast.makeText(getContext(), "Answer requested to " + requestBody, Toast.LENGTH_SHORT).show();
                                        if(!fbMap.get(requestBody).equals("")){
                                            requestBody=fbMap.get(requestBody);
                                        }
                                        dataHandler.request(requestBody, questionId);
                                        hideRequest();
                                    }
                                }else{
                                    errorText.setText("No user has been searched");
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG,"addListenerForSingleValueEvent:failure",databaseError.toException());
            }
        });
        return requestView;
    }

    public void hideRequest() {
        this.dismiss();

    }
    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
