package com.bn.Sample13_4;
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
	Sensor myLight; 	//����������
	TextView light;	//TextView��������	
	TextView info;	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
        light = (TextView)findViewById(R.id.light);	//������ʾ��ǿ�ȵ�
        info= (TextView)findViewById(R.id.info);//������ʾ�ֻ��й⴫�����������Ϣ
        //���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        //������������Ϊ�⴫����
        myLight=mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        //����һ��StringBuffer
        StringBuffer strb=new StringBuffer();
        strb.append("\n����: ");
        strb.append(myLight.getName());
        strb.append("\n�ĵ���(mA) : ");
        strb.append(myLight.getPower());
        strb.append("\n���ͱ��  : ");
        strb.append(myLight.getType());
        strb.append("\n������: ");
        strb.append(myLight.getVendor());
        strb.append("\n�汾: ");
        strb.append(myLight.getVersion());
        strb.append("\n��������Χ: ");
        strb.append(myLight.getMaximumRange());
        info.setText(strb.toString());	//����Ϣ�ַ���������Ϊinfo��TextView
    }
    @Override
	protected void onResume(){ //��дonResume����
		super.onResume();
		mySensorManager.registerListener(
				mySensorListener, 		//��Ӽ���
				myLight, 		//����������
				SensorManager.SENSOR_DELAY_NORMAL	//�������¼����ݵ�Ƶ��
		);
	}	
	@Override
	protected void onPause(){//��дonPause����	
		super.onPause();
		mySensorManager.unregisterListener(mySensorListener);//ȡ��ע�������
	}
	private SensorEventListener mySensorListener = 
		new SensorEventListener(){//����ʵ����SensorEventListener�ӿڵĴ�����������
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy){}
		@Override
		public void onSensorChanged(SensorEvent event){
			float []values=event.values;
			light.setText("���ǿ��Ϊ��"+values[0]);			
		}
	};
	
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