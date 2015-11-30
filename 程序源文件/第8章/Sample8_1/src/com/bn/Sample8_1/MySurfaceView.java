package com.bn.Sample8_1;

import java.io.IOException;
import java.io.InputStream;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.GLES20;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.bn.Sample8_1.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class MySurfaceView extends GLSurfaceView {
    
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//角度缩放比例
	private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置X坐标
	
	private SceneRenderer mRenderer;//场景渲染器
    int textureId;      //系统分配的纹理id 
    
    boolean drawWhatFlag=true;	//绘制线填充方式的标志位
    boolean lightFlag=true;

	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(2); //设置使用OPENGL ES2.0
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
    }
	
	//触摸事件回调方法
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//计算触控笔Y位移
            float dx = x - mPreviousX;//计算触控笔X位移
            mRenderer.cylinder.yAngle += dx * TOUCH_SCALE_FACTOR;//设置绕y轴旋转角度
            mRenderer.cylinder.zAngle+= dy * TOUCH_SCALE_FACTOR;//设置绕z轴旋转角度
            
            mRenderer.cylinderl.yAngle += dx * TOUCH_SCALE_FACTOR;//设置绕y轴旋转角度
            mRenderer.cylinderl.zAngle+= dy * TOUCH_SCALE_FACTOR;//设置绕z轴旋转角度
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置
        return true;
    }
    
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
		
		Cylinder cylinder;
		CylinderL cylinderl;
		
        public void onDrawFrame(GL10 gl) 
        { 
        	//清除深度缓冲与颜色缓冲
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);   
            
            //保护现场
            MatrixState.pushMatrix();
            MatrixState.translate(0, 0, -10);
            if(drawWhatFlag)
            {
            	cylinder.drawSelf();
            }else
            {
            	cylinderl.drawSelf();
            }
            MatrixState.popMatrix();
            
        }   

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置 
        	GLES20.glViewport(0, 0, width, height); 
        	//计算GLSurfaceView的宽高比
            float ratio= (float) width / height;
            //调用此方法计算产生透视投影矩阵
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 4f, 100);
            //调用此方法产生摄像机9参数位置矩阵
            MatrixState.setCamera(0,0,8.0f,0f,0f,0f,0f,1.0f,0.0f); 
            
	        //初始化光源
	        MatrixState.setLightLocation(10 , 0 , -10);
	                      
	        //启动一个线程定时修改灯光的位置
	        new Thread()
	        {
				public void run()
				{
					float redAngle = 0;
					while(lightFlag)
					{	
						//根据角度计算灯光的位置
						redAngle=(redAngle+5)%360;
						float rx=(float) (15*Math.sin(Math.toRadians(redAngle)));
						float rz=(float) (15*Math.cos(Math.toRadians(redAngle)));
						MatrixState.setLightLocation(rx, 0, rz);
						
						try {
								Thread.sleep(100);
							} catch (InterruptedException e) {				  			
								e.printStackTrace();
							}
					}
				}
	        }.start();
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES20.glClearColor(0.0f,0.0f,0.0f, 1.0f);  
            //启用深度测试
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    		//设置为打开背面剪裁
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            //初始化变换矩阵
            MatrixState.setInitStack();
            //加载纹理
            textureId=initTexture(R.drawable.android_robot0);
            //创建圆柱对象
            cylinder = new Cylinder(MySurfaceView.this,1,1.2f,3.9f,36,textureId,textureId,textureId);
            //创建圆柱骨架对象
            cylinderl= new CylinderL(MySurfaceView.this,1,1.2f,3.9f,36);
            
        }
    }
	
	public int initTexture(int drawableId)//textureId
	{
		//生成纹理ID
		int[] textures = new int[1];
		GLES20.glGenTextures
		(
				1,          //产生的纹理id的数量
				textures,   //纹理id的数组
				0           //偏移量
		);    
		int textureId=textures[0];    
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
        
        //通过输入流加载图片===============begin===================
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
        //通过输入流加载图片===============end=====================  
        
        //实际加载纹理
        GLUtils.texImage2D
        (
        		GLES20.GL_TEXTURE_2D,   //纹理类型，在OpenGL ES中必须为GL10.GL_TEXTURE_2D
        		0, 					  //纹理的层次，0表示基本图像层，可以理解为直接贴图
        		bitmapTmp, 			  //纹理图像
        		0					  //纹理边框尺寸
        );
        bitmapTmp.recycle(); 		  //纹理加载成功后释放图片
        
        return textureId;
	}
}
