package com.bn.gameView;
import static com.bn.gameView.Constant.ABOUT_HEIGHT;
import static com.bn.gameView.Constant.ANGLE_X_Z;
import static com.bn.gameView.Constant.ARSENAL_X;
import static com.bn.gameView.Constant.ARSENAL_Y;
import static com.bn.gameView.Constant.ARSENAL_Z;
import static com.bn.gameView.Constant.ArchieArray;
import static com.bn.gameView.Constant.BUTTON_RADAR_BG_WIDTH;
import static com.bn.gameView.Constant.BaoZha_scal;
import static com.bn.gameView.Constant.Crash_DISTANCE_start;
import static com.bn.gameView.Constant.Crash_DISTANCE_stop;
import static com.bn.gameView.Constant.DIRECTION_CAMERA;
import static com.bn.gameView.Constant.DIRECTION_CAMERA_SPAN;
import static com.bn.gameView.Constant.DISTANCE;
import static com.bn.gameView.Constant.ELEVATION_CAMERA;
import static com.bn.gameView.Constant.ELEVATION_CAMERA_DOWN;
import static com.bn.gameView.Constant.ELEVATION_CAMERA_ORI;
import static com.bn.gameView.Constant.ELEVATION_CAMERA_SPAN;
import static com.bn.gameView.Constant.ELEVATION_CAMERA_UP;
import static com.bn.gameView.Constant.HELP_HEIGHT;
import static com.bn.gameView.Constant.LANDS_HEIGHT_ARRAY;
import static com.bn.gameView.Constant.LAND_HIGHEST;
import static com.bn.gameView.Constant.LAND_UNIT_SIZE;
import static com.bn.gameView.Constant.Lock_Distance;
import static com.bn.gameView.Constant.MENU_BUTTON_WIDTH;
import static com.bn.gameView.Constant.MapArray;
import static com.bn.gameView.Constant.PLANE_DOWN_ROTATION_DOMAIN_X;
import static com.bn.gameView.Constant.PLANE_HEIGHT_MAX;
import static com.bn.gameView.Constant.PLANE_MOVE_SPAN;
import static com.bn.gameView.Constant.PLANE_ROTATION_SPEED_SPAN_X;
import static com.bn.gameView.Constant.PLANE_ROTATION_SPEED_SPAN_Z;
import static com.bn.gameView.Constant.PLANE_UP_ROTATION_DOMAIN_X;
import static com.bn.gameView.Constant.PLANE_X;
import static com.bn.gameView.Constant.PLANE_X_R;
import static com.bn.gameView.Constant.PLANE_Y;
import static com.bn.gameView.Constant.PLANE_Y_R;
import static com.bn.gameView.Constant.PLANE_Z;
import static com.bn.gameView.Constant.RADAR_DIRECTION;
import static com.bn.gameView.Constant.TRANSFER_Y;
import static com.bn.gameView.Constant.WATER_HEIGHT;
import static com.bn.gameView.Constant.WEAPON_INDEX;
import static com.bn.gameView.Constant.WIDTH_LALNDFORM;
import static com.bn.gameView.Constant.archie_List;
import static com.bn.gameView.Constant.archie_bomb_List;
import static com.bn.gameView.Constant.bomb_List;
import static com.bn.gameView.Constant.bomb_number;
import static com.bn.gameView.Constant.bullet_List;
import static com.bn.gameView.Constant.bullet_number;
import static com.bn.gameView.Constant.directionX;
import static com.bn.gameView.Constant.directionY;
import static com.bn.gameView.Constant.directionZ;
import static com.bn.gameView.Constant.fire_index;
import static com.bn.gameView.Constant.gradeArray;
import static com.bn.gameView.Constant.house_height;
import static com.bn.gameView.Constant.house_length;
import static com.bn.gameView.Constant.house_width;
import static com.bn.gameView.Constant.isFireOn;
import static com.bn.gameView.Constant.isCrashCartoonOver;
import static com.bn.gameView.Constant.isCrash;
import static com.bn.gameView.Constant.isOvercome;
import static com.bn.gameView.Constant.isVideo;
import static com.bn.gameView.Constant.isno_Hit;
import static com.bn.gameView.Constant.isno_Lock;
import static com.bn.gameView.Constant.isno_Vibrate;
import static com.bn.gameView.Constant.is_button_return;
import static com.bn.gameView.Constant.isno_draw_arsenal;
import static com.bn.gameView.Constant.isno_draw_plane;
import static com.bn.gameView.Constant.keyState;
import static com.bn.gameView.Constant.lock;
import static com.bn.gameView.Constant.mapId;
import static com.bn.gameView.Constant.minimumdistance;
import static com.bn.gameView.Constant.nx;
import static com.bn.gameView.Constant.ny;
import static com.bn.gameView.Constant.nz;
import static com.bn.gameView.Constant.planezAngle;
import static com.bn.gameView.Constant.rotationAngle_Plane_X;
import static com.bn.gameView.Constant.rotationAngle_Plane_Y;
import static com.bn.gameView.Constant.rotationAngle_Plane_Z;
import static com.bn.gameView.Constant.rotationAngle_SkyBall;
import static com.bn.gameView.Constant.scalMark;
import static com.bn.gameView.Constant.tank_bomb_List;
import static com.bn.gameView.GLGameView.arsenal;
import static com.bn.gameView.GLGameView.baoZhaList;
import static com.bn.gameView.GLGameView.bombRect;
import static com.bn.gameView.GLGameView.bombRectr;
import static com.bn.gameView.GLGameView.cop_archie_List;
import static com.bn.gameView.GLGameView.cop_archie_bomb_List;
import static com.bn.gameView.GLGameView.cop_bomb_List;
import static com.bn.gameView.GLGameView.cop_bullet_List;
import static com.bn.gameView.GLGameView.cx;
import static com.bn.gameView.GLGameView.cy;
import static com.bn.gameView.GLGameView.cz;
import static com.bn.gameView.GLGameView.enemy;
import static com.bn.gameView.GLGameView.isVideoPlaying;
import static com.bn.gameView.GLGameView.tankeList;
import static com.bn.gameView.GLGameView.treeList;
import static com.bn.gameView.GLGameView.tx;
import static com.bn.gameView.GLGameView.ty;
import static com.bn.gameView.GLGameView.tz;

import java.util.Collections;
import java.util.Date;


import com.bn.arsenal.Arsenal_House;
import com.bn.commonObject.DrawBomb;
import com.bn.commonObject.Tree;
import com.bn.core.SQLiteUtil;

public class KeyThread extends Thread 
{
	public boolean flag_go;//�̱߳�־λ
	private boolean isPlaneNoUp;//�ɻ�����������־λ
	GLGameView gv;//�������������
	private float oldTimeBullet=0;//���ڼ�¼�ϴη����ӵ���ʱ��
	private float oldTimeBomb=0;//���ڼ�¼�ϴη����ӵ���ʱ��
	int time;
	public float planeY;//�ɻ�׹�ٴ��ĵ���߶�
	int playId=1;//��Ƶ����
	public boolean isno_adjust;//�Ƿ�����ɻ�����
	int playIdArray;//�����±�
	public KeyThread(GLGameView gv)
	{
		this.gv=gv;
		flag_go=true;
		tx=PLANE_X=MapArray[mapId].length*WIDTH_LALNDFORM/2;
		ty=PLANE_Y;
		tz=PLANE_Z=MapArray[mapId].length*WIDTH_LALNDFORM/2;
		isVideo=true;
	}
	@Override
	public void run()
	{
		while(flag_go)
		{
			if(gv.isGameOn)//�����Ϸ��ʼ
			{
				synchronized(lock)
				{
					if(!isVideo&&is_button_return)//������ͣ��ť�����߷��ذ�ť
					{
						continue;
					}
					time+=50;			
					if(isVideo)//�������Ƶ����ʱ
					{
						if(!isVideoPlaying)
						{
							continue;
						}
						PLANE_MOVE_SPAN=40;//�ɻ��ٶ�
						float nx,nz;
						nx=ArchieArray[mapId][6][(playIdArray)*2]*WIDTH_LALNDFORM-PLANE_X;
						nz=ArchieArray[mapId][6][(playIdArray)*2+1]*WIDTH_LALNDFORM-PLANE_Z;
						  
						float distance=(float) Math.sqrt(nx*nx+nz*nz);
					    if(nz<0)
						{
					    	rotationAngle_Plane_Y=DIRECTION_CAMERA=(float)Math.toDegrees(Math.atan(nx/nz));	
						}
					    else if(nz==0)
					    {
							rotationAngle_Plane_Y=DIRECTION_CAMERA=nx>0?90:-90;
						}
						else 
						{
							rotationAngle_Plane_Y=DIRECTION_CAMERA=180+(float)Math.toDegrees(Math.atan(nx/nz));	
						}
					    rotationAngle_Plane_X=0;
					    ELEVATION_CAMERA=0;
					    rotationAngle_Plane_Z=0;
					    PLANE_Y=330;
						if(distance<40)
						{
							playIdArray++;
							playIdArray%=ArchieArray[mapId][6].length/2;
						}
					}
					//=====�ر��ж�ʱ��¼ʱ��
					if(gv.isGameMode==1&&!is_button_return&&!isVideo&&System.nanoTime()-gv.oldTime>1000000000)
        			{
						gv.goTime--;
						gv.oldTime=System.nanoTime();
        			}
					//-----�ر��ж�--------------------
					if(gv.isGameMode==1)
					{
						//ʱ�䵽��
						if(gv.goTime<0)
						{
							//�ж�ʧ��
							gv.isSpecActionState=2;
							if(gv.activity.bgMusic[1].isPlaying())
							{
								gv.activity.bgMusic[1].pause();
							}
							isOvercome=true;
						}
						//ʱ��û�е�----�ɻ�ը����---------------------------
						else if(gv.plane.blood<=0)
						{
							//�ж�ʧ��
							gv.isSpecActionState=2;
							if(gv.activity.bgMusic[1].isPlaying())
							{
								gv.activity.bgMusic[1].pause();
							}
							isCrash=true;//�ɻ�׹��
						}
						//ʱ�仹û�е�,�ɹ�����
						else
						{
							switch(mapId)
							{
							case 3://�����ж�
								if(enemy.size()==0)//ս��
								{
									if(gv.activity.bgMusic[1].isPlaying())
									{
										gv.activity.bgMusic[1].pause();
									}
									isOvercome=true;
									//�ж��ɹ�
									gv.isSpecActionState=1;
								}
								break;
							case 4://ɳĮ�籩
								if(tankeList.size()==0&&archie_List.size()==0)//̹�˺͸�����
								{
									if(gv.activity.bgMusic[1].isPlaying())
									{
										gv.activity.bgMusic[1].pause();
									}
									isOvercome=true;
									//�ж��ɹ�
									gv.isSpecActionState=1;
								}
								break;
							case 5://ն���ж�
								if(arsenal.size()==0)//�������
								{
									if(gv.activity.bgMusic[1].isPlaying())
									{
										gv.activity.bgMusic[1].pause();
									}
									isOvercome=true;
									//�ж��ɹ�
									gv.isSpecActionState=1;
								}
								break;
							}
						}
					}
					//------ս��ģʽ�·ɻ���ը--------
					if(gv.isGameMode==0&&gv.plane.blood<=0)//����ɻ���Ѫû�ˣ���ɻ�׹��
					{
						if(gv.activity.bgMusic[1].isPlaying())
						{
							gv.activity.bgMusic[1].pause();
						}
						isCrash=true;
						isFireOn=false;
						gradeArray[0]=mapId;
						gradeArray[2]=time/1000;//��ʱ����
						Date d=new Date();
						String month=d.getMonth()+1>=10?d.getMonth()+1+"":"0"+d.getMonth()+1;
						String day=d.getDate()>=10?d.getDate()+"":"0"+d.getDate();
						String date=d.getYear()+1900+""+month+""+day;
						String sql="insert into plane values("+"'"+gradeArray[0]+""+"'"+",'"+gradeArray[1]+""+"'," +
								"'"+gradeArray[2]+""+"','"+date+"');";
				        SQLiteUtil.insert(sql);
					}
					//-----ս��ģʽ-------�ɹ�����
					if(gv.isGameMode==0&&arsenal.size()==0&&tankeList.size()==0&&archie_List.size()==0&&enemy.size()==0)
					{
						if(gv.activity.bgMusic[1].isPlaying())
						{
							gv.activity.bgMusic[1].pause();
						}
						gradeArray[0]=mapId;
						gradeArray[2]=time/1000;//��ʱ����
						Date d=new Date();
						String month=d.getMonth()+1>=10?d.getMonth()+1+"":"0"+d.getMonth()+1;
						String day=d.getDate()>=10?d.getDate()+"":"0"+d.getDate();
						String date=d.getYear()+1900+""+month+""+day;
						String sql="insert into plane values("+"'"+gradeArray[0]+""+"'"+",'"+gradeArray[1]+""+"'," +
										"'"+gradeArray[2]+""+"','"+date+"');";
				        SQLiteUtil.insert(sql);
						isOvercome=true;
					}
					if(isCrash)//����ɻ�׹��
					{
						isFireOn=false;
						archie_List.clear();//����������
						bomb_List.clear();//�ڵ�����
						archie_bomb_List.clear();//�������ڵ�����
						bullet_List.clear();//�ɻ�������ӵ�����
						flag_go=false;//�ص����߳�
				    		new Thread()
				    		{
				    			int time=0;
				    			int thistime=0;
				    			boolean isnoStart;
				    			public void run()
				    			{
				    				isno_draw_arsenal=false;//�����ƾ����
				    				while(time<15000)
				    				{
				    					time+=50;
				    					planeY=	isYachtHeadCollectionsWithLandPaodan(PLANE_X,PLANE_Y-PLANE_Y_R,PLANE_Z);//�鿴�Ƿ���ײ����
				    					if(planeY<0&&!isnoStart)//���û����ײ���أ��ɻ�׹���½�
				    					{
				    						PLANE_Y-=10;
				    						tx=PLANE_X;//�����Ŀ��λ�ø��ŷɻ��ߣ������λ�ò���
				    			    		ty=PLANE_Y;
				    			    		tz=PLANE_Z;
				    					}
				    					else 
				    					{//����Ѿ�׹�ٵ����棬�����������
				    						if(!isnoStart)
				    						{
				    							thistime=time;//��¼���������˵�ʱ��
				    							isnoStart=true;
					    						ty=PLANE_Y=isYachtHeadCollectionsWithLandPaodan(PLANE_X,PLANE_Y-PLANE_Y_R,PLANE_Z)+PLANE_Y_R;
				    						}
				    					}
				    					if(isnoStart&&time-thistime<=2000)
				    					{
				    						ELEVATION_CAMERA=30;
				    						cx=(float)(tx+Math.cos(Math.toRadians(ELEVATION_CAMERA))*Math.sin(Math.toRadians(DIRECTION_CAMERA))*Crash_DISTANCE_start);//�����x���� 
				    					    cz=(float)(tz+Math.cos(Math.toRadians(ELEVATION_CAMERA))*Math.cos(Math.toRadians(DIRECTION_CAMERA))*Crash_DISTANCE_start);//�����z���� 
				    					    cy=(float)(ty+Math.sin(Math.toRadians(ELEVATION_CAMERA))*Crash_DISTANCE_start);//�����y����
				    					    if(Crash_DISTANCE_start<Crash_DISTANCE_stop*4){
				    					    	 Crash_DISTANCE_start+=20f;
				    					    	 rotationAngle_Plane_Y=DIRECTION_CAMERA+=5;//�����Χ�ŷɻ�ת��
				    					    }
				    					    else
				    					    {
				    					    	rotationAngle_Plane_Y=DIRECTION_CAMERA+=5;//�����Χ�ŷɻ�ת��
				    					    }
				    					   if(time%800==0)
				    					   {
				    						   baoZhaList.add(new DrawBomb(bombRectr,PLANE_X,PLANE_Y,PLANE_Z));//��ӱ�ըЧ��
				    						   gv.activity.playSound(1,0);
				    					   }
				    					}
				    					else if(isnoStart&&time-thistime>2000&&time-thistime<=5500)//��������ը
				    					{
				    						if(time-thistime<4000)
				    						{
				    							 if(time%150==0)
				    							 {
						    						   BaoZha_scal+=0.3f;
						    					 }
				    						}
				    						else if(time-thistime>=4000&&time-thistime<=4500)
				    						{
				    							isno_draw_plane=false;//�������ɻ�
				    							isno_draw_arsenal=false;//�����ƾ����
				    							if(time%100==0){
					    							 BaoZha_scal-=3.5f;
					    							 if(BaoZha_scal<0){
					    								 baoZhaList.add(new DrawBomb(bombRect,PLANE_X,PLANE_Y,PLANE_Z));//��ӱ�ըЧ��
					    								 BaoZha_scal=0;
					    							 }
						    					   }
				    						}else if(time-thistime>=4500)
				    						{
				    							 isCrashCartoonOver=true;//�����������
				    							 isno_draw_plane=false;//�����Ʒɻ�
					    						 gv.activity.playSound(0,1);
					    						 break;
				    						}
				    					}
				    					try 
				    					{
											Thread.sleep(50);
										}
				    					catch (InterruptedException e) 
				    					{
											e.printStackTrace();
										}
				    				}
				    				isCrashCartoonOver=true;//�����������
				    			}
				    		}.start();
						break;
					}
					if(isOvercome)//��������Ϸ
					{
						isFireOn=false;
						flag_go=false;
						archie_List.clear();//����������
						bomb_List.clear();//�ڵ�����
						archie_bomb_List.clear();//�������ڵ�����
						bullet_List.clear();//�ɻ�������ӵ�����
						isCrashCartoonOver=true;//�����������
						break;
					}
					//�����up�����µĻ�
					if((!isPlaneNoUp)&&(keyState&0x1)!=0)
					{
						//�ɻ�������
						if(rotationAngle_Plane_X<PLANE_UP_ROTATION_DOMAIN_X)
						{
							rotationAngle_Plane_X+=PLANE_ROTATION_SPEED_SPAN_X;
						}
						//�����������
						if(ELEVATION_CAMERA>ELEVATION_CAMERA_DOWN)
						{
							ELEVATION_CAMERA-=ELEVATION_CAMERA_SPAN;
						}
					}
					//�����down�����µĻ�
					else if((keyState&0x2)!=0)
					{
						//�ɻ����¸�
						if(rotationAngle_Plane_X>PLANE_DOWN_ROTATION_DOMAIN_X)
						{
							rotationAngle_Plane_X-=PLANE_ROTATION_SPEED_SPAN_X;
						}
						//��������¸�
						if(ELEVATION_CAMERA<ELEVATION_CAMERA_UP)
						{
							ELEVATION_CAMERA+=ELEVATION_CAMERA_SPAN;
						}
					}
					//���up��down��û�а��µĻ�,��ɻ�ƽ�з���   �������ƽ
					else if((isPlaneNoUp||((keyState&0x1)==0)&&((keyState&0x2)==0)))
					{
						if(isno_Lock)
						{
							if(!isno_adjust){//
								isno_adjust=true;
								rotationAngle_Plane_Y=(float) Math.toDegrees(Math.atan(nx/nz));
						if(nx==0&&nz==0)
						{
							rotationAngle_Plane_Y=0;
							
						}		
						if(nz>0)
						{
							rotationAngle_Plane_Y+=180;
						}
								rotationAngle_Plane_X=(float) Math.toDegrees(Math.atan(ny/Math.sqrt(nx*nx+nz*nz)));
								DIRECTION_CAMERA=rotationAngle_Plane_Y;//���������ͷɻ����з���һ��
							}
						}
						else
						{
							isno_adjust=false;
							//�ɻ�У��
							if(Math.abs(rotationAngle_Plane_X)<PLANE_ROTATION_SPEED_SPAN_X+0.1f)//����ɻ��ĸ����Ƕ�С��һ����ֵ,����Ϊ0
							{
								rotationAngle_Plane_X=0;//�ɻ�ǰ���ƽ
							}
							else if(rotationAngle_Plane_X>0)//����ɻ���������λ��,��Ƕȼ���
							{
								rotationAngle_Plane_X-=PLANE_ROTATION_SPEED_SPAN_X;
							}
							else//����ɻ����ڸ���λ��,��Ƕ�����
							{
								rotationAngle_Plane_X+=PLANE_ROTATION_SPEED_SPAN_X;
							}
							//�����У��
							if(Math.abs(ELEVATION_CAMERA-ELEVATION_CAMERA_ORI)<ELEVATION_CAMERA_SPAN+0.1f)//����ɻ��ĸ����Ƕ�С��һ����ֵ,����Ϊ0
							{
								ELEVATION_CAMERA=ELEVATION_CAMERA_ORI;//��������ǽ�����
							}
							else if(ELEVATION_CAMERA-ELEVATION_CAMERA_ORI>0)//����������������λ��,��Ƕȼ���
							{
								ELEVATION_CAMERA-=ELEVATION_CAMERA_SPAN;
							}
							else//�����������ڸ���λ��,��Ƕ�����
							{
								ELEVATION_CAMERA+=ELEVATION_CAMERA_SPAN;
							}
						}
					}
					if(!isVideo)
					{
						//�����left�����µĻ�
						if((keyState&0x4)!=0)
						{
							//��ת�ٶȵı���
							float temp_ratio=Math.abs(gv.activity.directionDotXY[0]-gv.activity.lr_domain)/30;
							//�������ת
							DIRECTION_CAMERA+=DIRECTION_CAMERA_SPAN*temp_ratio;
							//�ɻ�������ת
							rotationAngle_Plane_Y+=DIRECTION_CAMERA_SPAN*temp_ratio;
							//�ɻ��Ӿ���������ת��б
							rotationAngle_Plane_Z=-gv.activity.directionDotXY[0]*5f;
						}
						//�����right�����µĻ�
						else if((keyState&0x8)!=0)
						{
							float temp_ratio=Math.abs(gv.activity.directionDotXY[0]-gv.activity.lr_domain)/30;
							//�������ת
							DIRECTION_CAMERA-=DIRECTION_CAMERA_SPAN*temp_ratio;
							//�ɻ�������ת
							rotationAngle_Plane_Y-=DIRECTION_CAMERA_SPAN*temp_ratio;
							//�ɻ��Ӿ���������ת��б
							rotationAngle_Plane_Z=-gv.activity.directionDotXY[0]*5f;
						}
						//��û��������û������,�ɻ����ư���
						else if((keyState&0x3)==0)
						{
							//�ɻ�������бУ��
							if(Math.abs(rotationAngle_Plane_Z)<PLANE_ROTATION_SPEED_SPAN_Z+0.5f)
							{
								rotationAngle_Plane_Z=0;
							}
							else if(rotationAngle_Plane_Z<0)
							{
								rotationAngle_Plane_Z+=PLANE_ROTATION_SPEED_SPAN_Z;
							}
							else
							{
								rotationAngle_Plane_Z-=PLANE_ROTATION_SPEED_SPAN_Z;
							}
							//�ɻ��Ӿ���direction�������directionУ��
							if(Math.abs(rotationAngle_Plane_Y-DIRECTION_CAMERA)<2.1f)
							{
								rotationAngle_Plane_Y=DIRECTION_CAMERA;
							}
							else if(rotationAngle_Plane_Y-DIRECTION_CAMERA<0)
							{
								rotationAngle_Plane_Y+=2;
							}
							else
							{
								rotationAngle_Plane_Y-=2;
							}
						}	
					}
					planezAngle+=10;//�ɻ�������ת���Ƕ�
					planezAngle%=360;
					//�ɻ���ǰ��ʻ,ȷ���ɻ���λ��
		    		PLANE_X-=Math.sin(Math.toRadians(rotationAngle_Plane_Y))*Math.cos(Math.toRadians(rotationAngle_Plane_X))*PLANE_MOVE_SPAN;
		    		PLANE_Z-=Math.cos(Math.toRadians(rotationAngle_Plane_Y))*Math.cos(Math.toRadians(rotationAngle_Plane_X))*PLANE_MOVE_SPAN;
		    		float PLANE_Yt=(float) (PLANE_Y+Math.sin(Math.toRadians(rotationAngle_Plane_X))*PLANE_MOVE_SPAN);
		    		//�жϷɻ����ܳ�ȥ
		    		int gellSize=2;
		    		if(PLANE_X<(-gellSize+0.5f)*WIDTH_LALNDFORM)
		    		{
		    			PLANE_X=(-gellSize+0.5f)*WIDTH_LALNDFORM;
		    		}
		    		else if(PLANE_X>(MapArray[mapId].length+gellSize-0.5f)*WIDTH_LALNDFORM)
		    		{
		    			PLANE_X=(MapArray[mapId].length+gellSize-0.5f)*WIDTH_LALNDFORM;
		    		}
		    		if(PLANE_Z<(-gellSize+0.5f)*WIDTH_LALNDFORM)
		    		{
		    			PLANE_Z=(-gellSize+0.5f)*WIDTH_LALNDFORM;
		    		}
		    		else if(PLANE_Z>(MapArray[mapId].length+gellSize-0.5f)*WATER_HEIGHT)
		    		{
		    			PLANE_Z=(MapArray[mapId].length+gellSize-0.5f)*WATER_HEIGHT;
		    		}
		    		try
		    		{
		    			//��ײ���
			    		if(!isnoHitHill(PLANE_X,PLANE_Yt,PLANE_Z,rotationAngle_Plane_Y,rotationAngle_Plane_X))
			    		{
			    			PLANE_Y=PLANE_Yt;
			    		}
			    		else
			    		{
			    			if(!isVideo)
			    			{
			    				gv.plane.blood-=ArchieArray[mapId][9][4];
			    				gv.activity.playSound(0,0);//���ŷɻ���ը������
			    				gv.activity.shake();//�ֻ���һ��
			    			}
			    		}
		    		}catch(Exception e)
		    		{
		    			e.printStackTrace();
		    		}
		    		//����Էɻ��ĸ߶Ƚ��м��,�������һ���߶Ⱥ�,��ɻ���������
		    		if(PLANE_Y>=PLANE_HEIGHT_MAX)
		    		{
		    			isPlaneNoUp=false;
		    			PLANE_Y=PLANE_HEIGHT_MAX;
		    		}
		    		else
		    		{
		    			isPlaneNoUp=false;
		    		}
		    		//������ת�Ƕ�
		    		rotationAngle_SkyBall+=0.1f;
		    		//��������״�ָ�����ת�Ƕ�
		    		RADAR_DIRECTION=rotationAngle_Plane_Y;
					//���¼����������λ��
		    		tx=PLANE_X;
		    		ty=PLANE_Y;
		    		tz=PLANE_Z;
		    		cx=(float)(tx+Math.cos(Math.toRadians(ELEVATION_CAMERA))*Math.sin(Math.toRadians(DIRECTION_CAMERA))*DISTANCE);//�����x���� 
				    cz=(float)(tz+Math.cos(Math.toRadians(ELEVATION_CAMERA))*Math.cos(Math.toRadians(DIRECTION_CAMERA))*DISTANCE);//�����z���� 
				    cy=(float)(ty+Math.sin(Math.toRadians(ELEVATION_CAMERA))*DISTANCE);//�����y����
		    		//�����Ƿɻ�λ�õ�����
		    		gv.plane.arsenal_x=-scalMark*BUTTON_RADAR_BG_WIDTH*(MapArray[mapId].length*WIDTH_LALNDFORM/2-tx)/(MapArray[mapId].length*WIDTH_LALNDFORM/2);
		    		gv.plane.arsenal_y=scalMark*BUTTON_RADAR_BG_WIDTH*(MapArray[mapId].length*WIDTH_LALNDFORM/2-tz)/(MapArray[mapId].length*WIDTH_LALNDFORM/2);
		    		if(gv.plane.arsenal_x*gv.plane.arsenal_x+gv.plane.arsenal_y*gv.plane.arsenal_y>BUTTON_RADAR_BG_WIDTH*BUTTON_RADAR_BG_WIDTH*0.4f*0.4f){
		    			gv.plane.arsenal_x=(float) (gv.plane.arsenal_x*0.4f*
		    			(BUTTON_RADAR_BG_WIDTH/Math.sqrt(gv.plane.arsenal_x*gv.plane.arsenal_x+gv.plane.arsenal_y*gv.plane.arsenal_y)));
		    			gv.plane.arsenal_y=(float) (gv.plane.arsenal_y*0.4f*
		    			(BUTTON_RADAR_BG_WIDTH/Math.sqrt(gv.plane.arsenal_x*gv.plane.arsenal_x+gv.plane.arsenal_y*gv.plane.arsenal_y)));
		    		}
		    		
				    try
				    {
				    	Collections.sort(treeList);//��������
				    }
				    catch(Exception e)
				    {
				    	e.printStackTrace();
				    }
				    // �������ĳ���
				    for(Tree tree:treeList)
				    {
				    	try
				    	{
				    		tree.calculateBillboardDirection();
				    	}
				    	catch(Exception e)
				    	{
				    		e.printStackTrace();
				    	}
			    	}
				    //������з����ӵ�
				    if(WEAPON_INDEX==0&&isFireOn&&(System.nanoTime()-oldTimeBullet>150000000))//�����������������ӵ�
				    {
		        		//���б�������ӵ����� 
		        		try
		        		{
		        			if(bullet_number>0)
		        			{
		        				bullet_List.add(new BulletForControl(gv,gv.bullet_rect, PLANE_X, PLANE_Y, PLANE_Z, 
			        					rotationAngle_Plane_X, rotationAngle_Plane_Y,rotationAngle_Plane_X,
			        					rotationAngle_Plane_Y,rotationAngle_Plane_Z,0,0));
			        			bullet_List.add(new BulletForControl(gv,gv.bullet_rect, PLANE_X, PLANE_Y, PLANE_Z, 
			        					rotationAngle_Plane_X, rotationAngle_Plane_Y,rotationAngle_Plane_X,
			        					rotationAngle_Plane_Y,rotationAngle_Plane_Z,1,0));
			        			gv.activity.playSound(3,0);//�����ӵ�������
			        			bullet_number-=2;
			        			Collections.sort(bullet_List);
		        			}
		        		}
		        		catch(Exception ee)
		        		{
		        			ee.printStackTrace();
		        		}
		        		oldTimeBullet=System.nanoTime();
				    }
				    //����ɻ����з����ڵ�
				    if(WEAPON_INDEX==1&&isFireOn&&(System.nanoTime()-oldTimeBomb>1000000000))//���﷢���ڵ�
	        		{
	        			if(bomb_number>0)
	        			{
			        		//���б�������ӵ�����
			        		try
			        		{
			        			gv.activity.shake();
			        			bomb_List.add(new BombForControl(gv,gv.bullet_ball, PLANE_X, PLANE_Y, PLANE_Z, 
			        					rotationAngle_Plane_X, rotationAngle_Plane_Y,rotationAngle_Plane_X,rotationAngle_Plane_Y,
			        					rotationAngle_Plane_Z));
			        		}
			        		catch(Exception ee)
			        		{
			        			ee.printStackTrace();
			        		}
			        		//�趨�����ڵ��Ļ���λ��
			        		fire_index=(fire_index+1)%2;
			        		gv.activity.playSound(4,0);//��������
			        		bomb_number--;
			        		isno_Vibrate=true;
			        		oldTimeBomb=System.nanoTime();
	        			}
	        		}
				    //�Ը����ڽ�����ģ��
				    if(!isVideo){//�������Ƶ���Ž����򲻶������ģ��
				    	 //���ӵ����н���ģ��
					    for(int i=0;i<cop_bullet_List.size();i++)
					    {
					    	try
					    	{
					    		cop_bullet_List.get(i).go();
					    	}
					    	catch(Exception e)
					    	{
					    		e.printStackTrace();
					    	}
					    }
				    	//���ڵ��ķ��н���ģ��
					    for(int i=0;i<cop_bomb_List.size();i++)
					    {
					    	try
					    	{
					    		cop_bomb_List.get(i).go();
					    	}
					    	catch(Exception e)
					    	{
					    		e.printStackTrace();
					    	}
					    }
					    minimumdistance=Lock_Distance;//�����������
				    	isno_Lock=false;//�Ѿ��ж�����������־��Ϊfalse
				    	//�ɻ����еķ�������
				    	directionX=-(float) (Math.cos(Math.toRadians(rotationAngle_Plane_X))*Math.sin(Math.toRadians(rotationAngle_Plane_Y)));
				    	directionY=(float) (Math.sin(Math.toRadians(rotationAngle_Plane_X)));
				    	directionZ=-(float) (Math.cos(Math.toRadians(rotationAngle_Plane_X))*Math.cos(Math.toRadians(rotationAngle_Plane_Y)));
				    	
				    	for(Arsenal_House ah:arsenal)
				    	{
				    		//��������
				    		ah.calculateBillboardDirection();//�鿴������Ƿ�����
				    	}
				    	 //��̹�˽���ģ��
					    for(int i=0;i<tankeList.size();i++)
					    {
					    	try
					    	{
					    		tankeList.get(i).tank_go();
					    	}
					    	catch(Exception e)
					    	{
					    		e.printStackTrace();
					    	}
					    }
					    //�Ը����ڽ���ģ��
				    	for(int i=0;i<cop_archie_List.size();i++)
					    {
					    	try
					    	{
					    		cop_archie_List.get(i).go();
					    	}
					    	catch(Exception e)
					    	{
					    		e.printStackTrace();
					    	}
					    }
				    	
				    	//�Եл�ģ��
					    for(int i=0;i<enemy.size();i++)
					    {
					    	try
					    	{
					    		enemy.get(i).go();
					    	}
					    	catch(Exception e)
					    	{
					    		e.printStackTrace();
					    	}
					    }
					    //��̹���ڵ�����ģ��
					    for(int i=0;i<tank_bomb_List.size();i++)
					    {
					    	try
					    	{
					    		tank_bomb_List.get(i).go_tank();
					    	}
					    	catch(Exception e)
					    	{
					    		e.printStackTrace();
					    	}
					    }
					    //�Ը������ڵ�����ģ��
					    for(int i=0;i<cop_archie_bomb_List.size();i++)
					    {
					    	try
					    	{
					    		cop_archie_bomb_List.get(i).go_archie();
					    	}
					    	catch(Exception e)
					    	{
					    		e.printStackTrace();
					    	}
					    }
				    } 
				}
			}
			else//-----------------�˵�����-------------------------------
			{
				//������й��Բ���
				if(gv.hasInertia)//����й���
				{
					gv.curr_angle_speed=gv.curr_angle_speed+gv.curr_acceleratedSpeed;//���㵱ǰ���ٶ�
					if(Math.abs(gv.curr_angle_speed)>2f)
					{
						if(gv.missile_rotation+gv.curr_angle_speed>0)
						{
							gv.missile_rotation=0;
							gv.hasInertia=false;
							gv.curr_angle_speed=0;
						}
						else if(gv.missile_rotation+gv.curr_angle_speed<-225)
						{
							gv.missile_rotation=-225;
							gv.hasInertia=false;
							gv.curr_angle_speed=0;
						}
						else
						{
							gv.missile_rotation+=gv.curr_angle_speed;
						}
					}
					else
					{
						gv.curr_angle_speed=0;
						gv.hasInertia=false;
						gv.auto_adjust=true;//�����������ܵ���
					}
				}
				if(gv.auto_adjust)//������Ҫ�������ܵ���
				{
					gv.curr_menu_index=(int) (Math.abs(gv.missile_rotation-22.5f)/45%8);
					if(Math.abs(gv.missile_rotation+gv.curr_menu_index*45)<3.1f)
					{
						gv.activity.playSound(9,0);//���Ų˵���ת������
						gv.missile_rotation=-gv.curr_menu_index*45;
						gv.auto_adjust=false;//���ٵ���
					}
					else if(gv.missile_rotation+gv.curr_menu_index*45>3.1f)
					{
						gv.missile_rotation-=3f;
					}
					else if(gv.missile_rotation+gv.curr_menu_index*45<3.1f)
					{
						gv.missile_rotation+=3f;
					}
				}
				//������е��������ģ��
				if(gv.isMissileDowning)
				{
					gv.missile_ZOffset_Speed+=gv.missile_ZOffset_AcceSpeed;
					gv.missile_ZOffset=gv.missile_ZOffset+gv.missile_ZOffset_Speed;
					if(gv.missile_ZOffset<-100)
					{
						gv.activity.playSound(2,0);
						gv.isDrawBaozha=true;//���Ʊ�ըͼ
						gv.isMissileDowning=false;
						gv.missile_rotation=0;
					}
				}
				//����Ե����˵���ť���ƽ���ģ��
				if(gv.menu_button_move)
				{
					gv.menu_button_XOffset+=gv.menu_button_speed;
					if(gv.menu_button_XOffset>MENU_BUTTON_WIDTH/2+gv.ratio)
					{
						gv.menu_button_move=false;
						gv.isMissileDowning=true;
						gv.activity.playSound(4,0);//���ŵ������������
					}
				}
				//����Ե�����ը����ģ��
				if(gv.isDrawBaozha)
				{
					gv.baozha_ratio+=gv.baozha_increase;
					if(gv.baozha_ratio>1.0f)
					{
						gv.isMenuLevel=2;//��������˵�
						gv.isDrawBaozha=false;
						gv.menu_button_XOffset=0;//�����˵���ť��λ�ø�λ
						gv.baozha_ratio=0;
						gv.missile_ZOffset_Speed=0;
						gv.missile_ZOffset=gv.missile_ZOffset_Ori;
					}
				}
				//����Թزս���ģ��
				if(gv.doorState==0)
				{
					gv.door_YOffset-=gv.door_YSpan;//�������˶�
					if(gv.door_YOffset<0.5f)//�����Ź�����
					{
						gv.door_YOffset=0.5f;
						gv.door_YSpan=-gv.door_YSpan;
						gv.doorState=2;//������Ϊ�ر�
						
					}
					else if(gv.door_YOffset>1.5f)//�����Ŵ���
					{
						gv.door_YOffset=1.5f;
						gv.door_YSpan=-gv.door_YSpan;
						gv.doorState=1;//������Ϊ��
					}
				}
				//���ｫ�����˵���ת��Exit��
				if(gv.moveToExit)
				{
					gv.missile_rotation-=40;
					if(gv.missile_rotation<-225)
					{
						gv.activity.shake();
						gv.missile_rotation=-225;
						gv.moveToExit=false;
						gv.curr_menu_index=5;//��ǰΪExit�˵���
					}
				}
				//����԰����������ģ��
				if(gv.doorState==2&&gv.curr_menu_index==3)
				{
					gv.help_YOffset+=0.004f;
					if(gv.help_YOffset-HELP_HEIGHT/2>1)
					{
						gv.help_YOffset=-1-HELP_HEIGHT/2;
					}
					if(gv.help_YOffset+HELP_HEIGHT/2<-1)
					{
						gv.help_YOffset=1+HELP_HEIGHT/2;
					}
				}
				//����Թ��ڽ������ģ��
				if(gv.doorState==2&&gv.curr_menu_index==4)
				{
					gv.about_YOffset+=0.004f;
					if(gv.about_YOffset-ABOUT_HEIGHT/2>1)
					{
						gv.about_YOffset=-1-ABOUT_HEIGHT/2;
					}
					if(gv.about_YOffset+ABOUT_HEIGHT/2<-1)
					{
						gv.about_YOffset=1+ABOUT_HEIGHT/2;
					}
				}
				//������ѡ��ɻ�������ʱ
				if(2==gv.isMenuLevel)
				{
					gv.planeRotate=(gv.planeRotate+1)%360;
				}
				
			}
			try
			{
				Thread.sleep(50);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}	
		}
	}
	public boolean isnoHitHill(float fjX,float fjY,float fjZ,float yAngle,float xAngle)
	{
		float fjLeftX,fjRightX;//,fjBackLeftX,fjBackRightX;//�ɻ���Χ���ĸ����������
		float fjLeftY,fjRightY;//,fjBackLeftY,fjBackRightY;
		float fjLeftZ,fjRightZ;//,fjBackLeftZ,fjBackRightZ;
		fjLeftX=fjX-(float)(Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle+ANGLE_X_Z))
				*PLANE_X_R/(Math.sin(Math.toRadians(ANGLE_X_Z))));;
		fjLeftY=fjY-TRANSFER_Y-PLANE_Y_R+(float)(Math.sin(Math.toRadians(xAngle))
				*PLANE_X_R/(Math.sin(Math.toRadians(ANGLE_X_Z))));
		fjLeftZ=fjZ-(float)(Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle+ANGLE_X_Z))
				*PLANE_X_R/(Math.sin(Math.toRadians(ANGLE_X_Z))));
		if(isYachtHeadCollectionsWithLand(fjLeftX,fjLeftY,fjLeftZ)){
			return true;		
		}
		
		fjRightX=fjX-(float)(Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle-ANGLE_X_Z))
				*PLANE_X_R/(Math.sin(Math.toRadians(ANGLE_X_Z))));;
		fjRightY=fjY-TRANSFER_Y-PLANE_Y_R+(float)(Math.sin(Math.toRadians(xAngle))
				*PLANE_X_R/(Math.sin(Math.toRadians(ANGLE_X_Z))));
		fjRightZ=fjZ-(float)(Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle-ANGLE_X_Z))
				*PLANE_X_R/(Math.sin(Math.toRadians(ANGLE_X_Z))));
		
		if(isYachtHeadCollectionsWithLand(fjRightX,fjRightY,fjRightZ)){
			return true;		
		}
		return false;
	}
	//�ж�ĳһ���Ƿ���ɽ������
	public  boolean isYachtHeadCollectionsWithLand(float ctx,float ctyh,float ctz)
	{
		//�ж����Ƿ�ײ�������
		for(Arsenal_House as:arsenal)
		{
			if(ctyh>as.ty&&ctyh<as.ty+ARSENAL_Y//��������ײ��
					&&ctx>as.tx-ARSENAL_X&&ctx<as.tx+ARSENAL_X
					&&ctz>as.tz-ARSENAL_Z&&ctz<as.tz+ARSENAL_Z){
				gv.plane.blood-=ArchieArray[mapId][9][3];
				PLANE_Y=as.ty+ARSENAL_Y;
				isno_Hit=true;
				return true;
			}	
		}
		//�ж��Ƿ���ƽ������
		for(int i=0;i<ArchieArray[mapId][4].length/2;i++)
		{
			float positionX=ArchieArray[mapId][4][2*i]*WIDTH_LALNDFORM;//,LAND_HIGHEST+house_height/2, ArchieArray[mapId][4][2*i+1]*WIDTH_LALNDFORM
			float positionZ=ArchieArray[mapId][4][2*i+1]*WIDTH_LALNDFORM;
			if
			(
					ctx>(positionX-house_length/2)&&ctx<(positionX+house_length/2)&&
					ctz>(positionZ-house_width/2)&&ctz<(positionZ+house_width/2)&&
					ctyh>LAND_HIGHEST&&ctyh<(LAND_HIGHEST+house_height)
			)
			{
				gv.plane.blood-=ArchieArray[mapId][9][4];
				PLANE_Y+=house_height;
				isno_Hit=true;
				return true;
			}
		}
		
		float UNIT_SIZE=LAND_UNIT_SIZE;//½��ÿһ�����ӵĴ�С
		int COLS=LANDS_HEIGHT_ARRAY[0].length;//WATER_COLS;//����������
		int ROWS=LANDS_HEIGHT_ARRAY[0].length;//WATER_ROWS;
		
		float cellCount=0;//��Ҫ�ƶ��ĸ�����
		
		float smallBlockLength=WIDTH_LALNDFORM;//UNIT_SIZE*COLS;//ÿһ���и��ӵĴ�С
		ctx=ctx+cellCount*smallBlockLength;
		ctz=ctz+cellCount*smallBlockLength;//����ͼ�ƶ���������������� �˴�ֻ���ĸ��и�����ɵĵ�ͼ
		int col=(int)(ctx/smallBlockLength);//�ɻ����ڵ�����
		int row=(int)(ctz/smallBlockLength);
		if(col<0||row<0||col>MapArray[mapId].length-1||row>MapArray[mapId].length-1)
		{
			if(ctyh<0){
				PLANE_Y+=0-ctyh;
				return true;//����ǵ�ͼ�����Ϊ�����С��ˮ����Ϊ��ײ������
			}else{
				return false;
			}
		}
		ctx=ctx-col*smallBlockLength;
		ctz=ctz-row*smallBlockLength;//���õ��ƶ��������ԭ������괦
		int mapArrayId=MapArray[mapId][row][col];//�˴����ô�д
		float moderXZ=0;//�м����
		switch (mapArrayId) {
		case 4://��ת��ʮ��
			moderXZ=ctz;
			ctz=ctx;
			ctx=smallBlockLength-moderXZ;
			mapArrayId=0;
			break;
		case 5://��ת180��
			ctx=smallBlockLength-ctx;
			ctz=smallBlockLength-ctz;
			mapArrayId=0;
			break;
		case 6://��ת270��
			moderXZ=ctz;			
			ctz=smallBlockLength-ctx;
			ctx=moderXZ;
			mapArrayId=0;
			break;
			
		case 7://��ת��ʮ��
			moderXZ=ctz;
			ctz=ctx;
			ctx=smallBlockLength-moderXZ;
			mapArrayId=1;
			break;
		case 8://��ת180��
			ctx=smallBlockLength-ctx;
			ctz=smallBlockLength-ctz;
			mapArrayId=1;
			break;
		case 9://��ת270��
			moderXZ=ctz;			
			ctz=smallBlockLength-ctx;
			ctx=moderXZ;
			mapArrayId=1;
			break;
			
			
		case 10://��ת��ʮ��
			moderXZ=ctz;
			ctz=ctx;
			ctx=smallBlockLength-moderXZ;
			mapArrayId=2;
			break;
		case 11://��ת180��
			ctx=smallBlockLength-ctx;
			ctz=smallBlockLength-ctz;
			mapArrayId=2;
			break;
		case 12://��ת270��
			moderXZ=ctz;			
			ctz=smallBlockLength-ctx;
			ctx=moderXZ;
			mapArrayId=2;
			break;
		case 13:
		case 21:
			if(ctyh<LAND_HIGHEST)
			{
				PLANE_Y+=LAND_HIGHEST-ctyh;
				return true;
			}
			else
			{
				return false;
			}
		case 14:
			if(ctyh<0)
			{
				PLANE_Y+=0-ctyh;
				return true;
			}
			else
			{
				return false;
			}
		case 15:
			mapArrayId=4;
			break;
		case 16:
			mapArrayId=5;
			break;
		case 17:
			mapArrayId=6;
			break;
		case 18:
			moderXZ=ctz;			
			ctz=smallBlockLength-ctx;
			ctx=moderXZ;
			mapArrayId=3;
			break;
		case 19:
			moderXZ=ctz;			
			ctz=smallBlockLength-ctx;
			ctx=moderXZ;
			mapArrayId=6;
			break;
		case 20:
			moderXZ=ctz;			
			ctz=smallBlockLength-ctx;
			ctx=moderXZ;
			mapArrayId=5;
			break;
		
		default:
			break;
		}
		try
		{
			int tempCol=(int)(ctx/UNIT_SIZE)%7;//�õ�����Сģ�������
		    int tempRow=(int)(ctz/UNIT_SIZE)%7;
			float yArray[][]=Constant.LANDS_HEIGHT_ARRAY[mapArrayId];//����ģ��Id�ó���ģ���Y������
			float x0=tempCol*UNIT_SIZE;
		    float z0=tempRow*UNIT_SIZE; 
		    float y0=yArray[tempRow][tempCol];
	    
		    float x1=x0+UNIT_SIZE;
		    float z1=z0;
		    float y1=yArray[tempRow][(tempCol+1)%COLS];
		    
		    float x2=x0+UNIT_SIZE;
	        float z2=z0+UNIT_SIZE;
	        float y2=yArray[(tempRow+1)%ROWS][(tempCol+1)%COLS];
   
		    float x3=x0;
		    float z3=z0+UNIT_SIZE;
		    float y3=yArray[(tempRow+1)%ROWS][(tempCol)%COLS];    
		    //��ͷ����½�ظ߶�
		    float cty=0;
		    if(isInTriangle(x0,z0,x1,z1,x3,z3,ctx,ctz))
		    {//�жϸõ��Ƿ�λ��0-1-3������
		    	//��0-1-3���ڴ�ͷ���ĸ߶�
		    	cty=fromXZToY
			    (
				    	x0,y0,z0,
				    	x1,y1,z1,
				    	x3,y3,z3,
				    	ctx,ctz
				 );
		    }
		    else if(isInTriangle(x2,z2,x3,z3,x1,z1,ctx,ctz))
		    {
		    	//��1-2-3���ڸĵ㴦�ĸ߶�
		    	cty=fromXZToY
			    (
				    	x1,y1,z1,
				    	x2,y2,z2,
				    	x3,y3,z3,
				    	ctx,ctz
				);
		    }	    
		    if(cty>ctyh)
		    {//���ɻ�����½�ص��ڷɻ��߶��򷵻�true
		    	PLANE_Y+=cty-ctyh;
		    	return true;
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	    return false;
	}
	
	//�ж�ĳһ���Ƿ���ɽ������
	public static float isYachtHeadCollectionsWithLandPaodan(float ctx,float ctyh,float ctz)
	{
		float UNIT_SIZE=LAND_UNIT_SIZE;//½��ÿһ�����ӵĴ�С
		int COLS=LANDS_HEIGHT_ARRAY[0].length;//WATER_COLS;//����������
		int ROWS=LANDS_HEIGHT_ARRAY[0].length;//WATER_ROWS;
		
		float cellCount=0;//��Ҫ�ƶ��ĸ�����
		
		float smallBlockLength=WIDTH_LALNDFORM;//UNIT_SIZE*COLS;//ÿһ���и��ӵĴ�С
		ctx=ctx+cellCount*smallBlockLength;
		ctz=ctz+cellCount*smallBlockLength;//����ͼ�ƶ���������������� �˴�ֻ���ĸ��и�����ɵĵ�ͼ
		int col=(int)(ctx/smallBlockLength);//�ɻ����ڵ�����
		int row=(int)(ctz/smallBlockLength);
		if(col<0||row<0||col>MapArray[mapId].length-1||row>MapArray[mapId].length-1)
		{
			if(ctyh<0)
			{
				return 0;//����ǵ�ͼ�����Ϊ�����С��ˮ����Ϊ��ײ������
			}
			else
			{
				return -5;
			}
		}
		ctx=ctx-col*smallBlockLength;
		ctz=ctz-row*smallBlockLength;//���õ��ƶ��������ԭ������괦
		int mapArrayId=MapArray[mapId][row][col];//�˴����ô�д
		float moderXZ=0;//�м����
		switch (mapArrayId) 
		{
		case 4://��ת��ʮ��
			moderXZ=ctz;
			ctz=ctx;
			ctx=smallBlockLength-moderXZ;
			mapArrayId=0;
			break;
		case 5://��ת180��
			ctx=smallBlockLength-ctx;
			ctz=smallBlockLength-ctz;
			mapArrayId=0;
			break;
		case 6://��ת270��
			moderXZ=ctz;			
			ctz=smallBlockLength-ctx;
			ctx=moderXZ;
			mapArrayId=0;
			break;
			
		case 7://��ת��ʮ��
			moderXZ=ctz;
			ctz=ctx;
			ctx=smallBlockLength-moderXZ;
			mapArrayId=1;
			break;
		case 8://��ת180��
			ctx=smallBlockLength-ctx;
			ctz=smallBlockLength-ctz;
			mapArrayId=1;
			break;
		case 9://��ת270��
			moderXZ=ctz;			
			ctz=smallBlockLength-ctx;
			ctx=moderXZ;
			mapArrayId=1;
			break;
			
			
		case 10://��ת��ʮ��
			moderXZ=ctz;
			ctz=ctx;
			ctx=smallBlockLength-moderXZ;
			mapArrayId=2;
			break;
		case 11://��ת180��
			ctx=smallBlockLength-ctx;
			ctz=smallBlockLength-ctz;
			mapArrayId=2;
			break;
		case 12://��ת270��
			moderXZ=ctz;			
			ctz=smallBlockLength-ctx;
			ctx=moderXZ;
			mapArrayId=2;
			break;
		case 13:
			if(ctyh<LAND_HIGHEST){
				
				return LAND_HIGHEST;
			}else{
				return -5;
			}
		case 14:
			if(ctyh<0)
			{
				return 0;
			}
			else
			{
				return -5;
			}
		case 15:
			mapArrayId=4;
			break;
		case 16:
			mapArrayId=5;
			break;
		case 17:
			mapArrayId=6;
			break;
		case 18:
			moderXZ=ctz;			
			ctz=smallBlockLength-ctx;
			ctx=moderXZ;
			mapArrayId=3;
			break;
		case 19:
			moderXZ=ctz;			
			ctz=smallBlockLength-ctx;
			ctx=moderXZ;
			mapArrayId=6;
			break;
		case 20:
			moderXZ=ctz;			
			ctz=smallBlockLength-ctx;
			ctx=moderXZ;
			mapArrayId=5;
			break;
		default:
			return -5;
		}
		int tempCol=(int)(ctx/UNIT_SIZE)%7;//�õ�����Сģ�������
	    int tempRow=(int)(ctz/UNIT_SIZE)%7;
	    
		float yArray[][]=Constant.LANDS_HEIGHT_ARRAY[mapArrayId];//����ģ��Id�ó���ģ���Y������
		
		float x0=tempCol*UNIT_SIZE;
	    float z0=tempRow*UNIT_SIZE; 
	    float y0=yArray[tempRow][tempCol];
	    
	    float x1=x0+UNIT_SIZE;
	    float z1=z0;
	    float y1=yArray[tempRow][(tempCol+1)%COLS];
	    
	    float x2=x0+UNIT_SIZE;
        float z2=z0+UNIT_SIZE;
        float y2=yArray[(tempRow+1)%ROWS][(tempCol+1)%COLS];
    
	    float x3=x0;
	    float z3=z0+UNIT_SIZE;
	    float y3=yArray[(tempRow+1)%ROWS][(tempCol)%COLS];    
	    //��ͷ����½�ظ߶�
	    float cty=0;
	    
	    if(isInTriangle(x0,z0,x1,z1,x3,z3,ctx,ctz))
	    {//�жϸõ��Ƿ�λ��0-1-3������
	    	//��0-1-3���ڴ�ͷ���ĸ߶�
	    	cty=fromXZToY
		    (
			    	x0,y0,z0,
			    	x1,y1,z1,
			    	x3,y3,z3,
			    	ctx,ctz
			 );
	    }
	    else if(isInTriangle(x2,z2,x3,z3,x1,z1,ctx,ctz))
	    {
	    	//��1-2-3���ڸĵ㴦�ĸ߶�
	    	cty=fromXZToY
		    (
			    	x1,y1,z1,
			    	x2,y2,z2,
			    	x3,y3,z3,
			    	ctx,ctz
			);
	    }	    
	    if(cty>ctyh)
	    {//���ɻ�����½�ص��ڷɻ��߶��򷵻�true
	    	return cty;
	    }
	    
	   	    
	    return -5;
	}
	
	
	
	//�ж�һ�����Ƿ����������ڵķ���
	//�����㷨˼����������Ҫ���жϵĵ㵽���������������ʸ��1��2��3
	//Ȼ������ʸ�����������������ͬ�����λ���������ڣ�����λ����������
	public static boolean isInTriangle
	(
			//�����ε�һ�����XY����
			float x1,
			float y1,
			//�����εڶ������XY����
			float x2,
			float y2,
			//�����ε��������XY����
			float x3,
			float y3,
			//���жϵ��XY����
			float dx,
			float dy
	)
	{
		//���жϵ㵽�����ε�һ�����ʸ��
		float vector1x=dx-x1;
		float vector1y=dy-y1;
		
		//���жϵ㵽�����εڶ������ʸ��
		float vector2x=dx-x2;
		float vector2y=dy-y2;
		
		//���жϵ㵽�����ε��������ʸ��
		float vector3x=dx-x3;
		float vector3y=dy-y3;
		
		//�����1��2ʸ�������
		float crossProduct1=vector1x*vector2y-vector1y*vector2x;
		
		//�����2��3ʸ�������
		float crossProduct2=vector2x*vector3y-vector2y*vector3x;
		
		//�����3��1ʸ�������
		float crossProduct3=vector3x*vector1y-vector3y*vector1x;
		
		if(crossProduct1<0&&crossProduct2<0&&crossProduct3<0)
		{//���������ͬ�ŷ���true
			return true;
		}
		
		if(crossProduct1>0&&crossProduct2>0&&crossProduct3>0)
		{//���������ͬ�ŷ���true
			return true;
		}
		
		return false;
	}
	
	//������������0��1��2ȷ����ƽ����ָ��XZ���괦�ĸ߶�
	//�����㷨˼�룬�������0�ŵ㵽1��2�ŵ��ʸ��
	//Ȼ��������ʸ�������õ�������ƽ��ķ�ʸ��{A,B,C}
	//����ͨ����ʸ����0�ŵ��������д��������ƽ��ķ���
	// A(x-x0)+B(y-y0)+c(z-z0)=0
	//Ȼ������Ƶ���ָ��xz���괦y����ֵ��ʽ
	//y=(C(z0-z)+A(x0-x))/B+y0;
	//���ͨ����ֵ��ʽ���ָ��xz���괦y��ֵ
	public static  float fromXZToY
	(
		float tx0,float ty0,float tz0,//ȷ��ƽ��ĵ�0
		float tx1,float ty1,float tz1,//ȷ��ƽ��ĵ�1
		float tx2,float ty2,float tz2,//ȷ��ƽ��ĵ�2
		float ctx,float ctz//��ͷ��XZ����
	)
	{
		//���0�ŵ㵽1�ŵ��ʸ��
        float x1=tx1-tx0;
        float y1=ty1-ty0;
        float z1=tz1-tz0;
        //���0�ŵ㵽2�ŵ��ʸ��
        float x2=tx2-tx0;
        float y2=ty2-ty0;
        float z2=tz2-tz0;
        //�������ʸ�����ʸ����XYZ��ķ���ABC
        float A=y1*z2-y2*z1;
        float B=z1*x2-z2*x1;
        float C=x1*y2-x2*y1;
        //ͨ����ֵ��ʽ��ָ��xz����yֵ
		float yResult=(C*(tz0-ctz)+A*(tx0-ctx))/B+ty0;
		//���ؽ��
		return yResult;
	}	


}
