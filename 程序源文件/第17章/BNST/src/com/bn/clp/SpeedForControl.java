package com.bn.clp;

import static com.bn.clp.Constant.*;
import static com.bn.clp.MyGLSurfaceView.sight_angle;
import com.bn.core.MatrixState;
import com.bn.st.d2.MyActivity;
 
//�ɳ�����Ŀ�����
public class SpeedForControl
{
	long pzTime;//��ײʱ��ʱ��
	
	SpeedForEat speed;//���������
	int id;//���id
	float x;
	float y;
	float z;
	static float angleY;
	float rows;
	float cols;
	boolean isDrawFlag=true;//�Ƿ���Ƶı�־λ
	MyActivity ma;
	public SpeedForControl(SpeedForEat speedForEat,int id,float x,float y,float z,float angleY,float rows,float cols,MyActivity ma)
	{
		this.speed=speedForEat;
		this.id=id;
		this.x=x;
		this.y=y;
		this.z=z;
		this.rows=rows;
		this.cols=cols;
		this.ma=ma;
	}
	
	//���Ʒ���
	public void drawSelf(int texId,int dyFlag)
	{
		if(isDrawFlag)
		{			
			if(dyFlag==0)//����ʵ��
			{
				MatrixState.pushMatrix();
				MatrixState.translate(x, y, z);
				MatrixState.rotate(angleY, 0, 1, 0);
				speed.drawSelf(texId);
				MatrixState.popMatrix();
			}
			else if(dyFlag==1)//���Ƶ�Ӱ
			{
				//ʵ�ʻ���ʱY�����
				float yTranslate=y;
				//���о������ʱ�ĵ���ֵ
				float yjx=(0-yTranslate)*2;				
				MatrixState.pushMatrix();
				MatrixState.translate(x, y, z);
				MatrixState.rotate(angleY, 0, 1, 0);
				MatrixState.translate(0, yjx, 0);
				MatrixState.scale(1, -1, 1);
				speed.drawSelf(texId);
				MatrixState.popMatrix();
				}
		}
	}
	
	//�����Ƿ�����ײ
	//���ݴ���λ�ü������ͷλ�ã����ж��Ƿ���ĳ����ײ������ײ
	public void checkColl(float bX,float bZ,float carAlphaTemp)
	{
		//���������ײ��������
		float bPointX=(float) (bX-BOAT_UNIT_SIZE*Math.sin(Math.toRadians(sight_angle)));
		float bPointZ=(float) (bZ-BOAT_UNIT_SIZE*Math.cos(Math.toRadians(sight_angle)));		
		
		//������ײ���ڵ�ͼ�ϵ��к���
		float carCol=(float) Math.floor((bPointX+UNIT_SIZE/2)/UNIT_SIZE);
		float carRow=(float) Math.floor((bPointZ+UNIT_SIZE/2)/UNIT_SIZE);
		
		if(carRow==rows&&carCol==cols&&isDrawFlag)
		{//��������ͬһ������������ϸ����ײ���KZBJBJ
			double disP2=(bPointX-x)*(bPointX-x)+(bPointZ-z)*(bPointZ-z);
			//�����4Ϊһ������ֵ���Ժ���Ҫ����ʵ���������
			if(disP2<=4)
			{//��ײ��
				if(id==0)//�������ӵ��������
				{
					if(numberOfN2<maxNumberOfN2)
					{
						numberOfN2=numberOfN2+1;//����������ֵ����1
					} 
					if(SoundEffectFlag)
					{
						ma.shengyinBoFang(3, 0);
					}					
					isDrawFlag=false;
					pzTime=System.currentTimeMillis();
				}
				else if(id==1)//�Ե�����  
				{
					if(SoundEffectFlag)
					{
						ma.shengyinBoFang(3, 0);
					}					 
					CURR_BOAT_V=CURR_BOAT_V/2;//�Ե���������֮���ٶȱ�Ϊԭ�ȵ�һ��
					isDrawFlag=false;
					pzTime=System.currentTimeMillis();
				}
			} 
		}
	}
	//�÷������ж��Ѿ��Ե��������Ƿ��Ѿ�����50�룬�������50�룬��������ʾ
	public void checkEatYet()
	{
		//�������Ļ��Ʊ�־λΪtrue������ʱ������60��
		if(!isDrawFlag&&((System.currentTimeMillis()-pzTime)%60000/1000>=50))
		{
			isDrawFlag=true;
		}
	}
	

}