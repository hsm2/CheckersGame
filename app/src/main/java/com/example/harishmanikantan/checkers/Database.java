package com.example.harishmanikantan.checkers;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by harishmanikantan on 4/8/17.
 */

public class Database {

    private DatabaseReference databaseReference;
    public static ArrayList<User> users;

    public Database() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void loadAllUsers() {
        databaseReference = databaseReference.child("users");
        users = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("Database", snapshot.getKey());
                    String name = snapshot.child("name").getValue(String.class);
                    Uri photo_uri = Uri.parse(snapshot.child("photo_uri").getValue(String.class));
                    String uid = snapshot.getKey();

                    Log.d("Database", name);
                    Log.d("Database", uid);
                    Log.d("Database", photo_uri.toString());

                    ArrayList<GameRequest> gameRequests = new ArrayList<GameRequest>();
                    User user = new User(name, photo_uri, uid, gameRequests,0);
                    users.add(user);
                    Log.d("Database", "" + users.size());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.d("Database", "" + users.size());
    }

}
