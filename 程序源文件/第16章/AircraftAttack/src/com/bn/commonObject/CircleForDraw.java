package com.bn.commonObject;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;

import com.bn.core.MatrixState;
/**
 * ��triangle_fan��ʽ��������Բ�� ��Բ����ƽ����XYƽ���
 * 	���ڻ���Բ,Բ�������λ��ԭ��
 * �ڸ��������õ�
 */
public class CircleForDraw
{
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id     
    
	private  FloatBuffer   mVertexBuffer;//�����������ݻ���
	private  FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    private  int vCount;
    float angleSpan=12;//�зֽǶ�
    public CircleForDraw
    (
            int mProgram,
    		float radius//Բ�뾶
    )
    {
    	this.mProgram=mProgram;
    	initVertexData(radius);
    	initShader();
    }
    public  void initVertexData
    (
    		float radius//Բ�뾶
    )
    {
    	//���������������ݵĳ�ʼ��================begin============================
    	vCount=1+(int)(360/angleSpan)+1;//����ĸ���
    	float[] vertices=new float[vCount*3];//��ʼ����������
    	float[] textures=new float[vCount*2];
    	
    	//������ĵ�����
    	vertices[0]=0;
    	vertices[1]=0;
    	vertices[2]=0;
    	
    	//������ĵ�����
    	textures[0]=0.5f;
    	textures[1]=0.5f;
        
    	int vcount=3;//��ǰ������������
    	int tcount=2;//��ǰ������������
    	
    	for(float angle=0;angle<=360;angle=angle+angleSpan)
    	{
    		double angleRadian=Math.toRadians(angle);
    		//��������
    		vertices[vcount++]=radius*(float)Math.cos(angleRadian);
    		vertices[vcount++]=radius*(float)Math.sin(angleRadian);
    		vertices[vcount++]=0;
    		textures[tcount++]=textures[0]+0.5f*(float)Math.cos(angleRadian);
    		textures[tcount++]=textures[1]+0.5f*(float)Math.sin(angleRadian);
    	}  
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
                
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mTexCoorBuffer = tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(textures);//�򻺳����з��붥����ɫ����
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
        //����ͼ��
        GLES20.glDrawArrays
        (
        		GL10.GL_TRIANGLE_FAN, 		//��TRIANGLE_FAN��ʽ���
        		0,
        		vCount
        );
    }
}
