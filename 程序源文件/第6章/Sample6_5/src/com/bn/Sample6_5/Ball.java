package com.bn.Sample6_5;

import java.nio.ByteBuffer;

import static com.bn.Sample6_5.Constant.*;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import android.opengl.GLES20;

//��
public class Ball {
	int mProgram;// �Զ�����Ⱦ������ɫ������id
	int muMVPMatrixHandle;// �ܱ任��������
    int muMMatrixHandle;//λ�á���ת�任��������
	int muRHandle;// ��İ뾶��������   
	int maPositionHandle; // ����λ����������
    int maNormalHandle; //���㷨������������
    int maLightLocationHandle;//��Դλ����������
    int maCameraHandle; //�����λ���������� 
    
    
	String mVertexShader;// ������ɫ��
	String mFragmentShader;// ƬԪ��ɫ��

	FloatBuffer mVertexBuffer;// �����������ݻ���
	FloatBuffer mNormalBuffer;//���㷨�������ݻ���
	int vCount = 0;
	float yAngle = 0;// ��y����ת�ĽǶ�
	float xAngle = 0;// ��x����ת�ĽǶ�
	float zAngle = 0;// ��z����ת�ĽǶ�
	float r = 0.8f;
	public Ball(MySurfaceView mv) {
		// ��ʼ��������������ɫ����
		initVertexData();
		// ��ʼ��shader
		initShader(mv);
	}

	// ��ʼ�������������ݵķ���
	public void initVertexData() {
		// �����������ݵĳ�ʼ��================begin============================
		ArrayList<Float> alVertix = new ArrayList<Float>();// ��Ŷ��������ArrayList
		final int angleSpan = 10;// ������е�λ�зֵĽǶ�
		for (int vAngle = -90; vAngle < 90; vAngle = vAngle + angleSpan)// ��ֱ����angleSpan��һ��
		{
			for (int hAngle = 0; hAngle <= 360; hAngle = hAngle + angleSpan)// ˮƽ����angleSpan��һ��
			{// ����������һ���ǶȺ�����Ӧ�Ĵ˵��������ϵ�����
				float x0 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle)) * Math.cos(Math
						.toRadians(hAngle)));
				float y0 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle)) * Math.sin(Math
						.toRadians(hAngle)));
				float z0 = (float) (r * UNIT_SIZE * Math.sin(Math
						.toRadians(vAngle)));

				float x1 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle)) * Math.cos(Math
						.toRadians(hAngle + angleSpan)));
				float y1 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle)) * Math.sin(Math
						.toRadians(hAngle + angleSpan)));
				float z1 = (float) (r * UNIT_SIZE * Math.sin(Math
						.toRadians(vAngle)));

				float x2 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
						.cos(Math.toRadians(hAngle + angleSpan)));
				float y2 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
						.sin(Math.toRadians(hAngle + angleSpan)));
				float z2 = (float) (r * UNIT_SIZE * Math.sin(Math
						.toRadians(vAngle + angleSpan)));

				float x3 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
						.cos(Math.toRadians(hAngle)));
				float y3 = (float) (r * UNIT_SIZE
						* Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
						.sin(Math.toRadians(hAngle)));
				float z3 = (float) (r * UNIT_SIZE * Math.sin(Math
						.toRadians(vAngle + angleSpan)));

				// �����������XYZ��������Ŷ��������ArrayList
				alVertix.add(x1);
				alVertix.add(y1);
				alVertix.add(z1);
				alVertix.add(x3);
				alVertix.add(y3);
				alVertix.add(z3);
				alVertix.add(x0);
				alVertix.add(y0);
				alVertix.add(z0);

				alVertix.add(x1);
				alVertix.add(y1);
				alVertix.add(z1);
				alVertix.add(x2);
				alVertix.add(y2);
				alVertix.add(z2);
				alVertix.add(x3);
				alVertix.add(y3);
				alVertix.add(z3);
			}
		}
		vCount = alVertix.size() / 3;// ���������Ϊ����ֵ������1/3����Ϊһ��������3������

		// ��alVertix�е�����ֵת�浽һ��float������
		float vertices[] = new float[vCount * 3];
		for (int i = 0; i < alVertix.size(); i++) {
			vertices[i] = alVertix.get(i);
		}

		// ���������������ݻ���
		// vertices.length*4����Ϊһ�������ĸ��ֽ�
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());// �����ֽ�˳��
		mVertexBuffer = vbb.asFloatBuffer();// ת��Ϊint�ͻ���
		mVertexBuffer.put(vertices);// �򻺳����з��붥����������
		mVertexBuffer.position(0);// ���û�������ʼλ��
		// �ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
		// ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
		
		//�������ƶ��㷨��������
        ByteBuffer nbb = ByteBuffer.allocateDirect(vertices.length*4);
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = nbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mNormalBuffer.put(vertices);//�򻺳����з��붥����������
        mNormalBuffer.position(0);//���û�������ʼλ��     
	}

	// ��ʼ��shader
	public void initShader(MySurfaceView mv) {
		// ���ض�����ɫ���Ľű�����
		mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh",
				mv.getResources());
		// ����ƬԪ��ɫ���Ľű�����
		mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh",
				mv.getResources());
		// ���ڶ�����ɫ����ƬԪ��ɫ����������
		mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
		// ��ȡ�����ж���λ����������
		maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
		// ��ȡ�������ܱ任��������
		muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        //��ȡλ�á���ת�任��������
        muMMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMMatrix");  
		// ��ȡ��������뾶����
		muRHandle = GLES20.glGetUniformLocation(mProgram, "uR");
        //��ȡ�����ж��㷨������������  
        maNormalHandle= GLES20.glGetAttribLocation(mProgram, "aNormal");
        //��ȡ�����й�Դλ������
        maLightLocationHandle=GLES20.glGetUniformLocation(mProgram, "uLightLocation");
        //��ȡ�����������λ������
        maCameraHandle=GLES20.glGetUniformLocation(mProgram, "uCamera"); 
	}

	public void drawSelf() {		
    	MatrixState.rotate(xAngle, 1, 0, 0);//��X��ת��
    	MatrixState.rotate(yAngle, 0, 1, 0);//��Y��ת��
    	MatrixState.rotate(zAngle, 0, 0, 1);//��Z��ת��
		// �ƶ�ʹ��ĳ����ɫ������
		GLES20.glUseProgram(mProgram);
		// �����ձ任��������ɫ������
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
				MatrixState.getFinalMatrix(), 0); 
        //��λ�á���ת�任��������ɫ������
        GLES20.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0);
		// ���뾶�ߴ紫����ɫ������
		GLES20.glUniform1f(muRHandle, r * UNIT_SIZE);  
        //����Դλ�ô�����ɫ������   
        GLES20.glUniform3fv(maLightLocationHandle, 1, MatrixState.lightPositionFB);
        //�������λ�ô�����ɫ������   
        GLES20.glUniform3fv(maCameraHandle, 1, MatrixState.cameraFB);
        
		// ������λ�����ݴ�����Ⱦ����
		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
				false, 3 * 4, mVertexBuffer);
        //�����㷨�������ݴ�����Ⱦ����
		GLES20.glVertexAttribPointer(maNormalHandle, 3, GLES20.GL_FLOAT, false,
				3 * 4, mNormalBuffer);
		// ���ö���λ������
		GLES20.glEnableVertexAttribArray(maPositionHandle); 
        GLES20.glEnableVertexAttribArray(maNormalHandle);// ���ö��㷨��������
		// ������		
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
	}
}
