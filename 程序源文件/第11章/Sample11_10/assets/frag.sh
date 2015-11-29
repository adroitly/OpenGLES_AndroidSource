precision mediump float;
//���մӶ�����ɫ�������Ĳ���
varying vec4 diffuse;
varying vec4 specular;
varying float vEdge;
void main()                         
{
   float averageDiffuse = (diffuse.x + diffuse.y + diffuse.z)/3.0;
   if(averageDiffuse<0.5){
      averageDiffuse=0.2;
   } else {
      averageDiffuse=0.8;
   }
   vec4 diffuseFinal = vec4(averageDiffuse, averageDiffuse, averageDiffuse, 1.0);
   
   float averageSpecular = (specular.x + specular.y + specular.z)/3.0;
   if(averageSpecular<0.18){
      averageSpecular=0.0;
   } else {
      averageSpecular=1.0;
   }
   vec4 specularFinal = vec4(averageSpecular, averageSpecular, averageSpecular, 1.0);
   
   float edgeFinal = 1.0;
   if(vEdge<0.2){//���Ϊ��Ե���أ��ú�ɫ���
       edgeFinal = 0.0;
   }
   gl_FragColor = edgeFinal*(specularFinal+diffuseFinal);//����ƬԪ��ɫֵ
}