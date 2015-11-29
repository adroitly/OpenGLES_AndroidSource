package com.bn.menu;
import static com.bn.gameView.Constant.SCREEN_HEIGHT;
import static com.bn.gameView.Constant.SCREEN_WIDTH;
import static com.bn.gameView.Constant.isMusicOn;
import static com.bn.gameView.Constant.isSoundOn;
import static com.bn.gameView.Constant.isVibrateOn;
import static com.bn.gameView.Constant.isCrash;
import static com.bn.gameView.Constant.isOvercome;
import static com.bn.gameView.Constant.isVideo;
import static com.bn.gameView.Constant.is_button_return;
import static com.bn.gameView.Constant.keyState;
import static com.bn.gameView.Constant.ratio_height;
import static com.bn.gameView.Constant.ratio_width;
import java.util.HashMap;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import com.bn.core.SQLiteUtil;
import com.bn.gameView.GLGameView;

public class Aircraft_Activity extends Activity
{
	GLGameView gameView;//����Ϸ����
	static Handler handler;//��Ϣ������
	SoundPool soundPool;//������
	Vibrator mVibrator;//����
	public MediaPlayer bgMusic[]=new MediaPlayer[2];//��Ϸ�������ֲ�����
	HashMap<Integer,Integer> soundMap;//����������е�����ID��Map
	SensorManager mySensorManager;//������������	
	private boolean isNoBack;//���ؼ�������Ҫ���ڻ�ӭ���沥�Ź�����,���η��ؼ�
	private int flag;//�жϵ�ǰ��Ļ�Ƿ��ܹ���ת�ı�־λ
	public float lr_domain=1;//������������ת����ֵ
	public float[] directionDotXY=new float[2];
	private SensorEventListener mySensorListener = new SensorEventListener(){
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}

		@Override
		public void onSensorChanged(SensorEvent event) 
		{
			if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			{	
				float[] value=event.values;
				if(-value[1]>lr_domain)
				{
					//��ת
					keyState=keyState|0x4;
					keyState=keyState&0x7;
					directionDotXY[0]=value[1];
				}
				else if(-value[1]<-lr_domain)
				{
					//��ת
					keyState=keyState|0x8; 
					keyState=keyState&0xB;
					directionDotXY[0]=value[1];
				}
				else
				{
					//������ݸ�λ
					keyState=keyState&0xB;
					keyState=keyState&0x7;
					directionDotXY[0]=0;
				}
			}
		}
    };
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);       
        flag =Settings.System.getInt(this.getContentResolver(), //�жϵ�ǰ�Ƿ��ܹ���ת��
        		Settings.System.ACCELEROMETER_ROTATION, 0);
        if(flag==0)//����ת��
        {
        	Settings.System.putInt(this.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION,1);
        }        
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);//������������    
        initScreen();//��ʼ����Ļ
        initHandler();//��Ϣ������
        initSound();//��ʼ��
        initDatebase();
        collisionShake();//��ʼ������
        goTo_StartVideo();
        
    }
    //��Ϣ����������
    public void initHandler()
    {
    	handler=new Handler()
    	{
    		@Override
			public void handleMessage(Message msg)//��д���� 
    		{       	
    			switch(msg.what)
    			{
	        		case 1:
	    					isNoBack=false;//���ؼ�����
		        			gameView=new GLGameView(Aircraft_Activity.this);
		        			setContentView(gameView);
		        			bgMusic[0].start();//������������
	        		break;
        		}}};}
    //��ʼ����Ļ�ֱ���
    public void initScreen()
    {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��֪ͨ��
    	getWindow().setFlags//ȫ����ʾ    
    	(	
    		WindowManager.LayoutParams.FLAG_FULLSCREEN,
    		WindowManager.LayoutParams.FLAG_FULLSCREEN
    	);
    	this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int tempHeight=(int) (SCREEN_HEIGHT=dm.heightPixels);
        int tempWidth=(int) (SCREEN_WIDTH=dm.widthPixels); 
        if(tempHeight<tempWidth) 
        {
        	SCREEN_HEIGHT=tempHeight;
        	SCREEN_WIDTH=tempWidth;
        }
        else
        {
        	SCREEN_HEIGHT=tempWidth;
        	SCREEN_WIDTH=tempHeight;
        }
        ratio_width=SCREEN_WIDTH/800;
        ratio_height=SCREEN_HEIGHT/480;
    }
    public void initDatebase()
    {    	
        String sql="create table if not exists plane(map char(2),grade char(4),time char(4),date char(10));";
        SQLiteUtil.createTable(sql);//����SQL���
    } 
    public void goTo_StartVideo(){ //��Ϸ��ʼ���Ȳ�����Ƶ
    	isNoBack=true;//���ؼ�������
    	setContentView(R.layout.start_video);
    	final MyVideoView myVideoView=(MyVideoView)findViewById(R.id.start_video_videoview);
    	myVideoView.setVideoURI(Uri.parse("android.resource://com.bn.menu/" + R.raw.logo));
    	myVideoView.start(); 
    	myVideoView.setOnCompletionListener(new OnCompletionListener(){
			@Override
			public void onCompletion(MediaPlayer mp){				
				if(getGLVersion()<2){//�������opengles����
					//�����Ի���,˵����֧�ָ���Ϸ
					showDialog(0);		
				}else if(Build.VERSION.SDK_INT<Build.VERSION_CODES.FROYO){
					showDialog(1);
				 }else
				handler.sendEmptyMessage(1);//�������˵�����
			}});
    	}
   
    public int getGLVersion()//��ȡOPENGLES��֧�ֵ���߰汾    
    { 
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        int majorVersion=info.reqGlEsVersion;
        majorVersion=majorVersion>>>16;
        return majorVersion;
    }  
    public void collisionShake()//�ֻ���
    {  
    		mVibrator=(Vibrator)getApplication().getSystemService
            (Service.VIBRATOR_SERVICE);	
    }   
    public void shake()//��
    { 
    	if(0==isVibrateOn)//������    
    	{	
    		mVibrator.vibrate( new long[]{0,30},-1);
    	}
    }    
    public void initSound()//����������Դ
    {
    	bgMusic[0]=MediaPlayer.create(this,R.raw.menubg_music); 
    	bgMusic[0].setLooping(true);//�Ƿ�ѭ��
    	bgMusic[0].setVolume(0.3f, 0.3f);//������С
    	bgMusic[1]=MediaPlayer.create(this,R.raw.gamebg_music);
    	bgMusic[1].setLooping(true);//�Ƿ�ѭ��
    	bgMusic[1].setVolume(0.5f, 0.5f);//������С    	
		soundPool=new SoundPool(4,AudioManager.STREAM_MUSIC,100);//����������
		soundMap=new HashMap<Integer,Integer>();//����map
		soundMap.put(0, soundPool.load(this, R.raw.explode,1));//�ɻ�ײɽ��������������
		soundMap.put(1, soundPool.load(this, R.raw.awp_fire,1));//̹�˺͸����ڱ����б�ը
		soundMap.put(2, soundPool.load(this, R.raw.r700_fire,1));//��ը
		soundMap.put(3, soundPool.load(this, R.raw.bullet,1));//�ɻ������ӵ�����
		soundMap.put(4, soundPool.load(this, R.raw.missile,1));//�����ӵ�����
		soundMap.put(5, soundPool.load(this, R.raw.m16_fire,1));//�����ӵ�����		
		soundMap.put(6, soundPool.load(this, R.raw.rpg7_fire,1));//�����ӵ�����
		soundMap.put(7, soundPool.load(this, R.raw.w1200_fire,1));//̹�˷����ӵ�����
		soundMap.put(8, soundPool.load(this, R.raw.ground,1));//̹�˷����ӵ�����
		soundMap.put(9, soundPool.load(this, R.raw.rotation,1));//
	}
    //���������ķ���
	public void playSound(int sound,int loop)
	{
		if(0!=isSoundOn)
		{
			return;
		}
		AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;   
	    soundPool.play
	    (
	    	soundMap.get(sound), //������Դid
    		volume, 				 //����������
    		volume, 				 //����������
    		1, 						 //���ȼ�				 
    		loop, 					 //ѭ������ -1������Զѭ��
    		0.5f					 //�ط��ٶ�0.5f��2.0f֮��
	    );
	}
	@Override
    public Dialog onCreateDialog(int id)
	{
    	Dialog dialog=null;
    	switch(id)
    	{
    	  case 0://������ͨ�Ի���Ĵ���
    		  String msg="���豸��֧�ֵ�opengles�汾����,��֧�ִ���Ϸ!!!";
    		  Builder b=new AlertDialog.Builder(this);  
    		  b.setIcon(R.drawable.icon);//����ͼ��
    		  b.setTitle("������˼...");//���ñ���
    		  b.setMessage(msg);//������Ϣ
    		  b.setPositiveButton(//Ϊ�Ի������ð�ť    		  
    				"�˳�" ,
    				new DialogInterface.OnClickListener()
    				{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							System.exit(0);
						}});
    		  dialog=b.create();
    	  break;
    	  case 1://������ͨ�Ի���Ĵ���
    		  String msgt="���豸��ǰAndroid�汾�ǵ���2.2,��֧�ִ���Ϸ!!!";
    		  Builder bb=new AlertDialog.Builder(this);  
    		  bb.setIcon(R.drawable.icon);//����ͼ��
    		  bb.setTitle("������˼...");//���ñ���
    		  bb.setMessage(msgt);//������Ϣ
    		  bb.setPositiveButton(//Ϊ�Ի������ð�ť    		  
    				"�˳�" ,
    				new DialogInterface.OnClickListener()
    				{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{ 						
							System.exit(0);
						}});
    		  dialog=bb.create();
    	  break;
    	}
    	return dialog;
    }
    @Override
	protected void onResume()
    {						
		super.onResume();		
		mySensorManager.registerListener
		(
			mySensorListener, 
			mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
			SensorManager.SENSOR_DELAY_GAME
		);
    }
	@Override
	protected void onPause()
	{								
		super.onPause();
		mySensorManager.unregisterListener(mySensorListener);	//ȡ��ע�������
	}	
	public void exitRelease()//�˳�ʱ��Ҫִ�еķ���
	{
		if(flag==0)//�ص���ת��
		{		
			Settings.System.putInt(this.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION,0);
		}
		System.exit(0);
	}   
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent e)//������Ļ����     
    { 	
    	if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN||keyCode==KeyEvent.KEYCODE_VOLUME_UP)//����������ֻ�ܿ���ý�������Ĵ�С
    	{
	      setVolumeControlStream(AudioManager.STREAM_MUSIC);
	      return super.onKeyDown(keyCode, e);
    	}
    	if(keyCode==4)
    	{
    		if(isNoBack)//���ؼ�����    	
    		{	
    			return true;
    		}
    		if(!gameView.isGameOn)
    		{
        		return gameView.onKeyBackEvent();
        	}
    		else //��Ϸ��ʼ��
    		{
    			if(!isCrash&&!isOvercome)
    			{
    				if(!isVideo)
    				{
    					is_button_return=!is_button_return;//���·��ذ�ť    	
    					if(bgMusic[1].isPlaying())
        				{
        					bgMusic[1].pause();
        				}
        				else if(!bgMusic[1].isPlaying()&&isMusicOn==0)
        				{
        					bgMusic[1].start();
        				}
    				}
    				else
    				{
    					gameView.isTrueButtonAction=true;
    					GLGameView.isVideoPlaying=!GLGameView.isVideoPlaying;
    					if(bgMusic[1].isPlaying())
        				{
        					bgMusic[1].pause();
        				}
        				else if(!bgMusic[1].isPlaying()&&isMusicOn==0)
        				{
        					bgMusic[1].start();
        				}
    				}
    			}
    			return true;
    		}
    	}	
    	return true;
    }}
