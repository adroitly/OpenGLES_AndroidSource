package com.bn.clp;
import static com.bn.clp.Constant.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import android.opengl.GLES20;
import com.bn.core.MatrixState;

public class Tunnel extends BNDrawer
{
	//��λ����
	float UNIT_SIZE=15f;
	PipeLine ppl;//����·���ͨ��
	Mountion mountion;
	public Tunnel(int mProgramId0,int mProgramId1,float[][] yArray,int rows,int cols)
	{
		ppl=new PipeLine(mProgramId0);
		mountion=new Mountion(mProgramId1,yArray,rows,cols);
	}
	public void drawSelf(int[] texId,int udyflag)
	{
		//texId[0]Ϊ���������Id��texId[1]��ʾСɽ������id��texId[2]��ʾСɽ�Ϸ���ʯ������id
		ppl.drawSelf(texId[0]);//�����·�ͨ��
		mountion.drawSelf(texId[1],texId[2]);
	}
	
	//����·���ͨ��
	private class PipeLine
	{
		//�Զ�����Ⱦ������ɫ����id
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
		
		final float R=18f;
		float height=UNIT_SIZE*3f;
		final float ANGLE_SPAN=18;//�ָ�Ķ���
		public PipeLine(int programId) 
		{
			initVertexData();
			initShader(programId);
		}
		//��ʼ���������ݵķ���
		public void initVertexData()
		{
			List<Float> tempList=new ArrayList<Float>();
			for(float vAngle=180;vAngle>0;vAngle=vAngle-ANGLE_SPAN)
			{
				float x0=(float) (R*Math.cos(Math.toRadians(vAngle)));
				float y0=(float) (R*Math.sin(Math.toRadians(vAngle)));
				float z0=height;
				
				float x1=(float) (R*Math.cos(Math.toRadians(vAngle)));
				float y1=(float) (R*Math.sin(Math.toRadians(vAngle)));
				float z1=-height;
				
				float x2=(float) (R*Math.cos(Math.toRadians(vAngle-ANGLE_SPAN)));
				float y2=(float) (R*Math.sin(Math.toRadians(vAngle-ANGLE_SPAN)));
				float z2=-height;
				
				float x3=(float) (R*Math.cos(Math.toRadians(vAngle-ANGLE_SPAN)));
				float y3=(float) (R*Math.sin(Math.toRadians(vAngle-ANGLE_SPAN)));
				float z3=height;
				
				tempList.add(x0);tempList.add(y0);tempList.add(z0);
				tempList.add(x1);tempList.add(y1);tempList.add(z1);
				tempList.add(x3);tempList.add(y3);tempList.add(z3);
				
				tempList.add(x3);tempList.add(y3);tempList.add(z3);
				tempList.add(x1);tempList.add(y1);tempList.add(z1);
				tempList.add(x2);tempList.add(y2);tempList.add(z2);				
			}
			float[] vertex=new float[tempList.size()];
			for(int i=0;i<tempList.size();i++)
			{
				vertex[i]=tempList.get(i);
			}
			vCount=tempList.size()/3;
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertex.length*4);
			vbb.order(ByteOrder.nativeOrder());
			mVertexBuffer=vbb.asFloatBuffer();
			mVertexBuffer.put(vertex);
			mVertexBuffer.position(0);
			
			float[] texcoor=generateTexCoor((int)(180/ANGLE_SPAN),1,3,3);
			ByteBuffer tbb=ByteBuffer.allocateDirect(texcoor.length*4);
			tbb.order(ByteOrder.nativeOrder());
			mTexCoorBuffer=tbb.asFloatBuffer();
			mTexCoorBuffer.put(texcoor);
			mTexCoorBuffer.position(0);
		}
		
		//��ʼ����ɫ����initShader����
		public void initShader(int programId) 
		{
			//���ڶ�����ɫ����ƬԪ��ɫ����������
	        mProgram = programId;
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
	
	//����ϵ�Сɽ
	private class Mountion
	{
		//�Զ�����Ⱦ���ߵ�id
		int mProgram;
		//�ܱ仯�������õ�id
		int muMVPMatrixHandle;
		//����λ����������id
		int maPositionHandle;
		//��������������������id
		int maTexCoorHandle;
		
		//�ݵص�id
		int sTextureGrassHandle;
		//ʯͷ��id
		int sTextureRockHandle;
		//��ʼxֵ
		int b_YZ_StartYHandle;
		//����
		int b_YZ_YSpanHandle;
		//λ�á���ת�任����
		int muMMatrixHandle;
		//�Ƿ�Ϊ���ɽ�ı�־λ������id
		int sdflagHandle;
		//�˴�flagֵΪ0��ʾ���ɽ��ֵΪ1��ʾΪ��ͨɽ
		private int flag=0;
		
		
		//�������ݻ���������������ݻ���
		FloatBuffer mVertexBuffer;
		FloatBuffer mTexCoorBuffer; 
		//��������
		int vCount=0;
		final float TEMP_UNIT_SIZE_X=6*UNIT_SIZE/15;
		final float TEMP_UNIT_SIZE_Z=6*UNIT_SIZE/15;
		
		public Mountion(int programId,float[][] yArray,int rows,int cols)
		{
			initVertexData(yArray,rows,cols);
			initShader(programId);
		}
		
		//��ʼ�������������ݵ�initVertexData����
	    public void initVertexData(float[][] yArray,int rows,int cols)
	    {
	    	//�����������ݵĳ�ʼ��================begin============================
	    	vCount=cols*rows*2*3;//ÿ���������������Σ�ÿ��������3������   
	        float vertices[]=new float[vCount*3];//ÿ������xyz��������
	        int count=0;//���������
	        for(int j=0;j<rows;j++)
	        {
	        	for(int i=0;i<cols;i++) 
	        	{        		
	        		//���㵱ǰ�������ϲ������ 
	        		float zsx=-TEMP_UNIT_SIZE_X*cols/2+i*TEMP_UNIT_SIZE_X;
	        		float zsz=-TEMP_UNIT_SIZE_Z*rows/2+j*TEMP_UNIT_SIZE_Z;
	        		
	        		vertices[count++]=zsx;
	        		vertices[count++]=yArray[j][i];
	        		vertices[count++]=zsz;
	        		
	        		vertices[count++]=zsx;
	        		vertices[count++]=yArray[j+1][i];
	        		vertices[count++]=zsz+TEMP_UNIT_SIZE_Z;
	        		
	        		vertices[count++]=zsx+TEMP_UNIT_SIZE_X;
	        		vertices[count++]=yArray[j][i+1];
	        		vertices[count++]=zsz;
	        		
	        		vertices[count++]=zsx+TEMP_UNIT_SIZE_X;
	        		vertices[count++]=yArray[j][i+1];
	        		vertices[count++]=zsz;
	        		
	        		vertices[count++]=zsx;
	        		vertices[count++]=yArray[j+1][i];
	        		vertices[count++]=zsz+TEMP_UNIT_SIZE_Z;
	        		
	        		vertices[count++]=zsx+TEMP_UNIT_SIZE_X;
	        		vertices[count++]=yArray[j+1][i+1];
	        		vertices[count++]=zsz+TEMP_UNIT_SIZE_Z;
	        	}
	        }
			
	        //���������������ݻ���
	        //vertices.length*4����Ϊһ�������ĸ��ֽ�
	        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
	        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
	        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
	        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
	        mVertexBuffer.position(0);//���û�������ʼλ��

	        
	        //���������������ݵĳ�ʼ��================begin============================
	        float[] texCoor=generateTexCoor(cols,rows,8,8);
	        //�������������������ݻ���
	        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
	        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
	        mTexCoorBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
	        mTexCoorBuffer.put(texCoor);//�򻺳����з��붥����ɫ����
	        mTexCoorBuffer.position(0);//���û�������ʼλ��
	    }
		
		//��ʼ����ɫ����initShader����
		public void initShader(int programId) 
		{
			//���ڶ�����ɫ����ƬԪ��ɫ����������
	        mProgram = programId;
	        //��ȡ�����ж���λ����������id  
	        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
	        //��ȡ�����ж�������������������id  
	        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
	        //��ȡ�������ܱ任��������id
	        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
	        
	        //����
			//�ݵ�
			sTextureGrassHandle=GLES20.glGetUniformLocation(mProgram, "sTextureGrass");
			//ʯͷ
			sTextureRockHandle=GLES20.glGetUniformLocation(mProgram, "sTextureRock");
			//xλ��
			b_YZ_StartYHandle=GLES20.glGetUniformLocation(mProgram, "b_YZ_StartY");
			//x���
			b_YZ_YSpanHandle=GLES20.glGetUniformLocation(mProgram, "b_YZ_YSpan");
	    	//λ�á���ת�任���������id
	    	muMMatrixHandle=GLES20.glGetUniformLocation(mProgram, "uMMatrix");
	    	sdflagHandle=GLES20.glGetUniformLocation(mProgram, "sdflag");
		}        
		
		//ʵ�ʵĻ��Ʒ���
		public void drawSelf(int texId,int rock_textId)
		{
			//�ƶ�ʹ��ĳ��shader����
	   	 	GLES20.glUseProgram(mProgram); 
	        //�����ձ任������shader����
	        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
	        //��λ�á���ת�任�����뵽Shader������
	        GLES20.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0);
	        GLES20.glUniform1i(sdflagHandle, flag);
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
	        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, rock_textId);
			GLES20.glUniform1i(sTextureGrassHandle, 0);//ʹ��0������
	        GLES20.glUniform1i(sTextureRockHandle, 1); //ʹ��1������
	        
	        //������Ӧ��x����
	        GLES20.glUniform1f(b_YZ_StartYHandle, 0);
	        GLES20.glUniform1f(b_YZ_YSpanHandle, SD_HEIGHT);
	        
	        //�������
            GLES20.glEnable(GLES20.GL_BLEND);  
            //���û������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	        //�����������
	        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
	        //�رջ��
            GLES20.glDisable(GLES20.GL_BLEND); 
		} 
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