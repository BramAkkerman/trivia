package com.example.brama.trivia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighscoreAdapter extends ArrayAdapter<Highscore> {

    private Context context;

    public HighscoreAdapter(Context context, int resource, ArrayList<Highscore> highscores) {
        super(context, resource, highscores);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;
        Highscore highscore = getItem(position);

        if (result== null) {
            result= LayoutInflater.from(context).inflate(R.layout.highscore,parent,false);
        }

        TextView rank = result.findViewById(R.id.rank);
        TextView name = result.findViewById(R.id.name);
        TextView score = result.findViewById(R.id.score);

        rank.setText(String.valueOf(highscore.getRank()));
        name.setText(highscore.getName());
        score.setText(String.valueOf(highscore.getScore()));

        return result;
    }
}
