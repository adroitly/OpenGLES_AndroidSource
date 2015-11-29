//���ص����ڻ��Ƶ�3D����
	    function ObjObject
	    (
	       gl,						 //GL������
	       vertexDataIn    //������������
	    )
	    {
	    	  //���ն�������
	    	  this.vertexData=vertexDataIn;   	  
	    	  //�õ���������
	    	  this.vcount=this.vertexData.length/3;
	    	  //�����������ݻ���
	    	  this.vertexBuffer=gl.createBuffer();
	    	  //�������������뻺��
          gl.bindBuffer(gl.ARRAY_BUFFER,this.vertexBuffer);
          gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(this.vertexData),gl.STATIC_DRAW);  
          //������ɫ������
          this.program= loadShaderSerial(gl,"vshader", "fshader"); 
          //���ö�������
	        gl.enableVertexAttribArray(gl.getAttribLocation(this.program, "aPosition"));        
	        //����������������Ⱦ����
	        gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexBuffer);
	        gl.vertexAttribPointer(gl.getAttribLocation(this.program, "aPosition"), 3, gl.FLOAT, false, 0, 0);   
	        
	        this.drawSelf=function(ms)
	        {				      
		          //�����ܾ���
				      var uMVPMatrixHandle=gl.getUniformLocation(this.program, "uMVPMatrix");		      
				      gl.uniformMatrix4fv(uMVPMatrixHandle,false,new Float32Array(ms.getFinalMatrix())); 
			
						  //�ö��㷨��������
		          gl.drawArrays(gl.TRIANGLES, 0, this.vcount); 
	        }      
	    }