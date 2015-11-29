		    //��ʼ��WebGL Canvas
				function initWebGLCanvas(canvasName)
				{
				    var canvas = document.getElementById(canvasName);
				    var names = ["webgl", "experimental-webgl", "webkit-3d", "moz-webgl"];
	          		var context = null;
				    for (var ii = 0; ii < names.length; ++ii) 
				    {
					    try 
					    {
					      context = canvas.getContext(names[ii], null);				      
					    } 
					    catch(e) {}
					    if (context) 
					    {
					      break;
					    }
				    }			    
				    return context;
				}
	
				//���ص�����ɫ���ķ���			
				function loadSingleShader(ctx, shaderId)
				{
				    var shaderScript = document.getElementById(shaderId);
				    if (!shaderScript) 
				    {
				        log("*** Error: shader script '"+shaderId+"' not found");
				        return null;
				    }
				
				    if (shaderScript.type == "x-shader/x-vertex")
				        var shaderType = ctx.VERTEX_SHADER;
				    else if (shaderScript.type == "x-shader/x-fragment")
				        var shaderType = ctx.FRAGMENT_SHADER;
				    else {
				        log("*** Error: shader script '"+shaderId+"' of undefined type '"+shaderScript.type+"'");
				        return null;
				    }
				
				    // Create the shader object
				    var shader = ctx.createShader(shaderType);
				
				    // Load the shader source
				    ctx.shaderSource(shader, shaderScript.text);
				
				    // Compile the shader
				    ctx.compileShader(shader);
				
				    // Check the compile status
				    var compiled = ctx.getShaderParameter(shader, ctx.COMPILE_STATUS);
				    if (!compiled && !ctx.isContextLost()) {
				        // Something went wrong during compilation; get the error
				        var error = ctx.getShaderInfoLog(shader);
				        log("*** Error compiling shader '"+shaderId+"':"+error);
				        ctx.deleteShader(shader);
				        return null;
				    }			
				    return shader;
				}	
				
				//���ض���ƬԪһ����ɫ���ķ���
				function loadShaderSerial(gl, vshader, fshader)
				{
				    //���ض�����ɫ��
				    var vertexShader = loadSingleShader(gl, vshader);
				    //����ƬԪ��ɫ��
				    var fragmentShader = loadSingleShader(gl, fshader);
				
				    //������ɫ������
				    var program = gl.createProgram();
				
				    //��������ɫ����ƬԪ��ɫ���ҽӵ���ɫ������
				    gl.attachShader (program, vertexShader);
				    gl.attachShader (program, fragmentShader);
				
				    // Bind attributes
				    //for (var i = 0; i < attribs.length; ++i)
				    //    gl.bindAttribLocation (program, i, attribs[i]);
				
				    //������ɫ������
				    gl.linkProgram(program);
				
				    //��������Ƿ�ɹ�
				    var linked = gl.getProgramParameter(program, gl.LINK_STATUS);
				    if (!linked && !gl.isContextLost()) 
				    {
				        //��ȡ���ڿ���̨��ӡ������Ϣ
				        var error = gl.getProgramInfoLog (program);
				        log("Error in program linking:"+error);
				
				        gl.deleteProgram(program);
				        gl.deleteProgram(fragmentShader);
				        gl.deleteProgram(vertexShader);
				
				        return null;
				    }
				   	gl.useProgram(program);
				
				    gl.enable(gl.DEPTH_TEST);
				    return program;
				}		
				
