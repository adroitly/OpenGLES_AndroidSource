package com.bn.clp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import android.opengl.GLES20;
import com.bn.core.MatrixState;

//�Ǳ�
public class Castle extends BNDrawer
{
	//�Ǳ��Ϸ�������
	Castle_Up castle_Up;
	//�²��Բ��
	Cylinder cylinder;
	Cylinder cylinder0;
	Cylinder cylinder1;
	Cylinder cylinder2;
	
	//�зֵĽǶ�
	final float ANGLE_SPAN=30;
	final float UNIT_SIZE=3.75f;
	final float r=UNIT_SIZE*1.75f;
	final float R=UNIT_SIZE*2.2f;
	final float mR=UNIT_SIZE*2f;
	//�������ϸ����߶�ֵ
	final float HEIGHT0=UNIT_SIZE*1.7f;
	final float HEIGHT1=UNIT_SIZE*0.18f;
	final float HEIGHT2=UNIT_SIZE*0.18f;
	
	public Castle(int programId)
	{
		cylinder=new Cylinder(programId,mR,mR,ANGLE_SPAN,HEIGHT2);
		//Բ��1
		cylinder0=new Cylinder(programId,r,r,ANGLE_SPAN,HEIGHT0);
		//Բ��2
		cylinder1=new Cylinder(programId,r,R,ANGLE_SPAN,HEIGHT1);
		//Բ��3
		cylinder2=new Cylinder(programId,R,R,ANGLE_SPAN,HEIGHT2);
		//�Ǳ��Ϸ�������
		castle_Up=new Castle_Up(programId,R,ANGLE_SPAN,HEIGHT2);
	}
	//�ܵĻ��Ʒ���drawSelf
	public void drawSelf(int[] texId, int dyFlag)
	{
		MatrixState.pushMatrix();
		cylinder.drawSelf(texId[1]);
		MatrixState.popMatrix();
		//1
		MatrixState.pushMatrix();
		MatrixState.translate(0, HEIGHT2+HEIGHT0, 0);
		cylinder0.drawSelf(texId[0]);
		MatrixState.popMatrix();
		//2 
		MatrixState.pushMatrix();
		MatrixState.translate(0, HEIGHT2+HEIGHT0*2+HEIGHT1, 0);
		cylinder1.drawSelf(texId[1]);
		MatrixState.popMatrix();
		//3
		MatrixState.pushMatrix();
		MatrixState.translate(0, HEIGHT2+HEIGHT0*2+HEIGHT1*2+HEIGHT2, 0);
		cylinder2.drawSelf(texId[1]);
		MatrixState.popMatrix();
		//4
		MatrixState.pushMatrix();
		MatrixState.translate(0, HEIGHT2+HEIGHT0*2+HEIGHT1*2+HEIGHT2*2, 0);
		castle_Up.drawSelf(texId[1]);
		MatrixState.popMatrix();
	} 
	
	//�ڲ��ࡪ��Բ��
	private class Cylinder
	{
		//�Զ���Shader���������
		int mProgram;
		//�ܱ任���������id
		int muMVPMatrixHandle;
		//�������Ե�����id
		int maPositionHandle;
		//�����������������id
		int maTexCoorHandle;
		
		//�����������ݻ���
		FloatBuffer mVertexBuffer;
		//���������������ݻ���
		FloatBuffer mTexCoorBuffer;
		int vCount=0;//��������
		
		//RΪԲ���ײ��İ뾶��rΪԲ���ϲ��İ뾶��angle_span��ʾ�����зֵĽǶ�
		public Cylinder(int programId,float R,float r,float angle_span,float height)
		{
			initVertexData(R,r,angle_span,height);
			initShader(programId);
		}
		//��ʼ���������ݵ�initVertexData����
		public void initVertexData(float R,float r,float angle_span,float height)
		{
			List<Float> tempList=new ArrayList<Float>();
			for(float vAngle=0;vAngle<360;vAngle=vAngle+angle_span)
			{
				float x0=(float) (r*Math.cos(Math.toRadians(vAngle)));
				float y0=height; 
				float z0=(float) (-r*Math.sin(Math.toRadians(vAngle)));
				
				float x1=(float) (R*Math.cos(Math.toRadians(vAngle))); 
				float y1=-height;
				float z1=(float) (-R*Math.sin(Math.toRadians(vAngle)));
				
				float x2=(float) (R*Math.cos(Math.toRadians(vAngle+angle_span)));
				float y2=-height;
				float z2=(float) (-R*Math.sin(Math.toRadians(vAngle+angle_span)));
				
				float x3=(float) (r*Math.cos(Math.toRadians(vAngle+angle_span)));
				float y3=height;
				float z3=(float) (-r*Math.sin(Math.toRadians(vAngle+angle_span)));
				
				tempList.add(x0); tempList.add(y0); tempList.add(z0);
				tempList.add(x1); tempList.add(y1); tempList.add(z1);
				tempList.add(x3); tempList.add(y3); tempList.add(z3);

				tempList.add(x3); tempList.add(y3); tempList.add(z3);
				tempList.add(x1); tempList.add(y1); tempList.add(z1);
				tempList.add(x2); tempList.add(y2); tempList.add(z2);
			}
			vCount=tempList.size()/3;//��������
			float[] vertex=new float[tempList.size()];
			for(int i=0;i<tempList.size();i++)
			{
				vertex[i]=tempList.get(i);
			}
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertex.length*4);
			vbb.order(ByteOrder.nativeOrder());
			mVertexBuffer=vbb.asFloatBuffer();
			mVertexBuffer.put(vertex);
			mVertexBuffer.position(0);
			
			float[] texcoor=generateTexCoor((int)(360/angle_span),1,1,1);
			ByteBuffer tbb=ByteBuffer.allocateDirect(texcoor.length*4);
			tbb.order(ByteOrder.nativeOrder());
			mTexCoorBuffer=tbb.asFloatBuffer();
			mTexCoorBuffer.put(texcoor);
			mTexCoorBuffer.position(0);
		}
		//��ʼ����ɫ�������initShader����
		public void initShader(int programId)
		{
			mProgram=programId;
			//��ö����������ݵ�����
			maPositionHandle=GLES20.glGetAttribLocation(mProgram, "aPosition");
			//�����������������id
			maTexCoorHandle=GLES20.glGetAttribLocation(mProgram, "aTexCoor");
			muMVPMatrixHandle=GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		}
		//�Զ���Ļ��Ʒ���
		public void drawSelf(int texId)
		{
			//ʹ��ĳ��ָ����Shader����
			GLES20.glUseProgram(mProgram);
			//�����ձ任�����뵽Shader������
			GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
			//������������
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
	    public float[] generateTexCoor(int bw,int bh,float width,float height)
	    {
	    	float[] result=new float[bw*bh*6*2]; 
	    	float sizew=width/bw;//����
	    	float sizeh=height/bh;//����
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
	//�����ʾ�Ǳ��ϲ�Ĳ���
	private class Castle_Up
	{
		//�Զ���Shader���������
		int mProgram;
		//�ܱ任���������id
		int muMVPMatrixHandle;
		//�������Ե�����id
		int maPositionHandle;
		//�����������������id
		int maTexCoorHandle;
		
		//�����������ݻ���
		FloatBuffer mVertexBuffer;
		//���������������ݻ���
		FloatBuffer mTexCoorBuffer;
		int vCount=0;//��������
		
		//RΪԲ���ײ��İ뾶��rΪԲ���ϲ��İ뾶��angle_span��ʾ�����зֵĽǶ�
		public Castle_Up(int programId,float R,float angle_span,float height)
		{
			initVertexData(R,angle_span,height);
			initShader(programId);
		}
		//��ʼ���������ݵ�initVertexData����
		public void initVertexData(float R,float angle_span,float height)
		{
			List<Float> vertexList=new ArrayList<Float>();
			for(float vAngle=0;vAngle<360;vAngle=vAngle+angle_span)
			{
				
				float tempX0=(float) (R*Math.cos(Math.toRadians(vAngle)));
				float tempZ0=(float) (-R*Math.sin(Math.toRadians(vAngle)));
				
				float tempX1=(float) (R*Math.cos(Math.toRadians(vAngle+angle_span)));
				float tempZ1=(float) (-R*Math.sin(Math.toRadians(vAngle+angle_span)));
				//����֮��ļ��ƽ��3��
				float tempX=(tempX1-tempX0)/3;
				float tempZ=(tempZ1-tempZ0)/3;
				
				float x0=tempX0;
				float y0=height;
				float z0=tempZ0;
				
				float x1=tempX0; 
				float y1=-height;
				float z1=tempZ0;
				
				float x2=tempX0+tempX;
				float y2=height;
				float z2=tempZ0+tempZ;
				
				float x3=tempX0+tempX;
				float y3=-height;
				float z3=tempZ0+tempZ;
				
				float x4=tempX0+tempX*2;
				float y4=height;
				float z4=tempZ0+tempZ*2;
				
				float x5=tempX0+tempX*2;
				float y5=-height;
				float z5=tempZ0+tempZ*2;
				
				float x6=tempX1;
				float y6=height;
				float z6=tempZ1;
				
				float x7=tempX1;
				float y7=-height;
				float z7=tempZ1;
				//��һ��������
				vertexList.add(x0); vertexList.add(y0); vertexList.add(z0);
				vertexList.add(x1); vertexList.add(y1); vertexList.add(z1);
				vertexList.add(x3); vertexList.add(y3); vertexList.add(z3);
				//�ڶ���������
				vertexList.add(x0); vertexList.add(y0); vertexList.add(z0);
				vertexList.add(x3); vertexList.add(y3); vertexList.add(z3);
				vertexList.add(x2); vertexList.add(y2); vertexList.add(z2);
				//������������
				vertexList.add(x4); vertexList.add(y4); vertexList.add(z4);
				vertexList.add(x5); vertexList.add(y5); vertexList.add(z5);
				vertexList.add(x7); vertexList.add(y7); vertexList.add(z7);
				//���ĸ�������
				vertexList.add(x4); vertexList.add(y4); vertexList.add(z4);
				vertexList.add(x7); vertexList.add(y7); vertexList.add(z7);
				vertexList.add(x6); vertexList.add(y6); vertexList.add(z6);
			}
			vCount=vertexList.size()/3;
			System.out.println("vCount="+vCount);
			float[] vertex=new float[vertexList.size()];
			for(int i=0;i<vertexList.size();i++)
			{
				vertex[i]=vertexList.get(i);
			}
			
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertex.length*4);
			vbb.order(ByteOrder.nativeOrder());
			mVertexBuffer=vbb.asFloatBuffer();
			mVertexBuffer.put(vertex);
			mVertexBuffer.position(0);
			
			float[] texcoor=generateTexCoor((int)(360/angle_span),1,1,1);
			ByteBuffer tbb=ByteBuffer.allocateDirect(texcoor.length*4);
			tbb.order(ByteOrder.nativeOrder());
			mTexCoorBuffer=tbb.asFloatBuffer();
			mTexCoorBuffer.put(texcoor);
			mTexCoorBuffer.position(0);
		}
		//��ʼ����ɫ�������initShader����
		public void initShader(int programId)
		{
			mProgram=programId;
			//��ö����������ݵ�����
			maPositionHandle=GLES20.glGetAttribLocation(mProgram, "aPosition");
			//�����������������id
			maTexCoorHandle=GLES20.glGetAttribLocation(mProgram, "aTexCoor");
			muMVPMatrixHandle=GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		}
		//�Զ���Ļ��Ʒ���
		public void drawSelf(int texId)
		{
			//ʹ��ĳ��ָ����Shader����
			GLES20.glUseProgram(mProgram);
			//�����ձ任�����뵽Shader������
			GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
			//Ϊ������������
			GLES20.glVertexAttribPointer
			(
				maPositionHandle, 
				3, 
				GLES20.GL_FLOAT, 
				false, 
				3*4, 
				mVertexBuffer
			);
			//Ϊ����������������
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
	    public float[] generateTexCoor(int bw,int bh,float width,float height)
	    {
	    	float[] result=new float[bw*bh*12*2]; 
	    	float sizew=width/bw;//����
	    	float sizeh=height/bh;//����
	    	int c=0;
	    	for(int i=0;i<bh;i++)
	    	{
	    		for(int j=0;j<bw;j++)
	    		{
	    			//ÿ����һ�����Σ������������ι��ɣ��������㣬12����������
	    			float s=j*sizew;
	    			float t=i*sizeh;
	    			//��һ��������
	    			result[c++]=s;
	    			result[c++]=t;
	    			
	    			result[c++]=s;
	    			result[c++]=t+sizeh;
	    			
	    			result[c++]=s+sizew/3;
	    			result[c++]=t+sizeh;
	    			//�ڶ���������
	    			result[c++]=s;
	    			result[c++]=t;
	    			
	    			result[c++]=s+sizew/3;
	    			result[c++]=t+sizeh;
	    			
	    			result[c++]=s+sizew/3;
	    			result[c++]=t;
	    			//������������
	    			result[c++]=s+sizew*2/3;
	    			result[c++]=t;
	    			
	    			result[c++]=s+sizew*2/3;
	    			result[c++]=t+sizeh;
	    			
	    			result[c++]=s+sizew;
	    			result[c++]=t+sizeh;
	    			//���ĸ�������
	    			result[c++]=s+sizew*2/3;
	    			result[c++]=t;
	    			
	    			result[c++]=s+sizew;
	    			result[c++]=t+sizeh;
	    			
	    			result[c++]=s+sizew;
	    			result[c++]=t;
	    		}
	    	}
	    	return result;
	    }
	}
}