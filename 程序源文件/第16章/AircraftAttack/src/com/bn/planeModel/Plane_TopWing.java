package com.bn.planeModel;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

import com.bn.core.MatrixState;
//�������,���ڻ�������Ļ���    ���������
public class Plane_TopWing 
{
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id  
    String mVertexShader;//������ɫ������ű�    	 
    String mFragmentShader;//ƬԪ��ɫ������ű�
    
	private FloatBuffer   mVertexBuffer;//�����������ݻ���
    private FloatBuffer   mTextureBuffer;//������ɫ���ݻ���
    float mAngleX;
    float mAngleY;
    int vCount = 42;
    
    public Plane_TopWing(float width,float height,float length,int mProgram)
    {
    	this.mProgram=mProgram;
    	initVertexData(width,height,length);
    	initShader();
    }
    public void initVertexData(float width,float height,float length)
    {
        float vertices[]=new float[]
        {
        	-5.0f/6.0f*width,4.0f/3.0f*height,0,//A
        	-width,height,-length,//B
        	-width,height,length,//C
        	
        	-5.0f/6.0f*width,4.0f/3.0f*height,0,//A
        	-width,height,length,//C
        	width,height,length,//D
        	
        	-5.0f/6.0f*width,4.0f/3.0f*height,0,//A
        	width,height,length,//D
        	5.0f/6.0f*width,4.0f/3.0f*height,0,//O
        	
        	5.0f/6.0f*width,4.0f/3.0f*height,0,//O
        	width,height,length,//D
        	width,height,-length,//E
        	
        	5.0f/6.0f*width,4.0f/3.0f*height,0,//O
        	width,height,-length,//E
        	-width,height,-length,//B
        	
        	5.0f/6.0f*width,4.0f/3.0f*height,0,//O
        	-width,height,-length,//B
        	-5.0f/6.0f*width,4.0f/3.0f*height,0,//A
        	
        	-width,height,length,//C
        	-width,0,6.0f/5.0f*length,//F
        	width,0,6.0f/5.0f*length,//G
        	
        	-width,height,length,//C
        	width,0,6.0f/5.0f*length,//G
        	width,height,length,//D
        	
        	width,height,length,//D
        	width,0,6.0f/5.0f*length,//G
        	width,0,-6.0f/5.0f*length,//H
        	
        	width,height,length,//D
        	width,0,-6.0f/5.0f*length,//H
        	width,height,-length,//E
        	
        	width,height,-length,//E
        	width,0,-6.0f/5.0f*length,//H
        	-width,0,-6.0f/5.0f*length,//I
        	
        	width,height,-length,//E
        	-width,0,-6.0f/5.0f*length,//I
        	-width,height,-length,//B
        	
        	-width,height,-length,//B
        	-width,0,-6.0f/5.0f*length,//I
        	-width,0,6.0f/5.0f*length,//F
        	
        	-width,height,-length,//B
        	-width,0,6.0f/5.0f*length,//F
        	-width,height,length,//C
        };
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        float textures[]=new float[]
        {
        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
        };
        //���������������ݻ���
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mTextureBuffer= tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTextureBuffer.put(textures);//�򻺳����з��붥����ɫ����
        mTextureBuffer.position(0);//���û�������ʼλ��
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
}
