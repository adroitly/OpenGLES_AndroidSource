package com.bn.pp8;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Explosion {
	MySurfaceView gameView;
	private Bitmap[] bitmaps;// λͼ
	float x;// x����λ��
	float y;// y����λ��
	private int anmiIndex = 0;// ��ը����֡����

	public Explosion(MySurfaceView gameView, Bitmap[] bitmaps, float x, float y) {
		this.gameView = gameView;
		this.bitmaps = bitmaps;
		this.x = x;
		this.y = y;
	}

	// ���Ʊ����ķ���
	public void drawSelf(Canvas canvas, Paint paint) {
		if (anmiIndex >= bitmaps.length - 1) {// �������������ϣ����ٻ��Ʊ�ըЧ��
			return;
		}
		canvas.drawBitmap(bitmaps[anmiIndex], x, y, paint);// ����������ĳһ��ͼ
		anmiIndex++;// ��ǰ�±��1
	}
}
