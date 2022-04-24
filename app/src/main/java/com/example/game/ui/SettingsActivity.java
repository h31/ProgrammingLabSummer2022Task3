package com.example.game.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.myapplication.R;

public class SettingsActivity extends AppCompatActivity {

    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setViews();

        back.setOnClickListener(view -> {
            Intent mainActivity = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        });
    }

    private void setViews() {
        back = findViewById(R.id.back);
    }
}