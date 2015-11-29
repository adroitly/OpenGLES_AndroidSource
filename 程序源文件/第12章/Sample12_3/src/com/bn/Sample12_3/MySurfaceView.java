package com.bn.Sample12_3;
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
import static com.bn.Sample12_3.Constant.*;

class MySurfaceView extends GLSurfaceView   
{
    private SceneRenderer mRenderer;//������Ⱦ��    
    int textureFloor;//ϵͳ����Ĳ�͸���ذ�����id
    int textureFloorBTM;//ϵͳ����İ�͸���ذ�����id
    int textureBallId;//ϵͳ�������������id
	 
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        //this.setEGLConfigChooser(8, 8, 8, 8, 16, 8);
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
    	TextureRect texRect;//��ʾ�ذ���������
    	BallTextureByVertex btbv;//���ڻ��Ƶ���
    	BallForControl bfd;//���ڿ��Ƶ���
    	
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
             
            MatrixState.pushMatrix();
            MatrixState.translate(0, -2, 0);
            
            //���ģ�建��
            GLES20.glClear(GLES20.GL_STENCIL_BUFFER_BIT);
            //����ģ�����
            GLES20.glEnable(GLES20.GL_STENCIL_TEST);
            //����ģ����Բ���
            GLES20.glStencilFunc(GLES20.GL_ALWAYS, 1, 1);
            //����ģ����Ժ�Ĳ���
            GLES20.glStencilOp(GLES20.GL_KEEP, GLES20.GL_KEEP, GLES20.GL_REPLACE);            
            //���Ʒ�����ذ�
            texRect.drawSelf(textureFloor);  
            
            //����ģ����Բ���
            GLES20.glStencilFunc(GLES20.GL_EQUAL,1, 1); 
            //����ģ����Ժ�Ĳ���
            GLES20.glStencilOp(GLES20.GL_KEEP, GLES20.GL_KEEP, GLES20.GL_KEEP);            
            //���ƾ�����
            bfd.drawSelfMirror( textureBallId);
            //����ģ�����
            GLES20.glDisable(GLES20.GL_STENCIL_TEST);
            
            //���ư�͸���ذ�
            //�������
            GLES20.glEnable(GLES20.GL_BLEND);
            //���û������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            texRect.drawSelf(textureFloorBTM);    
            //�رջ��
            GLES20.glDisable(GLES20.GL_BLEND);  
            //����ʵ������ 
            bfd.drawSelf(textureBallId);  
            MatrixState.popMatrix();   
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) 
        {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 3, 100);
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(0.0f,8.0f,8.0f,0,0f,0,0,1,0);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);  
            //����������ζԶ��� 
            texRect=new TextureRect(MySurfaceView.this,4,2.568f);  
            //�������ڻ��Ƶ��������
            btbv=new BallTextureByVertex(MySurfaceView.this,BALL_SCALE);
            //�������ڿ��Ƶ��������
            bfd=new BallForControl(btbv,3f);
            //�ر���ȼ��
            GLES20.glDisable(GLES20.GL_DEPTH_TEST);
            //��ʼ������
            textureFloor=initTexture(R.drawable.mdb);
            textureFloorBTM=initTexture(R.drawable.mdbtm);
            textureBallId=initTexture(R.drawable.basketball);            
            //�򿪱������   
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            //��ʼ���任����
            MatrixState.setInitStack();
        }
    }
	
	public int initTexture(int drawableId)//textureId
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
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
        
        //ͨ������������ͼƬ===============begin===================
        InputStream is = this.getResources().openRawResource(drawableId);
        Bitmap bitmapTmp;
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
        //ͨ������������ͼƬ===============end=====================  
        
        //ʵ�ʼ�������
        GLUtils.texImage2D
        (
        		GLES20.GL_TEXTURE_2D,   //�������ͣ���OpenGL ES�б���ΪGLES20.GL_TEXTURE_2D
        		0, 					  //����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
        		bitmapTmp, 			  //����ͼ��
        		0					  //����߿�ߴ�
        );
        bitmapTmp.recycle(); 		  //������سɹ����ͷ�ͼƬ
        return textureId;
	}
}
