//���������ܵ�ƬԪ��ɫ��
precision mediump float;
varying vec2 vTextureCoord; //���մӶ�����ɫ�������Ĳ���
uniform sampler2D sTexture;//������������
uniform float stK;
void main()                         
{   
    //��������ST��ƫ����    
	vec2 st_Result=vec2(0,0);

	st_Result.x=vTextureCoord.x+stK;
	st_Result.y=vTextureCoord.y+stK;

   //����ƬԪ�������в�������ɫֵ            
   gl_FragColor = texture2D(sTexture, st_Result); 
}