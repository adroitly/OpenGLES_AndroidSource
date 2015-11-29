package com.bn.Sample11_9;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class FontUtil 
{
	static int cIndex=0;
	static final float textSize=40;
	static int R=255;
	static int G=255;
	static int B=255;
	public static Bitmap generateWLT(String[] str,int width,int height)
	{
		Paint paint=new Paint();
		paint.setARGB(255, R, G, B);
		paint.setTextSize(textSize);
		paint.setTypeface(null);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		Bitmap bmTemp=Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvasTemp = new Canvas(bmTemp);
		for(int i=0;i<str.length;i++)
		{
			canvasTemp.drawText(str[i], 0, textSize*i+(i-1)*5, paint);
		}
		return bmTemp;
	}
	static String[] content=
	{
		"�Կ��Ϻ�ӧ���⹳˪ѩ����",
		"�����հ����������ǡ�",
		"ʮ��ɱһ�ˣ�ǧ�ﲻ���С�",
		"���˷���ȥ�������������",
		"�й����������ѽ�ϥǰ�ᡣ",
		"������캥������Ȱ������",
		"������Ȼŵ��������Ϊ�ᡣ",
		"�ۻ����Ⱥ�������������",
		"���Իӽ�鳣��������𾪡�",
		"ǧ���׳ʿ���Ӻմ����ǡ�",
		"���������㣬��������Ӣ��",
		"˭�����x�£�����̫������",
	};
	//�������ķ���
	public static String[] getContent(int length,String[] content)
	{
		String[] result=new String[length+1];
		for(int i=0;i<=length;i++)
		{
			result[i]=content[i];
		}
		return result;
	}
	//������ɫ�ķ���
	public static void updateRGB()
	{
		R=(int)(255*Math.random());
		G=(int)(255*Math.random());
		B=(int)(255*Math.random());
	}
}