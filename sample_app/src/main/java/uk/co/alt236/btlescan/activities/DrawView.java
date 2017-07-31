package uk.co.alt236.btlescan.activities;

/**
 * Created by Lambert on 2016/8/12.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;

import uk.co.alt236.btlescan.util.Calculation;
import uk.co.alt236.btlescan.util.DrawObj;

public class DrawView extends View{

    Paint paint, pointpaint, subspacepaint;
    Canvas canvas;
    Bitmap bitmap;
    RectF rect, rect_lt, rect_rt, rect_lb, rect_rb;
    List<DrawObj> LineArray = new ArrayList<DrawObj>();
    List<DrawObj> PointArray = new ArrayList<DrawObj>();
    List<DrawObj> BeaconArray = new ArrayList<DrawObj>();
    ArrayList<Calculation> test = new ArrayList<Calculation>();//test
    DrawObj dp = new DrawObj();
    int c = 0;
    double X,Y;
    Boolean draw = false;

    public DrawView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initBitmap();
    }

    private void initBitmap() {
        initPaint();
        rect = new RectF(100, 25, 500, 275);
        rect_lt = new RectF(100, 25, 300, 150);
        rect_rt = new RectF(300, 25, 500, 150);
        rect_lb = new RectF(100, 150, 300, 275);
        rect_rb = new RectF(300, 150, 500, 275);

        for(int i = 1;i < 9;i++){ //列
            dp = new DrawObj();
            dp.startX = 100+50*i;
            dp.startY = 25;
            dp.stopX = 100+50*i;
            dp.stopY = 275;
            LineArray.add(dp);
        }

        for(int i = 1;i < 6;i++){ //行
            dp = new DrawObj();
            dp.startX = 100;
            dp.startY = 25+50*i;
            dp.stopX = 500;
            dp.stopY = 25+50*i;
            LineArray.add(dp);
        }

        for(int i = 0;i < 6;i++){// 畫點，從左下開始
            for(int j = 0;j < 9;j++){
                dp.PointNumber = c++;
                dp = new DrawObj();
                dp.X = 100+50*j;
                dp.Y = 275-50*i;
                PointArray.add(dp);
            }
        }

        BeaconArray = dp.WriteBeaconInformation(BeaconArray);

    }

    private void initPaint() {
        paint = new Paint();
        pointpaint = new Paint();
        subspacepaint = new Paint();

        canvas = new Canvas();
        paint.setColor(Color.BLUE);
//        paint.setStrokeJoin(Paint.Join.ROUND);
//        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(3);

        pointpaint.setColor(Color.RED);
        pointpaint.setStyle(Style.STROKE);
        pointpaint.setStrokeJoin(Paint.Join.ROUND);
        pointpaint.setStrokeCap(Paint.Cap.ROUND);
        pointpaint.setStrokeWidth(10);

        subspacepaint.setColor(Color.GREEN);
        subspacepaint.setStyle(Style.STROKE);
        subspacepaint.setStrokeJoin(Paint.Join.ROUND);
        subspacepaint.setStrokeCap(Paint.Cap.ROUND);
        subspacepaint.setStrokeWidth(15);
    }

    protected void onDraw(Canvas canvas) {
        for(int i = 0;i < LineArray.size();i++){
            canvas.drawLine(LineArray.get(i).startX, LineArray.get(i).startY, LineArray.get(i).stopX, LineArray.get(i).stopY, paint);
        }
        canvas.drawRect(rect, paint); //0, 0, 600, 300

//        for(int i = 0;i < PointArray.size();i++){
//        	canvas.drawPoint(PointArray.get(i).X, PointArray.get(i).Y, pointpaint);
//		}

        for(int i = 0;i < BeaconArray.size();i++){
            canvas.drawPoint(BeaconArray.get(i).X, BeaconArray.get(i).Y, pointpaint);
//        	canvas.drawCircle(BeaconArray.get(i).X, BeaconArray.get(i).Y, 45, paint);
        }

        if (draw == true) {
            canvas.drawPoint((float) X, (float) Y, subspacepaint);
        }


//        for(int i = 0;i < test.size();i++){//test
//        	canvas.drawPoint((float) test.get(i).X, (float) test.get(i).Y, subspacepaint);
//        }

//        if(MainActivity.subspaceNUM != -1 || MainActivity.subspaceNUM != 0){
//        	if(MainActivity.subspaceNUM == 1){
//        		canvas.drawRect(rect_lb, subspacepaint);
//        	}else if(MainActivity.subspaceNUM == 2){
//        		canvas.drawRect(rect_rb, subspacepaint);
//        	}else if(MainActivity.subspaceNUM == 3){
//        		canvas.drawRect(rect_lt, subspacepaint);
//        	}else{//4
//        		canvas.drawRect(rect_rt, subspacepaint);
//        	}
//        }

        invalidate();
    }

    public void setEpoint(ArrayList<Calculation> t) {//test
        // TODO Auto-generated method stub
        test = t;
    }

}

