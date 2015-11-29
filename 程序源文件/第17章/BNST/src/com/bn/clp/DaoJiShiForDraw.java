package com.bn.clp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import static com.bn.clp.Constant.*;
import android.opengl.GLES20;
import com.bn.core.MatrixState;
import com.bn.st.d2.MyActivity;

import static com.bn.clp.MyGLSurfaceView.*;

public class DaoJiShiForDraw 
{
	//���־��ο�Ŀ�Ⱥ͸߶�
	float SHUZI_KUANDU=0.5f;
	 
	int DaoJiShiFlag=3;
	 
	float z_Order_Offset=-10;
	
	WenLiJuXing wljx;
	
	public static boolean DAOJISHI_FLAG=true;
	
	public DJSThread djst;
	
	MyActivity ma;
	
	MyGLSurfaceView mgsv;
	
	public DaoJiShiForDraw(int mProgram,MyActivity ma,MyGLSurfaceView mgsv)
	{
		wljx=new WenLiJuXing
		(
			SHUZI_KUANDU*5,
			SHUZI_KUANDU*5
		);
		
		this.ma=ma;
		
		this.mgsv=mgsv;
		
		djst=new DJSThread();
		
		wljx.initShader(mProgram);
	}
	
	public void drawSelf(int texId)
	{
		if(DaoJiShiFlag==3)
		{			
			MatrixState.pushMatrix();
			MatrixState.translate(0, 0, z_Order_Offset);
			MatrixState.rotate(90, 1, 0, 0);
			wljx.drawSelf(texId,0);
			MatrixState.popMatrix();
		}
		else if(DaoJiShiFlag==2)
		{
			MatrixState.pushMatrix();
			MatrixState.translate(0, 0, z_Order_Offset);
			MatrixState.rotate(90, 1, 0, 0);
			wljx.drawSelf(texId,1);
			MatrixState.popMatrix();
		}
		else if(DaoJiShiFlag==1)
		{
			MatrixState.pushMatrix();
			MatrixState.translate(0, 0, z_Order_Offset);
			MatrixState.rotate(90, 1, 0, 0);
			wljx.drawSelf(texId,2);
			MatrixState.popMatrix();
		}
		else if(DaoJiShiFlag==0)
		{
			MatrixState.pushMatrix();
			MatrixState.translate(0, 0, z_Order_Offset);
			MatrixState.rotate(90, 1, 0, 0);
			wljx.drawSelf(texId,3);
			MatrixState.popMatrix();
		}		 
	}	
	
	//��������ڲ���
	class WenLiJuXing
	{
		int mProgram;//�Զ�����Ⱦ������ɫ������id 
	    int muMVPMatrixHandle;//�ܱ任��������id   
	    int muMMatrixHandle;//λ�á���ת�任����
	    int maCameraHandle; //�����λ����������id  
	    int maPositionHandle; //����λ����������id  
	    int maNormalHandle; //���㷨������������id  
	    int maTexCoorHandle; //��������������������id  
	    int maSunLightLocationHandle;//��Դλ����������id  
		
	    private FloatBuffer   mVertexBuffer;//�����������ݻ���
	    private FloatBuffer   mTextureBuffer[];//������ɫ���ݻ���
	    int vCount;//��������
	    int texId;//����Id
			
	    public WenLiJuXing(float width,float height){//�����ߺ�������������
	    	//�����������ݵĳ�ʼ��================begin============================
	        vCount=6;//ÿ���������������Σ�ÿ��������3������        
	        float vertices[]=
	        {
        		-width/2,0,-height/2,
        		-width/2,0,height/2,
        		width/2,0,height/2,
        		
        		-width/2,0,-height/2,
        		width/2,0,height/2,
        		width/2,0,-height/2
	        };
	        //���������������ݻ���
	        //vertices.length*4����Ϊһ�������ĸ��ֽ�
	        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
	        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
	        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
	        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
	        mVertexBuffer.position(0);//���û�������ʼλ��
	        
	        float[][] texTures=new float[][]
           	{
       			{
       				0.41f,0, 0.41f,1, 0.615f,1,
       				0.41f,0, 0.615f,1, 0.615f,0 
       			},
       			{
       				0.2f,0, 0.2f,1, 0.415f,1,
       				0.2f,0, 0.415f,1, 0.415f,0
       			},
       			{
       				0,0, 0,1, 0.2f,1,
       				0,0, 0.2f,1, 0.2f,0
       			},
       			{
       				0.62f,0, 0.62f,1, 1,1,
       				0.62f,0, 1,1, 1,0
       			}
           	};
	        
	        mTextureBuffer=new FloatBuffer[4];
	        for(int i=0;i<texTures.length;i++)
	        {
	        	//���������������ݻ���
		        ByteBuffer tbb = ByteBuffer.allocateDirect(texTures[i].length*4);
		        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
		        mTextureBuffer[i]= tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
		        mTextureBuffer[i].put(texTures[i]);//�򻺳����з��붥����ɫ����
		        mTextureBuffer[i].position(0);//���û�������ʼλ��
		        //���ض�����ɫ���Ľű�����
	        }
	    }
		//��ʼ����ɫ����initShader����
	    public void initShader(int mProgram)
	    {
	        this.mProgram=mProgram; 
	        //��ȡ�����ж���λ����������id  
	        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
	        //��ȡ�����ж�������������������id  
	        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
	        //��ȡ�������ܱ任��������id
	        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
	    }
		public void drawSelf(int texId,int number)
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
	               mTextureBuffer[number]
	        );   
	        //������λ����������
	        GLES20.glEnableVertexAttribArray(maPositionHandle);  
	        GLES20.glEnableVertexAttribArray(maTexCoorHandle);  
	        //������
	        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
	        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
	        //�����������
	        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
		}
	}
	
	class DJSThread extends Thread
	{
		
		public DJSThread()
		{
			this.setName("DJSThread");
		}
		
		public void run()
		{
			while(DAOJISHI_FLAG)
			{
				//����Ϸ��ʼʱ������ʱ3ʱ����һ��
				if(DaoJiShiFlag==3&&z_Order_Offset==-10&&SoundEffectFlag)
				{
					ma.shengyinBoFang(5, 0);
				}
				z_Order_Offset=z_Order_Offset+0.3f;
				if(z_Order_Offset>-5)
				{
					z_Order_Offset=-10;
					DaoJiShiFlag=DaoJiShiFlag-1; 
					
					//����ͼʱ������ʱ2��1������һ��
					if(DaoJiShiFlag>0&&SoundEffectFlag)
					{
						ma.shengyinBoFang(5, 0);
					}//����ʱΪ0ʱ�����ſ��Կ���������
					else if(DaoJiShiFlag==0&&SoundEffectFlag)
					{
						ma.shengyinBoFang(6, 0);
					}
				}				
				
				if(DaoJiShiFlag<0)
				{
					DAOJISHI_FLAG=false;
					isJiShi=true; 
					isAllowToClick=true;
					DEGREE_SPAN=2.5f;
					BOAT_A=0.025f;
					MyGLSurfaceView.yachtLeftOrRightAngleA=yachtLeftOrRightAngleValue;
					gameStartTime=System.currentTimeMillis();
					mgsv.kt.start(); 
					mgsv.tc.start();
					mgsv.tfe.start();  
				} 
				try
				{   
					Thread.sleep(80);   
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
