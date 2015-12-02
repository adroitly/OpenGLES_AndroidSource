#ifndef Triangle__h
#define Triangle__h

#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>

class Triangle {
    GLuint mProgram;
    GLuint muMVPMatrixHandle;
    GLuint maPositionHandle;
    GLuint maColorHandle;
    const GLvoid* pCoords;
    const GLvoid* pColors;
    int vCount;
public:
    Triangle();
    void initVertexData();
    void initShader();
    void drawSelf();
};

const float vertices[]=
{
    -0.8    ,0      ,0,
    0       ,0.8    ,0,
    0.8     ,0      ,0
};


const float colors[] = 
{
    1,1,1,0,
    0,0,1,0,
    0,1,0,0
};

#endif
