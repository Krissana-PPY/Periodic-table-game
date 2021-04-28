package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Adapter.GridViewAnswerAdapter;
import com.example.myapplication.Adapter.GridViewSuggestAdapterL1;
import com.example.myapplication.common.Common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Level_1_GuessPicture extends AppCompatActivity {

    public List<String> suggestSource = new ArrayList<>();

    public GridViewAnswerAdapter answerAdapter;
    public GridViewSuggestAdapterL1 suggestAdapter;

    public Button btnSubmit, back_gameButton;

    public GridView gridViewAnswer, gridViewSuggest;

    public ImageView imgViewQuestion;

    int[] image_list = {
      R.drawable.hydrogen, R.drawable.barium, R.drawable.silicon, R.drawable.bismuth,
      R.drawable.lithium, R.drawable.beryllium, R.drawable.antimony, R.drawable.gallium,
      R.drawable.caesium, R.drawable.calcium, R.drawable.arsenic, R.drawable.lead,
      R.drawable.francium, R.drawable.magnesium, R.drawable.astatine, R.drawable.polonium,
      R.drawable.sodium, R.drawable.radium, R.drawable.boron, R.drawable.thallium,
      R.drawable.potassium, R.drawable.strontium, R.drawable.germanium, R.drawable.tin,
      R.drawable.rubidium, R.drawable.tellurium, R.drawable.aluminum, R.drawable.indium,
      R.drawable.bromine, R.drawable.chlorine, R.drawable.fluorine, R.drawable.iodine,
      R.drawable.nitrogen, R.drawable.oxygen, R.drawable.phosphorus, R.drawable.selenium,
      R.drawable.carbon, R.drawable.sulfur, R.drawable.helium, R.drawable.krypton, R.drawable.neon,
      R.drawable.xenon, R.drawable.radon, R.drawable.argon, R.drawable.flerovium,
      R.drawable.livermorium, R.drawable.moscovium, R.drawable.nihonium, R.drawable.oganesson,
      R.drawable.tennessine
    };

    public char[] answer;

    public String correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_1__guess_picture);

        //Init View
        initView();

        back_gameButton = findViewById(R.id.back_gameButton);

        back_gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), LevelGuessPicture.class);
                startActivity(back);
            }
        });
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

                        GridViewSuggestAdapterL1 suggestAdapter = new GridViewSuggestAdapterL1
                                (suggestSource,getApplicationContext(),
                                Level_1_GuessPicture.this);
                        gridViewSuggest.setAdapter(suggestAdapter);
                        suggestAdapter.notifyDataSetChanged();

                        setupList();
                    }
                    else  {
                        Toast.makeText(Level_1_GuessPicture.this, "Incorrect!!!",
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
        suggestAdapter = new GridViewSuggestAdapterL1
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