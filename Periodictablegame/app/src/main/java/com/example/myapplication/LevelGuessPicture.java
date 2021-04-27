package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelGuessPicture extends AppCompatActivity {

    protected Button back_levelButton,game_level1Button,game_level2Button,game_level3Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_guess_picture);

        back_levelButton = findViewById(R.id.back_levelButton);
        game_level1Button = findViewById(R.id.level1);
        game_level2Button = findViewById(R.id.level2);
        game_level3Button = findViewById(R.id.level3);


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

        game_level2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game_level2 = new Intent(getApplicationContext(), Level_2_GuessPicture.class);
                startActivity(game_level2);
            }
        });

        game_level3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game_level3 = new Intent(getApplicationContext(), Level_3_GuessPicture.class);
                startActivity(game_level3);
            }
        });
    }
}