package ualberta15.reflex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FourPlayerActivity extends AppCompatActivity {

    public FourPlayerGame fourPlayerGame;
    boolean gameOver = false;
    private String gameType = "Four Player Game";
    private StatisticManager statsMan;
    private IOManager myIOMan;

    Runnable FourPlayerRunnable = new Runnable() {
        @Override
        public void run() {
            while (!gameOver){
                gameOver = fourPlayerGame.fourGame();
            }
            afterGame();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_player);

        myIOMan = (IOManager) getIntent().getParcelableExtra("IOManager");
        statsMan = new StatisticManager(myIOMan, this);

        fourPlayerGame = new FourPlayerGame(statsMan);

        Button playerOne = (Button) findViewById(R.id.PlayerOneButton);
        playerOne.setBackgroundColor(fourPlayerGame.getPlayerOneBuzzer().getBuzzerColor());
        playerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fourPlayerGame.pressBuzzerOne();
            }
        });


        Button playerTwo = (Button) findViewById(R.id.PlayerTwoButton);
        playerTwo.setBackgroundColor(fourPlayerGame.getPlayerTwoBuzzer().getBuzzerColor());
        playerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fourPlayerGame.pressBuzzerTwo();
            }
        });

        Button playerThree = (Button) findViewById(R.id.PlayerThreeButton);
        playerThree.setBackgroundColor(fourPlayerGame.getPlayerThreeBuzzer().getBuzzerColor());
        playerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fourPlayerGame.pressBuzzerThree();
            }
        });

        Button playerFour = (Button) findViewById(R.id.PlayerFourButton);
        playerFour.setBackgroundColor(fourPlayerGame.getPlayerFourBuzzer().getBuzzerColor());
        playerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fourPlayerGame.pressBuzzerFour();
            }
        });

        PopUp(findViewById(R.id.fourPlayerLayout), gameType + ": When Ready, Press Start Game To Begin. First Person To Buzz In Wins!", 1);
        startFourPlayerThread();

    }

    public void PopUp(View view, String message, int mode){
        AlertDialog.Builder popUpBuilder = new AlertDialog.Builder(this);
        popUpBuilder.setMessage(message);

        if (mode == 1){
            popUpBuilder.setNeutralButton("Start Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(FourPlayerActivity.this, "Game Started", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (mode == 2) {
            popUpBuilder.setPositiveButton("Restart Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gameOver = false;
                    fourPlayerGame = new FourPlayerGame(statsMan);
                    startFourPlayerThread();
                    Toast.makeText(FourPlayerActivity.this, "Game Restarted", Toast.LENGTH_SHORT).show();
                }
            });
            popUpBuilder.setNegativeButton("Exit Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(FourPlayerActivity.this, "Game Exited", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
        AlertDialog popUpDialog = popUpBuilder.create();
        popUpDialog.show();
    }

    public void afterGame(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Buzzer winner = fourPlayerGame.getWinner();
                PopUp(findViewById(R.id.fourPlayerLayout),winner.getPlayerName() + " Won The " + gameType + "!", 2);
            }
        });
    }

    public void startFourPlayerThread(){
        Thread fourPlayerThread = new Thread(FourPlayerRunnable);
        fourPlayerThread.start();
    }
}

