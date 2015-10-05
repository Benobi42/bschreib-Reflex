package ualberta15.reflex;

// Class to track the time that it has been alive
public class BuzzerTimer {
    private int startTime;

    //Constructor for buzzer timer, setting the start time to the current system time
    public BuzzerTimer() {
        startTime = (int) System.currentTimeMillis();
    }

    //Returns the time that the buzzer has been alive
    public int getTimeAlive(){
        int timeAlive = ((int) System.currentTimeMillis()) - startTime;
        return timeAlive;
    }
}
