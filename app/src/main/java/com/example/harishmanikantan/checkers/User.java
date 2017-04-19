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

    public User(String name, Uri photoUri, String uid, ArrayList<GameRequest> gameRequests, int numberOfGamesPlayed){
        this.name = name;
        this.photoUri = photoUri;
        this.uid = uid;
        this.gameRequests = gameRequests;
        this.numberOfGamesPlayed = numberOfGamesPlayed;
    }

    public String getName() {
        return name;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public String getUid(){
        return uid;
    }

    public ArrayList<GameRequest> getGameRequests() {
        return gameRequests;
    }

    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }
}