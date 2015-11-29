package com.bn.commonObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.bn.core.MatrixState;

import android.opengl.GLES20;

import static com.bn.gameView.Constant.*;
/*
 * ���ƴ��
 */
public class DamForDraw 
{
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id  
    FloatBuffer   mVertexBuffer;//�����������ݻ���
    FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount;
    public boolean isShaderOk;
	public DamForDraw(float height,float length1,float length2,float length3,int mProgram)
	{
		this.mProgram=mProgram;
		initData(height,length1,length2,length3);
	}
	//��ʼ���������Ϣ
	public void initData(float height,float length1,float length2,float length3)
	{
		float vertex[]=new float[(ArchieArray[mapId][5].length/2-1)*6*9*2];
		float texture[]=new float[(ArchieArray[mapId][5].length/2-1)*6*6*2];
		vCount=vertex.length/3;
		int t=0;
		int d=0;
		for(int i=0;i<ArchieArray[mapId][5].length/2-1;i++)
		{
			float x1,z1;
			float x2,z2;
			
			float texX,texY1,texY2,texY3,texY4;
			float spanx=1.0f/(ArchieArray[mapId][5].length/2-1);
			
			texX=spanx*i; 
			texY1=0;
			texY2=0.4f;
			texY3=0.6f;
			texY4=1;
			
			x1=ArchieArray[mapId][5][2*i]*WIDTH_LALNDFORM;
			x2=ArchieArray[mapId][5][2*(1+i)]*WIDTH_LALNDFORM;
			
			z1=ArchieArray[mapId][5][2*i+1]*WIDTH_LALNDFORM;
			z2=ArchieArray[mapId][5][2*(1+i)+1]*WIDTH_LALNDFORM;
			vertex[d++]=x1;//��һ��������
			vertex[d++]=0;
			vertex[d++]=z1-length1;
			
			vertex[d++]=x1;
			vertex[d++]=height;
			vertex[d++]=z1;
			
			vertex[d++]=x2;
			vertex[d++]=height;
			vertex[d++]=z2;
			
			texture[t++]=texX;
			texture[t++]=texY1;
			
			texture[t++]=texX;
			texture[t++]=texY2;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY2;
			
			vertex[d++]=x1;//��һ�������η���
			vertex[d++]=0;
			vertex[d++]=z1-length1;
					
			vertex[d++]=x2;
			vertex[d++]=-height;
			vertex[d++]=z2;
			
			vertex[d++]=x1;
			vertex[d++]=-height;
			vertex[d++]=z1;
			
			texture[t++]=texX;
			texture[t++]=texY1;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY2;
			
			texture[t++]=texX;
			texture[t++]=texY2;
			
			vertex[d++]=x1;//�ڶ���������
			vertex[d++]=0;
			vertex[d++]=z1-length1;
			
			vertex[d++]=x2;
			vertex[d++]=height;
			vertex[d++]=z2;
			
			vertex[d++]=x2;
			vertex[d++]=0;
			vertex[d++]=z2-length1;
			
			texture[t++]=texX;
			texture[t++]=texY1;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY2;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY1;
			
			vertex[d++]=x1;//�ڶ��������η���
			vertex[d++]=0;
			vertex[d++]=z1-length1;
			
			vertex[d++]=x2;
			vertex[d++]=0;
			vertex[d++]=z2-length1;
			
			vertex[d++]=x2;
			vertex[d++]=-height;
			vertex[d++]=z2;
			
			texture[t++]=texX;
			texture[t++]=texY1;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY1;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY2;
			
			vertex[d++]=x1;//������������
			vertex[d++]=height;
			vertex[d++]=z1;
			
			vertex[d++]=x1;
			vertex[d++]=height;
			vertex[d++]=z1+length2;
			
			vertex[d++]=x2;
			vertex[d++]=height;
			vertex[d++]=z2+length2;
			
			texture[t++]=texX;
			texture[t++]=texY2;
			
			texture[t++]=texX;
			texture[t++]=texY3;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY3;
			
			vertex[d++]=x1;//������������fanm����
			vertex[d++]=-height;
			vertex[d++]=z1;
			
			vertex[d++]=x2;
			vertex[d++]=-height;
			vertex[d++]=z2+length2;
			
			vertex[d++]=x1;
			vertex[d++]=-height;
			vertex[d++]=z1+length2;
			
			texture[t++]=texX;
			texture[t++]=texY2;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY3;
			
			texture[t++]=texX;
			texture[t++]=texY3;
			
			vertex[d++]=x1;//���ĸ�������
			vertex[d++]=height;
			vertex[d++]=z1;
			
			vertex[d++]=x2;
			vertex[d++]=height;
			vertex[d++]=z2+length2;
			
			vertex[d++]=x2;
			vertex[d++]=height;
			vertex[d++]=z2;
			
			texture[t++]=texX;
			texture[t++]=texY2;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY3;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY2;
			
			vertex[d++]=x1;//���ĸ������η���
			vertex[d++]=-height;
			vertex[d++]=z1;
			
			vertex[d++]=x2;
			vertex[d++]=-height;
			vertex[d++]=z2;
			
			vertex[d++]=x2;
			vertex[d++]=-height;
			vertex[d++]=z2+length2;
			
			texture[t++]=texX;
			texture[t++]=texY2;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY2;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY3;
			
			vertex[d++]=x1;//�����������
			vertex[d++]=height;
			vertex[d++]=z1+length2;
			
			vertex[d++]=x1;
			vertex[d++]=0;
			vertex[d++]=z1+length2+length3;
			
			vertex[d++]=x2;
			vertex[d++]=0;
			vertex[d++]=z2+length2+length3;
			
			texture[t++]=texX;
			texture[t++]=texY3;
			
			texture[t++]=texX;
			texture[t++]=texY4;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY4;
			
			vertex[d++]=x1;//����������η���
			vertex[d++]=-height;
			vertex[d++]=z1+length2;
			
			vertex[d++]=x2;
			vertex[d++]=0;
			vertex[d++]=z2+length2+length3;
			
			vertex[d++]=x1;
			vertex[d++]=0;
			vertex[d++]=z1+length2+length3;
			
			texture[t++]=texX;
			texture[t++]=texY3;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY4;
			
			texture[t++]=texX;
			texture[t++]=texY4;
			
			vertex[d++]=x1;//������������
			vertex[d++]=height;
			vertex[d++]=z1+length2;
			
			vertex[d++]=x2;
			vertex[d++]=0;
			vertex[d++]=z2+length2+length3;
			
			vertex[d++]=x2;
			vertex[d++]=height;
			vertex[d++]=z2+length2;
			
			texture[t++]=texX;
			texture[t++]=texY3;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY4;
			
			
			texture[t++]=texX+spanx;
			texture[t++]=texY3;
			
			vertex[d++]=x1;//�����������η���
			vertex[d++]=-height;
			vertex[d++]=z1+length2;
			
			vertex[d++]=x2;
			vertex[d++]=-height;
			vertex[d++]=z2+length2;
			
			vertex[d++]=x2;
			vertex[d++]=0;
			vertex[d++]=z2+length2+length3;

			texture[t++]=texX;
			texture[t++]=texY3;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY3;
			
			texture[t++]=texX+spanx;
			texture[t++]=texY4;
		}
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertex.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertex);//�򻺳����з��붥����������
        mVertexBuffer.position(0);
       
        ByteBuffer vbt=ByteBuffer.allocateDirect(texture.length*4);
        vbt.order(ByteOrder.nativeOrder());
        mTexCoorBuffer=vbt.asFloatBuffer();
        mTexCoorBuffer.put(texture);
        mTexCoorBuffer.position(0);
	}
	 public void initShader()
	  {   
		//��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
        //��ȡ�����ж�������������������id  
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
	   }
	//���Ʒ���
	public void drawSelf(int texId)
	{
		
		//ʹ��ĳ��ָ����Shader����
		GLES20.glUseProgram(mProgram);
		//�����ձ任�����뵽Shader������
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
		//������������
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
        //���Ƽ��ص�����
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);  
	}
}
