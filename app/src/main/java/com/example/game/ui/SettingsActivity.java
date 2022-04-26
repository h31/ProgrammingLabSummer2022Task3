package com.example.game.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game.R;

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
            overridePendingTransition(R.anim.from_left, R.anim.to_right);
            finish();
        });
    }

    private void setViews() {
        back = findViewById(R.id.back);
    }
}