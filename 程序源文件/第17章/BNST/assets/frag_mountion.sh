precision mediump float;
varying vec2 vTextureCoord; //���մӶ�����ɫ�������Ĳ���
uniform sampler2D sTexture;//������������

varying float currY;
uniform sampler2D sTextureGrass;//�����������ݣ���Ƥ��
uniform sampler2D sTextureRock;//�����������ݣ���ʯ��
varying float rTemp;
uniform float b_YZ_StartY;//½����ʼY
uniform float b_YZ_YSpan;//½��Yƫ����
uniform int sdflag;//�Ƿ�Ϊ���ɽ�ı�־λ

void main()                         
{      
       if(sdflag==0)//��ʾΪ���ɽ
       {  
           float min=0.4;
			   float max=0.8;
			   
			   float currYRatio=(currY-b_YZ_StartY)/b_YZ_YSpan;
			   
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
            	   
		   if(rTemp<330.0)//����С��400�Ĳ����� 
		   {
		   		finalColor.a=0.0;
		   }
		   else
		   {
			   //����ƬԪ�������в�������ɫֵ            
			   
		   }
		   gl_FragColor = finalColor; 
       }
       else if(sdflag==1)//��ͨ
       {
       	   float min=0.3;
		   float max=0.7;
		   
		   float currYRatio=(currY-b_YZ_StartY)/b_YZ_YSpan;
		   
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
       
       
   	   
}              