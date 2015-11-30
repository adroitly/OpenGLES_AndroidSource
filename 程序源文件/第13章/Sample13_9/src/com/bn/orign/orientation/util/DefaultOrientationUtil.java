package com.bn.orign.orientation.util;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;

public class DefaultOrientationUtil 
{
	public static DefaultOrientation defaultOrientation;
	
	public static void calDefaultOrientation(Activity activity)
	{
//	    DisplayMetrics dm = new DisplayMetrics();
//	    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		
		Display display;         
	    display = activity.getWindowManager().getDefaultDisplay();
	    int rotation = display.getRotation();//读取当前姿态相对于原始姿态的旋转角度
	    int widthOrign=0;//屏幕分辨率
	    int heightOrign=0;//屏幕分辨率
	    DisplayMetrics dm = new DisplayMetrics();//DisplayMetrics对象
	    display.getMetrics(dm);//宽高维度存放于DisplayMetrics对象中
	    switch (rotation) 
	    {
	    //当前姿态相对于原始姿态旋转了0度或180度
		    case Surface.ROTATION_0:
		    case Surface.ROTATION_180:
		    	widthOrign=dm.widthPixels;
		    	heightOrign=dm.heightPixels;
		    break;
		    //当前姿态相对于原始姿态旋转了90度或270度
		    case Surface.ROTATION_90:       
		    case Surface.ROTATION_270:
		    	widthOrign=dm.heightPixels;
		    	heightOrign=dm.widthPixels;
		    break;
	    }
	    
	    if(widthOrign>heightOrign)
	    {
	    	defaultOrientation=DefaultOrientation.LANDSCAPE;
	    }
	    else
	    {
	    	defaultOrientation=DefaultOrientation.PORTRAIT;
	    }
	    
	    System.out.println((defaultOrientation==DefaultOrientation.LANDSCAPE)?"heng":"shu");
	}
}
