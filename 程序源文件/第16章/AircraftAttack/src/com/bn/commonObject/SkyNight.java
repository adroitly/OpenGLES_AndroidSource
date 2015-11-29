package com.bn.commonObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.bn.core.MatrixState;

import android.opengl.GLES20;

//��ʾ�ǿ��������
public class SkyNight 
{
	float skyR=30.0f;//����뾶
	private FloatBuffer   mVertexBuffer;//�����������ݻ���
    int vCount=0;//��������
    float scale;//���ǳߴ�
    
    String mVertexShader;//������ɫ������ű�    	 
    String mFragmentShader;//ƬԪ��ɫ������ű�
    
    int mProgram;//�Զ�����Ⱦ������ɫ������id 
    int muMVPMatrixHandle;//�ܱ任��������id   
    int maPositionHandle; //����λ����������id  
    int uPointSizeHandle;//����ߴ��������
    
    public SkyNight(float scale,int vCount,float skyR)
    {
    	this.skyR=skyR;
    	this.scale=scale;
    	this.vCount=vCount;  
    	initVertexData();
    }
    
    //��ʼ����������ķ���
    public void initVertexData()
    {    	  	
    	//�����������ݵĳ�ʼ��================begin=======================================       
        float vertices[]=new float[vCount*6];
        for(int i=0;i<vCount;i++)
        {
        	//�������ÿ�����ǵ�xyz����
        	double angleTempJD=Math.PI*2*Math.random();
        	double angleTempWD=Math.PI/3*(Math.random());
        	vertices[i*6]=(float)(skyR*Math.cos(angleTempWD)*Math.sin(angleTempJD));
        	vertices[i*6+1]=(float)(skyR*Math.sin(angleTempWD));
        	vertices[i*6+2]=(float)(skyR*Math.cos(angleTempWD)*Math.cos(angleTempJD));
        	
        	vertices[i*6+3]=(float)(skyR*Math.cos(angleTempWD)*Math.sin(angleTempJD));
        	vertices[i*6+4]=-(float)(skyR*Math.sin(angleTempWD));
        	vertices[i*6+5]=(float)(skyR*Math.cos(angleTempWD)*Math.cos(angleTempJD));
        	
        	System.out.println();
        }
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ��Float�ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��

    }

    //��ʼ����ɫ����initShader����
    public void initShader(int mProgram)
    {
        this.mProgram = mProgram;
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");        
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix"); 
        //��ȡ����ߴ��������
        uPointSizeHandle = GLES20.glGetUniformLocation(mProgram, "uPointSize"); 
    }
    
    public void drawSelf()
    {  
    	//�ƶ�ʹ��ĳ��shader����
   	    GLES20.glUseProgram(mProgram);
        //�����ձ任������shader����
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);  
        //������ߴ紫��Shader����
        GLES20.glUniform1f(uPointSizeHandle, scale);  
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
        //������λ����������
        GLES20.glEnableVertexAttribArray(maPositionHandle);         
        //�������ǵ�    
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, vCount*2); 
    }
}
