package com.bn.sample14_1;

import android.app.Activity;
import android.os.Bundle;

public class Sample14_1Activity extends Activity {

    GL2JNIView mView;

    @Override protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mView = new GL2JNIView(getApplication());
		mView.requestFocus();					//获取焦点
		mView.setFocusableInTouchMode(true); 	//设置为可触控
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
