package com.bn.commonObject;
import com.bn.core.MatrixState;

/*	���Ƴ�����,�����ĵ�λ��ԭ�� ���г�ƽ����X��,��ƽ����Z��,��ƽ����Y��
*	�ֱ��ɳ������ָ��
*/
public class CubeForDraw 
{
	//�������������
	TextureRect sideXY;//ǰ��
	TextureRect sideYZ;//����
	TextureRect sideXZ;//����
	float length;
	float width;
	float height;
    public CubeForDraw
    (
    	float length,
    	float width,
    	float height,
    	int mProgram
    )
    {
    	this.length=length;
    	this.width=width;
    	this.height=height;
    	sideXY=new TextureRect( length, height,mProgram);//����ǰ��
    	sideYZ=new TextureRect( width, height,mProgram);//��������
    	sideXZ=new TextureRect(length, width, mProgram);//��������
    }
    public void drawSelf(int texId)
    {        
    	//����ǰ��
    	MatrixState.pushMatrix();
    	MatrixState.translate(0, 0,width/2);
    	sideXY.drawSelf(texId);
    	MatrixState.popMatrix();
    	//���ƺ���
    	MatrixState.pushMatrix();
    	MatrixState.rotate(180, 0, 1, 0);
    	MatrixState.translate(0, 0, width/2);
    	sideXY.drawSelf(texId);
    	MatrixState.popMatrix();
    	//��������
    	MatrixState.pushMatrix();
    	MatrixState.rotate(-90, 0, 1, 0);
    	MatrixState.translate(0,0, length/2);
    	sideYZ.drawSelf(texId);
    	MatrixState.popMatrix();
    	//��������
    	MatrixState.pushMatrix();
    	MatrixState.rotate(90, 0, 1, 0);
    	MatrixState.translate(0,0, length/2);
    	sideYZ.drawSelf(texId);
    	MatrixState.popMatrix();
    	//��������
    	MatrixState.pushMatrix();
    	MatrixState.rotate(-90, 1, 0, 0);
    	MatrixState.translate(0,0, height/2);
    	sideXZ.drawSelf(texId);
    	MatrixState.popMatrix();
    	//��������
    	MatrixState.pushMatrix();
    	MatrixState.rotate(90, 1, 0, 0);
    	MatrixState.translate(0,0, height/2);
    	sideXZ.drawSelf(texId);
    	MatrixState.popMatrix();
    }
    public void drawSelf(int texId,int texId1)
    {        
    	//����ǰ��
    	MatrixState.pushMatrix();
    	MatrixState.translate(0, 0,width/2);
    	sideXY.drawSelf(texId);
    	MatrixState.popMatrix();
    	//���ƺ���
    	MatrixState.pushMatrix();
    	MatrixState.rotate(180, 0, 1, 0);
    	MatrixState.translate(0, 0, width/2);
    	sideXY.drawSelf(texId);
    	MatrixState.popMatrix();
    	//��������
    	MatrixState.pushMatrix();
    	MatrixState.rotate(-90, 0, 1, 0);
    	MatrixState.translate(0,0, length/2);
    	sideYZ.drawSelf(texId);
    	MatrixState.popMatrix();
    	//��������
    	MatrixState.pushMatrix();
    	MatrixState.rotate(90, 0, 1, 0);
    	MatrixState.translate(0,0, length/2);
    	sideYZ.drawSelf(texId);
    	MatrixState.popMatrix();
    	//��������
    	MatrixState.pushMatrix();
    	MatrixState.rotate(-90, 1, 0, 0);
    	MatrixState.translate(0,0, height/2);
    	sideXZ.drawSelf(texId1);
    	MatrixState.popMatrix();   
    }
}
