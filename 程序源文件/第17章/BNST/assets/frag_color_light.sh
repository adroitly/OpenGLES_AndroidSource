precision mediump float;
//���մӶ�����ɫ�������Ĳ���
varying vec4 ambient;
varying vec4 diffuse;
varying vec4 specular;
uniform vec4 finalColor; 
void main()                         
{   
   gl_FragColor = finalColor*ambient+finalColor*specular+finalColor*diffuse;//����ƬԪ��ɫֵ
}   