package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    int activePlayer = 0;//o for x and 1 for y
    int [] gameState={2,2,2,2,2,2,2,2,2};//2corresponds to empty
    int [][] winningPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameActive= true;
    ImageView counter;

    public void dropIn(View view){

        counter=(ImageView)view;//here we want to see which imageview is tapped hence we used view variable here


        counter.setTranslationY(-1500);//putting image above the window

        int tapCounter=Integer.parseInt(counter.getTag().toString());
        if(gameState[tapCounter]==2&& gameActive) {
            gameState[tapCounter] = activePlayer;

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.tttx);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.ttto);
                activePlayer = 0;



            }

            counter.setEnabled(false);


            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);
        /* here we are animating the image to come down rotating as we press on image view in
        rotation we are adding values in degree and in duration it is in milliseconds
         */

        int flag=1;
            for (int[] winningPositions : winningPositions) {
                if (gameState[winningPositions[0]] == gameState[winningPositions[1]] && gameState[winningPositions[1]] == gameState[winningPositions[2]] && gameState[winningPositions[1]] != 2) {
                    String winner="" ;
                    gameActive=false;
                    if (activePlayer == 1) {
                        winner = "X";


                    } else{
                        winner = "O";

                    }

                    Button playAgainButton= (Button)findViewById(R.id.playAgainButton);
                    TextView winnerTextView=(TextView)findViewById(R.id.winnerTextView);
                    winnerTextView.setText(winner+" has won");
                    playAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);
                }
                flag++;

            }
            for (int i=0;i<gameState.length;i++){
                if(gameState[i]==2){
                    flag++;

                }

            }
            if(flag==9){

                Button playAgainButton= (Button)findViewById(R.id.playAgainButton);
                TextView winnerTextView=(TextView)findViewById(R.id.winnerTextView);
                winnerTextView.setText("No one has won , the match has been drawn");
                playAgainButton.setVisibility(View.VISIBLE);
                winnerTextView.setVisibility(View.VISIBLE);


            }




        }

    }
    public void playAgain (View view){
        Button playAgainButton= (Button)findViewById(R.id.playAgainButton);
        TextView winnerTextView=(TextView)findViewById(R.id.winnerTextView);

        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout3= (GridLayout)findViewById(R.id.gridLayout2);
        for (int i =0 ; i<gridLayout3.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout3.getChildAt(i);
            counter.setImageDrawable(null);
            counter.setEnabled(true);

        }/*here in this loop we are iterating through grid layout rows and coulumns
        and removing added images*/
        for (int i=0 ;i<gameState.length ; i++){
            gameState[i]=2;
        }
        activePlayer = 0;
         gameActive= true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}