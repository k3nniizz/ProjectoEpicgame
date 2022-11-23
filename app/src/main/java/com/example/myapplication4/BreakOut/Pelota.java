package com.example.myapplication4.BreakOut;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Random;


public class Pelota extends View {

    int x;
    int y;
    int dirx;
    int diry;
    boolean start;

    public Pelota(Context context) {
        super(context);
        start = true;
    }

    @Override
    protected void onDraw(Canvas canvas){

        int width =canvas.getWidth();
        int height = canvas.getHeight();

        int margen = 20;
        int Radio = 50;

        Random rn = new Random();

        if(start== true){

            x = margen+Radio+(rn.nextInt(width-margen-Radio));
            y = margen+Radio+(rn.nextInt(height-300-Radio));
            dirx = 8;
            diry = 8;

            if(rn.nextInt(1)==0){
                dirx =-8;

            }
            if(rn.nextInt(1)==0){
                diry =-8;

            }
            start = false;

        }else{
            if((x+dirx-Radio)< margen || (x+dirx+Radio)> width-margen){
                dirx = dirx*-1;

            }
            if((y+diry-Radio)< margen || (y+diry+Radio)> height-margen-300){
                diry = diry*-1;

            }

            x = x+dirx;
            y = y+diry;


        }

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        paint.setColor(Color.parseColor("#03CAFC"));
        canvas.drawRect(margen,margen,width-margen,height-300,paint);
        paint.setColor(Color.parseColor("#CD5C5C"));
        canvas.drawCircle(x,y,Radio,paint);





    }
}
