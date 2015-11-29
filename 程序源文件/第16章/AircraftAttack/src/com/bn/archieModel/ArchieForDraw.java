package com.bn.archieModel;
import static com.bn.gameView.Constant.*;

import com.bn.commonObject.*;
import com.bn.core.MatrixState;
/*
 * ���Ƹ�����
 * ����ԭ��λ����̨�ļ������Ĵ�
 * ��ô ���������ľ���Ϊ  ����ĸ߶ȵ�һ�����̨�߶ȵ�һ��,���ҵ�ƫ����Ϊ�ڹܵİ뾶
 * �ڹ���Ͷ˵�λ��Ϊ�����ƶ��ľ���Ϊ  : ��̨�߶�һ��+�ڹܳ���һ��
 */
public class ArchieForDraw    
{
	BarrelForDraw barrel;//�ڹ�
	BarbetteForDraw barbette;//��̨
	CubeForDraw cube;//��������
	public float barrel_elevation=30;//�ڹܵ�����
	public float barrel_direction=0;//�ڹܵķ����
	
	float barrel_down_X=0;//�ڹܵ׶˵�����
	public float barrel_down_Y=barbette_length/2+cube_height/2;
	float barrel_down_Z=0;
	
	public float barrel_curr_X;//�ڹܼ������ĵ�X����
	public float barrel_curr_Y;//�ڹܼ������ĵ�Y����
	public float barrel_curr_Z;//�ڹܼ������ĵ�Z����
	
	public ArchieForDraw(BarrelForDraw barrel,BarbetteForDraw barbette,CubeForDraw cube)
	{
		//�����ڹ�
		this.barrel=barrel;
		this.barbette=barbette;
		this.cube=cube;
	}
	public void drawSelf(int texBarbetteId[],int texCubeId,int texBarrelId[])
	{
		//��������ڹܵ���̬
		barrel_curr_Y=(float) (barrel_down_Y+Math.sin(Math.toRadians(barrel_elevation))*barrel_length/2);//�ڹܵ�Y����
		barrel_curr_Z=(float) (barrel_down_Z-Math.cos(Math.toRadians(barrel_elevation))*barrel_length/2);//�ڹܵ�Z����
		MatrixState.pushMatrix();
		MatrixState.rotate(barrel_direction,0, 1, 0);
		//������̨
		MatrixState.pushMatrix();
		barbette.drawSelf(texBarbetteId);
		MatrixState.popMatrix();
		//�����󵲰�
		MatrixState.pushMatrix();
		MatrixState.translate(-barrel_radius-cube_length/2, cube_height/2+barbette_length/2, 0);
		cube.drawSelf(texCubeId);
		MatrixState.popMatrix();
		//�����ҵ���
		MatrixState.pushMatrix();
		MatrixState.translate(barrel_radius+cube_length/2, cube_height/2+barbette_length/2, 0);
		cube.drawSelf(texCubeId);
		MatrixState.popMatrix();
		//�����ڹ�
		MatrixState.pushMatrix();
		//���ƶ�
		MatrixState.translate(0, barrel_curr_Y, barrel_curr_Z);
		//����ת
		MatrixState.rotate(barrel_elevation-90, 1, 0, 0);
		barrel.drawSelf(texBarrelId);
		MatrixState.popMatrix();
		MatrixState.popMatrix();
	}
}
