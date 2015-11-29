package com.bn.commonObject;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.opengl.GLES20;

import com.bn.core.MatrixState;
/*
 * ���ڻ���Բ��,ֻ������
 * Բ��������λ��ԭ��,Բ��������ƽ����Y��
 * �ڸ��������õ�
 */
public class CylinderForDraw 
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id  
    FloatBuffer   mVertexBuffer;//�����������ݻ���
    FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;//��������
    float aspan=15;//�зֽǶ�
	float lspan;//�зֳ���  
    public CylinderForDraw
    (
    	float radius,//Բ���뾶
    	float length,//Բ������
    	int mProgram
    )
    {    	
    	this.mProgram=mProgram;
    	lspan=length;//��ʼ���зֵĵ�λ����
    	//��ʼ������
    	initVertexData(radius,length);
    	//��ʼ����ɫ����initShader����       
    	initShader();
    }
    //��ʼ��������������ɫ���ݵķ���
    public  void initVertexData
    (
		float r,//Բ���뾶
    	float length//Բ������
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
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        float[] texCoorArray=generateTexCoor
    	(
    			 (int)(360/aspan), //����ͼ�зֵ�����
    			 (int)(length/lspan)  //����ͼ�зֵ�����
    	);  
        //�������������������ݻ���
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoorArray.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mTexCoorBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(texCoorArray);//�򻺳����з��붥����ɫ����
        mTexCoorBuffer.position(0);//���û�������ʼλ��
    }
    public void initShader()
    {
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
        //��ȡ�����ж�������������������id  
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
    }
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
         //������λ�á�����������������
         GLES20.glEnableVertexAttribArray(maPositionHandle);  
         GLES20.glEnableVertexAttribArray(maTexCoorHandle);  
         
         //������
         GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
         GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
         //��������Բ��
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
    }
    //�Զ��з����������������ķ���
    public float[] generateTexCoor(int bw,int bh)
    {
    	float[] result=new float[bw*bh*6*2]; 
    	float sizew=1.0f/bw;//����
    	float sizeh=1.0f/bh;//����
    	int c=0;
    	for(int i=0;i<bh;i++)
    	{
    		for(int j=0;j<bw;j++)
    		{
    			//ÿ����һ�����Σ������������ι��ɣ��������㣬12����������
    			float s=j*sizew;
    			float t=i*sizeh;
    			
    			result[c++]=s;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t+sizeh;    			
    		}
    	}
    	return result;
    }
}
