package com.bn.clp;
//�������л����и����
public class ClipGrid 
{
   public static final boolean[][][] CLIP_MASK=
   {
	   {
		   {true,true,true,true,true},
		   {true,true,true,true,true},
		   {true,true,true,true,true}, 
		   {true,true,true,true,true}, 
		   {false,false,false,false,false}
	   },
	   {
		   {true,true,true,true,false},
		   {true,true,true,true,false},
		   {true,true,true,true,false},
		   {true,true,true,true,false},
		   {true,true,true,true,false}
	   },
	   {
		   {false,false,false,false,false},
		   {true,true,true,true,true},
		   {true,true,true,true,true},
		   {true,true,true,true,true},
		   {true,true,true,true,true}
	   },
	   {
		   {false,true,true,true,true},
		   {false,true,true,true,true},
		   {false,true,true,true,true},
		   {false,true,true,true,true},
		   {false,true,true,true,true}
	   }
   };
}