//
//  GLView.h
//  OpenGL2.0Demo
//
//  Created by 1 on 13-4-13.
//  Copyright (c) 2013年 百纳. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <OpenGLES/EAGL.h>
#import <QuartzCore/QuartzCore.h>
#import <OpenGLES/ES2/gl.h>
#import <OpenGLES/ES2/glext.h>

@interface GLView : UIView
{
    EAGLContext* mContext;
}
- (void) drawView: (CADisplayLink*) displayLink;    //drawView方法
@end
