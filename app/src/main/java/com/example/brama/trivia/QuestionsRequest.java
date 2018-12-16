package com.example.brama.trivia;

import android.content.Context;
import android.text.Html;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// This Request class downloads a given amount of questions from the API and returns them in an
// ArrayList<Question>.
public class QuestionsRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private Callback activity;
    private String url;

    @Override
    public void onErrorResponse(VolleyError error) {
        this.activity.gotQuestionsError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            JSONArray array = response.getJSONArray("results");
            for (int i=0; i<array.length(); i++) {
                JSONObject item = array.getJSONObject(i);

                ArrayList<String> incorrect = new ArrayList<>();
                JSONArray incorrect_answers = item.getJSONArray("incorrect_answers");
                for (int j=0; j<incorrect_answers.length(); j++) {
                    incorrect.add(Html.fromHtml(incorrect_answers.getString(j),
                            Html.FROM_HTML_MODE_LEGACY).toString());
                }

                // Make the strings readable
                String questionText = Html.fromHtml(item.getString("question"),
                        Html.FROM_HTML_MODE_LEGACY).toString();
                String correct = Html.fromHtml(item.getString("correct_answer"),
                        Html.FROM_HTML_MODE_LEGACY).toString();

                Question question = new Question(item.getString("category"),
                        item.getString("type"),item.getString("difficulty"),
                        questionText,correct,incorrect);
                questions.add(question);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        this.activity.gotQuestions(questions);
    }

    public interface Callback {
        void gotQuestions(ArrayList<Question> questions);
        void gotQuestionsError(String message);
    }

    public QuestionsRequest(Context context, int amount, String type) {
        this.context = context;
        this.url = "https://opentdb.com/api.php?amount=" + String.valueOf(amount) + "&type=" + type;
    }

    void getQuestions(Callback activity) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);
        this.activity = activity;
    }
}
