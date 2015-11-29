#ifndef ShaderUtil_hpp
#define ShaderUtil_hpp
#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>

class ShaderUtil
{
public:
    static GLuint createProgram(const char* vertexShaderSource,
                                          const char* fragmentShaderSource);
    static GLuint loadShader(const char* source, GLenum shaderType);
};

#endif
