package com.bn.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;
import static com.bn.gameView.Constant.*;
/**
 * 
 * @author lishengjie
 *	��дVideoView,��Ҫ�ǽ���Ƶ����ȫ��
 */
public class MyVideoView extends VideoView 
{
	public MyVideoView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	@Override 
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
        int width = getDefaultSize((int) SCREEN_WIDTH, widthMeasureSpec);
        int height = getDefaultSize((int) SCREEN_HEIGHT, heightMeasureSpec);
        setMeasuredDimension(width,height);
    }
}
