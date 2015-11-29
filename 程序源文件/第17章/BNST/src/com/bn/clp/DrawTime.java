package com.bn.clp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import static com.bn.clp.Constant.*;
import android.opengl.GLES20;
import com.bn.core.MatrixState;
//����ʱ��  ��Ȧ ���� ��ͣ ���˳��ӽ� ���ٰ�ť����
public class DrawTime
{
	//���־��ο�Ŀ�Ⱥ͸߶�
	float SHUZI_KUANDU=0.1f;
	float SHUZI_GAODU=0.12f;
	
	//��¼��ʱ������� 
	public static long timeTotal[]=new long[3];
	
	//���ֵĻ��ƾ���
	WenLiJuXing[] shuzi=new WenLiJuXing[10];
	//ð�ŵĻ��ƾ���
	WenLiJuXing maohao; 
	//��time���Ļ��ƾ���
	WenLiJuXing timeText;
	//��lap���Ļ��ƾ���
	WenLiJuXing lapText;
	//б�ܵĻ��ƾ���
	WenLiJuXing xiegan;
	//"����"ͼƬ�Ļ��ƾ���
	WenLiJuXing n2;
	//�˺ŵĻ��ƾ���
	WenLiJuXing chenhao;
	
	//ʹ�õ����Ļ��ƾ���
	WenLiJuXing kejiasu;
	//���ɼ��ٵĵ����Ļ��ƾ���
	WenLiJuXing bukejiasu;
	
	//��һ�͵����˳ư�ť
//	WenLiJuXing firstView;
//	WenLiJuXing thirdView;
	
	//��ͣ���ָ���ť
	WenLiJuXing pauseButton;
	WenLiJuXing resumeButton;
	
	//�����ͼ���ܰ�ť
	WenLiJuXing miniMapButton;
	
	WenLiJuXing shache;
	WenLiJuXing noshache;
	
	public DrawTime(int mProgram)
	{
		for(int i=0;i<10;i++)
		{
			shuzi[i]=new WenLiJuXing
            (
            	SHUZI_KUANDU,
            	SHUZI_GAODU,
            	 new float[]
	             {
	           	  0.1f*i,0, 0.1f*i,0.26f, 0.1f*(i+1),0.26f,
	           	  0.1f*i,0, 0.1f*(i+1),0.26f,  0.1f*(i+1),0
	             }
             ); 
		}
		
		maohao=new WenLiJuXing
		(
			SHUZI_KUANDU,
        	SHUZI_GAODU,
        	 new float[]
             {
           	  0.725f,0.46f, 0.725f,0.71f, 0.8f,0.71f,
           	  0.725f,0.46f, 0.8f,0.71f,  0.8f,0.46f
             }
		);
		
		timeText=new WenLiJuXing
		(
				SHUZI_KUANDU*4,
	        	SHUZI_GAODU,
	        	 new float[]
	             {
	           	  0.025f,0.48f, 0.025f,0.7f, 0.31f,0.7f,
	           	  0.025f,0.48f, 0.31f,0.7f, 0.31f,0.48f
	             }
		);
		
		lapText=new WenLiJuXing
		(
				SHUZI_KUANDU*3,
	        	SHUZI_GAODU,
	        	 new float[]
	             {
	           	  0.33f,0.48f, 0.33f,0.7f, 0.625f,0.7f,
	           	  0.33f,0.48f, 0.625f,0.7f, 0.625f,0.48f
	             }
		);
		
		xiegan=new WenLiJuXing
		(
				SHUZI_KUANDU,
	        	SHUZI_GAODU,
	        	 new float[]
	             {
	           	  0.63f,0.445f, 0.63f,0.71f, 0.71f,0.71f,
	           	  0.63f,0.445f, 0.71f,0.71f, 0.71f,0.445f
	             }
		);
		
		n2=new WenLiJuXing
		(
				SHUZI_KUANDU*2,
	        	SHUZI_GAODU*2,
	        	 new float[]
	             {
	           	  0.81f,0.3f, 0.81f,0.72f, 0.95f,0.72f,
	           	  0.81f,0.3f, 0.95f,0.72f, 0.95f,0.3f
	             }
		);
		
		chenhao=new WenLiJuXing
		(
				SHUZI_KUANDU,
	        	SHUZI_GAODU,
	        	 new float[]
	             {
	           	  0.02f,0.795f, 0.02f,0.945f, 0.095f,0.945f,
	           	  0.02f,0.795f, 0.095f,0.945f, 0.095f,0.795f
	             }
		);
		
		noshache=new WenLiJuXing
		(
				Self_Adapter_Data_TRASLATE[screenId][17],
	        	SHUZI_GAODU*1.5f,  
	        	 new float[]
	             {
	           	  0,0f, 0,1f, 0.5f,1f,
	           	  0f,0f, 0.5f,1f, 0.5f,0f
	             }
		);
		
		shache=new WenLiJuXing
		(
				Self_Adapter_Data_TRASLATE[screenId][17],
	        	SHUZI_GAODU*1.5f,
	        	 new float[]
	             {
					0.5f,0f, 0.5f,1f, 1f,1f,
			        0.5f,0f, 1f,1f, 1f,0f 
	             }
		);
		
		kejiasu=new WenLiJuXing
		(
				Self_Adapter_Data_TRASLATE[screenId][8],
	        	SHUZI_GAODU*5,
	        	 new float[]
	             {
	           	  0,0f, 0,1f, 0.5f,1f,
	           	  0f,0f, 0.5f,1f, 0.5f,0f
	             }
		);
		
		bukejiasu=new WenLiJuXing
		(
				Self_Adapter_Data_TRASLATE[screenId][8],
	        	SHUZI_GAODU*5,
	        	 new float[]
	             {
	           	  0.5f,0f, 0.5f,1f, 1f,1f,
	           	  0.5f,0f, 1f,1f, 1f,0f
	             }
		);
		
//		firstView=new WenLiJuXing
//		(
//				Self_Adapter_Data_TRASLATE[screenId][2],
//	        	SHUZI_GAODU*2,
//	        	 new float[]
//	             {
//	           	  0.15f,0.73f, 0.15f,1f, 0.29f,1f,
//	           	  0.15f,0.73f, 0.29f,1f, 0.29f,0.73f
//	             }
//		);
//		
//		thirdView=new WenLiJuXing
//		(
//				Self_Adapter_Data_TRASLATE[screenId][2],
//	        	SHUZI_GAODU*2,
//	        	 new float[]
//	             {
//	           	  0.3f,0.73f, 0.3f,1f, 0.44f,1f,
//	           	  0.3f,0.73f, 0.44f,1f, 0.44f,0.73f
//	             }
//		);
		
		pauseButton=new WenLiJuXing
		(
				Self_Adapter_Data_TRASLATE[screenId][5],
	        	SHUZI_GAODU*2,
	        	 new float[]
	             {
					0.47f,0.73f, 0.47f,1f, 0.61f,1f,
					0.47f,0.73f, 0.61f,1f, 0.61f,0.73f
	             }
		);
		
		resumeButton=new WenLiJuXing
		(
				Self_Adapter_Data_TRASLATE[screenId][5],
	        	SHUZI_GAODU*2,
	        	 new float[]
	             {
					0.63f,0.73f, 0.63f,1f, 0.77f,1f, 
					0.63f,0.73f, 0.77f,1f, 0.77f,0.73f
	             }
		);
		
		miniMapButton=new WenLiJuXing
		(
				SHUZI_GAODU*2,
	        	SHUZI_GAODU*2,
	        	 new float[]
	             {
					0.75f,0.75f, 0.75f,1f, 0.95f,1f,
					0.75f,0.75f, 0.95f,1f, 0.95f,0.75f
	             }
		);
		
		//��ʼ��shader
		initShader(mProgram);
	}
	
	 public void initShader(int mProgram)  
	 {
    	for(WenLiJuXing fl:shuzi)
    	{
    		fl.initShader(mProgram);
    	}
    	maohao.initShader(mProgram);
    	timeText.initShader(mProgram);
    	lapText.initShader(mProgram);
    	xiegan.initShader(mProgram);
    	n2.initShader(mProgram);
    	chenhao.initShader(mProgram);
    	kejiasu.initShader(mProgram);
    	bukejiasu.initShader(mProgram);
    	pauseButton.initShader(mProgram);
    	resumeButton.initShader(mProgram);
//    	firstView.initShader(mProgram);
//    	thirdView.initShader(mProgram);
    	miniMapButton.initShader(mProgram);
    	shache.initShader(mProgram);
    	noshache.initShader(mProgram);
	 }
	 //����ʱ��ķ���
	 public void toTotalTime(long ms)
	 {
		timeTotal[0]=(long) Math.floor((ms%1000)/10);
		timeTotal[1]=(long) Math.floor((ms%60000)/1000);
		timeTotal[2]=(long) Math.floor((ms/60000));		 		
	 }
	 //���Ƽ�ʱ����lap��־
	 public void drawSelf(int timeTexId,int currLap,int numberOfN2,int goTexId,int shacheTexId,boolean isShaChe)
	 {
		 MatrixState.pushMatrix();
		 MatrixState.translate(-SHUZI_KUANDU*8+0.025f, SHUZI_GAODU+0.02f, 0);
		 MatrixState.rotate(90, 1, 0, 0);
		 lapText.drawSelf(timeTexId);//lapͼ��
		 MatrixState.popMatrix();
			
		 MatrixState.pushMatrix();
		 MatrixState.translate(-SHUZI_KUANDU*8, 0, 0);
		 MatrixState.rotate(90, 1, 0, 0);
		 xiegan.drawSelf(timeTexId);//lap�е�б��
		 MatrixState.popMatrix();		 
		 
		 MatrixState.pushMatrix();
		 MatrixState.translate(-SHUZI_KUANDU*9, 0, 0);
		 lapDrawSelf(timeTexId,currLap);//lap�е�����
		 MatrixState.popMatrix();
		 
		 MatrixState.pushMatrix();
		 MatrixState.translate(SHUZI_KUANDU*4-0.05f, SHUZI_GAODU+0.02f, 0);
		 MatrixState.rotate(90, 1, 0, 0);
		 timeText.drawSelf(timeTexId);//timeͼ��
		 MatrixState.popMatrix();
		 
		 MatrixState.pushMatrix();
		 MatrixState.translate(-0.055f, 0, 0);
		 timeDrawSelf(timeTexId,2);//����ʱ���еķ�
		 MatrixState.popMatrix();
					
		 MatrixState.pushMatrix();
		 MatrixState.translate(SHUZI_GAODU*3-0.055f, 0, 0);
		 timeDrawSelf(timeTexId,1);//����ʱ���е���
		 MatrixState.popMatrix();
					
		 MatrixState.pushMatrix();
		 MatrixState.translate(SHUZI_GAODU*6-0.055f, 0, 0);
		 timeDrawSelf(timeTexId,0);//����ʱ���еĺ���
		 MatrixState.popMatrix();
		 
		 MatrixState.pushMatrix();
		 MatrixState.translate(SHUZI_KUANDU*13, SHUZI_GAODU-0.05f, 0);
		 MatrixState.rotate(90, 1, 0, 0);
		 n2.drawSelf(timeTexId);//����ͼ��
		 MatrixState.popMatrix();
		 
		 MatrixState.pushMatrix();
		 MatrixState.translate(SHUZI_KUANDU*15-0.025f, SHUZI_GAODU-0.05f, 0);
		 MatrixState.rotate(90, 1, 0, 0);
		 chenhao.drawSelf(timeTexId);//�˺�ͼ��
		 MatrixState.popMatrix();
		 
		 MatrixState.pushMatrix();
		 MatrixState.translate(SHUZI_KUANDU*16, SHUZI_GAODU-0.05f, 0);
		 drawNumberOfN2(timeTexId,numberOfN2);//��������
		 MatrixState.popMatrix();
		 //���ٰ�ť
		 if(numberOfN2>0)
		 { 
			 MatrixState.pushMatrix();
			 MatrixState.translate(Self_Adapter_Data_TRASLATE[screenId][6], Self_Adapter_Data_TRASLATE[screenId][7], 0);
			 MatrixState.rotate(90, 1, 0, 0);
			 kejiasu.drawSelf(goTexId);
			 MatrixState.popMatrix();
		 }
		 else
		 {
			 MatrixState.pushMatrix();
			 MatrixState.translate(Self_Adapter_Data_TRASLATE[screenId][6], Self_Adapter_Data_TRASLATE[screenId][7], 0);
			 MatrixState.rotate(90, 1, 0, 0);
			 bukejiasu.drawSelf(goTexId);
			 MatrixState.popMatrix();
		 }
		 //ɲ����ť
		 if(isShaChe)
		 {
			 MatrixState.pushMatrix();
			 MatrixState.translate(Self_Adapter_Data_TRASLATE[screenId][15], Self_Adapter_Data_TRASLATE[screenId][16], 0);
			 MatrixState.rotate(90, 1, 0, 0);
			 shache.drawSelf(shacheTexId);
			 MatrixState.popMatrix();
		 }
		 else
		 {
			 MatrixState.pushMatrix();
			 MatrixState.translate(Self_Adapter_Data_TRASLATE[screenId][15], Self_Adapter_Data_TRASLATE[screenId][16], 0);
			 MatrixState.rotate(90, 1, 0, 0);
			 noshache.drawSelf(shacheTexId);
			 MatrixState.popMatrix();
		 }
//		 //���ӽǰ�ť
//		 if(isFirstPersonView)
//		 {
//			 MatrixState.pushMatrix();
//			 MatrixState.translate(Self_Adapter_Data_TRASLATE[screenId][0], Self_Adapter_Data_TRASLATE[screenId][1], 0);
//			 MatrixState.rotate(90, 1, 0, 0);
//			 firstView.drawSelf(timeTexId);
//			 MatrixState.popMatrix();
//		 }
//		 else
//		 {
//			 MatrixState.pushMatrix();
//			 MatrixState.translate(Self_Adapter_Data_TRASLATE[screenId][0], Self_Adapter_Data_TRASLATE[screenId][1], 0);
//			 MatrixState.rotate(90, 1, 0, 0);
//			 thirdView.drawSelf(timeTexId);
//			 MatrixState.popMatrix();
//		 }
		 //��ͣ��ť
		 if(!isPaused)
		 {
			 MatrixState.pushMatrix();
			 MatrixState.translate(Self_Adapter_Data_TRASLATE[screenId][3], Self_Adapter_Data_TRASLATE[screenId][4], 0);
			 MatrixState.rotate(90, 1, 0, 0);
			 pauseButton.drawSelf(timeTexId);
			 MatrixState.popMatrix();
		 }
		 else
		 {
			 MatrixState.pushMatrix();
			 MatrixState.translate(Self_Adapter_Data_TRASLATE[screenId][3], Self_Adapter_Data_TRASLATE[screenId][4], 0);
			 MatrixState.rotate(90, 1, 0, 0);
			 resumeButton.drawSelf(timeTexId);
			 MatrixState.popMatrix();
		 }
	 }
	 
	 //ʱ�������
	 public void timeDrawSelf(int texId,int number)
	 {
		String scoreStr;
		if(timeTotal[number]<10)
		{
			scoreStr="0"+timeTotal[number]+"";
		}
		else
		{
			scoreStr=timeTotal[number]+"";
		}
		
		for(int i=0;i<scoreStr.length();i++)
		{
			char c=scoreStr.charAt(i);
			
			 MatrixState.pushMatrix();
	         MatrixState.translate(i*SHUZI_KUANDU, 0, 0);
	         MatrixState.rotate(90, 1, 0, 0);
	         shuzi[c-'0'].drawSelf(texId);		         
	         MatrixState.popMatrix();
		}
		if(number!=0)
		{
			MatrixState.pushMatrix();
	        MatrixState.translate(2*SHUZI_KUANDU+0.02f, 0, 0);
	        MatrixState.rotate(90, 1, 0, 0);
	        maohao.drawSelf(texId);		         
	        MatrixState.popMatrix();
		}		
	 }
	 //Ȧ��������
	 public void lapDrawSelf(int texId,int currLap)
	 {
		String curr=currLap+"";
		String total=maxOfTurns+"";
		
		char c=curr.charAt(0);
		MatrixState.pushMatrix();
        MatrixState.rotate(90, 1, 0, 0);
        shuzi[c-'0'].drawSelf(texId);
        MatrixState.popMatrix();
        
        c=total.charAt(0);
		MatrixState.pushMatrix();
		MatrixState.translate(SHUZI_KUANDU*2, 0, 0);
        MatrixState.rotate(90, 1, 0, 0);
        shuzi[c-'0'].drawSelf(texId);
        MatrixState.popMatrix();
	 }	 
	 //����������������
	 public void drawNumberOfN2(int texId,int number)
	 {
		 String numberStr=number+"";
		  
		 char c=numberStr.charAt(0);
		 MatrixState.pushMatrix();
		 MatrixState.rotate(90, 1, 0, 0);
		 shuzi[c-'0'].drawSelf(texId);
		 MatrixState.popMatrix();
	 }
	 
	//��������ڲ���
	class WenLiJuXing
	{
		int mProgram;//�Զ�����Ⱦ������ɫ������id 
	    int muMVPMatrixHandle;//�ܱ任��������id   
	    int muMMatrixHandle;//λ�á���ת�任����
	    int maCameraHandle; //�����λ����������id  
	    int maPositionHandle; //����λ����������id  
	    int maNormalHandle; //���㷨������������id  
	    int maTexCoorHandle; //��������������������id  
	    int maSunLightLocationHandle;//��Դλ����������id  
		
	    private FloatBuffer   mVertexBuffer;//�����������ݻ���
	    private FloatBuffer   mTextureBuffer;//������ɫ���ݻ���
	    int vCount;//��������
	    int texId;//����Id
			
	    public WenLiJuXing(float width,float height,float[] textures){//�����ߺ�������������
	    	//�����������ݵĳ�ʼ��================begin============================
	        vCount=6;//ÿ���������������Σ�ÿ��������3������        
	        float vertices[]=
	        {
        		-width/2,0,-height/2,
        		-width/2,0,height/2,
        		width/2,0,height/2,
        		
        		-width/2,0,-height/2,
        		width/2,0,height/2,
        		width/2,0,-height/2
	        };
	        //���������������ݻ���
	        //vertices.length*4����Ϊһ�������ĸ��ֽ�
	        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
	        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
	        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
	        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
	        mVertexBuffer.position(0);//���û�������ʼλ��
	        
	        //���������������ݻ���
	        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
	        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
	        mTextureBuffer= tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
	        mTextureBuffer.put(textures);//�򻺳����з��붥����ɫ����
	        mTextureBuffer.position(0);//���û�������ʼλ��

	        //���ض�����ɫ���Ľű�����
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
	    }
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
	        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
	        //�����������
	        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
		}
	}
}