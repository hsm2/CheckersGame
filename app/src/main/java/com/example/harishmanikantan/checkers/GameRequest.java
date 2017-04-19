package com.example.harishmanikantan.checkers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by harishmanikantan on 4/17/17.
 */

public class GameRequest implements Parcelable{

    private String sourceUid;
    private String targetUid;
    private String time;

    public GameRequest(String sourceUid, String targetUid, String time) {
        this.sourceUid = sourceUid;
        this.targetUid = targetUid;
        this.time = time;
    }

    protected GameRequest(Parcel in) {
        sourceUid = in.readString();
        targetUid = in.readString();
        time = in.readString();
    }

    public String getSourceUid() {
        return sourceUid;
    }

    public String getTargetUid() {
        return targetUid;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

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
