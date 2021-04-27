package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.myapplication.Level_3_GuessPicture;
import com.example.myapplication.common.Common;

import java.util.List;

public class GridViewSuggestAdapterL3 extends BaseAdapter {

    protected List<String> suggestSource;
    protected Context context;
    protected Level_3_GuessPicture level_3_guessPicture;

    public GridViewSuggestAdapterL3(List<String> suggestSource, Context context, Level_3_GuessPicture level_3_guessPicture) {
        this.suggestSource = suggestSource;
        this.context = context;
        this.level_3_guessPicture = level_3_guessPicture;
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
                        if (String.valueOf(level_3_guessPicture.answer).
                                contains(suggestSource.get(position))) {
                            char compare = suggestSource.get(position).charAt(0); //Get char
                            for (int i = 0; i < level_3_guessPicture.answer.length; i++) {
                                if (compare == level_3_guessPicture.answer[i]) {
                                    Common.user_submit_answer[i] = compare;
                                }
                            }
                            //Update UI
                            GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(Common.user_submit_answer, context);
                            level_3_guessPicture.gridViewAnswer.setAdapter(answerAdapter);
                            answerAdapter.notifyDataSetChanged();

                            //Remove from suggest source
                            level_3_guessPicture.suggestSource.set(position, "null");
                            level_3_guessPicture.suggestAdapter = new GridViewSuggestAdapterL3(level_3_guessPicture.suggestSource, context, level_3_guessPicture);
                            level_3_guessPicture.gridViewSuggest.setAdapter(level_3_guessPicture.suggestAdapter);
                            level_3_guessPicture.suggestAdapter.notifyDataSetChanged();
                        }
                        else {
                            //Remove from suggest source
                            level_3_guessPicture.suggestSource.set(position, "null");
                            level_3_guessPicture.suggestAdapter = new GridViewSuggestAdapterL3(level_3_guessPicture.suggestSource, context, level_3_guessPicture);
                            level_3_guessPicture.gridViewSuggest.setAdapter(level_3_guessPicture.suggestAdapter);
                            level_3_guessPicture.suggestAdapter.notifyDataSetChanged();
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
