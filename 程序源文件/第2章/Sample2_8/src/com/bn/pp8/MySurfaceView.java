package com.bn.pp8;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * 2D SurfaceView
 * 
 */
public class MySurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {
	Sample2_8_Activity activity;// activity������
	Paint paint;// ��������
	DrawThread drawThread;// �����߳�����
	Bitmap bgBmp;//����ͼƬ
	Bitmap bulletBmp;// �ӵ�λͼ
	Bitmap[] explodeBmps;//��ըλͼ����
	Bullet bullet;//�ӵ���������
	public MySurfaceView(Sample2_8_Activity activity) {//������
		super(activity);
		this.activity = activity;
		// ��ý��㲢����Ϊ�ɴ���
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);// ע��ص��ӿ�
	}

	@Override
	protected void onDraw(Canvas canvas) {//���ƽ���ķ���
		super.onDraw(canvas);
		canvas.drawBitmap(bgBmp, 0, 0, paint);//���Ʊ���
		bullet.drawSelf(canvas, paint);//�����ӵ�
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {//����仯ʱ���õķ���
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		paint = new Paint();// ��������
		paint.setAntiAlias(true);// �򿪿����
		//����ͼƬ��Դ
		bulletBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.bullet);
		bgBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.bg);
		explodeBmps=new Bitmap[]{
				BitmapFactory.decodeResource(this.getResources(), R.drawable.explode0),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.explode1),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.explode2),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.explode3),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.explode4),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.explode5),
		};
		bullet = new Bullet(this, bulletBmp,explodeBmps,0,290,1.3f,-5.9f);//�����ӵ�����
		drawThread = new DrawThread(this);//���������߳�
		drawThread.start();//���������߳�
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {//��������ʱ���õķ���
		drawThread.setFlag(false);//ֹͣ�����߳�
	}
}
