package com.bn.Sample8_2;

//���������η������Ĺ�����
public class VectorUtil {
	
	//����Բ׶���㷨�����ķ���
	public static float[] calConeNormal
	(//������������������
			float x0,float y0,float z0,//A�����ĵ�(����Բ��Բ��)
			float x1,float y1,float z1,//B������Բ��һ��
			float x2,float y2,float z2 //C������(��ߵĵ�)
	)
	{
		float[] a={x1-x0, y1-y0, z1-z0};//����AB
		float[] b={x2-x0, y2-y0, z2-z0};//����AC
		float[] c={x2-x1, y2-y1, z2-z1};//����BC
		//����ֱ��ƽ��ABC������k
		float[] k=crossTwoVectors(a,b);
		//��c��k����ˣ��ó���������d
		float[] d=crossTwoVectors(c,k);
		return normalizeVector(d);//���ع�񻯺�ķ�����
	}
	//������񻯵ķ���
	public static float[] normalizeVector(float [] vec){
		float mod=module(vec);
		return new float[]{vec[0]/mod, vec[1]/mod, vec[2]/mod};//���ع�񻯺������
	}
	//��������ģ�ķ���
	public static float module(float [] vec){
		return (float) Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]+vec[2]*vec[2]);
	}
	//����������˵ķ���
	public static float[] crossTwoVectors(
			float[] a,
			float[] b)
	{
		float x=a[1]*b[2]-a[2]*b[1];
		float y=a[2]*b[0]-a[0]*b[2];
		float z=a[0]*b[1]-a[1]*b[0];
		return new float[]{x, y, z};//���ط�����
	}
}
