package com.example.harishmanikantan.checkers;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Notification extends AppCompatActivity {

    /**
     * This method is called when the notification activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        setTitle("Notifications");

        Intent intent = getIntent();
        ArrayList<GameRequest> gameRequests = intent.getParcelableArrayListExtra(Dashboard.GAME_REQUESTS);

        RecyclerView notificationRecyclerView = (RecyclerView) findViewById(R.id.notification_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        notificationRecyclerView.setLayoutManager(layoutManager);
        notificationRecyclerView.setHasFixedSize(true);

        NotificationAdapter notificationAdapter = new NotificationAdapter(gameRequests, this);
        notificationRecyclerView.setAdapter(notificationAdapter);
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
        startActivity(new Intent(Notification.this, LoginActivity.class));
        finish();
        return true;
    }
}
