package ualberta15.reflex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//Activity to run Functions for a Three Player Multi-Buzzer Game
public class ThreePlayerActivity extends AppCompatActivity {

    public ThreePlayerGame threePlayerGame;
    boolean gameOver = false;
    private String gameType = "Three Player Game";
    private StatisticManager statsMan;
    private IOManager myIOMan;

    //Runnable function to be run on a seperate thread, allowing for UI interaction as well as a loop
    Runnable ThreePlayerRunnable = new Runnable() {
        @Override
        public void run() {
            while (!gameOver){
                gameOver = threePlayerGame.threeGame();
            }
            afterGame();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_player);

        //Recieve the Input Output Manager from the calling activity and generate the Statistics Manager with it
        myIOMan = getIntent().getParcelableExtra("IOManager");
        statsMan = new StatisticManager(myIOMan,this);

        //Generate the Three Player Game Show with the Statistics Manager
        threePlayerGame = new ThreePlayerGame(statsMan);

        //Create a Button for each of the players, setting it to a different color than the others
        //and sets their Click Listeners to perform a function within the ThreePlayerGame
        Button playerOne = (Button) findViewById(R.id.PlayerOneButton);
        playerOne.setBackgroundColor(threePlayerGame.getPlayerOneBuzzer().getBuzzerColor());
        playerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threePlayerGame.pressBuzzerOne();
            }
        });

        Button playerTwo = (Button) findViewById(R.id.PlayerTwoButton);
        playerTwo.setBackgroundColor(threePlayerGame.getPlayerTwoBuzzer().getBuzzerColor());
        playerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threePlayerGame.pressBuzzerTwo();
            }
        });

        Button playerThree = (Button) findViewById(R.id.PlayerThreeButton);
        playerThree.setBackgroundColor(threePlayerGame.getPlayerThreeBuzzer().getBuzzerColor());
        playerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threePlayerGame.pressBuzzerThree();
            }
        });

        //Create a PopUp Dialog to notify the Players of the rules
        PopUp(findViewById(R.id.threePlayerLayout), gameType + ": When Ready, Press Start Game To Begin. First Person To Buzz In Wins!", 1);
        startThreePlayerThread();

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
                    Toast.makeText(ThreePlayerActivity.this, "Game Started", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //Mode 2: To be called after someone presses a buzzer, for them to restart or exit the game
        if (mode == 2) {
            popUpBuilder.setPositiveButton("Restart Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gameOver = false;
                    threePlayerGame = new ThreePlayerGame(statsMan);
                    startThreePlayerThread();
                    Toast.makeText(ThreePlayerActivity.this, "Game Restarted", Toast.LENGTH_SHORT).show();
                }
            });
            popUpBuilder.setNegativeButton("Exit Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ThreePlayerActivity.this, "Game Exited", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
        AlertDialog popUpDialog = popUpBuilder.create();
        popUpDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //When the Dialog is dismissed, if the game is not starting, end the activity
                if (gameOver){
                    finish();
                }
            }
        });
        popUpDialog.show();
    }

    //Function to be run after the game, to notify the Players of the winner
    public void afterGame(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Buzzer winner = threePlayerGame.getWinner();
                PopUp(findViewById(R.id.threePlayerLayout), winner.getPlayerName() + " Won The " + gameType + "!", 2);
            }
        });
    }


    //Creates a new thread with a runnable, and starts it
    public void startThreePlayerThread(){
        Thread threePlayerThread = new Thread(ThreePlayerRunnable);
        threePlayerThread.start();
    }
}
