package ualberta15.reflex;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

//Activity that displays the Statistics based on the saved Statistics File in the IOManager
public class StatisticsActivity extends AppCompatActivity {

    private StatisticManager statsMan;
    private ListView calculatedList;
    private IOManager myIOMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        calculatedList = (ListView) findViewById(R.id.statisticsList);

        //Recieve the Input Output Manager from the calling activity and generate the Statistics Manager with it
        myIOMan = (IOManager) getIntent().getParcelableExtra("IOManager");
        statsMan = new StatisticManager(myIOMan, this);

        //Generate Buttons to Clear the Stats orEmail Them
        Button clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Clear the Statistics in the StatisticManager
                statsMan.ClearStats();
                //Update the display with the cleared StatisticList
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
        myIOMan.loadStatsFromFile(statsMan.GetStatisticList(), this);
        setStatsView();
    }

    //Based on code from "http://www.tutorialspoint.com/android/android_alert_dialoges.htm"
    //Generates a PopUp Dialog with an editable textView for the user to enter an email for emailing the Statistics to
    public void PopUp(View v, String message){
        final AlertDialog.Builder popUpBuilder = new AlertDialog.Builder(this);
        popUpBuilder.setMessage(message);
        final EditText addressLine= new EditText(v.getContext());
        popUpBuilder.setView(addressLine);
        AlertDialog.Builder send = popUpBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Based on answer by Syeda at "http://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog"
                String address = addressLine.getText().toString();
                boolean emailed = statsMan.EmailStats(address);
                if (emailed) {
                    Toast.makeText(StatisticsActivity.this, "Statistics Emailing", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StatisticsActivity.this, "Statistics Failed To Email", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog popUpDialog = popUpBuilder.create();
        popUpDialog.show();
    }

    //Sets the Strings of Statistics to be Displayed on the Screen, and notifies the adapter that it has been changed
    public void setStatsView () {
        ArrayList<String> calculatedStats = statsMan.CalculateStats();
        ArrayAdapter<String> calculatedAdapter = new ArrayAdapter<String>(this, R.layout.stats_list, calculatedStats);
        calculatedList.setAdapter(calculatedAdapter);
        calculatedAdapter.notifyDataSetChanged();
    }

}
