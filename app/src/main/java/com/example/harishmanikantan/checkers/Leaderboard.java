package com.example.harishmanikantan.checkers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;

public class Leaderboard extends AppCompatActivity {

    /**
     * This method is called when the Leaderboard activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        setTitle("Leaderboard");

        Intent intent = getIntent();
        ArrayList<User> users = intent.getParcelableArrayListExtra(Dashboard.USERS);
        Collections.sort(users);

        RecyclerView leaderboardRecyclerView = (RecyclerView) findViewById(R.id.leaderboard_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        leaderboardRecyclerView.setLayoutManager(layoutManager);
        leaderboardRecyclerView.setHasFixedSize(true);

        LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(users);
        leaderboardAdapter.notifyDataSetChanged();
        leaderboardRecyclerView.setAdapter(leaderboardAdapter);
        //dsfsdf
    }

    /**
     * This method creates the options menu on the toolbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    /**
     * This method is called when one of menu items is selected
     * @param item the item selected
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        startActivity(new Intent(Leaderboard.this, LoginActivity.class));
        finish();
        return true;
    }

}
