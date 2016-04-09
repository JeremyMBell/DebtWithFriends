package com.firebase.androidchat;

import android.app.ListActivity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.snapshot.Node;

public class MainActivity extends ListActivity {

    // TODO: change this to your own Firebase URL
    private static final String FIREBASE_URL = "https://blistering-inferno-1333.firebaseIO.com";

    private static Person mUser;
    private Firebase mFirebaseRef, currUser;
    private ValueEventListener mConnectedListener;
    private PersonListAdapter mPersonListAdapter;
    private void auth(String email, String password) {
        final String u = email;
        mFirebaseRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                currUser = mFirebaseRef.child(u);
                currUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mUser = dataSnapshot.getValue(Person.class);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
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
    public void newUser(String email, String password) {
        final String u = email, p = password;
        mFirebaseRef.createUser(email, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                auth(u, p);
            }

            @Override
            public void onError(FirebaseError firebaseError) {

            }
        });
    }
    public void newTransaction(Debt debt, boolean currIsDebtor) {
        /*
            TODO: Check validity of identifying person thru constructor.
         */
        Person p;
        if(currIsDebtor) {
            mUser.addDebt(debt);
            p = debt.getCreditor();
            p.addLoan(debt);

        } else {
            mUser.addLoan(debt);
            p = debt.getDebitor();
            p.addDebt(debt);
        }
        updatePerson(mUser);
        updatePerson(p);
    }
    public void deleteTransaction(Debt debt) {
        if(mUser.equals(debt.getCreditor())) {
            Person p = debt.getDebitor();
            p.deleteDebt(debt);
            mUser.deleteLoan(debt);
            updatePerson(p);
            updatePerson(mUser);
        }
    }
    public void updatePerson(Person p) {
        mFirebaseRef.child(p.getName()).updateChildren(p.map());
    }

    public void updateFriends(Person friend, boolean isAdding) {
        if(isAdding) {
            mUser.addFriend(friend);
            friend.addFriend(mUser);
        } else {
            mUser.removeFriend(friend);
            friend.removeFriend(mUser);
        }
        updatePerson(mUser);
        updatePerson(friend);
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


    }

    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
        mPersonListAdapter = new PersonListAdapter(mFirebaseRef.limit(50), this, R.layout.chat_message, mUser);
        listView.setAdapter(mPersonListAdapter);
        mPersonListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mPersonListAdapter.getCount() - 1);
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
        mPersonListAdapter.cleanup();
    }
    public String getUser() {
        return mUser.getName();
    }
}
