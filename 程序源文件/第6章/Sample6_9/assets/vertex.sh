uniform mat4 uMVPMatrix; //�ܱ任����
attribute vec3 aPosition;  //����λ��
attribute vec3 aNormal;    //������

varying vec3 vPosition;//���ڴ��ݸ�ƬԪ��ɫ���Ķ���λ��
varying vec3 vNormal;//���ڴ��ݸ�ƬԪ��ɫ���Ķ��㷨����

void main()     
{                   
   //�����ܱ任�������˴λ��ƴ˶���λ��         		
   gl_Position = uMVPMatrix * vec4(aPosition,1);    
   //�������λ�ô���ƬԪ��ɫ��
   vPosition = aPosition; 
   //������ķ���������ƬԪ��ɫ��
   vNormal = aNormal;
}                      