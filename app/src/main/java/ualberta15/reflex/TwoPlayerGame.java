package ualberta15.reflex;

import android.app.AlertDialog;
import android.graphics.Color;

/**
 * Created by Ben on 01/10/2015.
 */
public class TwoPlayerGame {
    private Buzzer playerOneBuzzer;
    private Buzzer playerTwoBuzzer;
    private Buzzer winner;
    private StatisticManager statsMan;

    public TwoPlayerGame(StatisticManager statisticManager) {
        playerOneBuzzer = new Buzzer("Player One");
        int playerOneColor = Color.BLUE;
        playerOneBuzzer.setBuzzerColor(playerOneColor);
        playerTwoBuzzer = new Buzzer("Player Two");
        int playerTwoColor = Color.RED;
        playerTwoBuzzer.setBuzzerColor(playerTwoColor);
        statsMan = statisticManager;
    }

    public Buzzer getPlayerTwoBuzzer() {
        return playerTwoBuzzer;
    }

    public Buzzer getPlayerOneBuzzer() {
        return playerOneBuzzer;
    }

    public void pressBuzzerOne(){
        winner = playerOneBuzzer;
    }

    public void pressBuzzerTwo(){
        winner = playerTwoBuzzer;
    }

    public Buzzer getWinner() {
        return winner;
    }

    public boolean twoGame(){
        boolean gameOver = false;
        if (winner == null){
            gameOver = false;
        }
        else{
            statsMan.addStat(winner.getPlayerName(), "TwoPlayerWin", winner.timeAlive());
            gameOver = true;
        }
        return gameOver;
    }

}
