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
public class IOManager1 implements Parcelable{
    //private Context activity;
    private String FILENAME = "FILE1.sav";

    public IOManager1() {
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeString(FILENAME);
    }

    public static final Parcelable.Creator<IOManager1> CREATOR = new Parcelable.Creator<IOManager1>(){
        public IOManager1 createFromParcel(Parcel in){
            return new IOManager1(in);
        }
        public IOManager1[] newArray(int size){
            return new IOManager1[size];
        }
    };

    private IOManager1(Parcel in){
        FILENAME = in.readString();
    }

    public void loadStatsFromFile(StatisticList statisticList, Context activity) {
        try {
            FileInputStream fis = activity.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
            StatisticList statsList = (gson.fromJson(in, StatisticList.class));
            statisticList.setStatsList(statsList);
            fis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            int i = 0;
            //statisticList = new StatisticList();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }catch (NullPointerException e){
            int i = 0;
        }
    }
    public void saveStatsInFile(StatisticList statisticList, Context activity){
        try {
            FileOutputStream fos = activity.openFileOutput(FILENAME, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            //ArrayList<Statistic> statistics = statisticList.getStatsList();
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
