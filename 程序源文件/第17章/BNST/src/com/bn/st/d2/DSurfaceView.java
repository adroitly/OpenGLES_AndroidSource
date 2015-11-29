package com.bn.st.d2;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import static com.bn.st.xc.Constant.*;

public class DSurfaceView extends MySFView
{
	MyActivity activity;
	Paint paint;
	int index=1;//��ʱģʽ�;���ģʽ��ͼƬ����ֵ
	List<String> alist=new ArrayList<String>();//�洢�����ݿ���ȡ�����ݵļ���
	float offset=55*ratio_width;
	float tyoffset=20*ratio_width;
	float clipYOffset=10*ratio_height;
	//ǰһʱ�̴��ص��Yλ��
	float beY;
	//����ƫ��������ֵ
	float uBOffset;
	
	float yOffset=0;
	
	public DSurfaceView(MyActivity activity)
	{
		this.activity=activity;
		//�����������ڻص��ӿڵ�ʵ����
		paint=new Paint();
		paint.setAntiAlias(true);
	}
	
	public void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(recordBitmap[0], picLocation[0][0], picLocation[0][1], paint);   
		canvas.drawBitmap(recordBitmap[index], picLocation[1][0], picLocation[1][1], paint);
		canvas.save();//���浱ǰ����״̬
		canvas.clipRect(touchLocation[2][0], touchLocation[2][1]+clipYOffset, touchLocation[2][2], touchLocation[2][3]-clipYOffset);
		if(index==1)//��ʱģʽ
		{
			for(int i=0;i<alist.size();i++)
			{
				if(i%2==0)
				{
					drawDate
					(
						canvas,
						touchLocation[2][0]+offset,
						touchLocation[2][1]+i*(recordNum[0].getHeight()+tyoffset)/2+tyoffset+yOffset,
						recordNum[0].getWidth(),
						paint,
						alist.get(i),
						recordBitmap[3],
						recordBitmap[4]
				     );
				}
				else if(i%2==1)
				{
					drawTime
					(
						canvas,
						touchLocation[2][0]+20*recordNum[0].getWidth()+offset,
						touchLocation[2][1]+(i-1)*(recordNum[0].getHeight()+tyoffset)/2+tyoffset+yOffset,
						recordNum[0].getWidth(),
						paint,
						alist.get(i),
						recordBitmap[4]
				     );
				}
			}
		}
		else if(index==2)//����ģʽ
		{
			for(int i=0;i<alist.size();i++)
			{
				if(i%3==0)
				{
					drawDate
					(
						canvas,
						touchLocation[2][0]+offset,
						touchLocation[2][1]+i*(recordNum[0].getHeight()+tyoffset)/3+tyoffset+yOffset,
						recordNum[0].getWidth(),
						paint,
						alist.get(i),
						recordBitmap[3],
						recordBitmap[4]
				     );
				}   
				else if(i%3==1)
				{
					drawTime
					(
						canvas,
						touchLocation[2][0]+17*recordNum[0].getWidth()+offset,
						touchLocation[2][1]+(i-1)*(recordNum[0].getHeight()+tyoffset)/3+tyoffset+yOffset,
						recordNum[0].getWidth(),
						paint,
						alist.get(i),
						recordBitmap[4]
				     );
				} 
				else if(i%3==2)
				{
					drawDate
					(
						canvas,
						touchLocation[2][0]+28*recordNum[0].getWidth()+offset,
						touchLocation[2][1]+(i-2)*(recordNum[0].getHeight()+tyoffset)/3+tyoffset+yOffset,
						recordNum[0].getWidth(),
						paint,
						alist.get(i),
						recordBitmap[4],
						recordBitmap[4]
				     );
				}
			}
		}
		canvas.restore();//�ָ�����״̬ 
	}
	
	//�������ں�ʱ��ķ���
	public void drawDate(Canvas canvas,float xoffset,float yoffset,float width,Paint paint,String str,Bitmap bmp0,Bitmap bmp1)
	{
		String[] tempStr=str.split(":");
		for(int i=0;i<tempStr.length;i++)
		{
			//��������
			drawNum
			(
				canvas,
				xoffset+tempStr[i].length()*width*i+bmp0.getWidth()*i,
				yoffset,
				width,
				paint,
				tempStr[i]
			);
			if(i==1)//���ƺ���
			{
				canvas.drawBitmap
				(
					bmp0, 
					xoffset+tempStr[i].length()*width*i+(i-1)*bmp0.getWidth(), 
					yoffset, 
					paint
				);
			}
			if(i>2)
			{
				canvas.drawBitmap
				(
					bmp1, 
					xoffset+tempStr[i].length()*width*i+(i-1)*bmp1.getWidth(), 
					yoffset, 
					paint
				);
			}
		}
	}
	//����ʱ��
	public void drawTime(Canvas canvas,float xoffset,float yoffset,float width,Paint paint,String str,Bitmap bmp)
	{
		String[] tempStr=str.split(":");
		for(int i=0;i<tempStr.length;i++)
		{
			//��������
			drawNum
			(
				canvas,
				xoffset+tempStr[i].length()*width*i+bmp.getWidth()*i,
				yoffset,
				width,
				paint,
				tempStr[i]
			);
			if(i>=1&&i<3)//���ƺ���
			{
				canvas.drawBitmap
				(
					bmp, 
					xoffset+tempStr[i].length()*width*i+(i-1)*bmp.getWidth(), 
					yoffset, 
					paint
				);
			}
		}
	}
	//���������ַ����������ֵķ���
	public void drawNum(Canvas canvas,float xoffset,float yoffset,float width,Paint paint,String str)
	{
		for(int i=0;i<str.length();i++)
		{//ѭ�����Ƶ÷�
    		int tempScore=str.charAt(i)-'0';
    		canvas.drawBitmap(recordNum[tempScore], xoffset+i*width,yoffset, paint);
    	}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		float x=event.getX();
		float y=event.getY();
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				if(x>touchLocation[0][0]&&x<touchLocation[0][2]&&
				   y>touchLocation[0][1]&&y<touchLocation[0][3])//��ʱģʽ
				{
					index=1;
					yOffset=0;
					alist=DBUtil.queryDatabase("jsRecord");
					uBOffset=(alist.size()/(index+1)<=2)?alist.size()/(index+1)*(recordNum[0].getHeight()+tyoffset):3*(recordNum[0].getHeight()+tyoffset);
				}
				else if(x>touchLocation[1][0]&&x<touchLocation[1][2]&&
						y>touchLocation[1][1]&&y<touchLocation[1][3])//����ģʽ
				{
					index=2;
					yOffset=0;
					alist=DBUtil.queryDatabase("jRecord");
					uBOffset=(alist.size()/(index+1)<=2)?alist.size()/(index+1)*(recordNum[0].getHeight()+tyoffset):3*(recordNum[0].getHeight()+tyoffset);
				}
				beY=y;
			break;
			case MotionEvent.ACTION_MOVE:
				if(x>touchLocation[2][0]&&x<touchLocation[2][2]&&
				   y>touchLocation[2][1]&&y<touchLocation[2][3])//����ģʽ
				{
					yOffset=yOffset+y-beY;   
					beY=y;
					if(yOffset>=0)
					{
						yOffset=0;
					}
					else if(yOffset<=-(alist.size()/(index+1))*(recordNum[0].getHeight()+tyoffset)+uBOffset)
					{
						yOffset=-(alist.size()/(index+1))*(recordNum[0].getHeight()+tyoffset)+uBOffset;
					}
				}
			break;
			case MotionEvent.ACTION_UP:

			break;
		}
		return true;
	}

	public void init()
	{
		index=1;//��ʱģʽ�;���ģʽ��ͼƬ����ֵ
		alist=new ArrayList<String>();//�洢�����ݿ���ȡ�����ݵļ���
		offset=55*ratio_width;
		tyoffset=20*ratio_width;
		clipYOffset=10*ratio_height;
		//ǰһʱ�̴��ص��Yλ��
		beY=0;
		//����ƫ��������ֵ
		uBOffset=0;		
		yOffset=0;
		
		//��ȡ��ʱ����������
		alist=DBUtil.queryDatabase("jsRecord");
		uBOffset=(alist.size()/(index+1)<=2)?alist.size()/(index+1)*(recordNum[0].getHeight()+tyoffset):3*(recordNum[0].getHeight()+tyoffset);
	}


}