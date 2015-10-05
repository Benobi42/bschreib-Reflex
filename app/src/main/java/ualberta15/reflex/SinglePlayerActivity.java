/*Licensed to the Apache Software Foundation (ASF) under one
        or more contributor license agreements.  See the NOTICE file
        distributed with this work for additional information
        regarding copyright ownership.  The ASF licenses this file
        to you under the Apache License, Version 2.0 (the
        "License"); you may not use this file except in compliance
        with the License.  You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing,
        software distributed under the License is distributed on an
        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
        KIND, either express or implied.  See the License for the
        specific language governing permissions and limitations
        under the License.
*/

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

//Activity to run functions for a single player reaction timer
public class SinglePlayerActivity extends AppCompatActivity {

    private ReflexGame singlePlayerGame;
    private Button buzzerButton;
    boolean gameRunning = true;
    private StatisticManager statsMan;
    private IOManager myIOMan;

    //Based on answer by Zaid at "http://stackoverflow.com/questions/7478941/implementing-a-while-loop-in-android"
    //Runnable function to be run on a seperate thread, allowing for UI interaction as well as a loop
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
        setContentView(R.layout.activity_single_player);

        //Recieve the Input Output Manager from the calling activity and generate the Statistics Manager with it
        myIOMan = (IOManager) getIntent().getParcelableExtra("IOManager");
        statsMan = new StatisticManager(myIOMan,this);

        //Generate a Button to be used as a reaction timer
        buzzerButton = (Button) findViewById(R.id.singlePlayerButton);
        ChangeButtonColor(buzzerButton, Color.RED);
        buzzerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameRunning = false;
                //Create a PopUp Dialog to notify the player how long it took them to press the
                //button, or if they pressed the button to early
                PopUp(findViewById(R.id.singlePlayerLayout), singlePlayerGame.onPress(), 2);
                SystemClock.sleep(100);
            }
        });

        //Create a PopUp Dialog to notify the Players of the rules
        PopUp(findViewById(R.id.singlePlayerLayout), "Wait Until The Button Turns Green To Press. If Pressed Early, The Game Will Fail And Reset", 1);

    }

    //Sets gameRunning to be false when the activity is destroyed, so that the thread doesn't continue
    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameRunning = false;
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
                    ChangeButtonColor(buzzerButton, Color.RED);
                    singlePlayerGame = new ReflexGame(statsMan);
                    StartSingleThread();
                    Toast.makeText(SinglePlayerActivity.this, "Game Started", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //Mode 2: To be called after someone presses a buzzer, for them to restart the game
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

    //Changes the inputteted buttons display color to the Color defined by the integer setColor
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
