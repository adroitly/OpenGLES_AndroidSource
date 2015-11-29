package com.bn.Sample13_2;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
public class MyActivity extends Activity {
	SensorManager mySensorManager;	//SensorManager��������	
	Sensor myMagnetic; 	//����������
	TextView tvX;	//TextView��������	
	TextView tvY;	//TextView��������	
	TextView tvZ;	//TextView��������
	TextView info;	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tvX = (TextView)findViewById(R.id.tvX);	//������ʾx�᷽��ų�
        tvY = (TextView)findViewById(R.id.tvY);	//������ʾy�᷽��ų�	
        tvZ = (TextView)findViewById(R.id.tvZ); //������ʾz�᷽��ų�
        info= (TextView)findViewById(R.id.info);//������ʾ�ֻ��дų��������������Ϣ
        //���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        //������������Ϊ�ų�������
        myMagnetic=mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        
        //����һ��StringBuffer        
        StringBuffer strb=new StringBuffer();
        strb.append("\n����: ");
        strb.append(myMagnetic.getName());
        strb.append("\n�ĵ���(mA): ");
        strb.append(myMagnetic.getPower());
        strb.append("\n���ͱ�� : ");
        strb.append(myMagnetic.getType());
        strb.append("\n������: ");
        strb.append(myMagnetic.getVendor());
        strb.append("\n�汾: ");
        strb.append(myMagnetic.getVersion());
        strb.append("\n��������Χ: ");
        strb.append(myMagnetic.getMaximumRange());
        
        info.setText(strb.toString());	//����Ϣ�ַ���������Ϊinfo��TextView
    }
	private SensorEventListener mySensorListener = 
		new SensorEventListener(){//����ʵ����SensorEventListener�ӿڵĴ�����������
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy){}
		@Override
		public void onSensorChanged(SensorEvent event){
			float []values=event.values;//��ȡ�����᷽���ϵĴų�ֵ
			tvX.setText("x�᷽���ϵĴų�ǿ��Ϊ�� "+values[0]);		
			tvY.setText("y�᷽���ϵĴų�ǿ��Ϊ�� "+values[1]);		
			tvZ.setText("z�᷽���ϵĴų�ǿ��Ϊ�� "+values[2]);		
		}
	};
    @Override
	protected void onResume(){ //��дonResume����
		super.onResume();
		mySensorManager.registerListener(
				mySensorListener, 		//��Ӽ���
				myMagnetic, 		//����������
				SensorManager.SENSOR_DELAY_NORMAL	//�������¼����ݵ�Ƶ��
		);
	}	
	@Override
	protected void onPause(){//��дonPause����	
		super.onPause();
		mySensorManager.unregisterListener(mySensorListener);//ȡ��ע�������
	}
	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)
	{
		switch(keyCode)
	    	{
		case 4:
			System.exit(0);
			break;
	    	}
		return true;
	}
	
}