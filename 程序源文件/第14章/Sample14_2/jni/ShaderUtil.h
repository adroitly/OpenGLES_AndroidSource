#ifndef OpenGL2_0Demo_ShaderUtil_hpp
#define OpenGL2_0Demo_ShaderUtil_hpp
#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>

class ShaderUtil
{
public:
    static GLuint createProgram(const char* vertexShaderSource,
                                          const char* fragmentShaderSource)
    {
        GLuint vertexShader = loadShader(vertexShaderSource, GL_VERTEX_SHADER);
        GLuint fragmentShader = loadShader(fragmentShaderSource, GL_FRAGMENT_SHADER);
        GLuint programHandle = glCreateProgram();
        glAttachShader(programHandle, vertexShader);
        glAttachShader(programHandle, fragmentShader);
        glLinkProgram(programHandle);
        GLint linkSuccess;
        glGetProgramiv(programHandle, GL_LINK_STATUS, &linkSuccess);
        if (linkSuccess == GL_FALSE) {
            GLchar messages[256];
            glGetProgramInfoLog(programHandle, sizeof(messages), 0, &messages[0]);

        }
        return programHandle;
    }
    
    static GLuint loadShader(const char* source, GLenum shaderType)
    {
        GLuint shaderHandle = glCreateShader(shaderType);
        glShaderSource(shaderHandle, 1, &source, 0);
        glCompileShader(shaderHandle);
        GLint compileSuccess;
        glGetShaderiv(shaderHandle, GL_COMPILE_STATUS, &compileSuccess);
        if (compileSuccess == GL_FALSE) {
            GLchar messages[256];
            glGetShaderInfoLog(shaderHandle, sizeof(messages), 0, &messages[0]);

        }
        return shaderHandle;
    }
    
    
};

#endif
