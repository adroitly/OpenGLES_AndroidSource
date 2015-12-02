//
//  Earth.cpp
//  OpenGL2.0Demo
//
//  Created by 1 on 13-4-17.
//  Copyright (c) 2013年 百纳. All rights reserved.
//
#include <iostream>
#include <math.h>
#include "Celestial.h"
#include "ShaderUtil.cpp"
#include "MatrixState.hpp"


#define STRINGIFY(A) #A
#include "Simplec.frag"
#include "Simplec.vert"

using namespace std;

Celestial::Celestial(float scale,float yAngle)
{
    this->yAngle = yAngle;
    this->scale = scale;
    initVertexData();
    initShader();
}
void Celestial::initVertexData()
{
    
    //顶点坐标数据的初始化
    static float vertices[2000*3];
    for(int i=0;i<2000;i++){
        //随机产生每个星星的xyz坐标
        float angleTempJD=3.1415926*2*rand();
        float angleTempWD=3.1415926*(rand()-0.5f);
        vertices[i*3]=(float)(UNIT_SIZE*cos(angleTempWD)*sin(angleTempJD));
        vertices[i*3+1]=(float)(UNIT_SIZE*sin(angleTempWD));
        vertices[i*3+2]=(float)(UNIT_SIZE*cos(angleTempWD)*cos(angleTempJD));
    }
    static float ver[2000*3];
    for(int i=0;i<2000;i++)
    {
        ver[i] = vertices[i];
    }
    mVertexBuffer = ver;
}


void Celestial::initShader()
{
    mProgram = ShaderUtil::createProgram(SimpleVertexShaderC, SimpleFragmentShaderC);
    
    //获取程序中顶点位置属性引用id
    maPositionHandle = glGetAttribLocation(mProgram, "aPosition");
    //获取程序中总变换矩阵引用id
    muMVPMatrixHandle = glGetUniformLocation(mProgram, "uMVPMatrix");
    //获取顶点尺寸参数引用
    uPointSizeHandle = glGetUniformLocation(mProgram, "uPointSize");
}

void Celestial::drawSelf()
{
    glUseProgram(mProgram);
    
    //将最终变换矩阵传入着色器程序
    glUniformMatrix4fv(muMVPMatrixHandle, 1, 0, MatrixState::getFinalMatrix());
    glUniform1f(uPointSizeHandle, scale);  //将顶点尺寸传入着色器程序
    glVertexAttribPointer( //为画笔指定顶点位置数据
                                 maPositionHandle,
                                 3,
                                 GL_FLOAT,
                                 false,
                                 3*4,
                                 mVertexBuffer
                                 );
    //允许顶点位置数据数组
    glEnableVertexAttribArray(maPositionHandle);
    glDrawArrays(GL_POINTS, 0, vCount); //绘制星星点
    glDisableVertexAttribArray(maPositionHandle);
}