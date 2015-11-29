//���������ܵ�ƬԪ��ɫ��
precision mediump float;
varying vec2 vTextureCoord; //���մӶ�����ɫ�������Ĳ���
uniform sampler2D sTextureGrass;//�����������ݣ���Ƥ��
uniform sampler2D sTextureRock;//�����������ݣ���ʯ��
uniform float b_YZ_StartX;//½����ʼX
uniform float b_YZ_XSpan;//½��Xƫ����
varying float currX;//��ǰƬԪ��½��X
void main()                         
{           
   float min=0.2;
   float max=0.6;
   
   float currXRatio=(currX-b_YZ_StartX)/b_YZ_XSpan;
   
   vec4 gColor=texture2D(sTextureGrass, vTextureCoord); 
   vec4 rColor=texture2D(sTextureRock, vTextureCoord); 
   
   vec4 finalColor;
   
   if(currXRatio<min)
   {
      finalColor=gColor;
   }
   else if(currXRatio>max)
   {
      finalColor=rColor;
   }
   else
   {
      float rockBL=(currXRatio-min)/(max-min);
      finalColor=rockBL*rColor+(1.0-rockBL)*gColor;
   }  
   
   //����ƬԪ�������в�������ɫֵ            
   gl_FragColor = finalColor; 
}              