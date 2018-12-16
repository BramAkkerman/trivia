package com.example.brama.trivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// This activity handles list with highscores
public class ViewHighscoreActivity extends AppCompatActivity implements ViewHighscoreRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_highscore);

        ViewHighscoreRequest rq = new ViewHighscoreRequest(this);
        rq.getHighscores(this);
    }

    // Use HighscoreAdapter to fill the ListView
    @Override
    public void gotHighscores(ArrayList<Highscore> highscores) {
        highscores = orderToRank(highscores);
        HighscoreAdapter arrayAdapter = new HighscoreAdapter(this,R.layout.highscore,highscores);
        ListView list = findViewById(R.id.scoresList);
        list.setAdapter(arrayAdapter);
    }

    @Override
    public void gotHighscoresError(String message) {
        Toast.makeText(this,"Could not download highscores",Toast.LENGTH_LONG).show();
    }

    // Order the Highscores to rank them nicely in the ListView
    private ArrayList<Highscore> orderToRank(ArrayList<Highscore> highscores) {
        Comparator<Highscore> comp = new Comparator<Highscore>() {
            @Override
            public int compare(Highscore s1, Highscore s2) {
                return s2.getScore() - s1.getScore();
            }
        };
        Collections.sort(highscores, comp);

        int rank = 1;
        for (Highscore highscore : highscores) {
            highscore.setRank(rank);
            rank++;
        }

        return highscores;
    }
}
