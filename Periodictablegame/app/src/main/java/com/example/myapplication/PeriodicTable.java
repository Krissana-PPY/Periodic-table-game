package com.example.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PeriodicTable extends AppCompatActivity {
    protected Button selectButton, detailsButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodictable);
//        selectButton = (Button) findViewById(R.id.select_show_button);
//        selectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openShowDialog();
//            }
//        });
//        detailsButton = (Button) findViewById(R.id.details_button);
//        detailsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openDetailsDialog();
//            }
//        });
    }
    public void openShowDialog() {
        SelectShowDialog selectShow = new SelectShowDialog();
        selectShow.show(getSupportFragmentManager(), "select show dialog");
    }
    public void openDetailsDialog() {
        DetailsDialog selectShow = new DetailsDialog();
        selectShow.show(getSupportFragmentManager(), "details dialog");
    }


}
