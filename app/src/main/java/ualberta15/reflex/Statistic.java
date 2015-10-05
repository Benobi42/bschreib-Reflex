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

//Class that is a Statistic for reaction times and game-show buzzer wins
public class Statistic implements Comparable<Statistic> {
    private String name;
    private String type;
    private int reactionTime;

    public Statistic(String name, String type, int reactionTime) {
        this.name = name;
        this.type = type;
        this.reactionTime = reactionTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getReactionTime() {
        return reactionTime;
    }

    public void setReactionTime(int reactionTime) {
        this.reactionTime = reactionTime;
    }

    //Based on "http://www.mkyong.com/java/java-object-sorting-example-comparable-and-comparator/"
    //Compares the reaction times of two stats together, returning the difference
    public int compareTo(Statistic compareStat) {
        int compareReactionTime = ((Statistic) compareStat).getReactionTime();
        return this.reactionTime - compareReactionTime;
    }
}