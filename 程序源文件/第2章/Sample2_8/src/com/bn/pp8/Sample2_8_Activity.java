package com.bn.pp8;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
public class Sample2_8_Activity extends Activity {
	MySurfaceView gameView;//��Ϸ����
    @Override
    public void onCreate(Bundle savedInstanceState) {//��дonCreate����
        super.onCreate(savedInstanceState);
        //ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//����Ϊ����
        gameView = new MySurfaceView(this);//������Ϸ�������
        this.setContentView(gameView);//���ý�������Ϊ��������
    }
}