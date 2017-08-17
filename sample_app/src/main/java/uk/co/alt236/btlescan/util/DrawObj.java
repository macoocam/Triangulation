package uk.co.alt236.btlescan.util;

import java.util.List;

/**
 * Created by Lambert on 2016/8/12.
 */
public class DrawObj {

    public int PointNumber;
    public float startX;
    public float startY;
    public float stopX;
    public float stopY;

    public float X;
    public float Y;
    public float distance;

    public List<DrawObj> WriteBeaconInformation(List<DrawObj> Array){
        DrawObj dp = new DrawObj();

        dp.PointNumber = 1;
        dp.X = 100+50*0;
        dp.Y = 275-50*0;
        Array.add(dp);
        dp = new DrawObj();

        dp.PointNumber = 2;
        dp.X = 100+50*2;
        dp.Y = 275-50*0;
        Array.add(dp);
        dp = new DrawObj();

        dp.PointNumber = 4;
        dp.X = 100+50*4;
        dp.Y = 275-50*0;
        Array.add(dp);
        dp = new DrawObj();

        dp.PointNumber = 5;
        dp.X = 100+50*6;
        dp.Y = 275-50*0;
        Array.add(dp);
        dp = new DrawObj();

        dp.PointNumber = 6;
        dp.X = 100+50*8;
        dp.Y = 275-50*0;
        Array.add(dp);
        dp = new DrawObj();

        dp.PointNumber = 9;
        dp.X = 100+50*0;
        dp.Y = 275-50*2-25;
        Array.add(dp);
        dp = new DrawObj();

        dp.PointNumber = 11;
        dp.X = 100+50*2;
        dp.Y = 275-50*2-25;
        Array.add(dp);
        dp = new DrawObj();

        dp.PointNumber = 12;
        dp.X = 100+50*4;
        dp.Y = 275-50*2-25;
        Array.add(dp);
        dp = new DrawObj();

        dp.PointNumber = 13;
        dp.X = 100+50*6;
        dp.Y = 275-50*2-25;
        Array.add(dp);
        dp = new DrawObj();

        dp.PointNumber = 14;
        dp.X = 100+50*8;
        dp.Y = 275-50*2-25;
        Array.add(dp);
        dp = new DrawObj();

        dp.PointNumber = 15;
        dp.X = 100+50*0;
        dp.Y = 275-50*5;
        Array.add(dp);
        dp = new DrawObj();

        dp.PointNumber = 16;
        dp.X = 100+50*2;
        dp.Y = 275-50*5;
        Array.add(dp);
        dp = new DrawObj();

        dp.PointNumber = 17;
        dp.X = 100+50*4;
        dp.Y = 275-50*5;
        Array.add(dp);
        dp = new DrawObj();

        dp.PointNumber = 18;
        dp.X = 100+50*6;
        dp.Y = 275-50*5;
        Array.add(dp);
        dp = new DrawObj();

        dp.PointNumber = 19;
        dp.X = 100+50*8;
        dp.Y = 275-50*5;
        Array.add(dp);
        dp = new DrawObj();

        return Array;
    }
}
