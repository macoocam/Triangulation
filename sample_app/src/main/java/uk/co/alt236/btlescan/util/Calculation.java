package uk.co.alt236.btlescan.util;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Calculation {

    private double X = 0, Y = 0, sum = 0;

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getSum() {
        return sum;
    }

    private class Point {
        double X, Y, Weight;

        private Point(double x, double y, double weight) {
            X = x;
            Y = y;
            Weight = weight;
        }
    }

    public void CustomSubspceFingerPrint(double[] calbeacon) {
        // TODO Auto-generated method stub
        ArrayList<Calculation> tmp = new ArrayList<>();
        Calculation c;
        DataBase db = new DataBase();
        double[][] testlist = db.getArray();
        for (int i = 0; i < testlist.length; i++) {//和資料的點計算間距
            c = new Calculation();
            for (int j = 0; j < calbeacon.length; j++) {
                c.sum = c.sum + Math.pow(testlist[i][j] - calbeacon[j], 2);
                c.X = i / 5;
                c.Y = i % 5;
            }
            c.sum = Math.sqrt(c.sum);//歐幾里德距離
            tmp.add(c);
        }

        ArrayList<Point> nearP = new ArrayList<>();//離定位點的最近四點
        for (int i = 0; i < 4; i++) {//4NN
            double min = 100;//初始化
            int temp = 0;
            for (int j = 0; j < tmp.size(); j++) {
                if (min > tmp.get(j).sum) {
                    min = tmp.get(j).sum;
                    temp = j;
                }
            }
            Point P = new Point(tmp.get(temp).X, tmp.get(temp).Y, tmp.get(temp).sum);
            Log.d("asd", tmp.get(temp).X + " " + tmp.get(temp).Y + " " + tmp.get(temp).sum);
            nearP.add(P);
            tmp.remove(temp);
        }

        for (int i = 0; i < nearP.size(); i++) {//總權重
            sum = sum + 1 / (nearP.get(i).Weight * nearP.get(i).Weight);
        }
        Log.d("asd", sum + "");

        for (int i = 0; i < nearP.size(); i++) {
            Log.d("PP", nearP.get(i).X + " " + nearP.get(i).Y);//距離定位點最近的四點
            Log.d("PP", (1 / (nearP.get(i).Weight * nearP.get(i).Weight)) / sum + "");
            X = X + nearP.get(i).X * ((1 / (nearP.get(i).Weight * nearP.get(i).Weight)) / sum);//每點權重分量加權
            Y = Y + nearP.get(i).Y * ((1 / (nearP.get(i).Weight * nearP.get(i).Weight)) / sum);
        }

        DecimalFormat df = new DecimalFormat("#.##");//小數點後兩位
        X = Double.parseDouble(df.format(X));
        Y = Double.parseDouble(df.format(Y));
    }
}

