package com.bn.clp;
import static com.bn.clp.Constant.*;
import static com.bn.clp.MyGLSurfaceView.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bn.st.d2.DBUtil;
import com.bn.st.d2.MyActivity;

//��������״̬���߳�
public class KeyThread extends Thread
{
	MyGLSurfaceView mgmv; 
	MyActivity ma;
	public boolean flag=true;
	public boolean moveFlag=true;
	public static boolean otherBoatFlag=true;
	boolean pzFlag=false;
	public static boolean upFlag=true;
	float bOldZ;
	
	int[] stepIndex;//������
	int[] stepStatus;//0-�� 0-С��  2-����
	  
	int[] stepIndexC;//С������
	float[][] bdbqs;//������ʼ
	float[][] bdbjb;//���󲽽���
	int[] bdbzxb;//������С��
	
	int dqCount=0;//����������
	
	public KeyThread(MyGLSurfaceView mv,MyActivity ma)
	{
		this.setName("KeyThread");
		
		this.mgmv=mv;
		this.ma=ma;
		stepIndex=new int[MyGLSurfaceView.qtCount];
  	    stepStatus=new int[MyGLSurfaceView.qtCount];
  	    stepIndexC=new int[MyGLSurfaceView.qtCount];
  	    bdbqs=new float[MyGLSurfaceView.qtCount][2];
  	    bdbjb=new float[MyGLSurfaceView.qtCount][2];
  	    bdbzxb=new int[MyGLSurfaceView.qtCount];
	}
	
	public void run() 
	{ 
		while(flag)
		{		
			if(moveFlag)
			{
				synchronized(lockA)
				{
					if(!mgmv.isShaChe)
					{
						if(CURR_BOAT_V<Max_BOAT_V)
						{
							CURR_BOAT_V=CURR_BOAT_V+BOAT_A;
						}
						else if(CURR_BOAT_V>Max_BOAT_V)
						{
							CURR_BOAT_V=CURR_BOAT_V-BOAT_A;
						}
					}
					else
					{
						if(CURR_BOAT_V>0)
						{
							CURR_BOAT_V=CURR_BOAT_V+BOAT_A;
						}
						else
						{
							CURR_BOAT_V=0;
						}
					}
					
					CURR_BOAT_V_TMD=CURR_BOAT_V/Max_BOAT_V;
					if(CURR_BOAT_V_TMD>1)
					{
						CURR_BOAT_V_TMD=1;
					}
					
					if((MyGLSurfaceView.keyState&0x1)!=0) 
					{//��UP������
						float xOffset=0;//�˲���Xλ��
			    		float zOffset=0;//�˲���Zλ��  
			    		
			    		xOffset=(float)-Math.sin(Math.toRadians(sight_angle))*CURR_BOAT_V;
		  			    zOffset=(float)-Math.cos(Math.toRadians(sight_angle))*CURR_BOAT_V;
		  			    
		  			    //�����˶���Ĵ���XZֵ
		  			    float tempbx=bx+xOffset;
		  			    float tempbz=bz+zOffset;
	  			    
		  			    //�жϴ�ͷ����λ�õ�½�ظ߶��Ƿ����ˮ�沢��û�����Ŷշ�����ײ
		  			    if(isYachtHeadCollectionsWithLand(tempbx,tempbz)&&!isPZ(tempbx,tempbz))
		  			    {
		  			    	bOldZ=bz;
		  			    	
		  			    	bx=tempbx;   
		  			    	bz=tempbz;
		  			    	if(CURR_BOAT_V>CURR_BOAT_V_PZ)
		  			    	{
		  			    		pzFlag=false;
		  			    	}	
		  			    }  
		  			    else
		  			    {
		  			    	Constant.CURR_BOAT_V=0;
		  			    	if(pzFlag==false&&SoundEffectFlag)
		  			    	{
		  			    		ma.shengyinBoFang(1, 0);
		  			    		pzFlag=true;
		  			    	}
		  			    }
					}					
					
					if((MyGLSurfaceView.keyState&0x4)!=0)
					{//��left������
						//����ת������
						sight_angle=sight_angle+DEGREE_SPAN;
						//�����Ӿ�������б
						if(yachtLeftOrRightAngle<yachtLeftOrRightAngleMax)
						{
							yachtLeftOrRightAngle=yachtLeftOrRightAngle+yachtLeftOrRightAngleA;
						}
						else
						{
							yachtLeftOrRightAngle=yachtLeftOrRightAngleMax;
						}
					}
					else if((MyGLSurfaceView.keyState&0x8)!=0)
					{//��right������
						//����ת������
						sight_angle=sight_angle-DEGREE_SPAN; 
						//�����Ӿ�������б
						if(yachtLeftOrRightAngle>-yachtLeftOrRightAngleMax)
						{
							yachtLeftOrRightAngle=yachtLeftOrRightAngle-yachtLeftOrRightAngleA;
						}
						else
						{
							yachtLeftOrRightAngle=-yachtLeftOrRightAngleMax;
						}
					}
					else
					{//��������û�а��£��򷫴��Ӿ��ϲ���б
						if(yachtLeftOrRightAngle<0)
						{
							yachtLeftOrRightAngle=yachtLeftOrRightAngle+yachtLeftOrRightAngleA;
						}
						else if(yachtLeftOrRightAngle>0)
						{
							yachtLeftOrRightAngle=yachtLeftOrRightAngle-yachtLeftOrRightAngleA;
						}
					}
					
//					if(isFirstPersonView)
//					{
//						//�����µ������XZ����
//				    	cx=(float)(bx);//�����x����
//				        cz=(float)(bz);//�����z����
//						
//						//�����µĹ۲�Ŀ���XZ����
//				    	tx=(float)(cx-Math.sin(Math.toRadians(sight_angle))*DISTANCE+0.5f);//�۲�Ŀ���x���� 
//				        tz=(float)(cz-Math.cos(Math.toRadians(sight_angle))*DISTANCE);//�۲�Ŀ���z����   
//					}
//					else
//					{
						//�����µ������XZ����
				    	cx=(float)(bx+Math.sin(Math.toRadians(sight_angle-yachtLeftOrRightAngle/2))*DISTANCE);//�����x����
				        cz=(float)(bz+Math.cos(Math.toRadians(sight_angle-yachtLeftOrRightAngle/2))*DISTANCE);//�����z����
				    	
				    	//�����µĹ۲�Ŀ���XZ����
				    	tx=(float)(cx-Math.sin(Math.toRadians(sight_angle-yachtLeftOrRightAngle/2))*DISTANCE);//�۲�Ŀ���x���� 
				        tz=(float)(cz-Math.cos(Math.toRadians(sight_angle-yachtLeftOrRightAngle/2))*DISTANCE);//�۲�Ŀ���z����  
//					}
				}
			}
			
			isHalfForBoat(bx,bz);
			isOneCycleForBoat(bx,bz);  			
			
			if(otherBoatFlag)
			{
				for(int i=0;i<mgmv.otherPaths.size();i++)
	  		  	{
	  			  if(stepStatus[i]==0)
	  			  {//���Ǵ󲽵���
	  				  ArrayList<float[]> pathCurr=mgmv.otherPaths.get(i); 
	      			  bdbqs[i][0]=pathCurr.get(stepIndex[i])[0];
	      			  bdbqs[i][1]=pathCurr.get(stepIndex[i])[1];
	      			  stepIndexC[i]=0;
	      			  float bdbjsX=pathCurr.get((stepIndex[i]+1)%pathCurr.size())[0];
	      			  float bdbjsZ=pathCurr.get((stepIndex[i]+1)%pathCurr.size())[1];
	      			  double distance=Math.sqrt((bdbjsX-bdbqs[i][0])*(bdbjsX-bdbqs[i][0])+(bdbjsZ-bdbqs[i][1])*(bdbjsZ-bdbqs[i][1]));
	      			       
	      			  if(distance<1)
	      			  {
	      				  stepIndex[i]=stepIndex[i]+1;
	      				  if(stepIndex[i]==pathCurr.size())
	          			  {
	          				  stepIndex[i]=0; 
	          				  Constant.BOAT_LAP_NUMBER_OTHER[i]=Constant.BOAT_LAP_NUMBER_OTHER[i]+1;
	          				  //��Ȧ������
	          				  if(Constant.BOAT_LAP_NUMBER_OTHER[i]==3)
	          				  {
	          					stepStatus[i]=2;
	          					Constant.RANK_FOR_HELP=Constant.RANK_FOR_HELP+1;
	          				  }
	          			  } 
	      			  }
	      			  else
	      			  {
	      				  bdbzxb[i]=(int) (distance/Constant.Max_BOAT_V_OTHER[i]);
	          			  bdbjb[i][0]=(bdbjsX-bdbqs[i][0])/bdbzxb[i];
	          			  bdbjb[i][1]=(bdbjsZ-bdbqs[i][1])/bdbzxb[i];
	          			  
	          			  float degree=(float) Math.toDegrees(Math.atan2(bdbjb[i][0], bdbjb[i][1]));
	          			  MyGLSurfaceView.otherBoatLocation[i][2]=degree+180;
	          			  
	          			  stepStatus[i]=1;
	          			  stepIndex[i]=stepIndex[i]+1;
	          			  
	          			  if(stepIndex[i]==pathCurr.size())
	          			  {
	          				  stepIndex[i]=0; 
	          				  Constant.BOAT_LAP_NUMBER_OTHER[i]=Constant.BOAT_LAP_NUMBER_OTHER[i]+1;
	          				  //��Ȧ������
	          				  if(Constant.BOAT_LAP_NUMBER_OTHER[i]==3)
	          				  {
	          					stepStatus[i]=2;
	          					Constant.RANK_FOR_HELP=Constant.RANK_FOR_HELP+1;
	          				  }
	          			  } 
	          			  if(stepStatus[i]!=2)
	          			  {
	          				  MyGLSurfaceView.otherBoatLocation[i][0]=bdbqs[i][0]+ bdbjb[i][0]*stepIndexC[i];
	              			  MyGLSurfaceView.otherBoatLocation[i][1]=bdbqs[i][1]+ bdbjb[i][1]*stepIndexC[i]; 
	              			  stepIndexC[i]++; 
	          			  }
	      			  }
	  			  }
	  			  else if(stepStatus[i]==1)
	  			  {
	      			  MyGLSurfaceView.otherBoatLocation[i][0]=bdbqs[i][0]+ bdbjb[i][0]*stepIndexC[i];
	      			  MyGLSurfaceView.otherBoatLocation[i][1]=bdbqs[i][1]+ bdbjb[i][1]*stepIndexC[i]; 
	      			  stepIndexC[i]++;
	      			  if(stepIndexC[i]>=bdbzxb[i])
	      			  {
	      				  stepStatus[i]=0;
	      			  }
	  			  }         			  
	  		  	}
			}
			
			if(moveFlag)
			{
				if(dqCount>0)
				{
					dqCount--;
				}
				else if(dqCount<=0)
				{
					dqCount=0;
					Max_BOAT_V=Max_BOAT_V_VALUE;
				}
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	} 
	//�ж��Ƿ����Ŷշ�����ײ�ķ���
	public boolean isPZ(float bx,float bz)
	{
		
		//���������ײ��������
		float bPointX=(float) (bx-BOAT_UNIT_SIZE*Math.sin(Math.toRadians(sight_angle)));
		float bPointZ=(float) (bz-BOAT_UNIT_SIZE*Math.cos(Math.toRadians(sight_angle)));
		
		//������ײ���ڵ�ͼ�ϵ��к���
		float carCol=(float) Math.floor((bPointX+UNIT_SIZE/2)/UNIT_SIZE);  
		float carRow=(float) Math.floor((bPointZ+UNIT_SIZE/2)/UNIT_SIZE);
		
		for(PZZ temp:ma.gameV.pzzList)
		{ 
			if(temp.row==carRow&&temp.col==carCol)
			{
				if(temp.isIn(bPointX, bPointZ))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	//�жϴ�ͷ����λ�õ�½�ظ߶��Ƿ����ˮ��
	public boolean isYachtHeadCollectionsWithLand(float bx,float bz)
	{
		final float PRE_UNIT_SIZE=UNIT_SIZE/(yArray_ZD.length-1);
		
		//���������ײ��������
		float tempbx=(float) (bx-BOAT_UNIT_SIZE*Math.sin(Math.toRadians(sight_angle)));
		float tempbz=(float) (bz-BOAT_UNIT_SIZE*Math.cos(Math.toRadians(sight_angle)));  
		
	    //������ײ���ڵ�ͼ�ϵ��к���
		float col=(float) Math.floor((tempbx+UNIT_SIZE/2)/UNIT_SIZE);
		float row=(float) Math.floor((tempbz+UNIT_SIZE/2)/UNIT_SIZE);	
		
		int id=MAP_ARRAY[(int) row][(int) col];   
		if(id==8)
		{
			return true;
		}

		float colx=col*UNIT_SIZE-UNIT_SIZE/2;
		float rowz=row*UNIT_SIZE-UNIT_SIZE/2;
				
		//������ײ���ڶ�Ӧ�����и����е�x��z���꣬ÿ��С���ӵ����ĵ㼴Ϊ�ø��ӵ�����ԭ��
		float rawXIn=tempbx-colx;
		float rawZIn=tempbz-rowz;
	    
	    float xIn=rawXIn;
		float zIn=rawZIn;
		
		if(id==1||id==10)
    	{
    		xIn=UNIT_SIZE-rawZIn;
    		zIn=rawXIn;
    	}
		else if(id==3||id==12)
    	{
    		xIn=UNIT_SIZE-rawZIn;
    		zIn=rawXIn;
    	}
		else if(id==4||id==13)
    	{
    		xIn=UNIT_SIZE-rawXIn;
    		zIn=UNIT_SIZE-rawZIn;
    	}
    	else if(id==5||id==14)
    	{
    		xIn=UNIT_SIZE-rawZIn;
    		zIn=rawXIn;
    	}
    	else if(id==6||id==15)
    	{
    		xIn=rawZIn;
    		zIn=UNIT_SIZE-rawXIn;
    	}
		
		 float[][] yArrayCurr=null;
		 if(id==0||id==1||id==9||id==10)	//ֱ��(��������)
		 {
			 yArrayCurr=yArray_ZD;
		 }
		 else if(id==2||id==3||id==11||id==12)	//ֱ��(����������Сɽ)
		 {
			 yArrayCurr=yArray_ZD_DXD;
		 }
		 else if(id==4||id==5||id==6||id==7||id==13||id==14|id==15|id==16)	//���
		 {
			 yArrayCurr=yArray_WD;
		 }
		//���㴬ͷ��Ӧ��½�ظ��ӵ��С���
	    int tempCol=(int)(xIn/PRE_UNIT_SIZE);
	    int tempRow=(int)(zIn/PRE_UNIT_SIZE);		    
    	
    	//���㴬ͷ��Ӧ��½�ظ��ӵ��ĸ�������� 
	    float x0=tempCol*PRE_UNIT_SIZE;
	    float z0=tempRow*PRE_UNIT_SIZE;
	    float y0=yArrayCurr[tempRow][tempCol]; 
	        
	    float x1=x0+PRE_UNIT_SIZE;
	    float z1=z0;
	    float y1=yArrayCurr[tempRow][tempCol+1];
	    
	    float x2=x0+PRE_UNIT_SIZE;
        float z2=z0+PRE_UNIT_SIZE;
        float y2=yArrayCurr[tempRow+1][tempCol+1];
        
	    float x3=x0;
	    float z3=z0+PRE_UNIT_SIZE;
	    float y3=yArrayCurr[tempRow+1][tempCol];
	    		    
	    //��ͷ����½�ظ߶�
	    float cty=0;
	    
	    if(isInTriangle(x0,z0,x1,z1,x3,z3,xIn,zIn))
	    {//�жϷ�����ͷ�Ƿ�λ��0-1-3������
	    	//��0-1-3���ڴ�ͷ���ĸ߶�
	    	cty=fromXZToY
		    (
			    	x0,y0,z0,				    	
			    	x3,y3,z3,
			    	x1,y1,z1,
			    	xIn,zIn
			 );
	    }
	    else
	    {
	    	//��1-2-3���ڴ�ͷ���ĸ߶�
	    	cty=fromXZToY
		    (
			    	x1,y1,z1,
			    	x3,y3,z3,
			    	x2,y2,z2,
			    	xIn,zIn
			);
	    }
	    if(cty<=0)
	    {//����ͷ����½�ص���ˮ���򷵻�true
	    	return true;
	    }
	    return false;
	}
	
	//�ж�һ�����Ƿ����������ڵķ���
	//�����㷨˼����������Ҫ���жϵĵ㵽���������������ʸ��1��2��3
	//Ȼ������ʸ�����������������ͬ�����λ���������ڣ�����λ����������
	public boolean isInTriangle
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
	public float fromXZToY
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
	
	//����ӵģ���Ҫ���ж��Ƿ�һȦ����====================================================
	final float RACE_HALF_X=14*UNIT_SIZE;
	final float RACE_HALF_Z=20*UNIT_SIZE;
	final float RACE_BEGIN_X=30;
	final float RACE_BEGIN_Z=90;
	public void isHalfForBoat(float carTempX,float carTempZ)
	{
		double dis=Math.sqrt
		(
			(carTempX-RACE_HALF_X)*	(carTempX-RACE_HALF_X)
			+(carTempZ-RACE_HALF_Z)*(carTempZ-RACE_HALF_Z)
		);
		if(dis<=120)
		{
			halfFlag=true;
		}
	}
	//�Ƿ�����һȦ�ķ���
	public void isOneCycleForBoat(float carTempX,float carTempZ)
	{
		double dis=Math.sqrt
		(
				(carTempX-RACE_BEGIN_X)*(carTempX-RACE_BEGIN_X)
				+(carTempZ-RACE_BEGIN_Z)*(carTempZ-RACE_BEGIN_Z)
		);
		
		if(dis<=60&&bOldZ>RACE_BEGIN_Z&&carTempZ<=RACE_BEGIN_Z)
		{
			if(halfFlag==true)
			{
				numberOfTurns=numberOfTurns+1;//Ȧ����1
				if(numberOfTurns==3&&isSpeedMode)	//��Ϊ����ģʽ��������Ȧ���󣬻�õ�ǰ�����Σ����Ұ��ٶȺͼ��ٶ�����Ϊ0
				{
					RANK_FOR_HERO_BOAT=RANK_FOR_HELP;
					CURR_BOAT_V=0;
					BOAT_A=0;
					String currSysTime=Constant.getCurrTime();
					String currUseTime=Constant.getUseTime();
					DBUtil.insertRcRDatabase(currSysTime, currUseTime, RANK_FOR_HERO_BOAT); 
					//�����Ի���
					ma.hd.sendEmptyMessage(12);
				}
				else if(numberOfTurns==3&&!isSpeedMode)		//��Ϊ��ʱģʽ
				{
					CURR_BOAT_V=0;
					BOAT_A=0;
					String currSysTime=Constant.getCurrTime();
					String currUseTime=Constant.getUseTime();
					List<String> alist=DBUtil.getTimeFromJSDatabase();
					isBreakRecord=isFast(currUseTime,alist);
					DBUtil.insertJSDatabase(currSysTime, currUseTime);
					//�����Ƿ��Ƽ�¼�ĶԻ���
					ma.hd.sendEmptyMessage(11);
				}
			} 
			halfFlag=false;
		}
		else if(dis<=60&&bOldZ<=RACE_BEGIN_Z&&carTempZ>RACE_BEGIN_Z)
		{
			halfFlag=false;
		}
	}	
	//���������ʱ�Ƿ�Ϊ���ʱ����жϷ���
	public static boolean isFast(String currTime,List<String> aList)
	{//�����true���ǵ�ǰʱ��Ϊ���ʱ�䣬���Ƽ�¼������ѵ�ǰʱ���ȵ������ʱ��
		boolean result=false;
		//����ǰ��ʱ���ԡ�:���ֿ�
		String[] str=currTime.split(":");
		int currT=Integer.parseInt(str[0])*60*100+Integer.parseInt(str[1])*100+Integer.parseInt(str[2]);
		List<Integer> tempInteger=new ArrayList<Integer>();
		for(int i=0;i<aList.size();i++)
		{
			String[] tempStr=aList.get(i).split(":");
			tempInteger.add(Integer.parseInt(tempStr[0])*60*100+Integer.parseInt(tempStr[1])*100+Integer.parseInt(tempStr[2]));
		}
		Collections.sort(tempInteger);
		if(tempInteger.size()==0)
		{
			result=true;
		}
		else if(currT<tempInteger.get(0))
		{
			result=true;
		}
		return result;
	}
}