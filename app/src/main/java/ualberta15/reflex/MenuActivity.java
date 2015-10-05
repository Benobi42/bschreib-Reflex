package ualberta15.reflex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Activity for the Main Menu of the Application
public class MenuActivity extends AppCompatActivity {
    //Generates a Input Output Manager to be passed throughout activities
    protected IOManager appIOMan = new IOManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Generate the buttons on the menu, and sets their Click Listeners to open different activities
        Button singlePlayerModeButton = (Button) findViewById(R.id.modeButtonSinglePlayer);
        singlePlayerModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMode(v, 1);
            }
        });

        Button multiPlayerModeButton = (Button) findViewById(R.id.modeButtonMultiPlayer);
        multiPlayerModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMode(v, 2);
            }
        });

        Button statisticButton = (Button) findViewById(R.id.modeButtonStatistics);
        statisticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMode(v, 3);
            }
        });
    }

    //Opens different activities depending on the inputted mode, as well as passing the IOManager to them
    public void changeMode(View view, int mode){
        Intent intent = null;
        if (mode == 1) {
            intent = new Intent(this, SinglePlayerActivity.class);
            intent.putExtra("IOManager", appIOMan);
        }
        if (mode == 2){
            intent = new Intent(this, MultiplayerActivity.class);
            intent.putExtra("IOManager", appIOMan);
        }
        if (mode ==3){
            intent = new Intent(this, StatisticsActivity.class);
            intent.putExtra("IOManager", appIOMan);
        }
        startActivity(intent);
    }

}
