package com.bn.clp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import android.opengl.GLES20;
import com.bn.core.MatrixState;
 
//��ͨ��
public class TrafficCylinder extends KZBJDrawer
{
	//��ͨ���ϲ��ԲƬ
	Pedestal circle;
	//��ͨ���Ϸ���Բ��
	Cylinder cylinder;
	//��ͨ���²�ĵ�
	Pedestal pedestal;
	//��ͨ���²������
	Cylinder cld_lj;
	//�зֵĽǶ�
	final float ANGLE_SPAN=20;
	final float UNIT_SIZE=1.0f;
	final float HEIGHT=0.2f;
	//�����Ƭ�зֵĽǶ�
	float SPAN=60;
	public TrafficCylinder(int programId,float R,float r,float R2)
	{
		cylinder=new Cylinder(programId,R,r,ANGLE_SPAN,UNIT_SIZE);
		cld_lj=new Cylinder(programId,R2,R2,60,HEIGHT); 
		pedestal=new Pedestal(programId,R2,SPAN); 
		circle=new Pedestal(programId,r,ANGLE_SPAN);
	}
	//�ܵĻ��Ʒ���drawSelf
	public void drawSelf(int texId)
	{
		//����Բ���ϲ��ԲƬ
		MatrixState.pushMatrix();
		MatrixState.translate(0, UNIT_SIZE, 0);
		circle.drawSelf(texId);
		MatrixState.popMatrix();
		//���ƽ�ͨ����Բ������
		cylinder.drawSelf(texId);
		//���ƽ�ͨ���·�����������
		MatrixState.pushMatrix();
		MatrixState.translate(0, -UNIT_SIZE, 0);
		pedestal.drawSelf(texId);
		MatrixState.popMatrix();
		//���ƽ�ͨ���·������Ĳ���
		MatrixState.pushMatrix();
		MatrixState.translate(0, -UNIT_SIZE-HEIGHT, 0);
		cld_lj.drawSelf(texId);
		MatrixState.popMatrix();
		//���ƽ�ͨ���·�����������
		MatrixState.pushMatrix();
		MatrixState.translate(0, -UNIT_SIZE-HEIGHT*2, 0);
		MatrixState.rotate(180, 1, 0, 0);
		pedestal.drawSelf(texId);
		MatrixState.popMatrix();
	}
	//�ڲ��ࡪ��Բ��
	private class Cylinder
	{
		//�Զ�����ɫ�����������
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
		//��ʼ���������ݵķ���
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
		//��ʼ����ɫ������ķ���
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
			//���붥����������
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
	//�·��ĵ���
	private class Pedestal
	{
		//�Զ�����ɫ�����������
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
		
		//RΪԲ���ײ��İ뾶��rΪԲ���ϲ��İ뾶
		public Pedestal(int programId,float R,float span)
		{
			initVertexData(R,span);
			initShader(programId);
		}
		//��ʼ���������ݵ�initVertexData����
		public void initVertexData(float R,float span)
		{
			List<Float> alist=new ArrayList<Float>();
			for(float vAngle=0;vAngle<360;vAngle=vAngle+span)
			{
				float x0=0;
				float y0=0;
				float z0=0;
				
				float x1=(float) (R*Math.cos(Math.toRadians(vAngle)));
				float y1=0;
				float z1=(float) (-R*Math.sin(Math.toRadians(vAngle)));
				
				float x2=(float) (R*Math.cos(Math.toRadians(vAngle+span)));
				float y2=0;
				float z2=(float) (-R*Math.sin(Math.toRadians(vAngle+span)));
				
				alist.add(x0); alist.add(y0); alist.add(z0);
				alist.add(x1); alist.add(y1); alist.add(z1);
				alist.add(x2); alist.add(y2); alist.add(z2);
			}
			vCount=alist.size()/3;
			float[] vertex=new float[alist.size()];
			for(int i=0;i<alist.size();i++)
			{
				vertex[i]=alist.get(i);
			}
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertex.length*4);
			vbb.order(ByteOrder.nativeOrder());
			mVertexBuffer=vbb.asFloatBuffer();
			mVertexBuffer.put(vertex);
			mVertexBuffer.position(0);
			
			float[] texcoor=generateTexCoor(span,1,1);
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
			//���붥����������
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
		//�Զ��з����������������ķ���,triangleSize��ʾ�����зֵ������η���
	    public float[] generateTexCoor(float angle_span,float width,float height)
	    {
	    	float[] result=new float[(int) (360/angle_span*3*2)];
	    	int c=0;
	    	for(float i=0;i<360;i=i+angle_span)
	    	{
	    		result[c++]=0.5f*width;
	    		result[c++]=0.5f*height;
	    		
	    		result[c++]=(float) (0.5f+0.5f*Math.cos(Math.toRadians(i)))*width;
	    		result[c++]=(float) (0.5f-0.5f*Math.sin(Math.toRadians(i)))*height;
	    		
	    		result[c++]=(float) (0.5f+0.5f*Math.cos(Math.toRadians(i+angle_span)))*width;
	    		result[c++]=(float) (0.5f-0.5f*Math.sin(Math.toRadians(i+angle_span)))*height;
	    	}
	    	return result;
	    }
	}
}