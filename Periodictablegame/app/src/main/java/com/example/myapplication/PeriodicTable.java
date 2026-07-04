package com.example.myapplication;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PeriodicTable extends AppCompatActivity {

    // Element data class
    static class Element {
        int atomicNumber;
        String symbol;
        String nameThai;
        String nameEng;
        String atomicWeight;
        String category;
        int categoryColor;
        String group;
        String period;
        String block;
        String state;
        String electronConfig;
        String description;
        int drawableId;

        Element(int atomicNumber, String symbol, String nameThai, String nameEng,
                String atomicWeight, String category, int categoryColor,
                String group, String period, String block, String state,
                String electronConfig, String description, int drawableId) {
            this.atomicNumber = atomicNumber;
            this.symbol = symbol;
            this.nameThai = nameThai;
            this.nameEng = nameEng;
            this.atomicWeight = atomicWeight;
            this.category = category;
            this.categoryColor = categoryColor;
            this.group = group;
            this.period = period;
            this.block = block;
            this.state = state;
            this.electronConfig = electronConfig;
            this.description = description;
            this.drawableId = drawableId;
        }
    }

    private Element[] elements;
    private final ExecutorService mImageLoader = Executors.newSingleThreadExecutor();
    private final Handler mMainHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodictable);

        initElements();
        setupElementClickListeners();

        ImageButton backButton = findViewById(R.id.back_button);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImageLoader.shutdownNow();
    }

    private void initElements() {
        elements = new Element[118];

        // Period 1
        elements[0]   = new Element(1,   "H",   "ไฮโดรเจน",       "Hydrogen",       "1.008",    "Nonmetal",            0xFF4CAF50, "1",   "1", "s", "แก๊ส",  "1s¹",                       "ธาตุที่เบาที่สุดและมีมากที่สุดในเอกภพ เป็นส่วนประกอบของน้ำ (H₂O)",          R.drawable.hydrogen);
        elements[1]   = new Element(2,   "He",  "ฮีเลียม",          "Helium",         "4.003",    "Noble Gas",           0xFF9C27B0, "18",  "1", "s", "แก๊ส",  "1s²",                       "แก๊สมีตระกูล ไม่มีสี ไม่มีกลิ่น ใช้ในบอลลูนและเลเซอร์",                     R.drawable.helium);
        // Period 2
        elements[2]   = new Element(3,   "Li",  "ลิเทียม",          "Lithium",        "6.941",    "Alkali Metal",        0xFFFF5722, "1",   "2", "s", "ของแข็ง","[He] 2s¹",                  "โลหะอัลคาไลที่เบาที่สุด ใช้ในแบตเตอรีลิเทียมไอออน",                        R.drawable.lithium);
        elements[3]   = new Element(4,   "Be",  "เบริลเลียม",       "Beryllium",      "9.012",    "Alkaline Earth Metal", 0xFFFF9800, "2",  "2", "s", "ของแข็ง","[He] 2s²",                  "โลหะอัลคาไลน์เอิร์ธ แข็งแต่เบา ใช้ในอุตสาหกรรมการบิน",                    R.drawable.beryllium);
        elements[4]   = new Element(5,   "B",   "โบรอน",            "Boron",          "10.811",   "Metalloid",           0xFF607D8B, "13",  "2", "p", "ของแข็ง","[He] 2s² 2p¹",              "กึ่งโลหะ ใช้ในการทำแก้วโบโรซิลิเกต และสารกึ่งตัวนำ",                       R.drawable.boron);
        elements[5]   = new Element(6,   "C",   "คาร์บอน",          "Carbon",         "12.011",   "Nonmetal",            0xFF4CAF50, "14",  "2", "p", "ของแข็ง","[He] 2s² 2p²",              "ธาตุพื้นฐานของสิ่งมีชีวิต มีรูปแบบ diamond, graphite, fullerene",           R.drawable.carbon);
        elements[6]   = new Element(7,   "N",   "ไนโตรเจน",         "Nitrogen",       "14.007",   "Nonmetal",            0xFF4CAF50, "15",  "2", "p", "แก๊ส",  "[He] 2s² 2p³",              "ประมาณ 78% ของบรรยากาศโลก จำเป็นต่อสิ่งมีชีวิต",                           R.drawable.nitrogen);
        elements[7]   = new Element(8,   "O",   "ออกซิเจน",         "Oxygen",         "15.999",   "Nonmetal",            0xFF4CAF50, "16",  "2", "p", "แก๊ส",  "[He] 2s² 2p⁴",              "ประมาณ 21% ของบรรยากาศ จำเป็นต่อการหายใจและการเผาไหม้",                   R.drawable.oxygen);
        elements[8]   = new Element(9,   "F",   "ฟลูออรีน",          "Fluorine",       "18.998",   "Halogen",             0xFF00BCD4, "17",  "2", "p", "แก๊ส",  "[He] 2s² 2p⁵",              "ธาตุที่มีอิเล็กโทรเนกาติวิตีสูงที่สุด ทำปฏิกิริยาได้รุนแรง",               R.drawable.fluorine);
        elements[9]   = new Element(10,  "Ne",  "นีออน",             "Neon",           "20.180",   "Noble Gas",           0xFF9C27B0, "18",  "2", "p", "แก๊ส",  "[He] 2s² 2p⁶",              "แก๊สมีตระกูล ใช้ในหลอดไฟนีออน เรืองแสงสีแดง-ส้ม",                          R.drawable.neon);
        // Period 3
        elements[10]  = new Element(11,  "Na",  "โซเดียม",           "Sodium",         "22.990",   "Alkali Metal",        0xFFFF5722, "1",   "3", "s", "ของแข็ง","[Ne] 3s¹",                  "โลหะอัลคาไล ทำปฏิกิริยากับน้ำรุนแรง ใช้ในเกลือแกง (NaCl)",                 R.drawable.sodium);
        elements[11]  = new Element(12,  "Mg",  "แมกนีเซียม",        "Magnesium",      "24.305",   "Alkaline Earth Metal", 0xFFFF9800, "2",  "3", "s", "ของแข็ง","[Ne] 3s²",                  "โลหะเบา ใช้ในอุตสาหกรรมการบินและยานยนต์",                                   R.drawable.magnesium);
        elements[12]  = new Element(13,  "Al",  "อะลูมิเนียม",       "Aluminum",       "26.982",   "Post-transition Metal", 0xFF795548, "13", "3", "p", "ของแข็ง","[Ne] 3s² 3p¹",            "โลหะที่ใช้มากที่สุด เบา ทนทาน ใช้ในภาชนะและอุปกรณ์ต่างๆ",                 R.drawable.aluminum);
        elements[13]  = new Element(14,  "Si",  "ซิลิคอน",           "Silicon",        "28.086",   "Metalloid",           0xFF607D8B, "14",  "3", "p", "ของแข็ง","[Ne] 3s² 3p²",              "กึ่งโลหะ พื้นฐานของอุตสาหกรรมเซมิคอนดักเตอร์และแผงโซลาร์เซลล์",           R.drawable.silicon);
        elements[14]  = new Element(15,  "P",   "ฟอสฟอรัส",          "Phosphorus",     "30.974",   "Nonmetal",            0xFF4CAF50, "15",  "3", "p", "ของแข็ง","[Ne] 3s² 3p³",              "จำเป็นต่อสิ่งมีชีวิต ใช้ในปุ๋ย ไม้ขีดไฟ และระเบิด",                        R.drawable.phosphorus);
        elements[15]  = new Element(16,  "S",   "กำมะถัน",           "Sulfur",         "32.065",   "Nonmetal",            0xFF4CAF50, "16",  "3", "p", "ของแข็ง","[Ne] 3s² 3p⁴",              "ธาตุสีเหลือง ใช้ในการผลิตกรดซัลฟิวริก ยาง และดินปืน",                      R.drawable.sulfur);
        elements[16]  = new Element(17,  "Cl",  "คลอรีน",            "Chlorine",       "35.453",   "Halogen",             0xFF00BCD4, "17",  "3", "p", "แก๊ส",  "[Ne] 3s² 3p⁵",              "แก๊สสีเขียว-เหลือง ใช้ฆ่าเชื้อในน้ำและทำสารฟอกขาว",                        R.drawable.chlorine);
        elements[17]  = new Element(18,  "Ar",  "อาร์กอน",           "Argon",          "39.948",   "Noble Gas",           0xFF9C27B0, "18",  "3", "p", "แก๊ส",  "[Ne] 3s² 3p⁶",              "แก๊สมีตระกูล ใช้ในหลอดไฟ เชื่อมโลหะ และห้องสะอาด",                         R.drawable.argon);
        // Period 4
        elements[18]  = new Element(19,  "K",   "โพแทสเซียม",        "Potassium",      "39.098",   "Alkali Metal",        0xFFFF5722, "1",   "4", "s", "ของแข็ง","[Ar] 4s¹",                  "โลหะอัลคาไล จำเป็นต่อเซลล์ประสาทและกล้ามเนื้อ ใช้ในปุ๋ย",                 R.drawable.potassium);
        elements[19]  = new Element(20,  "Ca",  "แคลเซียม",          "Calcium",        "40.078",   "Alkaline Earth Metal", 0xFFFF9800, "2",  "4", "s", "ของแข็ง","[Ar] 4s²",                  "สำคัญต่อกระดูกและฟัน ใช้ในปูนซีเมนต์และปูนขาว",                             R.drawable.calcium);
        elements[20]  = new Element(21,  "Sc",  "สแคนเดียม",         "Scandium",       "44.956",   "Transition Metal",    0xFF2196F3, "3",   "4", "d", "ของแข็ง","[Ar] 3d¹ 4s²",              "โลหะทรานซิชัน ใช้ในโคมไฟเมทัลฮาไลด์และอุปกรณ์กีฬา",                       R.drawable.scandium);
        elements[21]  = new Element(22,  "Ti",  "ไทเทเนียม",         "Titanium",       "47.867",   "Transition Metal",    0xFF2196F3, "4",   "4", "d", "ของแข็ง","[Ar] 3d² 4s²",              "โลหะเบาแต่แข็งแรง ทนการกัดกร่อน ใช้ในอุตสาหกรรมการบินและทันตกรรม",       R.drawable.titanium);
        elements[22]  = new Element(23,  "V",   "วาเนเดียม",         "Vanadium",       "50.942",   "Transition Metal",    0xFF2196F3, "5",   "4", "d", "ของแข็ง","[Ar] 3d³ 4s²",              "โลหะทรานซิชัน ใช้ในการผลิตเหล็กกล้าและแบตเตอรีวาเนเดียม",                 R.drawable.vanadium);
        elements[23]  = new Element(24,  "Cr",  "โครเมียม",          "Chromium",       "51.996",   "Transition Metal",    0xFF2196F3, "6",   "4", "d", "ของแข็ง","[Ar] 3d⁵ 4s¹",              "ใช้ชุบโลหะให้เงางาม ป้องกันสนิม พบในเหล็กกล้าไร้สนิม",                    R.drawable.chromium);
        elements[24]  = new Element(25,  "Mn",  "แมงกานีส",          "Manganese",      "54.938",   "Transition Metal",    0xFF2196F3, "7",   "4", "d", "ของแข็ง","[Ar] 3d⁵ 4s²",              "ใช้ในการผลิตเหล็กกล้า ถ่านไฟฉาย และปุ๋ย",                                   R.drawable.manganese);
        elements[25]  = new Element(26,  "Fe",  "เหล็ก",             "Iron",           "55.845",   "Transition Metal",    0xFF2196F3, "8",   "4", "d", "ของแข็ง","[Ar] 3d⁶ 4s²",              "โลหะที่ใช้มากที่สุดในโลก ส่วนประกอบหลักของเหล็กหล่อและเหล็กกล้า",         R.drawable.iron);
        elements[26]  = new Element(27,  "Co",  "โคบอลต์",           "Cobalt",         "58.933",   "Transition Metal",    0xFF2196F3, "9",   "4", "d", "ของแข็ง","[Ar] 3d⁷ 4s²",              "ใช้ในแม่เหล็กถาวร แบตเตอรี และสีน้ำเงิน",                                   R.drawable.cobalt);
        elements[27]  = new Element(28,  "Ni",  "นิกเกิล",           "Nickel",         "58.693",   "Transition Metal",    0xFF2196F3, "10",  "4", "d", "ของแข็ง","[Ar] 3d⁸ 4s²",              "ใช้ในการชุบโลหะ เหรียญ และแบตเตอรีนิกเกิล-แคดเมียม",                       R.drawable.nickel);
        elements[28]  = new Element(29,  "Cu",  "ทองแดง",            "Copper",         "63.546",   "Transition Metal",    0xFF2196F3, "11",  "4", "d", "ของแข็ง","[Ar] 3d¹⁰ 4s¹",             "ตัวนำไฟฟ้าที่ดี ใช้ในสายไฟ ท่อน้ำ และเหรียญ",                              R.drawable.copper);
        elements[29]  = new Element(30,  "Zn",  "สังกะสี",           "Zinc",           "65.38",    "Transition Metal",    0xFF2196F3, "12",  "4", "d", "ของแข็ง","[Ar] 3d¹⁰ 4s²",             "ใช้ชุบเหล็กป้องกันสนิม ทำทองเหลือง และสังกะสีแผ่น",                        R.drawable.zinc);
        elements[30]  = new Element(31,  "Ga",  "แกลเลียม",          "Gallium",        "69.723",   "Post-transition Metal", 0xFF795548, "13", "4", "p", "ของแข็ง","[Ar] 3d¹⁰ 4s² 4p¹",       "หลอมละลายที่ใกล้อุณหภูมิห้อง ใช้ใน LED และวงจรอิเล็กทรอนิกส์",            R.drawable.gallium);
        elements[31]  = new Element(32,  "Ge",  "เจอร์เมเนียม",      "Germanium",      "72.630",   "Metalloid",           0xFF607D8B, "14",  "4", "p", "ของแข็ง","[Ar] 3d¹⁰ 4s² 4p²",        "กึ่งโลหะ ใช้ในทรานซิสเตอร์รุ่นแรกและเส้นใยแก้วนำแสง",                     R.drawable.germanium);
        elements[32]  = new Element(33,  "As",  "สารหนู",            "Arsenic",        "74.922",   "Metalloid",           0xFF607D8B, "15",  "4", "p", "ของแข็ง","[Ar] 3d¹⁰ 4s² 4p³",        "กึ่งโลหะที่มีพิษ ใช้ในสารกำจัดแมลงและสารกึ่งตัวนำ",                       R.drawable.arsenic);
        elements[33]  = new Element(34,  "Se",  "ซีลีเนียม",         "Selenium",       "78.971",   "Nonmetal",            0xFF4CAF50, "16",  "4", "p", "ของแข็ง","[Ar] 3d¹⁰ 4s² 4p⁴",        "ใช้ในเซลล์โซลาร์ แก้วสี และอาหารเสริม",                                     R.drawable.selenium);
        elements[34]  = new Element(35,  "Br",  "โบรมีน",            "Bromine",        "79.904",   "Halogen",             0xFF00BCD4, "17",  "4", "p", "ของเหลว","[Ar] 3d¹⁰ 4s² 4p⁵",        "ธาตุที่เป็นของเหลวที่อุณหภูมิห้อง ใช้ในยาและสารหน่วงไฟ",                  R.drawable.bromine);
        elements[35]  = new Element(36,  "Kr",  "คริปทอน",           "Krypton",        "83.798",   "Noble Gas",           0xFF9C27B0, "18",  "4", "p", "แก๊ส",  "[Ar] 3d¹⁰ 4s² 4p⁶",        "แก๊สมีตระกูล ใช้ในหลอดไฟประสิทธิภาพสูงและเลเซอร์",                         R.drawable.krypton);
        // Period 5
        elements[36]  = new Element(37,  "Rb",  "รูบิเดียม",         "Rubidium",       "85.468",   "Alkali Metal",        0xFFFF5722, "1",   "5", "s", "ของแข็ง","[Kr] 5s¹",                  "โลหะอัลคาไล ใช้ในนาฬิกาอะตอมและเซลล์โฟโต้อิเล็กทริก",                     R.drawable.rubidium);
        elements[37]  = new Element(38,  "Sr",  "สตรอนเทียม",        "Strontium",      "87.620",   "Alkaline Earth Metal", 0xFFFF9800, "2",  "5", "s", "ของแข็ง","[Kr] 5s²",                  "ใช้ในดอกไม้ไฟให้สีแดงสด และแม่เหล็กเซรามิก",                               R.drawable.strontium);
        elements[38]  = new Element(39,  "Y",   "อิตเทรียม",         "Yttrium",        "88.906",   "Transition Metal",    0xFF2196F3, "3",   "5", "d", "ของแข็ง","[Kr] 4d¹ 5s²",              "ใช้ใน LED สีขาว จอโทรทัศน์ และวัสดุเซรามิก",                               R.drawable.yttrium);
        elements[39]  = new Element(40,  "Zr",  "เซอร์โคเนียม",      "Zirconium",      "91.224",   "Transition Metal",    0xFF2196F3, "4",   "5", "d", "ของแข็ง","[Kr] 4d² 5s²",              "ทนความร้อนและการกัดกร่อน ใช้ในเตาปฏิกรณ์นิวเคลียร์",                       R.drawable.zirconium);
        elements[40]  = new Element(41,  "Nb",  "ไนโอเบียม",         "Niobium",        "92.906",   "Transition Metal",    0xFF2196F3, "5",   "5", "d", "ของแข็ง","[Kr] 4d⁴ 5s¹",              "ใช้ในเหล็กกล้าชั้นสูงและตัวนำยิ่งยวด",                                      R.drawable.niobium);
        elements[41]  = new Element(42,  "Mo",  "โมลิบดีนัม",        "Molybdenum",     "95.950",   "Transition Metal",    0xFF2196F3, "6",   "5", "d", "ของแข็ง","[Kr] 4d⁵ 5s¹",              "ใช้ในเหล็กกล้าทนความร้อน เครื่องยนต์และหัวเทียน",                           R.drawable.molybdenum);
        elements[42]  = new Element(43,  "Tc",  "เทคนีเชียม",        "Technetium",     "(98)",     "Transition Metal",    0xFF2196F3, "7",   "5", "d", "ของแข็ง","[Kr] 4d⁵ 5s²",              "ธาตุสังเคราะห์ที่เบาที่สุด ใช้ในการตรวจวินิจฉัยทางการแพทย์",               R.drawable.technetium);
        elements[43]  = new Element(44,  "Ru",  "รูทีเนียม",         "Ruthenium",      "101.070",  "Transition Metal",    0xFF2196F3, "8",   "5", "d", "ของแข็ง","[Kr] 4d⁷ 5s¹",              "โลหะแพลทินัมกรุ๊ป ใช้ในตัวเร่งปฏิกิริยาและอุปกรณ์อิเล็กทรอนิกส์",        R.drawable.ruthenium);
        elements[44]  = new Element(45,  "Rh",  "โรเดียม",           "Rhodium",        "102.906",  "Transition Metal",    0xFF2196F3, "9",   "5", "d", "ของแข็ง","[Kr] 4d⁸ 5s¹",              "ใช้ในตัวเร่งปฏิกิริยารถยนต์และเครื่องประดับ",                               R.drawable.rhodium);
        elements[45]  = new Element(46,  "Pd",  "แพลเลเดียม",        "Palladium",      "106.420",  "Transition Metal",    0xFF2196F3, "10",  "5", "d", "ของแข็ง","[Kr] 4d¹⁰",                 "ใช้ในตัวเร่งปฏิกิริยา เครื่องประดับ และอุปกรณ์ทันตกรรม",                   R.drawable.palladium);
        elements[46]  = new Element(47,  "Ag",  "เงิน",              "Silver",         "107.868",  "Transition Metal",    0xFF2196F3, "11",  "5", "d", "ของแข็ง","[Kr] 4d¹⁰ 5s¹",             "ตัวนำไฟฟ้าที่ดีที่สุด ใช้ในเครื่องประดับ ภาพถ่าย และยาฆ่าเชื้อ",          R.drawable.silver);
        elements[47]  = new Element(48,  "Cd",  "แคดเมียม",          "Cadmium",        "112.411",  "Transition Metal",    0xFF2196F3, "12",  "5", "d", "ของแข็ง","[Kr] 4d¹⁰ 5s²",             "ใช้ในแบตเตอรีNiCd และชุบโลหะ มีพิษต่อสิ่งแวดล้อม",                        R.drawable.cadmium);
        elements[48]  = new Element(49,  "In",  "อินเดียม",          "Indium",         "114.818",  "Post-transition Metal", 0xFF795548, "13", "5", "p", "ของแข็ง","[Kr] 4d¹⁰ 5s² 5p¹",       "ใช้ใน ITO สำหรับจอสัมผัสและ LED",                                           R.drawable.indium);
        elements[49]  = new Element(50,  "Sn",  "ดีบุก",             "Tin",            "118.710",  "Post-transition Metal", 0xFF795548, "14", "5", "p", "ของแข็ง","[Kr] 4d¹⁰ 5s² 5p²",       "ใช้ชุบกระป๋องอาหาร ทำบัดกรีและทองสัมฤทธิ์",                                R.drawable.tin);
        elements[50]  = new Element(51,  "Sb",  "พลวง",              "Antimony",       "121.760",  "Metalloid",           0xFF607D8B, "15",  "5", "p", "ของแข็ง","[Kr] 4d¹⁰ 5s² 5p³",        "ใช้ในสารหน่วงไฟ เซมิคอนดักเตอร์และแบตเตอรี",                              R.drawable.antimony);
        elements[51]  = new Element(52,  "Te",  "เทลลูเรียม",        "Tellurium",      "127.600",  "Metalloid",           0xFF607D8B, "16",  "5", "p", "ของแข็ง","[Kr] 4d¹⁰ 5s² 5p⁴",        "ใช้ในแผ่น DVD และเซลล์โซลาร์แบบ CdTe",                                      R.drawable.tellurium);
        elements[52]  = new Element(53,  "I",   "ไอโอดีน",           "Iodine",         "126.904",  "Halogen",             0xFF00BCD4, "17",  "5", "p", "ของแข็ง","[Kr] 4d¹⁰ 5s² 5p⁵",        "จำเป็นต่อการทำงานของต่อมไทรอยด์ ใช้เป็นยาฆ่าเชื้อ",                       R.drawable.iodine);
        elements[53]  = new Element(54,  "Xe",  "ซีนอน",             "Xenon",          "131.293",  "Noble Gas",           0xFF9C27B0, "18",  "5", "p", "แก๊ส",  "[Kr] 4d¹⁰ 5s² 5p⁶",        "แก๊สมีตระกูล ใช้ในหลอดไฟ Xenon และยาสลบ",                                  R.drawable.xenon);
        // Period 6
        elements[54]  = new Element(55,  "Cs",  "ซีเซียม",           "Caesium",        "132.905",  "Alkali Metal",        0xFFFF5722, "1",   "6", "s", "ของแข็ง","[Xe] 6s¹",                  "ใช้ในนาฬิกาอะตอมที่แม่นยำที่สุดในโลก",                                      R.drawable.caesium);
        elements[55]  = new Element(56,  "Ba",  "แบเรียม",           "Barium",         "137.327",  "Alkaline Earth Metal", 0xFFFF9800, "2",  "6", "s", "ของแข็ง","[Xe] 6s²",                  "ใช้ในดอกไม้ไฟให้สีเขียว และสารทึบรังสีในการตรวจทางการแพทย์",              R.drawable.barium);
        elements[56]  = new Element(57,  "La",  "แลนทาเนียม",        "Lanthanum",      "138.905",  "Lanthanide",          0xFFE91E63, "3",   "6", "f", "ของแข็ง","[Xe] 5d¹ 6s²",              "ธาตุแรกของกลุ่มแลนทาไนด์ ใช้ในกล้องและไฟฉายคุณภาพสูง",                    R.drawable.lanthanum);
        elements[57]  = new Element(58,  "Ce",  "ซีเรียม",           "Cerium",         "140.116",  "Lanthanide",          0xFFE91E63, "-",   "6", "f", "ของแข็ง","[Xe] 4f¹ 5d¹ 6s²",          "ใช้ในตัวเร่งปฏิกิริยารถยนต์ และหินจุดไฟ",                                   R.drawable.cerium);
        elements[58]  = new Element(59,  "Pr",  "เพรซีโอดิเมียม",    "Praseodymium",   "140.908",  "Lanthanide",          0xFFE91E63, "-",   "6", "f", "ของแข็ง","[Xe] 4f³ 6s²",              "ใช้ในแม่เหล็กถาวรและแว่นตานักเชื่อม",                                       R.drawable.praseodymium);
        elements[59]  = new Element(60,  "Nd",  "นีโอดิเมียม",       "Neodymium",      "144.242",  "Lanthanide",          0xFFE91E63, "-",   "6", "f", "ของแข็ง","[Xe] 4f⁴ 6s²",              "ใช้ในแม่เหล็ก Nd-Fe-B ที่แรงที่สุด ใช้ในมอเตอร์และลำโพง",                 R.drawable.neodymium);
        elements[60]  = new Element(61,  "Pm",  "โพรมีเทียม",        "Promethium",     "(145)",    "Lanthanide",          0xFFE91E63, "-",   "6", "f", "ของแข็ง","[Xe] 4f⁵ 6s²",              "ธาตุกัมมันตรังสีสังเคราะห์ ใช้ในแบตเตอรีนิวเคลียร์",                       R.drawable.promethium);
        elements[61]  = new Element(62,  "Sm",  "ซาแมเรียม",         "Samarium",       "150.360",  "Lanthanide",          0xFFE91E63, "-",   "6", "f", "ของแข็ง","[Xe] 4f⁶ 6s²",              "ใช้ในแม่เหล็ก Sm-Co และตัวดูดซับนิวตรอนในเตาปฏิกรณ์",                     R.drawable.samarium);
        elements[62]  = new Element(63,  "Eu",  "ยูโรเพียม",         "Europium",       "151.964",  "Lanthanide",          0xFFE91E63, "-",   "6", "f", "ของแข็ง","[Xe] 4f⁷ 6s²",              "ใช้ในฟอสเฟอร์สีแดงและสีน้ำเงินในจอโทรทัศน์และ LED",                        R.drawable.europium);
        elements[63]  = new Element(64,  "Gd",  "แกโดลิเนียม",       "Gadolinium",     "157.250",  "Lanthanide",          0xFFE91E63, "-",   "6", "f", "ของแข็ง","[Xe] 4f⁷ 5d¹ 6s²",          "ใช้เป็นสารทึบแสงใน MRI และดูดซับนิวตรอน",                                   R.drawable.gadolinium);
        elements[64]  = new Element(65,  "Tb",  "เทอร์เบียม",        "Terbium",        "158.925",  "Lanthanide",          0xFFE91E63, "-",   "6", "f", "ของแข็ง","[Xe] 4f⁹ 6s²",              "ใช้ในฟอสเฟอร์สีเขียวและวัสดุ magnetostrictive",                             R.drawable.terbium);
        elements[65]  = new Element(66,  "Dy",  "ดิสโพรเซียม",       "Dysprosium",     "162.500",  "Lanthanide",          0xFFE91E63, "-",   "6", "f", "ของแข็ง","[Xe] 4f¹⁰ 6s²",             "ใช้ในแม่เหล็กถาวรและเลเซอร์",                                               R.drawable.dysprosium);
        elements[66]  = new Element(67,  "Ho",  "โฮลเมียม",          "Holmium",        "164.930",  "Lanthanide",          0xFFE91E63, "-",   "6", "f", "ของแข็ง","[Xe] 4f¹¹ 6s²",             "มีสนามแม่เหล็กสูงที่สุดในธาตุทั้งหมด ใช้ในเลเซอร์ทางการแพทย์",            R.drawable.holmium);
        elements[67]  = new Element(68,  "Er",  "เออร์เบียม",        "Erbium",         "167.259",  "Lanthanide",          0xFFE91E63, "-",   "6", "f", "ของแข็ง","[Xe] 4f¹² 6s²",             "ใช้ในเส้นใยแก้วนำแสงและเลเซอร์ทางการแพทย์ Er:YAG",                         R.drawable.erbium);
        elements[68]  = new Element(69,  "Tm",  "ทูเลียม",           "Thulium",        "168.934",  "Lanthanide",          0xFFE91E63, "-",   "6", "f", "ของแข็ง","[Xe] 4f¹³ 6s²",             "ใช้ในเลเซอร์และแหล่งรังสีเอกซ์แบบพกพา",                                    R.drawable.thulium);
        elements[69]  = new Element(70,  "Yb",  "อิตเทอร์เบียม",     "Ytterbium",      "173.045",  "Lanthanide",          0xFFE91E63, "-",   "6", "f", "ของแข็ง","[Xe] 4f¹⁴ 6s²",             "ใช้ในนาฬิกาอะตอมแม่นยำสูงและเลเซอร์ Yb-fiber",                             R.drawable.ytterbium);
        elements[70]  = new Element(71,  "Lu",  "ลูทีเชียม",         "Lutetium",       "174.967",  "Lanthanide",          0xFFE91E63, "3",   "6", "d", "ของแข็ง","[Xe] 4f¹⁴ 5d¹ 6s²",         "ธาตุสุดท้ายของกลุ่มแลนทาไนด์ ใช้ในเครื่องสแกน PET",                       R.drawable.lutetium);
        elements[71]  = new Element(72,  "Hf",  "แฮฟเนียม",          "Hafnium",        "178.490",  "Transition Metal",    0xFF2196F3, "4",   "6", "d", "ของแข็ง","[Xe] 4f¹⁴ 5d² 6s²",         "ใช้ในเตาปฏิกรณ์นิวเคลียร์และทรานซิสเตอร์ขนาดนาโน",                        R.drawable.hafnium);
        elements[72]  = new Element(73,  "Ta",  "แทนทาลัม",          "Tantalum",       "180.948",  "Transition Metal",    0xFF2196F3, "5",   "6", "d", "ของแข็ง","[Xe] 4f¹⁴ 5d³ 6s²",         "ทนการกัดกร่อนสูง ใช้ในตัวเก็บประจุและอุปกรณ์ทางการแพทย์",                 R.drawable.tantalum);
        elements[73]  = new Element(74,  "W",   "ทังสเตน",           "Tungsten",       "183.840",  "Transition Metal",    0xFF2196F3, "6",   "6", "d", "ของแข็ง","[Xe] 4f¹⁴ 5d⁴ 6s²",         "มีจุดหลอมเหลวสูงที่สุด ใช้ในไส้หลอดไฟและเครื่องมือตัด",                   R.drawable.tungsten);
        elements[74]  = new Element(75,  "Re",  "รีเนียม",           "Rhenium",        "186.207",  "Transition Metal",    0xFF2196F3, "7",   "6", "d", "ของแข็ง","[Xe] 4f¹⁴ 5d⁵ 6s²",         "ใช้ในเครื่องยนต์เจ็ทและตัวเร่งปฏิกิริยาน้ำมัน",                            R.drawable.rhenium);
        elements[75]  = new Element(76,  "Os",  "ออสเมียม",          "Osmium",         "190.230",  "Transition Metal",    0xFF2196F3, "8",   "6", "d", "ของแข็ง","[Xe] 4f¹⁴ 5d⁶ 6s²",         "โลหะที่หนักที่สุด ใช้ในปลายปากกาและเข็มแผ่นเสียง",                         R.drawable.osmium);
        elements[76]  = new Element(77,  "Ir",  "อิริเดียม",         "Iridium",        "192.217",  "Transition Metal",    0xFF2196F3, "9",   "6", "d", "ของแข็ง","[Xe] 4f¹⁴ 5d⁷ 6s²",         "ทนการกัดกร่อนสูงที่สุด ใช้ในหัวเทียนและเข็มฉีดยา",                         R.drawable.iridium);
        elements[77]  = new Element(78,  "Pt",  "แพลทินัม",          "Platinum",       "195.084",  "Transition Metal",    0xFF2196F3, "10",  "6", "d", "ของแข็ง","[Xe] 4f¹⁴ 5d⁹ 6s¹",         "ใช้ในตัวเร่งปฏิกิริยา เครื่องประดับ และอุปกรณ์ทางการแพทย์",               R.drawable.platinum);
        elements[78]  = new Element(79,  "Au",  "ทอง",               "Gold",           "196.967",  "Transition Metal",    0xFF2196F3, "11",  "6", "d", "ของแข็ง","[Xe] 4f¹⁴ 5d¹⁰ 6s¹",        "โลหะมีค่า ตัวนำไฟฟ้าที่ดี ไม่เป็นสนิม ใช้ในเครื่องประดับและอิเล็กทรอนิกส์", R.drawable.gold);
        elements[79]  = new Element(80,  "Hg",  "ปรอท",              "Mercury",        "200.592",  "Transition Metal",    0xFF2196F3, "12",  "6", "d", "ของเหลว","[Xe] 4f¹⁴ 5d¹⁰ 6s²",        "โลหะที่เป็นของเหลวที่อุณหภูมิห้อง ใช้ในเทอร์โมมิเตอร์และหลอดฟลูออเรสเซนต์", R.drawable.mercury);
        elements[80]  = new Element(81,  "Tl",  "แทลเลียม",          "Thallium",       "204.383",  "Post-transition Metal", 0xFF795548, "13", "6", "p", "ของแข็ง","[Xe] 4f¹⁴ 5d¹⁰ 6s² 6p¹",   "มีพิษสูง ใช้ในอิเล็กทรอนิกส์และสารกำจัดสัตว์รบกวน",                      R.drawable.thallium);
        elements[81]  = new Element(82,  "Pb",  "ตะกั่ว",            "Lead",           "207.200",  "Post-transition Metal", 0xFF795548, "14", "6", "p", "ของแข็ง","[Xe] 4f¹⁴ 5d¹⁰ 6s² 6p²",   "ใช้ในแบตเตอรีตะกั่ว-กรด กระสุนปืน และฉนวนรังสี",                          R.drawable.lead);
        elements[82]  = new Element(83,  "Bi",  "บิสมัท",            "Bismuth",        "208.980",  "Post-transition Metal", 0xFF795548, "15", "6", "p", "ของแข็ง","[Xe] 4f¹⁴ 5d¹⁰ 6s² 6p³",   "ใช้ในยาลดกรด (Pepto-Bismol) และสีมุก",                                     R.drawable.bismuth);
        elements[83]  = new Element(84,  "Po",  "โพโลเนียม",         "Polonium",       "(209)",    "Post-transition Metal", 0xFF795548, "16", "6", "p", "ของแข็ง","[Xe] 4f¹⁴ 5d¹⁰ 6s² 6p⁴",   "ธาตุกัมมันตรังสี ค้นพบโดย Marie Curie ใช้ในหัวจรวดนิวเคลียร์",            R.drawable.polonium);
        elements[84]  = new Element(85,  "At",  "แอสทาทีน",          "Astatine",       "(210)",    "Halogen",             0xFF00BCD4, "17",  "6", "p", "ของแข็ง","[Xe] 4f¹⁴ 5d¹⁰ 6s² 6p⁵",   "ฮาโลเจนกัมมันตรังสี หายากที่สุดในธรรมชาติ",                                 R.drawable.astatine);
        elements[85]  = new Element(86,  "Rn",  "เรดอน",             "Radon",          "(222)",    "Noble Gas",           0xFF9C27B0, "18",  "6", "p", "แก๊ส",  "[Xe] 4f¹⁴ 5d¹⁰ 6s² 6p⁶",   "แก๊สมีตระกูลกัมมันตรังสี เกิดจากการสลายตัวของเรเดียม",                     R.drawable.radon);
        // Period 7
        elements[86]  = new Element(87,  "Fr",  "แฟรนเซียม",         "Francium",       "(223)",    "Alkali Metal",        0xFFFF5722, "1",   "7", "s", "ของแข็ง","[Rn] 7s¹",                  "โลหะอัลคาไลกัมมันตรังสี หายากและไม่เสถียร",                                 R.drawable.francium);
        elements[87]  = new Element(88,  "Ra",  "เรเดียม",           "Radium",         "(226)",    "Alkaline Earth Metal", 0xFFFF9800, "2",  "7", "s", "ของแข็ง","[Rn] 7s²",                  "ธาตุกัมมันตรังสี ค้นพบโดย Marie Curie เคยใช้รักษามะเร็ง",                 R.drawable.radium);
        elements[88]  = new Element(89,  "Ac",  "แอกทิเนียม",        "Actinium",       "(227)",    "Actinide",            0xFF673AB7, "3",   "7", "f", "ของแข็ง","[Rn] 6d¹ 7s²",              "ธาตุแรกของกลุ่มแอกทิไนด์ กัมมันตรังสี เรืองแสงในที่มืด",                  R.drawable.actinium);
        elements[89]  = new Element(90,  "Th",  "ทอเรียม",           "Thorium",        "232.038",  "Actinide",            0xFF673AB7, "-",   "7", "f", "ของแข็ง","[Rn] 6d² 7s²",              "กัมมันตรังสี ใช้เป็นเชื้อเพลิงในเตาปฏิกรณ์นิวเคลียร์",                    R.drawable.thorium);
        elements[90]  = new Element(91,  "Pa",  "โพรแทกทิเนียม",     "Protactinium",   "231.036",  "Actinide",            0xFF673AB7, "-",   "7", "f", "ของแข็ง","[Rn] 5f² 6d¹ 7s²",          "กัมมันตรังสีสูง ใช้ในการศึกษาธรณีวิทยา",                                    R.drawable.protactinium);
        elements[91]  = new Element(92,  "U",   "ยูเรเนียม",         "Uranium",        "238.029",  "Actinide",            0xFF673AB7, "-",   "7", "f", "ของแข็ง","[Rn] 5f³ 6d¹ 7s²",          "เชื้อเพลิงหลักของเตาปฏิกรณ์นิวเคลียร์และระเบิดปรมาณู",                    R.drawable.uranium);
        elements[92]  = new Element(93,  "Np",  "เนปทูเนียม",        "Neptunium",      "(237)",    "Actinide",            0xFF673AB7, "-",   "7", "f", "ของแข็ง","[Rn] 5f⁴ 6d¹ 7s²",          "ธาตุทรานส์ยูเรเนียมตัวแรก สังเคราะห์ในเตาปฏิกรณ์",                         R.drawable.neptunium);
        elements[93]  = new Element(94,  "Pu",  "พลูโตเนียม",        "Plutonium",      "(244)",    "Actinide",            0xFF673AB7, "-",   "7", "f", "ของแข็ง","[Rn] 5f⁶ 7s²",              "ใช้ในระเบิดนิวเคลียร์และเชื้อเพลิงนิวเคลียร์",                              R.drawable.plutonium);
        elements[94]  = new Element(95,  "Am",  "อเมริเซียม",        "Americium",      "(243)",    "Actinide",            0xFF673AB7, "-",   "7", "f", "ของแข็ง","[Rn] 5f⁷ 7s²",              "ใช้ในเครื่องตรวจจับควัน",                                                    R.drawable.americium);
        elements[95]  = new Element(96,  "Cm",  "คูเรียม",           "Curium",         "(247)",    "Actinide",            0xFF673AB7, "-",   "7", "f", "ของแข็ง","[Rn] 5f⁷ 6d¹ 7s²",          "ตั้งชื่อตาม Marie Curie ใช้เป็นแหล่งพลังงานในยานอวกาศ",                   R.drawable.curium);
        elements[96]  = new Element(97,  "Bk",  "เบิร์คีเลียม",      "Berkelium",      "(247)",    "Actinide",            0xFF673AB7, "-",   "7", "f", "ของแข็ง","[Rn] 5f⁹ 7s²",              "ธาตุสังเคราะห์ ตั้งชื่อตามเมือง Berkeley",                                  R.drawable.berkelium);
        elements[97]  = new Element(98,  "Cf",  "แคลิฟอร์เนียม",     "Californium",    "(251)",    "Actinide",            0xFF673AB7, "-",   "7", "f", "ของแข็ง","[Rn] 5f¹⁰ 7s²",             "ใช้เป็นแหล่งนิวตรอนในการตรวจสอบสินแร่",                                     R.drawable.californium);
        elements[98]  = new Element(99,  "Es",  "ไอน์สไตเนียม",      "Einsteinium",    "(252)",    "Actinide",            0xFF673AB7, "-",   "7", "f", "ของแข็ง","[Rn] 5f¹¹ 7s²",             "ตั้งชื่อตาม Albert Einstein ค้นพบจากระเบิดปรมาณู",                          R.drawable.einsteinium);
        elements[99]  = new Element(100, "Fm",  "เฟอร์เมียม",        "Fermium",        "(257)",    "Actinide",            0xFF673AB7, "-",   "7", "f", "ของแข็ง","[Rn] 5f¹² 7s²",             "ตั้งชื่อตาม Enrico Fermi ค้นพบจากการระเบิดไฮโดรเจน",                       R.drawable.fermium);
        elements[100] = new Element(101, "Md",  "เมนเดลีเวียม",      "Mendelevium",    "(258)",    "Actinide",            0xFF673AB7, "-",   "7", "f", "ของแข็ง","[Rn] 5f¹³ 7s²",             "ตั้งชื่อตาม Dmitri Mendeleev ผู้สร้างตารางธาตุ",                            R.drawable.mendelevium);
        elements[101] = new Element(102, "No",  "โนบีเลียม",         "Nobelium",       "(259)",    "Actinide",            0xFF673AB7, "-",   "7", "f", "ของแข็ง","[Rn] 5f¹⁴ 7s²",             "ตั้งชื่อตาม Alfred Nobel ผู้ก่อตั้งรางวัลโนเบล",                            R.drawable.nobelium);
        elements[102] = new Element(103, "Lr",  "ลอว์เรนเซียม",      "Lawrencium",     "(266)",    "Actinide",            0xFF673AB7, "3",   "7", "d", "ของแข็ง","[Rn] 5f¹⁴ 7s² 7p¹",         "ธาตุสุดท้ายของกลุ่มแอกทิไนด์ ตั้งชื่อตาม Ernest Lawrence",                R.drawable.lawrencium);
        elements[103] = new Element(104, "Rf",  "รัทเทอร์ฟอร์เดียม", "Rutherfordium",  "(267)",    "Transition Metal",    0xFF2196F3, "4",   "7", "d", "ของแข็ง","[Rn] 5f¹⁴ 6d² 7s²",         "ธาตุสังเคราะห์ ตั้งชื่อตาม Ernest Rutherford",                              R.drawable.rutherfordium);
        elements[104] = new Element(105, "Db",  "ดับเนียม",          "Dubnium",        "(268)",    "Transition Metal",    0xFF2196F3, "5",   "7", "d", "ของแข็ง","[Rn] 5f¹⁴ 6d³ 7s²",         "ธาตุสังเคราะห์ ตั้งชื่อตามเมือง Dubna ในรัสเซีย",                          R.drawable.dubnium);
        elements[105] = new Element(106, "Sg",  "ซีบอร์เกียม",       "Seaborgium",     "(269)",    "Transition Metal",    0xFF2196F3, "6",   "7", "d", "ของแข็ง","[Rn] 5f¹⁴ 6d⁴ 7s²",         "ตั้งชื่อตาม Glenn T. Seaborg นักเคมีผู้สร้างธาตุทรานส์ยูเรเนียม",          R.drawable.seaborgium);
        elements[106] = new Element(107, "Bh",  "โบเรียม",           "Bohrium",        "(270)",    "Transition Metal",    0xFF2196F3, "7",   "7", "d", "ของแข็ง","[Rn] 5f¹⁴ 6d⁵ 7s²",         "ตั้งชื่อตาม Niels Bohr นักฟิสิกส์ผู้พัฒนาแบบจำลองอะตอม",                  R.drawable.bohrium);
        elements[107] = new Element(108, "Hs",  "ฮัสเซียม",          "Hassium",        "(277)",    "Transition Metal",    0xFF2196F3, "8",   "7", "d", "ของแข็ง","[Rn] 5f¹⁴ 6d⁶ 7s²",         "ตั้งชื่อตามรัฐ Hesse ในประเทศเยอรมนี",                                       R.drawable.hassium);
        elements[108] = new Element(109, "Mt",  "ไมต์เนอเรียม",      "Meitnerium",     "(278)",    "Transition Metal",    0xFF2196F3, "9",   "7", "d", "ของแข็ง","[Rn] 5f¹⁴ 6d⁷ 7s²",         "ตั้งชื่อตาม Lise Meitner นักฟิสิกส์ผู้ค้นพบนิวเคลียร์ฟิชชัน",             R.drawable.meitnerium);
        elements[109] = new Element(110, "Ds",  "ดาร์มสตัดเทียม",    "Darmstadtium",   "(281)",    "Transition Metal",    0xFF2196F3, "10",  "7", "d", "ของแข็ง","[Rn] 5f¹⁴ 6d⁹ 7s¹",         "ตั้งชื่อตามเมือง Darmstadt ในเยอรมนี",                                       R.drawable.darmstadtium);
        elements[110] = new Element(111, "Rg",  "เรินต์เกเนียม",     "Roentgenium",    "(282)",    "Transition Metal",    0xFF2196F3, "11",  "7", "d", "ของแข็ง","[Rn] 5f¹⁴ 6d¹⁰ 7s¹",        "ตั้งชื่อตาม Wilhelm Röntgen ผู้ค้นพบรังสีเอกซ์",                            R.drawable.roentgenium);
        elements[111] = new Element(112, "Cn",  "โคเปอร์นิเซียม",    "Copernicium",    "(285)",    "Transition Metal",    0xFF2196F3, "12",  "7", "d", "แก๊ส",  "[Rn] 5f¹⁴ 6d¹⁰ 7s²",        "ตั้งชื่อตาม Nicolaus Copernicus นักดาราศาสตร์",                              R.drawable.copernicium);
        elements[112] = new Element(113, "Nh",  "นิโฮเนียม",         "Nihonium",       "(286)",    "Post-transition Metal", 0xFF795548, "13", "7", "p", "ของแข็ง","[Rn] 5f¹⁴ 6d¹⁰ 7s² 7p¹",   "ธาตุแรกที่ค้นพบในเอเชีย โดยนักวิทยาศาสตร์ญี่ปุ่น",                       R.drawable.nihonium);
        elements[113] = new Element(114, "Fl",  "ฟลีโรเวียม",        "Flerovium",      "(289)",    "Post-transition Metal", 0xFF795548, "14", "7", "p", "แก๊ส",  "[Rn] 5f¹⁴ 6d¹⁰ 7s² 7p²",   "ตั้งชื่อตาม Flerov Laboratory of Nuclear Reactions",                         R.drawable.flerovium);
        elements[114] = new Element(115, "Mc",  "มอสโคเวียม",        "Moscovium",      "(290)",    "Post-transition Metal", 0xFF795548, "15", "7", "p", "ของแข็ง","[Rn] 5f¹⁴ 6d¹⁰ 7s² 7p³",   "ตั้งชื่อตามกรุงมอสโก เมืองหลวงของรัสเซีย",                                 R.drawable.moscovium);
        elements[115] = new Element(116, "Lv",  "ลิเวอร์มอเรียม",    "Livermorium",    "(293)",    "Post-transition Metal", 0xFF795548, "16", "7", "p", "ของแข็ง","[Rn] 5f¹⁴ 6d¹⁰ 7s² 7p⁴",   "ตั้งชื่อตาม Lawrence Livermore National Laboratory",                          R.drawable.livermorium);
        elements[116] = new Element(117, "Ts",  "เทนเนสซีน",         "Tennessine",     "(294)",    "Halogen",             0xFF00BCD4, "17",  "7", "p", "ของแข็ง","[Rn] 5f¹⁴ 6d¹⁰ 7s² 7p⁵",   "ตั้งชื่อตามรัฐ Tennessee ในสหรัฐอเมริกา",                                   R.drawable.tennessine);
        elements[117] = new Element(118, "Og",  "โอกาเนสสัน",        "Oganesson",      "(294)",    "Noble Gas",           0xFF9C27B0, "18",  "7", "p", "แก๊ส",  "[Rn] 5f¹⁴ 6d¹⁰ 7s² 7p⁶",   "ธาตุที่หนักที่สุดที่สังเคราะห์ได้ ตั้งชื่อตาม Yuri Oganessian",            R.drawable.oganesson);
    }

    private void setupElementClickListeners() {
        // Map element index (0-based) to view IDs
        int[] viewIds = {
            R.id.elem_1,   R.id.elem_2,   R.id.elem_3,   R.id.elem_4,   R.id.elem_5,
            R.id.elem_6,   R.id.elem_7,   R.id.elem_8,   R.id.elem_9,   R.id.elem_10,
            R.id.elem_11,  R.id.elem_12,  R.id.elem_13,  R.id.elem_14,  R.id.elem_15,
            R.id.elem_16,  R.id.elem_17,  R.id.elem_18,  R.id.elem_19,  R.id.elem_20,
            R.id.elem_21,  R.id.elem_22,  R.id.elem_23,  R.id.elem_24,  R.id.elem_25,
            R.id.elem_26,  R.id.elem_27,  R.id.elem_28,  R.id.elem_29,  R.id.elem_30,
            R.id.elem_31,  R.id.elem_32,  R.id.elem_33,  R.id.elem_34,  R.id.elem_35,
            R.id.elem_36,  R.id.elem_37,  R.id.elem_38,  R.id.elem_39,  R.id.elem_40,
            R.id.elem_41,  R.id.elem_42,  R.id.elem_43,  R.id.elem_44,  R.id.elem_45,
            R.id.elem_46,  R.id.elem_47,  R.id.elem_48,  R.id.elem_49,  R.id.elem_50,
            R.id.elem_51,  R.id.elem_52,  R.id.elem_53,  R.id.elem_54,  R.id.elem_55,
            R.id.elem_56,  R.id.elem_57,  R.id.elem_58,  R.id.elem_59,  R.id.elem_60,
            R.id.elem_61,  R.id.elem_62,  R.id.elem_63,  R.id.elem_64,  R.id.elem_65,
            R.id.elem_66,  R.id.elem_67,  R.id.elem_68,  R.id.elem_69,  R.id.elem_70,
            R.id.elem_71,  R.id.elem_72,  R.id.elem_73,  R.id.elem_74,  R.id.elem_75,
            R.id.elem_76,  R.id.elem_77,  R.id.elem_78,  R.id.elem_79,  R.id.elem_80,
            R.id.elem_81,  R.id.elem_82,  R.id.elem_83,  R.id.elem_84,  R.id.elem_85,
            R.id.elem_86,  R.id.elem_87,  R.id.elem_88,  R.id.elem_89,  R.id.elem_90,
            R.id.elem_91,  R.id.elem_92,  R.id.elem_93,  R.id.elem_94,  R.id.elem_95,
            R.id.elem_96,  R.id.elem_97,  R.id.elem_98,  R.id.elem_99,  R.id.elem_100,
            R.id.elem_101, R.id.elem_102, R.id.elem_103, R.id.elem_104, R.id.elem_105,
            R.id.elem_106, R.id.elem_107, R.id.elem_108, R.id.elem_109, R.id.elem_110,
            R.id.elem_111, R.id.elem_112, R.id.elem_113, R.id.elem_114, R.id.elem_115,
            R.id.elem_116, R.id.elem_117, R.id.elem_118
        };

        for (int i = 0; i < viewIds.length && i < elements.length; i++) {
            final Element elem = elements[i];
            View view = findViewById(viewIds[i]);
            if (view instanceof ImageView && elem != null) {
                view.setOnClickListener(v -> showElementDialog(elem));

                // Load image on background thread to avoid ANR
                final ImageView imgView = (ImageView) view;
                final int drawableId = elem.drawableId;
                imgView.setBackgroundColor(elem.categoryColor);
                mImageLoader.submit(() -> {
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 4;
                    final Bitmap bmp = BitmapFactory.decodeResource(getResources(), drawableId, opts);
                    if (bmp != null) {
                        mMainHandler.post(() -> {
                            if (!isFinishing() && !isDestroyed()) {
                                imgView.setImageBitmap(bmp);
                                imgView.setBackgroundColor(Color.TRANSPARENT);
                            }
                        });
                    }
                });
            }
        }
    }

    private void showElementDialog(Element elem) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_element_detail);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(
                (int) (getResources().getDisplayMetrics().widthPixels * 0.92),
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

        // Set element image
        ImageView imgView = dialog.findViewById(R.id.dialog_element_image);
        imgView.setImageResource(elem.drawableId);

        // Set text fields
        TextView nameView = dialog.findViewById(R.id.dialog_element_name);
        nameView.setText(elem.nameThai + " (" + elem.nameEng + ")");

        TextView symbolView = dialog.findViewById(R.id.dialog_element_symbol);
        symbolView.setText(elem.symbol);

        TextView numView = dialog.findViewById(R.id.dialog_atomic_number);
        numView.setText(String.valueOf(elem.atomicNumber));

        TextView weightView = dialog.findViewById(R.id.dialog_atomic_weight);
        weightView.setText(elem.atomicWeight);

        TextView categoryBadge = dialog.findViewById(R.id.dialog_category_badge);
        categoryBadge.setText(elem.category);
        categoryBadge.setBackgroundColor(elem.categoryColor);

        TextView groupView = dialog.findViewById(R.id.dialog_group);
        groupView.setText(elem.group);

        TextView periodView = dialog.findViewById(R.id.dialog_period);
        periodView.setText(elem.period);

        TextView blockView = dialog.findViewById(R.id.dialog_block);
        blockView.setText(elem.block);

        TextView stateView = dialog.findViewById(R.id.dialog_state);
        stateView.setText(elem.state);

        TextView configView = dialog.findViewById(R.id.dialog_electron_config);
        configView.setText(elem.electronConfig);

        TextView descView = dialog.findViewById(R.id.dialog_description);
        descView.setText(elem.description);

        Button closeBtn = dialog.findViewById(R.id.dialog_close_button);
        if (closeBtn != null) {
            closeBtn.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }
}
