precision mediump float;
varying vec2 vTextureCoord; //���մӶ�����ɫ�������Ĳ���
uniform sampler2D sTexture;//������������
uniform int uisButtonDown;//��ť�Ƿ���,�������,�ı��䲻͸����
void main()                         
{           
   //����ƬԪ�������в�������ɫֵ            
   vec4 finalColor = texture2D(sTexture, vTextureCoord); 
   if(uisButtonDown==1)//��ǰ��ť����
   {
   	  gl_FragColor=finalColor*0.5;
   }
   else
   {
   	  gl_FragColor=finalColor;
   }
   
}              