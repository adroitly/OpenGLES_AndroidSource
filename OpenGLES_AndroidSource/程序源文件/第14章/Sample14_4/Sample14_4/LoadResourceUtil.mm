//
//  LoadResourceUtil.cpp
//  OpenGL2.0Demo
//
//  Created by 1 on 13-4-17.
//  Copyright (c) 2013年 百纳. All rights reserved.
//
#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>
#import <string>
#import <iostream>
#import <OpenGLES/ES2/gl.h>
#import <OpenGLES/ES2/glext.h>

#import "LoadResourceUtil.h"

using namespace std;


GLuint LoadResourceUtil::initTexture(const string& name)
{
    GLuint m_gridTexture;//定义纹理ID

    // Load the texture.
    glGenTextures(1, &m_gridTexture);//产生纹理对象索引
    glBindTexture(GL_TEXTURE_2D, m_gridTexture);
    //设置纹理对象的缩放过滤
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,GL_NEAREST);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,GL_LINEAR);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S,GL_CLAMP_TO_EDGE);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T,GL_CLAMP_TO_EDGE);
    
    //将c++字符串转换为Objective-C字符串对象
    NSString* basePath = [[NSString alloc] initWithUTF8String:name.c_str()];
    NSBundle* mainBundle = [NSBundle mainBundle];
    NSString* fullPath = [mainBundle pathForResource:basePath ofType:@"png"];//获取PNG文件的全路径符
    UIImage* uiImage = [[UIImage alloc] initWithContentsOfFile:fullPath];//创建UIImage对象
    CGImageRef cgImage = uiImage.CGImage;//从UIImage中获取内部CGImage对象
    float x = CGImageGetWidth(cgImage);//从内部CGImage对象中获取图像尺寸
    float y = CGImageGetHeight(cgImage);
    //从CGImage中创建CFData对象
    CFDataRef m_imageData = CGDataProviderCopyData(CGImageGetDataProvider(cgImage));
    void* pixels = (void*) CFDataGetBytePtr(m_imageData);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, x, y, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        
    CFRelease(m_imageData);//释放CFData对象
    [uiImage release];//释放UIImage对象
    [basePath release];//释放字符串对象
        
    return m_gridTexture;//返回纹理ID
}
