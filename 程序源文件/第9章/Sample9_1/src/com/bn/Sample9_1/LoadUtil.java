package com.bn.Sample9_1;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.res.Resources;
import android.util.Log;

public class LoadUtil 
{
	//��obj�ļ��м��ؽ�Я��������Ϣ������
    public static LoadedObjectVertexOnly loadFromFile(String fname, Resources r,MySurfaceView mv)
    {
    	LoadedObjectVertexOnly lo=null;
    	
    	ArrayList<Float> alv=new ArrayList<Float>();//ԭʼ���������б�
    	ArrayList<Float> alvResult=new ArrayList<Float>();//������������б�
    	
    	try
    	{
    		InputStream in=r.getAssets().open(fname);
    		InputStreamReader isr=new InputStreamReader(in);
    		BufferedReader br=new BufferedReader(isr);
    		String temps=null;
    		
		    while((temps=br.readLine())!=null)
		    {
		    	String[] tempsa=temps.split("[ ]+");
		      	if(tempsa[0].trim().equals("v"))
		      	{//����Ϊ��������
		      		alv.add(Float.parseFloat(tempsa[1]));
		      		alv.add(Float.parseFloat(tempsa[2]));
		      		alv.add(Float.parseFloat(tempsa[3]));
		      	}
		      	else if(tempsa[0].trim().equals("f"))
		      	{//����Ϊ��������
		      		int index=Integer.parseInt(tempsa[1].split("/")[0])-1;
		      		alvResult.add(alv.get(3*index));
		      		alvResult.add(alv.get(3*index+1));
		      		alvResult.add(alv.get(3*index+2));
		      		
		      		index=Integer.parseInt(tempsa[2].split("/")[0])-1;
		      		alvResult.add(alv.get(3*index));
		      		alvResult.add(alv.get(3*index+1));
		      		alvResult.add(alv.get(3*index+2));
		      		
		      		index=Integer.parseInt(tempsa[3].split("/")[0])-1;
		      		alvResult.add(alv.get(3*index));
		      		alvResult.add(alv.get(3*index+1));
		      		alvResult.add(alv.get(3*index+2));	
		      	}		      		
		    } 
		    
		    //���ɶ�������
		    int size=alvResult.size();
		    float[] vXYZ=new float[size];
		    for(int i=0;i<size;i++)
		    {
		    	vXYZ[i]=alvResult.get(i);
		    }
		    //�����������
		    lo=new LoadedObjectVertexOnly(mv,vXYZ);
    	}
    	catch(Exception e)
    	{
    		Log.d("load error", "load error");
    		e.printStackTrace();
    	}    	
    	return lo;
    }
}
