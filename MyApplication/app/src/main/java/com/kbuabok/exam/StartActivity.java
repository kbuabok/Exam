package com.kbuabok.exam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.random.api.RandomOrgClient;

public class StartActivity extends AppCompatActivity {

    TextView txt;

    ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);

        txt = (TextView)findViewById(R.id.txt1);
    }

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
}
