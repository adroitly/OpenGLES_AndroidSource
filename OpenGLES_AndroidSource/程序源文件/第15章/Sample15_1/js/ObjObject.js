//加载的用于绘制的3D物体
	    function ObjObject
	    (
	       gl,						 //GL上下文
	       vertexDataIn    //顶点坐标数组
	    )
	    {
	    	  //接收顶点数据
	    	  this.vertexData=vertexDataIn;   	  
	    	  //得到顶点数量
	    	  this.vcount=this.vertexData.length/3;
	    	  //创建顶点数据缓冲
	    	  this.vertexBuffer=gl.createBuffer();
	    	  //将顶点数据送入缓冲
          gl.bindBuffer(gl.ARRAY_BUFFER,this.vertexBuffer);
          gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(this.vertexData),gl.STATIC_DRAW);  
          //加载着色器程序
          this.program= loadShaderSerial(gl,"vshader", "fshader"); 
          //启用顶点数据
	        gl.enableVertexAttribArray(gl.getAttribLocation(this.program, "aPosition"));        
	        //将顶点数据送入渲染管线
	        gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexBuffer);
	        gl.vertexAttribPointer(gl.getAttribLocation(this.program, "aPosition"), 3, gl.FLOAT, false, 0, 0);   
	        
	        this.drawSelf=function(ms)
	        {				      
		          //送入总矩阵
				      var uMVPMatrixHandle=gl.getUniformLocation(this.program, "uMVPMatrix");		      
				      gl.uniformMatrix4fv(uMVPMatrixHandle,false,new Float32Array(ms.getFinalMatrix())); 
			
						  //用顶点法绘制物体
		          gl.drawArrays(gl.TRIANGLES, 0, this.vcount); 
	        }      
	    }