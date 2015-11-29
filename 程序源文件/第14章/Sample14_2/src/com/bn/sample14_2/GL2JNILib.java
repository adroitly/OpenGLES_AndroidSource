package com.bn.sample14_2;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

public class GL2JNILib {
     static {
         System.loadLibrary("gl2jni");
     }
     public static native void init(GLSurfaceView gsv,int width, int height);
     public static native void step();
     public static native void setCamera(    		
    		float cx,	//�����λ��x
     		float cy,   //�����λ��y
     		float cz,   //�����λ��z
     		float tx,   //�����Ŀ���x
     		float ty,   //�����Ŀ���y
     		float tz,   //�����Ŀ���z
     		float upx,  //�����UP����X����
     		float upy,  //�����UP����Y����
     		float upz   //�����UP����Z����		
     		);
}
