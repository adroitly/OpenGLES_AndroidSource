precision mediump float;
//���մӶ�����ɫ�������Ĳ���
varying vec4 ambient;
varying vec4 diffuse;
varying vec4 specular;
void main()                         
{    
   //�����������ɫ����ƬԪ
   vec4 finalColor=vec4(1.0,1.0,1.0,1.0);   
   gl_FragColor = finalColor*ambient+finalColor*specular+finalColor*diffuse;//����ƬԪ��ɫֵ
}   