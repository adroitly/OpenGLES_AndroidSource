package com.bn.st.d2;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import static com.bn.st.xc.Constant.*;

import com.bn.R;

public class HelpSurfaceView extends MySFView
{
	MyActivity activity;//Activity������ 
	Canvas c;//���ʵ�����
	Paint paint;//���ʵ�����
	SurfaceHolder holder;//��������
	Bitmap[] bg_bitmap;					//����ͼ
	Bitmap pre_page;					//��һҳ��ťͼƬ
	Bitmap next_page;					//��һҳ��ťͼƬ
	Bitmap back;
	
	Bitmap pre_page_press;				//��һҳ��ťͼƬ
	Bitmap next_page_press;				//��һҳ��ťͼƬ
	Bitmap back_press;
	
	//���������е���˸��ʾ
	Bitmap[] mark_bitmap;
	//ÿ����˸��ʾͼƬ��xy����
	private final float[][] mark_xy=
	{
			{246,308},
			{44,170},
			{15,115},
			{50,160},
			{184,242}
	};
	int fullTime=0;//��¼�ӿ�ʼ����ǰ��ʱ��
	long startTime;//��ʼʱ��
	
	private float button_pre_x=20f*ratio_width;//backͼƬ��ť�����Ͻǵĵ������
	private float button_pre_y=415f*ratio_height;
	private float button_next_x=710f*ratio_width;//nextͼƬ��ť�����Ͻǵ������
	private float button_next_y=415f*ratio_height;
	
	private int  next_flag=0;//0��ʾ���ƶ�,-1��ʾ����,1��ʾ����

	private float bg_bitmap_curr_x=0;//��ǰ����ͼƬ�����Ͻǵ��X����
	private float bg_bitmap_curr_y=0;//��ǰ����ͼƬ�����Ͻǵ��Y����
	private float bg_bitmap_next_x;//��һ������ͼƬ�����Ͻǵ��X����
	private float bg_bitmap_next_y=0;//��һ������ͼƬ�����Ͻǵ��Y����
	private float move_span=80;//ͼƬ�ƶ����ٶ�
	int page_index=0;//��ǰ����ҳ�������ֵ
	public boolean flag_go=true;
	
	boolean back_flag=false;
	boolean pre_page_flag=false;
	boolean next_page_flag=false;
	
	boolean isHaveNextFlag=true;
	boolean isHavePreFlag=false;
	
	public HelpSurfaceView(MyActivity activity)
	{
		this.activity = activity;
		paint = new Paint();	 //��������
		bg_bitmap=new Bitmap[5];//�����������汳��ͼƬ�������
		mark_bitmap=new Bitmap[5];//�������������б�ע����ͼƬ�������
		paint.setAntiAlias(true);//�򿪿����
		initBitmap();			//��ʼ���õ���ͼƬ��Դ
		startTime=System.currentTimeMillis();
	}
	
	public void initThread()
	{
		next_flag=0;//0��ʾ���ƶ�,-1��ʾ����,1��ʾ����

		bg_bitmap_curr_x=0;//��ǰ����ͼƬ�����Ͻǵ��X����
		bg_bitmap_curr_y=0;//��ǰ����ͼƬ�����Ͻǵ��Y����
		bg_bitmap_next_x=0;//��һ������ͼƬ�����Ͻǵ��X����
		bg_bitmap_next_y=0;//��һ������ͼƬ�����Ͻǵ��Y����
		move_span=80;//ͼƬ�ƶ����ٶ�
		page_index=0;//��ǰ����ҳ�������ֵ
		flag_go=true;
		
		back_flag=false;
		pre_page_flag=false;
		next_page_flag=false;
		
		isHaveNextFlag=true;
		isHavePreFlag=false;		
		
		new Thread()//����һ���̵߳���doDraw����
		{
			@Override
			public void run()
			{
				while(flag_go)
				{
					//�ж������ƻ�������
					if(next_flag==-1)//����
					{
						bg_bitmap_curr_x=bg_bitmap_curr_x-move_span;
						bg_bitmap_next_x=bg_bitmap_next_x-move_span;
						if(bg_bitmap_curr_x<=-SCREEN_WIDTH)
						{
							bg_bitmap_curr_x=-SCREEN_WIDTH;
							next_flag=0;
							page_index++;
							bg_bitmap_curr_x=0;
							bg_bitmap_next_x=SCREEN_WIDTH;
							if(page_index==bg_bitmap.length-1)
							{
								isHaveNextFlag=false;
							}
						}
					}
					if(next_flag==1)//����
					{
						bg_bitmap_curr_x=bg_bitmap_curr_x+move_span;
						bg_bitmap_next_x=bg_bitmap_next_x+move_span;
						if(bg_bitmap_curr_x>=SCREEN_WIDTH)
						{
							bg_bitmap_curr_x=SCREEN_WIDTH;
							page_index--;
							bg_bitmap_curr_x=0;
							bg_bitmap_next_x=-SCREEN_WIDTH;
							if(page_index==0)
							{
								isHavePreFlag=false;
							}
							next_flag=0;
						}
					}
					try
					{  
						Thread.sleep(10);//�߳�����100����
					}
					catch (InterruptedException e)  
					{
					e.printStackTrace();
					}
				}
			}  
		}.start();
	}
	
	//��дonDraw����
	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawColor(Color.argb(255, 0, 0, 0));//����Ϊ��ɫ
		canvas.drawBitmap(bg_bitmap[page_index],bg_bitmap_curr_x,bg_bitmap_curr_y, null);//����ǰ����
		
		//������û�����ƶ���ʱ�򣬻��Ƶ�ǰ�����������˸ͼ����ʾ
		if(next_flag==0)
		{
			//���ư�������������ͼ��
			long currentTime=System.currentTimeMillis();//��¼��ǰʱ��
			fullTime=(int) ((currentTime-startTime));//��¼��ʱ��	
			//��1��ֳ����ݣ���0.7���ڻ��ƣ�0.3���ڲ�����
			if((fullTime/100)%10 < 7) {
				//���Ʒ�ҳָʾͼ��
				System.out.println(ratio_width+"  "+ratio_height);
				canvas.drawBitmap(mark_bitmap[page_index], mark_xy[page_index][0]*ratio_width, mark_xy[page_index][1]*ratio_height, paint);			
			}
		}	
				
		if(next_flag==-1)
		{
			canvas.drawBitmap(bg_bitmap[page_index+1],bg_bitmap_next_x,bg_bitmap_next_y, null);//����һ������
		}
		if(next_flag==1)
		{
			canvas.drawBitmap(bg_bitmap[page_index-1],bg_bitmap_next_x,bg_bitmap_next_y, null);//����һ������
		}
		if(isHaveNextFlag==false)
		{
			if(!back_flag)
			{
				canvas.drawBitmap(back, button_next_x, button_next_y, null);//����back��ť
			}
			else
			{
				canvas.drawBitmap(back_press, button_next_x, button_next_y, null);//����back��ť
			}
		}
		if(page_index>0)//��ǰ��ҳ����������0
		{
			if(!pre_page_flag)
			{
				canvas.drawBitmap(pre_page, button_pre_x, button_pre_y, paint);//������һҳ��ť
			}else
			{
				canvas.drawBitmap(pre_page_press, button_pre_x, button_pre_y, paint);//������һҳ��ť
			}
		}
		if(!isHavePreFlag)
		{
			if(!back_flag)
			{
				canvas.drawBitmap(back, button_pre_x, button_pre_y, null);//����back��ť
			}
			else
			{
				canvas.drawBitmap(back_press, button_pre_x, button_pre_y, null);//����back��ť
			}
		}
		if(page_index<bg_bitmap.length-1)//��ǰҳ������ֵС�ڰ���ͼƬ����-1
		{
			if(!next_page_flag)
			{
				canvas.drawBitmap(next_page, button_next_x, button_next_y, paint);//������һҳ��ť
			}else
			{
				canvas.drawBitmap(next_page_press, button_next_x, button_next_y, paint);//������һҳ��ť
			}
		}
	}
	//��д�����¼�����
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		int x=(int)e.getX();//��ȡ���ص��XY����
		int y=(int)e.getY();
		switch(e.getAction())
		{
		case MotionEvent.ACTION_DOWN://̧���¼�
			if(next_flag==0&&x>button_pre_x&&x<button_pre_x+pre_page.getWidth()&&y>button_pre_y&&y<button_pre_y+pre_page.getHeight())
			{
				if(!isHavePreFlag)
				{
					back_flag=true;
				}
				else
				{
					pre_page_flag=true;
				}
			}
			else if(next_flag==0&&x>button_next_x&&x<button_next_x+pre_page.getWidth()&&y>button_next_y&&y<button_next_y+pre_page.getHeight())
			{
				if(!isHaveNextFlag)
				{
					back_flag=true;
				}
				else
				{
					next_page_flag=true;
				}
				
			}
//			else if(page_index==0&&x>button_next_x&&x<button_next_x+back.getWidth()&&y>button_next_y&&y<button_next_y+back.getHeight())
//			{//���ذ�ť
//    			back_flag=true;
//			}else if(page_index==bg_bitmap.length-1&&x>button_pre_x&&x<button_pre_x+back.getWidth()&&y>button_pre_y&&y<button_pre_y+back.getHeight())
//			{//���ذ�ť
//    			back_flag=true;
//			}
			break;
		case MotionEvent.ACTION_UP://̧���¼� 
			pre_page_flag=false;
			next_page_flag=false;
			back_flag=false;
			if(next_flag==0&&x>button_pre_x&&x<button_pre_x+pre_page.getWidth()&&y>button_pre_y&&y<button_pre_y+pre_page.getHeight())
			{
				if(!isHavePreFlag)
				{
					//���ص����˵�
					flag_go=false;
					activity.hd.sendEmptyMessage(1);
				}
				else
				{
					isHavePreFlag=true;
					isHaveNextFlag=true;
					//����   
					next_flag=1;
					bg_bitmap_next_x=-SCREEN_WIDTH;
				}
			}
			else if(next_flag==0&&x>button_next_x&&x<button_next_x+pre_page.getWidth()&&y>button_next_y&&y<button_next_y+pre_page.getHeight())
			{
				if(!isHaveNextFlag)
				{
					//�������˵�
					flag_go=false;
					activity.hd.sendEmptyMessage(1);
				}
				else
				{
					isHaveNextFlag=true;
					isHavePreFlag=true;
					//����
					next_flag=-1;
					bg_bitmap_next_x=SCREEN_WIDTH;
				}
				
			}
			break;
		}
		return true;
	}
	//��ʼ��ͼƬ�ķ���
	public void initBitmap()
	{
		bg_bitmap[0] = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bg_bitmap0),ratio_width,ratio_height);//�������汳��ͼƬ
		bg_bitmap[1] = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bg_bitmap1),ratio_width,ratio_height);//�������汳��ͼƬ
		bg_bitmap[2] = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bg_bitmap2),ratio_width,ratio_height);//�������汳��ͼƬ
		bg_bitmap[3] = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bg_bitmap3),ratio_width,ratio_height);//�������汳��ͼƬ
		bg_bitmap[4] = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bg_bitmap4),ratio_width,ratio_height);//�������汳��ͼƬ
		mark_bitmap[0] = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mark0),ratio_width,ratio_height);
		mark_bitmap[1] = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mark1),ratio_width,ratio_height);
		mark_bitmap[2] = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mark2),ratio_width,ratio_height);
		mark_bitmap[3] = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mark3),ratio_width,ratio_height);
		mark_bitmap[4] = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mark4),ratio_width,ratio_height);
		pre_page = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.pre_page),ratio_width,ratio_height);//��һҳ��ťͼƬ
		next_page = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.next_page),ratio_width,ratio_height);//��һҳ��ťͼƬ
		back = scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.back),ratio_width,ratio_height);//��һҳ��ťͼƬ
		
		pre_page_press= scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.pre_page_press),ratio_width,ratio_height);//��һҳ��ťͼƬ
		next_page_press= scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.next_page_press),ratio_width,ratio_height);//��һҳ��ťͼƬ
		back_press= scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.back_press),ratio_width,ratio_height);//��һҳ��ťͼƬ
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
