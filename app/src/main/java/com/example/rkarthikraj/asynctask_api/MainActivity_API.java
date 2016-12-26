package com.example.rkarthikraj.asynctask_api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity_API extends AppCompatActivity {

    EditText et;
    TextView tv2,tv3,tv4,tv5;
    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__api);

        et = (EditText) findViewById(R.id.inputTitle);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        poster =(ImageView) findViewById(R.id.PosterIV);


    }

    public void onClickGet(View view)
    {
        String ettext = et.getText().toString();
        String[] arr = ettext.split(" ");
        String finall = TextUtils.join("+", arr);
        new  apicall().execute( finall );
    }


    private class apicall extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            String outputresponse = "";
            try {
                URL url = new URL("http://omdbapi.com/?t=" + params[0]);
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
            } catch (IOException e) {

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return outputresponse;
        }

        @Override
        protected void onPostExecute(String result) {

            JSONObject json = null;
            String director = "";
            String rating = "";
            String imageurl;
            try {
                json = new JSONObject(result);
                tv2.setText("Director of the movie is " + json.getString("Director"));
                tv3.setText("Released Date " + json.getString("Released"));
                tv4.setText("Runtime" + json.getString("Runtime"));
                tv5.setText("IMDB Rating " + json.getString("imdbRating"));
                imageurl = json.getString("Poster");
                new AsyncImage().execute(imageurl);

            } catch (JSONException j) {

            }
        }
    }

    Bitmap bmp = null;
        private class AsyncImage extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params)
            {
                try
                {
                URL imageurl = new URL(params[0]);
                bmp = BitmapFactory.decodeStream(imageurl.openConnection().getInputStream());
                }
                catch (Exception e)
                {

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result)
            {
                poster.setImageBitmap(bmp);
            }


        }
}
