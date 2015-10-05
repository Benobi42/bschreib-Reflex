/*Licensed to the Apache Software Foundation (ASF) under one
        or more contributor license agreements.  See the NOTICE file
        distributed with this work for additional information
        regarding copyright ownership.  The ASF licenses this file
        to you under the Apache License, Version 2.0 (the
        "License"); you may not use this file except in compliance
        with the License.  You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing,
        software distributed under the License is distributed on an
        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
        KIND, either express or implied.  See the License for the
        specific language governing permissions and limitations
        under the License.
*/
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
        return (r.nextInt(highBound - lowBound) + lowBound);
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
