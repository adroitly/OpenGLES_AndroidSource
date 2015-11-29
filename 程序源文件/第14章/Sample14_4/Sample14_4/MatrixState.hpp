//
//  MatrixState.hpp
//  OpenGL2.0Demo
//
//  Created by 1 on 13-4-13.
//  Copyright (c) 2013年 百纳. All rights reserved.
//

#ifndef OpenGL2_0Demo_MatrixState_hpp
#define OpenGL2_0Demo_MatrixState_hpp

#include <OpenGLES/ES2/gl.h>
#include <OpenGLES/ES2/glext.h>
#include <cmath>
#include "Matrix.hpp"

class MatrixState
{
private:
    static float currMatrix[16];
	static float mProjMatrix[16];
    static float mVMatrix[16];
    static float mMVPMatrix[16];
    static float lightLocationSun[3];//太阳定位光光源位置
public:
    static GLfloat* cameraFB;
    static GLfloat* lightPositionFBSun;
    //保护变换矩阵的栈
    static float mStack[10][16];
    static int stackTop;
    
    static void setInitStack()//获取不变换初始矩阵
    {
        Matrix::setIdentityM(currMatrix,0);
    }
    
    static void pushMatrix()//保护变换矩阵
    {
    	stackTop++;
    	for(int i=0;i<16;i++)
    	{
    		mStack[stackTop][i]=currMatrix[i];
    	}
    }
    
    static void popMatrix()//恢复变换矩阵
    {
    	for(int i=0;i<16;i++)
    	{
    		currMatrix[i]=mStack[stackTop][i];
    	}
    	stackTop--;
    }
    
    static void translate(float x,float y,float z)//设置沿xyz轴移动
    {
        Matrix::translateM(currMatrix, 0, x, y, z);
    }
    
    static void rotate(float angle,float x,float y,float z)//设置绕xyz轴移动
    {
    	Matrix::rotateM(currMatrix,0,angle,x,y,z);
    }
    
    static void scale(float x,float y,float z)
    {
    	Matrix::scaleM(currMatrix,0, x, y, z);
    }
    
    static void setCamera
    (
     float cx,	//摄像机位置x
     float cy,   //摄像机位置y
     float cz,   //摄像机位置z
     float tx,   //摄像机目标点x
     float ty,   //摄像机目标点y
     float tz,   //摄像机目标点z
     float upx,  //摄像机UP向量X分量
     float upy,  //摄像机UP向量Y分量
     float upz   //摄像机UP向量Z分量
     )
    {
        Matrix::setLookAtM
        (
         mVMatrix,
         0,
         cx,
         cy,
         cz,
         
         tx,
         ty,
         tz,
         
         upx,
         upy,
         upz
         );
        
        static GLfloat cameraLocation[3];//摄像机位置
    	cameraLocation[0]=cx;
    	cameraLocation[1]=cy;
    	cameraLocation[2]=cz;
        
        cameraFB = cameraLocation;
    }
    
    //设置透视投影参数
    static void setProjectFrustum
    (
     float left,		//near面的left
     float right,    //near面的right
     float bottom,   //near面的bottom
     float top,      //near面的top
     float near,		//near面距离
     float far       //far面距离
     )
    {
        Matrix::frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }
    
    //获取具体物体的总变换矩阵
    static float* getFinalMatrix()
    {
        Matrix::multiplyMM(mMVPMatrix, 0, mVMatrix, 0, currMatrix, 0);
        Matrix::multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        
        return mMVPMatrix;
    }
    
    //获取具体物体的变换矩阵
    static float* getMMatrix()
    {
        return &currMatrix[0];
    }
    
    //获取投影矩阵
    static float* getProjMatrix()
    {
		return mProjMatrix;
    }
    
    //获取摄像机朝向的矩阵
    static float* getCaMatrix()
    {
		return mVMatrix;
        
    }
    
    //设置太阳光源位置的方法
    static void setLightLocationSun(float x,float y,float z)
    {
    	lightLocationSun[0]=x;
    	lightLocationSun[1]=y;
    	lightLocationSun[2]=z;
        
        lightPositionFBSun = lightLocationSun;
    }
};


#endif
