package ualberta15.reflex;

import android.graphics.Color;

/**
 * Created by Ben on 01/10/2015.
 */
public class FourPlayerGame {
    private Buzzer playerOneBuzzer;
    private Buzzer playerTwoBuzzer;
    private Buzzer playerThreeBuzzer;
    private Buzzer playerFourBuzzer;
    private Buzzer winner;
    private StatisticManager statsMan;

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

    public boolean fourGame(){
        boolean gameOver = false;
        if (winner == null){
            gameOver = false;
        }
        else{
            statsMan.AddStat(winner.getPlayerName(), "FourPlayerWin", winner.timeAlive());
            gameOver = true;
        }
        return gameOver;
    }


}
