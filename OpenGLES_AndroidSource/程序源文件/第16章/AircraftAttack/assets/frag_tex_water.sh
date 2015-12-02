//具有纹理功能的片元着色器
precision mediump float;
varying vec2 vTextureCoord; //接收从顶点着色器过来的参数
uniform sampler2D sTexture;//纹理内容数据
uniform float stK;
void main()                         
{   
    //纹理坐标ST的偏移量    
	vec2 st_Result=vec2(0,0);
	st_Result.x=vTextureCoord.x+stK;
	st_Result.y=vTextureCoord.y;
   //给此片元从纹理中采样出颜色值            
   gl_FragColor = texture2D(sTexture, st_Result); 
}
