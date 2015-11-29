precision mediump float;
varying vec2 vTextureCoord; //���մӶ�����ɫ�������Ĳ���
uniform sampler2D sTexture;//������������
varying float currY;
uniform sampler2D sTextureGrass;//�����������ݣ���Ƥ��
uniform sampler2D sTextureRock;//�����������ݣ���ʯ��
uniform float landStartY;//½����ʼY
uniform float landYSpan;//½��Yƫ����

void main()
{           
   	   float min=0.25;
	   float max=0.7;
	   
	   float currYRatio=(currY-landStartY)/landYSpan;
	   
	   vec4 gColor=texture2D(sTextureGrass, vTextureCoord); 
	   vec4 rColor=texture2D(sTextureRock, vTextureCoord); 
	   
	   vec4 finalColor;
	   
	   if(currYRatio<min)
	   {
	      finalColor=gColor;
	   }
	   else if(currYRatio>max)
	   {
	      finalColor=rColor;
	   }
	   else
	   {
	      float rockBL=(currYRatio-min)/(max-min);
	      finalColor=rockBL*rColor+(1.0-rockBL)*gColor;
	   }
	   //����ƬԪ�������в�������ɫֵ            
	   gl_FragColor = finalColor; 
}              