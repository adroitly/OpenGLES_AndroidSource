package com.bn.planeModel;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

import com.bn.core.MatrixState;
//�������,���ڻ��ƺ����,ֻ�ж��������
public class Plane_BackWing 
{
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id  
    String mVertexShader;//������ɫ������ű�    	 
    String mFragmentShader;//ƬԪ��ɫ������ű�
    
	private FloatBuffer   mVertexBuffer;//�����������ݻ���
    private FloatBuffer   mTextureBuffer;//������ɫ���ݻ���
    int vCount ;
    float mAngleX ;
    
    public Plane_BackWing(float width,float height,float length,int mProgram)
    {
    	this.mProgram=mProgram;
    	initVertexData(width,height,length);
    	initShader();
    }
    public void initVertexData(float width,float height,float length)
    {
        float vertices[]=new float[]
        {
    		//�ϱ���
    		0,height,length,-width,height,length,-13.0f/10.0f*width,2.0f/5.0f*height,length,//OAB        		
    		
    		0,height,length,-13.0f/10.0f*width,2.0f/5.0f*height,length,-width,-height,length,//OBC
    		
    		0,height,length,-width,-height,length,width,-height,length,//OCD
    		
    		0,height,length,width,-height,length,13.0f/10.0f*width,2.0f/5.0f*height,length,//ODE

    		0,height,length,13.0f/10.0f*width,2.0f/5.0f*height,length,width,height,length,//OEF
    		
    		//�±���
    		0,height,-length,-width,height,-length,-13.0f/10.0f*width,2.0f/5.0f*height,-length,//OAB        		
    		
    		0,height,-length,-13.0f/10.0f*width,2.0f/5.0f*height,-length,-width,-height,-length,//OBC
    		
    		0,height,-length,-width,-height,-length,width,-height,-length,//OCD
    		
    		0,height,-length,width,-height,-length,13.0f/10.0f*width,2.0f/5.0f*height,-length,//ODE

    		0,height,-length,13.0f/10.0f*width,2.0f/5.0f*height,-length,width,height,-length,//OEF
 
    		-width,height,length,-width,height,-length,-13.0f/10.0f*width,2.0f/5.0f*height,-length,
    		0-width,height,length,-13.0f/10.0f*width,2.0f/5.0f*height,-length,-13.0f/10.0f*width,2.0f/5.0f*height,length,
    		-13.0f/10.0f*width,2.0f/5.0f*height,length,-13.0f/10.0f*width,2.0f/5.0f*height,-length,-width,-height,-length,
    		-13.0f/10.0f*width,2.0f/5.0f*height,length,-width,-height,-length,-width,-height,length,
    		-width,-height,length,-width,-height,-length,width,-height,-length,
    		-width,-height,length,width,-height,-length,width,-height,length,
    		width,-height,length,width,-height,-length,13.0f/10.0f*width,2.0f/5.0f*height,-length,
    		width,-height,length,13.0f/10.0f*width,2.0f/5.0f*height,-length,13.0f/10.0f*width,2.0f/5.0f*height,length,
    		13.0f/10.0f*width,2.0f/5.0f*height,length,13.0f/10.0f*width,2.0f/5.0f*height,-length,width,height,-length,
    		13.0f/10.0f*width,2.0f/5.0f*height,length,width,height,-length,width,height,length,
    		width,height,length,width,height,-length,-width,height,-length,
    		width,height,length,width,height,-length,0,height,-length,
    		width,height,length,0,height,-length,0,height,length,
    		0,height,length,0,height,-length,-width,height,-length,
    		0,height,length, -width,height,-length,	-width,height,length,	
        };
        
        vCount=vertices.length/3;
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
        
        //�����������ݵĳ�ʼ��================begin============================
        float textures[]=new float[]
        {
        	
        	//�ϱ���
        		0.18f,0.0f,0.027f,0.0080f,0.0f,0.027f,
        		0.18f,0.0f,0.0f,0.027f,0.0f,0.09f,
        		0.18f,0.0f,0.0f,0.09f,0.035f,0.109f,
        		0.18f,0.0f,0.035f,0.109f,0.145f,0.109f,
        		0.18f,0.0f,0.145f,0.109f,0.168f,0.074f,
        		0.18f,0.0f,0.168f,0.074f,0.211f,0.07f,
        		
        	//�±���
        		0.18f,0.0f,0.027f,0.0080f,0.0f,0.027f,
        		0.18f,0.0f,0.0f,0.027f,0.0f,0.09f,
        		0.18f,0.0f,0.0f,0.09f,0.035f,0.109f,
        		0.18f,0.0f,0.035f,0.109f,0.145f,0.109f,
        		0.18f,0.0f,0.145f,0.109f,0.168f,0.074f,
        		0.18f,0.0f,0.168f,0.074f,0.211f,0.07f,
        	//����
        		0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,
        		0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,
        		0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,
        		0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,
        		0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,
        		0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,   		
        		0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,0.168f,0.043f,0.152f,0.09f,0.223f,0.074f, 
        };

        
        //���������������ݻ���
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mTextureBuffer= tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTextureBuffer.put(textures);//�򻺳����з��붥����ɫ����
        mTextureBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
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
