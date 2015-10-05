package ualberta15.reflex;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by Ben on 03/10/2015.
 */
public class StatisticManager {
    private StatisticList statisticList;
    private static final String FILENAME = "stat.sav";
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

    public void addStat(String name, String type, int reactionTime){
        Statistic myStat = new Statistic(name, type, reactionTime);
        statisticList.addStat(myStat);
        myIOMan.saveStatsInFile(statisticList, myActivity);
    }

    public StatisticList getStatisticList() {
        return statisticList;
    }

    /*
    public void loadStatsFromFile() {
        try {
            FileInputStream fis = myActivity.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
            statisticList = gson.fromJson(in, StatisticList.class);
            fis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //statisticList = new StatisticList();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }catch (NullPointerException e){

        }
    }
        public void saveStatsInFile(){
        try {
            FileOutputStream fos = myActivity.openFileOutput(FILENAME, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            //ArrayList<Statistic> statistics = statisticList.getStatsList();
            gson.toJson(statisticList, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }


    public void loadStatsFromFile() {
        try {
            FileInputStream fis = myActivity.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            //based on method 1 at "http://www.programcreek.com/2011/03/java-read-a-file-line-by-line-code-example/"
            String line = null;
            while ((line = in.readLine()) != null){
                String[] statsStringSplit = line.split(":");
                Statistic thisStat = new Statistic(statsStringSplit[0], statsStringSplit[1], Integer.parseInt(statsStringSplit[2]));
            }
            fis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            statisticList = new StatisticList();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (NullPointerException e){

        }
    }


    public void saveStatsInFile(){
        try {
            FileOutputStream fos = myActivity.openFileOutput(FILENAME, 0);
            OutputStreamWriter out = new OutputStreamWriter(fos);
            //BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            for (int i = 0; i < statisticList.getStatsList().size(); i++){
                out.write(statisticList.getStat(i).toStatString());
            }
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }*/


    public void clearStats(){
        StatisticList blankStatList= new StatisticList();
        statisticList.setStatsList(blankStatList);
        myIOMan.saveStatsInFile(statisticList, myActivity);
    }

    public boolean emailStats(String address){
        return true;
    }

    public ArrayList<String> calculateStats(){
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
