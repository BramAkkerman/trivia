package com.example.brama.trivia;

public class Highscore {
    private int score;
    private String name;
    //private String password;
    private int streak;
    private int streakScore;
    private int rank;

    public Highscore(int score, String name, int streak, int streakScore) {
        this.score = score;
        this.name = name;
        this.streak = streak;
        this.streakScore = streakScore;
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
}
