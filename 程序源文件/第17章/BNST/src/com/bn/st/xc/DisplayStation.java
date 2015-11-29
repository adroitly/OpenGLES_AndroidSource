package com.bn.st.xc;
import static com.bn.st.xc.Constant.*;

import com.bn.core.MatrixState;

import android.opengl.GLES20;
/*
 * ���ڻ���չ̨
 */
public class DisplayStation 
{
	CylinderTextureByVertex cylinderStation;//վ̨Բ��
	private float aspan=8;//ˮƽ�з�
	private float lspan=1;//��ֱ�з�
	private float length;
	CircleForDraw circle;//����Բ��
	
	private float alpha1=1.0f;
	private float alpha2=0.5f;
	public DisplayStation(float radius,float length)
	{
		this.length=length;
		cylinderStation=new CylinderTextureByVertex(ShaderManager.getColorshaderProgram(),radius,length,aspan,lspan,COLOR_CYLINDER);
		circle=new CircleForDraw(ShaderManager.getColorshaderProgram(), aspan, radius,COLOR_CIRCLE);
	}
	//���������Բ����
	public void drawSelfCylinder()
	{
		GLES20.glDisable(GLES20.GL_CULL_FACE);
		cylinderStation.drawSelf(alpha1);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
	}
	//���������Բ��
	public void drawSelfCircle()
	{
		MatrixState.pushMatrix();
		MatrixState.translate(0, length/2, 0);
		MatrixState.rotate(-90, 1, 0, 0);
		circle.drawSelf(alpha1);
		MatrixState.popMatrix();
	}
	//��͸����Բ��
	public void drawTransparentCircle()
	{
		MatrixState.pushMatrix();
		MatrixState.translate(0, -Constant.HOUSE_GAO/2+length/2,0);
		MatrixState.rotate(-90, 1, 0, 0);
		circle.drawSelf(alpha2);
		MatrixState.popMatrix();
	}
}
