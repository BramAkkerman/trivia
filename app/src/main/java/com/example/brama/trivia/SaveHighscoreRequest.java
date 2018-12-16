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

// This Request class uploads a new highscore account to the API
public class SaveHighscoreRequest {

    Context context;

    public SaveHighscoreRequest (Context context) {
        this.context = context;
    }

    public void postScore(final String name, final String password) {

        String url = "https://ide50-softwair.cs50.io/highscores";
        RequestQueue queue = Volley.newRequestQueue(this.context);

        StringRequest strRequest = new StringRequest(Request.Method.POST, url, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Register failed", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> highscore = new HashMap<>();
                highscore.put("name", name);
                highscore.put("password", password);
                highscore.put("score", "0");
                highscore.put("streak", "0");
                highscore.put("streakscore", "0");
                return highscore;
            }
        };

        queue.add(strRequest);
    }
}
