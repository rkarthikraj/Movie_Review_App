package com.example.rkarthikraj.asynctask_api;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity_API extends AppCompatActivity {

    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__api);

        et = (EditText) findViewById(R.id.inputTitle);
        tv = (TextView) findViewById(R.id.tv);

    }

    public void onClickGet(View view)
    {
        String ettext = et.getText().toString();
        String[] arr = ettext.split(" ");
        String finall = TextUtils.join("+", arr);
        new  apicall().execute( finall );
    }


    private class apicall extends AsyncTask<String, Void, String>
    {



        @Override
        protected String doInBackground(String... params) {
            String outputresponse="";
            try {
                URL url = new URL("http://omdbapi.com/?t=" + params[0] );
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);//connection.setRequestMethod("POST");c
               //connection.setRequestProperty("Content-Type", "application/json");
               /* OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                //osw.write(String.format( String.valueOf(json)));
                osw.flush();
                osw.close();*/

                InputStream stream = connection.getInputStream();
                InputStreamReader isReader = new InputStreamReader(stream);
                BufferedReader br = new BufferedReader(isReader);
                outputresponse = br.readLine();
                Thread.sleep(3000);
            }
            catch(IOException e)
            {

            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            return outputresponse;
        }

        @Override
        protected  void onPostExecute(String result)
        {

            JSONObject json = null;
            String director = "";
            String rating = "";
            try {
                json = new JSONObject(result);
                tv.setText("Director of the movie is " + json.getString("Director"));
            }
            catch (JSONException j)
            {

            }
        }
    }
}
