package com.bn.Sample5_1;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.opengl.GLES20;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;

class MySurfaceView extends GLSurfaceView 
{
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��
	 
	private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��X����
	
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }
	
	//�����¼��ص�����
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//���㴥�ر�Yλ��
            float dx = x - mPreviousX;//���㴥�ر�Xλ��            
            for(SixPointedStar h:mRenderer.ha)
            {
            	h.yAngle += dx * TOUCH_SCALE_FACTOR;//���������������еĸ�����������y����ת�Ƕ�
                h.xAngle+= dy * TOUCH_SCALE_FACTOR;//���������������еĸ�����������x����ת�Ƕ�
            }
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
    	SixPointedStar[] ha=new SixPointedStar[6];//����������
    	
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            //���������������еĸ���������
            for(SixPointedStar h:ha)
            {
            	h.drawSelf();
            }
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
        	float ratio= (float) width / height;
            //����ƽ��ͶӰ
        	MatrixState.setProjectOrtho(-ratio, ratio, -1, 1, 1, 10); 
        	
            //���ô˷������������9����λ�þ���
			MatrixState.setCamera(
					0, 0, 3f, 
					0, 0, 0f, 
					0f, 1.0f, 0.0f
					);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES20.glClearColor(0.5f,0.5f,0.5f, 1.0f);  
            //���������������еĸ������� 
            for(int i=0;i<ha.length;i++)
            {
            	ha[i]=new SixPointedStar(MySurfaceView.this,0.2f,0.5f,-0.3f*i);   
            }            
            //����ȼ��
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        }
    }
}
