package ualberta15.reflex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ualberta15.reflex.R;

//Activity to run Functions for a Multi-Buzzer Game Show
public class MultiplayerActivity extends AppCompatActivity {

    public MultiplayerGame multiplayerGame;
    boolean gameOver = false;
    private String gameType;
    private StatisticManager statsMan;
    private IOManager myIOMan;
    private int numPlayers;

    //Runnable function to be run on a seperate thread, allowing for UI interaction as well as a loop
    Runnable multiplayerRunnable = new Runnable() {
        @Override
        public void run() {
            while (!gameOver){
                gameOver = multiplayerGame.MultiGame();
            }
            afterGame();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_player);

        //Recieve the Input Output Manager from the calling activity and generate the Statistics Manager with it
        myIOMan = (IOManager) getIntent().getParcelableExtra("IOManager");
        numPlayers = getIntent().getIntExtra("PlayerNumber", 2);
        gameType = numPlayers + " Player Game";
        statsMan = new StatisticManager(myIOMan, this);

        //Generate the Four Player Game Show with the Statistics Manager
        multiplayerGame = new MultiplayerGame(statsMan, numPlayers);

        //Create a Button for each of the players, setting it to a different color than the others
        //and sets their Click Listeners to perform a function within the MultiplayerGame
        Button playerOne = (Button) findViewById(R.id.PlayerOneButton);
        playerOne.setBackgroundColor(multiplayerGame.getPlayerOneBuzzer().getBuzzerColor());
        playerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiplayerGame.pressBuzzerOne();
            }
        });

        Button playerTwo = (Button) findViewById(R.id.PlayerTwoButton);
        playerTwo.setBackgroundColor(multiplayerGame.getPlayerTwoBuzzer().getBuzzerColor());
        playerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiplayerGame.pressBuzzerTwo();
            }
        });


        Button playerThree = (Button) findViewById(R.id.PlayerThreeButton);
        if (numPlayers > 2) {
            playerThree.setBackgroundColor(multiplayerGame.getPlayerThreeBuzzer().getBuzzerColor());
            playerThree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    multiplayerGame.pressBuzzerThree();
                }
            });
        } else{
            //playerThree.setHeight(0);
            playerThree.setVisibility(View.GONE);
        }

        Button playerFour = (Button) findViewById(R.id.PlayerFourButton);
        if (numPlayers > 3) {
            playerFour.setBackgroundColor(multiplayerGame.getPlayerFourBuzzer().getBuzzerColor());
            playerFour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    multiplayerGame.pressBuzzerFour();
                }
            });
        } else{
            //playerFour.setHeight(0);
            playerFour.setVisibility(View.GONE);
        }


        //Create a PopUp Dialog to notify the Players of the rules
        PopUp(findViewById(R.id.multiplayerLayout), gameType + ": When Ready, Press Start Game To Begin. First Person To Buzz In Wins!", 1);
        startMultiplayerThread();

    }

    //Based on code from "http://www.tutorialspoint.com/android/android_alert_dialoges.htm"
    //Generates a PopUp Dialog with the inputted message, with the contents depending on the mode
    public void PopUp(View view, String message, int mode){
        AlertDialog.Builder popUpBuilder = new AlertDialog.Builder(this);
        popUpBuilder.setMessage(message);

        //Mode 1: To be called at the start of the Activity
        if (mode == 1){
            popUpBuilder.setNeutralButton("Start Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MultiplayerActivity.this, "Game Started", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //Mode 2: To be called after someone presses a buzzer, for them to restart or exit the game
        if (mode == 2) {
            popUpBuilder.setPositiveButton("Restart Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gameOver = false;
                    multiplayerGame = new MultiplayerGame(statsMan, numPlayers);
                    startMultiplayerThread();
                    Toast.makeText(MultiplayerActivity.this, "Game Restarted", Toast.LENGTH_SHORT).show();
                }
            });
            popUpBuilder.setNegativeButton("Exit Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MultiplayerActivity.this, "Game Exited", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
        AlertDialog popUpDialog = popUpBuilder.create();
        popUpDialog.show();
    }

    //Function to be run after the game, to notify the Players of the winner
    public void afterGame(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Buzzer winner = multiplayerGame.getWinner();
                PopUp(findViewById(R.id.multiplayerLayout),winner.getPlayerName() + " Won The " + gameType + "!", 2);
            }
        });
    }

    //Creates a new thread with a runnable, and starts it
    public void startMultiplayerThread(){
        Thread multiplayerThread = new Thread(multiplayerRunnable);
        multiplayerThread.start();
    }
}

