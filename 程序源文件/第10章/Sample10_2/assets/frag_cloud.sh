//�Ʋ���ɫ��
precision mediump float;
varying vec2 vTextureCoord;//���մӶ�����ɫ�������Ĳ���
varying vec4 ambient;
varying vec4 diffuse;
varying vec4 specular;
uniform sampler2D sTexture;//������������
void main()                         
{  
  //����ƬԪ�������в�������ɫֵ            
  vec4 finalColor = texture2D(sTexture, vTextureCoord); 
  //������ɫֵ����͸����
  finalColor.a=(finalColor.r+finalColor.g+finalColor.b)/3.0;
  //�����������
  finalColor=finalColor*ambient+finalColor*specular+finalColor*diffuse;
  //����ƬԪ��ɫֵ 
  gl_FragColor = finalColor;
}              