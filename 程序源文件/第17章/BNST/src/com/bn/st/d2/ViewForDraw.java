package com.bn.st.d2;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//���˵�View
public class ViewForDraw extends SurfaceView 
implements SurfaceHolder.Callback  //ʵ���������ڻص��ӿ�
{
	MyActivity activity;
	Paint paint;//����	
	boolean flag;
	MySFView curr;	
	
	public ViewForDraw(MyActivity activity) 
	{
		super(activity);
		this.setKeepScreenOn(true);
		this.activity = activity;		
		//�����������ڻص��ӿڵ�ʵ����
		this.getHolder().addCallback(this);
		//��ʼ������
		paint = new Paint();//��������
		paint.setAntiAlias(true);//�򿪿����
	} 
	
	public void initThread()
	{
		flag=true;
		new Thread()
		{
			{
				this.setName("VFD Thread");
			}
			public void run()
			{
				while(flag)
				{
					repaint();
					try 
					{
						Thread.sleep(40);
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public void onDraw(Canvas canvas)
	{		
		if(canvas==null)
		{
			return;
		}
		canvas.clipRect
		(
			new Rect
			(
				0,
				0,
				(int)com.bn.st.xc.Constant.SCREEN_WIDTH,
				(int)com.bn.st.xc.Constant.SCREEN_HEIGHT
			)
		);

		curr.onDraw(canvas);
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) 
	{
		
	}

	public void surfaceCreated(SurfaceHolder holder) {//����ʱ������
		repaint();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {//����ʱ������

	}
	
	public void repaint()
	{
		SurfaceHolder holder=this.getHolder();
		Canvas canvas = holder.lockCanvas();//��ȡ����
		try{
			synchronized(holder){
				onDraw(canvas);//����
			}			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(canvas != null){
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	//��Ļ�����¼�	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		if(curr==null)
		{
			return false;
		}
		
		return curr.onTouchEvent(e);
	}
}