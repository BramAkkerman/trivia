package com.example.brama.trivia;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

// This class is used to store the API retrieved questions. It implements Parcelable so it can
// be put into an intent.
public class Question implements Parcelable {
    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correctAnswer;
    private ArrayList<String> incorrectAnswers;

    public Question(String category, String type, String difficulty, String question, String correctAnswer, ArrayList<String> incorrectAnswers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public Question(Parcel in) {
        this.category = in.readString();
        this.type = in.readString();
        this.difficulty = in.readString();
        this.question = in.readString();
        this.correctAnswer = in.readString();
        this.incorrectAnswers = in.createStringArrayList();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public ArrayList<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(ArrayList<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.category);
        dest.writeString(this.type);
        dest.writeString(this.difficulty);
        dest.writeString(this.question);
        dest.writeString(this.correctAnswer);
        dest.writeStringList(this.incorrectAnswers);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
