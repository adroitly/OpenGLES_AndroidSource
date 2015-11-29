package com.bn.pp8;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
	private boolean flag = true;//�̹߳�����־λ
	private int sleepSpan = 100;//�߳�����ʱ��
	MySurfaceView gameView;//����������
	SurfaceHolder surfaceHolder;//surfaceHolder����

	public DrawThread(MySurfaceView gameView) {//������
		this.gameView = gameView;
		this.surfaceHolder = gameView.getHolder();
	}

	public void run() {
		Canvas c;//��������
		while (this.flag) {
			c = null;
			try {
				// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
				c = this.surfaceHolder.lockCanvas(null);
				synchronized (this.surfaceHolder) {
					gameView.onDraw(c);// ����
				}
			} finally {
				if (c != null) {// ���ͷ���
					this.surfaceHolder.unlockCanvasAndPost(c);
				}
			}
			try {
				Thread.sleep(sleepSpan);// ˯��ָ��������
			} catch (Exception e) {
				e.printStackTrace();// ��ӡ��ջ��Ϣ
			}
		}
	}

	public void setFlag(boolean flag) {//���ñ�־λ�ķ���
		this.flag = flag;
	}
}
