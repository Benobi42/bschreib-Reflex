package ualberta15.reflex;

import java.util.ArrayList;

/**
 * Created by Ben on 03/10/2015.
 */
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

    public Statistic getStat(int index){
        return statsList.get(index);
    }

    public void addStat(Statistic stat){
        statsList.add(stat);
    }
}
