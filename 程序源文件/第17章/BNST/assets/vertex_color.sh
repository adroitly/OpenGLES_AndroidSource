uniform mat4 uMVPMatrix; //�ܱ任����
uniform mat4 uMMatrix; //�任����
uniform vec3 uLightLocation;	//��Դλ��
uniform vec3 uCamera;	//�����λ��
attribute vec3 aPosition;  //����λ��
attribute vec3 aNormal;    //���㷨����
//���ڴ��ݸ�ƬԪ��ɫ���ı���
varying vec4 ambient;
varying vec4 diffuse;
varying vec4 specular;
varying vec3 vPosition;

//��λ����ռ���ķ���
void pointLight
(
  in vec3 normal,//������
  inout vec4 ambient,//���������
  inout vec4 diffuse,//ɢ������
  inout vec4 specular,//���淴������  
  in vec3 uLightLocation,	//��Դλ��
  in vec4 lightAmbient,//��Ļ��������
  in vec4 lightDiffuse,//���ɢ������
  in vec4 lightSpecular//��ľ��淴������
)
{
  //����任��ķ�����
  vec3 normalTarget=aPosition+normal;
  vec3 newNormal=(uMMatrix*vec4(normalTarget,1)).xyz-(uMMatrix*vec4(aPosition,1)).xyz;
  newNormal=normalize(newNormal);
  
  //����ӱ���㵽�������ʸ��
  vec3 eye= normalize(uCamera-(uMMatrix*vec4(aPosition,1)).xyz);
  
  //��Դλ��
  vec3 lLocation=uLightLocation;
  
  //����ӱ���㵽��Դλ�õ�ʸ��
  vec3 vp= normalize(lLocation-(uMMatrix*vec4(aPosition,1)).xyz);
  //��������͹�Դλ�õľ���
  float d=length(vp);
  //��ʽ��vp
  vp=normalize(vp);
  vec3 halfVector=normalize(vp+eye);//����������
  
  float shininess=100.0;//�ֲڶȣ�ԽСԽ�⻬
  
  float nDotViewPosition;//������ⷽ��ĵ��
  float nDotViewHalfVector;//���������������ĵ��
  float powerFactor;//���淴���������
  
  nDotViewPosition=max(0.0,dot(newNormal,vp));
  nDotViewHalfVector=max(0.0,dot(newNormal,halfVector));
  
  if(nDotViewPosition==0.0)
  {
     powerFactor=0.0;
  }
  else
  {
     powerFactor=pow(nDotViewHalfVector,shininess);
  }
  
  ambient+=lightAmbient;
  diffuse+=lightDiffuse*nDotViewPosition;
  specular=lightSpecular*powerFactor;  
}
void main()     
{ 
   gl_Position = uMVPMatrix * vec4(aPosition,1); //�����ܱ任�������˴λ��ƴ˶���λ��  
   
   vec4 ambientTemp=vec4(0.0,0.0,0.0,0.0);
   vec4 diffuseTemp=vec4(0.0,0.0,0.0,0.0);
   vec4 specularTemp=vec4(0.0,0.0,0.0,0.0);
   
   pointLight(normalize(aNormal),ambientTemp,diffuseTemp,specularTemp,uLightLocation,vec4(0.1,0.1,0.1,1.0),vec4(0.7,0.7,0.7,1.0),vec4(0.3,0.3,0.3,1.0));
 
   ambient=ambientTemp;
   diffuse=diffuseTemp;
   specular=specularTemp;
   vPosition=aPosition;
}                      