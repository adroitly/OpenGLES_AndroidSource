//
//  IRenderingEngine.h
//  OpenGL2.0Demo
//
//  Created by 1 on 13-4-13.
//  Copyright (c) 2013年 百纳. All rights reserved.
//

#ifndef IRenderingEngine_h
#define IRenderingEngine_h
struct IRenderingEngine* CreateRenderer2();
struct IRenderingEngine{
    //定义纯虚函数
    virtual void Initialize(int width, int height) = 0;
    virtual void Render() const = 0;
    virtual void OnFingerUp(float locationx,float locationy) = 0;
    virtual void OnFingerDown(float locationx,float locationy) = 0;
    virtual void OnFingerMove(float previousx,float previousy,float currentx,float current) = 0;
    virtual ~IRenderingEngine() {}
};
#endif
