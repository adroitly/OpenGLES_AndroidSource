#ifndef OpenGL2_0Demo_MatrixState_hpp
#define OpenGL2_0Demo_MatrixState_hpp

#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>
#include <math.h>
#include "Matrix.h"

class MatrixState
{
private:
    static float currMatrix[16];
	static float mProjMatrix[16];
    static float mVMatrix[16];
    static float mMVPMatrix[16];
    static float lightLocationSun[3];
public:
    static GLfloat* cameraFB;
    static GLfloat* lightPositionFBSun;
    static float mStack[10][16];
    static int stackTop;
    
    static void setInitStack()
    {
        Matrix::setIdentityM(currMatrix,0);
    }
    
    static void pushMatrix()
    {
    	stackTop++;
    	for(int i=0;i<16;i++)
    	{
    		mStack[stackTop][i]=currMatrix[i];
    	}
    }
    
    static void popMatrix()
    {
    	for(int i=0;i<16;i++)
    	{
    		currMatrix[i]=mStack[stackTop][i];
    	}
    	stackTop--;
    }
    
    static void translate(float x,float y,float z)
    {
        Matrix::translateM(currMatrix, 0, x, y, z);
    }
    
    static void rotate(float angle,float x,float y,float z)
    {
    	Matrix::rotateM(currMatrix,0,angle,x,y,z);
    }
    
    static void scale(float x,float y,float z)
    {
    	Matrix::scaleM(currMatrix,0, x, y, z);
    }
    
    static void setCamera
    (
     float cx,
     float cy,
     float cz,
     float tx,
     float ty,
     float tz,
     float upx,
     float upy,
     float upz
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
        
        static GLfloat cameraLocation[3];
        cameraLocation[0]=cx;
    	cameraLocation[1]=cy;
    	cameraLocation[2]=cz;
        
        cameraFB = cameraLocation;
    }
    
    static void setProjectFrustum
    (
     float left,
     float right,
     float bottom,
     float top,
     float near,
     float far
     )
    {
        Matrix::frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }
    
    static float* getFinalMatrix()
    {
        Matrix::multiplyMM(mMVPMatrix, 0, mVMatrix, 0, currMatrix, 0);
        Matrix::multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        
        return mMVPMatrix;
    }
    

    static float* getMMatrix()
    {
        return &currMatrix[0];
    }
    

    static float* getProjMatrix()
    {
		return mProjMatrix;
    }

    static float* getCaMatrix()
    {
		return mVMatrix;
    }

    static void setLightLocationSun(float x,float y,float z)
    {
    	lightLocationSun[0]=x;
    	lightLocationSun[1]=y;
    	lightLocationSun[2]=z;
        
        lightPositionFBSun = lightLocationSun;
    }
};


#endif
