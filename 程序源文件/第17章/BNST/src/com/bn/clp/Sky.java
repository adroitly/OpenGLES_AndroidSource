package com.bn.clp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import android.opengl.GLES20;
import com.bn.core.MatrixState;

public class Sky
{
	//�Զ�����Ⱦ��ɫ�����������
	int mProgram;
	//�ܱ任���������
	int muMVPMatrixHandle;
	//�������Ե�����
	int maPositionHandle;
	//���������������Ե�����
	int maTexCoorHandle;
	//�������ݻ����Լ����������������ݻ���
	FloatBuffer mVertexBuffer;
	FloatBuffer mTexCoorBuffer;
	//��������
	int vCount=0;
	
	public Sky(int programId,float radius)
	{
		initVertexData(radius);
		initShader(programId);
	}
	//��ʼ���������ݵķ���
	public void initVertexData(float radius)
	{
		float ANGLE_SPAN=18;
    	float angleV=90;
    	//��ȡ�з���ͼ����������
    	float[] texCoorArray= 
         generateTexCoor 
    	 ( 
    		(int)(360/ANGLE_SPAN), //����ͼ�зֵ�����
    		(int)(angleV/ANGLE_SPAN)  //����ͼ�зֵ�����
    	);
        int tc=0;//�������������
        int ts=texCoorArray.length;//�������鳤��
    	
    	ArrayList<Float> alVertix=new ArrayList<Float>();//��Ŷ��������ArrayList
    	ArrayList<Float> alTexture=new ArrayList<Float>();//������������ArrayList
    	
        for(float vAngle=angleV;vAngle>0;vAngle=vAngle-ANGLE_SPAN)//��ֱ����angleSpan��һ��
        {
        	for(float hAngle=360;hAngle>0;hAngle=hAngle-ANGLE_SPAN)//ˮƽ����angleSpan��һ��
        	{
        		//����������һ���ǶȺ�����Ӧ�Ĵ˵��������ϵ��ı��ζ�������
        		//��������������ı��ε�������
        		
        		double xozLength=radius*Math.cos(Math.toRadians(vAngle));
        		float x1=(float)(xozLength*Math.cos(Math.toRadians(hAngle)));
        		float z1=(float)(xozLength*Math.sin(Math.toRadians(hAngle)));
        		float y1=(float)(radius*Math.sin(Math.toRadians(vAngle)));
        		
        		xozLength=radius*Math.cos(Math.toRadians(vAngle-ANGLE_SPAN));
        		float x2=(float)(xozLength*Math.cos(Math.toRadians(hAngle)));
        		float z2=(float)(xozLength*Math.sin(Math.toRadians(hAngle)));
        		float y2=(float)(radius*Math.sin(Math.toRadians(vAngle-ANGLE_SPAN)));
        		
        		xozLength=radius*Math.cos(Math.toRadians(vAngle-ANGLE_SPAN));
        		float x3=(float)(xozLength*Math.cos(Math.toRadians(hAngle-ANGLE_SPAN)));
        		float z3=(float)(xozLength*Math.sin(Math.toRadians(hAngle-ANGLE_SPAN)));
        		float y3=(float)(radius*Math.sin(Math.toRadians(vAngle-ANGLE_SPAN)));
        		
        		xozLength=radius*Math.cos(Math.toRadians(vAngle));
        		float x4=(float)(xozLength*Math.cos(Math.toRadians(hAngle-ANGLE_SPAN)));
        		float z4=(float)(xozLength*Math.sin(Math.toRadians(hAngle-ANGLE_SPAN)));
        		float y4=(float)(radius*Math.sin(Math.toRadians(vAngle)));   
        		
        		//������һ������
        		alVertix.add(x1);alVertix.add(y1);alVertix.add(z1);
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4); 
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		       		
        		//�����ڶ�������
        		
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);
        		alVertix.add(x3);alVertix.add(y3);alVertix.add(z3); 
        		
        		//��һ������3�������6����������
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);        		
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);
        		//�ڶ�������3�������6����������
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);        		
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);       		
        	}
        } 	
        vCount=alVertix.size()/3;//���������Ϊ����ֵ������1/3����Ϊһ��������3������
        //��alVertix�е�����ֵת�浽һ��float������
        float vertices[]=new float[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
        //�������ƶ������ݻ���
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        
        //�����������껺��
        float textureCoors[]=new float[alTexture.size()];//��������ֵ����
        for(int i=0;i<alTexture.size();i++) 
        {
        	textureCoors[i]=alTexture.get(i);
        }
        ByteBuffer tbb = ByteBuffer.allocateDirect(textureCoors.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer = tbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mTexCoorBuffer.put(textureCoors);//�򻺳����з��붥����ɫ����
        mTexCoorBuffer.position(0);//���û�������ʼλ��
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
	//���Ʒ���
	public void drawSelf(int texId,float x,float z,int dyFlag)
	{
		if(dyFlag==0)//����ʵ��
		{
			MatrixState.pushMatrix();
			MatrixState.translate(x, 0, z);
			realDrawSelf(texId);
			MatrixState.popMatrix();
		}
		else if(dyFlag==1)//���Ƶ�Ӱ
		{
			//ʵ�ʻ���ʱY�����
			final float yTranslate=0;   
			//���о������ʱ�ĵ���ֵ
			float yjx=(0-yTranslate)*2;
			
			//�رձ������
            GLES20.glDisable(GLES20.GL_CULL_FACE);
			MatrixState.pushMatrix();
			MatrixState.translate(x, 0, z);
			MatrixState.translate(0, yjx, 0);
			MatrixState.scale(1, -1, 1);
			realDrawSelf(texId);
			MatrixState.popMatrix();
			//�򿪱������
            GLES20.glEnable(GLES20.GL_CULL_FACE);
		}
	}
	
	
	//�Զ���Ļ��Ʒ���
	public void realDrawSelf(int texId)
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
    			
    			result[c++]=s;
    			result[c++]=t;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s+sizew;
    			result[c++]=t+sizeh;    			
    		}
    	}
    	return result;
    }
}