package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelGuessPicture extends AppCompatActivity {

    protected Button back_levelButton,game_level1Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_guess_picture);

        back_levelButton = findViewById(R.id.back_levelButton);
        game_level1Button = findViewById(R.id.level1);

        back_levelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), Game.class);
                startActivity(back);
            }
        });

        game_level1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game_level1 = new Intent(getApplicationContext(), Level_1_GuessPicture.class);
                startActivity(game_level1);
            }
        });
    }
}