package ualberta15.reflex;

import java.util.ArrayList;
import java.util.Collections;

// Class that has methods for calculating things from statisticLists
public class StatisticCalculator {

    public StatisticCalculator() {
    }

    // Calculates the means for the last 10, last 100 and all items within the StatisticList
    public int[] getMeans(StatisticList statisticList){
        int sumAll = 0;
        int sumHun = 0;
        int sumTen = 0;
        int numAll = 0;

        try{
            for (int i = statisticList.getStatsList().size()-1; i >= 0; i--){
                int reactionTime = statisticList.getStat(i).getReactionTime();
                if (statisticList.getStat(i).getType().equals("Reaction")){
                    if (numAll < 10){
                        sumTen += reactionTime;
                    }
                    if (numAll < 100){
                        sumHun += reactionTime;
                    }
                    sumAll += reactionTime;
                    numAll += 1;
                }
            }
        } catch (Exception e){

        }

        int meanAll;
        int meanHun;
        int meanTen;
        try{
            meanAll = sumAll/numAll;
        }catch (ArithmeticException e){
            meanAll = 0;
        }
        if (numAll < 100) {
            try{
                meanHun = sumHun/numAll;
            }catch (ArithmeticException e) {
                meanHun = 0;
            }
        }else{
            meanHun = sumHun/100;
        }
        if (numAll < 10){
            try{
                meanTen = sumTen/numAll;
            }catch (ArithmeticException e) {
                meanTen = 0;
            }
        }else{
            meanTen = sumTen/10;
        }


        int[] means = new int[3];
        means[0] = meanAll;
        means[1] = meanHun;
        means[2] = meanTen;
        return means;
    }

    // Calculates the minimum value for the last 10, last 100 and all items within the StatisticList
    public int[] getMins(StatisticList statisticList){
        int minAll = 0;
        int minHun = 0;
        int minTen = 0;
        int numAll = 0;

        try{
            for (int i = statisticList.getStatsList().size()-1; i >= 0; i--){
                int reactionTime = statisticList.getStat(i).getReactionTime();
                if (statisticList.getStat(i).getType().equals("Reaction")){
                    if (numAll < 10) {
                        if (minTen == 0){
                            minTen = reactionTime;
                        }
                        if (reactionTime < minTen) {
                            minTen = reactionTime;
                        }
                    }
                    if (numAll < 100) {
                        if (minHun == 0){
                            minHun = reactionTime;
                        }
                        if (reactionTime < minHun) {
                            minHun = reactionTime;
                        }
                    }

                    if (minAll == 0){
                        minAll = reactionTime;
                    }
                    if (reactionTime < minAll) {
                        minAll = reactionTime;
                    }
                    numAll += 1;
                }
            }
        } catch (Exception e){

        }

        int[] mins = new int[3];
        mins[0] = minAll;
        mins[1] = minHun;
        mins[2] = minTen;
        return mins;
    }

    // Calculates the maximum value for the last 10, last 100 and all items within the StatisticList
    public int[] getMaxes(StatisticList statisticList){
        int maxAll = 0;
        int maxHun = 0;
        int maxTen = 0;
        int numAll = 0;

        try{
            for (int i = statisticList.getStatsList().size()-1; i >= 0; i--){
                int reactionTime = statisticList.getStat(i).getReactionTime();
                if (statisticList.getStat(i).getType().equals("Reaction")){
                    if (numAll < 10) {
                        if (reactionTime > maxTen) {
                            maxTen = reactionTime;
                        }
                    }
                    if (numAll < 100) {
                        if (reactionTime > maxHun) {
                            maxHun = reactionTime;
                        }
                    }
                    if (reactionTime > maxAll) {
                        maxAll = reactionTime;
                    }
                    numAll += 1;
                }
            }
        } catch(Exception e){

        }

        int[] maxes = new int[3];
        maxes[0] = maxAll;
        maxes[1] = maxHun;
        maxes[2] = maxTen;
        return maxes;
    }

    // Calculates the median value for the last 10, last 100 and all items within the StatisticList (the item in the middle of the array)
    public int[] getMeds(StatisticList statisticList){

        int medAll = 0;
        int medHun = 0;
        int medTen = 0;

        ArrayList<Statistic> allReactionsList = new ArrayList<Statistic>();
        ArrayList<Statistic> hunReactionsList = new ArrayList<Statistic>();
        ArrayList<Statistic> tenReactionsList = new ArrayList<Statistic>();

        try{
            for (int i = statisticList.getStatsList().size()-1; i >= 0; i--){
                Statistic reactionStat = statisticList.getStat(i);
                if (statisticList.getStat(i).getType().equals("Reaction")){
                    if (tenReactionsList.size() < 10) {
                        tenReactionsList.add(reactionStat);
                    }
                    if (hunReactionsList.size() < 100) {
                        hunReactionsList.add(reactionStat);
                    }
                    allReactionsList.add(reactionStat);
                }
            }
        } catch ( Exception e){

        }

        Collections.sort(allReactionsList);
        Collections.sort(hunReactionsList);
        Collections.sort(tenReactionsList);

        try {
            medAll = allReactionsList.get((allReactionsList.size() / 2) ).getReactionTime();
            medHun = hunReactionsList.get((hunReactionsList.size() / 2) ).getReactionTime();
            medTen = tenReactionsList.get((tenReactionsList.size() / 2) ).getReactionTime();
        } catch (Exception e){

        }

        int[] meds = new int[3];
        meds[0] = medAll;
        meds[1] = medHun;
        meds[2] = medTen;
        return meds;
    }

    //Goes through the StatisticList and sees how many Two Player Game Show Statistics there are,
    // and how many each buzzer won
    public int[] getTwoPlayerBuzzes(StatisticList statisticList){
        int playerOneBuzzes = 0;
        int playerTwoBuzzes = 0;
        for (int i = 0; i < statisticList.getStatsList().size(); i++){
            Statistic myStat = statisticList.getStat(i);
            if (myStat.getType().equals("TwoPlayerWin")){
                if (myStat.getName().equals("Player One")){
                    playerOneBuzzes += 1;
                }
                if (myStat.getName().equals("Player Two")){
                    playerTwoBuzzes += 1;
                }
            }
        }
        int[] twoPlayerBuzzes = new int[2];
        twoPlayerBuzzes[0] = playerOneBuzzes;
        twoPlayerBuzzes[1] = playerTwoBuzzes;
        return twoPlayerBuzzes;
    }

    //Goes through the StatisticList and sees how many Three Player Game Show Statistics there are,
    // and how many each buzzer won
    public int[] getThreePlayerBuzzes(StatisticList statisticList){
        int playerOneBuzzes = 0;
        int playerTwoBuzzes = 0;
        int playerThreeBuzzes = 0;
        for (int i = 0; i < statisticList.getStatsList().size(); i++){
            Statistic myStat = statisticList.getStat(i);
            if (myStat.getType().equals("ThreePlayerWin")){
                if (myStat.getName().equals("Player One")){
                    playerOneBuzzes += 1;
                }
                if (myStat.getName().equals("Player Two")){
                    playerTwoBuzzes += 1;
                }
                if (myStat.getName().equals("Player Three")){
                    playerThreeBuzzes += 1;
                }
            }
        }
        int[] threePlayerBuzzes = new int[3];
        threePlayerBuzzes[0] = playerOneBuzzes;
        threePlayerBuzzes[1] = playerTwoBuzzes;
        threePlayerBuzzes[2] = playerThreeBuzzes;
        return threePlayerBuzzes;
    }

    //Goes through the StatisticList and sees how many Four Player Game Show Statistics there are,
    // and how many each buzzer won
    public int[] getFourPlayerBuzzes(StatisticList statisticList){
        int playerOneBuzzes = 0;
        int playerTwoBuzzes = 0;
        int playerThreeBuzzes = 0;
        int playerFourBuzzes = 0;
        for (int i = 0; i < statisticList.getStatsList().size(); i++){
            Statistic myStat = statisticList.getStat(i);
            if (myStat.getType().equals("FourPlayerWin")){
                if (myStat.getName().equals("Player One")){
                    playerOneBuzzes += 1;
                }
                if (myStat.getName().equals("Player Two")){
                    playerTwoBuzzes += 1;
                }
                if (myStat.getName().equals("Player Three")){
                    playerThreeBuzzes += 1;
                }
                if (myStat.getName().equals("Player Four")){
                    playerFourBuzzes += 1;
                }
            }
        }
        int[] fourPlayerBuzzes = new int[4];
        fourPlayerBuzzes[0] = playerOneBuzzes;
        fourPlayerBuzzes[1] = playerTwoBuzzes;
        fourPlayerBuzzes[2] = playerThreeBuzzes;
        fourPlayerBuzzes[3] = playerFourBuzzes;
        return fourPlayerBuzzes;
    }
}
