package com.example.harishmanikantan.checkers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by harishmanikantan on 4/8/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<User> users;

    /**
     * The constructor which initializes the users arraylist
     * @param users
     */
    public UserAdapter(ArrayList<User> users) {
        this.users = users;
    }

    /**
     * This method creates a viewholder
     * @param parent
     * @param viewType
     * @return a viewholder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View userView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);

        return new ViewHolder(userView);
    }

    /**
     * This method binds the data corresponding to a position to a view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final User user = users.get(position);

        holder.nameView.setText(user.getName());
        Picasso.with(holder.pictureView.getContext())
                .load(user.getPhotoUri()).into(holder.pictureView);

        holder.sendRequestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPlayerToGame(user);
            }
        });
    }

    /**
     * When a user is selected, this method requests the user to a game
     * @param opponentUser the user to request a game
     */
    private void requestPlayerToGame(User opponentUser) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("users").child(opponentUser.getUid()).child("game_requests");

        ref.child(currentUser.getUid()).setValue(getCurrentUTCTime());
    }

    /**
     * This method gets the current UTC time
     * @return current UTC time
     */
    private String getCurrentUTCTime() {
        Calendar cal = new GregorianCalendar();
        long time = cal.getTimeInMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        return sdf.format(new Date(time));
    }

    /**
     * This method returns the size of the users arraylist
     * @return size of users arraylist
     */
    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView pictureView;
        public TextView nameView;
        public ImageView sendRequestView;

        public ViewHolder(View itemView) {
            super(itemView);

            pictureView = (ImageView) itemView.findViewById(R.id.profile_picture_view);
            nameView = (TextView) itemView.findViewById(R.id.name_view);
            sendRequestView = (ImageView) itemView.findViewById(R.id.request_button);
        }
    }

}
