package com.bn.Sample8_3;

import static com.bn.Sample8_3.ShaderUtil.createProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import android.opengl.GLES20;

/*
 * Բ��
 */
public class Torus 
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������
    int maPositionHandle; //����λ����������
    int maTexCoorHandle; //��������������������
    
    String mVertexShader;//������ɫ������ű�  	 
    String mFragmentShader;//ƬԪ��ɫ������ű�
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���

    int vCount=0;   
    float xAngle=0;//��x����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
    
    public Torus(MySurfaceView mv,float rBig, float rSmall,int nCol ,int nRow)
    {
    	//���ó�ʼ���������ݵ�initVertexData����
    	initVertexData(rBig,rSmall,nCol,nRow);
    	//���ó�ʼ����ɫ����intShader����
    	initShader(mv);
    }
    
    //�Զ���ĳ�ʼ���������ݵķ���
    public void initVertexData(
			float rBig, float rSmall,//Բ���⾶���ھ�
			int nCol ,int nRow) {//����������
		//��Ա������ʼ��
		float angdegColSpan=360.0f/nCol;
		float angdegRowSpan=360.0f/nRow;
		float A=(rBig-rSmall)/2;//������ת��СԲ�뾶
		float D=rSmall+A;//��ת�켣�γɵĴ�Բ�ܰ뾶
		vCount=3*nCol*nRow*2;//�������������nColumn*nRow*2�������Σ�ÿ�������ζ�����������
		//�������ݳ�ʼ��
		ArrayList<Float> alVertix=new ArrayList<Float>();//ԭ�����б�δ���ƣ�
		ArrayList<Integer> alFaceIndex=new ArrayList<Integer>();//��֯����Ķ��������ֵ�б�����ʱ����ƣ�		
		//����
		for(float angdegCol=0;Math.ceil(angdegCol)<360+angdegColSpan;
		angdegCol+=angdegColSpan)	{
			double a=Math.toRadians(angdegCol);//��ǰСԲ�ܻ���
			for(float angdegRow=0;Math.ceil(angdegRow)<360+angdegRowSpan;angdegRow+=angdegRowSpan)//�ظ���һ�ж��㣬�����������ļ���
			{
				double u=Math.toRadians(angdegRow);//��ǰ��Բ�ܻ���
				float y=(float) (A*Math.cos(a));
				float x=(float) ((D+A*Math.sin(a))*Math.sin(u));
				float z=(float) ((D+A*Math.sin(a))*Math.cos(u));
				//�����������XYZ��������Ŷ��������ArrayList
        		alVertix.add(x); alVertix.add(y); alVertix.add(z);
			}
		}				
		//����
		for(int i=0;i<nCol;i++){
			for(int j=0;j<nRow;j++){
				int index=i*(nRow+1)+j;
				//��������
				alFaceIndex.add(index+1);//��һ��---1
				alFaceIndex.add(index+nRow+1);//��һ��---2
				alFaceIndex.add(index+nRow+2);//��һ����һ��---3
				
				alFaceIndex.add(index+1);//��һ��---1
				alFaceIndex.add(index);//��ǰ---0
				alFaceIndex.add(index+nRow+1);//��һ��---2
			}
		}
		float[] vertices=new float[vCount*3];
		//������ƺ�Ķ�������
		cullVertex(alVertix, alFaceIndex, vertices);
		
		//����
		ArrayList<Float> alST=new ArrayList<Float>();//ԭ���������б�δ���ƣ�
		for(float angdegCol=0;Math.ceil(angdegCol)<360+angdegColSpan;angdegCol+=angdegColSpan)
		{
			float t=angdegCol/360;//t����
			for(float angdegRow=0;Math.ceil(angdegRow)<360+angdegRowSpan;angdegRow+=angdegRowSpan)//�ظ���һ���������꣬�������ļ���
			{
				float s=angdegRow/360;//s����
				//�����������ST��������Ŷ��������ArrayList
				alST.add(s); alST.add(t);
			}
		}
		//������ƺ���������
		float[] textures=cullTexCoor(alST, alFaceIndex);
		
		//�����������ݳ�ʼ��
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//���������������ݻ���
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��

        //st�������ݳ�ʼ��		
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);//���������������ݻ���
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mTexCoorBuffer = tbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mTexCoorBuffer.put(textures);//�򻺳����з��붥����������
        mTexCoorBuffer.position(0);//���û�������ʼλ��
	}
    
	//ͨ��ԭ������������ֵ���õ��ö�����Ƶ�����
	public static void cullVertex(
			ArrayList<Float> alv,//ԭ�����б�δ���ƣ�
			ArrayList<Integer> alFaceIndex,//��֯����Ķ��������ֵ�б�����ʱ����ƣ�
			float[] vertices//�ö�����Ƶ����飨����������������У����鳤��Ӧ���������б��ȵ�3����
		){
		//���ɶ��������
		int vCount=0;
		for(int i:alFaceIndex){
			vertices[vCount++]=alv.get(3*i);
			vertices[vCount++]=alv.get(3*i+1);
			vertices[vCount++]=alv.get(3*i+2);
		}
	}
	//����ԭ���������������������ƺ������ķ���
	public static float[] cullTexCoor(
			ArrayList<Float> alST,//ԭ���������б�δ���ƣ�
			ArrayList<Integer> alTexIndex//��֯������������������ֵ�б�����ʱ����ƣ�
			)
	{
		float[] textures=new float[alTexIndex.size()*2];
		//���ɶ��������
		int stCount=0;
		for(int i:alTexIndex){
			textures[stCount++]=alST.get(2*i);
			textures[stCount++]=alST.get(2*i+1);
		}
		return textures;
	}

    //�Զ����ʼ����ɫ��initShader����
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex_tex.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag_tex.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�������������������id  
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix"); 
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
         
         //���ö���λ������
         GLES20.glEnableVertexAttribArray(maPositionHandle);
         //���ö�����������
         GLES20.glEnableVertexAttribArray(maTexCoorHandle);  
         
         //������
         GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
         GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
         
         //�����������
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
         
    }
}
