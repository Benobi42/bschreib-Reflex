package ualberta15.reflex;

import android.graphics.Color;

//Class to run a Three Player Multi-Buzzer Game
public class ThreePlayerGame {
    private Buzzer playerOneBuzzer;
    private Buzzer playerTwoBuzzer;
    private Buzzer playerThreeBuzzer;
    private Buzzer winner;
    private StatisticManager statsMan;

    //Generates the Game, and sets each Buzzer to have a different name and color
    public ThreePlayerGame(StatisticManager statisticManager) {
        playerOneBuzzer = new Buzzer("Player One");
        int playerOneColor = Color.BLUE;
        playerOneBuzzer.setBuzzerColor(playerOneColor);
        playerTwoBuzzer = new Buzzer("Player Two");
        int playerTwoColor = Color.RED;
        playerTwoBuzzer.setBuzzerColor(playerTwoColor);
        playerThreeBuzzer = new Buzzer("Player Three");
        int playerThreeColor = Color.GREEN;
        playerThreeBuzzer.setBuzzerColor(playerThreeColor);
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

    public void pressBuzzerOne(){
        winner = playerOneBuzzer;
    }

    public void pressBuzzerTwo(){
        winner = playerTwoBuzzer;
    }

    public void pressBuzzerThree(){
        winner = playerThreeBuzzer;
    }

    public Buzzer getWinner() {
        return winner;
    }

    //To Be Looped in the activity, checks if there has been a buzzer press signifying a winner
    //Returns a boolean signifying if there has been a winner
    public boolean threeGame(){
        boolean gameOver = false;
        if (winner == null){
            gameOver = false;
        }
        else{
            statsMan.AddStat(winner.getPlayerName(), "ThreePlayerWin", winner.timeAlive());
            gameOver = true;
        }
        return gameOver;
    }


}
