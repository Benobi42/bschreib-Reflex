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

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//Parcelable functions based on developer reference at "http://developer.android.com/reference/android/os/Parcelable.html"
//Input Output Manager
//Function that allows for reading and writing to a File, to be maintained throughout all activities
public class IOManager implements Parcelable{
    //Name of the File that Statistics will be saved in
    private static String filename = "Statistics.sav";

    public IOManager() {
    }

    //Functions required for Parcelable Interface
    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeString(filename);
    }

    public static final Parcelable.Creator<IOManager> CREATOR = new Parcelable.Creator<IOManager>(){
        public IOManager createFromParcel(Parcel in){
            return new IOManager(in);
        }
        public IOManager[] newArray(int size){
            return new IOManager[size];
        }
    };

    //Constructor that generates the IOManager from a parcel
    private IOManager(Parcel in){
        filename = in.readString();
    }

    //Loads the Statistics, in json format, from the file and saves them into a StatisticList
    public void loadStatsFromFile(StatisticList statisticList, Context activity) {
        try {
            FileInputStream fis = activity.openFileInput(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
            StatisticList statsList = (gson.fromJson(in, StatisticList.class));
            statisticList.setStatsList(statsList);
            fis.close();
        } catch (FileNotFoundException e) {
            //Do Nothing
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (NullPointerException e){
            //Do Nothing
        }
    }

    //Saves the Statistics, from a StatisticList, to the file in json Format
    public void saveStatsInFile(StatisticList statisticList, Context activity){
        try {
            FileOutputStream fos = activity.openFileOutput(filename, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(statisticList, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }
}
