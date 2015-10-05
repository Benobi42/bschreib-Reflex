package ualberta15.reflex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MultiplayerActivity extends AppCompatActivity {

    private IOManager appIOMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        appIOMan = (IOManager) getIntent().getParcelableExtra("IOManager");

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
