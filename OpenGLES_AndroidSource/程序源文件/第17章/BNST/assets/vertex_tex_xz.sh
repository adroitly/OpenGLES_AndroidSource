//具有波浪功能的顶点着色器
uniform mat4 uMVPMatrix; //总变换矩阵
attribute vec3 aPosition;  //顶点位置
attribute vec2 aTexCoor;    //顶点纹理坐标
varying vec2 vTextureCoord;  //用于传递给片元着色器的变量
void main()     
{ 
   gl_Position = uMVPMatrix * vec4(aPosition,1); 
   vTextureCoord = aTexCoor;//将接收的纹理坐标传递给片元着色器
}                      
