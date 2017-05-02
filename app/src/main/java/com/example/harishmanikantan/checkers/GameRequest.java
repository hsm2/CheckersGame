package com.example.harishmanikantan.checkers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by harishmanikantan on 4/17/17.
 */

public class GameRequest implements Parcelable {

    private String sourceUid;
    private String targetUid;
    private String time;

    /**
     * This method constructs a GameRequest with sourceUid, targetUid and time of request
     * @param sourceUid the source player's uid
     * @param targetUid the target player's uid
     * @param time the time the request was made
     */
    public GameRequest(String sourceUid, String targetUid, String time) {
        this.sourceUid = sourceUid;
        this.targetUid = targetUid;
        this.time = time;
    }

    /**
     * Initializes the member variables with a Parcel object
     * @param in
     */
    protected GameRequest(Parcel in) {
        sourceUid = in.readString();
        targetUid = in.readString();
        time = in.readString();
    }

    /**
     * This method returns the source uid or the user uid of who sent the game request
     * @return source uid
     */
    public String getSourceUid() {
        return sourceUid;
    }

    /**
     * This method returns the target uid or the user uid of who was invited to the game
     * @return
     */
    public String getTargetUid() {
        return targetUid;
    }

    /**
     * This method returns the time of the game request
     * @return
     */
    public String getTime() {
        return time;
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
        dest.writeString(sourceUid);
        dest.writeString(targetUid);
        dest.writeString(time);
    }

    public static final Creator<GameRequest> CREATOR = new Creator<GameRequest>() {

        @Override
        public GameRequest createFromParcel(Parcel parcel) {
            return new GameRequest(parcel);
        }

        @Override
        public GameRequest[] newArray(int size) {
            return new GameRequest[size];
        }
    };
}
