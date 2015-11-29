uniform mat4 uMVPMatrix; //�ܱ任����
attribute vec3 aPosition;  //����λ��
varying vec3 vPosition;//���ڴ��ݸ�ƬԪ��ɫ���Ķ���λ��
varying vec4 vAmbient;//���ڴ��ݸ�ƬԪ��ɫ���Ļ��������
void main()     
{         
   //�����ܱ任�������˴λ��ƴ˶���λ��         		
   gl_Position = uMVPMatrix * vec4(aPosition,1); 
   //�������λ�ô���ƬԪ��ɫ��
   vPosition = aPosition;   
   //���Ļ������������ƬԪ��ɫ��
   vAmbient = vec4(0.15,0.15,0.15,1.0);
}                      