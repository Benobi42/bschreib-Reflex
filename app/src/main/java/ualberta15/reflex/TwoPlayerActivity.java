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

public class TwoPlayerActivity extends AppCompatActivity {

    public TwoPlayerGame twoPlayerGame;
    boolean gameOver = false;
    private String gameType = "Two Player Game";
    private StatisticManager statsMan;
    private IOManager myIOMan;

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

        myIOMan = (IOManager) getIntent().getParcelableExtra("IOManager");
        statsMan = new StatisticManager(myIOMan,this);

        twoPlayerGame = new TwoPlayerGame(statsMan);

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

        PopUp(findViewById(R.id.twoPlayerLayout), gameType + ": When Ready, Press Start Game To Begin. First Person To Buzz In Wins!", 1);
        startTwoPlayerThread();

    }

    public void PopUp(View v, String message, int mode){
        AlertDialog.Builder popUpBuilder = new AlertDialog.Builder(this);
        popUpBuilder.setMessage(message);

        if (mode == 1){
            popUpBuilder.setNeutralButton("Start Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(TwoPlayerActivity.this, "Game Started", Toast.LENGTH_SHORT).show();
                }
            });
        }

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

    public void afterGame(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Buzzer winner = twoPlayerGame.getWinner();
                PopUp(findViewById(R.id.twoPlayerLayout), winner.getPlayerName() + " Won The " + gameType + "!", 2);
            }
        });
    }

    public void startTwoPlayerThread(){
        Thread twoPlayerThread = new Thread(twoPlayerRunnable);
        twoPlayerThread.start();
    }
}
