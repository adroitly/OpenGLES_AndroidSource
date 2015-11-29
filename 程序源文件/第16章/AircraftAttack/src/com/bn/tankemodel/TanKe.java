package com.bn.tankemodel;
import static com.bn.gameView.Constant.BUTTON_RADAR_BG_WIDTH;
import static com.bn.gameView.Constant.Lock_angle;
import static com.bn.gameView.Constant.Lock_tanke;
import static com.bn.gameView.Constant.MapArray;
import static com.bn.gameView.Constant.PLANE_X;
import static com.bn.gameView.Constant.PLANE_Y;
import static com.bn.gameView.Constant.PLANE_Z;
import static com.bn.gameView.Constant.TANK_MAX_DISTANCE;
import static com.bn.gameView.Constant.WIDTH_LALNDFORM;
import static com.bn.gameView.Constant.directionX;
import static com.bn.gameView.Constant.directionY;
import static com.bn.gameView.Constant.directionZ;
import static com.bn.gameView.Constant.isno_Lock;
import static com.bn.gameView.Constant.mapId;
import static com.bn.gameView.Constant.minimumdistance;
import static com.bn.gameView.Constant.nx;
import static com.bn.gameView.Constant.ny;
import static com.bn.gameView.Constant.nz;
import static com.bn.gameView.Constant.rotationAngle_Plane_Z;
import static com.bn.gameView.Constant.scalMark;
import static com.bn.gameView.Constant.tank_bomb_List;
import static com.bn.gameView.Constant.tank_ratio;
import static com.bn.gameView.GLGameView.cx;
import static com.bn.gameView.GLGameView.cz;
import android.opengl.GLES20;

import com.bn.commonObject.BallTextureByVertex;
import com.bn.commonObject.NumberForDraw;
import com.bn.commonObject.TextureRect;
import com.bn.core.MatrixState;
import com.bn.gameView.BombForControl;
import com.bn.gameView.GLGameView;
public class TanKe 
{
	Model tanke_body, tanke_barrel;//ģ�� 
	private BallTextureByVertex bomb_ball;//�ڵ���
	GLGameView gv;
	public float tx,ty,tz;
	public float[] tank_position;//̹�˵�λ��
	public float barrel_bottom=16;//�ڹܵײ��ĸ߶�
	public float[] barrel_bottom_position=new float[3];//̹���ڹܵײ���λ��
	public float barrel_length=30;//�ڹܵĳ���
	int row;//��������
	int col;
	float tank_barrel_direction=0;//̹���ڹܵķ����
	float tank_barrel_elevation=0;//̹���ڹܵ�����
	public float[] bomb_position_init=new float[3];//̹�˷����ڵ��ĳ�ʼλ��
	private float oldTime=0;//���ڼ�¼�ڵ��ϴη����ʱ��
	
	public NumberForDraw nm;//��������
	public TextureRect backgroundRect;//����
	public float xue_scale=0.6f;//Ѫ���ű���
	public int blood=100;//̹�˵�����ֵ
	int drawblood;
	float yAnglexue;
	public boolean this_isno_Lock=false;//�Լ��Ƿ��������������ͻ�����������
	public TextureRect mark_lock;//��Ǳ������ľ���
	public TextureRect mark_plane;//��Ǿ���
	//��Ǿ����λ�õ���ɫ����λ��
	float arsenal_x,arsenal_y,arsenal_z;
	public TanKe(GLGameView gv,BallTextureByVertex bomb_ball,Model tanke_body,Model tanke_barrel,float[] tank_position,int col,int row,
			TextureRect backgroundRect,NumberForDraw nm,TextureRect mark_plane,TextureRect mark_lock
			)
	{
		this.nm=nm;
		this.backgroundRect=backgroundRect;
		
		this.gv=gv;
		
		this.bomb_ball=bomb_ball;//�ڵ�
		this.tanke_body=tanke_body;
		this.tanke_barrel=tanke_barrel;
		this.tank_position=tank_position;//��ʼ��̹�˵�λ��
		this.barrel_bottom_position[0]=tank_position[0];
		this.barrel_bottom_position[1]=tank_position[1]+barrel_bottom;
		this.barrel_bottom_position[2]=tank_position[2];
		this.col=col;
		this.row=row;
		
		tx=tank_position[0];
		ty=tank_position[1];
		tz=tank_position[2];
		this.mark_plane=mark_plane;
		arsenal_x=-scalMark*BUTTON_RADAR_BG_WIDTH*(MapArray[mapId].length*WIDTH_LALNDFORM/2-tx)/(MapArray[mapId].length*WIDTH_LALNDFORM/2);
		arsenal_y=scalMark*BUTTON_RADAR_BG_WIDTH*(MapArray[mapId].length*WIDTH_LALNDFORM/2-tz)/(MapArray[mapId].length*WIDTH_LALNDFORM/2);
		this.mark_lock=mark_lock;
	}
	public void drawSelf(int texId,int ii,int jj,int rowR,int colR,
			int backgroundRectId,int numberID,int locktexId
		)
	{
		if(row<ii||row>rowR||col<jj||col>colR)
		{
			return;
		}
		drawblood=blood;
		MatrixState.pushMatrix();
		MatrixState.translate(tank_position[0], tank_position[1], tank_position[2]);	
		MatrixState.scale(tank_ratio, tank_ratio, tank_ratio);
		tanke_body.drawSelf(texId);
		MatrixState.translate(0, barrel_bottom, 0);	
		MatrixState.rotate(tank_barrel_direction, 0, 1, 0);
		MatrixState.rotate(tank_barrel_elevation, 1, 0, 0);
		tanke_barrel.drawSelf(texId);
		MatrixState.popMatrix();
		
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		if(drawblood>=0)
		{
			MatrixState.pushMatrix();
			MatrixState.translate(tank_position[0], tank_position[1]+70, tank_position[2]);	
			MatrixState.scale(xue_scale, xue_scale, xue_scale);
			MatrixState.rotate(yAnglexue, 0,1, 0);
			backgroundRect.bloodValue=drawblood*2-100+6;
			backgroundRect.drawSelf(backgroundRectId);      	  
	    	MatrixState.popMatrix();
		}
		if(this_isno_Lock)
		{
			MatrixState.pushMatrix();//����������
			MatrixState.translate(tank_position[0], tank_position[1]+20, tank_position[2]);	
			
			MatrixState.rotate(yAnglexue, 0,1, 0);
			MatrixState.rotate(rotationAngle_Plane_Z, 0,0, 1);
			MatrixState.scale(tank_ratio, tank_ratio, tank_ratio);
			mark_lock.drawSelf(locktexId);
	    	MatrixState.popMatrix();
		}
		GLES20.glDisable(GLES20.GL_BLEND);
	}
	public void drawSelfMark(int texId)
	{
		MatrixState.pushMatrix();
    	MatrixState.translate(arsenal_x,arsenal_y,0);
    	mark_plane.drawSelf(texId);
    	MatrixState.popMatrix();
	}
	//��������־��ĳ���
	public void calculateBillboardDirection()
	{//���������λ�ü�����������泯��
		float currX_span=tx-cx;
		float currZ_span=tz-cz;
		if(currZ_span<0)
		{
			yAnglexue=(float)Math.toDegrees(Math.atan(currX_span/currZ_span));	
		}else if(currZ_span==0)
		{
			yAnglexue=currX_span>0?90:-90;
		}
		else 
		{
			yAnglexue=180+(float)Math.toDegrees(Math.atan(currX_span/currZ_span));	
		}
		if(isno_Lock)//����Ѿ��б�������Ŀ����
		{
			this_isno_Lock=false;
			return;
		}
		//�������Ƿ�����
		float x1,y1,z1,x2,y2,z2;
		x1=tx-PLANE_X;
		y1=ty-PLANE_Y;
		z1=tz-PLANE_Z;
		float distance1=(float) Math.sqrt(x1*x1+y1*y1+z1*z1);
		
		if(distance1>minimumdistance){//������볬����Χ�������Ѿ���һ���������ˣ����Լ����ܱ�����
			this_isno_Lock=false;
			return;
		}//����ɻ����еķ�������
		x2=directionX;//-(float) (Math.cos(Math.toRadians(rotationAngle_Plane_X))*Math.sin(Math.toRadians(rotationAngle_Plane_Y)));
		y2=directionY;//(float) (Math.sin(Math.toRadians(rotationAngle_Plane_X)));
		z2=directionZ;//-(float) (Math.cos(Math.toRadians(rotationAngle_Plane_X))*Math.cos(Math.toRadians(rotationAngle_Plane_Y)));
		float cosa=(float) Math.acos((x1*x2+y1*y2+z1*z2)/(distance1*1));
		if(cosa<Lock_angle)
		{
			if(Lock_tanke!=null)
			{
				Lock_tanke.this_isno_Lock=false;
			}
			this.this_isno_Lock=true;			
			minimumdistance=distance1;//��С��������Ϊ�þ���
			nx=x1;ny=y1+20;nz=z1;//�����ӵ���������
			isno_Lock=true;//�Ѿ�������
			Lock_tanke=this;//�Լ�������		
		}
		else
		{
			this_isno_Lock=false;
		}
	}
	//ʱʱ�ı�̹���ڹܵĽǶ�
	public void tank_go()
	{
		calculateBillboardDirection();//����Ѫ����ĳ���
		//�����ȡ�ɻ���λ��
		float curr_planeX=PLANE_X;
		float curr_planeY=PLANE_Y;
		float curr_planeZ=PLANE_Z;
		//���㵱ǰ�����ڵ�Ŀ���ͷɻ�֮��ľ���   ������    ƽ��
		float curr_distance=(curr_planeX-barrel_bottom_position[0])*(curr_planeX-barrel_bottom_position[0])+
							(curr_planeY-barrel_bottom_position[1])*(curr_planeY-barrel_bottom_position[1])+
							(curr_planeZ-barrel_bottom_position[2])*(curr_planeZ-barrel_bottom_position[2]);
		//������������ڵ�ɨ�淶Χ,��ôֱ�ӷ���
		if(curr_distance>TANK_MAX_DISTANCE*TANK_MAX_DISTANCE)
		{
			return;
		}
		//�������߶Ȳ�
		float curr_y_span=curr_planeY-barrel_bottom_position[1];
		if(curr_y_span<=0)
		{
			return;//���С��0,��ôֱ�ӷ���
		}
		//�������������ƽ����
		curr_distance=(float) Math.sqrt(curr_distance);
		//�����������ڵ����Ǻͷ�λ��
		float curr_elevation=(float) Math.toDegrees( Math.asin(curr_y_span/curr_distance));
		tank_barrel_elevation=curr_elevation;//����
		//���ݷ����м��㷽λ��
		float curr_x_span=curr_planeX-barrel_bottom_position[0];
		float curr_z_span=curr_planeZ-barrel_bottom_position[2];
		float curr_direction=(float)Math.toDegrees(Math.atan(curr_x_span/curr_z_span));
		if(curr_x_span==0&&curr_z_span==0)
		{ 
			tank_barrel_direction=curr_direction=0;
		}
		else if(curr_z_span>=0)
		{
			tank_barrel_direction=curr_direction=curr_direction+180;
		}
		else
		{
			tank_barrel_direction=curr_direction;
		}
		//---------------��ο��Է���
		//�������������ڵ��ĳ�ʼλ��
		if(System.nanoTime()-oldTime>2000000000)//ÿ��2�뷢��һ���ڵ�
		{
			bomb_position_init[0]=(float) (barrel_bottom_position[0]-Math.cos(Math.toRadians(tank_barrel_elevation))*
								Math.sin(Math.toRadians(tank_barrel_direction))*barrel_length);//X
			bomb_position_init[1]=(float) (barrel_bottom_position[1]+Math.sin(Math.toRadians(tank_barrel_elevation))*
									barrel_length);//Y
			bomb_position_init[2]=(float) (barrel_bottom_position[2]-Math.cos(Math.toRadians(tank_barrel_elevation))*
							   Math.cos(Math.toRadians(tank_barrel_direction))*barrel_length);//Z
			//�����ڵ�
			tank_bomb_List.add(new BombForControl(gv,bomb_ball,bomb_position_init,tank_barrel_elevation,
					tank_barrel_direction));
			gv.activity.playSound(1,0);
			oldTime=System.nanoTime();
		}
	}
}
