package com.bn.planeModel;
import static com.bn.gameView.Constant.planezAngle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

import com.bn.core.MatrixState;
/*
 * ����������   ���������
 */
public class Airscrew 
{
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id  
    String mVertexShader;//������ɫ������ű�    	 
    String mFragmentShader;//ƬԪ��ɫ������ű�
    
	private FloatBuffer mVertexBuffer;	//�����������ݻ���
	private FloatBuffer mTextureBuffer;	//�����������ݻ���
	
	int vCount=6;						//��������
	final int angleSpan=8;				//ÿƬ������ҶƬ�Ƕ�
	float scale;						//�ߴ�
	float zSpan=0;						//��������z���ϵ�ƫ��
	float speed_Airscrew=50f;//��������ת�ؽ��ٶ�
	
	public Airscrew(float scale,int mProgram)
	{
		this.mProgram=mProgram;
		this.scale=scale;	
		zSpan=scale/12;		//��������z���ϵ�ƫ��
		initVertex();		//��ʼ��������������
		initTexture();		//��ʼ��������������
		initShader();
	}
	//��ʼ���������Ϣ
	public void initVertex()
	{   //����������
		float x=(float) (this.scale*Math.cos(Math.toRadians(angleSpan)));//���������ζ����x����ı���
		float y=(float) (this.scale*Math.sin(Math.toRadians(angleSpan)));//���������ζ����y����ı���
		float z=zSpan;														 //���������ζ����z����ı���
		//�������껺�������ʼ��
		float[] vertices=
		{				
			//���������������ε�����
			0,0,0,
			x,y,0,
			x,-y,-z,
			
			//�����ڲ����������ε�����
			0,0,0,
			x,-y,-z,
			x,y,0,								
		};
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);	
		vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
		mVertexBuffer=vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
		mVertexBuffer.put(vertices);//�򻺳����з��붥����������
		mVertexBuffer.position(0);//���û�������ʼλ��
	}
	//��ʼ���������Ϣ
	public void initTexture()
	{
		float[] textures=generateTextures();	//����������������
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
		tbb.order(ByteOrder.nativeOrder());		//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
		mTextureBuffer=tbb.asFloatBuffer();		//ת��Ϊfloat�ͻ���
		mTextureBuffer.put(textures);			//�򻺳����з��붥����������
		mTextureBuffer.position(0);				//���û�������ʼλ��
	}
	//��������
	public float[] generateTextures()
	{
		float[] textures=new float[]
        {//����������������
			0,0,1,0,0,1,
			0,0,0,1,1,0,
	    };
		return textures;
	}
	//��ʼ��shader
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
		drawOneAirscrew(texId);//��������һ��������
		MatrixState.rotate(60, 0, 0, 1);
		drawOneAirscrew(texId);//��������һ��������
		MatrixState.rotate(60, 0, 0, 1);
		drawOneAirscrew(texId);//��������һ��������
		MatrixState.rotate(60, 0, 0, 1);
		drawOneAirscrew(texId);//��������һ��������
		MatrixState.rotate(60, 0, 0, 1);
		drawOneAirscrew(texId);//��������һ��������
		MatrixState.rotate(60, 0, 0, 1);
		drawOneAirscrew(texId);//��������һ��������
		MatrixState.popMatrix();
	}
	public void drawOneAirscrew(int texId)//��������һ��������
	{
		MatrixState.pushMatrix();
		MatrixState.rotate(planezAngle, 0, 0, 1);
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