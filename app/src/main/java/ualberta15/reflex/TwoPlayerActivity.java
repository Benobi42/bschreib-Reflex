package ualberta15.reflex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//Activity to run Functions for a Three Player Multi-Buzzer Game
public class TwoPlayerActivity extends AppCompatActivity {

    public TwoPlayerGame twoPlayerGame;
    boolean gameOver = false;
    private String gameType = "Two Player Game";
    private StatisticManager statsMan;
    private IOManager myIOMan;

    //Runnable function to be run on a separate thread, allowing for UI interaction as well as a loop
    Runnable twoPlayerRunnable = new Runnable() {
        @Override
        public void run() {
            while (!gameOver){
                gameOver = twoPlayerGame.twoGame();
            }
            afterGame();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);

        //Recieve the Input Output Manager from the calling activity and generate the Statistics Manager with it
        myIOMan = (IOManager) getIntent().getParcelableExtra("IOManager");
        statsMan = new StatisticManager(myIOMan,this);

        //Generate the Two Player Game Show with the Statistics Manager
        twoPlayerGame = new TwoPlayerGame(statsMan);

        //Create a Button for each of the players, setting it to a different color than the others
        //and sets their Click Listeners to perform a function within the TwoPlayerGame
        Button playerOne = (Button) findViewById(R.id.PlayerOneButton);
        playerOne.setBackgroundColor(twoPlayerGame.getPlayerOneBuzzer().getBuzzerColor());
        playerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoPlayerGame.pressBuzzerOne();
            }
        });

        Button playerTwo = (Button) findViewById(R.id.PlayerTwoButton);
        playerTwo.setBackgroundColor(twoPlayerGame.getPlayerTwoBuzzer().getBuzzerColor());
        playerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoPlayerGame.pressBuzzerTwo();
            }
        });

        //Create a PopUp Dialog to notify the Players of the rules
        PopUp(findViewById(R.id.twoPlayerLayout), gameType + ": When Ready, Press Start Game To Begin. First Person To Buzz In Wins!", 1);
        startTwoPlayerThread();

    }

    //Based on code from "http://www.tutorialspoint.com/android/android_alert_dialoges.htm"
    //Generates a PopUp Dialog with the inputted message, with the contents depending on the mode
    public void PopUp(View v, String message, int mode){
        AlertDialog.Builder popUpBuilder = new AlertDialog.Builder(this);
        popUpBuilder.setMessage(message);

        //Mode 1: To be called at the start of the Activity
        if (mode == 1){
            popUpBuilder.setNeutralButton("Start Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(TwoPlayerActivity.this, "Game Started", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //Mode 2: To be called after someone presses a buzzer, for them to restart or exit the game
        if (mode == 2) {
            popUpBuilder.setNeutralButton("Restart Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gameOver = false;
                    twoPlayerGame = new TwoPlayerGame(statsMan);
                    startTwoPlayerThread();
                    Toast.makeText(TwoPlayerActivity.this, "Game Restarted", Toast.LENGTH_SHORT).show();
                }
            });
            popUpBuilder.setNegativeButton("Exit Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(TwoPlayerActivity.this, "Game Exited", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
        final AlertDialog popUpDialog = popUpBuilder.create();
        popUpDialog.show();
    }

    //Function to be run after the game, to notify the Players of the winner
    public void afterGame(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Buzzer winner = twoPlayerGame.getWinner();
                PopUp(findViewById(R.id.twoPlayerLayout), winner.getPlayerName() + " Won The " + gameType + "!", 2);
            }
        });
    }

    //Creates a new thread with a runnable, and starts it
    public void startTwoPlayerThread(){
        Thread twoPlayerThread = new Thread(twoPlayerRunnable);
        twoPlayerThread.start();
    }
}
