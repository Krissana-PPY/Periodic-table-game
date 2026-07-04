package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

public class SuggestAdapter extends BaseAdapter {

    public interface LetterClickListener {
        void onLetterClicked(String letter, int position);
    }

    private static final int[] COLORS = {
        0xFFEF5350, // Red
        0xFFEC407A, // Pink
        0xFFAB47BC, // Purple
        0xFF7E57C2, // Deep Purple
        0xFF5C6BC0, // Indigo
        0xFF42A5F5, // Blue
        0xFF26C6DA, // Cyan
        0xFF26A69A, // Teal
        0xFF66BB6A, // Green
        0xFFFFA726, // Orange
        0xFFFF7043, // Deep Orange
        0xFF8D6E63, // Brown
    };

    private final List<String> source;
    private final Context context;

    public SuggestAdapter(List<String> source, Context context) {
        this.source = source;
        this.context = context;
    }

    @Override public int getCount() { return source.size(); }
    @Override public Object getItem(int pos) { return source.get(pos); }
    @Override public long getItemId(int pos) { return pos; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button btn = (convertView instanceof Button) ? (Button) convertView : new Button(context);
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));
        btn.setPadding(4, 4, 4, 4);
        btn.setTextSize(18f);
        btn.setTypeface(null, Typeface.BOLD);
        // Disable per-button click so GridView.setOnItemClickListener handles all positions
        btn.setClickable(false);
        btn.setFocusable(false);

        String letter = source.get(position);
        if ("null".equals(letter)) {
            btn.setText("");
            btn.setBackground(null);
        } else {
            btn.setText(letter.toUpperCase());
            btn.setTextColor(Color.WHITE);

            int color = COLORS[position % COLORS.length];
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(color);
            drawable.setCornerRadius(20f);
            btn.setBackground(drawable);
        }
        return btn;
    }
}
