package com.bn.st.xc;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.bn.core.MatrixState;

import android.opengl.GLES20;

//���غ�����塪������+��������
public class LoadedObjectVertexTexXC
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id  
    int muMVPMatrixHandle;//�ܱ任��������id
    int muMMatrixHandle;//λ�á���ת�任����
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id  
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���  
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;  
    
    public LoadedObjectVertexTexXC(int mProgramId,float[] vertices,float texCoors[])
    {    	
    	//��ʼ�������������ݵķ���
    	initVertexData(vertices,texCoors);
    	//��ʼ����ɫ������ķ���        
    	//initShader(mProgramId);
    }   
    
    //��ʼ�������������ݵķ���
    public void initVertexData(float[] vertices,float texCoors[])
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
      
        
        //���������������ݵĳ�ʼ��================begin============================  
        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoors.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer = tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(texCoors);//�򻺳����з��붥��������������
        mTexCoorBuffer.position(0);//���û�������ʼλ��
    }

    //��ʼ����ɫ������ķ���
    public void initShader(int mProgramId)
    {
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = mProgramId;
        //��ȡ�����ж���λ����������id
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
        //��ȡλ�á���ת�任��������id
        muMMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMMatrix"); 
        //��ȡ�����ж�������������������id
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor"); 
    }
    
    public void drawSelf(int texId)
    {        
    	 //�ƶ�ʹ��ĳ��shader����
    	 GLES20.glUseProgram(mProgram);
         //�����ձ任������shader����
         GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
         //��λ�á���ת�任������shader����
         GLES20.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0); 
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
         //������λ�á�������������������������
         GLES20.glEnableVertexAttribArray(maPositionHandle);  
         GLES20.glEnableVertexAttribArray(maTexCoorHandle); 
         //������
         GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
         GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
         //���Ƽ��ص�����
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
    }
}
