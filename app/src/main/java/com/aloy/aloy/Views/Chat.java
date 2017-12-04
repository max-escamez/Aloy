package com.aloy.aloy.Views;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.aloy.aloy.R;
import com.aloy.aloy.Util.DataHandler;
import com.aloy.aloy.Util.SharedPreferenceHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Max on 08/11/2017.
 */

public class Chat extends Fragment {
    private AutoCompleteTextView findUserField;
    private String requestBody;
    private DataHandler dataHandler;
    private TextView errorText;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private ArrayList<String> usersList;
    private Map<String, String> fbMap;

    public Chat() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View chatView = inflater.inflate(R.layout.fragment_chat, container, false);
        usersList= new ArrayList<>();
        fbMap=new HashMap<>();
        sharedPreferenceHelper = new SharedPreferenceHelper(getContext());
        dataHandler = new DataHandler(getContext());
        errorText = (TextView) chatView.findViewById(R.id.errorTextChat);
        FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot users) {
                final String currentUser = sharedPreferenceHelper.getCurrentUserId();
                for (DataSnapshot user : users.getChildren()) {
                    if(!(user.getKey().equals(currentUser))) {
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
                findUserField = (AutoCompleteTextView) chatView.findViewById(R.id.findChatUser);
                findUserField.setThreshold(1);
                findUserField.setAdapter(adapter);

                findUserField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            requestBody = findUserField.getText().toString();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                hideKeyboardFrom(getContext(),chatView);
                            }
                            if(!requestBody.isEmpty()) {
                                if (requestBody.equals(currentUser)) {
                                    findUserField.setText("");
                                    errorText.setText("Like talking to yourself ?");
                                } else if (!usersList.contains(requestBody)) {
                                    findUserField.setText("");
                                    errorText.setText("User is unknown");
                                } else {
                                    //will not work on homonyms..
                                    Toast.makeText(getContext(), "Started chatting with" + requestBody, Toast.LENGTH_SHORT).show();
                                    if(!fbMap.get(requestBody).equals("")){
                                        requestBody=fbMap.get(requestBody);
                                    }
                                    //dataHandler.chat(requestBody);
                                }
                            }else{
                                errorText.setText("No user has been searched");
                            }

                            return true;
                        }
                        return false;
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return chatView;
    }

    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
