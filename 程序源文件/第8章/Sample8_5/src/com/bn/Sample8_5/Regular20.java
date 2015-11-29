package com.bn.Sample8_5;

import static com.bn.Sample8_5.ShaderUtil.createProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import android.opengl.GLES20;

/*
 * ����ʮ����
 * �����������ഹֱ�Ļƽ𳤷���
 */
public class Regular20 
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������
    int maPositionHandle; //����λ����������
    int maTexCoorHandle; //��������������������
    int muMMatrixHandle;
    
    int maCameraHandle; //�����λ���������� 
    int maNormalHandle; //���㷨������������ 
    int maLightLocationHandle;//��Դλ����������  
    
    
    String mVertexShader;//������ɫ������ű�  	 
    String mFragmentShader;//ƬԪ��ɫ������ű�
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
	FloatBuffer   mNormalBuffer;//���㷨�������ݻ���
    int vCount=0;   
    float xAngle=0;//��x����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
    
    
    float bHalf=0;//�ƽ𳤷��εĿ�
    float r=0;//��İ뾶
    
    public Regular20(MySurfaceView mv,float scale,float aHalf,int n)
    {
    	//���ó�ʼ���������ݵ�initVertexData����
    	initVertexData(scale,aHalf,n);
    	//���ó�ʼ����ɫ����intShader����
    	initShader(mv);
    }
    
    //�Զ���ĳ�ʼ���������ݵķ���
    public void initVertexData(float scale, float aHalf, int n) //��С���ƽ𳤷��γ��ߵ�һ�룬�ֶ���
	{
		aHalf*=scale;		//���ߵ�һ��
		bHalf=aHalf*0.618034f;		//�̱ߵ�һ��
		r=(float) Math.sqrt(aHalf*aHalf+bHalf*bHalf);
		vCount=3*20*n*n;//�������������20�������Σ�ÿ�������ζ�����������
		//��20�����������ݳ�ʼ��
		ArrayList<Float> alVertix20=new ArrayList<Float>();//��20����Ķ����б�δ���ƣ�
		ArrayList<Integer> alFaceIndex20=new ArrayList<Integer>();//��20������֯����Ķ��������ֵ�б�����ʱ����ƣ�
		//��20���嶥��
		initAlVertix20(alVertix20,aHalf,bHalf);
		//��20��������
		initAlFaceIndex20(alFaceIndex20);
		//������ƶ���
		float[] vertices20=VectorUtil.cullVertex(alVertix20, alFaceIndex20);//ֻ���㶥��

		//�������ݳ�ʼ��
		ArrayList<Float> alVertix=new ArrayList<Float>();//ԭ�����б�δ���ƣ�
		ArrayList<Integer> alFaceIndex=new ArrayList<Integer>();//��֯����Ķ��������ֵ�б�����ʱ����ƣ�
		int vnCount=0;//ǰi-1��ǰ���ж������ĺ�
		for(int k=0;k<vertices20.length;k+=9)//����20����ÿ����������ѭ��
		{
			float [] v1=new float[]{vertices20[k+0], vertices20[k+1], vertices20[k+2]};
			float [] v2=new float[]{vertices20[k+3], vertices20[k+4], vertices20[k+5]};
			float [] v3=new float[]{vertices20[k+6], vertices20[k+7], vertices20[k+8]};
			//����
			for(int i=0;i<=n;i++)
			{
				float[] viStart=VectorUtil.devideBall(r, v1, v2, n, i);
				float[] viEnd=VectorUtil.devideBall(r, v1, v3, n, i);
				for(int j=0;j<=i;j++)
				{
					float[] vi=VectorUtil.devideBall(r, viStart, viEnd, i, j);
					alVertix.add(vi[0]); alVertix.add(vi[1]); alVertix.add(vi[2]);
				}
			}
			//����
			for(int i=0;i<n;i++)
			{
				if(i==0){//���ǵ�0�У�ֱ�Ӽ�����ƺ󶥵�����012
					alFaceIndex.add(vnCount+0); alFaceIndex.add(vnCount+1);alFaceIndex.add(vnCount+2);
					vnCount+=1;
					if(i==n-1){//�����ÿ���������ε����һ��ѭ��������һ�еĶ������Ҳ����
						vnCount+=2;
					}
					continue;
				}
				int iStart=vnCount;//��i�п�ʼ������
				int viCount=i+1;//��i�ж�����
				int iEnd=iStart+viCount-1;//��i�н�������
				
				int iStartNext=iStart+viCount;//��i+1�п�ʼ������
				int viCountNext=viCount+1;//��i+1�ж�����
				int iEndNext=iStartNext+viCountNext-1;//��i+1�н���������
				//ǰ����ı���
				for(int j=0;j<viCount-1;j++)
				{
					int index0=iStart+j;//�ı��ε��ĸ���������
					int index1=index0+1;
					int index2=iStartNext+j;
					int index3=index2+1;
					alFaceIndex.add(index0); alFaceIndex.add(index2);alFaceIndex.add(index3);//����ǰ����ı���
					alFaceIndex.add(index0); alFaceIndex.add(index3);alFaceIndex.add(index1);				
				}// j
				alFaceIndex.add(iEnd); alFaceIndex.add(iEndNext-1);alFaceIndex.add(iEndNext); //���һ��������
				vnCount+=viCount;//��i��ǰ���ж������ĺ�
				if(i==n-1){//�����ÿ���������ε����һ��ѭ��������һ�еĶ������Ҳ����
					vnCount+=viCountNext;
				}
			}// i
		}// k
		
		//������ƶ���
		float[] vertices=VectorUtil.cullVertex(alVertix, alFaceIndex);//ֻ���㶥��
		float[] normals=vertices;//������Ƿ�����
		
		//����
		//��20���������������ݳ�ʼ��
		ArrayList<Float> alST20=new ArrayList<Float>();//��20��������������б�δ���ƣ�
		ArrayList<Integer> alTexIndex20=new ArrayList<Integer>();//��20������֯������������������ֵ�б�����ʱ����ƣ�
		//��20������������
		float sSpan=1/5.5f;//ÿ�����������εı߳�
		float tSpan=1/3.0f;//ÿ�����������εĸ�
		//������ʮ�����ƽ��չ��ͼ������������
		for(int i=0;i<5;i++){
			alST20.add(sSpan+sSpan*i); alST20.add(0f);
		}
		for(int i=0;i<6;i++){
			alST20.add(sSpan/2+sSpan*i); alST20.add(tSpan);
		}
		for(int i=0;i<6;i++){
			alST20.add(sSpan*i); alST20.add(tSpan*2);
		}
		for(int i=0;i<5;i++){
			alST20.add(sSpan/2+sSpan*i); alST20.add(tSpan*3);
		}
		//��20��������
		initAlTexIndex20(alTexIndex20);

		//���������������
		float[] st20=VectorUtil.cullTexCoor(alST20, alTexIndex20);//ֻ������������
		ArrayList<Float> alST=new ArrayList<Float>();//ԭ���������б�δ���ƣ�
		for(int k=0;k<st20.length;k+=6)
		{
			float [] st1=new float[]{st20[k+0], st20[k+1], 0};//�����ε���������
			float [] st2=new float[]{st20[k+2], st20[k+3], 0};
			float [] st3=new float[]{st20[k+4], st20[k+5], 0};
			for(int i=0;i<=n;i++)
			{
				float[] stiStart=VectorUtil.devideLine(st1, st2, n, i);
				float[] stiEnd=VectorUtil.devideLine(st1, st3, n, i);
				for(int j=0;j<=i;j++)
				{
					float[] sti=VectorUtil.devideLine(stiStart, stiEnd, i, j);
					//��������������б�
					alST.add(sti[0]); alST.add(sti[1]);
				}
			}
		}
		//������ƺ���������
		float[] textures=VectorUtil.cullTexCoor(alST, alFaceIndex);
		
		//�����������ݳ�ʼ��
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//���������������ݻ���
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //���������ݳ�ʼ��  
        ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length*4);//�������㷨�������ݻ���
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mNormalBuffer = nbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mNormalBuffer.put(normals);//�򻺳����з��붥�㷨��������
        mNormalBuffer.position(0);//���û�������ʼλ��
        //st�������ݳ�ʼ��		
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);//���������������ݻ���
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mTexCoorBuffer = tbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mTexCoorBuffer.put(textures);//�򻺳����з��붥����������
        mTexCoorBuffer.position(0);//���û�������ʼλ��
	}
    
    public void initAlVertix20(ArrayList<Float> alVertix20,float aHalf,float bHalf){
    	
		alVertix20.add(0f); alVertix20.add(aHalf); alVertix20.add(-bHalf);//������׶����
		
		alVertix20.add(0f); alVertix20.add(aHalf); alVertix20.add(bHalf);//�����ϵĵ�
		alVertix20.add(aHalf); alVertix20.add(bHalf); alVertix20.add(0f);
		alVertix20.add(bHalf); alVertix20.add(0f); alVertix20.add(-aHalf);
		alVertix20.add(-bHalf); alVertix20.add(0f); alVertix20.add(-aHalf);
		alVertix20.add(-aHalf); alVertix20.add(bHalf); alVertix20.add(0f);
		
		alVertix20.add(-bHalf); alVertix20.add(0f); alVertix20.add(aHalf);
		alVertix20.add(bHalf); alVertix20.add(0f); alVertix20.add(aHalf);
		alVertix20.add(aHalf); alVertix20.add(-bHalf); alVertix20.add(0f);
		alVertix20.add(0f); alVertix20.add(-aHalf); alVertix20.add(-bHalf);
		alVertix20.add(-aHalf); alVertix20.add(-bHalf); alVertix20.add(0f);
		
		alVertix20.add(0f); alVertix20.add(-aHalf); alVertix20.add(bHalf);//����׶����
		
    }
    
    public void initAlFaceIndex20(ArrayList<Integer> alFaceIndex20){ //��ʼ������ʮ����Ķ�����������
    	
		alFaceIndex20.add(0); alFaceIndex20.add(1); alFaceIndex20.add(2);
		alFaceIndex20.add(0); alFaceIndex20.add(2); alFaceIndex20.add(3);
		alFaceIndex20.add(0); alFaceIndex20.add(3); alFaceIndex20.add(4);
		alFaceIndex20.add(0); alFaceIndex20.add(4); alFaceIndex20.add(5);
		alFaceIndex20.add(0); alFaceIndex20.add(5); alFaceIndex20.add(1);
		
		alFaceIndex20.add(1); alFaceIndex20.add(6); alFaceIndex20.add(7);
		alFaceIndex20.add(1); alFaceIndex20.add(7); alFaceIndex20.add(2);
		alFaceIndex20.add(2); alFaceIndex20.add(7); alFaceIndex20.add(8);
		alFaceIndex20.add(2); alFaceIndex20.add(8); alFaceIndex20.add(3);
		alFaceIndex20.add(3); alFaceIndex20.add(8); alFaceIndex20.add(9);
		alFaceIndex20.add(3); alFaceIndex20.add(9); alFaceIndex20.add(4);
		alFaceIndex20.add(4); alFaceIndex20.add(9); alFaceIndex20.add(10);
		alFaceIndex20.add(4); alFaceIndex20.add(10); alFaceIndex20.add(5);
		alFaceIndex20.add(5); alFaceIndex20.add(10); alFaceIndex20.add(6);
		alFaceIndex20.add(5); alFaceIndex20.add(6); alFaceIndex20.add(1);
		
		alFaceIndex20.add(6); alFaceIndex20.add(11); alFaceIndex20.add(7);
		alFaceIndex20.add(7); alFaceIndex20.add(11); alFaceIndex20.add(8);
		alFaceIndex20.add(8); alFaceIndex20.add(11); alFaceIndex20.add(9);
		alFaceIndex20.add(9); alFaceIndex20.add(11); alFaceIndex20.add(10);
		alFaceIndex20.add(10); alFaceIndex20.add(11); alFaceIndex20.add(6);
    }
    public void initAlTexIndex20(ArrayList<Integer> alTexIndex20) //��ʼ������������������
    {
		alTexIndex20.add(0); alTexIndex20.add(5); alTexIndex20.add(6);
		alTexIndex20.add(1); alTexIndex20.add(6); alTexIndex20.add(7);
		alTexIndex20.add(2); alTexIndex20.add(7); alTexIndex20.add(8);
		alTexIndex20.add(3); alTexIndex20.add(8); alTexIndex20.add(9);
		alTexIndex20.add(4); alTexIndex20.add(9); alTexIndex20.add(10);
		
		alTexIndex20.add(5); alTexIndex20.add(11); alTexIndex20.add(12);
		alTexIndex20.add(5); alTexIndex20.add(12); alTexIndex20.add(6);
		alTexIndex20.add(6); alTexIndex20.add(12); alTexIndex20.add(13);
		alTexIndex20.add(6); alTexIndex20.add(13); alTexIndex20.add(7);
		alTexIndex20.add(7); alTexIndex20.add(13); alTexIndex20.add(14);
		alTexIndex20.add(7); alTexIndex20.add(14); alTexIndex20.add(8);
		alTexIndex20.add(8); alTexIndex20.add(14); alTexIndex20.add(15);
		alTexIndex20.add(8); alTexIndex20.add(15); alTexIndex20.add(9);
		alTexIndex20.add(9); alTexIndex20.add(15); alTexIndex20.add(16);
		alTexIndex20.add(9); alTexIndex20.add(16); alTexIndex20.add(10);
		
		alTexIndex20.add(11); alTexIndex20.add(17); alTexIndex20.add(12);
		alTexIndex20.add(12); alTexIndex20.add(18); alTexIndex20.add(13);
		alTexIndex20.add(13); alTexIndex20.add(19); alTexIndex20.add(14);
		alTexIndex20.add(14); alTexIndex20.add(20); alTexIndex20.add(15);
		alTexIndex20.add(15); alTexIndex20.add(21); alTexIndex20.add(16);
    	
    }
    
    
    
    
    
    

    //�Զ����ʼ����ɫ��initShader����
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex_tex_light.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag_tex_light.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�������������������id  
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix"); 
        
        
        //��ȡ�����ж��㷨������������id  
        maNormalHandle= GLES20.glGetAttribLocation(mProgram, "aNormal"); 
        //��ȡ�����������λ������id
        maCameraHandle=GLES20.glGetUniformLocation(mProgram, "uCamera"); 
        //��ȡ�����й�Դλ������id
        maLightLocationHandle=GLES20.glGetUniformLocation(mProgram, "uLightLocation"); 
        //��ȡλ�á���ת�任��������id
        muMMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMMatrix");  
        
        
    }
    
    public void drawSelf(int texId)
    {
    	 MatrixState.rotate(xAngle, 1, 0, 0);
    	 MatrixState.rotate(yAngle, 0, 1, 0);
    	 MatrixState.rotate(zAngle, 0, 0, 1);
    	
    	 //�ƶ�ʹ��ĳ��shader����
    	 GLES20.glUseProgram(mProgram);        
         
         //�����ձ任������shader����
         GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
         
         //��λ�á���ת�任������shader����
         GLES20.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0); 
         //�������λ�ô���shader����   
         GLES20.glUniform3fv(maCameraHandle, 1, MatrixState.cameraFB);
         //����Դλ�ô���shader����   
         GLES20.glUniform3fv(maLightLocationHandle, 1, MatrixState.lightPositionFB);
         
         
         //���Ͷ���λ������
         GLES20.glVertexAttribPointer  
         (
         		maPositionHandle,   
         		3, 
         		GLES20.GL_FLOAT, 
         		false,
                3*4,   
                mVertexBuffer
         );       
         //���Ͷ���������������
         GLES20.glVertexAttribPointer  
         (
        		maTexCoorHandle, 
         		2, 
         		GLES20.GL_FLOAT, 
         		false,
                2*4,   
                mTexCoorBuffer
         ); 
         //���Ͷ��㷨��������
         GLES20.glVertexAttribPointer  
         (
        		maNormalHandle, 
         		4, 
         		GLES20.GL_FLOAT, 
         		false,
                3*4,   
                mNormalBuffer
         ); 
         
         //���ö���λ������
         GLES20.glEnableVertexAttribArray(maPositionHandle);
         //���ö�����������
         GLES20.glEnableVertexAttribArray(maTexCoorHandle);  
         //���ö��㷨��������
         GLES20.glEnableVertexAttribArray(maNormalHandle);
         
         
         //������
         GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
         GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
         
         //�����������
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
    }
}
