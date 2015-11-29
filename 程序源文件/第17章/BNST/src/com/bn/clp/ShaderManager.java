package com.bn.clp;
import com.bn.core.ShaderUtil;

import android.content.res.Resources;

public class ShaderManager
{
	final static int shaderCount=7;
	final static String[][] shaderName=
	{
		{"vertex_color_light.sh","frag_color_light.sh"},
		{"vertex_b_yz.sh","frag_b_yz.sh"},
		{"vertex_tex_g.sh","frag_tex_g.sh"},
		{"vertex_tex_xz.sh","frag_tex_xz.sh"},
		{"vertex_mountion.sh","frag_mountion.sh"},
		{"vertex_prograss.sh","frag_prograss.sh"},
		{"vertex_tex_xz.sh","frag_weilang.sh"}
	};
	static String[]mVertexShader=new String[shaderCount];
	static String[]mFragmentShader=new String[shaderCount];
	static int[] program=new int[shaderCount];
	
	public static void loadCodeFromFile(Resources r)
	{
		for(int i=0;i<shaderCount;i++)
		{  
			//���ض�����ɫ���Ľű�����       
	        mVertexShader[i]=ShaderUtil.loadFromAssetsFile(shaderName[i][0],r);
	        //����ƬԪ��ɫ���Ľű����� 
	        mFragmentShader[i]=ShaderUtil.loadFromAssetsFile(shaderName[i][1], r);
		}	
	}
	//������Ҫ�Ǳ���shader
	public static void compileShader()
	{		
		program[0]=ShaderUtil.createProgram(mVertexShader[0], mFragmentShader[0]);
		program[1]=ShaderUtil.createProgram(mVertexShader[1], mFragmentShader[1]);
		program[3]=ShaderUtil.createProgram(mVertexShader[3], mFragmentShader[3]);
		program[4]=ShaderUtil.createProgram(mVertexShader[4], mFragmentShader[4]);
		program[6]=ShaderUtil.createProgram(mVertexShader[6], mFragmentShader[6]);
	}
	
	public static void compileShaderHY()
	{
		program[2]=ShaderUtil.createProgram(mVertexShader[2], mFragmentShader[2]);
		program[5]=ShaderUtil.createProgram(mVertexShader[5], mFragmentShader[5]);
	}
	
	
	//���ﷵ�ص������ù��յ�Shader����
	public static int getLightShaderProgram()
	{
		return program[0];
	}
	//���ﷵ�ص��ǰ����ӵ�Shader����
	public static int getBYZTextureShaderProgram()
	{
		return program[1];
	}
	//���ﷵ�ص���ֻ�������Shader����
	public static int getTextureShaderProgram()
	{
		return program[2];
	}
	//���ﷵ�ص��ǻ���ˮ��ʱ�õ���Shader����
	public static int getWaterShaderProgram()
	{
		return program[3];
	}
	//���ﷵ�ػ���ɽʱ�õ���Shader����
	public static int getMountionShaderProgram()
	{
		return program[4];
	}
	//���ﷵ�ص��ǽ�������Shader����
	public static int getPrograssShaderProgram()
	{
		return program[5];
	}  
	//����β�˵�shader����
	public static int getWeiLangShaderProgram()
	{
		return program[6];
	}
}