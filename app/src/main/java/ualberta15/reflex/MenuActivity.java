package ualberta15.reflex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    protected IOManager1 appIOMan = new IOManager1();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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
