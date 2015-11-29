package com.bn.commonObject;

import static com.bn.gameView.GLGameView.cx;
import static com.bn.gameView.GLGameView.cy;
import static com.bn.gameView.GLGameView.cz;

import com.bn.core.MatrixState;

public class Tree implements Comparable<Tree>
{
	public TextureRect rect;
	public float tx,ty,tz;
	public float yAngle;//���ĳ���
	int texId;
	int col,row;
	public Tree(TextureRect rect,float tx,float ty,float tz,int texId,int col,int row)
	{
		this.rect=rect;
		this.tx=tx;
		this.tz=tz;
		this.ty=ty;
		this.texId=texId;
		this.col=col;
		this.row=row;
	}
	public void drawSelf(int ii,int jj,int rowR,int colR){//������
		if(row<ii||row>rowR||col<jj||col>colR)
		{
			return;
		}
		MatrixState.pushMatrix();
		MatrixState.translate(tx, ty, tz);
		MatrixState.rotate(yAngle, 0, 1, 0);
		rect.drawSelf(texId);
		MatrixState.popMatrix();
	}
	//��������־��ĳ���
	public void calculateBillboardDirection()
	{//���������λ�ü�����������泯��
		float currX_span=tx-cx;
		float currZ_span=tz-cz;
		if(currZ_span<0)
		{
			yAngle=(float)Math.toDegrees(Math.atan(currX_span/currZ_span));	
		}
		else if(currZ_span==0)
		{
			yAngle=currX_span>0?90:-90;
		}
		else 
		{
			yAngle=180+(float)Math.toDegrees(Math.atan(currX_span/currZ_span));	
		}
	}
	@Override
	public int compareTo(Tree another) 
	{//��д�ıȽ��������������������ķ���   �Ӵ�С��������
		float x=tx-cx;
		float z=ty-cz;
		float y=tz-cy;
		
		float xo=another.tx-cx;
		float zo=another.ty-cz;
		float yo=another.tz-cy;
		float disA=x*x+y*y+z*z;
		float disB=xo*xo+yo*yo+zo*zo;
		return ((disA-disB)==0)?0:((disA-disB)>0)?-1:1;  
	}
}
