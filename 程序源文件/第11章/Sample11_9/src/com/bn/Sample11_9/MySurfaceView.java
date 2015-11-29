package com.bn.Sample11_9;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

public class MySurfaceView extends GLSurfaceView
{
	SceneRenderer mRender;
	public MySurfaceView(Context context)
	{
		super(context);
		this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        mRender = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRender);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ 
	}
	
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {
		TextRect tRect;
		int wlWidth=512;//����������
		int wlHeight=512;//��������߶�
		long timeStamp=System.currentTimeMillis();
		int texId=-1;
		@Override
		public void onDrawFrame(GL10 gl)
		{
			long tts=System.currentTimeMillis();
        	if(tts-timeStamp>500)
        	{
        		timeStamp=tts;
        		FontUtil.cIndex=(FontUtil.cIndex+1)%FontUtil.content.length;
            	FontUtil.updateRGB();
        	}
        	
        	if(texId!=-1)
        	{
        		GLES20.glDeleteTextures(1, new int[]{texId}, 0);
        	}
        	//������������
        	Bitmap bm=FontUtil.generateWLT(FontUtil.getContent(FontUtil.cIndex, FontUtil.content), wlWidth, wlHeight);
        	texId=initTexture(bm);
        	
			//�����Ȼ�������ɫ����
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            
            MatrixState.pushMatrix();
            MatrixState.translate(0, 0, -2);
            tRect.drawSelf(texId);
            MatrixState.popMatrix();
		}
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			//�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectOrtho(-ratio, ratio, -1, 1, 1, 100);
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(0,0,1,0,0,0,0f,1.0f,0.0f);
		}
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			//������Ļ����ɫRGBA
            GLES20.glClearColor(0,0,0,1.0f);
            //����ȼ��
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //�򿪱������
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            
            MatrixState.setInitStack();
            tRect=new TextRect(MySurfaceView.this);
		}
    }
	//���������id
	public int initTexture(Bitmap bitmap)
	{
		//��������ID
		int[] textures = new int[1];
		GLES20.glGenTextures
		(
				1,          //����������id������
				textures,   //����id������
				0           //ƫ����
		);    
		int textureId=textures[0];    
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_REPEAT);
        
        //ʵ�ʼ�������
        GLUtils.texImage2D
        (
        		GLES20.GL_TEXTURE_2D,   //�������ͣ���OpenGL ES�б���ΪGL10.GL_TEXTURE_2D
        		0, 					  //����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
        		bitmap, 			  //����ͼ��
        		0					  //����߿�ߴ�
        );
        bitmap.recycle(); 		  //������سɹ����ͷ�ͼƬ
        return textureId;
	}
}