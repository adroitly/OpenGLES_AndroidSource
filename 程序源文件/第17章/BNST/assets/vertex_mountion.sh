uniform mat4 uMVPMatrix; //�ܱ任����
attribute vec3 aPosition;  //����λ��
attribute vec2 aTexCoor;    //������������
varying vec2 vTextureCoord;  //���ڴ��ݸ�ƬԪ��ɫ���ı���
varying float rTemp;		//���ڴ��ݸ�ƬԪ��ɫ���Ķ�������ı���
varying float currY;
void main()     
{                            		
   gl_Position = uMVPMatrix * vec4(aPosition,1); //�����ܱ任�������˴λ��ƴ˶���λ��
   vTextureCoord = aTexCoor;//�����յ��������괫�ݸ�ƬԪ��ɫ��
   currY=aPosition.y;
   rTemp=aPosition.x*aPosition.x+aPosition.y*aPosition.y;//��ɽ�Ͼ���ԭ������ľ���
}                      