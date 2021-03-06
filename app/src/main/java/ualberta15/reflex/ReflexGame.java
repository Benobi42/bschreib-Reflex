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

import android.graphics.Color;

//Class that runs a single player reaction time buzzer
public class ReflexGame {

    private Buzzer reflexBuzzer;
    private StatisticManager statsMan;

    public ReflexGame(StatisticManager statisticManager) {
        this.statsMan = statisticManager;
        this.reflexBuzzer = new Buzzer("ReflexBuzzer");
    }

    public Buzzer getReflexBuzzer() {
        return reflexBuzzer;
    }

    //Returns a integer, as defined in Color, that corresponds to a particular color depending on
    //if the reflexBuzzer has been alive for more or less time than its randomly generated break-time
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

    //sets the reflexBuzzer to a new Buzzer, creating a new random time difference, thereby restarting the game
    public void Restart(){
        this.reflexBuzzer = new Buzzer("ReflexBuzzer");

    }

    //Returns a string depending on if the reflex buzzer has been alive longer than its randomly generated break-time
    public String onPress(){
        String pressString = null;
        if (reflexBuzzer.timeAlive() < 0){
            // Tell the player that they pressed too early
            pressString = "Button Was Pressed Too Early";
        }
        else{
            //The amount of time that the buzzer has been greater than it's break-time
            int lifeTime = getReflexBuzzer().timeAlive();
            //Generates a stat and adds it to the StatisticManager
            statsMan.AddStat("ReflexButton", "Reaction", lifeTime);
            //Tell the player how long it took for them to press the buzzer
            pressString = "Congrats! Button Was Pressed After " + lifeTime + " Milliseconds";
        }
        return pressString;
    }

}
