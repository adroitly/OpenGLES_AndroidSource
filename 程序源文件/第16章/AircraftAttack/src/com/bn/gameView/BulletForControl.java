package com.bn.gameView;
import static com.bn.gameView.Constant.ANGLE_X_Z;
import static com.bn.gameView.Constant.ARCHIBALD_X;
import static com.bn.gameView.Constant.ARCHIBALD_Y;
import static com.bn.gameView.Constant.ARCHIBALD_Z;
import static com.bn.gameView.Constant.ARSENAL_X;
import static com.bn.gameView.Constant.ARSENAL_Y;
import static com.bn.gameView.Constant.ARSENAL_Z;
import static com.bn.gameView.Constant.ArchieArray;
import static com.bn.gameView.Constant.BULLET_MAX_DISTANCE;
import static com.bn.gameView.Constant.BULLET_VELOCITY;
import static com.bn.gameView.Constant.PLANE_X;
import static com.bn.gameView.Constant.PLANE_X_R;
import static com.bn.gameView.Constant.PLANE_Y;
import static com.bn.gameView.Constant.PLANE_Y_R;
import static com.bn.gameView.Constant.PLANE_Z;
import static com.bn.gameView.Constant.archie_List;
import static com.bn.gameView.Constant.bullet_List;
import static com.bn.gameView.Constant.gradeArray;
import static com.bn.gameView.Constant.isno_Lock;
import static com.bn.gameView.Constant.mapId;
import static com.bn.gameView.Constant.nx;
import static com.bn.gameView.Constant.ny;
import static com.bn.gameView.Constant.nz;
import static com.bn.gameView.GLGameView.arsenal;
import static com.bn.gameView.GLGameView.baoZhaList;
import static com.bn.gameView.GLGameView.bombRect;
import static com.bn.gameView.GLGameView.bomb_height;
import static com.bn.gameView.GLGameView.cx;
import static com.bn.gameView.GLGameView.cy;
import static com.bn.gameView.GLGameView.cz;
import static com.bn.gameView.GLGameView.enemy;
import static com.bn.gameView.GLGameView.tankeList;

import java.util.Iterator;

import com.bn.archieModel.ArchieForControl;
import com.bn.arsenal.Arsenal_House;
import com.bn.commonObject.DrawBomb;
import com.bn.commonObject.TextureRect;
import com.bn.core.MatrixState;
import com.bn.planeModel.EnemyPlane;
import com.bn.tankemodel.TanKe;

public class BulletForControl implements Comparable<BulletForControl>
{
	private TextureRect bullet_rect;//�ӵ���
	//���巢���ӵ�ʱ��λ��
	private float curr_x;
	private float curr_y;
	private float curr_z;
	//���巢���ӵ�ʱ�����Ǻͷ�λ��
	private float curr_elevation;
	private float curr_direction;
	private float distance;//���о���
	
	//����״̬�µķ���
	private boolean islocked;
	private float curr_nx;
	private float curr_ny;
	private float curr_nz;
	private float average;//ƽ������
	//�ӵ�����ת�Ƕ�
	private float curr_rotation;
	GLGameView gv;
	public int bulletId;//�ӵ���id�����ǵл������Ļ����Լ��������ӵ���1Ϊ�л��������ӵ�
	public BulletForControl(GLGameView gv,TextureRect bullet_rect,float plane_x,float plane_y,float plane_z,float plane_elevation,float plane_direction
			,float rotationAngle_Plane_X,float rotationAngle_Plane_Y,
			float rotationAngle_Plane_Z,int bulletIndex,int bulletId)//bulletIndex��ʾ����������ӵ������һ���
	{
		this.bulletId=bulletId;
		this.gv=gv;
		//����������
		this.bullet_rect=bullet_rect;
		this.curr_x=plane_x;
		this.curr_y=plane_y;
		this.curr_z=plane_z;
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
		initData(plane_x,plane_y,plane_z,rotationAngle_Plane_X,rotationAngle_Plane_Y,rotationAngle_Plane_Z,bulletIndex);
	}
	//ȷ���ɻ������ӵ��ķ���λ��
	public void initData(float plane_x,float plane_y,float plane_z,float rotationAngle_Plane_X,
			float rotationAngle_Plane_Y,float rotationAngle_Plane_Z,int bulletIndex)
	{
		//�趨��������ڵ���λ��
		curr_x=plane_x;
		curr_y=plane_y;
		curr_z=plane_z;
		//�ڵ�λ�õ���ز���
		float length;
		float ori_y;
		float ori_z;
		length=6;
		if(bulletIndex!=1)//��������ӵ�
		{
			ori_y=90;
			ori_z=-2;
		}
		else//�һ������ӵ�
		{
			ori_y=-90;
			ori_z=-2;
		}
		//ȷ���ӵ�������λ��
		curr_y=curr_y-(float)Math.sin(Math.toRadians(rotationAngle_Plane_Z+ori_z))*length;
		curr_x=curr_x-(float)Math.cos(Math.toRadians(rotationAngle_Plane_Z+ori_z))*(float)Math.sin(Math.toRadians(rotationAngle_Plane_Y+ori_y))*length;
		curr_z=curr_z-(float)Math.cos(Math.toRadians(rotationAngle_Plane_Z+ori_z))*(float)Math.cos(Math.toRadians(rotationAngle_Plane_Y+ori_y))*length;
	}
	public void drawSelf(int texId)
	{
		MatrixState.pushMatrix();
		MatrixState.translate(curr_x, curr_y, curr_z);//�ӵ��ƶ���ָ����λ��
		MatrixState.rotate(curr_rotation, 0, 1, 0);//��������������λ�ý�����ת
		bullet_rect.drawSelf(texId);//���л���
		MatrixState.popMatrix();		
	}
	public void go()
	{
		distance+=BULLET_VELOCITY;//�ӵ�����ʻ·������
		if(distance>=BULLET_MAX_DISTANCE)//����ӵ�����������
		{		
			Iterator<BulletForControl> ite=bullet_List.iterator();
			while(ite.hasNext())
			{
				if(ite.next()==this)
				{
					ite.remove();
					return;
				}
			}
		}
		if(bulletId==0)
		{
			//�ж��Ƿ�;���ⷢ����ײ
			for(Arsenal_House as:arsenal)
			{
				if(//����⻹����
						curr_y>as.ty&&curr_y<as.ty+ARSENAL_Y
						&&curr_x>as.tx-ARSENAL_X&&curr_x<as.tx+ARSENAL_X
						&&curr_z>as.tz-ARSENAL_Z&&curr_z<as.tz+ARSENAL_Z)
				{
					
					as.blood-=ArchieArray[mapId][7][0];//�����			
					
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
						gradeArray[1]+=ArchieArray[mapId][12][3];//�÷�����,
						baoZhaList.add(new DrawBomb(bombRect,as.tx,as.ty+bomb_height/2,as.tz));
					}
					Iterator<BulletForControl> ite=bullet_List.iterator();
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
			for(ArchieForControl afc:archie_List)//�鿴��û�л��и�����
			{
				if(curr_y>afc.position[1]&&curr_y<afc.position[1]+ARCHIBALD_Y
						&&curr_x>afc.position[0]-ARCHIBALD_X&&curr_x<afc.position[0]+ARCHIBALD_X
						&&curr_z>afc.position[2]-ARCHIBALD_Z&&curr_z<afc.position[2]+ARCHIBALD_Z)
				{
					afc.blood-=ArchieArray[mapId][7][2];//�����ڵ�Ѫ��1
					try
					{
						if(afc.blood<1)
						{
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
							gradeArray[1]+=ArchieArray[mapId][12][1];//�÷�����,
							baoZhaList.add(new DrawBomb(bombRect,curr_x,curr_y+bomb_height/2,curr_z));
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					Iterator<BulletForControl> ite=bullet_List.iterator();
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
			for(TanKe afc:tankeList){//�鿴��û�л���̹��
				if(curr_y>afc.ty&&curr_y<afc.ty+ARCHIBALD_Y
						&&curr_x>afc.tx-ARCHIBALD_X&&curr_x<afc.tx+ARCHIBALD_X
						&&curr_z>afc.tz-ARCHIBALD_Z&&curr_z<afc.tz+ARCHIBALD_Z){
					
					afc.blood-=ArchieArray[mapId][7][1];//̹�˵�Ѫ��1
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
						baoZhaList.add(new DrawBomb(bombRect,curr_x,curr_y+bomb_height/2,curr_z));
						gradeArray[1]+=ArchieArray[mapId][12][0];//�÷�����,
					}
					Iterator<BulletForControl> ite=bullet_List.iterator();
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
			for(EnemyPlane afc:enemy){//
				if(curr_y>afc.ty-PLANE_Y_R&&curr_y<afc.ty+PLANE_Y_R
						&&curr_x>afc.tx-PLANE_X_R&&curr_x<afc.tx+PLANE_X_R
						&&curr_z>afc.tz-ANGLE_X_Z&&curr_z<afc.tz+ANGLE_X_Z){
					
					afc.blood-=ArchieArray[mapId][7][3];//�л���Ѫ��1
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
						baoZhaList.add(new DrawBomb(bombRect,curr_x,curr_y+bomb_height/5,curr_z));
						gradeArray[1]+=ArchieArray[mapId][12][2];//�÷�����,
					}
					Iterator<BulletForControl> ite=bullet_List.iterator();
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
		}
		else//�з��ķɻ�������ӵ������ҷ��ķɻ�
		{
			float curr_planeX=PLANE_X;
			float curr_planeY=PLANE_Y;
			float curr_planeZ=PLANE_Z;
			//������ڵ��Ƿ���зɻ������ж�
			float curr_distance=(curr_planeX-curr_x)*(curr_planeX-curr_x)+
			(curr_planeY-curr_y)*(curr_planeY-curr_y)+
			(curr_planeZ-curr_z)*(curr_planeZ-curr_z);
			if(curr_distance<500)//�ڵ���ɻ���ײ
			{
				gv.plane.blood-=ArchieArray[mapId][9][2];//�ɻ�Ѫ����һ��
				try
				{
					Iterator<BulletForControl> ite=bullet_List.iterator();
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
		}
		
		//�����ӵ���һ����λ��
		//���㵱ǰ���Ǻͷ����
		//�����ж��Ƿ�����Ŀ��
		if(islocked)//�������Ŀ�꣬���������������ӵ�
		{
			
			 curr_x+=curr_nx/average*BULLET_VELOCITY;
			 curr_z+=curr_nz/average*BULLET_VELOCITY;
			 curr_y+=curr_ny/average*BULLET_VELOCITY;
		}
		else
		{
			curr_x=curr_x-(float)(Math.cos(Math.toRadians(curr_elevation))*Math.sin(Math.toRadians(curr_direction))*BULLET_VELOCITY);
			curr_z=curr_z-(float)(Math.cos(Math.toRadians(curr_elevation))*Math.cos(Math.toRadians(curr_direction))*BULLET_VELOCITY);
			curr_y=curr_y+(float)(Math.sin(Math.toRadians(curr_elevation))*BULLET_VELOCITY);//�ɻ���λ��
		}
		
		//���㳯��
		calculateBillboardDirection();
	}
	//��������־��ĳ���
	public void calculateBillboardDirection()
	{//���������λ�ü�����������泯��
		float currX_span=curr_x-cx;
		float currZ_span=curr_z-cz;
		if(currZ_span<=0)
		{
			curr_rotation=(float)Math.toDegrees(Math.atan(currX_span/currZ_span));	
		}
		else 
		{
			curr_rotation=180+(float)Math.toDegrees(Math.atan(currX_span/currZ_span));	
		}
	}
	@Override
	public int compareTo(BulletForControl another) 
	{//��д�ıȽ��������������������ķ���
		float x=curr_x-cx;
		float z=curr_y-cz;
		float y=curr_z-cy;
		
		float xo=another.curr_x-cx;
		float zo=another.curr_y-cz;
		float yo=another.curr_z-cy;
		float disA=x*x+y*y+z*z;
		float disB=xo*xo+yo*yo+zo*zo;
		return ((disA-disB)==0)?0:((disA-disB)>0)?-1:1;  
	}
}
