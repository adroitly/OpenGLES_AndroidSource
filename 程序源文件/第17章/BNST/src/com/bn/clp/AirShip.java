package com.bn.clp;

import static com.bn.clp.Constant.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import com.bn.core.MatrixState;
import android.opengl.GLES20;
   
//����Ϊ��ͧ��
public class AirShip extends BNDrawer
{     
	static float feitingX;//��ͧ�������й켣���ĵ�λ��
	static float feitingY; 
	static float feitingZ;
	
	final static float NORMAL_SIZE=5.0f;
	static float A=UNIT_SIZE-10;//��ͧ�˶������a�뾶��������x����
	static float B=UNIT_SIZE-10;//��ͧ�˶������b�뾶��������z����
	
	static float angle=360;//��ͧ��ǰ�Ƕ� 
	
	static float angle_Rotate=0;//��ͧ�Ŷ��Ƕ�
	
	static float height=1f;//��ͧ�����Ŷ��߲�
	static float angle_Y=270;//�������ߵ�ǰ֡�Ƕ� 
	
	public static GoThread goThread;//�˶��߳�
	DrawSpheroid bodyback;
	DrawSpheroid bodyhead;
	DrawSpheroid cabin;   
	DrawWeiba weiba;
	//��ͧ������Բ�����������
	final static float BODYBACK_A=3f*NORMAL_SIZE;
	final static float BODYBACK_B=1f*NORMAL_SIZE;
	final static float BODYBACK_C=1f*NORMAL_SIZE;
	//��ͧ����С��Բ�����������
	final static float BODYHEAD_A=2f*NORMAL_SIZE;  
	final static float BODYHEAD_B=1f*NORMAL_SIZE;
	final static float BODYHEAD_C=1f*NORMAL_SIZE;
	
	final static float WEIBA_WIDTH=0.3f*NORMAL_SIZE;
	final static float WEIBA_HEIGHT=0.3f*NORMAL_SIZE;
	
	final static float CABIN_A=0.4f*NORMAL_SIZE;
	final static float CABIN_B=0.2f*NORMAL_SIZE;
	final static float CABIN_C=0.2f*NORMAL_SIZE;
	
	public AirShip(int programId)
	{
		bodyback=new DrawSpheroid(programId,BODYBACK_A,BODYBACK_B,BODYBACK_C,30,-90,90,-90,90);
		bodyhead=new DrawSpheroid(programId,BODYHEAD_A,BODYHEAD_B,BODYHEAD_C,30,-90,90,-90,90);
		cabin=new DrawSpheroid(programId,CABIN_A,CABIN_B,CABIN_C,30,0,360,-90,90);
		weiba=new DrawWeiba(programId,WEIBA_WIDTH,WEIBA_HEIGHT);
		goThread=new GoThread();
		goThread.start();
	}
	
	//���Ʒ���
	public void drawSelf(int[] texId, int dyFlag)
	{
		feitingX=(float) (A*Math.cos(Math.toRadians(angle)));
		feitingY=(float) (height*Math.sin(Math.toRadians(angle_Y)));
		feitingZ=(float) (B*Math.sin(Math.toRadians(angle)));
		
		MatrixState.pushMatrix();
        
		MatrixState.translate
        (
    		feitingX, 
    		feitingY, 
    		feitingZ
        ); 
		MatrixState.rotate(angle_Rotate-90, 0, 1, 0);
		
		//��ͧ��
		MatrixState.pushMatrix();
		bodyback.drawSelf(texId[0]);
		MatrixState.popMatrix();
		//��ͧǰ��
		MatrixState.pushMatrix();
		MatrixState.rotate(180, 0, 1, 0);
		MatrixState.rotate(180, 1, 0, 0);   
		bodyhead.drawSelf(texId[0]);
		MatrixState.popMatrix();
		//�²�ķ�ͧ
		MatrixState.pushMatrix();
		
		MatrixState.translate(BODYHEAD_C*0.2f, -BODYHEAD_B, 0);
		cabin.drawSelf(texId[0]);
		MatrixState.popMatrix();
		
		//β���� 
		//�ϲ�
		MatrixState.pushMatrix();
		MatrixState.translate(BODYBACK_A*0.8f, BODYBACK_C*0.7f, 0);
		weiba.drawSelf(texId[1]);
		MatrixState.popMatrix();
		//ǰ��
		MatrixState.pushMatrix();
		MatrixState.translate(BODYBACK_A*0.8f, 0, BODYBACK_B*0.7f);
		MatrixState.rotate(90, 1, 0, 0);
		weiba.drawSelf(texId[1]);
		MatrixState.popMatrix();
		//���
		MatrixState.pushMatrix();
		MatrixState.translate(BODYBACK_A*0.8f, 0, -BODYBACK_B*0.7f);
		MatrixState.rotate(-90, 1, 0, 0);
		weiba.drawSelf(texId[1]);
		MatrixState.popMatrix();
		//�²�
		MatrixState.pushMatrix();
		MatrixState.translate(BODYBACK_A*0.8f, -BODYBACK_C*0.7f, 0);
		MatrixState.rotate(180, 1, 0, 0);
		weiba.drawSelf(texId[1]);
		MatrixState.popMatrix();
		
        MatrixState.popMatrix();
	} 
	
	
	
	//���Ʒ�ͧ���������
	private class DrawSpheroid
	{
		//�Զ�����Ⱦ���ߵ�id
		int mProgram;
		//�ܱ仯�������õ�id
		int muMVPMatrixHandle;
		//����λ����������id
		int maPositionHandle;
		//��������������������id
		int maTexCoorHandle;
		
		//�������ݻ���������������ݻ���
		FloatBuffer mVertexBuffer;
		FloatBuffer mTexCoorBuffer;
		//��������
		int vCount=0;
		
		public DrawSpheroid
		(
			int programId,float a,float b,float c,float angleSpan,
			float hAngleBegin,float hAngleOver,float vAngleBegin,float vAngleOver
		)
		{
			initVertexData(a,b,c,angleSpan,hAngleBegin,hAngleOver,vAngleBegin,vAngleOver);
			initShader(programId);
		}
		//��ʼ�Ķ�������
		public void initVertexData
		(
			float a,float b,float c,
			float angleSpan,
			float hAngleBegin,float hAngleOver,
			float vAngleBegin,float vAngleOver
		)
		{
			ArrayList<Float> alVertix=new ArrayList<Float>();//��Ŷ�������
			for(float vAngle=vAngleBegin;vAngle<vAngleOver;vAngle=vAngle+angleSpan)//��ֱ����angleSpan��һ��
	        {
	        	for(float hAngle=hAngleBegin;hAngle<hAngleOver;hAngle=hAngle+angleSpan)//ˮƽ����angleSpan��һ��
	        	{//����������һ���ǶȺ�����Ӧ�Ĵ˵��������ϵ�����    		
	        		float x0=(float)(a*Math.cos(Math.toRadians(vAngle))*Math.cos(Math.toRadians(hAngle)));
	        		float y0=(float)(b*Math.cos(Math.toRadians(vAngle))*Math.sin(Math.toRadians(hAngle)));
	        		float z0=(float)(c*Math.sin(Math.toRadians(vAngle)));
	        		
	        		float x1=(float)(a*Math.cos(Math.toRadians(vAngle))*Math.cos(Math.toRadians(hAngle+angleSpan)));
	        		float y1=(float)(b*Math.cos(Math.toRadians(vAngle))*Math.sin(Math.toRadians(hAngle+angleSpan)));
	        		float z1=(float)(c*Math.sin(Math.toRadians(vAngle)));
	        		
	        		float x2=(float)(a*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.cos(Math.toRadians(hAngle+angleSpan)));
	        		float y2=(float)(b*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.sin(Math.toRadians(hAngle+angleSpan)));
	        		float z2=(float)(c*Math.sin(Math.toRadians(vAngle+angleSpan)));
	        		
	        		float x3=(float)(a*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.cos(Math.toRadians(hAngle)));
	        		float y3=(float)(b*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.sin(Math.toRadians(hAngle)));
	        		float z3=(float)(c*Math.sin(Math.toRadians(vAngle+angleSpan)));
	        		
	        		//�����������XYZ��������Ŷ��������ArrayList        		
	        		alVertix.add(x1);alVertix.add(y1);alVertix.add(z1);  
	        		alVertix.add(x3);alVertix.add(y3);alVertix.add(z3);
	        		alVertix.add(x0);alVertix.add(y0);alVertix.add(z0);
	        		      		
	        		alVertix.add(x1);alVertix.add(y1);alVertix.add(z1);
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
	        //���������������ݻ���
	        //vertices.length*4����Ϊһ�������ĸ��ֽ�
	        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
	        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
	        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
	        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
	        mVertexBuffer.position(0);//���û�������ʼλ��
	        
	    	//��ȡ�з���ͼ����������
	    	float[] texCoorArray= 
	         generateTexCoor
	    	 (
	    			 (int)((hAngleOver-hAngleBegin)/angleSpan), //����ͼ�зֵ�����
	    			 (int)((vAngleOver-vAngleBegin)/angleSpan)  //����ͼ�зֵ����� 
	    	);
			
			ByteBuffer tbb=ByteBuffer.allocateDirect(texCoorArray.length*4);
			tbb.order(ByteOrder.nativeOrder());
			mTexCoorBuffer=tbb.asFloatBuffer();
			mTexCoorBuffer.put(texCoorArray);
			mTexCoorBuffer.position(0);
		}
		//��ʼ����ɫ�������initShader����
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
		//ʵ�ʵĻ��Ʒ���
		public void drawSelf(int texId)
		{
			//�ƶ�ʹ��ĳ��shader����
	   	 	GLES20.glUseProgram(mProgram); 
	        //�����ձ任������shader����
	        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
			//Ϊ����ָ������λ������
			GLES20.glVertexAttribPointer
			(
				maPositionHandle, 
				3, 
				GLES20.GL_FLOAT, 
				false, 
				3*4, 
				mVertexBuffer
			);
			//����������������
			GLES20.glVertexAttribPointer
			(
				maTexCoorHandle, 
				2, 
				GLES20.GL_FLOAT, 
				false, 
				2*4, 
				mTexCoorBuffer
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
	    			
	    			result[c++]=s+sizew;
	    			result[c++]=t;
	    			
	    			result[c++]=s;
	    			result[c++]=t+sizeh;
	    			
	    			result[c++]=s;
	    			result[c++]=t;
	    			
	    			result[c++]=s+sizew;
	    			result[c++]=t;
	    			
	    			result[c++]=s+sizew;
	    			result[c++]=t+sizeh;
	    			
	    			result[c++]=s;
	    			result[c++]=t+sizeh;
	    		}
	    	}
	    	return result;
	    }  
	}
	//���Ʒ�ͧ��β��
	private class DrawWeiba
	{
		//�Զ�����Ⱦ���ߵ�id
		int mProgram;
		//�ܱ仯�������õ�id
		int muMVPMatrixHandle;
		//����λ����������id
		int maPositionHandle;
		//��������������������id
		int maTexCoorHandle;
		
		//�������ݻ���������������ݻ���
		FloatBuffer mVertexBuffer;
		FloatBuffer mTexCoorBuffer;
		//��������
		int vCount=0;
		
		public DrawWeiba(int programId,float width,float height)
		{
			initVertexData(width,height);
			initShader(programId);
		}
		//��ʼ�Ķ������ݵ�initVertexData����
		public void initVertexData(float width,float height)
		{
			float[] vertices=new float[]
	        {
				-width,height,0,
				-width*1.5f,-height,0,
				width,-height,0,
				
				-width,height,0,
				width,-height,0,
				width,height,0,
				
				-width,height,0,
				width,height,0,
				width,-height,0,
				
				-width,height,0,
				width,-height,0,
				-width*1.5f,-height,0,
	        };
			vCount=12;
			ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
	        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
	        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
	        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
	        mVertexBuffer.position(0);//���û�������ʼλ��
	        
	    	//��ȡ�з���ͼ����������
	    	float[] texCoorArray=new float[]
            {
	    		0.2f,0,  0,1,  1,1,
	    		0.2f,0,  1,1,  1,0,
	    		
	    		0.2f,0,  1,0,  1,1,
	    		0.2f,0,  1,1,  0,1
            };
			
			ByteBuffer tbb=ByteBuffer.allocateDirect(texCoorArray.length*4);
			tbb.order(ByteOrder.nativeOrder());
			mTexCoorBuffer=tbb.asFloatBuffer();
			mTexCoorBuffer.put(texCoorArray);
			mTexCoorBuffer.position(0);
		}
		//��ʼ����ɫ�������initShader����
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
		//ʵ�ʵĻ��Ʒ���
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
			//����������������
			GLES20.glVertexAttribPointer
			(
				maTexCoorHandle, 
				2, 
				GLES20.GL_FLOAT, 
				false, 
				2*4, 
				mTexCoorBuffer
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
	//��ͧ�˶��߳�
	public class GoThread extends Thread 
	{	
		public GoThread()
		{
			this.setName("GoThread");
		}
		
		public void run()
		{
			while(Constant.threadFlag)
			{					 
				angle=angle-0.2f;
				angle_Y=angle_Y+30;
				angle_Rotate=angle_Rotate+0.2f;
				if(angle<=0)
				{
					angle=360;
				}
				if(angle_Y>=360)
				{
					angle_Y=0;
				}
				if(angle_Rotate>=360)
				{
					angle_Rotate=0;
				}
				try
				{
					Thread.sleep(200);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}