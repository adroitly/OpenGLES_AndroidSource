//���������ܵ�ƬԪ��ɫ��
precision mediump float;
varying vec2 vTextureCoord; //���մӶ�����ɫ�������Ĳ���
uniform sampler2D sTexture;//������������
uniform float stK;
uniform float tmd;
void main()                         
{   
    //��������ST��ƫ����    
	vec2 st_Result=vec2(0,0);

	st_Result.x=vTextureCoord.x;
	st_Result.y=vTextureCoord.y-stK;

    vec4 finalColor=texture2D(sTexture, st_Result);
    finalColor.a=finalColor.a*tmd;

   //����ƬԪ�������в�������ɫֵ
   gl_FragColor = finalColor; 
}