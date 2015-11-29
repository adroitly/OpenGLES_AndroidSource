package com.bn.st.xc;
import static com.bn.st.xc.Constant.*;

import com.bn.core.MatrixState;

//������Ҫ���������Ʒ���
public class HouseForDraw 
{
	//����ĳ����������ƽ�Ƶľ���
	private float floorWidth=60;
	private float floorHeight=60;
	private float floorDownOffset=-WALL_HEIGHT;
	
	//ǽ�Ŀ��   ������ָ  ��ߵ�һ��
	private float wallWidth=WALL_WIDHT;
	private float wallHeight=WALL_HEIGHT;
	private float wall_z_offset=(float) Math.cos((float)(Math.PI/6))*wallWidth*2;
	
	TextureRect floor;//�ذ�
	ColorLightRect wall;//Χǽ
	TextureRect wall_tex;//����ǽ���������ù��
	
	//͸����
	private float alpha1=1.0f;
	private float alpha2=0.3f;
	
	//����չ���ķ���
	public HouseForDraw ()	
	{  
		//��������
		floor=new TextureRect(ShaderManager.getCommTextureShaderProgram(),floorWidth,floorHeight);
		//����Χǽ
		wall=new ColorLightRect(ShaderManager.getColorshaderProgram(),wallWidth,wallHeight,HOUSE_COLOR[1]);
		//�������ù�������ǽ
		wall_tex=new TextureRect(ShaderManager.getCommTextureShaderProgram(),wallWidth,wallHeight);
	}
	//���Ƶذ�ķ���
	public void drawFloor(int texId)
	{
		MatrixState.pushMatrix();
		MatrixState.translate(0, floorDownOffset,0);
    	MatrixState.rotate(-90, 1, 0, 0);
    	floor.drawSelf(texId);
		MatrixState.popMatrix();
	}
	public void drawSelf()//��͸��
	{
		//����Χǽ1
		MatrixState.pushMatrix();
		MatrixState.translate(0, 0,-wall_z_offset);
		wall.drawSelf(alpha1);
		MatrixState.popMatrix();
		//����Χǽ3
		MatrixState.pushMatrix();
		MatrixState.rotate(120, 0,1, 0);
		MatrixState.translate(0,0, -wall_z_offset);
		wall.drawSelf(alpha1);
		MatrixState.popMatrix();
		//����Χǽ5
		MatrixState.pushMatrix();
		MatrixState.rotate(240, 0,1, 0);
		MatrixState.translate(0,0, -wall_z_offset);
		wall.drawSelf(alpha1);
		MatrixState.popMatrix();
	}
	
	//���ƹ��ǽ�ķ���
	public void drawTexWall(int[] texId,int index)
	{
		//����Χǽ2
		MatrixState.pushMatrix();
		MatrixState.rotate(60, 0,1, 0);
		MatrixState.translate(0,0, -wall_z_offset);
		wall_tex.drawSelf(texId[index]);
		MatrixState.popMatrix();
		
		//����Χǽ4
		MatrixState.pushMatrix();
		MatrixState.rotate(180, 0,1, 0);
		MatrixState.translate(0,0, -wall_z_offset);
		wall_tex.drawSelf(texId[index]);
		MatrixState.popMatrix();
		
		//����Χǽ6
		MatrixState.pushMatrix();
		MatrixState.rotate(300, 0,1, 0);
		MatrixState.translate(0,0, -wall_z_offset);
		wall_tex.drawSelf(texId[index]);
		MatrixState.popMatrix();
	}
	
	public void drawTransparentWall()
	{
		//����Χǽ1
		MatrixState.pushMatrix();
		MatrixState.translate(0, 0,-wall_z_offset+0.05f);
		wall.drawSelf(alpha2);
		MatrixState.popMatrix();
		//����Χǽ3
		MatrixState.pushMatrix();
		MatrixState.rotate(120, 0,1, 0);
		MatrixState.translate(0,0, -wall_z_offset+0.05f);
		wall.drawSelf(alpha2);
		MatrixState.popMatrix();
		//����Χǽ5
		MatrixState.pushMatrix();
		MatrixState.rotate(240, 0,1, 0);
		MatrixState.translate(0,0, -wall_z_offset+0.05f);
		wall.drawSelf(alpha2);
		MatrixState.popMatrix();
	}
}
