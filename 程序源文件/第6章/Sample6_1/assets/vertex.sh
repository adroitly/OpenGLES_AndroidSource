uniform mat4 uMVPMatrix; //�ܱ任����
attribute vec3 aPosition;  //����λ��
varying vec3 vPosition;//���ڴ��ݸ�ƬԪ��ɫ���Ķ���λ��
void main()     
{                   
   //�����ܱ任�������˴λ��ƴ˶���λ��         		
   gl_Position = uMVPMatrix * vec4(aPosition,1); 
   //�������λ�ô���ƬԪ��ɫ��
   vPosition = aPosition;//��ԭʼ����λ�ô��ݸ�ƬԪ��ɫ��
}                      