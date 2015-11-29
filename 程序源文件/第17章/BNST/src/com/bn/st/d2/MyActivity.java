package com.bn.st.d2;
import java.util.HashMap;
import com.bn.R;
import com.bn.clp.BoatInfo;
import com.bn.clp.Constant;
import com.bn.clp.DaoJiShiForDraw;
import com.bn.clp.KeyThread;
import com.bn.clp.MyGLSurfaceView;
import com.bn.clp.RotateThread;
import com.bn.st.xc.CircleForDraw;
import com.bn.st.xc.CylinderTextureByVertex;
import com.bn.st.xc.XCSurfaceView;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import static com.bn.clp.Constant.BgSoundFlag;
import static com.bn.clp.Constant.COLS;
import static com.bn.clp.Constant.ROWS;
import static com.bn.clp.Constant.generateMountion;
import static com.bn.clp.Constant.generatePDY;
import static com.bn.clp.Constant.generateTunnel;
import static com.bn.clp.Constant.generateWDY;
import static com.bn.clp.Constant.generateZDY;
import static com.bn.clp.Constant.generateZDY_XD;
import static com.bn.clp.Constant.yArray_ZD;
import static com.bn.st.xc.Constant.*;
/*
 * ��Ϊѡ������
 */
enum WhichView
{
	WELCOME_VIEW,MENU_VIEW,XC_VIEW,GAME_VIEW,HELP_VIEW,SOUND_VIEW,GAME_MODE_VIEW,GUANYU_VIEW,RECORD_VIEW
};
public class MyActivity extends Activity 
{
	static WhichView curr;
	public static Object boatInitLock=new Object();
	public MyGLSurfaceView gameV;
	XCSurfaceView xcV;//ѡ������������
	public Handler hd;//��Ϣ������
	
	ViewForDraw vfd;
	
	WelcomeView welV;//��ӭ������������
	MenuView menuV;//�˵����������	
	SoundSurfaceView ssv;//�������������
	HelpSurfaceView hsv;//��Ϸ�������������
	GameModeView gmv;//��Ϸģʽѡ����������
	GuanYuView gyv;//���ڽ��������
	DSurfaceView dsv;//��¼��ѯ���������
	
	//============================
	CheckVersionDialog cvDialog;
	AndroidVersionDialog avDialog;
	BreakRecordOrNotDialog bronDialog;//�Ƿ��Ƽ�¼�ĶԻ���
	RankingDialog rDialog;//���ζԻ���
	int flag;
	//==============================
	
	public MediaPlayer beijingyinyue;//��Ϸ�������ֲ�����
	SoundPool shengyinChi;//������
	HashMap<Integer,Integer> soundIdMap;//������������ID���Զ�������ID��Map
	
	//SensorManager�������� 
	SensorManager mySensorManager;	
	
	SharedPreferences sp;
	  
	//����ʵ����SensorEventListener�ӿڵĴ�����������
	private SensorEventListener mySensorListener = new SensorEventListener()
	{
		@Override    
		public void onAccuracyChanged(Sensor sensor, int accuracy) {			
			   
		} 
		@Override
		public void onSensorChanged(SensorEvent event) 
		{
			//��ʹ�ô�������־λΪtrue,���ҳ����ɴ��ر�־λΪtrue,��ʹ�ô������� 			
			if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			{//�ж��Ƿ�Ϊ���ٶȴ������仯����������
				float[] values=event.values;
				if(values[1]<-2)
				{//right
					MyGLSurfaceView.keyState=MyGLSurfaceView.keyState|0x4;
				}
				else if(values[1]>2)
				{//left
					MyGLSurfaceView.keyState=MyGLSurfaceView.keyState|0x8;
				}
				else  
				{//no left and no right  
					MyGLSurfaceView.keyState=MyGLSurfaceView.keyState&0x3;
				}
			}	
		}		
	}; 	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {  
        super.onCreate(savedInstanceState);
        
        //��ȡSharedPreferences
        sp=this.getSharedPreferences("actm", Context.MODE_PRIVATE);
        //���ֵ��Ĭ��ֵΪtrue
        com.bn.clp.Constant.BgSoundFlag = sp.getBoolean("bgSoundFlag", true);
        com.bn.clp.Constant.SoundEffectFlag = sp.getBoolean("soundEffectFlag", true);     
                        
        //��ʼ��������Դ
        chushihuaSounds(); 
        //����Ϊȫ��
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//����Ϊ����ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		//��Ϸ������ֻ���������ý�������������������ͨ��������
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		//�������ݿ��
		DBUtil.createTable();
		
		flag=Settings.System.getInt(this.getContentResolver(),
		         Settings.System.ACCELEROMETER_ROTATION, 0);
		        Settings.System.putInt(this.getContentResolver(),
		         Settings.System.ACCELEROMETER_ROTATION,1);  
		
		SCREEN_WIDTH=getWindowManager().getDefaultDisplay().getWidth();
		SCREEN_HEIGHT=getWindowManager().getDefaultDisplay().getHeight();
		
		float screenHeightTemp=SCREEN_HEIGHT;//��¼ϵͳ���ص���Ļ�ֱ��ʡ�
        float screenWidthTemp=SCREEN_WIDTH;
        
        if(screenHeightTemp>screenWidthTemp) //ָ����Ļ�Ŀ�͸ߡ�
        {
        	SCREEN_WIDTH=screenHeightTemp;
        	SCREEN_HEIGHT=screenWidthTemp;
        }
        com.bn.clp.Constant.screenRatio=SCREEN_WIDTH/SCREEN_HEIGHT;//��ȡ��Ļ�Ŀ�߱�
        if(Math.abs(com.bn.clp.Constant.screenRatio-com.bn.clp.Constant.screenRatio854x480)<0.001f)
        {
        	com.bn.clp.Constant.screenId=1;
       	}
        else if(Math.abs(com.bn.clp.Constant.screenRatio-com.bn.clp.Constant.screenRatio480x320)<0.01f)
        {
        	com.bn.clp.Constant.screenId=3;
        }
        else if(Math.abs(com.bn.clp.Constant.screenRatio-com.bn.clp.Constant.screenRatio960x540)<0.001f)
        {
        	com.bn.clp.Constant.screenId=2;
        }
        else
        {
        	com.bn.clp.Constant.screenId=0;
        }
        
		ratio_height=SCREEN_HEIGHT/480;
		ratio_width=SCREEN_WIDTH/854;
		//���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		new Thread()
		{
		   public void run()
		   {
			   synchronized(boatInitLock)
			   {
				   //ѡ����
				   CylinderTextureByVertex.initVertexData(14, 6, 8, 1);
				   CircleForDraw.initVertexData(8, 14);
				   XCSurfaceView.loadWelcomeBitmap(MyActivity.this.getResources());
				   XCSurfaceView.loadVertexFromObj(MyActivity.this.getResources());
				   com.bn.st.xc.ShaderManager.loadCodeFromFile(MyActivity.this.getResources());
				   
				    //��������
				    //����ֱ����Y��������
			        generateZDY();
			        //����ֱ����С����Y��������
			        generateZDY_XD(getResources());
			        //��������������Y��������
			        generateWDY();
			        //����ƽ��������Y��������
			        generatePDY();			        
			        generateMountion(getResources());
			        generateTunnel(getResources());			        
					ROWS=yArray_ZD.length-1;
					COLS=yArray_ZD[0].length-1; 
					MyGLSurfaceView.loadProgressBitmap(MyActivity.this.getResources());
					com.bn.clp.ShaderManager.loadCodeFromFile(MyActivity.this.getResources());
			   }
		   }
		}.start();
		
		//��ʼ��GLSurfaceView
        hd=new Handler()
        {
			@Override
			public void handleMessage(Message msg)
			{
        		switch(msg.what)
        		{
	        		case 0://ȥ��ӭ����
	        			gotoWelcomeView();
	        		break;
	        		case 1://ȥ���˵�����
	        			gotoMenuView();
	        		break;
	        		case 2://ȥģʽѡ�����
	        			gotoGameModeView();
	                break;
	        		case 3://ȥѡͧ����
	        			gotoXCView();
	        		break;
	        		case 4://ȥ��Ч���ý���
	        			gotoMusicSetView();
	        		break;
	        		case 5://ȥ��Ϸ��������
	        			gotoHelpView();
	        		break;
	        		case 6://ȥ���ڽ���
	        			gotoGuanYuView();
	        		break;
	        		case 7://�������˳���Ϸ��ť
	        			Settings.System.putInt(MyActivity.this.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION,flag);
	        			System.exit(0);
	        		break;
	        		case 8://ȥ��Ϸ����-��ʱģʽ
	        			gotoTimerGameView();
	        		break;
	        		case 9://ȥ��Ϸ����-����ģʽ
	        			gotoSpeedGameView();
	        		break;
	        		case 10://ȥ��Ϸ��¼��ѯ����
	        			gotoRecordView();
	        		break;
	        		case 11://��ʾ�Ƿ��Ƽ�¼�Ի���
	        			showDialog(3);
	        		break;
	        		case 12://��ʾ��ǰ���ζԻ���
	        			showDialog(4);
	        		break;
        		}
			}
        };
        hd.sendEmptyMessage(0);    
    }
    //��ת����Ϸ��������
    public void gotoHelpView()
    {
    	if(hsv==null)
    	{
    		hsv=new HelpSurfaceView(MyActivity.this);
    	}    	
    	hsv.initThread();
    	vfd.curr=hsv;
		curr=WhichView.HELP_VIEW;
    }
    //��ת����Ч���ý���
    public void gotoMusicSetView()
    {
    	if(ssv==null)
    	{
    		ssv=new SoundSurfaceView(MyActivity.this);
    	}
    	ssv.initThread();
    	vfd.curr=ssv;
		curr=WhichView.SOUND_VIEW;
    }    
    //��ת����Ϸģʽѡ�����
    public void gotoGameModeView()
    {
    	if(gmv==null)
    	{
    		gmv=new GameModeView(MyActivity.this);
    	}
    	if(!vfd.flag)
    	{
    		vfd.initThread();
    		setContentView(vfd); 
    	}
    	
    	gmv.initThread();
    	vfd.curr=gmv;
		curr=WhichView.GAME_MODE_VIEW;
    }    
    //��ת�����ڽ���ķ���
    public void gotoGuanYuView()
    {
    	if(gyv==null)
    	{
    		gyv=new GuanYuView(MyActivity.this);
    	}    	
    	vfd.curr=gyv;
    	curr=WhichView.GUANYU_VIEW;
    }
    //��ת����ӭ����ķ���
    public void gotoWelcomeView()
    {
    	welV=new WelcomeView(MyActivity.this);
		setContentView(welV);
		curr=WhichView.WELCOME_VIEW;
    }
    //��ת���˵�����
    public void gotoMenuView()
    {
    	if(vfd==null)
    	{    		
    		vfd=new ViewForDraw(this);    		   		
    	}
    	if(!vfd.flag)
    	{
    		vfd.initThread();
    		setContentView(vfd); 
    	}
    	if(menuV==null)
    	{
    		menuV=new MenuView(MyActivity.this);
    	}    
    	menuV.initThread();
    	vfd.curr=menuV;
		curr=WhichView.MENU_VIEW;
    }
    //��ת��ѡͧ����
    public void gotoXCView()
    {
    	vfd.flag=false;
    	xcV = new XCSurfaceView(MyActivity.this);
    	xcV.index_boat=BoatInfo.cuttBoatIndex;
    	
        setContentView(xcV);	
        xcV.requestFocus();//��ȡ����
        xcV.setFocusableInTouchMode(true);//����Ϊ�ɴ���
        curr=WhichView.XC_VIEW;
    }
    //��ת����ʱģʽ��Ϸ����
    public void gotoTimerGameView()
    {
    	vfd.flag=false;
    	//������Ϸ�в����ķ���
    	restartGame();
    	com.bn.clp.Constant.isSpeedMode=false;
    	KeyThread.otherBoatFlag=false;
    	if(BgSoundFlag&&beijingyinyue==null)
		{
    		beijingyinyue=MediaPlayer.create(this,com.bn.R.raw.backsound);
        	beijingyinyue.setLooping(true);//�Ƿ�ѭ��
			beijingyinyue.start();
		} 
    	gameV = new MyGLSurfaceView(MyActivity.this);
        //��ȡ����
        gameV.requestFocus();
        //����Ϊ�ɴ���
        gameV.setFocusableInTouchMode(true);
        setContentView(gameV);      
        curr=WhichView.GAME_VIEW;
    }
    //��ת������ģʽ��Ϸ����
    public void gotoSpeedGameView()
    {
    	vfd.flag=false;
    	//������Ϸ�в����ķ���
    	restartGame();
    	com.bn.clp.Constant.isSpeedMode=true;
    	KeyThread.otherBoatFlag=true;
    	if(BgSoundFlag&&beijingyinyue==null)
		{
    		beijingyinyue=MediaPlayer.create(this,com.bn.R.raw.backsound);
        	beijingyinyue.setLooping(true);//�Ƿ�ѭ��
			beijingyinyue.start();
		}
    	gameV = new MyGLSurfaceView(MyActivity.this);
        //��ȡ����
        gameV.requestFocus();
        //����Ϊ�ɴ���
        gameV.setFocusableInTouchMode(true);
        setContentView(gameV);      
        curr=WhichView.GAME_VIEW;
    }
    //��ת����¼��ѯ����
    public void gotoRecordView()
    {
    	if(dsv==null)
    	{
    		com.bn.st.xc.Constant.initBitmap(this.getResources());
            com.bn.st.xc.Constant.initLoaction();
        	dsv=new DSurfaceView(MyActivity.this);
    	}
        dsv.init();

		vfd.curr=dsv;
		curr=WhichView.RECORD_VIEW;
    } 
    //��ʼ����Ϸ�еľ�̬�����ķ���-���½�����Ϸ 
    public void restartGame()  
    { 
    	KeyThread.upFlag=true;
    	DaoJiShiForDraw.DAOJISHI_FLAG=true;
    	com.bn.clp.Constant.CURR_BOAT_V=0; 
    	com.bn.clp.Constant.BOAT_A=0;
    	com.bn.clp.Constant.isPaused=false;
    	com.bn.clp.Constant.DEGREE_SPAN=0;  
    	com.bn.clp.Constant.RANK_FOR_HELP=1;
    	com.bn.clp.Constant.halfFlag=false;
    	com.bn.clp.Constant.CURR_BOAT_V_TMD=0;
    	com.bn.clp.Constant.numberOfN2=0;
    	com.bn.clp.Constant.RANK_FOR_HELP=1; 
    	com.bn.clp.Constant.BOAT_LAP_NUMBER_OTHER=new int[2];
    	com.bn.clp.Constant.BOAT_LAP_NUMBER_OTHER[0]=1;
    	com.bn.clp.Constant.BOAT_LAP_NUMBER_OTHER[1]=1;
//    	com.bn.clp.Constant.isFirstPersonView=false;
    	MyGLSurfaceView.isDaoJiShi=true;
    	MyGLSurfaceView.isAllowToClick=false;
    	MyGLSurfaceView.isJiShi=false;
    	MyGLSurfaceView.sight_angle=com.bn.clp.Constant.DIRECTION_INI;    	
    	MyGLSurfaceView.yachtLeftOrRightAngle=0;
    	//����ӵ�
		MyGLSurfaceView.betweenStartAndPauseTime=0;
		Constant.gameTimeUse=0;
		Constant.numberOfTurns=1;
		Constant.threadFlag=true;
		new RotateThread().start();
    }
    public void exitGame()
    {
    	gameV.onPause();
    	gameV.otherPaths.clear();
    	gameV.tdObjectList.clear();
    	gameV.pzzList.clear();
    	gameV.speedWtList.clear();
    	gameV.treeList.clear();
    	gameV.kzbjList.clear();
    	
    	Constant.threadFlag=false;
    	gameV.kt.flag=false;
		gameV.tc.flag=false;
		gameV.tfe.flag=false;
		DaoJiShiForDraw.DAOJISHI_FLAG=false;
		if(beijingyinyue!=null)
		{
			beijingyinyue.stop();
			beijingyinyue=null;
		}  
		gameV=null;
    }
    @Override
    public void onResume()
    {
    	super.onResume();
    	//ע�������
        mySensorManager.registerListener
        (			
				mySensorListener, 					//����������
				mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),	//����������
				//SensorManager.SENSOR_DELAY_UI		//�������¼����ݵ�Ƶ��
				SensorManager.SENSOR_DELAY_GAME
		);   
        Constant.threadFlag=true;  
        if(gameV!=null)
        {
        	gameV.kt.moveFlag=true;
        }
    }
    @Override
    public void onPause()
    {
    	super.onPause();
    	mySensorManager.unregisterListener(mySensorListener);	//ȡ��ע�������
        Constant.threadFlag=false;
        if(gameV!=null)
        {
        	gameV.kt.moveFlag=false;
        }
    }  
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e)
    { 
    	//���������ϼ���24���¼���25����ǰ���ڰ���ʱֱ���߸�����Ե������ú�Ҳ������
    	if((keyCode==24)||(keyCode==25))
    	{
        	return false;
    	}
    	else if(keyCode==4)  
    	{
    		if(curr==WhichView.MENU_VIEW&&menuV.move_flag==0)
    		{    			
    			Settings.System.putInt(this.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION,flag);
    			System.exit(0);
    		}
    		else if(curr==WhichView.XC_VIEW)
    		{
    		    xcV.flagForThread=false;
    		    xcV.flag_display=false;
    		    xcV=null;
    			gotoMenuView();
    		}
    		else if(curr==WhichView.GAME_VIEW)
    		{
    			if(DaoJiShiForDraw.DAOJISHI_FLAG)
    			{
    				return true;
    			}
    			else
    			{
    				gotoGameModeView();
    				exitGame();    				
	    			gameV=null;  
    			}    			
    		}
    		else if(curr==WhichView.GAME_MODE_VIEW)
    		{
    			if(gmv==null)
    			{
    				return false;
    			}
    			gmv.move_flag=-1;
    			gmv.flag_go=false;
    			gotoMenuView();
    		}
    		else if(curr==WhichView.SOUND_VIEW)
    		{
    			if(ssv==null)
    			{
    				return false;
    			}
    			ssv.move_flag=-1;
    			ssv.flag_go=false;
    			gotoMenuView();
    		}
    		else if(curr==WhichView.HELP_VIEW)
    		{
    			hsv.flag_go=false; 
    			gotoMenuView();
    		}
    		else if(curr==WhichView.GUANYU_VIEW)
    		{
    			gotoMenuView();
    		}
    		else if(curr==WhichView.RECORD_VIEW)
    		{
    			gotoGameModeView();
    		}
    	}
    	return true;
    }
    
    //���������ķ���
    public void chushihuaSounds()
    {
    	shengyinChi=new SoundPool
    	(
    		7,
    		AudioManager.STREAM_MUSIC,  
    		100
    	);
    	soundIdMap=new HashMap<Integer,Integer>();
    	soundIdMap=new HashMap<Integer,Integer>();
    	soundIdMap.put(1, shengyinChi.load(this,com.bn.R.raw.pengzhuang,1));//��ײ����
    	soundIdMap.put(2, shengyinChi.load(this,com.bn.R.raw.boatgo,1));//�����ٵ�����
    	soundIdMap.put(3, shengyinChi.load(this,com.bn.R.raw.eatthings1,1));//�Զ���������
    	soundIdMap.put(4, shengyinChi.load(this,com.bn.R.raw.zhuangfei,1));//ײ�ɶ���������
    	soundIdMap.put(5, shengyinChi.load(this,com.bn.R.raw.daojishi,1));//321����ʱ������
    	soundIdMap.put(6, shengyinChi.load(this,com.bn.R.raw.start,1));//��ʼ�����ߵ�����
    }
    
  //���������ķ���
    public void shengyinBoFang(int sound,int loop)
    {
    	AudioManager mgr=(AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
    	float streamVolumeCurrent=mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
    	float streamVolumeMax=mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    	float volume=streamVolumeCurrent/streamVolumeMax;
    	shengyinChi.play(soundIdMap.get(sound), volume, volume, 1, loop, 1f);
    }
    public int getGLVersion() //��ȡOPENGLES��֧�ֵ���߰汾
    {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        int majorVersion=info.reqGlEsVersion;
        majorVersion=majorVersion>>>16;
        return majorVersion;
    }
    
    
    public Dialog onCreateDialog(int id)   
    {
    	Dialog result=null;
    	switch(id)
    	{
	    	case 1:
	    		cvDialog=new CheckVersionDialog(this);
	    		cvDialog.setCancelable(false);
	    		result=cvDialog;
	    	break;
	    	case 2:
	    		avDialog=new AndroidVersionDialog(this);
	    		avDialog.setCancelable(false);
	    		result=avDialog;
	    	break;
	    	case 3:
	    		bronDialog=new BreakRecordOrNotDialog(this);
	    		bronDialog.setCancelable(false);
	    		result=bronDialog;
	    	break;
	    	case 4:
	    		rDialog=new RankingDialog(this);
	    		rDialog.setCancelable(false);
	    		result=rDialog;
	    	break;
    	} 
		return result;
    }
    public void onPrepareDialog(int id, Dialog dialog)
    {
    	//�����ǵȴ��Ի����򷵻�
    	switch(id)
    	{
    	  case 1:
    		   Button bok=(Button)cvDialog.findViewById(R.id.ok_button);
    		   bok.setOnClickListener(
    				new OnClickListener()
    				{
						@Override
						public void onClick(View v) 
						{
							Settings.System.putInt(MyActivity.this.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION,flag);
							System.exit(0);
						}	
    				}
    		   );
    	  break;
    	  case 2:
   		  Button ok=(Button)avDialog.findViewById(R.id.ok);
   		   ok.setOnClickListener(
   				new OnClickListener()
   				{
						@Override
						public void onClick(View v) 
						{
							Settings.System.putInt(MyActivity.this.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION,flag);
							System.exit(0);
						}	
   				}
   		   );
   		   break;
    	  case 3:
    		  ok=(Button)bronDialog.findViewById(R.id.ok_button);
    		  if(!com.bn.clp.Constant.isBreakRecord)
    		  {
    				TextView tv=(TextView) bronDialog.findViewById(R.id.tview);
    				tv.setText("�ǳ���ϧ��û�д��Ƽ�¼�����ٽ�������");
    		  }
    		  else
    		  {
    				TextView tv=(TextView) bronDialog.findViewById(R.id.tview);
    				tv.setText("��ϲ�����ɹ���ͻ�Ƽ�¼��");    				
    		  }
    		  ok.setOnClickListener(
      				new OnClickListener()
      				{
   						@Override
   						public void onClick(View v)  
   						{
   							bronDialog.cancel();
   							MyActivity.this.hd.sendEmptyMessage(2);
   							exitGame();
   							gameV=null;
   							
   						}	
      				}
      		   );
    	  break;
    	  case 4:
    		 ok=(Button)rDialog.findViewById(R.id.ok_button);
    		 TextView tv=(TextView) rDialog.findViewById(R.id.tview);
    		 tv.setText("��ϲ����������˵�"+com.bn.clp.Constant.RANK_FOR_HERO_BOAT+"����");
	   		 ok.setOnClickListener(
 				new OnClickListener()
 				{  
					@Override
					public void onClick(View v)
					{
						rDialog.cancel();
						MyActivity.this.hd.sendEmptyMessage(2);
						exitGame();
						gameV=null;	  							
					}	
 				}
     		  );
   		   break;
    	}
    }
}