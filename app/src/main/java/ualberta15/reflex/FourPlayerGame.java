package ualberta15.reflex;

import android.graphics.Color;

//Class to run a Four Player Multi-Buzzer Game
public class FourPlayerGame {
    private Buzzer playerOneBuzzer;
    private Buzzer playerTwoBuzzer;
    private Buzzer playerThreeBuzzer;
    private Buzzer playerFourBuzzer;
    private Buzzer winner;
    private StatisticManager statsMan;

    //Generates the Game, and sets each Buzzer to have a different name and color
    public FourPlayerGame(StatisticManager statisticManager) {
        playerOneBuzzer = new Buzzer("Player One");
        int playerOneColor = Color.BLUE;
        playerOneBuzzer.setBuzzerColor(playerOneColor);
        playerTwoBuzzer = new Buzzer("Player Two");
        int playerTwoColor = Color.RED;
        playerTwoBuzzer.setBuzzerColor(playerTwoColor);
        playerThreeBuzzer = new Buzzer("Player Three");
        int playerThreeColor = Color.GREEN;
        playerThreeBuzzer.setBuzzerColor(playerThreeColor);
        playerFourBuzzer = new Buzzer("Player Four");
        int playerFourColor = Color.YELLOW;
        playerFourBuzzer.setBuzzerColor(playerFourColor);
        statsMan = statisticManager;
    }

    //Getters and Setters
    public Buzzer getPlayerOneBuzzer() {
        return playerOneBuzzer;
    }

    public Buzzer getPlayerTwoBuzzer() {
        return playerTwoBuzzer;
    }

    public Buzzer getPlayerThreeBuzzer() {
        return playerThreeBuzzer;
    }

    public Buzzer getPlayerFourBuzzer() {
        return playerFourBuzzer;
    }

    public void pressBuzzerOne(){
        winner = playerOneBuzzer;
    }

    public void pressBuzzerTwo(){
        winner = playerTwoBuzzer;
    }

    public void pressBuzzerThree(){
        winner = playerThreeBuzzer;
    }

    public void pressBuzzerFour(){
        winner = playerFourBuzzer;
    }

    public Buzzer getWinner() {
        return winner;
    }

    //To Be Looped in the activity, checks if there has been a buzzer press signifying a winner
    //Returns a boolean signifying if there has been a winner
    public boolean fourGame(){
        boolean gameOver = false;
        if (winner == null){
            gameOver = false;
    }
        else{
            //Adds a new Statistic to the Statistic Manager signifying the winner of the game
            statsMan.AddStat(winner.getPlayerName(), "FourPlayerWin", winner.timeAlive());
            gameOver = true;
        }
        return gameOver;
    }


}
