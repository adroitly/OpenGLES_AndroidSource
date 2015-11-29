package com.bn.archieModel;
import com.bn.commonObject.*;
import com.bn.core.MatrixState;
/*
 * �����ڹ�,���ڻ��Ƹ����ڵ��ڹܲ���
 */
public class BarrelForDraw 
{
	CylinderForDraw longCylinder;//����Ͳ
	CylinderForDraw shortCylinder;//����Ͳ
	CircleForDraw bigCircle;//��Բ,���ڶ���Ͳ
	CircleForDraw shortCircle;//СԲ,���ڳ���Ͳ
	
	private float radius_ratio=1.2f;//����Ͳ�뾶�볤��Ͳ�뾶�ı���
	private float cylinder_ratio=0.2f;//����Ͳռ����Ͳ�ı���
	private float length_long;//����Ͳ�ĳ���
	private float length_short;//����Ͳ�ĳ���
	private float radius_short;//����Ͳ�İ뾶
	
	public BarrelForDraw(float length,float radius,int mProgram)
	{
		this.length_long=length;
		this.length_short=length*cylinder_ratio;
		this.radius_short=radius*radius_ratio;
		longCylinder=new CylinderForDraw(radius, length, mProgram);//���Ƴ���Ͳ
		shortCircle=new CircleForDraw(mProgram, radius);//����Ͳ�˿�Բ
		shortCylinder=new CylinderForDraw(radius_short, length_short, mProgram);//���ƶ���Ͳ
		bigCircle=new CircleForDraw(mProgram, radius_short);//����Ͳ�˿�Բ
	}
	public void drawSelf(int texBarrelId[])//����0��ʾ����ͲԲ��,1��ʾ����ͲԲ��,2��ʾ����ͲԲ��,3��ʾ����ͲԲ��
	{
		//--------------------���Ƴ���Ͳ------------------------
		//���Ƴ���Ͳ
		MatrixState.pushMatrix();
		longCylinder.drawSelf(texBarrelId[0]);
		MatrixState.popMatrix();
		//���Ƴ���Ͳ�ϲ��˿�Բ
		MatrixState.pushMatrix();
		MatrixState.rotate(-90, 1, 0, 0);
		MatrixState.translate(0, 0, length_long/2);
		shortCircle.drawSelf(texBarrelId[1]);
		MatrixState.popMatrix();
		//���Ƴ���Ͳ�²���Բ
		MatrixState.pushMatrix();
		MatrixState.rotate(90, 1, 0, 0);
		MatrixState.translate(0, 0, length_long/2);
		shortCircle.drawSelf(texBarrelId[1]);
		MatrixState.popMatrix();
		//---------------------���ƶ���Ͳ-------------------------
		MatrixState.pushMatrix();
		MatrixState.translate(0, length_long/2-length_short, 0);
		
		MatrixState.pushMatrix();
		shortCylinder.drawSelf(texBarrelId[2]);
		MatrixState.popMatrix();
		//���Ƴ���Ͳ�ϲ��˿�Բ
		MatrixState.pushMatrix();
		MatrixState.rotate(-90, 1, 0, 0);
		MatrixState.translate(0, 0, length_short/2);
		bigCircle.drawSelf(texBarrelId[3]);
		MatrixState.popMatrix();
		//���Ƴ���Ͳ�²���Բ
		MatrixState.pushMatrix();
		MatrixState.rotate(90, 1, 0, 0);
		MatrixState.translate(0, 0, length_short/2);
		bigCircle.drawSelf(texBarrelId[3]);
		MatrixState.popMatrix();
		
		MatrixState.popMatrix();
	} 
}
