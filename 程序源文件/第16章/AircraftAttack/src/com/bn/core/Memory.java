package com.bn.core;
//�����ڴ��������,����ֻ���ǵ��Ƕ��������Ķ��ڴ�ռ�
public class Memory 
{
	public static long used()
	{
		long total=Runtime.getRuntime().totalMemory();
		long free=Runtime.getRuntime().freeMemory();
		return total-free;
	}
}
