package com.bn.st.d2;			//���������

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import static com.bn.st.xc.Constant.*;

import com.bn.R;

public class MenuView extends MySFView
{
	MyActivity activity;		//Activity����
	Canvas c;					//����������	
	SurfaceHolder holder;		//SurfaceView��������
    Bitmap background;			//����ͼ
    
    Bitmap button_play;			//������Ϸ��ťͼƬ
    Bitmap button_chooseboat;	//ѡ����Ϸ��ťͼƬ
    Bitmap button_soundset;		//��Ч���ð�ťͼƬ
    Bitmap button_help;			//��Ϸ������ťͼƬ
    Bitmap button_about;		//���ڰ�ťͼƬ
    Bitmap button_exit;			//�˳���Ϸ��ťͼƬ
    
    Bitmap button_play_press;			//������Ϸ��ťͼƬ
    Bitmap button_chooseboat_press;	//ѡ����Ϸ��ťͼƬ
    Bitmap button_soundset_press;		//��Ч���ð�ťͼƬ
    Bitmap button_help_press;			//��Ϸ������ťͼƬ
    Bitmap button_about_press;		//���ڰ�ťͼƬ
    Bitmap button_exit_press;			//�˳���Ϸ��ťͼƬ
    
    private float button_play_x;//��ťͼƬ�����Ͻ�X����
    private float button_play_y;//��ťͼƬ�����Ͻ�X����
    private float button_chooseboat_x;//��ťͼƬ�����Ͻ�Y����
    private float button_chooseboat_y;//��ťͼƬ�����Ͻ�Y����
    private float button_soundset_x;//��ťͼƬ�����Ͻ�Y����
    private float button_soundset_y;//��ťͼƬ�����Ͻ�Y����
    private float button_help_x;//��ťͼƬ�����Ͻ�X����
    private float button_help_y;//��ťͼƬ�����Ͻ�X����
    private float button_about_x;//��ťͼƬ�����Ͻ�Y����
    private float button_about_y;//��ťͼƬ�����Ͻ�Y����
    private float button_exit_x;//��ťͼƬ�����Ͻ�Y����
    private float button_exit_y;//��ťͼƬ�����Ͻ�Y����
    
    boolean play_flag=false;
    boolean chooseboat_flag=false;
    boolean soundset_flag=false;
    boolean help_flag=false;
    boolean about_flag=false;
    boolean exit_flag=false;
    
    private boolean flag_go=true;
    int move_flag=1;		//0---���ƶ�   -1---�������ƶ�    1---���м��ƶ�
    float move_span=MOVE_V;//��ť�ƶ��ٶ�
    int curr_menuId=0;//�Զ���Ĳ˵���ť���
	public MenuView(MyActivity activity)  
	{		
		this.activity = activity;//��ʼ��activity������
		initBitmap();			//��ʼ��ͼƬ
		if(Build.VERSION.SDK_INT<Build.VERSION_CODES.FROYO)
		{
			activity.showDialog(2);
		}
		else if(activity.getGLVersion()<2)   
		{
			activity.showDialog(1);
		}
	}
	
	public void initThread()
	{
		move_flag=1;
		button_play_x=-button_play.getWidth();
		button_play_y=190*ratio_height;
		button_chooseboat_x=SCREEN_WIDTH;
		button_chooseboat_y=button_play_y;
		
		button_soundset_x=button_play_x;
		button_soundset_y=275*ratio_height;
		button_help_x=button_chooseboat_x;
		button_help_y=button_soundset_y;
		
		button_about_x=button_play_x;
		button_about_y=360*ratio_height;
		button_exit_x=button_chooseboat_x;
		button_exit_y=button_about_y;
		flag_go=true;
		new Thread()
		{
			{
				this.setName("menuview thread");
			}
			@Override  
			public void run()
			{  
				while(flag_go)
				{
					if(move_flag==1)//�ƶ���־λΪ��
					{
						button_play_x=button_play_x+move_span*ratio_width;
						button_chooseboat_x=button_chooseboat_x-move_span*ratio_width;
						button_soundset_x=button_play_x;
						button_help_x=button_chooseboat_x;
						button_about_x=button_play_x;
						button_exit_x=button_chooseboat_x;
						if(button_play_x>=200*ratio_width)
						{
							button_play_x=200*ratio_width;
							button_chooseboat_x=485*ratio_width;
							button_soundset_x=button_play_x;
							button_help_x=button_chooseboat_x;
							button_about_x=button_play_x;
							button_exit_x=button_chooseboat_x;
							move_flag=0;
						}
					}
					else if(move_flag==-1)
					{
						button_play_x=button_play_x-move_span*ratio_width;
						button_chooseboat_x=button_chooseboat_x+move_span*ratio_width;
						button_soundset_x=button_play_x;
						button_help_x=button_chooseboat_x;
						button_about_x=button_play_x;
						button_exit_x=button_chooseboat_x;
						if(button_play_x<=-button_play.getWidth())
						{
							button_play_x=-button_play.getWidth();
							button_chooseboat_x=SCREEN_WIDTH;
							button_soundset_x=button_play_x;
							button_help_x=button_chooseboat_x;
							button_about_x=button_play_x;
							button_exit_x=button_chooseboat_x;
							move_flag=0;
							flag_go=false;
							MenuView.this.activity.hd.sendEmptyMessage(curr_menuId);
						}
					}
					
					try
					{
						Thread.sleep(MOVE_TIME);//����200����
					}
					catch(InterruptedException e)
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
		
		button_play = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.play),ratio_width,ratio_height);//������Ϸ��ť
		button_chooseboat = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.chooseboat),ratio_width,ratio_height);//ѡ����ť
		button_soundset = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.soundset),ratio_width,ratio_height);//��Ч���ð�ť
		button_help = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.gamehelp),ratio_width,ratio_height);//��Ϸ������ť
		button_about = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.about),ratio_width,ratio_height);//��Ϸ������ť
		button_exit = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.exit),ratio_width,ratio_height);//���ð�ť
		
		button_play_press = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.play_press ),ratio_width,ratio_height);//������Ϸ��ť
		button_chooseboat_press  = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.chooseboat_press ),ratio_width,ratio_height);//ѡ����ť
		button_soundset_press  = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.soundset_press ),ratio_width,ratio_height);//��Ч���ð�ť
		button_help_press  = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.gamehelp_press ),ratio_width,ratio_height);//��Ϸ������ť
		button_about_press  = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.about_press ),ratio_width,ratio_height);//��Ϸ������ť
		button_exit_press  = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.exit_press ),ratio_width,ratio_height);//���ð�ť
	}
	@Override
	public void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		canvas.drawColor(Color.argb(255, 0, 0, 0));//����Ϊ��ɫ
		canvas.drawBitmap(background,0,0, null);//������
		
		if(!play_flag)
		{
			canvas.drawBitmap(button_play, button_play_x, button_play_y, null);
		}else
		{
			canvas.drawBitmap(button_play_press, button_play_x, button_play_y, null);
		}
		if(!chooseboat_flag)
		{
			canvas.drawBitmap(button_chooseboat, button_chooseboat_x, button_chooseboat_y, null);
		}else
		{
			canvas.drawBitmap(button_chooseboat_press, button_chooseboat_x, button_chooseboat_y, null);
		}
		if(!soundset_flag)
		{
			canvas.drawBitmap(button_soundset, button_soundset_x, button_soundset_y, null);
		}else
		{
			canvas.drawBitmap(button_soundset_press, button_soundset_x, button_soundset_y, null);
		}
		if(!help_flag)
		{
			canvas.drawBitmap(button_help, button_help_x, button_help_y, null);
		}else
		{
			canvas.drawBitmap(button_help_press, button_help_x, button_help_y, null);
		}
		if(!about_flag)
		{
			canvas.drawBitmap(button_about, button_about_x, button_about_y, null);
		}else
		{
			canvas.drawBitmap(button_about_press, button_about_x, button_about_y, null);
		}
		if(!exit_flag)
		{
			canvas.drawBitmap(button_exit, button_exit_x, button_exit_y, null);
		}
		else
		{
			canvas.drawBitmap(button_exit_press, button_exit_x, button_exit_y, null);
		}
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int x = (int) event.getX();//��ȡ���ص��X����
		int y = (int) event.getY();//��ȡ���ص��Y����
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN://�����¼������»�ͼ
			if(move_flag==0&&x>button_play_x&&x<button_play_x+button_play.getWidth()&&y>button_play_y&&y<button_play_y+button_play.getHeight())
			{//������Ϸ
				play_flag=true;
				
			}else if(move_flag==0&&x>button_chooseboat_x&&x<button_chooseboat_x+button_chooseboat.getWidth()&&y>button_chooseboat_y&&y<button_chooseboat_y+button_chooseboat.getHeight())
			{//ѡ��
				chooseboat_flag=true;
			}else if(move_flag==0&&x>button_soundset_x&&x<button_soundset_x+button_soundset.getWidth()&&y>button_soundset_y&&y<button_soundset_y+button_soundset.getHeight())
			{//��Ч����
				soundset_flag=true;
			}else if(move_flag==0&&x>button_help_x&&x<button_help_x+button_help.getWidth()&&y>button_help_y&&y<button_help_y+button_help.getHeight())
			{//��Ϸ����
				help_flag=true;
			}else if(move_flag==0&&x>button_about_x&&x<button_about_x+button_about.getWidth()&&y>button_about_y&&y<button_about_y+button_about.getHeight())
			{//����
				 about_flag=true;
			}else if(move_flag==0&&x>button_exit_x&&x<button_exit_x+button_exit.getWidth()&&y>button_exit_y&&y<button_exit_y+button_exit.getHeight())
			{//�˳�
				exit_flag=true;
			}
			break;
		case MotionEvent.ACTION_UP://̧���¼�
			play_flag=false;
			chooseboat_flag=false;
			soundset_flag=false;
			help_flag=false;
			about_flag=false;
			exit_flag=false;
			if(move_flag==0&&x>button_play_x&&x<button_play_x+button_play.getWidth()&&y>button_play_y&&y<button_play_y+button_play.getHeight())
			{//������Ϸ
				
				curr_menuId=2;
				move_flag=-1;  
			}else if(move_flag==0&&x>button_chooseboat_x&&x<button_chooseboat_x+button_chooseboat.getWidth()&&y>button_chooseboat_y&&y<button_chooseboat_y+button_chooseboat.getHeight())
			{//ѡ��
				
				curr_menuId=3;
				move_flag=-1; 
			}else if(move_flag==0&&x>button_soundset_x&&x<button_soundset_x+button_soundset.getWidth()&&y>button_soundset_y&&y<button_soundset_y+button_soundset.getHeight())
			{//��Ч����
				
				curr_menuId=4;
				move_flag=-1; 
			}else if(move_flag==0&&x>button_help_x&&x<button_help_x+button_help.getWidth()&&y>button_help_y&&y<button_help_y+button_help.getHeight())
			{//��Ϸ����
				
				curr_menuId=5;
				move_flag=-1; 
			}else if(move_flag==0&&x>button_about_x&&x<button_about_x+button_about.getWidth()&&y>button_about_y&&y<button_about_y+button_about.getHeight())
			{//����
				   
				curr_menuId=6;
				move_flag=-1; 
			}else if(move_flag==0&&x>button_exit_x&&x<button_exit_x+button_exit.getWidth()&&y>button_exit_y&&y<button_exit_y+button_exit.getHeight())
			{//�˳�
				Settings.System.putInt(activity.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION,activity.flag);
				System.exit(0);
			}
			break;
		}
		return true;
	}
}