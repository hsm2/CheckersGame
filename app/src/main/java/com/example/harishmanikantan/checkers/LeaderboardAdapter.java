package com.example.harishmanikantan.checkers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by harishmanikantan on 5/1/17.
 */

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private ArrayList<User> users;

    /**
     * This constructor initializes the users arraylist
     * @param users the users arraylist
     */
    public LeaderboardAdapter(ArrayList<User> users) {
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
                .inflate(R.layout.leaderboard_item, parent, false);

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

        holder.rankView.setText((position + 1) + "");
        Picasso.with(holder.userPictureView.getContext())
                .load(user.getPhotoUri()).into(holder.userPictureView);
        holder.userNameView.setText(user.getName());
        holder.scoreView.setText(user.getTotalScore() + "");
    }

    /**
     * This method returns the size of the users arraylist
     * @return size of users arraylist
     */
    @Override
    public int getItemCount() {
        return users.size();
    }

    /**
     * Custom RecyclerView ViewHolder class
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView rankView;
        public ImageView userPictureView;
        public TextView userNameView;
        public TextView scoreView;

        public ViewHolder(View itemView) {
            super(itemView);

            rankView = (TextView) itemView.findViewById(R.id.rank_view);
            userPictureView = (ImageView) itemView.findViewById(R.id.user_picture_view);
            userNameView = (TextView) itemView.findViewById(R.id.name_view);
            scoreView = (TextView) itemView.findViewById(R.id.score_view);
        }
    }
}
