//
//  Earth.cpp
//  OpenGL2.0Demo
//
//  Created by 1 on 13-4-17.
//  Copyright (c) 2013年 百纳. All rights reserved.
//
#include <math.h>
#include "Earth.h"
#include "ShaderUtil.cpp"
#include "MatrixState.hpp"

#define STRINGIFY(A) #A
#include "Simple.frag"
#include "Simple.vert"

Earth::Earth(float r)
{
    initVertexData(r);
    initShader();
}
float Earth::toRadians(float angle)
{
    return angle*3.1415926f/180;
}
void Earth::initVertexData(float r)
{
    //顶点坐标数据的初始化================begin============================
    const float UNIT_SIZE=0.5f;
    const float angleSpan=10;//将球进行单位切分的角度
    float alVertixs[11664];//36*18*6*3
    static float *alVertix;
    alVertix = alVertixs;
    for(float vAngle=90;vAngle>-90;vAngle=vAngle-angleSpan){//垂直方向angleSpan度一份
        for(float hAngle=360;hAngle>0;hAngle=hAngle-angleSpan){//水平方向angleSpan度一份
            //纵向横向各到一个角度后计算对应的此点在球面上的坐标
            double xozLength=r*UNIT_SIZE*cos(toRadians(vAngle));
            float x1=(float)(xozLength*cos(toRadians(hAngle)));
            float z1=(float)(xozLength*sin(toRadians(hAngle)));
            float y1=(float)(r*UNIT_SIZE*sin(toRadians(vAngle)));
            
            xozLength=r*UNIT_SIZE*cos(toRadians(vAngle-angleSpan));
            float x2=(float)(xozLength*cos(toRadians(hAngle)));
            float z2=(float)(xozLength*sin(toRadians(hAngle)));
            float y2=(float)(r*UNIT_SIZE*sin(toRadians(vAngle-angleSpan)));
            
            xozLength=r*UNIT_SIZE*cos(toRadians(vAngle-angleSpan));
            float x3=(float)(xozLength*cos(toRadians(hAngle-angleSpan)));
            float z3=(float)(xozLength*sin(toRadians(hAngle-angleSpan)));
            float y3=(float)(r*UNIT_SIZE*sin(toRadians(vAngle-angleSpan)));
            
            xozLength=r*UNIT_SIZE*cos(toRadians(vAngle));
            float x4=(float)(xozLength*cos(toRadians(hAngle-angleSpan)));
            float z4=(float)(xozLength*sin(toRadians(hAngle-angleSpan)));
            float y4=(float)(r*UNIT_SIZE*sin(toRadians(vAngle)));
            
            //构建第一三角形
            *alVertix = x1;
            alVertix++;
            *alVertix = y1;
            alVertix++;
            *alVertix = z1;
            alVertix++;
            *alVertix = x2;
            alVertix++;
            *alVertix = y2;
            alVertix++;
            *alVertix = z2;
            alVertix++;
            *alVertix = x4;
            alVertix++;
            *alVertix = y4;
            alVertix++;
            *alVertix = z4;
            alVertix++;
            //构建第二三角形
            *alVertix = x4;
            alVertix++;
            *alVertix = y4;
            alVertix++;
            *alVertix = z4;
            alVertix++;
            *alVertix = x2;
            alVertix++;
            *alVertix = y2;
            alVertix++;
            *alVertix = z2;
            alVertix++;
            *alVertix = x3;
            alVertix++;
            *alVertix = y3;
            alVertix++;
            *alVertix = z3;
            alVertix++;
        }}
    vCount=11664/3;//顶点的数量为坐标值数量的1/3，因为一个顶点有3个坐标
    //将alVertix中的坐标值转存到一个float数组中
    int count = 11664;
    static float ver[11664];
    for(int i=0;i<count;i++)
    {
        ver[i] = alVertixs[i];
    }
    mVertexBuffer = ver;

    static float tex[7776];
    generateTexCoor(36, 18, tex);
    mTexCoorBuffer = tex;
}

//自动切分纹理产生纹理数组的方法
void Earth::generateTexCoor(int bw,int bh,float* tex){
    //float result[bw*bh*6*2];
    float sizew=1.0f/bw;//列数
    float sizeh=1.0f/bh;//行数
    int c=0;
    for(int i=0;i<bh;i++){
        for(int j=0;j<bw;j++){
            //每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
            float s=j*sizew;
            float t=i*sizeh;
            tex[c++]=s;
            tex[c++]=t;
            tex[c++]=s;
            tex[c++]=t+sizeh;
            tex[c++]=s+sizew;
            tex[c++]=t;
            tex[c++]=s+sizew;
            tex[c++]=t;
            tex[c++]=s;
            tex[c++]=t+sizeh;
            tex[c++]=s+sizew;
            tex[c++]=t+sizeh;
    }}
}

void Earth::initShader()
{
    mProgram = ShaderUtil::createProgram(SimpleVertexShader, SimpleFragmentShader);
    
    //获取程序中顶点位置属性引用id
    maPositionHandle = glGetAttribLocation(mProgram, "aPosition");
    //获取程序中顶点纹理属性引用id
    maTexCoorHandle=glGetAttribLocation(mProgram, "aTexCoor");
    //获取程序中顶点法向量属性引用id
    maNormalHandle= glGetAttribLocation(mProgram, "aNormal");
    //获取程序中总变换矩阵引用id
    muMVPMatrixHandle = glGetUniformLocation(mProgram, "uMVPMatrix");
    //获取程序中摄像机位置引用id
    maCameraHandle=glGetUniformLocation(mProgram, "uCamera");
    //获取程序中光源位置引用id
    maSunLightLocationHandle=glGetUniformLocation(mProgram, "uLightLocationSun");
    //获取白天、黑夜两个纹理引用
    uDayTexHandle=glGetUniformLocation(mProgram, "sTextureDay");
    uNightTexHandle=glGetUniformLocation(mProgram, "sTextureNight");
    //获取位置、旋转变换矩阵引用id
    muMMatrixHandle = glGetUniformLocation(mProgram, "uMMatrix");
}

void Earth::drawSelf(GLint texId,GLint texIdNight)
{
    glUseProgram(mProgram);

    glUniformMatrix4fv(muMVPMatrixHandle, 1, 0, MatrixState::getFinalMatrix());
    //将位置、旋转变换矩阵传入着色器程序
    glUniformMatrix4fv(muMMatrixHandle, 1, 0, MatrixState::getMMatrix());
    //将摄像机位置传入着色器程序
    glUniform3fv(maCameraHandle, 1, MatrixState::cameraFB);
    //将光源位置传入着色器程序
    glUniform3fv(maSunLightLocationHandle, 1, MatrixState::lightPositionFBSun);

    glVertexAttribPointer(maPositionHandle,3, GL_FLOAT, GL_FALSE,3*4, mVertexBuffer);
    glVertexAttribPointer(maTexCoorHandle,2,GL_FLOAT,GL_FALSE,2*4,mTexCoorBuffer);
    glVertexAttribPointer(maNormalHandle,4,GL_FLOAT,GL_FALSE,3*4,mVertexBuffer);

    glEnableVertexAttribArray(maPositionHandle);
    glEnableVertexAttribArray(maTexCoorHandle);
    glEnableVertexAttribArray(maNormalHandle);
    
    //绑定纹理
    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_2D, texId);
    glActiveTexture(GL_TEXTURE1);
    glBindTexture(GL_TEXTURE_2D, texIdNight);
    glUniform1i(uDayTexHandle, 0);
    glUniform1i(uNightTexHandle, 1);
    
    glDrawArrays(GL_TRIANGLES, 0, vCount);
    
    glDisableVertexAttribArray(maPositionHandle);
    glDisableVertexAttribArray(maTexCoorHandle);
    glDisableVertexAttribArray(maNormalHandle);
}