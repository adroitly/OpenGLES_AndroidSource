package com.bn.gameView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.bn.archieModel.ArchieForControl;
import com.bn.arsenal.Arsenal_House;
import com.bn.menu.R;
import com.bn.tankemodel.TanKe;

public class Constant 
{	
	public static int isMusicOn=0;//��������   0��ʾ����,1��ʾ�ر�/
	public static int isSoundOn=0;//��Ч����   0��ʾ����,1��ʾ�ر�
	public static int isVibrateOn=0;//��Ч��   0��ʾ����,1��ʾ�ر�
	//���õ����˵��Ŀ�Ⱥ͸߶�
	public static float MENU_BUTTON_WIDTH; 
	public static float MENU_BUTTON_HEIGHT;  
	//���õ����˵���ƫ����
	public static final float MENU_BUTTON_XOffset=0;
	public static final float MENU_BUTTON_YOffset=0;
	//�����˵��ķ�Χ
	public static float[] MENU_BUTTON_AREA;
	//�����ǵĿ�Ⱥ͸߶�
	public static float MENU_DOOR_WIDTH; 
	public static float MENU_DOOR_HEIGHT;  
	//����ҳ�水ť�Ŀ�Ⱥ͸߶�
	public static float SETTING_BUTTON_WIDTH; 
	public static float SETTING_BUTTON_HEIGHT;  
	//����ҳ�水ť��ƫ����1
	public static float SETTING_BUTTON_XOffset1=0;
	public static float SETTING_BUTTON_YOffset1=0.5f;
	//����ҳ�水ť�ķ�Χ1
	public static float[] SETTING_BUTTON_AREA1;
	//����ҳ�水ť��ƫ����2
	public static float SETTING_BUTTON_XOffset2=0;
	public static float SETTING_BUTTON_YOffset2=0;
	//����ҳ�水ť�ķ�Χ2
	public static float[] SETTING_BUTTON_AREA2;
	//����ҳ�水ť��ƫ����3
	public static float SETTING_BUTTON_XOffset3=0;
	public static float SETTING_BUTTON_YOffset3=-0.5f;
	//����ҳ�水ť�ķ�Χ3
	public static float[] SETTING_BUTTON_AREA3;
	//�˳��Ի���Ŀ�Ⱥ͸߶� ������ͷ�Ͱ�ť
	public static float EXIT_DIALOG_WIDTH; 
	public static float EXIT_DIALOG_HEIGHT;  
	//ȷ����ť�ķ�Χ
	public static float DIALOG_BUTTON_WIDTH; 
	public static float  DIALOG_BUTTON_HEIGHT;
	public static float DIALOG_YES_XOffset;
	public static float DIALOG_YES_YOffset;
	public static float[] DIALOG_BUTTON_YES;
	//���ذ�ť�ķ�Χ
	public static float DIALOG_NO_XOffset;
	public static float DIALOG_NO_YOffset;
	public static float[] DIALOG_BUTTON_NO;
	//��������Ŀ�Ⱥ͸߶�
	public static float HELP_WIDTH; 
	public static float HELP_HEIGHT;  
	//���ڽ���Ŀ�Ⱥ͸߶�
	public static float ABOUT_WIDTH; 
	public static float ABOUT_HEIGHT;  
	
	//-------------�����˵�---------------------------
	//�����еı�ͷ
	public static float PLANE_SELECT_HEAD_WIDTH;
	public static float PLANE_SELECT_HEAD_HEIGHT;
	//ѡ�ɻ���ť
	public static float PLANE_SELECT_PLANE_WIDTH;
	public static float PLANE_SELECT_PLANE_HEIGHT;
	public static float PLANE_BTN_XOffset;//��ť��ƫ����
	public static float PLANE_BTN_YOffset;
	public static float[] PLANE_SELECT_PLANE;//��ť�ķ�Χ
	
	
	//-----��Ϸģʽ��ť
	public static float MENU_TWO_GAME_MODEL_BUTTON_WIDTH;
	public static float MENU_TWO_GAME_MODEL_BUTTON_HEIGHT;
	//------ս��ģʽ��ť
	public static float MENU_TWO_WAR_BUTTON_XOffset;//��ť��ƫ����
	public static float MENU_TWO_WAR_BUTTON_YOffset;
	public static float[] MENU_TWO_WAR_BUTTON_AREA;//��ť�ķ�Χ
	//------�ر��ж���ť
	public static float MENU_TWO_ACTION_BUTTON_XOffset;//��ť��ƫ����
	public static float MENU_TWO_ACTION_BUTTON_YOffset;
	public static float[] MENU_TWO_ACTION_BUTTON_AREA;//��ť�ķ�Χ
	
	
	//------�˵����а�ť�Ŀ�Ⱥ͸߶�
	public static float MENU_TWO_BUTTON_WIDTH;
	public static float MENU_TWO_BUTTON_HEIGHT;
	//--------ȷ����ť�ķ�Χ
	public static float MENU_TWO_BUTTON_OK_XOffset;//��ť��ƫ����
	public static float MENU_TWO_BUTTON_OK_YOffset;
	public static float[] MENU_TWO_BUTTON_OK_AREA;//��ť�ķ�Χ
	//--------�󰴰�ť�ķ�Χ
	public static float MENU_TWO_BUTTON_LEFT_XOffset;//��ť��ƫ����
	public static float MENU_TWO_BUTTON_LEFT_YOffset;
	public static float[] MENU_TWO_BUTTON_LEFT_AREA;//��ť�ķ�Χ
	//--------�Ұ���ť�ķ�Χ
	public static float MENU_TWO_BUTTON_RIGHT_XOffset;//��ť��ƫ����
	public static float MENU_TWO_BUTTON_RIGHT_YOffset;
	public static float[] MENU_TWO_BUTTON_RIGHT_AREA;//��ť�ķ�Χ
	//----�����˵��еķɻ�ͼƬ��Ⱥ͸߶�
	public static float MENU_TWO_PLANE_ICON_WIDTH;
	public static float MENU_TWO_PLANE_ICON_HEIGHT;
	
	public static float MENU_TWO_PLANE_ICON_ONE_XOffset;//��ť��ƫ����
	public static float MENU_TWO_PLANE_ICON_ONE_YOffset;
	public static float[] MENU_TWO_PLANE_ICON_ONE_AREA;//��ť�ķ�Χ
	public static float MENU_TWO_PLANE_ICON_TWO_XOffset;//��ť��ƫ����
	public static float MENU_TWO_PLANE_ICON_TWO_YOffset;
	public static float[] MENU_TWO_PLANE_ICON_TWO_AREA;//��ť�ķ�Χ
	public static float MENU_TWO_PLANE_ICON_THREE_XOffset;//��ť��ƫ����
	public static float MENU_TWO_PLANE_ICON_THREE_YOffset;
	public static float[] MENU_TWO_PLANE_ICON_THREE_AREA;//��ť�ķ�Χ
	
	
	//-------��Ϸ��˵�����ֵĿ�Ⱥ͸߶�
	public static float NOTICE_WIDTH;
	public static float NOTICE_HEIGHT;
	//----------------------------------------------------------
	
	//-----�����˵�------��ͼѡ����水ť�Ŀ�Ⱥ͸߶�
	public static float MAP_BUTTON_WIDTH; 
	public static float MAP_BUTTON_HEIGHT;  
	//��һ�صķ�Χ
	public static float MAP_ONE_XOffset;
	public static float MAP_ONE_YOffset;
	public static float[] MAP_ONE_AREA;
	//�ڶ��صķ�Χ
	public static float MAP_TWO_XOffset;
	public static float MAP_TWO_YOffset;
	public static float[] MAP_TWO_AREA;
	//�����صķ�Χ
	public static float MAP_THREE_XOffset;
	public static float MAP_THREE_YOffset;
	public static float[] MAP_THREE_AREA;
	
	//��ͼѡ����水ť�Ŀ�Ⱥ͸߶�
	public static float MAP_WORD_WIDTH; 
	public static float MAP_WORD_HEIGHT; 
	public static float WORD_YOffset;
	
	
	//���а�����ͼ�Ŀ�Ⱥ͸߶�
	public static float RANK_MAP_WIDTH; 
	public static float RANK_MAP_HEIGHT;  
	
	//���а�������ֿ�Ⱥ͸߶�
	public static float RANK_NUMBER_WIDTH; 
	public static float RANK_NUMBER_HEIGHT;  
	
	public static final int ARCHIBALD_TIME=1;//�����ڱ������ߴ�����ը
	public static final float ARCHIBALD_BOX_X=8;//�����ڵİ�Χ�д�С
	public static final float ARCHIBALD_BOX_Y=12;
	public static final float ARCHIBALD_BOX_Z=8;
	
	public static final int CELL_SIZE=15;//�Ź����һ�ߵĸ���
	public static final int TANKE_SIZE=15;//����̹�˵ȸ�������ľŹ�����
	public static final int MapArray[][][]=new int[][][]
    {
		//��һ��
		{
			{14,8 ,5 ,7 ,14,	14,14,14,14,14,	14,14,14,14,14,	14,14,14,14,14,},
			{14,6 ,13,10,7 ,	14,14,14,14,14,	14,14,14,14,14,	14,14,14,14,14,},//0
			{8 ,11,13,2 ,1 ,	14,14,14,14,14,	14,14,14,14,14,	14,14,14,14,14,},//1
			{9 ,12,13,4 ,14,	14,14,14,14,14,	8 ,7 ,14,14,14,	14,14,14,14,14,},//2
			{14,9 ,0 ,1 ,14,	14,14,14,14,14,	6 ,10,5 ,7 ,14,	14,14,14,14,14,},//3
			
			{14,14,14,14,14,	14,14,14,14,8 ,	11,13,13,10,7 ,	14,14,14,14,14,},//
			{14,14,14,8 ,5 ,	5 ,5 ,5 ,5 ,11,	13,13,13,13,10,	5 ,5 ,7 ,14,14,},
			{14,14,8 ,11,13,	13,13,13, 3,17, 17,16,13,13,13,	13,13,10,7 ,14,},
			{14,8 ,11,13,13,	13,13,13,13,13,	13,13,13,13,18,	13,13,13,10,7 ,},
			{8 ,11,13,2 ,0 ,	0 ,12,13,13,18,	13,21,13,13,19,	13,13,13,2 ,1 ,},
			
			{6 ,13,2 ,1 ,14,	14,9 ,12,13,20,	13,21,13,13,20,	13,13,2 ,1 ,14,},
			{6 ,2 ,1 ,14,14,	14,14,9 ,12,13,	13,21,13,13,13,	13,2 ,1 ,14,14,},
			{9 ,1 ,14,8 ,5 ,	7 ,14,14,6 ,13,	13,21,13,13,13,	2 ,1 ,14,14,14,},
			{14,14,8 ,11,13,	4 ,14,14,6 ,13,	13,13,13,13,2 ,	1 ,14,14,14,14,},
			{14,8 ,11,13,2 ,	1 ,14,8 ,11,13,	13,13,13,2 ,1 ,	14,14,14,14,14,},
			
			{8 ,11,13,13,4 ,	8 ,5 ,11,13,13,	13,13,13,4 ,14,	14,14,14,14,14,},
			{6 ,13,13,2 ,1 ,	9 ,12,13,13,13,	3 ,17,16,4 ,14,	14,14,14,14,14,},
			{6 ,13,2 ,1 ,14,	14,9 ,12,13,13,	13,13,2 ,1 ,14,	14,14,14,14,14,},
			{9 ,0 ,1 ,14,14,	14,14,9 ,12,13,	13,2 ,1 ,14,14,	14,14,14,14,14,},
			{14,14,14,14,14,	14,14,14,9 ,0 ,	0 ,1 ,14,14,14,	14,14,14,14,14,},
			
		},
		//�ڶ���
		{
			{14,14,14,14,14,	14,14,14, 8, 5,	 5, 5, 7,14,14,	14,14,14,14,14},
			{14,14,14,14,14,	14,14, 8,11,13,	13,13,10, 7,14,	14,14,14,14,14},
			{14,14,14,14,14,	14, 8,11,13,13,	13,13,13,10, 5,	 5, 7,14,14,14},
			{14,14,14,14,14,	 8,11, 3,16, 2,	 0, 0,12,13,13,	13,10, 7,14,14},
			{14,14,14,14, 8,	11,13,13,13, 4,	14,14, 9,12,13,	13,18,10, 5, 7},
			
			{14,14,14, 8,11,	18,13,13,13, 4,	14,14,14, 9,12,	13,20,13, 2, 1},
			{14,14, 8,11,13,	19,13,13, 2, 1,	14,14,14,14, 6,	13,13, 2, 1,14},
			{14,14, 9,12,13,	20, 2, 0, 1,14,	14,14,14,14, 6,	13, 2, 1,14,14},
			{14,14,14, 9,12,	 2, 1,14,14,14,	14,14,14,14, 6,	13, 4,14,14,14},
			{14,14,14,14, 9,	 1,14,14, 8, 5,	 7,14,14,14, 6,	13, 4,14,14,14},
			
			{14,14,14,14,14,	14,14, 8,11, 2,	 1,14,14, 8,11,	13,10, 5, 7,14},
			{14,14,14, 8, 7,	14,14, 6,13, 4,	14,14, 8,11,13,	13,13,13,10, 7},
			{14,14, 8,11, 4,	14,14, 9, 0, 1,	14, 8,11,13,13,	13, 2, 0, 0, 1},
			{14, 8,11,18,10,	 7,14,14,14,14,	 8,11,13,13,13,	 2, 1,14,14,14},
			{ 8,11,13,19, 2,	 1,14,14,14, 8,	11,3,17,16, 2,	 1,14,14,14,14},
			
			{ 6,13,13,19, 4,	14,14,14, 8,11,	13,13,13, 2, 1,	14,14,14,14,14},
			{ 9,12,13,20, 4,	14,14, 8,11,13,	13,13, 2, 1,14,	14,14,14,14,14},
			{14, 9,12, 2, 1,	14,14, 9,12,13,	13, 2, 1,14,14,	14,14,14,14,14},
			{14,14, 9, 1,14,	14,14,14, 9,12,	13, 4,14,14,14,	14,14,14,14,14},
			{14,14,14,14,14,	14,14,14,14, 9,	 0, 1,14,14,14,	14,14,14,14,14},
			
		},
		//������
		{
			{14,14,14,14,14,	14,14, 8, 5, 5,	 5, 7,14,14,14,	14,14,14,14,14},
			{14,14,14,14, 8,	 5, 5,11, 3,17,	16,10, 5, 5, 7,	14,14,14,14,14},
			{14,14, 8, 5,11,	13,13,13, 2, 0,	 0, 0,12,13,10,	 5, 5, 7,14,14},
			{14,14, 6,13,13,	13, 2, 0, 1,14,	14,14, 9, 0,12,	13,13, 4,14,14},
			{14,14, 6,18, 2,	 0, 1,14,14,14,	14,14,14,14, 9,	12,13,10, 7,14},
			
			{14, 8,11,20, 4,	14,14,14, 8, 5,	 7,14,14,14,14,	 9, 0, 0, 1,14},
			{14, 6,13, 2, 1,	14,14, 8,11, 2,	 1,14,14, 8, 7,	14,14,14,14,14},
			{ 8,11,13, 4,14,	14, 8,11, 2, 1,	14,14, 8,11, 4,	14,14,14,14,14},
			{ 6, 2, 0, 1,14,	 8,11, 2, 1,14,	14,14, 6,13, 4,	14, 8, 5, 7,14},
			{ 9, 1,14,14,14,	 6, 2, 1,14,14,	14,14, 6,13, 4,	14, 9,12,10, 7},
			
			{14,14,14,14, 8,	11, 4,14,14,14,	 8, 5,11,13, 4,	14,14, 6,13, 4},
			{14,14,14,14, 9,	12, 4,14,14,14,	 6,13,13, 2, 1,	14,14, 6, 2, 1},
			{ 8, 5, 7,14,14,	 6,10, 5, 5, 5,	11, 2, 0, 1,14,	14, 8,11, 4,14},
			{ 9,12,10, 5, 7,	 9, 0,12, 3,16,  2, 1,14,14,14,	 8,11, 2, 1,14},
			{14, 6,13,13, 4,	14,14, 9, 0, 0,	 1,14,14,14,14,	 6,13, 4,14,14},
			
			{14, 6,13,13,10,	 5, 7,14,14,14,	14,14,14, 8, 5,	11, 2, 1,14,14},
			{14, 9,12,13,13,	13,10, 5, 7,14,	14, 8, 5,11,13,	 2, 1,14,14,14},
			{14,14, 9,12, 3,	17,17,16, 4,14,	 8,11, 3,16, 2,	 1,14,14,14,14},
			{14,14,14, 9,12,	 2, 0, 0, 1,14,	 9, 0, 0, 0, 1,	14,14,14,14,14},
			{14,14,14,14, 9,	 1,14,14,14,14,	14,14,14,14,14,	14,14,14,14,14},
		},
		{
			{14,14,14,14,14,	14,14,14, 8, 5,	 5, 5, 7,14,14,	14,14,14,14,14},
			{14,14,14,14,14,	14,14, 8,11,13,	13,13,10, 7,14,	14,14,14,14,14},
			{14,14,14,14,14,	14, 8,11,13,13,	13,13,13,10, 5,	 5, 7,14,14,14},
			{14,14,14,14,14,	 8,11, 3,16, 2,	 0, 0,12,13,13,	13,10, 7,14,14},
			{14,14,14,14, 8,	11,13,13,13, 4,	14,14, 9,12,13,	13,18,10, 5, 7},
			
			{14,14,14, 8,11,	18,13,13,13, 4,	14,14,14, 9,12,	13,20,13, 2, 1},
			{14,14, 8,11,13,	19,13,13, 2, 1,	14,14,14,14, 6,	13,13, 2, 1,14},
			{14,14, 9,12,13,	20, 2, 0, 1,14,	14,14,14,14, 6,	13, 2, 1,14,14},
			{14,14,14, 9,12,	 2, 1,14,14,14,	14,14,14,14, 6,	13, 4,14,14,14},
			{14,14,14,14, 9,	 1,14,14, 8, 5,	 7,14,14,14, 6,	13, 4,14,14,14},
			
			{14,14,14,14,14,	14,14, 8,11, 2,	 1,14,14, 8,11,	13,10, 5, 7,14},
			{14,14,14, 8, 7,	14,14, 6,13, 4,	14,14, 8,11,13,	13,13,13,10, 7},
			{14,14, 8,11, 4,	14,14, 9, 0, 1,	14, 8,11,13,13,	13, 2, 0, 0, 1},
			{14, 8,11,18,10,	 7,14,14,14,14,	 8,11,13,13,13,	 2, 1,14,14,14},
			{ 8,11,13,19, 2,	 1,14,14,14, 8,	11,3,17,16, 2,	 1,14,14,14,14},
			
			{ 6,13,13,19, 4,	14,14,14, 8,11,	13,13,13, 2, 1,	14,14,14,14,14},
			{ 9,12,13,20, 4,	14,14, 8,11,13,	13,13, 2, 1,14,	14,14,14,14,14},
			{14, 9,12, 2, 1,	14,14, 9,12,13,	13, 2, 1,14,14,	14,14,14,14,14},
			{14,14, 9, 1,14,	14,14,14, 9,12,	13, 4,14,14,14,	14,14,14,14,14},
			{14,14,14,14,14,	14,14,14,14, 9,	 0, 1,14,14,14,	14,14,14,14,14},
			
		},
		{
			{14,14,14,14,14,	14,14, 8, 5, 5,	 5, 7,14,14,14,	14,14,14,14,14},
			{14,14,14,14, 8,	 5, 5,11, 3,17,	16,10, 5, 5, 7,	14,14,14,14,14},
			{14,14, 8, 5,11,	13,13,13, 2, 0,	 0, 0,12,13,10,	 5, 5, 7,14,14},
			{14,14, 6,13,13,	13, 2, 0, 1,14,	14,14, 9, 0,12,	13,13, 4,14,14},
			{14,14, 6,18, 2,	 0, 1,14,14,14,	14,14,14,14, 9,	12,13,10, 7,14},
			
			{14, 8,11,20, 4,	14,14,14, 8, 5,	 7,14,14,14,14,	 9, 0, 0, 1,14},
			{14, 6,13, 2, 1,	14,14, 8,11, 2,	 1,14,14, 8, 7,	14,14,14,14,14},
			{ 8,11,13, 4,14,	14, 8,11, 2, 1,	14,14, 8,11, 4,	14,14,14,14,14},
			{ 6, 2, 0, 1,14,	 8,11, 2, 1,14,	14,14, 6,13, 4,	14, 8, 5, 7,14},
			{ 9, 1,14,14,14,	 6, 2, 1,14,14,	14,14, 6,13, 4,	14, 9,12,10, 7},
			
			{14,14,14,14, 8,	11, 4,14,14,14,	 8, 5,11,13, 4,	14,14, 6,13, 4},
			{14,14,14,14, 9,	12, 4,14,14,14,	 6,13,13, 2, 1,	14,14, 6, 2, 1},
			{ 8, 5, 7,14,14,	 6,10, 5, 5, 5,	11, 2, 0, 1,14,	14, 8,11, 4,14},
			{ 9,12,10, 5, 7,	 9, 0,12, 3,16,  2, 1,14,14,14,	 8,11, 2, 1,14},
			{14, 6,13,13, 4,	14,14, 9, 0, 0,	 1,14,14,14,14,	 6,13, 4,14,14},
			
			{14, 6,13,13,10,	 5, 7,14,14,14,	14,14,14, 8, 5,	11, 2, 1,14,14},
			{14, 9,12,13,13,	13,10, 5, 7,14,	14, 8, 5,11,13,	 2, 1,14,14,14},
			{14,14, 9,12, 3,	17,17,16, 4,14,	 8,11, 3,16, 2,	 1,14,14,14,14},
			{14,14,14, 9,12,	 2, 0, 0, 1,14,	 9, 0, 0, 0, 1,	14,14,14,14,14},
			{14,14,14,14, 9,	 1,14,14,14,14,	14,14,14,14,14,	14,14,14,14,14},
		},
		{
			{14,8 ,5 ,7 ,14,	14,14,14,14,14,	14,14,14,14,14,	14,14,14,14,14,},
			{14,6 ,13,10,7 ,	14,14,14,14,14,	14,14,14,14,14,	14,14,14,14,14,},//0
			{8 ,11,13,2 ,1 ,	14,14,14,14,14,	14,14,14,14,14,	14,14,14,14,14,},//1
			{9 ,12,13,4 ,14,	14,14,14,14,14,	8 ,7 ,14,14,14,	14,14,14,14,14,},//2
			{14,9 ,0 ,1 ,14,	14,14,14,14,14,	6 ,10,5 ,7 ,14,	14,14,14,14,14,},//3
			
			{14,14,14,14,14,	14,14,14,14,8 ,	11,13,13,10,7 ,	14,14,14,14,14,},//
			{14,14,14,8 ,5 ,	5 ,5 ,5 ,5 ,11,	13,13,13,13,10,	5 ,5 ,7 ,14,14,},
			{14,14,8 ,11,13,	13,13,13, 3,17, 17,16,13,13,13,	13,13,10,7 ,14,},
			{14,8 ,11,13,13,	13,13,13,13,13,	13,13,13,13,18,	13,13,13,10,7 ,},
			{8 ,11,13,2 ,0 ,	0 ,12,13,13,18,	13,21,13,13,19,	13,13,13,2 ,1 ,},
			
			{6 ,13,2 ,1 ,14,	14,9 ,12,13,20,	13,21,13,13,20,	13,13,2 ,1 ,14,},
			{6 ,2 ,1 ,14,14,	14,14,9 ,12,13,	13,21,13,13,13,	13,2 ,1 ,14,14,},
			{9 ,1 ,14,8 ,5 ,	7 ,14,14,6 ,13,	13,21,13,13,13,	2 ,1 ,14,14,14,},
			{14,14,8 ,11,13,	4 ,14,14,6 ,13,	13,13,13,13,2 ,	1 ,14,14,14,14,},
			{14,8 ,11,13,2 ,	1 ,14,8 ,11,13,	13,13,13,2 ,1 ,	14,14,14,14,14,},
			
			{8 ,11,13,13,4 ,	8 ,5 ,11,13,13,	13,13,13,4 ,14,	14,14,14,14,14,},
			{6 ,13,13,2 ,1 ,	9 ,12,13,13,13,	3 ,17,16,4 ,14,	14,14,14,14,14,},
			{6 ,13,2 ,1 ,14,	14,9 ,12,13,13,	13,13,2 ,1 ,14,	14,14,14,14,14,},
			{9 ,0 ,1 ,14,14,	14,14,9 ,12,13,	13,2 ,1 ,14,14,	14,14,14,14,14,},
			{14,14,14,14,14,	14,14,14,9 ,0 ,	0 ,1 ,14,14,14,	14,14,14,14,14,},
			
		},
	};
	//ÿ�����������Ϣ����
	public static final float ArchieArray[][][]=
	{
		//---------------ս��ģʽ--------------------------------------------------
		//��һ��
		{
			//�����ڵ�λ��0
			{
				3.5f,2.5f, 
				1.5f,17.5f, 1.5f,16.5f, 2.5f,15.5f,	
				10.5f,17.5f, 10.5f,18, 9.5f,18.5f, 8.5f,17.5f, 8.5f,16.5f, 9.5f,16.5f, 13.5f,6.5f, 14.5f,7.5f, 15,7.5f, 12.5f,7.75f,
				13.5f,8.5f,  3.5f,8.5f, 5.5f,8.5f,
			},   
			//̹��λ������1
			{//3.5f,14.5f, 3.75f,14.5f, 9.5f,14.5f, 12.5f,14.5f, 14.5f,12.5f, 13.5f,13.5f, 6.5f,8.5f, 4.5f,7.5f,
				},
			//���������λ��2
			{ 2.5f,16.5f,// 9.5f,17.5f, 13.5f,7.5f
					},
			//����λ������3
			{2.5f,1.5f},
			//ƽ��������λ������4
			{2.5f,2.5f, 
				12.25f,9.5f, 10.5f,12.5f, 15.15f,9.5f, 11.5f,6.5f, 
			},
			//��ӵ���ʼλ��5
			{
				2,11,  2.05f,11.25f,  2.125f,11.55f,  2.20f,11.85f,  2.45f,12.25f,  2.85f,12.725f,  3.25f,12.85f,  3.45f,12.9f, 3.75f,12.95f,
				4,13, 
				
				4.25f,15.75f,  4.625f,16.125f,  4.75f,16.225f, 5,16.25f,  5.25f,16.225f,  5.375f,16.125f,  5.75f,15.75f
				
			},
			//�ɻ���Ƶ����ʱ,�����ĵ�.6
			{6,4, 13,4,  15,6, 15,12, 13,15, 6,15, 3,13, 3,6,},
			//�ɻ��ӵ����жԷ�Ŀ����ٵ�����ֵ7
			{5,5,8,2},//�����,̹��,������,�л�
			//�ɻ��ڵ����жԷ�Ŀ����ٵ�����ֵ8
			{50,100,70,100},//�����,̹��,������,�л�
			//�з����зɻ����ٵ�����ֵ9
			{5,15,1,30,5},//̹��,������,�л�,���������,���������Ķ���
			//�ɻ����칫��¥
			{9f,9.5f},//10
			//�����ڵ�����11
			{2.5f,2.25f, 2.25f,2,  2.25f,3,  1,2.75f, 
				3.5f,16.25f, 4.5f,13.5f, 4.25f,13.5f, 
				13f,9.5f,  11,6.25f, 11.5f,6.25f, 12,6.5f, 12.5f,7, 13,9.5f, 13.5f, 9.75f, 12.25f,11, 12.25f,11.75f,
				10.75f,11.25f, 10.75f,11.75f, 15.25f,9, 15.75f,9, 15.25f,11, 
				10.5f,14.5f, 12.5f,14.5f, 10.5f,15.5f, 10.5f,18.5f, 9.5f,8.5f, 17.5f,8.5f, 1.5f,9.5f, 1.5f,10.5f,
				2.5f,9.5f, 3,9.5f, 4.5f,8.5f, 5,8, 5.5f,7.5f, 
			},
			//����з���Ʒ���ӵķ���12
			{10,15,20,50},//0̹��1������,2�л�,3�����
			//�ڵ����ӵ�����Ŀ13
			{0,1000},
		},
		//�ڶ���
		{
			//�����ڵ�λ��0
			{
				1.5f,15.25f, // 1.5f,16f,  2.5f,14.5f,  4,12,  8,7,  8,6,  7.5f,5.75f,  6.5f,5.25f,  8,5,  9.5f,2.5f,  
				11.5f,1.5f,  14.5f,3.5f,  15.5f,5.5f,  16,10,  15.5f,12.5f,  10.5f,15.75f,  11.25f,15.75f,  11.5f,16.5f,
				8.5f,11.5f,  5,14
			},   
			//̹��λ������1
			{
				2.25f,14.25f,  2.75f,14.25f,  2.25f,14.75f,  2.75f,14.75f,  
				2.4f,16.5f,  2.8f,16.75f,  4.25f,7.5f, 
				4.75f,7.75f,  8.5f,5.5f,  8.5f,4.5f,  12.5f,2.5f, 15.25f,3.5f,  15.75f,3.5f, 14.5f,4.25f,  14.5f,4.75f,
				9.5f,17.5f,  10.5f, 17.5f 
			},
			//���������λ��2
			{2.5f,15.5f, 15.5f,4.5f, //7.5f,6.5f, 10.5f,16.5f
				},
			//����λ������3
			{9f,11f},
			//ƽ��������λ������4
			{
				6.125f,6.5f,  4.75f,7,  10.5f,1.5f,  18,5,  15.5f,8.5f,  19,12,
				12.5f,15,  14.5f,12.5f, 
			},
			//��ӵ���ʼλ��5
			{},
			//�ɻ���Ƶ����ʱ,�����ĵ�.6
			{6,4, 13,4,  15,6, 15,12, 13,15, 6,15, 3,13, 3,6,},
			//�ɻ��ӵ����жԷ�Ŀ����ٵ�����ֵ7
			{3,5,5,4},//�����,̹��,������,�л�
			//�ɻ��ڵ����жԷ�Ŀ����ٵ�����ֵ8
			{50,80,70,100},//�����,̹��,������,�л�
			//�з����зɻ����ٵ�����ֵ9
			{8,10,4,10,4},//̹��,������,�л�,���������,���������Ķ���
			//�ɻ����칫��¥
			{9f,9.5f},//10
			//�����ڵ�����11
			{
				2,16,  2.25f,16,  2.5f,16,  2.75f,16,  3,16,  4.125f,15,    4.125f,15.5f,     4.125f,16.5f,  2,17,
				4,13,  4.25f,13,  5,9,      5.25f,9,   3,7,   3.5f,7,  4.75f,6.5f,  7,7,      7,6,
				8.25f,2.25f,   8.75f,2.75f,  9.5f,1.5f,  10,2,  10.5f,2,  13.5f,3.5f,  15,4,  16,5,
				15.5f,5,  15.5f,6.5f,  16.5f,6.5f,  19,5,  15.5f,7.5f,  16,9,  18,12,  17.5f,11.5f, 
				13.5f,12.5f,  12.5f,15.5f,  10.5f,18.5f,  9,10,  10,10,  9,12
			},
			//����з���Ʒ���ӵķ���12
			{12,17,22,55},//0̹��1������,2�л�,3�����
			//�ڵ����ӵ�����Ŀ13
			{50,1500},
		},
		//������
		{
			//�����ڵ�λ��0
			{
				10,6,8,8,  8,7,  7,9,  /*6.5f,9.5f,*/  7,13,  13.5f,8.5f,  5.5f,2.5f,  6.5f,2.5f, //2.5f,6.5f,  
				3.5f,15.5f,  3.5f,16.5f, /* 5.5f,16.5f,*/  18,10,  18,11,  19,10,  19,11,  17,13,  16,16
			},   
			//̹��λ������1
			{
				4,16.5f, /* 5,17, */ 6,16,  5,16,  3.5f,14.5f,  3,7,  3,6,/*  6,3,*/  7,3,  12,2,  13.5f,2.5f,  16,3.25f,  /*16.5f, 3.25f, */ 17f,3.25f,
				17,14,  15f,17f, /* 13.25f,9.5f, */ 13.75f,9.5f,  13.5f,10.5f,  12,12,  6,10,  
			},
			//���������λ��2
			{16.5f,4.5f,  4.5f,3.5f,  4.5f,16.5f, 18.5f,10.5f
			},
			//����λ������3
			{6f,11f},
			//ƽ��������λ������4
			{
				2.725f,6,  14,2,  7.5f,2.5f,  15,3,  12,18,  14.5f,16.5f,  
			},
			//��ӵ���ʼλ��5
			{},
			//�ɻ���Ƶ����ʱ,�����ĵ�.6
			{6,4, 13,4,  15,6, 15,12, 13,15, 6,15, 3,13, 3,6,},
			//�ɻ��ӵ����жԷ�Ŀ����ٵ�����ֵ7
			{5,10,10,5},//�����,̹��,������,�л�
			//�ɻ��ڵ����жԷ�Ŀ����ٵ�����ֵ8
			{30,70,70,50},//�����,̹��,������,�л�
			//�з����зɻ����ٵ�����ֵ
			{17,20,2,15,4},//̹��,������,�л�,���������,���������Ķ���
			//�ɻ����칫��¥
			{9f,9.5f},//10
			//�����ڵ�����11
			{
				5.5f,18.125f,  6.5f,18.125f,  8,18,  3,16,  4,16,  6,17,  2,13,  3,14,  4,14,  3,15,  2,8,  2,7,  4,6,  
				4,4,  5,4,  6,2,  10,2.125f,  13,2.5f,  14,3,  16,4,  17,5, 18,9,  18,12,  18,12.5f, 18,13,
				17.5f,13.5f,   12,17,  14,17,
				14,7,  13,11,  11,13,  5,11,  6,9,  8.5f,7.5f
			},
			//����з���Ʒ���ӵķ���12
			{15,20,25,70},//0̹��1������,2�л�,3�����
			//�ڵ����ӵ�����Ŀ13
			{80,2000},
		},
		//--------------------------------------�ر��ж�---------------------------------------------------
		//��һ��----------����ɻ�����
		{
			//�����ڵ�λ��0
			{
				1.5f,15.25f,  1.5f,16f,  2.5f,14.5f, // 4,12,  8,7,  8,6,  7.5f,5.75f,  6.5f,5.25f,  8,5,  9.5f,2.5f,  
				11.5f,1.5f,  /*14.5f,3.5f, */ 15.5f,5.5f,  16,10,  15.5f,12.5f,/*  10.5f,15.75f,  11.25f,15.75f, */ 11.5f,16.5f,
				8.5f,11.5f,  5,14
			},   
			//̹��λ������1
			{
				2.25f,14.25f,  2.75f,14.25f,//  2.25f,14.75f,  2.75f,14.75f,  
				2.4f,16.5f,  2.8f,16.75f,  //4.25f,7.5f, 
				4.75f,7.75f,  8.5f,5.5f,/*  8.5f,4.5f, */ 12.5f,2.5f, 15.25f,3.5f,  15.75f,3.5f,// 14.5f,4.25f,  14.5f,4.75f,
				9.5f,17.5f,  10.5f, 17.5f 
			},
			//���������λ��2
			{2.5f,15.5f, 15.5f,4.5f,/* 7.5f,6.5f, 10.5f,16.5f*/},
			//����λ������3
			{9f,11f},
			//ƽ��������λ������4
			{
				6.125f,6.5f,  4.75f,7,  10.5f,1.5f,  18,5,  15.5f,8.5f,  19,12,
				12.5f,15,  14.5f,12.5f, 
			},
			//��ӵ���ʼλ��5
			{},
			//�ɻ���Ƶ����ʱ,�����ĵ�.6
			{6,4, 13,4,  15,6, 15,12, 13,15, 6,15, 3,13, 3,6,},
			//�ɻ��ӵ����жԷ�Ŀ����ٵ�����ֵ7
			{3,7,7,4},//�����,̹��,������,�л�
			//�ɻ��ڵ����жԷ�Ŀ����ٵ�����ֵ8
			{50,100,70,100},//�����,̹��,������,�л�
			//�з����зɻ����ٵ�����ֵ9
			{8,11,4,6,4},//̹��,������,�л�,���������,���������Ķ���
			//�ɻ����칫��¥
			{9f,9.5f},//10
			//�����ڵ�����11
			{
				2,16,  2.25f,16,  2.5f,16,  2.75f,16,  3,16,  4.125f,15,    4.125f,15.5f,     4.125f,16.5f,  2,17,
				4,13,  4.25f,13,  5,9,      5.25f,9,   3,7,   3.5f,7,  4.75f,6.5f,  7,7,      7,6,
				8.25f,2.25f,   8.75f,2.75f,  9.5f,1.5f,  10,2,  10.5f,2,  13.5f,3.5f,  15,4,  16,5,
				15.5f,5,  15.5f,6.5f,  16.5f,6.5f,  19,5,  15.5f,7.5f,  16,9,  18,12,  17.5f,11.5f, 
				13.5f,12.5f,  12.5f,15.5f,  10.5f,18.5f,  9,10,  10,10,  9,12
			},
			//����з���Ʒ���ӵķ���12
			{12,17,22,55},//0̹��1������,2�л�,3�����
			//�ڵ����ӵ�����Ŀ13
			{20,800},
		},
		//�ڶ���----------------��������ں�̹��
		{
			//�����ڵ�λ��0
			{
				10,6,  8,8,  8,7,  7,9,  6.5f,9.5f,  7,13, /* 13.5f,8.5f, */ 5.5f,2.5f,  6.5f,2.5f,// 2.5f,6.5f,  
				3.5f,15.5f,  3.5f,16.5f,  5.5f,16.5f,  18,10, /* 18,11,  19,10,*/  19,11,  17,13,  16,16
			},   
			//̹��λ������1
			{
				4,16.5f, 5,17,  6,16, /* 5,16,  3.5f,14.5f, */ 3,7,  3,6,  6,3,  7,3,  12,2,/*  13.5f,2.5f,*/  16,3.25f,  16.5f, 3.25f,  17f,3.25f,
				17,14,  /*15f,17f, */ 13.25f,9.5f,  13.75f,9.5f,  13.5f,10.5f,  12,12,//  6,10,  
			},
			//���������λ��2
			{16.5f,4.5f, /* 4.5f,3.5f,  4.5f,16.5f, 18.5f,10.5f*/},
			//����λ������3
			{6f,11f},
			//ƽ��������λ������4
			{
				2.725f,6,  14,2,  7.5f,2.5f,  15,3,  12,18,  14.5f,16.5f,  
			},
			//��ӵ���ʼλ��5
			{},
			//�ɻ���Ƶ����ʱ,�����ĵ�.6
			{6,4, 13,4,  15,6, 15,12, 13,15, 6,15, 3,13, 3,6,},
			//�ɻ��ӵ����жԷ�Ŀ����ٵ�����ֵ7
			{5,10,10,7},//�����,̹��,������,�л�
			//�ɻ��ڵ����жԷ�Ŀ����ٵ�����ֵ8
			{50,100,70,100},//�����,̹��,������,�л�
			//�з����зɻ����ٵ�����ֵ9
			{17,20,13,15,4},//̹��,������,�л�,���������,���������Ķ���
			//�ɻ����칫��¥
			{9f,9.5f},//10
			//�����ڵ�����11
			{
				5.5f,18.125f,  6.5f,18.125f,  8,18,  3,16,  4,16,  6,17,  2,13,  3,14,  4,14,  3,15,  2,8,  2,7,  4,6,  
				4,4,  5,4,  6,2,  10,2.125f,  13,2.5f,  14,3,  16,4,  17,5, 18,9,  18,12,  18,12.5f, 18,13,
				17.5f,13.5f,   12,17,  14,17,
				14,7,  13,11,  11,13,  5,11,  6,9,  8.5f,7.5f
			},
			//����з���Ʒ���ӵķ���12
			{15,20,25,70},//0̹��1������,2�л�,3�����
			//�ڵ����ӵ�����Ŀ13
			{50,1500},
		},
		//������--------------------��������
		{
			//�����ڵ�λ��0
			{3.5f,2.5f, 
			1.5f,17.5f, 1.5f,16.5f, 2.5f,15.5f,	
			10.5f,17.5f, 10.5f,18, 9.5f,18.5f, 8.5f,17.5f, 8.5f,16.5f, 9.5f,16.5f, 13.5f,6.5f, 14.5f,7.5f, 15,7.5f, 12.5f,7.75f,
			13.5f,8.5f,  3.5f,8.5f, 5.5f,8.5f,
			},   
			//̹��λ������1
			{3.5f,14.5f, 3.75f,14.5f, 9.5f,14.5f, 12.5f,14.5f, 14.5f,12.5f, 13.5f,13.5f, 6.5f,8.5f, 4.5f,7.5f,},
			//���������λ��2
			{ 2.5f,16.5f, 9.5f,17.5f, 13.5f,7.5f
				},
			//����λ������3
			{2.5f,1.5f},
			//ƽ��������λ������4
			{2.5f,2.5f, 
				12.25f,9.5f, 10.5f,12.5f, 15.15f,9.5f, 11.5f,6.5f, 
			},
			//��ӵ���ʼλ��5
			{
				2,11,  2.05f,11.25f,  2.125f,11.55f,  2.20f,11.85f,  2.45f,12.25f,  2.85f,12.725f,  3.25f,12.85f,  3.45f,12.9f, 3.75f,12.95f,
				4,13, 
				
				4.25f,15.75f,  4.625f,16.125f,  4.75f,16.225f, 5,16.25f,  5.25f,16.225f,  5.375f,16.125f,  5.75f,15.75f
				
			},
			//�ɻ���Ƶ����ʱ,�����ĵ�.6
			{6,4, 13,4,  15,6, 15,12, 13,15, 6,15, 3,13, 3,6,},
			//�ɻ��ӵ����жԷ�Ŀ����ٵ�����ֵ7
			{1,5,5,2},//�����,̹��,������,�л�
			//�ɻ��ڵ����жԷ�Ŀ����ٵ�����ֵ8
			{50,100,70,100},//�����,̹��,������,�л�
			//�з����зɻ����ٵ�����ֵ9
			{10,20,20,100,10},//̹��,������,�л�,���������,���������Ķ���
			//�ɻ����칫��¥
			{9f,9.5f},//10
			//�����ڵ�����11
			{2.5f,2.25f, 2.25f,2,  2.25f,3,  1,2.75f, 
				3.5f,16.25f, 4.5f,13.5f, 4.25f,13.5f, 
				13f,9.5f,  11,6.25f, 11.5f,6.25f, 12,6.5f, 12.5f,7, 13,9.5f, 13.5f, 9.75f, 12.25f,11, 12.25f,11.75f,
				10.75f,11.25f, 10.75f,11.75f, 15.25f,9, 15.75f,9, 15.25f,11, 
				10.5f,14.5f, 12.5f,14.5f, 10.5f,15.5f, 10.5f,18.5f, 9.5f,8.5f, 17.5f,8.5f, 1.5f,9.5f, 1.5f,10.5f,
				2.5f,9.5f, 3,9.5f, 4.5f,8.5f, 5,8, 5.5f,7.5f, 
			},
			//����з���Ʒ���ӵķ���12
			{10,15,20,50},//0̹��1������,2�л�,3�����
			//�ڵ����ӵ�����Ŀ13
			{10,200},
		}
	};
	//------------�ر��ж���ʱ����
	public static int [] actionTimeSpan={70,160,60};
	
	//���ڼ�¼�������Ϣ
	public static int[] gradeArray=new int[3];//0�ؿ�,1�ܵķ�,2��ʱ.
	//���ĸ߶ȺͿ��
	public static final float treeWhidth=130;
	public static final float treeHeight=150;
	//ƽ�񷿵ĳ����
	public static final float house_length=200;
	public static final float house_width=90;
	public static final float house_height=100;
	//����Ӣ�۵�ǰ״̬
	public static boolean isCrash=false;//�ɻ��Ƿ�׹��
	public static boolean isOvercome=false;//�Ƿ�սʤ����ը�������
	public static boolean isCrashCartoonOver=false;//�ɻ�׹�ٶ����������
	public static float Crash_DISTANCE_start=15;//׹��ʱ��ײ����������ĳ���
	public static float Crash_DISTANCE_stop=60;//׹��ʱ�������Ŀ�����������
	public static float BaoZha_scal=0f;//��ըЧ�������ű�
	public static boolean isno_draw_plane=false;//�Ƿ���Ʒɻ�
	public static boolean isno_draw_arsenal=true;//�Ƿ���ƾ����
	//�ոս��볡����������Ƶ�ȱ�־
	public static boolean isVideo=true;//�Ƿ񲥷���Ƶ�б�־
	public static int plane_blood=999;//999;
	public static int arsenal_blood=100;
	//��ǰ���ڵĹ���
	public static int mapId=0;
	
	//�Ƿ�����Ϸ���水���˷��ذ�ť
	public static boolean is_button_return=false;
	
	
	//�Ƿ�̹�˻��߸����ڻ���
	public static boolean isno_Hit;//�����ϰ��ﶶ��
	public static boolean isno_Vibrate;//�����ڵ� ����
	
	//�Ƿ���ĳһ������������
	public static final float Lock_Distance=2000;
	public static boolean isno_Lock=false;//����Ƿ�����
	public static float Lock_angle=(float) Math.toRadians(8);//�������ĽǶȷ�Χ
	public static TanKe Lock_tanke;//��������̹��
	public static Arsenal_House Lock_arsenal;//�������ľ����
	public static ArchieForControl Lock_Arch;//�������ĸ�����
	public static Arsenal_House Lock_Arsenal;//�������ľ����
	
	public static float minimumdistance=Lock_Distance;//��ǰ����������С����
	//����ʱ�ķ�������
	public static float nx,ny,nz; 
	public static float directionX,directionY,directionZ;//�ɻ����еĵ�ǰ������������Ŀ��

	public static float planezAngle;//�ɻ�������ת���Ƕ�

	{
		 //�趨��������Ǻͷ�λ��
		ELEVATION_CAMERA_ORI=8F;//�������ʼ����
	    ELEVATION_CAMERA=30;//ELEVATION_CAMERA_ORI;//������۲���Ŀ��������
	    DIRECTION_CAMERA=225;//������۲���Ŀ���ķ����
	    ELEVATION_CAMERA_UP=30;//����������������ֵ
	    ELEVATION_CAMERA_DOWN=-5;//��������¸������ֵ
	    ELEVATION_CAMERA_SPAN=0.4F;//��������¸����Ĳ�ֵ
	    DIRECTION_CAMERA_SPAN=2F;//�����������ת�ز�ֵ
	    rotationAngle_Plane_Y=DIRECTION_CAMERA;
	    PLANE_X=1675;//�ɻ���Xλ��
		PLANE_Y=500;//�ɻ���Yλ��
		PLANE_Z=2060;//�ɻ���Zλ��
		PLANE_MOVE_SPAN=15;//�ɻ����ٶ�
		isCrash=false;//�ɻ��Ƿ�׹��
		isOvercome=false;//�Ƿ�սʤ����ը�������
		isCrashCartoonOver=false;//�ɻ�׹�ٶ����Ƿ񲥷����
		Crash_DISTANCE_start=15;//׹��ʱ��ײ����������ĳ���
		Crash_DISTANCE_stop=60;//׹��ʱ�������Ŀ�����������
		BaoZha_scal=0f;//��ըЧ�������ű�
		isno_draw_plane=true;//�Ƿ���Ʒɻ�
		isno_draw_arsenal=true;//�Ƿ���ƾ����
		isVideo=false;//�Ƿ񲥷���Ƶ�б�־
		bullet_number=(int) ArchieArray[mapId][13][1];
		bomb_number=(int) ArchieArray[mapId][13][0];
		WEAPON_INDEX=0;
		
	}
	public static void initMap_Value()//��ʼ��һЩֵ
	{
		 //�趨��������Ǻͷ�λ��
		ELEVATION_CAMERA_ORI=8F;//�������ʼ����
	    ELEVATION_CAMERA=30;//ELEVATION_CAMERA_ORI;//������۲���Ŀ��������
	    DIRECTION_CAMERA=225;//������۲���Ŀ���ķ����
	    ELEVATION_CAMERA_UP=30;//����������������ֵ
	    ELEVATION_CAMERA_DOWN=-5;//��������¸������ֵ
	    ELEVATION_CAMERA_SPAN=0.4F;//��������¸����Ĳ�ֵ
	    DIRECTION_CAMERA_SPAN=2F;//�����������ת�ز�ֵ
	    rotationAngle_Plane_Y=DIRECTION_CAMERA;
	    PLANE_X=1675;//�ɻ���Xλ��
		PLANE_Y=500;//�ɻ���Yλ��
		PLANE_Z=2060;//�ɻ���Zλ��
		PLANE_MOVE_SPAN=15;//�ɻ����ٶ�
		isCrash=false;//�ɻ��Ƿ�׹��
		isOvercome=false;//�Ƿ�սʤ����ը�������
		isCrashCartoonOver=false;//�ɻ�׹�ٶ����Ƿ񲥷����
		Crash_DISTANCE_start=15;//׹��ʱ��ײ����������ĳ���
		Crash_DISTANCE_stop=60;//׹��ʱ�������Ŀ�����������
		BaoZha_scal=0f;//��ըЧ�������ű�
		isno_draw_plane=true;//�Ƿ���Ʒɻ�
		isno_draw_arsenal=true;//�Ƿ���ƾ����
		isVideo=false;//�Ƿ񲥷���Ƶ�б�־
		bullet_number=(int) ArchieArray[mapId][13][1];
		bomb_number=(int) ArchieArray[mapId][13][0];
		WEAPON_INDEX=0;
		
	}
	public static final float PLANE_X_R=15f;//�ɻ���Χ�а뾶
	public static final float PLANE_Y_R=15; 
	public static final float PLANE_Z_R=20;
	public static final float ANGLE_X_Z=35;//�ɻ���Χ�ж�����ɻ����������������ߵļн�
	public static final float TRANSFER_Y=0;//Y����������ͷɻ����µ�ֵ
	//�����İ�Χ��
	public static final float ARSENAL_X=100;
	public static final float ARSENAL_Y=50;
	public static final float ARSENAL_Z=90;
	//̹�˰�Χ��
	public static final float ARCHIBALD_X=40;
	public static final float ARCHIBALD_Y=40;
	public static final float ARCHIBALD_Z=40;
	
	//���ֵĸ߶ȣ���ʾѪ�İٷֱ�
	public static final float NUMBER_WIDTH=20;
	public static final float NUMBER_HEIGHT=20;//���ֵĿ�Ⱥ͸߶ȣ���ʾѪ
	public static Object lock=new Object();//����������
	public static float sXtart=0;//2D�������ʼ����
	public static float sYtart=0;
	
	//�ֻ���Ļ�Ŀ�Ⱥ͸߶�
	public static  float SCREEN_WIDTH=480;
	public static  float SCREEN_HEIGHT=800;
	//��Ļ�����ű���
	public static float ratio_width;
	public static float ratio_height;
	//����״̬
	public static int keyState=0;//����״̬  1-up 2-down 4-left 8-right
    //�趨��������Ǻͷ�λ��
	public static float ELEVATION_CAMERA_ORI=8F;//�������ʼ����
    public static float ELEVATION_CAMERA=45;//ELEVATION_CAMERA_ORI;//������۲���Ŀ��������
    public static float  DIRECTION_CAMERA=180;//������۲���Ŀ���ķ����
    public static float ELEVATION_CAMERA_UP=30;//����������������ֵ
    public static float ELEVATION_CAMERA_DOWN=-5;//��������¸������ֵ
    public static float ELEVATION_CAMERA_SPAN=0.8F;//��������¸����Ĳ�ֵ
    public static float DIRECTION_CAMERA_SPAN=2F;//�����������ת�ز�ֵ
    
    //������۲���Ŀ���ľ���
    public static final float DISTANCE=180;
	
	//�������ת
	public static  float rotationAngle_SkyBall;
	//ˮ��Ŀ��
	public static float WATER_WIDTH;
	//ˮ��ĸ߶�
	public static float WATER_HEIGHT; 
	//------------------------------------ɽ----------------------------------------------
	public static final float LAND_HIGHEST=150f;   
	public static final float LAND_HIGHT=600;//����ɽ�ĸ߶ȵ���
	
	public static final float waterHillHight=20;//ˮ���ϵ�ɽ����߶�
	public static final float LOTHight=10;//�ؿ齥��߶� 
	public static final float HillHight=100;//����ɽ�Ľ���߶�
	
	public static final float height_span_LOT=130;//������ɽ����߶� 
	public static final float height_span_Water=180;//ˮ��ɽ����߶�
	public static final float height_span_Hill=200;//ɽ��ɽ����߶�
	
	//�÷��������ڲ���ɽ�صĹ���Ч��
	//����ڻҶ�ͼ��ÿһ��һ�еĲ�������        �����ĻҶ�ͼΪ8*8
	static int span=7;// ����������ռ������
	static int rows=7;//������                           
	public static float zdYRowFunction(float rowIndex)//����Ϊ�е�����                                           
	{		                                                                                 
		if(rowIndex>=0&&rowIndex<4)
		{
			return LAND_HIGHEST;
		}
		else
		{
			return 0;
		}
	}                                                                                        
	//����ֱ��Y��������ķ���                                              
	public static float[][] generateZDY()                            
	{                                                           
		int colsPlusOne=rows+1;                                 
		int rowsPlusOne=rows+1;		                            
		float[][]temp=new float[rowsPlusOne][colsPlusOne];          
		                                                        
		for(int i=0;i<rowsPlusOne;i++)                          
		{                                                       
			float h=zdYRowFunction(i);                          
			for(int j=0;j<colsPlusOne;j++)                      
			{                                                   
				temp[i][j]=h;                              
			}                                                   
		} 
		return temp;
	}
	//���������Y��������ķ���                                               
	public static float[][] generateUpWDY()                              
	{                                                            
		int colsPlusOne=rows+1;                                  
		int rowsPlusOne=rows+1;		                             
		float [][]temp=new float[rowsPlusOne][colsPlusOne];		     
		for(int i=0;i<rowsPlusOne;i++)                           
		{                                                        
			for(int j=0;j<colsPlusOne;j++)                       
			{                                                    
				float p=(float) Math.sqrt(i*i+j*j);				 
				float h=zdYRowFunction(p);                       
				if(h>LAND_HIGHEST)                           
				{                                                
					h=LAND_HIGHEST;                          
				}  
				if(h<0)
				{
					h=0;
				}
				if(j>7-i)
				{
					h=0;
				}
				temp[i][j]=h;                               
			}                                                    
		} 
		return temp;
	} 
	//�����������Y����ķ���
	public static float[][] generateDownWDY()                             
	{                                                            
		int colsPlusOne=rows+1;                                  
		int rowsPlusOne=rows+1;		                             
		float [][] temp=new float[rowsPlusOne][colsPlusOne];		     
		for(int i=0;i<rowsPlusOne;i++)                           
		{                                                        
			for(int j=0;j<colsPlusOne;j++)                       
			{                                                         
				float p=(float) Math.sqrt(i*i+j*j);				 
				float h=zdYRowFunction(p);                       
				if(h>LAND_HIGHEST)                           
				{                                                
					h=LAND_HIGHEST;                          
				} 
				if(h<0)
				{
					h=0;
				}
				temp[7-i][7-j]=LAND_HIGHEST-h;                               
			}                                                    
		}
		return temp;
	} 
	//------�趨ɽ�ĵ���ֵ
	public static final float LAND_HIGH_ADJUST=0;
	//------½�صĿ���
	public static final int LANDS_SIZE=7;
	//-----½�صĸ߶�����
	public static float[][][] LANDS_HEIGHT_ARRAY=new float[LANDS_SIZE][][];
	//-----½�ص�ÿ��λ���
	public static final float LAND_UNIT_SIZE=60;
	//½�صĿ�Ⱥ͸߶�
	public static float WIDTH_LALNDFORM;
	public static float HEIGHT_LANDFORM;
	//�趨��յİ뾶
	public static final float SKY_BALL_RADIUS=40*LAND_UNIT_SIZE*rows;
	public static final float SKY_BALL_SMALL=LAND_UNIT_SIZE*rows*(CELL_SIZE/2-0.3f);
	//------��ʼ��½�ض������Ϣ
	public static void initLandsHeightInfo(Resources r)
	{
		LANDS_HEIGHT_ARRAY[0]= generateZDY();
		LANDS_HEIGHT_ARRAY[1]=generateUpWDY();
		LANDS_HEIGHT_ARRAY[2]= generateDownWDY();
		LANDS_HEIGHT_ARRAY[3]=loadLandforms(r,R.drawable.landform,LAND_HIGHEST);//½���ϵ�ɽ
		LANDS_HEIGHT_ARRAY[4]=loadLandforms(r,R.drawable.landform1,0);//ˮ�����ɽ
		LANDS_HEIGHT_ARRAY[5]=loadLandforms(r,R.drawable.landform3,LAND_HIGHEST);//½���ϵ��ұ�
		LANDS_HEIGHT_ARRAY[6]=loadLandforms(r,R.drawable.landform2,LAND_HIGHEST);//���߽�
		//�����ʼ��½�صĿ�Ⱥ͸߶�
		WIDTH_LALNDFORM=LAND_UNIT_SIZE*(LANDS_HEIGHT_ARRAY[0].length-1);
		HEIGHT_LANDFORM=LAND_UNIT_SIZE*(LANDS_HEIGHT_ARRAY[0][0].length-1);
		WATER_WIDTH=WIDTH_LALNDFORM;
		WATER_HEIGHT=HEIGHT_LANDFORM;
	}
	//------�ӻҶ�ͼƬ�м���½����ÿ������ĸ߶�
	public static float[][] loadLandforms(Resources resource,int landformDrawable,float height)
	{
		//���ص��λҶ�ͼ
		Bitmap bt=BitmapFactory.decodeResource(resource, landformDrawable);
		//��ȡ�Ҷ�ͼ�ĸ߶�����    �������ܱ�������������һ
		int colsPlusOne=bt.getWidth();
		int rowsPlusOne=bt.getHeight(); 
		//���Ҷ�ͼ�е�ÿ�����صĻҶ�ֵ�����½�ش˵�ĸ߶�ֵ
		float[][] result=new float[rowsPlusOne][colsPlusOne];
		for(int i=0;i<rowsPlusOne;i++)
		{
			for(int j=0;j<colsPlusOne;j++)
			{
				int color=bt.getPixel(j,i);//��ȡָ��λ����ɫֵ
				int r=Color.red(color);//��ȡ��ɫ����ֵ
				int g=Color.green(color); //��ȡ��ɫ����ֵ
				int b=Color.blue(color);//��ȡ��ɫ����ֵ
				int h=(r+g+b)/3;//��ɫ��ֵ  Ϊ60
				result[i][j]=h*LAND_HIGHT/255.0f+height;  
			}
		}		
		return result;
	}
	//����ť�Ŀ�Ⱥ͸߶�
	public static float BUTTON_FIRE_WIDTH=0.6f;
	public static float BUTTON_FIRE_HEIGHT=0.6f;
	//����ť��ƽ��
	public static float BUTTON_FIRE_XOffset=1.4f;
	public static float BUTTON_FIRE_YOffset=-0.7f;//-----------
	//����ť�ķ�Χ
	public static float[]BUTTON_FIRE_AREA;
	//�״ﱳ���Ĵ�С
	public static float BUTTON_RADAR_BG_WIDTH=0.5f;
	public static float BUTTON_RADAR_BG_HEIGHT=0.5f;
	public static float scalMark=0.3f;
	
	//�״�ָ��Ĵ�С
	public static float BUTTON_RADAR_PLANE_WIDTH=0.35F;
	public static float BUTTON_RADAR_PLANE_HEIGHT=0.35F;
	//�״��λ��
	public static float BUTTON_RADAR_XOffset=1.4f;
	public static float BUTTON_RADAR_YOffset=0.7f;
	//�״�ָ�����ת�Ƕ�
	public static  float RADAR_DIRECTION;
	//����ѡ��ť�Ŀ�Ⱥ͸߶�
	public static float BUTTON_WEAPON_WIDTH=0.3f;
	public static float BUTTON_WEAPON_HEIGHT=0.3f;
	//����ѡ��ť��ƽ��
	public static float BUTTON_WEAPON_XOffset=-1.45f;
	public static float BUTTON_WEAPON_YOffset=0.8f;//-----------
	//��������������
	public static int WEAPON_INDEX=0;//0��ʾ�ӵ�,1��ʾ����
	//����ѡ��ť�ķ�Χ
	public static float[]BUTTON_WEAPON_AREA;
	
	//����ʣ�����Ŀ�Ⱥ͸߶�
	public static float WEAPON_NUMBER_WIDTH=0.05f;
	public static float WEAPON_NUMBER_HEIGHT=0.15f;
	//����ѡ��ť��ƽ��
	public static float WEAPON_NUMBER_XOffset;
	public static float WEAPON_NUMBER_YOffset;//-----------
	
	//���ϰ�ť�Ŀ�ȸ߶�
	public static float BUTTON_UP_WIDTH=0.5f;
	public static float BUTTON_UP_HEIGHT=0.5f;
	//���ϰ�ť��ƽ��
	public static float BUTTON_UP_XOffset=-1.45f;
	public static float BUTTON_UP_YOffset=-0.4f;//-----------
	//���ϰ�ť�ķ�Χ
	public static float[]BUTTON_UP_AREA;
	
	//���°�ť�Ŀ�ȸ߶�
	public static float BUTTON_DOWN_WIDTH=0.5f;
	public static float BUTTON_DOWN_HEIGHT=0.5f;
	//���°�ť��ƽ��
	public static float BUTTON_DOWN_XOffset=-1.45f;
	public static float BUTTON_DOWN_YOffset=-0.8f;//-----------
	//���°�ť�ķ�Χ
	public static float[]BUTTON_DOWN_AREA;

	//����Max����ʱ�����ű���
	public static final float ratio_3dmax=0.7f;
	
	//------�ɻ�ģ�͵���ز���-------------------
	public final static float BODYBACK_B=0.08f;				//��������b�᳤��
	public final static float BODYBACK_C=0.08f;				//��������c�᳤��
	public final static float BODYBACK_A=0.6f;				//��������a�᳤��
	public final static float BODYHEAD_A=0.2f;				//��ͷ����a�᳤��
	
	public static final float PLANE_SIZE=1.5f*2;				//hero���ĳߴ�
	public final static float BODYHEAD_B=0.08f;				//��ͷ����b�᳤��
	public final static float BODYHEAD_C=0.08f;				//��ͷ����c�᳤��
	public final static float CABIN_A=0.08f;				//��������a�᳤��
	public final static float CABIN_B=0.032f;				//��������b�᳤��
	public final static float CABIN_C=0.032f;				//��������c�᳤��
	public static final float ENEMYPLANE_SIZE=3;			//�л��ĳߴ�
	//--------�ɻ�������ز���------------------------------------
	public final static float PLANE_RATIO=20F;//�ɻ������ű���
	public static float PLANE_X=1675;//�ɻ���Xλ��
	public static float PLANE_Y=330;//�ɻ���Yλ��
	public static float PLANE_Z=2060;//�ɻ���Zλ��
	//�趨�ɻ�ÿ����ǰ�ƶ��ľ���
    public static float PLANE_MOVE_SPAN=12;
    //�趨�л������ٶ�
    public static float ENEMYPLANE_SPAN=10;
    //�ɻ��ķ��еĺ������ֵ
    public static float PLANE_HEIGHT_MAX=LAND_HIGHT*1.2f;
    //�ɻ���ת�ؽǶ�    �ֱ�Ϊ�� X��,Y��,��Z��
    public static float rotationAngle_Plane_X;
    public static float rotationAngle_Plane_Y=DIRECTION_CAMERA;
    public static float rotationAngle_Plane_Z; 
	
	public final static float PLANE_UP_ROTATION_DOMAIN_X=45;//�ɻ������������ֵ
	public final static float PLANE_ROTATION_SPEED_SPAN_X=5f;//�ɻ����¸������ٶ�
	public final static float PLANE_DOWN_ROTATION_DOMAIN_X=-20;//�ɻ����¸������ֵ
	
	public final static float PLANE_ROTATION_SPEED_SPAN_Z=2f;//�ɻ�������б���ٶ� 
	public final static float PLANE_LEFT_ROTATION_DOMAIN_Z=16;//�ɻ�������б�����ֵ
	public final static float PLANE_RIGHT_ROTATION_DOMAIN_Z=-16;//�ɻ�������б�����ֵ
	
	//-----------------------�ӵ�����ز���
	public final static float BULLET_WIDTH=7F;
	public final static float BULLET_HEIGHT=4F;
	public final static float BULLET_SCALE=4f;//0.6F;//�ӵ��Ĵ�С
	public final static float BULLET_MAX_DISTANCE=2000;//�ӵ���������
	public static ArrayList<BulletForControl> bullet_List=new ArrayList<BulletForControl>();//�����ȥ���ӵ��б�
	public final static float BULLET_VELOCITY=40;//�ӵ����ٶ�
	public static boolean isFireOn;//�Ƿ����ӵ�
	public static int bullet_number;//�ӵ�����
	//-----------------------�ڵ�����ز���
	public static ArrayList<BombForControl> bomb_List=new ArrayList<BombForControl>();//�����ȥ���ӵ��б�
	public static int fire_index=0;//0��ʾ�������,1��ʾ�һ�����
	public final static float BOMB_MAX_DISTANCE=1500;//�ӵ���������
	public final static float BOMB_VELOCITY=40;//�ڵ����ٶ�
	public static int bomb_number;//�ӵ�����
	//------------------------�����ڵ���ز���
	public static float barrel_length=60;//�ڹܵĳ���
	public static float barrel_radius=6f;//�ڹܵİ뾶
	public static float barbette_length=6;//��̨�ĳ���
	public static float barbette_radius=35;//��̨�İ뾶
	public static float cube_length=10;//����ĳ���
	public static float cube_width=15;//����Ŀ��
	public static float cube_height=25;//����ĸ߶�
	public static final float ARCHIE_MAX_DISTANCE=1500;//�������ܹ�ɨ�赽�����Χ
	public static ArrayList<ArchieForControl> archie_List=new ArrayList<ArchieForControl>();//�����ڵ��б�
	public static Map<Integer,ArrayList<ArchieForControl>> archie_Map=new HashMap<Integer,ArrayList<ArchieForControl>>();
	public static ArrayList<BombForControl> archie_bomb_List=new ArrayList<BombForControl>();//�������ڵ����б�
	public final static float ARCHIE_BOMB_VELOCITY=30;//�ڵ����ٶ�
	//------------------------̹����ز���--------
	public static final float TANK_MAX_DISTANCE=600;//̹���ܹ�ɨ�赽�����Χ
	public static ArrayList<BombForControl> tank_bomb_List=new ArrayList<BombForControl>();//̹���ڵ����б�
	public final static float TANK_BOMB_VELOCITY=30f;//�ڵ����ٶ�
	public final static float tank_ratio=3f;//̹�˵����ű���
	//------ͨ����������������ķ���
	public static int initTexture(Resources r,int drawableId,boolean isMipmap)
	{
		int[] textures = new int[1];
		GLES20.glGenTextures
		(
				1,          //����������id������
				textures,   //����id������ 
				0           //ƫ����
		);    
		int textureId=textures[0];    
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		if(isMipmap)
		{//Mipmap����������˲���	
			GLES20.glTexParameteri ( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);   
			GLES20.glTexParameteri ( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_NEAREST);
		}
		else
		{//��Mipmap����������˲���	
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
		}
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_REPEAT);
        InputStream is = r.openRawResource(drawableId);
        System.out.println("drawableId"+drawableId);   
        Bitmap bitmapTmp;
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
//        GLUtils.texImage2D
//        (
//         GLES20.GL_TEXTURE_2D, //��������
//          0,   
//          GLUtils.getInternalFormat(bitmapTmp), 
//          bitmapTmp, //����ͼ��
//          GLUtils.getType(bitmapTmp), 
//          0 //����߿�ߴ�
//         );   
        GLUtils.texImage2D
        (
         GLES20.GL_TEXTURE_2D, //��������
          0,   
          bitmapTmp, //����ͼ��
          0 //����߿�ߴ�
         );   

        //�Զ�����Mipmap����
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        
        bitmapTmp.recycle(); 		  //������سɹ����ͷ�ͼƬ
        return textureId;
	}
	//�л�λ������
	public static final float[][][] enemy_plane_place=
	{
		{
//			{-500,LAND_HIGHT+100,-500, 0,225,0,
//				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
//			{LAND_UNIT_SIZE*7*MapArray[mapId].length-500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length-500, 0,135,0,	
//				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
//			{500,LAND_HIGHT+100,-500, 0,225,0,
//				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
//			{LAND_UNIT_SIZE*7*MapArray[mapId].length+500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length-500, 0,135,0,	
//				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},	
		},
		{
//			{-500,LAND_HIGHT+100,-500, 0,225,0,
//				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
//			{LAND_UNIT_SIZE*7*MapArray[mapId].length-500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length-500, 0,135,0,	
//				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
//			{500,LAND_HIGHT+100,-500, 0,225,0,
//				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
//			{LAND_UNIT_SIZE*7*MapArray[mapId].length+500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length-500, 0,135,0,	
//				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},	
//			
//			{500,LAND_HIGHT+100,500, 0,225,0,
//				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
//			{LAND_UNIT_SIZE*7*MapArray[mapId].length+500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+500, 0,135,0,	
//				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},	
		},
		
		{
			{-500,LAND_HIGHT+100,-500, 0,225,0,
				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
//			{LAND_UNIT_SIZE*7*MapArray[mapId].length-500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length-500, 0,135,0,	
//				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			{500,LAND_HIGHT+100,-500, 0,225,0,
				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
//			{LAND_UNIT_SIZE*7*MapArray[mapId].length+500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length-500, 0,135,0,	
//				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},	
			
			{500,LAND_HIGHT+100,500, 0,225,0,
				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			{LAND_UNIT_SIZE*7*MapArray[mapId].length+500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+500, 0,135,0,	
			-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			
			{-800,LAND_HIGHT+100,-800, 0,225,0,
				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
//			{LAND_UNIT_SIZE*7*MapArray[mapId].length-700,200,LAND_UNIT_SIZE*7*MapArray[mapId].length-700, 0,135,0,	
//				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
		},
		//--------------------�ر��ж�------------------------------
		{
			{-500,LAND_HIGHT+100,-500, 0,225,0,
				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			{LAND_UNIT_SIZE*7*MapArray[mapId].length-500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length-500, 0,135,0,	
				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			{500,LAND_HIGHT+100,-500, 0,225,0,
				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			{LAND_UNIT_SIZE*7*MapArray[mapId].length+500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length-500, 0,135,0,	
				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},	
			
			{500,LAND_HIGHT+100,500, 0,225,0,
				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			{LAND_UNIT_SIZE*7*MapArray[mapId].length+500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+500, 0,135,0,	
				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},	
		},
		{
			{-500,LAND_HIGHT+100,-500, 0,225,0,
				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			{LAND_UNIT_SIZE*7*MapArray[mapId].length-500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length-500, 0,135,0,	
				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			{500,LAND_HIGHT+100,-500, 0,225,0,
				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			{LAND_UNIT_SIZE*7*MapArray[mapId].length+500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length-500, 0,135,0,	
				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},	
			
			{500,LAND_HIGHT+100,500, 0,225,0,
				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			{LAND_UNIT_SIZE*7*MapArray[mapId].length+500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+500, 0,135,0,	
			-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			
			{-800,LAND_HIGHT+100,-800, 0,225,0,
				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			{LAND_UNIT_SIZE*7*MapArray[mapId].length-700,200,LAND_UNIT_SIZE*7*MapArray[mapId].length-700, 0,135,0,	
				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
		},
		{
			{-500,LAND_HIGHT+100,-500, 0,225,0,
				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			{LAND_UNIT_SIZE*7*MapArray[mapId].length-500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length-500, 0,135,0,	
				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			{500,LAND_HIGHT+100,-500, 0,225,0,
				LAND_UNIT_SIZE*7*MapArray[mapId].length+1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},
			{LAND_UNIT_SIZE*7*MapArray[mapId].length+500,200,LAND_UNIT_SIZE*7*MapArray[mapId].length-500, 0,135,0,	
				-1000,200,LAND_UNIT_SIZE*7*MapArray[mapId].length+1000},	
		},
	};
}
