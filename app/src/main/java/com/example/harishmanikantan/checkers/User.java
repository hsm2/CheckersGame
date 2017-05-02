package com.example.harishmanikantan.checkers;

/**
 * Created by harishmanikantan on 4/8/17.
 */

import android.net.Uri;

import java.util.ArrayList;

public class User {

    private String name;
    private Uri photoUri;
    private String uid;
    private ArrayList<GameRequest> gameRequests;
    private int numberOfGamesPlayed;

    /**
     * Creates a new user with its attributes
     * @param name name of user
     * @param photoUri photo uri of user
     * @param uid uid of user
     * @param gameRequests game requests of user
     * @param numberOfGamesPlayed number of games played by user
     */
    public User(String name, Uri photoUri, String uid, ArrayList<GameRequest> gameRequests, int numberOfGamesPlayed){
        this.name = name;
        this.photoUri = photoUri;
        this.uid = uid;
        this.gameRequests = gameRequests;
        this.numberOfGamesPlayed = numberOfGamesPlayed;
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
}