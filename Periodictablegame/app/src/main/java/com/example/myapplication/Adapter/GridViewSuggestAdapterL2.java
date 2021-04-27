package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.myapplication.Level_2_GuessPicture;
import com.example.myapplication.common.Common;

import java.util.List;

public class GridViewSuggestAdapterL2 extends BaseAdapter {

    protected List<String> suggestSource;
    protected Context context;
    protected Level_2_GuessPicture level_2_guessPicture;

    public GridViewSuggestAdapterL2(List<String> suggestSource, Context context, Level_2_GuessPicture level_2_guessPicture) {
        this.suggestSource = suggestSource;
        this.context = context;
        this.level_2_guessPicture = level_2_guessPicture;
    }

    @Override
    public int getCount() {
        return suggestSource.size();
    }

    @Override
    public Object getItem(int position) {
        return suggestSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;
        if (convertView == null) {
            if (suggestSource.get(position).equals("null")) {
                button = new Button(context);
                button.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
                button.setPadding(8, 8, 8, 8);
                button.setBackgroundColor(Color.DKGRAY);
            }
            else {
                button = new Button(context);
                button.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
                button.setPadding(8, 8, 8, 8);
                button.setBackgroundColor(Color.DKGRAY);
                button.setTextColor(Color.YELLOW);
                button.setText(suggestSource.get(position));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //If correct answer contains character user selectec
                        if (String.valueOf(level_2_guessPicture.answer).
                                contains(suggestSource.get(position))) {
                            char compare = suggestSource.get(position).charAt(0); //Get char
                            for (int i = 0; i < level_2_guessPicture.answer.length; i++) {
                                if (compare == level_2_guessPicture.answer[i]) {
                                    Common.user_submit_answer[i] = compare;
                                }
                            }
                            //Update UI
                            GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(Common.user_submit_answer, context);
                            level_2_guessPicture.gridViewAnswer.setAdapter(answerAdapter);
                            answerAdapter.notifyDataSetChanged();

                            //Remove from suggest source
                            level_2_guessPicture.suggestSource.set(position, "null");
                            level_2_guessPicture.suggestAdapter = new GridViewSuggestAdapterL2(level_2_guessPicture.suggestSource, context, level_2_guessPicture);
                            level_2_guessPicture.gridViewSuggest.setAdapter(level_2_guessPicture.suggestAdapter);
                            level_2_guessPicture.suggestAdapter.notifyDataSetChanged();
                        }
                        else {
                            //Remove from suggest source
                            level_2_guessPicture.suggestSource.set(position, "null");
                            level_2_guessPicture.suggestAdapter = new GridViewSuggestAdapterL2(level_2_guessPicture.suggestSource, context, level_2_guessPicture);
                            level_2_guessPicture.gridViewSuggest.setAdapter(level_2_guessPicture.suggestAdapter);
                            level_2_guessPicture.suggestAdapter.notifyDataSetChanged();
                        }
                    }
                });

            }
        }
        else {
            button = (Button) convertView;
        }
        return button;
    }
}
