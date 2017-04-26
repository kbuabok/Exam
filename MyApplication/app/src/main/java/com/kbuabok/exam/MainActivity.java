package com.kbuabok.exam;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView txtmin,txtmax;
    int correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.edtanswer);
        txtmin = (TextView)findViewById(R.id.txtmin);
        txtmax = (TextView)findViewById(R.id.txtmax);

        Bundle bundle = getIntent().getExtras();
        correct_answer = bundle.getInt("answer");

        Log.e("answer : " , ""+correct_answer);

    }

    public void verify(View view){
        int answer = Integer.parseInt(editText.getText().toString());

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
    }
}
