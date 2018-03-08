package com.example.hp.gameconnect3;



import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class First_Activity extends AppCompatActivity {

     // 0= yellow & 1= red

    int activePlayer=0;
    boolean gameIsActive =true;

    // array of 2 means unplayed rooms

    int[] gameState = {2,2,2,2,2,2,2,2,2};

    // winning positions horizontally, vertically and diagonally

    int [][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    // dropIn method is to drop the red & yellow coins in the tapped ImageView

    public void dropIn(View view){

        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        // tappedCounter is the typecasted form of Tag of ImageViews in XML part

        if(gameState[tappedCounter]== 2 && gameIsActive) {

            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
               }
            else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
               }
            counter.animate().translationYBy(1000f).setDuration(100);

            // Below condition is to check the whethere someonr wins or game goes for draw

            for(int[] winningPositions : winningPositions){
                if(gameState[winningPositions[0]] == gameState[winningPositions[1]] &&
                        gameState[winningPositions[1]]== gameState[winningPositions[2]] &&
                            gameState[winningPositions[0]] != 2){

                    // Someone has Won !!
                    gameIsActive = false;

                    String winner = "Red";
                    if(gameState[winningPositions[0]] ==0){
                        winner = "Yellow";
                    }

                    TextView winnerMessege = (TextView) findViewById(R.id.winningMessegeID);
                    winnerMessege.setText(winner+ " has Won !");

                    LinearLayout layout =(LinearLayout)findViewById(R.id.playAgainID);
                    layout.setVisibility(View.VISIBLE);
                    break ;
                }
               else{

                    //This else statement is for Draw part

                      boolean gameIsOver = true;
                      for(int counterState : gameState){
                          if(counterState == 2) gameIsOver = false;
                      }

                      if(gameIsOver)
                      {
                          TextView winnerMessege = (TextView) findViewById(R.id.winningMessegeID);
                          winnerMessege.setText("It's a Draw");

                          LinearLayout layout =(LinearLayout)findViewById(R.id.playAgainID);
                          layout.setVisibility(View.VISIBLE);
                      }
                }
            }
        }
    }

      // playAgain method is to return the game to initial state
      public void playAgain(View view){

        gameIsActive = true;

        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainID);
        layout.setVisibility(View.INVISIBLE);

        activePlayer =0;
        for(int i=0; i<gameState.length; i++)
          {
            gameState[i]=2;
          }

          GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayoutID);
          for(int i=0;i<gridLayout.getChildCount();i++)
            {
                ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
            }
      }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_);
    }

    @Override
    public void onBackPressed(){
          final AlertDialog.Builder builder = new AlertDialog.Builder(First_Activity.this);
          builder.setMessage("Are you sure want to Exit ?");
          builder.setCancelable(true);
          builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                  dialogInterface.cancel();
              }
          });

          builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                  finish();
              }
          });
          AlertDialog alertDialog = builder.create();
          alertDialog.show();
    }
}




