package ualberta15.reflex;

import android.graphics.Color;

/**
 * Created by Ben on 01/10/2015.
 */
public class ReflexGame {

    private int mode;
    private Buzzer reflexBuzzer;
    private StatisticManager statsMan;
    boolean gameOver = false;

    public ReflexGame(StatisticManager statisticManager) {
        this.statsMan = statisticManager;
        this.reflexBuzzer = new Buzzer("ReflexBuzzer");
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public Buzzer getReflexBuzzer() {
        return reflexBuzzer;
    }

    public int getBuzzerColor() {
        int myColor;
        if (reflexBuzzer.checkTime()) {
            myColor = Color.RED;
        }
        else {
            myColor = Color.GREEN;
        }
        return myColor;
    }

    public void Restart(){
        this.reflexBuzzer = new Buzzer("ReflexBuzzer");

    }

    public String onPress(){
        String pressString = null;
        if (reflexBuzzer.timeAlive() < 0){
            // Tell the player that they pressed too early
            pressString = "Button Was Pressed Too Early";
        }
        else{
            gameOver = true;
            int lifeTime = getReflexBuzzer().timeAlive();
            statsMan.AddStat("ReflexButton", "Reaction", lifeTime);
            pressString = "Congrats! Button Was Pressed After " + lifeTime + " Milliseconds";
        }
        return pressString;
    }

}
