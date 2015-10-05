package ualberta15.reflex;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    private StatisticManager statsMan;
    private ListView calculatedList;
    private ArrayList<String> calculatedStats;
    private ArrayAdapter<String> calculatedAdapter;
    private Activity me = this;
    private IOManager myIOMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        calculatedList = (ListView) findViewById(R.id.statisticsList);

        myIOMan = (IOManager) getIntent().getParcelableExtra("IOManager");
        statsMan = new StatisticManager(myIOMan, this);

        Button clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statsMan.clearStats();
                setStatsView();
            }
        });

        Button emailButton = (Button) findViewById(R.id.emailButton);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUp(v, "Enter Email Address");

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        myIOMan.loadStatsFromFile(statsMan.getStatisticList(), this);
        setStatsView();
    }

    //Based on code from "http://www.tutorialspoint.com/android/android_alert_dialoges.htm"
    public void PopUp(View v, String message){
        final AlertDialog.Builder popUpBuilder = new AlertDialog.Builder(this);
        popUpBuilder.setMessage(message);
        final EditText addressLine= new EditText(v.getContext());
        popUpBuilder.setView(addressLine);
        popUpBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Based on answer by Syeda at "http://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog"
                String address = addressLine.getText().toString();
                boolean emailed = statsMan.emailStats(address);
                if (emailed){
                    Toast.makeText(StatisticsActivity.this, "Statistics Emailed To " + address, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(StatisticsActivity.this, "Statistics Failed To Email", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog popUpDialog = popUpBuilder.create();
        popUpDialog.show();
    }

    public void setStatsView () {
        calculatedStats = statsMan.calculateStats();
        calculatedAdapter = new ArrayAdapter<String>(me, R.layout.stats_list, calculatedStats);
        calculatedList.setAdapter(calculatedAdapter);
        calculatedAdapter.notifyDataSetChanged();
    }

}
