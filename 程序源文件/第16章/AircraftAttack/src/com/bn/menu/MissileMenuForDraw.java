package com.bn.menu;
import android.opengl.GLES20;

import com.bn.commonObject.BallTextureByVertex;
import com.bn.commonObject.CylinderForDraw;
import com.bn.commonObject.TextureRect;
import com.bn.core.MatrixState;
/*
 * ��Ҫ���ڻ��Ƶ����˵�
 */
public class MissileMenuForDraw
{
	private TextureRect rect;//�������
	private BallTextureByVertex halfBall;//��ͷ�õİ���
	private CylinderForDraw cylinder;//Բ��
	private TextureRect rect_tail;//�������
	private float rect_width=0.8f;//������εĿ��
	private float rect_height=2.5f;//������εĸ߶�
	private float tail_width=2.3f;//β��Ŀ��
	private float tail_height=1.2f;//β��ĸ߶�
	private float rect_offset=(float) (rect_width/2/Math.tan(Math.toRadians(22.5f)));//���������Z�Ḻ�����ƫ����
	private float radius=(float) (rect_width/2/Math.sin(Math.toRadians(22.5f)));//�����Բ���İ뾶
	private float halfBall_span=rect_height/2;//�����ƫ����
	private float cylinder_length=0.2f;//Բ���ĳ���
	public MissileMenuForDraw(int mProgram) 
	{
		rect=new TextureRect(rect_width, rect_height, mProgram);//����������ζ���
		halfBall=new BallTextureByVertex(radius, mProgram, 0);//��������
		cylinder=new CylinderForDraw(radius, cylinder_length, mProgram); //����Բ��
		rect_tail=new TextureRect( tail_width, tail_height,mProgram);//������������ڵ�β��
	}
	public void drawSelft(int[]tex_RectId)
	{
		//------------------------���Ƶ����м䲿��
		MatrixState.pushMatrix();
		//���Ƶ�һ����
		MatrixState.pushMatrix();
		MatrixState.translate(0, 0, rect_offset);
		rect.drawSelf(tex_RectId[0]);
		MatrixState.popMatrix();
		//���Ƶڶ�����
		MatrixState.pushMatrix();
		MatrixState.rotate(45, 0, 1, 0);
		MatrixState.translate(0, 0, rect_offset);
		rect.drawSelf(tex_RectId[1]);
		MatrixState.popMatrix();
		//���Ƶ�������
		MatrixState.pushMatrix();
		MatrixState.rotate(90, 0, 1, 0);
		MatrixState.translate(0, 0, rect_offset);
		rect.drawSelf(tex_RectId[2]);
		MatrixState.popMatrix();
		//���Ƶ��ĸ���
		MatrixState.pushMatrix();
		MatrixState.rotate(135, 0, 1, 0);
		MatrixState.translate(0, 0, rect_offset);
		rect.drawSelf(tex_RectId[3]);
		MatrixState.popMatrix();
		//���Ƶ������
		MatrixState.pushMatrix();
		MatrixState.rotate(180, 0, 1, 0);
		MatrixState.translate(0, 0, rect_offset);
		rect.drawSelf(tex_RectId[4]);
		MatrixState.popMatrix();
		//���Ƶ�������
		MatrixState.pushMatrix();
		MatrixState.rotate(225, 0, 1, 0);
		MatrixState.translate(0, 0, rect_offset);
		rect.drawSelf(tex_RectId[5]);
		MatrixState.popMatrix();
		//���Ƶ��߸���
		MatrixState.pushMatrix();
		MatrixState.rotate(270, 0, 1, 0);
		MatrixState.translate(0, 0, rect_offset);
		rect.drawSelf(tex_RectId[6]);
		MatrixState.popMatrix();
		//���Ƶڰ˸���
		MatrixState.pushMatrix();
		MatrixState.rotate(315, 0, 1, 0);
		MatrixState.translate(0, 0, rect_offset);
		rect.drawSelf(tex_RectId[7]);
		MatrixState.popMatrix();
		MatrixState.popMatrix();
		//------------------------���Ƶ���ͷ����
		MatrixState.pushMatrix();
		MatrixState.translate(0, halfBall_span, 0);
		halfBall.drawSelf(tex_RectId[8]);
		MatrixState.translate(0, -cylinder_length/2, 0);
		cylinder.drawSelf(tex_RectId[9]);
		MatrixState.popMatrix();
		//------------------------���Ƶ���β����
		MatrixState.pushMatrix();
		MatrixState.translate(0, -halfBall_span, 0);
		MatrixState.rotate(180, 0, 0, 1);
		halfBall.drawSelf(tex_RectId[8]);
		MatrixState.translate(0, -cylinder_length/2, 0);
		cylinder.drawSelf(tex_RectId[9]);
		MatrixState.popMatrix();
		//������Ҷ
		GLES20.glDisable(GLES20.GL_CULL_FACE);
		MatrixState.pushMatrix();
		MatrixState.translate(0, -halfBall_span-tail_height/2, 0);
		rect_tail.drawSelf(tex_RectId[10]);
		MatrixState.rotate(120, 0, 1, 0);
		rect_tail.drawSelf(tex_RectId[10]);
		MatrixState.rotate(120, 0, 1, 0);
		rect_tail.drawSelf(tex_RectId[10]);
		MatrixState.popMatrix();
		GLES20.glEnable(GLES20.GL_CULL_FACE);
	}
}
