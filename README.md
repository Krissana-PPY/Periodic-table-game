# Periodic Table Game 🧪

แอปพลิเคชัน Android เกมทายชื่อธาตุจากตารางธาตุ พัฒนาเพื่อการเรียนรู้ธาตุเคมีอย่างสนุกสนาน รองรับทุกระดับตั้งแต่ธาตุหลักไปจนถึงธาตุกัมมันตรังสีและแลนทาไนด์

---

## สารบัญ

- [ภาพรวมโปรเจค](#ภาพรวมโปรเจค)
- [ผู้พัฒนา](#ผู้พัฒนา)
- [ฟีเจอร์หลัก](#ฟีเจอร์หลัก)
- [โครงสร้างโปรเจค](#โครงสร้างโปรเจค)
- [เทคโนโลยีที่ใช้](#เทคโนโลยีที่ใช้)
- [ความต้องการของระบบ](#ความต้องการของระบบ)
- [วิธีติดตั้งและรัน](#วิธีติดตั้งและรัน)
- [วิธีรันทดสอบ](#วิธีรันทดสอบ)
- [วิธีเล่นเกม](#วิธีเล่นเกม)

---

## ภาพรวมโปรเจค

Periodic Table Game เป็นแอปพลิเคชัน Android สำหรับทดสอบความรู้เกี่ยวกับตารางธาตุ ผู้เล่นจะเห็นภาพการ์ดธาตุแล้วพิมพ์ชื่อธาตุจากตัวอักษรที่กำหนดให้ นอกจากนี้ยังมีหน้าตารางธาตุแบบโต้ตอบที่สามารถซูมและกดดูข้อมูลรายละเอียดของแต่ละธาตุได้

---

## ผู้พัฒนา

| ชื่อ | หน้าที่ |
|------|---------|
| **Thanawat Thongnoi** | พัฒนาระบบตารางธาตุ และ UI/UX หลัก |
| **Krissana Pumpayom** | พัฒนาระบบเกม Guess from the picture ทั้ง 3 ระดับ |

---

## ฟีเจอร์หลัก

### ตารางธาตุแบบโต้ตอบ
- แสดงธาตุครบทั้ง **118 ธาตุ** บังคับหมุนหน้าจอเป็นแนวนอน (Landscape)
- **Pinch-to-zoom** ซูมเข้า-ออกได้อิสระ และ **Drag** เลื่อนดูทุกส่วนของตาราง
- โหลดภาพธาตุแบบ Background thread — ไม่กระตุก ไม่ค้าง
- **กดที่ธาตุ** เพื่อดู pop-up ข้อมูลละเอียด ประกอบด้วย:
  - ชื่อไทย / ชื่ออังกฤษ / สัญลักษณ์ / เลขอะตอม / มวลอะตอม
  - หมวดหมู่ / หมู่ / คาบ / บล็อก / สถานะ
  - การจัดเรียงอิเล็กตรอน
  - คำอธิบายย่อ

### เกมทายชื่อธาตุ — 3 ระดับ

| ระดับ | ประเภทธาตุ | จำนวน |
|-------|------------|--------|
| **Level 1** | ธาตุหลัก, อโลหะ, แก๊สมีตระกูล, Metalloid | 50 ธาตุ |
| **Level 2** | โลหะทรานซิชัน (Sc–Rg) | 38 ธาตุ |
| **Level 3** | แลนทาไนด์ และ แอกทิไนด์ | 30 ธาตุ |

**รูปแบบการเล่น:**
- เห็นการ์ดธาตุ (เลขอะตอม + สัญลักษณ์ + มวลอะตอม) แล้วเดาชื่อภาษาอังกฤษ
- กดตัวอักษรจากกริดที่ให้มาเพื่อเติมลงในช่องคำตอบ
- กดตัวอักษรในช่องคำตอบเพื่อลบออกและเลือกใหม่
- มีชีวิต **3 หัวใจ** — กดตัวอักษรผิดหัวใจจะหาย, ตอบถูกได้หัวใจคืน 1 ดวง
- คะแนนสะสมตลอดเซสชัน

---

## โครงสร้างโปรเจค

```
Periodictablegame/
└── app/src/main/
    ├── java/com/example/myapplication/
    │   ├── MainActivity.java              # หน้าหลัก (เมนู)
    │   ├── PeriodicTable.java             # ตารางธาตุแบบซูมได้ + ข้อมูลธาตุ 118 ตัว
    │   ├── ZoomableScrollView.java        # Custom View: Pinch-to-zoom + Pan
    │   ├── LevelGuessPicture.java         # หน้าเลือกระดับ
    │   ├── BaseLevelActivity.java         # Logic หลักของเกม (abstract base)
    │   ├── Level_1_GuessPicture.java      # ระดับ 1 — ธาตุหลัก (50 ธาตุ)
    │   ├── Level_2_GuessPicture.java      # ระดับ 2 — Transition metals (38 ธาตุ)
    │   ├── Level_3_GuessPicture.java      # ระดับ 3 — Lanthanide/Actinide (30 ธาตุ)
    │   ├── Adapter/
    │   │   ├── SuggestAdapter.java        # Grid ตัวอักษรให้เลือก
    │   │   └── GridViewAnswerAdapter.java # Grid ช่องคำตอบ
    │   └── common/
    │       └── Common.java                # ค่าคงที่ shared (alphabet, answer buffer)
    └── res/
        ├── drawable/                      # ภาพธาตุ 118 รูป + UI drawables
        ├── layout/                        # XML layouts
        └── values/                        # strings, colors, styles
```

---

## เทคโนโลยีที่ใช้

| เทคโนโลยี | รายละเอียด |
|-----------|------------|
| **ภาษา** | Java |
| **Platform** | Android |
| **IDE** | Android Studio |
| **Build System** | Gradle 4.1.3 |
| **Min SDK** | API 16 (Android 4.1 Jelly Bean) |
| **Target SDK** | API 30 (Android 11) |
| **UI** | XML Layouts + Custom Views |
| **Async Image Loading** | `ExecutorService` + `Handler` (ไม่พึ่ง library ภายนอก) |
| **Touch / Zoom** | Custom `ZoomableScrollView` (Pinch-to-zoom, Pan, Fit-to-screen) |
| **Unit Testing** | JUnit 4 |

---

## ความต้องการของระบบ

- **Android** 4.1 (API 16) ขึ้นไป
- **RAM** แนะนำ 2 GB ขึ้นไป (ตารางธาตุโหลดภาพ 118 รูปพร้อมกัน)
- **พื้นที่ว่าง** ประมาณ 30–50 MB
- รองรับทั้ง Portrait และ Landscape (หน้าตารางธาตุบังคับ Landscape)

---

## วิธีติดตั้งและรัน

### ความต้องการ
- [Android Studio](https://developer.android.com/studio) Bumblebee ขึ้นไป
- Android SDK API 30
- JDK 8 ขึ้นไป

### ขั้นตอน

**1. Clone โปรเจค**
```bash
git clone https://github.com/Krissana-PPY/Periodic-table-game.git
cd Periodic-table-game
```

**2. เปิดใน Android Studio**
- เลือก `File → Open`
- เลือกโฟลเดอร์ `Periodictablegame/`

**3. Sync Gradle**
- Android Studio จะ sync อัตโนมัติ หากไม่ sync ให้กด `File → Sync Project with Gradle Files`

**4. รันบนอุปกรณ์หรือ Emulator**
- เสียบอุปกรณ์ Android และเปิด USB Debugging หรือสร้าง Virtual Device (AVD)
- กดปุ่ม ▶ Run หรือ `Shift+F10`

**5. Build APK (สำหรับแจกจ่าย)**

macOS / Linux:
```bash
cd Periodictablegame
./gradlew assembleDebug
```

Windows:
```cmd
cd Periodictablegame
gradlew.bat assembleDebug
```

ไฟล์ APK จะอยู่ที่: `app/build/outputs/apk/debug/app-debug.apk`

**6. ติดตั้ง APK บนอุปกรณ์ผ่าน ADB**
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## วิธีรันทดสอบ

โปรเจคมี Unit Tests สำหรับทดสอบ logic หลัก **ไม่ต้องใช้อุปกรณ์หรือ Emulator** รันได้เลยทันที

### รันทดสอบทั้งหมด

Windows:
```cmd
cd Periodictablegame
gradlew.bat testDebugUnitTest
```

macOS / Linux:
```bash
cd Periodictablegame
./gradlew testDebugUnitTest
```

ผลลัพธ์จะแสดงในเทอร์มินัล เช่น:
```
BUILD SUCCESSFUL
tests=19, failures=0, errors=0
```

### ดูรายงานทดสอบแบบละเอียด

เปิดไฟล์นี้ในเบราว์เซอร์:
```
app/build/reports/tests/testDebugUnitTest/index.html
```

### Test Cases ที่ครอบคลุม (19 tests)

| หมวด | สิ่งที่ทดสอบ |
|------|-------------|
| ZoomableScrollView | fitScale, centerX/Y symmetry, clamp bounds (left/right/small) |
| Bug regression | `layout_gravity=center` ทำให้ child offset ติดลบ |
| Game logic | การจับคู่ตัวอักษร, ตรวจสอบช่องคำตอบ |
| Lives system | หัวใจลด, หัวใจเพิ่ม (cap 3), Game Over เมื่อหมด |
| Suggest grid | decoy ต้องไม่ซ้ำกับตัวอักษรในคำตอบ |
| Score | เพิ่มเมื่อตอบถูก, ไม่เปลี่ยนเมื่อตอบผิด |

---

## วิธีเล่นเกม

1. เปิดแอป → กดปุ่ม **Game**
2. เลือกระดับ **Level 1 / 2 / 3**
3. จะเห็นการ์ดธาตุแสดงสัญลักษณ์ เลขอะตอม และมวลอะตอม
4. กดตัวอักษรจากกริดด้านล่างเพื่อสะกดชื่อธาตุภาษาอังกฤษ
5. กดตัวอักษรในช่องคำตอบเพื่อลบและเลือกใหม่
6. ตอบถูก → ได้คะแนน + หัวใจคืน → ขึ้นคำถามถัดไป
7. ตอบผิด (กดตัวอักษรที่ไม่อยู่ในชื่อธาตุ) → เสียหัวใจ 1 ดวง
8. หัวใจหมด → Game Over แสดงคำตอบที่ถูกต้องและคะแนนที่ได้

---

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.
