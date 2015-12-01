package com.bn.zqt.util;

public class ZQTEdgeUtil 
{
	public static float STANDARD_EDGE_LENGTH=2;
	public static float[] ZHU_VECTOR_NORMAL={1,0,0};
	
	public static float[] calTranslateRotateScale(float[] ab)
	{		
		//拆分出AB点坐标
		float xa=ab[0];
		float ya=ab[1];
		float za=ab[2];
		float xb=ab[3];
		float yb=ab[4];
		float zb=ab[5];
		//计算a点到b点的向量  
		float[] abVector={xb-xa,yb-ya,zb-za};
		//规格化AB向量
		float[] normalAB=VectorUtil.vectorNormal(abVector);
		//AB向量cross柱向量  
		float[] normalABCrossZhu=VectorUtil.vectorNormal
		(
			VectorUtil.getCrossProduct
		    (
		    	normalAB[0],normalAB[1],normalAB[2],
		    	ZHU_VECTOR_NORMAL[0], ZHU_VECTOR_NORMAL[1], ZHU_VECTOR_NORMAL[2]
		    )
		);
		//求AB向量与柱向量夹角
		float angle=(float)Math.toDegrees(VectorUtil.angle(normalAB, ZHU_VECTOR_NORMAL));
		//求AB点中点
		float xABZ=(xa+xb)/2;
		float yABZ=(ya+yb)/2;
		float zABZ=(za+zb)/2;
		//求长度缩放值
		float scale=VectorUtil.mould(abVector)/STANDARD_EDGE_LENGTH;		
		final float angleThold=0.8f;		
		//旋转角不应该为0或180
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
