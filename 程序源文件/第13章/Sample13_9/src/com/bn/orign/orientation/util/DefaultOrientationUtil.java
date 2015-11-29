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
	    int rotation = display.getRotation();//��ȡ��ǰ��̬�����ԭʼ��̬����ת�Ƕ�
	    int widthOrign=0;//��Ļ�ֱ���
	    int heightOrign=0;//��Ļ�ֱ���
	    DisplayMetrics dm = new DisplayMetrics();//DisplayMetrics����
	    display.getMetrics(dm);//���ά�ȴ����DisplayMetrics������
	    switch (rotation) 
	    {
	    //��ǰ��̬�����ԭʼ��̬��ת��0�Ȼ�180��
		    case Surface.ROTATION_0:
		    case Surface.ROTATION_180:
		    	widthOrign=dm.widthPixels;
		    	heightOrign=dm.heightPixels;
		    break;
		    //��ǰ��̬�����ԭʼ��̬��ת��90�Ȼ�270��
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
