package com.example.harishmanikantan.checkers;

/**
 * Created by harishmanikantan on 4/8/17.
 */

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable, Comparable<User> {

    private String name;
    private Uri photoUri;
    private String uid;
    private ArrayList<GameRequest> gameRequests;
    private int numberOfGamesPlayed;
    private int totalScore;

    /**
     * Creates a new user with its attributes
     * @param name name of user
     * @param photoUri photo uri of user
     * @param uid uid of user
     * @param gameRequests game requests of user
     * @param numberOfGamesPlayed number of games played by user
     */
    public User(String name, Uri photoUri, String uid, ArrayList<GameRequest> gameRequests, int numberOfGamesPlayed, int totalScore){
        this.name = name;
        this.photoUri = photoUri;
        this.uid = uid;
        this.gameRequests = gameRequests;
        this.numberOfGamesPlayed = numberOfGamesPlayed;
        this.totalScore = totalScore;
    }

    /**
     * Initializes the member variables with a Parcel object
     * @param in
     */
    protected User(Parcel in) {
        name = in.readString();
        photoUri = Uri.parse(in.readString());
        uid = in.readString();
        gameRequests = in.readArrayList(GameRequest.class.getClassLoader());
        numberOfGamesPlayed = in.readInt();
        totalScore = in.readInt();
    }

    /**
     * This method returns the name of the user
     * @return name of user
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the photo uri of the user
     * @return photo uri of the user
     */
    public Uri getPhotoUri() {
        return photoUri;
    }

    /**
     * This method returns the uid of the user
     * @return uid of the user
     */
    public String getUid(){
        return uid;
    }

    /**
     * This method returns an arraylist of the game requests of the user
     * @return arraylist of game requests of the user
     */
    public ArrayList<GameRequest> getGameRequests() {
        return gameRequests;
    }

    /**
     * This method returns the number of games played by the user
     * @return number of games played by the user
     */
    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    /**
     * This method returns the total score of the player
     * @return
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * This method describes the contents
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * This method writes the member data to a Parcel object
     * @param dest
     * @param i
     */
    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(name);
        dest.writeString(String.valueOf(photoUri));
        dest.writeString(uid);
        dest.writeList(gameRequests);
        dest.writeInt(numberOfGamesPlayed);
        dest.writeInt(totalScore);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int compareTo(User compareUser) {
        return compareUser.totalScore - totalScore;
    }
}