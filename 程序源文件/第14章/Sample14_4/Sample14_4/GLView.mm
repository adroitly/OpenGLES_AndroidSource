//
//  GLView.m
//  OpenGL2.0Demo
//
//  Created by 1 on 13-4-13.
//  Copyright (c) 2013年 百纳. All rights reserved.
//
#import "GLView.h"
#import "IRenderingEngine.hpp"
@implementation GLView
IRenderingEngine* mRenderingEngine;
- (id)initWithFrame:(CGRect)frame {
    if (self = [super initWithFrame:frame]) {
        CAEAGLLayer* eaglLayer = (CAEAGLLayer*) super.layer;
        eaglLayer.opaque = YES;
        //设置使用OPENGL ES2.0
        EAGLRenderingAPI api = kEAGLRenderingAPIOpenGLES2;
        //使用OPENGL ES2.0版初始化EAGLContext
        mContext = [[EAGLContext alloc] initWithAPI:api]; 
        //使用2.0版初始化EAGLContext失败
        if (!mContext || ![EAGLContext setCurrentContext:mContext]){ 
            [self release];//释放内存
            return nil;
        }else{
            mRenderingEngine = CreateRenderer2();//创建mRenderingEngine对象
        }
        mRenderingEngine->
                Initialize(CGRectGetWidth(frame), CGRectGetHeight(frame));
        [mContext renderbufferStorage:GL_RENDERBUFFER fromDrawable: eaglLayer]; 
        [self drawView: nil];
        CADisplayLink* displayLink;
        displayLink = [CADisplayLink displayLinkWithTarget:self
                                        selector:@selector(drawView:)];
        [displayLink addToRunLoop:[NSRunLoop currentRunLoop]
                          forMode:NSDefaultRunLoopMode];
        
    }
    return self;
}
- (void) drawView: (CADisplayLink*) displayLink{
    mRenderingEngine->Render();//调用mRenderingEngine对象的渲染方法
    [mContext presentRenderbuffer:GL_RENDERBUFFER];
}
+ (Class) layerClass{
    return [CAEAGLLayer class];
}
- (void) touchesBegan: (NSSet*) touches withEvent: (UIEvent*) event{
    UITouch* touch = [touches anyObject];//获取事件
    CGPoint location  = [touch locationInView: self];//获取触摸点的坐标
    mRenderingEngine->OnFingerDown(location.x, location.y);//调用监控按下屏幕的函数
}
- (void) touchesEnded: (NSSet*) touches withEvent: (UIEvent*) event{
    UITouch* touch = [touches anyObject];//获取事件
    CGPoint location  = [touch locationInView: self];//获取触摸点的坐标
    mRenderingEngine->OnFingerUp(location.x, location.y);//调用监控抬起屏幕的函数
}
- (void) touchesMoved: (NSSet*) touches withEvent: (UIEvent*) event{
    UITouch* touch = [touches anyObject];//获取事件
    CGPoint previous  = [touch previousLocationInView: self];//获取上一个触摸点的坐标
    CGPoint current = [touch locationInView: self];//获取当前触摸点的坐标
    mRenderingEngine->OnFingerMove(previous.x, previous.y,//调用监控触摸点移动的函数
                                    current.x, current.y);
}
@end
