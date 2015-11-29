#include <jni.h>
#include <android/log.h>
#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "Triangle.h"
#include "Matrix.h"
#include "MatrixState.h"

Triangle *t;

bool setupGraphics(int w, int h) {
    glViewport(0, 0, w, h);
    float ratio = (float) w/h;
    MatrixState::setProjectFrustum(-ratio, ratio, -1, 1, 1, 10);
    MatrixState::setCamera(0, 0, 3, 0, 0, 0, 0, 1, 0);
    MatrixState::setInitStack();
    glClearColor(0.5f, 0.5f, 0.5f, 1);
    t = new Triangle();
    return true;
}

void renderFrame() {
    glClear(GL_COLOR_BUFFER_BIT);
    t->drawSelf();
    MatrixState::rotate(1,1,0,0);
}
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_com_bn_sample14_11_GL2JNILib_init
  (JNIEnv *, jclass, jint width, jint height)
{
    setupGraphics(width, height);
}

JNIEXPORT void JNICALL Java_com_bn_sample14_11_GL2JNILib_step
  (JNIEnv *, jclass)
{
    renderFrame();
}

#ifdef __cplusplus
}
#endif


