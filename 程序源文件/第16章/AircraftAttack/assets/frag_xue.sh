precision mediump float;
varying vec2 vTextureCoord; //���������������
varying float vertexHeight;//���ܶ���ĸ߶�ֵ
varying float vertexwhidth;//����Ѫ�������λ��
uniform sampler2D sTexture;//������������
uniform float ublood;
void main()                         
{
  vec4 finalColor=texture2D(sTexture, vTextureCoord);
  if(vertexwhidth<ublood&&vertexwhidth>-97.0&&vertexwhidth<97.0&&vertexHeight>-6.5&&vertexHeight<6.5)
  {
  	gl_FragColor = vec4(0.5,0.17,0.04,1.0);
  }
  else
  {
  	gl_FragColor=finalColor;
  }
}              