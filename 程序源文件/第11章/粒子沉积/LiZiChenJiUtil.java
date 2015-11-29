package com.bn;

import java.util.ArrayList;
import java.util.Collections;

public class LiZiChenJiUtil 
{
	//������������
	@SuppressWarnings("unchecked")
	public static void genCJ
	(
		float[][] result,  	//ִ�г���������
		int cx,				//���ӵ�������ĵ�X����
		int cy,				//���ӵ�������ĵ�Y����
		int count,			//��������
		int span,			//�����뾶
		int gdyzIn,			//�߶���ֵ��Χ
		boolean ssfxjh,		//���������Ƿ����
		boolean sfSmms,		//�Ƿ����ɽ��ģʽ
		int[][] smwz 		//ɽ��λ������
	)
	{
		//���ɿ��ܵ�����λ���б�		
		@SuppressWarnings("rawtypes")
		ArrayList[] knPosition=new ArrayList[span];		
		for(int k=1;k<=span;k++)
		{
			knPosition[k-1]=new ArrayList<int[]>();
			int powerSpan=k*k;
			for(int i=-k;i<=k;i++)
			{
				for(int j=-k;j<=k;j++)
				{
					if(i==0&&j==0) continue;
					if(i*i+j*j<=powerSpan)
					{
						knPosition[k-1].add(new int[]{i,j});
					}
				}
			}
		}						
		
		//��ȡ���ε�����������
		//��Ӧ�Ҷ�ͼ�Ŀ����߶�
		int width=result.length;
		int height=result[0].length;
		
		//ѭ�������ƶ�����������
		for(int i=0;i<count;i++)
		{
			//���ӵ�ǰ����
			int currX=cx;
			int currY=cy;	
			//������ɽ��ģʽ
			if(sfSmms)
			{
				currX=smwz[i%smwz.length][0];
				currY=smwz[i%smwz.length][1];
			}			
			
			//ѭ��̽����Χλ�õĸ߶ȣ�ֱ���ҵ����������ճ�����λ��
			zong:while(true)
			{
				//ȡ��Ŀ��λ�õĸ߶�
				float currHeight=result[currX][currY];	
				//����ڷ�Χ�ڵó��˴������Ĳ���
				int currSpan=(int)Math.ceil(span*Math.random());
				//����ڷ�Χ�ڵó��˴������ĸ߶���ֵ
				int gdyz=(int)(Math.ceil((gdyzIn/2.0)*Math.random()+(gdyzIn/2.0)));
				//���ݴ˴������Ĳ�����ȡ���ܵ�����λ���б�
				ArrayList<int[]> knwz=(ArrayList<int[]>)(knPosition[currSpan-1]);
				//��������������������λ�ô���
				if(ssfxjh)
				{
					Collections.shuffle(knwz);
				}			
				//�Կ��ܵ�λ�ý�������
				for(int[] wz:knwz)
				{
					int j=wz[0];
					int k=wz[1];				
					//�����ܵ�λ�ó�����Χ������˿���λ��
					if(currX+j<0||currX+j>=width||currY+k<0||currY+k>=height)
					{
						continue;
					}			
					//��ȡ�˿���λ�õĸ߶�
					float tempHeight=result[currX+j][currY+k];
					//����ǰλ�õĸ߶������λ�õĸ߶Ȳ���ڸ߶���ֵ
					if(currHeight-tempHeight>gdyz)
					{
						//����ǰλ���ƶ����˿��ܵ�λ��
						currX=currX+j;
						currY=currY+k;
						//����λ�ÿ�ʼ��������
						continue zong;
					}
				}
				//���ҵ��˴��������ճ�����λ�����˳�����ѭ��
				break zong;
			}			
			//�ڴ����ӳ�����λ�ý��߶�ֵ��һ
			result[currX][currY]+=1;			
		}		
	}
}
