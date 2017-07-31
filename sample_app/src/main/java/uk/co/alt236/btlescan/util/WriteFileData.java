package uk.co.alt236.btlescan.util;

/**
 * Created by Lambert on 2016/8/12.
 */
public class WriteFileData {
    public String TimeStamp;
    public int Minor;
    public int RSSI;

    public void setTimeStamp(String T){
        TimeStamp = T;
    }

    public String getTimeStamp(){
        return TimeStamp;
    }

    public void setMinor(int M){
        Minor = M;
    }

    public int getMinor(){
        return Minor;
    }

    public void setRSSI(int R){
        RSSI = R;
    }

    public int getRSSI(){
        return RSSI;
    }
}

