precision mediump float;
varying  vec4 vaaColor; //���մӶ�����ɫ�������Ĳ���
varying vec4 vambient;
varying vec4 vdiffuse;
varying vec4 vspecular;
void main()                         
{
   //����ɫ����ƬԪ
	vec4 finalColor = vaaColor;
   //����ƬԪ��ɫֵ 
   gl_FragColor = finalColor*vambient+finalColor*vspecular+finalColor*vdiffuse;//����ƬԪ��ɫֵ
}              