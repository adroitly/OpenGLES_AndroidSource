package com.bn.core;

import android.content.res.Resources;
/*
 * ��shader��������Ҫ�����ڼ���shader�ͱ���shader
 */
public class ShaderManager
{
	final static String[][] shaderName=
	{
		{"vertex_tex_only.sh","frag_tex_only.sh"},//loading �����shader
		{"vertex_tex_water.sh","frag_tex_water.sh"},//ˮ��������shader
		{"vertex_landform.sh","frag_tex_landform.sh"},//���ε�shader
		{"vertex_button.sh","frag_button.sh"},//��ť��shader
		{"vertex_xk.sh","frag_xk.sh"},//�ǿ���ɫ��
		{"vertex_xue.sh","frag_xue.sh"},//Ѫ��ɫ��
		{"vertex_color.sh","frag_color.sh"},//������ɫ��ɫ��
	};
	static String[]mVertexShader=new String[shaderName.length];//������ɫ���ַ�������
	static String[]mFragmentShader=new String[shaderName.length];//ƬԪ��ɫ���ַ�������
	static int[] program=new int[shaderName.length];//��������
	//����loading �����shader
	public static void loadFirstViewCodeFromFile(Resources r)
	{
		mVertexShader[0]=ShaderUtil.loadFromAssetsFile(shaderName[0][0],r);
		mFragmentShader[0]=ShaderUtil.loadFromAssetsFile(shaderName[0][1], r);
	}
	//����shader�ַ���
	public static void loadCodeFromFile(Resources r)
	{
		for(int i=1;i<shaderName.length;i++)
		{
			//���ض�����ɫ���Ľű�����       
	        mVertexShader[i]=ShaderUtil.loadFromAssetsFile(shaderName[i][0],r);
	        //����ƬԪ��ɫ���Ľű����� 
	        mFragmentShader[i]=ShaderUtil.loadFromAssetsFile(shaderName[i][1], r);
		}	
	}
	//������Ҫ�Ǳ���loading�����е�shader
	public static void compileFirstViewShader()
	{
			program[0]=ShaderUtil.createProgram(mVertexShader[0], mFragmentShader[0]);
	}
	//����������shader
	public static void compileShader()
	{
		for(int i=1;i<shaderName.length;i++)
		{
			program[i]=ShaderUtil.createProgram(mVertexShader[i], mFragmentShader[i]);
		}
	}
	//���ﷵ�ص����״μ��ص�shader
	public static int getFirstViewShaderProgram()
	{
		return program[0];
	}
	//���ص���ֻ�������shader����
	public static int getOnlyTextureShaderProgram()
	{
		return program[0];
	}
	//���ﷵ�ص���ˮ��������shader����
	public static int getWaterTextureShaderProgram()
	{
		return program[1];
	}
	//���ﷵ�ص��ǵ��ε�shader
	public static int getLandformTextureShaderProgram()
	{
		return program[2];
	}
	//���ﷵ�ص��ǰ�ť��shader
	public static int getButtonTextureShaderProgram()
	{
		return program[3];
	}
	//���ﷵ���ǿյ���ɫ��shader
	public static int getStarrySkyShaderProgram()
	{
		return program[4];
	}
	//���ﷵ��Ѫ��ɫ��shader
	public static int getStarryXueShaderProgram()
	{
		return program[5];
	}
	//���ﷵ��Ѫ��ɫ��shader
	public static int getOnlyColorShaderProgram()
	{
		return program[6];
	}
}
