package com.bn.commonObject;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

import com.bn.core.MatrixState;
//�������     ���иþ�����ƽ����XYƽ���,����ԭ�����ĶԳ�
//���Ҳ�����ڻ���ˮ��
//������ڻ��ư�ť
//���εĿ�Ⱥ͸߶ȷֱ�Ϊ width,height
public class TextureRect 
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id  
    int uIsButtonDownHandle;//��ť�Ƿ�������Id
    int uTypeHandle;//��ť������
    int uCurrAlphaHandle;//��ǰ��ť�Ĳ�͸����
	int uWidthHandle;//��ǰ��ť�Ŀ��
	int uCurrXHandle;//�����X����
    
    
    int maSTOffset;	//ˮ������ͼ��ƫ��������id
    int uBloodValueHandle;//����ֵ������
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;  
    private boolean isFlow;//��ǰ�Ƿ���ˮ��
    private boolean flag_flow_go=true;//ˮ���Ƿ��˶�
    private float currStartST=0;	//ˮ����������ĵ�ǰ��ʼ����0~1
    //��ť��־λ,�����ı䰴ť�Ĳ�͸����
    private int index=0;//����id,�����1,��ʾ��ǰΪ��ť,���Ϊ2,��ʾ�������������ֵ
    public  int button_type=0;//��ť������  0��ʾ������ʾ,1��ʾ���²�͸���İ�ť,2��ʾѭ���任�İ�ť,3��ʾ�������
    public  float bloodValue;//���������ֵ
    public  int isButtonDown;//��ť�Ƿ���0��ʾû�а���,1��ʾ����
    public float currAlpha;//��ǰ�Ĳ�͸����
    public float buttonWidth;//��ť�Ŀ��
    public float currX;//�����Xֵ
    
    
    public float flowSpeed;//�������ٶ�
    public float[] textures;//������������
    //��ͨ������
    public TextureRect(float width,float height,int mProgram)
    {    	
    	//��ʼ�������ݵ�initVertexDate����
    	initVertexData(width,height,false,1);
    	//��ʼ����ɫ����initShader����        
    	initShader(mProgram);
    }
    //��ť/����ֵ������
    public TextureRect(float width,float height,int mProgram,int index,int button_type)//1Ϊ��ť,2Ϊ����ֵ
    {   
    	this.index=index;
    	this.button_type=button_type;
    	//��ʼ��������������ɫ����
    	initVertexData(width,height,false,1);
    	//��ʼ��shader        
    	initShader(mProgram);
    }
    //ˮ�湹����
    public TextureRect(float width,float height,int mProgram,boolean isWater,int n) 
    {
    	//��ʼ��������������ɫ����
    	initVertexData(width,height,false,n);
    	//��ʼ��shader        
    	initShader(mProgram);
    }
    //���������Ĺ�����
    public TextureRect(float width,float height,int mProgram,boolean isFlow,float speed)
    {
    	this.isFlow=isFlow;
    	this.flowSpeed=speed;
    	//��ʼ��������������ɫ����
    	initVertexData(width,height,false,1);
    	//��ʼ��shader        
    	initShader(mProgram);
    	//����һ���̶߳�ʱ��֡
    	new Thread()
    	{
    		public void run()
    		{
    			while(flag_flow_go)
    			{
    				//��νˮ�涨ʱ��ֻ֡���޸�ÿ֡��ʼ�Ƕȼ��ɣ�
    				//ˮ�涥��Y����ı仯�ɶ�����ɫ��Ԫ���
    				currStartST=(currStartST+0.00008f*flowSpeed)%1;
        			try 
        			{
    					Thread.sleep(10);  
    				}
        			catch (InterruptedException e) 
    				{
    					e.printStackTrace();
    				}
    			} 
    		}    
    	}.start();  
    }
    //�������ֵĹ�����,��Ҫ�ǽ��������괫�����
    public TextureRect(float width,float height,float[] textures,int mProgram)//�����ߺ�������������
    {
    	this.textures=textures;
    	initVertexData(width,height,true,1);
    	initShader(mProgram);
    }
    //��ʼ��������������ɫ���ݵķ���
    public void initVertexData(float width,float height,boolean hasTexture,int n)
    {
        vCount=4;
        float vertices[]=new float[]
        {
        	-width/2,height/2,0,
        	-width/2,-height/2,0,
        	width/2,height/2,0,
        	width/2,-height/2,0 
        };
        //���������������ݻ���
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        if(!hasTexture)//���û�д�������
        {
        	textures=new float[]//������ɫֵ���飬ÿ������4��ɫ��ֵRGBA
	        {
	        	0,0, 0,n, 
	        	n,0, n,n        		
	        };        
        }
        //�������������������ݻ���
        ByteBuffer cbb = ByteBuffer.allocateDirect(textures.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mTexCoorBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(textures);//�򻺳����з��붥����ɫ����
        mTexCoorBuffer.position(0);//���û�������ʼλ��
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
        if(isFlow)
        {
        	//��ȡˮ������ͼƫ����������id
            maSTOffset=GLES20.glGetUniformLocation(mProgram, "stK");  
        }
        if(index==1)//��ǰΪ��ť
        {
        	uIsButtonDownHandle=GLES20.glGetUniformLocation(mProgram, "isButtonDown");//��ť�Ƿ���
        	uTypeHandle=GLES20.glGetUniformLocation(mProgram, "type");//��ť������
        	uCurrAlphaHandle=GLES20.glGetUniformLocation(mProgram, "currAlpha");//��ǰ��ť�Ĳ�͸����
        	uWidthHandle=GLES20.glGetUniformLocation(mProgram, "width");//��ǰ��ť�Ŀ��
        	uCurrXHandle=GLES20.glGetUniformLocation(mProgram, "currX");//�����X����
        }
        else if(index==2)//��ǰΪ���������ֵ
        {
        	uBloodValueHandle=GLES20.glGetUniformLocation(mProgram, "ublood");
        }
    }
    public void drawSelf(int texId)
    {        
    	 //�ƶ�ʹ��ĳ��shader����
    	 GLES20.glUseProgram(mProgram);        
         //�����ձ任������shader����
         GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
         if(isFlow)
         {
        	//��ˮ������ͼ��stƫ��������shader����
             GLES20.glUniform1f(maSTOffset, currStartST);
         }
         if(index==1)//�����ǰΪ��ť
         {
        	 GLES20.glUniform1i(uIsButtonDownHandle, isButtonDown);//��ť�Ƿ���
        	 GLES20.glUniform1i(uTypeHandle, button_type);//��ť������
        	 GLES20.glUniform1f(uCurrAlphaHandle, currAlpha);//��ť�Ĳ�͸����
        	 GLES20.glUniform1f(uWidthHandle, buttonWidth);//��ť�Ŀ��
        	 GLES20.glUniform1f(uCurrXHandle, currX);//�����Xֵ
         }
         else if(index==2)//�����ǰΪ��������ֵ���ο�
         {
        	 GLES20.glUniform1f(uBloodValueHandle, bloodValue);
         }
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
         //������λ����������
         GLES20.glEnableVertexAttribArray(maPositionHandle);  
         GLES20.glEnableVertexAttribArray(maTexCoorHandle);  
         //������
         GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
         GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
         //�����������
         GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vCount); 
    }
}

