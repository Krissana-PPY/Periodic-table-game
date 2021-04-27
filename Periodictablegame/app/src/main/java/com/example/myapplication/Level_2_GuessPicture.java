package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Adapter.GridViewAnswerAdapter;
import com.example.myapplication.Adapter.GridViewSuggestAdapterL2;
import com.example.myapplication.common.Common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Level_2_GuessPicture extends AppCompatActivity {

    public List<String> suggestSource = new ArrayList<>();

    public GridViewAnswerAdapter answerAdapter;
    public GridViewSuggestAdapterL2 suggestAdapter;

    public Button btnSubmit;

    public GridView gridViewAnswer, gridViewSuggest;

    public ImageView imgViewQuestion;

    int[] image_list = {
        R.drawable.copernicium
    };

    public char[] answer;

    public String correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_2__guess_picture);

        //Init View
        initView();
    }

    protected void initView() {
        gridViewAnswer = (GridView)findViewById(R.id.gridViewAnswer);
        gridViewSuggest = (GridView)findViewById(R.id.gridViewSuggest);

        imgViewQuestion = (ImageView)findViewById(R.id.imgVIIIA);

        //Add SetupList Here
        setupList();

        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                for (int i = 0; i < Common.user_submit_answer.length; i++) {
                    result += String.valueOf(Common.user_submit_answer[i]);
                    if (result.equals(correct_answer)) {
                        Toast.makeText(getApplicationContext(),"Finish ! This is " + result,
                                Toast.LENGTH_SHORT).show();

                        //Reset
                        Common.count = 0;
                        Common.user_submit_answer = new char[correct_answer.length()];

                        //set Adapter
                        GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter
                                (setupNullList(),getApplicationContext());
                        gridViewAnswer.setAdapter(answerAdapter);
                        answerAdapter.notifyDataSetChanged();

                        GridViewSuggestAdapterL2 suggestAdapter = new GridViewSuggestAdapterL2
                                (suggestSource,getApplicationContext(),
                                        Level_2_GuessPicture.this);
                        gridViewSuggest.setAdapter(suggestAdapter);
                        suggestAdapter.notifyDataSetChanged();

                        setupList();
                    }
                    else  {
                        Toast.makeText(Level_2_GuessPicture.this, "Incorrect!!!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void setupList() {
        //Random VIIIA
        Random random = new Random();
        int imageSelected = image_list[random.nextInt(image_list.length)];
        imgViewQuestion.setImageResource(imageSelected);

        correct_answer = getResources().getResourceName(imageSelected);
        correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/") + 1);

        answer = correct_answer.toCharArray();

        Common.user_submit_answer = new char[answer.length];

        //Add Answer character to list
        suggestSource.clear();
        for (char item:answer) {
            //Add VIIIA name to list
            suggestSource.add(String.valueOf(item));
        }
        //Random add some character to list
        for (int i = answer.length; i < answer.length * 2; i++) {
            suggestSource.add(Common.alphabet_character[random.nextInt
                    (Common.alphabet_character.length)]);
        }
        //Sort random
        Collections.shuffle(suggestSource);

        //Set for GridView
        answerAdapter = new GridViewAnswerAdapter(setupNullList(), this);
        suggestAdapter = new GridViewSuggestAdapterL2
                (suggestSource, this, this);

        answerAdapter.notifyDataSetChanged();
        suggestAdapter.notifyDataSetChanged();

        gridViewSuggest.setAdapter(suggestAdapter);
        gridViewAnswer.setAdapter(answerAdapter);
    }

    public char[] setupNullList() {
        char result[] = new char[answer.length];
        for (int i = 0; i < answer.length; i++) {
            result[i] = ' ';
        }
        return result;
    }
}