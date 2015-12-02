//
//  Earth.h
//  OpenGL2.0Demo
//
//  Created by 1 on 13-4-17.
//  Copyright (c) 2013年 百纳. All rights reserved.
//

#ifndef __OpenGL2_0Demo__Celestial__
#define __OpenGL2_0Demo__Celestial__

#include <iostream>
#include <OpenGLES/ES2/gl.h>
#include <OpenGLES/ES2/glext.h>

class Celestial {
    const float UNIT_SIZE=10.0f;//天球半径
	const GLvoid* mVertexBuffer;//顶点坐标数据缓冲
    const int vCount=2000;//星星数量
    float yAngle;//天球绕Y轴旋转的角度
    float scale;//星星尺寸
    GLuint mProgram;//自定义渲染管线程序id
    GLuint muMVPMatrixHandle;//总变换矩阵引用id
    GLuint maPositionHandle; //顶点位置属性引用id
    GLuint uPointSizeHandle;//顶点尺寸参数引用
public:
    Celestial(float scale,float yAngle);
    void initVertexData();
    void initShader();
    void drawSelf();
};


#endif /* defined(__OpenGL2_0Demo__Earth__) */
