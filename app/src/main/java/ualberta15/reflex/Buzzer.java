package ualberta15.reflex;

import java.util.Random;

// Class that acts as a reaction timer and Button
public class Buzzer {
    private String playerName;
    private BuzzerTimer buzzerTimer;
    private int randomTime;
    private int buzzerColor;

    //Based on Answer by Erik at "http://stackoverflow.com/questions/5271598/java-generate-random-number-between-two-given-values"
    //Will generate a random number between two integer values
    public int randomBetween(int lowBound, int highBound){
        Random r = new Random();
        int between = r.nextInt(highBound - lowBound) + lowBound;
        return between;
    }

    public Buzzer(String playerName) {
        this.playerName = playerName;
        this.buzzerTimer = new BuzzerTimer();
        //Generate a random integer between 10 and 2000, to be used as the random time difference
        //for ReflexGame
        this.randomTime = randomBetween(10, 2000);
    }

    //Check if the Buzzer has been alive for the random amount of time prescribed.
    public boolean checkTime() {
        if(this.randomTime > this.buzzerTimer.getTimeAlive()) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getBuzzerColor() {
        return buzzerColor;
    }

    public void setBuzzerColor(int buzzerColor) {
        this.buzzerColor = buzzerColor;
    }

    //Returns the time that the buzzer has been "alive", meaning the time since its creation
    //minus the random time difference
    public int timeAlive() {
        return this.buzzerTimer.getTimeAlive() - randomTime;
    }

}
