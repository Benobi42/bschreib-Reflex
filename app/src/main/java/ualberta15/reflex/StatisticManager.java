package ualberta15.reflex;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;


public class StatisticManager {
    private StatisticList statisticList;
    private IOManager myIOMan;
    private StatisticCalculator statsCalc;
    private Activity myActivity;


    public StatisticManager(IOManager IOMan, Activity activity) {
        statisticList = new StatisticList();
        statsCalc = new StatisticCalculator();
        myActivity = activity;
        myIOMan = IOMan;
        myIOMan.loadStatsFromFile(statisticList, myActivity);
    }

    public void AddStat(String name, String type, int reactionTime){
        Statistic myStat = new Statistic(name, type, reactionTime);
        statisticList.addStat(myStat);
        myIOMan.saveStatsInFile(statisticList, myActivity);
    }

    public StatisticList GetStatisticList() {
        return statisticList;
    }

    public void ClearStats(){
        StatisticList blankStatList= new StatisticList();
        statisticList.setStatsList(blankStatList);
        myIOMan.saveStatsInFile(statisticList, myActivity);
    }

    //based on example at "https://developer.android.com/training/basics/intents/sending.html"
    public boolean EmailStats(String address){
        ArrayList<String> calculatedStats = CalculateStats();
        StringBuffer statsBuffer = new StringBuffer(calculatedStats.size());
        for (int i = 0; i < calculatedStats.size(); i++){
            statsBuffer.append(calculatedStats.get(i)+'\n');
        }
        String statsString = new String(statsBuffer);
        try{
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", address, null));
            // type
            //emailIntent.setType("mailto");
            //emailIntent.putExtra(Intent.EXTRA_EMAIL, address); // recipient address
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Statistics from bschreib-Reflex");
            emailIntent.putExtra(Intent.EXTRA_TEXT, statsString);
            PackageManager packMan = myActivity.getPackageManager();
            List<ResolveInfo> activities = packMan.queryIntentActivities(emailIntent, 0);
            boolean isIntentSafe = (activities.size() > 0);
            if (isIntentSafe){
                myActivity.startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public ArrayList<String> CalculateStats(){
        ArrayList<String> calcArray = new ArrayList<String>();

        int[] means = statsCalc.getMeans(statisticList);
        String meansString = "Mean- All: " + means[0] + " Last Hundred: "
                + means[1] + " Last Ten: " + means[2];
        calcArray.add(meansString);

        int[] mins = statsCalc.getMins(statisticList);
        String minsString = "Minimum- All: " + mins[0] + " Last Hundred: "
                + mins[1] + "\nLast Ten: " + mins[2];
        calcArray.add(minsString);

        int[] meds = statsCalc.getMeds(statisticList);
        String medsString = "Median- All: " + meds[0] + " Last Hundred: "
                + meds[1] + "\nLast Ten: " + meds[2];
        calcArray.add(medsString);

        int[] maxes = statsCalc.getMaxes(statisticList);
        String maxesString = "Maximum- All: " + maxes[0] + " Last Hundred: "
                + maxes[1] + "\nLast Ten: " + maxes[2];
        calcArray.add(maxesString);

        int[] twoPlayerBuzzes = statsCalc.getTwoPlayerBuzzes(statisticList);
        String twoPlayerString = "Two Player Buzzes- Player One: " + twoPlayerBuzzes[0]
                + "\nPlayer Two: " + twoPlayerBuzzes[1];
        calcArray.add(twoPlayerString);

        int[] threePlayerBuzzes = statsCalc.getThreePlayerBuzzes(statisticList);
        String threePlayerString = "Three Player Buzzes- Player One: " + threePlayerBuzzes[0]
                + "\nPlayer Two: " + threePlayerBuzzes[1] + " Player Three: " + threePlayerBuzzes[2];
        calcArray.add(threePlayerString);

        int[] fourPlayerBuzzes = statsCalc.getFourPlayerBuzzes(statisticList);
        String fourPlayerString = "Four Player Buzzes- Player One: " + fourPlayerBuzzes[0]
                + "\nPlayer Two: " + fourPlayerBuzzes[1] + " Player Three: " + fourPlayerBuzzes[2]
                + "\nPlayer Four: " + fourPlayerBuzzes[3];
        calcArray.add(fourPlayerString);

        return calcArray;
    }

}
