package com.example.a2jenkm59.asynctask;


import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddSongActivity extends AppCompatActivity implements View.OnClickListener{

    class MyTask2 extends AsyncTask<String,Void,String>
    {
        public String doInBackground(String... details)
        {
            HttpURLConnection conn = null;
            try
            {

                URL url = new URL("http://www.free-map.org.uk/course/mad/ws/addhit.php");
                conn = (HttpURLConnection) url.openConnection();

                String postData = "song=" + details[0] + "&artist=" + details[1] + "&year=" + details[2];
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(postData.length());

                OutputStream out = null;
                out = conn.getOutputStream();
                out.write(postData.getBytes());
                if(conn.getResponseCode() == 200)
                {
                    InputStream in = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String all = "", line;
                    while((line = br.readLine()) !=null)
                        all += line;
                    return all;
                }
                else
                {
                    return "HTTP ERROR: " + conn.getResponseCode();
                }
            }
            catch(IOException e)
            {
                return e.toString();
            }
            finally
            {
                if(conn!=null)
                {
                    conn.disconnect();
                }
            }

        }
        public void onPostExecute(String result)
        {

            new AlertDialog.Builder(AddSongActivity.this).
                    setMessage("Server sent back: " + result).
                    setPositiveButton("OK", null).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsong);
        Button go = (Button)findViewById(R.id.btn2);
        go.setOnClickListener(this);
    }

     public void onClick(View v) {

         String song;
         EditText et = (EditText)findViewById(R.id.et2);
         song = et.getText().toString();
         String artist;
         EditText et2 = (EditText)findViewById(R.id.et3);
         artist = et2.getText().toString();
         String year;
         EditText et3 = (EditText)findViewById(R.id.et4);
         year = et3.getText().toString();
         MyTask2 t = new MyTask2();

         t.execute(song, artist, year);

     }
}


