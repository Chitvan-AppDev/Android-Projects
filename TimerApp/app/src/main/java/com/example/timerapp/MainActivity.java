package com.example.timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    SeekBar seekBar;
    Button goButton;
    CountDownTimer countDownTimer;
    boolean counterActive=false;
    public void resetTimer(){
        textView.setText("00:00");
        seekBar.setProgress(0);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        
        goButton.setText("Lets Go!!");
        counterActive=false;
    }
    public void buttonPressed(View view){
        if(counterActive){
            resetTimer();

        }
        else{
        counterActive=true;
        goButton.setText("stop!");
        seekBar.setEnabled(false);
        countDownTimer=new CountDownTimer(seekBar.getProgress()*1000,1000) {
            @Override
            public void onTick(long l) {
                updateTimer((int)l/1000);

            }

            @Override
            public void onFinish() {
                Log.i("timer","finished");//there is a bit delay in starting sound due to some mathematical calucation in countdown timer to fix that we can add 100 ms in msfuture
                MediaPlayer mediaPlayer= MediaPlayer.create(getApplicationContext(),R.raw.bellsound);
                mediaPlayer.start();
                resetTimer();

            }
        }.start();
    }}
    public void updateTimer(int secondsLeft){
        int minutes=secondsLeft/60;
        int seconds=secondsLeft-(minutes*60);
        String secondString= Integer.toString(seconds);

        if(seconds<=9){
             secondString="0"+secondString;
        }
        textView.setText(Integer.toString(minutes)+":"+secondString);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar= (SeekBar)findViewById(R.id.seekBar);
         textView=(TextView)findViewById(R.id.textView2);
         goButton=(Button)findViewById(R.id.button);
        seekBar.setMax(3600);
        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}