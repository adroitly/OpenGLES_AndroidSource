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
    		float cx,	//摄像机位置x
     		float cy,   //摄像机位置y
     		float cz,   //摄像机位置z
     		float tx,   //摄像机目标点x
     		float ty,   //摄像机目标点y
     		float tz,   //摄像机目标点z
     		float upx,  //摄像机UP向量X分量
     		float upy,  //摄像机UP向量Y分量
     		float upz   //摄像机UP向量Z分量		
     		);
}
