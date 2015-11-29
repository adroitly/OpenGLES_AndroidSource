package com.bn.clp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;
import com.bn.core.MatrixState;

public class Bridge extends BNDrawer
{
	Bridge_In bridge_in;
	public Bridge(int programId) 
	{
		bridge_in=new Bridge_In(programId);
	}
	
	@Override
	public void drawSelf(int[] texId,int dyFlag)
	{
		MatrixState.pushMatrix();
		bridge_in.realDrawSelf(texId[0]);			
		MatrixState.popMatrix();		
	}
	
	private class Bridge_In
	{
		//��λ����
		float UNIT_SIZE=2.1f;
		
		//�Զ�����Ⱦ���ߵ�id
		int mProgram;
		//�ܱ仯�������õ�id
		int muMVPMatrixHandle;
		//����λ����������id
		int maPositionHandle;
		//��������������������id
		int maTexCoorHandle;
		
		//�������ݻ���������������ݻ���
		FloatBuffer mVertexBuffer;
		FloatBuffer mTexCoorBuffer;
		//��������
		int vCount=0;
		
		public Bridge_In(int programId) 
		{
			initVertexData();
			initShader(programId);
		}
		//��ʼ���������ݵ�initVertexData����
		public void initVertexData()
		{
			float[] vertex=new float[]
		    {
				//�����ǰ��
				-6*UNIT_SIZE,4.5f*UNIT_SIZE,0,
				-6*UNIT_SIZE,4*UNIT_SIZE,0,
				23*UNIT_SIZE,4*UNIT_SIZE,0,
				
				-6*UNIT_SIZE,4.5f*UNIT_SIZE,0,
				23*UNIT_SIZE,4*UNIT_SIZE,0,
				23*UNIT_SIZE,4.5f*UNIT_SIZE,0,
				//�����²�
				23*UNIT_SIZE,4*UNIT_SIZE,0,
				-6*UNIT_SIZE,4*UNIT_SIZE,0,
				-6*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,

				23*UNIT_SIZE,4*UNIT_SIZE,0,
				-6*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
				23*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
				//������
				-6*UNIT_SIZE,4.5f*UNIT_SIZE,-4*UNIT_SIZE,
				23*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
				-6*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
				
				-6*UNIT_SIZE,4.5f*UNIT_SIZE,-4*UNIT_SIZE,
				23*UNIT_SIZE,4.5f*UNIT_SIZE,-4*UNIT_SIZE,
				23*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
				//���²��֧����������
				//���²��֧���������Ρ�������
				//ǰ��
				2*UNIT_SIZE,4*UNIT_SIZE,0,
				3*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				4*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,

				2*UNIT_SIZE,4*UNIT_SIZE,0,
				4*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				5*UNIT_SIZE,4*UNIT_SIZE,0,
				//�Ҳ�
				5*UNIT_SIZE,4*UNIT_SIZE,0,
				4*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				4*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,

				5*UNIT_SIZE,4*UNIT_SIZE,0,
				4*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				5*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
				//���
				2*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
				4*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				3*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,

				2*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
				5*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
				4*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				//���
				2*UNIT_SIZE,4*UNIT_SIZE,0,
				2*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
				3*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,

				2*UNIT_SIZE,4*UNIT_SIZE,0,
				3*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				3*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				//���²��֧�����������塪������
				//ǰ��
				3*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				3*UNIT_SIZE,0,-UNIT_SIZE,
				4*UNIT_SIZE,0,-UNIT_SIZE,

				3*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				4*UNIT_SIZE,0,-UNIT_SIZE,
				4*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				//�Ҳ�
				4*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				4*UNIT_SIZE,0,-UNIT_SIZE,
				4*UNIT_SIZE,0,-3*UNIT_SIZE,

				4*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				4*UNIT_SIZE,0,-3*UNIT_SIZE,
				4*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				//���
				3*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				4*UNIT_SIZE,0,-3*UNIT_SIZE,
				3*UNIT_SIZE,0,-3*UNIT_SIZE,

				3*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				4*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				4*UNIT_SIZE,0,-3*UNIT_SIZE,
				//���
				3*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				3*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				3*UNIT_SIZE,0,-3*UNIT_SIZE,

				3*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				3*UNIT_SIZE,0,-3*UNIT_SIZE,
				3*UNIT_SIZE,0,-UNIT_SIZE,
				//���²��֧����������

				//���²��֧�������Ҳ��
				//���²��֧���������Ρ����Ҳ��
				//ǰ��
				10*UNIT_SIZE,4*UNIT_SIZE,0,
				11*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				12*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,

				10*UNIT_SIZE,4*UNIT_SIZE,0,
				12*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				13*UNIT_SIZE,4*UNIT_SIZE,0,
				//�Ҳ�
				13*UNIT_SIZE,4*UNIT_SIZE,0,
				12*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				12*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,

				13*UNIT_SIZE,4*UNIT_SIZE,0,
				12*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				13*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
				//���
				10*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
				12*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				11*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,

				10*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
				13*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
				12*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				//���
				10*UNIT_SIZE,4*UNIT_SIZE,0,
				10*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
				11*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,

				10*UNIT_SIZE,4*UNIT_SIZE,0,
				11*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				11*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				//���²��֧�����������塪���Ҳ��
				//ǰ��
				11*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				11*UNIT_SIZE,0,-UNIT_SIZE,
				12*UNIT_SIZE,0,-UNIT_SIZE,

				11*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				12*UNIT_SIZE,0,-UNIT_SIZE,
				12*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				//�Ҳ�
				12*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				12*UNIT_SIZE,0,-UNIT_SIZE,
				12*UNIT_SIZE,0,-3*UNIT_SIZE,

				12*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				12*UNIT_SIZE,0,-3*UNIT_SIZE,
				12*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				//���
				11*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				12*UNIT_SIZE,0,-3*UNIT_SIZE,
				11*UNIT_SIZE,0,-3*UNIT_SIZE,

				11*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				12*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				12*UNIT_SIZE,0,-3*UNIT_SIZE,
				//���
				11*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				11*UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				11*UNIT_SIZE,0,-3*UNIT_SIZE,

				11*UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				11*UNIT_SIZE,0,-3*UNIT_SIZE,
				11*UNIT_SIZE,0,-UNIT_SIZE
		    };
			vCount=vertex.length/3;
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertex.length*4);
			vbb.order(ByteOrder.nativeOrder());
			mVertexBuffer=vbb.asFloatBuffer();
			mVertexBuffer.put(vertex);
			mVertexBuffer.position(0);
			
			float[] texcoor=new float[]
	        {
				//�����ǰ��
				0,0,   0,0.1f,   2,0.1f,
				0,0,   2,0.1f,   2,0,
				//�����²�
				2,0,    0,0,   0,0.7f,
				2,0,    0,0.7f,   2,0.7f,
				//������
				0,0,   2,0.1f,   0,0.1f,
				0,0,   2,0,   2,0.1f,
				//���²��֧����������
				//���²��֧���������Ρ�������
				//ǰ��
				0.0667f,0.2f,   0.1333f,0.4f,   0.2667f,0.4f,
				0.0667f,0.2f,   0.2667f,0.4f,   0.3333f,0.2f,
				//�Ҳ�
				0.0667f,0.2f,   0.1333f,0.4f,   0.2667f,0.4f,
				0.0667f,0.2f,   0.2667f,0.4f,   0.3333f,0.2f,
				//���
				0.0667f,0.2f,   0.2667f,0.4f,   0.1333f,0.4f,
				0.0667f,0.2f,   0.3333f,0.2f,   0.2667f,0.4f,
				//���
				0.3333f,0.2f,   0.0667f,0.2f,   0.1333f,0.4f,
				0.3333f,0.2f,   0.1333f,0.4f,   0.2667f,0.4f,
				//���²��֧�����������塪������
				//ǰ��
				0.1333f,0.4f,   0.1333f,1,   0.2667f,1,
				0.1333f,0.4f,   0.2667f,1,   0.2667f,0.4f,
				//�Ҳ�
				0.1333f,0.4f,   0.1333f,1,   0.2667f,1,
				0.1333f,0.4f,   0.2667f,1,   0.2667f,0.4f,
				//���
				0.1333f,0.4f,   0.2667f,1,   0.1333f,1,
				0.1333f,0.4f,   0.2667f,0.4f,   0.2667f,1,
				//���
				0.2667f,0.4f,   0.1333f,0.4f,   0.1333f,1,
				0.2667f,0.4f,   0.1333f,1,   0.2667f,1,
				//���²��֧����������

				//���²��֧�������Ҳ��
				//���²��֧���������Ρ����Ҳ��
				//ǰ��
				0.6667f,0.2f,   0.7333f,0.4f,   0.8667f,0.4f,
				0.6667f,0.2f,   0.8667f,0.4f,   0.9333f,0.2f,
				//�Ҳ�
				0.6667f,0.2f,   0.7333f,0.4f,   0.8667f,0.4f,
				0.6667f,0.2f,   0.8667f,0.4f,   0.9333f,0.2f,
				//���
				0.6667f,0.2f,   0.8667f,0.4f,   0.7333f,0.4f,
				0.6667f,0.2f,   0.9333f,0.2f,   0.8667f,0.4f,
				//���
				0.9333f,0.2f,   0.6667f,0.2f,   0.7333f,0.4f,
				0.9333f,0.2f,   0.7333f,0.4f,   0.8667f,0.4f,
				//���²��֧�����������塪���Ҳ��
				//ǰ��
				0.7333f,0.4f,   0.7333f,1,   0.8667f,1,
				0.7333f,0.4f,   0.8667f,1,   0.8667f,0.4f,
				//�Ҳ�
				0.7333f,0.4f,   0.7333f,1,   0.8667f,1,
				0.7333f,0.4f,   0.8667f,1,   0.8667f,0.4f,
				//���
				0.7333f,0.4f,   0.8667f,1,   0.7333f,1,
				0.7333f,0.4f,   0.8667f,0.4f,   0.8667f,1,
				//���
				0.8667f,0.4f,   0.7333f,0.4f,   0.7333f,1,
				0.8667f,0.4f,   0.7333f,1,   0.8667f,1,
	        };
			ByteBuffer tbb=ByteBuffer.allocateDirect(texcoor.length*4);
			tbb.order(ByteOrder.nativeOrder());
			mTexCoorBuffer=tbb.asFloatBuffer();
			mTexCoorBuffer.put(texcoor);
			mTexCoorBuffer.position(0);
		}
		
		//��ʼ����ɫ����initShader����
		public void initShader(int programId) 
		{
			//���ڶ�����ɫ����ƬԪ��ɫ����������
	        mProgram = programId;
	        //��ȡ�����ж���λ����������id  
	        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
	        //��ȡ�����ж�������������������id  
	        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
	        //��ȡ�������ܱ任��������id
	        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
		}
		
		//�Զ���Ļ��Ʒ���drawSelf
		public void realDrawSelf(int texId)
		{
			//�ƶ�ʹ��ĳ��shader����
	   	 	GLES20.glUseProgram(mProgram); 
	        //�����ձ任������shader����
	        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
	        
			//���붥��λ������
			GLES20.glVertexAttribPointer
			(
				maPositionHandle, 
				3, 
				GLES20.GL_FLOAT, 
				false, 
				3*4, 
				mVertexBuffer
			);
			//����������������
			GLES20.glVertexAttribPointer
			(
				maTexCoorHandle, 
				2, 
				GLES20.GL_FLOAT, 
				false, 
				2*4, 
				mTexCoorBuffer
			);
			//������λ����������
	        GLES20.glEnableVertexAttribArray(maPositionHandle);  
	        GLES20.glEnableVertexAttribArray(maTexCoorHandle);  
	        
	        //������
	        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
	        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
	        
	        //�����������
	        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
		}
	}
}