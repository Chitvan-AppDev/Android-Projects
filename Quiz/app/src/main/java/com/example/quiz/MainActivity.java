package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button playButton ;
    TextView questionTextView;
    ArrayList<Integer> options = new ArrayList<>();
    int locationOfCorrectAnswer;
    TextView resultTextView;
    int correct=0;
    int noOfQuestions =0;
    TextView scoreTextView;
    Button button0,button1,button2,button3;
    TextView timerTextView;
    Button playAgainButton;
    ConstraintLayout gameLayout;

    public void playAgain(final View view){
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        correct=0;
        noOfQuestions=0;
        timerTextView.setText("30s");
        scoreTextView.setText(Integer.toString(correct)+"/"+Integer.toString(noOfQuestions));
        newQuestion();
        playAgainButton.setVisibility(view.INVISIBLE);
        resultTextView.setText("");
        new CountDownTimer(30000,1000){
            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l/1000)+"s");
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Done!");
                playAgainButton.setVisibility(view.VISIBLE);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);



            }
        }.start();



    }
    public void chooseAnswer(View view){
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            resultTextView.setText("correct!!");
            correct++;
        }
        else{
            resultTextView.setText("wrong :(");
        }
        noOfQuestions++;
        scoreTextView.setText(Integer.toString(correct)+"/"+Integer.toString(noOfQuestions));
        newQuestion();

    }
    public void start(View view){

        playButton.setVisibility(view.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playButton));// in this we are adding a arbitrary view as we dont use it anywhere
    }
    public void newQuestion(){
        Random rand= new Random();
        int a= rand.nextInt(99);
        int b=rand.nextInt(99);
        questionTextView.setText(Integer.toString(a)+" + "+Integer.toString(b));
        locationOfCorrectAnswer=rand.nextInt(4);
        options.clear();
        for (int i=0;i<4;i++){
            if(i==locationOfCorrectAnswer){
                options.add(a+b);
            }else{
                int wrongAnswer= rand.nextInt(198);
                while(wrongAnswer==a+b){
                    wrongAnswer=rand.nextInt(198);
                    for(int j=0;j<i;j++){
                        if(options.get(j)==wrongAnswer);
                        wrongAnswer=rand.nextInt(198);
                    }
                }
                options.add(wrongAnswer);
            }
        }
        button0.setText(Integer.toString(options.get(0)));
        button1.setText(Integer.toString(options.get(1)));
        button2.setText(Integer.toString(options.get(2)));
        button3.setText(Integer.toString(options.get(3)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button0=(Button)findViewById(R.id.button0);
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        resultTextView=(TextView) findViewById(R.id.resultTextView);
        scoreTextView=(TextView)findViewById(R.id.scoreTextView);

        playButton =(Button)findViewById(R.id.playButton);
        questionTextView=(TextView)findViewById(R.id.questionTextView);
        timerTextView=(TextView)findViewById(R.id.timerTextView);
        playAgainButton=(Button)findViewById(R.id.playAgainButton);
        gameLayout=(ConstraintLayout)findViewById(R.id.gameLayout);
        gameLayout.setVisibility(View.INVISIBLE);
        playButton.setVisibility(View.VISIBLE);




    }
}