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

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

// Class that holds a StatisticList and has methods that act on it
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

    //Creates a new Statistic with the inputted name, type and reactionTime, and adds that
    //Statistic to the StatisticList, then saves the updated StatisticList in the File
    public void AddStat(String name, String type, int reactionTime){
        Statistic myStat = new Statistic(name, type, reactionTime);
        statisticList.addStat(myStat);
        myIOMan.saveStatsInFile(statisticList, myActivity);
    }

    public StatisticList GetStatisticList() {
        return statisticList;
    }

    //Clears the StatisticList and saves the blank list in the File
    public void ClearStats(){
        StatisticList blankStatList= new StatisticList();
        statisticList.setStatsList(blankStatList);
        myIOMan.saveStatsInFile(statisticList, myActivity);
    }

    //based on example at "https://developer.android.com/training/basics/intents/sending.html"
    //Emails the calculated Statistics (Mean, Min, Max, Median, Multiplayer Buzzes) to an
    //inputted email address using a pre-set up email program
    public boolean EmailStats(String address){
        //Calculates the stats, and saves the resulting strings in an ArrayList
        ArrayList<String> calculatedStats = CalculateStats();
        //Creates a StringBuffer with the size of the calculatedStats ArrayList
        StringBuffer statsBuffer = new StringBuffer(calculatedStats.size());
        for (int i = 0; i < calculatedStats.size(); i++){
            //For every item in the String Arraylist, add the string to the buffer and close with a
            //newline character
            statsBuffer.append(calculatedStats.get(i)+'\n');
        }
        //Create a string from the StringBuffer
        String statsString = new String(statsBuffer);

        //Tries to email the String of Calculated Statistics to the inputted email address
        try{
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", address, null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Statistics from bschreib-Reflex");
            emailIntent.putExtra(Intent.EXTRA_TEXT, statsString);
            PackageManager packMan = myActivity.getPackageManager();
            List<ResolveInfo> activities = packMan.queryIntentActivities(emailIntent, 0);
            boolean isIntentSafe = (activities.size() > 0);
            if (isIntentSafe){
                myActivity.startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        }catch (Exception e){
            //If it fails, notify the caller that it was unable to email
            return false;
        }
        //If the emailing was successful, notify the callse
        return true;
    }

    //Returns an ArrayList of Strings, where each String is a certain type of calculated Statistic
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
