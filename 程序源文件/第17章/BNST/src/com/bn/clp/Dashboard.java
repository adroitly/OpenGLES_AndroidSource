package com.bn.clp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import com.bn.core.MatrixState;
import android.opengl.GLES20;
import static com.bn.clp.Constant.*;
//���Ƽ���������
public class Dashboard
{
	TextureRect tr;
	DrawLine dl;
	float angle;
	float startAngle=125;  
	  
	public Dashboard(int programId)
	{
		tr=new TextureRect(programId,Self_Adapter_Data_TRASLATE[screenId][11],0.25f); 
		dl=new DrawLine(programId,0.005f,0.16f);
	}
	//���Ʒ���
	public void drawSelf(int texId)
	{
		//�����Ǳ���
		MatrixState.pushMatrix();
		tr.drawSelf(texId);
		MatrixState.popMatrix();
		//������
		MatrixState.pushMatrix();
		MatrixState.translate(0.005f, -0.01f, 0.5f);
		MatrixState.rotate(angle, 0, 0, 1);
		dl.drawSelf(texId); 
		MatrixState.popMatrix();
	}
	
	public void changeangle(float v)//ָ��Ƕ�ת�ķ���
	{
		float vSpan=Max_BOAT_V_FINAL/250;//ÿһ�Ǳ���ָ��Ƕ�����ʾ���ٶ�
		{
			float v_Angle=v/vSpan;
			angle=startAngle-v_Angle;
		}		
	}
	
	//�����Ǳ��̵���
	private class TextureRect
	{
		//�Զ�����Ⱦ������ɫ������id
		int mProgram;
		//�ܱ仯���������id
		int muMVPMatrixHandle;
		//�������Ե�����id
		int maPositionHandle;
		//�����������Ե�����id
		int maTexCoorHandle;
		//�����������ݻ��塢���������������ݻ���
		FloatBuffer mVertexBuffer;
		FloatBuffer mTexCoorBuffer;
		//��������
		int vCount;
		
		public TextureRect(int mProgramId,float width,float height)
		{
			initVertexData(width,height);
			initShader(mProgramId);
		}
		//��ʼ����Ӧ�������ݵķ���
		public void initVertexData(float width,float height)
		{
			float[] vertices=new float[]
	        {
				-width,height,0,
				-width,-height,0,
				width,-height,0,
				
				-width,height,0,
				width,-height,0,
				width,height,0,
	        };
			vCount=6;
			//���ö������껺����
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);
			vbb.order(ByteOrder.nativeOrder());
			mVertexBuffer=vbb.asFloatBuffer();
			mVertexBuffer.put(vertices);
			mVertexBuffer.position(0);
			
			float[] texcoor=new float[]
	        {
				0,0.129f,  0,0.98f,  1,0.98f,
				0,0.129f,  1,0.98f,  1,0.129f,
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
			//�����Զ����Shader����
			mProgram=programId;
			//��ö����������Ե�����id
			maPositionHandle=GLES20.glGetAttribLocation(mProgram, "aPosition");
			//��ö��������������Ե�����id
			maTexCoorHandle=GLES20.glGetAttribLocation(mProgram, "aTexCoor");
			//����ܱ任���������id
			muMVPMatrixHandle=GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		}
		//�Զ���Ļ��Ʒ���
		public void drawSelf(int texId)
		{
			//ʹ��ĳ��ָ����Shader����
			GLES20.glUseProgram(mProgram);
			//���ܱ任������Shader����
			GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
			//���붥������
			GLES20.glVertexAttribPointer
			(
				maPositionHandle,
				3, 
				GLES20.GL_FLOAT,
				false, 
				3*4, 
				mVertexBuffer
			);
			//���붥��������������
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
			//����
			GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
		}
	}
	//������
	private class DrawLine
	{
		//�Զ�����Ⱦ������ɫ������id
		int mProgram;
		//�ܱ仯���������id
		int muMVPMatrixHandle;
		//�������Ե�����id
		int maPositionHandle;
		//�����������Ե�����id
		int maTexCoorHandle;
		//�����������ݻ��塢���������������ݻ���
		FloatBuffer mVertexBuffer;
		FloatBuffer mTexCoorBuffer;
		//��������
		int vCount;
		
		public DrawLine(int mProgramId,float width,float height)
		{
			initVertexData(width,height);
			initShader(mProgramId);
		}
		//��ʼ����Ӧ�������ݵ�initVertexData����
		public void initVertexData(float width,float height)
		{
			float[] vertices=new float[]
	        {
				-width,height,0,
				-width,0,0,
				width,0,0,
				
				-width,height,0,
				width,0,0,
				width,height,0,
	        };
			vCount=6;
			//���ö������껺����
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);
			vbb.order(ByteOrder.nativeOrder());
			mVertexBuffer=vbb.asFloatBuffer();
			mVertexBuffer.put(vertices);
			mVertexBuffer.position(0);
			
			float[] texcoor=new float[]
	        {
				0,0,  0,0.125f,  0.45f,0.125f,
				0,0,  0.45f,0.125f,  0.45f,0,
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
			//�����Զ����Shader����
			mProgram=programId;
			//��ö����������Ե�����id
			maPositionHandle=GLES20.glGetAttribLocation(mProgram, "aPosition");
			//��ö��������������Ե�����id
			maTexCoorHandle=GLES20.glGetAttribLocation(mProgram, "aTexCoor");
			//����ܱ任���������id
			muMVPMatrixHandle=GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		}
		//�Զ���Ļ��Ʒ���
		public void drawSelf(int texId)
		{
			//ʹ��ĳ��ָ����Shader����
			GLES20.glUseProgram(mProgram);
			//���ܱ任������Shader����
			GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
			//���붥������
			GLES20.glVertexAttribPointer
			(
				maPositionHandle,
				3, 
				GLES20.GL_FLOAT,
				false, 
				3*4, 
				mVertexBuffer
			);
			//���붥��������������
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
			//����
			GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
		}
	}
	
}