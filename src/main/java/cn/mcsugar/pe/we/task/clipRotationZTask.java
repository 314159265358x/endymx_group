package cn.mcsugar.pe.we.task;

import cn.mcsugar.pe.we.ClipboardStore;
import cn.mcsugar.pe.we.WeSimpleTask;
import cn.nukkit.math.BlockVector3;

public class clipRotationZTask extends WeSimpleTask {

	public clipRotationZTask(byte m) {
		super(m);
	}

	@Override
	protected boolean processEdit(ClipboardStore cilp) {
		BlockVector3 size=cilp.getSize();
		int[][][] blocks=cilp.getContent();
		
//		int[][][] resb=new int[size.x][size.z][size.y];
		int zp=size.z/2;
		int b[]=null;
		
		for(int x=0;x<size.x;x++){
			for(int z=0;z<zp;z++){
				b=blocks[x][size.z-z-1];
				blocks[x][size.z-z-1]=blocks[x][z];
				blocks[x][z]=b;
			}
		}
		
		cilp.setContent(blocks);
		return true;
	}

}
