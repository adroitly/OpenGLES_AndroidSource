package com.bn.Sample13_3;
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
	Sensor myGyroscope; 	//����������
	TextView tvX;	//TextView��������	
	TextView tvY;	//TextView��������	
	TextView tvZ;	//TextView��������
	TextView info;	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tvX = (TextView)findViewById(R.id.tvX);	//������ʾx����ٶ�
        tvY = (TextView)findViewById(R.id.tvY);	//������ʾy����ٶ�	
        tvZ = (TextView)findViewById(R.id.tvZ); //������ʾz����ٶ�
        info= (TextView)findViewById(R.id.info);//������ʾ�ֻ��н��ٶȴ������������Ϣ
        //���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        //������������
        myGyroscope=mySensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        
        //����һ��StringBuffer
        StringBuffer strb=new StringBuffer();
        strb.append("\n����: ");
        strb.append(myGyroscope.getName());
        strb.append("\n�ĵ���(mA): ");
        strb.append(myGyroscope.getPower());
        strb.append("\n���ͱ�� : ");
        strb.append(myGyroscope.getType());
        strb.append("\n������: ");
        strb.append(myGyroscope.getVendor());
        strb.append("\n�汾: ");
        strb.append(myGyroscope.getVersion());
        strb.append("\n��������Χ: ");
        strb.append(myGyroscope.getMaximumRange());
        
        info.setText(strb.toString());	//����Ϣ�ַ���������Ϊinfo��TextView
    }
    @Override
	protected void onResume(){ //��дonResume����
		super.onResume();
		mySensorManager.registerListener(
				mySensorListener, 		//��Ӽ���
				myGyroscope, 		//����������
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
			float []values=event.values;//��ȡ�����᷽���ϵĽ��ٶ�ֵ
			tvX.setText("x��Ľ��ٶ�Ϊ��"+values[0]);		
			tvY.setText("y��Ľ��ٶ�Ϊ��"+values[1]);		
			tvZ.setText("z��Ľ��ٶ�Ϊ��"+values[2]);		
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