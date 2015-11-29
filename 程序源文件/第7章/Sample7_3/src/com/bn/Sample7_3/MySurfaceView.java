package com.bn.Sample7_3;
import java.io.IOException;
import java.io.InputStream;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.GLES20;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class MySurfaceView extends GLSurfaceView 
{
    private SceneRenderer mRenderer;//������Ⱦ��
    
    int currenttexId32;				//��ǰ�����id 
    int currenttexId256;				//��ǰ�����id 
    int[] texId = new int[8];          //ϵͳ���������id
    
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
    	TextureRect texRect; 	//������ζ��������
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            //����С�������
            MatrixState.pushMatrix();	//��ջ
            MatrixState.translate(0, 1.3f, 1);	//ƽ�� 
            MatrixState.rotate(-20, 0, 0, 1);	//��ת
            MatrixState.scale(0.3f, 0.3f, 0.3f); //����
            texRect.drawSelf(currenttexId256);	//����С�ı��� 
            MatrixState.popMatrix();//��ջ
            
            //���ƴ��������
            MatrixState.pushMatrix();	//��ջ
            MatrixState.translate(0, -0.6f, 1);	//ƽ��   
            MatrixState.rotate(-20, 0, 0, 1);	//��ת         
            texRect.drawSelf(currenttexId32);	//���ƴ��ı��� 
            MatrixState.popMatrix();//��ջ
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 1, 10);
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(0,0,3,0f,0f,0f,0f,1.0f,0.0f);
            //��ʼ����ת����
            MatrixState.setInitStack();
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES20.glClearColor(0.5f,0.5f,0.5f, 1.0f);  
            //����������ζ���
            texRect = new TextureRect(MySurfaceView.this);
            //����ȼ��
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //��ʼ��32*32���ص��ĸ�����
            texId[0] = initTexture(R.drawable.bw32,GLES20.GL_NEAREST,GLES20.GL_NEAREST);
            texId[1] = initTexture(R.drawable.bw32,GLES20.GL_LINEAR,GLES20.GL_LINEAR);
            texId[2] = initTexture(R.drawable.bw32,GLES20.GL_NEAREST,GLES20.GL_LINEAR);
            texId[3] = initTexture(R.drawable.bw32,GLES20.GL_LINEAR,GLES20.GL_NEAREST);
            //��ʼ��256*256���ص��ĸ�����
            texId[4] = initTexture(R.drawable.bw256,GLES20.GL_NEAREST,GLES20.GL_NEAREST);
            texId[5] = initTexture(R.drawable.bw256,GLES20.GL_LINEAR,GLES20.GL_LINEAR);
            texId[6] = initTexture(R.drawable.bw256,GLES20.GL_NEAREST,GLES20.GL_LINEAR);
            texId[7] = initTexture(R.drawable.bw256,GLES20.GL_LINEAR,GLES20.GL_NEAREST);
            
            currenttexId32 = texId[0];	//����Ĭ������
            currenttexId256 = texId[4];	//����Ĭ������
            //�رձ������   
            GLES20.glDisable(GLES20.GL_CULL_FACE);
        }
    }
		
	public int initTexture(int drawableId,float sample1,float sample2){
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
					
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,sample1);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,sample2);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
        
        //ͨ������������ͼƬ===============begin===================
        InputStream is = this.getResources().openRawResource(drawableId);
        Bitmap bitmapTmp;
        try {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally {
            try {
                is.close();
            } 
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        //ʵ�ʼ�������
        GLUtils.texImage2D
        (
        		GLES20.GL_TEXTURE_2D,   //�������ͣ���OpenGL ES�б���ΪGL10.GL_TEXTURE_2D
        		0, 					  //����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
        		bitmapTmp, 			  //����ͼ��
        		0					  //����߿�ߴ�
        );
        bitmapTmp.recycle(); 		  //������سɹ����ͷ�ͼƬ
        
        return textureId;
	}
}
