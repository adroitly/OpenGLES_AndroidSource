package com.bn.st.d2;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import static com.bn.st.xc.Constant.*;
import com.bn.R;

public class SoundSurfaceView extends MySFView
{
	MyActivity activity;
	Canvas c;
	SurfaceHolder holder;
	Bitmap background;				//����ͼ
	Bitmap back;
	Bitmap back_press;
	
	Bitmap button_bgsound;			//��������
	Bitmap button_bgsound_open;		//�������ֿ�ͼƬ��ť
	Bitmap button_bgsound_close;	//�������ֹ�ͼƬ��ť

	Bitmap button_gameeffect;		//��Ϸ��Ч
	Bitmap button_gameeffect_open;	//��Ϸ��Ч����ť
	Bitmap button_gameeffect_close;	//��Ϸ��Ч�ذ�ť
	
	private float button_bgsound_x;//��������ͼƬ�����Ͻ�X����
	private float button_bgsound_y;//��������ͼƬ�����Ͻ�Y����
    private float button_bgsound_open_x;//�������ֿ�ͼƬ�����Ͻ�X����
    private float button_bgsound_open_y;//�������ֹ�ͼƬ�����Ͻ�Y����
    
    private float button_gameeffect_x;//��Ϸ��ЧͼƬ�����Ͻ�X����
    private float button_gameeffect_y;//��Ϸ��ЧͼƬ�����Ͻ�Y����
    private float button_gameeffect_open_x;//��Ϸ��Ч��ͼƬ�����Ͻ�X����
    private float button_gameeffect_open_y;//��Ϸ��Ч��ͼƬ�����Ͻ�Y����
    private float button_back_x=20f*ratio_width;//backͼƬ��ť�����Ͻǵĵ������
	private float button_back_y=415f*ratio_height;
    
    public boolean flag_go=true;
    int move_flag=1;		//0---���ƶ�   -1---�������ƶ�    1---���м��ƶ�
    float move_span=MOVE_V;//��ť�ƶ��ٶ�
    
    boolean back_flag=false;
    
	public SoundSurfaceView(MyActivity activity) 
	{
		this.activity = activity;
		initBitmap();					//��ʼ��ͼƬ
		
		button_bgsound_x=-button_bgsound.getWidth();
		button_bgsound_y=222f*ratio_height;
		button_bgsound_open_x=SCREEN_WIDTH;
		button_bgsound_open_y=button_bgsound_y;
		
		button_gameeffect_x=button_bgsound_x;
		button_gameeffect_y=307f*ratio_height;
		button_gameeffect_open_x=button_bgsound_open_x;
		button_gameeffect_open_y=button_gameeffect_y;
	}
	
	public void initThread()
	{
		flag_go=true;
	    move_flag=1;		//0---���ƶ�   -1---�������ƶ�    1---���м��ƶ�
		
		button_bgsound_x=-button_bgsound.getWidth();
		button_bgsound_y=222f*ratio_height;
		button_bgsound_open_x=SCREEN_WIDTH;
		button_bgsound_open_y=button_bgsound_y;
		
		button_gameeffect_x=button_bgsound_x;
		button_gameeffect_y=307f*ratio_height;
		button_gameeffect_open_x=button_bgsound_open_x;
		button_gameeffect_open_y=button_gameeffect_y;
		
		new Thread()
		{
			@Override
			public void run()
			{  
				while(flag_go)//�ƶ���־λΪ��
				{
					if(move_flag==1)   
					{  
						//����������ťͼƬ�����Ͻǵ��X����
						button_bgsound_x=button_bgsound_x+move_span*ratio_width;
						button_bgsound_open_x=button_bgsound_open_x-move_span*ratio_width;
						button_gameeffect_x=button_bgsound_x;
						button_gameeffect_open_x=button_bgsound_open_x;
						//������ֵ�ﵽ�ٽ�ֵʱ
						if(button_bgsound_x>=200*ratio_width)
						{    
							//�õ���ʱ��X����
							button_bgsound_x=200*ratio_width;
							button_bgsound_open_x=485*ratio_width;
							button_gameeffect_x=button_bgsound_x;
							button_gameeffect_open_x=button_bgsound_open_x; 
							move_flag=0;//���ƶ���־λ��Ϊ0
						}
					}
					else if(move_flag==-1)   
					{
						//����������ťͼƬ�����Ͻǵ��X����             
						button_bgsound_x=button_bgsound_x-move_span*ratio_width;
						button_bgsound_open_x=button_bgsound_open_x+move_span*ratio_width;
						button_gameeffect_x=button_bgsound_x;
						button_gameeffect_open_x=button_bgsound_open_x;
						if(button_bgsound_x<=-button_bgsound.getWidth())
						{
							button_bgsound_x=-button_bgsound.getWidth();
							button_bgsound_open_x=SCREEN_WIDTH;
							button_gameeffect_x=button_bgsound_x;
							button_gameeffect_open_x=button_bgsound_open_x;
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
		back_press = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.back_press),ratio_width,ratio_height);//��һҳ��ťͼƬ
		button_bgsound=scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bg_music),ratio_width,ratio_height);//����ͼƬ
		button_bgsound_open=scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.open),ratio_width,ratio_height);//�������ֿ�ͼƬ
		button_bgsound_close=scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.close),ratio_width,ratio_height);//�������ֹ�ͼƬ
		
		button_gameeffect=scaleToFit(BitmapFactory.decodeResource(activity.getResources(),R.drawable.game_music),ratio_width,ratio_height);//��Ϸ��ЧͼƬ
		button_gameeffect_open=scaleToFit(BitmapFactory.decodeResource(activity.getResources(),R.drawable.open),ratio_width,ratio_height);//��Ϸ��Ч��ͼƬ
		button_gameeffect_close=scaleToFit(BitmapFactory.decodeResource(activity.getResources(),R.drawable.close),ratio_width,ratio_height);//��Ϸ��Ч��ͼƬ
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
		canvas.drawBitmap(button_bgsound, button_bgsound_x, button_bgsound_y, null);//���Ʊ�������ͼƬ
		canvas.drawBitmap(button_gameeffect, button_gameeffect_x, button_gameeffect_y, null);//������Ϸ��ЧͼƬ
		if(com.bn.clp.Constant.BgSoundFlag)//���ݱ������ֵı�־λ������ͼƬ  
		{
			canvas.drawBitmap(button_bgsound_open, button_bgsound_open_x, button_bgsound_open_y, null);
		}
		else if(!com.bn.clp.Constant.BgSoundFlag)//�������ֹر�
		{
			canvas.drawBitmap(button_bgsound_close, button_bgsound_open_x, button_bgsound_open_y, null);
		}
		if(com.bn.clp.Constant.SoundEffectFlag)//��Ϸ��Ч��  
		{
			canvas.drawBitmap(button_gameeffect_open, button_gameeffect_open_x, button_gameeffect_open_y, null);
		}
		else if(!com.bn.clp.Constant.SoundEffectFlag)//��Ϸ�ر�
		{
			canvas.drawBitmap(button_gameeffect_close, button_gameeffect_open_x, button_gameeffect_open_y, null);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		int x = (int) event.getX();
		int y = (int) event.getY();				
    	switch(event.getAction())
    	{
    	case MotionEvent.ACTION_DOWN:
    		if(x>button_bgsound_open_x&&x<button_bgsound_open_x+button_bgsound_open.getWidth()&&
    					y>button_bgsound_open_y&&y<button_bgsound_open_y+button_bgsound_open.getHeight())
			{//�������ֿ���ͼƬ�������ͼ
    			
			}
    		else if(x>button_gameeffect_open_x&&x<button_gameeffect_open_x+button_gameeffect_open.getWidth()&&
    				y>button_gameeffect_open_y&&y<button_gameeffect_open_y+button_gameeffect_open.getHeight())
			{//��Ϸ��Ч����ͼƬ�������ͼ
    			
			}
    		else if(x>button_back_x&&x<button_back_x+back.getWidth()&&y>button_back_y&&y<button_back_y+back.getHeight())
			{//���ذ�ť
    			back_flag=true;
			}
    		break;
    	case MotionEvent.ACTION_UP:
    		back_flag=false;
    		if(x>button_bgsound_open_x&&x<button_bgsound_open_x+button_bgsound_open.getWidth()&&
    					y>button_bgsound_open_y&&y<button_bgsound_open_y+button_bgsound_open.getHeight())
			{
    	        //��SharedPreferences��д�ر����޸���Ϣ
    	        SharedPreferences.Editor editor=activity.sp.edit();
    	        editor.putBoolean("bgSoundFlag", !com.bn.clp.Constant.BgSoundFlag);
    	        editor.commit();
    	        
    			com.bn.clp.Constant.BgSoundFlag=!com.bn.clp.Constant.BgSoundFlag;
    			if(com.bn.clp.Constant.BgSoundFlag)
    			{
    				//Toast.makeText(activity, "�������ֿ�", Toast.LENGTH_SHORT).show();
    			}
    			else
    			{
    				//Toast.makeText(activity, "�������ֹ�", Toast.LENGTH_SHORT).show();
    			}
			}
    		else if(x>button_gameeffect_open_x&&x<button_gameeffect_open_x+button_gameeffect_open.getWidth()&&
    				y>button_gameeffect_open_y&&y<button_gameeffect_open_y+button_gameeffect_open.getHeight())
			{
    			//��SharedPreferences��д�ر����޸���Ϣ
    	        SharedPreferences.Editor editor=activity.sp.edit();
    	        editor.putBoolean("soundEffectFlag", !com.bn.clp.Constant.SoundEffectFlag);
    	        editor.commit();
    	        
    			com.bn.clp.Constant.SoundEffectFlag=!com.bn.clp.Constant.SoundEffectFlag;
    			if(com.bn.clp.Constant.SoundEffectFlag)
    			{
    				//Toast.makeText(activity, "��Ϸ��Ч��", Toast.LENGTH_SHORT).show();
    			}  
    			else
    			{
    				//Toast.makeText(activity, "��Ϸ��Ч��", Toast.LENGTH_SHORT).show();
    			}
			}
    		else if(x>button_back_x&&x<button_back_x+back.getWidth()&&y>button_back_y&&y<button_back_y+back.getHeight())
			{//���ذ�ť
    			move_flag=-1;
			}
    		break;
    	}
		return true;
	}


	//����ͼƬ�ķ���
	public static Bitmap scaleToFit(Bitmap bm,float width_Ratio,float height_Ratio)
	{		
    	int width = bm.getWidth(); 						//ͼƬ���
    	int height = bm.getHeight();					//ͼƬ�߶�
    	Matrix matrix = new Matrix(); 
    	matrix.postScale((float)width_Ratio, (float)height_Ratio);//ͼƬ�ȱ�����СΪԭ����fblRatio��
    	Bitmap bmResult = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);//����λͼ        	
    	return bmResult;								//���ر����ŵ�ͼƬ
    }
}
