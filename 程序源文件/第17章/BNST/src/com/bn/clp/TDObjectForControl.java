package com.bn.clp;

import android.opengl.GLES20;

import com.bn.core.MatrixState;
/*
 * �������Ҫ�����ǿ�����Ӧ3D����Ļ��ƣ�
 * ��Ҫ�����Ӧ3D����Ķ���objectId�����id��������Ӧ��x��y��zλ�á���ת�ĽǶ�yAngle 
 */
public class TDObjectForControl
{
	BNDrawer bndrawer;//3D����Ķ���
	int objectId;//objectId�����id
	float x;
	float y;
	float z;
	float yAngle;
	int rows;
	int cols;
	public TDObjectForControl(BNDrawer bndrawer,int objectId,float x,float y,float z,float yAngle,int rows,int cols)
	{
		this.bndrawer=bndrawer;
		this.objectId=objectId;
		this.x=x;
		this.y=y;
		this.z=z;
		this.yAngle=yAngle;
		this.rows=rows;
		this.cols=cols;
	}
	//�Զ���Ļ��Ʒ���drawSelf�������ڸ÷�������Ҫ�Ծ������ƽ���Լ���ת�任������������ҪpushMatrix�������ҪpopMatrix
	public void drawSelf(int[] texId,int dyFlag)
	{
		if(dyFlag==0)//����ʵ��
		{
			MatrixState.pushMatrix();
			MatrixState.translate(x, y, z);
			MatrixState.rotate(yAngle, 0, 1, 0);
			bndrawer.drawSelf(texId,dyFlag);
			MatrixState.popMatrix();
		}
		else if(dyFlag==1)//���Ƶ�Ӱ
		{
			//ʵ�ʻ���ʱY�����
			float yTranslate=y;
			//���о������ʱ�ĵ���ֵ
			float yjx=(0-yTranslate)*2;
			
			//�رձ������
            GLES20.glDisable(GLES20.GL_CULL_FACE);
			MatrixState.pushMatrix();
			MatrixState.translate(x, y, z);
			MatrixState.rotate(yAngle, 0, 1, 0);
			MatrixState.translate(0, yjx, 0);
			MatrixState.scale(1, -1, 1);
			bndrawer.drawSelf(texId,dyFlag);
			MatrixState.popMatrix();
			//�򿪱������
            GLES20.glEnable(GLES20.GL_CULL_FACE);
		}
	}
}