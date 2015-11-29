precision mediump float;
//���մӶ�����ɫ�������Ĳ���
varying vec4 ambient;
varying vec4 diffuse;
varying vec4 specular;
varying vec3 vPosition;

uniform float colorR;//��ɫֵ��R����
uniform float colorG;//��ɫֵ��G����
uniform float colorB;//��ɫֵ��B����
uniform float colorA;

void main()                         
{    

     //�����������ɫ����ƬԪ
	   vec4 finalColor;

	   		finalColor=vec4(colorR,colorG,colorB,colorA);


	   gl_FragColor = finalColor*ambient+finalColor*specular+finalColor*diffuse;//����ƬԪ��ɫֵ
}