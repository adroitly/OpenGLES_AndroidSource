package com.bn.clp;
import static com.bn.clp.TDObjectData.*;
import static com.bn.clp.KEatData.*;
import static com.bn.clp.KZBJData.*;
import com.bn.R;
import static com.bn.clp.Constant.*;
import static com.bn.clp.TreeData.*;  
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.bn.core.MatrixState;
import com.bn.clp.KeyThread;
import com.bn.st.d2.MyActivity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView
{   
	//��Ϸ�м�¼��ͣʱ�Ѿ��õ�ʱ��  
	public static long betweenStartAndPauseTime;
	//��Ϸ�м�¼��ͣʱ�Ѿ��õ�ʱ��  
	
	//����ģʽ��ز���
	//������������
    static final int qtCount=2;
    //������������ x z angle
    static float[][] otherBoatLocation=new float[qtCount][3];
    //������ֹ�����������������x z angle
    static float[][] otherBoatLocationForHelp=new float[qtCount][3];
    //��������·���켣
    public ArrayList<ArrayList<float[]>> otherPaths=new ArrayList<ArrayList<float[]>>();
	
	//��ǰ�Ľ���ֵ
	int curr_process=0;
	MyActivity ma;
	
	//������Ⱦ��������
	private SceneRenderer sRenderer;
	//���ںӵ����������ID
	int rt_testur_Id;
	//ϵͳ�����ˮ������id  
	int textureFlagId;    
	//�ŵ�����Id
	int bridge_id; 
	//ɽ��ʯͷ������id
	int rock_id;  
	//ϵͳ����Ĺ�ľ����id
	int textureShrubId0; 
	//��ľ1����id
	int textrueShrubId1;
	//ϵͳ����Ĺ�ľ����id
	int textureShrubId2;	
	//��ľ1����id
	int textrueShrubId3;
	//����ӵģ���ͧ��Ҫ����������id����Ҫ�з�ͧ�����Լ������β��
	int texAirShipBody;//��ͧ���岿�ֵ�id
	int texAirShipWy;//��ͧ��β���ֵ�id	
	//����ӵģ���ͧ��Ҫ����������id����Ҫ�з�ͧ�����Լ������β��
	int waterId;	
	//����ӵģ��ӵ������ʯͷ����id
	int raceTrackRockId;
	//����ӵģ����������Ҫ������id�����������Լ����
	int ggSzTexId;
	int[] ggTexId=new int[3];
	//��Ϸ��ʼ�ͽ�������������id
	int gameStartTexId;
	int gameEndTexId;
	//����ͷ������id
	int dockTexId;
	//�״��ͼ������id
	int radarBackGroundTexId;
	//�״�ָ�������id
	int radarZhiZhenTexId;
	//�״���������������id
	int radarOtherBoatTexId;
	//�Ǳ���Ҫ����������id
	int castleTexIdA;
	int castleTexIdB;
	
	//�۲��λ�õ���������
	static float cx;
	static float cy;
	static float cz;
	//Ŀ��Ŀ������������
	static float tx; 
	static float ty;
	static float tz;
	//ʵ�ֵĽǶ�
	public static float sight_angle=DIRECTION_INI;
	
	public static float yachtLeftOrRightAngle=0;//��������ת
	static float yachtLeftOrRightAngleMax=15;
	public static float yachtLeftOrRightAngleA=2.5f;
	public static final float yachtLeftOrRightAngleValue=2.5f;
	
	static float bx;//����x����
    static float bz;//����z����
    static float bxForSpecFrame;//����x����
    static float bzForSpecFrame;//����z����    
    static float angleForSpecFrame;//С��ת���ĽǶ�ֵ(���Ŷ�)
    static float angleForSpecFrameS;//С��ת���ĽǶ�ֵ(�����Ŷ�)
    static float cxForSpecFrame;
    static float czForSpecFrame;
        
    public static int keyState=0;//����״̬  1-up 2-down 4-left 8-right
    public KeyThread  kt;//����״̬����߳�
    public ThreadColl tc;//����ײ��������߳�
    
    static int bCol;//�����ڵ�ͼ��λ����
	static int bRow;//�����ڵ�ͼ��λ����
	static int bColOld;//�����ڵ�ͼ��λ����
	static int bRowOld;//�����ڵ�ͼ��λ����
	
	//����ӵ�BNDrawer��һά����   
	BNDrawer[] bndrawer;
	//�洢TDObjectForControl�ļ���
	public List<TDObjectForControl> tdObjectList=new ArrayList<TDObjectForControl>();
	public List<int[]> texIdList=new ArrayList<int[]>();
	
	public List<PZZ> pzzList=new ArrayList<PZZ>();
		
	//�洢SpeedForControl�ļ���
	SpeedForEat[] speedForEat;
	public List<SpeedForControl> speedWtList=new ArrayList<SpeedForControl>();
	int speedUpTexId;//�������������id
	int speedDownTexId;//������������id
	public ThreadForEat tfe;
	//��
	SingleShrub ss;
	public List<ShrubForControl> treeList=new ArrayList<ShrubForControl>();
	
	//����ʱ�ƵĻ�����
	public DaoJiShiForDraw djsfd;
	
	//����
	Sky sky;
	int sky_texId;
	//�洢KZBJForControl�ļ���
	KZBJDrawer[] kzbj_drawer;
	public List<KZBJForControl> kzbjList=new ArrayList<KZBJForControl>();
	//��ͨ������ͨ׶
	KZBJDrawer[] kzbj_array;
	//��ͨ���ͽ�ͨ׶������id
	int jt_texId;
	//���ű���
	float ratio;
	//�Ǳ������id
	int ybbTexId;
	
	//������Դ
    static Bitmap bmbackGround;
    static Bitmap bmPgsDt;
    static Bitmap bmNum;
    static Bitmap bmPgsFgt;
    
    //��Ϸ��ʼʱ��
    static long gameStartTime;
    
    //�Ƿ񵹼�ʱ��־λ
	public static boolean isDaoJiShi=true;
	//�ܼ�ʱ�Ƿ�ʼ��־λ
	public static boolean isJiShi=false;
	//����ʱ�Ƿ��������������ͣ���л��ӽǰ�ť�ɰ�
	public static boolean isAllowToClick=false;
	//�Ƿ���ɲ���ı�־λ
	public boolean isShaChe=false;
    
    public static void loadProgressBitmap(Resources r)
    {
    	InputStream is=null;
        try  
        {
      	  is= r.openRawResource(R.drawable.load_bj);	
      	  bmbackGround=BitmapFactory.decodeStream(is);
      	  is= r.openRawResource(R.drawable.load_dt);	
      	  bmPgsDt=BitmapFactory.decodeStream(is);
      	  is=r.openRawResource(R.drawable.load_fgt);
    	  bmPgsFgt=BitmapFactory.decodeStream(is);
      	  is= r.openRawResource(R.drawable.number);	
      	  bmNum=BitmapFactory.decodeStream(is);      	             	  
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
    //����������
    @Override
	public boolean onTouchEvent(MotionEvent e)
	{
		float x=e.getX();
		float y=e.getY();
		float yRatio=y/com.bn.st.xc.Constant.SCREEN_HEIGHT;
        float xRatio=x/com.bn.st.xc.Constant.SCREEN_WIDTH;   
		switch(e.getAction())
		{
		case MotionEvent.ACTION_DOWN:	
			if(isAllowToClick)
			{
				//���ٰ�ť
				if
				(
						xRatio>Self_Adapter_Data_ON_TOUCH[screenId][10]&&xRatio<Self_Adapter_Data_ON_TOUCH[screenId][11]
						&&yRatio>Self_Adapter_Data_ON_TOUCH[screenId][8]&&yRatio<Self_Adapter_Data_ON_TOUCH[screenId][9]
				        &&!isPaused
				)
				{
					if(numberOfN2>0)
					{		
						if(SoundEffectFlag)
						{
							ma.shengyinBoFang(2, 0);
						}						
						numberOfN2=numberOfN2-1;
						Max_BOAT_V=Max_BOAT_V_FINAL;
						kt.dqCount=80;						
					}
					else
					{
						numberOfN2=0; 
					}
				}//��ͣ�Ϳ�ʼ��ť
				else if
				(
						xRatio>Self_Adapter_Data_ON_TOUCH[screenId][6]&&xRatio<Self_Adapter_Data_ON_TOUCH[screenId][7]
						&&yRatio>Self_Adapter_Data_ON_TOUCH[screenId][4]&&yRatio<Self_Adapter_Data_ON_TOUCH[screenId][5]
				)
				{
					if(isPaused)
					{//��̬ͣ������̬						
						CURR_BOAT_V=CURR_BOAT_V_PAUSE;
						BOAT_A=BOAT_A_PAUSE;
						DEGREE_SPAN=2f;
						CURR_BOAT_V_PAUSE=0;
						BOAT_A_PAUSE=0;
						isPaused=false; 
						kt.moveFlag=true;
						KeyThread.otherBoatFlag=true;
						//����ӵ�
						gameStartTime=System.currentTimeMillis();
						//����ӵ�
					}
					else if(!isPaused)
					{//����̬����̬ͣ				
						CURR_BOAT_V_PAUSE=CURR_BOAT_V;
						BOAT_A_PAUSE=BOAT_A; 
						CURR_BOAT_V=0;
						BOAT_A=0;
						DEGREE_SPAN=0;
						isPaused=true;
						kt.moveFlag=false;
						KeyThread.otherBoatFlag=false;
						//����ӵ�
						betweenStartAndPauseTime=gameContinueTime()+betweenStartAndPauseTime;
						//����ӵ�
						CURR_BOAT_V_TMD=0;
					}
				}//��һ�˳ƺ͵����˳ư�ť
				else if
				(
						xRatio>Self_Adapter_Data_ON_TOUCH[screenId][14]&&xRatio<Self_Adapter_Data_ON_TOUCH[screenId][15]
						&&yRatio>Self_Adapter_Data_ON_TOUCH[screenId][12]&&yRatio<Self_Adapter_Data_ON_TOUCH[screenId][13]
						&&!isPaused
				)
				{
					isShaChe=true;
					BOAT_A=-0.02f;					
				}
				else if
				(
						xRatio>Self_Adapter_Data_ON_TOUCH[screenId][18]&&xRatio<Self_Adapter_Data_ON_TOUCH[screenId][19]
						&&yRatio>Self_Adapter_Data_ON_TOUCH[screenId][16]&&yRatio<Self_Adapter_Data_ON_TOUCH[screenId][17]						
				)
				{
					if(isOpenHSJ)
					{
						isOpenHSJ=false;
					}
					else
					{
						isOpenHSJ=true;
					}					
				}
			}			
			break; 
		case MotionEvent.ACTION_UP:
				if
				(
						isShaChe&&!isPaused
				)
				{
					isShaChe=false;
					BOAT_A=0.025f;		
				}
			break;
		}
		return true;		
	}
	
	public MyGLSurfaceView(Context context)
	{
		super(context);
		
		ma=(MyActivity)context;
		
		this.setKeepScreenOn(true);
		
		if(isSpeedMode)
		{
			//�ӳ������м����������ĳ�ʼλ�� ��
			otherBoatLocation[0][0]=YACHT_INI_X-3;
			otherBoatLocation[0][1]=YACHT_INI_Z-3;
			otherBoatLocation[0][2]=0;
			//��
			otherBoatLocation[1][0]=YACHT_INI_X+3;
			otherBoatLocation[1][1]=YACHT_INI_Z-4;
			otherBoatLocation[1][2]=0;
		}		
		
		//�ӳ������м���Ӣ�۴��ĳ�ʼλ��
        bx=YACHT_INI_X;
        bz=YACHT_INI_Z;
        
        cx=(float)(bx+Math.sin(Math.toRadians(sight_angle))*DISTANCE);;//�����x����
        cy=CAMERA_INI_Y;//�����y����
        cz=(float)(bz+Math.cos(Math.toRadians(sight_angle))*DISTANCE);//�����z����
        
        tx=(float)(cx-Math.sin(Math.toRadians(sight_angle))*DISTANCE);//�۲�Ŀ���x����  
        ty=CAMERA_INI_Y-1.5f;//ƽ�ӹ۲�Ŀ���y����
        tz=(float)(cz-Math.cos(Math.toRadians(sight_angle))*DISTANCE);//�۲�Ŀ���z����
        
        //ʹС�� ��Զ��ǰ��
        keyState=keyState|0x1;
		
		//����ʹ��ES2.0
		this.setEGLContextClientVersion(2);
		sRenderer=new SceneRenderer();
		//������Ⱦ��   
		setRenderer(sRenderer);
		//������ȾģʽΪ������Ⱦ
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		
		 //�������������̼���߳�
        kt=new KeyThread(this,ma);         
                
        tc=new ThreadColl(this);        
        
        tfe=new ThreadForEat(this);              
	}

	//������Ⱦ��
	private class SceneRenderer implements GLSurfaceView.Renderer
	{
		//����ӵ�
		BackGround bgd;
		//�������ĵײ�
		BackGround pgs_dt;
		//�����������ͼ
		Process pgs_fgt;
		//�ܱ���ͼ��id
		int backGroundId;
		//�������ĵײ�ͼid
		int pgs_dt_id;
		//�������ĸ���ͼ��id
		int pgs_fgt_id;
		//�������ϵ�����
		BackGround[] no=new BackGround[11];
		int no_texId;
		//�������ϵ�����
		
		//�����������д󱳾�ͼ��������������
		final float[] bg_texCoor=new float[]
        {
			0,0,  0,1,  1,1,
			0,0,  1,1,  1,0
        };
		//�������·���������������
		final float[] pgs_dt_texCoor=new float[]
        {
			0,0,  0,1f,  1,1f,
			0,0,  1,1f,  1,0
        };
		//�������Ϸ��ĸ���ͼ��������������
		final float[] pgs_fgt_texCoor=new float[]
        {
			0,0,  0,1f,  1,1f,
			0,0,  1,1f,  1,0
        };
		//����ֱ��������
		RaceTrack rtzd;
		//����ֱ����С��������
		RaceTrack rtzddxd;
		//�������������
		RaceTrack rtwd;
		//����ˮ�������
		Water water;
		//��
		Boat boat;
		//�촬
		Boat quickBoat;
		//����
		Boat slowBoat;
		//β��
		WeiLang wl;
		//�Ǳ��
		Dashboard db;
		//��ʱ����lap
		DrawTime dt;
		//��ʱ����lap�͵���������id
		int timeTexId;
		//���ٰ�ť������id
		int goTexId;
		//ɲ����ť������id
		int shacheTexId;
		//����ӵģ���Ϸ��ʼ�ͽ���
		StartAndEnd gameStartAndEnd;		
		//����ʱ�Ƶ�����id
		int djsTexId;
		//�״��ͼ�Ļ�����
		com.bn.st.xc.TextureRect radar_Background;
		//�״�ָ��Ļ�����
		com.bn.st.xc.TextureRect radar_Zhizhen;
		//�״����������Ļ�����
		com.bn.st.xc.TextureRect other_Radar_Zhizhen;
		//���Ӿ��Ļ�����
		com.bn.st.xc.TextureRect houshijing;
		//���Ӿ�����ID
		int houshijingTexId;
		
		final float[] weilang_texCoor=new float[]
		{
			0,0,  0,1,  1,1,
			0,0,  1,1,  1,0
        };
    	public boolean isBegin=true;
    	int  frameCount=0;
    	
    	int[][] sdHz=new int[25][2];
    	int sdCount=0;
    	
    	//��������id����
    	int[] heroBoatTexId;
    	int[] quickBoatTexId;
    	int[] slowBoatTexId;
    	
    	float startST;
    	float stK;
    	
		@Override
		public void onDrawFrame(GL10 gl)
		{
			if(isBegin)
			{
				bColOld=-100;//�����ڵ�ͼ��λ����
				bRowOld=-100;//�����ڵ�ͼ��λ����
				//�����Ȼ����Լ���ɫ����
				GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT|GLES20.GL_COLOR_BUFFER_BIT);				
				//���ô˷��������������ͶӰ����
				MatrixState.setProjectOrtho(-1, 1, -1, 1, 1, 10);  
				//���ô˷������������9����λ�þ���
				MatrixState.setCamera(0, 0, 0, 0, 0, -1, 0, 1, 0);
				MatrixState.copyMVMatrix();
				MatrixState.pushMatrix();
				MatrixState.translate(0, 0, -2);
				bgd.drawSelf(backGroundId);
				MatrixState.popMatrix(); 
				
				//�������    
	            GLES20.glEnable(GLES20.GL_BLEND);  
	            //���û������
	            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
				
				//���Ƹ���ͼ�ĵ�ͼ
				MatrixState.pushMatrix();
				MatrixState.translate(0, -0.15f, -1.99f);
				pgs_dt.drawSelf(pgs_dt_id);
				MatrixState.popMatrix();
				
				//���Ƹ���ͼ
				MatrixState.pushMatrix();
				MatrixState.translate(0, -0.15f, -1.98f);
				pgs_fgt.drawSelf(pgs_fgt_id); 
				MatrixState.popMatrix(); 				
								
				//���ƽ������Ϸ�������
				String tempStr=curr_process+"";
				for(int i=0;i<tempStr.length();i++)   
				{   
					MatrixState.pushMatrix(); 
					MatrixState.translate(0.1f*(i-1), -0.15f, -1.97f);
					no[tempStr.charAt(i)-'0'].drawSelf(no_texId); 
					MatrixState.popMatrix(); 
				}
				
				//���ưٷֺ�
				MatrixState.pushMatrix(); 
				MatrixState.translate(0.1f*tempStr.length(), -0.15f, -1.97f);
				no[10].drawSelf(no_texId); 
				MatrixState.popMatrix();
				//�رջ��
	            GLES20.glDisable(GLES20.GL_BLEND);   
	            
	            if(frameCount<2)
	            {
	            	frameCount++;
	            }
	            else
	            {
	            	this.readLoadTask();
	            }
			}
			else
			{
				if(isDaoJiShi)
				{
					djsfd.djst.start();
					isDaoJiShi=false;
				}
				//copy�õ���ǰ����������ƫ����
				startST=water.currStartST;
				//�����Ȼ�������ɫ����
				GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT|GLES20.GL_COLOR_BUFFER_BIT);				
				//����ͶӰģʽ����
				MatrixState.setProjectFrustum(-ratio*0.7f, ratio*0.7f, -0.7f*0.7f, 1.3f*0.7f, 1, 300);
				
				synchronized(lockA)
				{
					//���ô˷������������9����λ�þ���
					MatrixState.setCamera(cx, cy, cz, tx, ty, tz, 0, 1, 0);
					//�����ӵģ���ֹ����
					MatrixState.copyMVMatrix();
					bxForSpecFrame=bx;
					bzForSpecFrame=bz;  
					angleForSpecFrameS=sight_angle;
					angleForSpecFrame=angleForSpecFrameS+yachtLeftOrRightAngle;					
					cxForSpecFrame=cx;
					czForSpecFrame=cz;					
					directNo=getDirectionNumber(sight_angle);
					if(isSpeedMode)
					{
						for(int i=0;i<otherBoatLocation.length;i++)
						{
							otherBoatLocationForHelp[i][0]=otherBoatLocation[i][0];
							otherBoatLocationForHelp[i][1]=otherBoatLocation[i][1];
							otherBoatLocationForHelp[i][2]=otherBoatLocation[i][2];
						}
					}					
				}
				
				bCol=(int)(Math.floor((cxForSpecFrame+UNIT_SIZE/2)/UNIT_SIZE));//�����ڵ�ͼ��λ����
	        	bRow=(int)(Math.floor((czForSpecFrame+UNIT_SIZE/2)/UNIT_SIZE));//�����ڵ�ͼ��λ����
				//�������񷵹Ӱ
	            sky.drawSelf(sky_texId,bxForSpecFrame, bzForSpecFrame,1);
				
				//��������
	            sky.drawSelf(sky_texId,bxForSpecFrame, bzForSpecFrame,0);
	            
	            //����ɸѡԤ������
	            if(bColOld!=bCol||bRowOld!=bRow)
	            {
	            	//��������׼��
		            sdYb();
		            //����3D����׼��
		            tdYb();
		            //���ƿ�ײ������׼��
		            kzYb();
		            //��������Ԥ��
		            treeYb();
		            //���˼Ӽ�������Ԥ��
		            speedForEatYb();
	            }	            
	            
				//�رձ������
	            GLES20.glDisable(GLES20.GL_CULL_FACE);            
	            //�����������䵹Ӱ
				drawSD(startST);			
				//�򿪱������
	            GLES20.glEnable(GLES20.GL_CULL_FACE);
				
	            //����3D����ĵ�Ӱ
	            DrawTDObjects(1);	            
	            //����3D����
	            DrawTDObjects(0);
	               
	            //���ƴ���Ӱ
	            boat.drawSelf(bxForSpecFrame, 0.3f, bzForSpecFrame,angleForSpecFrame,1,heroBoatTexId);
	            if(isSpeedMode)
	            {
	            	 //���������� 
		            for(int i=0;i<otherBoatLocationForHelp.length;i++)
		            {
		            	if(i==0)
		            	{
		            		quickBoat.drawSelf(otherBoatLocationForHelp[i][0], 0.3f, otherBoatLocationForHelp[i][1],otherBoatLocationForHelp[i][2],1,quickBoatTexId);
		            	}
		            	else if(i==1)
		            	{
		            		slowBoat.drawSelf(otherBoatLocationForHelp[i][0], 0.3f, otherBoatLocationForHelp[i][1],otherBoatLocationForHelp[i][2],1,slowBoatTexId);
		            	}	            	 
		            }
	            }
	            
	            drawKZBJ(1);
	            
	            //�رձ������
	            GLES20.glDisable(GLES20.GL_CULL_FACE);
	            //�������
	            GLES20.glEnable(GLES20.GL_BLEND);  
	            //���û������
	            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	            //������
	            drawTrees(1); 
	            //�رջ��  
	            GLES20.glDisable(GLES20.GL_BLEND);     
	            
	            //�������
	            GLES20.glEnable(GLES20.GL_BLEND);
	            //���û������
	            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	        	drawSpeedForEat(1);
	        	//�رջ��  
	            GLES20.glDisable(GLES20.GL_BLEND);     
	        	
	            drawStartAndEnd(numberOfTurns,1);  
	            //�������
	            GLES20.glEnable(GLES20.GL_BLEND);  
	            //���û������
	            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	            //����ˮ��
				drawWater(startST);
				//�رջ��  
	            GLES20.glDisable(GLES20.GL_BLEND); 
				//�򿪱������
	            GLES20.glEnable(GLES20.GL_CULL_FACE);    
	            
	            drawKZBJ(0);
	            stK=wl.currStartST; 
	            boat.drawSelf(bxForSpecFrame, 0.3f, bzForSpecFrame,angleForSpecFrame,0,heroBoatTexId);
	            if(isSpeedMode)
	            {
	            	 //����������
		            for(int i=0;i<otherBoatLocationForHelp.length;i++)
		            {
		            	if(i==0)
		            	{
		            		quickBoat.drawSelf(otherBoatLocationForHelp[i][0], 0.3f, otherBoatLocationForHelp[i][1],otherBoatLocationForHelp[i][2],0,quickBoatTexId);
		            	}
		            	else if(i==1)
		            	{
		            		slowBoat.drawSelf(otherBoatLocationForHelp[i][0], 0.3f, otherBoatLocationForHelp[i][1],otherBoatLocationForHelp[i][2],0,slowBoatTexId);
		            	}	            	 
		            }
	            }	           
				MatrixState.pushMatrix(); 
				//�������
	            GLES20.glEnable(GLES20.GL_BLEND);
	            //���û������
	            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
				MatrixState.translate(bxForSpecFrame, 0.02f, bzForSpecFrame);
				MatrixState.rotate(-90, 1, 0, 0);
				MatrixState.rotate(angleForSpecFrameS, 0, 0, 1);
				wl.drawSelf(waterId,stK);
				//�رջ��  
	            GLES20.glDisable(GLES20.GL_BLEND);
				MatrixState.popMatrix();
	            //�رձ������
	            GLES20.glDisable(GLES20.GL_CULL_FACE);
	            //�������
	            GLES20.glEnable(GLES20.GL_BLEND);  
	            //���û������
	            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	            //������
	            drawTrees(0);
				//�رջ��    
	            GLES20.glDisable(GLES20.GL_BLEND);    
	            drawStartAndEnd(numberOfTurns,0);  
	            //�������
	            GLES20.glEnable(GLES20.GL_BLEND);
	            //���û������
	            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	        	drawSpeedForEat(0);
	        	//�رջ��    
	            GLES20.glDisable(GLES20.GL_BLEND);    
	        	drawDaoJiShi();
	            if(isOpenHSJ)
	            {
	            	drawHouShiJing();
	            }  
	            //�ڻ����Ǳ���ʱ���Ѿ���ͶӰģʽ���������λ�ý��������µ����ã�������
	            //���Ƽ�ʱ����lap��ʱ��û���ٴν�������
	            drawYiBiaoBan();
	            drawTimeAndLap();
	            if(isSpeedMode)
	            {
	            	drawRadar();
	            }	 
	            
	            bColOld=bCol;
	            bRowOld=bRow;
	            if(isOpenHSJ)
	            {
	            	drawHSJKuang();
	            }
			}	
		}
		
		//���ƺ��Ӿ��ķ���
		public void drawHouShiJing()
		{
            //���ü��ò���
        	GLES20.glEnable(GL10.GL_SCISSOR_TEST);
        	//��������
        	System.out.println(screenId+"  screenId");
        	GLES20.glScissor((int)Self_Adapter_Data_HSJ_XY[screenId][0],(int)Self_Adapter_Data_HSJ_XY[screenId][1],300,90);
        	//�����ɫ��������Ȼ���
            GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-1.0f*ratio, ratio, -1.7f*0.7f, 0.3f*0.7f, 1, 400);
            //���ô˷������������9����λ�þ���
			MatrixState.setCamera
			(
				cx,
				cy,
				cz,
				(float)(cx+Math.sin(Math.toRadians(sight_angle))*DISTANCE), 
				ty+1.1f, 
				(float)(cz+Math.cos(Math.toRadians(sight_angle))*DISTANCE), 
				0, 
				1, 
				0
			);
			MatrixState.copyMVMatrix();
			
            sky.drawSelf(sky_texId,bxForSpecFrame, bzForSpecFrame,1);
            sky.drawSelf(sky_texId,bxForSpecFrame, bzForSpecFrame,0);
			//�رձ������
            GLES20.glDisable(GLES20.GL_CULL_FACE);            
			drawSD(startST);			
			//�򿪱������
            GLES20.glEnable(GLES20.GL_CULL_FACE);
			
            //����3D����ĵ�Ӱ
            DrawTDObjects(1);	            
            //����3D����
            DrawTDObjects(0);
            boat.drawSelf(bxForSpecFrame, 0.3f, bzForSpecFrame,angleForSpecFrame,1,heroBoatTexId);
            if(isSpeedMode)
            {
            	 //���������� 
	            for(int i=0;i<otherBoatLocationForHelp.length;i++)
	            {
	            	if(i==0)
	            	{
	            		quickBoat.drawSelf(otherBoatLocationForHelp[i][0], 0.3f, otherBoatLocationForHelp[i][1],otherBoatLocationForHelp[i][2],1,quickBoatTexId);
	            	}
	            	else if(i==1)
	            	{
	            		slowBoat.drawSelf(otherBoatLocationForHelp[i][0], 0.3f, otherBoatLocationForHelp[i][1],otherBoatLocationForHelp[i][2],1,slowBoatTexId);
	            	}	            	 
	            }
            }
            drawKZBJ(1);
            //�رձ������
            GLES20.glDisable(GLES20.GL_CULL_FACE);
            //�������
            GLES20.glEnable(GLES20.GL_BLEND);  
            //���û������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            //������
            drawTrees(1); 
            //�رջ��  
            GLES20.glDisable(GLES20.GL_BLEND);     
            //�������
            GLES20.glEnable(GLES20.GL_BLEND);
            //���û������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        	drawSpeedForEat(1);
        	//�رջ��  
            GLES20.glDisable(GLES20.GL_BLEND);     
            drawStartAndEnd(numberOfTurns,1);  
            //�������
            GLES20.glEnable(GLES20.GL_BLEND);  
            //���û������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            //����ˮ��
			drawWater(startST);
			//�رջ��  
            GLES20.glDisable(GLES20.GL_BLEND); 
			//�򿪱������
            GLES20.glEnable(GLES20.GL_CULL_FACE);    
            drawKZBJ(0);
            boat.drawSelf(bxForSpecFrame, 0.3f, bzForSpecFrame,angleForSpecFrame,0,heroBoatTexId);
            if(isSpeedMode)
            {
            	 //����������
	            for(int i=0;i<otherBoatLocationForHelp.length;i++)
	            {
	            	if(i==0)
	            	{
	            		quickBoat.drawSelf(otherBoatLocationForHelp[i][0], 0.3f, otherBoatLocationForHelp[i][1],otherBoatLocationForHelp[i][2],0,quickBoatTexId);
	            	}
	            	else if(i==1)
	            	{
	            		slowBoat.drawSelf(otherBoatLocationForHelp[i][0], 0.3f, otherBoatLocationForHelp[i][1],otherBoatLocationForHelp[i][2],0,slowBoatTexId);
	            	}	            	 
	            }
            }	           
			MatrixState.pushMatrix(); 
			//�������
            GLES20.glEnable(GLES20.GL_BLEND);
            //���û������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
			MatrixState.translate(bxForSpecFrame, 0.02f, bzForSpecFrame);
			MatrixState.rotate(-90, 1, 0, 0);
			MatrixState.rotate(angleForSpecFrameS, 0, 0, 1);
			wl.drawSelf(waterId,stK);
			//�رջ��  
            GLES20.glDisable(GLES20.GL_BLEND);
			MatrixState.popMatrix();
            //�رձ������
            GLES20.glDisable(GLES20.GL_CULL_FACE);
            //�������
            GLES20.glEnable(GLES20.GL_BLEND);  
            //���û������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            //������
            drawTrees(0);
			//�رջ��    
            GLES20.glDisable(GLES20.GL_BLEND);    
            drawStartAndEnd(numberOfTurns,0);  
            //�������
            GLES20.glEnable(GLES20.GL_BLEND);
            //���û������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        	drawSpeedForEat(0);
        	//�رջ��    
            GLES20.glDisable(GLES20.GL_BLEND);    
			//���ü��ò���
        	GLES20.glDisable(GL10.GL_SCISSOR_TEST);  
		}
		
		//���ƺ��Ӿ���ķ���		
		public void drawHSJKuang()
		{ 
			//���ƺ��Ӿ���
			//����Ϊ����ͶӰ����
            MatrixState.setProjectOrtho(-ratio, ratio, -1, 1, 1, 100);
			//�������������λ��
			MatrixState.setCamera(0, 0, 0, 0, 0, -1, 0, 1, 0); 
			MatrixState.copyMVMatrix();
			MatrixState.pushMatrix();
			//�������
            GLES20.glEnable(GLES20.GL_BLEND);  
            //���û������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            MatrixState.translate(0.01f, Self_Adapter_Data_HSJ_XY[screenId][2], -1);
			houshijing.drawSelf(houshijingTexId);
			MatrixState.popMatrix();
		}
		
		//����С�״�ķ���
		public void drawRadar()
		{
			MatrixState.pushMatrix();
			//�������
            GLES20.glEnable(GLES20.GL_BLEND);  
            //���û������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            MatrixState.translate(Self_Adapter_Data_TRASLATE[screenId][12], Self_Adapter_Data_TRASLATE[screenId][13], -2);
			radar_Background.drawSelf(radarBackGroundTexId);			
			MatrixState.popMatrix();
			
			MatrixState.pushMatrix();  
			MatrixState.translate(Self_Adapter_Data_TRASLATE[screenId][12], Self_Adapter_Data_TRASLATE[screenId][13], -1);
			MatrixState.rotate(sight_angle, 0, 0, 1);
			radar_Zhizhen.drawSelf(radarZhiZhenTexId);
			MatrixState.popMatrix();
			
			for(int i=0;i<otherBoatLocation.length;i++)
			{
				float x_Temp=otherBoatLocationForHelp[i][0]-bxForSpecFrame;
				float z_Temp=otherBoatLocationForHelp[i][1]-bzForSpecFrame;
				
				float r_Temp=(float) Math.sqrt((x_Temp/Radar_Ratio)*(x_Temp/Radar_Ratio)+(z_Temp/Radar_Ratio)*(z_Temp/Radar_Ratio));
				
				if(r_Temp<=0.26f)
				{ 
					MatrixState.pushMatrix(); 					
					MatrixState.translate(Self_Adapter_Data_TRASLATE[screenId][12], Self_Adapter_Data_TRASLATE[screenId][13], -1f);
					MatrixState.translate(x_Temp/Radar_Ratio, -z_Temp/Radar_Ratio, 0);
					other_Radar_Zhizhen.drawSelf(radarOtherBoatTexId);
					MatrixState.popMatrix(); 
				}
			}
		}
		
		//���Ƶ���ʱ�ķ��� 
		public void drawDaoJiShi()
		{
			MatrixState.pushMatrix();
			//�������
            GLES20.glEnable(GLES20.GL_BLEND);  
            //���û������ 
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
			MatrixState.translate(0.0f, 1.8f, 90.0f);
			djsfd.drawSelf(djsTexId);
			//�رջ��
            GLES20.glDisable(GLES20.GL_BLEND); 
			MatrixState.popMatrix();
		}
		
		//���ƿ�ʼ�ͽ�����־
		public void drawStartAndEnd(int currOfTurns,int dyFlag)
		{
			MatrixState.pushMatrix();
			MatrixState.translate(UNIT_SIZE*0,LAND_MAX_HIGHEST-5,UNIT_SIZE*1f-5);
			if(currOfTurns==1&&!Constant.halfFlag)//�տ�ʼ
			{
				gameStartAndEnd.drawSelf(ggSzTexId,gameStartTexId,LAND_MAX_HIGHEST-5,dyFlag);
			}
			else if(currOfTurns==2&&Constant.halfFlag)
			{
				gameStartAndEnd.drawSelf(ggSzTexId,gameEndTexId,LAND_MAX_HIGHEST-5,dyFlag);
			}
			MatrixState.popMatrix();
		}
		
		//���Ƽ�ʱ����lap�ķ���
		public void drawTimeAndLap()
		{
			if(!isJiShi)
			{
				dt.toTotalTime(0);
			}
			else if(isPaused)
			{//��ͣ״̬
				dt.toTotalTime(betweenStartAndPauseTime);
			}
			else if(!isPaused)
			{//����̬ͣ������ʱ
				if(numberOfTurns<3)
				{
					dt.toTotalTime(MyGLSurfaceView.gameContinueTime()+betweenStartAndPauseTime);
					gameTimeUse=MyGLSurfaceView.gameContinueTime()+betweenStartAndPauseTime;
				}
				else
				{
					dt.toTotalTime(gameTimeUse);
				}				
			}			
			
			MatrixState.pushMatrix();
			//�������
            GLES20.glEnable(GLES20.GL_BLEND);  
            //���û������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
			MatrixState.translate(-0.35f, 0.8f, -2);
			if(numberOfTurns<=2)
			{
				dt.drawSelf(timeTexId,numberOfTurns,numberOfN2,goTexId,shacheTexId,isShaChe); 
			}
			else
			{
				dt.drawSelf(timeTexId,2,numberOfN2,goTexId,shacheTexId,isShaChe);
			}
			//�رջ��
            GLES20.glDisable(GLES20.GL_BLEND);
			MatrixState.popMatrix(); 
		}
		
		//�����Ǳ��ķ���
		public void drawYiBiaoBan()
		{
			//���ݵ�ǰ�ٶȣ��õ�ָ���ѡ��Ƕ�
            db.changeangle(CURR_BOAT_V);
            //����Ϊ����ͶӰ����
            MatrixState.setProjectOrtho(-ratio, ratio, -1, 1, 1, 100);
			//�������������λ��
			MatrixState.setCamera(0, 0, 0, 0, 0, -1, 0, 1, 0); 
			MatrixState.copyMVMatrix();
			MatrixState.pushMatrix();
			//�������
            GLES20.glEnable(GLES20.GL_BLEND);  
            //���û������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            MatrixState.translate(Self_Adapter_Data_TRASLATE[screenId][9], Self_Adapter_Data_TRASLATE[screenId][10], -2);
			db.drawSelf(ybbTexId); 
			//�رջ��
            GLES20.glDisable(GLES20.GL_BLEND);  
			MatrixState.popMatrix();
		}
		
		//���Ƹ�����ŵ�����
		public void drawSDSingle
		(
			int id,//�������  
			int row,//�������ڵ�ͼ�ϵ���
			int col //�������ڵ�ͼ�ϵ���
		)
		{
			MatrixState.pushMatrix();
			MatrixState.translate(UNIT_SIZE*col, 0, UNIT_SIZE*row);
			switch(id)  
			{
			  case 0://���ŵ���������X��ƽ�У�	
			  case 9:
				  rtzd.drawSelf(rt_testur_Id,raceTrackRockId);				  
			  break;
			  case 1://���ŵ���������Z��ƽ�У�
			  case 10:
				  MatrixState.rotate(90, 0, 1, 0);
				  rtzd.drawSelf(rt_testur_Id,raceTrackRockId);
			  break;
			  case 2://���ŵ���������X��ƽ��,��С����
			  case 11:
				  rtzddxd.drawSelf(rt_testur_Id,raceTrackRockId);
			  break;
			  case 3://���ŵ���������Z��ƽ��,��С����
			  case 12:
				  MatrixState.rotate(90, 0, 1, 0);
				  rtzddxd.drawSelf(rt_testur_Id,raceTrackRockId);
			  break;
			  case 4://Z��������յ�X������������
			  case 13:
				  MatrixState.rotate(180, 0, 1, 0);
				  rtwd.drawSelf(rt_testur_Id,raceTrackRockId);
			  break;
			  case 5://Z��������յ�X�Ḻ��������
			  case 14:
				  MatrixState.rotate(90, 0, 1, 0);
				  rtwd.drawSelf(rt_testur_Id,raceTrackRockId);
			  break;
			  case 6://X�Ḻ����յ�Z�Ḻ��������
			  case 15:
				  MatrixState.rotate(270, 0, 1, 0);
				  rtwd.drawSelf(rt_testur_Id,raceTrackRockId);
			  break;
			  case 7://X��������յ�Z�Ḻ�������� 
			  case 16:
				  rtwd.drawSelf(rt_testur_Id,raceTrackRockId);
			  break;
			}
			MatrixState.popMatrix();
		}
		
		//���������е�ˮ��
		public void drawWaterSingle
		(
			int row,//�������ڵ�ͼ�ϵ���
			int col,//�������ڵ�ͼ�ϵ���			
			float startST		//ˮ�����������ƫ����
		)
		{
			MatrixState.pushMatrix();
			MatrixState.translate(UNIT_SIZE*col, 0, UNIT_SIZE*row);
			water.drawSelf(textureFlagId,startST);	 
			MatrixState.popMatrix();
		}
		
		//��������Ԥ��
		public void sdYb()
		{
			sdCount=0;
			int mrow=MAP_ARRAY.length;
			int mcol=MAP_ARRAY[0].length;
        	        	
			for(int i=0;i<mrow;i++)
			{	//ѭ���к�				
				int rowM=i-bRow;
				if(rowM>=NUMBER_MAP||rowM<=-NUMBER_MAP)
        		{
        			continue;
        		}				
				for(int j=0;j<mcol;j++)
				{
					int colM=j-bCol;
					if(colM>=NUMBER_MAP||colM<=-NUMBER_MAP) 
        			{
        				continue;
        			}	
					if(ClipGrid.CLIP_MASK[directNo][rowM+2][colM+2])
					{
						sdHz[sdCount][0]=i;
						sdHz[sdCount][1]=j;
						sdCount++;
					}
				}
			}
		}
		
		//���ݵ�ͼ�����������
		public void drawSD(float startST)
		{
			for(int i=0;i<sdCount;i++)
			{
				drawSDSingle
				(
					MAP_ARRAY[sdHz[i][0]][sdHz[i][1]],//�������
					sdHz[i][0],//�������ڵ�ͼ�ϵ���
					sdHz[i][1]//�������ڵ�ͼ�ϵ���
				);
			}
		}
		//���ݵ�ͼ������������е�ˮ��
		public void drawWater(float startST)
		{			
			for(int i=0;i<sdCount;i++)
			{
				if(MAP_ARRAY[sdHz[i][0]][sdHz[i][1]]!=8)
				{
					drawWaterSingle
					(
						sdHz[i][0],//�������ڵ�ͼ�ϵ���
						sdHz[i][1],//�������ڵ�ͼ�ϵ���
						startST
					);
				}
			}
		}
		
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			//�����Ӵ���С��λ�� 
			GLES20.glViewport(0, 0, width, height);
			//����GLSurfaceView�Ŀ�߱�
			ratio=(float)width/height;
		}
		
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			synchronized(MyActivity.boatInitLock)
			{
				//������Ļ����ɫRGBA
	            GLES20.glClearColor(0.6039f,0.9333f,0.9843f,1.0f);
	            //�򿪱������
	            GLES20.glEnable(GLES20.GL_CULL_FACE);
	            //����ȼ��
	            GLES20.glEnable(GLES20.GL_DEPTH_TEST);   
	            //��ʼ���任����
	            MatrixState.setInitStack();            
	            ShaderManager.compileShaderHY();
	            //�󱳾�
	            bgd=new BackGround(ShaderManager.getTextureShaderProgram(),1,1,bg_texCoor); 
				//��������ͼ
				pgs_dt=new BackGround(ShaderManager.getTextureShaderProgram(),0.9f,0.11f,pgs_dt_texCoor); 
				//�����������ͼ
				pgs_fgt=new Process(ShaderManager.getPrograssShaderProgram(),0.9f,0.11f,pgs_fgt_texCoor,0);
				//������Ӧ���ֵĶ���0-9�Լ��ٷֺ�
				for(int i=0;i<no.length;i++) 
				{  
					float[] tempTexCoor=new float[]
	  			    {
						0.091f*i,0,  0.091f*i,1,  0.091f*(i+1),1,
	  					0.091f*i,0,  0.091f*(i+1),1,  0.091f*(i+1),0
	  			    };      
					no[i]=new BackGround(ShaderManager.getTextureShaderProgram(),0.06f,0.06f,tempTexCoor); 
				}			
				//���ó�ʼ������id�ķ���
				backGroundId=initTextureFromBitmap(bmbackGround);
				pgs_dt_id=initTextureFromBitmap(bmPgsDt);  
				pgs_fgt_id=initTextureFromBitmap(bmPgsFgt);
				no_texId=initTextureFromBitmap(bmNum);
			}
		}
		
		int step=0;
		public void readLoadTask()
		{
			if(step==13)
			{
				//���ô˷����������͸��ͶӰ����
				MatrixState.setProjectFrustum(-ratio*0.7f, ratio*0.7f, -0.7f*0.7f, 1.3f*0.7f, 1, 300);
				isBegin=false;
				return;
			}
			if(step==0)  
			{
				ShaderManager.compileShader();
				curr_process=8*(step+1);
				pgs_fgt.percent=0.0833f*(step+1);
				step++;
			}
			else if(step==1)  
			{
				//�������ŵ������Ķ���
				rtzd=new RaceTrack(ShaderManager.getMountionShaderProgram(),yArray_ZD,ROWS,COLS,true);
		        //�������ŵĴ�С������������
				rtzddxd=new RaceTrack(ShaderManager.getMountionShaderProgram(),yArray_ZD_DXD,ROWS,COLS,false);
		        //�������������Ķ���
				rtwd=new RaceTrack(ShaderManager.getMountionShaderProgram(),yArray_WD,ROWS,COLS,false);
		        //����ˮ��Ķ���
		        water=new Water(ShaderManager.getWaterShaderProgram(),1,1); 
		        curr_process=8*(step+1);
				pgs_fgt.percent=0.0833f*(step+1);
				step++;
			}
			else if(step==2)
			{
				//����Ҫ���Ƶ����� 
		        boat=new Boat 
		        (
		        		BoatInfo.boatPartNames[BoatInfo.cuttBoatIndex],
		        		MyGLSurfaceView.this,
		        		ShaderManager.getTextureShaderProgram()
		        );	
		        heroBoatTexId=new int[BoatInfo.boatTexIdName[BoatInfo.cuttBoatIndex].length];
		        for(int i=0;i<BoatInfo.boatTexIdName[BoatInfo.cuttBoatIndex].length;i++)
		        {
		        	heroBoatTexId[i]=initTexture(BoatInfo.boatTexIdName[BoatInfo.cuttBoatIndex][i]);
		        }
		        wl=new WeiLang(0.5f,0.8f,2.4f,weilang_texCoor,ShaderManager.getWeiLangShaderProgram());	            
				waterId=initTexture(R.raw.weilang);
				//�����Ǳ�����
				db=new Dashboard(ShaderManager.getTextureShaderProgram());
				//��ʼ���Ǳ������id 
				ybbTexId=initTexture(R.drawable.ybp);
		        curr_process=8*(step+1);
				pgs_fgt.percent=0.0833f*(step+1);
				step++;
			}
			else if(step==3)
			{
				if(isSpeedMode)
				{
					slowBoat=new Boat 
			        (
			        		BoatInfo.boatPartNames[(BoatInfo.cuttBoatIndex+2)%3],
			        		MyGLSurfaceView.this,
			        		ShaderManager.getTextureShaderProgram()
			        );
					slowBoatTexId=new int[BoatInfo.boatTexIdName[(BoatInfo.cuttBoatIndex+2)%3].length];
					for(int i=0;i<BoatInfo.boatTexIdName[(BoatInfo.cuttBoatIndex+2)%3].length;i++)
			        {
						slowBoatTexId[i]=initTexture(BoatInfo.boatTexIdName[(BoatInfo.cuttBoatIndex+2)%3][i]);
			        }
				}				
				//�������Ƶ���ľ
		        ss=new SingleShrub(ShaderManager.getTextureShaderProgram());
		        //����
		        sky=new Sky(ShaderManager.getTextureShaderProgram(),UNIT_SIZE*2.5f);
		        curr_process=8*(step+1);
				pgs_fgt.percent=0.0833f*(step+1);
				step++;
			}
			else if(step==4)
			{
				//��ʼ��3D�����б�
		        bndrawer=new BNDrawer[]
		      	{
		        	new Bridge(ShaderManager.getTextureShaderProgram()),//��
		      		new Tunnel						//���
		      		(
		      			ShaderManager.getTextureShaderProgram(),
		      			ShaderManager.getMountionShaderProgram(),
		      			Constant.yArray_Tunnel,
		      			Constant.yArray_Tunnel.length-1,
		      			Constant.yArray_Tunnel[0].length-1
		      		),
		      		new Mountion					//ɽ
		      		(
		      			ShaderManager.getMountionShaderProgram(),
		      			Constant.yArray_Mountion,
		      			Constant.yArray_Mountion.length-1,
		      			Constant.yArray_Mountion[0].length-1
		      		),
		      		new B_YZ(ShaderManager.getBYZTextureShaderProgram()),//������
		      		new AirShip(ShaderManager.getTextureShaderProgram()),//��ͧ
		      		new Poster(ShaderManager.getTextureShaderProgram()),//�������
		      		new Dock(ShaderManager.getTextureShaderProgram()),//����ͷ
		      		new Castle(ShaderManager.getTextureShaderProgram()),//�Ǳ�
		      	};     
		        curr_process=8*(step+1);
				pgs_fgt.percent=0.0833f*(step+1);
				step++;
			}
			else if(step==5)
			{
				 //����ײ���弯�ϵĳ�ʼ��
		        kzbj_array=new KZBJDrawer[]
		        {
		            new TrafficCylinder(ShaderManager.getTextureShaderProgram(),0.5f,0.5f,1),
		            new Cone(ShaderManager.getTextureShaderProgram(),0.5f,1),
		        };
		        //����ӵģ���ʼ�����ԳԵ���������б�
		        speedForEat=new SpeedForEat[]
                {
		        	new Speed(ShaderManager.getTextureShaderProgram()),
		        	new Speed(ShaderManager.getTextureShaderProgram())
                };
		        curr_process=8*(step+1);
				pgs_fgt.percent=0.0833f*(step+1);
				step++;
			}
			else if(step==6)
			{
				//��ʼ�����ںӵ���������
		        rt_testur_Id=initTexture(R.drawable.grass); 
		        textureFlagId=initTexture(R.drawable.water);	
		        db=new Dashboard(ShaderManager.getTextureShaderProgram());
				ybbTexId=initTexture(R.drawable.ybp);
				dt=new DrawTime(ShaderManager.getTextureShaderProgram());
				timeTexId=initTexture(R.drawable.time);
				goTexId=initTexture(R.drawable.go);
				shacheTexId=initTexture(R.drawable.shache);  
				//���Ӿ��߿�
				houshijing=new com.bn.st.xc.TextureRect(ShaderManager.getTextureShaderProgram(),Self_Adapter_Data_HSJ_XY[screenId][3],Self_Adapter_Data_HSJ_XY[screenId][4]);
				houshijingTexId=initTexture(R.raw.houshijing);
		        curr_process=8*(step+1);
				pgs_fgt.percent=0.0833f*(step+1);
				step++;
			}
			else if(step==7)
			{
				 //�ŵ�����id
		        bridge_id=initTexture(R.drawable.bridge_cm);
		    	//ɽ��ʯͷ������id
		    	rock_id=initTexture(R.drawable.rock);
		    	djsTexId=initTexture(R.drawable.daojishi);
				djsfd=new DaoJiShiForDraw(ShaderManager.getTextureShaderProgram(),ma,MyGLSurfaceView.this);
				if(isSpeedMode)
				{
					quickBoat=new Boat 
			        (
			        		BoatInfo.boatPartNames[(BoatInfo.cuttBoatIndex+1)%3],
			        		MyGLSurfaceView.this,
			        		ShaderManager.getTextureShaderProgram()
			        );
					quickBoatTexId=new int[BoatInfo.boatTexIdName[(BoatInfo.cuttBoatIndex+1)%3].length];
					for(int i=0;i<BoatInfo.boatTexIdName[(BoatInfo.cuttBoatIndex+1)%3].length;i++)
			        {
						quickBoatTexId[i]=initTexture(BoatInfo.boatTexIdName[(BoatInfo.cuttBoatIndex+1)%3][i]);
			        }
				}				
		    	curr_process=8*(step+1);
				pgs_fgt.percent=0.0833f*(step+1);
				step++;
			}
			else if(step==8)
			{
				//��ľ0����id
		    	textureShrubId0=initTexture(R.drawable.shrub);
		    	//��ľ1����id
		    	textrueShrubId1=initTexture(R.drawable.shrub1);		
		    	if(isSpeedMode)
		    	{
		    		//�״����ݵ�����
			    	radarBackGroundTexId=initTexture(R.drawable.rador_bg);
			    	radarZhiZhenTexId=initTexture(R.drawable.rador_plane);
			    	radarOtherBoatTexId=initTexture(R.drawable.other_boat);
			    	radar_Background=new com.bn.st.xc.TextureRect(ShaderManager.getTextureShaderProgram(),Self_Adapter_Data_TRASLATE[screenId][14],0.3f);
			    	radar_Zhizhen=new com.bn.st.xc.TextureRect(ShaderManager.getTextureShaderProgram(),Self_Adapter_Data_TRASLATE[screenId][14]*0.167f,0.05f);
			    	other_Radar_Zhizhen=new com.bn.st.xc.TextureRect(ShaderManager.getTextureShaderProgram(),Self_Adapter_Data_TRASLATE[screenId][14]*0.067f,0.02f);
		    	}		    	
		    	curr_process=8*(step+1); 
				pgs_fgt.percent=0.0833f*(step+1);
				step++;
			}
			else if(step==9)
			{
				textureShrubId2=initTexture(R.drawable.shrub2); 
		    	textrueShrubId3=initTexture(R.drawable.shrub3);
		    	//��Ϸ��ʼ�ͽ���
				gameStartAndEnd=new StartAndEnd(ShaderManager.getTextureShaderProgram());
				gameStartTexId=initTexture(R.drawable.game_start);
				gameEndTexId=initTexture(R.drawable.game_end);
		    	curr_process=8*(step+1);
				pgs_fgt.percent=0.0833f*(step+1);
				step++;
			}
			else if(step==10)
			{
				//�����Ӧ������id
		    	sky_texId=initTexture(R.drawable.sky);
		    	//��ͨ���ͽ�ͨ׶��Ӧ������id
		    	jt_texId=initTexture(R.drawable.jt_wl);
		    	//��������ͼ������������id
				speedUpTexId=initTexture(R.drawable.speed_up);
				speedDownTexId=initTexture(R.drawable.speed_down);
		    	curr_process=8*(step+1);
				pgs_fgt.percent=0.0833f*(step+1);
				step++;
			}
			else if(step==11)
			{
				texAirShipBody=initTexture(R.drawable.airship_st);
				texAirShipWy=initTexture(R.drawable.airship_wy);
				raceTrackRockId=initTexture(R.drawable.hd_rock);
				//������Ӷ�Ӧ������id
				ggSzTexId=initTexture(R.drawable.gg_sz);
				ggTexId[0]=initTexture(R.drawable.gg_0);
				ggTexId[1]=initTexture(R.drawable.gg_1);
				ggTexId[2]=initTexture(R.drawable.gg_2);
		        //����ͷ������id
		        dockTexId=initTexture(R.drawable.wood);
		        //�Ǳ���Ҫ����������id
				castleTexIdA=initTexture(R.drawable.castle0);
				castleTexIdB=initTexture(R.drawable.castle1);
		        
				//��ʼ��3D�����б�
		        initTDObjectList();
		        curr_process=8*(step+1);
				pgs_fgt.percent=0.0833f*(step+1);
				step++;
			}
			else if(step==12)
			{
				 //��ʼ�����б�
		        initTreeList();
		        initKzbjList();
		        //��ʼ�����ԳԵ��������б�
		        initCanEatList();
		        if(isSpeedMode&&otherPaths.size()==0)
		        {
		        	 //��ʼ��������������·��
					for(int i=0;i<qtCount;i++)
					{
						ArrayList<float[]> pathTemp=PathUtil.generatePath();
						pathTemp.get(0)[0]=otherBoatLocation[i][0];
						pathTemp.get(0)[1]=otherBoatLocation[i][1];
						otherPaths.add(pathTemp);
					}
		        }		       
		        curr_process=100; 
				pgs_fgt.percent=1.0f;
				step++;
			}
		}		
	}
	
	//��ʼ��������������id
	public int initTexture(int drawableId)
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
        
        //ͨ������������ͼƬ
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
        //ͨ������������ͼƬ
        
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
	
	public int initTextureFromBitmap(Bitmap bitmapTmp)
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
        		bitmapTmp, 			  //����ͼ��
        		0					  //����߿�ߴ�
        );       
        return textureId;
	}
	
	//3D����Ԥ��
	int[] tdHz=new int[64];
	int tdCount=0;
	public void tdYb()
	{
		tdCount=0;
		
		for(int i=0;i<tdObjectList.size();i++)
		{
			int rowM=tdObjectList.get(i).rows-bRow;
			int colM=tdObjectList.get(i).cols-bCol;			
			
			if(rowM>=NUMBER_MAP||rowM<=-NUMBER_MAP||
			  colM>=NUMBER_MAP||colM<=-NUMBER_MAP)
    		{
    			continue;
    		}	
			
			if(ClipGrid.CLIP_MASK[directNo][rowM+2][colM+2])
			{
				tdHz[tdCount]=i;
				tdCount++;
			}
		}
	}
	
	
	//���Ʒ���
	public void DrawTDObjects(int dyFlag)
	{
		for(int i=0;i<tdCount;i++)
		{
			tdObjectList.get(tdHz[i]).drawSelf(texIdList.get(tdHz[i]),dyFlag);
		}
	}
	
	
	//��������Ԥ������
	List<ShrubForControl> treeListTemp=new ArrayList<ShrubForControl>();
	public void treeYb()
	{
		treeListTemp.clear();  		
		//ѭ���б�   
		for(int i=0;i<treeList.size();i++)
		{			  
			int rowM=treeList.get(i).rows-bRow;
			int colM=treeList.get(i).cols-bCol;
			
			//����25����ѭ���ж�
			if(rowM>=NUMBER_MAP||rowM<=-NUMBER_MAP||
			   colM>=NUMBER_MAP||colM<=-NUMBER_MAP)
    		{
    			continue;
    		}	
			
			if(ClipGrid.CLIP_MASK[directNo][rowM+2][colM+2])
			{
				treeListTemp.add(treeList.get(i));
			}
		}   
	}
	
	public void drawTrees(int dyFlag)
	{  
		//����Զ������
		if(dyFlag==1)
		{
			Collections.sort(treeListTemp);
		}		
		for(int i=0;i<treeListTemp.size();i++)
		{
			//������ľ
            ss.drawSelf
            (
            		treeListTemp.get(i).texIds, 
            		treeListTemp.get(i).id,
            		treeListTemp.get(i).xoffset,
            		treeListTemp.get(i).yoffset,
            		treeListTemp.get(i).zoffset,
            		dyFlag
            );
		}
	}
	
	//����ײ���壨��ͨ׶����ͨ��������Ԥ��
	int[] kzHz=new int[64];
	int kzCount=0;
	public void kzYb()
	{
		kzCount=0;
		for(int i=0;i<kzbjList.size();i++)
		{
			int rowM=kzbjList.get(i).row-bRow;
			int colM=kzbjList.get(i).col-bCol;
			
			//����25����ѭ���ж�
			if(rowM>=NUMBER_MAP||rowM<=-NUMBER_MAP||
					  colM>=NUMBER_MAP||colM<=-NUMBER_MAP)
    		{
    			continue;
    		}			
			
			if(ClipGrid.CLIP_MASK[directNo][rowM+2][colM+2])
			{
				kzHz[kzCount]=i;
				kzCount++;
			}
		}		
	}
	
	//���ƿ���ײ����ķ���
	public void drawKZBJ(int dyFlag)
    {
		for(int i=0;i<kzCount;i++)
		{
			kzbjList.get(kzHz[i]).drawSelf(jt_texId,dyFlag);
		}
    }
	
	//��ʼ��List<TDObjectForControl> tdObjectList���ϵķ���
	public void initTDObjectList()
	{
		int tempId=0;
		for(int i=0;i<PART_LIST.length;i++)
		{
			if(PART_LIST[i][0]==0)//��  
			{
				tempId=0;
				int[] tempArray=new int[]
				{
					bridge_id
				};
				texIdList.add(tempArray);
				//��ֱ����1
				if(PART_LIST[i][4]==0)
				{
					pzzList.add(new PZZ(PART_LIST[i][1]+6.3f,PART_LIST[i][3]-6.3f,PART_LIST[i][5],PART_LIST[i][6],0));
					pzzList.add(new PZZ(PART_LIST[i][1]+23.1f,PART_LIST[i][3]-6.3f,PART_LIST[i][5],PART_LIST[i][6],0));
				}
				else if(PART_LIST[i][4]==-90)//���ŵ���0
				{					
					pzzList.add(new PZZ(PART_LIST[i][1]+2.1f,PART_LIST[i][3]+6.3f,PART_LIST[i][5],PART_LIST[i][6],-90));
					pzzList.add(new PZZ(PART_LIST[i][1]+2.1f,PART_LIST[i][3]+23.1f,PART_LIST[i][5],PART_LIST[i][6],-90));
				}
			}
			else if(PART_LIST[i][0]==1)//���
			{
				tempId=1;
				int[] tempArray=new int[]
				{
					bridge_id,rt_testur_Id,rock_id
				};
				texIdList.add(tempArray);
			}
			else if(PART_LIST[i][0]==2)//ɽ
			{
				tempId=2;
				int[] tempArray=new int[]
				{
					rt_testur_Id,rock_id
				};
				texIdList.add(tempArray);
			}
			else if(PART_LIST[i][0]==3)//������
			{
				tempId=3;
				int[] tempArray=new int[]
				{
					rt_testur_Id,rock_id
				};
				texIdList.add(tempArray);
			}
			else if(PART_LIST[i][0]==4)//��ͧ
			{
				tempId=4;
				int[] tempArray=new int[]
				{
					texAirShipBody,texAirShipWy
				};
				texIdList.add(tempArray);
			}
			else if(PART_LIST[i][0]==5)//�������
			{
				tempId=5;
				int[] tempArray=new int[]
				{
					ggSzTexId,ggTexId[0],ggTexId[1],ggTexId[2]
				};
				texIdList.add(tempArray);
			}
			else if(PART_LIST[i][0]==6)//����ͷ
			{
				tempId=6;
				int[] tempArray=new int[]
				{
					dockTexId
				};
				texIdList.add(tempArray);
			}
			else if(PART_LIST[i][0]==7)//�Ǳ�
			{
				tempId=7;
				int[] tempArray=new int[]
				{
					castleTexIdA,castleTexIdB 
				};
				texIdList.add(tempArray);
			}
			tdObjectList.add
			(
				new TDObjectForControl
				(
					bndrawer[tempId],
					(int)(PART_LIST[i][0]),
					PART_LIST[i][1],
					PART_LIST[i][2],
					PART_LIST[i][3],
					PART_LIST[i][4],
					(int)(PART_LIST[i][5]),
					(int)(PART_LIST[i][6])
				)
			);
		}
	}
	
	public void initTreeList()
	{
		for(int i=0;i<Tree_Data.length;i++)
		{
			if(Tree_Data[i][0]==0)//��0����
			{
				treeList.add
				(
					new ShrubForControl
					(
						(int)Tree_Data[i][4],
						(int)Tree_Data[i][5],
						Tree_Data[i][1],
						Tree_Data[i][2],
						Tree_Data[i][3],
						(int)Tree_Data[i][0],
						textureShrubId0
					)	
				);
			}
			else if(Tree_Data[i][0]==1)//��1����  
			{
				treeList.add
				(
					new ShrubForControl  
					(
						(int)Tree_Data[i][4],
						(int)Tree_Data[i][5],
						Tree_Data[i][1],
						Tree_Data[i][2],
						Tree_Data[i][3],
						(int)Tree_Data[i][0],
						textrueShrubId1
					)	
				);				
			}
			else if(Tree_Data[i][0]==2)//��1����  
			{
				treeList.add
				(
					new ShrubForControl  
					(
						(int)Tree_Data[i][4],
						(int)Tree_Data[i][5],
						Tree_Data[i][1],
						Tree_Data[i][2],
						Tree_Data[i][3],
						(int)Tree_Data[i][0],
						textureShrubId2
					)	
				);				
			}	
			else if(Tree_Data[i][0]==3)//��1����  
			{
				treeList.add
				(
					new ShrubForControl  
					(
						(int)Tree_Data[i][4],
						(int)Tree_Data[i][5],
						Tree_Data[i][1],
						Tree_Data[i][2],
						Tree_Data[i][3],
						(int)Tree_Data[i][0],
						textrueShrubId3
					)	
				);				
			}	
		}
	}
	
	//��ʼ������ײ����б��ϵķ���
	public void initKzbjList()
	{
		for(int i=0;i<KZBJ_ARRAY.length;i++)
		{
			if(KZBJ_ARRAY[i][0]==0)//��ͨ��
			{
				kzbjList.add
				(
					new KZBJForControl
					(
						kzbj_array[0],
						(int)KZBJ_ARRAY[i][0],
						KZBJ_ARRAY[i][1],
						KZBJ_ARRAY[i][2],
						KZBJ_ARRAY[i][3],
						(int)KZBJ_ARRAY[i][4],
						(int)KZBJ_ARRAY[i][5],
						ma
					)
				);
			}
			else if(KZBJ_ARRAY[i][0]==1)//��ͨ׶
			{
				kzbjList.add
				(
					new KZBJForControl
					(
						kzbj_array[1],
						(int)KZBJ_ARRAY[i][0],
						KZBJ_ARRAY[i][1],
						KZBJ_ARRAY[i][2],
						KZBJ_ARRAY[i][3],
						(int)KZBJ_ARRAY[i][4],
						(int)KZBJ_ARRAY[i][5],
						ma
					)
				);
			}
		}
	}
	public static long gameContinueTime()//��ȡ��Ϸʱ��
    {
    	return System.currentTimeMillis()-gameStartTime;
    } 
	//����ӵģ���ʼ�����ԳԵ�������б�
	public void initCanEatList()
	{
		int tempIndex=0;
		for(int i=0;i<KEAT_ARRAY.length;i++)
		{
			if(KEAT_ARRAY[i][0]==0)//�ӵ���
			{
				tempIndex=0;
			}
			else if(KEAT_ARRAY[i][0]==1)//����
			{
				tempIndex=1;//speedWtTexId
			}
			
			speedWtList.add
			(
				new SpeedForControl
				(
					speedForEat[tempIndex],
					(int)KEAT_ARRAY[i][0],
					KEAT_ARRAY[i][1],
					KEAT_ARRAY[i][2],
					KEAT_ARRAY[i][3],
					KEAT_ARRAY[i][4],
					KEAT_ARRAY[i][5],
					KEAT_ARRAY[i][6],
					ma
				)
			);
		}
	}
	
	//�Ӽ���Ԥ��
	int[] speedHz=new int[64];
	int speedCount=0;
	public void speedForEatYb()
	{
		speedCount=0;
		for(int i=0;i<speedWtList.size();i++)
		{
			int rowM=(int) (speedWtList.get(i).rows-bRow);
			int colM=(int) (speedWtList.get(i).cols-bCol);
			
			//����25����ѭ���ж�
			if(rowM>=NUMBER_MAP||rowM<=-NUMBER_MAP||
					  colM>=NUMBER_MAP||colM<=-NUMBER_MAP)
    		{
    			continue;
    		}			
			
			if(ClipGrid.CLIP_MASK[directNo][rowM+2][colM+2])
			{
				speedHz[speedCount]=i;
				speedCount++;
			}
		}
	}	
	
	//����ӵģ����Ƽ��١���������
	public void drawSpeedForEat(int dyFlag)
	{
        for(int i=0;i<speedCount;i++)
        {
        	//���ƽ�ͨ׶����ͨ��
			if(speedWtList.get(speedHz[i]).id==0)//�ӵ�����
			{
				speedWtList.get(speedHz[i]).drawSelf(speedUpTexId,dyFlag);//���������id��Ҫ�滻Ϊ������
			}
			else if(speedWtList.get(speedHz[i]).id==1)//���ٵ�
			{
				speedWtList.get(speedHz[i]).drawSelf(speedDownTexId,dyFlag);//���������id��Ҫ�滻Ϊ���ٵ� 
			}
        }
	}
}