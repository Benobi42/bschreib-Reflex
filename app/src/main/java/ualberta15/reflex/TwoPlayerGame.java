package ualberta15.reflex;

import android.graphics.Color;

//Class to run a Two Player Multi-Buzzer Game
public class TwoPlayerGame {
    private Buzzer playerOneBuzzer;
    private Buzzer playerTwoBuzzer;
    private Buzzer winner;
    private StatisticManager statsMan;

    //Generates the Game, and sets each Buzzer to have a different name and color
    public TwoPlayerGame(StatisticManager statisticManager) {
        playerOneBuzzer = new Buzzer("Player One");
        int playerOneColor = Color.BLUE;
        playerOneBuzzer.setBuzzerColor(playerOneColor);
        playerTwoBuzzer = new Buzzer("Player Two");
        int playerTwoColor = Color.RED;
        playerTwoBuzzer.setBuzzerColor(playerTwoColor);
        statsMan = statisticManager;
    }

    //Getters and Setters
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

    //To Be Looped in the activity, checks if there has been a buzzer press signifying a winner
    //Returns a boolean signifying if there has been a winner
    public boolean twoGame(){
        boolean gameOver = false;
        if (winner == null){
            gameOver = false;
        }
        else{
            statsMan.AddStat(winner.getPlayerName(), "TwoPlayerWin", winner.timeAlive());
            gameOver = true;
        }
        return gameOver;
    }

}
