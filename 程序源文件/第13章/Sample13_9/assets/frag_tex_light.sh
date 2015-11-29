precision mediump float;
uniform sampler2D sTexture;//������������
varying vec2 vTextureCoord; //���մӶ�����ɫ�������Ĳ���
varying vec4 vambient;
varying vec4 vdiffuse;
varying vec4 vspecular;
void main()                         
{
   //�����������ɫ����ƬԪ
   vec4 finalColor=texture2D(sTexture, vTextureCoord);
   //����ƬԪ��ɫֵ 
   gl_FragColor = finalColor*vambient+finalColor*vspecular+finalColor*vdiffuse;//����ƬԪ��ɫֵ
}              