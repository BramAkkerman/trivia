package com.example.brama.trivia;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

// This Request class updates the highscore of a given id
public class PutHighscoreRequest {
    Context context;

    public PutHighscoreRequest (Context context) {
        this.context = context;
    }

    public void putScore(int intscore, int intstreak, int intstreakScore, int id) {
        final String score = String.valueOf(intscore);
        final String streak = String.valueOf(intstreak);
        final String streakScore = String.valueOf(intstreakScore);

        String url = "https://ide50-softwair.cs50.io/highscores/"+String.valueOf(id);
        RequestQueue queue = Volley.newRequestQueue(this.context);

        StringRequest strRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Score uploaded", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Score upload failed", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> highscore = new HashMap<String, String>();
                highscore.put("score", score);
                highscore.put("streak", streak);
                highscore.put("streakscore", streakScore);
                return highscore;
            }
        };

        queue.add(strRequest);
    }
}
