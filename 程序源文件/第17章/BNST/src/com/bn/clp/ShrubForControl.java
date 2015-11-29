package com.bn.clp;

import static com.bn.clp.MyGLSurfaceView.*;

//��ʾ��ľ��Ŀ�����
public class ShrubForControl implements Comparable<ShrubForControl>
{
    int rows;
	int cols;
	float xoffset;
	float yoffset;
	float zoffset;
	int id;
	int texIds;
    
    public ShrubForControl(int rows,int cols,float xoffset,float yoffset,float zoffset,int id,int texIds)
    {  
    	this.rows=rows;
    	this.cols=cols;
    	this.xoffset=xoffset;
    	this.yoffset=yoffset;
    	this.zoffset=zoffset;
    	this.id=id;
    	this.texIds=texIds;
    }

	@Override
	public int compareTo(ShrubForControl another) 
	{
		//��д�ıȽ�������ľ�����������ķ���
		float xs=xoffset-cxForSpecFrame;
		float zs=zoffset-czForSpecFrame;
		
		float xo=another.xoffset-cxForSpecFrame;
		float zo=another.zoffset-czForSpecFrame;
		
		float disA=(float)Math.sqrt(xs*xs+zs*zs);
		float disB=(float)Math.sqrt(xo*xo+zo*zo);
		
		return ((disA-disB)==0)?0:((disA-disB)>0)?-1:1;  
	}
}
