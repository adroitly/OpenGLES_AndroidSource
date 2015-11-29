precision mediump float;
varying vec2 vTextureCoord; //���������������
varying float vertexHeight;//���ܶ���ĸ߶�ֵ
uniform sampler2D usTextureTuCeng;//������������   ----����
uniform sampler2D usTextureCaoDi;//������������-----�ݵ�
uniform sampler2D usTextureShiTou;//������������-----ʯͷ
uniform sampler2D usTextureShanDing;//������������-----ɽ��
uniform float uheight;//��͵�߶�
uniform float uheight_span;
uniform int uland_flag;//ɽ�ı�־1Ϊ�����ϵĸ�ɽ
void main()                         
{           
   //��һ���ͼ�ĸ߶�
 	float height1=30.0;//��һ���Ľ���߶�
 	float height2=30.0;
 	float height3=10.0;
 	
 	float land1=250.0;//½��ɽ�Ĺ���
	float land2=400.0;

 	vec4 finalColor0=vec4(0.3,0.3,0.3,0.5);//��ɫ��
   vec4 finalColor1=texture2D(usTextureTuCeng, vTextureCoord);//����
   vec4 finalColor2=texture2D(usTextureCaoDi, vTextureCoord);   //�ݵ�
   vec4 finalColor3=texture2D(usTextureShiTou, vTextureCoord);//ʯͷ
   vec4 finalColor4=texture2D(usTextureShanDing, vTextureCoord);//ɽ������
   if(uland_flag==0)
   {
	   if(abs(vertexHeight)<uheight)
	   {
	      float ratio=abs(vertexHeight)/uheight;
	      finalColor3 *=(1.0-ratio); 
	   	  finalColor0 *=ratio;
	      gl_FragColor =finalColor3+ finalColor0;
	   }
	   else if(abs(vertexHeight)>=uheight&&abs(vertexHeight)<=uheight+height1)//��һ������߶�
	   {
	   		float ratio=(abs(vertexHeight)-uheight)/height1;
	   		finalColor0 *=(1.0-ratio); 
	   		finalColor1 *=ratio;
	   		gl_FragColor =finalColor1 + finalColor0; 
	   }
	   else if(abs(vertexHeight)>uheight+height1&&abs(vertexHeight)<=uheight_span-height2)
	   {
	   		gl_FragColor =finalColor1;
	   }
	   else if(abs(vertexHeight)>=uheight_span-height2&&abs(vertexHeight)<=uheight_span)
	   {
	   		float ratio=(abs(vertexHeight)-uheight_span+height2)/height2;
	   		finalColor1 *=(1.0-ratio); 
	   		finalColor0 *=ratio;
	   		gl_FragColor =finalColor1 + finalColor0; 
	   }
	   else if(abs(vertexHeight)>=uheight_span&&abs(vertexHeight)<=uheight_span+height3)
	   {
	   		float ratio=(abs(vertexHeight)-uheight_span)/height3;
	   		finalColor0 *=(1.0-ratio); 
	   		finalColor2 *=ratio;
	   		finalColor0.a=0.2;
	   		gl_FragColor =finalColor2 + finalColor0; 
	   }   
	   else
	   {
	    	gl_FragColor = finalColor2; 
	   }  
	}
	else
	{
		
		if(abs(vertexHeight)<land1)
		{
			gl_FragColor = finalColor2; 
		}
		else if(abs(vertexHeight)>=land1&&abs(vertexHeight)<=land2)
		{
			float ratio=(abs(vertexHeight)-land1)/(land2-land1);
	   		finalColor2 *=(1.0-ratio); 
	   		finalColor4 *=ratio;
	   		gl_FragColor =finalColor2 + finalColor4; 
		}
		else
		{
			gl_FragColor = finalColor4; 
		}
	} 
}              