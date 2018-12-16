package com.example.brama.trivia;

import android.os.Parcel;
import android.os.Parcelable;

// This class is used to store the API retrieved highscore account. It implements Parcelable so it
// can be put into an intent.
public class Highscore implements Parcelable {
    private int score;
    private String name;
    private String password;
    private int streak;
    private int streakScore;
    private int rank;
    private int id;

    public Highscore(int score, String name, String password, int streak, int streakScore, int id) {
        this.score = score;
        this.name = name;
        this.password = password;
        this.streak = streak;
        this.streakScore = streakScore;
        this.id = id;
    }

    public Highscore(Parcel in) {
        this.score = in.readInt();
        this.name = in.readString();
        this.password = in.readString();
        this.streak = in.readInt();
        this.streakScore = in.readInt();
        this.id = in.readInt();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getStreakScore() {
        return streakScore;
    }

    public void setStreakScore(int streakScore) {
        this.streakScore = streakScore;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.score);
        dest.writeString(this.name);
        dest.writeString(this.password);
        dest.writeInt(this.streak);
        dest.writeInt(this.streakScore);
        dest.writeInt(this.id);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Highscore createFromParcel(Parcel in) {
            return new Highscore(in);
        }

        public Highscore[] newArray(int size) {
            return new Highscore[size];
        }
    };
}
