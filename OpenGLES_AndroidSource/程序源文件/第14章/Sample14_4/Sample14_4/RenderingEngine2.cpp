//
//  RenderingEngine2.cpp
//  HelloArrow
//
//  Created by 1 on 13-4-11.
//  Copyright (c) 2013年 1. All rights reserved.
//
#include <OpenGLES/ES2/gl.h>
#include <OpenGLES/ES2/glext.h>
#include "IRenderingEngine.hpp"
#include "RenderingEngine2.hpp"
#include "MatrixState.hpp"

#include "LoadResourceUtil.h"

RenderingEngine2::RenderingEngine2()
{
}

void RenderingEngine2::Initialize(int width, int height)
{
    glGenRenderbuffers(1, &m_renderbuffer);//创建一个渲染缓冲区对象
    glBindRenderbuffer(GL_RENDERBUFFER, m_renderbuffer);//将该渲染缓冲区对象绑定到管线上
    glGenRenderbuffers(1, &m_depthRenderbuffer);//创建深度缓冲区对象
    glBindRenderbuffer(GL_RENDERBUFFER, m_depthRenderbuffer);//将该深度缓冲区对象绑定到管线上
    glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT16,width, height);//设定渲染缓存的存储类型
    glGenFramebuffers(1, &m_framebuffer);//创建一个帧缓冲区对象
    glBindFramebuffer(GL_FRAMEBUFFER, m_framebuffer);//将该帧染缓冲区对象绑定到管线上
    //将创建的渲染缓冲区绑定到帧缓冲区上，并使用颜色填充
    glFramebufferRenderbuffer(GL_FRAMEBUFFER,GL_COLOR_ATTACHMENT0,GL_RENDERBUFFER,m_renderbuffer);
    //将创建的深度缓冲区绑定到帧缓冲区上
    glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT,GL_RENDERBUFFER, m_depthRenderbuffer);
    glBindRenderbuffer(GL_RENDERBUFFER, m_renderbuffer);//将该渲染缓冲区对象绑定到管线上
    
    glViewport(0, 0, width, height);//设置适口
    glEnable(GL_CULL_FACE);
    glEnable(GL_DEPTH_TEST);
    //计算宽高比
    float ratio = (float) width/height;
    //设置投影矩阵
    MatrixState::setProjectFrustum(-ratio, ratio, -1, 1, 4, 100);
    //设置社摄像机
   MatrixState::setCamera(0, 0, 11.2f, 0, 0, 0,0,1, 0);
    //设置太阳灯光的初始位置
    MatrixState::setLightLocationSun(100,5,0);
    MatrixState::setInitStack();   //初始化矩阵
    textureIdEarth = LoadResourceUtil::initTexture("earth");  //加载地球白天纹理
    textureIdEarthNight = LoadResourceUtil::initTexture("earthn");//加载地球夜晚纹理
    textureIdMoon = LoadResourceUtil::initTexture("moon");//加载月球纹理

    e = new Earth(2.0);//新建地球对象
    m = new Moon(1.0);//新建月球对象
    cs = new Celestial(1,0);//新建天空球对象
   
}



void RenderingEngine2::Render() const
{
    glClearColor(0.0f, 0.0f, 0.1f, 1);//设置背景颜色
    //清除深度缓冲与颜色缓冲
    glClear(GL_DEPTH_BUFFER_BIT|GL_COLOR_BUFFER_BIT);
    
    MatrixState::pushMatrix();//保护现场
    MatrixState::rotate(eAngle, 0, 1, 0);//地球自转
    e->drawSelf(textureIdEarth, textureIdEarthNight);//绘制地球
    MatrixState::translate(2, 0, 0);
    //月球自转
    MatrixState::rotate(eAngle, 0, 1, 0);
    m->drawSelf(textureIdMoon);//绘制月球
    MatrixState::popMatrix();//恢复现场
    
    //保护现场
    MatrixState::pushMatrix();
    MatrixState::rotate(cAngle, 0, 1, 0);//天空球自转
    cs->drawSelf();//绘制天空球
    //恢复现场
    MatrixState::popMatrix();
    
    eAngle = eAngle+0.2f;
    cAngle = cAngle+0.05f;
}
void RenderingEngine2::OnFingerDown(float locationx,float locationy)
{

}
void RenderingEngine2::OnFingerUp(float locationx,float locationy)
{
    
}
void RenderingEngine2::OnFingerMove(float previousx,float previousy,float currentx,float currenty)
{
    //触控横向位移太阳绕y轴旋转
    float dx=currentx-previousx;//计算触控笔X位移
    yAngle+=(float)dx*180.0f/320.0f;
    float sunx=(float)std::cos((float)yAngle*PI)*100;
    float sunz=-(float)(std::sin((float)yAngle*PI)*100);
    MatrixState::setLightLocationSun(sunx,0,sunz);//重新设置灯光
    //触控纵向位移摄像机绕x轴旋转 -90〜+90
    float dy=currenty-previousy;//计算触控笔Y位移
    xAngle+=(float)dy*90.0f/320.0f;//设置太阳绕y轴旋转的角度
    if(xAngle>90)
    {
        xAngle=90;
    }
    else if(xAngle<-90)
    {
        xAngle=-90;
    }
    float cy=(float) (11.2f*std::sin((float)xAngle*PI));
    float cz=(float) (11.2f*std::cos((float)xAngle*PI));
    float upy=(float) std::cos((float)xAngle*PI);
    float upz=-(float) std::sin((float)xAngle*PI);
    MatrixState::setCamera(0, cy, cz, 0, 0, 0, 0, upy,upz);//重新设置摄像机
}

