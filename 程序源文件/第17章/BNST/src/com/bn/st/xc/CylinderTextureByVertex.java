package com.bn.st.xc;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.bn.core.MatrixState;

import android.opengl.GLES20;
/*
 * ���ڻ���Բ��,�ù��պ���ɫ
 */
public class CylinderTextureByVertex 
{	
	int mProgram;//�Զ�����Ⱦ����ɫ�������߳���id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maNormalHandle; //���㷨������������id
    int muMMatrixHandle;//λ�á���ת�任����
    int maLightLocationHandle;//��Դλ����������id  
    int maCameraHandle; //�����λ����������id 
    
    int maColorR;	//��ɫֵ��R��������id
    int maColorG;	//��ɫֵ��G��������id
    int maColorB;	//��ɫֵ��B��������id
    int maColorA;
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��
    private static FloatBuffer   mVertexBuffer;//�����������ݻ���
    private static FloatBuffer   mNormalBuffer;//���㷨�������ݻ���
    static int vCount=0;//��������
	
	float r;	//��ɫֵ��R����
    float g;	//��ɫֵ��G����
    float b;	//��ɫֵ��B����
   
    public CylinderTextureByVertex
    (
    	int mProgramIn,
    	float radius,//Բ���뾶
    	float length,//Բ������
    	float aspan,//�зֽǶ�
    	float lspan,//�зֳ���  
    	float[]color
    )
    {    	
    	this.r=color[0];
    	this.g=color[1];
    	this.b=color[2];
    	//��ʼ����ɫ������ķ���        
    	initShader(mProgramIn);
    }
    //��ʼ�������������ݵķ���
    public static void initVertexData
    (
		float r,//Բ���뾶
    	float length,//Բ������
    	float aspan,//�зֽǶ�
    	float lspan//�зֳ���  	
    )
    {
    	//�����������ݵĳ�ʼ��================begin============================
    	//��ȡ�з���ͼ����������    	
    	ArrayList<Float> alVertix=new ArrayList<Float>();//��Ŷ��������ArrayList
        for(float tempY=length/2;tempY>-length/2;tempY=tempY-lspan)//��ֱ����lspan����һ��
        {
        	for(float hAngle=360;hAngle>0;hAngle=hAngle-aspan)//ˮƽ����angleSpan��һ��
        	{
        		//����������һ���ǶȺ�����Ӧ�Ĵ˵��������ϵ��ı��ζ�������
        		//��������������ı��ε�������
        		
        		float x1=(float)(r*Math.cos(Math.toRadians(hAngle)));
        		float z1=(float)(r*Math.sin(Math.toRadians(hAngle)));
        		float y1=tempY;
        		float x2=(float)(r*Math.cos(Math.toRadians(hAngle)));
        		float z2=(float)(r*Math.sin(Math.toRadians(hAngle)));
        		float y2=tempY-lspan;
        		
        		float x3=(float)(r*Math.cos(Math.toRadians(hAngle-aspan)));
        		float z3=(float)(r*Math.sin(Math.toRadians(hAngle-aspan)));
        		float y3=tempY-lspan;
        		
        		float x4=(float)(r*Math.cos(Math.toRadians(hAngle-aspan)));
        		float z4=(float)(r*Math.sin(Math.toRadians(hAngle-aspan)));
        		float y4=tempY;   
        		
        		//������һ������
        		alVertix.add(x1);alVertix.add(y1);alVertix.add(z1);
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);        		
        		//�����ڶ�������
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		alVertix.add(x3);alVertix.add(y3);alVertix.add(z3); 
        	}
        } 	
        vCount=alVertix.size()/3;//���������Ϊ����ֵ������1/3����Ϊһ��������3������
        //��alVertix�е�����ֵת�浽һ��int������
        float vertices[]=new float[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��

        //�������㷨�������껺��
        ByteBuffer nbb = ByteBuffer.allocateDirect(vertices.length*4);
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = nbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mNormalBuffer.put(vertices);//�򻺳����з��붥����ɫ����
        mNormalBuffer.position(0);//���û�������ʼλ��

    }
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
    public void drawSelf(float alpha)
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
         GLES20.glUniform1f(maColorA , alpha);
         //��������Բ��
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
    }
}
