package com.example.harishmanikantan.checkers;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Intent intent = getIntent();
        ArrayList<GameRequest> gameRequests = intent.getParcelableArrayListExtra(Dashboard.GAME_REQUESTS);

        RecyclerView notificationRecyclerView = (RecyclerView) findViewById(R.id.notification_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        notificationRecyclerView.setLayoutManager(layoutManager);
        notificationRecyclerView.setHasFixedSize(true);

        NotificationAdapter notificationAdapter = new NotificationAdapter(gameRequests, this);
        notificationRecyclerView.setAdapter(notificationAdapter);
    }
}
