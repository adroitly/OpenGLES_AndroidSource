precision mediump float;
varying vec2 vTextureCoord; //���մӶ�����ɫ�������Ĳ���
uniform sampler2D sTexture;//������������
void main() { 
   vec4 bcolor = texture2D(sTexture, vTextureCoord);//����ƬԪ�������в�������ɫֵ 
   if(bcolor.a<0.6) {
   		discard;
   } else {
      gl_FragColor=bcolor;
}}