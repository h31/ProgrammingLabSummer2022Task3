package com.example.game.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private Button newGame;
    private Button settings;
    private Button escape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

        settings.setOnClickListener(view -> {
            Intent settingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsActivity);
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