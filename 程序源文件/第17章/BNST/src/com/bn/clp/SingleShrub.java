package com.bn.clp;

import static com.bn.clp.Constant.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.bn.core.MatrixState;

//��ʾ������ľ����
public class SingleShrub
{
	//������б�
	List<float []> all=new ArrayList<float []>();
	//���ڻ��Ƹ�������������
	ShrubForDraw sfd;
	
	//�������飬ÿ����������������ܲ�ͬ
	float[] texList=
	{
		 0,0, 0,1, 0.5f,0,
 	     0.5f, 0,0,1, 0.5f,1
	};
	
	public SingleShrub(int programId)
	{		
		//��ʼ�����ڻ��Ƶ�����ľ�Ķ���
    	sfd=new ShrubForDraw
    	(
    		programId,
    		texList
    	);  
	}
		
	public void drawSelf(int texIds,int id,float x,float y,float z,int dyFlag)
	{		
    	//��������������Ϣ
		float[][] tempData=
		{
			{x,z,0},
			{x+GRASS_UNIT_SIZE/2, z+GRASS_UNIT_SIZE*0.866f, 60},
			{x+GRASS_UNIT_SIZE*1.5f, z+GRASS_UNIT_SIZE*0.866f, 120},
			{x+GRASS_UNIT_SIZE*2, z, 180},
			{x+GRASS_UNIT_SIZE*1.5f, z-GRASS_UNIT_SIZE*0.866f, 240},
			{x+GRASS_UNIT_SIZE/2, z-GRASS_UNIT_SIZE*0.866f, 300}
		};		
		all=Arrays.asList(tempData);
				
		//�������������
		Collections.sort(all, new MyComparable());
		
		if(dyFlag==0)//����ʵ�� 
		{
			//ѭ���б������ľ�Ļ���  
			for(float []tt:all)
			{
				MatrixState.pushMatrix();
				MatrixState.translate(tt[0], y, tt[1]);
				MatrixState.rotate(tt[2], 0, 1, 0);
				sfd.drawSelf(texIds);		
				MatrixState.popMatrix();   
			}
		}
		else if(dyFlag==1)//���Ƶ�Ӱ
		{
			//ʵ�ʻ���ʱY�����============!!!!!!!!!!!????????????
			float yTranslate=y;
			//���о������ʱ�ĵ���ֵ
			float yjx=(0-yTranslate)*2;
			
			//ѭ���б������ľ�Ļ���
			for(float []tt:all)
			{
				MatrixState.pushMatrix();				
				MatrixState.translate(tt[0], y, tt[1]);
				MatrixState.rotate(tt[2], 0, 1, 0);
				MatrixState.translate(0, yjx, 0);
				MatrixState.scale(1, -1, 1);
				sfd.drawSelf(texIds);		
				MatrixState.popMatrix();   
			}			
		}
	}
}