#include "Triangle.h"
#include "ShaderUtil.h"
#include "MatrixState.h"

#define STRINGIFY(A) #A
#include "Simple.frag"
#include "Simple.vert"

Triangle::Triangle()
{
    initVertexData();
    initShader();
}

void Triangle::initVertexData()
{
    vCount = 3;
    pCoords = &vertices[0];
    pColors = &colors[0];
}

void Triangle::initShader() 
{
    mProgram = ShaderUtil::createProgram(SimpleVertexShader, SimpleFragmentShader);
    maPositionHandle = glGetAttribLocation(mProgram, "Position");
    maColorHandle = glGetAttribLocation(mProgram, "SourceColor");
    muMVPMatrixHandle = glGetUniformLocation(mProgram, "Modelview");
}

void Triangle::drawSelf()
{
    glUseProgram(mProgram);
    
    glUniformMatrix4fv(muMVPMatrixHandle, 1, 0, MatrixState::getFinalMatrix());
    
    glVertexAttribPointer(maPositionHandle, 3, GL_FLOAT, GL_FALSE, 3*4, pCoords);
    glVertexAttribPointer(maColorHandle, 4, GL_FLOAT, GL_FALSE, 4*4, pColors);
    
    glEnableVertexAttribArray(maPositionHandle);
    glEnableVertexAttribArray(maColorHandle);
    
    glDrawArrays(GL_TRIANGLES, 0, vCount);
}
