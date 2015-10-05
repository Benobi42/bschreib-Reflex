package ualberta15.reflex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Activity that opens the various Game Show Buzzer Modes
public class MultiplayerActivity extends AppCompatActivity {

    //Input Output Manager, to be passed from a higher activity
    private IOManager appIOMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        //Recieves the IOManager from the parent activity
        appIOMan = (IOManager) getIntent().getParcelableExtra("IOManager");

        //Generate the buttons on the menu, and sets their Click Listeners to open different activities
        Button twoPlayerButton = (Button) findViewById(R.id.TwoPlayerButton);
        twoPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMode(v, 1);
            }
        });

        Button threePlayerButton = (Button) findViewById(R.id.ThreePlayerButton);
        threePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMode(v, 2);
            }
        });

        Button fourPlayerButton = (Button) findViewById(R.id.FourPlayerButton);
        fourPlayerButton.setOnClickListener(new View.OnClickListener() {
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
            intent = new Intent(this, TwoPlayerActivity.class);
            intent.putExtra("IOManager", appIOMan);
        }
        if (mode == 2){
            intent = new Intent(this, ThreePlayerActivity.class);
            intent.putExtra("IOManager", appIOMan);
        }
        if (mode == 3){
            intent = new Intent(this, FourPlayerActivity.class);
            intent.putExtra("IOManager", appIOMan);
        }
        startActivity(intent);
    }

}
