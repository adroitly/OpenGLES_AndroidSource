package com.bn.st.d2;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import static com.bn.st.xc.Constant.*;

import com.bn.R;

public class GameModeView extends MySFView
{
	MyActivity activity;		//Activity����
	Canvas c;					//����������	
	SurfaceHolder holder;		//SurfaceView��������
    Bitmap background;			//����ͼ
	Bitmap back;				//���ذ�ťͼƬ
    
    Bitmap timer_mode;			//��ʱģʽͼƬ
    Bitmap speed_mode;			//����ģʽͼƬ
    Bitmap record_select;   	//��¼��ѯͼƬ
    
    Bitmap timer_mode_press;	//��ʱģʽ����ͼƬ
    Bitmap speed_mode_press;	//����ģʽ����ͼƬ
    Bitmap record_select_press;	//��¼��ѯ����ͼƬ
    Bitmap back_press;
    
    private float timer_mode_x;//��ťͼƬ�����Ͻ�X����
    private float timer_mode_y;//��ťͼƬ�����Ͻ�X����
    private float speed_mode_x;//��ťͼƬ�����Ͻ�Y����
    private float speed_mode_y;//��ťͼƬ�����Ͻ�Y����
    private float record_select_x;//��ťͼƬ�����Ͻ�Y����
    private float record_select_y;//��ťͼƬ�����Ͻ�Y���� 
    private float button_back_x=20f*ratio_width;//backͼƬ��ť�����Ͻǵĵ������
	private float button_back_y=415f*ratio_height;
	
	boolean time_flag=false;
	boolean speed_flag=false;
	boolean record_flag=false;
	boolean back_flag=false;
    
    public boolean flag_go=true;
    int move_flag=1;		//0---���ƶ�   -1---�������ƶ�    1---���м��ƶ�
    float move_span=MOVE_V;//��ť�ƶ��ٶ�

	public GameModeView(Context context) 
	{
		this.activity = (MyActivity) context;//��ʼ��activity������
		initBitmap();			//��ʼ��ͼƬ
	}
	
	public void initThread()
	{
		time_flag=false;
		speed_flag=false;
		record_flag=false;
		back_flag=false;	    
	    flag_go=true;
	    move_flag=1;		//0---���ƶ�   -1---�������ƶ�    1---���м��ƶ�
	    
	    timer_mode_x=-timer_mode.getWidth();//��ʱģʽͼƬ�ĳ�ʼ���Ͻ�λ��
		timer_mode_y=190*ratio_height;
		speed_mode_x=SCREEN_WIDTH;			//����ģʽͼƬ�ĳ�ʼ���Ͻ�λ��
		speed_mode_y=275*ratio_height;
		record_select_x=timer_mode_x;		//��¼��ѯͼƬ�ĳ�ʼ���Ͻ�λ��
		record_select_y=360*ratio_height;
		
		
		new Thread()
		{
			@Override
			public void run()
			{    
				while(flag_go)
				{
					if(move_flag==1)
					{
						timer_mode_x=timer_mode_x+move_span*ratio_width;
						speed_mode_x=speed_mode_x-move_span*ratio_width;
						record_select_x=timer_mode_x;
						if(timer_mode_x>=(SCREEN_WIDTH-timer_mode.getWidth())*0.5f)
						{
							timer_mode_x=(SCREEN_WIDTH-timer_mode.getWidth())*0.5f;
							speed_mode_x=timer_mode_x;
							record_select_x=timer_mode_x;
							move_flag=0;
						}
					}
					else if(move_flag==-1)
					{
						timer_mode_x=timer_mode_x-move_span*ratio_width;
						speed_mode_x=speed_mode_x+move_span*ratio_width;
						record_select_x=timer_mode_x;
						if(timer_mode_x<=-timer_mode.getWidth())
						{
							timer_mode_x=-timer_mode.getWidth();
							speed_mode_x=SCREEN_WIDTH;
							record_select_x=timer_mode_x;
							move_flag=0;
							flag_go=false;
							activity.hd.sendEmptyMessage(1);
						}
					}
					try
					{
						Thread.sleep(MOVE_TIME);//����200����
					}catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}				
			}
		}.start();
	}
	   
	//��ͼƬ����
	public void initBitmap()
	{
		background = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.background),ratio_width,ratio_height);//�˵����汳��ͼƬ
		back = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.back),ratio_width,ratio_height);//��һҳ��ťͼƬ
		timer_mode = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.timer_mode),ratio_width,ratio_height);//
		speed_mode = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.speed_mode),ratio_width,ratio_height);//
		record_select = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.record_select),ratio_width,ratio_height);//	
		
		timer_mode_press = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.timer_mode_press),ratio_width,ratio_height);//
		speed_mode_press = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.speed_mode_press),ratio_width,ratio_height);//
		record_select_press = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.record_select_press),ratio_width,ratio_height);//
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
		if(!time_flag)
		{
			canvas.drawBitmap(timer_mode, timer_mode_x, timer_mode_y, null);
		}else
		{
			canvas.drawBitmap(timer_mode_press, timer_mode_x, timer_mode_y, null);
		}
		if(!speed_flag)
		{
			canvas.drawBitmap(speed_mode, speed_mode_x, speed_mode_y, null);
		}else
		{
			canvas.drawBitmap(speed_mode_press, speed_mode_x, speed_mode_y, null);
		}
		if(!record_flag)
		{
			canvas.drawBitmap(record_select, record_select_x, record_select_y, null);
		}else
		{
			canvas.drawBitmap(record_select_press, record_select_x, record_select_y, null);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int x = (int) event.getX();//��ȡ���ص��X����
		int y = (int) event.getY();//��ȡ���ص��Y����
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN://�����¼�
			if(x>timer_mode_x&&x<timer_mode_x+timer_mode.getWidth()&&y>timer_mode_y&&y<timer_mode_y+timer_mode.getHeight())
			{
				time_flag=true;
			}else if(x>speed_mode_x&&x<speed_mode_x+speed_mode.getWidth()&&y>speed_mode_y&&y<speed_mode_y+speed_mode.getHeight())
			{
				speed_flag=true;
			}else if(x>record_select_x&&x<record_select_x+record_select.getWidth()&&y>record_select_y&&y<record_select_y+record_select.getHeight())
			{
				record_flag=true;
			}else if(x>button_back_x&&x<button_back_x+back.getWidth()&&y>button_back_y&&y<button_back_y+back.getHeight())
			{//���ذ�ť
    			back_flag=true;
			}
			break;
		case MotionEvent.ACTION_UP://̧���¼�
			time_flag=false;
			speed_flag=false;
			record_flag=false;
			back_flag=false;  
			if(x>timer_mode_x&&x<timer_mode_x+timer_mode.getWidth()&&y>timer_mode_y&&y<timer_mode_y+timer_mode.getHeight())
			{
				flag_go=false;
				activity.hd.sendEmptyMessage(8);
			}else if(x>speed_mode_x&&x<speed_mode_x+speed_mode.getWidth()&&y>speed_mode_y&&y<speed_mode_y+speed_mode.getHeight())
			{
				flag_go=false;
				activity.hd.sendEmptyMessage(9);
			}else if(x>record_select_x&&x<record_select_x+record_select.getWidth()&&y>record_select_y&&y<record_select_y+record_select.getHeight())
			{
				flag_go=false;
				activity.hd.sendEmptyMessage(10);				
			}else if(x>button_back_x&&x<button_back_x+back.getWidth()&&y>button_back_y&&y<button_back_y+back.getHeight())
			{//���ذ�ť
    			move_flag=-1;
			}
			break;
		}
		return true;
	}
}
