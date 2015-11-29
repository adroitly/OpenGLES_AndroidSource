package com.bn.st.xc;

import com.bn.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Constant 
{
	//��������ѡ����������һ����һ����������Ӧ�ĳ���  0��Ϊ480x800 1��Ϊ480x854 2��Ϊ540x960 3��Ϊ320x480
	public static final float[][] XC_Self_Adapter_Data_TRASLATE=
	{//��һ��  ��һ��  ��ť�߶ȵ�һ��
		{0.21f},
		{0.22f},
		{0.22f},
		{0.20f}
	};
	
	//�������Ʋ˵��ƶ������ĳ���
	public static final float MOVE_V=20f;
	public static final long MOVE_TIME=15;
	
	//��Ļ�Ĵ�С
	public static  float SCREEN_WIDTH;
	public static  float SCREEN_HEIGHT;
	//���ű���
    public static float ratio_width;
    public static float ratio_height;
	//--------�趨����ĳ����
	public static final float HOUSE_CHANG=50;
	public static final float HOUSE_KUAN=50;
	public static final float HOUSE_GAO=30;
	//-------=�趨������۲���Ŀ���ľ���
	public static final float XC_DISTANCE=35;
	//���Ҵ����ӵ����ű���
	public static final float RATIO_BOAT=8.0f;
	//չ̨�İ뾶�͸߶�
	public static final float RADIUS_DISPLAY=14;
	public static final float LENGTH_DISPLAY=6;
	//�趨�������ɫ
	public static final float[][] HOUSE_COLOR=new float[][]
	{
		{1f,1f,1f},//��͸������
		{1f,1f,1f},//͸��ǽ
	};
	//�趨չ̨Բ����ɫ
	public static final float[] COLOR_CYLINDER=new float[]{0.9f,0.9f,0.9f,1.0f};
	//�趨չ̨Բ����ɫ
	public static final float[] COLOR_CIRCLE=new float[]{0.9f,0.9f,0.9f,0.5f};
	//Χǽ�Ŀ��   ����Ŀ�߾�Ϊ  ʵ�ʵ�һ��
	public static final float WALL_WIDHT=20f;
	public static final float WALL_HEIGHT=18f;
	//��������
	public static Bitmap scaleToFit(Bitmap bm,float width_Ratio,float height_Ratio)
	{		
    	int width = bm.getWidth(); 							//ͼƬ���
    	int height = bm.getHeight();							//ͼƬ�߶�
    	Matrix matrix = new Matrix(); 
    	matrix.postScale((float)width_Ratio, (float)height_Ratio);				//ͼƬ�ȱ�����СΪԭ����fblRatio��
    	Bitmap bmResult = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);//����λͼ        	
    	return bmResult;									//���ر����ŵ�ͼƬ
    }
	
	//����ӵ�=================================================================================
		
	//��Ҫ��ͼƬ��id
	public static int[] picId=new int[]
    {
		R.drawable.background,		//�󱳾�
		R.drawable.tmode,	//��ʱģʽ����
		R.drawable.rmode,	//����ģʽ����
		R.drawable.hengxian,	//����
		R.drawable.maohao,	//ð��
    };
	//����ͼƬ
	public static int[] picNum=new int[]
    {
		R.drawable.d0,R.drawable.d1,
		R.drawable.d2,R.drawable.d3,
		R.drawable.d4,R.drawable.d5,
		R.drawable.d6,R.drawable.d7, 
		R.drawable.d8,R.drawable.d9,
    };
	public static Bitmap[] recordBitmap;
	public static Bitmap[] recordNum;
	//��ʼ��ͼƬ�ķ���
	public static void initBitmap(Resources res)
	{
		recordBitmap=new Bitmap[picId.length];
		recordNum=new Bitmap[picNum.length];
		for(int i=0;i<picId.length;i++)
		{
			recordBitmap[i]=scaleToFit(BitmapFactory.decodeResource(res, picId[i]),ratio_width,ratio_height);
		}
		for(int i=0;i<picNum.length;i++)
		{
			recordNum[i]=scaleToFit(BitmapFactory.decodeResource(res, picNum[i]),ratio_width,ratio_height);
		}
	}
	public static float[][] picLocation;
	public static float[][] touchLocation;
	//��ʼ��ͼƬ��λ����Ϣ
	public static void initLoaction()
	{
		//ͼƬ��λ����Ϣ
		float[][] tempPicLocation=new float[][]
	    {
			{0,0},//�󱳾�ͼƬλ��
			{(SCREEN_WIDTH-recordBitmap[1].getWidth())/2,150},//��ʱģʽͼƬλ��
	    };
		//�ɴ�������
		float[][] tempTouchLocation=new float[][]
	    {
			{(SCREEN_WIDTH-recordBitmap[1].getWidth())/2,150,(SCREEN_WIDTH)/2,200},//Timing Mode��λ��
			{(SCREEN_WIDTH)/2,150,(SCREEN_WIDTH+recordBitmap[1].getWidth())/2,200},//Racing Mode��λ��
			{(SCREEN_WIDTH-recordBitmap[1].getWidth())/2,230,(SCREEN_WIDTH+recordBitmap[1].getWidth())/2,410},//����ʱ����Ϣ��λ��
	    };
		
		picLocation=new float[tempPicLocation.length][tempPicLocation[0].length];
		touchLocation=new float[tempTouchLocation.length][tempTouchLocation[0].length];
		for(int i=0;i<tempPicLocation.length;i++)
		{ 
			for(int j=0;j<tempPicLocation[0].length;j++)
			{
				if(j%2==1)
				{//�߶�
					picLocation[i][j]=tempPicLocation[i][j]*ratio_height;
				} 
				else if(j%2==0)
				{//���
					picLocation[i][j]=tempPicLocation[i][j];
				}
			} 
		}
		for(int i=0;i<tempTouchLocation.length;i++)
		{
			for(int j=0;j<tempTouchLocation[0].length;j++)
			{
				if(j%2==1)
				{//�߶�
					touchLocation[i][j]=tempTouchLocation[i][j]*ratio_height;
				}
				else if(j%2==0)
				{//���
					touchLocation[i][j]=tempTouchLocation[i][j];
				}
			}
		}
	}
}
