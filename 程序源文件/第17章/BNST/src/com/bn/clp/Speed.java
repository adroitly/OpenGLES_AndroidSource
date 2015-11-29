package com.bn.clp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;
import com.bn.core.MatrixState;
 
//�ɳ�����
public class Speed extends SpeedForEat
{
	Diamond dd;
	final float UNIT_SIZE=1.0f;
	final float width=UNIT_SIZE;
	final float height=UNIT_SIZE*2;
	public Speed(int programId)
	{
		dd=new Diamond(programId,width,height);
	} 
	//�ܵĻ��Ʒ���drawSelf
	public void drawSelf(int texId)
	{
		dd.drawSelf(texId);
	}
	//�ڲ��ࡪ������
	private class Diamond
	{
		//�Զ���Shader���������
		int mProgram;
		//�ܱ任���������id
		int muMVPMatrixHandle;
		//�������Ե�����id
		int maPositionHandle;
		//�����������������id
		int maTexCoorHandle;
		
		//�����������ݻ���
		FloatBuffer mVertexBuffer;
		//���������������ݻ���
		FloatBuffer mTexCoorBuffer;
		int vCount=0;//��������
		
		//RΪԲ���ײ��İ뾶��rΪԲ���ϲ��İ뾶��angle_span��ʾ�����зֵĽǶ�
		public Diamond(int programId,float width,float height)
		{
			initVertexData(width,height);
			initShader(programId);
		}
		//��ʼ���������ݵ�initVertexData����
		public void initVertexData(float width,float height)
		{
			float[] vertex=new float[]
            {
				0,height,0,   -width,0,-width,   -width,0,width,
				0,height,0,   -width,0,width,   width,0,width,
				0,height,0,   width,0,width,   width,0,-width,
				0,height,0,   width,0,-width,   -width,0,-width,
				
				0,-height,0,   -width,0,width,   -width,0,-width,
				0,-height,0,   width,0,width,   -width,0,width,
				0,-height,0,   width,0,-width,   width,0,width,
				0,-height,0,   -width,0, -width,  width,0,-width, 
            };
			vCount=24;//��������
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertex.length*4);
			vbb.order(ByteOrder.nativeOrder());
			mVertexBuffer=vbb.asFloatBuffer();
			mVertexBuffer.put(vertex);
			mVertexBuffer.position(0);
			
			float[] texcoor=new float[]
            {
				0.5f,0,   0,1,   1,1,
				0.5f,0,   0,1,   1,1,
				0.5f,0,   0,1,   1,1,
				0.5f,0,   0,1,   1,1,
				
				0.5f,0,   0,1,   1,1,
				0.5f,0,   0,1,   1,1,
				0.5f,0,   0,1,   1,1,
				0.5f,0,   0,1,   1,1,
            };
			ByteBuffer tbb=ByteBuffer.allocateDirect(texcoor.length*4);
			tbb.order(ByteOrder.nativeOrder());
			mTexCoorBuffer=tbb.asFloatBuffer();
			mTexCoorBuffer.put(texcoor);
			mTexCoorBuffer.position(0);
		}
		//��ʼ����ɫ�������initShader����
		public void initShader(int programId)
		{
			mProgram=programId;
			//��ö����������ݵ�����
			maPositionHandle=GLES20.glGetAttribLocation(mProgram, "aPosition");
			//�����������������id
			maTexCoorHandle=GLES20.glGetAttribLocation(mProgram, "aTexCoor");
			muMVPMatrixHandle=GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		}
		//�Զ���Ļ��Ʒ���
		public void drawSelf(int texId)
		{
			//ʹ��ĳ��ָ����Shader����
			GLES20.glUseProgram(mProgram);
			//�����ձ任�����뵽Shader������
			GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
			//���붥����������
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