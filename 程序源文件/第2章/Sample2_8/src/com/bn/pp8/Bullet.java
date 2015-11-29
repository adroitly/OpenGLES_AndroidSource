package com.bn.pp8;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {
	MySurfaceView gameView;
	private Bitmap bitmap;// λͼ
	private Bitmap[] bitmaps;// ��ը����ͼ��
	float x;// x����λ��
	float y;// y����λ��
	float vx;// x�����ٶ�
	float vy;// y�����ٶ�
	private float t = 0;// ʱ��
	private float timeSpan = 0.5f;// ʱ����
	int size;// �ӵ��ߴ�
	boolean explodeFlag = false;// �Ƿ�����ӵ��ı��
	Explosion mExplosion;// ��ը��������

	// ������
	public Bullet(MySurfaceView gameView, Bitmap bitmap, Bitmap[] bitmaps,
			float x, float y, float vx, float vy) {
		this.gameView = gameView;// ��Ա������ֵ
		this.bitmap = bitmap;
		this.bitmaps = bitmaps;
		this.x = x;// ��Ա������ֵ
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		size = bitmap.getHeight();// ���ͼƬ�ĸ߶�
	}

	// �����ӵ��ķ���
	public void drawSelf(Canvas canvas, Paint paint) {
		if (explodeFlag && mExplosion != null) {// ����Ѿ���ը�����Ʊ�ը����
			mExplosion.drawSelf(canvas, paint);
		} else {
			go();// �ӵ�ǰ��
			canvas.drawBitmap(bitmap, x, y, paint);// �����ӵ�
		}
	}

	// �ӵ�ǰ���ķ���
	public void go() {
		x += vx * t;// ˮƽ��������ֱ���˶�
		y += vy * t + 0.5f * Constant.G * t * t;// ��ֱ�����ȼ���ֱ���˶�
		if (x >= Constant.EXPLOSION_X || y >= Constant.SCREEN_HEIGHT) {// �ӵ����ض�λ�ñ�ը
			mExplosion = new Explosion(gameView, bitmaps, x, y);// ������ը����
			explodeFlag = true;// ���ٻ����ӵ�
			return;
		}
		t += timeSpan;// ʱ����
	}
}
