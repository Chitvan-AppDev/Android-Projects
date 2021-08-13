package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText= (EditText) findViewById(R.id.editText);
        resultTextView=(TextView)findViewById(R.id.resultTextView);

    }
    public void getWeather(View view){
        try {
            downloadTask task = new downloadTask();
            String encodedCityName = URLEncoder.encode(editText.getText().toString(), "UTF-8");//this handles all the spaces between city name...for eg new delhi
            task.execute("https://api.openweathermap.org/data/2.5/weather?q=" + encodedCityName + "&appid=4fa1005aa7d9cde72a053595a430b3ef");
            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"coudn't find the weather",Toast.LENGTH_SHORT).show();
        }
    }

    public class downloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpsURLConnection urlConnection= null;
            try{
                url= new URL(urls[0]);
                urlConnection=(HttpsURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader= new InputStreamReader(in);
                int data = reader.read();
                while(data!=-1){
                    char current = (char)data;
                    result+=current;
                    data=reader.read();
                }
                return result;
            }catch(Exception e){
                e.printStackTrace();
//                Toast.makeText(getApplicationContext(),"coudn't find the weather",Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("JSON",""+s);
            try{
                JSONObject jsonObject= new JSONObject(s);
                String weatherInfo =jsonObject.getString("weather");
                Log.i("weather info",weatherInfo);// this give the result in form of array so to handle this we will make jsonarray
                JSONArray arr=new JSONArray(weatherInfo);
                String message="";
                for(int i=0;i<arr.length();i++){
                    JSONObject jsonPart= arr.getJSONObject(i);
                    String main=jsonPart.getString("main");
                    String description=jsonPart.getString("description");
                    if(!main.equals("")&&!description.equals("")){
                        message="Main: "+main +"\n"+"Description: "+description+ "\r\n";
                    }

                }
                if(!message.equals("")){
                    resultTextView.setText(message);
                }else{
                    Toast.makeText(getApplicationContext(),"coudn't find the weather",Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"coudn't find the weather",Toast.LENGTH_SHORT).show();

            }
        }//every above thing is done in background and in this method we can play with ui.... here the string s is the result of above method (do in background)
    }
}