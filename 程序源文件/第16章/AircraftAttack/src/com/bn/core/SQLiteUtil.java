package com.bn.core;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteUtil 
{
	static SQLiteDatabase sld;//�������ݿ�
	//����������ݿ�ķ���
    public static void createOrOpenDatabase()
    {
    	try
    	{
	    	sld=SQLiteDatabase.openDatabase
	    	(
	    			"/data/data/com.bn.menu/mydb", //��ǰӦ�ó���ֻ�����Լ��İ��´������ݿ�
	    			null, 								//CursorFactory
	    			SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY //��д�����������򴴽�
	    	);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
  //�ر����ݿ�ķ���
    public static void closeDatabase()
    {
    	try
    	{
	    	sld.close();    
    	}
		catch(Exception e)
		{
            e.printStackTrace();
		}
    }
    //����
    public static void createTable(String sql)
    {
    	createOrOpenDatabase();//�����ݿ�
    	try
    	{
        	sld.execSQL(sql);//����
    	}
		catch(Exception e)
		{
            e.printStackTrace();
		}
    	closeDatabase();//�ر����ݿ�
    }
  //�����¼�ķ���
    public static void insert(String sql)
    {
    	createOrOpenDatabase();//�����ݿ�
    	try
    	{
        	sld.execSQL(sql);
    	}
		catch(Exception e)
		{
            e.printStackTrace();
		}
		closeDatabase();//�ر����ݿ�
    }
    //ɾ����¼�ķ���
    public static  void delete(String sql)
    {
    	createOrOpenDatabase();//�����ݿ�
    	try
    	{
        	sld.execSQL(sql);
      	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		closeDatabase();//�ر����ݿ�
    }
    //�޸ļ�¼�ķ���
    public static void update(String sql)
    {   
    	createOrOpenDatabase();//�����ݿ�
    	try
    	{
        	sld.execSQL(sql);    	
    	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		closeDatabase();//�ر����ݿ�
    }
    //��ѯ�ķ���
    public static ArrayList<String[]> query(String sql)
    {
    	createOrOpenDatabase();//�����ݿ�
    	ArrayList<String[]> al=new ArrayList<String[]>();//�½���Ų�ѯ���������
    	try
    	{
           Cursor cur=sld.rawQuery(sql, new String[]{});
        	while(cur.moveToNext())
        	{
        		int col=cur.getColumnCount();		//����ÿһ�ж������ֶ�
        		String[]temp=new String[col];
        		for( int i=0;i<col;i++)
				{
					temp[i]=cur.getString(i);
				}				
        		al.add(temp);
        	}
        	cur.close();		
    	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		closeDatabase();//�ر����ݿ�
		return al;
    }  
}
