package com.bn.Sample13_5;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class MyActivity extends Activity {
	SensorManager mySensorManager; // SensorManager��������
	Sensor myTemperature; // ����������
	TextView temperature; // TextView��������
	TextView info;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		temperature = (TextView) findViewById(R.id.temperature); // ������ʾ�¶�ֵ
		info = (TextView) findViewById(R.id.info);// ������ʾ�ֻ����¶ȴ������������Ϣ
		// ���SensorManager����
		mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		// ������������Ϊ�¶ȴ�����
		myTemperature = mySensorManager
				.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
		// ����һ��StringBuffer
		StringBuffer strb = new StringBuffer();
		strb.append("\n����: ");
		strb.append(myTemperature.getName());
		strb.append("\n�ĵ���(mA): ");
		strb.append(myTemperature.getPower());
		strb.append("\n���ͱ�� : ");
		strb.append(myTemperature.getType());
		strb.append("\n������: ");
		strb.append(myTemperature.getVendor());
		strb.append("\n�汾: ");
		strb.append(myTemperature.getVersion());
		strb.append("\n��������Χ: ");
		strb.append(myTemperature.getMaximumRange());
		info.setText(strb.toString()); // ����Ϣ�ַ���������Ϊinfo��TextView
	}

	@Override
	protected void onResume() { // ��дonResume����
		super.onResume();
		mySensorManager.registerListener(mySensorListener, // ��Ӽ���
				myTemperature, // ����������
				SensorManager.SENSOR_DELAY_NORMAL // �������¼����ݵ�Ƶ��
				);
	}

	@Override
	protected void onPause() {// ��дonPause����
		super.onPause();
		mySensorManager.unregisterListener(mySensorListener);// ȡ��ע�������
	}

	private SensorEventListener mySensorListener = new SensorEventListener() {// ����ʵ����SensorEventListener�ӿڵĴ�����������
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			float[] values = event.values;
			temperature.setText("�¶�Ϊ��" + values[0]);
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent e) {
		switch (keyCode) {
		case 4:
			System.exit(0);
			break;
		}
		return true;
	}

}