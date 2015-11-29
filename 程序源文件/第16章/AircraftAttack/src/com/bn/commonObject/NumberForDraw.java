package com.bn.commonObject;
import com.bn.core.MatrixState;
//����0-9ʮ������
public class NumberForDraw 
{
	//����10�����ֵ��������
	public TextureRect[] number;
	String scoreStr;//�����ַ���
	float width;
	float height;
	public NumberForDraw(int numberSize,float width,float height,int mProgram)
	{
		number=new TextureRect[numberSize];
		this.width=width;
		this.height=height;
		//����ʮ�����ֵ��������
		for(int i=0;i<numberSize;i++)
		{
			number[i]=new TextureRect
            (
            		width,
            		height,
        		new float[]
	            {
	           	  1f/numberSize*i,0,1f/numberSize*i,1, 1f/numberSize*(i+1),0,
	           	  1f/numberSize*(i+1),1,
	            },
	            mProgram
             ); 
		}
	}
	public void drawSelf(String score,int texId)//�������ֺ���������
	{		
		scoreStr=score;
		MatrixState.pushMatrix();
		MatrixState.translate(-scoreStr.length()*width, 0, 0);
		for(int i=0;i<scoreStr.length();i++)//���÷��е�ÿ�������ַ�����
		{
			char c=scoreStr.charAt(i);
	        MatrixState.translate(width, 0, 0);
	        number[c-'0'].drawSelf(texId);		         
		}
		MatrixState.popMatrix();	
	}
	public void drawSelfLeft(String score,int texId)//�������ֺ��������� �����
	{		
		scoreStr=score;
		MatrixState.pushMatrix();
		for(int i=0;i<scoreStr.length();i++)//���÷��е�ÿ�������ַ�����
		{
			char c=scoreStr.charAt(i);
	        MatrixState.translate(width, 0, 0);
	        number[c-'0'].drawSelf(texId);		         
		}
		MatrixState.popMatrix();	
	}
}