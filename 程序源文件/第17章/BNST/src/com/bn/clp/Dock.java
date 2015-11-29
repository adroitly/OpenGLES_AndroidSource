package com.bn.clp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20; 
import com.bn.core.MatrixState;

//�����Ǵ���ͷ
public class Dock extends BNDrawer
{
	DockIn dockIn;
	public Dock(int programId)
	{
		dockIn=new DockIn(programId);
	}
	@Override
	public void drawSelf(int[] texId, int dyFlag)
	{
		MatrixState.pushMatrix();
		dockIn.drawSelf(texId[0]);
		MatrixState.popMatrix();
	}
	
	private class DockIn
	{
		//��λ����
		float UNIT_SIZE=0.3f;
		
		//�Զ�����Ⱦ������ɫ����id
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
		
		public DockIn(int programId)
		{
			initVertexData();
			initShader(programId);
		}
		//��ʼ���������ݵ�initVertexData����
		public void initVertexData()
		{
			float[] vertex=new float[]
		    {
					//������
					-15*UNIT_SIZE,9.5f*UNIT_SIZE,-5*UNIT_SIZE,
					-15*UNIT_SIZE,9.5f*UNIT_SIZE,5*UNIT_SIZE,
					15*UNIT_SIZE,9.5f*UNIT_SIZE,-5*UNIT_SIZE,
					
					15*UNIT_SIZE,9.5f*UNIT_SIZE,-5*UNIT_SIZE,
					-15*UNIT_SIZE,9.5f*UNIT_SIZE,5*UNIT_SIZE,
					15*UNIT_SIZE,9.5f*UNIT_SIZE,5*UNIT_SIZE,
					//������
					15*UNIT_SIZE,8.5f*UNIT_SIZE,-5*UNIT_SIZE,
					15*UNIT_SIZE,8.5f*UNIT_SIZE,5*UNIT_SIZE,
					-15*UNIT_SIZE,8.5f*UNIT_SIZE,-5*UNIT_SIZE,
					
					-15*UNIT_SIZE,8.5f*UNIT_SIZE,-5*UNIT_SIZE,
					15*UNIT_SIZE,8.5f*UNIT_SIZE,5*UNIT_SIZE,
					-15*UNIT_SIZE,8.5f*UNIT_SIZE,5*UNIT_SIZE,
					//��ǰ��
					-15*UNIT_SIZE,9.5f*UNIT_SIZE,5*UNIT_SIZE,
					-15*UNIT_SIZE,8.5f*UNIT_SIZE,5*UNIT_SIZE,
					15*UNIT_SIZE,9.5f*UNIT_SIZE,5*UNIT_SIZE,
					
					15*UNIT_SIZE,9.5f*UNIT_SIZE,5*UNIT_SIZE,
					-15*UNIT_SIZE,8.5f*UNIT_SIZE,5*UNIT_SIZE,
					15*UNIT_SIZE,8.5f*UNIT_SIZE,5*UNIT_SIZE,
					//�����
					15*UNIT_SIZE,9.5f*UNIT_SIZE,-5*UNIT_SIZE,
					15*UNIT_SIZE,8.5f*UNIT_SIZE,-5*UNIT_SIZE,
					-15*UNIT_SIZE,9.5f*UNIT_SIZE,-5*UNIT_SIZE,
					
					-15*UNIT_SIZE,9.5f*UNIT_SIZE,-5*UNIT_SIZE,
					15*UNIT_SIZE,8.5f*UNIT_SIZE,-5*UNIT_SIZE,
					-15*UNIT_SIZE,8.5f*UNIT_SIZE,-5*UNIT_SIZE,
					//������
					-15*UNIT_SIZE,9.5f*UNIT_SIZE,-5*UNIT_SIZE,
					-15*UNIT_SIZE,8.5f*UNIT_SIZE,-5*UNIT_SIZE,
					-15*UNIT_SIZE,9.5f*UNIT_SIZE,5*UNIT_SIZE,
					
					-15*UNIT_SIZE,9.5f*UNIT_SIZE,5*UNIT_SIZE,
					-15*UNIT_SIZE,8.5f*UNIT_SIZE,-5*UNIT_SIZE,
					-15*UNIT_SIZE,8.5f*UNIT_SIZE,5*UNIT_SIZE,
					//������
					15*UNIT_SIZE,9.5f*UNIT_SIZE,5*UNIT_SIZE,
					15*UNIT_SIZE,8.5f*UNIT_SIZE,5*UNIT_SIZE,
					15*UNIT_SIZE,9.5f*UNIT_SIZE,-5*UNIT_SIZE,
					
					15*UNIT_SIZE,9.5f*UNIT_SIZE,-5*UNIT_SIZE,    
					15*UNIT_SIZE,9.5f*UNIT_SIZE,5*UNIT_SIZE,
					15*UNIT_SIZE,9.5f*UNIT_SIZE,-5*UNIT_SIZE,
					
					//---------------------���Ͻ�֧��---------------------------
					//����
					-13*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					-13*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					-12*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					
					-12*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					-13*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					-12*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					//����
					-12*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					
					-13*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					//ǰ��
					-13*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					-12*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					
					-12*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					//����
					-12*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					-13*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					
					-13*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					//����
					-13*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					-13*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					
					-13*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					//����
					-12*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					-12*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					
					-12*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					//----------------------���½�֧��------------------
					//����
					-13*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					-13*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					-12*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					
					-12*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					-13*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					-12*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					//����
					-12*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					
					-13*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					
					//ǰ��
					-13*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					-12*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					
					-12*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					//����
					-12*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					-13*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					
					-13*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					//����
					-13*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					-13*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					
					-13*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					-13*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					//����
					-12*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					-12*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					
					-12*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					-12*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					//----------���Ͻ�֧��------------
					//����
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					0.5f*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					
					0.5f*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					0.5f*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					
					//����
					0.5f*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					//ǰ��
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					0.5f*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					
					0.5f*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					//����
					0.5f*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					//����
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					//����
					0.5f*UNIT_SIZE,12*UNIT_SIZE,-4*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					0.5f*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					
					0.5f*UNIT_SIZE,12*UNIT_SIZE,-5*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,-4*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,-5*UNIT_SIZE,
					//----------���½�֧��------------
					//����
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					0.5f*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					
					0.5f*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					0.5f*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					//����
					0.5f*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					    
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					//ǰ��
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					0.5f*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					
					0.5f*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					//����
					0.5f*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					//����
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					
					-0.5f*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE,
					-0.5f*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					//����
					0.5f*UNIT_SIZE,12*UNIT_SIZE,5*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					0.5f*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					
					0.5f*UNIT_SIZE,12*UNIT_SIZE,4*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,5*UNIT_SIZE,
					0.5f*UNIT_SIZE,0*UNIT_SIZE,4*UNIT_SIZE, 
		    };
			vCount=vertex.length/3;
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertex.length*4);
			vbb.order(ByteOrder.nativeOrder());
			mVertexBuffer=vbb.asFloatBuffer();
			mVertexBuffer.put(vertex);
			mVertexBuffer.position(0);
			
			float[] texcoor=new float[]
	        {
				//--------һ�����������������--------------
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				//--------һ�����������������--------------
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				//--------һ�����������������--------------
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				//--------һ�����������������--------------
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				//--------һ�����������������--------------
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
				
				0,0, 0,1, 1,0,
				1,0, 0,1, 1,1,
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
	        mProgram =programId;
	        //��ȡ�����ж���λ����������id  
	        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
	        //��ȡ�����ж�������������������id  
	        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
	        //��ȡ�������ܱ任��������id
	        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
		}
		
		//�Զ���Ļ��Ʒ���drawSelf
		public void drawSelf(int texId)
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
