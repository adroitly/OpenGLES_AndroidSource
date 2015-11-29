package com.bn.clp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;
import com.bn.core.MatrixState;

public class WeiLang
{
	//�Զ�����Ⱦ������ɫ������id
	int mProgram; 
	//�ܱ仯�������õ�id
	int muMVPMatrixHandle;
	//����λ����������id
	int maPositionHandle;
	//��������������������id
	int maTexCoorHandle;
	
	int maSTOffset;	//ˮ������ͼ��ƫ��������id
	int muTMD;//�ٶ�͸���Ȳ���
	
	float currStartST=0;	//ˮ����������ĵ�ǰ��ʼ����0~1
	
	//�������ݻ���������������ݻ���
	FloatBuffer mVertexBuffer;
	FloatBuffer mTexCoorBuffer;
	//��������
	int vCount=0;
	
	public WeiLang(float a,float b,float height,float[] texCoor,int programId)
	{
		initVertexData(a,b,height,texCoor);
		initShader(programId);
		//����һ���̶߳�ʱ��֡
    	new Thread()
    	{   
    		public void run()   
    		{
    			while(Constant.threadFlag)
    			{
    				//��νˮ�涨ʱ��ֻ֡���޸�ÿ֡��ʼ�Ƕȼ��ɣ�
    				//ˮ�涥��Y����ı仯�ɶ�����ɫ��Ԫ���
    				currStartST=(currStartST+0.1f)%1;
        			try 
        			{
    					Thread.sleep(100);  
    				} catch (InterruptedException e) 
    				{
    					e.printStackTrace();
    				}
    			}     
    		}    
    	}.start();  
	}
	//��ʼ���������ݵķ���
	public void initVertexData(float a,float b,float height,float[] texCoor)
	{
		float[] vertex=new float[]
	    {
			-a,height/3,0,   
			-b,-height*2,0,
			b,-height*2,0,

			-a,height/3,0,
			b,-height*2,0,
			a,height/3,0,
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
        mProgram = programId;
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�������������������id  
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        //��ȡˮ������ͼƫ����������id
        maSTOffset=GLES20.glGetUniformLocation(mProgram, "stK");  
        //��ȡβ���ٶ�͸���ȵ�����id
        muTMD=GLES20.glGetUniformLocation(mProgram, "tmd");  
	}
	
	//�Զ���Ļ��Ʒ���drawSelf
	public void drawSelf(int texId,float startST)
	{
		//�ƶ�ʹ��ĳ��shader����
   	 	GLES20.glUseProgram(mProgram); 
        //�����ձ任������shader����
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
        //��ˮ������ͼ��stƫ��������shader����
        GLES20.glUniform1f(maSTOffset, startST);
        //��β���ٶ�͸���ȴ���shader����
        GLES20.glUniform1f(muTMD, Constant.CURR_BOAT_V_TMD);
        
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