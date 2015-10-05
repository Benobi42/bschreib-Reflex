package ualberta15.reflex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SinglePlayerActivity extends AppCompatActivity {

    private ReflexGame singlePlayerGame;
    private Button buzzerButton;
    boolean gameRunning = true;
    private StatisticManager statsMan;
    private IOManager myIOMan;

    //Based on answer by Zaid at "http://stackoverflow.com/questions/7478941/implementing-a-while-loop-in-android"
    Runnable gameRunnable = new Runnable() {
        @Override
        public void run() {
            while(gameRunning){
               ChangeButtonColor(buzzerButton, singlePlayerGame.getBuzzerColor());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myIOMan = (IOManager) getIntent().getParcelableExtra("IOManager");
        statsMan = new StatisticManager(myIOMan,this);

        setContentView(R.layout.activity_single_player);

        buzzerButton = (Button) findViewById(R.id.singlePlayerButton);
        ChangeButtonColor(buzzerButton, Color.RED);
        buzzerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameRunning = false;
                //ChangeButtonColor(buzzerButton, singlePlayerGame.getBuzzerColor());
                PopUp(findViewById(R.id.singlePlayerLayout), singlePlayerGame.onPress(), 2);
                SystemClock.sleep(100);
            }
        });

        PopUp(findViewById(R.id.singlePlayerLayout), "Wait Until The Button Turns Green To Press. If Pressed Early, The Game Will Fail And Reset", 1);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameRunning = false;
    }

    //Based on code from "http://www.tutorialspoint.com/android/android_alert_dialoges.htm"
    public void PopUp(View v, String message, int mode){
        AlertDialog.Builder popUpBuilder = new AlertDialog.Builder(this);
        popUpBuilder.setMessage(message);

        if (mode == 1){
            popUpBuilder.setNeutralButton("Start Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ChangeButtonColor(buzzerButton, Color.RED);
                    singlePlayerGame = new ReflexGame(statsMan);
                    StartSingleThread();
                    Toast.makeText(SinglePlayerActivity.this, "Game Started", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (mode == 2) {
            popUpBuilder.setNeutralButton("Restart Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Reset the game
                    ChangeButtonColor(buzzerButton, Color.RED);
                    singlePlayerGame.Restart();
                    StartSingleThread();
                    Toast.makeText(SinglePlayerActivity.this, "Game Restarted", Toast.LENGTH_SHORT).show();
                }
            });
        }
        AlertDialog popUpDialog = popUpBuilder.create();
        popUpDialog.show();
    }

    public void ChangeButtonColor(Button button, int setColor){
        //Based on answer from tyczj at "http://stackoverflow.com/questions/27549763/editing-a-buttons-colour-in-a-nonui-thread-android"
        final Button buttonToChange = button;
        final int newButtonColor = setColor;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                buttonToChange.setBackgroundColor(newButtonColor);
            }
        });
    }

    public void StartSingleThread(){
        gameRunning = true;
        Thread gameThread = new Thread(gameRunnable);
        gameThread.start();

    }

}
