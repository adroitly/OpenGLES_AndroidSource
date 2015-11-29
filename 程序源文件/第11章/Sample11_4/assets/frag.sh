precision mediump float;							//����Ĭ�ϵĸ��㾫��
varying vec2 vTextureCoord; 						//���մӶ�����ɫ����������������
varying float currY;								//���մӶ�����ɫ��������Y����
uniform sampler2D sTextureGrass;					//�����������ݣ���Ƥ��
uniform sampler2D sTextureRock;					//�����������ݣ���ʯ��
uniform float landStartY;							//����������ʼY����
uniform float landYSpan;							//����������
void main(){          
   vec4 gColor=texture2D(sTextureGrass, vTextureCoord); 	//�Ӳ�Ƥ�����в�������ɫ
   vec4 rColor=texture2D(sTextureRock, vTextureCoord); 	//����ʯ�����в�������ɫ
   vec4 finalColor;									//������ɫ
   if(currY<landStartY){			
	  finalColor=gColor;	//��ƬԪY����С�ڹ���������ʼY����ʱ���ò�Ƥ����
   }else if(currY>landStartY+landYSpan){
	  finalColor=rColor;	//��ƬԪY������ڹ���������ʼY����ӿ��ʱ������ʯ����
   }else{
       float currYRatio=(currY-landStartY)/landYSpan;	//������ʯ������ռ�İٷֱ�
       finalColor= currYRatio*rColor+(1.0- currYRatio)*gColor;//����ʯ����Ƥ������ɫ���������
   } 
	   gl_FragColor = finalColor; //����ƬԪ������ɫֵ    
}  
