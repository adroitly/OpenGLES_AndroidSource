package com.bn.st.xc;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.bn.R;
import com.bn.clp.BoatInfo;
import com.bn.core.MatrixState;
import com.bn.st.d2.MyActivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;
import static com.bn.st.xc.Constant.*;

public class XCSurfaceView extends GLSurfaceView 
{
	final float TOUCH_SCALE_FACTOR = 180.0f/SCREEN_WIDTH;//�Ƕ����ű���
    SceneRenderer mRenderer;//������Ⱦ��
    float mPreviousX;//�ϴεĴ���λ��X����
    float mPreviousY;//�ϴεĴ���λ��Y����

    int tex_index=0;//�������id����   
    
    int textureUpId;//ϵͳ�������Ϸǰ�����ⰴť����id
    int textureDownId;//ϵͳ�������Ϸ�������ⰴť����id
    int texWallId[]=new int[3];  //���ǽ����Id����
    int texFloorId;//��������id
    
    //��������id����
    int[] heroBoatTexId;
    int[] quickBoatTexId;
    int[] slowBoatTexId;
    
    //��������ͼƬ����
    static Bitmap[] heroBoatTexBitmap;
    static Bitmap[] quickBoatTexBitmap;
    static Bitmap[] slowBoatTexBitmap;
	
    static Bitmap bmUp;//ǰ�����ⰴť
    static Bitmap bmDown;//�������ⰴť
    static Bitmap[] bmaWall=new Bitmap[3];//���ǽ��������
    static Bitmap bmFloor;//����
    
	private float yAngle=0;//��Y��ת����
	private float xAngle=20;//����
	//���������
	private float cx;
	private float cz;
	private float cy;
	//Ŀ�������
	private float tx=0;
	private float tz=0;
	private float ty=-HOUSE_GAO/3;
	//�����ͷ��ָ��
	private float upX=0;
	private float upY=1;
	private float upZ=0;
	//----��������----------------------
	HouseForDraw house;//����
	DisplayStation displayStation;//չ̨
	Boat boat[]=new Boat[3];//��
	TextureRect button;//��ť
	LoadedObjectVertexNormalXC rome;//������������
	
	private float half_width_button=0.15f;
//	private float half_height_button=0.1f;
	private float offset_X_Button1=-0.7f;
	private float offset_Y_Button1=-0.8f;
	private float offset_X_Button2=0.7f;
	private float offset_Y_Button2=-0.8f;
	//��ť�ķ�Χ
	private float[] button1;
	private float[] button2;
	
	public boolean flagForThread=true;//�̱߳�־
	
	public int index_boat=0;//��������
	private float ratio;//����ͶӰ�������ű���
	
	//--------���Թ���---------------
	private boolean flag_go;//������Ա�־λ 
	final int countGoStepInitValue=35;
	private int countGoStep=0;
	private float acceleratedVelocity=0.06f;
	private float ori_angle_speed=7;//��ʼ���ٶ�
	private float curr_angle_speed;//��ǰ���ٶ�
	private boolean isMoved;//�ж��Ƿ��ƶ���
	//---------����ǽǽ�ϵĹ��---------------
	public boolean flag_display=true;//����־λ
	public XCSurfaceView(Context context)
	{
        super(context);
        this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
        this.setKeepScreenOn(true);
        cx=(float)(tx+Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle))*XC_DISTANCE);//�����x���� 
        cz=(float)(tz+Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle))*XC_DISTANCE);//�����z���� 
        cy=(float)(ty+Math.sin(Math.toRadians(xAngle))*XC_DISTANCE);//�����y���� 
    }
	//�����¼��ص�����
	@Override 
    public boolean onTouchEvent(MotionEvent e) 
	{
        float y = e.getY();//�õ����µ�XY����
        float x = e.getX();
        float domain_span=5;
        switch (e.getAction())
        {
        case MotionEvent.ACTION_MOVE:
            float dx = x - mPreviousX;//���㴥�ر�Xλ��
			float dy = y-  mPreviousY;//
        	yAngle -= dx * TOUCH_SCALE_FACTOR;//
        	xAngle -= dy * TOUCH_SCALE_FACTOR/2.0f;//���Ǹı�
        	if(xAngle>45)//���ǵ����ֵ
        	{
        		xAngle=45;
        	}
        	if(xAngle<10){//���ǵ���Сֵ
        		xAngle=10;
        	}

            if(Math.abs(dx)>domain_span)//ȷ��һ����ֵ
            {
	            isMoved=true;//��ʾ��ǰ�ƶ���
	            //���õ�ǰ���ٶ�
	            curr_angle_speed=ori_angle_speed*(-dx/SCREEN_WIDTH);
	            if(dx>20||dx<-20)
	            {
	            	if(curr_angle_speed>0)
		            {
		            	curr_angle_speed=curr_angle_speed+ori_angle_speed;
		            }  
		            else
		            {
		            	curr_angle_speed=curr_angle_speed-ori_angle_speed;
		            }
	            }
	            
	            //���ü��ٶȴ�С
	            acceleratedVelocity=0.03f;
	            //����ǰ���ٶȴ���������ٶ���Ϊ��
	            if(curr_angle_speed>0)
	            {
	            	acceleratedVelocity=-0.03f;
	            }
            }
            cx=(float)(tx+Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle))*XC_DISTANCE);//�����x���� 
            cz=(float)(tz+Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle))*XC_DISTANCE);//�����z���� 
            cy=(float)(ty+Math.sin(Math.toRadians(xAngle))*XC_DISTANCE);//�����y����
            break;
            
        case MotionEvent.ACTION_DOWN :
        	flag_go=false;
        	if(x>button1[0]&&x<button1[1]&&y>button1[2]&&y<button1[3])
        	{
        		index_boat=(index_boat-1+3)%3;
        	}
        	if(x>button2[0]&&x<button2[1]&&y>button2[2]&&y<button2[3])
        	{
        		index_boat=(index_boat+1)%3;
        	}
        	BoatInfo.cuttBoatIndex=index_boat;
        	break;
        case MotionEvent.ACTION_UP: 
        	if(isMoved)
        	{
        		flag_go=true;
        		countGoStep=countGoStepInitValue;
        		isMoved=false;
        	}
        	break;
        }
        mPreviousX = x;//��¼���ر�λ��   
        mPreviousY=y;
        cx=(float)(tx+Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle))*XC_DISTANCE);//�����x���� 
        cz=(float)(tz+Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle))*XC_DISTANCE);//�����z���� 
        cy=(float)(ty+Math.sin(Math.toRadians(xAngle))*XC_DISTANCE);//�����y����
        return true;
    }
	
	//������LoadedObjectVertexNormal�б�
	static LoadedObjectVertexTexXC[][] parts=new LoadedObjectVertexTexXC[3][];
	static 
	{
		parts[0]=new LoadedObjectVertexTexXC[BoatInfo.boatPartNames[0].length];
		parts[1]=new LoadedObjectVertexTexXC[BoatInfo.boatPartNames[1].length];
		parts[2]=new LoadedObjectVertexTexXC[BoatInfo.boatPartNames[2].length];
	}
	
	static LoadedObjectVertexNormalXC romeData;
	
	public static void loadVertexFromObj(Resources r)
	{
		romeData=LoadUtilXC.loadFromFileVertexOnly("rome.obj",r, 1f, 1f, 1f);
		
		for(int j=0;j<BoatInfo.boatPartNames.length;j++)
		{
			for(int i=0;i<BoatInfo.boatPartNames[j].length;i++)  
			{  
				parts[j][i]=LoadUtilTexXC.loadFromFileVertexOnly
				(
					BoatInfo.boatPartNames[j][i], 
					r,
					ShaderManager.getCommTextureShaderProgram()
			     );			
			}
		}
	}
	
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {
    	//long olds;
    	//long currs;
        public void onDrawFrame(GL10 gl) 
        { 
        	//currs=System.nanoTime();			
			//System.out.println(1000000000.0/(currs-olds)+"FPS");
			//olds=currs;
        	 
        	//�����Ȼ�������ɫ����
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT); 
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 2.5f, 1000);           
            MatrixState.setCamera(cx,cy,cz,tx,ty,tz,upX,upY,upZ);
            MatrixState.copyMVMatrix();
            //��ʼ����Դλ��
            MatrixState.setLightLocation(cx, cy+5, cz+3);            
            //--------------------------���ƴ���ǽ���ϵĵ�Ӱ-------------------------------------------            
            drawBoatMirrorOnWall();
           
            //�ڻ�͸��ǽ
            GLES20.glEnable(GLES20.GL_BLEND);//�������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            house.drawTransparentWall();
            GLES20.glDisable(GLES20.GL_BLEND); //�رջ��
            
            house.drawFloor(texFloorId);//���Ƶذ�
            //���ƹ��ǽ
            house.drawTexWall(texWallId,tex_index);
            
            //--------------------------���ƴ���չ̨�ϵĵ�Ӱ-------------------------------------------
            //����չ̨�Ĳ�͸����Բ��
            MatrixState.pushMatrix();
            MatrixState.translate(0, -Constant.HOUSE_GAO/2, 0);
            displayStation.drawSelfCylinder();
            MatrixState.popMatrix();
            
            //���ƴ���չ̨�ϵĵ�Ӱ
            MatrixState.pushMatrix();
            drawBoatShadow();
            MatrixState.popMatrix(); 
            
            //����͸��Բ��,�������
            GLES20.glEnable(GLES20.GL_BLEND);//�������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);//���û������
            displayStation.drawTransparentCircle();
            GLES20.glDisable(GLES20.GL_BLEND); //�رջ��            
            
            //������ʵ��
            drawBoat();
            //��������
            drawRomeColumn();
            //������һ����һ����ť
            drawButton();
        }  
        public void onSurfaceChanged(GL10 gl, int width, int height)
        {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            ratio = (float) width / height;            
            virtualButton();
            new Thread()
            {
				@Override
            	public void run()
            	{
            		while(flagForThread)
            		{
            			try 
            			{
            				//������й��Բ���
            				if(flag_go)//����������
            				{
            					countGoStep--;
            					if(countGoStep<=0)
            					{
            						curr_angle_speed=curr_angle_speed+acceleratedVelocity;//���㵱ǰ���ٶ�
            					}
            					
            					if(Math.abs(curr_angle_speed)>0.1f)
            					{
            						yAngle=yAngle+curr_angle_speed;
                					cx=(float)(tx+Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle))*XC_DISTANCE);//�����x���� 
                				    cz=(float)(tz+Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle))*XC_DISTANCE);//�����z���� 
                				    cy=(float)(ty+Math.sin(Math.toRadians(xAngle))*XC_DISTANCE);//�����y���� 
            					}
            					else
            					{
            						curr_angle_speed=0;
            						flag_go=false;
            					}
            				}
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
        
        
        public void onSurfaceCreated(GL10 gl, EGLConfig config) 
        {	    
        	synchronized(MyActivity.boatInitLock)
        	{        		
            	ShaderManager.compileShaderReal();            	
            	textureUpId=initTextureFromBitmap(bmUp);//�ϰ�ť
                textureDownId=initTextureFromBitmap(bmDown);//�°�ť
                texWallId[0]=initTextureFromBitmap(bmaWall[0]);//�������ͼ1    
                texWallId[1]=initTextureFromBitmap(bmaWall[1]);//�������ͼ2
                texWallId[2]=initTextureFromBitmap(bmaWall[2]);//�������ͼ3
                texFloorId=initTextureFromBitmap(bmFloor);//��������ͼ3   
                
                heroBoatTexId=new int[heroBoatTexBitmap.length];
                quickBoatTexId=new int[quickBoatTexBitmap.length];
                slowBoatTexId=new int[slowBoatTexBitmap.length];
                
                for(int i=0;i<heroBoatTexBitmap.length;i++)
                {
                	heroBoatTexId[i]=initTextureFromBitmap(heroBoatTexBitmap[i]);
                }
                
                for(int i=0;i<quickBoatTexBitmap.length;i++)
                {
                	quickBoatTexId[i]=initTextureFromBitmap(quickBoatTexBitmap[i]);
                }
                
                for(int i=0;i<slowBoatTexBitmap.length;i++)
                {
                	slowBoatTexId[i]=initTextureFromBitmap(slowBoatTexBitmap[i]);
                }
            	
                //������Ļ����ɫRGBA
                GLES20.glClearColor(1f,1f,1f, 1.0f);
                                
                //��������==========================================================
                house=new HouseForDraw();//��������
                displayStation=new DisplayStation(RADIUS_DISPLAY, LENGTH_DISPLAY);//����չ̨
                boat[0]=new Boat(parts[0],XCSurfaceView.this);//������ֻltf
                boat[1]=new Boat(parts[1],XCSurfaceView.this);//������ֻcjg
                boat[2]=new Boat(parts[2],XCSurfaceView.this);//������ֻpjh
            	button=new TextureRect(ShaderManager.getCommTextureShaderProgram(), half_width_button, XC_Self_Adapter_Data_TRASLATE[com.bn.clp.Constant.screenId][0]);//������ذ�ť
            	
             	//��������������
            	romeData.initShader(ShaderManager.getColorshaderProgram());
            	rome=romeData;
            	//�����������   
                GLES20.glEnable(GLES20.GL_CULL_FACE);
                //����ȼ��
                GLES20.glEnable(GLES20.GL_DEPTH_TEST);
                //�򿪶���
                GLES20.glEnable(GLES20.GL_DITHER);
                MatrixState.setInitStack();
                //--------------------------��������ǽ------------------------------------------------
    	          new Thread()
    	          {
    	          	@Override
    	          	public void run()
    	          	{
    	          		while(flag_display)
    	          		{
    	          			tex_index=(tex_index+1)%texWallId.length;//ÿ�����뻻һ������ͼ
    	          			
    	        			try
    	              		{
    	                          Thread.sleep(2000);
    	              		}
    	              		catch(InterruptedException e)
    	              		{
    	              			e.printStackTrace();
    	              		}
    	          		}
    	          	}
    	          }.start();
              //--------------------------��������ǽ------------------------------------------------
        	}
        }
        //��������
        public void drawRomeColumn()
        {
        	float ratio_column=0.55f;
        	float column_height=-18;
        	float adjust=3f;//����ֵ
           
            //���Ƶ�һ������
            MatrixState.pushMatrix();
            MatrixState.rotate(30, 0, 1, 0);
            MatrixState.translate( 0,column_height, -WALL_WIDHT*2+adjust);
            MatrixState.scale(ratio_column, ratio_column, ratio_column);            
            rome.drawSelf(1.0f);
            MatrixState.popMatrix();
            
            //��ʱ����Ƶڶ������ӣ���ʱΪ������  
            MatrixState.pushMatrix();
            MatrixState.rotate(90, 0, 1, 0);
            MatrixState.translate( 0,column_height, -WALL_WIDHT*2+adjust);
            MatrixState.scale(ratio_column, ratio_column, ratio_column);
            rome.drawSelf(1.0f);
            MatrixState.popMatrix();
            
            //��ʱ����Ƶ��������ӣ���ʱΪ������
            MatrixState.pushMatrix();
            MatrixState.rotate(150, 0, 1, 0);
            MatrixState.translate( 0,column_height, -WALL_WIDHT*2+adjust);
            MatrixState.scale(ratio_column, ratio_column, ratio_column);
            rome.drawSelf(1.0f);
            MatrixState.popMatrix();
            
            //��ʱ����Ƶ��ĸ����ӣ���ʱΪ������
            MatrixState.pushMatrix();
            MatrixState.rotate(210, 0, 1, 0);  
            MatrixState.translate( 0,column_height, -WALL_WIDHT*2+adjust);
            MatrixState.scale(ratio_column, ratio_column, ratio_column);
            rome.drawSelf(1.0f);
            MatrixState.popMatrix();
            
            //��ʱ����Ƶ�������ӣ���ʱΪ������
            MatrixState.pushMatrix();
            MatrixState.rotate(270, 0, 1, 0);
            MatrixState.translate( 0,column_height, -WALL_WIDHT*2+adjust);
            MatrixState.scale(ratio_column, ratio_column, ratio_column);
            rome.drawSelf(1.0f);
            MatrixState.popMatrix();
            
            //��ʱ����Ƶ��������ӣ���ʱΪ������
            MatrixState.pushMatrix();
            MatrixState.rotate(330, 0, 1, 0);
            MatrixState.translate( 0,column_height, -WALL_WIDHT*2+adjust);
            MatrixState.scale(ratio_column, ratio_column, ratio_column);
            rome.drawSelf(1.0f);
            MatrixState.popMatrix();
        }

        //���ƴ��ķ���
        public void drawBoat()
        {
        	 MatrixState.pushMatrix();
             switch(index_boat) 
             {  
             case 0://�ڷɵĴ�   
                  MatrixState.translate(0, -10f, 0);             	  
                  MatrixState.scale(RATIO_BOAT, RATIO_BOAT, RATIO_BOAT);
                  boat[0].drawSelf(heroBoatTexId);  
             	break;   
             case 1://���Ĵ�  
            	 MatrixState.translate(0, -10f, 0);
                  MatrixState.scale(RATIO_BOAT, RATIO_BOAT, RATIO_BOAT);
                  boat[1].drawSelf(quickBoatTexId);
             	break;
             case 2://����Ĵ�
             	  MatrixState.translate(0, -10f, 0);             	  
                  MatrixState.scale(RATIO_BOAT, RATIO_BOAT, RATIO_BOAT);
                  boat[2].drawSelf(slowBoatTexId);
             	break;
             }
             MatrixState.popMatrix();
        }
        
        //���ƴ��ĵ�Ӱ�ķ���
        public void drawBoatShadow()
        {
			//�رձ������
            GLES20.glDisable(GLES20.GL_CULL_FACE);
            MatrixState.pushMatrix();   
			switch(index_boat) 
            {  
            case 0://�ڷɵĴ�  
                 MatrixState.translate(-1f, -14f, 0); 
                 MatrixState.rotate(180, 0, 0, 1);
                 MatrixState.scale(RATIO_BOAT, RATIO_BOAT, RATIO_BOAT);
                 boat[0].drawSelf(heroBoatTexId);  
            	break;   
            case 1://���Ĵ�  
           	 	MatrixState.translate(0.6f, -14f, 0);
           	 	MatrixState.rotate(180, 0, 0, 1);
                MatrixState.scale(RATIO_BOAT, RATIO_BOAT, RATIO_BOAT);
                boat[1].drawSelf(quickBoatTexId);
            	break;
            case 2://����Ĵ�
            	MatrixState.translate(0, -14f, 0); 
            	MatrixState.rotate(180, 0, 0, 1);
                MatrixState.scale(RATIO_BOAT, RATIO_BOAT, RATIO_BOAT);
                boat[2].drawSelf(slowBoatTexId);
            	break;
            }
            MatrixState.popMatrix();
            //�򿪱������
            GLES20.glEnable(GLES20.GL_CULL_FACE);
        }
        //���ƴ���ǽ�еľ���
        public void drawBoatMirrorOnWall()
        {
        	final float ydistance=6f;
        	final float zdistance=-43f;
        	int k=(int) Math.abs(yAngle)/360+2;
        	float yAngleTemp=(yAngle+360*k)%360;
        	final int span=68;
        	//�ڵ�һ��ǽ        	
        	if(yAngleTemp<=span&&yAngleTemp>=0||yAngleTemp>=360-span&&yAngleTemp<=360)
        	{
        		MatrixState.pushMatrix();
            	MatrixState.translate(0, ydistance, zdistance);
            	MatrixState.rotate(180, 0, 1, 0);
            	MatrixState.rotate(-15, 1, 0, 0);
            	drawBoat();
            	MatrixState.popMatrix();   
        	}  
        	     	
        	//��ʱ���ڵڶ���ǽ������========================?
        	int bzAngle;
        	
        	//��ʱ���ڵ�����ǽ�澵��  
        	bzAngle=120;
        	if(yAngleTemp>bzAngle-span&&yAngleTemp<bzAngle+span)
        	{
		    	MatrixState.pushMatrix();
		    	MatrixState.rotate(120, 0, 1, 0);
		    	MatrixState.translate(0, ydistance, zdistance);
		    	MatrixState.rotate(-90, 0, 1, 0);
		    	MatrixState.rotate(-15, 0, 0, 1);
		    	drawBoat();
		    	MatrixState.popMatrix();    
        	}
        	//��ʱ��������
        	bzAngle=240;
        	if(yAngleTemp>bzAngle-span&&yAngleTemp<bzAngle+span)
        	{
	        	MatrixState.pushMatrix();
	        	MatrixState.rotate(240, 0, 1, 0);
	        	MatrixState.translate(0, ydistance, zdistance);
	        	MatrixState.rotate(90, 0, 1, 0);
	        	MatrixState.rotate(15, 0, 0, 1);
	        	drawBoat();
	        	MatrixState.popMatrix();
        	}
        }       
     
        //������ذ�ť
        public void drawButton()
        {
        	//������������
        	MatrixState.setProjectOrtho(-1,1,-1,1,1,100);
        	//���������
        	MatrixState.setCamera(0, 0, 0, 0, 0,-1, 0, 1, 0);
        	MatrixState.copyMVMatrix();
        	 //�������
            GLES20.glEnable(GLES20.GL_BLEND);  
            //���û������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        	MatrixState.pushMatrix();
        	MatrixState.translate(-0.7f, -0.8f, -1);
        	button.drawSelf(textureUpId);//���ƺ��ư�ť
            MatrixState.popMatrix();
            MatrixState.pushMatrix();
            MatrixState.translate(0.7f, -0.8f, -1);
            button.drawSelf(textureDownId);//���ƺ��ư�ť
            MatrixState.popMatrix();
            //�رջ��
            GLES20.glDisable(GLES20.GL_BLEND);
        }
    }
	//���ⰴť�ļ�������
	public void virtualButton()
	{
		//��ť1�����ռ�ı���
		float leftEdge=(float)(1-half_width_button+offset_X_Button1)/2*SCREEN_WIDTH;
		float rightEdge=(float)(1+half_width_button+offset_X_Button1)/2*SCREEN_WIDTH;
		float topEdge=(float)(1-XC_Self_Adapter_Data_TRASLATE[com.bn.clp.Constant.screenId][0]-offset_Y_Button1)/2*SCREEN_HEIGHT;
		float bottomEdge=(float)(1+XC_Self_Adapter_Data_TRASLATE[com.bn.clp.Constant.screenId][0]-offset_Y_Button1)/2*SCREEN_HEIGHT;
		button1=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		//��ť2�����ռ�ı���
		leftEdge=(float)(1-half_width_button+offset_X_Button2)/2*SCREEN_WIDTH;
		rightEdge=(float)(1+half_width_button+offset_X_Button2)/2*SCREEN_WIDTH;
		topEdge=(float)(1-XC_Self_Adapter_Data_TRASLATE[com.bn.clp.Constant.screenId][0]-offset_Y_Button2)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+XC_Self_Adapter_Data_TRASLATE[com.bn.clp.Constant.screenId][0]-offset_Y_Button2)/2*SCREEN_HEIGHT;
		button2=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		
		
	}
	public int initTextureFromBitmap(Bitmap bitmapTmp)//textureId
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
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_REPEAT);    
        
        //ʵ�ʼ�������
        GLUtils.texImage2D
        (
        		GLES20.GL_TEXTURE_2D,   //�������ͣ���OpenGL ES�б���ΪGL10.GL_TEXTURE_2D
        		0, 					  //����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
        		bitmapTmp, 			  //����ͼ��
        		0					  //����߿�ߴ�
        );
        
        return textureId;
	}
	
   	public static void loadWelcomeBitmap(Resources r)
	{
		  InputStream is=null;
          try  
          {
        	  is= r.openRawResource(R.drawable.up);	
        	  bmUp=BitmapFactory.decodeStream(is);
        	  is= r.openRawResource(R.drawable.down);	
        	  bmDown=BitmapFactory.decodeStream(is);
        	  is= r.openRawResource(R.drawable.gg1);	
        	  bmaWall[0]=BitmapFactory.decodeStream(is);
        	  is= r.openRawResource(R.drawable.gg2);	
        	  bmaWall[1]=BitmapFactory.decodeStream(is);
        	  is= r.openRawResource(R.drawable.gg3);	
        	  bmaWall[2]=BitmapFactory.decodeStream(is);
        	  is= r.openRawResource(R.drawable.floor);	
        	  bmFloor=BitmapFactory.decodeStream(is); 

    	      heroBoatTexBitmap=new Bitmap[parts[0].length];
        	  quickBoatTexBitmap=new Bitmap[parts[1].length];
        	  slowBoatTexBitmap=new Bitmap[parts[2].length];  
        	  
        	  for(int i=0;i<parts[0].length;i++)
        	  {
        		  is= r.openRawResource(BoatInfo.boatTexIdName[0][i]);	
        		  heroBoatTexBitmap[i]=BitmapFactory.decodeStream(is);
        	  }
        	  
        	  for(int i=0;i<parts[1].length;i++)
        	  {
        		  is= r.openRawResource(BoatInfo.boatTexIdName[1][i]);	
        		  quickBoatTexBitmap[i]=BitmapFactory.decodeStream(is);
        	  }
        	  
        	  for(int i=0;i<parts[2].length;i++)
        	  {
        		  is= r.openRawResource(BoatInfo.boatTexIdName[2][i]);	
        		  slowBoatTexBitmap[i]=BitmapFactory.decodeStream(is);
        	  }
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
	}   
}
