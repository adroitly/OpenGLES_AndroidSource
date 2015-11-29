package com.bn.planeModel;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.opengl.GLES20;

import com.bn.core.MatrixState;
/*
 * ������������        ���������
 * ���ڻ��ƻ���,��ͷ�ͻ���
 */
public class DrawSpheroid 
{
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id  
    String mVertexShader;//������ɫ������ű�    	 
    String mFragmentShader;//ƬԪ��ɫ������ű�
    
	private FloatBuffer  mVertexBuffer;//�����������ݻ���
	private FloatBuffer mTextureBuffer;//������
    public float mAngleX;//��x����ת�Ƕ�
    public float mAngleY;//��y����ת�Ƕ� 
    public float mAngleZ;//��z����ת�Ƕ� 
    int vCount=0;
    float a;
    float b;		//����뾶��
    float c;
    float angleSpan;//������е�λ�зֵĽǶ�
    float hAngleBegin;//���Ȼ�����ʼ�Ƕ�
    float hAngleOver;//���Ȼ��ƽ����Ƕ�
    float vAngleBegin;//γ�Ȼ�����ʼ�Ƕ�
    float vAngleOver;//γ�Ȼ��ƽ����Ƕ�
    
    //hAngle��ʾ���ȣ�vAngle��ʾγ�ȡ�
    public DrawSpheroid(float a,float b,float c,float angleSpan,
    					float hAngleBegin,float hAngleOver,float vAngleBegin,float vAngleOver,int mProgram)
    {	
    	this.a=a;
    	this.b=b;
    	this.c=c;
    	this.hAngleBegin=hAngleBegin;
    	this.hAngleOver=hAngleOver;
    	this.vAngleBegin=vAngleBegin;
    	this.vAngleOver=vAngleOver;
    	this.mProgram=mProgram;
    	this.angleSpan=angleSpan;
    	initVertexData();
    	initShader();
    }
    //��ʼ���������Ϣ
    public void initVertexData()
    {
    	ArrayList<Float> alVertix=new ArrayList<Float>();//��Ŷ�������
        for(float vAngle=vAngleBegin;vAngle<vAngleOver;vAngle=vAngle+angleSpan)//��ֱ����angleSpan��һ��
        {
        	for(float hAngle=hAngleBegin;hAngle<hAngleOver;hAngle=hAngle+angleSpan)//ˮƽ����angleSpan��һ��
        	{//����������һ���ǶȺ�����Ӧ�Ĵ˵��������ϵ�����    		
        		float x1=(float)(a*Math.cos(Math.toRadians(vAngle))*Math.cos(Math.toRadians(hAngle)));                          
        		float y1=(float)(b*Math.cos(Math.toRadians(vAngle))*Math.sin(Math.toRadians(hAngle)));
        		float z1=(float)(c*Math.sin(Math.toRadians(vAngle)));
        		
        		float x2=(float)(a*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.cos(Math.toRadians(hAngle)));
        		float y2=(float)(b*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.sin(Math.toRadians(hAngle)));
        		float z2=(float)(c*Math.sin(Math.toRadians(vAngle+angleSpan)));
        		
        		float x3=(float)(a*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.cos(Math.toRadians(hAngle+angleSpan)));
        		float y3=(float)(b*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.sin(Math.toRadians(hAngle+angleSpan)));
        		float z3=(float)(c*Math.sin(Math.toRadians(vAngle+angleSpan)));
        		
        		float x4=(float)(a*Math.cos(Math.toRadians(vAngle))*Math.cos(Math.toRadians(hAngle+angleSpan)));
        		float y4=(float)(b*Math.cos(Math.toRadians(vAngle))*Math.sin(Math.toRadians(hAngle+angleSpan)));
        		float z4=(float)(c*Math.sin(Math.toRadians(vAngle)));
        		
        		//�����������XYZ��������Ŷ��������ArrayList
        		alVertix.add(x1);alVertix.add(y1);alVertix.add(z1);
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);
        		
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		alVertix.add(x3);alVertix.add(y3);alVertix.add(z3); 
        	}
        } 	
        vCount=alVertix.size()/3;//���������Ϊ����ֵ������1/3����Ϊһ��������3������
        //��alVertix�е�����ֵת�浽һ��int������
        float[] vertices=new float[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
		//����
    	//��ȡ�з���ͼ����������
    	float[] texCoorArray= 
         generateTexCoor
    	 (
    			 (int)(180/angleSpan), //����ͼ�зֵ�����
    			 (int)(180/angleSpan)  //����ͼ�зֵ����� 
    	);
		ByteBuffer tbb=ByteBuffer.allocateDirect(texCoorArray.length*4);
		tbb.order(ByteOrder.nativeOrder());
		mTextureBuffer=tbb.asFloatBuffer();
		mTextureBuffer.put(texCoorArray);
		mTextureBuffer.position(0);
    }
    //��ʼ����ɫ����initShader����
    public void initShader()
    {
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�������������������id  
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix"); 
    }
    public void drawSelf(int texId)
    {    	
    	MatrixState.pushMatrix();
    	MatrixState.rotate(mAngleX, 1, 0, 0);
    	MatrixState.rotate(mAngleY, 0, 1, 0);
    	MatrixState.rotate(mAngleZ, 0, 0, 1);
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
               mTextureBuffer
        );   
        //������λ����������
        GLES20.glEnableVertexAttribArray(maPositionHandle);  
        GLES20.glEnableVertexAttribArray(maTexCoorHandle);  
        //������
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
        //�����������
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
        MatrixState.popMatrix();
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
