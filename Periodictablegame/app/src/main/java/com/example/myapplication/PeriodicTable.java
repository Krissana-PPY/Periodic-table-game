package com.example.myapplication;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.example.myapplication.R.*;

public class PeriodicTable extends AppCompatActivity implements SelectShowDialog.okClickListener {
    protected Button initialButton, detailsButton, atomicButton, weightButton;
    Dialog detailDialog;
    protected TextView[] elmentTextview = new TextView[118];
    protected String[] initial, weight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_periodictable);
        detailDialog = new Dialog(this);
        initial = getResources().getStringArray(array.initials);
        weight = getResources().getStringArray(array.weight);
        if (savedInstanceState != null) {
            initialButton = (Button) findViewById(id.Intitials_button);
            getTextView();

            initialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setToInitial();
                }
            });
            detailsButton = (Button) findViewById(id.details_button);
            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailDialog.setContentView(layout.details_dialog);
                    detailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                    detailDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    detailDialog.show();
                }
            });
            weightButton = (Button) findViewById(id.weight_button);
            weightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setToWeight();
                }
            });
            atomicButton = (Button) findViewById(id.atomic_buttons);
            atomicButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setToAtomicNum();
                }
            });
        }
    }

//    public void openShowDialog() {
//        SelectShowDialog selectShow = new SelectShowDialog();
//        selectShow.show(getSupportFragmentManager(), "select show dialog");
//    }
    public void getTextView() {
        elmentTextview[0] = findViewById(id.elment1);
        elmentTextview[1] = findViewById(id.elment2);
        elmentTextview[2] = findViewById(id.elment3);
        elmentTextview[3] = findViewById(id.elment4);
        elmentTextview[4] = findViewById(id.elment5);
        elmentTextview[5] = findViewById(id.elment6);
        elmentTextview[6] = findViewById(id.elment7);
        elmentTextview[7] = findViewById(id.elment8);
        elmentTextview[8] = findViewById(id.elment9);
        elmentTextview[9] = findViewById(id.elment10);
        elmentTextview[10] = findViewById(id.elment11);
        elmentTextview[11] = findViewById(id.elment12);
        elmentTextview[12] = findViewById(id.elment13);
        elmentTextview[13] = findViewById(id.elment14);
        elmentTextview[14] = findViewById(id.elment15);
        elmentTextview[15] = findViewById(id.elment16);
        elmentTextview[16] = findViewById(id.elment17);
        elmentTextview[17] = findViewById(id.elment18);
        elmentTextview[18] = findViewById(id.elment19);
        elmentTextview[19] = findViewById(id.elment20);
        elmentTextview[20] = findViewById(id.elment21);
        elmentTextview[21] = findViewById(id.elment22);
        elmentTextview[22] = findViewById(id.elment23);
        elmentTextview[23] = findViewById(id.elment24);
        elmentTextview[24] = findViewById(id.elment25);
        elmentTextview[25] = findViewById(id.elment26);
        elmentTextview[26] = findViewById(id.elment27);
        elmentTextview[27] = findViewById(id.elment28);
        elmentTextview[28] = findViewById(id.elment29);
        elmentTextview[29] = findViewById(id.elment30);
        elmentTextview[30] = findViewById(id.elment31);
        elmentTextview[31] = findViewById(id.elment32);
        elmentTextview[32] = findViewById(id.elment33);
        elmentTextview[33] = findViewById(id.elment34);
        elmentTextview[34] = findViewById(id.elment35);
        elmentTextview[35] = findViewById(id.elment36);
        elmentTextview[36] = findViewById(id.elment37);
        elmentTextview[37] = findViewById(id.elment38);
        elmentTextview[38] = findViewById(id.elment39);
        elmentTextview[39] = findViewById(id.elment40);
        elmentTextview[40] = findViewById(id.elment41);
        elmentTextview[41] = findViewById(id.elment42);
        elmentTextview[42] = findViewById(id.elment43);
        elmentTextview[43] = findViewById(id.elment44);
        elmentTextview[44] = findViewById(id.elment45);
        elmentTextview[45] = findViewById(id.elment46);
        elmentTextview[46] = findViewById(id.elment47);
        elmentTextview[47] = findViewById(id.elment48);
        elmentTextview[48] = findViewById(id.elment49);
        elmentTextview[49] = findViewById(id.elment50);
        elmentTextview[50] = findViewById(id.elment51);
        elmentTextview[51] = findViewById(id.elment52);
        elmentTextview[52] = findViewById(id.elment53);
        elmentTextview[53] = findViewById(id.elment54);
        elmentTextview[54] = findViewById(id.elment55);
        elmentTextview[55] = findViewById(id.elment56);
        elmentTextview[56] = findViewById(id.elment57);
        elmentTextview[57] = findViewById(id.elment58);
        elmentTextview[58] = findViewById(id.elment59);
        elmentTextview[59] = findViewById(id.elment60);
        elmentTextview[60] = findViewById(id.elment61);
        elmentTextview[61] = findViewById(id.elment62);
        elmentTextview[62] = findViewById(id.elment63);
        elmentTextview[63] = findViewById(id.elment64);
        elmentTextview[64] = findViewById(id.elment65);
        elmentTextview[65] = findViewById(id.elment66);
        elmentTextview[66] = findViewById(id.elment67);
        elmentTextview[67] = findViewById(id.elment68);
        elmentTextview[68] = findViewById(id.elment69);
        elmentTextview[69] = findViewById(id.elment70);
        elmentTextview[70] = findViewById(id.elment71);
        elmentTextview[71] = findViewById(id.elment72);
        elmentTextview[72] = findViewById(id.elment73);
        elmentTextview[73] = findViewById(id.elment74);
        elmentTextview[74] = findViewById(id.elment75);
        elmentTextview[75] = findViewById(id.elment76);
        elmentTextview[76] = findViewById(id.elment77);
        elmentTextview[77] = findViewById(id.elment78);
        elmentTextview[78] = findViewById(id.elment79);
        elmentTextview[79] = findViewById(id.elment80);
        elmentTextview[80] = findViewById(id.elment81);
        elmentTextview[81] = findViewById(id.elment82);
        elmentTextview[82] = findViewById(id.elment83);
        elmentTextview[83] = findViewById(id.elment84);
        elmentTextview[84] = findViewById(id.elment85);
        elmentTextview[85] = findViewById(id.elment86);
        elmentTextview[86] = findViewById(id.elment87);
        elmentTextview[87] = findViewById(id.elment88);
        elmentTextview[88] = findViewById(id.elment89);
        elmentTextview[89] = findViewById(id.elment90);
        elmentTextview[90] = findViewById(id.elment91);
        elmentTextview[91] = findViewById(id.elment92);
        elmentTextview[92] = findViewById(id.elment93);
        elmentTextview[93] = findViewById(id.elment94);
        elmentTextview[94] = findViewById(id.elment95);
        elmentTextview[95] = findViewById(id.elment96);
        elmentTextview[96] = findViewById(id.elment97);
        elmentTextview[97] = findViewById(id.elment98);
        elmentTextview[98] = findViewById(id.elment99);
        elmentTextview[99] = findViewById(id.elment100);
        elmentTextview[100] = findViewById(id.elment101);
        elmentTextview[101] = findViewById(id.elment102);
        elmentTextview[102] = findViewById(id.elment103);
        elmentTextview[103] = findViewById(id.elment104);
        elmentTextview[104] = findViewById(id.elment105);
        elmentTextview[105] = findViewById(id.elment106);
        elmentTextview[106] = findViewById(id.elment107);
        elmentTextview[107] = findViewById(id.elment108);
        elmentTextview[108] = findViewById(id.elment109);
        elmentTextview[109] = findViewById(id.elment110);
        elmentTextview[110] = findViewById(id.elment111);
        elmentTextview[111] = findViewById(id.elment112);
        elmentTextview[112] = findViewById(id.elment113);
        elmentTextview[113] = findViewById(id.elment114);
        elmentTextview[114] = findViewById(id.elment115);
        elmentTextview[115] = findViewById(id.elment116);
        elmentTextview[116] = findViewById(id.elment117);
        elmentTextview[117] = findViewById(id.elment118);
    }

    public void setToAtomicNum () {
        for (int i = 0; i < 118; i++) {
            elmentTextview[i].setText(String.valueOf(i+1));
            if (i >= 99) {
                elmentTextview[i].setTextSize(16);
            }
        }
    }

    public void setToInitial() {
        for (int i = 0; i < 118; i++) {
            elmentTextview[i].setText(initial[i]);
            elmentTextview[i].setTextSize(24);
        }
    }

    public void setToWeight() {
        for (int i = 0; i < 118; i++) {
            elmentTextview[i].setText(weight[i]);
            elmentTextview[i].setTextSize(16);
        }
    }

    @Override
    public void onOkClick(int n) {
        if (n == 0) {
            setToInitial();
        }else if (n == 1 ) {
            setToAtomicNum();
        }else if (n == 2) {
            setToWeight();
        }
    }
}
