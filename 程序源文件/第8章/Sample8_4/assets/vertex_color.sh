uniform mat4 uMVPMatrix; //�ܱ任����
attribute vec3 aPosition;  //����λ��
attribute vec4 aColor;    //������ɫ
varying  vec4 vaColor;  //���ڴ��ݸ�ƬԪ��ɫ���ı���
void main()     
{                            		
   gl_Position = uMVPMatrix * vec4(aPosition,1); //�����ܱ任�������˴λ��ƴ˶���λ��
   vaColor = aColor;//�����յ���ɫ���ݸ�ƬԪ��ɫ��
}                      