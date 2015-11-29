package com.bn.clp;

import static com.bn.clp.MyGLSurfaceView.*;

//��ʱ������ײ�ɵĲ������罻ͨͲ�Ʋ������Ƿ���ײ���߳�
public class ThreadColl extends Thread
{
	MyGLSurfaceView surface;
	
	public boolean flag=true;
	
	public ThreadColl(MyGLSurfaceView surface)
	{ 
		this.surface=surface;
		this.setName("ThreadColl");
	}
	 
	public void run()
	{
		while(flag) 
		{			
			for(KZBJForControl kzbjfcTemp:surface.kzbjList)
			{
				if(!kzbjfcTemp.state)
				{
					kzbjfcTemp.checkColl(bxForSpecFrame, bzForSpecFrame, angleForSpecFrame);
				}				
				kzbjfcTemp.go();
			}
			try
			{
				Thread.sleep(50);//50
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}