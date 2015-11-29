package com.bn.clp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;
import com.bn.core.MatrixState;

public class Process
{
	//�Զ�����Ⱦ���ߵ�id
	int mProgram;
	//�ܱ仯�������õ�id
	int muMVPMatrixHandle;
	//����λ����������id
	int maPositionHandle;
	//��������������������id
	int maTexCoorHandle;
	int maPrograss;//j������λ������
	
	//�������ݻ���������������ݻ���
	FloatBuffer mVertexBuffer;  
	FloatBuffer mTexCoorBuffer;
	//��������
	int vCount=0;
	
	//ѭ����־λ
	boolean flag=true;
	float currPrograss;//��ǰ����
	float width;
	float percent;
	//percent��ʾ����ֵ
	public Process(int programId,float width,float height,float[] texCoor,float percent)
	{
		this.width=width;
		this.percent=percent;
		//��ʼ����������  
		initVertexData(width,height,texCoor);
		initShader(programId);
		
		new Thread()
		{
			@Override
			public void run()
			{
				while(flag)
				{
					if(currPrograss>=Process.this.width)
					{
						flag=false;
						break;
					}
					currPrograss=percentToWidth(Process.this.percent,Process.this.width);
					try
					{
						Thread.sleep(100);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	//��ʼ���������ݵķ���
	public void initVertexData(float width,float height,float[] texCoor)
	{
		float[] vertex=new float[]
	    {
			-width,height,0,
			-width,-height,0,
			width,-height,0,

			-width,height,0,
			width,-height,0,
			width,height,0,
	    };
		vCount=vertex.length/3;
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertex.length*4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer=vbb.asFloatBuffer();
		mVertexBuffer.put(vertex);
		mVertexBuffer.position(0);
		
		//�����������ݻ���
		ByteBuffer tbb=ByteBuffer.allocateDirect(texCoor.length*4);
		tbb.order(ByteOrder.nativeOrder());
		mTexCoorBuffer=tbb.asFloatBuffer();
		mTexCoorBuffer.put(texCoor); 
		mTexCoorBuffer.position(0);
	}
	
	//��ʼ����ɫ���ķ���
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
        //���ȵ�����id
        maPrograss= GLES20.glGetUniformLocation(mProgram, "aPrograss");
	}
	
	//�Զ���Ļ��Ʒ���drawSelf
	public void drawSelf(int texId)
	{
		//�ƶ�ʹ��ĳ��shader����
   	 	GLES20.glUseProgram(mProgram); 
        //�����ձ任������shader����
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
        //����ǰ�Ľ��ȴ��뵽Shader����
        GLES20.glUniform1f(maPrograss, currPrograss);
        
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
	
	//���ٷֱȶ�Ӧ����width��ص����ݣ�percentΪ�ٷֱȣ�widthΪ��Ӧ����Ļ�İ���
	public float percentToWidth(float percent,float width)
	{
		return percent*width*2-width;
	}
}