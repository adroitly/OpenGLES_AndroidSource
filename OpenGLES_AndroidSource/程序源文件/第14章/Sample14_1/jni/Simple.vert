const char* SimpleVertexShader = STRINGIFY(
attribute vec3 Position;
attribute vec4 SourceColor;
varying vec4 DestinationColor;
uniform mat4 Modelview;
void main(void)
{
    DestinationColor = SourceColor;
    gl_Position = Modelview * vec4(Position,1);
}
);