precision mediump float;
//���մӶ�����ɫ�������Ĳ���
varying vec4 ambient;
varying vec4 diffuse;
varying vec4 specular;
varying float u_clipDist;
void main()                         
{    
	 if(u_clipDist < 0.0) discard;

   //�����������ɫ����ƬԪ
   vec4 finalColor=vec4(0.95,0.95,0.95,1.0);   
   gl_FragColor = finalColor*ambient+finalColor*specular+finalColor*diffuse;//����ƬԪ��ɫֵ
}   