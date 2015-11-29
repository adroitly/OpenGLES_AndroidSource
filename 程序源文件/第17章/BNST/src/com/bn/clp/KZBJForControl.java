package com.bn.clp;

import static com.bn.clp.Constant.*;

import com.bn.core.MatrixState;
import com.bn.st.d2.MyActivity;

import android.opengl.GLES20;
import static com.bn.clp.MyGLSurfaceView.*;

//����ײ����������,��ͨ����idΪ0����ͨ׶��idΪ1   
public class KZBJForControl
{
	KZBJDrawer kzbjdrawer;
	int id;//��Ӧ����ײ��id��0��ʾ��ͨͲ��1��ʾ�ϰ���
	boolean state=false;//false��ʾ�ɱ���ײ��true��ʾ��ײ������У����򲻿���ײ��
	float x;//�ڷŵĳ�ʼλ��
	float y;
	float z;
	
	float alpha;//ת���Ƕ�
	float alphaX;//ת��������
	float alphaY;
	float alphaZ;
	
	float currentX;//�����еĵ�ǰλ��
	float currentY;
	float currentZ;
	
	int row;//λ�����ڵ�ͼ�к���
	int col;
	
	float vx;//�����е��ٶȷ���
	float vy;
	float vz;
	
	float time_Fly;//�����ۼ�ʱ��
	
	MyActivity ma;
	
	public KZBJForControl(KZBJDrawer kzbjdrawer,int id,float x,float y,float z,int row,int col,MyActivity ma)
	{
		this.kzbjdrawer=kzbjdrawer;
		this.id=id;
		this.x=x;
		this.y=y;
		this.z=z;
		this.row=row;
		this.col=col;
		this.ma=ma;
	}
	
	public void drawSelf(int texId,int dyFlag)
	{
		if(dyFlag==0)//����ʵ��
		{
			MatrixState.pushMatrix();
			if(!state)
			{//ԭʼ״̬����
				MatrixState.translate(x, y, z);
				//MyGLSurfaceView���еĸÿ�������б�
				ma.gameV.kzbj_array[id].drawSelf(texId);
			}
			else
			{//�����л���
				if(currentY>-40) 
				{//����Ѿ����е��������£��Ͳ��ٻ���
					MatrixState.translate(currentX, currentY, currentZ);
					MatrixState.rotate(alpha,alphaX, alphaY, alphaZ);
					ma.gameV.kzbj_array[id].drawSelf(texId);
				}
			}
			MatrixState.popMatrix();
		}
		else if(dyFlag==1)//���Ƶ�Ӱ
		{
			//ʵ�ʻ���ʱY�����
			float yTranslate=y;
			//���о������ʱ�ĵ���ֵ
			float yjx=(0-yTranslate)*2;
			
			MatrixState.pushMatrix();
			//�رձ������
            GLES20.glDisable(GLES20.GL_CULL_FACE);
			if(!state)
			{//ԭʼ״̬����
				MatrixState.translate(x, y, z);
				MatrixState.translate(0, yjx, 0);
				MatrixState.scale(1, -1, 1);
				//MyGLSurfaceView���еĸÿ�������б�
				ma.gameV.kzbj_array[id].drawSelf(texId);
			}
			else
			{//�����л���
				if(currentY>-40) 
				{//����Ѿ����е��������£��Ͳ��ٻ���
					MatrixState.translate(currentX, currentY, currentZ);
					MatrixState.rotate(alpha,alphaX, alphaY, alphaZ);
					MatrixState.translate(0, yjx, 0);
					MatrixState.scale(1, -1, 1);
					ma.gameV.kzbj_array[id].drawSelf(texId);
				}
			}
			//�򿪱������
            GLES20.glEnable(GLES20.GL_CULL_FACE);
			MatrixState.popMatrix();			
		}
	}
	
	//���ݴ���λ�ü������ͷλ�ã����ж��Ƿ���ĳ����ײ������ײ
	public void checkColl(float bX,float bZ,float carAlphaTemp)
	{
		//���������ײ��������
		float bPointX=(float) (bX-BOAT_UNIT_SIZE*Math.sin(Math.toRadians(sight_angle)));
		float bPointZ=(float) (bZ-BOAT_UNIT_SIZE*Math.cos(Math.toRadians(sight_angle)));		
		
		//������ײ���ڵ�ͼ�ϵ��к���
		float carCol=(float) Math.floor((bPointX+UNIT_SIZE/2)/UNIT_SIZE);
		float carRow=(float) Math.floor((bPointZ+UNIT_SIZE/2)/UNIT_SIZE);
		
		if(carRow==row&&carCol==col)
		{//��������ͬһ������������ϸ����ײ���KZBJBJ
			double disP2=(bPointX-x)*(bPointX-x)+(bPointZ-z)*(bPointZ-z);
			//����ͷ����Ŀ��С��4��Ϊ��ײ
			if(disP2<=4)
			{
				if(SoundEffectFlag)
				{
					ma.shengyinBoFang(4, 0); 
				}				
				state=true;//����״̬Ϊ������״̬
				time_Fly=0;//���г���ʱ������
				alpha=0;
				alphaX=(float) (-20*Math.cos(Math.toRadians(carAlphaTemp)));
				alphaY=0;
				alphaZ=(float) (20*Math.sin(Math.toRadians(carAlphaTemp)));
				currentX=x;//���÷�����ʼ��Ϊԭʼ�ڷŵ�
				currentY=y;
				currentZ=z;
				//���ݴ����н�����ȷ�������ٶȵ���������
				vx=(float) (-20*Math.sin(Math.toRadians(carAlphaTemp)));
				vy=15;
				vz=(float) (-10*Math.cos(Math.toRadians(carAlphaTemp)));
			}
		}
	}
	
	//�����ƶ��������̶߳�ʱ���ô˷�����ʵ�ֿ�ײ�������
	public void go()
	{
		if(!state)
		{//������ڷ���״̬�в���Ҫgo
			return;
		}
		
		time_Fly=time_Fly+0.3f;//���г���ʱ������
		alpha=alpha+10;
		//���ݷ����ٶȵ��������������г���ʱ������������㵱ǰλ��
		currentX=x+vx*time_Fly;
		currentZ=z+vz*time_Fly;
		currentY=y+vy*time_Fly-0.5f*5*time_Fly*time_Fly;//5Ϊ�������ٶ�
		//����ײ��������䵽��������2000ʱ�ָ�ԭλ
		if(currentY<-2000)
		{
			state=false;			
		}
	}
}