package uk.co.alt236.btlescan.util;

/**
 * Created by Lambert on 2016/8/12.
 */

import java.util.ArrayList;

public class CountObj {
    ArrayList<Integer> array = new ArrayList<Integer>();

    public int getindex(int index) {
        return array.get(index);
    }

    public void inputArray(int t) {
        array.add(t);
    }

    public int getSize() {
        return array.size();
    }

    public double getAvg() {
        double sum = 0;
        for (int i = 0; i < array.size(); i++) {
            sum += array.get(i);
        }
        double avg = sum / array.size();

        return avg;
    }

    public double getStandardD() {
        double StandardD = 0;
        double avg = getAvg();
        double sum = 0;
        for (int i = 0; i < array.size(); i++) {
            sum += Math.pow(array.get(i) - avg, 2);
        }
        StandardD = Math.sqrt(sum / array.size());

        return StandardD;
    }

    public int getMax() {
        int max = -100;
        for (int i = 0; i < array.size(); i++) {
            if (max < array.get(i)) {
                max = array.get(i);
            }
        }
        return max;
    }

    public int getMin() {
        int min = -1;
        for (int i = 0; i < array.size(); i++) {
            if (min > array.get(i)) {
                min = array.get(i);
            }
        }
        return min;
    }

}

