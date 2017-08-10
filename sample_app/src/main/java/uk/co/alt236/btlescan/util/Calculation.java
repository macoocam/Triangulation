package uk.co.alt236.btlescan.util;

/**
 * Created by Lambert on 2016/8/12.
 */
import java.util.ArrayList;



public class Calculation {

    public double X = 0, Y = 0, sum;

    class Point {
        double X, Y;

        public Point(double x, double y) {
            X = x;
            Y = y;
        }
    }

    public void CustomSubspceFingerPrint(double[] calbeacon) {
        // TODO Auto-generated method stub
        ArrayList<Calculation> tmp = new ArrayList<Calculation>();
        Calculation c;
        DataBase db = new DataBase();
        double[][] testlist = db.getArray();
        for (int i = 0; i < testlist.length; i++) {//和資料的點計算間距
            c = new Calculation();
            for (int j = 0; j < calbeacon.length; j++) {
                c.sum += Math.sqrt(Math.pow(testlist[i][j] - calbeacon[j], 2));
                c.X = i / 5;
                c.Y = i % 5;
            }
            tmp.add(c);
            c = null;
        }

//        for (int j = 0; j < tmp.size(); j++) {//從最短距離計算最接近的點
//            if (min > tmp.get(j).sum) {
//                min = tmp.get(j).sum;
//                temp = j;
//            }
//        }
//
//        X = temp / 5;//轉換成座標
//        Y = temp % 5;
//        Log.v("ptest", "FingerPrint(" + X + ", " + Y + ")");


        ArrayList<Point> nearP = new ArrayList<Point>();//離定位點的最近四點4NN
        for (int i = 0; i < 4; i++) {
            double min = 100;//初始化
            int temp = 0;
            for (int j = 0; j < tmp.size(); j++) {
                if (min > tmp.get(j).sum) {
                    min = tmp.get(j).sum;
                    temp = j;
                }
            }
            Point P = new Point(tmp.get(temp).X, tmp.get(temp).Y);
            nearP.add(P);
            tmp.remove(temp);
        }

        for (int i = 0; i < nearP.size(); i++) {
            X = X + nearP.get(i).X;
            Y = Y + nearP.get(i).Y;
        }
        X = X / nearP.size();
        Y = Y / nearP.size();
    }
}

