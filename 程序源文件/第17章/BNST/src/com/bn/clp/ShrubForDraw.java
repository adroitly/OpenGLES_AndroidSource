package com.bn.clp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import com.bn.core.MatrixState;
import android.opengl.GLES20;
import static com.bn.clp.Constant.*;

//���ڻ��Ƶ�����ľ���������
public class ShrubForDraw 
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id   
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id     
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;   
    
    public ShrubForDraw(int programId,float[] texCoor)
    {    	
    	//��ʼ���������ݵ�initVertexData����
    	initVertexData(texCoor);
    	//��ʼ����ɫ����initShader����  
    	initShader(programId);
    }
    
    //��ʼ���������ݵķ���
    public void initVertexData(float[] texCoor)
    {
    	//�����������ݵĳ�ʼ��================begin============================
        vCount=6;
        float vertices[]=new float[]
        {
        		-GRASS_UNIT_SIZE,GRASS_UNIT_SIZE*5,0,
            	-GRASS_UNIT_SIZE,0,0,
            	GRASS_UNIT_SIZE,GRASS_UNIT_SIZE*5,0,
            	
            	GRASS_UNIT_SIZE,GRASS_UNIT_SIZE*5,0,
            	-GRASS_UNIT_SIZE,0,0,
            	GRASS_UNIT_SIZE,0,0,
        };
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��

        
        //���������������ݵĳ�ʼ��================begin============================
        //�������������������ݻ���
        
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(texCoor);//�򻺳����з��붥����ɫ����
        mTexCoorBuffer.position(0);//���û�������ʼλ��


    }

    //��ʼ����ɫ����initShader����
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
         
         //�����������
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
    }
}