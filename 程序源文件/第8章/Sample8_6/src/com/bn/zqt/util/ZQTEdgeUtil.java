package com.bn.zqt.util;

public class ZQTEdgeUtil 
{
	public static float STANDARD_EDGE_LENGTH=2;
	public static float[] ZHU_VECTOR_NORMAL={1,0,0};
	
	public static float[] calTranslateRotateScale(float[] ab)
	{		
		//��ֳ�AB������
		float xa=ab[0];
		float ya=ab[1];
		float za=ab[2];
		float xb=ab[3];
		float yb=ab[4];
		float zb=ab[5];
		//����a�㵽b�������  
		float[] abVector={xb-xa,yb-ya,zb-za};
		//���AB����
		float[] normalAB=VectorUtil.vectorNormal(abVector);
		//AB����cross������  
		float[] normalABCrossZhu=VectorUtil.vectorNormal
		(
			VectorUtil.getCrossProduct
		    (
		    	normalAB[0],normalAB[1],normalAB[2],
		    	ZHU_VECTOR_NORMAL[0], ZHU_VECTOR_NORMAL[1], ZHU_VECTOR_NORMAL[2]
		    )
		);
		//��AB�������������н�
		float angle=(float)Math.toDegrees(VectorUtil.angle(normalAB, ZHU_VECTOR_NORMAL));
		//��AB���е�
		float xABZ=(xa+xb)/2;
		float yABZ=(ya+yb)/2;
		float zABZ=(za+zb)/2;
		//�󳤶�����ֵ
		float scale=VectorUtil.mould(abVector)/STANDARD_EDGE_LENGTH;		
		final float angleThold=0.8f;		
		//��ת�ǲ�Ӧ��Ϊ0��180
		if(Math.abs(angle)>angleThold&&Math.abs(angle)<180-angleThold){			
			return new float[]{
				xABZ,yABZ,zABZ,
				-angle,normalABCrossZhu[0],normalABCrossZhu[1],normalABCrossZhu[2],
				scale,1,1
			};
		}
		else{			
			return new float[]{
				xABZ,yABZ,zABZ,
				0,0,0,1,
				scale,1,1
			};
		}
	}
}
