package com.bn.gameView;
import static com.bn.gameView.Constant.ANGLE_X_Z;
import static com.bn.gameView.Constant.ARCHIBALD_X;
import static com.bn.gameView.Constant.ARCHIBALD_Y;
import static com.bn.gameView.Constant.ARCHIBALD_Z;
import static com.bn.gameView.Constant.ARCHIE_BOMB_VELOCITY;
import static com.bn.gameView.Constant.ARSENAL_X;
import static com.bn.gameView.Constant.ARSENAL_Y;
import static com.bn.gameView.Constant.ARSENAL_Z;
import static com.bn.gameView.Constant.ArchieArray;
import static com.bn.gameView.Constant.BOMB_MAX_DISTANCE;
import static com.bn.gameView.Constant.BOMB_VELOCITY;
import static com.bn.gameView.Constant.PLANE_X;
import static com.bn.gameView.Constant.PLANE_X_R;
import static com.bn.gameView.Constant.PLANE_Y;
import static com.bn.gameView.Constant.PLANE_Y_R;
import static com.bn.gameView.Constant.PLANE_Z;
import static com.bn.gameView.Constant.TANK_BOMB_VELOCITY;
import static com.bn.gameView.Constant.archie_List;
import static com.bn.gameView.Constant.archie_bomb_List;
import static com.bn.gameView.Constant.bomb_List;
import static com.bn.gameView.Constant.fire_index;
import static com.bn.gameView.Constant.gradeArray;
import static com.bn.gameView.Constant.isno_Hit;
import static com.bn.gameView.Constant.isno_Lock;
import static com.bn.gameView.Constant.mapId;
import static com.bn.gameView.Constant.nx;
import static com.bn.gameView.Constant.ny;
import static com.bn.gameView.Constant.nz;
import static com.bn.gameView.Constant.tank_bomb_List;
import static com.bn.gameView.GLGameView.arsenal;
import static com.bn.gameView.GLGameView.baoZhaList;
import static com.bn.gameView.GLGameView.bombRect;
import static com.bn.gameView.GLGameView.bombRectr;
import static com.bn.gameView.GLGameView.bomb_height;
import static com.bn.gameView.GLGameView.enemy;
import static com.bn.gameView.GLGameView.tankeList;

import java.util.Iterator;

import com.bn.archieModel.ArchieForControl;
import com.bn.arsenal.Arsenal_House;
import com.bn.commonObject.BallTextureByVertex;
import com.bn.commonObject.DrawBomb;
import com.bn.core.MatrixState;
import com.bn.planeModel.EnemyPlane;
import com.bn.tankemodel.TanKe;

//�ڵ��Ŀ�����
public class BombForControl
{
	GLGameView gv;
	private BallTextureByVertex bomb_ball;//�ӵ���
	//���巢���ڵ�ʱ�ɻ������Ǻͷ�λ��
	private float curr_elevation;
	private float  curr_direction;
	//�ڵ���λ��
	private float curr_x;
	private float curr_y;
	private float curr_z;
	private float distance;//���о���
	//�ɻ������ڵ�����
	private boolean islocked;
	private float curr_nx;
	private float curr_ny;
	private float curr_nz;
	private float average;//ƽ������
	//�ɻ������ڵ��Ĺ�����
	public BombForControl(GLGameView gv,BallTextureByVertex bomb_ball,float plane_x,float plane_y,float plane_z,
			float plane_elevation,float plane_direction,float rotationAngle_Plane_X,float rotationAngle_Plane_Y,
			float rotationAngle_Plane_Z)
	{
		this.gv=gv;
		//����������
		this.bomb_ball=bomb_ball;
		this.curr_elevation=plane_elevation;
		this.curr_direction=plane_direction;
		this.islocked=isno_Lock;
		if(islocked)
		{
			curr_nx=nx;
			curr_ny=ny;
			curr_nz=nz;
			average=(float) Math.sqrt(curr_nx*curr_nx+curr_ny*curr_ny+curr_nz*curr_nz);
		}
		//��ʼ���ڵ��ķ���λ��
		initData(plane_x,plane_y,plane_z,rotationAngle_Plane_X,rotationAngle_Plane_Y,rotationAngle_Plane_Z);
	}
	//�����ں�̹�˷����ڵ��Ĺ�����
	public BombForControl(GLGameView gv,BallTextureByVertex bomb_ball,float[]init_position,float init_elevation,float init_direction)
	{
		this.gv=gv;
		this.bomb_ball=bomb_ball;
		curr_x=init_position[0];//
		curr_y=init_position[1];//
		curr_z=init_position[2];//
		curr_elevation=init_elevation;
		curr_direction=init_direction;
	}
	//ȷ���ɻ������ڵ��ķ���λ��
	public void initData(float plane_x,float plane_y,float plane_z,float rotationAngle_Plane_X,
			float rotationAngle_Plane_Y,float rotationAngle_Plane_Z)
	{
		//�趨��������ڵ���λ��
		curr_x=plane_x;
		curr_y=plane_y;
		curr_z=plane_z;
		//�ڵ�λ�õ���ز���
		float length;
		float ori_y;
		float ori_z;
		length=12;
		if(fire_index!=1)//��������ڵ�
		{
			ori_y=90;
			ori_z=-2;
		}
		else//�һ������ڵ�
		{
			ori_y=-90;
			ori_z=-2;
		}
		//ȷ���ڵ�������λ��
		curr_y=curr_y-(float)Math.sin(Math.toRadians(rotationAngle_Plane_Z+ori_z))*length;
		curr_x=curr_x-(float)Math.cos(Math.toRadians(rotationAngle_Plane_Z+ori_z))*(float)Math.sin(Math.toRadians(rotationAngle_Plane_Y+ori_y))*length;
		curr_z=curr_z-(float)Math.cos(Math.toRadians(rotationAngle_Plane_Z+ori_z))*(float)Math.cos(Math.toRadians(rotationAngle_Plane_Y+ori_y))*length;
	}
	public void drawSelf(int texId)
	{
		MatrixState.pushMatrix();
		MatrixState.translate(curr_x, curr_y, curr_z);
		bomb_ball.drawSelf(texId);
		MatrixState.popMatrix();		
	}
	//�ɻ������ڵ�
	public void go()
	{
		distance+=BOMB_VELOCITY;//�ӵ�����ʻ·������
		if(distance>=BOMB_MAX_DISTANCE)//����ӵ�����������
		{	
			
			Iterator<BombForControl> ite=bomb_List.iterator();
			gv.activity.playSound(1,0);
			while(ite.hasNext())
			{
				if(ite.next()==this)
				{
					ite.remove();
					return;
				}
			}
			
		}	
		float tyy;
		if((tyy=KeyThread.isYachtHeadCollectionsWithLandPaodan(curr_x,curr_y,curr_z))>0){
			baoZhaList.add(new DrawBomb(bombRectr,curr_x,tyy,curr_z));//����ڵ�ײ������
			Iterator<BombForControl> ite=bomb_List.iterator();
			while(ite.hasNext())
			{
				if(ite.next()==this)
				{
					ite.remove();
					return;
				}
			}
		}
		//�ж��Ƿ�;���ⷢ����ײ
		for(Arsenal_House as:arsenal){
			if(//����⻹����
					curr_y>as.ty&&curr_y<as.ty+ARSENAL_Y
					&&curr_x>as.tx-ARSENAL_X&&curr_x<as.tx+ARSENAL_X
					&&curr_z>as.tz-ARSENAL_Z&&curr_z<as.tz+ARSENAL_Z){
				
				as.blood-=ArchieArray[mapId][8][0];//������Ѫ��1			
				
				if(as.blood<1){//�������ⱻը��
					gv.activity.playSound(0,0);
					try
					{
						Iterator<Arsenal_House> ite=arsenal.iterator();
						while(ite.hasNext())
						{
							if(ite.next()==as)
							{
								ite.remove();
							}
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					baoZhaList.add(new DrawBomb(bombRect,as.tx,as.ty+bomb_height/2,as.tz));
					gradeArray[1]+=ArchieArray[mapId][12][3];//�÷�����,
				}
				Iterator<BombForControl> ite=bomb_List.iterator();
				while(ite.hasNext())
				{
					if(ite.next()==this)
					{
						ite.remove();
						
						return;
					}
				}
				
			}
		}
		

		for(ArchieForControl afc:archie_List){//�鿴��û�л��и�����
			if(curr_y>afc.position[1]&&curr_y<afc.position[1]+ARCHIBALD_Y*2
					&&curr_x>afc.position[0]-ARCHIBALD_X*2&&curr_x<afc.position[0]+ARCHIBALD_X*2
					&&curr_z>afc.position[2]-ARCHIBALD_Z*2&&curr_z<afc.position[2]+ARCHIBALD_Z*2){
				
				afc.blood-=ArchieArray[mapId][8][2];//�����ڵ�Ѫ��10
				if(afc.blood<0){
					gv.activity.playSound(0,1);
					try
					{
						Iterator<ArchieForControl> ite=archie_List.iterator();
						while(ite.hasNext())
						{
							if(ite.next()==afc)
							{
								ite.remove();
							}
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					baoZhaList.add(new DrawBomb(bombRect,curr_x,afc.position[1]+ARCHIBALD_Y,curr_z));
					gradeArray[1]+=ArchieArray[mapId][12][1];//�÷�����,
				}
				Iterator<BombForControl> ite=bomb_List.iterator();
				while(ite.hasNext())
				{
					if(ite.next()==this)
					{
						ite.remove();
						
						return;
					}
				}
				
			}
		}
		for(TanKe afc:tankeList)//�鿴��û�л���̹��
		{
			if(curr_y>afc.ty&&curr_y<afc.ty+ARCHIBALD_Y
					&&curr_x>afc.tx-ARCHIBALD_X&&curr_x<afc.tx+ARCHIBALD_X
					&&curr_z>afc.tz-ARCHIBALD_Z&&curr_z<afc.tz+ARCHIBALD_Z)
			{
				afc.blood-=ArchieArray[mapId][8][1];//̹�˵�Ѫ��1
				if(afc.blood<=0)
				{
					gv.activity.playSound(0,1);
					try
					{
						Iterator<TanKe> ite=tankeList.iterator();
						while(ite.hasNext())
						{
							if(ite.next()==afc)
							{
								ite.remove();
							}
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					gradeArray[1]+=ArchieArray[mapId][12][0];//�÷�����,
				}
				baoZhaList.add(new DrawBomb(bombRect,curr_x,curr_y+bomb_height/5,curr_z));
				Iterator<BombForControl> ite=bomb_List.iterator();
				while(ite.hasNext())
				{
					if(ite.next()==this)
					{
						ite.remove();
						
						return;
					}
				}
				
			}
		}
		//��û�л��ел�
		for(EnemyPlane afc:enemy){//�鿴��û�л���
			if(curr_y>afc.ty-PLANE_Y_R&&curr_y<afc.ty+PLANE_Y_R
					&&curr_x>afc.tx-PLANE_X_R&&curr_x<afc.tx+PLANE_X_R
					&&curr_z>afc.tz-ANGLE_X_Z&&curr_z<afc.tz+ANGLE_X_Z){
				
				afc.blood-=ArchieArray[mapId][8][3];//�л���Ѫ��1
					gv.activity.playSound(8,0);
				if(afc.blood<=0){
					gv.activity.playSound(0,1);
					try
					{
						Iterator<EnemyPlane> ite=enemy.iterator();
						while(ite.hasNext())
						{
							if(ite.next()==afc)
							{
								ite.remove();
							}
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					gradeArray[1]+=ArchieArray[mapId][12][2];//�÷�����,
				}
				baoZhaList.add(new DrawBomb(bombRect,curr_x,curr_y+bomb_height/5,curr_z));
				Iterator<BombForControl> ite=bomb_List.iterator();
				while(ite.hasNext())
				{
					if(ite.next()==this)
					{
						ite.remove();
						
						return;
					}
				}
				
			}
		}
		//�����ж��Ƿ�����Ŀ��
		if(islocked)//�������Ŀ��
		{
			 curr_x+=curr_nx/average*BOMB_VELOCITY;
			 curr_z+=curr_nz/average*BOMB_VELOCITY;
			 curr_y+=curr_ny/average*BOMB_VELOCITY;
		}
		else
		{
		//�����ڵ���һ����λ��
		//���㵱ǰ���Ǻͷ����
			curr_x=curr_x-(float)(Math.cos(Math.toRadians(curr_elevation))*Math.sin(Math.toRadians(curr_direction))*BOMB_VELOCITY);
			curr_z=curr_z-(float)(Math.cos(Math.toRadians(curr_elevation))*Math.cos(Math.toRadians(curr_direction))*BOMB_VELOCITY);
			curr_y=curr_y+(float)(Math.sin(Math.toRadians(curr_elevation))*BOMB_VELOCITY);//�ɻ���λ��
		}
	}
	//�����ڷ����ڵ�
	public void go_archie()
	{
		float curr_planeX=PLANE_X;
		float curr_planeY=PLANE_Y;
		float curr_planeZ=PLANE_Z;
		distance+=ARCHIE_BOMB_VELOCITY;//�ӵ�����ʻ·������
		if(distance>=BOMB_MAX_DISTANCE)//����ӵ�����������
		{	
			try
			{
				Iterator<BombForControl> ite=archie_bomb_List.iterator();
				while(ite.hasNext())
				{
					if(ite.next()==this)
					{
						ite.remove();
						return;
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		//������ڵ��Ƿ���зɻ������ж�
		float curr_distance=(curr_planeX-curr_x)*(curr_planeX-curr_x)+
		(curr_planeY-curr_y)*(curr_planeY-curr_y)+
		(curr_planeZ-curr_z)*(curr_planeZ-curr_z);
		if(curr_distance<500)//�ڵ���ɻ���ײ
		{
			gv.activity.playSound(1,0);
			isno_Hit=true;//�ɻ���������һ��
			gv.plane.blood-=ArchieArray[mapId][9][1];//�ɻ�Ѫ����һ��
			gv.activity.shake();//�ֻ���һ��
			try
			{
				Iterator<BombForControl> ite=archie_bomb_List.iterator();
				while(ite.hasNext())
				{
					if(ite.next()==this)
					{
						ite.remove();
						return;
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			} 
			return ;
		}
		//�����ڵ���һ����λ��
		//���㵱ǰ���Ǻͷ����
		curr_x=curr_x-(float)(Math.cos(Math.toRadians(curr_elevation))*Math.sin(Math.toRadians(curr_direction))*ARCHIE_BOMB_VELOCITY);
		curr_z=curr_z-(float)(Math.cos(Math.toRadians(curr_elevation))*Math.cos(Math.toRadians(curr_direction))*ARCHIE_BOMB_VELOCITY);
		curr_y=curr_y+(float)(Math.sin(Math.toRadians(curr_elevation))*ARCHIE_BOMB_VELOCITY);//�ɻ���λ��
	}
	
	//̹�˷����ڵ�
	public void go_tank()
	{
		
		float curr_planeX=PLANE_X;
		float curr_planeY=PLANE_Y;
		float curr_planeZ=PLANE_Z;
		distance+=TANK_BOMB_VELOCITY;//�ӵ�����ʻ·������
		if(distance>=BOMB_MAX_DISTANCE)//����ӵ�����������
		{	
			try
			{
				Iterator<BombForControl> ite=tank_bomb_List.iterator();
				while(ite.hasNext())
				{
					if(ite.next()==this)
					{
						ite.remove();
						return;
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		//������ڵ��Ƿ���зɻ������ж�
		float curr_distance=(curr_planeX-curr_x)*(curr_planeX-curr_x)+
		(curr_planeY-curr_y)*(curr_planeY-curr_y)+
		(curr_planeZ-curr_z)*(curr_planeZ-curr_z);
		if(curr_distance<500)//�ڵ���ɻ���ײ
		{
			gv.activity.playSound(1,0);
			isno_Hit=true;//�ɻ�������һ����
			gv.plane.blood-=ArchieArray[mapId][9][0];//�ɻ�Ѫ����һ��
			gv.activity.shake();//�ֻ���һ��
			try
			{
				Iterator<BombForControl> ite=tank_bomb_List.iterator();
				while(ite.hasNext())
				{
					if(ite.next()==this)
					{
						ite.remove();
						return;
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return ;
		}
		//�����ڵ���һ����λ��
		//���㵱ǰ���Ǻͷ����
		curr_x=curr_x-(float)(Math.cos(Math.toRadians(curr_elevation))*Math.sin(Math.toRadians(curr_direction))*TANK_BOMB_VELOCITY);
		curr_z=curr_z-(float)(Math.cos(Math.toRadians(curr_elevation))*Math.cos(Math.toRadians(curr_direction))*TANK_BOMB_VELOCITY);
		curr_y=curr_y+(float)(Math.sin(Math.toRadians(curr_elevation))*TANK_BOMB_VELOCITY);
	}
}
