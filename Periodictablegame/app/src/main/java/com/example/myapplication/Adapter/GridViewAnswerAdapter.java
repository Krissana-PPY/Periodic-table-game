package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class GridViewAnswerAdapter extends BaseAdapter {

    public interface SlotClickListener {
        void onSlotClicked(int position);
    }

    private final char[] slots;
    private final Context context;

    public GridViewAnswerAdapter(char[] slots, Context context) {
        this.slots = slots;
        this.context = context;
    }

    @Override public int getCount() { return slots.length; }
    @Override public Object getItem(int pos) { return slots[pos]; }
    @Override public long getItemId(int pos) { return pos; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button btn = (convertView instanceof Button) ? (Button) convertView : new Button(context);
        btn.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 110));
        btn.setPadding(2, 2, 2, 2);
        btn.setTextSize(16f);
        // Disable per-button click so GridView.setOnItemClickListener handles all positions
        btn.setClickable(false);
        btn.setFocusable(false);

        char c = slots[position];
        if (c == ' ') {
            btn.setText("_");
            btn.setBackgroundColor(0xFF455A64);
            btn.setTextColor(0xFF78909C);
        } else {
            btn.setText(String.valueOf(Character.toUpperCase(c)));
            btn.setBackgroundColor(0xFF2E7D32);
            btn.setTextColor(Color.WHITE);
        }
        return btn;
    }
}
