package com.example.brama.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// This activity handles the logging in of the user
public class LoginActivity extends AppCompatActivity {

    private String name;
    private String encodedPassword;

    private TextView nameView;
    private TextView passView;
    private TextView wrongLogin;

    private Boolean logIn = false;
    private Boolean register = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameView = findViewById(R.id.editName);
        passView = findViewById(R.id.editPassword);
        wrongLogin = findViewById(R.id.wrongLogin);

        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new loginClick());

        Button register = findViewById(R.id.registerButton);
        register.setOnClickListener(new loginClick());
    }

    /*
     * When clicked on the login or register button, check if no field is empty anymore and read
     * the fields. Check which button is clicked, and set the booleans so that they match the
     * button clicked. Request the Highscores from the API.
     */
    private class loginClick implements View.OnClickListener, ViewHighscoreRequest.Callback {
        @Override
        public void onClick(View v) {
            name = nameView.getText().toString();
            String password = passView.getText().toString();
            encodedPassword = EncodePassword(password);

            if (name.trim().isEmpty() && password.trim().isEmpty()) {
                String namePasswordWrong = "Fill in name and password";
                wrongLogin.setText(namePasswordWrong);
                wrongLogin.setVisibility(View.VISIBLE);
            } else {
                switch (v.getId()) {
                    case R.id.registerButton:
                        register = true;
                        logIn = false;
                        break;
                    case R.id.loginButton:
                        logIn = true;
                        register = false;
                        break;
                }

                wrongLogin.setVisibility(View.INVISIBLE);

                ViewHighscoreRequest rq = new ViewHighscoreRequest(LoginActivity.this);
                rq.getHighscores(this);
            }
        }

        /*
         * If login button is clicked, check if there is a Highscore with the same name and the same
         * password to log in. If register button is clicked, check if there is a Highscore with the
         * same name to deny the user to register under that name. If the criteria are met, the
         * login will be made and saved to an ArrayList<Login> to put into the intent, and start
         * the MainActivity again to go back the the menu.
         */
        @Override
        public void gotHighscores(ArrayList<Highscore> highscores) {
            Boolean success = true;
            int id = 0;
            int score = 0;
            int streak = 0;
            int streakScore = 0;

            // To register an account on the next following id, remember the last id
            int lastId = 0;

            for (Highscore highscore : highscores) {
                lastId = highscore.getId();
                if (logIn) {
                    if (name.equals(highscore.getName())) {
                        if (encodedPassword.equals(highscore.getPassword())) {
                            score = highscore.getScore();
                            streak = highscore.getStreak();
                            streakScore = highscore.getStreakScore();
                            id = highscore.getId();
                            success = true;
                            break;
                        } else {
                            String passwordWrong = "Wrong password!";
                            wrongLogin.setText(passwordWrong);
                            success = false;
                            break;
                        }
                    } else {
                        String noSuchUser = "No such user!";
                        wrongLogin.setText(noSuchUser);
                        success = false;
                    }
                } else if (register) {
                    if (name.equals(highscore.getName())) {
                        String nameAlreadyTaken = "Name already exists!";
                        wrongLogin.setText(nameAlreadyTaken);
                        success = false;
                    }
                }
            }

            if (success) {
                Toast.makeText(LoginActivity.this,"Login successful",Toast.LENGTH_LONG).show();
                if (register) {
                    SaveHighscoreRequest saveRequest = new SaveHighscoreRequest(LoginActivity.this);
                    saveRequest.postScore(name, encodedPassword);
                    id = lastId+1;
                }

                Highscore login = new Highscore(score,name,encodedPassword,streak,streakScore,id);
                ArrayList<Highscore> logins = new ArrayList<>();
                logins.add(login);

                Intent menu = new Intent(LoginActivity.this, MainActivity.class);
                menu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                menu.putParcelableArrayListExtra("logins",logins);
                startActivity(menu);

            } else {
                wrongLogin.setVisibility(View.VISIBLE);
                logIn = false;
                register = false;
            }
        }

        @Override
        public void gotHighscoresError(String message) {
            Toast.makeText(LoginActivity.this,message, Toast.LENGTH_LONG).show();
        }

        // This function is used to encode the password in a simple manner just so not everybody
        // can read the password from the API.
        private String EncodePassword(String password) {
            String newPassword = "";
            for (char item : password.toCharArray()) {
                int asciiItem = (int) item;
                asciiItem += 3;
                newPassword += Character.toString((char) asciiItem);
            }
            return newPassword;
        }
    }
}
