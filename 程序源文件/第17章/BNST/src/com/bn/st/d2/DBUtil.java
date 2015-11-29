package com.bn.st.d2;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//���ݿ⹤����
public class DBUtil
{
	static SQLiteDatabase sld;
	//�򿪻򴴽����ݿ�
	public static void createorOpenDatabase()
	{
		try
		{
			sld=SQLiteDatabase.openDatabase
	    	(
    			"/data/data/com.bn/mydb", //���ݿ�����·��
    			null, 								//CursorFactory
    			SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY //��д�����������򴴽�
	    	);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	//�ر����ݿ�
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
	//������
	public static void createTable()
	{
		createorOpenDatabase();
		try
		{
	    	String jsSql="create table if not exists jsRecord" +
						 "(" +
						 	 "id INTEGER PRIMARY KEY AUTOINCREMENT," +
							 "date char(20)," +
							 "time char(10)" +
						 ")"; 
	    	String rcRSql="create table if not exists jRecord" + 
						 "("+
						 	"id INTEGER PRIMARY KEY AUTOINCREMENT," +
						 	"date char(20)," +
							"time char(10)," +
							"ranking Integer"+
						 ")";
	    	sld.execSQL(jsSql);
	    	sld.execSQL(rcRSql);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		closeDatabase();
	}
	//���뵽��ʱ�����ݿ�ķ���
	public static void insertJSDatabase(String date,String time)
	{
		createorOpenDatabase();
		try
		{
			String sql="insert into jsRecord values(null, '"+date+"','"+time+"');";
			sld.execSQL(sql);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		closeDatabase();
	}
	//���뵽��ʱ�����ݿ�ķ���
	public static void insertRcRDatabase(String date,String time,int ranking)
	{
		createorOpenDatabase();
		try
		{
			String sql="insert into jRecord values(null, '"+date+"','"+time+"','"+ranking+"');";
			sld.execSQL(sql);
		}
		catch(Exception e)
		{ 
			e.printStackTrace();
		}
		closeDatabase(); 
	}
	//��ѯ���ݿ�
	public static List<String> queryDatabase(String tableName)
	{
		createorOpenDatabase();
		List<String> alist=new ArrayList<String>();
		try
		{
			String sql="select * from "+tableName;
			Cursor cur=sld.rawQuery(sql, new String[]{});
			while(cur.moveToNext())
			{
				int tempCount=cur.getColumnCount();
				for(int i=1;i<tempCount;i++)
				{
					alist.add(cur.getString(i));
				}
			}
			cur.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		closeDatabase();
		return alist;
	}
	//ɾ����
	public static void dropDatabase()
	{
		createorOpenDatabase();
		try
		{
			String sql="drop table jsRecord;";
			String sql0="drop table jRecord;";
			sld.execSQL(sql);
			sld.execSQL(sql0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		closeDatabase();
	}
	//��ѯ��ʱģʽ���е���ʱ
	public static List<String> getTimeFromJSDatabase()
	{
		createorOpenDatabase();
		List<String> alist=new ArrayList<String>();
		try
		{
			String sql="select time from jsRecord;";
			Cursor cur=sld.rawQuery(sql, new String[]{});
			while(cur.moveToNext())
			{ 
				alist.add(cur.getString(0));
			}
			cur.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		closeDatabase();
		return alist;
	}
}