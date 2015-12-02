package com.bn;
import java.io.*;
import java.util.*;
public class LoadUtilOnlyVertex 
{
	//从obj文件中加载仅携带顶点信息的物体
    public static void loadFromFile(String fname)
    {    	
    	ArrayList<Float> alv=new ArrayList<Float>();//原始顶点坐标列表
    	ArrayList<Float> alvResult=new ArrayList<Float>();//结果顶点坐标列表
    	
    	try
    	{
    		InputStream in=new FileInputStream("data/"+fname);
    		InputStreamReader isr=new InputStreamReader(in);
    		BufferedReader br=new BufferedReader(isr);
    		String temps=null;
    		
		    while((temps=br.readLine())!=null)
		    {
		    	String[] tempsa=temps.split("[ ]+");
		      	if(tempsa[0].trim().equals("v"))
		      	{//此行为顶点坐标
		      		alv.add(Float.parseFloat(tempsa[1]));
		      		alv.add(Float.parseFloat(tempsa[2]));
		      		alv.add(Float.parseFloat(tempsa[3]));
		      	}
		      	else if(tempsa[0].trim().equals("f"))
		      	{//此行为三角形面
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
		    
		    //输出到JS文件
		    StringBuilder sb=new StringBuilder();
		    sb.append("var vertexDataFromObj=[\n");		    
		    int size=alvResult.size();		    
		    for(int i=0;i<size;i++)
		    {
		    	sb.append(alvResult.get(i));		    	
		    	if(i!=size-1)
		    	{
		    		sb.append(",");
		    	}
		    	if(i%9==0&&i!=0)
		    	{
		    		sb.append("\n");
		    	}
		    }		    
		    sb.append("];");
		    
		    FileWriter fw=new FileWriter("data/"+fname.replaceFirst("\\..*", "")+".js");	
		    fw.write(sb.toString());
		    fw.close();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}    	
    }
}
