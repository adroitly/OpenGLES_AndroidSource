package com.bn.st.xc;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.bn.core.MatrixState;

import android.opengl.GLES20;

//���غ�����塪��Я��������Ϣ���Զ�������ƽ��������
public class LoadedObjectVertexNormalXC
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id  
    int muMVPMatrixHandle;//�ܱ任��������id
    int muMMatrixHandle;//λ�á���ת�任����
    int maPositionHandle; //����λ����������id  
    int maNormalHandle; //���㷨������������id  
    int maLightLocationHandle;//��Դλ����������id  
    int maCameraHandle; //�����λ����������id 
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��    
    
    int maColorR;	//��ɫֵ��R��������id
    int maColorG;	//��ɫֵ��G��������id
    int maColorB;	//��ɫֵ��B��������id
    int maColorA;
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���  
	FloatBuffer   mNormalBuffer;//���㷨�������ݻ���
    int vCount=0;  
    
    float r;	//��ɫֵ��R����
    float g;	//��ɫֵ��G����
    float b;	//��ɫֵ��B����
    
    public LoadedObjectVertexNormalXC(float[] vertices,float[] normals,float r,float g,float b)
    {    	
    	//��ʼ�������������ݵķ���
    	initVertexData(vertices,normals);
    	this.r=r;
    	this.g=g;
    	this.b=b;
    }
    
    //��ʼ�������������ݵķ���
    public void initVertexData(float[] vertices,float[] normals)
    {
    	//�����������ݵĳ�ʼ��================begin============================
    	vCount=vertices.length/3;   
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��

        
        //���㷨�������ݵĳ�ʼ��================begin============================  
        ByteBuffer cbb = ByteBuffer.allocateDirect(normals.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mNormalBuffer.put(normals);//�򻺳����з��붥�㷨��������
        mNormalBuffer.position(0);//���û�������ʼλ��

    }
    //��ʼ����ɫ������ķ���
    public void initShader(int mProgramIn)
    {
        mProgram = mProgramIn;
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�����ɫ��������id  
        maNormalHandle= GLES20.glGetAttribLocation(mProgram, "aNormal");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
        //��ȡλ�á���ת�任��������id
        muMMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMMatrix"); 
        //��ȡ�����й�Դλ������id
        maLightLocationHandle=GLES20.glGetUniformLocation(mProgram, "uLightLocation");
        //��ȡ�����������λ������id
        maCameraHandle=GLES20.glGetUniformLocation(mProgram, "uCamera"); 
        
        maColorR=GLES20.glGetUniformLocation(mProgram, "colorR");
        maColorG=GLES20.glGetUniformLocation(mProgram, "colorG");
        maColorB=GLES20.glGetUniformLocation(mProgram, "colorB");
        maColorA=GLES20.glGetUniformLocation(mProgram, "colorA");
    }
    
    public void drawSelf(float Alpha)
    {        
    	 //�ƶ�ʹ��ĳ��shader����
    	 GLES20.glUseProgram(mProgram);
         //�����ձ任������shader����
         GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
         //��λ�á���ת�任������shader����
         GLES20.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0);   
         //����Դλ�ô���shader����   
         GLES20.glUniform3fv(maLightLocationHandle, 1, MatrixState.lightPositionFB);
         //�������λ�ô���shader����   
         GLES20.glUniform3fv(maCameraHandle, 1, MatrixState.cameraFB);
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
         //���붥�㷨��������
         GLES20.glVertexAttribPointer  
         (
        		maNormalHandle, 
         		3,   
         		GLES20.GL_FLOAT,   
         		false,
                3*4,      
                mNormalBuffer
         );   
         //������λ�á���������������
         GLES20.glEnableVertexAttribArray(maPositionHandle);  
         GLES20.glEnableVertexAttribArray(maNormalHandle);  
         
         GLES20.glUniform1f(maColorR , r); 
         GLES20.glUniform1f(maColorG , g); 
         GLES20.glUniform1f(maColorB , b); 
         GLES20.glUniform1f(maColorA , Alpha);
         //���Ƽ��ص�����
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
    }
}
