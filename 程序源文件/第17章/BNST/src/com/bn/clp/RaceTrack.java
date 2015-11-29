package com.bn.clp;
import static com.bn.clp.Constant.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import com.bn.core.MatrixState;
import android.opengl.GLES20;

//������ͧ��������ֱ��������
public class RaceTrack
{
	//�Զ�����Ⱦ������ɫ�������id
	private int mProgram;
	//�ܱ仯�������õ�id
	private int muMVPMatrixHandle;
	//λ�á���ת�任����
	int muMMatrixHandle;
	//����λ�����Ե�id
	private int maPositionHandle;
	//���������������õ�id
	private int maTexCoorHandle;
	//����ӵ�==========================================================================
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
	//����ӵ�=============================================================================
	
	//�������껺��
	private FloatBuffer mVertexBuffer;
	//ƬԪ������
	private FloatBuffer mTexCoorBuffer;
	//���������
	private int vCount=0;
	
	//�Ƿ�Ϊֱ�����
	boolean isZD;
	
	/*
	 * ������вι�����������mvΪMyGLSurfaceView������ã�
	 * yArrayΪ��Ӧ�Ҷ�ͼ�����y���꣬rowsΪ�ûҶ�ͼ��������colsΪ�ûҶ�ͼ������
	*/
	public RaceTrack(int programId,float[][] yArray,int rows,int cols,boolean isZD)
	{
		this.isZD=isZD;
		//��ʼ����������
		initVertexData(yArray,rows,cols,isZD);
		//��ʼ��Shader
		initShader(programId);
	}
	
	//��ʼ���������ݵ�initVertexData����
	public void initVertexData(float[][] yArray,int rows,int cols,boolean isZD)
	{
		if(isZD)
		{
			initVertexDataZD(yArray,rows,cols);
		}
		else
		{
			initVertexDataFZD(yArray,rows,cols);
		}
	}
	
	//��ʼ����ֱͨ���������ݵķ���
	public void initVertexDataZD(float[][] yArray,int rows,int cols)
	{
		float width=UNIT_SIZE/cols;//�ֳɸ��ӵĿ��
		float height=UNIT_SIZE/rows;//�ֳɸ��ӵĿ�߶�
		
		vCount=rows*cols*2*3;//���������
		float[] vertex=new float[vCount*3];
		float[] texture=new float[vCount*2];
		
		float tempWidth=2.0f/cols;//�ֳɸ��ӵ�������
		float tempHeight=2.0f/rows;//�ֳɸ��ӵ�����߶�		
		int countv=0;
		int countt=0;
		
		int state=0;//0--һ��ʼ  1--�߶�Ϊ0  2--�ָ�
		
		for(int j=0;j<cols;j++)
		{
			state=0;
			for(int i=0;i<rows+1;i++)
			{
            	if(j!=0&&i==0)
            	{
            		//���������
    				vertex[countv++]=width*(j+1)-UNIT_SIZE/2;
    				vertex[countv++]=yArray[i][j+1];
    				vertex[countv++]=height*i-UNIT_SIZE/2;
    				
    				//�����S��T����
    				texture[countt++]=tempWidth*(j+1)-width/2;
    				texture[countt++]=tempHeight*i-height/2; 
    				
    				//���������
    				vertex[countv++]=width*(j+1)-UNIT_SIZE/2;
    				vertex[countv++]=yArray[i][j+1];
    				vertex[countv++]=height*i-UNIT_SIZE/2;
    				
    				//�����S��T����
    				texture[countt++]=tempWidth*(j+1)-width/2;
    				texture[countt++]=tempHeight*i-height/2; 
            	}  
				if(state==0)
				{
					//���������
					vertex[countv++]=width*(j+1)-UNIT_SIZE/2;
					vertex[countv++]=yArray[i][j+1];
					vertex[countv++]=height*i-UNIT_SIZE/2;
					
					//�����S��T����
					texture[countt++]=tempWidth*(j+1)-width/2;
					texture[countt++]=tempHeight*i-height/2; 
	            	
	            	//���������
					vertex[countv++]=width*j-UNIT_SIZE/2;
					vertex[countv++]=yArray[i][j];
					vertex[countv++]=height*i-UNIT_SIZE/2;
					
					//�����S��T����
					texture[countt++]=tempWidth*j-width/2;
					texture[countt++]=tempHeight*i-height/2;  
					
					if(yArray[i][j]==0&&yArray[i][j+1]==0&&yArray[i+1][j]==0&&yArray[i+1][j+1]==0)
					{
						state=1;
						//���������
						vertex[countv++]=width*j-UNIT_SIZE/2;
						vertex[countv++]=yArray[i][j];
						vertex[countv++]=height*i-UNIT_SIZE/2;
						
						//�����S��T����
						texture[countt++]=tempWidth*j-width/2;
						texture[countt++]=tempHeight*i-height/2;
						
						//���������
						vertex[countv++]=width*j-UNIT_SIZE/2;
						vertex[countv++]=yArray[i][j];
						vertex[countv++]=height*i-UNIT_SIZE/2;
						
						//�����S��T����
						texture[countt++]=tempWidth*j-width/2;
						texture[countt++]=tempHeight*i-height/2;
					}
				}
				else if(state==1)
				{
					if(!(yArray[i+1][j]==0&&yArray[i+1][j+1]==0))
					{
						//���������
						vertex[countv++]=width*(j+1)-UNIT_SIZE/2;
						vertex[countv++]=yArray[i][j+1];
						vertex[countv++]=height*i-UNIT_SIZE/2;
						
						//�����S��T����
						texture[countt++]=tempWidth*(j+1)-width/2;
						texture[countt++]=tempHeight*i-height/2; 
						
						//���������
						vertex[countv++]=width*(j+1)-UNIT_SIZE/2;
						vertex[countv++]=yArray[i][j+1];
						vertex[countv++]=height*i-UNIT_SIZE/2;
						
						//�����S��T����
						texture[countt++]=tempWidth*(j+1)-width/2;
						texture[countt++]=tempHeight*i-height/2; 
						
						//���������
						vertex[countv++]=width*(j+1)-UNIT_SIZE/2;
						vertex[countv++]=yArray[i][j+1];
						vertex[countv++]=height*i-UNIT_SIZE/2;
						
						//�����S��T����
						texture[countt++]=tempWidth*(j+1)-width/2;
						texture[countt++]=tempHeight*i-height/2; 
		            	
		            	//���������
						vertex[countv++]=width*j-UNIT_SIZE/2;
						vertex[countv++]=yArray[i][j];
						vertex[countv++]=height*i-UNIT_SIZE/2;
						
						//�����S��T����
						texture[countt++]=tempWidth*j-width/2;
						texture[countt++]=tempHeight*i-height/2;  
						state=2;
					}
				}
				else if(state==2)
				{
					//���������
					vertex[countv++]=width*(j+1)-UNIT_SIZE/2;
					vertex[countv++]=yArray[i][j+1];
					vertex[countv++]=height*i-UNIT_SIZE/2;
					
					//�����S��T����
					texture[countt++]=tempWidth*(j+1)-width/2;
					texture[countt++]=tempHeight*i-height/2; 
	            	
	            	//���������
					vertex[countv++]=width*j-UNIT_SIZE/2;
					vertex[countv++]=yArray[i][j];
					vertex[countv++]=height*i-UNIT_SIZE/2;
					
					//�����S��T����
					texture[countt++]=tempWidth*j-width/2;
					texture[countt++]=tempHeight*i-height/2;  
				}
            	
				
				if(i==rows&&j!=cols-1)
				{
					//���������
					vertex[countv++]=width*j-UNIT_SIZE/2;
					vertex[countv++]=yArray[i][j];
					vertex[countv++]=height*i-UNIT_SIZE/2;
					
					//�����S��T����
					texture[countt++]=tempWidth*j-width/2;
					texture[countt++]=tempHeight*i-height/2;   
					   
					//���������
					vertex[countv++]=width*j-UNIT_SIZE/2;  
					vertex[countv++]=yArray[i][j];  
					vertex[countv++]=height*i-UNIT_SIZE/2;
					
					//�����S��T����
					texture[countt++]=tempWidth*j-width/2;
					texture[countt++]=tempHeight*i-height/2;   
				}                     
			}
		}
		vCount=countt/2;
		
		//����������Ӱ�Ķ���������������
		float[] vertexL=new float[vCount*3];
		for(int i=0;i<vertexL.length;i++)
		{
			vertexL[i]=vertex[i];
		}
		vertex=vertexL;
		
		vertexL=new float[(vCount*2+2)*3];
		int cTemp=0;
		for(int i=0;i<vertex.length;i++)
		{
			vertexL[cTemp++]=vertex[i];
		}
		vertexL[cTemp++]=vertex[vertex.length-3];
		vertexL[cTemp++]=vertex[vertex.length-2];
		vertexL[cTemp++]=vertex[vertex.length-1];
		
		vertexL[cTemp++]=vertex[0];
		vertexL[cTemp++]=-vertex[1];
		vertexL[cTemp++]=vertex[2];
		
		for(int i=0;i<vertex.length;i++)
		{
			if(i%3==1)
			{
				vertexL[cTemp++]=-vertex[i];
			}
			else
			{
				vertexL[cTemp++]=vertex[i];
			}
		}
		
		
		float[] textureL=new float[vCount*2];
		for(int i=0;i<textureL.length;i++)
		{
			textureL[i]=texture[i];
		}
		texture=textureL;
		
		textureL=new float[(vCount*2+2)*2];
		cTemp=0;
		for(int i=0;i<texture.length;i++)
		{
			textureL[cTemp++]=texture[i];
		}
		textureL[cTemp++]=texture[texture.length-2];
		textureL[cTemp++]=texture[texture.length-1];
		
		textureL[cTemp++]=texture[0];
		textureL[cTemp++]=texture[1];
		
		for(int i=0;i<texture.length;i++)
		{
			textureL[cTemp++]=texture[i];
		}
		
		vCount=vCount*2+2;
		
		//�������㻺��������
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertexL.length*4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer=vbb.asFloatBuffer();
		mVertexBuffer.put(vertexL);
		mVertexBuffer.position(0);
		
		//�з�����ͼƬ�ķ���
		ByteBuffer cbb=ByteBuffer.allocateDirect(textureL.length*4);
		cbb.order(ByteOrder.nativeOrder());
		mTexCoorBuffer=cbb.asFloatBuffer();
		mTexCoorBuffer.put(textureL);
		mTexCoorBuffer.position(0);
	}
	
	//��ʼ������ֱͨ���������ݵķ���
	public void initVertexDataFZD(float[][] yArray,int rows,int cols)
	{
		float width=UNIT_SIZE/cols;//�ֳɸ��ӵĿ��
		float height=UNIT_SIZE/rows;//�ֳɸ��ӵĿ�߶�
		
		vCount=rows*cols*2*3;//���������
		float[] vertex=new float[vCount*3];
		float[] texture=new float[vCount*2];
		
		float tempWidth=2.0f/cols;//�ֳɸ��ӵĿ��
		float tempHeight=2.0f/rows;//�ֳɸ��ӵĿ�߶�
		
		int countv=0;
		int countt=0;
		//ѭ���������㣬��->��
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{				
				if(yArray[i][j]!=0||yArray[i+1][j]!=0||yArray[i][j+1]!=0)
				{
					//��ʱ����Ƶ�һ�������ε����ϽǵĶ���
					vertex[countv++]=width*j-UNIT_SIZE/2;
					vertex[countv++]=yArray[i][j];
					vertex[countv++]=height*i-UNIT_SIZE/2;
					//��ʱ����Ƶ�һ�������ε����½ǵĶ���
					vertex[countv++]=width*j-UNIT_SIZE/2;
					vertex[countv++]=yArray[i+1][j];
					vertex[countv++]=height*(i+1)-UNIT_SIZE/2;
					//��ʱ����Ƶ�һ�������ε����ϽǵĶ���
					vertex[countv++]=width*(j+1)-UNIT_SIZE/2;
					vertex[countv++]=yArray[i][j+1];
					vertex[countv++]=height*i-UNIT_SIZE/2;
					
					//����ͼ���е�һ�����������Ͻǵ�S��T����
					texture[countt++]=tempWidth*i-width/2;
					texture[countt++]=tempHeight*j-height/2;
					//����ͼ���е�һ�����������½ǵ�S��T����
					texture[countt++]=tempWidth*(i+1)-width/2;
					texture[countt++]=tempHeight*j-height/2;
					//����ͼ���е�һ�����������Ͻǵ�S��T����
					texture[countt++]=tempWidth*i-width/2;
					texture[countt++]=tempHeight*(j+1)-height/2;
				}
				
				if(yArray[i][j+1]!=0||yArray[i+1][j]!=0||yArray[i+1][j+1]!=0)
				{
					//��ʱ����Ƶڶ��������ε����ϽǵĶ���
					vertex[countv++]=width*(j+1)-UNIT_SIZE/2;
					vertex[countv++]=yArray[i][j+1];
					vertex[countv++]=height*i-UNIT_SIZE/2;
					//��ʱ����Ƶڶ��������ε����½ǵĶ���
					vertex[countv++]=width*j-UNIT_SIZE/2;
					vertex[countv++]=yArray[i+1][j];
					vertex[countv++]=height*(i+1)-UNIT_SIZE/2;
					//��ʱ����Ƶڶ��������ε����½ǵĶ���
					vertex[countv++]=width*(j+1)-UNIT_SIZE/2;
					vertex[countv++]=yArray[i+1][j+1];
					vertex[countv++]=height*(i+1)-UNIT_SIZE/2;
					
					//����ͼ���еڶ������������Ͻǵ�S��T����
					texture[countt++]=tempWidth*i-width/2;
					texture[countt++]=tempHeight*(j+1)-height/2;
					//����ͼ���еڶ������������½ǵ�S��T����
					texture[countt++]=tempWidth*(i+1)-width/2;
					texture[countt++]=tempHeight*j-height/2;
					//����ͼ���еڶ������������Ͻǵ�S��T����
					texture[countt++]=tempWidth*(i+1)-width/2;
					texture[countt++]=tempHeight*(j+1)-height/2;
				}
			}
		}
		vCount=countt/2;
		
		//����������Ӱ�Ķ���������������
		float[] vertexL=new float[vCount*3];
		for(int i=0;i<vertexL.length;i++)
		{
			vertexL[i]=vertex[i];
		}
		vertex=vertexL;
		
		vertexL=new float[(vCount*2)*3];
		int cTemp=0;
		for(int i=0;i<vertex.length;i++)
		{
			vertexL[cTemp++]=vertex[i];
		}		
		
		for(int i=0;i<vertex.length;i++)
		{
			if(i%3==1)
			{
				vertexL[cTemp++]=-vertex[i];
			}
			else
			{
				vertexL[cTemp++]=vertex[i];
			}
		}
		
		
		float[] textureL=new float[vCount*2];
		for(int i=0;i<textureL.length;i++)
		{
			textureL[i]=texture[i];
		}
		texture=textureL;
		
		textureL=new float[(vCount*2)*2];
		cTemp=0;
		for(int i=0;i<texture.length;i++)
		{
			textureL[cTemp++]=texture[i];
		}		
		
		for(int i=0;i<texture.length;i++)
		{
			textureL[cTemp++]=texture[i];
		}
		
		vCount=vCount*2;
		
		//�������㻺��������
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertexL.length*4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer=vbb.asFloatBuffer();
		mVertexBuffer.put(vertexL);
		mVertexBuffer.position(0);
		
		//�з�����ͼƬ�ķ���
		ByteBuffer cbb=ByteBuffer.allocateDirect(textureL.length*4);
		cbb.order(ByteOrder.nativeOrder());
		mTexCoorBuffer=cbb.asFloatBuffer();
		mTexCoorBuffer.put(textureL);
		mTexCoorBuffer.position(0);
	}
	
	//��ʼ����ɫ���ķ���
	public void initShader(int programId)
	{
		//���ڶ�����ɫ����ƬԪ��ɫ����������
		mProgram=programId;  
		//��ȡ�����ж���λ����������id
		maPositionHandle=GLES20.glGetAttribLocation(mProgram, "aPosition");
		//��ȡ������ƬԪ��������id
		maTexCoorHandle=GLES20.glGetAttribLocation(mProgram, "aTexCoor");
		//��ȡ�������ܱ任��������id
		muMVPMatrixHandle=GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		//��ȡλ�á���ת�任��������id
        muMMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMMatrix");  
        
        //����ӵ�=========================================================================
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
    	//����ӵ�=========================================================================
	}   
		  
	//����ͼ�εķ���
	public void drawSelf(int rock_textId,int textureId)
	{  
		realDrawTask(textureId,rock_textId);  
	}
	
	//�����Ļ�������
	public void realDrawTask(int textureId,int rock_textId)
	{
		//�ƶ�ʹ��ĳ��shader����
		GLES20.glUseProgram(mProgram);
		//�����ձ任������Shader����   
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
        //��λ�á���ת�任������shader����
        GLES20.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0); 
        
        //����ӵ�============================================================
        //���Ƿ�Ϊ��ͨɽ�ı�־λ����Shader������
        GLES20.glUniform1i(sdflagHandle, flag);
        //����ӵ�============================================================
        
        //���붥��λ������
        GLES20.glVertexAttribPointer
        (
        	maPositionHandle, 
    		3, 
    		GLES20.GL_FLOAT, 
    		false,				//��������ľ������壿������������ 
    		3*4,				//3�Ƕ������������������4������ʲô����������
    		mVertexBuffer		//�������껺������
        );
        //���붥��������������
        GLES20.glVertexAttribPointer
        (
        	maTexCoorHandle, 
    		2, 
    		GLES20.GL_FLOAT, 
    		false,				//��������ľ������壿������������ 
    		2*4,				//3�Ƕ������������������4������ʲô����������
    		mTexCoorBuffer		//�������껺������
        );
        //������λ�á�����������������
        GLES20.glEnableVertexAttribArray(maPositionHandle);//��������
        GLES20.glEnableVertexAttribArray(maTexCoorHandle);//��������
        //������
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        //����ӵ�===============================================================
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, rock_textId);
		GLES20.glUniform1i(sTextureGrassHandle, 0);//ʹ��0������
        GLES20.glUniform1i(sTextureRockHandle, 1); //ʹ��1������
        
        //������Ӧ��x����
        GLES20.glUniform1f(b_YZ_StartYHandle, 0);
        
        GLES20.glUniform1f(b_YZ_YSpanHandle, LAND_MAX_HIGHEST); 
        //����ӵ�===============================================================
        if(isZD)
        {
        	//�����������
    		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vCount);
        }
        else
        {
        	//�����������
    		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
        }
	}
}