package ualberta15.reflex;

import java.util.ArrayList;

//Class that holds and maintains an arrayList of Statistics
public class StatisticList {
    private ArrayList<Statistic> statsList;

    public StatisticList() {
        this.statsList = new ArrayList<Statistic>();
    }

    public ArrayList<Statistic> getStatsList() {
        return statsList;
    }

    public void setStatsList(StatisticList statisticList) {
        this.statsList = statisticList.getStatsList();
    }

    //Returns a specific Statistic at the inputted index
    public Statistic getStat(int index){
        return statsList.get(index);
    }

    //Adds a Statistic to the end of the ArrayList
    public void addStat(Statistic stat){
        statsList.add(stat);
    }
}
