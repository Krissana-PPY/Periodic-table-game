package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button periodicTable_Button, game_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        periodicTable_Button = findViewById(R.id.periodicTableButton);
        game_Button = findViewById(R.id.gameButton);

        game_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game = new Intent(getApplicationContext(), Game.class);
                startActivity(game);
            }
        });

        periodicTable_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent periodicTable = new Intent(getApplicationContext(), PeriodicTable.class);
                startActivity(periodicTable);
            }
        });
    }
}