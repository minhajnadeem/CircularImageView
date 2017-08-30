package com.example.minhaj.circularimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by minhaj on 18/08/2017.
 */

public class CustomImageView extends android.support.v7.widget.AppCompatImageView {

    private final String TAG = "image";
    private float width,height;

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /*TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomImageView,0,0);
        try {
            width = typedArray.getDimension(R.styleable.CustomImageView_custom_width,100);
            height = typedArray.getDimension(R.styleable.CustomImageView_custom_height,100);
        } finally {
            typedArray.recycle();
        }*/

    }

    @Override
    protected void onDraw(Canvas canvas) {

        Log.d(TAG,"onDraw");
        Drawable drawable = getDrawable();
        Bitmap bitmapDrawable = ((BitmapDrawable) drawable).getBitmap();

       // drawOval(canvas);
        Bitmap bitmap = roundedBitmap(bitmapDrawable,(int) width);
        //Bitmap bitmap = ovalBitmap(bitmapDrawable);
        canvas.drawBitmap(bitmap,0,0,null);
        Path path = new Path();
        RectF rectF = new RectF(0,0,width,height);
        //path.addOval(rectF,Path.Direction.CCW);
        path.addCircle(width/2,height/2,width/2, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    private Bitmap roundedBitmap(Bitmap bitmap,int radius){
        int smallest = Math.min(bitmap.getWidth(),bitmap.getHeight());
        int factor = smallest/radius;
        Bitmap bitmap0 = Bitmap.createScaledBitmap(bitmap,(bitmap.getWidth()/factor),bitmap.getHeight()/factor,false);
        Bitmap bitmap1  = Bitmap.createBitmap(radius,radius, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        final String color = "#BAB399";
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor(color));
        canvas.drawCircle(radius / 2 + 0.7f, radius / 2 + 0.7f,
                radius / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap0, rect, rect, paint);
        return bitmap1;
    }

    private Bitmap ovalBitmap(Bitmap bitmap){
        int smallest = Math.min(bitmap.getWidth(),bitmap.getHeight());
        int factor = smallest/(int) width;
       Bitmap bitmap0 = Bitmap.createScaledBitmap(bitmap,(bitmap.getWidth()/factor),bitmap.getHeight()/factor,false);
        Bitmap bitmap1 = Bitmap.createBitmap((int)width,(int)height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        final String color = "#BAB399";
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(Color.parseColor(color));
        Rect rect = new Rect(0,0,(int) width,(int) height);
        RectF rectF = new RectF(0,0,width,height);
        canvas.drawOval(rectF,paint);
        canvas.drawBitmap(bitmap,rect,rect,paint);
        return bitmap1;
    }

    private void drawOval(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
         RectF rectF = new RectF(0,0,getWidth(),getHeight());
        canvas.drawOval(rectF,paint);
    }
}