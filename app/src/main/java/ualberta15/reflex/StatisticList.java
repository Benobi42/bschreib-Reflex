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

import java.util.ArrayList;

//Class that holds and maintains an arrayList of Statistics
public class StatisticList {
    private ArrayList<Statistic> statsList;

    public StatisticList() {
        this.statsList = new ArrayList<Statistic>();
    }

    public ArrayList<Statistic> getStatsList() {
        return statsList;
    }

    public void setStatsList(StatisticList statisticList) {
        this.statsList = statisticList.getStatsList();
    }

    //Returns a specific Statistic at the inputted index
    public Statistic getStat(int index){
        return statsList.get(index);
    }

    //Adds a Statistic to the end of the ArrayList
    public void addStat(Statistic stat){
        statsList.add(stat);
    }
}
