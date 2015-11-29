package com.bn.archieModel;

import com.bn.commonObject.CircleForDraw;
import com.bn.commonObject.CylinderForDraw;
import com.bn.core.MatrixState;

/*
 * ������̨,ΪԲ������̨,�伸������λ��ԭ��
 */
public class BarbetteForDraw 
{
	CylinderForDraw cylinder;//Բ��
	CircleForDraw circle;//Բ��
	private float length;
	public BarbetteForDraw(float length,float radius,int mProgram)
	{
		this.length=length;
		cylinder=new CylinderForDraw(radius, length, mProgram);
		circle=new CircleForDraw(mProgram, radius);
	}
	public void drawSelf(int texBarbetteId[])//0��ʾԲ��,1��ʾԲ��
	{
		MatrixState.pushMatrix();
		cylinder.drawSelf(texBarbetteId[0]);//Բ��
		MatrixState.popMatrix();
		
		MatrixState.pushMatrix();
		MatrixState.rotate(-90, 1, 0, 0);
		MatrixState.translate(0, 0, length/2 );
		circle.drawSelf(texBarbetteId[1]);//�ϰ�Բ
		MatrixState.popMatrix();
		
	}
}
