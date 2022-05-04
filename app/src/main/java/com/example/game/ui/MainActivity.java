package com.example.game.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game.R;

public class MainActivity extends AppCompatActivity {

    private Button newGame;
    private Button settings;
    private Button escape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

        newGame.setOnClickListener(view -> {
            Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
            startActivity(gameActivity);
            overridePendingTransition(R.anim.from_right, R.anim.to_left);
            finish();
        });

        settings.setOnClickListener(view -> {
            Intent settingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsActivity);
            overridePendingTransition(R.anim.from_right, R.anim.to_left);
            finish();
        });

        escape.setOnClickListener(view -> finish());
    }

    private void setViews() {
        newGame = findViewById(R.id.newGame);
        settings = findViewById(R.id.settings);
        escape = findViewById(R.id.escape);
    }
}