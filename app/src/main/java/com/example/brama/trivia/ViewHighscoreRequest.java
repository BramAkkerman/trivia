package com.example.brama.trivia;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// This Request class downloads all the Highscores from the API and returns them in an
// ArrayList<Highscore>.
public class ViewHighscoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener {

    private Context context;
    private Callback activity;

    @Override
    public void onErrorResponse(VolleyError error) {
        this.activity.gotHighscoresError(error.getMessage());
    }

    @Override
    public void onResponse(JSONArray array) {
        ArrayList<Highscore> highscores = new ArrayList<>();
        try {
            for (int i=0; i<array.length(); i++) {
                JSONObject item = array.getJSONObject(i);

                Highscore highscore = new Highscore(item.getInt("score"),
                        item.getString("name"), item.getString("password"),
                        item.getInt("streak"), item.getInt("streakscore"),
                        item.getInt("id"));
                highscores.add(highscore);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        this.activity.gotHighscores(highscores);
    }

    public interface Callback {
        void gotHighscores(ArrayList<Highscore> highscores);
        void gotHighscoresError(String message);
    }

    public ViewHighscoreRequest(Context context) {
        this.context = context;
    }

    void getHighscores(Callback activity) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://ide50-softwair.cs50.io/highscores";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, this, this);
        queue.add(jsonArrayRequest);
        this.activity = activity;
    }
}
