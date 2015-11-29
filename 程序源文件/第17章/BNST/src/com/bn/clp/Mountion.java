package com.bn.clp;
import static com.bn.clp.Constant.*; 
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;
import com.bn.core.MatrixState;

public class Mountion extends BNDrawer
{
	Mountion_In mountion_in;
	public Mountion(int programId,float[][] yArray,int rows,int cols)
	{
		mountion_in=new Mountion_In(programId,yArray,rows,cols);
	}
	
	@Override
	public void drawSelf(int[] texId,int dyFlag)
	{
		//texId[0]Ϊ��Ƥ����id��texId[1]Ϊʯͷ����id
		mountion_in.drawSelf(texId[0], texId[1]);
	}
	
	//����Сɽ���ڲ���
	private class Mountion_In
	{
		//��λ����
		float UNIT_SIZE=Constant.UNIT_SIZE/15;
		
		//�Զ�����Ⱦ������ɫ�������id
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
		//�Ƿ�Ϊ���ɽ�ı�־λ������id   
		int sdflagHandle;
		//�˴�flagֵΪ0��ʾ���ɽ��ֵΪ1��ʾΪ��ͨɽ
		private int flag=1;
		
		//�������ݻ���������������ݻ���
		FloatBuffer mVertexBuffer;
		FloatBuffer mTexCoorBuffer; 
		//��������
		int vCount=0;
		
		public Mountion_In(int programId,float[][] yArray,int rows,int cols)
		{
			initVertexData(yArray,rows,cols);
			initShader(programId);
		}
		//��ʼ���������ݵ�initVertexData����
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
	        		float zsx=-UNIT_SIZE*cols/2+i*UNIT_SIZE;
	        		float zsz=-UNIT_SIZE*rows/2+j*UNIT_SIZE;
	        		
	        		vertices[count++]=zsx;
	        		vertices[count++]=yArray[j][i];
	        		vertices[count++]=zsz;
	        		
	        		vertices[count++]=zsx;
	        		vertices[count++]=yArray[j+1][i];
	        		vertices[count++]=zsz+UNIT_SIZE;
	        		
	        		vertices[count++]=zsx+UNIT_SIZE;
	        		vertices[count++]=yArray[j][i+1];
	        		vertices[count++]=zsz;
	        		
	        		vertices[count++]=zsx+UNIT_SIZE;
	        		vertices[count++]=yArray[j][i+1];
	        		vertices[count++]=zsz;
	        		
	        		vertices[count++]=zsx;
	        		vertices[count++]=yArray[j+1][i];
	        		vertices[count++]=zsz+UNIT_SIZE;
	        		
	        		vertices[count++]=zsx+UNIT_SIZE;
	        		vertices[count++]=yArray[j+1][i+1];
	        		vertices[count++]=zsz+UNIT_SIZE;
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
	        float[] texCoor=generateTexCoor(cols,rows);
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
	    	sdflagHandle=GLES20.glGetUniformLocation(mProgram, "sdflag");
		}
		
		//�Զ���Ļ��Ʒ���drawSelf
		public void drawSelf(int texId,int rock_textId)
		{
			//�ƶ�ʹ��ĳ��shader����
	   	 	GLES20.glUseProgram(mProgram); 
	        //�����ձ任������shader����
	        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
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
	        
	        //�����������
	        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
		}
		//�Զ��з����������������ķ���
	    public float[] generateTexCoor(int bw,int bh)
	    {
	    	float[] result=new float[bw*bh*6*2]; 
	    	float sizew=8.0f/bw;//����
	    	float sizeh=8.0f/bh;//����
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
}