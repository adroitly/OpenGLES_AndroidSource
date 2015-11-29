package com.bn.st.xc;

import java.util.ArrayList;
import java.util.List;

public class Boat 
{  
	List<LoadedObjectVertexTexXC> lovo=new ArrayList<LoadedObjectVertexTexXC>();
	//strλ�ļ������ƣ�colorλÿһ�����ֶ�Ӧ����ɫ����Ϊ��ά���飩
	public Boat(LoadedObjectVertexTexXC[] parts,XCSurfaceView mv)
	{
		for(int i=0;i<parts.length;i++)  
		{  
			lovo.add(parts[i]);
			parts[i].initShader(ShaderManager.getCommTextureShaderProgram());
		}
	}  
	public void drawSelf(int[] texId)
	{
		for(int i=0;i<lovo.size();i++)
		{
			lovo.get(i).drawSelf(texId[i]);
		}
	}
}