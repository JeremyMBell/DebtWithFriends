package com.firebase.androidchat;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends ListActivity {

    // TODO: change this to your own Firebase URL
    private static final String FIREBASE_URL = "https://blistering-inferno-1333.firebaseIO.com";

    private Person mUser;
    private Firebase mFirebaseRef, currUser;
    private ValueEventListener mConnectedListener;
    private ChatListAdapter mChatListAdapter;
    private void auth(String email, String password) {
        mFirebaseRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                mUser = new Person(authData.getUid());
                currUser = mFirebaseRef.child(mUser.getName());
                /*
                    TODO: Switch to the main screen of friends, replace activity_main
                 */
                setContentView(R.layout.activity_main);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
            }
        });
    }
    private void newUser(String email, String password) {
        final String r = email;
        mFirebaseRef.createUser(email, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                mUser = new Person(r);
            }

            @Override
            public void onError(FirebaseError firebaseError) {

            }
        });
    }
    private void newTransaction(String toUser, String amount, String principal, String text) {
        /*
            TODO: Check validity of identifying person thru constructor.
         */
        Person toPerson = new Person(toUser);
        float a = Float.parseFloat(amount);
        float p = Float.parseFloat(principal);
        Debt d = new Debt(mUser, toPerson, a, p, text);
        toPerson.addDebt(d);
        mUser.addLoan(d);

    }
    public void updatePerson(Person p) {
        mFirebaseRef.child(p.getName()).updateChildren(p.map());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* TODO:
            On create,
            have user login screen instead of activity_main
         */
        setContentView(R.layout.activity_main);

        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL).child("debtswithfriends");
        /*
            TODO: Link the right EditTexts to the right objects
            Get EditText objects from design
         */
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        /* TODO: Define the button and the method associated with clicking it.
         */
        button.onclick(function {
            auth(email.getText().toString(), password.getText().toString());

        })
        /*
            TODO: Define a button to start a new loan/debt
         */
        button.onclick(function {
            /*
            TODO: Redirect to new transaction screen
             */
            setContentView(R.layout.activity_main);
            EditText toUser = (EditText) findViewById(R.id.toUser);
            EditText amount = (EditText) findViewById(R.id.amount);
            EditText interest = (EditText) findViewById(R.id.interest);
            EditText text = (EditText) findViewById(R.id.text);
            /*
                TODO: Button to send the transaction.
             */
            button.onclick(function {
                newTransaction(toUser.getText().toString(), amount.getText().toString(),
                        interest.getText().toString(), text.getText().toString());
            })
        })

    }

    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
        mChatListAdapter = new ChatListAdapter(mFirebaseRef.limit(50), this, R.layout.chat_message, mUsername);
        listView.setAdapter(mChatListAdapter);
        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mChatListAdapter.getCount() - 1);
            }
        });

        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(MainActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mChatListAdapter.cleanup();
    }
    public String getUser() {
        return "";
    }
}
