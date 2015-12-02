//
//  MatrixState.cpp
//  OpenGL2.0Demo
//
//  Created by 1 on 13-4-14.
//  Copyright (c) 2013年 百纳. All rights reserved.
//

#include "MatrixState.hpp"


float MatrixState::currMatrix[16];
float MatrixState::mProjMatrix[16];
float MatrixState::mVMatrix[16];
float MatrixState::mMVPMatrix[16];
float MatrixState::mStack[10][16];
float MatrixState::lightLocationSun[3];//太阳定位光光源位置

GLfloat* MatrixState::cameraFB=NULL;
GLfloat* MatrixState::lightPositionFBSun=NULL;
int MatrixState::stackTop=-1;