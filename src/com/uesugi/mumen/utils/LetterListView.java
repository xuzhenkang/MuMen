package com.uesugi.mumen.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LetterListView extends View{
	
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;  
	  
    private String[] b = { "@", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",  
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",  
            "Y", "Z", "#" };  
    
    private String[] letters = null;
    
  
    int choose = -1;  
  
    private Paint paint = new Paint();  
  
    boolean showBkg = false;  
  
    public LetterListView(Context context, AttributeSet attrs, int defStyle)  
    {  
        super(context, attrs, defStyle);  
 
        letters = b;
    }  
  
    public LetterListView(Context context, AttributeSet attrs)  
    {  
        super(context, attrs);  
        
        letters = b;
  
    }  
  
    public LetterListView(Context context)  
    {  
        super(context);  
        
        letters = b;
    }  
    
    
    public void showLetterOnly(HashMap<String , Integer> map){
    	
    	letters = new String[map.size()];
    	Iterator iter = map.entrySet().iterator();
    	int i = 0;
    	while(iter.hasNext()){
    		Map.Entry entry = (Map.Entry) iter.next();
    		letters[i] = (String) entry.getKey();
    		i++;
    	}
    	
    	this.invalidate();
    }
  
    protected void onDraw(Canvas canvas)  
    {  
        super.onDraw(canvas);  
        if (showBkg)  
        {  
            canvas.drawColor(Color.parseColor("#40000000"));  
        }  
  
        int height = getHeight();  
        int width = getWidth();  
        int singleHeight = height / letters.length;  
        for (int i = 0; i < letters.length; i++)  
        {  
            paint.setTextSize(18f);  
            paint.setColor(Color.BLACK);  
            paint.setTypeface(Typeface.DEFAULT_BOLD);  
            paint.setAntiAlias(true);  
            if (i == choose)  
            {  
                paint.setColor(Color.parseColor("#3399ff"));  
                paint.setFakeBoldText(true);  
            }  
            float xPos = width / 2 - paint.measureText(letters[i]) / 2;  
            float yPos = singleHeight * i + singleHeight;  
  
            canvas.drawText(letters[i], xPos, yPos, paint);  
  
            paint.reset();  
        }  
  
    }  
  
    @Override  
    public boolean dispatchTouchEvent(MotionEvent event)  
    {  
        final int action = event.getAction();  
        final float y = event.getY();  
        final float x = event.getX();  
        final int oldChoose = choose;  
        final OnTouchingLetterChangedListener listener =  
                onTouchingLetterChangedListener;  
        final int c = (int) (y / getHeight() * letters.length);  
  
        switch (action)  
        {  
        case MotionEvent.ACTION_DOWN:  
            showBkg = true;  
            if (oldChoose != c && listener != null)  
            {  
                if (c > 0 && c < letters.length)  
                {  
                    listener.onTouchingLetterChanged(letters[c], y, x);  
                    choose = c;  
                    invalidate();  
                }  
            }  
  
            break;  
        case MotionEvent.ACTION_MOVE:  
            if (oldChoose != c && listener != null)  
            {  
                if (c > 0 && c < letters.length)  
                {  
                    listener.onTouchingLetterChanged(letters[c], y, x);  
                    choose = c;  
                    invalidate();  
                }  
            }  
            break;  
        case MotionEvent.ACTION_UP:  
            showBkg = false;  
            choose = -1;  
            listener.onTouchingLetterEnd();  
            invalidate();  
            break;  
        }  
        return true;  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent event)  
    {  
        return super.onTouchEvent(event);  
    }  
  
    public void setOnTouchingLetterChangedListener(  
            OnTouchingLetterChangedListener onTouchingLetterChangedListener)  
    {  
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;  
    }  
  
    public interface OnTouchingLetterChangedListener  
    {  
  
        public void onTouchingLetterEnd();  
  
        public void onTouchingLetterChanged(String s, float y, float x);  
    }  

}
