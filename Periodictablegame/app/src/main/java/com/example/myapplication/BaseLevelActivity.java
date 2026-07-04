package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.GridViewAnswerAdapter;
import com.example.myapplication.Adapter.SuggestAdapter;
import com.example.myapplication.common.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class BaseLevelActivity extends AppCompatActivity
        implements SuggestAdapter.LetterClickListener, GridViewAnswerAdapter.SlotClickListener {

    public List<String> suggestSource = new ArrayList<>();
    public GridViewAnswerAdapter answerAdapter;
    public SuggestAdapter suggestAdapter;
    public GridView gridViewAnswer, gridViewSuggest;
    public ImageView imgViewQuestion;

    public char[] answer;
    public String correct_answer;

    private int score = 0;
    private int lives = 3;
    private TextView tvScore;
    private TextView tvHearts;

    protected abstract int[] getImageList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_1__guess_picture);

        gridViewAnswer  = findViewById(R.id.gridViewAnswer);
        gridViewSuggest = findViewById(R.id.gridViewSuggest);
        imgViewQuestion = findViewById(R.id.imgVIIIA);
        tvScore         = findViewById(R.id.tvScore);
        tvHearts        = findViewById(R.id.tvHearts);

        findViewById(R.id.back_gameButton).setOnClickListener(v -> {
            finish();
            startActivity(new Intent(this, LevelGuessPicture.class));
        });

        gridViewSuggest.setOnItemClickListener((parent, view, position, id) -> {
            String letter = suggestSource.get(position);
            if (letter != null && !"null".equals(letter)) {
                onLetterClicked(letter, position);
            }
        });

        gridViewAnswer.setOnItemClickListener((parent, view, position, id) ->
                onSlotClicked(position));

        setupQuestion();
    }

    protected void setupQuestion() {
        int[] imageList = getImageList();
        if (imageList == null || imageList.length == 0) return;
        int imageSelected = imageList[new Random().nextInt(imageList.length)];
        imgViewQuestion.setImageResource(imageSelected);

        correct_answer = getResources().getResourceName(imageSelected);
        correct_answer = correct_answer.substring(correct_answer.lastIndexOf('/') + 1);
        if (correct_answer.isEmpty()) return;
        answer = correct_answer.toCharArray();

        char[] emptySlots = new char[answer.length];
        Arrays.fill(emptySlots, ' ');
        Common.user_submit_answer = emptySlots;

        buildSuggestSource();
        // Force adapter recreation for new question (new array reference)
        answerAdapter = null;
        suggestAdapter = null;
        refreshAdapters();
        updateHUD();
    }

    private void buildSuggestSource() {
        suggestSource.clear();
        String answerUpper = new String(answer).toUpperCase();

        // One button per unique letter in the answer
        Set<String> added = new HashSet<>();
        for (char c : answer) {
            String letter = String.valueOf(c).toUpperCase();
            if (added.add(letter)) suggestSource.add(letter);
        }

        // Decoys: letters NOT in the answer
        List<String> decoys = new ArrayList<>();
        for (String c : Common.alphabet_character) {
            if (!answerUpper.contains(c)) decoys.add(c);
        }
        Collections.shuffle(decoys);
        int numDecoys = Math.min(added.size(), decoys.size());
        for (int i = 0; i < numDecoys; i++) suggestSource.add(decoys.get(i));

        Collections.shuffle(suggestSource);
    }

    @Override
    public void onLetterClicked(String letter, int suggestPosition) {
        if (letter == null || letter.isEmpty()) return;
        char c = letter.toLowerCase().charAt(0);
        boolean placedAny = false;

        for (int i = 0; i < answer.length; i++) {
            if (answer[i] == c && Common.user_submit_answer[i] == ' ') {
                Common.user_submit_answer[i] = c;
                placedAny = true;
            }
        }

        suggestSource.set(suggestPosition, "null");

        if (!placedAny) {
            lives--;
            vibrate();
            updateHUD();
            Toast.makeText(this, "✗  ไม่มี \"" + letter.toUpperCase() + "\"  -❤️", Toast.LENGTH_SHORT).show();
            if (lives <= 0) {
                showGameOverDialog();
                return;
            }
        }

        refreshAdapters();
        checkComplete();
    }

    @Override
    public void onSlotClicked(int position) {
        char c = Common.user_submit_answer[position];
        if (c == ' ') return;

        for (int i = 0; i < Common.user_submit_answer.length; i++) {
            if (Common.user_submit_answer[i] == c) Common.user_submit_answer[i] = ' ';
        }

        boolean restored = false;
        for (int i = 0; i < suggestSource.size(); i++) {
            if ("null".equals(suggestSource.get(i))) {
                suggestSource.set(i, String.valueOf(c).toUpperCase());
                restored = true;
                break;
            }
        }
        if (!restored) suggestSource.add(String.valueOf(c).toUpperCase());

        refreshAdapters();
    }

    private void checkComplete() {
        for (char c : Common.user_submit_answer) {
            if (c == ' ') return;
        }
        score++;
        lives = Math.min(3, lives + 1);
        updateHUD();
        String name = Character.toUpperCase(correct_answer.charAt(0)) + correct_answer.substring(1);
        showSuccessDialog(name);
    }

    private void updateHUD() {
        if (tvScore != null) tvScore.setText(String.valueOf(score));
        if (tvHearts != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 3; i++) sb.append(i < lives ? "❤️" : "🖤");
            tvHearts.setText(sb.toString());
        }
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (v == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createWaveform(new long[]{0, 150, 80, 150}, -1));
        } else {
            v.vibrate(new long[]{0, 150, 80, 150}, -1);
        }
    }

    private void showSuccessDialog(String elementName) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(64, 56, 64, 56);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(0xFF1A237E);
        bg.setCornerRadius(40f);
        bg.setStroke(4, 0xFF5C6BC0);
        root.setBackground(bg);

        TextView emoji = new TextView(this);
        emoji.setText("🎉");
        emoji.setTextSize(48f);
        emoji.setGravity(Gravity.CENTER);
        root.addView(emoji);

        TextView titleTv = new TextView(this);
        titleTv.setText("ถูกต้อง!");
        titleTv.setTextSize(30f);
        titleTv.setTextColor(0xFF69F0AE);
        titleTv.setTypeface(null, Typeface.BOLD);
        titleTv.setGravity(Gravity.CENTER);
        titleTv.setPadding(0, 8, 0, 4);
        root.addView(titleTv);

        TextView nameTv = new TextView(this);
        nameTv.setText("\"" + elementName + "\"");
        nameTv.setTextSize(22f);
        nameTv.setTextColor(Color.WHITE);
        nameTv.setGravity(Gravity.CENTER);
        nameTv.setPadding(0, 4, 0, 16);
        root.addView(nameTv);

        TextView scoreTv = new TextView(this);
        scoreTv.setText("คะแนน: " + score + "   " + getHeartString());
        scoreTv.setTextSize(18f);
        scoreTv.setTextColor(0xFFFFD740);
        scoreTv.setGravity(Gravity.CENTER);
        scoreTv.setPadding(0, 0, 0, 28);
        root.addView(scoreTv);

        Button nextBtn = new Button(this);
        nextBtn.setText("คำถามถัดไป  ▶");
        nextBtn.setTextSize(17f);
        nextBtn.setTextColor(Color.WHITE);
        nextBtn.setTypeface(null, Typeface.BOLD);
        GradientDrawable btnBg = new GradientDrawable();
        btnBg.setColor(0xFF00C853);
        btnBg.setCornerRadius(32f);
        nextBtn.setBackground(btnBg);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 130);
        btnParams.setMargins(0, 0, 0, 0);
        nextBtn.setLayoutParams(btnParams);
        nextBtn.setOnClickListener(v -> { dialog.dismiss(); setupQuestion(); });
        root.addView(nextBtn);

        dialog.setContentView(root);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void showGameOverDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(64, 56, 64, 56);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(0xFF4A0000);
        bg.setCornerRadius(40f);
        bg.setStroke(4, 0xFFB71C1C);
        root.setBackground(bg);

        TextView emoji = new TextView(this);
        emoji.setText("💀");
        emoji.setTextSize(48f);
        emoji.setGravity(Gravity.CENTER);
        root.addView(emoji);

        TextView titleTv = new TextView(this);
        titleTv.setText("เกมจบแล้ว!");
        titleTv.setTextSize(28f);
        titleTv.setTextColor(0xFFEF5350);
        titleTv.setTypeface(null, Typeface.BOLD);
        titleTv.setGravity(Gravity.CENTER);
        titleTv.setPadding(0, 8, 0, 4);
        root.addView(titleTv);

        String correctName = Character.toUpperCase(correct_answer.charAt(0)) + correct_answer.substring(1);
        TextView answerTv = new TextView(this);
        answerTv.setText("คำตอบคือ \"" + correctName + "\"");
        answerTv.setTextSize(16f);
        answerTv.setTextColor(0xFFFFCCBC);
        answerTv.setGravity(Gravity.CENTER);
        answerTv.setPadding(0, 4, 0, 16);
        root.addView(answerTv);

        TextView scoreTv = new TextView(this);
        scoreTv.setText("คะแนนของคุณ: " + score);
        scoreTv.setTextSize(20f);
        scoreTv.setTextColor(0xFFFFD740);
        scoreTv.setGravity(Gravity.CENTER);
        scoreTv.setPadding(0, 0, 0, 28);
        root.addView(scoreTv);

        // Restart button
        Button restartBtn = new Button(this);
        restartBtn.setText("🔄  เล่นใหม่");
        restartBtn.setTextSize(17f);
        restartBtn.setTextColor(Color.WHITE);
        restartBtn.setTypeface(null, Typeface.BOLD);
        GradientDrawable restartBg = new GradientDrawable();
        restartBg.setColor(0xFFE53935);
        restartBg.setCornerRadius(32f);
        restartBtn.setBackground(restartBg);
        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 120);
        p1.setMargins(0, 0, 0, 16);
        restartBtn.setLayoutParams(p1);
        restartBtn.setOnClickListener(v -> {
            dialog.dismiss();
            lives = 3; score = 0;
            setupQuestion();
        });
        root.addView(restartBtn);

        // Back button
        Button backBtn = new Button(this);
        backBtn.setText("← กลับ");
        backBtn.setTextSize(16f);
        backBtn.setTextColor(Color.WHITE);
        GradientDrawable backBg = new GradientDrawable();
        backBg.setColor(0xFF546E7A);
        backBg.setCornerRadius(32f);
        backBtn.setBackground(backBg);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 110);
        backBtn.setLayoutParams(p2);
        backBtn.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
            startActivity(new Intent(this, LevelGuessPicture.class));
        });
        root.addView(backBtn);

        dialog.setContentView(root);
        dialog.setCancelable(false);
        dialog.show();
    }

    private String getHeartString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) sb.append(i < lives ? "❤️" : "🖤");
        return sb.toString();
    }

    protected void refreshAdapters() {
        if (answerAdapter == null) {
            answerAdapter = new GridViewAnswerAdapter(Common.user_submit_answer, this);
            gridViewAnswer.setAdapter(answerAdapter);
        } else {
            answerAdapter.notifyDataSetChanged();
        }
        if (suggestAdapter == null) {
            suggestAdapter = new SuggestAdapter(suggestSource, this);
            gridViewSuggest.setAdapter(suggestAdapter);
        } else {
            suggestAdapter.notifyDataSetChanged();
        }
    }
}
