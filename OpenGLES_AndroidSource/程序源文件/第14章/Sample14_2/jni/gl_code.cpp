#include <jni.h>
#include <android/log.h>
#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "Mountion.h"
#include "Matrix.h"
#include "MatrixState.h"

Mountion *m;
int mountionId;
int rockId;

bool setupGraphics(JNIEnv * env,jobject obj,int w, int h) {

	jclass cl = env->FindClass("com/bn/sample14_2/GL2JNIView");
	jmethodID id = env->GetStaticMethodID(cl,"initTextureRepeat","(Landroid/opengl/GLSurfaceView;Ljava/lang/String;)I");
	jstring name = env->NewStringUTF("grass.png");
    mountionId = env->CallStaticIntMethod(cl,id,obj,name);
    name = env->NewStringUTF("rock.png");
    rockId = env->CallStaticIntMethod(cl,id,obj,name);
    glViewport(0, 0, w, h);
    float ratio = (float) w/h;
    MatrixState::setProjectFrustum(-ratio, ratio, -1, 1, 1, 1000);
    MatrixState::setCamera(0, 5, 20, 0, 1, 0, 0, 1, 0);
    MatrixState::setInitStack();
    glEnable(GL_DEPTH_TEST);

    m = new Mountion();
    return true;
}

void renderFrame() {

	glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
	glClearColor(0.0f, 0.0f, 0.0f, 1);
	MatrixState::setInitStack();
    m->drawSelf(mountionId,rockId);

}
#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT void JNICALL Java_com_bn_sample14_12_GL2JNILib_init(JNIEnv * env, jclass jc, jobject obj,  jint width, jint height)
{
    setupGraphics(env,obj,width, height);
}

JNIEXPORT void JNICALL Java_com_bn_sample14_12_GL2JNILib_step(JNIEnv * env, jclass jc)
{
    renderFrame();
}

JNIEXPORT void JNICALL Java_com_bn_sample14_12_GL2JNILib_setCamera
  (JNIEnv *, jclass, jfloat cx, jfloat cy, jfloat cz, jfloat tx, jfloat ty, jfloat tz, jfloat upx, jfloat upy, jfloat upz)
{
	MatrixState::setCamera(cx,5,cz,tx,1,tz,0,1,0);
}
#ifdef __cplusplus
}
#endif
