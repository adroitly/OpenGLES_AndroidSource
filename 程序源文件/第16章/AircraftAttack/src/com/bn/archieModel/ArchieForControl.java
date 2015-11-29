package com.bn.archieModel;
import android.opengl.GLES20;
import com.bn.commonObject.BallTextureByVertex;
import com.bn.commonObject.CubeForDraw;
import com.bn.commonObject.NumberForDraw;
import com.bn.commonObject.TextureRect;
import com.bn.core.MatrixState;
import com.bn.gameView.BombForControl;
import com.bn.gameView.GLGameView;

import static com.bn.gameView.Constant.*;
import static com.bn.gameView.GLGameView.cx;
import static com.bn.gameView.GLGameView.cz;
/*
 * �����ڵĿ�����
 */
/*
 * ���Ƹ�����
 * ����ԭ��λ����̨�ļ������Ĵ�
 * ��ô ���������ľ���Ϊ  ����ĸ߶ȵ�һ�����̨�߶ȵ�һ��,���ҵ�ƫ����Ϊ�ڹܵİ뾶
 * �ڹ���Ͷ˵�λ��Ϊ�����ƶ��ľ���Ϊ  : ��̨�߶�һ��+�ڹܳ���һ��
 */
public class ArchieForControl
{
	GLGameView gv;//��������
	public float[] position=new float[3];//�����ڵİڷ�λ��
	public float[] targetPosition=new float[3];//�����ڵ���ת�ᴦ��λ��
	public float[] barrel_center_position=new float[3];//�ڹ����Ĵ�������
	public float[] bomb_position_init=new float[3];//�����ڷ����ڵ��ĳ�ʼλ��
	
	int row;
	int col;
	private float oldTime=0;//��¼�ϴη����ʱ��
	//--------------------
	BarrelForDraw barrel;//�ڹ�
	BarbetteForDraw barbette;//��̨
	CubeForDraw cube;//��������
	private BallTextureByVertex bomb_ball;//�ڵ�
	public float barrel_elevation=30;//�ڹܵ�����
	public float barrel_direction=0;//�ڹܵķ����
	
	float barrel_down_X=0;//�ڹܵ׶˵�����
	public float barrel_down_Y=barbette_length/2+cube_height/2;
	float barrel_down_Z=0;
	
	public float barrel_curr_X;//�ڹܼ������ĵ�X����
	public float barrel_curr_Y;//�ڹܼ������ĵ�Y����
	public float barrel_curr_Z;//�ڹܼ������ĵ�Z����
	
	public NumberForDraw nm;//��������
	public TextureRect backgroundRect;//����
	public float yAnglexue;//Ѫת��
	public float xue_scale=0.4f;//Ѫ���ű���
	
	public int blood=100;//Ѫ
	public int drawblood;
	
	public TextureRect mark_plane;//��Ǿ���
	//��Ǿ����λ�õ���ɫ����λ��
	float arsenal_x,arsenal_y,arsenal_z;
	
	public boolean this_isno_Lock;//�Ƿ�����
	public TextureRect mark_lock;//��Ǳ������ľ���
	//-----------------------------
	public ArchieForControl(GLGameView gv,BarrelForDraw barrel,BarbetteForDraw barbette,CubeForDraw cube,
							BallTextureByVertex bomb_ball,float []position,int row,int col,
							TextureRect backgroundRect,NumberForDraw nm,TextureRect mark_plane,TextureRect mark_lock	
	)
	{
		this.col=col;
		this.row=row;
		this.nm=nm;
		this.backgroundRect=backgroundRect;
		
		this.gv=gv;
		this.barrel=barrel;//��ʼ���ڹ�
		this.barbette=barbette;//��ʼ����̨
		this.cube=cube;//��ʼ������
		this.bomb_ball=bomb_ball;//�ڵ�
		this.position[0]=position[0];
		this.position[1]=position[1];
		this.position[2]=position[2];
		this.targetPosition[0]=position[0];
		this.targetPosition[1]=position[1]+barrel_down_Y;
		this.targetPosition[2]=position[2];
		
		this.mark_plane=mark_plane;
		this.mark_lock=mark_lock;
		
		arsenal_x=-scalMark*BUTTON_RADAR_BG_WIDTH*(MapArray[mapId].length*WIDTH_LALNDFORM/2-targetPosition[0])/(MapArray[mapId].length*WIDTH_LALNDFORM);
		arsenal_y=scalMark*BUTTON_RADAR_BG_WIDTH*(MapArray[mapId].length*WIDTH_LALNDFORM/2-targetPosition[2])/(MapArray[mapId].length*WIDTH_LALNDFORM);
	}
	//���Ʒ���
	public void drawSelf(int[] texBarbetteId,int texCubeId,int[] texBarrelId,int i,int j,int rowR,int colR,
			int backgroundRectId,int numberID,int locktexId
	)
	{
		if(row<i||row>rowR||col<j||col>colR)
		{
			return;
		}
		drawblood=blood;
		
		MatrixState.pushMatrix();
		MatrixState.translate(position[0], position[1], position[2]);
		//��������ڹܵ���̬
		barrel_curr_Y=(float) (barrel_down_Y+Math.sin(Math.toRadians(barrel_elevation))*barrel_length/2);//�ڹܵ�Y����
		barrel_curr_Z=(float) (barrel_down_Z-Math.cos(Math.toRadians(barrel_elevation))*barrel_length/2);//�ڹܵ�Z����
		
		
		
		
		MatrixState.pushMatrix();
		MatrixState.rotate(barrel_direction,0, 1, 0);
		//������̨
		MatrixState.pushMatrix();
		barbette.drawSelf(texBarbetteId);
		MatrixState.popMatrix();
		//�����󵲰�
		MatrixState.pushMatrix();
		MatrixState.translate(-barrel_radius-cube_length/2, cube_height/2+barbette_length/2, 0);
		cube.drawSelf(texCubeId);
		MatrixState.popMatrix();
		//�����ҵ���
		MatrixState.pushMatrix();
		MatrixState.translate(barrel_radius+cube_length/2, cube_height/2+barbette_length/2, 0);
		cube.drawSelf(texCubeId);
		MatrixState.popMatrix();
		//�����ڹ�
		MatrixState.pushMatrix();
		//���ƶ�
		MatrixState.translate(0, barrel_curr_Y, barrel_curr_Z);
		//����ת
		MatrixState.rotate(barrel_elevation-90, 1, 0, 0);
		barrel.drawSelf(texBarrelId);
		MatrixState.popMatrix();
		MatrixState.popMatrix();
		
		MatrixState.popMatrix();
		
		
		
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		
    	if(drawblood>=0){
    		
    		MatrixState.pushMatrix();
    		MatrixState.translate(position[0], position[1]+65, position[2]);	
    		MatrixState.scale(xue_scale, xue_scale, xue_scale);
    		MatrixState.rotate(yAnglexue, 0,1, 0);
    		backgroundRect.bloodValue=drawblood*2-100+6;
    		backgroundRect.drawSelf(backgroundRectId);      	  
        	MatrixState.popMatrix();
		}
    	if(this_isno_Lock)
    	{
			MatrixState.pushMatrix();//����������
			MatrixState.translate(position[0], position[1]+20, position[2]);	
			MatrixState.rotate(yAnglexue, 0,1, 0);
			MatrixState.rotate(rotationAngle_Plane_Z, 0,0, 1);
			MatrixState.scale(1.1f, 1.1f, 0);	
			mark_lock.drawSelf(locktexId);
	    	MatrixState.popMatrix();
		}
    	
    	GLES20.glDisable(GLES20.GL_BLEND);
    	
    	
		
		
		
	}
	public void drawSelfMark(int texId){//��Ǿ���,�Ǳ��
		MatrixState.pushMatrix();
    	MatrixState.translate(arsenal_x,arsenal_y,0);
    	mark_plane.drawSelf(texId);
    	MatrixState.popMatrix();
	}
	
	//��������־��ĳ���
	public void calculateBillboardDirection()
	{//���������λ�ü�����������泯��
		float currX_span=position[0]-cx;
		float currZ_span=position[2]-cz;
		if(currZ_span<0)
		{
			yAnglexue=(float)Math.toDegrees(Math.atan(currX_span/currZ_span));	
		}else if(currZ_span==0){
			yAnglexue=currX_span>0?90:-90;
		}
		else 
		{
			yAnglexue=180+(float)Math.toDegrees(Math.atan(currX_span/currZ_span));	
		}
		
		if(isno_Lock){
			this_isno_Lock=false;
			return;
		}
		//�������Ƿ�����
		float x1,y1,z1,x2,y2,z2;
		x1=position[0]-PLANE_X;
		y1=position[1]-PLANE_Y;
		z1=position[2]-PLANE_Z;
		float distance1=(float) Math.sqrt(x1*x1+y1*y1+z1*z1);
		
		if(distance1>minimumdistance){//������볬����Χ�������Ѿ���һ���������ˣ����Լ����ܱ�����
			this_isno_Lock=false;
			return;
		}//����ɻ����еķ�������
		x2=directionX;//-(float) (Math.cos(Math.toRadians(rotationAngle_Plane_X))*Math.sin(Math.toRadians(rotationAngle_Plane_Y)));
		y2=directionY;//(float) (Math.sin(Math.toRadians(rotationAngle_Plane_X)));
		z2=directionZ;//-(float) (Math.cos(Math.toRadians(rotationAngle_Plane_X))*Math.cos(Math.toRadians(rotationAngle_Plane_Y)));
		
		float cosa=(float) Math.acos((x1*x2+y1*y2+z1*z2)/(distance1*1));
		if(cosa<Lock_angle){
			if(Lock_Arch!=null){
			Lock_Arch.this_isno_Lock=false;
			}
			this.this_isno_Lock=true;			
			minimumdistance=distance1;//��С��������Ϊ�þ���
			nx=x1;ny=y1+10;nz=z1;//�����ӵ���������
			isno_Lock=true;//�Ѿ�������
			Lock_Arch=this;//�Լ�������		
		}else{
			this_isno_Lock=false;
		}
		
	}
	//ʱʱ�ı�����ڵĽǶ�
	public void go()
	{
		calculateBillboardDirection();
		//�����ȡ�ɻ���λ��
		float curr_planeX=PLANE_X;
		float curr_planeY=PLANE_Y;
		float curr_planeZ=PLANE_Z;
		
		//���㵱ǰ�����ڵ�Ŀ���ͷɻ�֮��ľ���   ������    ƽ��
		float curr_distance=(curr_planeX-targetPosition[0])*(curr_planeX-targetPosition[0])+
							(curr_planeY-targetPosition[1])*(curr_planeY-targetPosition[1])+
							(curr_planeZ-targetPosition[2])*(curr_planeZ-targetPosition[2]);
		curr_distance=(float) Math.sqrt(curr_distance);
		if(curr_distance>ARCHIE_MAX_DISTANCE)
		{
			return;//������������ڵ�ɨ�淶Χ,��ôֱ�ӷ���
		}
		//�������߶Ȳ�
		float curr_y_span=curr_planeY-targetPosition[1];
		if(curr_y_span<=0)
		{
			return;//���С��0,��ôֱ�ӷ���
		}
		//�����������ڵ����Ǻͷ�λ��
		float curr_elevation=(float) Math.toDegrees( Math.asin(curr_y_span/curr_distance));
		barrel_elevation=curr_elevation;//����
		//���ݷ����м��㷽λ��
		float curr_x_span=curr_planeX-targetPosition[0];
		float curr_z_span=curr_planeZ-targetPosition[2];
		float curr_direction=(float)Math.toDegrees(Math.atan(curr_x_span/curr_z_span));
		if(curr_x_span==0&&curr_z_span==0)
		{
			barrel_direction=curr_direction=0;
		}
		else if(curr_z_span>=0)
		{
			barrel_direction=curr_direction=curr_direction+180;
		}
		else
		{
			barrel_direction=curr_direction;
		}
		//---------------��ο��Է���
		//�������������ڵ��ĳ�ʼλ��
		if(System.nanoTime()-oldTime>1000000000)//ÿ��һ�뷢��һ���ڵ�
		{
			bomb_position_init[0]=(float) (targetPosition[0]-Math.cos(Math.toRadians(barrel_elevation))*
								Math.sin(Math.toRadians(barrel_direction))*barrel_length);//X
			bomb_position_init[1]=(float) (targetPosition[1]+Math.sin(Math.toRadians(barrel_elevation))*
									barrel_length);//Y
			bomb_position_init[2]=(float) (targetPosition[2]-Math.cos(Math.toRadians(barrel_elevation))*
							   Math.cos(Math.toRadians(barrel_direction))*barrel_length);//Z
//			�����ڵ�
			archie_bomb_List.add(new BombForControl(gv,bomb_ball,bomb_position_init,barrel_elevation,
								barrel_direction));
			gv.activity.playSound(7,0);
			oldTime=System.nanoTime();
		}
	}
}
