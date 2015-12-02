//
//  Earth.h
//  OpenGL2.0Demo
//
//  Created by 1 on 13-4-17.
//  Copyright (c) 2013年 百纳. All rights reserved.
//

#ifndef __OpenGL2_0Demo__Earth__
#define __OpenGL2_0Demo__Earth__

#include <iostream>
#include <OpenGLES/ES2/gl.h>
#include <OpenGLES/ES2/glext.h>

class Earth {
    GLuint mProgram;//自定义渲染管线程序id
    GLuint muMVPMatrixHandle;//总变换矩阵引用id
    GLuint muMMatrixHandle;//位置、旋转变换矩阵
    GLuint maCameraHandle; //摄像机位置属性引用id
    GLuint maPositionHandle; //顶点位置属性引用id
    GLuint maNormalHandle; //顶点法向量属性引用id
    GLuint maTexCoorHandle; //顶点纹理坐标属性引用id
    GLuint maSunLightLocationHandle;//光源位置属性引用id
    GLuint uDayTexHandle;//白天纹理属性引用id
    GLuint uNightTexHandle;//黑夜纹理属性引用id
    
    const GLvoid* mVertexBuffer;//顶点坐标数据缓冲
    const GLvoid* mTexCoorBuffer;//顶点纹理坐标数据缓冲
    
    int vCount;
public:
    Earth(float r);
    void initVertexData(float r);
    void initShader();
    void drawSelf(int texId,int texIdNight);
    float toRadians(float angle);
    void generateTexCoor(int bw,int bh,float* tex);
};


#endif /* defined(__OpenGL2_0Demo__Earth__) */
