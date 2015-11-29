package com.bn.arsenal;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.bn.core.MatrixState;

import android.opengl.GLES20;

public class Triangle
{
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id  
    
    private FloatBuffer   mVertexBuffer;//�����������ݻ���
    private FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;//��������
	public Triangle(int  mProgram,float width,float height)
	{
		this.mProgram=mProgram;
    	//��ʼ����ɫ����initShader����        
    	initShader();
		 vCount=3;
	        float vertices[]=new float[]    
	        {
	        	0,height,0,
	        	-width/2,0,0,      
	        	width/2,0,0,
	        	
	        	
	        };
	        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
	        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
	        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
	        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
	        mVertexBuffer.position(0);//���û�������ʼλ��
	     
	        float texCoor[]=new float[]//������ɫֵ���飬ÿ������4��ɫ��ֵRGBA
	        {
	      		0.5f,0, 0,1, 1,1,	      		       		
	        };        
	       //�������������������ݻ���
	       ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
	       cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
	       mTexCoorBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
	       mTexCoorBuffer.put(texCoor);//�򻺳����з��붥����ɫ����
	       mTexCoorBuffer.position(0);//���û�������ʼλ��
	}
	 public void initShader()
	    {
	        //��ȡ�����ж���λ����������id  
	        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
	        //��ȡ�������ܱ任��������id
	        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
	        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
	    }
	 public void drawSelf(int texId)
	    {        
	    	//�ƶ�ʹ��ĳ����ɫ������  
	   	 	GLES20.glUseProgram(mProgram);   
	        //�����ձ任��������ɫ������
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
	         //�����������
	         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
	    }
}
