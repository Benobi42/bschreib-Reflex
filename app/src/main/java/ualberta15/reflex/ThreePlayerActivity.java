package ualberta15.reflex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ThreePlayerActivity extends AppCompatActivity {

    public ThreePlayerGame threePlayerGame;
    boolean gameOver = false;
    private String gameType = "Three Player Game";
    private StatisticManager statsMan;
    private IOManager1 myIOMan;

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

        myIOMan = (IOManager1) getIntent().getParcelableExtra("IOManager");
        statsMan = new StatisticManager(myIOMan,this);

        threePlayerGame = new ThreePlayerGame(statsMan);

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

        PopUp(findViewById(R.id.threePlayerLayout), gameType + ": When Ready, Press Start Game To Begin. First Person To Buzz In Wins!", 1);
        startThreePlayerThread();

    }

    public void PopUp(View v, String message, int mode){
        AlertDialog.Builder popUpBuilder = new AlertDialog.Builder(this);
        popUpBuilder.setMessage(message);

        if (mode == 1){
            popUpBuilder.setNeutralButton("Start Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ThreePlayerActivity.this, "Game Started", Toast.LENGTH_SHORT).show();
                }
            });
        }

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
                if (gameOver){
                    finish();
                }
            }
        });
        popUpDialog.show();
    }

    public void afterGame(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Buzzer winner = threePlayerGame.getWinner();
                PopUp(findViewById(R.id.threePlayerLayout), winner.getPlayerName() + " Won The " + gameType + "!", 2);
            }
        });
    }

    public void startThreePlayerThread(){
        Thread threePlayerThread = new Thread(ThreePlayerRunnable);
        threePlayerThread.start();
    }
}
