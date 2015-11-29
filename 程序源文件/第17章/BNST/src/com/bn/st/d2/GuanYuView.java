package com.bn.st.d2;

import static com.bn.st.xc.Constant.ratio_height;
import static com.bn.st.xc.Constant.ratio_width;
import static com.bn.st.xc.Constant.scaleToFit;
import com.bn.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class GuanYuView extends MySFView
{
	MyActivity activity;		//Activity����
	Canvas c;					//����������	
	SurfaceHolder holder;		//SurfaceView��������
    Bitmap background;			//����ͼ
	Bitmap back;
	Bitmap back_press;
    private float button_back_x=20f*ratio_width;//backͼƬ��ť�����Ͻǵĵ������
	private float button_back_y=415f*ratio_height;
	
	boolean back_flag=false;
	boolean flag=true;
    
	public GuanYuView(Context context) 
	{
		this.activity = (MyActivity) context;//��ʼ��activity������
		initBitmap();			//��ʼ��ͼƬ
	}
	
	//��ͼƬ����
	public void initBitmap()
	{
		background = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.guanyu),ratio_width,ratio_height);//�˵����汳��ͼƬ
		back = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.back),ratio_width,ratio_height);//��һҳ��ťͼƬ
		back_press = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.back_press),ratio_width,ratio_height);//��һҳ��ťͼƬ
	}
	
	@Override
	public void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		canvas.drawColor(Color.argb(255, 0, 0, 0));//����Ϊ��ɫ
		canvas.drawBitmap(background,0,0, null);//������   
		if(!back_flag)
		{
			canvas.drawBitmap(back, button_back_x, button_back_y, null);//����back��ť 
		}else
		{
			canvas.drawBitmap(back_press, button_back_x, button_back_y, null);//����back��ť 
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		int x=(int)e.getX();
		int y=(int)e.getY();
		switch(e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			if(x>button_back_x&&x<button_back_x+back.getWidth()&&y>button_back_y&&y<button_back_y+back.getHeight())
			{//���ذ�ť
				back_flag=true;
			}  
			break;
		case MotionEvent.ACTION_UP:
			back_flag=false;
			if(x>button_back_x&&x<button_back_x+back.getWidth()&&y>button_back_y&&y<button_back_y+back.getHeight())
			{//���ذ�ť
				activity.hd.sendEmptyMessage(1);
			}  
			break;
		}
		return true;
	}
}
