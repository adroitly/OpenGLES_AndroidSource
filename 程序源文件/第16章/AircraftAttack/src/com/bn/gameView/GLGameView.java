package com.bn.gameView;
import static com.bn.gameView.Constant.*;

import java.util.ArrayList;
import java.util.Collections;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.view.MotionEvent;
import android.widget.Toast;

import com.bn.archieModel.ArchieForControl;
import com.bn.archieModel.BarbetteForDraw;
import com.bn.archieModel.BarrelForDraw;
import com.bn.arsenal.Arsenal_House;
import com.bn.arsenal.House;
import com.bn.arsenal.PlaneHouse;
import com.bn.commonObject.BallTextureByVertex;
import com.bn.commonObject.CubeForDraw;
import com.bn.commonObject.DamForDraw;
import com.bn.commonObject.DrawBomb;
import com.bn.commonObject.LandForm;
import com.bn.commonObject.Light_Tower;
import com.bn.commonObject.NumberForDraw;
import com.bn.commonObject.SkyBall;
import com.bn.commonObject.SkyNight;
import com.bn.commonObject.TextureRect;
import com.bn.commonObject.Tree;
import com.bn.core.MatrixState;
import com.bn.core.SQLiteUtil;
import com.bn.core.ShaderManager;
import com.bn.menu.Aircraft_Activity;
import com.bn.menu.MissileMenuForDraw;
import com.bn.menu.R;
import com.bn.planeModel.EnemyPlane;
import com.bn.planeModel.Plane;
import com.bn.tankemodel.MoXingJiaZai;
import com.bn.tankemodel.Model;
import com.bn.tankemodel.TanKe;
public class GLGameView extends GLSurfaceView 
{
	public Aircraft_Activity activity;//��Activity����
	private final float TOUCH_SCALE_FACTOR = 180.0f/480;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��
    private int load_step=0;//������Դ�Ĳ���
	private boolean isLoadedOk=false;//�Ƿ������ɵı�־λ
    private float mPreviousY;//�ϴεĴ���λ��Y����
    //���������ز��� �������ط�����
    public static float cx;    
    public static float cy;
    public static float cz;
    public static float tx;
    public static float ty;
    public static float tz; 
    public static float upX=0;
    public static float upY=1;
    public static float upZ=0;
    //--------��Ȼ֡����ֵ
    float curr_cx;
	float curr_cy;
	float curr_cz;
	float curr_tx;
	float curr_ty;
	float curr_tz;
	float curr_upx;
	float curr_upy;
	float curr_upz;
	float curr_PlaneX;
	float curr_PlaneZ;
	float curr_PlaneY;
	float curr_rot_Plane_X;
	float curr_rot_Plane_Y;
	float curr_rot_Plane_Z;
	public KeyThread kThread;
	public static boolean isVideoPlaying=true;//�Ƿ��ڲ��Ž��������ͣ���棬trueΪ����״̬
    //͸��ͶӰ�����ű�
    public  float ratio;
    //--------3D���------------------------------
    TextureRect loadingView;//3D�еļ��ؽ���
    TextureRect processBar;//���ؽ����еĽ�����
    LandForm terrain[]=new LandForm[LANDS_SIZE];//����½��    0 ��ʾyz��������б��1��ʾ�ս�λ�ã��ȸ������ﰼ
    TextureRect terrain_plain;//����ƽ���ͼ     ƽ����XYƽ���
    TextureRect water;//ˮ��
    //----------------------------------
    TextureRect fireButton;//����ť 
    TextureRect radar_bg;//�״ﱳ��
    TextureRect radar_plane;//�״��ָ��ɻ�
    TextureRect weapon_button;//����ѡ��ť
    NumberForDraw weapon_number;//���ڱ�ʶ�ӵ����ڵ�������
    
    TextureRect up_button;//���ϰ�ť
    TextureRect down_button;//����ѡ��ť
    NumberForDraw numberRect;//����
    TextureRect leftTimeRect;//ʣ��ʱ��
    TextureRect backgroundRect_blood;//��ʾѪ��������
    TextureRect plane_Hit;//�ɻ������б�ʾ����
    DamForDraw dam;//���
    
    
    
    public static TextureRect bombRect;//��ը���� 
    public static TextureRect bombRectr;//��ը����
    public static float bomb_width=40;
    public static float bomb_height=50;//��ը������εĴ�С
    
    SkyBall skyBall;//��������
    SkyBall skyBallsmall;//�Ź������ֵ����
    SkyNight skynight;//���ϵ����
    SkyNight skynightBig;//��һ�������
    public  Plane plane;//�ɻ�
    BallTextureByVertex bullet_ball;//�ӵ�������
    public TextureRect bullet_rect;//�ӵ��������
    //�������������
    BarrelForDraw barrel;
    BarbetteForDraw barbette;
    CubeForDraw cube;
    ArchieForControl archie;//������

    public Model tanke_body;//̹������
    public Model tanke_gun;//̹���ڹ�
    public static ArrayList<TanKe> tankeList=new ArrayList<TanKe>();
    public static ArrayList<DrawBomb> baoZhaList=new ArrayList<DrawBomb>();//̹�˱�ը�����б�
    public  ArrayList<DrawBomb> copybaozhaList=new ArrayList<DrawBomb>();//̹�˱�ը�����б�
    public static ArrayList<EnemyPlane> enemy=new ArrayList<EnemyPlane>();
    Light_Tower lighttower;//����
    public static ArrayList<BombForControl> cop_archie_bomb_List=new ArrayList<BombForControl>();//�������ڵ����б�
    public static ArrayList<BombForControl> cop_bomb_List=new ArrayList<BombForControl>();//�����ȥ���ӵ��б�
    public static ArrayList<ArchieForControl> cop_archie_List=new ArrayList<ArchieForControl>();//�����ڵ��б�
    public static ArrayList<BulletForControl> cop_bullet_List=new ArrayList<BulletForControl>();//�����ȥ���ӵ��б�
    public static ArrayList<BombForControl> copy_tank_bomb_List=new ArrayList<BombForControl>();//�����ȥ��̹���ڵ��б�
    public static ArrayList<Tree> treeList=new ArrayList<Tree>();//�����ϵ���
    House house;//�����ģ��
    public static ArrayList<Arsenal_House> arsenal=new ArrayList<Arsenal_House>();//�����
    public ArrayList<PlaneHouse> houseplane=new ArrayList<PlaneHouse>();
    public CubeForDraw housePlane;//ƽ��
    public Light_Tower chimney;//�̴�

    //�˵�����Ƶ���Ž��������
    TextureRect menu_Rect;//�ɻ���ը��Ĳ˵���ʾ����
    TextureRect menu_video;//��Ƶ���Ž���ĸ�����ť����
    TextureRect mark_placeRect;//��־��λ�õ����Ǳ����ϵġ�
    public TextureRect mark_lock;//��Ǳ������ľ���
    public TextureRect treeRect;
    public TextureRect mark_aim;//Ŀ���ǿ�
    public TextureRect noticeRect;//ս��˵������
    public float initNoticeHeight=-0.8f;//��ʼ��ʾ���ֵĸ߶�

    //------��Ϸ�������ID---------------------------------------------------
    //---------��Ϸ��ʼǰ��˵������
    private int tex_noticeId[]=new int[6];
    private int tex_actionWinId;//�����ж��ɹ��Ի���
    private int tex_actionFailId;//ʧ�ܶԻ���
    private int tex_numberRectId;//��������
    private int tex_backgroundRectId;//Ѫ����ͼƬ
    private int tex_lefttimeId;//ʣ��ʱ������
    private int tex_lighttowerid;
    private int tex_lightid;//����������
    private int tex_loadingviewId;//���ؽ����ID
    private int tex_processId;//������
    private int tex_terrain_tuceng_Id;//�������� ----����
    private int tex_terrain_caodiId;//��������-----�ݵ�
    private int tex_terrain_shitouId;//��������------ʯͷ
    private int tex_terrain_shandingId;//��������------ʯͷ
    private int tex_fireButtonId;//����ť������
    private int tex_skyBallId;//���������
    private int tex_nightId;//�����������
    private int tex_waterId;//ˮ������
    private int tex_bulletId;//�ӵ�����
    private int tex_radar_bg_Id;//�״ﱳ������
    private int tex_radar_plane_Id;//�״�ķɻ�ָ��
    private int tex_button_weaponId[]=new int[2];//������ťͼ��
    private int tex_button_upId;//���ϰ�ť����
    private int tex_button_downId;//���°�ť����
    private int tex_tankeid;//̹��ID
    private int tex_roofId;//�ݶ�
    private int tex_frontId;//���ݲ�������
    private int tex_AnnulusId;//Χ�Ʒ���ת��Բ������
    private int tex_damId;//��ӵ�����
    private int tex_chimneyId;//�̴�����id
    private int tex_housePlaneId[]=new int[2];//ƽ��������Id
    private int tex_housePlaneSmallId[]=new int[2];//Сƽ������
    public int treeTexId;//������
    public int treeTexId_2;//�ڶ�����
    public static int baoZhaXiaoguo2;//��ըЧ��2
    public static  int baoZhaXiaoguo;//��ըЧ������
    public static  int baoZhaTexId[];//��ը��������
    //�ɻ������е�����
    public int tex_plane_hitId;//�ɻ������еı�ʾ�������
    public int tex_locktexId;//������������
    public int tx_lockaimtexId;//Ŀ�������
    //����˵����������ɻ�׹�ٺ��
    public int tex_menu_text;//����
    public int tex_menu_text_win;//Ӯ�˵�
    //��Ƶ���Ű�ť����ʾ���̰�ť
    public int stopId;//ֹͣ��ť
	public int pauseId;//��ͣ��ť
	public int playId;//���Ű�ť
    //��־��λ�õ����Ǳ����ϵ�
	public int tex_mark_tanke;//̹�˺͸������Ǳ���ͼ��
	public int tex_mark_ackId;//�л��Ǳ���ͼ��
	public int tex_mark_arsenalId;//������Ǳ���ͼ��
	public int tex_mark_planeId;//�ɻ��Ǳ���ͼ��
    //-------�ɻ����������
    public int planeHeadId;				//��ͷ
	public int frontWingId;				//ǰ��������
	public int frontWing2Id;			//ǰ��������2
	public int bacckWingId;				//���������
	public int topWingId;				//�ϻ�������
	public int planeBodyId;				//��������
	public int planeCabinId;			//��������
	public int cylinder1Id;				//Բ������1
	public int cylinder2Id;				//Բ������2
	public int screw1Id;				//����������
	//---------�����ڵ��������
	public int[] texBarbetteId=new int[2];//0��ʾ��̨Բ������1��ʾ��̨��Բ������
	public int texCubeId;//��������
	public int[] texBarrelId=new int[4];//����0��ʾ����ͲԲ��,1��ʾ����ͲԲ��,2��ʾ����ͲԲ��,3��ʾ����ͲԲ��
    //������ƶ�
    float time_span=10;//����ֻÿ���ƶ��ľ���
    float degree_span=10;//����ֻÿ����ת�ؽǶ�
    
    float[] fa=new float[16];
    float[] fb=new float[16];
    float[] fc=new float[16];
	float[] resultUp=new float[4];  
	float[] resultxUp=new float[4];
	float[] YB=new float[4];
    float lightAngle=0;
    //--------------------------�����˵�����
    public MissileMenuForDraw missile_menu;//���������˵�
    public TextureRect menu_Background;//�����˵����ֵĴ�ر���
    public TextureRect menu_clouds;//�����˵����ֵ��Ʋ�
    public TextureRect front_frame;//�����˵�������ǰ��Ŀ��
    public TextureRect front_cover_button;//�����˵�������ǰ�������
    public TextureRect front_door;//�����˵����ֻ�����
    public TextureRect front_door_bg;//���ײŲ˵��Ĳ��ֻ�����
    public TextureRect menu_setting;//����ҳ��İ�ť
    public TextureRect helpView;//��������
    public TextureRect aboutView;//���ڽ���
    public NumberForDraw rank_number;//���а���������� 
    public TextureRect map_name;//���а�����ͼ������
    //-----------------ѡ�ɻ�������ز���--------------------------------------
    public float planeRotate=0;
    
    
    
    //-------------------------------�����˵���----------------------------
	public int planeModelIndex=0;//0��ʾ��һ�ҷɻ�,1�ڶ��ܷɻ�,2�����ܷɻ�
    CircleForDraw circle_station;//�ɻ���չʾ̨
    TextureRect backgroundRect;//�ɻ�չʾ̨�ı���
    
    Model planeModel[]=new Model[3];
    int planeModelTexId[]=new int[3];
    //���ܷɻ������ű���
	public static final float RATIO_PLANE=1.0f;
	
	TextureRect plane_select_head;//ѡ�ɻ������еı�����
	TextureRect plane_select_plane;//ѡ��ɻ���ť
	TextureRect menu_two_game_model_btn;//ѡ����Ϸģʽ��ť
	
	TextureRect menu_two_button;//�����˵��еİ�ť
	TextureRect menu_two_plane_icon;//�����˵��е������ɻ���ģ��ͼƬ
	
	int tex_plane_select_head;//��������
	int tex_plane_select_planeIndex=0;//ѡ�ɻ���ť��������
	int tex_plane_select_modelIndex=0;//ѡģʽ��ť����
	int tex_menu_two_war_btnIndex=1;
	int tex_menu_two_war_btnId[]=new int[2];//ս��ģʽ
	int tex_menu_two_action_btnIndex;
	int tex_menu_two_action_btnId[]=new int[2];//�ر��ж�
	int tex_model_select_promptId;//ѡ��ģʽ��ʾ
	int tex_menu_two_okIndex;//ȷ����ť
	int tex_menu_two_leftIndex;//�󰴰�ť
	int tex_menu_two_rightIndex;//�Ұ���ť
	int tex_menu_two_okId[]=new int[2];//ȷ�ϰ�ť
	int tex_menu_two_leftId[]=new int[2];//�󰴰�ť
	int tex_menu_two_rightId[]=new int[2];//�Ұ���ť
	int tex_special_action_bgId;//�ر��ж�����ͼ
	int tex_menu_two_plane_iconIndex[]={1,0,0};//�˵����зɻ�ͼƬ����ID 
	int tex_menu_two_plane_iconId[][]=new int[3][2];//�˵����зɻ�ͼƬ����
	
	
	
	public boolean isPlaneBtnSelected=false;//�ɻ�ѡ��ť״̬
	public boolean isDrawGameModelView=false;//�ɻ�ѡ��ť״̬
	public boolean idPlaneSelectedPrompt=true;//ѡ��ɻ�����ʾ
	public boolean isModelSelectedPrompt=false;//ѡ��ģʽ����ʾ
	public boolean isPlaneSelectOk=false;//�����ɻ�ѡ��ť���OK��ť
	public boolean isModelSelectOk=false;//����ģʽѡ��ť���OK��ť
	public boolean isPlaneBtnDown=false;//ѡ�ɻ���ť�Ƿ��Ѿ�����
	public boolean isModeBtnDown=false;//ѡģʽ��ť�Ƿ��Ѿ�����
	
	//----------------��Ϸģʽ------
	public int isGameMode;//0ս��ģʽ1�ر��ж�
	
	//-----ѡ��ɻ���ť��ģʽ   0��ʾ����ģʽ  1��ʾѭ���任ģʽ ,2 ��ʾ����ģʽ
	public int planeModeType=1;
	//-----ѡ��ģʽ��ť��ģʽ   0��ʾ����ģʽ  1��ʾѭ���任ģʽ ,2 ��ʾ����ģʽ
	public int war_button_mode=1;
	//----��ť����ȥѡ�ɻ�����---0��ʾչ��,1��ʾ�ر�,2��ʾ������ʾ
	public int selectPlaneOPen=0;
	//----��ť����ȥѡģʽ����---0��ʾչ��,1��ʾ�ر�,2��ʾ������ʾ
	public int selectModelOPen=0;
    //��ǰ�Ĳ�͸����
	public float currAlpha_planeBtn=1.0f;
	public float operator_planeBtn=-1;
	public float currAlpha_modelBtn=1.0f;
	public float operator_modelBtn=-1;
	//��ǰ���λ��
	public float currX=0;
	public float operator2=1;
    
    //-------------------�˵����ֵ�����--------------------------------
    public int tex_rectId[]=new int[11];//�����˵�������
    public int tex_bgId;//�����˵��µı���ͼ
    public int tex_cloudsId;//�����˵��µ��Ʋ�
    public int tex_front_frameId;//�����˵���ǰ�ߵ�ǰ��ͼ
    public int tex_front_coverId;//�����˵���ǰ�ߵ�����
    public int tex_menu_doorId;//����������
    public int tex_musicId[]=new int[2];//�Ƿ�����������
    public int tex_soundId[]=new int[2];//�Ƿ�����Ч��������
    public int tex_vibrateId[]=new int[2];//�Ƿ�����
    public int tex_helpId;//��������Id
    public int tex_aboutId;//���ڽ���Id
    public int tex_mapSelectedBgId;//��ͼѡ�����ı���
    public int tex_mapId[]=new int[3];//��ͼ��������ͼ
    public int tex_rankBgId;//���а񱳾�ͼ
    public int tex_rankNumberId;//���а���������
    
    //-------------------------�˵�������ز���
    public  boolean isGameOn=false;//�ж���Ϸ�Ƿ��Ѿ���ʼ
    public float missile_rotation=0;//��������ת
    public boolean isTouchMoved=false;//��ǰ�Ƿ����ڴ����ƶ���
    public boolean hasInertia=false;//�жϵ�ǰ�Ƿ���Ҫ����
    public float ori_angle_speed=15;//�����˵����Ľ��ٶ�
    public float curr_angle_speed;//��ǰ�����˵���ת�ؽ��ٶ�
    public float ori_acceleratedSpeed=0.1f;//��ʼ���ٶ�
    public float curr_acceleratedSpeed;//��ǰ���ٶ�
    public boolean auto_adjust=false;//�Ƿ�����Զ�����
    public int curr_menu_index=0;//��ǰ�˵�ѡ�������
    public boolean isMissileDowning;//�жϵ����Ƿ���������
    public float missile_ZOffset_AcceSpeed=-0.4f;//������Z��ƫ�����ļ��ٶ�
    public float missile_ZOffset_Speed;//������Z��ƫ�������ٶ�
    public float missile_ZOffset_Ori=-1.5f;//������Z���ʼƫ����
    public float missile_ZOffset=missile_ZOffset_Ori;//������Z��ƫ����
    public float missile_YOffset=0;//���� Y���ƫ����
    public int doorState=1;//1��ʾ��,2��ʾ�ر� 0��ʾ�˶�
    public float door_YOffset=1.5f;//�����ŵ�Y��ƫ����   1.5~0.5
    public float door_YSpan=0.08f;//�����ŵ��ٶ�
  
    public int dialog_YesId=0;//�Ի�����ȷ����ť��ͼƬ����
    public int dialog_NoId=0;//�Ի����з��ذ�ť��ͼƬ����
    public boolean moveToExit;//�����ؼ��Ƿ��ƶ����˳���ť�˵���
    public float help_YOffset=0;//���������Y��ƫ����
    public float about_YOffset=0;//���ڽ����Y��ƫ����
    public boolean isPoint;//�����ť�¼��Ƿ�ɹ�
    public int isMenuLevel=0;//���ò˵�������� һ��,����,�����˵�
    public float rank_move;//���а��д��������ı���
    public boolean isDrawBaozha;//�����˵����Ƿ���Ʊ�ըͼ
    public float baozha_ratio;//������ըͼ�����ű���
    public float baozha_increase=0.05f;//������ըͼ����ճ����
    public boolean menu_button_move;//�˵���ť�Ƿ�����,�������ʼ��,�˵���ť����
    public float menu_button_speed=0.15f;//�˵���ť�������ٶ�
    public float menu_button_XOffset;//�˵���ť�������ٶ�
    //-------���ݿ���ز���-------
    ArrayList<String[]> rank=new ArrayList<String[]>();//�������ݿ��¼��Ϣ
    //---------�ر��ж������ڼ�¼ʱ��
    public int goTime;
    public float oldTime;
    //-------------�����ж��ɹ�ʧ�ܵ�״̬  1��ʾ�ɹ�2��ʾʧ��
    public int isSpecActionState;
    public boolean isTrueButtonAction=false;//ʵ����������ⰴť
    //̨�ӵ�����id
    public int stageId;//չ��̨������id
    //--------------�����¼���ز���----------------
    private boolean isOKButtonDown;
    private boolean isLeftButtonDown;
    private boolean isRightButtonDown;
    private boolean isWarButtonDown;
    private boolean isActionButtonDown;
    
    //------------------�����˵��иı䰴ť�Ĳ�͸����
    private boolean isChangeAlpha=true;
    private float currAlpha=1.0f;
    private int direction=-1;
    
    
    
	public GLGameView(Context context)	
	{
        super(context);
        activity=(Aircraft_Activity)context;
        this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
        this.setKeepScreenOn(true);        
        tx=PLANE_X=MapArray[mapId].length*WIDTH_LALNDFORM/2;
		ty=PLANE_Y;//�����������λ��
		tz=PLANE_Z=MapArray[mapId].length*WIDTH_LALNDFORM/2;
        cx=(float)(tx+Math.cos(Math.toRadians(ELEVATION_CAMERA))*Math.sin(Math.toRadians(DIRECTION_CAMERA))*DISTANCE);//�����x���� 
        cz=(float)(tz+Math.cos(Math.toRadians(ELEVATION_CAMERA))*Math.cos(Math.toRadians(DIRECTION_CAMERA))*DISTANCE);//�����z���� 
        cy=(float)(ty+Math.sin(Math.toRadians(ELEVATION_CAMERA))*DISTANCE);//�����y����
    }
	int shootId=2;//���䰴ť��ID
	int upId=2;
	long time;
	//�����¼��ص�����
	@Override 
    public boolean onTouchEvent(MotionEvent e) 
	{
		if(isGameOn)
		{
			int actionId=e.getAction()&MotionEvent.ACTION_MASK;//��ȡ�����¼�ID	
			//��ȡ��������id��downʱ������id����ȷ��upʱ����id��ȷ������idҪ��ѯMap��ʣ�µ�һ�����id��
			int id=(e.getAction()&MotionEvent.ACTION_POINTER_ID_MASK)>>>MotionEvent.ACTION_POINTER_ID_SHIFT;	
			float x=e.getX(id);
			float y=e.getY(id);
			switch(actionId)
			{
			case MotionEvent.ACTION_POINTER_DOWN:	
			case MotionEvent.ACTION_DOWN:
				if(isGameMode==1&&isSpecActionState==1)//�ر��ж�----------�ɹ�
	            {
					if(x>280*ratio_width&&x<540*ratio_width&&y>186*ratio_height&&y<270*ratio_height)//��һ����
					{
						kThread.flag_go=false;//�̱߳�־λ
						plane.blood=plane_blood;
						if(mapId+1<6)
						{
							mapId++;
							goTime=actionTimeSpan[mapId-3];
							oldTime=0;
							initMap();//���´��������ں�̹��
							initMap_Value();//��ʼ������ֵ
							kThread=new KeyThread(this);
							kThread.start();
							if(isMusicOn==0&&!activity.bgMusic[1].isPlaying())
							{
								activity.bgMusic[1].start();
							}
							isSpecActionState=0;//�ر��ж�״̬:��û�гɹ�Ҳû��ʧ��
						}
						else
						{
							Toast.makeText(activity, "û���µ��ж�,�����˵���ť,���ز˵�����!!!", Toast.LENGTH_LONG).show();
						}
 					}
					else if(x>280*ratio_width&&x<540*ratio_width&&y>270*ratio_height&&y<370*ratio_height)//�˵�
					{
						Toast.makeText(activity, "--------�˵�--------------------", 1000).show();
						kThread.flag_go=false;//�̱߳�־λ
						missile_ZOffset=missile_ZOffset_Ori;
						missile_rotation=0;
						isCrash=false;
						is_button_return=false;
						isGameOn=false;//��Ϸ�Ƿ��������Ϊ���ڽ�����
						plane.blood=plane_blood;
						kThread=new KeyThread(this);
						kThread.start();
						if(activity.bgMusic[1].isPlaying())
						{
							activity.bgMusic[1].pause();
						}
						if(0==isMusicOn)
						{
							activity.bgMusic[0].start();
						}
						isSpecActionState=0;//�ر��ж�״̬:��û�гɹ�Ҳû��ʧ��
					}
	            }
	            else if(isGameMode==1&&isSpecActionState==2)//�ر��ж�--------------ʧ��
	            {
	            	if(x>280*ratio_width&&x<540*ratio_width&&y>175*ratio_height&&y<270*ratio_height)//������ս
					{
						kThread.flag_go=false;//�̱߳�־λ
						plane.blood=plane_blood;
						goTime=actionTimeSpan[mapId-3];
						oldTime=0;
						initMap();//���´��������ں�̹��
						initMap_Value();//��ʼ������ֵ
						kThread=new KeyThread(this);
						kThread.start();
						if(isMusicOn==0&&!activity.bgMusic[1].isPlaying())
						{
							activity.bgMusic[1].start();
						}
						else
						{
							Toast.makeText(activity, "û���µ��ж�,�����˵���ť,���ز˵�����!!!", Toast.LENGTH_LONG).show();
						}
						isSpecActionState=0;//�ر��ж�״̬:��û�гɹ�Ҳû��ʧ��
					}
					else if(x>280*ratio_width&&x<540*ratio_width&&y>270*ratio_height&&y<370*ratio_height)//�˵�
					{
						kThread.flag_go=false;//�̱߳�־λ
						missile_ZOffset=missile_ZOffset_Ori;
						missile_rotation=0;
						isCrash=false;
						is_button_return=false;
						isGameOn=false;//��Ϸ�Ƿ��������Ϊ���ڽ�����
						plane.blood=plane_blood;
						kThread=new KeyThread(this);
						kThread.start();
						if(activity.bgMusic[1].isPlaying())
						{
							activity.bgMusic[1].pause();
						}
						if(0==isMusicOn)
						{
							activity.bgMusic[0].start();
						}
						isSpecActionState=0;//�ر��ж�״̬:��û�гɹ�Ҳû��ʧ��
					}
	            	return true;
	            }
	            else//--------------------Ȼ�����������
	            {
	            	//--------------------------ս��ģʽʧ����--------����------��Ƶǰ�ĶԻ���---------------------------------------------
					if((!isVideo&&isCrash&&isCrashCartoonOver)||(!isVideo&&is_button_return)||(isVideo&&!isVideoPlaying&&(!isVideoPlaying&&isTrueButtonAction)))
					{
						if(x>200*ratio_width&&x<600*ratio_width&&y>174*ratio_height&&y<200*ratio_height)//------������ť
						{
							if(!isVideo&&is_button_return)//�����������Ƶ���Ž���
							{
								is_button_return=!is_button_return;
								if(isMusicOn==0&&!activity.bgMusic[1].isPlaying())
								{
									activity.bgMusic[1].start();
								}
							}
							else if(isVideo&&!isVideoPlaying&&(!isVideoPlaying&&isTrueButtonAction))//�������Ƶ���Ž���
							{
								isVideoPlaying=true;
								if(isMusicOn==0&&!activity.bgMusic[1].isPlaying())
								{
									activity.bgMusic[1].start();
								}
							}
							else//--------ս��ģʽʧ����----------------------------
							{
								kThread.flag_go=false;//�̱߳�־λ
								plane.blood=plane_blood;
								initMap();//���´��������ں�̹��
								initMap_Value();//��ʼ������ֵ
								kThread=new KeyThread(this);
								kThread.start();
								if(isMusicOn==0&&!activity.bgMusic[1].isPlaying())
								{
									activity.bgMusic[1].start();
								}
							} 
							return true;
						}
						else if(x>200*ratio_width&&x<600*ratio_width&&y>250*ratio_height&&y<310*ratio_height)//���ز˵��˵�
						{
							kThread.flag_go=false;//�̱߳�־λ
							missile_ZOffset=missile_ZOffset_Ori;
							missile_rotation=0;
							isCrash=false;
							is_button_return=false;
							isVideoPlaying=true;
							isGameOn=false;//��Ϸ�Ƿ��������Ϊ���ڽ�����
							plane.blood=plane_blood;
							kThread=new KeyThread(this);
							kThread.start();
							if(activity.bgMusic[1].isPlaying())
							{
								activity.bgMusic[1].pause();
							}
							if(0==isMusicOn)
							{
								activity.bgMusic[0].start();
							}
							return true;
						}
						
					}
					//------------------------------Ӯ�ñ��ضԻ���----------------------------------------------
					if(isOvercome&&isCrashCartoonOver)
					{
						if(x>200*ratio_width&&x<600*ratio_width&&y>124*ratio_height&&y<180*ratio_height)//��һ�ذ�ť
						{
							kThread.flag_go=false;//�̱߳�־λ
							plane.blood=plane_blood;
							if(mapId+1<3)
							{
								mapId++;
								initMap();//���´��������ں�̹��
								initMap_Value();//��ʼ������ֵ
								kThread=new KeyThread(this);
								kThread.start();
								if(isMusicOn==0&&!activity.bgMusic[1].isPlaying())
								{
									activity.bgMusic[1].start();
								}
							}
							else
							{
								Toast.makeText(activity,"û���µ�ս��,�����˵���ť,���ز˵�����!!!", Toast.LENGTH_LONG).show(); 
							}
						}
						else if(x>200*ratio_width&&x<600*ratio_width&&y>200*ratio_height&&y<260*ratio_height)//���水ť
						{
							kThread.flag_go=false;//�̱߳�־λ
							plane.blood=plane_blood;
							initMap();//���´��������ں�̹��
							initMap_Value();//��ʼ������ֵ
							kThread=new KeyThread(this);
							kThread.start();			
							if(isMusicOn==0&&!activity.bgMusic[1].isPlaying())
							{
								activity.bgMusic[1].start();
							}
						}
						else if(x>200*ratio_width&&x<600*ratio_width&&y>300*ratio_height&&y<360*ratio_height)//���ز˵���ť
						{
							kThread.flag_go=false;//�̱߳�־λ
							missile_ZOffset=missile_ZOffset_Ori;
							missile_rotation=0;
							isCrash=false;
							is_button_return=false;
							isGameOn=false;//��Ϸ�Ƿ��������Ϊ���ڽ�����
							plane.blood=plane_blood;
							kThread=new KeyThread(this);
							kThread.start();
							if(activity.bgMusic[1].isPlaying())
							{
								activity.bgMusic[1].pause();
							}
							if(0==isMusicOn)  
							{
								activity.bgMusic[0].start();
							}
						}
						return true;
					}
	            }
				if(isCrash||isOvercome)
				{//����Ƿɻ����߾����ը���ˣ�����������
	        		break;
	        	}
				//-------------------------------------------------------------------------------
				if(isVideo&&!(!isVideoPlaying&&isTrueButtonAction))//�������Ƶ���Ž���
				{
					isFireOn=false;
	        		if(x<160*ratio_width&&y>404*ratio_height)//--------------������ͣ����---------------------
	        		{
	        			isTrueButtonAction=false;//���ⰴť����
	        			isVideoPlaying=!isVideoPlaying;//���Ż�����ͣ��ť������ͣʱ���ţ�����ʱ��ͣ
	        			if(isVideoPlaying&&isMusicOn==0&&!activity.bgMusic[1].isPlaying())
	        			{
	        				activity.bgMusic[1].start();
	        			}
	        			if(!isVideoPlaying&&activity.bgMusic[1].isPlaying())
	        			{
	        				activity.bgMusic[1].pause();
	        			}
	        			return true;	
	        		}
	        		if(!is_button_return&&x>680*ratio_width&&y>404*ratio_height)//----������Ϸ
	        		{
	        			
	        			plane.blood=plane_blood;
	        			isno_draw_plane=true;//��ʼ���Ʒɻ�
	        			PLANE_MOVE_SPAN=15;//�ɻ����ٶ�
	        			PLANE_X=-100;
	        			PLANE_Y=300;
	        			PLANE_Z=-100;
	        			rotationAngle_Plane_Y=225f;
	        			rotationAngle_Plane_X=0;
	        			rotationAngle_Plane_Z=0;
	        			DIRECTION_CAMERA=225;
	        			isVideo=false;//��������Ϊ��Ϸ����
	        			isVideoPlaying=true;//��Ƶ��������Ϊ����
	        			kThread.time=0;
	        			return true;	
	        		}
	        	}
	        	//����ť
	        	if(!isVideo&&x>BUTTON_FIRE_AREA[0]&&x<BUTTON_FIRE_AREA[1]&&y>BUTTON_FIRE_AREA[2]&&y<BUTTON_FIRE_AREA[3])
	        	{
	        		fireButton.isButtonDown=1;//����ť��־λ��Ϊtrue,���в�͸���ȵı仯
	        		shootId=id;
	        		isFireOn=true;
	        	}
	        	//ѡ��������ť
	        	if(!isVideo&&x>BUTTON_WEAPON_AREA[0]&&x<BUTTON_WEAPON_AREA[1]&&y>BUTTON_WEAPON_AREA[2]&&y<BUTTON_WEAPON_AREA[3])
	        	{
	        		WEAPON_INDEX=(WEAPON_INDEX+1)%tex_button_weaponId.length;//��������ͼƬ
	        	} 
	        	//���ϰ�ť
	        	if(!isVideo&&x>BUTTON_UP_AREA[0]&&x<BUTTON_UP_AREA[1]&&y>BUTTON_UP_AREA[2]&&y<BUTTON_UP_AREA[3])
	        	{
	        		upId=id;
	        		up_button.isButtonDown=1;//����ť��־λ��Ϊtrue,���в�͸���ȵı仯
	        		//��
					keyState=keyState|0x1;
					keyState=keyState&0xD;
	        	} 
	        	//���°�ť
	        	if(!isVideo&&x>BUTTON_DOWN_AREA[0]&&x<BUTTON_DOWN_AREA[1]&&y>BUTTON_DOWN_AREA[2]&&y<BUTTON_DOWN_AREA[3])
	        	{
	        		upId=id;
	        		down_button.isButtonDown=1;//����ť��־λ��Ϊtrue,���в�͸���ȵı仯
	        		//��
					keyState=keyState|0x2;
					keyState=keyState&0xE;
	        	} 
	        	break;
				
	        case MotionEvent.ACTION_MOVE:
	            break;
	        case MotionEvent.ACTION_POINTER_UP:	
	        	if(isVideo||isCrash||isOvercome)
	        	{
	        		break;
	        	}
	        	if(id==shootId)
	        	{
	        		isFireOn=false;
	        		shootId=2;
	        	}
	        	else if(id==upId)
	        	{
	        		fireButton.isButtonDown=0;//��ť���ٱ仯
		        	up_button.isButtonDown=0;//��ť���ٱ仯
		        	down_button.isButtonDown=0;//��ť���ٱ仯
		        	keyState=keyState&0xc;
		        	upId=2;
	        	}
	        	break;
	        case MotionEvent.ACTION_UP:
	        	if(isVideo||isCrash||isOvercome)
	        	{
	        		break;
	        	}
	        	if(e.getPointerCount()==1)
	        	{
	        		isFireOn=false;
	        		fireButton.isButtonDown=0;//��ť���ٱ仯
		        	up_button.isButtonDown=0;//��ť���ٱ仯
		        	down_button.isButtonDown=0;//��ť���ٱ仯
		        	keyState=keyState&0xc;
		        	upId=2;
		        	shootId=2;
	        	}
	        	else
	        	{
	        		if(shootId==0)
	        		{
	        			isFireOn=false;
	        			shootId=2;
	        		}
	        		else if(upId==0)
	        		{
	        			fireButton.isButtonDown=0;//��ť���ٱ仯
	    	        	up_button.isButtonDown=0;//��ť���ٱ仯
	    	        	down_button.isButtonDown=0;//��ť���ٱ仯
	    	        	keyState=keyState&0xc;
	    	        	upId=2;
	        		}
	        	}
	        	break;
	        }
		}
		else//��Ϸ��û�п�ʼ,��ʱ���ڲ˵�����
		{
			float x=e.getX();
			float y=e.getY();
			switch(e.getAction())
			{
			case MotionEvent.ACTION_DOWN:
				if(1==isMenuLevel)//һ���˵�
				{
					if(doorState==1)//��ǰΪ����״̬,����û�е����Ի���
					{
						if(hasInertia)//����ʱ,����й���,��ôֱ��ֹͣ
						{
							hasInertia=false;
							curr_angle_speed=0;
						}
						//����Թ��ֲ˵��µİ����¼����м���
						if(x>MENU_BUTTON_AREA[0]&&x<MENU_BUTTON_AREA[1]&&y>MENU_BUTTON_AREA[2]&&y<MENU_BUTTON_AREA[3])
			        	{
							isPoint=true;//�������Ӧ�Ĳ˵���ť
			        	}
					}
				}
				else if(2==isMenuLevel)//---��������˵�
				{
					//--------���������ȷ����ť------
					if(x>MENU_TWO_BUTTON_OK_AREA[0]&&x<MENU_TWO_BUTTON_OK_AREA[1]&&y>MENU_TWO_BUTTON_OK_AREA[2]&&y<MENU_TWO_BUTTON_OK_AREA[3])
		        	{
						isOKButtonDown=true;
						//������Ҫ��ͼ
						tex_menu_two_okIndex=1;
		        	}
					//--------����������󰴰�ť------
					if(x>MENU_TWO_BUTTON_LEFT_AREA[0]&&x<MENU_TWO_BUTTON_LEFT_AREA[1]&&y>MENU_TWO_BUTTON_LEFT_AREA[2]&&y<MENU_TWO_BUTTON_LEFT_AREA[3])
		        	{
						isLeftButtonDown=true;
						//������Ҫ��ͼ
						tex_menu_two_leftIndex=1;
						
		        	}
					//--------����������Ұ���ť------
					if(x>MENU_TWO_BUTTON_RIGHT_AREA[0]&&x<MENU_TWO_BUTTON_RIGHT_AREA[1]&&y>MENU_TWO_BUTTON_RIGHT_AREA[2]&&y<MENU_TWO_BUTTON_RIGHT_AREA[3])
		        	{
						isRightButtonDown=true;
						//������Ҫ��ͼ
						tex_menu_two_rightIndex=1;
						
		        	}
					//--------���������ս��ģʽ��ť------
					if(x>MENU_TWO_WAR_BUTTON_AREA[0]&&x<MENU_TWO_WAR_BUTTON_AREA[1]&&y>MENU_TWO_WAR_BUTTON_AREA[2]&&y<MENU_TWO_WAR_BUTTON_AREA[3])
		        	{
						isChangeAlpha=false;
						isWarButtonDown=true;
						//������Ҫ��ͼ
						tex_menu_two_war_btnIndex=1;
		        	}
					//--------����������ر��ж���ť------
					if(x>MENU_TWO_ACTION_BUTTON_AREA[0]&&x<MENU_TWO_ACTION_BUTTON_AREA[1]&&y>MENU_TWO_ACTION_BUTTON_AREA[2]&&y<MENU_TWO_ACTION_BUTTON_AREA[3])
		        	{
						isChangeAlpha=false;
						isActionButtonDown=true;
						//������Ҫ��ͼ
						tex_menu_two_action_btnIndex=1;
		        	}
					//-----------��һ���ɻ�
					if(x>MENU_TWO_PLANE_ICON_ONE_AREA[0]&&x<MENU_TWO_PLANE_ICON_ONE_AREA[1]&&y>MENU_TWO_PLANE_ICON_ONE_AREA[2]&&y<MENU_TWO_PLANE_ICON_ONE_AREA[3])
		        	{
						//������Ҫ��ͼ
						tex_menu_two_plane_iconIndex[0]=1;
						tex_menu_two_plane_iconIndex[1]=0;
						tex_menu_two_plane_iconIndex[2]=0;
						planeModelIndex=0;
		        	}
					//-----------�ڶ����ɻ�
					if(x>MENU_TWO_PLANE_ICON_TWO_AREA[0]&&x<MENU_TWO_PLANE_ICON_TWO_AREA[1]&&y>MENU_TWO_PLANE_ICON_TWO_AREA[2]&&y<MENU_TWO_PLANE_ICON_TWO_AREA[3])
		        	{
						//������Ҫ��ͼ
						tex_menu_two_plane_iconIndex[0]=0;
						tex_menu_two_plane_iconIndex[1]=1;
						tex_menu_two_plane_iconIndex[2]=0;
						planeModelIndex=1;
		        	}
					//-----------�������ɻ�
					if(x>MENU_TWO_PLANE_ICON_THREE_AREA[0]&&x<MENU_TWO_PLANE_ICON_THREE_AREA[1]&&y>MENU_TWO_PLANE_ICON_THREE_AREA[2]&&y<MENU_TWO_PLANE_ICON_THREE_AREA[3])
		        	{
						//������Ҫ��ͼ
						tex_menu_two_plane_iconIndex[0]=0;
						tex_menu_two_plane_iconIndex[1]=0;
						tex_menu_two_plane_iconIndex[2]=1;
						planeModelIndex=2;
		        	}
				}
				//-------------------------------�����˵�-----------------------------------------
				else if(3==isMenuLevel)
				{
					if(x>MAP_ONE_AREA[0]&&x<MAP_ONE_AREA[1]&&y>MAP_ONE_AREA[2]&&y<MAP_ONE_AREA[3])
					{
						if(0==isGameMode)
						{
							mapId=0;
							initMap();
							initMap_Value();
							isVideo=true;
							if(activity.bgMusic[0].isPlaying())
							{
								activity.bgMusic[0].pause();
							}
							if(isMusicOn==0)
							{
								activity.bgMusic[1].start();
							}
							isGameOn=true;
						}
						else//�����ж�  --- ��ɻ�
						{
							goTime=actionTimeSpan[0];
							oldTime=0;
							mapId=3;
							initMap();
							initMap_Value();
							isVideo=true;
							if(activity.bgMusic[0].isPlaying())
							{
								activity.bgMusic[0].pause();
							}
							if(isMusicOn==0)
							{
								activity.bgMusic[1].start();
							}
							isGameOn=true;
						}
					}
					else if(x>MAP_TWO_AREA[0]&&x<MAP_TWO_AREA[1]&&y>MAP_TWO_AREA[2]&&y<MAP_TWO_AREA[3])
					{
						if(0==isGameMode)
						{
							mapId=1;
							initMap();
							initMap_Value();
							isVideo=true;
							if(activity.bgMusic[0].isPlaying())
							{
								activity.bgMusic[0].pause();
							}
							if(isMusicOn==0)
							{
								activity.bgMusic[1].start();
							}
							isGameOn=true;
						}
						else//ɳĮ�籩  -̹��,������
						{
							goTime=actionTimeSpan[1];
							oldTime=0;
							mapId=4;
							initMap();
							initMap_Value();
							isVideo=true;
							if(activity.bgMusic[0].isPlaying())
							{
								activity.bgMusic[0].pause();
							}
							if(isMusicOn==0)
							{
								activity.bgMusic[1].start();
							}
							isGameOn=true;
						}
					}
					else if(x>MAP_THREE_AREA[0]&&x<MAP_THREE_AREA[1]&&y>MAP_THREE_AREA[2]&&y<MAP_THREE_AREA[3])
					{
						if(0==isGameMode)
						{
							mapId=2;
							initMap();
							initMap_Value();
							isVideo=true;
							if(activity.bgMusic[0].isPlaying())
							{
								activity.bgMusic[0].pause();
							}
							if(isMusicOn==0)
							{
								activity.bgMusic[1].start();
							}
							isGameOn=true;
						}
						else//ն���ж�  �����
						{
							goTime=actionTimeSpan[2];
							oldTime=0;
							mapId=5;
							initMap();
							initMap_Value();
							isVideo=true;
							if(activity.bgMusic[0].isPlaying())
							{
								activity.bgMusic[0].pause();
							}
							if(isMusicOn==0)
							{
								activity.bgMusic[1].start();
							}
							isGameOn=true;
						}
					}
				}
				
				break;
			case MotionEvent.ACTION_MOVE:
				float dy=y-mPreviousY;
				if(1==isMenuLevel)//һ���˵�
				{
					if(doorState==1)//��ǰΪ����״̬,����û�е����Ի���
					{
						//������з�Χ����
						if(missile_rotation+dy*TOUCH_SCALE_FACTOR>20)
						{
							missile_rotation=20;
						}
						else if(missile_rotation+dy*TOUCH_SCALE_FACTOR<-245)
						{
							missile_rotation=-245;
						}
						else
						{
							missile_rotation+=dy*TOUCH_SCALE_FACTOR;//��ǰ�����˵���ת�ؽǶ�
						}
						if(Math.abs(dy)>8)//�趨һ����ֵ,������������ֵ,�ɿ���ָ��,��ӹ���
						{
							isTouchMoved=true;//��ǰ���ڴ��� �ƶ���
							curr_angle_speed=ori_angle_speed*(dy/SCREEN_WIDTH);
						}
						if(isPoint&&Math.abs(dy)>10)//�������¼�Ϊtrue,�������ƶ���,���Ե���¼���Ϊfalse
						{
							isPoint=false;
						}
					}
					if(doorState==2&&curr_menu_index==2)//���а������ƶ�
					{
						if(rank_move-dy*TOUCH_SCALE_FACTOR*0.002f>0)//ȷ���ƶ��ķ�Χ
						{
							rank_move-=dy*TOUCH_SCALE_FACTOR*0.002f;
						}
						else
						{
							rank_move=0;
						}
					}
					if(doorState==2&&curr_menu_index==3)//����������ƶ�
					{
						help_YOffset-=dy*TOUCH_SCALE_FACTOR*0.01f;
						
					}
					if(doorState==2&&curr_menu_index==4)//���ڽ�����ƶ�
					{
						about_YOffset-=dy*TOUCH_SCALE_FACTOR*0.01f;
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				//�����˵�����
				if(1==isMenuLevel)
				{
					if(doorState==1)//��ǰΪ����״̬,����û�е����Ի���
					{
						if(isTouchMoved)//�����ǰ������
						{
							isTouchMoved=false;
							hasInertia=true;//���й���
							  //����ǰ���ٶȴ���������ٶ���Ϊ��
							curr_acceleratedSpeed=ori_acceleratedSpeed;
				            if(curr_angle_speed>0)
				            {
				            	curr_acceleratedSpeed=-ori_acceleratedSpeed;
				            }
						}
						else//���������Ƕ����ܵ���
						{
							auto_adjust=true;
						}
						//---------------��ť
						//����Թ��ֲ˵��µİ����¼����м���
						if(x>MENU_BUTTON_AREA[0]&&x<MENU_BUTTON_AREA[1]&&y>MENU_BUTTON_AREA[2]&&y<MENU_BUTTON_AREA[3])
			        	{
							if(isPoint)//�������¼�Ϊtrue
							{
								isPoint=false;
								switch(curr_menu_index)
								{
								case 0://��ʼ��Ϸ
									menu_button_move=true;//�����˵���ť���ƿ�ʼ
									break;
								case 1://���ð�ť
									doorState=0;//���йز�
									break;
								case 2://���а�
									doorState=0;//���йز�
									rank_move=0;//�����ƶ���Χ����Ϊ0
									//��ʼ�����ݿ�,��ȡ����
									String sql="select * from plane ;";
									rank=SQLiteUtil.query(sql);
									Collections.reverse(rank);//��ʱ�䵹����
									break;
								case 3://����
									doorState=0;//���йز�
									help_YOffset=-HELP_HEIGHT/2.5f;//���������Y��ƫ����
									break;
								case 4://����
									doorState=0;//���йز�
									about_YOffset=-ABOUT_HEIGHT/2.5f;//���ڽ����Y��ƫ����
									break;
								case 5://�˳�
									activity.exitRelease();
									break;
								}
							}
			        	}
					}
					if(doorState==2)//�����ǰΪ�ز�״̬
					{
						if(curr_menu_index==1)//�����ǰΪ���ý���
						{
							//�Ƿ����ÿ�����������
							if(x>SETTING_BUTTON_AREA1[0]&&x<SETTING_BUTTON_AREA1[1]&&y>SETTING_BUTTON_AREA1[2]&&y<SETTING_BUTTON_AREA1[3])
							{
								isMusicOn=(isMusicOn+1)%2;
								if(isMusicOn==1&&activity.bgMusic[0].isPlaying())//����˵�������������ڲ���
								{
									activity.bgMusic[0].pause();
								}
								if(isMusicOn==0&&!activity.bgMusic[0].isPlaying())
								{
									activity.bgMusic[0].start();
								}
							}
							//�Ƿ����ÿ�����Ч����
							if(x>SETTING_BUTTON_AREA2[0]&&x<SETTING_BUTTON_AREA2[1]&&y>SETTING_BUTTON_AREA2[2]&&y<SETTING_BUTTON_AREA2[3])
							{
								isSoundOn=(isSoundOn+1)%2;
							}
							//�Ƿ����ÿ�����Ч��
							if(x>SETTING_BUTTON_AREA3[0]&&x<SETTING_BUTTON_AREA3[1]&&y>SETTING_BUTTON_AREA3[2]&&y<SETTING_BUTTON_AREA3[3])
							{
								isVibrateOn=(isVibrateOn+1)%2;
							} 
						}
					}
				}
				//-------------------ѡ�ɻ������������-------------------
				else if(2==isMenuLevel)
				{
					//--------���������ȷ����ť------
					if(isOKButtonDown)
					{
						if(x>MENU_TWO_BUTTON_OK_AREA[0]&&x<MENU_TWO_BUTTON_OK_AREA[1]&&y>MENU_TWO_BUTTON_OK_AREA[2]&&y<MENU_TWO_BUTTON_OK_AREA[3])
			        	{
							isMenuLevel=3;
			        	}
						tex_menu_two_okIndex=0;
						isOKButtonDown=false;
					}
					//--------����������󰴰�ť------
					if(isLeftButtonDown)
					{
						if(x>MENU_TWO_BUTTON_LEFT_AREA[0]&&x<MENU_TWO_BUTTON_LEFT_AREA[1]&&y>MENU_TWO_BUTTON_LEFT_AREA[2]&&y<MENU_TWO_BUTTON_LEFT_AREA[3])
			        	{
							planeModelIndex--;
							if(planeModelIndex<0)
							{
								planeModelIndex=2;
							}
							
							tex_menu_two_plane_iconIndex[0]=0;
							tex_menu_two_plane_iconIndex[1]=0;
							tex_menu_two_plane_iconIndex[2]=0;
							tex_menu_two_plane_iconIndex[planeModelIndex]=1;
			        	}
						//������Ҫ��ͼ
						tex_menu_two_leftIndex=0;
						isLeftButtonDown=false;
					}
					//--------����������Ұ���ť------
					if(isRightButtonDown)
					{
						if(x>MENU_TWO_BUTTON_RIGHT_AREA[0]&&x<MENU_TWO_BUTTON_RIGHT_AREA[1]&&y>MENU_TWO_BUTTON_RIGHT_AREA[2]&&y<MENU_TWO_BUTTON_RIGHT_AREA[3])
			        	{
							planeModelIndex++;
							if(planeModelIndex>2)
							{
								planeModelIndex=0;
							}
							
							tex_menu_two_plane_iconIndex[0]=0;
							tex_menu_two_plane_iconIndex[1]=0;
							tex_menu_two_plane_iconIndex[2]=0;
							tex_menu_two_plane_iconIndex[planeModelIndex]=1;	
			        	}
						//������Ҫ��ͼ
						tex_menu_two_rightIndex=0;
						isRightButtonDown=false;
					}
					//--------���������ս��ģʽ��ť------
					if(isWarButtonDown)
					{
						isChangeAlpha=true;
						if(x>MENU_TWO_WAR_BUTTON_AREA[0]&&x<MENU_TWO_WAR_BUTTON_AREA[1]&&y>MENU_TWO_WAR_BUTTON_AREA[2]&&y<MENU_TWO_WAR_BUTTON_AREA[3])
			        	{
							isGameMode=0;
							//������Ҫ��ͼ
							tex_menu_two_action_btnIndex=0;
			        	}
						else
						{
							if(isGameMode!=0)
							{
								//������Ҫ��ͼ
								tex_menu_two_war_btnIndex=0;
							}
						}
						isWarButtonDown=false;
					}
					//--------����������ر��ж���ť------
					if(isActionButtonDown)
					{
						isChangeAlpha=true;
						if(x>MENU_TWO_ACTION_BUTTON_AREA[0]&&x<MENU_TWO_ACTION_BUTTON_AREA[1]&&y>MENU_TWO_ACTION_BUTTON_AREA[2]&&y<MENU_TWO_ACTION_BUTTON_AREA[3])
			        	{
							isGameMode=1;
							//������Ҫ��ͼ
							tex_menu_two_war_btnIndex=0;
			        	}
						else
						{
							if(isGameMode!=1)
							{
								//������Ҫ��ͼ
								tex_menu_two_action_btnIndex=0;
							}
						}
						isActionButtonDown=false;
					}
				}
				break;
			}
			mPreviousY=y;
		}
        return true;
    }
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
		//�Ƿ��ǵ�һ֡
		private boolean isFirstFrame=true;
		int plane_hit_id=0;
		
        public void onDrawFrame(GL10 gl) 
        {   
        	//�����Ȼ�������ɫ����
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            if(!isLoadedOk)//���û�м������
            {
            	 drawOrthLoadingView();
            }
            else//����������
            {
            	if(isGameOn)//������Ϸ����
            	{
            		drawPerspective();//������Ϸ����
                	drawVirtualIcon();//�������ⰴť
                	drawVideoDirection();//������Ƶ���Ž���
                	drawGameDialog();//���Ʒɻ�׹�ٺ�Ĳ˵�����
            	}
            	else//����˵�����
            	{
            		drawGameMenu();//���Ƶ����˵�����
            	}
            }
        }
        public void onSurfaceChanged(GL10 gl, int width, int height)
        {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            ratio = (float) width / height;
            ConfigVirtualButtonArea();//�԰�ť�ķ�Χ������Ӧ������
        }
        public void onSurfaceCreated(GL10 gl, EGLConfig config) 
        {	
            //������Ļ����ɫRGBA
            GLES20.glClearColor(0.0f,0.0f,0.0f, 1.0f);
            //�򿪱������   
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            //����ȼ��
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //��ʼ���Զ���ջ
            MatrixState.setInitStack();
            ShaderManager.loadFirstViewCodeFromFile(GLGameView.this.getResources());//����shader
            ShaderManager.compileFirstViewShader();//����shader
            //�������ؽ�����������
            loadingView=new TextureRect(2, 2, ShaderManager.getFirstViewShaderProgram());
            tex_loadingviewId=initTexture(GLGameView.this.getResources(),R.drawable.loading,false);
            processBar=new TextureRect(2, 0.1f, ShaderManager.getFirstViewShaderProgram());
            tex_processId=initTexture(GLGameView.this.getResources(),R.drawable.process,false);
        }
        //����ͶӰ���Ƽ��ؽ���
        public void drawOrthLoadingView(){
            if(isFirstFrame){ //����ǵ�һ֡
            	MatrixState.pushMatrix();
	            MatrixState.setProjectOrtho(-1, 1, -1, 1, 1, 10);//��������ͶӰ
	            MatrixState.setCamera(0, 0, 1, 0, 0,-1, 0, 1, 0);//���������
	            MatrixState.copyMVMatrix();
	            loadingView.drawSelf(tex_loadingviewId);
	            MatrixState.popMatrix();
            	isFirstFrame=false;
            } else {//���������Դ�ļ���
            	MatrixState.pushMatrix();
	            MatrixState.setProjectOrtho(-1, 1, -1, 1, 1, 10);//��������ͶӰ
	            MatrixState.setCamera(0, 0, 1, 0, 0,-1, 0, 1, 0);//���������
	            MatrixState.copyMVMatrix();
	            MatrixState.pushMatrix();  //���ý�����
	            MatrixState.translate(-2+2*load_step/(float)40, -1+0.05f, 0f);
	            processBar.drawSelf(tex_processId);
	            MatrixState.popMatrix();
	            loadingView.drawSelf(tex_loadingviewId);//���Ʊ���ͼ
	            MatrixState.popMatrix();
	            loadResource();//������Դ�ķ���
	            return;    
            }}
        //͸��ͶӰ���� ��Ϸ����
        public void drawPerspective()
        {
        	MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 3, 40000);//����͸��ͶӰ
        	MatrixState.pushMatrix();
        	//���������ر����ĸ���,���ڲ�ͬ�߳�֮��Ĳ�ͬ��,�ᷢ����������
        	synchronized(lock)
			{
        		curr_cx=cx;
	        	curr_cy=cy+2;
	        	curr_cz=cz;
	        	curr_tx=tx;
	        	curr_ty=ty;
	        	curr_tz=tz;
	        	curr_upx=upX;
	        	curr_upy=upY;
	        	curr_upz=upZ;
	        	
	        	curr_PlaneX=PLANE_X;
	        	curr_PlaneZ=PLANE_Z;  
	        	curr_PlaneY=PLANE_Y;
	        	curr_rot_Plane_X=rotationAngle_Plane_X;
	        	curr_rot_Plane_Y=rotationAngle_Plane_Y;
	        	curr_rot_Plane_Z=rotationAngle_Plane_Z;
	        	
	        	Matrix.setRotateM(fa, 0, curr_rot_Plane_Y, 0, 1, 0);//�õ���ת����
	        	YB[0]=(float) (Math.sin(Math.toRadians(-curr_rot_Plane_Z)));
	        	YB[1]=(float) (Math.cos(Math.toRadians(-curr_rot_Plane_Z))* Math.cos(Math.toRadians(rotationAngle_Plane_X)));
	        	YB[2]=(float) (Math.cos(Math.toRadians(-curr_rot_Plane_Z))* Math.sin(Math.toRadians(rotationAngle_Plane_X)));
	        	YB[3]=1;
	        	Matrix.multiplyMV(resultUp, 0, fa, 0, YB, 0);
	        	
	        	MatrixState.setCamera(curr_cx,curr_cy,curr_cz,curr_tx,curr_ty,curr_tz,
	        			resultUp[0],resultUp[1],resultUp[2]);//�����������λ��
	        	MatrixState.copyMVMatrix();
	        	MatrixState.translate(-20*(float)Math.sin(Math.toRadians(curr_rot_Plane_Z/4)),
	        			-20*(float)Math.cos(Math.toRadians(curr_rot_Plane_Z/4)),0);
	        	copybaozhaList.clear();//���ӱ�ըЧ���б�
	        	for(DrawBomb db:baoZhaList)
	        	{
	        		copybaozhaList.add(db);
	        	}
	        	cop_archie_bomb_List.clear();//���Ƹ������ӵ�
	        	for(BombForControl db:archie_bomb_List)
	        	{
	        		cop_archie_bomb_List.add(db);
	        	}
	        	cop_bomb_List.clear();//�����ڵ��б�
	        	for(BombForControl db:bomb_List)
	        	{
	        		cop_bomb_List.add(db);
	        	}
	        	cop_archie_List.clear();
	        	for(ArchieForControl db:archie_List)//�������ڵ�����
	        	{
	        		cop_archie_List.add(db);
	        	}
	        	cop_bullet_List.clear();//�ӵ�����
	        	for(BulletForControl db:bullet_List)
	        	{
	        		cop_bullet_List.add(db);
	        	}
	        	copy_tank_bomb_List.clear();//�ڵ�����
	        	for(BombForControl db:tank_bomb_List)
	        	{
	        		copy_tank_bomb_List.add(db);
	        	}
			}
        	lightAngle+=2;
          //���㵱ǰ�Ź���,��������Ӧ�ķ���������Ӧ������
           drawAll();
          MatrixState.popMatrix();
        }
        //���㵱ǰ�Ź���,��������Ӧ�ķ���������Ӧ������
        public void drawAll()
        {
        	int rowi=0;//��ʼ����
    		int colj=0;
    		int colT=CELL_SIZE;//��ֹ����
    		int rowT=CELL_SIZE;
    		int col=(int)(curr_PlaneX/WIDTH_LALNDFORM);//-CELL_SIZE/2;//�õ���ǰ���ڵ�����
    		int row=(int)(curr_PlaneZ/HEIGHT_LANDFORM);//-CELL_SIZE/2;
    		int rcCount=2;//��ǰ��ʱ����Ҫ���Ƶĵؿ���
    		//�ü�
    		curr_rot_Plane_Y%=360;
    		if(curr_rot_Plane_Y<0)
    		{
    			curr_rot_Plane_Y=360+curr_rot_Plane_Y;
    		}
    		if((curr_rot_Plane_Y>=0&&curr_rot_Plane_Y<=45)||(curr_rot_Plane_Y>=315&&curr_rot_Plane_Y<=360))
    		{//���ԭZ�Ḻ�������
    			if(curr_PlaneZ<0)//�ڱ�������ʱ��ȫ����������
    			{
    				rowi=0;
    				rowT=1;
    				colj=0;//���еؿ鶼�û���
    				colT=MapArray[mapId].length;
    			}
    			else if(curr_PlaneZ>MapArray[mapId].length*WIDTH_LALNDFORM)//������ڽ��뵺�Ĺ�����
    			{
    				rowi=MapArray[mapId].length-CELL_SIZE/2;//���еؿ鶼�û���
    				rowT=MapArray[mapId].length;
    				colj=0;//���еؿ鶼�û���
    				colT=MapArray[mapId].length;
    				
    			}
    			else//�����ڵ��м䣬��ֻ���Ʒɻ�ǰ��Ĳ���
    			{
    				rowi=row-CELL_SIZE/2;
    				if(rowi<0)
    				{
    					rowi=0;
    				}
    				//---------------------------
    				rowT=row+rcCount;
    				if(rowT>MapArray[mapId].length)
    				{
    				 rowT=MapArray[mapId].length;
    				}else if(rowT<0){
    					rowT=0;
    				}
    				colj=col-CELL_SIZE/2;//���еؿ鶼�û���
    				colT=col+CELL_SIZE/2;
    				if(colj<0)
    				{
    					colj=0;
    				}
    				else if(colj>MapArray[mapId].length)
    				{
    					colj=MapArray[mapId].length;
    				}
    				if(colT<0)
    				{
    					colT=0;
    				}
    				else if(colT>MapArray[mapId].length)
    				{
    					colT=MapArray[mapId].length;
    				}
    			}
    		}
    		else if(curr_rot_Plane_Y>=45&&curr_rot_Plane_Y<135)
    		{
    			
    			if(curr_PlaneX<0)//�ڱ�������ʱ��ȫ����������
    			{
    				colj=0;
    				colT=1;
    				rowi=0;//���еؿ鶼�û���
    				rowT=MapArray[mapId].length;
    			}
    			else if(curr_PlaneX>=MapArray[mapId].length*WIDTH_LALNDFORM)
    			{//������ڽ��뵺�Ĺ�����
    				colj=MapArray[mapId].length-CELL_SIZE/2;//���еؿ鶼�û���
    				colT=MapArray[mapId].length;
    				rowi=0;//���еؿ鶼�û���
    				rowT=MapArray[mapId].length;
    			}
    			else
    			{//�����ڵ��м䣬��ֻ���Ʒɻ�ǰ��Ĳ���
    				colj=col-CELL_SIZE/2;
    				if(colj<0)
    				{
    					colj=0;
    				}
    				colT=col+rcCount;
    				if(colT>MapArray[mapId].length)
    				{
    				 rowT=MapArray[mapId].length;
    				}else if(colT<0){
    					colT=0;
    				}
    				rowi=row-CELL_SIZE/2;//���еؿ鶼�û���
    				rowT=row+CELL_SIZE/2;
    				if(rowi<0)
    				{
    					rowi=0;
    				}
    				else if(rowi>MapArray[mapId].length)
    				{
    					rowi=MapArray[mapId].length;
    				}
    				if(rowT<0)
    				{
    					rowT=0;
    				}
    				else if(rowT>MapArray[mapId].length)
    				{
    					rowT=MapArray[mapId].length;
    				}
    			}
    		}
    		else if(curr_rot_Plane_Y>=135&&curr_rot_Plane_Y<225)
    		{
    			if(curr_PlaneZ<0)
    			{//���뵺����ʱ��ȫ��������
    				rowi=0;//���еؿ鶼�û���
    				rowT=CELL_SIZE/2;
    				colj=0;//���еؿ鶼�û���
    				colT=MapArray[mapId].length;
    			}
    			else if(curr_PlaneZ>MapArray[mapId].length*WIDTH_LALNDFORM)
    			{//������ڱ������ɵĹ�����
    				rowi=MapArray[mapId].length-1;//�����Ƶ�
    				rowT=MapArray[mapId].length;
    				colj=0;//���еؿ鶼�û���
    				colT=MapArray[mapId].length;
    			}
    			else
    			{//�����ڵ��м䣬��ֻ���Ʒɻ�ǰ��Ĳ���
    				rowi=row-rcCount;
    				if(rowi<0)
    				{
    					rowi=0;
    				}
    				rowT=row+CELL_SIZE/2;
    				if(rowT>MapArray[mapId].length)
    				{
    				 rowT=MapArray[mapId].length;
    				}else if(rowT<0){
    					rowT=0;
    				}
    				colj=col-CELL_SIZE/2;//���еؿ鶼�û���
    				colT=col+CELL_SIZE/2;
    				if(colj<0)
    				{
    					colj=0;
    				}
    				else if(colj>MapArray[mapId].length)
    				{
    					colj=MapArray[mapId].length;
    				}
    				if(colT<0)
    				{
    					colT=0;
    				}
    				else if(colT>MapArray[mapId].length)
    				{
    					colT=MapArray[mapId].length;
    				}
    			}
    		}
    		else
    		{
    			if(curr_PlaneX<0)
    			{//�ڽ�������ʱ��ȫ��������
    				colj=0;//���еؿ鶼�û���
    				colT=CELL_SIZE/2;//MapArray[mapId].length;
    				rowi=0;//���еؿ鶼�û���
    				rowT=MapArray[mapId].length;
    			}
    			else if(curr_PlaneX>MapArray[mapId].length*WIDTH_LALNDFORM)
    			{//������ڱ������ɵĹ�����
    				colj=0;
    				colT=1;
    				rowi=0;//���еؿ鶼�û���
    				rowT=MapArray[mapId].length;
    			}
    			else
    			{//�����ڵ��м䣬��ֻ���Ʒɻ�ǰ��Ĳ���
    				colj=col-rcCount;
    				if(colj<0)
    				{
    					colj=0;
    				}
    				colT=col+CELL_SIZE/2;
    				if(colT>MapArray[mapId].length)
    				{
    				colT=MapArray[mapId].length;
    				}else if(colT<0){
    					colT=0;
    				}
    				rowi=row-CELL_SIZE/2;//���еؿ鶼�û���
    				rowT=row+CELL_SIZE/2;
    				if(rowi<0)
    				{
    					rowi=0;
    				}
    				else if(rowi>MapArray[mapId].length)
    				{
    					rowi=MapArray[mapId].length;
    				}
    				if(rowT<0)
    				{
    					rowT=0;
    				}
    				else if(rowT>MapArray[mapId].length)
    				{
    					rowT=MapArray[mapId].length;
    				}
    			}
    		}
 			drawSky();//�������    
         	//���ƴ��
            drawdam();
    		//����ɽ
            drawLandForm(rowi,colj,rowT,colT);
            //����ˮ
            drawWater();
            //����̹��
            drawTanke(rowi,colj,rowT,colT);
            //���Ƹ�����
            drawarchie(rowi,colj,rowT,colT);
            //���ƾ����
            drawHouse(rowi,colj,rowT,colT);
            //���Ʒ���
    	    drawHousePlane(rowi,colj,rowT,colT);
         	//�����ڵ�
         	drawBombs();
         	//���Ƹ������ڵ�
         	drawArchieBombs();
         	//����̹�˷�����ڵ�
         	drawTankBombs();
         	//���Ƶл�
         	drawEnemyPlane();
         	//������Ҳٿطɻ�
         	drawPlane(curr_PlaneX,curr_PlaneY,curr_PlaneZ,curr_rot_Plane_X,curr_rot_Plane_Y,curr_rot_Plane_Z);
         	//���Ƶ���
            drawLightTower();
         	//������
         	drawTree(rowi,colj,rowT,colT);
            //���Ʊ�ըЧ��
            drawBomb();
    		//�����ӵ�
         	drawBullets();
    		//����ⱬըЧ��
            drawBaoZhaXiaoguo();
        }
        
        
        
        //�������
        public void drawSky()
        {
        	if(mapId==0||mapId==5)
        	{
        		skyBallsmall.drawSelf(tex_nightId,curr_PlaneX,0,curr_PlaneZ, rotationAngle_SkyBall);
        		MatrixState.pushMatrix();
        		MatrixState.translate(curr_PlaneX,0,curr_PlaneZ);
        		MatrixState.rotate(rotationAngle_SkyBall, 0, 1, 0);
        		skynight.drawSelf();
    			skynightBig.drawSelf();
    			MatrixState.popMatrix();
        	}
        	else
        	{
        		skyBallsmall.drawSelf(tex_skyBallId,curr_PlaneX,0,curr_PlaneZ, rotationAngle_SkyBall);
        	}
        }
        //���Ƹ������ڵ�
        public void drawArchieBombs()
        {
        	try
        	{
        		for(int i=0;i<cop_archie_bomb_List.size();i++)
            	{
        			cop_archie_bomb_List.get(i).drawSelf(tex_bulletId);
            	}
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        //�����ӵ��ķ���
        public void drawBullets()
        {
        	//�������
        	GLES20.glEnable(GLES20.GL_BLEND);
        	GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        	try
        	{
        		for(int i=0;i<cop_bullet_List.size();i++)
            	{
        			cop_bullet_List.get(i).drawSelf(tex_bulletId);
            	}
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        	GLES20.glDisable(GLES20.GL_BLEND);
        }
        //�����ڵ��ķ���
        public void drawBombs()
        {
        	try
        	{
        		for(int i=0;i<cop_bomb_List.size();i++)
            	{
        			cop_bomb_List.get(i).drawSelf(tex_bulletId);
            	}
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        //����̹���ڵ�
        public void drawTankBombs()
        {
        	try
        	{
        		for(int i=0;i<copy_tank_bomb_List.size();i++)
            	{
        			copy_tank_bomb_List.get(i).drawSelf(tex_bulletId);
            	}
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        //����ˮ��ķ���
        public void drawWater()
        {
        		MatrixState.pushMatrix(); 
        		MatrixState.translate(SKY_BALL_RADIUS/2,0,SKY_BALL_RADIUS/2);
				MatrixState.rotate(-90, 1, 0, 0);
				GLES20.glEnable(GLES20.GL_BLEND);
				GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
				water.drawSelf(tex_waterId);
				GLES20.glDisable(GLES20.GL_BLEND);
				MatrixState.popMatrix();
        }
        //����ɽ��
        public void drawLandForm(int rowi,int colj,int rowT,int colT)
        {
    		for(int i=rowi;i<rowT;i++)
        	{
        		for(int j=colj;j<colT;j++)
        		{
                	MatrixState.pushMatrix();   			
                	MatrixState.translate((0+j)*WIDTH_LALNDFORM, LAND_HIGH_ADJUST, (0+i)*HEIGHT_LANDFORM);
                	try
                	{
                		draw_number_LandForm(MapArray[mapId][i][j]); //���ݱ�Ż���ɽ      
                	} 
                	catch(Exception e)
                	{
                		e.printStackTrace();
                	}      	
                	MatrixState.popMatrix();
        		}
        	}	
        }
        //���ݱ�Ż��ƶ�Ӧ�Ŀ�
        public void draw_number_LandForm(int number)
        {
        	switch (number) 
        	{
			case 0:
			case 1:
			case 2:terrain[number].drawSelf(0,tex_terrain_shandingId,tex_terrain_tuceng_Id, tex_terrain_caodiId,tex_terrain_shitouId, LOTHight,height_span_LOT);
			break;
			case 3:
				terrain[number].drawSelf(1,tex_terrain_shandingId,tex_terrain_tuceng_Id,tex_terrain_caodiId, tex_terrain_shitouId, HillHight,height_span_Hill);
				break;
			case 4:
				MatrixState.translate(0, 0, WIDTH_LALNDFORM);
				MatrixState.rotate(90, 0, 1, 0);
				terrain[0].drawSelf(0,tex_terrain_shandingId,tex_terrain_tuceng_Id, tex_terrain_caodiId,tex_terrain_shitouId, LOTHight,height_span_LOT);
				break;
			case 5:
				MatrixState.translate(WIDTH_LALNDFORM, 0, WIDTH_LALNDFORM);
				MatrixState.rotate(180, 0, 1, 0);
				terrain[0].drawSelf(0,tex_terrain_shandingId,tex_terrain_tuceng_Id, tex_terrain_caodiId, tex_terrain_shitouId,LOTHight,height_span_LOT);
				break;
			case 6:
				MatrixState.translate(WIDTH_LALNDFORM, 0, 0);
				MatrixState.rotate(270, 0, 1, 0);
				terrain[0].drawSelf(0,tex_terrain_shandingId,tex_terrain_tuceng_Id, tex_terrain_caodiId,tex_terrain_shitouId, LOTHight,height_span_LOT);
				break;
			case 7:
				MatrixState.translate(0, 0, WIDTH_LALNDFORM);
				MatrixState.rotate(90, 0, 1, 0);
				terrain[1].drawSelf(0,tex_terrain_shandingId,tex_terrain_tuceng_Id, tex_terrain_caodiId,tex_terrain_shitouId, LOTHight,height_span_LOT);
				break;
			case 8:
				MatrixState.translate(WIDTH_LALNDFORM, 0, WIDTH_LALNDFORM);
				MatrixState.rotate(180, 0, 1, 0);
				terrain[1].drawSelf(0,tex_terrain_shandingId,tex_terrain_tuceng_Id, tex_terrain_caodiId,tex_terrain_shitouId, LOTHight,height_span_LOT);
				break;
			case 9:
				MatrixState.translate(WIDTH_LALNDFORM, 0, 0);
				MatrixState.rotate(270, 0, 1, 0);
				terrain[1].drawSelf(0,tex_terrain_shandingId,tex_terrain_tuceng_Id, tex_terrain_caodiId,tex_terrain_shitouId, LOTHight,height_span_LOT);
				break;
			case 10:
				MatrixState.translate(0, 0, WIDTH_LALNDFORM);
				MatrixState.rotate(90, 0, 1, 0);
				terrain[2].drawSelf(0,tex_terrain_shandingId,tex_terrain_tuceng_Id, tex_terrain_caodiId,tex_terrain_shitouId, LOTHight,height_span_LOT);
				break;
			case 11:
				MatrixState.translate(WIDTH_LALNDFORM, 0, WIDTH_LALNDFORM);
				MatrixState.rotate(180, 0, 1, 0);
				terrain[2].drawSelf(0,tex_terrain_shandingId,tex_terrain_tuceng_Id, tex_terrain_caodiId,tex_terrain_shitouId, LOTHight,height_span_LOT);
				break;
			case 12:
				MatrixState.translate(WIDTH_LALNDFORM, 0, 0);
				MatrixState.rotate(270, 0, 1, 0);
				terrain[2].drawSelf(0,tex_terrain_shandingId,tex_terrain_tuceng_Id, tex_terrain_caodiId, tex_terrain_shitouId,LOTHight,height_span_LOT);
				break;
			case 13://��ƽ��
				MatrixState.translate(WIDTH_LALNDFORM/2, LAND_HIGHEST, WIDTH_LALNDFORM/2);
				MatrixState.rotate(-90, 1, 0, 0);
				terrain_plain.drawSelf(tex_terrain_caodiId);
				break;
			case 15://ɽ��ˮ��ɽ��
				terrain[4].drawSelf(0,tex_terrain_shandingId,tex_terrain_tuceng_Id,tex_terrain_caodiId,tex_terrain_shitouId, waterHillHight,height_span_Water);
				break;
			case 16://ɽ�ϵ�
				terrain[5].drawSelf(1,tex_terrain_shandingId,tex_terrain_tuceng_Id,tex_terrain_caodiId, tex_terrain_shitouId, HillHight,height_span_Hill);
				break;
			case 17://�м��
				terrain[6].drawSelf(1,tex_terrain_shandingId,tex_terrain_tuceng_Id,tex_terrain_caodiId, tex_terrain_shitouId, HillHight,height_span_Hill);
				break;
			case 18://��һ��ɽ��ת270
				MatrixState.translate(WIDTH_LALNDFORM, 0, 0);
				MatrixState.rotate(270, 0, 1, 0);
				terrain[3].drawSelf(1,tex_terrain_shandingId,tex_terrain_tuceng_Id,tex_terrain_caodiId, tex_terrain_shitouId, HillHight,height_span_Hill);
				break;
			case 19://�м��ɽ��ת270
				MatrixState.translate(WIDTH_LALNDFORM, 0, 0);
				MatrixState.rotate(270, 0, 1, 0);
				terrain[6].drawSelf(1,tex_terrain_shandingId,tex_terrain_tuceng_Id,tex_terrain_caodiId, tex_terrain_shitouId, HillHight,height_span_Hill);
				break;
			case 20://�м��ɽ��ת270
				MatrixState.translate(WIDTH_LALNDFORM, 0, 0);
				MatrixState.rotate(270, 0, 1, 0);
				terrain[5].drawSelf(1,tex_terrain_shandingId,tex_terrain_tuceng_Id,tex_terrain_caodiId, tex_terrain_shitouId, HillHight,height_span_Hill);
				break;
			case 21://���Ʒɻ��ܵ�
				MatrixState.translate(WIDTH_LALNDFORM/2, LAND_HIGHEST, WIDTH_LALNDFORM/2);
				MatrixState.rotate(-90, 1, 0, 0);
				terrain_plain.drawSelf(tex_damId);
				break;
			}
        }
        /*	
         * ���Ʒɻ��ķ���
         */
        public void drawPlane(float curr_x,float curr_y,float curr_z,
           float rotationAngle_Plane_X,float rotationAngle_Plane_Y,float rotationAngle_Plane_Z)
        {
        	if(!isno_draw_plane||isVideo)
        	{
        		return;
        	}
        	try
        	{
        		MatrixState.pushMatrix();
            	MatrixState.translate(curr_x,curr_y-10,curr_z);
            	MatrixState.rotate(rotationAngle_Plane_Y, 0, 1, 0);  
            	MatrixState.rotate(rotationAngle_Plane_Z, 0, 0, 1);  
            	MatrixState.rotate(rotationAngle_Plane_X, 1, 0, 0);  
            	GLES20.glEnable(GLES20.GL_BLEND);
    			GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            	MatrixState.translate(0, 0, -70);
            	mark_aim.drawSelf(tx_lockaimtexId);
            	GLES20.glDisable(GLES20.GL_BLEND);
            	MatrixState.translate(0, 0, 70);
            	GLES20.glDisable(GLES20.GL_CULL_FACE);
            	planeModel[planeModelIndex].drawSelf(planeModelTexId[planeModelIndex]);
            	 GLES20.glEnable(GLES20.GL_CULL_FACE);
            	MatrixState.popMatrix();
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        //���Ƶ���
        public void drawLightTower()
        {
        	try
        	{
        		GLES20.glDisable(GLES20.GL_CULL_FACE);//�رձ������
    			GLES20.glEnable(GLES20.GL_BLEND);//�������
    			GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            	MatrixState.pushMatrix();
            	MatrixState.translate(ArchieArray[mapId][3][0]*WIDTH_LALNDFORM,200+LAND_HIGHEST, ArchieArray[mapId][3][1]*WIDTH_LALNDFORM);
            	MatrixState.pushMatrix();
            	MatrixState.rotate(180, 1, 0, 0);
            	lighttower.drawSelf(tex_lightid);
            	MatrixState.popMatrix();
            	MatrixState.rotate(90, 1, 0, 0);
            	MatrixState.rotate(lightAngle,0, 0, 1);
            	lighttower.drawSelf(tex_lighttowerid);
            	MatrixState.rotate(180, 1, 0, 0);
            	lighttower.drawSelf(tex_lighttowerid);
            	MatrixState.popMatrix();
            	GLES20.glDisable(GLES20.GL_BLEND);
            	GLES20.glEnable(GLES20.GL_CULL_FACE);//�򿪱������
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        //����̹��
        public void drawTanke(int rowi,int colj,int rowT,int colT)
        {
        	try
        	{
        		for(TanKe tanke:tankeList)//����̹��
        		{
            		tanke.drawSelf(tex_tankeid,rowi,colj,rowT,colT,
            				tex_backgroundRectId, tex_numberRectId,tex_locktexId);
            	}
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        //���Ƹ�����
        public void drawarchie(int rowi,int colj,int rowT,int colT){
        	try
        	{
        		for(int i=0;i<archie_List.size();i++)//���Ƹ�����
        		{
        			archie_List.get(i).drawSelf(texBarbetteId,texCubeId,texBarrelId,rowi,colj,rowT,colT,
        					tex_backgroundRectId, tex_numberRectId,tex_locktexId		
        			);
        		}
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        //���ƾ����
        public void drawHouse(int ii,int jj,int rowR,int colR)
        {
        	if(!isno_draw_arsenal)
        	{
        		return;
        	}
        	GLES20.glDisable(GLES20.GL_CULL_FACE);//�رձ������
        	try
        	{
        		for(Arsenal_House ah:arsenal)
        		{
            		ah.drawSelf(tex_frontId, tex_frontId, tex_roofId,tex_AnnulusId,lightAngle,
                			tex_backgroundRectId,  tex_numberRectId,tex_locktexId,
                			ii,jj,rowR, colR);	
            	}	
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        	GLES20.glEnable(GLES20.GL_CULL_FACE);//�򿪱������
        }
        //������ͨ����
	    public void drawHousePlane(int ii,int jj,int rowR,int colR)
	    {
	    	try
	    	{
	    		for(PlaneHouse ph:houseplane)
		    	{
		    		ph.drawSelf(tex_housePlaneId[1], tex_housePlaneId[0],tex_housePlaneSmallId[0],tex_housePlaneSmallId[1], tex_chimneyId, ii,jj,rowR, colR);
		    	}
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }
        //������
	    public void drawTree(int ii,int jj,int rowR,int colR)
	    {
	    	GLES20.glEnable(GLES20.GL_BLEND);
			GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
			try
			{
				for(int i=0;i<treeList.size();i++)
		    	{
		    		treeList.get(i).drawSelf(ii,jj,rowR, colR);
		    	}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	    	GLES20.glDisable(GLES20.GL_BLEND);
	    }
	    //���Ʊ�ըЧ��
        public void drawBomb()
        {
        	GLES20.glDisable(GLES20.GL_CULL_FACE);//�رձ������
        	GLES20.glEnable(GLES20.GL_BLEND);
			GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
			try
			{
				for(DrawBomb dbb:copybaozhaList)
				{
	        		dbb.drawSelf();
	        	}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
        	GLES20.glDisable(GLES20.GL_BLEND);
        	GLES20.glEnable(GLES20.GL_CULL_FACE);//�򿪱������
        }
        //���ƴ��
        public void drawdam()
        {
        	if(ArchieArray[mapId][5].length>0)
    		{
        		if(!dam.isShaderOk)
            	{
            		dam.isShaderOk=true;
            		dam.initShader();//��ʼ��shader����
            	}
        		dam.drawSelf(tex_damId);
    		}
        }
        //�������ɻ���ըЧ��
        public void drawBaoZhaXiaoguo()
        {
        	GLES20.glDisable(GLES20.GL_CULL_FACE);//�رձ������
        	if(isCrash)//�ɻ�ը��
        	{
        		isFireOn=false;
        		fireButton.isButtonDown=0;//��ť���ٱ仯
	        	up_button.isButtonDown=0;//��ť���ٱ仯
	        	down_button.isButtonDown=0;//��ť���ٱ仯
	        	keyState=keyState&0xc;
	        	upId=2;
	        	shootId=2;
        		GLES20.glEnable(GLES20.GL_BLEND);
    			GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
    			MatrixState.pushMatrix();
    			MatrixState.translate(curr_PlaneX,curr_PlaneY,curr_PlaneZ);
    			MatrixState.scale(BaoZha_scal, BaoZha_scal, BaoZha_scal);
    			MatrixState.rotate(-90, 1, 0, 0);
    			bombRect.drawSelf(baoZhaXiaoguo);
    			MatrixState.popMatrix();
    			GLES20.glDisable(GLES20.GL_BLEND);
        	}
        	GLES20.glEnable(GLES20.GL_CULL_FACE);//�򿪱������
        }
        //-------------------------������Ƶ�����ϵĸ�����ť-----------------------------
		public void drawVideoDirection()
		{
			if(!isVideo)//���������Ƶ���Ž���Ͳ�����
			{
				return;
			}
			MatrixState.pushMatrix();
			MatrixState.setProjectOrtho(-ratio, ratio, -1, 1, 1, 10);
			MatrixState.setCamera(0, 0, 0, 0, 0, -1, 0, 1, 0);//�ָ�����
			MatrixState.copyMVMatrix();
			//�������
			GLES20.glEnable(GLES20.GL_BLEND);  
			//���û������
			GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
			
            //-------------------------�������˵������----------------
            MatrixState.pushMatrix();
            MatrixState.translate(0, 0, -2.5f);
            noticeRect.drawSelf(tex_noticeId[mapId]);
            MatrixState.popMatrix();
          //---------------------��ͣ�Ͳ���---------------------
			MatrixState.pushMatrix();
            MatrixState.translate(-ratio+0.25f*ratio/2, -1+0.35f/2, -1.5f);
            if(isVideoPlaying)
            {
            	menu_video.drawSelf(pauseId);
            }
            else
            {
            	menu_video.drawSelf(playId);
            }
            MatrixState.popMatrix();
			//-----------------------������Ϸ��ť-----------------------
            MatrixState.pushMatrix();
            MatrixState.translate(ratio-0.25f*ratio/2, -1+0.35f/2, -1.5f);
        	menu_video.drawSelf(stopId);
            MatrixState.popMatrix();
            
            //�رջ��
            GLES20.glDisable(GLES20.GL_BLEND);
            MatrixState.popMatrix();
		}
		 //-------------------------------������Ϸ�еĶԻ���-------------------------
		public void drawGameDialog()
		{
			if(!isVideo&&!is_button_return)
			{
				if(!isCrashCartoonOver)
				{
					return;//������Ƿɻ�׹�ٺ�Ķ����������
				}
			}
			MatrixState.pushMatrix();//���Ʊ���
			MatrixState.setProjectOrtho(-1, 1, -1, 1, 1, 10);
			MatrixState.setCamera(0, 0, 0, 0, 0, -1, 0, 1, 0);//�ָ�����
			MatrixState.copyMVMatrix();
			//�������
			GLES20.glEnable(GLES20.GL_BLEND);  
			//���û������
			GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
			MatrixState.pushMatrix();
            MatrixState.translate(0, 0,-1);
            if((isGameMode==0&&isCrash&&!isVideo)||(is_button_return&&!isVideo)||(isVideo&&!isVideoPlaying&&isTrueButtonAction))//����Ƿɻ�׹�٣����߰����˷��ذ�ť
            {
            	menu_Rect.drawSelf(tex_menu_text);
            }
            else if(isGameMode==0&&!isVideo)//ս��ģʽ
            {
            	menu_Rect.drawSelf(tex_menu_text_win);
            }
            //-----------------�ж������ж��ɹ�
            if(isGameMode==1&&isSpecActionState==1)//�ɹ�
            {
            	menu_Rect.drawSelf(tex_actionWinId);
            }
            else if(isGameMode==1&&isSpecActionState==2)//ʧ��
            {
            	menu_Rect.drawSelf(tex_actionFailId);
            }
            MatrixState.popMatrix();
            //�رջ��
            GLES20.glDisable(GLES20.GL_BLEND);
            MatrixState.popMatrix();
		}
		//���Ʒɻ������ڻ���ʱ�ı�ʶ
		public void onDrawHit()
		{
			if(isno_Hit||isno_Vibrate)//�����������
			{
				if(plane_hit_id%4==0)//��������һ��
				{
					if(isno_Hit)
					{
						plane_Hit.drawSelf(tex_plane_hitId);	
					}
					PLANE_Y-=2f;
				}
				if(plane_hit_id%4==2)
				{
					PLANE_Y+=2f;
				}
				plane_hit_id++;
				if(plane_hit_id==8)
				{
					plane_hit_id=0;
					isno_Hit=false;
					isno_Vibrate=false;
				}
			}
		}
		//���Ƶл�
		public void drawEnemyPlane()
		{
			try
			{
				for(EnemyPlane emp:enemy)
				{
					emp.drawSelf(planeHeadId,
			    			screw1Id,
			    			planeBodyId,
			    			planeCabinId, 
			    			frontWingId,
			    			frontWing2Id,
			    			cylinder1Id,
			    			cylinder2Id,
			    			cylinder2Id,
			    			bacckWingId,
			    			topWingId,
			    			tex_backgroundRectId, tex_numberRectId,tex_locktexId
					);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		//������ͶӰ��������ͼ��
        public void drawVirtualIcon()
        {
        	if(isVideo)//�������Ƶ�����У��򷵻�
        	{
        		return;
        	}
        	 MatrixState.pushMatrix();
        	//������������
        	MatrixState.setProjectOrtho(-ratio, ratio,-1f,1f,1,10);
        	//���������
        	MatrixState.setCamera(0, 0, 0, 0, 0,-1, 0, 1, 0);
        	MatrixState.copyMVMatrix();
        	 //�������
            GLES20.glEnable(GLES20.GL_BLEND);  
            //���û������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            MatrixState.pushMatrix();
        	MatrixState.translate(0,0, -6);
        	onDrawHit();//���Ʒɻ�������Ч��
            MatrixState.popMatrix();
            //�ɻ�Ѫ
            MatrixState.pushMatrix();
        	MatrixState.translate(0,0.9f, -3);
        	MatrixState.scale(0.01f, 0.005f, 0.1f);
        	backgroundRect_blood.bloodValue=plane.blood/5-100+6;
        	backgroundRect_blood.drawSelf(tex_backgroundRectId);//Ѫ
            MatrixState.popMatrix();
            
        	MatrixState.pushMatrix();
        	MatrixState.translate(BUTTON_FIRE_XOffset,BUTTON_FIRE_YOffset, -2);
        	fireButton.drawSelf(tex_fireButtonId);//���ƿ���ť
            MatrixState.popMatrix();
            
            MatrixState.pushMatrix();
        	MatrixState.translate(BUTTON_RADAR_XOffset,BUTTON_RADAR_YOffset, -2.1f);
        	radar_bg.drawSelf(tex_radar_bg_Id);//�����״�ͼ��
        	MatrixState.translate(0,0, 0.5f);
        	
        	drawMardPlace();
            MatrixState.popMatrix();
            
            MatrixState.pushMatrix();
        	MatrixState.translate(BUTTON_RADAR_XOffset,BUTTON_RADAR_YOffset, -2f);
        	MatrixState.rotate(RADAR_DIRECTION, 0, 0, 1);
        	radar_plane.drawSelf(tex_radar_plane_Id);//�����״�ָ��ͼ��        	
            MatrixState.popMatrix();
            
            MatrixState.pushMatrix();
        	MatrixState.translate(BUTTON_WEAPON_XOffset,BUTTON_WEAPON_YOffset, -2f);
        	//���л�ͼ
        	weapon_button.drawSelf(tex_button_weaponId[WEAPON_INDEX]);//��������ѡ��ͼ��
            MatrixState.popMatrix();
          
            //�����ӵ�������
            MatrixState.pushMatrix();
        	MatrixState.translate(WEAPON_NUMBER_XOffset,WEAPON_NUMBER_YOffset, -2f);
        	if(0==WEAPON_INDEX)//�ӵ�
        	{
        		if(bullet_number<0)
        		{
        			bullet_number=0;
        		}
        		weapon_number.drawSelfLeft(bullet_number+"", tex_rankNumberId);
        	}
        	else//�ڵ�
        	{
        		if(bomb_number<0)
        		{
        			bomb_number=0;
        		}
        		weapon_number.drawSelfLeft(bomb_number+"", tex_rankNumberId);
        	}
        	MatrixState.popMatrix();
        	//---------------���Ƶ���ʱ---------------------------------
        	if(isGameMode==1)
        	{
        		//����ʣ��ʱ�����
        		MatrixState.pushMatrix();
            	MatrixState.translate(-2*ratio*0.1f,WEAPON_NUMBER_YOffset, -2f);
            	leftTimeRect.drawSelf(tex_lefttimeId);
            	MatrixState.popMatrix();
        		//��������
        		MatrixState.pushMatrix();
            	MatrixState.translate(0,WEAPON_NUMBER_YOffset, -2f);
        		if(goTime<0)
        		{
        			goTime=0;
        		}
        		weapon_number.drawSelfLeft(goTime+"", tex_rankNumberId);
            	MatrixState.popMatrix();
        	}
        	MatrixState.pushMatrix();
        	MatrixState.translate(BUTTON_UP_XOffset,BUTTON_UP_YOffset, -2);
        	up_button.drawSelf(tex_button_upId);//�������ϰ�ť
            MatrixState.popMatrix();
            
            MatrixState.pushMatrix();
        	MatrixState.translate(BUTTON_DOWN_XOffset,BUTTON_DOWN_YOffset, -2);
        	down_button.drawSelf(tex_button_downId);//�������°�ť
            MatrixState.popMatrix();
            
            //�رջ��
            GLES20.glDisable(GLES20.GL_BLEND);
            MatrixState.popMatrix();
        }
        //���Ƹ�������ı�־λ��
        //���Ʊ�־λ����ɫ����
        public void drawMardPlace()
        {
        	try
    		{
	        	if(lightAngle%10!=0)//�ɻ��Ǳ���ͼ��
	        	{
        			plane.drawSelfMark(tex_mark_planeId);	
	        	}
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    		if(!(mapId==4||mapId==5))
    		{
    			try
        		{
    	    		for(EnemyPlane emp:enemy)//�л��Ǳ���ͼ��
    	        	{
            			emp.drawSelfMark(tex_mark_ackId);
    	        	}
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
    		}
    		if(!(mapId==3||mapId==5))
    		{
    			try
        		{
    	        	for(TanKe tanke:tankeList)//̹��
    	        	{
            			tanke.drawSelfMark(tex_mark_tanke);
    	        	}
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        		try
        		{
    	        	for(int i=0;i<archie_List.size();i++)//���Ƹ������Ǳ���ͼ��
    	    		{
            			archie_List.get(i).drawSelfMark(tex_mark_tanke);
    	    		}
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
    		}
    		if(!(mapId==3||mapId==4))
    		{
    			try
        		{
    	        	for(Arsenal_House ah:arsenal)//������Ǳ���ͼ�����
    	        	{
    	    			ah.drawSelfMark(tex_mark_arsenalId);	
    	    		}
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
    		}
        }
        //------------------------------- һ�� �˵�--------���Ʋ˵�����-------------
	    public void drawGameMenu()
	    {
	    	if(1==isMenuLevel)//һ���˵�
	    	{
	    		//����͸��ͶӰ
		    	MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 3, 200);
		    	//���������
		    	MatrixState.setCamera(0, 0, 8, 0, 0,0, 0, 1, 0);
		    	MatrixState.copyMVMatrix();
		    	if(!(doorState==2&&curr_menu_index==2))//�����а�����²����ƴ���
		    	{
		    		//���Ƶ����˵�½�ر���
			    	MatrixState.pushMatrix();
			    	MatrixState.translate(0, 0,-100);
			    	menu_Background.drawSelf(tex_bgId);
			    	MatrixState.popMatrix();
			    	//�����Ʋ�
			    	MatrixState.pushMatrix();
			    	GLES20.glEnable(GLES20.GL_BLEND);
			    	GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
			    	MatrixState.translate(0, 0,-90);
			    	menu_clouds.drawSelf(tex_cloudsId);
			    	if(isDrawBaozha)//������Ƶ��������ı�ըЧ��ͼ
			    	{
			    		MatrixState.translate(0, 0,1);
			    		MatrixState.scale(baozha_ratio, baozha_ratio,1);
			    		bombRect.drawSelf(baoZhaXiaoguo);
			    	}
			    	GLES20.glDisable(GLES20.GL_BLEND);
			    	MatrixState.popMatrix();
		    	}
		    	if(doorState!=2)//���Ƶ���
		    	{
		        	//���Ƶ���
		    		MatrixState.pushMatrix();
		        	MatrixState.translate(0, missile_YOffset, missile_ZOffset);
		        	MatrixState.rotate(-90, 0, 0, 1);
		        	MatrixState.rotate(missile_rotation, 0, 1, 0);
		        	missile_menu.drawSelft(tex_rectId);
		        	MatrixState.popMatrix();
		    	}
	    	}
	    	//������������
	    	MatrixState.setProjectOrtho(-ratio, ratio,-1f,1f,1,10);
	    	//���������
	    	MatrixState.setCamera(0, 0, 1, 0, 0,-1, 0, 1, 0);
	    	MatrixState.copyMVMatrix();
	    	if(1==isMenuLevel)//���û�н����ͼѡ�����
	    	{
	    		if(!(doorState==2&&(curr_menu_index==1||curr_menu_index==2||curr_menu_index==3||curr_menu_index==4)))
		    	{
		        		//�����ϲ��Ż�����
			        	MatrixState.pushMatrix();
			        	MatrixState.translate(0, door_YOffset, -2);
			        	front_door.drawSelf(tex_menu_doorId);
			        	MatrixState.popMatrix();
			        	//�����²��� 
			        	MatrixState.pushMatrix();
			        	MatrixState.translate(0, -Math.abs(door_YOffset), -2);
			        	MatrixState.rotate(180, 0, 0, 1);   
			        	front_door.drawSelf(tex_menu_doorId);
			        	MatrixState.popMatrix();
		    	}
	    	}
	    	//�������
	        GLES20.glEnable(GLES20.GL_BLEND);  
	        //���û������
	        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	        if(1==isMenuLevel)//�����ǰ����λ�ڵ�ͼѡ�����
	        {
	        	if(doorState==1)//�����ǰλ�ڿ��ֽ���,���Ƶ����˵���ť
		        {
	        		MatrixState.pushMatrix();
		        	MatrixState.translate(menu_button_XOffset, 0, -1);
		        	front_cover_button.drawSelf(tex_front_coverId);//����ǰ�ߵ�����
		        	MatrixState.popMatrix();
		        }
		        //�������ý������
		        if(doorState==2&&curr_menu_index==1)
		        { 
		        	//�Ƿ�����������
		        	MatrixState.pushMatrix();
		        	MatrixState.translate(SETTING_BUTTON_XOffset1,SETTING_BUTTON_YOffset1, -1);
		        	menu_setting.drawSelf(tex_musicId[isMusicOn]);
		            MatrixState.popMatrix();
		            //�Ƿ�����Ч����
		            MatrixState.pushMatrix();
		        	MatrixState.translate(SETTING_BUTTON_XOffset2,SETTING_BUTTON_YOffset2, -1);
		        	menu_setting.drawSelf(tex_soundId[isSoundOn]);
		            MatrixState.popMatrix();
		            //�Ƿ�����Ч��
		            MatrixState.pushMatrix();
		        	MatrixState.translate(SETTING_BUTTON_XOffset3,SETTING_BUTTON_YOffset3, -1);
		        	menu_setting.drawSelf(tex_vibrateId[isVibrateOn]);
		            MatrixState.popMatrix();
		        }
		        //�������а����
		        if(doorState==2&&curr_menu_index==2)
		        {
		        	MatrixState.pushMatrix();
		        	MatrixState.translate(0, 0, -1);
		        	front_frame.drawSelf(tex_rankBgId);//�������а񱳾�
		        	MatrixState.popMatrix();
		        	//---------------------
		        	for(int i=0;i<rank.size();i++)//��������
		        	{
		        		float curr_y=0.22f-RANK_NUMBER_HEIGHT*1.3f*i+rank_move;//��ǰyλ��
		        		if(curr_y<=0.24f&&curr_y>=-0.68f)//ȷ����Χ
		        		{
		        			MatrixState.pushMatrix();
			        		MatrixState.translate(0, curr_y, 0); 
			        		//���ƹؿ�
			        		MatrixState.pushMatrix();
			        		MatrixState.translate(-ratio*0.60f, 0, 0);
			        		map_name.drawSelf(tex_mapId[Integer.parseInt(rank.get(i)[0])]);
			        		MatrixState.popMatrix();
			        		//�ܵ÷�
			        		MatrixState.pushMatrix();
			        		MatrixState.translate(-ratio*0.17f, 0, 0);
			        		rank_number.drawSelf(rank.get(i)[1],tex_rankNumberId);
			        		MatrixState.popMatrix();
			        		//��ʱ
			        		MatrixState.pushMatrix();
			        		MatrixState.translate(ratio*0.23f, 0, 0);
			        		rank_number.drawSelf(rank.get(i)[2],tex_rankNumberId);
			        		MatrixState.popMatrix();
			        		//����
			        		MatrixState.pushMatrix();
			        		MatrixState.translate(ratio*0.73f, 0, 0);
			        		rank_number.drawSelf(rank.get(i)[3],tex_rankNumberId);
			        		MatrixState.popMatrix();
			        		MatrixState.popMatrix();
		        		}
		        	}
		        }
		        //���ư�������
		        if(doorState==2&&curr_menu_index==3)
		        {
		        	MatrixState.pushMatrix();
		        	MatrixState.translate(0, help_YOffset, -1);
		        	helpView.drawSelf(tex_helpId);
		        	MatrixState.popMatrix();
		        }
		        //���ƹ��ڽ���
		        if(doorState==2&&curr_menu_index==4)
		        {
		        	MatrixState.pushMatrix();
		        	MatrixState.translate(0, about_YOffset, -1);
		        	aboutView.drawSelf(tex_aboutId);
		        	MatrixState.popMatrix();
		        }
		        if(!(doorState==2&&curr_menu_index==2))
		        {
		        	front_frame.drawSelf(tex_front_frameId);//������ǰ�ߵ�ǰ��ͼ
		        }
	        }
	        GLES20.glDisable(GLES20.GL_BLEND);
	        //----------------------�����˵�-------------�������ģʽѡ�����------------------------------
	        if(2==isMenuLevel)
	        {
	        	//------------���Ȼ��Ʒɻ�ģ��------
	        	//����͸��ͶӰ
		    	MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 3, 20000);  
		    	//���������
		    	MatrixState.setCamera(0, 100, 220, 0, 0,0, 0, 1, 0);
		    	MatrixState.copyMVMatrix();
		    	
		    	MatrixState.pushMatrix();
		        MatrixState.translate(0, 100f, -150); 
//		        backgroundRect.drawSelf(backgroundId_01);
		        MatrixState.translate(0, -150f, 170); 
		        MatrixState.rotate(-90, 1, 0, 0);
//		        backgroundRect.drawSelf(backgroundId_02);
		        MatrixState.popMatrix();
		         
		        MatrixState.pushMatrix();   
		    	MatrixState.rotate(planeRotate, 0, 1, 0);
		    	GLES20.glDisable(GLES20.GL_CULL_FACE);//�رձ������
	        	drawPlaneModel();//����չ̨
	        	GLES20.glEnable(GLES20.GL_CULL_FACE);
	        	MatrixState.popMatrix();
	        	//-----------------�������ģʽѡ��Ĳ˵�����------------------
	        	//������������
		    	MatrixState.setProjectOrtho(-ratio, ratio,-1f,1f,1,10);
		    	//���������
		    	MatrixState.setCamera(0, 0, 1, 0, 0,-1, 0, 1, 0);
		    	MatrixState.copyMVMatrix();
		    	//�������
		        GLES20.glEnable(GLES20.GL_BLEND);  
		        //���û������
		        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		    	//��ͷѡ�ɻ������еı�����
	        	MatrixState.pushMatrix();
	        	MatrixState.translate(0, 1-PLANE_SELECT_HEAD_HEIGHT/2, 0);
	        	plane_select_head.drawSelf(tex_plane_select_head);
	        	MatrixState.popMatrix();
	        	//-------------������������ɻ�ͼƬ-----------------------
	        	//���Ƶ�һ���ɻ�ͼƬ
	        	MatrixState.pushMatrix();
	        	MatrixState.translate(MENU_TWO_PLANE_ICON_ONE_XOffset,MENU_TWO_PLANE_ICON_ONE_YOffset, 0);
	        	menu_two_plane_icon.drawSelf(tex_menu_two_plane_iconId[0][tex_menu_two_plane_iconIndex[0]]);
	        	MatrixState.popMatrix();
	        	//���Ƶڶ����ɻ�ͼƬ
	        	MatrixState.pushMatrix();
	        	MatrixState.translate(MENU_TWO_PLANE_ICON_TWO_XOffset, MENU_TWO_PLANE_ICON_TWO_YOffset, 0);
	        	menu_two_plane_icon.drawSelf(tex_menu_two_plane_iconId[1][tex_menu_two_plane_iconIndex[1]]);
	        	MatrixState.popMatrix();
	        	//���Ƶ������ɻ�ͼƬ
	        	MatrixState.pushMatrix();
	        	MatrixState.translate(MENU_TWO_PLANE_ICON_THREE_XOffset,MENU_TWO_PLANE_ICON_THREE_YOffset, 0);
	        	menu_two_plane_icon.drawSelf(tex_menu_two_plane_iconId[2][tex_menu_two_plane_iconIndex[2]]);
	        	MatrixState.popMatrix();
	        	
	        	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	        	if(isChangeAlpha)//������Ըı䲻͸����
	        	{
	        		float temp=currAlpha+direction*0.05f;
	        		if(temp>1.0)
	        		{
	        			temp=1.0f;
	        			direction=-direction;
	        			menu_two_game_model_btn.currAlpha=temp;
	        		}
	        		else if(temp<0.5f)
	        		{
	        			temp=0.5f;
	        			direction=-direction;
	        			menu_two_game_model_btn.currAlpha=temp;
	        		}
	        		else
	        		{
	        			currAlpha=temp;
	        			menu_two_game_model_btn.currAlpha=currAlpha;
	        		}
	        	}
	        	else
	        	{
	        		menu_two_game_model_btn.currAlpha=1.0f;
	        	}
	        	if(isGameMode==0)//ս��ģʽ
	        	{
	        		//-------------------------ս��ģʽ��ť----------------
		        	MatrixState.pushMatrix();
		        	MatrixState.translate(MENU_TWO_WAR_BUTTON_XOffset, MENU_TWO_WAR_BUTTON_YOffset, 0);
	        		menu_two_game_model_btn.drawSelf(tex_menu_two_war_btnId[tex_menu_two_war_btnIndex]);
		        	MatrixState.popMatrix();
		        	//-------------------------�ر��ж���ť----------------
		        	menu_two_game_model_btn.currAlpha=1.0f;
		        	MatrixState.pushMatrix();
		        	MatrixState.translate(MENU_TWO_ACTION_BUTTON_XOffset, MENU_TWO_ACTION_BUTTON_YOffset, 0);
	        		menu_two_game_model_btn.drawSelf(tex_menu_two_action_btnId[tex_menu_two_action_btnIndex]);
		        	MatrixState.popMatrix();
	        		
	        	}
	        	else//�����ж�
	        	{
		        	//-------------------------�ر��ж���ť----------------
		        	MatrixState.pushMatrix();
		        	MatrixState.translate(MENU_TWO_ACTION_BUTTON_XOffset, MENU_TWO_ACTION_BUTTON_YOffset, 0);
	        		menu_two_game_model_btn.drawSelf(tex_menu_two_action_btnId[tex_menu_two_action_btnIndex]);
		        	MatrixState.popMatrix();
		        	//-------------------------ս��ģʽ��ť----------------
		        	menu_two_game_model_btn.currAlpha=1.0f;
		        	MatrixState.pushMatrix();
		        	MatrixState.translate(MENU_TWO_WAR_BUTTON_XOffset, MENU_TWO_WAR_BUTTON_YOffset, 0);
	        		menu_two_game_model_btn.drawSelf(tex_menu_two_war_btnId[tex_menu_two_war_btnIndex]);
		        	MatrixState.popMatrix();
	        	}
	        	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

	        	//----------����ѡ��ť=================
	        	MatrixState.pushMatrix();
        		MatrixState.translate(MENU_TWO_BUTTON_LEFT_XOffset,MENU_TWO_BUTTON_LEFT_YOffset, 0);
        		menu_two_button.drawSelf(tex_menu_two_leftId[tex_menu_two_leftIndex]);
        		MatrixState.popMatrix();
        		
        		MatrixState.pushMatrix();
        		MatrixState.translate(MENU_TWO_BUTTON_RIGHT_XOffset,MENU_TWO_BUTTON_RIGHT_YOffset, 0);
        		menu_two_button.drawSelf(tex_menu_two_rightId[tex_menu_two_rightIndex]);
        		MatrixState.popMatrix();
	        	//-----------------�������ȷ����ť--------
	        	MatrixState.pushMatrix();
        		MatrixState.translate(MENU_TWO_BUTTON_OK_XOffset, MENU_TWO_BUTTON_OK_YOffset, 0);
        		menu_two_button.drawSelf(tex_menu_two_okId[tex_menu_two_okIndex]);
        		MatrixState.popMatrix();
	        	GLES20.glDisable(GLES20.GL_BLEND);
	        }
	        //---------------���������˵�   ��Ҫ����ս��ģʽ,�ر��ж�
	        if(3==isMenuLevel)
	        {
	        	//������������
		    	MatrixState.setProjectOrtho(-ratio, ratio,-1f,1f,1,10);
		    	//���������
		    	MatrixState.setCamera(0, 0, 1, 0, 0,-1, 0, 1, 0);
		    	MatrixState.copyMVMatrix();
		        if(0==isGameMode)//ս��ģʽ
		        {
		        	front_frame.drawSelf(tex_mapSelectedBgId);
		        }
		        else if(1==isGameMode)//�ر��ж�
		        {
		        	front_frame.drawSelf(tex_special_action_bgId);
		        }
	        }
	    }
	    //����ѡ��ɻ������ķ���
	    public void drawPlaneModel()
	    {
	    	 //����չ̨
	         MatrixState.pushMatrix();
	         MatrixState.translate(0, -5f, 0);      
	         drawCircleStation(0);
	         MatrixState.popMatrix();
	    }
	    //����ѡ�ɻ������е�չ̨
	    public void drawCircleStation(float yOffset)
	    {
	    	MatrixState.pushMatrix();
            MatrixState.translate(0, yOffset, 0);
            MatrixState.rotate(-90, 1, 0, 0);
            circle_station.drawSelf(0f,stageId);//��͸��Բ��
            MatrixState.popMatrix();
            
            GLES20.glDisable(GLES20.GL_DEPTH_TEST);//��ȼ��
            //���Ƶ�Ӱ
            MatrixState.pushMatrix();
            MatrixState.rotate(180, 0, 0, 1);
            planeModel[planeModelIndex].drawSelf(planeModelTexId[planeModelIndex]);
            MatrixState.popMatrix();
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);//��ȼ��
	    	GLES20.glEnable(GLES20.GL_BLEND);//�������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);//���û������
            MatrixState.pushMatrix();
            MatrixState.translate(0, yOffset+0.5f, 0);
            MatrixState.rotate(-90, 1, 0, 0);
            circle_station.drawSelf(0.4f,stageId);//͸��Բ��
            MatrixState.popMatrix();
            
            GLES20.glDisable(GLES20.GL_BLEND); //�رջ��       
            MatrixState.pushMatrix();
            MatrixState.translate(0, yOffset, 0);
            MatrixState.rotate(-90, 1, 0, 0);
            MatrixState.pushMatrix();
           
            MatrixState.rotate(90, 1, 0, 0);
            MatrixState.translate(0, -30, 0);
            circle_station.taizi.drawSelf(stageId);//Բ��
            MatrixState.popMatrix();
            MatrixState.popMatrix();
            //����ʵ�ʴ�
	        MatrixState.pushMatrix();
            planeModel[planeModelIndex].drawSelf(planeModelTexId[planeModelIndex]);
	        MatrixState.popMatrix();
	    }
}
	//�������е���Դ
	public void loadResource()
	{
		switch(load_step)
		{
		case 0:
			init_Shader();
			load_step++;
			break;
		case 1:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 2:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 3:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 4:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 5:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 6:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 7:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 8:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 9:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 10:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 11:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 12:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 13:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 14:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 15:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 16:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 17:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 18:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 19:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 20:
			init_All_Texture(load_step);
			load_step++;
			break;
		case 21:
			init_All_Object(load_step);
			load_step++;
			break;
		case 22:
			init_All_Object(load_step);
			load_step++;
			break;
		case 23:
			init_All_Object(load_step);
			load_step++;
			break;
		case 24:
			init_All_Object(load_step);
			load_step++;
			break;
		case 25:
			init_All_Object(load_step);
			load_step++;
			break;
		case 26:
			init_All_Object(load_step);
			load_step++;
			break;
		case 27:
			init_All_Object(load_step);
			load_step++;
			break;
		case 28:
			init_All_Object(load_step);
			load_step++;
			break;
		case 29:
			init_All_Object(load_step);
			load_step++;
			break;
		case 30:
			init_All_Object(load_step);
			load_step++;
			break;
		case 31:
			init_All_Object(load_step);
			load_step++;
			break;
		case 32:
			init_All_Object(load_step);
			load_step++;
			break;
		case 33:
			init_All_Object(load_step);
			load_step++;
			break;
		case 34:
			init_All_Object(load_step);
			load_step++;
			break;
		case 35:
			init_All_Object(load_step);
			load_step++;
			break;
		case 36:
			init_All_Object(load_step);
			load_step++;
			break;
		case 37:
			init_All_Object(load_step);
			load_step++;
			break;
		case 38:
			init_All_Object(load_step);
			load_step++;
			break;
		case 39:
			init_All_Object(load_step);
			load_step++;
			break;
		case 40:
			init_All_Object(load_step);
			isLoadedOk=true;//�л���һ���˵�
			isMenuLevel=1;//�л���һ���˵�
			loadingView=null;//����
			processBar=null;//����
			break;
		}
	}
	//����shader����  
	public void init_Shader()
	{
		ShaderManager.loadCodeFromFile(getResources());
		ShaderManager.compileShader();
	}
	//��������ķ���
	public void init_All_Texture(int index)
	{
		switch(index)
		{
		case 1:
			stageId=initTexture(getResources(), R.drawable.taiziwenli,false);//չ̨����
			//�ɻ�����
			planeModelTexId[0]=initTexture(getResources(), R.drawable.feijione,false);
			planeModelTexId[1]=initTexture(getResources(), R.drawable.feijitwo,false);
			planeModelTexId[2]=initTexture(getResources(), R.drawable.feijithree,false);
			tex_plane_select_head=initTexture(getResources(), R.drawable.plane_select_head,false);//��������
			break;
		case 2:
			tex_menu_two_war_btnId[0]=initTexture(getResources(), R.drawable.menu_two_war_btn_up,false);
        	tex_menu_two_war_btnId[1]=initTexture(getResources(), R.drawable.menu_two_war_btn_down,false);
        	tex_menu_two_action_btnId[0]=initTexture(getResources(), R.drawable.menu_two_action_btn_up,false);
        	tex_menu_two_action_btnId[1]=initTexture(getResources(), R.drawable.menu_two_action_btn_down,false);
        	tex_menu_two_okId[0]=initTexture(getResources(), R.drawable.plane_select_ok,false);//�˵���ȷ����ť
        	tex_menu_two_okId[1]=initTexture(getResources(), R.drawable.plane_select_ok_down,false);//�˵���ȷ����ť
			break;
		case 3:
        	tex_menu_two_leftId[0]=initTexture(getResources(), R.drawable.menu_two_left_up,false);//�󰴰�ť
        	tex_menu_two_leftId[1]=initTexture(getResources(), R.drawable.menu_two_left_down,false);//�󰴰�ť
        	tex_menu_two_rightId[0]=initTexture(getResources(), R.drawable.menu_two_right_up,false);//�Ұ���ť
        	tex_menu_two_rightId[1]=initTexture(getResources(), R.drawable.menu_two_right_down,false);//�Ұ���ť
        	tex_menu_two_plane_iconId[0][0]=initTexture(getResources(), R.drawable.menu_two_planeicon_one_up,false);//��һ���ɻ�ͼƬ
            tex_menu_two_plane_iconId[0][1]=initTexture(getResources(), R.drawable.menu_two_planeicon_one_down,false);//�ڶ����ɻ�ͼƬ
            tex_menu_two_plane_iconId[1][0]=initTexture(getResources(), R.drawable.menu_two_planeicon_two_up,false);//�������ɻ�ͼƬ
            tex_menu_two_plane_iconId[1][1]=initTexture(getResources(), R.drawable.menu_two_planeicon_two_down,false);//��һ���ɻ�ͼƬ
            tex_menu_two_plane_iconId[2][0]=initTexture(getResources(), R.drawable.menu_two_planeicon_three_up,false);//�ڶ����ɻ�ͼƬ
            tex_menu_two_plane_iconId[2][1]=initTexture(getResources(), R.drawable.menu_two_planeicon_three_down,false);//�������ɻ�ͼƬ
			break;  
		case 4:
			//��������
			tex_lighttowerid=initTexture(getResources(), R.drawable.light,false);
			tex_lightid=initTexture(getResources(), R.drawable.nighttexid,false);   
			tex_terrain_tuceng_Id=initTexture(getResources(), R.drawable.zhonjiantuceng,true);//.tuceng1);
			tex_terrain_caodiId=initTexture(getResources(), R.drawable.caodi,true);
		    tex_terrain_shitouId=initTexture(getResources(), R.drawable.xiacengtuceng,true);//.shitou);
		    tex_terrain_shandingId=initTexture(getResources(), R.drawable.shanding,true);//.shitou);
			//����ť����
		    tex_fireButtonId=initTexture(getResources(), R.drawable.firebutton,false);
		    tex_lefttimeId=initTexture(getResources(), R.raw.lefttime,false);
			//�������
		    tex_skyBallId=initTexture(getResources(), R.drawable.sky,false);
		    tex_nightId=initTexture(getResources(), R.drawable.skynight,false);//ҹ��
			//��ˮ����
			tex_waterId=initTexture(getResources(), R.drawable.water,false);
			break;
		case 5:
			//̹������
			tex_tankeid=initTexture(getResources(), R.drawable.tanke,false);
			//������������
		    tex_roofId=initTexture(getResources(), R.drawable.roofwenli,false);
		    tex_frontId=initTexture(getResources(), R.drawable.fangwufront,false);
		    tex_AnnulusId=initTexture(getResources(), R.drawable.yuanhuanwenli,false);
			break;
		case 6:
			tex_special_action_bgId=initTexture(getResources(), R.drawable.map_selected_bg_action,false);//�ر��ж�����ͼ
			treeTexId_2=initTexture(getResources(), R.drawable.tree2,false);;//������
			treeTexId=initTexture(getResources(), R.drawable.tree,false);;//������
			//������������
			tex_locktexId=initTexture(getResources(), R.drawable.locktexid,false);
			//����Ŀ������
			tx_lockaimtexId=initTexture(getResources(), R.raw.locktexidaim,false); 
			//�ɻ������е�����
			tex_plane_hitId=initTexture(getResources(), R.drawable.planehittext,false);
			 //�̴�
		    tex_chimneyId=initTexture(getResources(), R.drawable.chimney,false);
		    //ƽ��������Id
		    tex_housePlaneId[0]=initTexture(getResources(), R.drawable.bigsmallpingfang,false);
		    tex_housePlaneId[1]=initTexture(getResources(), R.drawable.bigsmallpingfangwuding,false);  
		    //Сƽ������
		    tex_housePlaneSmallId[0]=initTexture(getResources(), R.drawable.smallpingfang,false);
		    tex_housePlaneSmallId[1]=initTexture(getResources(), R.drawable.smallpingfangwuding,false);
			break;
		case 7: 
			//����˵����������ɻ�׹�ٺ��
		    tex_menu_text=initTexture( getResources(),R.drawable.caidanfanhuianniu,false);//����
		    tex_menu_text_win=initTexture( getResources(),R.drawable.caidanshengli,false);//Ӯ��ʱ�Ĳ˵�����
		    //��Ƶ���Ű�ť����ʾ���̰�ť
		    stopId=initTexture( getResources(),R.drawable.stop,false);//ֹͣ��ť
			pauseId=initTexture( getResources(),R.drawable.pause,false);//��ͣ��ť
			playId=initTexture( getResources(),R.drawable.play,false);//���Ű�ť
			break;
		case 8:
			 //-----------------------��ʼ���˵����ֵ�����
			//�ӵ�����
			tex_bulletId=initTexture(getResources(), R.drawable.bullet_purple,false);
		    //�״ﱳ��
			tex_radar_bg_Id=initTexture(getResources(), R.drawable.rador_bg,false);
		    //�״�ɻ�ָ�� 
			tex_radar_plane_Id=initTexture(getResources(), R.drawable.rador_plane,false);
			//����ͼ��
			tex_button_weaponId[0]=initTexture(getResources(), R.drawable.bullet_button,false);//�ӵ���ťͼ��
			tex_button_weaponId[1]=initTexture(getResources(), R.drawable.missile_button,false);//������ťͼ��
			//���ϰ�ť����
		    tex_button_upId=initTexture(getResources(), R.drawable.button_up,false);
		    //���°�ť����
		    tex_button_downId=initTexture(getResources(), R.drawable.button_down,false);
			break;
		case 9:
			tex_musicId[1]=initTexture(getResources(), R.drawable.music_on,false);//�Ƿ�����������
		    tex_musicId[0]=initTexture(getResources(), R.drawable.music_off,false);//�Ƿ�����������
		    tex_soundId[1]=initTexture(getResources(), R.drawable.sounds_on,false);//�Ƿ�����Ч��������
		  
			break;
		case 10:
			//�ɻ��������
			planeHeadId=initTexture(getResources(), R.drawable.planehead,false);
			frontWingId=initTexture(getResources(), R.drawable.frontwing,false);
			frontWing2Id=initTexture(getResources(), R.drawable.frontwing2,false);
			bacckWingId=initTexture(getResources(), R.drawable.planebody,false);
			topWingId=planeHeadId;   
			planeBodyId=bacckWingId;
			planeCabinId=planeHeadId;
			cylinder1Id=planeHeadId;
			cylinder2Id=cylinder1Id;
			screw1Id=planeCabinId;
			break;
		case 11:
			//�����ж��ɹ�ʧ�ܶԻ���
		    tex_actionWinId=initTexture(getResources(), R.raw.action_win,false);//�����ж��ɹ��Ի���
		    tex_actionFailId=initTexture(getResources(), R.raw.action_fail,false);//ʧ�ܶԻ���
		    //-----��Ϸ��ʼǰ��˵������
		    tex_noticeId[0]=initTexture(getResources(), R.raw.war_yyxd,false);
		    tex_noticeId[1]=initTexture(getResources(), R.raw.war_wzgl,false);
		    tex_noticeId[2]=initTexture(getResources(), R.raw.war_zjfc,false);
		    tex_noticeId[3]=initTexture(getResources(), R.raw.action_plxd,false);
		    tex_noticeId[4]=initTexture(getResources(), R.raw.action_smfb,false);
		    tex_noticeId[5]=initTexture(getResources(), R.raw.action_zsxd,false);
			break;
		case 12:
			//�����ڵ�����
			texBarbetteId[0]=initTexture(getResources(), R.drawable.barrel_circle_long,false);
			texBarbetteId[0]=initTexture(getResources(), R.drawable.barrel_circle_short,false);
			texCubeId=initTexture(getResources(), R.drawable.barrel_cylinder_long,false);
			texBarrelId[0]=initTexture(getResources(), R.drawable.barrel_cylinder_long,false);
			texBarrelId[1]=initTexture(getResources(), R.drawable.barrel_circle_long,false);
			texBarrelId[2]=initTexture(getResources(), R.drawable.barrel_cylinder_short,false);
			texBarrelId[3]=initTexture(getResources(), R.drawable.barrel_circle_short,false);
			break;
		case 13:
			baoZhaXiaoguo=initTexture( getResources(),R.drawable.baozaoxiaoguo,false);//��ըЧ��
		    baoZhaXiaoguo2=initTexture( getResources(),R.drawable.baozhazdan,false);//��ըЧ��2
		    tex_numberRectId=initTexture( getResources(),R.drawable.number,false);//��������
		    tex_backgroundRectId=initTexture( getResources(),R.drawable.xuebeijing,false);//Ѫ����ͼƬ
		    tex_damId=initTexture(getResources(),R.drawable.dam,false);//���
			break;
		case 14:
			//��־��λ�õ����Ǳ����ϵ�
			tex_mark_tanke=initTexture( getResources(),R.drawable.marktanke,false);//̹�˺͸������Ǳ���ͼ��
			tex_mark_ackId=initTexture( getResources(),R.drawable.markask,false);//�л��Ǳ���ͼ��
			tex_mark_arsenalId=initTexture( getResources(),R.drawable.markarsenal,false);//������Ǳ���ͼ��
			tex_mark_planeId=initTexture( getResources(),R.drawable.markplane,false);//��ҷɻ��Ǳ���ͼ��
			break;
		case 15:
			tex_rectId[0]=initTexture(getResources(), R.drawable.start,false);
		    tex_rectId[1]=initTexture(getResources(), R.drawable.config,false);
		    tex_rectId[2]=initTexture(getResources(), R.drawable.rank,false);
		    tex_rectId[3]=initTexture(getResources(), R.drawable.help,false);
		    tex_rectId[4]=initTexture(getResources(), R.drawable.about,false);
		    tex_rectId[5]=initTexture(getResources(), R.drawable.exit,false);
			break;
		case 16:
			tex_rectId[6]=initTexture(getResources(), R.drawable.other,false); 
		    tex_rectId[7]=tex_rectId[6];
		    tex_rectId[8]=initTexture(getResources(), R.drawable.missile_end,false); 
		    tex_rectId[9]=initTexture(getResources(), R.drawable.missile_cylinder,false);
		    tex_rectId[10]=initTexture(getResources(), R.drawable.missile_tail,false);
		    tex_bgId=initTexture(getResources(), R.drawable.land,false);//�����˵��µı���ͼ
			break;
		case 17:
			tex_cloudsId=initTexture(getResources(), R.raw.clouds,false);//�����˵��µ��Ʋ�
		    tex_front_frameId=initTexture(getResources(), R.drawable.front_frame,false);//�����˵��µı���ͼ
		    tex_front_coverId=initTexture(getResources(), R.drawable.front_cover,false);//�����˵��µı���ͼ
		    tex_menu_doorId=initTexture(getResources(), R.drawable.menu_door,false);//�����˵��µĻ����ű���
			break;
		case 18:
		  	tex_soundId[0]=initTexture(getResources(), R.drawable.sounds_off,false);//�Ƿ�����Ч��������
		    tex_vibrateId[1]=initTexture(getResources(), R.drawable.vibrate_on,false);//�Ƿ���������
		    tex_vibrateId[0]=initTexture(getResources(), R.drawable.vibrate_off,false);//�Ƿ���������
		    tex_helpId=initTexture(getResources(), R.drawable.helpview,false);//��������Id
		    break;
		case 19:
			tex_aboutId=initTexture(getResources(), R.drawable.aboutview,false);//���ڽ���Id
		    tex_mapSelectedBgId=initTexture(getResources(), R.drawable.map_selected_bg,false);//��ͼѡ�����ı���
		    tex_mapId[0]=initTexture(getResources(), R.drawable.yeyingxingdong,false);//��ͼѡ�����ı���
			break;
		case 20:
			tex_mapId[1]=initTexture(getResources(), R.drawable.zhongjifuchou,false);//��ͼѡ�����ı���
		    tex_mapId[2]=initTexture(getResources(), R.drawable.wangzheguilai,false);//��ͼѡ�����ı���
		    tex_rankBgId=initTexture(getResources(), R.drawable.rank_bg,false);//���а񱳾�ͼ
		    tex_rankNumberId=initTexture(getResources(), R.drawable.rank_number,false);//���а���������
			break;
		} 
	}
	//�������еĶ���
	public void init_All_Object(int index)//��һ�ν������봴���ģ�����ǵڶ��ν����Ͳ���Ҫ��
	{
		switch(index)
		{
		case 21:
			//------------------------------���������˵�
			missile_menu=new MissileMenuForDraw(ShaderManager.getOnlyTextureShaderProgram());//�����˵�
			menu_Background=new TextureRect(150, 100, ShaderManager.getWaterTextureShaderProgram(),true,1);//½��
			menu_clouds=new TextureRect(200, 150, ShaderManager.getWaterTextureShaderProgram(),true,3f);//�Ʋ�
			break;
		case 22:
			menu_setting=new TextureRect(SETTING_BUTTON_WIDTH, SETTING_BUTTON_HEIGHT, ShaderManager.getOnlyTextureShaderProgram());//����������
			helpView=new TextureRect(HELP_WIDTH, HELP_HEIGHT, ShaderManager.getOnlyTextureShaderProgram());//��������
			aboutView=new TextureRect(ABOUT_WIDTH, ABOUT_HEIGHT, ShaderManager.getOnlyTextureShaderProgram());//���ڽ���
			rank_number=new NumberForDraw(11,RANK_NUMBER_WIDTH,RANK_NUMBER_HEIGHT, ShaderManager.getOnlyTextureShaderProgram());
			map_name=new TextureRect(RANK_MAP_WIDTH, RANK_MAP_HEIGHT, ShaderManager.getOnlyTextureShaderProgram());//���а����ĵ�ͼ������
			break;
		case 23:
			plane_select_head=new TextureRect(PLANE_SELECT_HEAD_WIDTH, PLANE_SELECT_HEAD_HEIGHT, ShaderManager.getOnlyTextureShaderProgram());//ѡ�ɻ������еı�����
            plane_select_plane=new TextureRect(PLANE_SELECT_PLANE_WIDTH, PLANE_SELECT_PLANE_HEIGHT, ShaderManager.getButtonTextureShaderProgram(),1,0);//ѡ�ɻ������е�ѡ�ɻ���ť
            menu_two_game_model_btn=new TextureRect(MENU_TWO_GAME_MODEL_BUTTON_WIDTH, MENU_TWO_GAME_MODEL_BUTTON_HEIGHT, ShaderManager.getButtonTextureShaderProgram(),1,2);//ѡ�ɻ������е�ѡģʽ��ť
            menu_two_button=new TextureRect(MENU_TWO_BUTTON_WIDTH, MENU_TWO_BUTTON_HEIGHT, ShaderManager.getButtonTextureShaderProgram(),1,0);//ѡ�ɻ������е�ѡģʽ��ť
        	menu_two_plane_icon=new TextureRect(MENU_TWO_PLANE_ICON_WIDTH,MENU_TWO_PLANE_ICON_HEIGHT,ShaderManager.getButtonTextureShaderProgram(),1,0);
            //----����˵������----------------
            noticeRect=new TextureRect(NOTICE_WIDTH, NOTICE_HEIGHT, ShaderManager.getOnlyTextureShaderProgram());
			break;
		case 24:
			//���ص�����Ϣ
			Constant.initLandsHeightInfo(getResources()); 
			//����½�� 
			for(int i=0;i<LANDS_SIZE;i++)
			{
				terrain[i]=new LandForm(i, ShaderManager.getLandformTextureShaderProgram());
			}
			//����ƽ���ͼ
			terrain_plain=new TextureRect(WIDTH_LALNDFORM, HEIGHT_LANDFORM, ShaderManager.getOnlyTextureShaderProgram());
			//��������ť
			fireButton=new TextureRect(BUTTON_FIRE_WIDTH, BUTTON_FIRE_HEIGHT,
					                                ShaderManager.getButtonTextureShaderProgram(),1,1);
			break;
		case 25:
			//���������
			skyBall=new SkyBall(GLGameView.this, SKY_BALL_RADIUS, ShaderManager.getOnlyTextureShaderProgram(),0,0,0);
			skyBallsmall=new SkyBall(GLGameView.this, SKY_BALL_SMALL, ShaderManager.getOnlyTextureShaderProgram(),0,0,0);
			skynight=new SkyNight(1.5f,100,SKY_BALL_SMALL-100);
			skynightBig=new SkyNight(2,50,SKY_BALL_SMALL-100);
			skynightBig.initShader(ShaderManager.getStarrySkyShaderProgram());
			skynight.initShader(ShaderManager.getStarrySkyShaderProgram());
			break;
		case 26:
			//����̹��ģ��
			tanke_body=MoXingJiaZai.loadFromFileVertexOnly("tank_body.obj", getResources(),ShaderManager.getOnlyTextureShaderProgram());//̹��
			break;
		case 27:
			weapon_number=new NumberForDraw(11,WEAPON_NUMBER_WIDTH,WEAPON_NUMBER_HEIGHT, ShaderManager.getOnlyTextureShaderProgram());
			//���ϰ�ť
			up_button=new TextureRect(BUTTON_UP_WIDTH,BUTTON_UP_HEIGHT,ShaderManager.getButtonTextureShaderProgram(),1,1);
			//����ѡ��ť
			down_button=new TextureRect(BUTTON_DOWN_WIDTH,BUTTON_DOWN_HEIGHT,ShaderManager.getButtonTextureShaderProgram(),1,1);
			//��ʾʣ��ʱ��
			leftTimeRect=new TextureRect(2*ratio*0.15f,2*0.13f,ShaderManager.getOnlyTextureShaderProgram());
			break;
		case 28: 
			//��������
			cube=new CubeForDraw(cube_length, cube_width, cube_height, ShaderManager.getOnlyTextureShaderProgram());
			chimney=new Light_Tower(8,15,150,1);//�����̴�
			chimney.initShader(ShaderManager.getOnlyTextureShaderProgram());
			lighttower=new Light_Tower(25,1,300,1);//��������
			lighttower.initShader(ShaderManager.getOnlyTextureShaderProgram());
			break;
		case 29:
			//����ˮ��
			water=new TextureRect(SKY_BALL_RADIUS*3.5f, SKY_BALL_RADIUS*3.5f, ShaderManager.getOnlyTextureShaderProgram(),true,20);//,true,0,0,0);//
			//�����ӵ�������
			bullet_ball=new BallTextureByVertex(BULLET_SCALE,ShaderManager.getOnlyTextureShaderProgram(),-90);
			break;
		case 30:
			//����������ť
			weapon_button=new TextureRect(BUTTON_WEAPON_WIDTH,BUTTON_WEAPON_HEIGHT,ShaderManager.getOnlyTextureShaderProgram());
			numberRect=new NumberForDraw(10,NUMBER_WIDTH,NUMBER_HEIGHT,ShaderManager.getOnlyTextureShaderProgram());//��������
			backgroundRect_blood=new TextureRect(NUMBER_WIDTH*10,NUMBER_HEIGHT,ShaderManager.getStarryXueShaderProgram(),2,0);
			break;
		case 31:
			house=new House(ShaderManager.getOnlyTextureShaderProgram(),backgroundRect_blood,	numberRect);//���������ģ��
			housePlane=new CubeForDraw(house_length,house_width,house_height,ShaderManager.getOnlyTextureShaderProgram());//����ƽ��
		    bombRect=new TextureRect(bomb_width,bomb_height,ShaderManager.getOnlyTextureShaderProgram());//��ըЧ������
		    bombRectr=new TextureRect(bomb_width/2,bomb_height/2,ShaderManager.getOnlyTextureShaderProgram());//��ըЧ������
			break;
		case 32:
			//�ɻ������е��������
		    plane_Hit=new TextureRect(ratio*2,2,ShaderManager.getOnlyTextureShaderProgram());
		    menu_Rect=new TextureRect(0.8f,1.2f,ShaderManager.getOnlyTextureShaderProgram());;//�ɻ���ը��Ĳ˵���ʾ����
		    menu_video=new TextureRect(0.25f*ratio,0.35f,ShaderManager.getOnlyTextureShaderProgram());//���Ž���ĸ�����ť
			break;
		case 33:
			  //��־λ�õľ���
		    mark_placeRect=new TextureRect(0.025f,0.025f,ShaderManager.getOnlyTextureShaderProgram());
		    //�����ɻ�
			plane=new Plane(this,ShaderManager.getOnlyTextureShaderProgram(),mark_placeRect);
		    //����������������
		    mark_lock=new TextureRect(ARCHIBALD_X,ARCHIBALD_Y,ShaderManager.getOnlyTextureShaderProgram()); 
		    //Ŀ���߿�
		    mark_aim=new TextureRect(10	,10,ShaderManager.getOnlyTextureShaderProgram()); 
			break;
		case 34:
		    //�������������
		    treeRect=new TextureRect(treeWhidth,treeHeight,ShaderManager.getOnlyTextureShaderProgram()); 
			//���طɻ�ģ��
			planeModel[0]=MoXingJiaZai.loadFromFileVertexOnly("feiji11.obj", getResources(),ShaderManager.getOnlyTextureShaderProgram());//�ɻ�
			planeModel[1]=MoXingJiaZai.loadFromFileVertexOnly("feiji22.obj", getResources(),ShaderManager.getOnlyTextureShaderProgram());//�ɻ�
			planeModel[2]=MoXingJiaZai.loadFromFileVertexOnly("feiji33.obj", getResources(),ShaderManager.getOnlyTextureShaderProgram());//�ɻ�
			break;
		case 35:
			//����̹��ģ��
			tanke_gun=MoXingJiaZai.loadFromFileVertexOnly("tank_berral1.obj", getResources(),ShaderManager.getOnlyTextureShaderProgram());//̹��
			break;
		case 36:
			//-------------�����˵��е�����------------------------------------------------------
			backgroundRect=new TextureRect(450, 450, ShaderManager.getOnlyTextureShaderProgram());
            circle_station=new CircleForDraw(ShaderManager.getOnlyColorShaderProgram(), 
            		5, 70,new float[]{0.3f,0.3f,0.3f},ShaderManager.getOnlyTextureShaderProgram());//����ѡ������Ķ�ģ��
			break;
		case 37:
			front_frame=new TextureRect(ratio*2, 2, ShaderManager.getOnlyTextureShaderProgram());//�Ʋ�
			front_cover_button=new TextureRect(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, ShaderManager.getOnlyTextureShaderProgram());//�����˵��µİ�ť
			front_door=new TextureRect(MENU_DOOR_WIDTH, MENU_DOOR_HEIGHT, ShaderManager.getOnlyTextureShaderProgram());//����������
			front_door_bg=new TextureRect(MENU_DOOR_WIDTH, MENU_DOOR_HEIGHT*1.8f, ShaderManager.getOnlyTextureShaderProgram());//����������
			break;
		case 38:
			//�����ڹ�
			barrel=new BarrelForDraw(barrel_length, barrel_radius, ShaderManager.getOnlyTextureShaderProgram());
			//������̨
			barbette=new BarbetteForDraw(barbette_length, barbette_radius,ShaderManager.getOnlyTextureShaderProgram());
			break;
		case 39:
			//�����ӵ��������
			bullet_rect=new TextureRect(BULLET_WIDTH,BULLET_HEIGHT,ShaderManager.getOnlyTextureShaderProgram());
			//�����״ﱳ��
			radar_bg=new TextureRect(BUTTON_RADAR_BG_WIDTH,BUTTON_RADAR_BG_HEIGHT,ShaderManager.getOnlyTextureShaderProgram());
			//�����״�ָ��
			radar_plane=new TextureRect(BUTTON_RADAR_PLANE_WIDTH, BUTTON_RADAR_PLANE_HEIGHT,ShaderManager.getOnlyTextureShaderProgram());
			break;
		case 40:
			kThread=new KeyThread(this);
		    kThread.start();
			break;
		}
	}
	public void initMap()//����ڶ��ص���Ҫ������
	{
		tankeList.clear();//̹������
		archie_List.clear();//����������
		bomb_List.clear();//�ڵ�����
		archie_bomb_List.clear();//�������ڵ�����
		bullet_List.clear();//�ɻ�������ӵ�����
		enemy.clear();//�л���������
		arsenal.clear();//���������
		houseplane.clear();//ƽ������
		treeList.clear();//��
		//�����л�
		for(int i=0;i<enemy_plane_place[mapId].length;i++)
		{
			enemy.add(new EnemyPlane(this,plane,enemy_plane_place[mapId][i][0],
					enemy_plane_place[mapId][i][1],enemy_plane_place[mapId][i][2],enemy_plane_place[mapId][i][3],enemy_plane_place[mapId][i][4],enemy_plane_place[mapId][i][5],
					backgroundRect_blood,	numberRect,mark_placeRect,mark_lock,i
			));
		}
		//����̹��
		for(int i=0;i<ArchieArray[mapId][1].length/2;i++){
			
			tankeList.add(new TanKe(this,bullet_ball,tanke_body,tanke_gun,new float[]{ArchieArray[mapId][1][i*2]*WATER_WIDTH,LAND_HIGHEST,ArchieArray[mapId][1][i*2+1]*WATER_WIDTH},
					(int)ArchieArray[mapId][1][i*2],(int)ArchieArray[mapId][1][i*2+1],backgroundRect_blood,	numberRect,mark_placeRect,mark_lock));
		}
		//�������ڷ����б���
		for(int i=0;i<ArchieArray[mapId][0].length/2;i++)
		{
			archie_List.add(new ArchieForControl
			(
					this,
					barrel,
					barbette,
					cube,
					bullet_ball,
					new float[]{ArchieArray[mapId][0][i*2]*WIDTH_LALNDFORM,LAND_HIGHEST+barbette_length/2,
					ArchieArray[mapId][0][i*2+1]*WIDTH_LALNDFORM},(int)ArchieArray[mapId][0][i*2+1],(int)ArchieArray[mapId][0][i*2],
					backgroundRect_blood,	numberRect,mark_placeRect,mark_lock));
		}
		//���������
		for(int i=0;i<ArchieArray[mapId][2].length/2;i++)
		{
	    	arsenal.add(new Arsenal_House(house, ArchieArray[mapId][2][2*i]*WIDTH_LALNDFORM,
					LAND_HIGHEST, ArchieArray[mapId][2][2*i+1]*WIDTH_LALNDFORM,mark_placeRect,mark_lock,
					(int)ArchieArray[mapId][2][2*i],(int)ArchieArray[mapId][2][2*i+1]
										)
	    	);//���������
	    }
		//������
		for(int i=0;i<ArchieArray[mapId][11].length/4;i++)
		{
			treeList.add(new Tree(treeRect,ArchieArray[mapId][11][i*4]*WIDTH_LALNDFORM,LAND_HIGHEST+treeHeight/2-5,ArchieArray[mapId][11][i*4+1]*WIDTH_LALNDFORM,treeTexId_2,
					(int)ArchieArray[mapId][11][i*4],(int)ArchieArray[mapId][11][i*4+1]	
			));
			treeList.add(new Tree(treeRect,ArchieArray[mapId][11][i*4+2]*WIDTH_LALNDFORM,LAND_HIGHEST+treeHeight/2-5,ArchieArray[mapId][11][i*4+3]*WIDTH_LALNDFORM,treeTexId,
					(int)ArchieArray[mapId][11][i*4+2],(int)ArchieArray[mapId][11][i*4+3]
			));
		}
		//��������
		for(int i=0;i<ArchieArray[mapId][4].length/2;i++){
			houseplane.add(new PlaneHouse(ArchieArray[mapId][4][2*i]*WIDTH_LALNDFORM,LAND_HIGHEST+house_height/2, ArchieArray[mapId][4][2*i+1]*WIDTH_LALNDFORM,
					housePlane,	chimney,(int)ArchieArray[mapId][4][2*i],(int)ArchieArray[mapId][4][2*i+1]
			));
		}
		//�������
		dam=null;		
		if(ArchieArray[mapId][5].length>0)
		{
			dam=new DamForDraw(LAND_HIGHEST-20,30,90,150,ShaderManager.getOnlyTextureShaderProgram());
		}
	}
	//�������ⰴť�ķ�Χ,��onChanged�����е���
	public void ConfigVirtualButtonArea()
	{ 
		//---------------һ���˵�-----------------------------
		//�����˵���ѡ�ť����ز���
		MENU_BUTTON_WIDTH=ratio*0.5f;
		MENU_BUTTON_HEIGHT=1*0.38f;
		float leftEdge=(float)(ratio-MENU_BUTTON_WIDTH/2+MENU_BUTTON_XOffset)/(2*ratio)*SCREEN_WIDTH;
		float rightEdge=(float)(ratio+MENU_BUTTON_WIDTH/2+MENU_BUTTON_XOffset)/(2*ratio)*SCREEN_WIDTH;
		float topEdge=(float)(1-MENU_BUTTON_HEIGHT/2-MENU_BUTTON_YOffset)/2*SCREEN_HEIGHT;
		float bottomEdge=(float)(1+MENU_BUTTON_HEIGHT/2-BUTTON_FIRE_YOffset)/2*SCREEN_HEIGHT;
		MENU_BUTTON_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		//�����˵��еĻ�������ز���
		MENU_DOOR_WIDTH=ratio*2; 
		MENU_DOOR_HEIGHT=1;  
		//------------------------����ҳ�水ť����ز���----------------------------------
		SETTING_BUTTON_WIDTH=ratio; //���ý��水ť�Ŀ��
		SETTING_BUTTON_HEIGHT=0.5f;  //���ý��水ť�ĸ߶�
		
		SETTING_BUTTON_XOffset1=-2*ratio*0.2f;
		SETTING_BUTTON_YOffset1=0.43f;
		
		SETTING_BUTTON_XOffset2=0;
		SETTING_BUTTON_YOffset2=-2*ratio*0.02f;
		
		SETTING_BUTTON_XOffset3=2*ratio*0.2f;
		SETTING_BUTTON_YOffset3=-0.55f;
		
		//����ҳ�水ť�ķ�Χ1
		leftEdge=(float)(ratio-SETTING_BUTTON_WIDTH/2+SETTING_BUTTON_XOffset1)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+SETTING_BUTTON_WIDTH/2+SETTING_BUTTON_XOffset1)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-SETTING_BUTTON_HEIGHT/2-SETTING_BUTTON_YOffset1)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+SETTING_BUTTON_HEIGHT/2-SETTING_BUTTON_YOffset1)/2*SCREEN_HEIGHT;
		SETTING_BUTTON_AREA1=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		//����ҳ�水ť�ķ�Χ2
		leftEdge=(float)(ratio-SETTING_BUTTON_WIDTH/2+SETTING_BUTTON_XOffset2)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+SETTING_BUTTON_WIDTH/2+SETTING_BUTTON_XOffset2)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-SETTING_BUTTON_HEIGHT/2-SETTING_BUTTON_YOffset2)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+SETTING_BUTTON_HEIGHT/2-SETTING_BUTTON_YOffset2)/2*SCREEN_HEIGHT;
		SETTING_BUTTON_AREA2=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		//����ҳ�水ť�ķ�Χ3
		leftEdge=(float)(ratio-SETTING_BUTTON_WIDTH/2+SETTING_BUTTON_XOffset3)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+SETTING_BUTTON_WIDTH/2+SETTING_BUTTON_XOffset3)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-SETTING_BUTTON_HEIGHT/2-SETTING_BUTTON_YOffset3)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+SETTING_BUTTON_HEIGHT/2-SETTING_BUTTON_YOffset3)/2*SCREEN_HEIGHT;
		SETTING_BUTTON_AREA3=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		//��ʼ���˳��Ի���Ŀ�Ⱥ͸߶�
		EXIT_DIALOG_WIDTH=ratio;
		EXIT_DIALOG_HEIGHT=1;
		//ȷ����ť�Ĳ���
		DIALOG_BUTTON_WIDTH=EXIT_DIALOG_WIDTH/2; 
		DIALOG_BUTTON_HEIGHT=EXIT_DIALOG_HEIGHT/2;
		DIALOG_YES_XOffset=-EXIT_DIALOG_WIDTH/4;
		DIALOG_YES_YOffset=-EXIT_DIALOG_HEIGHT/4;
		//���ذ�ť�Ĳ���
		DIALOG_NO_XOffset=EXIT_DIALOG_WIDTH/4;
		DIALOG_NO_YOffset=-EXIT_DIALOG_HEIGHT/4;
		//ȷ����ť�ķ�Χ
		leftEdge=(float)(ratio-DIALOG_BUTTON_WIDTH/2+DIALOG_YES_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+DIALOG_BUTTON_WIDTH/2+DIALOG_YES_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-DIALOG_BUTTON_HEIGHT/2-DIALOG_YES_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+DIALOG_BUTTON_HEIGHT/2-DIALOG_YES_YOffset)/2*SCREEN_HEIGHT;
		DIALOG_BUTTON_YES=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		//���ذ�ť�ķ�Χ
		leftEdge=(float)(ratio-DIALOG_BUTTON_WIDTH/2+DIALOG_NO_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+DIALOG_BUTTON_WIDTH/2+DIALOG_NO_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-DIALOG_BUTTON_HEIGHT/2-DIALOG_NO_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+DIALOG_BUTTON_HEIGHT/2-DIALOG_NO_YOffset)/2*SCREEN_HEIGHT;
		DIALOG_BUTTON_NO=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		//����ҳ��Ŀ�Ⱥ͸߶�
		HELP_WIDTH=ratio*2*0.85f;
		HELP_HEIGHT=5; 
		//����ҳ��Ŀ�Ⱥ͸߶�
		ABOUT_WIDTH=ratio*2*0.85f;
		ABOUT_HEIGHT=4.5f; 
		//-----------------------------------------�����˵�----------------------------------
		//����
		PLANE_SELECT_HEAD_WIDTH=2*ratio;
		PLANE_SELECT_HEAD_HEIGHT=2*0.15f;
		//�����ɻ�ͼƬ�Ĵ�С
		MENU_TWO_PLANE_ICON_WIDTH=2*ratio*0.15f;
		MENU_TWO_PLANE_ICON_HEIGHT=2*0.2f;
		
		MENU_TWO_PLANE_ICON_ONE_XOffset=-ratio+MENU_TWO_PLANE_ICON_WIDTH/2;//��ť��ƫ����
		MENU_TWO_PLANE_ICON_ONE_YOffset=1-PLANE_SELECT_HEAD_HEIGHT-MENU_TWO_PLANE_ICON_HEIGHT/2;
		
		leftEdge=(float)(ratio-MENU_TWO_PLANE_ICON_WIDTH/2+MENU_TWO_PLANE_ICON_ONE_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+MENU_TWO_PLANE_ICON_WIDTH/2+MENU_TWO_PLANE_ICON_ONE_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-MENU_TWO_PLANE_ICON_HEIGHT/2-MENU_TWO_PLANE_ICON_ONE_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+MENU_TWO_PLANE_ICON_HEIGHT/2-MENU_TWO_PLANE_ICON_ONE_YOffset)/2*SCREEN_HEIGHT;
		MENU_TWO_PLANE_ICON_ONE_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		
		MENU_TWO_PLANE_ICON_TWO_XOffset=MENU_TWO_PLANE_ICON_ONE_XOffset+MENU_TWO_PLANE_ICON_WIDTH;//��ť��ƫ����
		MENU_TWO_PLANE_ICON_TWO_YOffset=MENU_TWO_PLANE_ICON_ONE_YOffset;
		
		leftEdge=(float)(ratio-MENU_TWO_PLANE_ICON_WIDTH/2+MENU_TWO_PLANE_ICON_TWO_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+MENU_TWO_PLANE_ICON_WIDTH/2+MENU_TWO_PLANE_ICON_TWO_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-MENU_TWO_PLANE_ICON_HEIGHT/2-MENU_TWO_PLANE_ICON_TWO_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+MENU_TWO_PLANE_ICON_HEIGHT/2-MENU_TWO_PLANE_ICON_ONE_YOffset)/2*SCREEN_HEIGHT;
		MENU_TWO_PLANE_ICON_TWO_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};

		MENU_TWO_PLANE_ICON_THREE_XOffset=MENU_TWO_PLANE_ICON_TWO_XOffset+MENU_TWO_PLANE_ICON_WIDTH;//��ť��ƫ����
		MENU_TWO_PLANE_ICON_THREE_YOffset=MENU_TWO_PLANE_ICON_ONE_YOffset;
		
		leftEdge=(float)(ratio-MENU_TWO_PLANE_ICON_WIDTH/2+MENU_TWO_PLANE_ICON_THREE_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+MENU_TWO_PLANE_ICON_WIDTH/2+MENU_TWO_PLANE_ICON_THREE_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-MENU_TWO_PLANE_ICON_HEIGHT/2-MENU_TWO_PLANE_ICON_THREE_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+MENU_TWO_PLANE_ICON_HEIGHT/2-MENU_TWO_PLANE_ICON_THREE_YOffset)/2*SCREEN_HEIGHT;
		MENU_TWO_PLANE_ICON_THREE_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		
		//�˵����а�ť�Ŀ�Ⱥ͸߶�
		MENU_TWO_BUTTON_WIDTH=2*ratio*0.15f;
		MENU_TWO_BUTTON_HEIGHT=2*0.15f;
		//ȷ����ť
		MENU_TWO_BUTTON_OK_XOffset=ratio-MENU_TWO_BUTTON_WIDTH/1.5f;//��ť��ƫ����
		MENU_TWO_BUTTON_OK_YOffset=-1+MENU_TWO_BUTTON_HEIGHT/1.5f;
		leftEdge=(float)(ratio-MENU_TWO_BUTTON_WIDTH/2+MENU_TWO_BUTTON_OK_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+MENU_TWO_BUTTON_WIDTH/2+MENU_TWO_BUTTON_OK_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-MENU_TWO_BUTTON_HEIGHT/2-MENU_TWO_BUTTON_OK_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+MENU_TWO_BUTTON_HEIGHT/2-MENU_TWO_BUTTON_OK_YOffset)/2*SCREEN_HEIGHT;
		MENU_TWO_BUTTON_OK_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		//�󰴰�ť
		MENU_TWO_BUTTON_LEFT_XOffset=-ratio+MENU_TWO_BUTTON_WIDTH/2;//��ť��ƫ����
		MENU_TWO_BUTTON_LEFT_YOffset=-2*0.1f;
		leftEdge=(float)(ratio-MENU_TWO_BUTTON_WIDTH/2+MENU_TWO_BUTTON_LEFT_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+MENU_TWO_BUTTON_WIDTH/2+MENU_TWO_BUTTON_LEFT_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-MENU_TWO_BUTTON_HEIGHT/2-MENU_TWO_BUTTON_LEFT_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+MENU_TWO_BUTTON_HEIGHT/2-MENU_TWO_BUTTON_LEFT_YOffset)/2*SCREEN_HEIGHT;
		MENU_TWO_BUTTON_LEFT_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		//�Ұ���ť
		MENU_TWO_BUTTON_RIGHT_XOffset=ratio-MENU_TWO_BUTTON_WIDTH/2;//��ť��ƫ����
		MENU_TWO_BUTTON_RIGHT_YOffset=-2*0.1f;
		leftEdge=(float)(ratio-MENU_TWO_BUTTON_WIDTH/2+MENU_TWO_BUTTON_RIGHT_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+MENU_TWO_BUTTON_WIDTH/2+MENU_TWO_BUTTON_RIGHT_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-MENU_TWO_BUTTON_HEIGHT/2-MENU_TWO_BUTTON_RIGHT_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+MENU_TWO_BUTTON_HEIGHT/2-MENU_TWO_BUTTON_RIGHT_YOffset)/2*SCREEN_HEIGHT;
		MENU_TWO_BUTTON_RIGHT_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		//-----------------ѡ��Ϸģʽ��ť----------------------------
		MENU_TWO_GAME_MODEL_BUTTON_WIDTH=2*ratio*0.2f;
		MENU_TWO_GAME_MODEL_BUTTON_HEIGHT=2*0.15f;
		//-----------------ս��ģʽ��ť---------------------
		MENU_TWO_WAR_BUTTON_XOffset=2*ratio*0.15f;//��ť��ƫ����
		MENU_TWO_WAR_BUTTON_YOffset=1-PLANE_SELECT_HEAD_HEIGHT-MENU_TWO_GAME_MODEL_BUTTON_HEIGHT/2;
		//ѡս��ģʽ��ť�ķ�Χ
		leftEdge=(float)(ratio-MENU_TWO_GAME_MODEL_BUTTON_WIDTH/2+MENU_TWO_WAR_BUTTON_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+MENU_TWO_GAME_MODEL_BUTTON_WIDTH/2+MENU_TWO_WAR_BUTTON_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-MENU_TWO_GAME_MODEL_BUTTON_HEIGHT/2-MENU_TWO_WAR_BUTTON_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+MENU_TWO_GAME_MODEL_BUTTON_HEIGHT/2-MENU_TWO_WAR_BUTTON_YOffset)/2*SCREEN_HEIGHT;
		MENU_TWO_WAR_BUTTON_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		//-------------------�ر��ж���ť-----------
		MENU_TWO_ACTION_BUTTON_XOffset=MENU_TWO_WAR_BUTTON_XOffset+MENU_TWO_GAME_MODEL_BUTTON_WIDTH;//��ť��ƫ����
		MENU_TWO_ACTION_BUTTON_YOffset=1-PLANE_SELECT_HEAD_HEIGHT-MENU_TWO_GAME_MODEL_BUTTON_HEIGHT/2;
		//ѡս��ģʽ��ť�ķ�Χ
		leftEdge=(float)(ratio-MENU_TWO_GAME_MODEL_BUTTON_WIDTH/2+MENU_TWO_ACTION_BUTTON_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+MENU_TWO_GAME_MODEL_BUTTON_WIDTH/2+MENU_TWO_ACTION_BUTTON_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-MENU_TWO_GAME_MODEL_BUTTON_HEIGHT/2-MENU_TWO_ACTION_BUTTON_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+MENU_TWO_GAME_MODEL_BUTTON_HEIGHT/2-MENU_TWO_ACTION_BUTTON_YOffset)/2*SCREEN_HEIGHT;
		MENU_TWO_ACTION_BUTTON_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		
		
		//------�����˵�-------��ͼѡ�����İ�ť�Ŀ�Ⱥ͸߶�
		MAP_BUTTON_WIDTH=2*ratio*0.23f; 
		MAP_BUTTON_HEIGHT=2*0.7f;  
		//��һ�صķ�Χ
		MAP_ONE_XOffset=-MAP_BUTTON_WIDTH/0.82f;
		MAP_ONE_YOffset=0f;
		leftEdge=(float)(ratio-MAP_BUTTON_WIDTH/2+MAP_ONE_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+MAP_BUTTON_WIDTH/2+MAP_ONE_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-MAP_BUTTON_HEIGHT/2-MAP_ONE_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+MAP_BUTTON_HEIGHT/2-MAP_ONE_YOffset)/2*SCREEN_HEIGHT;
		MAP_ONE_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		//�ڶ��صķ�Χ
		MAP_TWO_XOffset=0f;
		MAP_TWO_YOffset=0f;
		leftEdge=(float)(ratio-MAP_BUTTON_WIDTH/2+MAP_TWO_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+MAP_BUTTON_WIDTH/2+MAP_TWO_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-MAP_BUTTON_HEIGHT/2-MAP_TWO_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+MAP_BUTTON_HEIGHT/2-MAP_TWO_YOffset)/2*SCREEN_HEIGHT;
		MAP_TWO_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		//�����صķ�Χ
		MAP_THREE_XOffset=MAP_BUTTON_WIDTH/0.77f;
		MAP_THREE_YOffset=0;
		leftEdge=(float)(ratio-MAP_BUTTON_WIDTH/2+MAP_THREE_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+MAP_BUTTON_WIDTH/2+MAP_THREE_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-MAP_BUTTON_HEIGHT/2-MAP_THREE_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+MAP_BUTTON_HEIGHT/2-MAP_THREE_YOffset)/2*SCREEN_HEIGHT;
		MAP_THREE_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
	
		
		//���а����ؿ����ֵĿ�Ⱥ͸߶�
		RANK_MAP_WIDTH=2*ratio*0.12f;
		RANK_MAP_HEIGHT=2*0.06f;
		RANK_NUMBER_WIDTH=2*ratio*0.017f; 
		RANK_NUMBER_HEIGHT=2*0.07f;  
		
		//-------------------------------��Ϸ��˵�����ֵĿ�Ⱥ͸߶�
		NOTICE_WIDTH=2*ratio;
		NOTICE_HEIGHT=2;
		
		//----------------------------------------
		//����ť��ƽ��
		BUTTON_FIRE_XOffset=ratio-BUTTON_FIRE_WIDTH/1.5f;
		BUTTON_FIRE_YOffset=-1+BUTTON_FIRE_HEIGHT/1.5f;//-----------
		
		//����ť
		leftEdge=(float)(ratio-BUTTON_FIRE_WIDTH/2+BUTTON_FIRE_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+BUTTON_FIRE_WIDTH/2+BUTTON_FIRE_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-BUTTON_FIRE_HEIGHT/2-BUTTON_FIRE_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+BUTTON_FIRE_HEIGHT/2-BUTTON_FIRE_YOffset)/2*SCREEN_HEIGHT;
		BUTTON_FIRE_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		
		BUTTON_WEAPON_XOffset=-ratio+BUTTON_WEAPON_WIDTH/1.5f;
		BUTTON_WEAPON_YOffset=1-BUTTON_WEAPON_HEIGHT/1.5f;//-----------
		
		//����ѡ��ť
		leftEdge=(float)(ratio-BUTTON_WEAPON_WIDTH/2+BUTTON_WEAPON_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+BUTTON_WEAPON_WIDTH/2+BUTTON_WEAPON_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-BUTTON_WEAPON_HEIGHT/2-BUTTON_WEAPON_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+BUTTON_WEAPON_HEIGHT/2-BUTTON_WEAPON_YOffset)/2*SCREEN_HEIGHT;
		BUTTON_WEAPON_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		
		//�ӵ�������
		WEAPON_NUMBER_XOffset=BUTTON_WEAPON_XOffset+WEAPON_NUMBER_WIDTH*3 ;
		WEAPON_NUMBER_YOffset=BUTTON_WEAPON_YOffset-WEAPON_NUMBER_HEIGHT/2;
		
		
		//���ϰ�ť��ƽ��
		BUTTON_UP_XOffset=-ratio+BUTTON_UP_WIDTH/2;
		BUTTON_UP_YOffset=-1+BUTTON_UP_HEIGHT*1.8f;//------
		
		//���ϰ�ť
		leftEdge=(float)(ratio-BUTTON_UP_WIDTH/2+BUTTON_UP_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+BUTTON_UP_WIDTH/2+BUTTON_UP_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-BUTTON_UP_HEIGHT/2-BUTTON_UP_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+BUTTON_UP_HEIGHT/2-BUTTON_UP_YOffset)/2*SCREEN_HEIGHT;
		BUTTON_UP_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
		
		BUTTON_DOWN_XOffset=-ratio+BUTTON_DOWN_WIDTH/2;
		BUTTON_DOWN_YOffset=-1+BUTTON_DOWN_HEIGHT/1.7f;//-----------
		//���°�ť
		leftEdge=(float)(ratio-BUTTON_DOWN_WIDTH/2+BUTTON_DOWN_XOffset)/(2*ratio)*SCREEN_WIDTH;
		rightEdge=(float)(ratio+BUTTON_DOWN_WIDTH/2+BUTTON_DOWN_XOffset)/(2*ratio)*SCREEN_WIDTH;
		topEdge=(float)(1-BUTTON_DOWN_HEIGHT/2-BUTTON_DOWN_YOffset)/2*SCREEN_HEIGHT;
		bottomEdge=(float)(1+BUTTON_DOWN_HEIGHT/2-BUTTON_DOWN_YOffset)/2*SCREEN_HEIGHT;
		BUTTON_DOWN_AREA=new float[]{leftEdge,rightEdge,topEdge,bottomEdge};
	}
	//����������̵ķ��ؼ��ķ�������
	public boolean  onKeyBackEvent()
	{
		//�����ǰ�ĵ�ǰ���ڻ����Źرյ�״̬,��ô���·��ؼ�,�����Ŵ�
		if(!isGameOn&&1==isMenuLevel&&doorState==2)
		{
			doorState=0;
			return true;
		}
		if(!isGameOn&&isMenuLevel==2)
		{
			isMenuLevel=1;
			missile_ZOffset=missile_ZOffset_Ori;
			missile_ZOffset_Speed=0;
			return true;
		}
		if(!isGameOn&&isMenuLevel==3)
		{
			isMenuLevel=2;
			return true;
		}
		//�����ǰ���ڿ���״̬,���ҵ�ǰ�Ĳ˵������Ų���5,��ôҪ��ת��5,����ת���˳���ť��
		if(!isGameOn&&1==isMenuLevel&&(doorState==1)&&(curr_menu_index!=5)&&!isMissileDowning)
		{
			moveToExit=true;//��־λ��Ϊtrue
		}
		return true;
	}
}
