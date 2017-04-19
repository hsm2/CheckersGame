package com.example.harishmanikantan.checkers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    private RecyclerView userRecyclerView;
    private UserAdapter userAdapter;

    private ArrayList<User> gameUsers;
    private DatabaseReference databaseReference;
    private FirebaseUser currentFirebaseUser;
    private User currentUser;

    private Context context;

    private int games_played = 0;
    private boolean firstTime = true;

    public static final String GAME_REQUESTS = "GAME_REQUESTS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        context = this;

        userRecyclerView = (RecyclerView) findViewById(R.id.user_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        userRecyclerView.setLayoutManager(layoutManager);
        userRecyclerView.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        gameUsers = new ArrayList<>();
        loadAllUsers();

        userAdapter = new UserAdapter(gameUsers);
        userAdapter.notifyDataSetChanged();
        userRecyclerView.setAdapter(userAdapter);

        listenForAcceptedGameRequests();
    }

    private void listenForAcceptedGameRequests() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(currentFirebaseUser.getUid()).child("games");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                games_played++;

                if (currentUser != null && games_played > currentUser.getNumberOfGamesPlayed()) {
                    databaseReference.child(currentUser.getUid()).child("games_played").setValue("" + games_played);
                    Intent intent = new Intent(context, Game.class);
                    intent.putExtra(Game.GAME, dataSnapshot.getKey());
                    intent.putExtra(Game.YOUR_ID, dataSnapshot.child("host").getValue(String.class));
                    intent.putExtra(Game.OPPONENT_ID, dataSnapshot.child("opponent").getValue(String.class));
                    startActivity(intent);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadAllUsers() {
        databaseReference = databaseReference.child("users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gameUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("Database", snapshot.getKey());
                    String name = snapshot.child("name").getValue(String.class);
                    Uri photo_uri = Uri.parse(snapshot.child("photo_uri").getValue(String.class));
                    String uid = snapshot.getKey();

                    int numberOfGamesPlayed = Integer.valueOf(
                            snapshot.child("games_played").getValue(String.class));

                    ArrayList<GameRequest> gameRequests = new ArrayList<GameRequest>();

                    for (DataSnapshot snap : snapshot.child("game_requests").getChildren()) {
                        String sourceUid = snap.getKey();
                        String time = snap.getValue(String.class);
                        String targetUid = currentFirebaseUser.getUid();

                        GameRequest gameRequest = new GameRequest(sourceUid, targetUid, time);

                        gameRequests.add(gameRequest);
                    }

                    Log.d("Database", name);
                    Log.d("Database", uid);
                    Log.d("Database", photo_uri.toString());

                    User user = new User(name, photo_uri, uid, gameRequests, numberOfGamesPlayed);
                    gameUsers.add(user);

                    if (user.getUid().equals(currentFirebaseUser.getUid())) {
                        currentUser = user;
                    }

                    Log.d("Database", "" + gameUsers.size());
                }

                userAdapter.notifyDataSetChanged();
                invalidateOptionsMenu();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_notification);

        if (currentUser == null || currentUser.getGameRequests().size() == 0) {
            item.setIcon(R.drawable.notifications_off_icon);
        }
        else {
            item.setIcon(R.drawable.notification_on_icon);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            finish();
        }
        else if (item.getItemId() == R.id.action_notification && currentUser.getGameRequests().size() != 0) {
            Intent intent = new Intent(Dashboard.this, Notification.class);
            intent.putParcelableArrayListExtra(GAME_REQUESTS, currentUser.getGameRequests());
            startActivity(intent);
        }

        return true;
    }

}
