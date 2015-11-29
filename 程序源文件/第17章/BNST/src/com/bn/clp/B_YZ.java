package com.bn.clp;
 
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;
import com.bn.core.MatrixState;

//��������ӵ���    
public class B_YZ extends BNDrawer
{
	//��λ���� 
	float UNIT_SIZE=2.5f;    
	X_BYZ x_byz;     
	public B_YZ(int programId)
	{         
		x_byz=new X_BYZ(programId);
	}
	
	@Override
	public void drawSelf(int[] texId,int dyFlag)
	{
		MatrixState.pushMatrix();
		x_byz.drawSelf(texId[0], texId[1]);
		MatrixState.popMatrix();
		//�Ҳ� 
		MatrixState.pushMatrix();
		MatrixState.translate(22*UNIT_SIZE, 0, -4*UNIT_SIZE);
		MatrixState.rotate(180, 0, 1, 0);
		x_byz.drawSelf(texId[0], texId[1]);
		MatrixState.popMatrix(); 
	}
	//�������ڲ���
	private class X_BYZ
	{
		//�Զ�����Ⱦ����id
		int mProgram;
		//����λ�����Ե�����id
		int maPositionHandle;
		//���������������Ե�����id
		int maTexCoorHandle;
		//�ܱ仯���������id
		int muMVPMatrixHandle;
		
		//�ݵص�id
		int sTextureGrassHandle;
		//ʯͷ��id
		int sTextureRockHandle;
		//��ʼxֵ
		int b_YZ_StartXHandle;
		//����
		int b_YZ_XSpanHandle;
		
		
		//�����������ݻ���Ͷ����������ݻ���
		FloatBuffer mVertexBuffer;
		FloatBuffer mTexCoorBuffer;
		int vCount=0;
		
		public X_BYZ(int programId)
		{
			initVertexData();
			initShader(programId);
		}
		//��ʼ���������ݵ�initVertexData����
		public void initVertexData()
		{
			float[] vertex=new float[]
	        {
				//�ײ�				
				//�м䲿��
				//ǰ��
				UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				UNIT_SIZE,2*UNIT_SIZE,-UNIT_SIZE,
				5*UNIT_SIZE,4*UNIT_SIZE,-UNIT_SIZE,

				UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				5*UNIT_SIZE,4*UNIT_SIZE,-UNIT_SIZE,
				5*UNIT_SIZE,5*UNIT_SIZE,-UNIT_SIZE,
				//�Ҳ�
				5*UNIT_SIZE,4*UNIT_SIZE,-UNIT_SIZE,
				UNIT_SIZE,2*UNIT_SIZE,-UNIT_SIZE,
				UNIT_SIZE,2*UNIT_SIZE,-3*UNIT_SIZE,

				5*UNIT_SIZE,4*UNIT_SIZE,-UNIT_SIZE,
				UNIT_SIZE,2*UNIT_SIZE,-3*UNIT_SIZE,
				5*UNIT_SIZE,4*UNIT_SIZE,-3*UNIT_SIZE,
				//���
				UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				5*UNIT_SIZE,4*UNIT_SIZE,-3*UNIT_SIZE,
				UNIT_SIZE,2*UNIT_SIZE,-3*UNIT_SIZE,

				UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				5*UNIT_SIZE,5*UNIT_SIZE,-3*UNIT_SIZE,
				5*UNIT_SIZE,4*UNIT_SIZE,-3*UNIT_SIZE,
				//���
				5*UNIT_SIZE,5*UNIT_SIZE,-3*UNIT_SIZE,
				UNIT_SIZE,3*UNIT_SIZE,-3*UNIT_SIZE,
				UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,

				5*UNIT_SIZE,5*UNIT_SIZE,-3*UNIT_SIZE,
				UNIT_SIZE,3*UNIT_SIZE,-UNIT_SIZE,
				5*UNIT_SIZE,5*UNIT_SIZE,-UNIT_SIZE,
				//���ϲಿ��
				//ǰ��
				5*UNIT_SIZE,5*UNIT_SIZE,-UNIT_SIZE,
				5*UNIT_SIZE,4*UNIT_SIZE,-UNIT_SIZE,
				7*UNIT_SIZE,4.3f*UNIT_SIZE,-UNIT_SIZE,

				5*UNIT_SIZE,5*UNIT_SIZE,-UNIT_SIZE,
				7*UNIT_SIZE,4.3f*UNIT_SIZE,-UNIT_SIZE,
				7*UNIT_SIZE,4.7f*UNIT_SIZE,-UNIT_SIZE,
				//�Ҳࣨ�£�
				7*UNIT_SIZE,4.3f*UNIT_SIZE,-UNIT_SIZE,
				5*UNIT_SIZE,4*UNIT_SIZE,-UNIT_SIZE,
				5*UNIT_SIZE,4*UNIT_SIZE,-3*UNIT_SIZE,

				7*UNIT_SIZE,4.3f*UNIT_SIZE,-UNIT_SIZE,
				5*UNIT_SIZE,4*UNIT_SIZE,-3*UNIT_SIZE,
				7*UNIT_SIZE,4.3f*UNIT_SIZE,-3*UNIT_SIZE,
				//���
				5*UNIT_SIZE,5*UNIT_SIZE,-3*UNIT_SIZE,
				7*UNIT_SIZE,4.3f*UNIT_SIZE,-3*UNIT_SIZE,
				5*UNIT_SIZE,4*UNIT_SIZE,-3*UNIT_SIZE,

				5*UNIT_SIZE,5*UNIT_SIZE,-3*UNIT_SIZE,
				7*UNIT_SIZE,4.7f*UNIT_SIZE,-3*UNIT_SIZE,
				7*UNIT_SIZE,4.3f*UNIT_SIZE,-3*UNIT_SIZE,
				//��ࣨ�ϣ�
				7*UNIT_SIZE,4.7f*UNIT_SIZE,-3*UNIT_SIZE,
				5*UNIT_SIZE,5*UNIT_SIZE,-3*UNIT_SIZE,
				5*UNIT_SIZE,5*UNIT_SIZE,-UNIT_SIZE,

				7*UNIT_SIZE,4.7f*UNIT_SIZE,-3*UNIT_SIZE,
				5*UNIT_SIZE,5*UNIT_SIZE,-UNIT_SIZE,
				7*UNIT_SIZE,4.7f*UNIT_SIZE,-UNIT_SIZE,
				//���Ҳ�ķ��
				7*UNIT_SIZE,4.7f*UNIT_SIZE,-UNIT_SIZE,
				7*UNIT_SIZE,4.3f*UNIT_SIZE,-UNIT_SIZE,
				7*UNIT_SIZE,4.3f*UNIT_SIZE,-3*UNIT_SIZE,

				7*UNIT_SIZE,4.7f*UNIT_SIZE,-UNIT_SIZE,
				7*UNIT_SIZE,4.3f*UNIT_SIZE,-3*UNIT_SIZE,
				7*UNIT_SIZE,4.7f*UNIT_SIZE,-3*UNIT_SIZE
	        };
			vCount=vertex.length/3;
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertex.length*4);
			vbb.order(ByteOrder.nativeOrder());
			mVertexBuffer=vbb.asFloatBuffer();
			mVertexBuffer.put(vertex);
			mVertexBuffer.position(0);
			
			float[] texcoor=new float[]
  		    {
  				//�м䲿��
  				//ǰ��
  				0.1f,0.4f,   0.1f,0.6f,   0.5f,0.2f,
  				0.1f,0.4f,   0.5f,0.2f,   0.5f,0,
  				//�Ҳ�
  				0.5f,0.2f,   0.1f,0.6f,   0.2f,1.0f,
  				0.5f,0.2f,   0.2f,1.0f,   0.6f,0.6f,
  				//���
  				0.1f,0.4f,   0.5f,0.2f,   0.1f,0.6f,
  				0.1f,0.4f,   0.5f,0,   0.5f,0.2f,
  				//���
  				0.6f,0.6f,   0.2f,1.0f,   0.1f,0.6f,
  				0.6f,0.6f,   0.1f,0.6f,   0.5f,0.2f,
  				//���ϲಿ��
  				//ǰ��
  				0.5f,0,   0.5f,0.2f,   0.7f,0.14f,
  				0.5f,0,   0.7f,0.14f,   0.7f,0.06f,
  				//�Ҳࣨ�£�
  				0.7f,0.14f,   0.5f,0.2f,   0.6f,0.6f,
  				0.7f,0.14f,   0.6f,0.6f,   0.8f,0.54f,
  				//���
  				0.5f,0,   0.7f,0.14f,   0.5f,0.2f,
  				0.5f,0,   0.7f,0.06f,   0.7f,0.14f,
  				//��ࣨ�ϣ�
  				0.8f,0.54f,   0.6f,0.6f,   0.5f,0.2f,
  				0.8f,0.54f,   0.5f,0.2f,   0.7f,0.14f,
  				//���Ҳ�ķ��
  				0.7f,0.06f,   0.7f,0.14f,   0.9f,0.14f,
  				0.7f,0.06f,   0.9f,0.14f,   0.9f,0.06f
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
			mProgram=programId;
			//��ö����������Ե�����id
			maPositionHandle=GLES20.glGetAttribLocation(mProgram, "aPosition");
			//��ö��������������Ե�����id
			maTexCoorHandle=GLES20.glGetAttribLocation(mProgram, "aTexCoor");
			//����ܱ仯�������õ�id
			muMVPMatrixHandle=GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");			
			
			//����
			//�ݵ�
			sTextureGrassHandle=GLES20.glGetUniformLocation(mProgram, "sTextureGrass");
			//ʯͷ
			sTextureRockHandle=GLES20.glGetUniformLocation(mProgram, "sTextureRock");
			//xλ��
			b_YZ_StartXHandle=GLES20.glGetUniformLocation(mProgram, "b_YZ_StartX");
			//x���
			b_YZ_XSpanHandle=GLES20.glGetUniformLocation(mProgram, "b_YZ_XSpan");
		}
		//�Զ���Ļ��Ʒ���
		public void drawSelf(int texIdGrass,int texIdRock)
		{
			//ָ��ĳ��Shader����
			GLES20.glUseProgram(mProgram);
			//�����ձ仯�����뵽Shader����
			GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
			//Ϊ����ָ���������ꡪ��>�˴������괫��Shader??????
			GLES20.glVertexAttribPointer
			(
				maPositionHandle, 
				3, 
				GLES20.GL_FLOAT, 
				false, 
				3*4, 
				mVertexBuffer
			);
			//���붥����������
			GLES20.glVertexAttribPointer
			(
				maTexCoorHandle, 
				2, 
				GLES20.GL_FLOAT, 
				false, 
				2*4, 
				mTexCoorBuffer
			);
			//����ʹ�ö��������Լ�������������
			GLES20.glEnableVertexAttribArray(maPositionHandle);
			GLES20.glEnableVertexAttribArray(maTexCoorHandle);
			
			//��������
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texIdGrass);
			GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texIdRock);
			GLES20.glUniform1i(sTextureGrassHandle, 0);//ʹ��0������
	        GLES20.glUniform1i(sTextureRockHandle, 1); //ʹ��1������
			
	        //������Ӧ��x����
	        GLES20.glUniform1f(b_YZ_StartXHandle, 0);
	        GLES20.glUniform1f(b_YZ_XSpanHandle, 7*UNIT_SIZE);
	        
			//��������
			GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);		
		}
	}
}