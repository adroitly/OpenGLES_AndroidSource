precision mediump float;
//���մӶ�����ɫ�������Ĳ���
uniform float colorR;//��ɫֵ��R����
uniform float colorG;//��ɫֵ��G����
uniform float colorB;//��ɫֵ��B����
uniform float colorA;
void main()                         
{    
    //�����������ɫ����ƬԪ
	vec4 finalColor;
	finalColor=vec4(colorR,colorG,colorB,colorA);
    gl_FragColor = finalColor;//����ƬԪ��ɫֵ
}