uniform mat4 uMVPMatrix; //�ܱ任����
attribute vec3 aPosition;  //����λ��
varying vec3 vPosition;  //����λ��
void main() {                            		
   gl_Position = uMVPMatrix * vec4(aPosition,1); //�����ܱ任�������˴λ��ƴ˶���λ��
   vPosition=aPosition;//�������ԭʼλ�ô��ݸ�ƬԪ��ɫ��
}