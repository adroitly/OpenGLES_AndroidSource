/**
 * 
 * 	���ڻ���Բ
 */
package com.bn.gameView;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

import com.bn.commonObject.Light_Tower;
import com.bn.core.MatrixState;

import android.opengl.GLES20;
//��triangle_fan��ʽ����Բ�� ��Բ����ƽ����XYƽ���
public class CircleForDraw
{
	float taizihight=30;
	int mProgram;//�Զ�����Ⱦ���߳���id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
        
    int maColorR;	//��ɫֵ��R��������id
    int maColorG;	//��ɫֵ��G��������id
    int maColorB;	//��ɫֵ��B��������id
    int maColorA;
	private static FloatBuffer   mVertexBuffer;//�����������ݻ���
    private static int vCount;
 
    float r;	//��ɫֵ��R����
    float g;	//��ɫֵ��G����
    float b;	//��ɫֵ��B����
    
    Light_Tower taizi;
    public CircleForDraw
    (
            int mProgram,
    		float angleSpan,//�зֽǶ�
    		float radius,//Բ�뾶
    		float[]color,
    		int mProgramlitht
    )
    {
    	this.r=color[0];
    	this.g=color[1];
    	this.b=color[2];
    	initVertexData(angleSpan,radius);
    	initShader(mProgram);
    	
    	taizi=new Light_Tower(radius,radius,taizihight,1);
    	taizi.initShader(mProgramlitht);
    }
    public static void initVertexData
    (
    		float angleSpan,//�зֽǶ�
    		float radius//Բ�뾶
    )
    {
    	//���������������ݵĳ�ʼ��================begin============================
    	vCount=1+(int)(360/angleSpan)+1;//����ĸ���
    	float[] vertices=new float[vCount*3];//��ʼ����������
    	//������ĵ�����
    	vertices[0]=0;
    	vertices[1]=0;
    	vertices[2]=0;
    	int vcount=3;//��ǰ������������
    	for(float angle=0;angle<=360;angle=angle+angleSpan)
    	{
    		double angleRadian=Math.toRadians(angle);
    		//��������
    		vertices[vcount++]=radius*(float)Math.cos(angleRadian);
    		vertices[vcount++]=radius*(float)Math.sin(angleRadian);
    		vertices[vcount++]=0;
    	}  
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
    }
    public void initShader(int mProgramIn)
    {
        mProgram = mProgramIn;
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
        maColorR=GLES20.glGetUniformLocation(mProgram, "colorR");
        maColorG=GLES20.glGetUniformLocation(mProgram, "colorG");
        maColorB=GLES20.glGetUniformLocation(mProgram, "colorB");
        maColorA=GLES20.glGetUniformLocation(mProgram, "colorA");
    }
    public void drawSelf(float alpha,int texId)
    {        
    	//�ƶ�ʹ��ĳ��shader����
   	 	GLES20.glUseProgram(mProgram); 
        //�����ձ任������shader����
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
        //��λ�á���ת�任������shader����
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
        //������λ�á���������������
        GLES20.glEnableVertexAttribArray(maPositionHandle);  
        GLES20.glUniform1f(maColorR , r); 
        GLES20.glUniform1f(maColorG , g); 
        GLES20.glUniform1f(maColorB , b);
        GLES20.glUniform1f(maColorA , alpha);
        //����ͼ��
        GLES20.glDrawArrays
        (
        		GL10.GL_TRIANGLE_FAN, 		//��TRIANGLE_FAN��ʽ���
        		0,
        		vCount
        );
    }
}
