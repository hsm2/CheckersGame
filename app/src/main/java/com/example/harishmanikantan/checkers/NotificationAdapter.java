package com.example.harishmanikantan.checkers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by harishmanikantan on 4/17/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private ArrayList<GameRequest> gameRequests;
    private DatabaseReference ref;
    private Context context;

    public NotificationAdapter(ArrayList<GameRequest> gameRequests, Context context) {
        this.gameRequests = gameRequests;
        this.ref = FirebaseDatabase.getInstance().getReference().child("users");
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View notificationItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_request_item, parent, false);

        return new ViewHolder(notificationItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final GameRequest gameRequest = gameRequests.get(position);
        populateRequest(holder, gameRequest);

        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameRequests.remove(position);
                notifyDataSetChanged();
                removeGameRequestFromDatabase(gameRequest);
                createGameAndStartGame(gameRequest);
            }
        });

        holder.declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameRequests.remove(position);
                notifyDataSetChanged();
                removeGameRequestFromDatabase(gameRequest);
            }
        });

    }

    private void createGameAndStartGame(GameRequest gameRequest) {
        DatabaseReference gameReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference();

        gameReference = gameReference.child("games").push();
        userReference = userReference.child("users");

        String key = gameReference.getKey();

        gameReference.child("host").setValue(gameRequest.getSourceUid());
        gameReference.child("opponent").setValue(gameRequest.getTargetUid());
        gameReference.child("turn").setValue(gameRequest.getSourceUid());

        userReference.child(gameRequest.getSourceUid()).child("games").child(key).setValue(true);
        userReference.child(gameRequest.getTargetUid()).child("games").child(key).setValue(true);

        Intent intent = new Intent(context, Game.class);
        intent.putExtra(Game.GAME, key);

        context.startActivity(intent);
        ((Activity) context).finish();
    }

    private void removeGameRequestFromDatabase(GameRequest gameRequest) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("users").child(gameRequest.getTargetUid())
                .child("game_requests").child(gameRequest.getSourceUid()).removeValue();
    }

    private ViewHolder tempHolder;
    private GameRequest tempGameRequest;

    private void populateRequest(ViewHolder holder, GameRequest gameRequest) {
        tempHolder = holder;
        tempGameRequest = gameRequest;

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(tempGameRequest.getSourceUid())) {
                    String name = dataSnapshot.child(tempGameRequest.getSourceUid())
                            .child("name").getValue(String.class);
                    String photoUri = dataSnapshot.child(tempGameRequest.getSourceUid())
                            .child("photo_uri").getValue(String.class);
                    String time = convertUTCtoSimpleTime(tempGameRequest.getTime());

                    tempHolder.nameView.setText(name);

                    Picasso.with(tempHolder.pictureView.getContext())
                            .load(photoUri).into(tempHolder.pictureView);

                    tempHolder.dateView.setText(time);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String convertUTCtoSimpleTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));

        try {
            Date date = sdf.parse(time);

            int hour = date.getHours();
            int min = date.getMinutes();
            int day = date.getDay();
            String month = new DateFormatSymbols().getMonths()[date.getMonth() - 1];

            String dayPostFix = "";

            if (day % 10 == 1) {
                dayPostFix = "st";
            }
            else if (day % 10 == 2) {
                dayPostFix = "nd";
            }
            else if (day % 10 == 3) {
                dayPostFix = "rd";
            }
            else {
                dayPostFix = "th";
            }

            String ampm = "";

            if (hour == 12) {
                ampm = "PM";
            }
            else if(hour >= 12){
                ampm = "PM";
                hour = hour%12;
            }
            else {
                ampm = "AM";
            }

            if (hour == 0) {
                hour = 12;
            }

            return hour + ":" + min + " " + ampm + " on " + day + dayPostFix + " " + month;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public int getItemCount() {
        return gameRequests.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameView;
        public TextView dateView;
        public ImageView pictureView;
        public Button acceptButton;
        public Button declineButton;

        public ViewHolder(View itemView) {
            super(itemView);

            nameView = (TextView) itemView.findViewById(R.id.user_name_view);
            dateView = (TextView) itemView.findViewById(R.id.game_time_view);
            pictureView = (ImageView) itemView.findViewById(R.id.user_picture_view);
            acceptButton = (Button) itemView.findViewById(R.id.accept_view);
            declineButton = (Button) itemView.findViewById(R.id.decline_view);
        }

    }

}
