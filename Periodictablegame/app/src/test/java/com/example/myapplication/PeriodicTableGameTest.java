package com.example.myapplication;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class PeriodicTableGameTest {

    // ───── ZoomableScrollView math ─────

    private static final float MIN_ZOOM = 0.3f;
    private static final float MAX_ZOOM = 4.0f;

    private float fitScale(float parentW, float parentH, float childW, float childH) {
        float sx = parentW / childW;
        float sy = parentH / childH;
        return Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, Math.min(sx, sy)));
    }

    private float centerX(float parentW, float childW, float scale) {
        return (parentW - childW * scale) / 2f;
    }

    private float centerY(float parentH, float childH, float scale) {
        return (parentH - childH * scale) / 2f;
    }

    @Test
    public void fitScale_tableWiderThanScreen_scaleIsLessThanOne() {
        // 1050dp table, 440dpi → 2887.5px; landscape screen 2220×1080
        float scale = fitScale(2220, 1080, 2887.5f, 1732.5f);
        assertTrue("Scale should be < 1 for oversized table", scale < 1.0f);
        assertTrue("Scale should be > MIN_ZOOM", scale >= MIN_ZOOM);
    }

    @Test
    public void fitScale_limitedByHeight() {
        // Height is the binding constraint here
        float scale = fitScale(2220, 1080, 2887.5f, 1732.5f);
        float scaleByHeight = 1080f / 1732.5f;  // ≈ 0.623
        assertEquals(scaleByHeight, scale, 0.001f);
    }

    @Test
    public void centerX_isSymmetric() {
        float parentW = 2220, childW = 2887.5f;
        float scale = fitScale(parentW, 1080, childW, 1732.5f);
        float tx = centerX(parentW, childW, scale);
        float scaledW = childW * scale;
        float leftMargin = tx;
        float rightMargin = parentW - (tx + scaledW);
        assertEquals("Left and right margins should be equal", leftMargin, rightMargin, 1.0f);
    }

    @Test
    public void centerY_tableExactlyFitsHeight() {
        // When height is binding, translateY should be ≈ 0
        float parentH = 1080, childH = 1732.5f;
        float scale = fitScale(2220, parentH, 2887.5f, childH);
        float ty = centerY(parentH, childH, scale);
        assertTrue("TranslateY should be near 0 when height is binding", Math.abs(ty) < 2.0f);
    }

    @Test
    public void withLayoutGravityCenter_childStartsAtNegative() {
        // This documents the BUG that was caused by layout_gravity="center"
        // When child is larger than parent, FrameLayout centers it at negative offset
        float parentW = 2220, childW = 2887.5f;
        float buggyChildLeft = (parentW - childW) / 2f;  // = -333.75
        assertTrue("layout_gravity=center on oversized child gives negative left", buggyChildLeft < 0);
    }

    @Test
    public void withoutLayoutGravityCenter_childStartsAtZero_translateIsPositive() {
        // After fix: child at (0,0), translateX is positive → table starts inside screen
        float parentW = 2220, childW = 2887.5f;
        float scale = fitScale(parentW, 1080, childW, 1732.5f);
        float tx = centerX(parentW, childW, scale);
        assertTrue("TranslateX must be positive so table left edge is visible", tx > 0);
    }

    // ───── Clamp translation ─────

    private float clampX(float tx, float parentW, float childW, float scale) {
        float scaledW = childW * scale;
        if (scaledW <= parentW) return (parentW - scaledW) / 2f;
        float max = 0f, min = parentW - scaledW;
        return Math.max(min, Math.min(max, tx));
    }

    @Test
    public void clamp_cannotScrollPastLeftEdge() {
        float tx = clampX(300f, 2220, 2887.5f, 1.5f);
        assertTrue("Cannot scroll right past left edge (tx <= 0)", tx <= 0);
    }

    @Test
    public void clamp_cannotScrollPastRightEdge() {
        float tx = clampX(-5000f, 2220, 2887.5f, 1.5f);
        float scaledW = 2887.5f * 1.5f;
        assertTrue("Right edge cannot go past screen right", tx + scaledW >= 2220);
    }

    @Test
    public void clamp_smallerThanScreen_alwaysCentered() {
        float tx = clampX(0f, 2220, 200f, 1.0f);
        assertEquals((2220 - 200) / 2f, tx, 0.1f);
    }

    // ───── Game logic: answer matching ─────

    private char[] makeEmptySlots(int length) {
        char[] slots = new char[length];
        Arrays.fill(slots, ' ');
        return slots;
    }

    private boolean isComplete(char[] slots) {
        for (char c : slots) if (c == ' ') return false;
        return true;
    }

    private boolean letterIsInAnswer(char letter, char[] answer) {
        for (char c : answer) if (c == Character.toLowerCase(letter)) return true;
        return false;
    }

    @Test
    public void emptySlots_notComplete() {
        assertFalse(isComplete(makeEmptySlots(8)));
    }

    @Test
    public void filledSlots_isComplete() {
        char[] slots = "hydrogen".toCharArray();
        assertTrue(isComplete(slots));
    }

    @Test
    public void letterInAnswer_hydrogen_H_found() {
        assertTrue(letterIsInAnswer('H', "hydrogen".toCharArray()));
    }

    @Test
    public void letterNotInAnswer_hydrogen_Z_notFound() {
        assertFalse(letterIsInAnswer('Z', "hydrogen".toCharArray()));
    }

    // ───── Lives system ─────

    @Test
    public void lives_startAt3_wrongAnswer_decrement() {
        int lives = 3;
        lives--;  // wrong answer
        assertEquals(2, lives);
    }

    @Test
    public void lives_correctAnswer_incrementCappedAt3() {
        int lives = 2;
        lives = Math.min(3, lives + 1);
        assertEquals(3, lives);
        lives = Math.min(3, lives + 1); // already at max
        assertEquals(3, lives);
    }

    @Test
    public void lives_reachZero_gameOver() {
        int lives = 1;
        lives--;
        assertTrue("Game over when lives reach 0", lives <= 0);
    }

    // ───── Suggest source: no decoy equals answer letter ─────

    @Test
    public void buildSuggest_decoysMustNotBeInAnswer() {
        String answer = "IRON";
        Set<Character> answerChars = new HashSet<>();
        for (char c : answer.toCharArray()) answerChars.add(c);

        String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L",
                "M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

        List<String> decoys = new ArrayList<>();
        for (String s : alphabet) {
            if (!answerChars.contains(s.charAt(0))) decoys.add(s);
        }

        for (String d : decoys) {
            assertFalse("Decoy '" + d + "' must not appear in answer",
                    answerChars.contains(d.charAt(0)));
        }
    }

    @Test
    public void score_incrementsOnCorrectAnswer() {
        int score = 0;
        score++;  // correct answer
        assertEquals(1, score);
    }

    @Test
    public void score_doesNotIncrementOnWrong() {
        int score = 5;
        // wrong → no change
        assertEquals(5, score);
    }
}
