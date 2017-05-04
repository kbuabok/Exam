# Exam
ทายตัวเลขตั้งแต่ 1000 - 9999 ให้ถูกต้องโดยใช้ api ของเว็บ random.org ในการสุ่มตัวเลขคำตอบ
ถ้าไม่เท่ากันจะแสดงข้อความบอกว่าเลขที่กรอกเข้ามามากกว่าหรือน้อยกว่าคำตอบที่สุ่มมา
ถ้าหากเลขที่กรอกเข้ามาเท่ากับคำตอบที่สุ่มมาให้เริ่มเกมใหม่
# ขั้นตอนการพัฒนา
หลังจากสร้างโปรเจคขึ้นมาให้ทำการติดตั้ง library 3ตัวคือ gson junit และ org-apache-commons-codec เพื่อใช้งาน api ของ random.org ได้
หลักจากติดตั้งแล้วให้ import api เข้ามาในโปรเจคโดยให้อยู่ใน packet ชื่อ org.random <br>

จากนั้นสร้างหน้า layout ของแอพขึ้น โดยตั้งชื่อว่า activity_start.xml
 <?xml version="1.0" encoding="utf-8"?>
  <RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.kbuabok.exam.MainActivity">

    <RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true"
        android:layout_centerHorizontal="true">

        <TextView
            android:id="@+id/txt1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Numbers Game"
            android:textSize="30dp"
            />

        <Button
            android:id="@+id/btnStart"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="5dp"
            android:layout_below="@+id/txt1"
            android:layout_centerHorizontal="true"
            android:text="Start"
            android:onClick="start"/>

    </RelativeLayout>

</RelativeLayout>

หลักจากนั้นเขียนคำสั่งให้กับปุ่ม start เพิ่อเล่นเกม โดยเมื่อกดปุ่มจะแสดง dialog ขึ้นมาบนหน้า UI ให้รอสักครู่ในที่ กำลังติดต่อกับ api เพื่อสุ่มค่าตัวเลขออกมา 1 ตัวโดยที่มีค่าอยู่ระหว่าง 1000 - 9999 เพื่อได้ผลลัพท์ จะส่งเลขที่ได้ไปยัง MainActivity
 

    public void start(View view){
        prgDialog.show();
        new RandomAnswer().execute("2a152d18-8e17-4dc1-81f7-024aceec597e");

    }

    private class RandomAnswer extends AsyncTask<String, Integer, String> {

        int answer;

        protected String doInBackground(String... API_KEY) {
            RandomOrgClient roc = RandomOrgClient.getRandomOrgClient(API_KEY[0]);
            try {
                int[] randoms = roc.generateIntegers(1, 1000, 9999);
                answer = randoms[0];
            } catch (Exception e){
                Log.e("Error : ", ""+e);
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
        }
        protected void onPostExecute(String result) {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            intent.putExtra("answer", answer);
            prgDialog.cancel();
            startActivity(intent);
            finish();
        }

    }
    
  เขียนหน้า layout สำหรับหน้าเล่นเกม โดยตั้งชื่อว่า activity_main.xml
  
  <?xml version="1.0" encoding="utf-8"?>
  <RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.kbuabok.exam.MainActivity">

    <RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true"
        android:layout_centerHorizontal="true">

        <LinearLayout
            android:id="@+id/line1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:layout_centerHorizontal="true">

            <TextView
                android:id="@+id/txtmin"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="1000"
                android:textSize="30dp" />

            <TextView
                android:id="@+id/txt"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text=" - "
                android:textSize="30dp" />

            <TextView
                android:id="@+id/txtmax"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="9999"
                android:textSize="30dp"/>

        </LinearLayout>

        <EditText
            android:id="@+id/edtanswer"
            android:layout_width="200dp"
            android:layout_height="wrap_content"
            android:layout_below="@+id/line1"
            android:inputType="number"/>

        <Button
            android:id="@+id/btnStart"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="5dp"
            android:layout_below="@+id/edtanswer"
            android:layout_centerHorizontal="true"
            android:text="Verifyt"
            android:onClick="verify"/>

    </RelativeLayout>

</RelativeLayout>

เขียนหน้า dialog สำหรับแสดงผลลัพท์ โดยตั้งชื่อว่า result_dialog.xml 

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:background="#AAAAAA" >
    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="25dp"
        android:gravity="center"
        android:orientation="vertical" >

        <TextView
            android:id="@+id/textView1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="10dp"
            android:textSize="25dp"
            android:text="txt" />

            <Button
                android:id="@+id/btn"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="ok" />

    </LinearLayout>
</RelativeLayout>

จากนั้นมาเขียนคำสั่งให้กับหน้า MainActivity โดยเริ่มจากนำตัวเลขที่ได้รับใส่ไว้ในตัวแปรคำตอบ

        Bundle bundle = getIntent().getExtras();
        correct_answer = bundle.getInt("answer");

จากนั้น เขียนคำสั่งให้กับปุ่ม verify เมื่อคลิกที่ปุ่มให้เราข้อความใน Edittext มาเก็บไว้ในตัวแปรเพื่อนำไปเปรียบเทียบกับตัวเลขที่สุ่มมาได้
        
        int answer = Integer.parseInt(editText.getText().toString());

นำตัวเลขที่สุ่มได้กับตัวเลขที่รับเข้ามา มาเปรียบเทียบกันโดยที่ถ้าหาก ตัวเลขที่กรอกเข้ามามีค่าน้อยกว่า จะแสดง dialog ขึ้นมาพร้อมกับข้อความว่า Less than! Try again  เมื่อกดปุ่ม ok จะเปิด dialog และเปลี่ยนเลขต่ำสุดเป็นตัวเลขที่กรอกเข้ามา
ถ้าหากเปรียบเทียบกันแล้ว ตัวเลขที่กรอกเข้ามามีค่ามากกว่า จะแสดง dialog ขึ้นมาพร้อมกับข้อความว่า More than! Try again  เมื่อกดปุ่ม ok จะเปิด dialog และเปลี่ยนเลขสูงเป็นตัวเลขที่กรอกเข้ามา ซึ่งถ้าหากเลขที่กรอกเข้ามา ตรงกับเลขที่สุ่มได้ จะแสดง dialog ขึ้นมาพร้อมกับข้อความว่า Congratulations
The answer is ตัวเลขที่ถูกต้อง  เมื่อกดปุ่ม ok จะกลับไปที่หน้าเริ่มต้น



        if(answer < correct_answer){
            txtmin.setText(""+answer);
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.result_dialog);
            dialog.setCancelable(true);

            TextView textView = (TextView)dialog.findViewById(R.id.textView1);
            textView.setText("Less than! Try again");

            Button button1 = (Button)dialog.findViewById(R.id.btn);
            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog.show();

        } else if(answer > correct_answer){
            txtmax.setText(""+answer);
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.result_dialog);
            dialog.setCancelable(true);

            TextView textView = (TextView)dialog.findViewById(R.id.textView1);
            textView.setText("More than! Try again");

            Button button1 = (Button)dialog.findViewById(R.id.btn);
            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog.show();
        } else{
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.result_dialog);
            dialog.setCancelable(true);

            TextView textView = (TextView)dialog.findViewById(R.id.textView1);
            textView.setText("Congratulations\nThe answer is " + answer);

            Button button1 = (Button)dialog.findViewById(R.id.btn);
            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.cancel();
                    Intent intent = new Intent(MainActivity.this, StartActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            dialog.show();

        }
        
        
# ไอเดียในการพัฒนาต่อ
ทำระบบตัวละครขึ้นมาโดยที่ผู้เล่นสามารถที่จะปรับแต่งตัวละคร มีระบบระดับของตัวละครโดยที่ผู้เล่นต้องเล่นเพื่อที่จะได้เพิ่มระดับ เมื่อระดับเพิ่มขึ้นก็จะปลดล็อคอุปกรณ์มาตกแต่งตัวละครของตัวเอง นอกจากนี้อาจจะใช้รางวัลจากการจัดอันดับมาทำการปลดล็อคอุปกรณ์ สามารถแชร์ตัวละครของตัวเองไปยังสื่อโซเชียลมีเดียต่างๆ แชร์ผลการเล่น แชร์อันดับของตัวเองในสกอร์บอร์ด
พัฒนาระบบการเล่นเป็นสองระบโดย 
1) เป็นการเล่นแบบผู้เล่นคนเดียวโดยที่เมื่อเล่นเกมจบแล้วจะทำการบันทึกจำนวนครั้งที่พิมพ์คำตอบลงไปเป็นคะแนนของผู้เล่นคนนั้นๆแล้วมีการจัดอันดับกันในกลุ่มเพื่อนแสดงในรูปแบบสกอร์บอร์ดโดยวัดจากจำนวนครั้งที่เล่นและคะแนนที่ทำได้ โดยที่จะรีเช็ตใหม่ทุกๆวันเพื่อให้อันดับมีความหลากหลาย เมื่อมีการรีเช็ตอันดับก็ให้รางวัลสำหรับผู้เล่นในแต่ละอันดับเพื่อเอาไปใช้ตกแต่งตัวละครของตนเอง  
2) รูปแบบการแข่งขันโดยผู้เล่นสารมารถเชิญเพื่อนที่อยู่ในรายชื่อเพื่อนมาเพื่อร่วมแข่งขันกัน โดยที่จะให้ผลัดกันตอบถ้าใครตอบตัวเลขตรงกับเลขที่สุ่มมาก็จะถือว่าแพ้ ในรูปแบบการเล่นนี้ก็จะมีการจัดอันดับเช่นกันโดยที่คะแนนจะวัดจากผลการแพ้ชนะของผู้เล่น
