package com.bn.sample14_2;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

public class Sample14_2Activity extends Activity {

    GL2JNIView mView;
	//��Ļ��Ӧ�Ŀ�Ⱥ͸߶�
	static float WIDTH;
	static float HEIGHT;
	
    @Override protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //����Ϊȫ��
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//���ϵͳ�Ŀ���Լ��߶�
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        if(dm.widthPixels>dm.heightPixels)
        {
        	WIDTH=dm.widthPixels;
        	HEIGHT=dm.heightPixels;
        }
        else
        {
        	WIDTH=dm.heightPixels;
        	HEIGHT=dm.widthPixels;
        }
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mView = new GL2JNIView(getApplication());
        mView.requestFocus();//��ȡ����
        mView.setFocusableInTouchMode(true);//����Ϊ�ɴ���  
        setContentView(mView);
    }

    @Override protected void onPause() {
        super.onPause();
        mView.onPause();
    }

    @Override protected void onResume() {
        super.onResume();
        mView.onResume();
    }
    
    
}
