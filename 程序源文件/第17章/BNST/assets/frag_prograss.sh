precision mediump float;
varying vec2 vTextureCoord; //���մӶ�����ɫ�������Ĳ���
uniform sampler2D sTexture;//������������
uniform float aPrograss;
varying vec3 vPosition;
void main()                         
{
	if(vPosition.x<aPrograss)
	{
		//����ƬԪ�������в�������ɫֵ            
   		gl_FragColor = texture2D(sTexture, vTextureCoord); 
	}
	else
	{
		discard;
	}
   
}              