precision mediump float;
varying  vec3 vPosition;  //����λ��
void main() {
   vec4 bColor=vec4(0.678,0.231,0.129,0);//ש�����ɫ
   vec4 mColor=vec4(0.763,0.657,0.614,0);//�������ɫ
   float y=vPosition.y;
   y=mod((y+100.0)*4.0,4.0);
   if(y>1.8) {
     gl_FragColor = bColor;//����ƬԪ��ɫֵ
   } else {
     gl_FragColor = mColor;//����ƬԪ��ɫֵ
}} 