package com.bn.commonObject;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.bn.core.MatrixState;

import android.opengl.GLES20;
import static com.bn.gameView.Constant.*;
//�������ε���   ����ƽ����XZƽ��    ����λ��XZƽ��ĵ�������
//�����ɽ��Ҫ���ĸ�����,����ɫ���и��ݸ߶����ж�
public class LandForm
{
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id  
    
    int uTuCengTexHandle;//����������������id  
    int uCaoDiTexHandle;//�ݵ�������������id  
    int uShiTouTexHandle;//ʯͷ������������id  
    int uShanDingTexHandle;//ɽ��������������id  

    int muHightHandle;//����߶�
    int muHightspanHandle;
    int uLandFlagHandle;//��ͬ���͵�ɽ�ı�־���Ϊ1��ʾ�����ϵ�ɽ
    private FloatBuffer   mVertexBuffer;//�����������ݻ���
    private FloatBuffer   mTextureBuffer;//������ɫ���ݻ���
    int vCount;//��������
    private boolean ishuidutu=false;//�Ƿ��ǻҶ�ͼ���ǳ�������
    public LandForm(int terrainId,int mProgram)
    {
    	this.mProgram=mProgram;
    	if(terrainId==3||terrainId==5||terrainId==6)//�����½���ϵ�ɽ,�򲻻��Ƶ�Ӱ
    	{
    		ishuidutu=true;
    	}
    	initVertexData(terrainId);
    	initShader();
    }
    public void initVertexData(int terrainId)
    {
    	int cols=LANDS_HEIGHT_ARRAY[terrainId][0].length-1;//����
    	int rows=LANDS_HEIGHT_ARRAY[terrainId].length-1;//����
    	//���Ƴ������ɵ�ɽ
    	if(!ishuidutu)
    	{
	        //����Ŀ���
	    	float textureSize=1f;
	    	float sizew=textureSize/cols;//�п�
	    	float sizeh=textureSize/rows;//�п�
	    	//����ļ���
	    	ArrayList<Float> alVertex=new ArrayList<Float>();
	    	//����� ����
	    	ArrayList<Float> alTexture=new ArrayList<Float>();
	        for(int i=0;i<rows;i++)
	        {
	        	for(int j=0;j<cols;j++)
	        	{        		
	        		//���㵱ǰ�������ϲ������       
	        		float zsx=j*LAND_UNIT_SIZE;//��ǰ��x����
	        		float zsz=i*LAND_UNIT_SIZE;//��ǰ��z���� 
	        		
	        		float s=j*sizew;  //s����
	    			float t=i*sizeh;  //t����
	    			
	        		if(LANDS_HEIGHT_ARRAY[terrainId][i][j]!=0||LANDS_HEIGHT_ARRAY[terrainId][i+1][j]!=0||LANDS_HEIGHT_ARRAY[terrainId][i][j+1]!=0)
	        		{
	        			//���ϵ�
	            		alVertex.add(zsx);
	            		alVertex.add(LANDS_HEIGHT_ARRAY[terrainId][i][j]);
	            		alVertex.add(zsz);
	            		//���µ�
	            		alVertex.add(zsx);
	            		alVertex.add(LANDS_HEIGHT_ARRAY[terrainId][i+1][j]);
	            		alVertex.add(zsz+LAND_UNIT_SIZE);
	            		//���ϵ�
	            		
	            		alVertex.add(zsx+LAND_UNIT_SIZE);
	            		alVertex.add(LANDS_HEIGHT_ARRAY[terrainId][i][j+1]);
	            		alVertex.add(zsz);
	        			
	        			alTexture.add(s);
	        			alTexture.add(t);
	        			
	        			alTexture.add(s);
	        			alTexture.add(t+sizeh);
	        			
	        			alTexture.add(s+sizew);
	        			alTexture.add(t);
            			//--------------------���Ƶ�Ӱ-------------        		
                		//���ϵ�
                		alVertex.add(zsx);
	            		alVertex.add(-LANDS_HEIGHT_ARRAY[terrainId][i][j]);
	            		alVertex.add(zsz);
                		//���ϵ�
                		alVertex.add(zsx+LAND_UNIT_SIZE);
	            		alVertex.add(-LANDS_HEIGHT_ARRAY[terrainId][i][j+1]);
	            		alVertex.add(zsz);
                		//���µ�
                		alVertex.add(zsx);
	            		alVertex.add(-LANDS_HEIGHT_ARRAY[terrainId][i+1][j]);
	            		alVertex.add(zsz+LAND_UNIT_SIZE);
            			
            			alTexture.add(s);
	        			alTexture.add(t);
            			
            			alTexture.add(s+sizew);
	        			alTexture.add(t);

	        			alTexture.add(s);
	        			alTexture.add(t+sizeh);
	        		}
	        		if(LANDS_HEIGHT_ARRAY[terrainId][i][j+1]!=0||LANDS_HEIGHT_ARRAY[terrainId][i+1][j]!=0||LANDS_HEIGHT_ARRAY[terrainId][i+1][j+1]!=0){
	        			//���ϵ�
	            		alVertex.add(zsx+LAND_UNIT_SIZE);
	            		alVertex.add(LANDS_HEIGHT_ARRAY[terrainId][i][j+1]);
	            		alVertex.add(zsz);
	            		//���µ�
	            		alVertex.add(zsx);
	            		alVertex.add(LANDS_HEIGHT_ARRAY[terrainId][i+1][j]);
	            		alVertex.add(zsz+LAND_UNIT_SIZE);
	            		//���µ�
	            		alVertex.add(zsx+LAND_UNIT_SIZE);
	            		alVertex.add(LANDS_HEIGHT_ARRAY[terrainId][i+1][j+1]);
	            		alVertex.add(zsz+LAND_UNIT_SIZE);
	        			
	        			alTexture.add(s+sizew);
	        			alTexture.add(t);
	        			
	        			alTexture.add(s);
	        			alTexture.add(t+sizeh);
	        			
	        			alTexture.add(s+sizew);
	        			alTexture.add(t+sizeh);
            			//���ϵ�
                		alVertex.add(zsx+LAND_UNIT_SIZE);
                		alVertex.add(-LANDS_HEIGHT_ARRAY[terrainId][i][j+1]);
                		alVertex.add(zsz);
                		//���µ�
                		alVertex.add(zsx+LAND_UNIT_SIZE);
                		alVertex.add(-LANDS_HEIGHT_ARRAY[terrainId][i+1][j+1]);
                		alVertex.add(zsz+LAND_UNIT_SIZE);
                		//���µ�
                		alVertex.add(zsx);
                		alVertex.add(-LANDS_HEIGHT_ARRAY[terrainId][i+1][j]);
                		alVertex.add(zsz+LAND_UNIT_SIZE);
            			
            			alTexture.add(s+sizew);
            			alTexture.add(t);
            			
            			alTexture.add(s+sizew);
            			alTexture.add(t+sizeh);
            			
            			alTexture.add(s);
            			alTexture.add(t+sizeh);
	        		}
	        	}
	        }
	        vCount=alVertex.size()/3;
	        float vertices[]=new float[vCount*3];
	        for(int i=0;i<vCount*3;i++)
	        {
	        	vertices[i]=alVertex.get(i);
	        }
	        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
	        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
	        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
	        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
	        mVertexBuffer.position(0);//���û�������ʼλ��
	    	//��������������
	        float textures[]=new float[alTexture.size()];
	        for(int i=0;i<alTexture.size();i++)
	        {
	        	textures[i]=alTexture.get(i);
	        }
	        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
	        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
	        mTextureBuffer= tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
	        mTextureBuffer.put(textures);//�򻺳����з��붥����ɫ����
	        mTextureBuffer.position(0);//���û�������ʼλ��
    	}
    	else//���صĻҶ�ͼ,���Ƶ�ʱ����trangle_strip��ʽ��
    	{
	        //����Ŀ���
	    	float textureSize=1f;
	    	float sizew=textureSize/cols;//�п�
	    	float sizeh=textureSize/rows;//�п�
	    	//����ļ���
	    	ArrayList<Float> alVertex=new ArrayList<Float>();
	    	//����� ����
	    	ArrayList<Float> alTexture=new ArrayList<Float>();
	        for(int i=0;i<rows;i++)
	        {
	        	for(int j=0;j<cols;j++)
	        	{        		
	        		//���㵱ǰ�������ϲ������       
	        		float zsx=j*LAND_UNIT_SIZE;//��ǰ��x����
	        		float zsz=i*LAND_UNIT_SIZE;//��ǰ��z���� 
	        		
	        		float s=j*sizew;  //s����
	    			float t=i*sizeh;  //t����
	    			
	    			if(i!=0&&j==0)
	    			{
	    				//���ϵ�
		    			alVertex.add(zsx);
	            		alVertex.add(LANDS_HEIGHT_ARRAY[terrainId][i][j]);
	            		alVertex.add(zsz);
	            		
	            		alTexture.add(s);
	        			alTexture.add(t);
	    			}
    				//���ϵ�
	    			alVertex.add(zsx);
            		alVertex.add(LANDS_HEIGHT_ARRAY[terrainId][i][j]);
            		alVertex.add(zsz);
            		
            		alTexture.add(s);
        			alTexture.add(t);
            		//���µ�
            		alVertex.add(zsx);
            		alVertex.add(LANDS_HEIGHT_ARRAY[terrainId][i+1][j]);
            		alVertex.add(zsz+LAND_UNIT_SIZE);
            		
            		alTexture.add(s);
        			alTexture.add(t+sizeh);
        			
	    			if(j==cols-1)
	    			{
	    				//���ϵ�
		    			alVertex.add(zsx+LAND_UNIT_SIZE);
	            		alVertex.add(LANDS_HEIGHT_ARRAY[terrainId][i][j+1]);
	            		alVertex.add(zsz);
	            		
	            		alTexture.add(s+sizew);
	        			alTexture.add(t);
	            		//���µ�
	            		alVertex.add(zsx+LAND_UNIT_SIZE);
	            		alVertex.add(LANDS_HEIGHT_ARRAY[terrainId][i+1][j+1]);
	            		alVertex.add(zsz+LAND_UNIT_SIZE);
	            		
	            		alTexture.add(s+sizew);
	        			alTexture.add(t+sizeh);
	            		if(i!=rows-1)
	            		{
	            			//���µ�
		            		alVertex.add(zsx+LAND_UNIT_SIZE);
		            		alVertex.add(LANDS_HEIGHT_ARRAY[terrainId][i+1][j+1]);
		            		alVertex.add(zsz+LAND_UNIT_SIZE);
		            		
		            		alTexture.add(s+sizew);
		        			alTexture.add(t+sizeh);
	            		}
	    			  }
	        	}
	        }
	        vCount=alVertex.size()/3;
	        float vertices[]=new float[vCount*3];
	        for(int i=0;i<vCount*3;i++)
	        {
	        	vertices[i]=alVertex.get(i);
	        }
	        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
	        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
	        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
	        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
	        mVertexBuffer.position(0);//���û�������ʼλ��
	    	//��������������
	        float textures[]=new float[alTexture.size()];
	        for(int i=0;i<alTexture.size();i++)
	        {
	        	textures[i]=alTexture.get(i);
	        }
	        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
	        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
	        mTextureBuffer= tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
	        mTextureBuffer.put(textures);//�򻺳����з��붥����ɫ����
	        mTextureBuffer.position(0);//���û�������ʼλ��
    	}
    }
    //��ʼ����ɫ����initShader����
    public void initShader()
    {
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�������������������id  
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        //ɽ�ص���������ͼ
        uShanDingTexHandle=GLES20.glGetUniformLocation(mProgram, "usTextureShanDing");  
        uTuCengTexHandle=GLES20.glGetUniformLocation(mProgram, "usTextureTuCeng");  
        uCaoDiTexHandle=GLES20.glGetUniformLocation(mProgram, "usTextureCaoDi");  
        muHightHandle=GLES20.glGetUniformLocation(mProgram, "uheight");  
        muHightspanHandle=GLES20.glGetUniformLocation(mProgram, "uheight_span");
        uShiTouTexHandle=GLES20.glGetUniformLocation(mProgram, "usTextureShiTou");  
        uLandFlagHandle=GLES20.glGetUniformLocation(mProgram, "uland_flag");  
    }
   public void drawSelf(int landFlag,int tex_terrain_shandingId,int texTuCengId,int texCaoDiId,int texShiTouId,float height,float height_span)
    {  
    	//�ƶ�ʹ��ĳ��shader����
   	 	GLES20.glUseProgram(mProgram);        
        //�����ձ任������shader����
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
        GLES20.glUniform1f(muHightHandle, height);//���뽥��ĸ߶�
        GLES20.glUniform1f(muHightspanHandle, height_span);//���뽥��ĸ߶�
        GLES20.glUniform1i(uLandFlagHandle, landFlag);//����ɽ�ı�־
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
               mTextureBuffer
        );   
        //������λ����������
        GLES20.glEnableVertexAttribArray(maPositionHandle);  
        GLES20.glEnableVertexAttribArray(maTexCoorHandle);  
        //������
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texTuCengId);    
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texCaoDiId);    
        GLES20.glActiveTexture(GLES20.GL_TEXTURE2);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texShiTouId);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE3);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex_terrain_shandingId);     
        GLES20.glUniform1i(uTuCengTexHandle, 0);
        GLES20.glUniform1i(uCaoDiTexHandle, 1);  
        GLES20.glUniform1i(uShiTouTexHandle, 2);  
        GLES20.glUniform1i(uShanDingTexHandle, 3);  
        if(!ishuidutu)
        {
        	//�����������
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
        }
        else
        {
        	//�����������
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vCount);
        }
    }
}
