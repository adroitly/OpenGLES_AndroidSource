package com.bn.clp;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import com.bn.core.MatrixState;
import android.opengl.GLES20;
import static com.bn.clp.Constant.*;

//�в���Ч����ˮ��
public class Water 
{	
	int mPrograms;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id 
    
    int maSTOffset;	//ˮ������ͼ��ƫ��������id

    static float[] mMMatrix = new float[16];//����������ƶ���ת����
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;   //��������
    float currStartST=0;	//ˮ����������ĵ�ǰ��ʼ����0~1
    
    public Water(int programId,int rows,int cols)
    {    	
    	//��ʼ�����������initVertexData����
    	initVertexData(rows,cols);
    	//��ʼ����ɫ���ķ���        
    	initShader(programId);
    	//����һ���̶߳�ʱ��֡
    	new Thread()
    	{
    		public void run()
    		{
    			while(Constant.threadFlag)
    			{
    				//��νˮ�涨ʱ��ֻ֡���޸�ÿ֡��ʼ�Ƕȼ��ɣ�
    				//ˮ�涥��Y����ı仯�ɶ�����ɫ��Ԫ���
    				currStartST=(currStartST+0.004f)%1;
        			try 
        			{
    					Thread.sleep(100);  
    				} catch (InterruptedException e) 
    				{
    					e.printStackTrace();
    				}
    			}     
    		}    
    	}.start();  
    }
    
    //��ʼ�����������initVertexData����
    public void initVertexData(int rows,int cols)
    {
    	final float pre_Size=UNIT_SIZE/rows;
    	
    	//�����������ݵĳ�ʼ��================begin============================
    	vCount=cols*rows*2*3;//ÿ���������������Σ�ÿ��������3������        
        float vertices[]=new float[vCount*3];//ÿ������xyz��������
        
        int count=0;//���������
        for(int j=0;j<rows;j++)
        {
        	for(int i=0;i<cols;i++)
        	{        		
        		//���㵱ǰ�������ϲ������ 
        		float zsx=-UNIT_SIZE/2+i*pre_Size;
        		float zsy=WATER_HIGH_ADJUST;
        		float zsz=-UNIT_SIZE/2+j*pre_Size;
        		
        		vertices[count++]=zsx;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz;
        		
        		vertices[count++]=zsx;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz+pre_Size;
        		
        		vertices[count++]=zsx+pre_Size;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz;
        		
        		vertices[count++]=zsx+pre_Size;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz;
        		
        		vertices[count++]=zsx;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz+pre_Size;
        		        		
        		vertices[count++]=zsx+pre_Size;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz+pre_Size;   
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
        float texCoor[]=generateTexCoor(cols,rows);     
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
        mPrograms =programId;
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mPrograms, "aPosition");
        //��ȡ�����ж�������������������id   
        maTexCoorHandle= GLES20.glGetAttribLocation(mPrograms, "aTexCoor");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mPrograms, "uMVPMatrix");  
        //��ȡˮ������ͼƫ����������id
        maSTOffset=GLES20.glGetUniformLocation(mPrograms, "stK");  
    }
    
    public void drawSelf(int texId,float startST)
    {   	    	
    	 //�ƶ�ʹ��ĳ��shader����
    	 GLES20.glUseProgram(mPrograms); 
         //�����ձ任������shader����
         GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
         //��ˮ������ͼ��stƫ��������shader����
         GLES20.glUniform1f(maSTOffset, startST);
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
                mTexCoorBuffer
         );   
         //������λ�á�����������������
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